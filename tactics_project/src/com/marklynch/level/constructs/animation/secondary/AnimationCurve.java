package com.marklynch.level.constructs.animation.secondary;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextureUtils;
import com.marklynch.utils.Utils.Point;

public class AnimationCurve extends Animation {

	public String name;
	float x, y, startX, startY, speed;
	float angle = 0;
	float distanceToCoverX, distanceToCoverY, distanceCoveredX, distanceCoveredY;
	GameObject projectileObject;
	float rotationSpeed = 0;
	float hypotanusLength;
	Point focalPoint;
	float startAngle, targetAngle;
	// float currentAngle = 0;

	// SOH... Sine: sin(θ) = Opposite / Hypotenuse
	// ...CAH... Cosine: cos(θ) = Adjacent / Hypotenuse
	// ...TOA Tangent: tan(θ) = Opposite / Adjacent

	public AnimationCurve(GameObject projectileObject, float speed, Point focalPoint, float angleChange) {

		super(null);

		this.projectileObject = projectileObject;
		this.speed = speed;
		this.focalPoint = focalPoint;
		this.startAngle = this.angle = getAngle(focalPoint,
				new Point(projectileObject.squareGameObjectIsOn.xInGridPixels,
						projectileObject.squareGameObjectIsOn.yInGridPixels));
		this.targetAngle = angle + angleChange;
		this.startX = this.x = projectileObject.squareGameObjectIsOn.xInGridPixels;// shooter.getCenterX();
		this.startY = this.y = projectileObject.squareGameObjectIsOn.yInGridPixels;// shooter.getCenterY();
		this.hypotanusLength = (float) Math.hypot(Math.abs(x - focalPoint.x), Math.abs(y - focalPoint.y));
		System.out.println("this.hypotanusLength = " + this.hypotanusLength);
		if (targetAngle < startAngle)
			this.rotationSpeed = -rotationSpeed;
		else
			this.rotationSpeed = rotationSpeed;

		blockAI = true;

		projectileObject.squareGameObjectIsOn.inventory.remove(projectileObject);

	}

	public float getAngle(Point center, Point target) {
		float angle = (float) Math.atan2(target.y - center.y, target.x - center.x);

		return angle;
	}

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;

		if (targetAngle > 0)
			angle += (float) (speed * delta);
		else
			angle -= (float) (speed * delta);

		System.out.println("angle = " + angle);

		if (targetAngle == startAngle) {
			runCompletionAlgorightm();
		} else if (Math.abs(angle) >= Math.abs(targetAngle) && Math.abs(angle) >= Math.abs(targetAngle)) {
			runCompletionAlgorightm();
		} else {
			x = (float) (focalPoint.x + this.hypotanusLength * Math.cos(angle));// Hypotenuse* cos(θ) = Adjacent
			y = (float) (focalPoint.y + this.hypotanusLength * Math.sin(angle));// Hypotenuse * sin(θ) = Opposite

			System.out.println("x = " + x);
			System.out.println("y = " + y);
			// runCompletionAlgorightm();

			// SOH... Sine: sin(θ) = Opposite / Hypotenuse
			// ...CAH... Cosine: cos(θ) = Adjacent / Hypotenuse

			// runCompletionAlgorightm();
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

		TextureUtils.drawTexture(projectileObject.imageTexture, 1.0f, x, y, x + projectileObject.width,
				y + projectileObject.height, projectileObject.backwards);

		QuadUtils.drawQuad(Color.WHITE, focalPoint.x, focalPoint.y, focalPoint.x + 20, focalPoint.y + 20);

		// Game.flush();
		// view.rotate(-radians, new Vector3f(0f, 0f, 1f));
		// view.translate(new Vector2f(-x, -y));
		// Game.activeBatch.updateUniforms();

	}
}
