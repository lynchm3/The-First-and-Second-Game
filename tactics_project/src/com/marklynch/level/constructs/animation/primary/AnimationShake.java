package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.objects.inanimateobjects.GameObject;

public class AnimationShake extends Animation {

	double durationToReach = 200;
	double durationSoFar = 0;

	// for show only, walking actor, primary

	public AnimationShake(GameObject performer, OnCompletionListener onCompletionListener, boolean alwaysRun,
			double durationToReach) {
		super(performer, onCompletionListener, null, null, null, null, null, null, alwaysRun, true, performer);
		if (!runAnimation)
			return;
		this.durationToReach = 200;
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
	protected void animationSubclassRunCompletionAlgorightm(boolean wait) {

	}

}
