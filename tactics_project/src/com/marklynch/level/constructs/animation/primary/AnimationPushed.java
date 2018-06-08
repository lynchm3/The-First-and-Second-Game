package com.marklynch.level.constructs.animation.primary;

import com.marklynch.Game;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public class AnimationPushed extends Animation {

	public Square startSquare;
	public Square endSquare;
	public float startOffsetX = 0;
	public float startOffsetY = 0;

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;

	float durationPerSquare = 50;

	// Animation previousAnimation;

	// for show only, walking actor, primary

	public AnimationPushed(GameObject performer, Square startSquare, Square endSquare, Animation oldAnimation) {
		super(performer);
		this.startSquare = startSquare;
		this.endSquare = endSquare;

		float distance = (float) Math.hypot(this.startSquare.xInGrid - this.endSquare.xInGrid,
				this.startSquare.yInGrid - this.endSquare.yInGrid);

		durationToReach = distance * durationPerSquare;

		quarterDurationToReach = durationToReach / 4;
		halfDurationToReach = quarterDurationToReach + quarterDurationToReach;
		threeQuarterDurationToReach = halfDurationToReach + quarterDurationToReach;

		startOffsetX = offsetX = (int) ((this.startSquare.xInGrid - this.endSquare.xInGrid) * Game.SQUARE_WIDTH);
		startOffsetY = offsetY = (int) ((this.startSquare.yInGrid - this.endSquare.yInGrid) * Game.SQUARE_HEIGHT);

		backwards = performer.backwards;
		// -180 to +180 = -3.14 to 3.14

		// targetLimbRadians = (float) Math.atan2(endSquare.yInGrid -
		// startSquare.yInGrid,
		// endSquare.xInGrid - startSquare.xInGrid) + 1.5708f;

		// if (endSquare.yInGrid - startSquare.yInGrid < 0) {
		// targetLimbRadians = -targetLimbRadians;
		// }
		// if (endSquare.xInGrid - startSquare.xInGrid < 0) {
		// targetLimbRadians = -targetLimbRadians;
		// }
		//
		// while (targetLimbRadians > 3.14f) {
		// targetLimbRadians -= 3.14f;
		// }
		//
		// while (targetLimbRadians < -3.14f) {
		// targetLimbRadians += 3.14f;
		// }

		float up = 0f;
		float down = 3.14f;
		float left = -1.7f;
		float right = 1.7f;

		if (endSquare.yInGrid - startSquare.yInGrid < 0) {
			targetRadians = up;
			targetArmRadians = -0.25f;

		} else if (endSquare.yInGrid - startSquare.yInGrid > 0) {
			targetRadians = down;
			targetArmRadians = 0.25f;

		}
		if (endSquare.xInGrid - startSquare.xInGrid < 0) {
			targetRadians = left;
			targetArmRadians = -0.25f;

		} else if (endSquare.xInGrid - startSquare.xInGrid > 0) {
			targetRadians = right;
			targetArmRadians = 0.25f;
		}

		// System.out.println("deltaX = " + deltaX);
		// System.out.println("deltaY = " + deltaY);
		System.out.println("targetLimbDegrees = " + targetRadians);

		blockAI = true;
	}

	float targetRadians = 0;
	float targetArmRadians = 0;

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;
		super.update(delta);

		durationSoFar += delta;

		float progress = durationSoFar / durationToReach;

		float durationReamaining = durationToReach - durationSoFar;

		if (progress >= 1) {
			progress = 1;
		}

		float angleChange = (float) (0.01d * delta);

		torsoAngle = moveTowardsTargetAngleInRadians(torsoAngle, angleChange, targetRadians);

		leftElbowAngle = moveTowardsTargetAngleInRadians(leftElbowAngle, angleChange, 0);
		rightElbowAngle = moveTowardsTargetAngleInRadians(rightElbowAngle, angleChange, 0);

		leftHipAngle = moveTowardsTargetAngleInRadians(leftHipAngle, angleChange, 0);
		rightHipAngle = moveTowardsTargetAngleInRadians(rightHipAngle, angleChange, 0);
		leftKneeAngle = moveTowardsTargetAngleInRadians(leftElbowAngle, angleChange, 0);
		rightKneeAngle = moveTowardsTargetAngleInRadians(leftElbowAngle, angleChange, 0);

		if (progress >= 1) {
			complete();
			// if (performer.getPrimaryAnimation() == this)
			// performer.setPrimaryAnimation(new AnimationWait(performer));
		}

		offsetX = (int) (startOffsetX * (1 - progress));
		offsetY = (int) (startOffsetY * (1 - progress));

		// If at last square, drop y.
		if (durationReamaining <= durationPerSquare) {

			float dropRatio = 1f - (durationReamaining / durationPerSquare);
			offsetY += dropRatio * 32f;
			leftShoulderAngle = moveTowardsTargetAngleInRadians(leftShoulderAngle, angleChange, 0);
			rightShoulderAngle = moveTowardsTargetAngleInRadians(rightShoulderAngle, angleChange, 0);

		} else {

			leftShoulderAngle = moveTowardsTargetAngleInRadians(leftShoulderAngle, angleChange, targetArmRadians);
			rightShoulderAngle = moveTowardsTargetAngleInRadians(rightShoulderAngle, angleChange, targetArmRadians);
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
