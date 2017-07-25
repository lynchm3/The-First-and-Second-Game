package com.marklynch.ai.routines;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.constructs.bounds.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Food;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.HerbivoreWildAnimal;
import com.marklynch.objects.weapons.Weapon;

public class AIRoutineForHerbivoreWildAnimal extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

	enum SHOPKEEP_STATE {
		SHOPKEEPING, UPDATING_SIGN, GO_TO_BED_AND_GO_TO_SLEEP, SLEEP
	};

	final String ACTIVITY_DESCRIPTION_FEEDING = "Feeding";
	final String ACTIVITY_DESCRIPTION_ESCAPING = "Escaping";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";
	final String ACTIVITY_DESCRIPTION_DISGRUNTLED = "Disgruntled";

	public SHOPKEEP_STATE shopkeepState = SHOPKEEP_STATE.SHOPKEEPING;

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	HerbivoreWildAnimal friendlyWildAnimal;
	Square targetSquare = null;

	public AIRoutineForHerbivoreWildAnimal(HerbivoreWildAnimal actor, Area area) {
		super(actor);
		if (area != null) {
			keepInBounds = true;
			this.areaBounds.add(area);
		}
		this.friendlyWildAnimal = actor;
		aiType = AI_TYPE.RUNNER;
	}

	@Override
	public void update() {
		this.actor.aiLine = null;
		this.actor.miniDialogue = null;
		this.actor.activityDescription = null;
		this.actor.thoughtBubbleImageTexture = null;
		createSearchLocationsBasedOnSounds(Weapon.class);
		createSearchLocationsBasedOnVisibleAttackers();

		// In bed
		if (actor.squareGameObjectIsOn == null)
			return;

		// Go to bed
		if (this.actor.bed != null) {
			if (actor.name.equals("Female Rabbit"))
				System.out.println("bed != null a");
			if (actor.straightLineDistanceTo(actor.bed.squareGameObjectIsOn) <= 1) {
				actor.bed.inventory.add(actor);
			}

			if (AIRoutineUtils.moveTowardsTargetToBeAdjacent(this.actor.bed))
				return;

			// boolean goingToSleep = AIRoutineUtils.sleep();
			// if (!goingToSleep) {
			// AIRoutineUtils.moveTowardsTargetToBeOn(this.actor.bed);
			// } else {
			//
			// }

		}
		// if (actor.bed != null) {
		// if (actor.name.equals("Male Rabbit"))
		// System.out.println("bed != null a");
		// if (actor.straightLineDistanceTo(actor.bed.squareGameObjectIsOn) < 1)
		// return;
		// if (actor.name.equals("Male Rabbit"))
		// System.out.println("bed != null b");
		//
		// if
		// (AIRoutineUtils.moveTowardsTargetSquare(this.actor.bed.squareGameObjectIsOn))
		// return;
		// if (actor.name.equals("Male Rabbit"))
		// System.out.println("bed != null c");
		// }

		if (runEscapeRoutine()) {
			// createSearchLocationsBasedOnSounds();
			createSearchLocationsBasedOnVisibleAttackers();
			return;
		}

		if (escapeCooldown > 0) {
			runEscapeCooldown(false);
			escapeCooldown--;
			createSearchLocationsBasedOnVisibleAttackers();
			return;
		}

		// if (runSearchRoutine()) {
		// // createSearchLocationsBasedOnSounds();
		// createSearchLocationsBasedOnVisibleAttackers();
		// return;
		// }

		// if (searchCooldown > 0) {
		// runSearchCooldown();
		// searchCooldown--;
		// createSearchLocationsBasedOnVisibleAttackers();
		// return;
		// }

		// If not leader defer to pack
		if (this.actor.group != null && this.actor != this.actor.group.getLeader()) {
			if (this.actor.group.update(this.actor)) {
				return;
			}
		}

		// 1. eat loot on ground
		GameObject food = target = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(Food.class, 5f, true, false, true,
				false, false, false);
		if (food != null) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_FEEDING;
			this.actor.thoughtBubbleImageTexture = food.imageTexture;
			boolean pickedUpLoot = AIRoutineUtils.eatTarget(food);
			if (!pickedUpLoot) {
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(food);
			} else {

			}
			return;
		}

		// Defer to quest
		if (this.actor.quest != null) {
			if (this.actor.quest.update(this.actor)) {
				return;
			}
		}

		// Move about a bit
		if (targetSquare != null) {
			boolean moved = AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
			if (friendlyWildAnimal.squareGameObjectIsOn == targetSquare
					|| friendlyWildAnimal.getPathTo(targetSquare) == null)
				targetSquare = null;
			if (moved)
				return;
		} else {
			if (Math.random() < 0.05) {
				targetSquare = AIRoutineUtils.getRandomSquare(5, false);
				if (AIRoutineUtils.moveTowardsTargetSquare(targetSquare))
					return;
			}
		}
	}

}
