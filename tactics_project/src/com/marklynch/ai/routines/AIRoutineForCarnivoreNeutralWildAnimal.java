package com.marklynch.ai.routines;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.constructs.bounds.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Carcass;
import com.marklynch.objects.Food;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.CarnivoreNeutralWildAnimal;
import com.marklynch.objects.units.HerbivoreWildAnimal;

public class AIRoutineForCarnivoreNeutralWildAnimal extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

	enum SHOPKEEP_STATE {
		SHOPKEEPING, UPDATING_SIGN, GO_TO_BED_AND_GO_TO_SLEEP, SLEEP
	};

	final String ACTIVITY_DESCRIPTION_FEEDING = "Feeding";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Hunting";
	final String ACTIVITY_DESCRIPTION_ESCAPING = "Escaping";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";
	final String ACTIVITY_DESCRIPTION_DISGRUNTLED = "Disgruntled";

	public SHOPKEEP_STATE shopkeepState = SHOPKEEP_STATE.SHOPKEEPING;

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;
	CarnivoreNeutralWildAnimal wildAnimal;
	Square targetSquare = null;

	public AIRoutineForCarnivoreNeutralWildAnimal(CarnivoreNeutralWildAnimal actor, Area area) {
		super(actor);
		if (area != null) {
			keepInBounds = true;
			this.areaBounds.add(area);
		}
		this.wildAnimal = actor;
		aiType = AI_TYPE.FIGHTER;
		ACTIVITY_DESCRIPTION_FIGHTING = "Attacking";
	}

	@Override
	public void update() {

		aiRoutineStart();

		// Fight
		if (runFightRoutine())
			return;

		// Search
		if (runSearchRoutine())
			return;

		// Search cooldown
		if (runSearchCooldown())
			return;

		// Defer to group leader
		if (deferToGroupLeader())
			return;

		// 1. attack small animal
		GameObject smallWildAnimal = target = AIRoutineUtils.getNearestForPurposeOfAttacking(50f, false, true, false,
				false, true, true, 0, HerbivoreWildAnimal.class);
		if (smallWildAnimal != null) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_HUNTING;
			this.actor.thoughtBubbleImageTexture = smallWildAnimal.imageTexture;
			if (this.wildAnimal.canSeeSquare(smallWildAnimal.squareGameObjectIsOn)) {
				this.wildAnimal.addAttackerForThisAndGroupMembers(smallWildAnimal);
			} else {
				AIRoutineUtils.moveTowardsSquareToBeAdjacent(smallWildAnimal.squareGameObjectIsOn);
			}
			return;
		}

		// 2. eat corpse on ground
		GameObject corpse = target = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(5f, true, false, true, false,
				false, false, 0, Carcass.class);
		if (corpse != null) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_FEEDING;
			this.actor.thoughtBubbleImageTexture = corpse.imageTexture;
			boolean ateCorpse = AIRoutineUtils.eatTarget(corpse);
			if (!ateCorpse) {
				AIRoutineUtils.moveTowardsSquareToBeAdjacent(corpse.squareGameObjectIsOn);
			} else {

			}
			return;
		}

		// 3. eat food on ground
		GameObject food = target = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(5f, true, false, true, false,
				false, false, 0, Food.class);
		if (food != null) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_FEEDING;
			this.actor.thoughtBubbleImageTexture = food.imageTexture;
			boolean ateFood = AIRoutineUtils.eatTarget(food);
			if (!ateFood) {
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
			if (wildAnimal.squareGameObjectIsOn == targetSquare || wildAnimal.getPathTo(targetSquare) == null)
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
