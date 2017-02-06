package com.marklynch.tactics.objects.unit.ai.routines;

import com.marklynch.tactics.objects.unit.ai.utils.AIRoutineUtils;

public class AIRoutineForWildAnimal extends AIRoutine {
	@Override
	public void update() {

		System.out.println("AIRoutineForWildAnimal.update()");

		AIRoutineUtils.moveToRandomSquare();

	}

}
