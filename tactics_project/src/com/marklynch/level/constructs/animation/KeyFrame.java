package com.marklynch.level.constructs.animation;

import com.marklynch.level.Level;
import com.marklynch.objects.GameObject;

public class KeyFrame {

	public double keyFrameTimeMillis = 0;

	public double offsetX = 0;
	public double offsetY = 0;

	public double torsoAngle = 0f;
	public double leftShoulderAngle = 0f;
	public double leftElbowAngle = 0f;
	public double rightShoulderAngle = 0f;
	public double rightElbowAngle = 0f;

	public double leftHipAngle = 0;
	public double leftKneeAngle = 0;
	public double rightHipAngle = 0;
	public double rightKneeAngle = 0;

	public double scaleX = 1;
	public double scaleY = 1;

	public double alpha = 1f;

	// Arrow
	public boolean drawArrowInOffHand = false;
	public boolean drawArrowInMainHand = false;
	public double arrowHandleY = 0;

	public boolean drawEquipped = true;

	// Bow string
	public boolean drawBowString = false;
	public double bowStringHandleY = 0;

	public int boundsX1 = -128;
	public int boundsY1 = -128;
	public int boundsX2 = 128;
	public int boundsY2 = 128;
	public double headToToeOffset = 0f;

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

	public double headToToeOffsetSpeed = 1;

	Animation animation;
	GameObject performer;

	public boolean done = false;
	public boolean normaliseSpeeds = false;

	public KeyFrame(GameObject performer, Animation animation) {
		this.animation = animation;
		this.performer = performer;
		copyPositions();

	}

	public void copyPositions() {

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

			this.headToToeOffset = performer.getPrimaryAnimation().headToToeOffset;

			this.alpha = performer.getPrimaryAnimation().alpha;
		}
	}

	public void animate(double delta) {

		if (performer == Level.player) {
			System.out.println("animation.headToToeOffset = " + animation.headToToeOffset);
			System.out.println("headToToeOffsetSpeed = " + headToToeOffsetSpeed);
			System.out.println("headToToeOffsetSpeed * delta = " + (headToToeOffsetSpeed * delta));
			System.out.println("headToToeOffset = " + headToToeOffset);
		}

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
		animation.headToToeOffset = animation.moveTowardsTargetAngleInRadians(animation.headToToeOffset,
				headToToeOffsetSpeed * delta, headToToeOffset);

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
					&& animation.headToToeOffset == headToToeOffset//
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
					&& animation.headToToeOffset == headToToeOffset//
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

	public void createSpeeds() {
		if (!normaliseSpeeds)
			return;

		double distanceToCoverOffsetX = Math.abs(animation.offsetX - offsetX);
		double distanceToCoverOffsetY = Math.abs(animation.offsetY - offsetY);
		double distanceToCoverscaleX = Math.abs(animation.scaleX - scaleX);
		double distanceToCoverscaleY = Math.abs(animation.scaleY - scaleY);
		double distanceToCoverboundsX1 = Math.abs(animation.boundsX1 - boundsX1);
		double distanceToCoverboundsY1 = Math.abs(animation.boundsY1 - boundsY1);
		double distanceToCoverboundsX2 = Math.abs(animation.boundsX2 - boundsX2);
		double distanceToCoverboundsY2 = Math.abs(animation.boundsY2 - boundsY2);
		double distanceToCoverHeadToToeOffset = Math.abs(animation.headToToeOffset - headToToeOffset);

		double distanceToCovertorsoAngle;
		double distanceToCoverleftShoulderAngle;
		double distanceToCoverrightShoulderAngle;
		double distanceToCoverleftElbowAngle;
		double distanceToCoverrightElbowAngle;
		double distanceToCoverleftHipAngle;
		double distanceToCoverrightHipAngle;
		double distanceToCoverleftKneeAngle;
		double distanceToCoverrightKneeAngle;

		if (!animation.backwards) {
			distanceToCovertorsoAngle = Math.abs(animation.torsoAngle - torsoAngle);
			distanceToCoverleftShoulderAngle = Math.abs(animation.leftShoulderAngle - leftShoulderAngle);
			distanceToCoverrightShoulderAngle = Math.abs(animation.rightShoulderAngle - rightShoulderAngle);
			distanceToCoverleftElbowAngle = Math.abs(animation.leftElbowAngle - leftElbowAngle);
			distanceToCoverrightElbowAngle = Math.abs(animation.rightElbowAngle - rightElbowAngle);
			distanceToCoverleftHipAngle = Math.abs(animation.leftHipAngle - leftHipAngle);
			distanceToCoverrightHipAngle = Math.abs(animation.rightHipAngle - rightHipAngle);
			distanceToCoverleftKneeAngle = Math.abs(animation.leftKneeAngle - leftKneeAngle);
			distanceToCoverrightKneeAngle = Math.abs(animation.rightKneeAngle - rightKneeAngle);
		} else {
			distanceToCovertorsoAngle = Math.abs(animation.torsoAngle + torsoAngle);
			distanceToCoverleftShoulderAngle = Math.abs(animation.leftShoulderAngle + rightShoulderAngle);
			distanceToCoverrightShoulderAngle = Math.abs(animation.rightShoulderAngle + leftShoulderAngle);
			distanceToCoverleftElbowAngle = Math.abs(animation.leftElbowAngle + rightElbowAngle);
			distanceToCoverrightElbowAngle = Math.abs(animation.rightElbowAngle + leftElbowAngle);
			distanceToCoverleftHipAngle = Math.abs(animation.leftHipAngle + rightHipAngle);
			distanceToCoverrightHipAngle = Math.abs(animation.rightHipAngle + leftHipAngle);
			distanceToCoverleftKneeAngle = Math.abs(animation.leftKneeAngle + rightKneeAngle);
			distanceToCoverrightKneeAngle = Math.abs(animation.rightKneeAngle + leftKneeAngle);

		}

		offsetXSpeed = distanceToCoverOffsetX / keyFrameTimeMillis;
		offsetYSpeed = distanceToCoverOffsetY / keyFrameTimeMillis;
		scaleXSpeed = distanceToCoverscaleX / keyFrameTimeMillis;
		scaleYSpeed = distanceToCoverscaleY / keyFrameTimeMillis;
		boundsX1Speed = distanceToCoverboundsX1 / keyFrameTimeMillis;
		boundsY1Speed = distanceToCoverboundsY1 / keyFrameTimeMillis;
		boundsX2Speed = distanceToCoverboundsX2 / keyFrameTimeMillis;
		boundsY2Speed = distanceToCoverboundsY2 / keyFrameTimeMillis;
		torsoAngleSpeed = distanceToCovertorsoAngle / keyFrameTimeMillis;
		headToToeOffsetSpeed = distanceToCoverHeadToToeOffset / keyFrameTimeMillis;

		if (!animation.backwards) {
			leftShoulderAngleSpeed = distanceToCoverleftShoulderAngle / keyFrameTimeMillis;
			rightShoulderAngleSpeed = distanceToCoverrightShoulderAngle / keyFrameTimeMillis;
			leftElbowAngleSpeed = distanceToCoverleftElbowAngle / keyFrameTimeMillis;
			rightElbowAngleSpeed = distanceToCoverrightElbowAngle / keyFrameTimeMillis;
			leftHipAngleSpeed = distanceToCoverleftHipAngle / keyFrameTimeMillis;
			rightHipAngleSpeed = distanceToCoverrightHipAngle / keyFrameTimeMillis;
			leftKneeAngleSpeed = distanceToCoverleftKneeAngle / keyFrameTimeMillis;
			rightKneeAngleSpeed = distanceToCoverrightKneeAngle / keyFrameTimeMillis;
		} else {
			rightShoulderAngleSpeed = distanceToCoverleftShoulderAngle / keyFrameTimeMillis;
			leftShoulderAngleSpeed = distanceToCoverrightShoulderAngle / keyFrameTimeMillis;
			rightElbowAngleSpeed = distanceToCoverleftElbowAngle / keyFrameTimeMillis;
			leftElbowAngleSpeed = distanceToCoverrightElbowAngle / keyFrameTimeMillis;
			rightHipAngleSpeed = distanceToCoverleftHipAngle / keyFrameTimeMillis;
			leftHipAngleSpeed = distanceToCoverrightHipAngle / keyFrameTimeMillis;
			rightKneeAngleSpeed = distanceToCoverleftKneeAngle / keyFrameTimeMillis;
			leftKneeAngleSpeed = distanceToCoverrightKneeAngle / keyFrameTimeMillis;
		}

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
		double millisToCompleterightHipAngle = Math.abs(animation.rightHipAngle - rightHipAngle)
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

		if (millisToCompleteOffsetX != 0)
			offsetXSpeed = offsetXSpeed * millisToCompleteOffsetX / maxMillis;
		if (millisToCompleteOffsetY != 0)
			offsetYSpeed = offsetYSpeed * millisToCompleteOffsetY / maxMillis;
		if (millisToCompletescaleX != 0)
			scaleXSpeed = scaleXSpeed * millisToCompletescaleX / maxMillis;
		if (millisToCompletescaleY != 0)
			scaleYSpeed = scaleYSpeed * millisToCompletescaleY / maxMillis;
		if (millisToCompleteboundsX1 != 0)
			boundsX1Speed = boundsX1Speed * millisToCompleteboundsX1 / maxMillis;
		if (millisToCompleteboundsY1 != 0)
			boundsY1Speed = boundsY1Speed * millisToCompleteboundsY1 / maxMillis;
		if (millisToCompleteboundsX2 != 0)
			boundsX2Speed = boundsX2Speed * millisToCompleteboundsX2 / maxMillis;
		if (millisToCompleteboundsY2 != 0)
			boundsY2Speed = boundsY2Speed * millisToCompleteboundsY2 / maxMillis;
		if (millisToCompletetorsoAngle != 0)
			torsoAngleSpeed = torsoAngleSpeed * millisToCompletetorsoAngle / maxMillis;
		if (millisToCompleteleftShoulderAngle != 0)
			leftShoulderAngleSpeed = leftShoulderAngleSpeed * millisToCompleteleftShoulderAngle / maxMillis;
		if (millisToCompleterightShoulderAngle != 0)
			rightShoulderAngleSpeed = rightShoulderAngleSpeed * millisToCompleterightShoulderAngle / maxMillis;
		if (millisToCompleteleftElbowAngle != 0)
			leftElbowAngleSpeed = leftElbowAngleSpeed * millisToCompleteleftElbowAngle / maxMillis;
		if (millisToCompleterightElbowAngle != 0)
			rightElbowAngleSpeed = rightElbowAngleSpeed * millisToCompleterightElbowAngle / maxMillis;
		if (millisToCompleteleftHipAngle != 0)
			leftHipAngleSpeed = leftHipAngleSpeed * millisToCompleteleftHipAngle / maxMillis;
		if (millisToCompleterightHipAngle != 0)
			rightHipAngleSpeed = rightHipAngleSpeed * millisToCompleterightHipAngle / maxMillis;
		if (millisToCompleteleftKneeAngle != 0)
			leftKneeAngleSpeed = leftKneeAngleSpeed * millisToCompleteleftKneeAngle / maxMillis;
		if (millisToCompleterightKneeAngle != 0)
			rightKneeAngleSpeed = rightKneeAngleSpeed * millisToCompleterightKneeAngle / maxMillis;

		millisToCompleteOffsetX = Math.abs(animation.offsetX - offsetX) / Math.abs(offsetXSpeed);
		millisToCompleteOffsetY = Math.abs(animation.offsetY - offsetY) / Math.abs(offsetYSpeed);
		millisToCompletescaleX = Math.abs(animation.scaleX - scaleX) / Math.abs(scaleXSpeed);
		millisToCompletescaleY = Math.abs(animation.scaleY - scaleY) / Math.abs(scaleYSpeed);
		millisToCompleteboundsX1 = Math.abs(animation.boundsX1 - boundsX1) / Math.abs(boundsX1Speed);
		millisToCompleteboundsY1 = Math.abs(animation.boundsY1 - boundsY1) / Math.abs(boundsY1Speed);
		millisToCompleteboundsX2 = Math.abs(animation.boundsX2 - boundsX2) / Math.abs(boundsX2Speed);
		millisToCompleteboundsY2 = Math.abs(animation.boundsY2 - boundsY2) / Math.abs(boundsY2Speed);
		millisToCompletetorsoAngle = Math.abs(animation.torsoAngle - torsoAngle) / Math.abs(torsoAngleSpeed);
		millisToCompleteleftShoulderAngle = Math.abs(animation.leftShoulderAngle - leftShoulderAngle)
				/ Math.abs(leftShoulderAngleSpeed);
		millisToCompleterightShoulderAngle = Math.abs(animation.rightShoulderAngle - rightShoulderAngle)
				/ Math.abs(rightShoulderAngleSpeed);
		millisToCompleteleftElbowAngle = Math.abs(animation.leftElbowAngle - leftElbowAngle)
				/ Math.abs(leftElbowAngleSpeed);
		millisToCompleterightElbowAngle = Math.abs(animation.rightElbowAngle - rightElbowAngle)
				/ Math.abs(rightElbowAngleSpeed);
		millisToCompleteleftHipAngle = Math.abs(animation.leftHipAngle - leftHipAngle) / Math.abs(leftHipAngleSpeed);
		millisToCompleterightHipAngle = Math.abs(animation.leftHipAngle - rightHipAngle) / Math.abs(rightHipAngleSpeed);
		millisToCompleteleftKneeAngle = Math.abs(animation.leftKneeAngle - leftKneeAngle)
				/ Math.abs(leftKneeAngleSpeed);
		millisToCompleterightKneeAngle = Math.abs(animation.rightKneeAngle - rightKneeAngle)
				/ Math.abs(rightKneeAngleSpeed);

	}

	public double getMax(double... values) {

		if (values == null || values.length == 0)
			return Double.MIN_VALUE;

		double currentMax = values[0];

		for (int i = 1; i < values.length; i++) {
			currentMax = Math.max(currentMax, values[i]);
		}

		return currentMax;
	}

}
