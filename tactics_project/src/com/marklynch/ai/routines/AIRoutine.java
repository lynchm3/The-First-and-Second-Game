package com.marklynch.ai.routines;

import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Investigation;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.structure.StructureRoom;
import com.marklynch.level.quest.caveoftheblind.Mort;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.ThoughtBubbles;
import com.marklynch.objects.actions.ActionMine;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.MapUtil;

public class AIRoutine {

	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";

	public Actor actor;
	public GameObject target;
	public int searchCooldown = 0;
	public GameObject searchCooldownActor = null;

	public static enum AI_TYPE {
		FIGHTER, RUNNER, GUARD, HOSTILE, ANIMAL
	};

	public AI_TYPE aiType = AI_TYPE.FIGHTER;

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

				if (!sound.legal) {
					System.out.println("adding sound = " + sound);
					System.out.println("sound.sourceActo = r" + sound.sourceActor);
					System.out.println("sound.sourceObject = " + sound.sourceObject);
					System.out.println("sound.sourceSquare = " + sound.sourceSquare);
					System.out.println("sound.legal = " + sound.legal);
					System.out.println("sound.action = " + sound.action);
					// System.out.println("sound.action.getName() = " +
					// sound.action.getName());
					this.actor.addInvestigation(sound.sourceActor, sound.sourceSquare,
							Investigation.INVESTIGATION_PRIORITY_CRIME_HEARD);
				} else if (!classesArrayList.contains(sound.sourceObject.getClass())) {
					System.out.println("adding sound = " + sound);
					System.out.println("sound.sourceActo = r" + sound.sourceActor);
					System.out.println("sound.sourceObject = " + sound.sourceObject);
					System.out.println("sound.sourceSquare = " + sound.sourceSquare);
					System.out.println("sound.legal = " + sound.legal);
					System.out.println("sound.action = " + sound.action);
					// System.out.println("sound.action.getName() = " +
					// sound.action.getName());
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

		// 1. Fighting
		if (this.actor.hasAttackers()) {

			this.actor.getAttackers().sort(AIRoutineUtils.sortAttackers);

			for (GameObject attacker1 : this.actor.getAttackers()) {

				if (this.actor.canSeeGameObject(attacker1)) {
					target = attacker1;

					if (target instanceof HidingPlace) {

						if (actor instanceof Mort)
							System.out.println("BS 1");

						this.actor.activityDescription = ACTIVITY_DESCRIPTION_SEARCHING;
					} else {
						this.actor.activityDescription = ACTIVITY_DESCRIPTION_FIGHTING;
					}

					// GET NEAREST ATTACKER FAILING??
					boolean attackedTarget = false;
					if (target != null) {
						attackedTarget = AIRoutineUtils.attackTarget(target);
						if (!attackedTarget) {
							AIRoutineUtils.moveTowardsTargetToAttack(target);

							for (GameObject attacker2 : this.actor.getAttackers()) {
								if (this.actor.canSeeGameObject(attacker2)) {
									// Change status to fighting if u can see an
									// enemy from
									// new location
									this.actor.thoughtBubbleImageTexture = null;
									if (target instanceof HidingPlace) {

										if (actor instanceof Mort)
											System.out.println("BS 2");
										this.actor.activityDescription = ACTIVITY_DESCRIPTION_SEARCHING;
									} else {
										this.actor.activityDescription = ACTIVITY_DESCRIPTION_FIGHTING;
									}
								}
							}
						}
					}
					return true;
				}
			}
		}

		return false;
	}

	public boolean runSearchRoutine() {

		// Searching
		if (this.actor.investigationsMap.size() == 0)
			return false;

		MapUtil.sortByValue(this.actor.investigationsMap);

		// this.actor.locationsToSearch.sort(AIRoutineUtils.sortLocationsToSearch);
		ArrayList<GameObject> toRemove = new ArrayList<GameObject>();
		boolean moved = false;

		for (GameObject actorToSearchFor : this.actor.investigationsMap.keySet()) {

			// If you're within 2 squares and can see the target actor, remove
			// from search list
			if (this.actor.straightLineDistanceTo(actorToSearchFor.squareGameObjectIsOn) <= 2
					&& this.actor.canSeeGameObject(actorToSearchFor)) {
				searchCooldown = 0;
				toRemove.add(actorToSearchFor);
				continue;
			}

			Square searchSquare = this.actor.investigationsMap.get(actorToSearchFor).square;
			searchCooldownActor = actorToSearchFor;
			searchCooldown = 10;

			// moce towards search square
			if (this.actor.squareGameObjectIsOn.straightLineDistanceTo(searchSquare) > 1
					&& this.actor.getPathTo(searchSquare) != null) {

				if (actor instanceof Mort)
					System.out.println("BS 3");
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

					if (actor instanceof Mort)
						System.out.println("BS 4");
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

					if (actor instanceof Mort)
						System.out.println("BS 5");
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

		for (GameObject actorsToSearchFor : toRemove) {
			this.actor.investigationsMap.remove(actorsToSearchFor);
		}

		if (moved) {

			for (GameObject attacker : this.actor.getAttackers()) {
				if (this.actor.canSeeGameObject(attacker)) {
					// Change status to fighting if u can see an enemy from
					// new location
					this.actor.thoughtBubbleImageTexture = null;
					if (target instanceof HidingPlace) {

						if (actor instanceof Mort)
							System.out.println("BS 6");
						this.actor.activityDescription = ACTIVITY_DESCRIPTION_SEARCHING;
					} else
						this.actor.activityDescription = ACTIVITY_DESCRIPTION_FIGHTING;
					break;
				}
			}

			return true;
		}
		return false;

	}

	public boolean runSearchCooldown() {

		// DOORWAYS are my biggest issue here.

		if (actor instanceof Mort)
			System.out.println("BS 7");
		this.actor.activityDescription = ACTIVITY_DESCRIPTION_SEARCHING;
		this.actor.thoughtBubbleImageTexture = ThoughtBubbles.QUESTION_MARK;
		StructureRoom room = actor.squareGameObjectIsOn.structureRoomSquareIsIn;

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
			moved = AIRoutineUtils.moveTowardsTargetSquare(actor.squareGameObjectIsOn.getSquareToRightOf());
		} else if (actor.squareGameObjectIsOn.xInGrid < actor.lastSquare.xInGrid) {
			moved = AIRoutineUtils.moveTowardsTargetSquare(actor.squareGameObjectIsOn.getSquareToLeftOf());
		} else {
			if (actor.squareGameObjectIsOn.yInGrid > actor.lastSquare.yInGrid) {
				moved = AIRoutineUtils.moveTowardsTargetSquare(actor.squareGameObjectIsOn.getSquareBelow());
			} else if (actor.squareGameObjectIsOn.yInGrid < actor.lastSquare.yInGrid) {
				moved = AIRoutineUtils.moveTowardsTargetSquare(actor.squareGameObjectIsOn.getSquareAbove());
			} else {

			}
		}

		if (!moved) {
			Square randomSquare = AIRoutineUtils.getRandomAdjacentSquare(actor.squareGameObjectIsOn);
			moved = AIRoutineUtils.moveTowardsTargetSquare(randomSquare);
		}
		// }
		return false;
	}

	protected boolean runCrimeReactionRoutine() {
		for (Actor criminal : actor.crimesWitnessed.keySet()) {
			int accumulatedSeverity = 0;
			ArrayList<Crime> unresolvedIllegalMining = new ArrayList<Crime>();
			ArrayList<Crime> unresolvedCrimes = new ArrayList<Crime>();
			ArrayList<GameObject> stolenItems = new ArrayList<GameObject>();
			for (Crime crime : actor.crimesWitnessed.get(criminal)) {
				accumulatedSeverity += crime.severity;
				if (crime.resolved == false) {
					unresolvedCrimes.add(crime);
					if (crime.action instanceof ActionMine) {
						unresolvedIllegalMining.add(crime);
					}
					for (GameObject stolenItem : crime.stolenItems)
						stolenItems.add(stolenItem);
				}
			}

			if (accumulatedSeverity >= 5) {
				actor.addAttackerForNearbyFactionMembersIfVisible(criminal);
				actor.addAttackerForThisAndGroupMembers(criminal);
				for (Crime unresolvedCrime : unresolvedCrimes) {
					unresolvedCrime.resolved = true;
				}
				return runFightRoutine();
			} else if (unresolvedCrimes.size() > 0) {
				for (Crime unresolvedCrime : unresolvedCrimes) {
					unresolvedCrime.resolved = true;
				}
				return true;
			}
		}
		return false;
	}

	public Actor actorToKeepTrackOf = null;
	public Square lastLocationSeenActorToKeepTrackOf = null;

	// public
	public boolean keepTrackOf(Actor target) {
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

}
