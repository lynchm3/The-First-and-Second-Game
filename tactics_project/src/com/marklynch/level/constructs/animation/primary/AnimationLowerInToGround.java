package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.objects.inanimateobjects.GameObject;

public class AnimationLowerInToGround extends Animation {

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;

	float offsetXFromLastAnimation;
	float offsetYFromLastAnimation;

	// for show only, walking actor, primary

	public AnimationLowerInToGround(GameObject performer, OnCompletionListener onCompletionListener) {
		super(performer, onCompletionListener, null, null, null, null, null, null, false, true, performer);
		if (!runAnimation)
			return;
		backwards = performer.backwards;

		blockAI = true;
//		boundsY1 = (int) 0;
		boundsY2 = (int) performer.height;

		KeyFrame kf0 = new KeyFrame(performer, this);
//		kf0.setAllSpeeds(0.01f);
//		kf0.boundsY1 = 0;

		kf0.offsetY = performer.height;
		kf0.offsetYSpeed = 0.1f;

		kf0.boundsY2 = 0;
		kf0.boundsY2Speed = 0.1f;

		keyFrames.add(kf0);
	}

	@Override
	public void update(double delta) {
		super.keyFrameUpdate(delta);

	}

	@Override
	protected void animationSubclassRunCompletionAlgorightm(boolean wait) {
		if (performer.squareGameObjectIsOn != null) {
			performer.squareGameObjectIsOn.inventory.remove(performer);
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

}
