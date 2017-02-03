package com.marklynch.tactics.objects.unit.ai.routines;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.Junk;
import com.marklynch.tactics.objects.unit.Trader;
import com.marklynch.tactics.objects.unit.WildAnimal;
import com.marklynch.tactics.objects.unit.ai.utils.AIRoutineUtils;

public class AIRoutineForHunter extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

	enum HUNT_STATE {
		PICK_WILD_ANIMAL, GO_TO_WILD_ANIMAL_AND_ATTACK, GO_TO_WILD_ANIMAL_AND_LOOT, PICK_SHOP_KEEPER, GO_TO_SHOP_KEEPER_AND_SELL_JUNK, GO_TO_BED_AND_GO_TO_SLEEP, SLEEP
	};

	public HUNT_STATE huntState = HUNT_STATE.PICK_WILD_ANIMAL;

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	public AIRoutineForHunter() {

	}

	@Override
	public void update() {

		// Interrupts first, could put these in to the
		// 1. loot on ground
		GameObject loot = AIRoutineUtils.getNearest(GameObject.class, 5f, true, false, true);
		if (loot != null) {
			boolean lootedAnimal = AIRoutineUtils.pickupTarget(loot);
			if (!lootedAnimal) {
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(loot);
			} else {

			}
			return;
		}

		// Go about your business
		System.out.println("AIRoutineHunt.update()");
		if (huntState == HUNT_STATE.PICK_WILD_ANIMAL)

		{
			System.out.println("huntState == HUNT_STATE.PICK_WILD_ANIMAL");
			// if (target == null)
			target = AIRoutineUtils.getNearest(WildAnimal.class, 0, false, true, false);
			if (target == null) {
				huntState = HUNT_STATE.GO_TO_BED_AND_GO_TO_SLEEP;
			} else {
				huntState = HUNT_STATE.GO_TO_WILD_ANIMAL_AND_ATTACK;
			}
		}

		if (huntState == HUNT_STATE.GO_TO_WILD_ANIMAL_AND_ATTACK) {
			System.out.println("huntState == HUNT_STATE.GO_TO_WILD_ANIMAL_AND_ATTACK");
			if (target.remainingHealth <= 0 && target.inventory.size() > 0) {
				huntState = HUNT_STATE.GO_TO_WILD_ANIMAL_AND_LOOT;
			} else if (target.remainingHealth <= 0 && target.inventory.size() == 0) {
				huntState = HUNT_STATE.GO_TO_BED_AND_GO_TO_SLEEP;
			} else {
				boolean attackedAnimal = AIRoutineUtils.attackTarget(target);
				if (!attackedAnimal)
					AIRoutineUtils.moveTowardsTargetToAttack(target);
			}
		}

		if (huntState == HUNT_STATE.GO_TO_WILD_ANIMAL_AND_LOOT) {
			System.out.println("huntState == HUNT_STATE.LOOT_WILD_ANIMAL");
			boolean lootedAnimal = AIRoutineUtils.lootTarget(target);
			if (!lootedAnimal) {
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(target);
			} else {
				target = null;
				huntState = HUNT_STATE.PICK_SHOP_KEEPER;
			}

		}
		if (huntState == HUNT_STATE.PICK_SHOP_KEEPER) {
			System.out.println("huntState == HUNT_STATE.PICK_SHOP_KEEPER");
			// if (target == null)
			target = AIRoutineUtils.getNearest(Trader.class, 0, false, true, false);
			if (target == null) {
				huntState = HUNT_STATE.GO_TO_BED_AND_GO_TO_SLEEP;
			} else {
				huntState = HUNT_STATE.GO_TO_SHOP_KEEPER_AND_SELL_JUNK;
			}
		}

		if (huntState == HUNT_STATE.GO_TO_SHOP_KEEPER_AND_SELL_JUNK) {
			System.out.println("huntState == HUNT_STATE.GO_TO_SHOP_KEEPER_AND_SELL_JUNK");

			boolean soldItems = AIRoutineUtils.sellAllToTarget(Junk.class, target);
			if (!soldItems)
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(target);
			else
				huntState = HUNT_STATE.GO_TO_BED_AND_GO_TO_SLEEP;

		}

		if (huntState == HUNT_STATE.GO_TO_BED_AND_GO_TO_SLEEP) {
			System.out.println("huntState == HUNT_STATE.GO_TO_BED_AND_SLEEP");
			// huntState = HUNT_STATE.PICK_WILD_ANIMAL;

			// CONCEPT OF BED AND SLEEPING NEXT
			if (Game.level.activeActor.bed != null) {

				// boolean goingToSleep = AIRoutineUtils.sleep();
				// if (!goingToSleep) {
				// AIRoutineUtils.moveTowardsTargetToBeOn(Game.level.activeActor.bed);
				// } else {
				//
				// }

			} else {
				huntState = HUNT_STATE.PICK_WILD_ANIMAL;
			}

			// ALSO -

		}

		if (huntState == HUNT_STATE.SLEEP) {
			// sleep();
		}
	}

	// 1. Pick nearest target of type WILD ANIMAL within 100 squares
	// 2. Go to target
	// 3. Kill it
	// 4. Loot it
	// 5. Pick nearest target of type SHOP
	// 6. Go to target
	// 7. Sell loot

	// CAN I MERGE THE PICK WITH THE GO TO?

	// Special cases
	// If u dont have loot, dont got to the shop, got back to step one
	// If there's no wild animal close enough go to sleep until there is
	// DOnt have somewhere to sleep
	// low health
	//

}
