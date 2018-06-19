package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.objects.GameObject;

public class AnimationScale extends Animation {

	public AnimationScale(GameObject performer) {

		super(performer);
		blockAI = false;
		durationToReach = 2000;

		scaleX = 0;
		scaleY = 0;
	}

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;
		super.update(delta);

		durationSoFar += delta;
		double progress = durationSoFar / durationToReach;
		if (progress >= 1) {
			progress = 1;
			runCompletionAlgorightm();
		}
		scaleX = (float) progress * 1f;
		scaleY = (float) progress * 1f;

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
