package com.marklynch.ai.routines;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.HerbivoreWildAnimal;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Stump;
import com.marklynch.objects.inanimateobjects.Tree;

public class AIRoutineForLumberjack extends AIRoutine {

	public AIRoutineForLumberjack() {

		super();
		aiType = AI_TYPE.FIGHTER;
	}

	public AIRoutineForLumberjack(Actor actor) {

		super(actor);
		aiType = AI_TYPE.FIGHTER;
	}

	@Override
	public void update() {
		aiRoutineStart();

		if (Game.level.hour > 20 || Game.level.hour < 6) {
			state = STATE.GO_TO_BED_AND_GO_TO_SLEEP;
		} else {
			state = STATE.CHOPPING_WOOD;
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
		if (runFightRoutine(true)) {
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
		if (state == STATE.CHOPPING_WOOD) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_CHOPPING_WOODG;
			this.actor.followersShouldFollow = true;

			if (target != null && actor.straightLineDistanceTo(target.squareGameObjectIsOn) == 1
					&& target.remainingHealth > 0) {
				this.actor.thoughtBubbleImageTextureAction = Action.textureChop;
				this.actor.thoughtBubbleImageTextureObject = target.imageTexture;
				boolean chopped = AIRoutineUtils.chop(target);
				if (!chopped) {
					AIRoutineUtils.moveTowards(AIRoutineUtils.tempPath);
				}
			} else {
				target = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(10, false, false, true, true, 0, false,
						true, Tree.class, Stump.class);
				if (target != null) {
					this.actor.thoughtBubbleImageTextureAction = Action.textureChop;
					this.actor.thoughtBubbleImageTextureObject = target.imageTexture;
					AIRoutineUtils.moveTowards(AIRoutineUtils.tempPath);
				} else {
					AIRoutineUtils.moveTowards(actor.area.centreSquare);
				}
			}

		}

		if (state == STATE.GO_TO_BED_AND_GO_TO_SLEEP) {

			goToBedAndSleep();
		}
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForLumberjack(actor);
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
