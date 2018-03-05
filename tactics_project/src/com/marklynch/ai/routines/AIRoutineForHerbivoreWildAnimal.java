package com.marklynch.ai.routines;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.SmallHidingPlace;
import com.marklynch.objects.actions.ActionStopHidingInside;
import com.marklynch.objects.units.Actor;

public class AIRoutineForHerbivoreWildAnimal extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

	final String ACTIVITY_DESCRIPTION_FEEDING = "Feeding";
	final String ACTIVITY_DESCRIPTION_ESCAPING = "Escaping";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";
	final String ACTIVITY_DESCRIPTION_DISGRUNTLED = "Disgruntled";

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	Actor friendlyWildAnimal;
	Square targetSquare = null;

	int hidingCount = 0;

	public AIRoutineForHerbivoreWildAnimal(Actor actor) {
		super(actor);
		if (actor.area != null) {
			keepInBounds = true;
			this.areaBounds.add(actor.area);
		}
		this.friendlyWildAnimal = actor;
		aiType = AI_TYPE.RUNNER;
	}

	@Override
	public void update() {

		if (Game.level.hour > 20 || Game.level.hour < 6) {
			state = STATE.GO_TO_BED_AND_GO_TO_SLEEP;
		} else {
			state = STATE.HUNTING;
		}

		if (runSleepRoutine())
			return;

		actor.followersShouldFollow = true;

		// Hiding cooldown
		if (actor.squareGameObjectIsOn == null) {
			if (actor.inventoryThatHoldsThisObject.parent instanceof SmallHidingPlace) {
				hidingCount++;
				if (hidingCount >= 50) {
					new ActionStopHidingInside(actor, (SmallHidingPlace) actor.inventoryThatHoldsThisObject.parent)
							.perform();
					hidingCount = 0;
					escapeCooldown = 0;
					searchCooldown = 0;
				}
			}
			return;
		}

		aiRoutineStart();

		if (runEscapeRoutine())
			return;

		// Escape cooldown
		if (runEscapeCooldown(false))
			return;

		// Defer to group leader
		if (deferToGroupLeader())
			return;

		if (eatFoodOnGround())
			return;

		// 1. eat food on ground
		// GameObject food = target =
		// AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(5f, true, false,
		// true, false,
		// false, false, 0, false, Food.class);
		// if (food != null) {
		// this.actor.activityDescription = ACTIVITY_DESCRIPTION_FEEDING;
		// this.actor.thoughtBubbleImageTextureObject = food.imageTexture;
		// boolean pickedUpLoot = AIRoutineUtils.eatTarget(food);
		// if (!pickedUpLoot) {
		// AIRoutineUtils.moveTowards(AIRoutineUtils.tempPath);
		// } else {
		//
		// }
		// return;
		// }

		// Defer to quest
		if (deferToQuest())
			return;

		if (state == STATE.HUNTING) {
			// Move about a bit
			if (targetSquare != null) {
				boolean moved = AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
				if (friendlyWildAnimal.squareGameObjectIsOn == targetSquare || !moved)
					targetSquare = null;
				if (moved)
					return;
			} else {
				if (Math.random() < 0.05) {
					targetSquare = AIRoutineUtils.getRandomSquare(0, 5, false);
					if (AIRoutineUtils.moveTowardsTargetSquare(targetSquare))
						return;
				}
			}
		}

		if (state == STATE.GO_TO_BED_AND_GO_TO_SLEEP) {

			goToBedAndSleep();
		}
	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForHerbivoreWildAnimal(actor);
	}

}
