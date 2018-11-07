package com.marklynch.level.constructs.animation;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.primary.AnimationDie;
import com.marklynch.level.constructs.animation.primary.AnimationWait;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Player;

public abstract class Animation {
	public boolean blockAI = false;
	// public boolean blockPlayer = false;
	public boolean completed = false;
	public float offsetX = 0;
	public float offsetY = 0;
	public float headToToeOffset = 0f;

	public float torsoAngle = 0f;
	public float leftShoulderAngle = 0f;
	public float leftElbowAngle = 0f;
	public float rightShoulderAngle = 0f;
	public float rightElbowAngle = 0f;

	public float scaleX = 1;
	public float scaleY = 1;

	// leg stuff
	public float leftHipAngle = 0;
	public float leftKneeAngle = 0;
	public float rightHipAngle = 0;
	public float rightKneeAngle = 0;

	public float alpha = 1f;

	public float durationSoFar = 0;
	public float durationToReachMillis = 200;

	public abstract void draw1();

	public abstract void draw2();

	public abstract void draw3();

	public abstract void drawStaticUI();

	protected boolean backwards = false;
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

	public GameObject performer;

	public boolean runAnimation = false;

	public OnCompletionListener onCompletionListener;

	public int phase = 0;
	public ArrayList<KeyFrame> keyFrames = new ArrayList<KeyFrame>();
	public Square[] targetSquares;
	public Square targetSquare;
	public GameObject projectileObject;
	public Action action;
	public Actor shooter;
	public GameObject weapon;

	public Animation(GameObject performer, OnCompletionListener onCompletionListener, Square[] targetSquares,
			Square targetSquare, GameObject projectileObject, Action action, Actor shooter, GameObject weapon,
			Object... objectsInvolved) {

		this.performer = performer;
		this.targetSquares = targetSquares;
		this.onCompletionListener = onCompletionListener;
		this.targetSquare = targetSquare;
		this.projectileObject = projectileObject;
		this.action = action;
		this.shooter = shooter;
		this.weapon = weapon;

		runAnimation = Game.level.shouldLog(objectsInvolved, false);// && performer == Game.level.player;
		if (!runAnimation) {
			runCompletionAlorightm(true);
			return;
		}

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

			this.headToToeOffset = performer.getPrimaryAnimation().headToToeOffset;

			this.alpha = performer.getPrimaryAnimation().alpha;
		}
	}

	public abstract void update(double delta);

	// protected void reverseAnimation() {
	//
	// torsoAngle = -torsoAngle;
	//
	// float temp = rightShoulderAngle;
	// rightShoulderAngle = -leftShoulderAngle;
	// leftShoulderAngle = -temp;
	//
	// temp = rightElbowAngle;
	// rightElbowAngle = -leftElbowAngle;
	// leftElbowAngle = -temp;
	//
	// temp = rightHipAngle;
	// rightHipAngle = -leftHipAngle;
	// leftHipAngle = -temp;
	//
	// temp = rightKneeAngle;
	// rightKneeAngle = -leftKneeAngle;
	// leftKneeAngle = -temp;
	//
	// }

	protected float moveTowardsTargetAngleInRadians(double angleToChange, double angleChange, double targetAngle) {
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

		return (float) angleToChange;

	}

	protected abstract void childRunCompletionAlgorightm(boolean wait);

	public void runCompletionAlorightm(boolean wait) {
		if (performer == Level.player) {
			System.out.println("runCompletionAlorightm a for" + this);
		}

		if (completed)
			return;

		if (performer == Level.player) {
			System.out.println("runCompletionAlorightm b for" + this);
		}

		completed = true;

		childRunCompletionAlgorightm(wait);

		Level.blockingAnimations.remove(this);

		Level.animations.remove(this);

		if (shouldWait(wait)) {
			performer.setPrimaryAnimation(new AnimationWait(performer, null));
		}

		if (onCompletionListener != null)
			onCompletionListener.animationComplete(performer);
	}

	private boolean shouldWait(boolean wait) {

		if (performer == null)
			return false;

		if (wait == false)
			return false;

		// Not primary animation
		if (this != performer.getPrimaryAnimation()) {
			return false;
		}

		// Is a wait or die animation
		if (this instanceof AnimationWait || this instanceof AnimationDie) {
			return false;
		}

		if (performer.remainingHealth <= 0)
			return true;

		// Make player do wait animation at end of walk.
		if (performer == Level.player) {
			if (Player.playerPathToMove != null || Player.playerTargetAction != null) {
				return false;
			}
		}

		return true;
	}

	public boolean firstUpdate = true;

	public void keyFrameUpdate(double delta) {

		if (getCompleted())
			return;

		if (firstUpdate) {
			initiateNextKeyFrame();
			firstUpdate = false;
		}

		float alphaChange = (float) (0.002d * delta);
		float targetAlpha = 1f;
		if (performer != null && performer.hiding) {
			targetAlpha = 0.5f;
		}
		alpha = moveTowardsTargetAngleInRadians(alpha, alphaChange, targetAlpha);

		keyFrames.get(phase).animate(delta);
		if (keyFrames.get(phase).done) {
			phase++;

			if (phase < keyFrames.size()) {
				initiateNextKeyFrame();

			}
		}

		if (phase == keyFrames.size()) {
			runCompletionAlorightm(true);
		}
	}

	public void initiateNextKeyFrame() {
		keyFrames.get(phase).createSpeeds();
	}

	public boolean getCompleted() {
		return completed;
	}

	public static interface OnCompletionListener {
		public void animationComplete(GameObject gameObject);
	}

	public void swapLegs() {
		for (KeyFrame keyFrame : keyFrames) {
			float temp = keyFrame.leftKneeAngle;
			keyFrame.leftKneeAngle = keyFrame.rightKneeAngle;
			keyFrame.rightKneeAngle = temp;

			temp = keyFrame.leftHipAngle;
			keyFrame.leftHipAngle = keyFrame.rightHipAngle;
			keyFrame.rightHipAngle = temp;
		}

	}

}
