package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Arrow;
import com.marklynch.objects.GameObject;

public class AnimationStraightLine extends Animation {

	public String name;
	public Square[] targetSquares;
	private int index = 0;
	float x, y, targetX, targetY, speed, speedX, speedY;
	float angle = 0;
	float distanceToCoverX, distanceToCoverY, distanceCoveredX, distanceCoveredY;
	GameObject projectileObject;
	float rotationSpeed = 0;
	double delay;

	public AnimationStraightLine(GameObject projectileObject, float speed, boolean blockAI, double delay,
			OnCompletionListener onCompletionListener, Square... targetSquares) {

		super(projectileObject, onCompletionListener, projectileObject, targetSquares[targetSquares.length - 1]);
		if (!runAnimation)
			return;

		this.targetSquares = targetSquares;
		this.projectileObject = projectileObject;
		this.speed = speed;

		this.x = projectileObject.squareGameObjectIsOn.xInGridPixels;// shooter.getCenterX();
		this.y = projectileObject.squareGameObjectIsOn.yInGridPixels;// shooter.getCenterY();
		if (speedX < 0)
			this.rotationSpeed = -rotationSpeed;
		else
			this.rotationSpeed = rotationSpeed;

		this.blockAI = blockAI;
		this.delay = delay;

		if (projectileObject instanceof Arrow && distanceToCoverX < 0) {
			projectileObject.backwards = true;
		}

		for (int i = 0; i < targetSquares.length; i++) {

			KeyFrame kf0 = new KeyFrame(performer, this);
			kf0.setAllSpeeds(1);
			kf0.offsetX = this.targetSquares[i].xInGridPixels - this.x;
			kf0.offsetY = this.targetSquares[i].yInGridPixels - this.y;
			// kf0.
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
	public void runCompletionAlgorightm(boolean wait) {
		postRangedAnimation(projectileObject, targetSquares, false);
		super.runCompletionAlgorightm(wait);
	}

	public void postRangedAnimation(GameObject projectileObject, Square... targetSquares) {
		postRangedAnimation(projectileObject, targetSquares, false);
	}

	public void postRangedAnimation(GameObject projectileObject, Square[] targetSquares, boolean doesNothing) {
		if (projectileObject != null) {

			if (projectileObject.getPrimaryAnimation() != null) {
				projectileObject.getPrimaryAnimation().offsetX = 0;
				projectileObject.getPrimaryAnimation().offsetY = 0;
			}
			targetSquares[targetSquares.length - 1].inventory.add(projectileObject);
		}
	}

	@Override
	public void draw3() {

		if (getCompleted())
			return;

	}
}
