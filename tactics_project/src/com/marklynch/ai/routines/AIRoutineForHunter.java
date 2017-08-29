package com.marklynch.ai.routines;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.objects.Carcass;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Junk;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.CarnivoreNeutralWildAnimal;
import com.marklynch.objects.units.HerbivoreWildAnimal;
import com.marklynch.objects.units.TinyNeutralWildAnimal;
import com.marklynch.objects.units.Trader;

public class AIRoutineForHunter extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

	enum HUNT_STATE {
		PICK_WILD_ANIMAL, GO_TO_WILD_ANIMAL_AND_ATTACK, GO_TO_WILD_ANIMAL_AND_LOOT, PICK_SHOP_KEEPER, GO_TO_SHOP_KEEPER_AND_SELL_JUNK, GO_TO_BED_AND_GO_TO_SLEEP, SLEEP
	};

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	// final String ACTIVITY_DESCRIPTION_SKINNING = "Skinning";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";
	final String ACTIVITY_DESCRIPTION_SELLING_LOOT = "Selling spoils";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";
	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";

	public HUNT_STATE huntState = HUNT_STATE.PICK_WILD_ANIMAL;

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	public AIRoutineForHunter(Actor actor) {

		super(actor);
		aiType = AI_TYPE.FIGHTER;
	}

	@Override
	public void update() {
		aiRoutineStart();

		// Fight
		if (runFightRoutine())
			return;

		// Crime reaction
		if (runCrimeReactionRoutine())
			return;

		// Search
		if (runSearchRoutine())
			return;

		// Search cooldown
		if (runSearchCooldown())
			return;

		// Door maintenance routine
		if (runDoorRoutine())
			return;

		// Defer to group leader
		if (deferToGroupLeader())
			return;

		// if group leader wait for group
		if (this.actor.group != null && this.actor == this.actor.group.getLeader()) {
			if (this.actor.group.leaderNeedsToWait()) {
				this.actor.activityDescription = "Waiting for " + this.actor.group.name;
				return;
			}
		}

		// 1. loot corpses
		GameObject carcass = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(5f, false, false, true, true, true,
				true, 0, Carcass.class);
		if (carcass != null) {
			this.actor.thoughtBubbleImageTexture = carcass.imageTexture;
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_LOOTING;
			boolean lootedCarcass = AIRoutineUtils.lootTarget(carcass);
			if (!lootedCarcass) {
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(carcass);
			} else {

			}
			return;
		}

		// Loot from ground
		if (lootFromGround())
			return;

		// Defer to quest
		if (deferToQuest())
			return;

		// Go about your business
		if (huntState == HUNT_STATE.PICK_WILD_ANIMAL)

		{
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_HUNTING;
			// if (target == null)
			target = AIRoutineUtils.getNearestForPurposeOfAttacking(100, false, true, false, false, false, true, 0,
					AggressiveWildAnimal.class, CarnivoreNeutralWildAnimal.class, HerbivoreWildAnimal.class,
					TinyNeutralWildAnimal.class);

			if (target == null) {
				if (this.actor.inventory.contains(Junk.class)) {
					huntState = HUNT_STATE.PICK_SHOP_KEEPER;
				} else {
					huntState = HUNT_STATE.GO_TO_BED_AND_GO_TO_SLEEP;
				}
			} else {
				huntState = HUNT_STATE.GO_TO_WILD_ANIMAL_AND_ATTACK;
			}

		}

		if (huntState == HUNT_STATE.GO_TO_WILD_ANIMAL_AND_ATTACK) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_HUNTING;
			if (target.remainingHealth <= 0 && this.actor.inventory.size() > 0) {
				huntState = HUNT_STATE.PICK_SHOP_KEEPER;
			} else if (target.remainingHealth <= 0 && target.inventory.size() == 0) {
				huntState = HUNT_STATE.PICK_WILD_ANIMAL;
			} else {
				boolean attackedAnimal = AIRoutineUtils.attackTarget(target);
				if (!attackedAnimal) {
					AIRoutineUtils.moveTowardsTargetToAttack(target);
				}
			}
		}

		if (huntState == HUNT_STATE.PICK_SHOP_KEEPER) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_SELLING_LOOT;
			// if (target == null)
			target = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(0, false, true, false, false, false, false, 0,
					Trader.class);
			if (target == null) {
				huntState = HUNT_STATE.GO_TO_BED_AND_GO_TO_SLEEP;
			} else {
				huntState = HUNT_STATE.GO_TO_SHOP_KEEPER_AND_SELL_JUNK;
			}
		}

		if (huntState == HUNT_STATE.GO_TO_SHOP_KEEPER_AND_SELL_JUNK) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_SELLING_LOOT;

			boolean soldItems = AIRoutineUtils.sellAllToTarget(Junk.class, target);
			if (!soldItems)
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(target);
			else
				huntState = HUNT_STATE.GO_TO_BED_AND_GO_TO_SLEEP;

		}

		if (huntState == HUNT_STATE.GO_TO_BED_AND_GO_TO_SLEEP) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_GOING_TO_BED;
			// huntState = HUNT_STATE.PICK_WILD_ANIMAL;

			// CONCEPT OF BED AND SLEEPING NEXT
			if (this.actor.bed != null) {

				if (this.actor.squareGameObjectIsOn == this.actor.bed.squareGameObjectIsOn) {
					huntState = HUNT_STATE.SLEEP;
				} else {
					AIRoutineUtils.moveTowardsTargetToBeOn(this.actor.bed);
				}

				// boolean goingToSleep = AIRoutineUtils.sleep();
				// if (!goingToSleep) {
				// AIRoutineUtils.moveTowardsTargetToBeOn(this.actor.bed);
				// } else {
				//
				// }

			} else {
				huntState = HUNT_STATE.PICK_WILD_ANIMAL;
			}

			// ALSO -

		}

		if (huntState == HUNT_STATE.SLEEP) {
			// this.actor.activityDescription =
			// ACTIVITY_DESCRIPTION_GOING_TO_BED;
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_SLEEPING;
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
