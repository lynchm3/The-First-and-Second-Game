package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;

public class AnimationWait extends Animation {

	public AnimationWait(Animation oldAnimation) {
		super();
		// durationToReach = 400;
		blockAI = false;

		if (oldAnimation != null) {
			this.torsoAngle = oldAnimation.torsoAngle;

			this.leftShoulderAngle = oldAnimation.leftShoulderAngle;
			this.rightShoulderAngle = oldAnimation.rightShoulderAngle;
			this.leftElbowAngle = oldAnimation.leftElbowAngle;
			this.rightElbowAngle = oldAnimation.rightElbowAngle;

			this.leftHipAngle = oldAnimation.leftHipAngle;
			this.rightHipAngle = oldAnimation.rightHipAngle;
			this.leftKneeAngle = oldAnimation.leftKneeAngle;
			this.rightKneeAngle = oldAnimation.rightKneeAngle;
		}
	}

	@Override
	public void update(double delta) {

		if (completed)
			return;

		durationSoFar += delta;
		// double progress = durationSoFar / durationToReach;
		if (leftShoulderAngle == 0 &&
		//
				rightShoulderAngle == 0 &&
				//
				leftElbowAngle == 0 &&
				//
				rightElbowAngle == 0) {
			completed = true;
		}

		float angleChange = (float) (0.002d * delta);

		// torsoAngle = 0.5f;
		torsoAngle = moveTowardsTargetAngleInRadians(torsoAngle, angleChange, 0);

		leftShoulderAngle = moveTowardsTargetAngleInRadians(leftShoulderAngle, angleChange, 0);
		rightShoulderAngle = moveTowardsTargetAngleInRadians(rightShoulderAngle, angleChange, 0);
		leftElbowAngle = moveTowardsTargetAngleInRadians(leftElbowAngle, angleChange, 0);
		rightElbowAngle = moveTowardsTargetAngleInRadians(rightElbowAngle, angleChange, 0);

		leftHipAngle = moveTowardsTargetAngleInRadians(leftHipAngle, angleChange, 0);
		rightHipAngle = moveTowardsTargetAngleInRadians(rightHipAngle, angleChange, 0);
		leftKneeAngle = moveTowardsTargetAngleInRadians(leftKneeAngle, angleChange, 0);
		rightKneeAngle = moveTowardsTargetAngleInRadians(rightKneeAngle, angleChange, 0);

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
