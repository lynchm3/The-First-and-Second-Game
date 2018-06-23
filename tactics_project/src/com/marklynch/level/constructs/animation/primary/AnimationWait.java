package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.objects.GameObject;

public class AnimationWait extends Animation {

	public AnimationWait(GameObject performer) {
		super(performer, performer);
		if (!runAnimation)
			return;
		durationToReachMillis = 400;
		blockAI = false;
		// if (performer == Level.player)
		// blockAI = true;
	}

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;
		super.update(delta);

		durationSoFar += delta;
		// double progress = durationSoFar / durationToReach;
		float targetRightHip = 0f;
		float targetRightKnee = 0f;
		float targetLeftHip = 0f;
		float targetLeftKnee = 0f;
		float targetTorsoAngle = 0f;

		float targetOffsetY = 0f;
		if (performer.hiding) {

			targetLeftHip = -1.1f;
			targetRightHip = -1.1f;

			targetLeftKnee = 2f;
			targetRightKnee = 2f;

			targetOffsetY = 28f;
		}

		if (durationSoFar >= durationToReachMillis &&
		//
				offsetY == targetOffsetY &&
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
			runCompletionAlgorightm(true);
		}

		float offsetYChange = (float) (0.05d * delta);
		float angleChange = (float) (0.002d * delta);
		float angleChangeKnee = (float) (0.004d * delta);

		offsetY = moveTowardsTargetAngleInRadians(offsetY, offsetYChange, targetOffsetY);

		// torsoAngle = 0.5f;
		torsoAngle = moveTowardsTargetAngleInRadians(torsoAngle, angleChange, targetTorsoAngle);

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

	@Override
	public void draw3() {
		// TODO Auto-generated method stub

	}
}
