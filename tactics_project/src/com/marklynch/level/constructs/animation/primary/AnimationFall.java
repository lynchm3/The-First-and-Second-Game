package com.marklynch.level.constructs.animation.primary;

import com.marklynch.objects.GameObject;

public class AnimationFall extends AnimationScale {

	float startLeftArmAngle = 0f;
	float startRightArmAngle = -0f;
	float targetLeftArmAngle = 3.14f;
	float targetRightArmAngle = -3.14f;

	public AnimationFall(GameObject performer, float start, float end, float durationToReachMillis) {

		super(performer, start, end, durationToReachMillis);
		if (!runAnimation)
			return;
		startLeftArmAngle = this.leftShoulderAngle;
		startRightArmAngle = this.rightShoulderAngle;
	}

	@Override
	public void update(double delta) {
		// runCompletionAlgorightm();

		if (getCompleted())
			return;
		super.update(delta);
		double progress = durationSoFar / durationToReachMillis;
		leftShoulderAngle = (float) (startLeftArmAngle + progress * (targetLeftArmAngle - startLeftArmAngle));
		rightShoulderAngle = (float) (startRightArmAngle + progress * (targetRightArmAngle - startRightArmAngle));

		// scaleX = (float) progress * endScale;
		// scaleY = (float) progress * endScale;

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
