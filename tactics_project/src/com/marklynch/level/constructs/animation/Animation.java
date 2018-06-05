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

	// leg stuff
	public float leftHipAngle = 0;
	public float leftKneeAngle = 0;
	public float rightHipAngle = 0;
	public float rightKneeAngle = 0;

	public float durationSoFar = 0;
	public float durationToReach = 200;
	public int phase = 0;

	public abstract void update(double delta);

	public abstract void draw1();

	public abstract void draw2();

	public abstract void drawStaticUI();

	protected boolean backwards = true;

	public boolean drawEquipped = true;

	// Arrow
	public boolean drawArrowInOffHand = false;
	public boolean drawArrowInMainHand = false;
	public float arrowHandleY = 0;

	// Bow string
	public boolean drawBowString = false;
	public float bowStringHandleY = 0;

	public int boundsX1 = Integer.MAX_VALUE;
	public int boundsY1 = Integer.MAX_VALUE;
	public int boundsX2 = Integer.MAX_VALUE;
	public int boundsY2 = Integer.MAX_VALUE;

	protected void reverseAnimtion() {

		float temp = rightShoulderAngle;
		rightShoulderAngle = -leftShoulderAngle;
		leftShoulderAngle = -temp;

		temp = rightElbowAngle;
		rightElbowAngle = -leftElbowAngle;
		leftElbowAngle = -temp;

		temp = rightHipAngle;
		rightHipAngle = -leftHipAngle;
		leftHipAngle = -temp;

		temp = rightKneeAngle;
		rightKneeAngle = -leftKneeAngle;
		leftKneeAngle = -temp;

	}

}
