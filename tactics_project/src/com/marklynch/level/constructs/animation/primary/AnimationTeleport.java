package com.marklynch.level.constructs.animation.primary;

import com.marklynch.Game;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;

public class AnimationTeleport extends Animation {

	public Square startSquare;
	public Square endSquare;
	public float startOffsetX = 0;
	public float startOffsetY = 0;
	public float endOffsetX = 0;
	public float endOffsetY = 0;

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;

	float offsetXFromLastAnimation;
	float offsetYFromLastAnimation;

	// for show only, walking actor, primary

	public AnimationTeleport(GameObject performer, Square startSquare, Square endSquare, boolean straightenUp,
			OnCompletionListener onCompletionListener) {
		super(performer, onCompletionListener, null, null, null, null, null, null, false, startSquare, endSquare);
		if (!runAnimation)
			return;
		durationToReachMillis = 400;

		quarterDurationToReach = durationToReachMillis / 4;
		halfDurationToReach = quarterDurationToReach + quarterDurationToReach;
		threeQuarterDurationToReach = halfDurationToReach + quarterDurationToReach;

		this.startSquare = startSquare;
		this.endSquare = endSquare;
		startOffsetX = offsetX;// = offsetX;
		startOffsetY = offsetY;
		endOffsetX = (int) ((this.endSquare.xInGrid - this.startSquare.xInGrid) * Game.SQUARE_WIDTH);
		endOffsetY = (int) ((this.endSquare.yInGrid - this.startSquare.yInGrid) * Game.SQUARE_HEIGHT);
		backwards = performer.backwards;

		blockAI = true;

		KeyFrame kf0 = new KeyFrame(performer, this);
		kf0.setAllSpeeds(0.01f);
		kf0.boundsY1 = (int) performer.height;
		kf0.boundsY1Speed = 1;

		if (straightenUp) {
			kf0.torsoAngle = 0f;
			kf0.leftShoulderAngle = 0f;
			kf0.rightShoulderAngle = 0f;
			kf0.leftElbowAngle = 0f;
			kf0.rightElbowAngle = 0f;
			kf0.leftHipAngle = 0f;
			kf0.rightHipAngle = 0f;
			kf0.leftKneeAngle = 0f;
			kf0.rightKneeAngle = 0f;
		}

		keyFrames.add(kf0);

		KeyFrame kf1 = new KeyFrame(performer, this);
		kf1.offsetX = endOffsetX;
		kf1.offsetY = endOffsetY;
		kf1.setAllSpeeds(0.01f);
		kf1.offsetXSpeed = 9999999;
		kf1.offsetYSpeed = 9999999;
		kf1.boundsY1 = -128;
		kf1.boundsY1Speed = 1;

		if (straightenUp) {
			kf1.torsoAngle = 0f;
			kf1.leftShoulderAngle = 0f;
			kf1.rightShoulderAngle = 0f;
			kf1.leftElbowAngle = 0f;
			kf1.rightElbowAngle = 0f;
			kf1.leftHipAngle = 0f;
			kf1.rightHipAngle = 0f;
			kf1.leftKneeAngle = 0f;
			kf1.rightKneeAngle = 0f;
		}
		keyFrames.add(kf1);
	}

	@Override
	public void update(double delta) {
		super.keyFrameUpdate(delta);

	}

	@Override
	protected void childRunCompletionAlgorightm(boolean wait) {
		this.offsetX = 0;
		this.offsetY = 0;
		performer.lastSquare = performer.squareGameObjectIsOn;

		performer.squareGameObjectIsOn.inventory.remove(performer);
		performer.squareGameObjectIsOn = endSquare;
		endSquare.inventory.add(performer);
		// super.runCompletionAlgorightm(wait);
	}

	@Override
	public void draw2() {

	}

	@Override
	public void draw1() {

	}

	@Override
	public void drawStaticUI() {
	}

	@Override
	public void draw3() {

	}

}
