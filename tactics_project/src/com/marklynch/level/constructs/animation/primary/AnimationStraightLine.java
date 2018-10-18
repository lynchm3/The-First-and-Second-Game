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
			Square... targetSquares) {

		super(projectileObject, projectileObject, targetSquares[targetSquares.length - 1]);
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
			keyFrames.add(kf0);
		}

	}

	@Override
	public void update(double delta) {
		super.keyFrameUpdate(delta);

	}

	@Override
	public String toString() {
		return "AnimationThrown";
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

	public static void postRangedAnimation(GameObject projectileObject, Square... targetSquares) {
		postRangedAnimation(projectileObject, targetSquares, false);
	}

	public static void postRangedAnimation(GameObject projectileObject, Square[] targetSquares, boolean doesNothing) {
		targetSquares[targetSquares.length - 1].inventory.add(projectileObject);
	}

	@Override
	public void draw3() {

		if (getCompleted())
			return;

		// Game.flush();
		// float radians = (float) Math.toRadians(angle);
		// Matrix4f view = Game.activeBatch.getViewMatrix();
		// view.translate(new Vector2f(x, y));
		// view.rotate(radians, new Vector3f(0f, 0f, 1f));
		// Game.activeBatch.updateUniforms();

		// TextureUtils.drawTexture(projectileObject.imageTexture, 1.0f,
		// x + projectileObject.drawOffsetRatioX * Game.SQUARE_WIDTH,
		// y + projectileObject.drawOffsetRatioY * Game.SQUARE_HEIGHT,
		// x + projectileObject.width + projectileObject.drawOffsetRatioX *
		// Game.SQUARE_WIDTH,
		// y + projectileObject.height + projectileObject.drawOffsetRatioY *
		// Game.SQUARE_HEIGHT,
		// projectileObject.backwards);

		// Game.flush();
		// view.rotate(-radians, new Vector3f(0f, 0f, 1f));
		// view.translate(new Vector2f(-x, -y));
		// Game.activeBatch.updateUniforms();

	}
}
