package com.marklynch.level.constructs.animation;

import com.marklynch.level.Level;
import com.marklynch.objects.GameObject;

public class KeyFrame {

	public float keyFrameTimeMillis = 0;

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
	public float headToToeOffset = 0f;

	public boolean drawWeapon = true;

	// Speeds
	// public double speed = 1;
	public float offsetXSpeed = 1;
	public float offsetYSpeed = 1;
	public float torsoAngleSpeed = 1;
	public float leftShoulderAngleSpeed = 1;
	public float leftElbowAngleSpeed = 1;
	public float rightShoulderAngleSpeed = 1;
	public float rightElbowAngleSpeed = 1;
	public float scaleXSpeed = 1;
	public float scaleYSpeed = 1;
	public float leftHipAngleSpeed = 1;
	public float leftKneeAngleSpeed = 1;
	public float rightHipAngleSpeed = 1;
	public float rightKneeAngleSpeed = 1;

	public float boundsX1Speed = 1;
	public float boundsY1Speed = 1;
	public float boundsX2Speed = 1;
	public float boundsY2Speed = 1;

	public float headToToeOffsetSpeed = 1;

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

			if (performer == Level.player) {
				System.out.println("============================================================");
				System.out.println("animation.offsetX = " + animation.offsetX);
				System.out.println("offsetX = " + offsetX);
				System.out.println("animation.offsetY = " + animation.offsetY);
				System.out.println("offsetY = " + offsetY);
				System.out.println("animation.scaleX = " + animation.scaleX);
				System.out.println("scaleX = " + scaleX);
				System.out.println("animation.boundsX1 = " + animation.boundsX1);
				System.out.println("boundsX1 = " + boundsX1);
				System.out.println("animation.boundsY1 = " + animation.boundsY1);
				System.out.println("boundsY1 = " + boundsY1);
				System.out.println("animation.boundsX2 = " + animation.boundsX2);
				System.out.println("boundsX2 = " + boundsX2);
				System.out.println("animation.boundsY2 = " + animation.boundsY2);
				System.out.println("offsetY = " + boundsY2);
				System.out.println("animation.headToToeOffset = " + animation.headToToeOffset);
				System.out.println("headToToeOffset = " + headToToeOffset);
				System.out.println("animation.torsoAngle = " + animation.torsoAngle);
				System.out.println("torsoAngle = " + torsoAngle);
				System.out.println("animation.leftShoulderAngle = " + animation.leftShoulderAngle);
				System.out.println("leftShoulderAngle = " + leftShoulderAngle);
				System.out.println("animation.rightShoulderAngle = " + animation.rightShoulderAngle);
				System.out.println("rightShoulderAngle = " + rightShoulderAngle);
				System.out.println("animation.leftElbowAngle = " + animation.leftElbowAngle);
				System.out.println("leftElbowAngle = " + leftElbowAngle);
				System.out.println("animation.rightElbowAngle = " + animation.rightElbowAngle);
				System.out.println("rightElbowAngle = " + rightElbowAngle);
				System.out.println("animation.leftHipAngle = " + animation.leftHipAngle);
				System.out.println("leftHipAngle = " + leftHipAngle);
				System.out.println("animation.rightHipAngle = " + animation.rightHipAngle);
				System.out.println("rightHipAngle = " + rightHipAngle);
				System.out.println("animation.leftKneeAngle = " + animation.leftKneeAngle);
				System.out.println("leftKneeAngle = " + leftKneeAngle);
				System.out.println("animation.rightKneeAngle = " + animation.rightKneeAngle);
				System.out.println("rightKneeAngle = " + rightKneeAngle);

				System.out.println("" + (animation.offsetX == offsetX));
				System.out.println("" + (animation.offsetY == offsetY));
				System.out.println("" + (animation.scaleX == scaleX));
				System.out.println("" + (animation.boundsX1 == boundsX1));
				System.out.println("" + (animation.boundsY1 == boundsY1));
				System.out.println("" + (animation.boundsX2 == boundsX2));
				System.out.println("" + (animation.boundsY2 == boundsY2));
				System.out.println("" + (animation.headToToeOffset == headToToeOffset));
				System.out.println("" + (animation.torsoAngle == torsoAngle));
				System.out.println("" + (animation.leftShoulderAngle == leftShoulderAngle));
				System.out.println("" + (animation.rightShoulderAngle == rightShoulderAngle));
				System.out.println("" + (animation.leftElbowAngle == leftElbowAngle));
				System.out.println("" + (animation.rightElbowAngle == rightElbowAngle));
				System.out.println("" + (animation.leftHipAngle == leftHipAngle));
				System.out.println("" + (animation.rightHipAngle == rightHipAngle));
				System.out.println("" + (animation.leftKneeAngle == leftKneeAngle));
				System.out.println("" + (animation.rightKneeAngle == rightKneeAngle));
			}

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

				if (performer == Level.player) {
					System.out.println("SETTING DONE TO TRUE!");
				}
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

	public void setAllSpeeds(float d) {
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

		float distanceToCoverOffsetX = Math.abs(animation.offsetX - offsetX);
		float distanceToCoverOffsetY = Math.abs(animation.offsetY - offsetY);
		float distanceToCoverscaleX = Math.abs(animation.scaleX - scaleX);
		float distanceToCoverscaleY = Math.abs(animation.scaleY - scaleY);
		float distanceToCoverboundsX1 = Math.abs(animation.boundsX1 - boundsX1);
		float distanceToCoverboundsY1 = Math.abs(animation.boundsY1 - boundsY1);
		float distanceToCoverboundsX2 = Math.abs(animation.boundsX2 - boundsX2);
		float distanceToCoverboundsY2 = Math.abs(animation.boundsY2 - boundsY2);
		float distanceToCoverHeadToToeOffset = Math.abs(animation.headToToeOffset - headToToeOffset);

		float distanceToCovertorsoAngle;
		float distanceToCoverleftShoulderAngle;
		float distanceToCoverrightShoulderAngle;
		float distanceToCoverleftElbowAngle;
		float distanceToCoverrightElbowAngle;
		float distanceToCoverleftHipAngle;
		float distanceToCoverrightHipAngle;
		float distanceToCoverleftKneeAngle;
		float distanceToCoverrightKneeAngle;

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

		float millisToCompleteOffsetX = Math.abs(animation.offsetX - offsetX) / Math.abs(offsetXSpeed);
		float millisToCompleteOffsetY = Math.abs(animation.offsetY - offsetY) / Math.abs(offsetYSpeed);
		float millisToCompletescaleX = Math.abs(animation.scaleX - scaleX) / Math.abs(scaleXSpeed);
		float millisToCompletescaleY = Math.abs(animation.scaleY - scaleY) / Math.abs(scaleYSpeed);
		float millisToCompleteboundsX1 = Math.abs(animation.boundsX1 - boundsX1) / Math.abs(boundsX1Speed);
		float millisToCompleteboundsY1 = Math.abs(animation.boundsY1 - boundsY1) / Math.abs(boundsY1Speed);
		float millisToCompleteboundsX2 = Math.abs(animation.boundsX2 - boundsX2) / Math.abs(boundsX2Speed);
		float millisToCompleteboundsY2 = Math.abs(animation.boundsY2 - boundsY2) / Math.abs(boundsY2Speed);
		float millisToCompletetorsoAngle = Math.abs(animation.torsoAngle - torsoAngle) / Math.abs(torsoAngleSpeed);
		float millisToCompleteleftShoulderAngle = Math.abs(animation.leftShoulderAngle - leftShoulderAngle)
				/ Math.abs(leftShoulderAngleSpeed);
		float millisToCompleterightShoulderAngle = Math.abs(animation.rightShoulderAngle - rightShoulderAngle)
				/ Math.abs(rightShoulderAngleSpeed);
		float millisToCompleteleftElbowAngle = Math.abs(animation.leftElbowAngle - leftElbowAngle)
				/ Math.abs(leftElbowAngleSpeed);
		float millisToCompleterightElbowAngle = Math.abs(animation.rightElbowAngle - rightElbowAngle)
				/ Math.abs(rightElbowAngleSpeed);
		float millisToCompleteleftHipAngle = Math.abs(animation.leftHipAngle - leftHipAngle)
				/ Math.abs(leftHipAngleSpeed);
		float millisToCompleterightHipAngle = Math.abs(animation.rightHipAngle - rightHipAngle)
				/ Math.abs(rightHipAngleSpeed);
		float millisToCompleteleftKneeAngle = Math.abs(animation.leftKneeAngle - leftKneeAngle)
				/ Math.abs(leftKneeAngleSpeed);
		float millisToCompleterightKneeAngle = Math.abs(animation.rightKneeAngle - rightKneeAngle)
				/ Math.abs(rightKneeAngleSpeed);

		float maxMillis = getMax(millisToCompleteOffsetX, millisToCompleteOffsetY, millisToCompletescaleX,
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

	public float getMax(float... values) {

		if (values == null || values.length == 0)
			return Float.MIN_VALUE;

		float currentMax = values[0];

		for (int i = 1; i < values.length; i++) {
			currentMax = Math.max(currentMax, values[i]);
		}

		return currentMax;
	}

}
