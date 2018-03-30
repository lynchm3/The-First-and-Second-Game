package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;

public class AnimationWait extends Animation {

	public AnimationWait(Animation oldAnimation) {
		super();
		durationToReach = 0;
		blockAI = false;

		if (oldAnimation != null) {
			this.leftShoulderAngle = oldAnimation.leftShoulderAngle;
			this.rightShoulderAngle = oldAnimation.rightShoulderAngle;
			this.leftElbowAngle = oldAnimation.leftElbowAngle;
			this.rightElbowAngle = oldAnimation.rightElbowAngle;
		}
	}

	@Override
	public void update(double delta) {

		if (completed)
			return;

		// durationSoFar += delta;
		// double progress = durationSoFar / durationToReach;
		// if (progress >= 1) {
		// completed = true;
		// }

		float angleChange = (float) (0.002d * delta);

		leftShoulderAngle = moveTowardsZero(leftShoulderAngle, angleChange);
		rightShoulderAngle = moveTowardsZero(rightShoulderAngle, angleChange);
		leftElbowAngle = moveTowardsZero(leftElbowAngle, angleChange);
		rightElbowAngle = moveTowardsZero(rightElbowAngle, angleChange);

		if (leftElbowAngle == 0 && rightShoulderAngle == 0 && leftElbowAngle == 0 && rightElbowAngle == 0)
			completed = true;

	}

	private float moveTowardsZero(float angleToChange, float angleChange) {
		if (angleToChange == 0) {

		} else if (Math.abs(angleToChange) < angleChange) {
			angleToChange = 0;
		} else if (angleToChange > 0) {
			if (angleToChange < angleChange) {
				angleToChange = 0;
			} else {
				angleToChange -= angleChange;
			}
		} else if (angleToChange < 0) {
			if (angleToChange > angleChange) {
				angleToChange = 0;
			} else {
				angleToChange += angleChange;
			}
		}

		return angleToChange;

	}

	@Override
	public void draw2() {

	}

	@Override
	public void draw1() {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawStaticUI() {
	}
}
