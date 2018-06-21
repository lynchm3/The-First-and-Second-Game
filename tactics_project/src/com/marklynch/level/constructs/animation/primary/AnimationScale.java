package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.objects.GameObject;

public class AnimationScale extends Animation {

	float startScale, endScale;

	public AnimationScale(GameObject performer, float start, float end, float durationToReachMillis) {

		super(performer, performer);
		if (!runAnimation)
			return;
		blockAI = false;
		this.durationToReachMillis = durationToReachMillis;

		scaleX = start;
		scaleY = start;
		startScale = start;
		endScale = end;
	}

	@Override
	public void update(double delta) {
		// runCompletionAlgorightm();

		if (getCompleted())
			return;
		super.update(delta);

		durationSoFar += delta;
		double progress = durationSoFar / durationToReachMillis;
		if (progress >= 1) {
			progress = 1;
			scaleX = 1;
			scaleY = 1;
			runCompletionAlgorightm();
			return;
		}
		scaleX = (float) (startScale + progress * (endScale - startScale));
		scaleY = (float) (startScale + progress * (endScale - startScale));

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
