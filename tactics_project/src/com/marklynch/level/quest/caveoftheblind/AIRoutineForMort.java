package com.marklynch.level.quest.caveoftheblind;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Templates;
import com.marklynch.objects.ThoughtBubbles;
import com.marklynch.objects.actions.ActionDrop;
import com.marklynch.objects.actions.ActionGive;
import com.marklynch.objects.actions.ActionLock;
import com.marklynch.objects.actions.ActionMine;
import com.marklynch.objects.actions.ActionRing;
import com.marklynch.objects.actions.ActionTake;
import com.marklynch.objects.actions.ActionTalk;
import com.marklynch.objects.actions.ActionThrow;
import com.marklynch.objects.tools.Bell;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;

public class AIRoutineForMort extends AIRoutine {

	Mort mort;
	GameObject target;
	boolean rangBellAsLastResort;
	boolean retreatedToRoom = false;

	enum FEEDING_DEMO_STATE {
		WALK_TO_TROUGH, PLACE_MEAT, RING_BELL, WALK_AWAY, WAIT_FOR_BLIND_TO_ENTER, WAIT_FOR_BLIND_TO_LEAVE
	};

	public FEEDING_DEMO_STATE feedingDemoState = FEEDING_DEMO_STATE.WALK_TO_TROUGH;

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	final String ACTIVITY_DESCRIPTION_SKINNING = "Skinning";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";
	final String ACTIVITY_DESCRIPTION_SELLING_LOOT = "Selling spoils";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";
	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";
	final String ACTIVITY_DESCRIPTION_FOLLOWING = "Following";
	final String ACTIVITY_DESCRIPTION_RETREATING = "Retreating";
	final String ACTIVITY_DESCRIPTION_HIDING = "Hiding";
	final String ACTIVITY_DESCRIPTION_RINGING_DINNER_BELL = "Ringing Dinner Bell";

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	public AIRoutineForMort(Mort mort) {

		super(mort);
		this.mort = mort;
		aiType = AI_TYPE.FIGHTER;
	}

	@Override
	public void update() {

		this.actor.miniDialogue = null;
		this.actor.activityDescription = null;
		this.actor.miniDialogue = null;
		this.actor.thoughtBubbleImageTexture = null;

		createSearchLocationsBasedOnVisibleAttackers();
		createSearchLocationsBasedOnVisibleCriminals();
		createSearchLocationsBasedOnSounds(Weapon.class);

		// Remove search locations if outside jurisdiction
		ArrayList<GameObject> toRemove = new ArrayList<GameObject>();
		for (GameObject actor : mort.investigationsMap.keySet()) {
			Square squareToSearch = mort.investigationsMap.get(actor).square;
			if (retreatedToRoom) {
				if (squareToSearch.structureRoomSquareIsIn != mort.mortsRoom
						&& squareToSearch.structureRoomSquareIsIn != mort.mortsVault
						&& squareToSearch != mort.mortsRoomDoorway) {
					toRemove.add(actor);
				}

			} else {
				if (squareToSearch.structureRoomSquareIsIn != mort.mortsMine
						&& squareToSearch.structureRoomSquareIsIn != mort.mortsRoom
						&& squareToSearch.structureRoomSquareIsIn != mort.mortsVault
						&& squareToSearch != mort.mortsRoomDoorway) {
					toRemove.add(actor);
				}

			}
		}

		for (GameObject actor : toRemove) {
			mort.investigationsMap.remove(actor);
		}

		// If blind are in mine and getting too close to mgmt door, move to it
		float mortsDistanceFromBedroomDoor = mort
				.straightLineDistanceTo(mort.questCaveOfTheBlind.mortsBedroomDoor.squareGameObjectIsOn);
		for (Blind blind : mort.questCaveOfTheBlind.blind) {
			if (blind.remainingHealth > 0 && blind.squareGameObjectIsOn.structureRoomSquareIsIn == mort.mortsMine) {
				float blindDistanceFromMortsRoom = blind
						.straightLineDistanceTo(mort.questCaveOfTheBlind.mortsBedroomDoor.squareGameObjectIsOn);
				if (blindDistanceFromMortsRoom - mortsDistanceFromBedroomDoor < 4) {
					Square doorSquare = mort.questCaveOfTheBlind.mortsBedroomDoor.squareGameObjectIsOn;
					Square safeSideOfDoorSquare = Game.level.squares[doorSquare.xInGrid - 1][doorSquare.yInGrid];
					mort.performingFeedingDemo = false;
					if (mort.squareGameObjectIsOn == safeSideOfDoorSquare) {
						mort.activityDescription = ACTIVITY_DESCRIPTION_HIDING;
						new ActionLock(mort, mort.questCaveOfTheBlind.mortsBedroomDoor).perform();
						mort.investigationsMap.clear();
						searchCooldown = 0;
						retreatedToRoom = true;
					} else {
						mort.activityDescription = ACTIVITY_DESCRIPTION_RETREATING;
						AIRoutineUtils.moveTowardsTargetSquare(safeSideOfDoorSquare);
					}
					return;
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
				// this.actor.miniDialogue = "You won't get out of here alive";
				// HERE
				rangBellAsLastResort = true;
				return;
			}
		}

		if (runFightRoutine()) {
			createSearchLocationsBasedOnVisibleAttackers();
			return;
		}

		if (runCrimeReactionRoutine()) {
			createSearchLocationsBasedOnVisibleAttackers();
			return;
		}

		if (runSearchRoutine()) {
			createSearchLocationsBasedOnVisibleAttackers();
			return;
		}

		if (searchCooldown > 0) {
			runSearchCooldown();
			searchCooldown--;
			return;
		}

		// if already retreated to room and not in it now
		if (retreatedToRoom && mort.squareGameObjectIsOn.structureRoomSquareIsIn != mort.mortsRoom
				&& mort.squareGameObjectIsOn.structureRoomSquareIsIn != mort.mortsVault) {
			Square doorSquare = mort.questCaveOfTheBlind.mortsBedroomDoor.squareGameObjectIsOn;
			Square safeSideOfDoorSquare = Game.level.squares[doorSquare.xInGrid - 1][doorSquare.yInGrid];
			mort.performingFeedingDemo = false;
			if (mort.squareGameObjectIsOn == safeSideOfDoorSquare) {
				mort.activityDescription = ACTIVITY_DESCRIPTION_HIDING;
				new ActionLock(mort, mort.questCaveOfTheBlind.mortsBedroomDoor).perform();
				mort.investigationsMap.clear();
				searchCooldown = 0;
				retreatedToRoom = true;
			} else {
				mort.activityDescription = ACTIVITY_DESCRIPTION_RETREATING;
				AIRoutineUtils.moveTowardsTargetSquare(safeSideOfDoorSquare);
			}
			mort.activityDescription = ACTIVITY_DESCRIPTION_HIDING;
			return;
		}

		if (retreatedToRoom)
			return;

		// Feeding demo
		if (mort.performingFeedingDemo) {

			if (feedingDemoState == FEEDING_DEMO_STATE.WALK_TO_TROUGH) {
				AIRoutineUtils.moveTowardsSquareToBeAdjacent(mort.questCaveOfTheBlind.troughSquare);

				if (mort.straightLineDistanceTo(mort.questCaveOfTheBlind.troughSquare) <= 1)
					feedingDemoState = FEEDING_DEMO_STATE.PLACE_MEAT;
				return;
			}

			if (feedingDemoState == FEEDING_DEMO_STATE.PLACE_MEAT) {

				if (!mort.inventory.contains(mort.mortsMeatChunk)) {
					mort.addAttackerForThisAndGroupMembers(Game.level.player);
					this.actor.activityDescription = "FEELING BETRAYED";
					this.actor.miniDialogue = "You! You robbed me!";
					mort.performingFeedingDemo = false;
					return;
				}

				new ActionDrop(mort, mort.questCaveOfTheBlind.troughSquare, mort.mortsMeatChunk).perform();
				mort.mortsMeatChunk.quest = null;

				feedingDemoState = FEEDING_DEMO_STATE.RING_BELL;

				return;
			}

			if (feedingDemoState == FEEDING_DEMO_STATE.RING_BELL) {

				if (!mort.inventory.contains(mort.mortsBell)) {
					mort.addAttackerForThisAndGroupMembers(Game.level.player);
					this.actor.activityDescription = "FEELING BETRAYED";
					this.actor.miniDialogue = "You! You robbed me!";
					mort.performingFeedingDemo = false;
					return;
				}

				new ActionRing(mort, mort.mortsBell).perform();
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_RINGING_DINNER_BELL;
				this.actor.miniDialogue = "Dinner time!!!";

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
				AIRoutineUtils.moveTowardsSquareToBeAdjacent(mort.questCaveOfTheBlind.safeSquare);
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
			AIRoutineUtils.moveTowardsTargetSquare(mort.mortsStandingSpot);
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

	@Override
	protected boolean runCrimeReactionRoutine() {
		for (final Actor criminal : actor.crimesWitnessed.keySet()) {
			int accumulatedSeverity = 0;
			ArrayList<Crime> unresolvedIllegalMining = new ArrayList<Crime>();
			final ArrayList<Crime> unresolvedCrimes = new ArrayList<Crime>();
			final ArrayList<GameObject> stolenItemsOnCriminal = new ArrayList<GameObject>();
			final ArrayList<GameObject> stolenItemsOnGround = new ArrayList<GameObject>();
			// final ArrayList<GameObject> stolenItemsOnInContainer = new
			// ArrayList<GameObject>();

			// Mark issues as resolved
			for (Crime crime : actor.crimesWitnessed.get(criminal)) {
				if (crime.resolved == false) {
					if (crime.stolenItems.length == 0) {
						crime.resolved = true;
						continue;
					}
					boolean itemsToBeRetaken = false;
					for (GameObject stolenItem : crime.stolenItems) {
						if (criminal.inventory.contains(stolenItem) || stolenItem.squareGameObjectIsOn != null) {
							itemsToBeRetaken = true;
						}
					}
					if (itemsToBeRetaken)
						crime.resolved = false;
					else
						crime.resolved = true;
				}
			}

			// Create list of unresolved crimes, stolenItems
			// Also calculate accumulated severity of crimes
			for (Crime crime : actor.crimesWitnessed.get(criminal)) {
				accumulatedSeverity += crime.severity;
				if (crime.resolved == false) {
					unresolvedCrimes.add(crime);
					if (crime.action instanceof ActionMine) {
						unresolvedIllegalMining.add(crime);
					}
					for (GameObject stolenItem : crime.stolenItems) {
						if (criminal.inventory.contains(stolenItem)) {
							stolenItemsOnCriminal.add(stolenItem);
						} else if (stolenItem.squareGameObjectIsOn != null) {
							stolenItemsOnGround.add(stolenItem);
						}
					}

				}
			}

			if (accumulatedSeverity >= 10) {
				actor.addAttackerForNearbyFactionMembersIfVisible(criminal);
				actor.addAttackerForThisAndGroupMembers(criminal);
				for (Crime unresolvedCrime : unresolvedCrimes) {
					unresolvedCrime.resolved = true;
				}
				return runFightRoutine();
			} else if (unresolvedIllegalMining.size() > 0) {
				actor.miniDialogue = "MY ORES!";
				new ActionThrow(actor, criminal, Templates.ROCK.makeCopy(null, null)).perform();
				for (GameObject stolenItem : stolenItemsOnCriminal) {
					if (criminal.inventory.contains(stolenItem)) {
						new ActionDrop(criminal, criminal.squareGameObjectIsOn, stolenItem).perform();
						this.actor.thoughtBubbleImageTexture = stolenItem.imageTexture;
					}
				}
				for (Crime unresolvedCrime : unresolvedCrimes) {
					unresolvedCrime.resolved = true;
				}
				// actor.thoughtBubbleImageTexture = ThoughtBubbles.JUSTICE;
				return true;
			} else if (stolenItemsOnCriminal.size() > 0) {
				if (actor.straightLineDistanceTo(criminal.squareGameObjectIsOn) == 1) {
					new ActionTalk(this.actor, criminal, createJusticeConversation(criminal, stolenItemsOnCriminal))
							.perform();
					actor.thoughtBubbleImageTexture = ThoughtBubbles.JUSTICE;
					return true;
				}

				if (actor.sight > actor.straightLineDistanceTo(criminal.squareGameObjectIsOn)
						&& actor.canSeeGameObject(criminal)) {
					if (AIRoutineUtils.moveTowardsTargetToBeAdjacent(criminal)) {
						actor.thoughtBubbleImageTexture = ThoughtBubbles.JUSTICE;
						return true;
					}
				}
			} else if (stolenItemsOnGround.size() > 0) {
				for (GameObject stolenItemOnGround : stolenItemsOnGround) {
					if (actor.straightLineDistanceTo(stolenItemOnGround.squareGameObjectIsOn) == 1) {
						new ActionTake(this.actor, stolenItemOnGround).perform();
						actor.thoughtBubbleImageTexture = ThoughtBubbles.JUSTICE;
						return true;
					}

					if (actor.canSeeSquare(stolenItemOnGround.squareGameObjectIsOn)) {
						if (AIRoutineUtils.moveTowardsTargetToBeAdjacent(stolenItemOnGround)) {
							actor.thoughtBubbleImageTexture = ThoughtBubbles.JUSTICE;
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public Conversation createJusticeConversation(final Actor criminal,
			final ArrayList<GameObject> stolenItemsOnCriminal) {
		ConversationResponse accept = new ConversationResponse("Accept", null) {
			@Override
			public void select() {
				super.select();
				for (GameObject stolenItemOnCriminal : stolenItemsOnCriminal) {
					new ActionGive(criminal, actor, stolenItemOnCriminal).perform();
				}
			}
		};
		ConversationResponse refuse = new ConversationResponse("Refuse", null) {
			@Override
			public void select() {
				super.select();
				actor.addAttackerForNearbyFactionMembersIfVisible(criminal);
				actor.addAttackerForThisAndGroupMembers(criminal);
			}
		};

		Object[] demand = new Object[] {};
		if (stolenItemsOnCriminal.size() == 1) {
			demand = new Object[] { "Give me that ", stolenItemsOnCriminal.get(0), "!" };
		} else {
			ArrayList<Object> demandArrayList = new ArrayList<Object>();
			for (int i = 0; i < stolenItemsOnCriminal.size(); i++) {
				if (i == 0) {
					// first item
					demandArrayList.add("Give me that ");
					demandArrayList.add(stolenItemsOnCriminal.get(i));
				} else if (i == stolenItemsOnCriminal.size() - 1) {
					// last item
					demandArrayList.add(" and ");
					demandArrayList.add(stolenItemsOnCriminal.get(i));
					demandArrayList.add("!");
				} else {
					// middle items
					demandArrayList.add(", ");
					demandArrayList.add(stolenItemsOnCriminal.get(i));
				}
			}

			demand = demandArrayList.toArray();
		}

		ConversationPart conversationPartJustice = new ConversationPart(demand,
				new ConversationResponse[] { accept, refuse }, this.actor);

		return new Conversation(conversationPartJustice);

	}

	private Conversation getConversationLastResort() {

		ConversationResponse conversationReponseDone = new ConversationResponse("Done", null);
		ConversationPart conversationPartYouWontGetOut = new ConversationPart(
				new Object[] { "You won't get out of here alive [Mort rings his bell]" },
				new ConversationResponse[] { conversationReponseDone }, mort);

		return new Conversation(conversationPartYouWontGetOut);

	}
}
