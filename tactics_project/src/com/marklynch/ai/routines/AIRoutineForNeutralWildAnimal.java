package com.marklynch.ai.routines;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.constructs.bounds.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Food;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.NeutralWildAnimal;
import com.marklynch.objects.weapons.Weapon;

public class AIRoutineForNeutralWildAnimal extends AIRoutine {

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

	NeutralWildAnimal friendlyWildAnimal;
	Square targetSquare = null;

	public AIRoutineForNeutralWildAnimal(NeutralWildAnimal actor, Area area) {
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
		System.out.println("AIRoutineForNeutralWildAnimal a friendlyWildAnimal = " + friendlyWildAnimal);
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
		System.out.println("AIRoutineForNeutralWildAnimal b");

		if (escapeCooldown > 0) {
			runEscapeCooldown(false);
			escapeCooldown--;
			createSearchLocationsBasedOnVisibleAttackers();
			return;
		}
		System.out.println("AIRoutineForNeutralWildAnimal c");

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
		System.out.println("AIRoutineForNeutralWildAnimal d");

		// 1. eat loot on ground
		GameObject loot = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(Food.class, 5f, true, false, true, false,
				false, false);
		if (loot != null) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_FEEDING;
			this.actor.thoughtBubbleImageTexture = loot.imageTexture;
			boolean pickedUpLoot = AIRoutineUtils.eatTarget(loot);
			if (!pickedUpLoot) {
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(loot);
			} else {

			}
			return;
		}
		System.out.println("AIRoutineForNeutralWildAnimal e");

		// Defer to quest
		if (this.actor.quest != null) {
			if (this.actor.quest.update(this.actor)) {
				return;
			}
		}
		System.out.println("AIRoutineForNeutralWildAnimal f");

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
		System.out.println("AIRoutineForNeutralWildAnimal g");
	}

}
