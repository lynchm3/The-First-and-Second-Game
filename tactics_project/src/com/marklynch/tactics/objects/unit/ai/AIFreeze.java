package com.marklynch.tactics.objects.unit.ai;

public class AIFreeze extends AI {
	public final static String[] editableAttributes = { "name" };

	public AIFreeze() {
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
	public AI makeCopy() {
		AIFreeze ai = new AIFreeze();
		ai.name = new String(name);
		return ai;
	}
}
