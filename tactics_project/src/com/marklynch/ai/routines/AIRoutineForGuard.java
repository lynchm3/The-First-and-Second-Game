package com.marklynch.ai.routines;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Guard;
import com.marklynch.objects.units.HerbivoreWildAnimal;

public class AIRoutineForGuard extends AIRoutine {

	GameObject target;
	Guard guard;
	int patrolIndex = 0;
	// Square squareToMoveTo;

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	// final String ACTIVITY_DESCRIPTION_SKINNING = "Skinning";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";

	// guard
	// their patrol, list of squares. can be on square.

	// Put one beside the lost and found
	// Put one in the town center

	public AIRoutineForGuard(Actor actor) {

		super(actor);
		aiType = AI_TYPE.FIGHTER;
		guard = (Guard) actor;
	}

	@Override
	public void update() {

		// 18:03 patrolling
		// 00:04 nothing

		aiRoutineStart();
		if (guard.shift.sleepStart > guard.shift.sleepEnd
				&& (Game.level.hour >= guard.shift.sleepStart || Game.level.hour <= guard.shift.sleepEnd)) {
			state = STATE.GO_TO_BED_AND_GO_TO_SLEEP;

		} else if (guard.shift.sleepStart < guard.shift.sleepEnd
				&& (Game.level.hour >= guard.shift.sleepStart && Game.level.hour <= guard.shift.sleepEnd)) {
			state = STATE.GO_TO_BED_AND_GO_TO_SLEEP;
		} else

		if (guard.shift.workStart > guard.shift.workEnd
				&& (Game.level.hour >= guard.shift.workStart || Game.level.hour <= guard.shift.workEnd)) {
			state = STATE.PATROL;

		} else if (guard.shift.workStart < guard.shift.workEnd
				&& (Game.level.hour >= guard.shift.workStart && Game.level.hour <= guard.shift.workEnd)) {
			state = STATE.PATROL;
		} else {
			state = STATE.FREE_TIME;
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
			if (skinCarcass()) {
				this.actor.followersShouldFollow = true;
				return;
			}

			// Loot from carcass
			if (lootCarcass()) {
				this.actor.followersShouldFollow = true;
				return;
			}

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

		// Update wanted poster
		if (updateWantedPosterRoutine())
			return;

		// Update wanted poster
		if (dropOffToLostAndFoundRoutine())
			return;

		// Pick up from lost and found
		if (pickUpFromLostAndFoundRoutine())
			return;

		// Door maintenance routine
		if (runDoorRoutine()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Sell items
		if (sellItems(10)) {
			this.actor.followersShouldFollow = true;
			return;
		}

		if (replenishEquipment()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// Go about your business
		if (state == STATE.PATROL) {
			actor.thoughtBubbleImageTextureObject = Action.texturePatrol;
			Square targetSquare = guard.patrolSquares[patrolIndex];
			if (guard.squareGameObjectIsOn == targetSquare) {
				patrolIndex++;
				if (patrolIndex >= guard.patrolSquares.length) {
					patrolIndex = 0;
				}
				targetSquare = guard.patrolSquares[patrolIndex];
			}
			AIRoutineUtils.moveTowards(targetSquare);
		}

		if (state == STATE.GO_TO_BED_AND_GO_TO_SLEEP) {
			goToBedAndSleep();
		}
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForGuard(actor);
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
