package com.marklynch.ai.routines;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Vein;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.HerbivoreWildAnimal;

public class AIRoutineForMiner extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	// final String ACTIVITY_DESCRIPTION_SKINNING = "Skinning";
	final String ACTIVITY_DESCRIPTION_MINING = "Mine Time";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";

	public AIRoutineForMiner(Actor actor) {

		super(actor);
		aiType = AI_TYPE.FIGHTER;
	}

	@Override
	public void update() {
		aiRoutineStart();

		if (Game.level.hour > 20 && Game.level.hour < 6) {
			state = STATE.GO_TO_BED_AND_GO_TO_SLEEP;
		} else {
			state = STATE.MINING;
		}

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

			// Skin carcass
			// if (skinCarcass()) {
			// this.actor.followersShouldFollow = true;
			// return;
			// }

			// Loot from carcass
			// if (lootCarcass()) {
			// this.actor.followersShouldFollow = true;
			// return;
			// }

			// Loot from ground
			if (lootFromGround()) {
				this.actor.followersShouldFollow = true;
				return;
			}
		}

		// Fight
		if (runFightRoutine()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Crime reaction
		if (runCrimeReactionRoutine()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Search
		if (runSearchRoutine()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Search cooldown
		if (runSearchCooldown()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Door maintenance routine
		if (runDoorRoutine()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Defer to group leader
		if (deferToGroupLeader()) {
			this.actor.followersShouldFollow = true;
			return;
		}

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
		if (sellItems()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Go about your business
		if (state == STATE.MINING)

		{
			this.actor.followersShouldFollow = true;
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_MINING;
			// if (target == null)
			target = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(100, false, false, true, false, false, true, 0,
					Vein.class);
			if (target == null) {
				// AIRoutineUtils.moveTowardsSquareToBeAdjacent(actor.area.centreSuqare);
			} else {
				if (target == null || target.squareGameObjectIsOn == null) {
					target = null;
				} else {
					this.actor.activityDescription = ACTIVITY_DESCRIPTION_MINING;
					if (target.remainingHealth <= 0) {
					} else {
						boolean mined = AIRoutineUtils.mine(target);
						if (!mined) {
							AIRoutineUtils.moveTowardsSquareToBeAdjacent(target.squareGameObjectIsOn);
						}
					}
				}
			}

		}

		if (state == STATE.GO_TO_BED_AND_GO_TO_SLEEP) {
			actor.followersShouldFollow = false;
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_GOING_TO_BED;
			if (this.actor.bed != null) {
				if (this.actor.squareGameObjectIsOn == this.actor.bed.squareGameObjectIsOn) {
					actor.sleeping = true;
					this.actor.activityDescription = ACTIVITY_DESCRIPTION_SLEEPING;
				} else {
					AIRoutineUtils.moveTowardsTargetToBeOn(this.actor.bed);
				}
			} else {
				state = STATE.HUNTING;
			}
		}
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForMiner(actor);
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
