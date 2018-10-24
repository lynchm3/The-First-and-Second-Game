package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.objects.GameObject;

public class AnimationSlash extends Animation {

	// public Square startSquare;
	// public Square endSquare;
	// public float startOffsetX = 0;
	// public float startOffsetY = 0;

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;

	// for show only, walking actor, primary

	GameObject target;

	public AnimationSlash(GameObject performer, GameObject target, OnCompletionListener onCompletionListener) {
		super(performer, onCompletionListener, null, null, null, null, null, null, performer, target);
		if (!runAnimation)
			return;
		this.target = target;
		durationToReachMillis = 400;

		quarterDurationToReach = durationToReachMillis / 4;
		halfDurationToReach = quarterDurationToReach + quarterDurationToReach;
		threeQuarterDurationToReach = halfDurationToReach + quarterDurationToReach;

		// this.startSquare = startSquare;
		// this.endSquare = endSquare;

		// startOffsetX = offsetX = (int) ((this.startSquare.xInGrid -
		// this.endSquare.xInGrid) * Game.SQUARE_WIDTH);
		// startOffsetY = offsetY = (int) ((this.startSquare.yInGrid -
		// this.endSquare.yInGrid) * Game.SQUARE_HEIGHT);

		backwards = performer.backwards;

		blockAI = true;

		KeyFrame kf0 = new KeyFrame(performer, this);
		kf0.setAllSpeeds(0.004);
		kf0.rightShoulderAngle = -2.25f;
		kf0.rightElbowAngle = -2.25f;
		kf0.leftShoulderAngle = 0;
		kf0.leftElbowAngle = 0;
		keyFrames.add(kf0);

		KeyFrame kf1 = new KeyFrame(performer, this);
		kf1.setAllSpeeds(0.004);
		kf1.rightShoulderAngle = 0;
		kf1.rightElbowAngle = 0;
		kf1.leftShoulderAngle = 0;
		kf1.leftElbowAngle = 0;
		keyFrames.add(kf1);

	}

	@Override
	public String toString() {
		return "AnimationSlash";
	}

	@Override
	public void update(double delta) {
		super.keyFrameUpdate(delta);

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

	@Override
	protected void childRunCompletionAlgorightm(boolean wait) {
		// TODO Auto-generated method stub

	}

}
