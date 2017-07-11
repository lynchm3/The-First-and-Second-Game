package com.marklynch.ai.routines;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Pig;
import com.marklynch.objects.weapons.Weapon;

public class AIRoutineForPig extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

	enum SHOPKEEP_STATE {
		SHOPKEEPING, UPDATING_SIGN, GO_TO_BED_AND_GO_TO_SLEEP, SLEEP
	};

	final String ACTIVITY_DESCRIPTION_PIGGING_OUT = "Pigging out!";
	final String ACTIVITY_DESCRIPTION_BEING_A_PIG = "Being a pig";
	final String ACTIVITY_DESCRIPTION_BEING_A_CHICKEN = "Being a chicken";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";
	final String ACTIVITY_DESCRIPTION_DISGRUNTLED = "Disgruntled";

	public SHOPKEEP_STATE shopkeepState = SHOPKEEP_STATE.SHOPKEEPING;

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	Pig pig;
	Square targetSquare = null;

	public AIRoutineForPig(Pig actor) {
		super(actor);
		this.pig = actor;
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
		GameObject loot = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(GameObject.class, 5f, true, false, true,
				false, false, false);
		if (loot != null) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_PIGGING_OUT;
			this.actor.thoughtBubbleImageTexture = loot.imageTexture;
			boolean pickedUpLoot = AIRoutineUtils.eatTarget(loot);
			if (!pickedUpLoot) {
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(loot);
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

		this.actor.activityDescription = ACTIVITY_DESCRIPTION_BEING_A_PIG;
		// Move about a bit
		if (targetSquare != null) {
			boolean moved = AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
			if (pig.squareGameObjectIsOn == targetSquare || pig.getPathTo(targetSquare) == null)
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
