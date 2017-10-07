package com.marklynch.level.constructs.animation;

public abstract class Animation {
	public boolean completed = false;
	public float offsetX = 0;
	public float offsetY = 0;

	public double durationSoFar = 0;
	public double durationToReach = 200;

	public abstract void update(double delta);

	public abstract void complete();

}
