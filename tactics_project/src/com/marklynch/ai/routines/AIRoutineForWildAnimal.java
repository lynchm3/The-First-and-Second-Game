package com.marklynch.ai.routines;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.ActionMove;

public class AIRoutineForWildAnimal extends AIRoutine {
	Square targetSquare;

	final static String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";

	@Override
	public void update() {

		// 1. Fighting
		if (Game.level.activeActor.hasAttackers()) {
			Game.level.activeActor.activityDescription = ACTIVITY_DESCRIPTION_FIGHTING;
			GameObject target = AIRoutineUtils.getNearestAttacker(Game.level.activeActor.getAttackers());
			boolean attackedTarget = false;
			if (target != null) {
				attackedTarget = AIRoutineUtils.attackTarget(target);
				if (!attackedTarget)
					AIRoutineUtils.moveTowardsTargetToAttack(target);
			}
			return;
		}

		// If not leader defer to pack
		if (Game.level.activeActor.group != null
				&& Game.level.activeActor != Game.level.activeActor.group.getLeader()) {
			if (Game.level.activeActor.group.update(Game.level.activeActor)) {
				return;
			}
		}

		// Defer to quest
		if (Game.level.activeActor.quest != null) {
			Game.level.activeActor.quest.update(Game.level.activeActor);
			return;
		}

		// Go about ur business...
		if (targetSquare == null || Game.level.activeActor.getPathTo(targetSquare) == null) {
			targetSquare = AIRoutineUtils.getRandomSquare(10, true);
		}

		if (targetSquare != null) {
			Square squareToMoveTo = AIRoutineUtils
					.getSquareToMoveAlongPath(Game.level.activeActor.getPathTo(targetSquare));
			new ActionMove(Game.level.activeActor, squareToMoveTo).perform();
			// AIRoutineUtils.moveTo(Game.level.activeActor, squareToMoveTo);
			if (Game.level.activeActor.squareGameObjectIsOn == targetSquare)
				targetSquare = null;
		}

	}

}
