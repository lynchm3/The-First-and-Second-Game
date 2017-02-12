package com.marklynch.tactics.objects.unit.ai.routines;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.ai.utils.AIRoutineUtils;

public class AIRoutineForWildAnimal extends AIRoutine {
	Square targetSquare;

	@Override
	public void update() {

		System.out.println("AIRoutineForWildAnimal.update()");

		// SO... Fight or run when attacked
		if (Game.level.activeActor.pack != null) {
			Game.level.activeActor.pack.update(Game.level.activeActor);
			return;
		}

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
