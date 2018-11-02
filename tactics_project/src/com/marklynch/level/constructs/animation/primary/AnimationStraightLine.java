package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Arrow;
import com.marklynch.objects.GameObject;

public class AnimationStraightLine extends Animation {

	public String name;
	private int index = 0;
	float x, y, targetX, targetY, speed, speedX, speedY;
	float angle = 0;
	float distanceToCoverX, distanceToCoverY, distanceCoveredX, distanceCoveredY;
	float rotationSpeed = 0;
	double delay;

	public AnimationStraightLine(GameObject performer, float speed, boolean blockAI, double delay,
			OnCompletionListener onCompletionListener, Square... targetSquares) {

		super(performer, onCompletionListener, targetSquares, null, null, null, null, null, performer,
				targetSquares[targetSquares.length - 1]);
		if (!runAnimation)
			return;

		this.targetSquares = targetSquares;
		// this.projectileObject = projectileObject;
		this.speed = speed;

		this.x = performer.squareGameObjectIsOn.xInGridPixels;// shooter.getCenterX();
		this.y = performer.squareGameObjectIsOn.yInGridPixels;// shooter.getCenterY();
		if (speedX < 0)
			this.rotationSpeed = -rotationSpeed;
		else
			this.rotationSpeed = rotationSpeed;

		this.blockAI = blockAI;
		this.delay = delay;

		if (performer instanceof Arrow && distanceToCoverX < 0) {
			performer.backwards = true;
		}

		for (int i = 0; i < targetSquares.length; i++) {

			KeyFrame kf0 = new KeyFrame(performer, this);
			kf0.setAllSpeeds(1);
			kf0.offsetX = this.targetSquares[i].xInGridPixels - this.x;
			kf0.offsetY = this.targetSquares[i].yInGridPixels - this.y;
			// kf0.
			kf0.keyFrameTimeMillis = 25;
			kf0.normaliseSpeeds = true;
			keyFrames.add(kf0);
		}

	}

	@Override
	public void update(double delta) {
		super.keyFrameUpdate(delta);

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
	public void childRunCompletionAlgorightm(boolean wait) {
		postRangedAnimation();
		// super.runCompletionAlgorightm(wait);
	}

	public void postRangedAnimation() {
		if (performer != null) {

			if (performer.getPrimaryAnimation() != null) {
				performer.getPrimaryAnimation().offsetX = 0;
				performer.getPrimaryAnimation().offsetY = 0;
			}
			targetSquares[targetSquares.length - 1].inventory.add(performer);
		}
	}

	@Override
	public void draw3() {

		if (getCompleted())
			return;

	}

	// @Override
	// protected void childeRunCompletionAlgorightm(boolean wait) {
	// // TODO Auto-generated method stub
	//
	// }
}
