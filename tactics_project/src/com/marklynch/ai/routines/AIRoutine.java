package com.marklynch.ai.routines;

import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.structure.StructureRoom;
import com.marklynch.objects.Expressions;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.MapUtil;

public class AIRoutine {

	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";

	public Actor actor;
	public GameObject target;
	public int searchCooldown = 0;

	public AIRoutine(Actor actor) {
		this.actor = actor;
	}

	public void update() {
	}

	public void createSearchLocationsBasedOnVisibleAttackers() {

		// Check for enemies last seen locations to search
		if (this.actor.hasAttackers()) {
			for (Actor attacker : this.actor.getAttackers()) {
				if (this.actor.straightLineDistanceTo(attacker.squareGameObjectIsOn) <= this.actor.sight
						&& this.actor.visibleFrom(attacker.squareGameObjectIsOn)) {
					this.actor.locationsToSearch.put(attacker, attacker.squareGameObjectIsOn);
				}
			}
		}

	}

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
									this.actor.expressionImageTexture = null;
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

			// System.out.println(
			// "this.actor.squareGameObjectIsOn.straightLineDistanceTo(this.actor.locationsToSearch.get(actorToSearchFor))
			// > 0"
			// + (this.actor.squareGameObjectIsOn
			// .straightLineDistanceTo(this.actor.locationsToSearch.get(actorToSearchFor))
			// > 0));
			//
			// System.out.println("this.actor.getPathTo(this.actor.locationsToSearch.get(actorToSearchFor))
			// != null"
			// +
			// (this.actor.getPathTo(this.actor.locationsToSearch.get(actorToSearchFor))
			// != null));

			if (this.actor.squareGameObjectIsOn
					.straightLineDistanceTo(this.actor.locationsToSearch.get(actorToSearchFor)) > 0
					&& this.actor.getPathTo(this.actor.locationsToSearch.get(actorToSearchFor)) != null) {

				this.actor.activityDescription = ACTIVITY_DESCRIPTION_SEARCHING;
				this.actor.expressionImageTexture = Expressions.questionMark;
				AIRoutineUtils.moveTowardsTargetSquare(this.actor.locationsToSearch.get(actorToSearchFor));
				moved = true;

				break;

			} else {
				toRemove.add(actorToSearchFor);
			}
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
					this.actor.expressionImageTexture = null;
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
		this.actor.expressionImageTexture = Expressions.questionMark;
		StructureRoom room = actor.squareGameObjectIsOn.structureRoomSquareIsIn;

		// if (room != null) {
		// AIRoutineUtils.moveTowardsTargetSquare(AIRoutineUtils.getRandomSquareInRoom(room));
		// return true;
		// } else {

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

		System.out.println("runSearchCooldown() moved 1 = " + moved);

		if (!moved) {
			Square randomSquare = AIRoutineUtils.getRandomAdjacentSquare(actor.squareGameObjectIsOn);
			moved = AIRoutineUtils.moveTowardsTargetSquare(randomSquare);
			System.out.println("runSearchCooldown() randomSquare = " + randomSquare);
			System.out.println("runSearchCooldown() moved 2 = " + moved);
		}
		// }
		return false;
	}

}
