package com.marklynch.tactics.objects.unit.ai.routines;

public class AIRoutineFreeze extends AIRoutine {
	public final static String[] editableAttributes = { "name" };

	public AIRoutineFreeze() {
		name = this.getClass().getSimpleName();
	}

	@Override
	public boolean move() {
		return false;
	}

	@Override
	public boolean attack() {
		return false;
	}

	@Override
	public AIRoutine makeCopy() {
		AIRoutineFreeze ai = new AIRoutineFreeze();
		ai.name = new String(name);
		return ai;
	}
}
