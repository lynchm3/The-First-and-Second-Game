package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.objects.GameObject;

public class AnimationScale extends Animation {

	float startScale, endScale;

	public AnimationScale(GameObject performer, float start, float end, float durationToReachMillis,
			OnCompletionListener onCompletionListener) {

		super(performer, onCompletionListener, null, null, null, null, null, null, false, performer);
		if (!runAnimation)
			return;
		blockAI = false;
		this.durationToReachMillis = durationToReachMillis;

		scaleX = start;
		scaleY = start;

		KeyFrame kf0 = new KeyFrame(performer, this);
		kf0.scaleX = end;
		kf0.scaleY = end;
		kf0.scaleXSpeed = 0.0001f;
		kf0.scaleYSpeed = 0.0001f;
		keyFrames.add(kf0);

	}

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
	protected void childRunCompletionAlgorightm(boolean wait) {
		// TODO Auto-generated method stub

	}
}
