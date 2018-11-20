package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public class AnimationFlinch extends Animation {

	public AnimationFlinch(GameObject performer, Square squareBeingAttackedFrom, Animation oldAnimation,
			OnCompletionListener onCompletionListener) {
		super(performer, onCompletionListener, null, null, null, null, null, null, false, performer);
		if (!runAnimation)
			return;

		durationToReachMillis = 400;

		float down = 0.5f;
		float up = -0.5f;
		float right = -0.5f;
		float left = 0.5f;

		if (squareBeingAttackedFrom.yInGrid - performer.squareGameObjectIsOn.yInGrid < 0) {
			targetRadians = up;

		} else if (squareBeingAttackedFrom.yInGrid - performer.squareGameObjectIsOn.yInGrid > 0) {
			targetRadians = down;

		}
		if (squareBeingAttackedFrom.xInGrid - performer.squareGameObjectIsOn.xInGrid < 0) {
			targetRadians = left;

		} else if (squareBeingAttackedFrom.xInGrid - performer.squareGameObjectIsOn.xInGrid > 0) {
			targetRadians = right;
		}
		backwards = performer.backwards;

		KeyFrame kf0 = new KeyFrame(performer, this);

		kf0.torsoAngle = targetRadians;
		kf0.leftElbowAngle = 0;
		kf0.rightElbowAngle = 0;
		kf0.leftShoulderAngle = -targetRadians;
		kf0.rightShoulderAngle = -targetRadians;
		kf0.leftHipAngle = -targetRadians;
		kf0.rightHipAngle = -targetRadians;
		kf0.leftKneeAngle = 0;
		kf0.rightKneeAngle = 0;
		// kf0.speed = 0.02d;
		kf0.setAllSpeeds(0.02f);
		keyFrames.add(kf0);

		blockAI = true;
	}

	float targetRadians = 0;

	@Override
	public void update(double delta) {
		keyFrameUpdate(delta);
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

	@Override
	protected void childRunCompletionAlgorightm(boolean wait) {
		// TODO Auto-generated method stub

	}

}
