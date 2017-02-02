package com.marklynch.tactics.objects.unit.ai.routines;

import com.marklynch.tactics.objects.unit.ai.utils.AIRoutineUtils;

public class AIRoutineStationary extends AIRoutineUtils {
	public final static String[] editableAttributes = { "name", };

	public AIRoutineStationary() {
		name = this.getClass().getSimpleName();
	}

	@Override
	public boolean move() {
		return false;
	}

	@Override
	public boolean attack() {
		return super.attackRandomEnemy();
	}
}
