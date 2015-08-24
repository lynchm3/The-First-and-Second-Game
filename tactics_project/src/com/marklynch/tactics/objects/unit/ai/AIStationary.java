package com.marklynch.tactics.objects.unit.ai;

public class AIStationary extends AI {
	public final static String[] editableAttributes = { "name", };

	public AIStationary() {
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
