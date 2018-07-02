package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.objects.GameObject;

public class AnimationFallFromTheSky extends Animation {

	// float startLeftArmAngle = 0f;
	// float startRightArmAngle = -0f;
	// float targetLeftArmAngle = 3.14f;
	float targetY = 0f;
	float startY = 0f;

	public AnimationFallFromTheSky(GameObject performer, float durationToReachMillis) {

		super(performer, performer);
		if (!runAnimation)
			return;
		offsetX = 0;
		startY = offsetY = -1024;
		scaleX = 1;
		scaleY = 1;
		this.durationToReachMillis = durationToReachMillis;
	}

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;

		super.update(delta);
		durationSoFar += delta;
		double progress = durationSoFar / durationToReachMillis;
		if (progress >= 1) {
			progress = 1;
			offsetY = 0;
			runCompletionAlgorightm(true);
			return;
		}
		offsetY = (float) (startY + progress * (targetY - startY));
		// rightShoulderAngle = (float) (startRightArmAngle + progress *
		// (targetRightArmAngle - startRightArmAngle));

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
