package com.marklynch.ai.routines;

import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.Game;
import com.marklynch.ai.utils.AILine;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Investigation;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.bounds.Area;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.BrokenGlass;
import com.marklynch.objects.Carcass;
import com.marklynch.objects.Door;
import com.marklynch.objects.Food;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.Junk;
import com.marklynch.objects.SmallHidingPlace;
import com.marklynch.objects.Templates;
import com.marklynch.objects.ThoughtBubbles;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionClose;
import com.marklynch.objects.actions.ActionDropSpecificItem;
import com.marklynch.objects.actions.ActionGiveSpecificItem;
import com.marklynch.objects.actions.ActionHideInside;
import com.marklynch.objects.actions.ActionLock;
import com.marklynch.objects.actions.ActionMine;
import com.marklynch.objects.actions.ActionShoutForHelp;
import com.marklynch.objects.actions.ActionTakeSpecificItem;
import com.marklynch.objects.actions.ActionTalk;
import com.marklynch.objects.actions.ActionThrowSpecificItem;
import com.marklynch.objects.tools.Knife;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Farmer;
import com.marklynch.objects.units.HerbivoreWildAnimal;
import com.marklynch.objects.units.Hunter;
import com.marklynch.objects.units.NonHuman;
import com.marklynch.objects.units.Pig;
import com.marklynch.objects.units.Trader;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.utils.MapUtil;

public class AIRoutine {

	String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";
	final String ACTIVITY_DESCRIPTION_BEING_A_CHICKEN = "Being a chicken";
	final String ACTIVITY_DESCRIPTION_RUNNING_AWAY = "Running away";
	final String ACTIVITY_DESCRIPTION_SHOUTING_FOR_HELP = "Shouting for help";
	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	final String ACTIVITY_DESCRIPTION_SKINNING = "Skinning";
	final String ACTIVITY_DESCRIPTION_SELLING_LOOT = "Selling loot";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";

	public Actor actor;
	public GameObject target;
	public int searchCooldown = 0;
	public GameObject searchCooldownActor = null;
	public int escapeCooldown = 0;
	public GameObject escapeCooldownAttacker = null;

	enum STATE {
		PICK_WILD_ANIMAL, GO_TO_WILD_ANIMAL_AND_ATTACK, GO_TO_WILD_ANIMAL_AND_LOOT, GO_TO_BED_AND_GO_TO_SLEEP
	};

	public STATE state;

	public ArrayList<GameObject> visibleHazards = new ArrayList<GameObject>();

	public static enum AI_TYPE {
		FIGHTER, RUNNER, GUARD, HOSTILE, ANIMAL
	};

	public AI_TYPE aiType = AI_TYPE.FIGHTER;

	public boolean keepInBounds = false;
	public ArrayList<Area> areaBounds = new ArrayList<Area>();
	public ArrayList<StructureSection> sectionBounds = new ArrayList<StructureSection>();
	public ArrayList<StructureRoom> roomBounds = new ArrayList<StructureRoom>();
	public ArrayList<Square> squareBounds = new ArrayList<Square>();

	public AIRoutine(Actor actor) {
		this.actor = actor;
	}

	public void update() {
	}

	public void createSearchLocationsBasedOnVisibleAttackers() {

		if (actor.sleeping)
			return;

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

		if (actor.sleeping)
			return;

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
					&& !this.actor.canSeeGameObject(sound.sourcePerformer)) {

				if (actor.sleeping && actor.straightLineDistanceTo(sound.sourceSquare) > sound.loudness - 2)
					continue;
				// (sound.loudness < 5

				if (sound.actionType == ActionShoutForHelp.class) {
					this.actor.addInvestigation(sound.sourceObject, sound.sourceSquare,
							Investigation.INVESTIGATION_PRIORITY_CRIME_HEARD);
					if (sound.sourceObject != null && sound.sourceObject.remainingHealth > 0) {
						this.actor.addAttackerForThisAndGroupMembers(sound.sourceObject);
					}
					actor.sleeping = false;
				} else if (!sound.legal) {

					this.actor.addInvestigation(sound.sourcePerformer, sound.sourceSquare,
							Investigation.INVESTIGATION_PRIORITY_CRIME_HEARD);
					actor.sleeping = false;
				} else if (sound.sourceObject != null && !classesArrayList.contains(sound.sourceObject.getClass())) {
					this.actor.addInvestigation(sound.sourcePerformer, sound.sourceSquare,
							Investigation.INVESTIGATION_PRIORITY_SOUND_HEARD);
					actor.sleeping = false;
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

	public static boolean escapeFromAttackerToSmallHidingPlace(GameObject attacker) {

		// Go to burrow and hide if can
		SmallHidingPlace smallHidingPlace = (SmallHidingPlace) AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(20f,
				false, false, true, false, false, false, 0, SmallHidingPlace.class);
		if (smallHidingPlace != null) {

			if (Game.level.activeActor.straightLineDistanceTo(smallHidingPlace.squareGameObjectIsOn) < 2) {
				new ActionHideInside(Game.level.activeActor, smallHidingPlace).perform();
			} else {

				AIRoutineUtils.moveTowardsTargetToBeOn(smallHidingPlace);
			}
			return true;
		}
		return false;
	}

	public boolean runSleepRoutine() {
		if (state != STATE.GO_TO_BED_AND_GO_TO_SLEEP) {
			actor.sleeping = false;
			return false;

		}

		if (this.actor.group != null && this.actor != this.actor.group.getLeader()
				&& this.actor.group.getLeader().followersShouldFollow == true) {
			actor.sleeping = false;
			return false;
		}

		if (actor.sleeping) {
			this.actor.followersShouldFollow = false;
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_SLEEPING;
			return true;
		}

		return false;
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
			actor.removeHidingPlacesFromAttackersList();
		}

		// Return whether we did anything or not
		if (moved || attacked) {
			createSearchLocationsBasedOnVisibleAttackers();
			this.actor.followersShouldFollow = true;
			return true;
		} else
			return false;
	}

	public boolean runGetHelpRoutine() {
		boolean moved = false;

		actor.removeHidingPlacesFromAttackersList();
		if (this.actor.hasAttackers()) {

			// sort by best attack option (walk distance, dmg you can do)
			this.actor.getAttackers().sort(AIRoutineUtils.sortAttackers);

			// Go through attackers list
			for (GameObject attacker : this.actor.getAttackers()) {

				// If we can see the attacker go for them
				if (this.actor.canSeeGameObject(attacker)) {
					new ActionShoutForHelp(actor, attacker).perform();
					this.actor.activityDescription = ACTIVITY_DESCRIPTION_SHOUTING_FOR_HELP;

					Actor actorNearby = (Actor) AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(20, false, true,
							false, false, false, false, 0, Hunter.class, Farmer.class);

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
		if (moved) {
			// shoutForHelpCooldown = 10;
			createSearchLocationsBasedOnVisibleAttackers();
			escapeCooldown = 10;
			return true;
		} else {
			return false;
		}
	}

	public boolean runEscapeRoutine() {
		boolean canSeeAttacker = false;

		actor.removeHidingPlacesFromAttackersList();

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
						if (actor instanceof HerbivoreWildAnimal
								&& escapeFromAttackerToSmallHidingPlace(attackerToRunFrom)) {
							// Successfully ran towards burrow
						} else {
							AIRoutineUtils.escapeFromAttacker(attackerToRunFrom);
						}
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

			createSearchLocationsBasedOnVisibleAttackers();
			return true;
		} else {
			return false;
		}
	}

	public boolean runEscapeCooldown(boolean shout) {

		if (escapeCooldown <= 0)
			return false;

		escapeCooldown--;

		// DOORWAYS are my biggest issue here.
		if (Game.level.activeActor instanceof Pig)
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_BEING_A_CHICKEN;
		else if (shout) {
			new ActionShoutForHelp(actor, escapeCooldownAttacker).perform();
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_SHOUTING_FOR_HELP;
		} else {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_RUNNING_AWAY;
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

		createSearchLocationsBasedOnVisibleAttackers();
		return true;
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
			} else if (actorToSearchFor.squareGameObjectIsOn == null) {
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
			createSearchLocationsBasedOnVisibleAttackers();
			return true;
		}
		return false;

	}

	public boolean runSearchCooldown() {

		if (searchCooldown <= 0)
			return false;

		searchCooldown--;

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

		if (moved) {
			createSearchLocationsBasedOnVisibleAttackers();
			return true;
		} else {
			return false;
		}
	}

	public static String STOP_THAT = "Stop that!";
	public static String I_SAW_THAT = "I saw that!!";

	protected boolean runCrimeReactionRoutine() {
		for (final Actor criminal : actor.crimesWitnessed.keySet()) {
			int accumulatedSeverity = 0;
			final ArrayList<Crime> unresolvedIllegalMinings = new ArrayList<Crime>();
			final ArrayList<Crime> unresolvedThefts = new ArrayList<Crime>();
			final ArrayList<Crime> unresolvedCrimes = new ArrayList<Crime>();
			final ArrayList<GameObject> stolenItemsOnCriminal = new ArrayList<GameObject>();
			final ArrayList<GameObject> stolenItemsEquippedByCriminal = new ArrayList<GameObject>();
			final ArrayList<GameObject> stolenItemsOnGroundToPickUp = new ArrayList<GameObject>();
			boolean newCrime = false;
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
						unresolvedIllegalMinings.add(crime);
					}
					if (crime.stolenItems.length != 0)
						unresolvedThefts.add(crime);
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

			// "STOP THAT!"
			boolean saidStop = false;
			for (Crime crime : actor.crimesWitnessed.get(criminal)) {
				if (!crime.hasBeenToldToStop) {
					if (criminal == Game.level.player) {
						new ActionTalk(this.actor, criminal, createJusticeStopConversation(crime)).perform();
					} else {
						if (Game.level.shouldLog(this.actor)) {
							if (crime.severity == Crime.CRIME_SEVERITY_THEFT)
								actor.setMiniDialogue(I_SAW_THAT, criminal);
							else
								actor.setMiniDialogue(STOP_THAT, criminal);
						}

					}
					actor.thoughtBubbleImageTexture = ThoughtBubbles.JUSTICE;
					// actor.activityDescription = "Dispensing Justice";

					saidStop = true;
					break;
				}

			}
			if (saidStop) {
				for (Crime crime : criminal.crimesPerformedInLifetime) {
					crime.hasBeenToldToStop = true;
				}
			}

			if (accumulatedSeverity >= 10) {
				actor.addAttackerForNearbyFactionMembersIfVisible(criminal);
				actor.addAttackerForThisAndGroupMembers(criminal);
				for (Crime unresolvedCrime : unresolvedCrimes) {
					unresolvedCrime.resolved = true;
				}
				return runFightRoutine();

				// Illegal mining
			} else if (unresolvedIllegalMinings.size() > 0) {

				if (criminal == Game.level.player) {
					new ActionTalk(this.actor, criminal, createConversationMyOres()).perform();
				} else {
					actor.setMiniDialogue("MY ORES!", criminal);
				}
				new ActionThrowSpecificItem(actor, criminal, Templates.ROCK.makeCopy(null, null)).perform();
				for (GameObject stolenItem : stolenItemsOnCriminal) {
					if (criminal.inventory.contains(stolenItem)) {
						new ActionDropSpecificItem(criminal, criminal.squareGameObjectIsOn, stolenItem).perform();
						this.actor.thoughtBubbleImageTexture = stolenItem.imageTexture;
					}
				}
				for (Crime unresolvedCrime : unresolvedIllegalMinings) {
					unresolvedCrime.resolved = true;
				}
				// actor.thoughtBubbleImageTexture = ThoughtBubbles.JUSTICE;
				return true;
				// Stolen items in criminal inventory
			} else if (stolenItemsOnCriminal.size() > 0) {
				if (actor.straightLineDistanceTo(criminal.squareGameObjectIsOn) == 1) {
					if (criminal == Game.level.player) {
						new ActionTalk(this.actor, criminal,
								createJusticeReclaimConversation(criminal, stolenItemsOnCriminal)).perform();
					} else {
						actor.setMiniDialogue("Give me that!", criminal);
						for (GameObject stolenItemOnCriminal : stolenItemsOnCriminal) {
							new ActionGiveSpecificItem(criminal, actor, stolenItemOnCriminal, true).perform();
						}
					}
					actor.thoughtBubbleImageTexture = ThoughtBubbles.JUSTICE;
					actor.activityDescription = "Confiscating";
					return true;
				}

				if (actor.sight > actor.straightLineDistanceTo(criminal.squareGameObjectIsOn)
						&& actor.canSeeGameObject(criminal)) {
					if (AIRoutineUtils.moveTowardsSquareToBeAdjacent(criminal.squareGameObjectIsOn)) {
						actor.thoughtBubbleImageTexture = ThoughtBubbles.JUSTICE;
						actor.activityDescription = "Confiscating";
						createSearchLocationsBasedOnVisibleAttackers();
						return true;
					}
				}

				// Stolen items equipped by criminal but not in inventory
				// This is for large objects that dont fit in inventory
			} else if (stolenItemsEquippedByCriminal.size() > 0) {
				if (actor.straightLineDistanceTo(criminal.squareGameObjectIsOn) == 1) {
					new ActionTalk(this.actor, criminal,
							createJusticeDropConversation(criminal, stolenItemsEquippedByCriminal)).perform();
					actor.thoughtBubbleImageTexture = ThoughtBubbles.JUSTICE;
					return true;
				}

				if (actor.sight > actor.straightLineDistanceTo(criminal.squareGameObjectIsOn)
						&& actor.canSeeGameObject(criminal)) {
					if (AIRoutineUtils.moveTowardsSquareToBeAdjacent(criminal.squareGameObjectIsOn)) {
						actor.thoughtBubbleImageTexture = ThoughtBubbles.JUSTICE;
						createSearchLocationsBasedOnVisibleAttackers();
						return true;
					}
				}

				// Stolen items on ground
			} else if (stolenItemsOnGroundToPickUp.size() > 0) {
				for (GameObject stolenItemOnGround : stolenItemsOnGroundToPickUp) {
					if (actor.straightLineDistanceTo(stolenItemOnGround.squareGameObjectIsOn) == 1) {
						new ActionTakeSpecificItem(this.actor, stolenItemOnGround.squareGameObjectIsOn,
								stolenItemOnGround).perform();
						actor.thoughtBubbleImageTexture = ThoughtBubbles.JUSTICE;
						return true;
					}

					if (actor.canSeeSquare(stolenItemOnGround.squareGameObjectIsOn)) {
						if (AIRoutineUtils.moveTowardsSquareToBeAdjacent(stolenItemOnGround.squareGameObjectIsOn)) {
							actor.thoughtBubbleImageTexture = ThoughtBubbles.JUSTICE;
							createSearchLocationsBasedOnVisibleAttackers();
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public Conversation createJusticeStopConversation(Crime crime) {
		ConversationResponse done = new ConversationResponse(":/", null);
		ConversationPart conversationPartJustice = null;
		if (crime.severity == Crime.CRIME_SEVERITY_THEFT) {
			conversationPartJustice = new ConversationPart(new Object[] { I_SAW_THAT },
					new ConversationResponse[] { done }, this.actor);

		} else {
			conversationPartJustice = new ConversationPart(new Object[] { STOP_THAT },
					new ConversationResponse[] { done }, this.actor);

		}

		return new Conversation(conversationPartJustice);

	}

	public Conversation createJusticeReclaimConversation(final Actor criminal,
			final ArrayList<GameObject> stolenItemsOnCriminal) {
		ConversationResponse accept = new ConversationResponse("Comply [Give items]", null) {
			@Override
			public void select() {
				super.select();
				for (GameObject stolenItemOnCriminal : stolenItemsOnCriminal) {
					new ActionGiveSpecificItem(criminal, actor, stolenItemOnCriminal, true).perform();
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
					new ActionDropSpecificItem(criminal, criminal.squareGameObjectIsOn, stolenItemOnCriminal).perform();
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

	private Conversation createConversationMyOres() {

		ConversationResponse conversationReponseDone = new ConversationResponse("Done", null);
		ConversationPart conversationPartYouWontGetOut = new ConversationPart(new Object[] { "My Ores!" },
				new ConversationResponse[] { conversationReponseDone }, actor);

		return new Conversation(conversationPartYouWontGetOut);

	}

	public boolean deferToGroupLeader() {

		if (this.actor.group != null && this.actor != this.actor.group.getLeader()
				&& this.actor.group.getLeader().followersShouldFollow == true) {
			if (this.actor.group.update(this.actor)) {
				return true;
			}
		}
		return false;
	}

	public boolean deferToQuest() {
		if (this.actor.quest != null) {
			if (this.actor.quest.update(this.actor)) {
				return true;
			}
		}
		return false;
	}

	public boolean lootCarcass() {

		// 1. loot carcasses
		GameObject carcass = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(9f, false, false, true, true, true,
				true, 0, Carcass.class);
		if (carcass != null) {
			this.actor.thoughtBubbleImageTexture = carcass.imageTexture;
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_LOOTING;
			boolean lootedCarcass = AIRoutineUtils.lootTarget(carcass);
			if (!lootedCarcass) {
				AIRoutineUtils.moveTowardsSquareToBeAdjacent(carcass.squareGameObjectIsOn);
			} else {
			}
			return true;
		}

		return false;
	}

	public boolean skinCarcass() {

		if (!actor.inventory.contains(Knife.class))
			return false;

		// 1. loot carcasses
		GameObject carcass = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(9f, false, false, true, true, true,
				true, 0, Carcass.class);
		if (carcass != null) {
			this.actor.thoughtBubbleImageTexture = carcass.imageTexture;
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_SKINNING;
			boolean lootedCarcass = AIRoutineUtils.skinTarget(carcass);
			if (!lootedCarcass) {
				AIRoutineUtils.moveTowardsSquareToBeAdjacent(carcass.squareGameObjectIsOn);
			} else {
			}
			return true;
		}

		return false;
	}

	public boolean lootFromGround() {
		// Pick up loot on ground
		GameObject loot = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(9f, true, false, true, false, true, true,
				10, Junk.class, Food.class);
		if (loot != null) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_LOOTING;
			this.actor.thoughtBubbleImageTexture = loot.imageTexture;
			boolean pickedUpLoot = AIRoutineUtils.pickupTarget(loot);
			if (!pickedUpLoot) {
				AIRoutineUtils.moveTowardsSquareToBeAdjacent(loot.squareGameObjectIsOn);
			} else {

			}
			return true;
		}
		return false;
	}

	public void aiRoutineStart() {
		this.actor.followersShouldFollow = false;
		this.actor.aiLine = null;
		this.actor.miniDialogue = null;
		this.actor.activityDescription = null;
		this.actor.thoughtBubbleImageTexture = null;
		createSearchLocationsBasedOnSounds(Weapon.class, BrokenGlass.class);
		if (actor instanceof NonHuman) {

		} else {
			createSearchLocationsBasedOnVisibleCriminals();
		}
		createSearchLocationsBasedOnVisibleAttackers();
		if (actor.group != null)
			state = actor.group.getLeader().aiRoutine.state;
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

		for (Area area : areaBounds) {
			if (square.areaSquareIsIn == area) {
				return true;
			}
		}

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

	public boolean runDoorRoutine() {
		for (Door door : actor.doors) {

			if (door.locked == false && door.shouldBeLocked() && actor.hasKeyForDoor(door)
					&& actor.squareGameObjectIsOn != door.squareGameObjectIsOn && actor.canSeeGameObject(door)) {
				actor.thoughtBubbleImageTexture = door.imageTexture;
				Action action = new ActionLock(this.actor, door);
				if (actor.straightLineDistanceTo(door.squareGameObjectIsOn) > 1) {
					if (AIRoutineUtils.moveTowardsSquareToBeAdjacent(door.squareGameObjectIsOn)) {
						return true;
					}
				} else if (!action.enabled) {
					return false;
				} else {
					action.perform();
					return true;
				}
			} else if (door.isOpen() && door.shouldBeClosed() && actor.squareGameObjectIsOn != door.squareGameObjectIsOn
					&& actor.canSeeGameObject(door)) {
				actor.thoughtBubbleImageTexture = door.imageTexture;
				Action action = new ActionClose(this.actor, door);
				if (actor.straightLineDistanceTo(door.squareGameObjectIsOn) > 1) {
					if (AIRoutineUtils.moveTowardsSquareToBeAdjacent(door.squareGameObjectIsOn)) {
						return true;
					}
				} else if (!action.enabled) {
					return false;
				} else {
					action.perform();
					return true;
				}
			}
		}
		return false;

	}

	public boolean sellItems() {

		if (actor.inventory.itemsToSellCount <= 0)
			return false;

		target = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(100, false, true, false, false, false, false, 0,
				Trader.class);
		if (target == null) {
			return false;
		}
		this.actor.activityDescription = ACTIVITY_DESCRIPTION_SELLING_LOOT;

		boolean soldItems = actor.sellItemsMarkedToSell((Actor) target);
		if (!soldItems)
			return AIRoutineUtils.moveTowardsSquareToBeAdjacent(target.squareGameObjectIsOn);
		else
			return true;
	}

}
