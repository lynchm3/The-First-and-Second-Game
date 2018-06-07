package com.marklynch.level.constructs.animation;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.primary.AnimationWait;
import com.marklynch.level.constructs.animation.primary.AnimationWalk;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Player;

public abstract class Animation {
	public boolean blockAI = false;
	private boolean completed = false;
	public float offsetX = 0;
	public float offsetY = 0;

	public float torsoAngle = 0f;
	public float leftShoulderAngle = 0f;
	public float leftElbowAngle = 0f;
	public float rightShoulderAngle = 0f;
	public float rightElbowAngle = 0f;

	// leg stuff
	public float leftHipAngle = 0;
	public float leftKneeAngle = 0;
	public float rightHipAngle = 0;
	public float rightKneeAngle = 0;

	public float durationSoFar = 0;
	public float durationToReach = 200;
	public int phase = 0;

	public abstract void update(double delta);

	public abstract void draw1();

	public abstract void draw2();

	public abstract void drawStaticUI();

	protected boolean backwards = true;

	public boolean drawEquipped = true;

	// Arrow
	public boolean drawArrowInOffHand = false;
	public boolean drawArrowInMainHand = false;
	public float arrowHandleY = 0;

	// Bow string
	public boolean drawBowString = false;
	public float bowStringHandleY = 0;

	public int boundsX1 = -128;
	public int boundsY1 = -128;
	public int boundsX2 = 128;
	public int boundsY2 = 128;
	public boolean drawWeapon = true;

	protected GameObject performer;

	public Animation(GameObject performer) {

		this.performer = performer;

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
		}
	}

	protected void reverseAnimation() {

		float temp = rightShoulderAngle;
		rightShoulderAngle = -leftShoulderAngle;
		leftShoulderAngle = -temp;

		temp = rightElbowAngle;
		rightElbowAngle = -leftElbowAngle;
		leftElbowAngle = -temp;

		temp = rightHipAngle;
		rightHipAngle = -leftHipAngle;
		leftHipAngle = -temp;

		temp = rightKneeAngle;
		rightKneeAngle = -leftKneeAngle;
		leftKneeAngle = -temp;

	}

	protected float moveTowardsTargetAngleInRadians(float angleToChange, float angleChange, float targetAngle) {
		if (angleToChange == targetAngle) {

			// } else if (Math.abs(angleToChange) < angleChange) {
			// angleToChange = targetAngle;
		} else if (angleToChange > targetAngle) {

			angleToChange -= angleChange;
		} else if (angleToChange < targetAngle) {
			angleToChange += angleChange;
		}

		if (Math.abs(angleToChange - targetAngle) < angleChange) {
			angleToChange = targetAngle;
		}

		return angleToChange;

	}

	public void complete() {
		if (blockAI)
			Level.blockingAnimations.remove(this);
		completed = true;
		if (performer == Level.player && Player.playerPathToMove == null && Player.playerTargetSquare == null
				&& Player.playerTargetAction == null && Player.playerTargetActor == null) {
			performer.setPrimaryAnimation(new AnimationWait(performer));

		}
		System.out.println("a complete() performer = " + performer + ", " + this);

		if (!(performer instanceof Actor))
			return;
		System.out.println("b complete() performer = " + performer + ", " + this);

		if (this instanceof AnimationWalk || this instanceof AnimationWait)
			return;
		System.out.println("c complete() performer = " + performer + ", " + this);

		// if (!(this instanceof AnimationWalk)) {
		//
		// } else
	}

	public boolean getCompleted() {
		return completed;
	}

}
