package com.marklynch.ai.routines;

import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.ai.utils.AILine;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Investigation;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.structure.StructureRoom;
import com.marklynch.level.constructs.structure.StructureSection;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.ThoughtBubbles;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionDrop;
import com.marklynch.objects.actions.ActionGive;
import com.marklynch.objects.actions.ActionShoutForHelp;
import com.marklynch.objects.actions.ActionTake;
import com.marklynch.objects.actions.ActionTalk;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Hunter;
import com.marklynch.objects.units.Pig;
import com.marklynch.utils.MapUtil;

public class AIRoutine {

	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";
	final String ACTIVITY_DESCRIPTION_BEING_A_CHICKEN = "Being a chicken";
	final String ACTIVITY_DESCRIPTION_RUNNING_AWAY = "Running away";
	final String ACTIVITY_DESCRIPTION_SHOUTING_FOR_HELP = "Shouting for help";

	public Actor actor;
	public GameObject target;
	public int searchCooldown = 0;
	public GameObject searchCooldownActor = null;
	public int escapeCooldown = 0;
	public int shoutForHelpCooldown = 0;
	public GameObject escapeCooldownAttacker = null;

	public static enum AI_TYPE {
		FIGHTER, RUNNER, GUARD, HOSTILE, ANIMAL
	};

	public AI_TYPE aiType = AI_TYPE.FIGHTER;

	public boolean keepInBounds = false;
	public ArrayList<StructureSection> sectionBounds = new ArrayList<StructureSection>();
	public ArrayList<StructureRoom> roomBounds = new ArrayList<StructureRoom>();
	public ArrayList<Square> squareBounds = new ArrayList<Square>();

	public AIRoutine(Actor actor) {
		this.actor = actor;
	}

	public void update() {
	}

	public void createSearchLocationsBasedOnVisibleAttackers() {

		// Check for enemies last seen locations to search
		if (this.actor.hasAttackers()) {
			for (GameObject attacker : this.actor.getAttackers()) {
				if (this.actor.canSeeGameObject(attacker)) {
					this.actor.addInvestigation(attacker, attacker.squareGameObjectIsOn,
							Investigation.INVESTIGATION_PRIORITY_ATTACKED);
				}
			}
		}

	}

	public void createSearchLocationsBasedOnVisibleCriminals() {

		// Check for enemies last seen locations to search
		for (Actor criminal : this.actor.crimesWitnessed.keySet()) {

			for (Crime crime : actor.crimesWitnessed.get(criminal)) {
				if (!crime.resolved) {
					if (this.actor.canSeeGameObject(criminal)) {
						this.actor.addInvestigation(criminal, criminal.squareGameObjectIsOn,
								Investigation.INVESTIGATION_PRIORITY_CRIME_SEEN);
					}
				}
			}
		}

	}

	// public void witnessCrimes() {
	//
	// }
	//
	// public void updateListOfCrimesWitnessed() {
	// int searchBoxX1 = actor.squareGameObjectIsOn.xInGrid - actor.sight;
	// if (searchBoxX1 < 0)
	// searchBoxX1 = 0;
	//
	// int searchBoxX2 = actor.squareGameObjectIsOn.xInGrid + actor.sight;
	// if (searchBoxX2 > Game.level.width - 1)
	// searchBoxX2 = Game.level.width - 1;
	//
	// int searchBoxY1 = actor.squareGameObjectIsOn.yInGrid - actor.sight;
	// if (searchBoxY1 < 0)
	// searchBoxY1 = 0;
	//
	// int searchBoxY2 = actor.squareGameObjectIsOn.yInGrid + actor.sight;
	// if (searchBoxY2 > Game.level.height - 1)
	// searchBoxY2 = Game.level.height - 1;
	//
	// for (int i = searchBoxX1; i <= searchBoxX2; i++) {
	// for (int j = searchBoxY1; j <= searchBoxY2; j++) {
	// Actor actor = (Actor)
	// Game.level.squares[i][j].inventory.getGameObjectOfClass(Actor.class);
	// if (actor != null) {
	// for (Action action : actor.actions) {
	// if (action.legal == false) {
	// if (this.actor.straightLineDistanceTo(actor.squareGameObjectIsOn) <=
	// this.actor.sight
	// && this.actor.visibleFrom(actor.squareGameObjectIsOn)) {
	//
	// }
	// }
	// }
	// }
	// }
	// }
	// }

	public void createSearchLocationsBasedOnSounds(Class... classes) {

		ArrayList<Class> classesArrayList = new ArrayList<Class>(Arrays.asList(classes));

		// Check for sounds to investigate
		for (Sound sound : this.actor.squareGameObjectIsOn.sounds) {
			if (!this.actor.investigationsMap.containsValue(sound.sourceSquare)
					&& !this.actor.canSeeGameObject(sound.sourceActor)) {

				if (sound.actionType == ActionShoutForHelp.class) {
					this.actor.addInvestigation(sound.sourceObject, sound.sourceSquare,
							Investigation.INVESTIGATION_PRIORITY_CRIME_HEARD);
					this.actor.attackers.add(sound.sourceObject);
				} else if (!sound.legal) {
					this.actor.addInvestigation(sound.sourceActor, sound.sourceSquare,
							Investigation.INVESTIGATION_PRIORITY_CRIME_HEARD);
				} else if (!classesArrayList.contains(sound.sourceObject.getClass())) {
					this.actor.addInvestigation(sound.sourceActor, sound.sourceSquare,
							Investigation.INVESTIGATION_PRIORITY_SOUND_HEARD);
				}

			}
		}
	}

	public Sound getSoundFromSourceType(Class clazz) {

		// Check for sounds to investigate
		for (Sound sound : this.actor.squareGameObjectIsOn.sounds) {
			if (clazz.isInstance(sound.sourceObject)) {
				return sound;
			}
		}
		return null;
	}

	public boolean runFightRoutine() {
		boolean attacked = false;
		boolean moved = false;

		// 1. Fighting
		if (this.actor.hasAttackers()) {

			// sort by best attack option (walk distance, dmg you can do)
			this.actor.getAttackers().sort(AIRoutineUtils.sortAttackers);

			// Go through attackers list
			for (GameObject victimToAttackAttemptPreMove : this.actor.getAttackers()) {

				// If we can see the attacker go for them
				if (this.actor.canSeeGameObject(victimToAttackAttemptPreMove)) {
					target = victimToAttackAttemptPreMove;

					// If it's a hiding place we're planning on attacking,
					// we're searching
					if (target instanceof HidingPlace) {
						this.actor.activityDescription = ACTIVITY_DESCRIPTION_SEARCHING;
					} else {
						this.actor.activityDescription = ACTIVITY_DESCRIPTION_FIGHTING;
					}

					// Try to attack the preference 1 target
					if (target != null) {
						attacked = AIRoutineUtils.attackTarget(target);
						// If you can't attack preference 1 target, move towards
						// them
						if (!attacked) {
							moved = AIRoutineUtils.moveTowardsTargetToAttack(target);
							if (moved)
								break;
						}
					}

					actor.aiLine = new AILine(AILine.AILineType.AI_LINE_TYPE_ATTACK, actor,
							target.squareGameObjectIsOn);

				}

				if (moved || attacked)
					break;
			}
		}

		// If not targeting a hiding place, hiding places from list
		if (!(target instanceof HidingPlace)) {
			actor.removeHidingPlacesFromAttackers();
		}

		// Return whether we did anything or not
		if (moved || attacked)
			return true;
		else
			return false;
	}

	public boolean runGetHelpRoutine() {
		boolean moved = false;

		actor.removeHidingPlacesFromAttackers();
		if (this.actor.hasAttackers()) {

			// sort by best attack option (walk distance, dmg you can do)
			this.actor.getAttackers().sort(AIRoutineUtils.sortAttackers);

			// Go through attackers list
			for (GameObject attacker : this.actor.getAttackers()) {

				// If we can see the attacker go for them
				if (this.actor.canSeeGameObject(attacker)) {
					new ActionShoutForHelp(actor, attacker).perform();
					this.actor.activityDescription = ACTIVITY_DESCRIPTION_SHOUTING_FOR_HELP;

					Actor actorNearby = (Actor) AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(Hunter.class, 20,
							false, true, false, false, false, false);

					// if (this.actor.canSeeGameObject(actorNearby)) {
					// } else {
					if (actorNearby != null) {
						AIRoutineUtils.moveTowardsSquareToBeAdjacent(actorNearby.squareGameObjectIsOn);
						moved = true;
						this.escapeCooldownAttacker = attacker;

						actor.aiLine = new AILine(AILine.AILineType.AI_LINE_TYPE_ESCAPE, actor,
								actorNearby.squareGameObjectIsOn);
					}
					// }

				}

				if (moved)
					break;

			}
		}

		// Return whether we did anything or not
		if (moved)

		{
			// shoutForHelpCooldown = 10;
			shoutForHelpCooldown = 10;
			return true;
		} else {
			return false;
		}
	}

	public boolean runEscapeRoutine() {
		boolean canSeeAttacker = false;

		actor.removeHidingPlacesFromAttackers();

		// 1. Fighting
		if (this.actor.hasAttackers()) {

			// sort by best attack option (walk distance, dmg you can do)
			this.actor.getAttackers().sort(AIRoutineUtils.sortAttackers);

			// Go through attackers list
			for (GameObject attackerToRunFrom : this.actor.getAttackers()) {

				// If we can see the attacker go for them
				if (this.actor.canSeeGameObject(attackerToRunFrom)) {
					canSeeAttacker = true;

					// Try to attack the preference 1 target
					if (attackerToRunFrom != null) {
						AIRoutineUtils.escapeFromAttacker(attackerToRunFrom);
					}

					actor.aiLine = new AILine(AILine.AILineType.AI_LINE_TYPE_ESCAPE, actor,
							attackerToRunFrom.squareGameObjectIsOn);
				}

				if (canSeeAttacker)
					break;
			}
		}

		// Return whether we did anything or not
		if (canSeeAttacker) {
			if (actor instanceof Pig) {
				actor.activityDescription = ACTIVITY_DESCRIPTION_BEING_A_CHICKEN;
			} else {
				actor.activityDescription = ACTIVITY_DESCRIPTION_RUNNING_AWAY;
			}
			escapeCooldown = 10;

			return true;
		} else {
			return false;
		}
	}

	public boolean runEscapeCooldown(boolean shout) {

		// DOORWAYS are my biggest issue here.
		this.actor.activityDescription = ACTIVITY_DESCRIPTION_BEING_A_CHICKEN;
		if (shout) {
			new ActionShoutForHelp(actor, escapeCooldownAttacker).perform();
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_SHOUTING_FOR_HELP;
		}

		// Move Away From Last Square;
		boolean moved = false;
		if (actor.squareGameObjectIsOn.xInGrid > actor.lastSquare.xInGrid) {
			Square squareToMoveTo = actor.squareGameObjectIsOn.getSquareToRightOf();
			if (squareInBounds(squareToMoveTo))
				moved = AIRoutineUtils.moveTowardsTargetSquare(squareToMoveTo);
		} else if (actor.squareGameObjectIsOn.xInGrid < actor.lastSquare.xInGrid) {
			Square squareToMoveTo = actor.squareGameObjectIsOn.getSquareToLeftOf();
			if (squareInBounds(squareToMoveTo))
				moved = AIRoutineUtils.moveTowardsTargetSquare(squareToMoveTo);
		} else {
			if (actor.squareGameObjectIsOn.yInGrid > actor.lastSquare.yInGrid) {
				Square squareToMoveTo = actor.squareGameObjectIsOn.getSquareBelow();
				if (squareInBounds(squareToMoveTo))
					moved = AIRoutineUtils.moveTowardsTargetSquare(squareToMoveTo);
			} else if (actor.squareGameObjectIsOn.yInGrid < actor.lastSquare.yInGrid) {
				Square squareToMoveTo = actor.squareGameObjectIsOn.getSquareAbove();
				if (squareInBounds(squareToMoveTo))
					moved = AIRoutineUtils.moveTowardsTargetSquare(squareToMoveTo);
			} else {

			}
		}

		return false;
	}

	public boolean runSearchRoutine() {
		// Searching
		if (this.actor.investigationsMap.size() == 0)
			return false;

		// Remove dead objects
		// and remove gameObjects you can see from investigation list
		// and remove out of bounds squares from investigation list
		ArrayList<GameObject> gameObjectsToStopSearchingFor = new ArrayList<GameObject>();
		for (GameObject actorToSearchFor : this.actor.investigationsMap.keySet()) {
			if (actorToSearchFor.remainingHealth <= 0) {
				gameObjectsToStopSearchingFor.add(actorToSearchFor);
			} else if (this.actor.canSeeGameObject(actorToSearchFor)) {
				gameObjectsToStopSearchingFor.add(actorToSearchFor);
			} else if (!squareInBounds(actorToSearchFor.squareGameObjectIsOn)) {
				gameObjectsToStopSearchingFor.add(actorToSearchFor);
			}
		}
		for (GameObject gameObjectToRemove : gameObjectsToStopSearchingFor) {
			this.actor.investigationsMap.remove(gameObjectToRemove);
		}

		// Sort list by priority
		MapUtil.sortByValue(this.actor.investigationsMap);

		// this.actor.locationsToSearch.sort(AIRoutineUtils.sortLocationsToSearch);
		ArrayList<GameObject> toRemove = new ArrayList<GameObject>();
		boolean moved = false;

		Square searchSquare = null;

		for (GameObject actorToSearchFor : this.actor.investigationsMap.keySet()) {

			// If you're within 2 squares and can see the target actor, remove
			// from search list
			// if
			// (this.actor.straightLineDistanceTo(actorToSearchFor.squareGameObjectIsOn)
			// <= 2
			// && this.actor.canSeeGameObject(actorToSearchFor)) {
			// searchCooldown = 0;
			// toRemove.add(actorToSearchFor);
			// continue;
			// }

			searchSquare = this.actor.investigationsMap.get(actorToSearchFor).square;
			searchCooldownActor = actorToSearchFor;
			searchCooldown = 10;

			// moce towards search square
			if (this.actor.squareGameObjectIsOn.straightLineDistanceTo(searchSquare) > 1
					&& this.actor.getPathTo(searchSquare) != null) {
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_SEARCHING;
				this.actor.thoughtBubbleImageTexture = ThoughtBubbles.QUESTION_MARK;
				moved = AIRoutineUtils.moveTowardsTargetSquare(searchSquare);

				break;

			}

			if (moved)
				break;

			if (this.actor.squareGameObjectIsOn.straightLineDistanceTo(searchSquare) <= 1) {
				toRemove.add(actorToSearchFor);
				break;
			}

			// distance 1 to search square
			for (Square searchSquareAtDistanceOne : searchSquare.getAllSquaresAtDistance(1)) {
				if (this.actor.squareGameObjectIsOn.straightLineDistanceTo(searchSquare) > 1
						&& this.actor.getPathTo(searchSquareAtDistanceOne) != null) {
					this.actor.activityDescription = ACTIVITY_DESCRIPTION_SEARCHING;
					this.actor.thoughtBubbleImageTexture = ThoughtBubbles.QUESTION_MARK;
					moved = AIRoutineUtils.moveTowardsTargetSquare(searchSquareAtDistanceOne);

					break;

				}
			}

			if (moved)
				break;

			if (this.actor.squareGameObjectIsOn.straightLineDistanceTo(searchSquare) <= 2) {
				toRemove.add(actorToSearchFor);
				break;
			}

			// distance 2
			for (Square searchSquareAtDistanceTwo : searchSquare.getAllSquaresAtDistance(2)) {
				if (this.actor.getPathTo(searchSquareAtDistanceTwo) != null) {
					this.actor.activityDescription = ACTIVITY_DESCRIPTION_SEARCHING;
					this.actor.thoughtBubbleImageTexture = ThoughtBubbles.QUESTION_MARK;
					moved = AIRoutineUtils.moveTowardsTargetSquare(searchSquareAtDistanceTwo);
					break;

				}
			}

			if (moved)
				break;

			toRemove.add(actorToSearchFor);
		}

		for (GameObject actorsToRemoveFromList : toRemove) {
			this.actor.investigationsMap.remove(actorsToRemoveFromList);
		}

		if (moved) {

			for (GameObject attacker : this.actor.getAttackers()) {
				if (this.actor.canSeeGameObject(attacker)) {
					// Change status to fighting if u can see an enemy from
					// new location
					this.actor.thoughtBubbleImageTexture = null;
					if (target instanceof HidingPlace) {
						this.actor.activityDescription = ACTIVITY_DESCRIPTION_SEARCHING;
					} else
						this.actor.activityDescription = ACTIVITY_DESCRIPTION_FIGHTING;
					break;
				}
			}

			actor.aiLine = new AILine(AILine.AILineType.AI_LINE_TYPE_SEARCH, actor, searchSquare);
			return true;
		}
		return false;

	}

	public boolean runSearchCooldown() {

		// DOORWAYS are my biggest issue here.
		this.actor.activityDescription = ACTIVITY_DESCRIPTION_SEARCHING;
		this.actor.thoughtBubbleImageTexture = ThoughtBubbles.QUESTION_MARK;

		// if (room != null) {
		// AIRoutineUtils.moveTowardsTargetSquare(AIRoutineUtils.getRandomSquareInRoom(room));
		// return true;
		// } else {
		if (this.searchCooldownActor != null) {
			if (this.actor.canSeeGameObject(searchCooldownActor)) {
				searchCooldown = 0;
				return false;
			}
		}

		// Move Away From Last Square;
		boolean moved = false;
		if (actor.squareGameObjectIsOn.xInGrid > actor.lastSquare.xInGrid) {
			Square squareToMoveTo = actor.squareGameObjectIsOn.getSquareToRightOf();
			if (squareInBounds(squareToMoveTo))
				moved = AIRoutineUtils.moveTowardsTargetSquare(squareToMoveTo);
		} else if (actor.squareGameObjectIsOn.xInGrid < actor.lastSquare.xInGrid) {
			Square squareToMoveTo = actor.squareGameObjectIsOn.getSquareToLeftOf();
			if (squareInBounds(squareToMoveTo))
				moved = AIRoutineUtils.moveTowardsTargetSquare(squareToMoveTo);
		} else {
			if (actor.squareGameObjectIsOn.yInGrid > actor.lastSquare.yInGrid) {
				Square squareToMoveTo = actor.squareGameObjectIsOn.getSquareBelow();
				if (squareInBounds(squareToMoveTo))
					moved = AIRoutineUtils.moveTowardsTargetSquare(squareToMoveTo);
			} else if (actor.squareGameObjectIsOn.yInGrid < actor.lastSquare.yInGrid) {
				Square squareToMoveTo = actor.squareGameObjectIsOn.getSquareAbove();
				if (squareInBounds(squareToMoveTo))
					moved = AIRoutineUtils.moveTowardsTargetSquare(squareToMoveTo);
			} else {

			}
		}

		if (!moved) {
			Square randomSquareToMoveTo = AIRoutineUtils.getRandomAdjacentSquare(actor.squareGameObjectIsOn);
			if (squareInBounds(randomSquareToMoveTo))
				moved = AIRoutineUtils.moveTowardsTargetSquare(randomSquareToMoveTo);
		}
		// }
		return false;
	}

	protected boolean runCrimeReactionRoutine() {
		for (final Actor criminal : actor.crimesWitnessed.keySet()) {
			int accumulatedSeverity = 0;
			// ArrayList<Crime> unresolvedIllegalMining = new
			// ArrayList<Crime>();
			final ArrayList<Crime> unresolvedCrimes = new ArrayList<Crime>();
			final ArrayList<GameObject> stolenItemsOnCriminal = new ArrayList<GameObject>();
			final ArrayList<GameObject> stolenItemsEquippedByCriminal = new ArrayList<GameObject>();
			final ArrayList<GameObject> stolenItemsOnGroundToPickUp = new ArrayList<GameObject>();
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
					// if (crime.action instanceof ActionMine) {
					// unresolvedIllegalMining.add(crime);
					// }
					for (GameObject stolenItem : crime.stolenItems) {
						if (criminal.inventory.contains(stolenItem)) {
							stolenItemsOnCriminal.add(stolenItem);
						} else if (criminal.equipped == stolenItem) {
							stolenItemsEquippedByCriminal.add(stolenItem);
						} else if (stolenItem.squareGameObjectIsOn != null && stolenItem.fitsInInventory) {
							stolenItemsOnGroundToPickUp.add(stolenItem);
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
				// } else if (unresolvedIllegalMining.size() > 0) {
				// actor.miniDialogue = "MY ORES!";
				// new ActionThrow(actor, criminal,
				// Templates.ROCK.makeCopy(null, null)).perform();
				// for (GameObject stolenItem : stolenItemsOnCriminal) {
				// if (criminal.inventory.contains(stolenItem)) {
				// new ActionDrop(criminal, criminal.squareGameObjectIsOn,
				// stolenItem).perform();
				// this.actor.thoughtBubbleImageTexture =
				// stolenItem.imageTexture;
				// }
				// }
				// for (Crime unresolvedCrime : unresolvedCrimes) {
				// unresolvedCrime.resolved = true;
				// }
				// // actor.thoughtBubbleImageTexture = ThoughtBubbles.JUSTICE;
				// return true;
			} else if (stolenItemsOnCriminal.size() > 0) {
				if (actor.straightLineDistanceTo(criminal.squareGameObjectIsOn) == 1) {
					new ActionTalk(this.actor, criminal, createJusticeTakeConversation(criminal, stolenItemsOnCriminal))
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
			} else if (stolenItemsEquippedByCriminal.size() > 0) {
				if (actor.straightLineDistanceTo(criminal.squareGameObjectIsOn) == 1) {
					new ActionTalk(this.actor, criminal,
							createJusticeDropConversation(criminal, stolenItemsEquippedByCriminal)).perform();
					Crime crime = new Crime(null, criminal, this.actor, 1);
					criminal.crimesPerformedThisTurn.add(crime);
					criminal.crimesPerformedInLifetime.add(crime);
					Action.notifyWitnessesOfCrime(crime);
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
			} else if (stolenItemsOnGroundToPickUp.size() > 0) {
				for (GameObject stolenItemOnGround : stolenItemsOnGroundToPickUp) {

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

	public Conversation createJusticeTakeConversation(final Actor criminal,
			final ArrayList<GameObject> stolenItemsOnCriminal) {
		ConversationResponse accept = new ConversationResponse("Comply [Give items]", null) {
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

	public Conversation createJusticeDropConversation(final Actor criminal,
			final ArrayList<GameObject> stolenItemsEquippedByCriminal) {
		ConversationResponse accept = new ConversationResponse("Comply", null) {
			@Override
			public void select() {
				super.select();
				for (GameObject stolenItemOnCriminal : stolenItemsEquippedByCriminal) {
					new ActionDrop(criminal, criminal.squareGameObjectIsOn, stolenItemOnCriminal).perform();
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
		demand = new Object[] { "Drop that ", stolenItemsEquippedByCriminal.get(0), "!" };

		ConversationPart conversationPartJustice = new ConversationPart(demand,
				new ConversationResponse[] { accept, refuse }, this.actor);

		return new Conversation(conversationPartJustice);

	}

	public Actor actorToKeepTrackOf = null;
	public Square lastLocationSeenActorToKeepTrackOf = null;

	// public
	public boolean keepTrackOf(Actor target) {

		// If the actor is in the attackers list they will be handled by
		// fightRoutine/searchRoutine
		if (actor.attackers.contains(target))
			return false;
		// Can mort see the Player in his territory? If so record it. If not,
		// follow.

		if (target != actorToKeepTrackOf) {
			actorToKeepTrackOf = target;
			lastLocationSeenActorToKeepTrackOf = null;
		}

		if (!actor.canSeeGameObject(target)) {

			if (lastLocationSeenActorToKeepTrackOf != null) {
				AIRoutineUtils.moveTowardsTargetSquare(lastLocationSeenActorToKeepTrackOf);

				if (actor.squareGameObjectIsOn == lastLocationSeenActorToKeepTrackOf) {
					this.actor.addInvestigation(target, lastLocationSeenActorToKeepTrackOf,
							Investigation.INVESTIGATION_PRIORITY_KEEP_TRACK);
					lastLocationSeenActorToKeepTrackOf = null;
				}

				if (actor.canSeeGameObject(target)) {
					actor.investigationsMap.remove(target);
					lastLocationSeenActorToKeepTrackOf = target.squareGameObjectIsOn;
				}

				return true;
			}
		} else {
			actor.investigationsMap.remove(target);
			lastLocationSeenActorToKeepTrackOf = target.squareGameObjectIsOn;
		}
		return false;
	}

	public boolean squareInBounds(Square square) {
		// Return true if there are no bounds
		if (keepInBounds == false)
			return true;

		for (StructureSection section : sectionBounds) {
			if (square.structureSectionSquareIsIn == section) {
				return true;
			}
		}

		for (StructureRoom room : roomBounds) {
			if (square.structureRoomSquareIsIn == room) {
				return true;
			}
		}

		for (Square legitSquare : squareBounds) {
			if (square == legitSquare) {
				return true;
			}
		}
		return false;

	}

}
