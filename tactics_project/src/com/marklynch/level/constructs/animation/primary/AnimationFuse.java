package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.level.constructs.effect.EffectBurn;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.inanimateobjects.Fuse;
import com.marklynch.utils.TextureUtils;

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

		if (performer.drawLeftBufferStop && performer.connectedToExplosiveDirection != Direction.LEFT) {
			direction1 = Direction.LEFT;
		} else if (performer.drawRightBufferStop && performer.connectedToExplosiveDirection != Direction.RIGHT) {
			direction1 = Direction.RIGHT;
		} else if (performer.drawUpBufferStop && performer.connectedToExplosiveDirection != Direction.UP) {
			direction1 = Direction.UP;
		} else if (performer.drawDownBufferStop && performer.connectedToExplosiveDirection != Direction.DOWN) {
			direction1 = Direction.DOWN;
		}

		if (this.direction1 == performer.direction1) {
			this.direction2 = performer.direction2;
		} else {
			this.direction2 = performer.direction1;
		}

		blockAI = true;
		boundsX2 = 128;
		boundsY2 = 128;

		setUpKf0();
		setUpKf1();
	}

	private void setUpKf0() {

		KeyFrame kf0 = new KeyFrame(performer, this);

		kf0.boundsX2 = 128;
		kf0.boundsY2 = 128;

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

		if (direction1 == Direction.LEFT || direction1 == Direction.RIGHT) {
			if (direction2 == Direction.UP) {
				this.boundsY2 = 99;
				kf0.boundsY2 = 99;
				kf0.boundsY2Speed = 1;
			} else if (direction2 == Direction.DOWN) {
				this.boundsY1 = 96;
				kf0.boundsY1 = 96;
				kf0.boundsY1Speed = 1;

			}
		}

		if (direction1 == Direction.UP || direction1 == Direction.DOWN) {
			if (direction2 == Direction.LEFT) {
				this.boundsX2 = 35;
				kf0.boundsX2 = 35;
				kf0.boundsX2Speed = 1;
			} else if (direction2 == Direction.RIGHT) {
				this.boundsX1 = 32;
				kf0.boundsX1 = 32;
				kf0.boundsX1Speed = 1;

			}
		}

		keyFrames.add(kf0);
	}

	private void setUpKf1() {
		KeyFrame kf1 = new KeyFrame(performer, this);
		// x 32,35
		// y 96,99

		kf1.boundsX2 = 128;
		kf1.boundsY2 = 128;

		if (direction2 == Direction.LEFT) {
			kf1.boundsX2 = 0;
			kf1.boundsX2Speed = 0.1f;
		} else if (direction2 == Direction.RIGHT) {
			kf1.boundsX1 = 128;
			kf1.boundsX1Speed = 0.1f;
		} else if (direction2 == Direction.UP) {
			kf1.boundsY2 = 0;
			kf1.boundsY2Speed = 0.1f;
		} else if (direction2 == Direction.DOWN) {
			kf1.boundsY1 = 128;
			kf1.boundsY1Speed = 0.1f;
		}
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

		if (keyFrameIndex >= keyFrames.size())
			return;

		KeyFrame currentKeyFrame = keyFrames.get(keyFrameIndex);

		float offSetX = 34;
		float offSetY = 99;

		if (currentKeyFrame.boundsX1 != 0)
			offSetX = boundsX1;
		else if (currentKeyFrame.boundsX2 != 128)
			offSetX = boundsX2;

		if (currentKeyFrame.boundsY1 != 0)
			offSetY = boundsY1;
		else if (currentKeyFrame.boundsY2 != 128)
			offSetY = boundsY2;

		TextureUtils.drawTexture(EffectBurn.flameTexture, //
				performer.squareGameObjectIsOn.xInGridPixels - 8 + offSetX, //
				performer.squareGameObjectIsOn.yInGridPixels - 16 + offSetY, //
				performer.squareGameObjectIsOn.xInGridPixels + 8 + offSetX, //
				performer.squareGameObjectIsOn.yInGridPixels + offSetY //
		);

	}

	@Override
	public void drawStaticUI() {
	}

	@Override
	public void draw3() {

	}

}
