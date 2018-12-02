package com.marklynch.ai.routines;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actors.Actor;

public class AIRoutineForPig extends AIRoutine {

	Actor pig;

	public AIRoutineForPig(Actor actor) {
		super(actor);
		this.pig = actor;
		aiType = AI_TYPE.RUNNER;
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

		// Escape
		if (runEscapeRoutine())
			return;

		// Escape cooldown
		if (runEscapeCooldown(false))
			return;

		// Defer to group leader
		if (deferToGroupLeader())
			return;

		// 1. eat loot on ground
		GameObject loot = AIRoutineUtils.getNearestForPurposeOfBeingAdjacent(5f, true, false, false, false, 0, false,
				true, GameObject.class);
		if (loot != null) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_PIGGING_OUT;
			this.actor.thoughtBubbleImageTextureObject = loot.imageTexture;
			boolean pickedUpLoot = AIRoutineUtils.eatTarget(loot);
			if (!pickedUpLoot) {
				AIRoutineUtils.moveTowards(AIRoutineUtils.tempPath);
			} else {

			}
			return;
		}

		// Defer to quest
		if (deferToQuest())
			return;

		if (state == STATE.HUNTING) {
			this.actor.activityDescription = ACTIVITY_DESCRIPTION_BEING_A_PIG;
			// Move about a bit
			if (targetSquare != null) {
				boolean moved = AIRoutineUtils.moveTowardsTargetSquare(targetSquare);
				if (pig.squareGameObjectIsOn == targetSquare || !moved)
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
		return new AIRoutineForPig(actor);
	}

}
