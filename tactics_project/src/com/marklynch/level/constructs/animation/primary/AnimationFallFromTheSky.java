package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.objects.GameObject;

public class AnimationFallFromTheSky extends Animation {

	// float startLeftArmAngle = 0f;
	// float startRightArmAngle = -0f;
	// float targetLeftArmAngle = 3.14f;
	float targetY = 0f;
	float startY = 0f;

	public AnimationFallFromTheSky(GameObject performer, float durationToReachMillis,
			OnCompletionListener onCompletionListener) {

		super(performer, onCompletionListener, performer);
		if (!runAnimation)
			return;

		offsetY = -1024;

		KeyFrame kf0 = new KeyFrame(performer, this);
		kf0.torsoAngle = 0;
		kf0.leftElbowAngle = 0;
		kf0.rightElbowAngle = 0;
		kf0.leftShoulderAngle = 0;
		kf0.rightShoulderAngle = 0;
		kf0.leftHipAngle = -0;
		kf0.rightHipAngle = -0;
		kf0.leftKneeAngle = 0;
		kf0.rightKneeAngle = 0;

		kf0.scaleX = 1;
		kf0.scaleY = 1;

		kf0.offsetY = 0;

		kf0.setAllSpeeds(0.004d);
		kf0.offsetYSpeed = 1;
		kf0.scaleXSpeed = 0.001;
		kf0.scaleYSpeed = 0.001;

		keyFrames.add(kf0);
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
}
