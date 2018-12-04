package com.marklynch.ai.routines;

import com.marklynch.Game;
import com.marklynch.actions.ActionMove;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;

public class AIRoutineForWildAnimal extends AIRoutine {

	public AIRoutineForWildAnimal() {
		super();
		aiType = AI_TYPE.ANIMAL;
	}

	public AIRoutineForWildAnimal(Actor actor) {
		super(actor);
		aiType = AI_TYPE.ANIMAL;
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

		// Fight
		if (runFightRoutine(true))
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

		// Defer to quest
		if (deferToQuest())
			return;

		if (state == STATE.HUNTING) {
			// Go about ur business...
			if (targetSquare == null || this.actor.getPathTo(targetSquare) == null) {
				targetSquare = AIRoutineUtils.getRandomSquare(0, 10, true, true);
			}

			if (targetSquare != null) {
				Square squareToMoveTo = AIRoutineUtils.getSquareToMoveAlongPath(this.actor.getPathTo(targetSquare));
				if (squareToMoveTo == null) {
					targetSquare = null;
					return;
				} else {
					new ActionMove(this.actor, squareToMoveTo, true).perform();
					// AIRoutineUtils.moveTo(this.actor, squareToMoveTo);
					if (this.actor.squareGameObjectIsOn == targetSquare)
						targetSquare = null;
				}
			}
		}

		if (state == STATE.GO_TO_BED_AND_GO_TO_SLEEP) {

			goToBedAndSleep();
		}

	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForWildAnimal(actor);
	}

}
