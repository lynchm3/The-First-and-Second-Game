package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public class AnimationPush extends Animation {

	public GameObject performer;
	public Square targetSquare;

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;

	public AnimationPush(GameObject performer, Square targetSquare, Animation previousAnimation) {
		super();
		this.targetSquare = targetSquare;
		this.performer = performer;
		durationToReach = 400;
		drawWeapon = false;
		if (previousAnimation != null) {
			torsoAngle = previousAnimation.torsoAngle;
			leftShoulderAngle = previousAnimation.leftShoulderAngle;
			rightShoulderAngle = previousAnimation.rightShoulderAngle;
			leftElbowAngle = previousAnimation.leftElbowAngle;
			rightElbowAngle = previousAnimation.rightElbowAngle;
			leftHipAngle = previousAnimation.leftHipAngle;
			rightHipAngle = previousAnimation.rightHipAngle;
			leftKneeAngle = previousAnimation.leftKneeAngle;
			rightKneeAngle = previousAnimation.rightKneeAngle;
		}

		quarterDurationToReach = durationToReach / 4;
		halfDurationToReach = quarterDurationToReach + quarterDurationToReach;
		threeQuarterDurationToReach = halfDurationToReach + quarterDurationToReach;

		backwards = performer.backwards;

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

		} else if (targetSquare.xInGrid - performer.squareGameObjectIsOn.xInGrid > 0) {
			targetRadians = right;
		}

		System.out.println("targetLimbDegrees = " + targetRadians);

		blockAI = true;

		if (blockAI)
			Level.blockingAnimations.add(this);
	}

	float targetRadians = 0;

	@Override
	public void update(double delta) {

		if (completed) {
			return;
		}

		durationSoFar += delta;

		float progress = durationSoFar / durationToReach;

		float durationReamaining = durationToReach - durationSoFar;

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
			completed = true;
			Level.blockingAnimations.remove(this);
			if (performer.primaryAnimation == this)
				performer.primaryAnimation = new AnimationWait(this);
		}

		// If at last square, drop y.
		if (durationSoFar < quarterDurationToReach) {
			leftShoulderAngle = moveTowardsTargetAngleInRadians(leftShoulderAngle, angleChange, targetRadians);
			rightShoulderAngle = moveTowardsTargetAngleInRadians(rightShoulderAngle, angleChange, targetRadians);
		} else {
			// leftShoulderAngle = moveTowardsTargetAngleInRadians(leftShoulderAngle,
			// angleChange, 0);
			// rightShoulderAngle = moveTowardsTargetAngleInRadians(rightShoulderAngle,
			// angleChange, 0);
		}

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
