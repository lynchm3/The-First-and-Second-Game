package com.marklynch.level.constructs.animation;

public class AnimationWait extends Animation {

	public AnimationWait() {
		super();
		durationToReach = 0;
		blockAI = false;
	}

	@Override
	public void update(double delta) {

		if (completed)
			return;

		durationSoFar += delta;
		double progress = durationSoFar / durationToReach;
		if (progress >= 1) {
			completed = true;
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
}
