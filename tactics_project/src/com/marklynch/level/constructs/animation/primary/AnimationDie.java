package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.objects.GameObject;

public class AnimationDie extends Animation {

	public AnimationDie(GameObject performer) {
		super(performer);
		// durationToReach = 400;
		blockAI = true;
	}

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;

		durationSoFar += delta;
		// double progress = durationSoFar / durationToReach;

		float targetTorsoAngle = 1.57f;// 3.14/2
		float targetRightHip = 0f;
		float targetRightKnee = 0f;
		float targetLeftHip = 0f;
		float targetLeftKnee = 0f;
		float targetOffsetY = 64f;
		float targetOffsetX = 32f;

		if (offsetY == targetOffsetY &&
		//
				offsetX == targetOffsetX &&
				//
				torsoAngle == targetTorsoAngle &&
				//
				leftShoulderAngle == 0 &&
				//
				rightShoulderAngle == 0 &&
				//
				leftElbowAngle == 0 &&
				//
				rightElbowAngle == 0 &&
				//
				leftHipAngle == targetLeftHip &&
				//
				rightHipAngle == targetRightHip &&
				//
				leftKneeAngle == targetLeftKnee &&
				//
				rightKneeAngle == targetRightKnee) {
			complete();
		}

		float offsetYChange = (float) (1d * delta);
		float offsetXChange = (float) (0.5d * delta);
		float torsoAngleChange = (float) (0.1d * delta);
		float angleChange = (float) (0.002d * delta);
		float angleChangeKnee = (float) (0.004d * delta);

		offsetY = moveTowardsTargetAngleInRadians(offsetY, offsetYChange, targetOffsetY);
		offsetX = moveTowardsTargetAngleInRadians(offsetX, offsetYChange, targetOffsetX);
		torsoAngle = moveTowardsTargetAngleInRadians(torsoAngle, torsoAngleChange, targetTorsoAngle);

		// torsoAngle = 0.5f;

		leftShoulderAngle = moveTowardsTargetAngleInRadians(leftShoulderAngle, angleChange, 0);
		rightShoulderAngle = moveTowardsTargetAngleInRadians(rightShoulderAngle, angleChange, 0);
		leftElbowAngle = moveTowardsTargetAngleInRadians(leftElbowAngle, angleChange, 0);
		rightElbowAngle = moveTowardsTargetAngleInRadians(rightElbowAngle, angleChange, 0);

		leftHipAngle = moveTowardsTargetAngleInRadians(leftHipAngle, angleChange, targetLeftHip);
		rightHipAngle = moveTowardsTargetAngleInRadians(rightHipAngle, angleChange, targetRightHip);
		leftKneeAngle = moveTowardsTargetAngleInRadians(leftKneeAngle, angleChangeKnee, targetLeftKnee);
		rightKneeAngle = moveTowardsTargetAngleInRadians(rightKneeAngle, angleChangeKnee, targetRightKnee);

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
