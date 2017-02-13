package com.marklynch.ai.routines;

import com.marklynch.ai.utils.AIRoutineUtils;

public class AIRoutineFreeze extends AIRoutineUtils {
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
	public AIRoutineUtils makeCopy() {
		AIRoutineFreeze ai = new AIRoutineFreeze();
		ai.name = new String(name);
		return ai;
	}
}
