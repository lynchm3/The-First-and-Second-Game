package com.marklynch.tactics.objects.unit.ai.routines;

public class AIRoutineStationary extends AIRoutine {
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
