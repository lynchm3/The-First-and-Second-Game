package com.marklynch.level.constructs.animation.primary;

import com.marklynch.Game;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;

public class AnimationPushed extends Animation {

	public Square startSquare;
	// public Square endSquare;
	public float startOffsetX = 0;
	public float startOffsetY = 0;
	public float endOffsetX = 0;
	public float endOffsetY = 0;

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;

	float durationPerSquare = 50;

	// Animation previousAnimation;

	// for show only, walking actor, primary

	public AnimationPushed(GameObject performer, Square startSquare, Square targetSquare, Animation oldAnimation,
			OnCompletionListener onCompletionListener) {
		super(performer, onCompletionListener, null, targetSquare, null, null, null, null, false, true, performer,
				startSquare, targetSquare);
		if (!runAnimation)
			return;

		blockAI = true;

		this.startSquare = startSquare;
		// this.endSquare = endSquare;

		// startOffsetX = offsetX =
		// startOffsetY = offsetY =
		endOffsetX = (int) ((this.targetSquare.xInGrid - this.startSquare.xInGrid) * Game.SQUARE_WIDTH);
		endOffsetY = (int) ((this.targetSquare.yInGrid - this.startSquare.yInGrid) * Game.SQUARE_HEIGHT);

		backwards = performer.backwards;

		float up = 0f;
		float down = 3.14f;
		float left = -1.7f;
		float right = 1.7f;

		if (this.targetSquare.yInGrid - startSquare.yInGrid < 0) {
			targetRadians = up;
			targetArmRadians = -0.25f;

		} else if (this.targetSquare.yInGrid - startSquare.yInGrid > 0) {
			targetRadians = down;
			targetArmRadians = 0.25f;

		}
		if (this.targetSquare.xInGrid - startSquare.xInGrid < 0) {
			targetRadians = left;
			targetArmRadians = -0.25f;

		} else if (this.targetSquare.xInGrid - startSquare.xInGrid > 0) {
			targetRadians = right;
			targetArmRadians = 0.25f;
		}

		KeyFrame kf0 = new KeyFrame(performer, this);
		kf0.torsoAngle = targetRadians;
		kf0.offsetX = endOffsetX;
		kf0.offsetY = endOffsetY;
		kf0.leftShoulderAngle = targetArmRadians;
		kf0.rightShoulderAngle = targetArmRadians;
		kf0.leftElbowAngle = 0;
		kf0.rightElbowAngle = 0;
		kf0.leftHipAngle = 0;
		kf0.rightHipAngle = 0;
		kf0.leftKneeAngle = 0;
		kf0.rightKneeAngle = 0;
		kf0.setAllSpeeds(0.001f);
		kf0.offsetXSpeed = 1;
		kf0.offsetYSpeed = 1;
		keyFrames.add(kf0);
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
	protected void animationSubclassRunCompletionAlgorightm(boolean wait) {
		leftShoulderAngle = 0;
		rightShoulderAngle = 0;

		targetSquare.inventory.add(performer);

	}

}
