package com.marklynch.tactics.objects.unit.ai.routines;

import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.unit.ai.utils.UtilFind;

public class AIRoutineHunt {

	GameObject Target;

	enum HUNT_STATE {
		PICK_WILD_ANIMAL, GO_TO_WILD_ANIMAL, KILL_WILD_ANIMAL, LOOT_WILD_ANIMAL, PICK_SHOP, GO_TO_SHOP, SELL_LOOT, GO_TO_BED, SLEEP
	};

	public HUNT_STATE huntState = HUNT_STATE.PICK_WILD_ANIMAL;

	public AIRoutineHunt() {

	}

	public void update() {
		if (huntState == HUNT_STATE.PICK_WILD_ANIMAL) {
			UtilFind.findNearest(Actor.class, 100, null);
		}
		if (huntState == HUNT_STATE.GO_TO_WILD_ANIMAL) {

		}
		if (huntState == HUNT_STATE.KILL_WILD_ANIMAL) {

		}
		if (huntState == HUNT_STATE.LOOT_WILD_ANIMAL) {

		}
		if (huntState == HUNT_STATE.PICK_SHOP) {

		}
		if (huntState == HUNT_STATE.GO_TO_SHOP) {

		}
		if (huntState == HUNT_STATE.SELL_LOOT) {

		}

		if (huntState == HUNT_STATE.GO_TO_BED) {

		}
		if (huntState == HUNT_STATE.SLEEP) {

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
