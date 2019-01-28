package com.marklynch.level.constructs.animation.primary;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;

public class AnimationStraightLine extends Animation {

	public String name;
	float x, y;
	float angle = 0;
	float rotationSpeed = 0;
	// Square[] targetSquares;

	public AnimationStraightLine(GameObject performer, float time, boolean blockAI, double delay,
			OnCompletionListener onCompletionListener, Square... targetSquares) {

		super(performer, onCompletionListener, targetSquares, null, null, null, null, null, false, true, performer,
				targetSquares[targetSquares.length - 1]);
		if (!runAnimation)
			return;

		System.out.println("AnimationStraightLine");
		System.out.println("AnimationStraightLine performer = " + performer);

		this.targetSquares = targetSquares;

		this.x = performer.squareGameObjectIsOn.xInGridPixels;// shooter.getCenterX();
		this.y = performer.squareGameObjectIsOn.yInGridPixels;// shooter.getCenterY();

		float keyFrameTimeMillis = time;

		this.blockAI = blockAI;
		if (!blockAI) {

			if (performer == Level.player) {
				keyFrameTimeMillis = Game.MINIMUM_TURN_TIME_PLAYER / targetSquares.length;
			} else {
				keyFrameTimeMillis = Game.MINIMUM_TURN_TIME_NON_PLAYER / targetSquares.length;
			}
		}

		// if (performer instanceof Arrow && distanceToCoverX < 0) {
		// performer.backwards = true;
		// }

		for (int i = 0; i < targetSquares.length; i++) {

			KeyFrame kf0 = new KeyFrame(performer, this);
			kf0.setAllSpeeds(1);
			kf0.offsetX = this.targetSquares[i].xInGridPixels - this.x;
			kf0.offsetY = this.targetSquares[i].yInGridPixels - this.y;
			kf0.keyFrameTimeMillis = keyFrameTimeMillis;
			kf0.normaliseSpeeds = true;
			keyFrames.add(kf0);
			System.out.println("AnimationStraightLine kf0.offsetX = " + kf0.offsetX);
			System.out.println("AnimationStraightLine kf0.offsetY = " + kf0.offsetY);
			System.out.println("AnimationStraightLine kf0.keyFrameTimeMillis = " + kf0.keyFrameTimeMillis);
		}

	}

	@Override
	public void update(double delta) {
		super.keyFrameUpdate(delta);

	}

	@Override
	public void initiateNextKeyFrame() {
		keyFrames.get(phase).createSpeeds();
	}

	@Override
	public String toString() {
		return "AnimationStraightLine";
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
	public void animationSubclassRunCompletionAlgorightm(boolean wait) {
		postRangedAnimation();
	}

	public void postRangedAnimation() {
		if (performer != null) {
			this.offsetX = 0;
			this.offsetY = 0;
			targetSquares[targetSquares.length - 1].inventory.add(performer);
		}
	}

	@Override
	public void draw3() {
		if (getCompleted())
			return;
	}
}
