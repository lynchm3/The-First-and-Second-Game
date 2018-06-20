package com.marklynch.level.constructs.animation.secondary;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Arrow;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.TextureUtils;

public class AnimationStraightLine extends Animation {

	public String name;
	public Square[] targetSquares;
	private int index = 0;
	float x, y, targetX, targetY, speed, speedX, speedY;
	float angle = 0;
	float distanceToCoverX, distanceToCoverY, distanceCoveredX, distanceCoveredY;
	GameObject projectileObject;
	float rotationSpeed = 0;

	public AnimationStraightLine(GameObject projectileObject, float speed, Square... targetSquares) {

		super(null);

		this.targetSquares = targetSquares;
		this.projectileObject = projectileObject;
		this.speed = speed;

		this.x = projectileObject.squareGameObjectIsOn.xInGridPixels;// shooter.getCenterX();
		this.y = projectileObject.squareGameObjectIsOn.yInGridPixels;// shooter.getCenterY();
		if (speedX < 0)
			this.rotationSpeed = -rotationSpeed;
		else
			this.rotationSpeed = rotationSpeed;

		blockAI = true;

		projectileObject.squareGameObjectIsOn.inventory.remove(projectileObject);

		setupForNextSquare();

	}

	public void setupForNextSquare() {

		this.targetX = this.targetSquares[index].xInGridPixels;
		this.targetY = this.targetSquares[index].yInGridPixels;

		distanceToCoverX = this.targetX - this.x;
		distanceToCoverY = this.targetY - this.y;
		float totalDistanceToCover = Math.abs(distanceToCoverX) + Math.abs(distanceToCoverY);

		this.speedX = (distanceToCoverX / totalDistanceToCover) * speed;
		this.speedY = (distanceToCoverY / totalDistanceToCover) * speed;

		if (projectileObject instanceof Arrow && distanceToCoverX < 0) {
			projectileObject.backwards = true;
		}

	}

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;

		float distanceX = (float) (speedX * delta);
		float distanceY = (float) (speedY * delta);

		angle += rotationSpeed * delta;

		distanceCoveredX += distanceX;
		distanceCoveredY += distanceY;

		if (distanceToCoverX == 0 && distanceToCoverY == 0) {

			index++;
			if (index >= targetSquares.length) {
				System.out.println("DONE 1");
				runCompletionAlgorightm();
			} else {
				setupForNextSquare();
			}
		} else if (Math.abs(distanceCoveredX) >= Math.abs(distanceToCoverX)
				&& Math.abs(distanceCoveredY) >= Math.abs(distanceToCoverY)) {

			index++;
			if (index >= targetSquares.length) {
				System.out.println("DONE 2");
				runCompletionAlgorightm();
			} else {
				setupForNextSquare();
			}

		} else {
			x += distanceX;
			y += distanceY;

		}
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

		if (Level.player.inventory.groundDisplay != null)
			Level.player.inventory.groundDisplay.refreshGameObjects();
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

		TextureUtils.drawTexture(projectileObject.imageTexture, 1.0f,
				x + projectileObject.drawOffsetRatioX * Game.SQUARE_WIDTH,
				y + projectileObject.drawOffsetRatioY * Game.SQUARE_HEIGHT,
				x + projectileObject.width + projectileObject.drawOffsetRatioX * Game.SQUARE_WIDTH,
				y + projectileObject.height + projectileObject.drawOffsetRatioY * Game.SQUARE_HEIGHT,
				projectileObject.backwards);

		// Game.flush();
		// view.rotate(-radians, new Vector3f(0f, 0f, 1f));
		// view.translate(new Vector2f(-x, -y));
		// Game.activeBatch.updateUniforms();

	}
}
