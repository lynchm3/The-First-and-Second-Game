package com.marklynch.ai.routines;

import com.marklynch.Game;
import com.marklynch.actions.ActionDropItems;
import com.marklynch.actions.ActionLock;
import com.marklynch.actions.ActionRing;
import com.marklynch.actions.ActionTalk;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.constructs.conversation.Conversation;
import com.marklynch.level.constructs.conversation.ConversationPart;
import com.marklynch.level.constructs.conversation.ConversationResponse;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Blind;
import com.marklynch.objects.actors.Mort;
import com.marklynch.objects.tools.Bell;

public class AIRoutineForMort extends AIRoutine {

	Mort mort;
	public boolean rangBellAsLastResort;
	public boolean retreatedToRoom = false;

	enum FEEDING_DEMO_STATE {
		WALK_TO_TROUGH, PLACE_MEAT, RING_BELL, WALK_AWAY, WAIT_FOR_BLIND_TO_ENTER, WAIT_FOR_BLIND_TO_LEAVE
	};

	public FEEDING_DEMO_STATE feedingDemoState = FEEDING_DEMO_STATE.WALK_TO_TROUGH;

	public AIRoutineForMort() {

		super();
		aiType = AI_TYPE.FIGHTER;

		keepInBounds = true;
	}

	public AIRoutineForMort(Actor mort) {

		super(mort);
		this.mort = (Mort) mort;
		aiType = AI_TYPE.FIGHTER;

		keepInBounds = true;
	}

	@Override
	public void update() {

		aiRoutineStart();

		// If blind are in mine and getting too close to mgmt door, move to it
		float mortsDistanceFromBedroomDoor = mort
				.straightLineDistanceTo(mort.questCaveOfTheBlind.mortsGameRoomDoor.squareGameObjectIsOn);
		if (!retreatedToRoom) {
			for (Blind blind : mort.questCaveOfTheBlind.blind) {
				if (blind.remainingHealth > 0 && blind.squareGameObjectIsOn.structureRoomSquareIsIn == mort.mortsMine) {
					float blindDistanceFromMortsRoom = blind
							.straightLineDistanceTo(mort.questCaveOfTheBlind.mortsGameRoomDoor.squareGameObjectIsOn);
					if (blindDistanceFromMortsRoom - mortsDistanceFromBedroomDoor < 4) {
						Square doorSquare = mort.questCaveOfTheBlind.mortsGameRoomDoor.squareGameObjectIsOn;
						Square safeSideOfDoorSquare = Game.level.squares[doorSquare.xInGrid - 1][doorSquare.yInGrid];
						mort.performingFeedingDemo = false;
						if (mort.squareGameObjectIsOn == safeSideOfDoorSquare) {
							mort.activityDescription = ACTIVITY_DESCRIPTION_HIDING;
							new ActionLock(mort, mort.questCaveOfTheBlind.mortsGameRoomDoor).perform();
							mort.investigationsMap.clear();
							searchCooldown = 0;
							retreatedToRoom = true;
							roomBounds.remove(mort.mortsMine);
						} else {
							mort.activityDescription = ACTIVITY_DESCRIPTION_RETREATING;
							AIRoutineUtils.moveTowardsTargetSquare(safeSideOfDoorSquare);
						}
						return;
					}

				}
			}
		}

		// Player attacker mort and mort is under half health - ring bell
		if (!rangBellAsLastResort && mort.remainingHealth < mort.totalHealth / 2) {
			Bell bell = (Bell) mort.inventory.getGameObjectOfClass(Bell.class);
			if (bell != null && mort.getAttackers().contains(Game.level.player)) {
				new ActionTalk(actor, Game.level.player, this.getConversationLastResort()).perform();
				new ActionRing(mort, bell).perform();
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_RINGING_DINNER_BELL;
				rangBellAsLastResort = true;
				return;
			}
		}

		// Fight
		if (runFightRoutine(true))
			return;

		// Crime reaction
		if (runCrimeReactionRoutine())
			return;

		// Search
		if (runSearchRoutine())
			return;

		// Search cooldown
		if (runSearchCooldown())
			return;

		// Door maintenance routine
		if (runDoorRoutine())
			return;

		if (retreatedToRoom)
			return;

		// Feeding demo
		if (mort.performingFeedingDemo) {

			if (feedingDemoState == FEEDING_DEMO_STATE.WALK_TO_TROUGH) {
				AIRoutineUtils.moveTowards(mort.questCaveOfTheBlind.troughSquare);

				if (mort.straightLineDistanceTo(mort.questCaveOfTheBlind.troughSquare) <= 1)
					feedingDemoState = FEEDING_DEMO_STATE.PLACE_MEAT;
				return;
			}

			if (feedingDemoState == FEEDING_DEMO_STATE.PLACE_MEAT) {

				if (!mort.inventory.contains(mort.mortsMeatChunk)) {
					mort.addAttackerForThisAndGroupMembers(Game.level.player);
					this.actor.activityDescription = "FEELING BETRAYED";
					actor.createConversation("You! You robbed me!");
					mort.performingFeedingDemo = false;
					return;
				}

				new ActionDropItems(mort, mort.questCaveOfTheBlind.troughSquare, mort.mortsMeatChunk).perform();
				mort.mortsMeatChunk.quest = null;

				feedingDemoState = FEEDING_DEMO_STATE.RING_BELL;

				return;
			}

			if (feedingDemoState == FEEDING_DEMO_STATE.RING_BELL) {

				if (!mort.inventory.contains(mort.mortsBell)) {
					mort.addAttackerForThisAndGroupMembers(Game.level.player);
					this.actor.activityDescription = "FEELING BETRAYED";
					actor.createConversation("You! You robbed me!");
					mort.performingFeedingDemo = false;
					return;
				}

				new ActionRing(mort, mort.mortsBell).perform();
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_RINGING_DINNER_BELL;
				actor.createConversation("Dinner time!");

				for (Blind blind : mort.questCaveOfTheBlind.blind) {
					if (blind.remainingHealth > 0
							&& blind.squareGameObjectIsOn.structureRoomSquareIsIn == mort.mortsMine) {
						feedingDemoState = FEEDING_DEMO_STATE.WAIT_FOR_BLIND_TO_LEAVE;
					}
				}
				return;
			}

			if (feedingDemoState == FEEDING_DEMO_STATE.WAIT_FOR_BLIND_TO_LEAVE) {

				// MOVE BACK
				AIRoutineUtils.moveTowards(mort.questCaveOfTheBlind.safeSquare);
				for (Blind blind : mort.questCaveOfTheBlind.blind) {
					if (blind.remainingHealth > 0
							&& blind.squareGameObjectIsOn.structureRoomSquareIsIn == mort.mortsMine) {
						return;
					}
				}
				mort.performingFeedingDemo = false;
			}
		}

		// Can mort see the Player in his territory? If so record it. If not,
		// follow.
		if (actor.canSeeGameObject(Game.level.player) && targetInTerritory(Game.level.player)) {
			if (keepTrackOf(Game.level.player)) {
				return;
			}
		} else if (lastLocationSeenActorToKeepTrackOf != null
				&& squareInTerritory(lastLocationSeenActorToKeepTrackOf)) {
			if (keepTrackOf(Game.level.player)) {
				return;
			}
		} else if (lastLocationSeenActorToKeepTrackOf != null
				&& !squareInTerritory(lastLocationSeenActorToKeepTrackOf)) {
			lastLocationSeenActorToKeepTrackOf = null;
		}

		if (squareInTerritory(Game.level.player.squareGameObjectIsOn)) {
		} else {
			AIRoutineUtils.moveTowardsTargetSquare(Mort.mortsStandingSpot);
		}
	}

	public boolean targetInTerritory(Actor target) {
		if (target.squareGameObjectIsOn.structureRoomSquareIsIn != mort.mortsMine
				&& target.squareGameObjectIsOn.structureRoomSquareIsIn != mort.mortsRoom
				&& target.squareGameObjectIsOn.structureRoomSquareIsIn != mort.mortsVault
				&& target.squareGameObjectIsOn != mort.mortsRoomDoorway
				&& target.squareGameObjectIsOn != mort.mortsVaultDoorway)
			return false;

		return true;

	}

	public boolean squareInTerritory(Square square) {
		if (square.structureRoomSquareIsIn != mort.mortsMine && square.structureRoomSquareIsIn != mort.mortsRoom
				&& square.structureRoomSquareIsIn != mort.mortsVault && square != mort.mortsRoomDoorway
				&& square != mort.mortsVaultDoorway)
			return false;

		return true;

	}

	private Conversation getConversationLastResort() {

		ConversationResponse conversationReponseDone = new ConversationResponse("Done", null);
		ConversationPart conversationPartYouWontGetOut = new ConversationPart(
				new Object[] { "You won't get out of here alive [Mort rings his bell]" },
				new ConversationResponse[] { conversationReponseDone }, mort);

		return new Conversation(conversationPartYouWontGetOut, actor, true);

	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForMort(actor);
	}
}
