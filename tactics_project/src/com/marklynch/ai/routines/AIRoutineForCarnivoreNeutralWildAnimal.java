package com.marklynch.ai.routines;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.HerbivoreWildAnimal;

public class AIRoutineForCarnivoreNeutralWildAnimal extends AIRoutine {

	GameObject target;
	// Square squareToMoveTo;

	enum SHOPKEEP_STATE {
		SHOPKEEPING, UPDATING_SIGN, GO_TO_BED_AND_GO_TO_SLEEP, SLEEP
	};

	final String ACTIVITY_DESCRIPTION_HUNTING = "Hunting";
	final String ACTIVITY_DESCRIPTION_ESCAPING = "Escaping";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";
	final String ACTIVITY_DESCRIPTION_DISGRUNTLED = "Disgruntled";

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;
	Actor wildAnimal;
	Square targetSquare = null;

	public AIRoutineForCarnivoreNeutralWildAnimal(Actor actor) {
		super(actor);
		if (actor.area != null) {
			keepInBounds = true;
			this.areaBounds.add(actor.area);
		}
		this.wildAnimal = actor;
		aiType = AI_TYPE.FIGHTER;
		ACTIVITY_DESCRIPTION_FIGHTING = "Attacking";
	}

	@Override
	public void update() {

		aiRoutineStart();

		if (Game.level.hour > 20 || Game.level.hour < 6) {
			state = STATE.GO_TO_BED_AND_GO_TO_SLEEP;
		} else {
			state = STATE.HUNTING;
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

			// Eat carcass on ground
			if (eatCarcassOnGround()) {
				this.actor.followersShouldFollow = true;
				return;
			}

			// Eat food on ground
			if (eatFoodOnGround()) {
				this.actor.followersShouldFollow = true;
				return;
			}
		}

		// Fight
		if (runFightRoutine(true)) {
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

		// Defer to quest
		if (deferToQuest()) {
			this.actor.followersShouldFollow = true;
			return;
		}

		// (float maxDistance, boolean fitsInInventory,
		// boolean checkActors, boolean checkInanimateObjects, boolean
		// mustContainObjects, boolean mustBeUnowned,
		// boolean ignoreQuestObjects, int minimumValue, Class[] classes)

		// 1. attack small animal
		if (state == STATE.HUNTING) {
			GameObject smallWildAnimal = target = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(50f, false, false,
					true, true, 0, false, true, HerbivoreWildAnimal.class);
			if (smallWildAnimal != null) {
				this.actor.activityDescription = ACTIVITY_DESCRIPTION_HUNTING;
				this.actor.thoughtBubbleImageTextureObject = smallWildAnimal.imageTexture;
				if (this.wildAnimal.canSeeSquare(smallWildAnimal.squareGameObjectIsOn)) {
					this.wildAnimal.addAttackerForThisAndGroupMembers(smallWildAnimal);
				} else {
					AIRoutineUtils.moveTowards(smallWildAnimal.squareGameObjectIsOn);
				}
				return;
			}

			// Move about a bit
			if (targetSquare != null) {
				boolean moved = AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
				if (wildAnimal.squareGameObjectIsOn == targetSquare || moved == false)
					targetSquare = null;
				if (moved)
					return;
			} else {
				if (Math.random() < 0.05) {
					targetSquare = AIRoutineUtils.getRandomSquare(0, 5, false, true);
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
		return new AIRoutineForCarnivoreNeutralWildAnimal(actor);
	}

}
