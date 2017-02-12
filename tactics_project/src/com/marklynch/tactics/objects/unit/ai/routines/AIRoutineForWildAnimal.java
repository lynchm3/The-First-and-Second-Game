package com.marklynch.tactics.objects.unit.ai.routines;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.ai.utils.AIRoutineUtils;

public class AIRoutineForWildAnimal extends AIRoutine {
	Square targetSquare;

	final static String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";

	@Override
	public void update() {

		System.out.println("AIRoutineForWildAnimal.update()");

		// Defer to pack
		if (Game.level.activeActor.pack != null) {
			Game.level.activeActor.pack.update(Game.level.activeActor);
			return;
		}

		// Fighting
		if (Game.level.activeActor.hasAttackers()) {
			Game.level.activeActor.activityDescription = ACTIVITY_DESCRIPTION_FIGHTING;
			GameObject target = AIRoutineUtils.getNearestAttacker(Game.level.activeActor.getAttackers());
			boolean attackedTarget = AIRoutineUtils.attackTarget(target);
			if (!attackedTarget)
				AIRoutineUtils.moveTowardsTargetToAttack(target);
			return;
		}

		// Defer to quest
		if (Game.level.activeActor.quest != null) {
			Game.level.activeActor.quest.update(Game.level.activeActor);
			return;
		}

		// Go about ur business...
		if (targetSquare == null || Game.level.activeActor.paths.get(targetSquare) == null) {
			targetSquare = AIRoutineUtils.getRandomSquare(10, true);
		}

		if (targetSquare != null) {
			Square squareToMoveTo = AIRoutineUtils
					.getSquareToMoveAlongPath(Game.level.activeActor.paths.get(targetSquare));
			AIRoutineUtils.moveTo(Game.level.activeActor, squareToMoveTo);
			if (Game.level.activeActor.squareGameObjectIsOn == targetSquare)
				targetSquare = null;
		}

	}

}
