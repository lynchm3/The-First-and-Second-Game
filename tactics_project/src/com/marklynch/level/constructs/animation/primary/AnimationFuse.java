package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.inanimateobjects.Fuse;

public class AnimationFuse extends Animation {

	int centerX1 = 32;
	int centerX2 = 35;
	int centerY1 = 96;
	int centerY2 = 99;

	Direction direction1;
	Direction direction2;

	public AnimationFuse(Fuse performer, OnCompletionListener onCompletionListener) {
		super(performer, onCompletionListener, null, null, null, null, null, null, false, true, performer);
		if (!runAnimation)
			return;

		if (performer.drawLeftBufferStop) {
			direction1 = Direction.LEFT;
		} else if (performer.drawRightBufferStop) {
			direction1 = Direction.RIGHT;
		} else if (performer.drawUpBufferStop) {
			direction1 = Direction.UP;
		} else if (performer.drawDownBufferStop) {
			direction1 = Direction.DOWN;
		}

		if (this.direction1 == performer.direction1) {
			this.direction2 = performer.direction2;
		} else {
			this.direction2 = performer.direction1;
		}

		blockAI = true;
		boundsY2 = (int) performer.height;

		setUpKf0();
		setUpKf1();
	}

	private void setUpKf0() {
		KeyFrame kf0 = new KeyFrame(performer, this);

		if (direction1 == Direction.LEFT) {
			kf0.boundsX1 = 32;
			kf0.boundsX1Speed = 0.1f;
		} else if (direction1 == Direction.RIGHT) {
			kf0.boundsX2 = 35;
			kf0.boundsX2Speed = 0.1f;
		} else if (direction1 == Direction.UP) {
			kf0.boundsY1 = 96;
			kf0.boundsY1Speed = 0.1f;
		} else if (direction1 == Direction.DOWN) {
			kf0.boundsY2 = 99;
			kf0.boundsY2Speed = 0.1f;
		}

		keyFrames.add(kf0);
	}

	private void setUpKf1() {
		KeyFrame kf1 = new KeyFrame(performer, this);
		kf1.boundsY2 = 0;
		kf1.boundsY2Speed = 0.1f;
		keyFrames.add(kf1);
	}

	@Override
	public void update(double delta) {
		super.keyFrameUpdate(delta);

	}

	@Override
	protected void animationSubclassRunCompletionAlgorightm(boolean wait) {
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
