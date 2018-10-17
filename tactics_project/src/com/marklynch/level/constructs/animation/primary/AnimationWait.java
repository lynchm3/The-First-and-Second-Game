package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.objects.GameObject;

public class AnimationWait extends Animation {

	public AnimationWait(GameObject performer) {
		super(performer, performer);
		if (!runAnimation)
			return;
		durationToReachMillis = 400;
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
		if (performer.hiding) {
			kf0.leftHipAngle = -1.1f;
			kf0.rightHipAngle = -1.1f;
			kf0.leftKneeAngle = 2f;
			kf0.rightKneeAngle = 2f;
			kf0.offsetY = 28f;
		}
		kf0.speed = 0.004d;
		keyFrames.add(kf0);
	}

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;

		super.update(delta);

		keyFrames.get(phase).animate(delta);
		if (keyFrames.get(phase).done)
			phase++;

		if (phase == keyFrames.size()) {
			runCompletionAlgorightm(true);
		}

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
