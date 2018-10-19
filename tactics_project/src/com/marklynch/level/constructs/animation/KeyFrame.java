package com.marklynch.level.constructs.animation;

import com.marklynch.objects.GameObject;

public class KeyFrame {
	public float offsetX = 0;
	public float offsetY = 0;

	public float torsoAngle = 0f;
	public float leftShoulderAngle = 0f;
	public float leftElbowAngle = 0f;
	public float rightShoulderAngle = 0f;
	public float rightElbowAngle = 0f;

	public float leftHipAngle = 0;
	public float leftKneeAngle = 0;
	public float rightHipAngle = 0;
	public float rightKneeAngle = 0;

	public float scaleX = 1;
	public float scaleY = 1;

	public float alpha = 1f;

	// Arrow
	public boolean drawArrowInOffHand = false;
	public boolean drawArrowInMainHand = false;
	public float arrowHandleY = 0;

	public boolean drawEquipped = true;

	// Bow string
	public boolean drawBowString = false;
	public float bowStringHandleY = 0;

	public int boundsX1 = -128;
	public int boundsY1 = -128;
	public int boundsX2 = 128;
	public int boundsY2 = 128;
	public boolean drawWeapon = true;

	// Speeds
	// public double speed = 1;
	public double offsetXSpeed = 1;
	public double offsetYSpeed = 1;
	public double torsoAngleSpeed = 1;
	public double leftShoulderAngleSpeed = 1;
	public double leftElbowAngleSpeed = 1;
	public double rightShoulderAngleSpeed = 1;
	public double rightElbowAngleSpeed = 1;
	public double scaleXSpeed = 1;
	public double scaleYSpeed = 1;
	public double leftHipAngleSpeed = 1;
	public double leftKneeAngleSpeed = 1;
	public double rightHipAngleSpeed = 1;
	public double rightKneeAngleSpeed = 1;

	public double boundsX1Speed = 1;
	public double boundsY1Speed = 1;
	public double boundsX2Speed = 1;
	public double boundsY2Speed = 1;

	Animation animation;

	public boolean done = false;
	public boolean normaliseSpeeds = false;

	public KeyFrame(GameObject performer, Animation animation) {
		this.animation = animation;
		if (performer != null && performer.getPrimaryAnimation() != null) {

			this.offsetX = performer.getPrimaryAnimation().offsetX;
			this.offsetY = performer.getPrimaryAnimation().offsetY;

			this.torsoAngle = performer.getPrimaryAnimation().torsoAngle;

			this.leftShoulderAngle = performer.getPrimaryAnimation().leftShoulderAngle;
			this.rightShoulderAngle = performer.getPrimaryAnimation().rightShoulderAngle;

			this.leftElbowAngle = performer.getPrimaryAnimation().leftElbowAngle;
			this.rightElbowAngle = performer.getPrimaryAnimation().rightElbowAngle;

			this.leftHipAngle = performer.getPrimaryAnimation().leftHipAngle;
			this.rightHipAngle = performer.getPrimaryAnimation().rightHipAngle;

			this.leftKneeAngle = performer.getPrimaryAnimation().leftKneeAngle;
			this.rightKneeAngle = performer.getPrimaryAnimation().rightKneeAngle;

			this.scaleX = performer.getPrimaryAnimation().scaleX;
			this.scaleY = performer.getPrimaryAnimation().scaleY;

			this.alpha = performer.getPrimaryAnimation().alpha;
		}
	}

	public void animate(double delta) {

		animation.offsetX = animation.moveTowardsTargetAngleInRadians(animation.offsetX, offsetXSpeed * delta, offsetX);
		animation.offsetY = animation.moveTowardsTargetAngleInRadians(animation.offsetY, offsetYSpeed * delta, offsetY);
		animation.scaleX = animation.moveTowardsTargetAngleInRadians(animation.scaleX, scaleXSpeed * delta, scaleX);
		animation.scaleY = animation.moveTowardsTargetAngleInRadians(animation.scaleY, scaleYSpeed * delta, scaleY);
		animation.boundsX1 = (int) animation.moveTowardsTargetAngleInRadians(animation.boundsX1, boundsX1Speed * delta,
				boundsX1);
		animation.boundsY1 = (int) animation.moveTowardsTargetAngleInRadians(animation.boundsY1, boundsY1Speed * delta,
				boundsY1);
		animation.boundsX2 = (int) animation.moveTowardsTargetAngleInRadians(animation.boundsX2, boundsX2Speed * delta,
				boundsX2);
		animation.boundsY2 = (int) animation.moveTowardsTargetAngleInRadians(animation.boundsY2, boundsY2Speed * delta,
				boundsY2);

		if (!animation.backwards) {

			animation.torsoAngle = animation.moveTowardsTargetAngleInRadians(animation.torsoAngle,
					torsoAngleSpeed * delta, torsoAngle);

			animation.leftShoulderAngle = animation.moveTowardsTargetAngleInRadians(animation.leftShoulderAngle,
					leftShoulderAngleSpeed * delta, leftShoulderAngle);
			animation.rightShoulderAngle = animation.moveTowardsTargetAngleInRadians(animation.rightShoulderAngle,
					rightShoulderAngleSpeed * delta, rightShoulderAngle);

			animation.leftElbowAngle = animation.moveTowardsTargetAngleInRadians(animation.leftElbowAngle,
					leftElbowAngleSpeed * delta, leftElbowAngle);
			animation.rightElbowAngle = animation.moveTowardsTargetAngleInRadians(animation.rightElbowAngle,
					rightElbowAngleSpeed * delta, rightElbowAngle);

			animation.leftHipAngle = animation.moveTowardsTargetAngleInRadians(animation.leftHipAngle,
					leftHipAngleSpeed * delta, leftHipAngle);
			animation.rightHipAngle = animation.moveTowardsTargetAngleInRadians(animation.rightHipAngle,
					rightHipAngleSpeed * delta, rightHipAngle);

			animation.leftKneeAngle = animation.moveTowardsTargetAngleInRadians(animation.leftKneeAngle,
					leftKneeAngleSpeed * delta, leftKneeAngle);
			animation.rightKneeAngle = animation.moveTowardsTargetAngleInRadians(animation.rightKneeAngle,
					rightKneeAngleSpeed * delta, rightKneeAngle);

			if (animation.offsetX == offsetX //
					&& animation.offsetY == offsetY //
					&& animation.scaleX == scaleX //
					&& animation.boundsX1 == boundsX1 //
					&& animation.boundsY1 == boundsY1 //
					&& animation.boundsX2 == boundsX2 //
					&& animation.boundsY2 == boundsY2 //
					&& animation.torsoAngle == torsoAngle //
					&& animation.leftShoulderAngle == leftShoulderAngle//
					&& animation.rightShoulderAngle == rightShoulderAngle//
					&& animation.leftElbowAngle == leftElbowAngle//
					&& animation.rightElbowAngle == rightElbowAngle//
					&& animation.leftHipAngle == leftHipAngle//
					&& animation.rightHipAngle == rightHipAngle//
					&& animation.leftKneeAngle == leftKneeAngle//
					&& animation.rightKneeAngle == rightKneeAngle//
			) {
				done = true;
			}
		} else {// BACKWARDS

			animation.torsoAngle = animation.moveTowardsTargetAngleInRadians(animation.torsoAngle,
					torsoAngleSpeed * delta, -torsoAngle);

			animation.leftShoulderAngle = animation.moveTowardsTargetAngleInRadians(animation.leftShoulderAngle,
					rightShoulderAngleSpeed * delta, -rightShoulderAngle);
			animation.rightShoulderAngle = animation.moveTowardsTargetAngleInRadians(animation.rightShoulderAngle,
					leftShoulderAngleSpeed * delta, -leftShoulderAngle);

			animation.leftElbowAngle = animation.moveTowardsTargetAngleInRadians(animation.leftElbowAngle,
					rightElbowAngleSpeed * delta, -rightElbowAngle);
			animation.rightElbowAngle = animation.moveTowardsTargetAngleInRadians(animation.rightElbowAngle,
					leftElbowAngleSpeed * delta, -leftElbowAngle);

			animation.leftHipAngle = animation.moveTowardsTargetAngleInRadians(animation.leftHipAngle,
					rightHipAngleSpeed * delta, -rightHipAngle);
			animation.rightHipAngle = animation.moveTowardsTargetAngleInRadians(animation.rightHipAngle,
					leftHipAngleSpeed * delta, -leftHipAngle);

			animation.leftKneeAngle = animation.moveTowardsTargetAngleInRadians(animation.leftKneeAngle,
					rightKneeAngleSpeed * delta, -rightKneeAngle);
			animation.rightKneeAngle = animation.moveTowardsTargetAngleInRadians(animation.rightKneeAngle,
					leftKneeAngleSpeed * delta, -leftKneeAngle);

			if (animation.offsetX == offsetX //
					&& animation.offsetY == offsetY//
					&& animation.scaleX == scaleX //
					&& animation.scaleY == scaleY //
					&& animation.boundsX1 == boundsX1 //
					&& animation.boundsY1 == boundsY1 //
					&& animation.boundsX2 == boundsX2 //
					&& animation.boundsY2 == boundsY2 //
					&& animation.torsoAngle == -torsoAngle //
					&& animation.leftShoulderAngle == -rightShoulderAngle//
					&& animation.rightShoulderAngle == -leftShoulderAngle//
					&& animation.leftElbowAngle == -rightElbowAngle//
					&& animation.rightElbowAngle == -leftElbowAngle//
					&& animation.leftHipAngle == -rightHipAngle//
					&& animation.rightHipAngle == -leftHipAngle//
					&& animation.leftKneeAngle == -rightKneeAngle//
					&& animation.rightKneeAngle == -leftKneeAngle//
			) {
				done = true;
			}
		}

	}

	public void setAllSpeeds(double d) {
		offsetXSpeed = offsetYSpeed = torsoAngleSpeed//
				= leftShoulderAngleSpeed = leftElbowAngleSpeed//
						= rightShoulderAngleSpeed = rightElbowAngleSpeed//
								= scaleXSpeed = scaleYSpeed = leftHipAngleSpeed//
										= leftKneeAngleSpeed = rightHipAngleSpeed//
												= rightKneeAngleSpeed = d;

	}

	public void normaliseSpeeds() {
		if (!normaliseSpeeds)
			return;

		double millisToCompleteOffsetX = Math.abs(animation.offsetX - offsetX) / Math.abs(offsetXSpeed);
		double millisToCompleteOffsetY = Math.abs(animation.offsetY - offsetY) / Math.abs(offsetYSpeed);
		double millisToCompletescaleX = Math.abs(animation.scaleX - scaleX) / Math.abs(scaleXSpeed);
		double millisToCompletescaleY = Math.abs(animation.scaleY - scaleY) / Math.abs(scaleYSpeed);
		double millisToCompleteboundsX1 = Math.abs(animation.boundsX1 - boundsX1) / Math.abs(boundsX1Speed);
		double millisToCompleteboundsY1 = Math.abs(animation.boundsY1 - boundsY1) / Math.abs(boundsY1Speed);
		double millisToCompleteboundsX2 = Math.abs(animation.boundsX2 - boundsX2) / Math.abs(boundsX2Speed);
		double millisToCompleteboundsY2 = Math.abs(animation.boundsY2 - boundsY2) / Math.abs(boundsY2Speed);
		double millisToCompletetorsoAngle = Math.abs(animation.torsoAngle - torsoAngle) / Math.abs(torsoAngleSpeed);
		double millisToCompleteleftShoulderAngle = Math.abs(animation.leftShoulderAngle - leftShoulderAngle)
				/ Math.abs(leftShoulderAngleSpeed);
		double millisToCompleterightShoulderAngle = Math.abs(animation.rightShoulderAngle - rightShoulderAngle)
				/ Math.abs(rightShoulderAngleSpeed);
		double millisToCompleteleftElbowAngle = Math.abs(animation.leftElbowAngle - leftElbowAngle)
				/ Math.abs(leftElbowAngleSpeed);
		double millisToCompleterightElbowAngle = Math.abs(animation.rightElbowAngle - rightElbowAngle)
				/ Math.abs(rightElbowAngleSpeed);
		double millisToCompleteleftHipAngle = Math.abs(animation.leftHipAngle - leftHipAngle)
				/ Math.abs(leftHipAngleSpeed);
		double millisToCompleterightHipAngle = Math.abs(animation.leftHipAngle - rightHipAngle)
				/ Math.abs(rightHipAngleSpeed);
		double millisToCompleteleftKneeAngle = Math.abs(animation.leftKneeAngle - leftKneeAngle)
				/ Math.abs(leftKneeAngleSpeed);
		double millisToCompleterightKneeAngle = Math.abs(animation.rightKneeAngle - rightKneeAngle)
				/ Math.abs(rightKneeAngleSpeed);

		double maxMillis = getMax(millisToCompleteOffsetX, millisToCompleteOffsetY, millisToCompletescaleX,
				millisToCompletescaleY, millisToCompleteboundsX1, millisToCompleteboundsY1, millisToCompleteboundsX2,
				millisToCompleteboundsY2, millisToCompletetorsoAngle, millisToCompleteleftShoulderAngle,
				millisToCompleterightShoulderAngle, millisToCompleteleftElbowAngle, millisToCompleterightElbowAngle,
				millisToCompleteleftHipAngle, millisToCompleterightHipAngle, millisToCompleteleftKneeAngle,
				millisToCompleterightKneeAngle);

		offsetXSpeed = offsetXSpeed * maxMillis / millisToCompleteOffsetX;
		offsetYSpeed = offsetYSpeed * maxMillis / millisToCompleteOffsetX;
		scaleXSpeed = scaleXSpeed * maxMillis / millisToCompleteOffsetX;
		scaleYSpeed = scaleYSpeed * maxMillis / millisToCompleteOffsetX;
		boundsX1Speed = boundsX1Speed * maxMillis / millisToCompleteOffsetX;
		boundsY1Speed = boundsY1Speed * maxMillis / millisToCompleteOffsetX;
		boundsX2Speed = boundsX2Speed * maxMillis / millisToCompleteOffsetX;
		boundsY2Speed = boundsY2Speed * maxMillis / millisToCompleteOffsetX;
		torsoAngleSpeed = torsoAngleSpeed * maxMillis / millisToCompleteOffsetX;
		leftShoulderAngleSpeed = leftShoulderAngleSpeed * maxMillis / millisToCompleteOffsetX;
		rightShoulderAngleSpeed = rightShoulderAngleSpeed * maxMillis / millisToCompleteOffsetX;
		leftElbowAngleSpeed = leftElbowAngleSpeed * maxMillis / millisToCompleteOffsetX;
		rightElbowAngleSpeed = rightElbowAngleSpeed * maxMillis / millisToCompleteOffsetX;
		leftHipAngleSpeed = leftHipAngleSpeed * maxMillis / millisToCompleteOffsetX;
		rightHipAngleSpeed = rightHipAngleSpeed * maxMillis / millisToCompleteOffsetX;
		leftKneeAngleSpeed = leftKneeAngleSpeed * maxMillis / millisToCompleteOffsetX;
		rightKneeAngleSpeed = rightKneeAngleSpeed * maxMillis / millisToCompleteOffsetX;

	}

	public double getMax(double... values) {

		if (values == null || values.length == 0)
			return Double.MIN_VALUE;

		double currentMax = values[0];

		for (int i = 1; i < values.length; i++) {
			currentMax = Math.max(values[i - 1], values[i]);
		}

		return currentMax;
	}

}
