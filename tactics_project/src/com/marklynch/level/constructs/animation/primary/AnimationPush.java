package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public class AnimationPush extends Animation {

	public Square targetSquare;

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;

	public AnimationPush(GameObject performer, Square targetSquare, Animation oldAnimation) {
		super(performer);
		this.targetSquare = targetSquare;
		durationToReachMillis = 400;

		quarterDurationToReach = durationToReachMillis / 4;
		halfDurationToReach = quarterDurationToReach + quarterDurationToReach;
		threeQuarterDurationToReach = halfDurationToReach + quarterDurationToReach;

		float down = 0f;
		float up = 3.14f;
		float right = -1.5f;
		float left = 1.5f;

		if (targetSquare.yInGrid - performer.squareGameObjectIsOn.yInGrid < 0) {
			targetRadians = up;

		} else if (targetSquare.yInGrid - performer.squareGameObjectIsOn.yInGrid > 0) {
			targetRadians = down;

		}
		if (targetSquare.xInGrid - performer.squareGameObjectIsOn.xInGrid < 0) {
			targetRadians = left;
			performer.backwards = true;

		} else if (targetSquare.xInGrid - performer.squareGameObjectIsOn.xInGrid > 0) {
			targetRadians = right;
			performer.backwards = false;
		}
		backwards = performer.backwards;

		blockAI = true;
	}

	float targetRadians = 0;

	@Override
	public void update(double delta) {

		if (getCompleted()) {
			return;
		}
		super.update(delta);

		durationSoFar += delta;

		float progress = durationSoFar / durationToReachMillis;

		if (progress >= 1) {
			progress = 1;
		}

		float angleChange = (float) (0.02d * delta);

		torsoAngle = moveTowardsTargetAngleInRadians(torsoAngle, angleChange, 0);
		leftElbowAngle = moveTowardsTargetAngleInRadians(leftElbowAngle, angleChange, 0);
		rightElbowAngle = moveTowardsTargetAngleInRadians(rightElbowAngle, angleChange, 0);
		leftHipAngle = moveTowardsTargetAngleInRadians(leftHipAngle, angleChange, 0);
		rightHipAngle = moveTowardsTargetAngleInRadians(rightHipAngle, angleChange, 0);
		leftKneeAngle = moveTowardsTargetAngleInRadians(leftElbowAngle, angleChange, 0);
		rightKneeAngle = moveTowardsTargetAngleInRadians(leftElbowAngle, angleChange, 0);

		if (progress >= 1) {
			runCompletionAlgorightm();
			// if (performer.getPrimaryAnimation() == this)
			// performer.setPrimaryAnimation(new AnimationWait(performer));
		}

		// If at last square, drop y.
		if (durationSoFar < quarterDurationToReach) {
			if (!backwards) {
				leftShoulderAngle = moveTowardsTargetAngleInRadians(leftShoulderAngle, angleChange, targetRadians);
			} else {
				rightShoulderAngle = moveTowardsTargetAngleInRadians(rightShoulderAngle, angleChange, targetRadians);
			}
		} else {
			// leftShoulderAngle = moveTowardsTargetAngleInRadians(leftShoulderAngle,
			// angleChange, 0);
			// rightShoulderAngle = moveTowardsTargetAngleInRadians(rightShoulderAngle,
			// angleChange, 0);
		}

		// if (backwards)
		// reverseAnimation();

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
