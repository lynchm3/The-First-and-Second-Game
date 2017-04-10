package com.marklynch.ai.routines;

import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.structure.StructureRoom;
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

	public AIRoutine(Actor actor) {
		this.actor = actor;
	}

	public void update() {
	}

	public void createSearchLocationsBasedOnVisibleAttackers() {

		// Check for enemies last seen locations to search
		if (this.actor.hasAttackers()) {
			for (Actor attacker : this.actor.getAttackers()) {
				if (this.actor.canSee(attacker.squareGameObjectIsOn)) {
					this.actor.locationsToSearch.put(attacker, attacker.squareGameObjectIsOn);
				}
			}
		}

	}

	public void createSearchLocationsBasedOnVisibleCriminals() {

		// Check for enemies last seen locations to search
		for (Actor criminal : this.actor.crimesWitnessed.keySet()) {

			for (Crime crime : actor.crimesWitnessed.get(criminal)) {
				if (!crime.resolved) {
					if (this.actor.straightLineDistanceTo(criminal.squareGameObjectIsOn) <= this.actor.sight
							&& this.actor.visibleFrom(criminal.squareGameObjectIsOn)) {
						System.out.println("Adding criminal");
						this.actor.locationsToSearch.put(criminal, criminal.squareGameObjectIsOn);
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
			if (!this.actor.locationsToSearch.containsValue(sound.sourceSquare)
					&& !this.actor.canSee(sound.sourceSquare)) {

				if (!sound.legal || classesArrayList.contains(sound.sourceObject.getClass())) {
					this.actor.locationsToSearch.put(sound.sourceActor, sound.sourceSquare);
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

				if (this.actor.straightLineDistanceTo(attacker1.squareGameObjectIsOn) <= this.actor.sight
						&& this.actor.visibleFrom(attacker1.squareGameObjectIsOn)) {
					target = attacker1;
					this.actor.activityDescription = ACTIVITY_DESCRIPTION_FIGHTING;

					// GET NEAREST ATTACKER FAILING??
					boolean attackedTarget = false;
					if (target != null) {
						attackedTarget = AIRoutineUtils.attackTarget(target);
						if (!attackedTarget) {
							AIRoutineUtils.moveTowardsTargetToAttack(target);

							for (Actor attacker2 : this.actor.getAttackers()) {
								if (this.actor
										.straightLineDistanceTo(attacker2.squareGameObjectIsOn) <= this.actor.sight
										&& this.actor.visibleFrom(attacker2.squareGameObjectIsOn)) {
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

		// Searching
		if (this.actor.locationsToSearch.size() == 0)
			return false;

		MapUtil.sortByValue(this.actor.locationsToSearch);

		// this.actor.locationsToSearch.sort(AIRoutineUtils.sortLocationsToSearch);
		ArrayList<Actor> toRemove = new ArrayList<Actor>();
		boolean moved = false;

		for (Actor actorToSearchFor : this.actor.locationsToSearch.keySet()) {
			// distance 0

			Square searchSquare = this.actor.locationsToSearch.get(actorToSearchFor);
			searchCooldownActor = actorToSearchFor;
			searchCooldown = 10;
			boolean done = false;

			if (this.actor.squareGameObjectIsOn.straightLineDistanceTo(searchSquare) > 1
					&& this.actor.getPathTo(searchSquare) != null) {

				this.actor.activityDescription = ACTIVITY_DESCRIPTION_SEARCHING;
				this.actor.thoughtBubbleImageTexture = ThoughtBubbles.QUESTION_MARK;
				AIRoutineUtils.moveTowardsTargetSquare(searchSquare);
				moved = true;
				System.out.println("runSearchRoutine 0");

				done = true;
				break;

			}

			if (done)
				break;

			if (this.actor.squareGameObjectIsOn.straightLineDistanceTo(searchSquare) <= 1) {
				toRemove.add(actorToSearchFor);
				break;
			}

			// distance 1
			for (Square searchSquareAtDistanceOne : searchSquare.getAllSquaresAtDistance(1)) {
				if (this.actor.squareGameObjectIsOn.straightLineDistanceTo(searchSquare) > 1
						&& this.actor.getPathTo(searchSquareAtDistanceOne) != null) {

					this.actor.activityDescription = ACTIVITY_DESCRIPTION_SEARCHING;
					this.actor.thoughtBubbleImageTexture = ThoughtBubbles.QUESTION_MARK;
					AIRoutineUtils.moveTowardsTargetSquare(searchSquareAtDistanceOne);
					moved = true;
					System.out.println("runSearchRoutine 1");

					done = true;
					break;

				}
			}

			if (done)
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
					AIRoutineUtils.moveTowardsTargetSquare(searchSquareAtDistanceTwo);
					moved = true;
					System.out.println("runSearchRoutine 2");

					done = true;
					break;

				}
			}

			if (done)
				break;

			toRemove.add(actorToSearchFor);
		}

		for (Actor actorsToSearchFor : toRemove) {
			this.actor.locationsToSearch.remove(actorsToSearchFor);
		}

		if (moved) {

			for (Actor attacker : this.actor.getAttackers()) {
				if (this.actor.straightLineDistanceTo(attacker.squareGameObjectIsOn) <= this.actor.sight
						&& this.actor.visibleFrom(attacker.squareGameObjectIsOn)) {
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
			if (this.actor.canSee(searchCooldownActor.squareGameObjectIsOn)) {
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

}
