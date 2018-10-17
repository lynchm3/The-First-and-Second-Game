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

	public float scaleX = 1;
	public float scaleY = 1;

	public float leftHipAngle = 0;
	public float leftKneeAngle = 0;
	public float rightHipAngle = 0;
	public float rightKneeAngle = 0;

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

	public double speed = 1;

	Animation animation;

	public boolean done = false;

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

			this.alpha = performer.getPrimaryAnimation().alpha;
		}
	}

	public void animate(double delta) {

		float angleChange = (float) (speed * delta);

		if (!animation.backwards) {

			animation.torsoAngle = animation.moveTowardsTargetAngleInRadians(animation.torsoAngle, angleChange,
					torsoAngle);

			animation.leftShoulderAngle = animation.moveTowardsTargetAngleInRadians(animation.leftShoulderAngle,
					angleChange, leftShoulderAngle);
			animation.rightShoulderAngle = animation.moveTowardsTargetAngleInRadians(animation.rightShoulderAngle,
					angleChange, rightShoulderAngle);

			animation.leftElbowAngle = animation.moveTowardsTargetAngleInRadians(animation.leftElbowAngle, angleChange,
					leftElbowAngle);
			animation.rightElbowAngle = animation.moveTowardsTargetAngleInRadians(animation.rightElbowAngle,
					angleChange, rightElbowAngle);

			animation.leftHipAngle = animation.moveTowardsTargetAngleInRadians(animation.leftHipAngle, angleChange,
					leftHipAngle);
			animation.rightHipAngle = animation.moveTowardsTargetAngleInRadians(animation.rightHipAngle, angleChange,
					rightHipAngle);

			animation.leftKneeAngle = animation.moveTowardsTargetAngleInRadians(animation.leftKneeAngle, angleChange,
					leftKneeAngle);
			animation.rightKneeAngle = animation.moveTowardsTargetAngleInRadians(animation.rightKneeAngle, angleChange,
					rightKneeAngle);

			if (animation.torsoAngle == torsoAngle //
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

			animation.torsoAngle = animation.moveTowardsTargetAngleInRadians(animation.torsoAngle, angleChange,
					-torsoAngle);

			animation.leftShoulderAngle = animation.moveTowardsTargetAngleInRadians(animation.leftShoulderAngle,
					angleChange, -rightShoulderAngle);
			animation.rightShoulderAngle = animation.moveTowardsTargetAngleInRadians(animation.rightShoulderAngle,
					angleChange, -leftShoulderAngle);

			animation.leftElbowAngle = animation.moveTowardsTargetAngleInRadians(animation.leftElbowAngle, angleChange,
					-rightElbowAngle);
			animation.rightElbowAngle = animation.moveTowardsTargetAngleInRadians(animation.rightElbowAngle,
					angleChange, -leftElbowAngle);

			animation.leftHipAngle = animation.moveTowardsTargetAngleInRadians(animation.leftHipAngle, angleChange,
					-rightHipAngle);
			animation.rightHipAngle = animation.moveTowardsTargetAngleInRadians(animation.rightHipAngle, angleChange,
					-leftHipAngle);

			animation.leftKneeAngle = animation.moveTowardsTargetAngleInRadians(animation.leftKneeAngle, angleChange,
					-rightKneeAngle);
			animation.rightKneeAngle = animation.moveTowardsTargetAngleInRadians(animation.rightKneeAngle, angleChange,
					-leftKneeAngle);

			if (animation.torsoAngle == -torsoAngle //
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

}
