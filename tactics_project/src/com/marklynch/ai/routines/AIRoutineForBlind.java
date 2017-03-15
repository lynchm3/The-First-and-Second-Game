package com.marklynch.ai.routines;

import com.marklynch.objects.units.Actor;

public class AIRoutineForBlind extends AIRoutine {

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	final String ACTIVITY_DESCRIPTION_SKINNING = "Skinning";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";
	final String ACTIVITY_DESCRIPTION_SELLING_LOOT = "Selling spoils";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	public AIRoutineForBlind(Actor actor) {
		super(actor);
	}

	@Override
	public void update() {

		this.actor.activityDescription = null;
		this.actor.expressionImageTexture = null;
		createSearchLocationsBasedOnSounds();
		createSearchLocationsBasedOnVisibleAttackers();
		if (runFightRoutine())
			return;
		if (runSearchRoutine())
			return;

		// If not leader defer to pack
		if (this.actor.group != null && this.actor != this.actor.group.getLeader())

		{
			if (this.actor.group.update(this.actor)) {
				return;
			}
		}

		// if group leader wait for group
		if (this.actor.group != null && this.actor == this.actor.group.getLeader()) {
			if (this.actor.group.leaderNeedsToWait()) {
				this.actor.activityDescription = "Waiting for " + this.actor.group.name;
				return;
			}
		}

		// // 1. loot dead animals
		// GameObject carcass =
		// AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(Carcass.class, 5f,
		// false, false, true,
		// true);
		// if (carcass != null) {
		// this.actor.activityDescription =
		// ACTIVITY_DESCRIPTION_SKINNING;
		// boolean lootedCarcass = AIRoutineUtils.lootTarget(carcass);
		// if (!lootedCarcass) {
		// AIRoutineUtils.moveTowardsTargetToBeAdjacent(carcass);
		// } else {
		//
		// }
		// return;
		// }

		// // 1. pick up loot on ground
		// GameObject loot =
		// AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(GameObject.class,
		// 5f, true, false, true,
		// false);
		// if (loot != null) {
		// this.actor.activityDescription =
		// ACTIVITY_DESCRIPTION_LOOTING;
		// boolean pickedUpLoot = AIRoutineUtils.pickupTarget(loot);
		// if (!pickedUpLoot) {
		// AIRoutineUtils.moveTowardsTargetToBeAdjacent(loot);
		// } else {
		//
		// }
		// return;
		// }

		// Defer to quest
		if (this.actor.quest != null) {
			if (this.actor.quest.update(this.actor)) {
				return;
			}
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
