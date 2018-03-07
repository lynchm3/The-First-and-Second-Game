package com.marklynch.ai.routines;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.HerbivoreWildAnimal;

public class AIRoutineForFisherman extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	// final String ACTIVITY_DESCRIPTION_SKINNING = "Skinning";
	final String ACTIVITY_DESCRIPTION_MINING = "Mine Time";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";

	public AIRoutineForFisherman(Actor actor) {

		super(actor);
		aiType = AI_TYPE.RUNNER;
	}

	@Override
	public void update() {
		aiRoutineStart();

		if (Game.level.hour > 20 || Game.level.hour < 6) {
			state = STATE.GO_TO_BED_AND_GO_TO_SLEEP;
		} else {
			state = STATE.FISHING;
		}

		if (runSleepRoutine())
			return;

		// Shout for help

		boolean hostileInAttackers = false;
		for (GameObject attacker : actor.attackers) {
			if (!(attacker instanceof HerbivoreWildAnimal)) {
				hostileInAttackers = true;
				break;
			}
		}

		if (!hostileInAttackers) {

			// Loot from ground
			if (lootFromGround()) {
				this.actor.followersShouldFollow = true;
				return;
			}
		}

		if (runGetHelpRoutine())
			return;

		// Shout for help cooldown
		if (runEscapeCooldown(true))
			return;

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
		if (deferToGroupLeader())
			return;

		// Defer to quest
		if (deferToQuest())
			return;

		// Update wanted poster
		if (reportToGuardRoutine())
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
		if (state == STATE.FISHING) {
			runFishingRoutine();
		}

		if (state == STATE.GO_TO_BED_AND_GO_TO_SLEEP) {

			goToBedAndSleep();
		}
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForFisherman(actor);
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
