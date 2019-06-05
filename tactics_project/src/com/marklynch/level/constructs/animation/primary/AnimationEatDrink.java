package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.objects.inanimateobjects.GameObject;

public class AnimationEatDrink extends Animation {

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;

	public AnimationEatDrink(GameObject performer, Animation oldAnimation, OnCompletionListener onCompletionListener) {
		super(performer, onCompletionListener, null, null, null, null, null, null, false, true, performer, null);
		if (!runAnimation)
			return;
		float right = -1.5f;
		targetRadians = right;

		backwards = performer.backwards;

		// 1. bring arm straight out (like animation push, but just one hand)
		KeyFrame kf0 = new KeyFrame(performer, this);
		kf0.torsoAngle = 0;
		kf0.offsetX = 0;
		kf0.offsetY = 0;
		kf0.leftShoulderAngle = 0;
		kf0.rightShoulderAngle = targetRadians;
		kf0.leftElbowAngle = 0;
		kf0.rightElbowAngle = 0;
		kf0.leftHipAngle = 0;
		kf0.rightHipAngle = 0;
		kf0.leftKneeAngle = 0;
		kf0.rightKneeAngle = 0;
		kf0.setAllSpeeds(0.01f);
		kf0.offsetXSpeed = 1;
		kf0.offsetYSpeed = 1;
		keyFrames.add(kf0);

		// Was planning these, but its grand for the moment.
		// 2. Flip equipped upside down (scale y from 1 to minus 1)
		// 3. Hold in place for a sec
		// 4. Do animation spread and switch full jar w/ empty jar

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
		// TODO Auto-generated method stub

	}

}
