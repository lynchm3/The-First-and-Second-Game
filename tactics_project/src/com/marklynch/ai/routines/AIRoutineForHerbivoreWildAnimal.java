package com.marklynch.ai.routines;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Food;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.SmallHidingPlace;
import com.marklynch.objects.actions.ActionStopHidingInside;
import com.marklynch.objects.units.HerbivoreWildAnimal;

public class AIRoutineForHerbivoreWildAnimal extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

	final String ACTIVITY_DESCRIPTION_FEEDING = "Feeding";
	final String ACTIVITY_DESCRIPTION_ESCAPING = "Escaping";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";
	final String ACTIVITY_DESCRIPTION_DISGRUNTLED = "Disgruntled";

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	HerbivoreWildAnimal friendlyWildAnimal;
	Square targetSquare = null;

	int hidingCount = 0;

	public AIRoutineForHerbivoreWildAnimal(HerbivoreWildAnimal actor, Area area) {
		super(actor);
		if (area != null) {
			keepInBounds = true;
			this.areaBounds.add(area);
		}
		this.friendlyWildAnimal = actor;
		aiType = AI_TYPE.RUNNER;
	}

	@Override
	public void update() {

		// if (1 == 1)
		// return;

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

		// 1. eat food on ground
		GameObject food = target = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(5f, true, false, true, false,
				false, false, 0, Food.class);
		if (food != null) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_FEEDING;
			this.actor.thoughtBubbleImageTexture = food.imageTexture;
			boolean pickedUpLoot = AIRoutineUtils.eatTarget(food);
			if (!pickedUpLoot) {
				AIRoutineUtils.moveTowardsSquareToBeAdjacent(food.squareGameObjectIsOn);
			} else {

			}
			return;
		}

		// Defer to quest
		if (deferToQuest())
			return;

		// Move about a bit
		if (targetSquare != null) {
			boolean moved = AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
			if (friendlyWildAnimal.squareGameObjectIsOn == targetSquare
					|| friendlyWildAnimal.getPathTo(targetSquare) == null)
				targetSquare = null;
			if (moved)
				return;
		} else {
			if (Math.random() < 0.05) {
				targetSquare = AIRoutineUtils.getRandomSquare(5, false);
				if (AIRoutineUtils.moveTowardsTargetSquare(targetSquare))
					return;
			}
		}
	}

}
