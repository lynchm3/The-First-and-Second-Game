package com.marklynch.ai.routines;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.CarnivoreNeutralWildAnimal;
import com.marklynch.objects.units.HerbivoreWildAnimal;
import com.marklynch.objects.units.TinyNeutralWildAnimal;

public class AIRoutineForHunter extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	// final String ACTIVITY_DESCRIPTION_SKINNING = "Skinning";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";

	public AIRoutineForHunter(Actor actor) {

		super(actor);
		aiType = AI_TYPE.FIGHTER;
		stateOnWakeup = STATE.PICK_WILD_ANIMAL;
		state = STATE.PICK_WILD_ANIMAL;
	}

	@Override
	public void update() {
		aiRoutineStart();

		if (runSleepRoutine())
			return;

		boolean hostileInAttackers = false;
		for (GameObject attacker : actor.attackers) {
			if (!(attacker instanceof HerbivoreWildAnimal)) {
				hostileInAttackers = true;
				break;
			}
		}

		if (!hostileInAttackers) {

			// Loot from carcass
			if (lootCarcass())
				return;

			// Loot from ground
			if (lootFromGround())
				return;
		}

		// Fight
		if (runFightRoutine()) {
			this.actor.followersShouldFollow = true;
			return;
		}

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

		// Loot carcass
		if (skinCarcass())
			return;

		// Loot carcass
		if (lootCarcass())
			return;

		// Loot from ground
		if (lootFromGround())
			return;

		// Defer to group leader
		if (deferToGroupLeader())
			return;

		// if group leader wait for group
		// if (this.actor.group != null && this.actor ==
		// this.actor.group.getLeader()) {
		// if (this.actor.group.leaderNeedsToWait()) {
		// this.actor.activityDescription = "Waiting for " +
		// this.actor.group.name;
		// return;
		// }
		// }

		// Defer to quest
		if (deferToQuest()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Sell items
		if (sellItems())
			return;

		// Go about your business
		if (state == STATE.PICK_WILD_ANIMAL)

		{
			this.actor.followersShouldFollow = true;
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_HUNTING;
			// if (target == null)
			target = AIRoutineUtils.getNearestForPurposeOfAttacking(100, false, true, false, false, false, true, 0,
					AggressiveWildAnimal.class, CarnivoreNeutralWildAnimal.class, HerbivoreWildAnimal.class,
					TinyNeutralWildAnimal.class);

			if (target == null) {
				state = STATE.GO_TO_BED_AND_GO_TO_SLEEP;
			} else {
				state = STATE.GO_TO_WILD_ANIMAL_AND_ATTACK;
			}

		}

		if (state == STATE.GO_TO_WILD_ANIMAL_AND_ATTACK) {
			this.actor.followersShouldFollow = true;

			if (target.squareGameObjectIsOn == null) {
				target = null;
				state = STATE.GO_TO_BED_AND_GO_TO_SLEEP;
			}

			this.actor.activityDescription = ACTIVITY_DESCRIPTION_HUNTING;
			if (target.remainingHealth <= 0 && this.actor.inventory.size() > 0) {
				state = STATE.GO_TO_BED_AND_GO_TO_SLEEP;
			} else if (target.remainingHealth <= 0 && target.inventory.size() == 0) {
				state = STATE.PICK_WILD_ANIMAL;
			} else {
				boolean attackedAnimal = AIRoutineUtils.attackTarget(target);
				if (!attackedAnimal) {
					AIRoutineUtils.moveTowardsTargetToAttack(target);
				}
			}
		}

		if (state == STATE.GO_TO_BED_AND_GO_TO_SLEEP) {
			actor.followersShouldFollow = false;
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_GOING_TO_BED;
			if (this.actor.bed != null) {
				if (this.actor.squareGameObjectIsOn == this.actor.bed.squareGameObjectIsOn) {
					actor.sleeping = true;
				} else {
					AIRoutineUtils.moveTowardsTargetToBeOn(this.actor.bed);
				}
			} else {
				state = STATE.PICK_WILD_ANIMAL;
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
