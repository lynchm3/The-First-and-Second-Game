package com.marklynch.ai.routines;

import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.units.Actor;

public class AIRoutineForWildAnimal extends AIRoutine {
	Square targetSquare;

	final static String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";

	public AIRoutineForWildAnimal(Actor actor) {
		super(actor);
		aiType = AI_TYPE.ANIMAL;
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

		// Defer to quest
		if (deferToQuest())
			return;

		// Go about ur business...
		if (targetSquare == null || this.actor.getPathTo(targetSquare) == null) {
			targetSquare = AIRoutineUtils.getRandomSquare(0, 10, true);
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

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForWildAnimal(actor);
	}

}
