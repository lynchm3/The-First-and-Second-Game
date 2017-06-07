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
	public Actor searchCooldownActor = null;

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
			for (Actor attacker : this.actor.getAttackers()) {
				if (this.actor.canSeeGameObject(attacker)) {
					System.out.println("locationsToSearch.put a");
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
						System.out.println("locationsToSearch.put b");
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

				if (!sound.legal || classesArrayList.contains(sound.sourceObject.getClass())) {
					System.out.println("locationsToSearch.put c");
					this.actor.addInvestigation(sound.sourceActor, sound.sourceSquare,
							Investigation.INVESTIGATION_PRIORITY_CRIME_HEARD);
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

			for (Actor attacker1 : this.actor.getAttackers()) {

				if (this.actor.canSeeGameObject(attacker1)) {
					target = attacker1;
					this.actor.activityDescription = ACTIVITY_DESCRIPTION_FIGHTING;

					// GET NEAREST ATTACKER FAILING??
					boolean attackedTarget = false;
					if (target != null) {
						attackedTarget = AIRoutineUtils.attackTarget(target);
						if (!attackedTarget) {
							AIRoutineUtils.moveTowardsTargetToAttack(target);

							for (Actor attacker2 : this.actor.getAttackers()) {
								if (this.actor.canSeeGameObject(attacker2)) {
									// Change status to fighting if u can see an
									// enemy from
									// new location
									this.actor.thoughtBubbleImageTexture = null;
									this.actor.activityDescription = ACTIVITY_DESCRIPTION_FIGHTING;
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

		if (actor instanceof Mort)
			System.out.println("runSearchRoutine");

		// Searching
		if (this.actor.investigationsMap.size() == 0)
			return false;

		if (actor instanceof Mort)
			System.out.println("runSearchRoutine a");

		MapUtil.sortByValue(this.actor.investigationsMap);

		// this.actor.locationsToSearch.sort(AIRoutineUtils.sortLocationsToSearch);
		ArrayList<Actor> toRemove = new ArrayList<Actor>();
		boolean moved = false;

		if (actor instanceof Mort)
			System.out.println("runSearchRoutine b");

		for (Actor actorToSearchFor : this.actor.investigationsMap.keySet()) {

			if (actor instanceof Mort) {
				System.out.println("runSearchRoutine c");
				System.out.println("actorToSearchFor =  " + actorToSearchFor);
			}

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

				this.actor.activityDescription = ACTIVITY_DESCRIPTION_SEARCHING;
				this.actor.thoughtBubbleImageTexture = ThoughtBubbles.QUESTION_MARK;
				moved = AIRoutineUtils.moveTowardsTargetSquare(searchSquare);

				break;

			}

			if (actor instanceof Mort) {
				System.out.println("runSearchRoutine d");
				System.out.println("searchSquare =  " + searchSquare);
			}

			if (moved)
				break;

			if (actor instanceof Mort)
				System.out.println("runSearchRoutine e");

			if (this.actor.squareGameObjectIsOn.straightLineDistanceTo(searchSquare) <= 1) {
				toRemove.add(actorToSearchFor);
				break;
			}

			if (actor instanceof Mort)
				System.out.println("runSearchRoutine f");

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

			if (actor instanceof Mort)
				System.out.println("runSearchRoutine g");

			if (moved)
				break;

			if (actor instanceof Mort)
				System.out.println("runSearchRoutine h");

			if (this.actor.squareGameObjectIsOn.straightLineDistanceTo(searchSquare) <= 2) {
				toRemove.add(actorToSearchFor);
				break;
			}

			if (actor instanceof Mort)
				System.out.println("runSearchRoutine i");

			// distance 2
			for (Square searchSquareAtDistanceTwo : searchSquare.getAllSquaresAtDistance(2)) {
				if (this.actor.getPathTo(searchSquareAtDistanceTwo) != null) {

					this.actor.activityDescription = ACTIVITY_DESCRIPTION_SEARCHING;
					this.actor.thoughtBubbleImageTexture = ThoughtBubbles.QUESTION_MARK;
					moved = AIRoutineUtils.moveTowardsTargetSquare(searchSquareAtDistanceTwo);
					break;

				}
			}

			if (actor instanceof Mort)
				System.out.println("runSearchRoutine j");

			if (moved)
				break;

			if (actor instanceof Mort) {
				System.out.println("runSearchRoutine k");
			}

			toRemove.add(actorToSearchFor);
		}

		if (actor instanceof Mort) {
			System.out.println("runSearchRoutine l");
			System.out.println("toRemove.size() = " + toRemove.size());
		}

		for (Actor actorsToSearchFor : toRemove) {
			this.actor.investigationsMap.remove(actorsToSearchFor);
		}

		if (moved) {

			for (Actor attacker : this.actor.getAttackers()) {
				if (this.actor.canSeeGameObject(attacker)) {
					// Change status to fighting if u can see an enemy from
					// new location
					this.actor.thoughtBubbleImageTexture = null;
					this.actor.activityDescription = ACTIVITY_DESCRIPTION_FIGHTING;
				}
			}

			return true;
		}
		return false;

	}

	public boolean runSearchCooldown() {

		// DOORWAYS are my biggest issue here.

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
	public void keepTrackOf(Actor target) {
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
					System.out.println("locationsToSearch.put d");
					this.actor.addInvestigation(target, lastLocationSeenActorToKeepTrackOf,
							Investigation.INVESTIGATION_PRIORITY_KEEP_TRACK);
					lastLocationSeenActorToKeepTrackOf = null;
				}

				if (actor.canSeeGameObject(target)) {
					actor.investigationsMap.remove(target);
					lastLocationSeenActorToKeepTrackOf = target.squareGameObjectIsOn;
				}

				return;
			}
		} else {
			actor.investigationsMap.remove(target);
			lastLocationSeenActorToKeepTrackOf = target.squareGameObjectIsOn;
		}
	}

}
