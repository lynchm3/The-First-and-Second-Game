package com.marklynch.tactics.objects.unit.ai.routines;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.ai.utils.AIRoutineUtils;

public class AIRoutineForWildAnimal extends AIRoutine {
	Square square;

	@Override
	public void update() {

		System.out.println("AIRoutineForWildAnimal.update()");

		if (square == null || Game.level.activeActor.paths.get(square) == null) {
			System.out.println("square == null || Game.level.activeActor.paths.get(square) == null)");
			square = AIRoutineUtils.getRandomSquare(10);
			System.out.println("square = " + square);
		}

		if (square != null) {
			System.out.println("square != null");
			System.out
					.println("Game.level.activeActor.paths.get(square) = " + Game.level.activeActor.paths.get(square));
			Square squareToMoveTo = AIRoutineUtils.getSquareToMoveAlongPath(Game.level.activeActor.paths.get(square));
			AIRoutineUtils.move(Game.level.activeActor, squareToMoveTo);
			System.out.println("called move along path");
		}

	}

}
