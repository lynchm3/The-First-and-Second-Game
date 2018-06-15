package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.objects.GameObject;

public class AnimationWait extends Animation {

	public AnimationWait(GameObject performer) {
		super(performer);
		durationToReach = 400;
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

		// 1
		// float targetTorsoAngle = (float) Math.toRadians(11.4f);
		// float targetLeftHip = (float) Math.toRadians(-19.13f);
		// float targetLeftKnee = (float) Math.toRadians(13.86f);
		// float targetRightHip = (float) Math.toRadians(-33.01f);
		// float targetRightKnee = (float) Math.toRadians(75.21f);

		// 2
		// float targetTorsoAngle = (float) Math.toRadians(8.9f);
		// float targetLeftHip = (float) Math.toRadians(-2f);
		// float targetLeftKnee = (float) Math.toRadians(-1f);
		// float targetRightHip = (float) Math.toRadians(-34f);
		// float targetRightKnee = (float) Math.toRadians(57f);

		// 3
		// float targetTorsoAngle = (float) Math.toRadians(13.35f);
		// float targetLeftHip = (float) Math.toRadians(-3f);
		// float targetLeftKnee = (float) Math.toRadians(22f);
		// float targetRightHip = (float) Math.toRadians(-36f);
		// float targetRightKnee = (float) Math.toRadians(27f);

		// 4
		float targetTorsoAngle = (float) Math.toRadians(7.27f);
		float targetLeftHip = (float) Math.toRadians(-2f);
		float targetLeftKnee = (float) Math.toRadians(42f);
		float targetRightHip = (float) Math.toRadians(-40f);
		float targetRightKnee = (float) Math.toRadians(27f);

		float targetOffsetY = 0f;
		if (performer.hiding) {

			targetLeftHip = -1.1f;
			targetRightHip = -1.1f;

			targetLeftKnee = 2f;
			targetRightKnee = 2f;

			targetOffsetY = 28f;
		}

		if (durationSoFar >= durationToReach &&
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
			runCompletionAlgorightm();
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
}
