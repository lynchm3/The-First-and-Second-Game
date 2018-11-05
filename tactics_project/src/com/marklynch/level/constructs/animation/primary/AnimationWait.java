package com.marklynch.level.constructs.animation.primary;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.objects.GameObject;

public class AnimationWait extends Animation {

	public AnimationWait(GameObject performer, OnCompletionListener onCompletionListener) {
		super(performer, onCompletionListener, null, null, null, null, null, null, performer);
		if (!runAnimation)
			return;
		blockAI = false;

		KeyFrame kf0 = new KeyFrame(performer, this);
		kf0.torsoAngle = 0;
		kf0.leftElbowAngle = 0;
		kf0.rightElbowAngle = 0;
		kf0.leftShoulderAngle = -0;
		kf0.rightShoulderAngle = -0;
		kf0.leftHipAngle = -0;
		kf0.rightHipAngle = -0;
		kf0.leftKneeAngle = 0;
		kf0.rightKneeAngle = 0;
		kf0.offsetY = 0;
		kf0.headToToeOffset = 0f;
		if (performer == Level.player) {
			kf0.keyFrameTimeMillis = Game.MINIMUM_TURN_TIME_PLAYER;
		} else {
			kf0.keyFrameTimeMillis = Game.MINIMUM_TURN_TIME_NON_PLAYER;
		}
		if (performer.hiding) {
			kf0.leftHipAngle = -1.1f;
			kf0.rightHipAngle = -1.1f;
			kf0.leftKneeAngle = 2f;
			kf0.rightKneeAngle = 2f;
			kf0.offsetY = 28f;
			kf0.headToToeOffset = -30f;
			// kf0.keyFrameTimeMillis = Game.MINIMUM_TURN_TIME;
		}

		if (this.leftShoulderAngle == 0 && this.rightShoulderAngle == 0) {
			kf0.leftShoulderAngle = 0.1d;
			kf0.rightShoulderAngle = 0.1d;
		}

		// kf0.setAllSpeeds(0.004d);
		// kf0.offsetYSpeed = 0.1;
		kf0.normaliseSpeeds = true;
		keyFrames.add(kf0);
	}

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
