package com.marklynch.level.constructs.animation;

public abstract class Animation {
	public boolean completed = false;
	public int offsetX = 0;
	public int offsetY = 0;

	public double durationSoFar = 0;
	public double durationToReach = 200;

	public abstract void update(double delta);

}
