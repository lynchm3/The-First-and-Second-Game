package com.marklynch.ai.routines;

import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.Expressions;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.MapUtil;

public class AIRoutine {

	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";

	public Actor actor;
	GameObject target;

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
		ArrayList<Square> squaresThisCanHear = this.actor.getAllSquaresWithinDistance(this.actor.hearing);
		for (Square squareThisCanHear : squaresThisCanHear) {
			for (Sound sound : squareThisCanHear.sounds) {
				if (!this.actor.locationsToSearch.containsValue(sound.sourceSquare)
						&& !this.actor.canSee(sound.sourceSquare)) {

					if (!sound.legal || classesArrayList.contains(sound.sourceObject.getClass())) {
						this.actor.locationsToSearch.put(sound.sourceActor, sound.sourceSquare);
					}
				}
			}
		}
	}

	public Sound getSoundFromSourceType(Class clazz) {

		// Check for sounds to investigate
		ArrayList<Square> squaresThisCanHear = this.actor.getAllSquaresWithinDistance(this.actor.hearing);
		for (Square squareThisCanHear : squaresThisCanHear) {
			for (Sound sound : squareThisCanHear.sounds) {
				if (clazz.isInstance(sound.sourceObject)) {
					return sound;
				}
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
		if (this.actor.locationsToSearch.size() > 0) {

			MapUtil.sortByValue(this.actor.locationsToSearch);

			// this.actor.locationsToSearch.sort(AIRoutineUtils.sortLocationsToSearch);
			ArrayList<Actor> toRemove = new ArrayList<Actor>();
			ArrayList<Actor> toAdd = new ArrayList<Actor>();
			boolean moved = false;

			for (Actor actorToSearchFor : this.actor.locationsToSearch.keySet()) {

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

			for (Actor actorsToSearchFor : toAdd) {
				this.actor.locationsToSearch.put(actorsToSearchFor, actorsToSearchFor.squareGameObjectIsOn);
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

		}
		return false;
	}

}
