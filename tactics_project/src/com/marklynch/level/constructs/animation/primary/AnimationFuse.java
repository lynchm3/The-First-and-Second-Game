package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.inanimateobjects.GameObject;

public class AnimationFuse extends Animation {

	int centerX1 = 32;
	int centerX2 = 35;
	int centerY1 = 96;
	int centerY2 = 99;

	Direction direction1;
	Direction direction2;

	public AnimationFuse(GameObject performer, Direction direction1, Direction direction2,
			OnCompletionListener onCompletionListener) {
		super(performer, onCompletionListener, null, null, null, null, null, null, false, true, performer);
		if (!runAnimation)
			return;

		this.direction1 = direction1;
		this.direction2 = direction2;

		backwards = performer.backwards;

		blockAI = true;
		boundsY2 = (int) performer.height;

		KeyFrame kf0 = new KeyFrame(performer, this);

//		kf0.offsetY = performer.height;
//		kf0.offsetYSpeed = 0.1f;

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
