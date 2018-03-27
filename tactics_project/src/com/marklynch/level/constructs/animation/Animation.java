package com.marklynch.level.constructs.animation;

public abstract class Animation {
	public boolean blockAI = false;
	public boolean completed = false;
	public float offsetX = 0;
	public float offsetY = 0;

	public float leftShoulderAngle = 0f;
	public float leftElbowAngle = 0f;
	public float rightShoulderAngle = 0f;
	public float rightElbowAngle = 0f;

	public float durationSoFar = 0;
	public float durationToReach = 200;
	public int phase = 0;

	public abstract void update(double delta);

	public abstract void draw1();

	public abstract void draw2();

	public abstract void drawStaticUI();

	boolean backwards = true;

	protected void reverseAnimtion() {

		float temp = rightShoulderAngle;
		rightShoulderAngle = -leftShoulderAngle;
		leftShoulderAngle = -temp;

		temp = rightElbowAngle;
		rightElbowAngle = -leftElbowAngle;
		leftElbowAngle = -temp;

	}

}
