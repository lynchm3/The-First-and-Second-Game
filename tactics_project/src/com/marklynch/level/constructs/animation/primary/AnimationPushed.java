package com.marklynch.level.constructs.animation.primary;

import com.marklynch.Game;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
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

	public AnimationPushed(GameObject performer, Square startSquare, Square endSquare, Animation oldAnimation,
			OnCompletionListener onCompletionListener) {
		super(performer, onCompletionListener, null, null, null, null, null, null, performer, startSquare, endSquare);
		if (!runAnimation)
			return;
		this.startSquare = startSquare;
		this.endSquare = endSquare;

		startOffsetX = offsetX = (int) ((this.startSquare.xInGrid - this.endSquare.xInGrid) * Game.SQUARE_WIDTH);
		startOffsetY = offsetY = (int) ((this.startSquare.yInGrid - this.endSquare.yInGrid) * Game.SQUARE_HEIGHT);

		backwards = performer.backwards;

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

		KeyFrame kf0 = new KeyFrame(performer, this);
		kf0.torsoAngle = targetRadians;
		kf0.offsetX = 0;
		kf0.offsetY = 0;
		kf0.leftShoulderAngle = targetArmRadians;
		kf0.rightShoulderAngle = targetArmRadians;
		kf0.leftElbowAngle = 0;
		kf0.rightElbowAngle = 0;
		kf0.leftHipAngle = 0;
		kf0.rightHipAngle = 0;
		kf0.leftKneeAngle = 0;
		kf0.rightKneeAngle = 0;
		kf0.setAllSpeeds(0.001d);
		kf0.offsetXSpeed = 1;
		kf0.offsetYSpeed = 1;
		keyFrames.add(kf0);

		blockAI = true;
	}

	float targetRadians = 0;
	float targetArmRadians = 0;

	@Override
	public void update(double delta) {
		keyFrameUpdate(delta);
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
