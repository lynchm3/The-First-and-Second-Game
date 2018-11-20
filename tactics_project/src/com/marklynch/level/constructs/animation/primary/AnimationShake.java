package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.objects.GameObject;

public class AnimationShake extends Animation {

	double durationToReach = 200;
	double durationSoFar = 0;

	// for show only, walking actor, primary

	public AnimationShake(GameObject performer, OnCompletionListener onCompletionListener, boolean alwaysRun) {
		super(performer, onCompletionListener, null, null, null, null, null, null, alwaysRun, performer);
		if (!runAnimation)
			return;
		blockAI = false;

	}

	@Override
	public void update(double delta) {
		durationSoFar += delta;
		if (durationSoFar >= durationToReach) {
			runCompletionAlorightm(true);
		} else {
			offsetX = (float) (Math.random() * 16) - 8f;
			offsetY = (float) (Math.random() * 16) - 8f;

		}
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

	}

}
