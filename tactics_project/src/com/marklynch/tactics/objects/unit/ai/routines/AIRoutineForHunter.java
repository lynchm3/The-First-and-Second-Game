package com.marklynch.tactics.objects.unit.ai.routines;

import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.Junk;
import com.marklynch.tactics.objects.unit.ShopKeeper;
import com.marklynch.tactics.objects.unit.WildAnimal;
import com.marklynch.tactics.objects.unit.ai.utils.AIRoutineUtils;

public class AIRoutineForHunter extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

	enum HUNT_STATE {
		PICK_WILD_ANIMAL, GO_TO_WILD_ANIMAL_AND_ATTACK, GO_TO_WILD_ANIMAL_AND_LOOT, PICK_SHOP_KEEPER, GO_TO_SHOP_KEEPER_AND_SELL_JUNK, GO_TO_BED_AND_SLEEP
	};

	public HUNT_STATE huntState = HUNT_STATE.PICK_WILD_ANIMAL;

	public AIRoutineForHunter() {

	}

	@Override
	public void update() {
		System.out.println("AIRoutineHunt.update()");
		if (huntState == HUNT_STATE.PICK_WILD_ANIMAL) {
			System.out.println("huntState == HUNT_STATE.PICK_WILD_ANIMAL");
			// if (target == null)
			target = AIRoutineUtils.getNearest(WildAnimal.class);
			if (target == null) {
				huntState = HUNT_STATE.GO_TO_BED_AND_SLEEP;
			} else {
				huntState = HUNT_STATE.GO_TO_WILD_ANIMAL_AND_ATTACK;
			}
		}

		if (huntState == HUNT_STATE.GO_TO_WILD_ANIMAL_AND_ATTACK) {
			System.out.println("huntState == HUNT_STATE.GO_TO_WILD_ANIMAL_AND_ATTACK");
			if (target.remainingHealth <= 0 && target.inventory.size() > 0) {
				huntState = HUNT_STATE.GO_TO_WILD_ANIMAL_AND_LOOT;
			} else if (target.remainingHealth <= 0 && target.inventory.size() == 0) {
				huntState = HUNT_STATE.GO_TO_BED_AND_SLEEP;
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
				AIRoutineUtils.moveTowardsTargetToLoot(target);
			} else {
				target = null;
				huntState = HUNT_STATE.PICK_SHOP_KEEPER;
			}

		}
		if (huntState == HUNT_STATE.PICK_SHOP_KEEPER) {
			System.out.println("huntState == HUNT_STATE.PICK_SHOP_KEEPER");
			// if (target == null)
			target = AIRoutineUtils.getNearest(ShopKeeper.class);
			if (target == null) {
				huntState = HUNT_STATE.GO_TO_BED_AND_SLEEP;
			} else {
				huntState = HUNT_STATE.GO_TO_SHOP_KEEPER_AND_SELL_JUNK;
			}
		}

		if (huntState == HUNT_STATE.GO_TO_SHOP_KEEPER_AND_SELL_JUNK) {
			System.out.println("huntState == HUNT_STATE.GO_TO_SHOP_KEEPER_AND_SELL_JUNK");

			boolean soldItems = AIRoutineUtils.sellAllToTarget(Junk.class, target);
			if (!soldItems)
				AIRoutineUtils.moveTowardsTargetToLoot(target);
			else
				huntState = HUNT_STATE.GO_TO_BED_AND_SLEEP;

		}

		if (huntState == HUNT_STATE.GO_TO_BED_AND_SLEEP) {
			System.out.println("huntState == HUNT_STATE.GO_TO_BED_AND_SLEEP");

		}
	}

	// 1. Pick nearest target of type WILD ANIMAL within 100 squares
	// 2. Go to target
	// 3. Kill it
	// 4. Loot it
	// 5. Pick nearest target of type SHOP
	// 6. Go to target
	// 7. Sell loot

	// Special cases
	// If u dont have loot, dont got to the shop, got back to step one
	// If there's no wild animal close enough go to sleep until there is

}
