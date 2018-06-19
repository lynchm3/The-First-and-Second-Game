package com.marklynch.level.constructs.animation.secondary;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Arrow;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.TextureUtils;

public class AnimationStraightLine extends Animation {

	public String name;
	public Square targetSquare;
	float x, y, originX, originY, targetX, targetY, speedX, speedY;
	float angle = 0;
	float distanceToCoverX, distanceToCoverY, distanceCoveredX, distanceCoveredY;
	GameObject projectileObject;
	float rotationSpeed = 0;

	public AnimationStraightLine(GameObject projectileObject, Square targetSquare, float speed) {

		super(null);

		this.targetSquare = targetSquare;
		this.projectileObject = projectileObject;

		this.x = this.originX = projectileObject.squareGameObjectIsOn.xInGridPixels;// shooter.getCenterX();
		this.y = this.originY = projectileObject.squareGameObjectIsOn.yInGridPixels;// shooter.getCenterY();

		this.targetX = this.targetSquare.xInGridPixels;
		this.targetY = this.targetSquare.yInGridPixels;

		distanceToCoverX = this.targetX - this.originX;
		distanceToCoverY = this.targetY - this.originY;
		float totalDistanceToCover = Math.abs(distanceToCoverX) + Math.abs(distanceToCoverY);

		this.speedX = (distanceToCoverX / totalDistanceToCover) * speed;
		this.speedY = (distanceToCoverY / totalDistanceToCover) * speed;

		if (speedX < 0)
			this.rotationSpeed = -rotationSpeed;
		else
			this.rotationSpeed = rotationSpeed;

		if (projectileObject instanceof Arrow && distanceToCoverX < 0) {
			projectileObject.backwards = true;
		}

		blockAI = true;

		projectileObject.squareGameObjectIsOn.inventory.remove(projectileObject);
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

			System.out.println("DONE 1");
			runCompletionAlgorightm();
		} else if (Math.abs(distanceCoveredX) >= Math.abs(distanceToCoverX)
				&& Math.abs(distanceCoveredY) >= Math.abs(distanceToCoverY)) {

			System.out.println("DONE 2");

			runCompletionAlgorightm();

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

	public static void postRangedAnimation(GameObject projectileObject, Square targetSquare) {

		targetSquare.inventory.add(projectileObject);

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

		TextureUtils.drawTexture(projectileObject.imageTexture, 1.0f, x, y, x + projectileObject.width,
				y + projectileObject.height, projectileObject.backwards);

		// Game.flush();
		// view.rotate(-radians, new Vector3f(0f, 0f, 1f));
		// view.translate(new Vector2f(-x, -y));
		// Game.activeBatch.updateUniforms();

	}
}