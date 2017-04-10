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
import com.marklynch.objects.actions.ActionDrop;
import com.marklynch.objects.actions.ActionGive;
import com.marklynch.objects.actions.ActionLock;
import com.marklynch.objects.actions.ActionMine;
import com.marklynch.objects.actions.ActionPickUp;
import com.marklynch.objects.actions.ActionRing;
import com.marklynch.objects.actions.ActionTalk;
import com.marklynch.objects.actions.ActionThrow;
import com.marklynch.objects.tools.Bell;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;

public class AIRoutineForMort extends AIRoutine {

	Mort mort;
	GameObject target;
	boolean rangBellAsLastResort;
	boolean lockedInRoom = false;

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
	final String ACTIVITY_DESCRIPTION_RINGING_DINNER_BELL = "Ringing Dinner Bell";

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	public AIRoutineForMort(Mort mort) {

		super(mort);
		this.mort = mort;
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
		ArrayList<Actor> toRemove = new ArrayList<Actor>();
		for (Actor actor : mort.locationsToSearch.keySet()) {
			Square squareToSearch = mort.locationsToSearch.get(actor);
			if (squareToSearch.structureRoomSquareIsIn != mort.mortsMine
					&& squareToSearch.structureRoomSquareIsIn != mort.mortsRoom
					&& squareToSearch.structureRoomSquareIsIn != mort.mortsVault
					&& squareToSearch != mort.mortsRoomDoorway) {
				toRemove.add(actor);
			}
		}
		for (Actor actor : toRemove) {
			System.out.println("removing actor out of jurisdiction " + actor.squareGameObjectIsOn.xInGrid + ", "
					+ actor.squareGameObjectIsOn.xInGrid);
			mort.locationsToSearch.remove(actor);
		}

		// updateListOfCrimesWitnessed();

		// Player attacker and under half health - ring bell
		if (!rangBellAsLastResort && mort.remainingHealth < mort.totalHealth / 2) {
			Bell bell = (Bell) mort.inventory.getGameObjectOfClass(Bell.class);
			if (bell != null && mort.getAttackers().contains(Game.level.player)) {
				new ActionRing(mort, bell).perform();
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_RINGING_DINNER_BELL;
				this.actor.miniDialogue = "You won't get out of here alive";
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

		if (lockedInRoom) {
			return;
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
						new ActionLock(mort, mort.questCaveOfTheBlind.mortsBedroomDoor).perform();
						lockedInRoom = true;
					} else {
						AIRoutineUtils.moveTowardsTargetSquare(safeSideOfDoorSquare);
					}
					return;
				}

			}
		}

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
		if (actor.canSee(Game.level.player.squareGameObjectIsOn) && targetInTerritory(Game.level.player)) {
			keepTrackOf(Game.level.player);
		} else if (lastLocationSeenActorToKeepTrackOf != null
				&& squareInTerritory(lastLocationSeenActorToKeepTrackOf)) {
			keepTrackOf(Game.level.player);
		} else if (lastLocationSeenActorToKeepTrackOf != null
				&& !squareInTerritory(lastLocationSeenActorToKeepTrackOf)) {
			lastLocationSeenActorToKeepTrackOf = null;
		}

		// If not leader defer to pack
		if (this.actor.group != null && this.actor != this.actor.group.getLeader())

		{
			if (this.actor.group.update(this.actor)) {
				return;
			}
		}

		// if group leader wait for group
		if (this.actor.group != null && this.actor == this.actor.group.getLeader()) {
			if (this.actor.group.leaderNeedsToWait()) {
				this.actor.activityDescription = "Waiting for " + this.actor.group.name;
				return;
			}
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

			if (accumulatedSeverity >= 5) {
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
					System.out.println("stolenItem = " + stolenItem);
					if (criminal.inventory.contains(stolenItem)) {
						new ActionDrop(criminal, criminal.squareGameObjectIsOn, stolenItem).perform();
					}
				}
				for (Crime unresolvedCrime : unresolvedCrimes) {
					unresolvedCrime.resolved = true;
				}
				return true;
			} else if (stolenItemsOnCriminal.size() > 0) {
				if (actor.straightLineDistanceTo(criminal.squareGameObjectIsOn) == 1) {
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
					ConversationPart conversationPartSaveTheWolf = new ConversationPart(
							new Object[] { "Give me that!" }, new ConversationResponse[] { accept, refuse },
							this.actor);
					new ActionTalk(this.actor, criminal, new Conversation(conversationPartSaveTheWolf)).perform();
					return true;
				}

				if (actor.sight > actor.straightLineDistanceTo(criminal.squareGameObjectIsOn)
						&& actor.visibleFrom(criminal.squareGameObjectIsOn)) {
					System.out.println("unresolvedCrimes b");
					if (AIRoutineUtils.moveTowardsTargetToBeAdjacent(criminal)) {
						return true;
					}
				}
			} else if (stolenItemsOnGround.size() > 0) {
				for (GameObject stolenItemOnGround : stolenItemsOnGround) {
					if (actor.straightLineDistanceTo(stolenItemOnGround.squareGameObjectIsOn) == 1) {
						new ActionPickUp(this.actor, stolenItemOnGround).perform();
						return true;
					}

					if (actor.canSee(stolenItemOnGround.squareGameObjectIsOn)) {
						if (AIRoutineUtils.moveTowardsTargetToBeAdjacent(stolenItemOnGround)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
