package com.marklynch.tactics.objects.unit.ai.routines;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.ai.utils.AIRoutineUtils;

public class AIRoutineForWildAnimal extends AIRoutine {
	Square targetSquare;

	@Override
	public void update() {

		System.out.println("AIRoutineForWildAnimal.update()");

		if (targetSquare == null || Game.level.activeActor.paths.get(targetSquare) == null) {
			System.out.println("square == null || Game.level.activeActor.paths.get(square) == null)");
			targetSquare = AIRoutineUtils.getRandomSquare(10, true);
			System.out.println("square = " + targetSquare);
		}

		if (targetSquare != null) {
			System.out.println("square != null");
			System.out.println(
					"Game.level.activeActor.paths.get(square) = " + Game.level.activeActor.paths.get(targetSquare));
			Square squareToMoveTo = AIRoutineUtils
					.getSquareToMoveAlongPath(Game.level.activeActor.paths.get(targetSquare));
			AIRoutineUtils.moveTo(Game.level.activeActor, squareToMoveTo);
			System.out.println("called move along path");
			if (Game.level.activeActor.squareGameObjectIsOn == targetSquare)
				targetSquare = null;
		}

	}

}
