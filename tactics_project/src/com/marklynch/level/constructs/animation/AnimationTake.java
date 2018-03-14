package com.marklynch.level.constructs.animation;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.Color;
import com.marklynch.utils.TextureUtils;

public class AnimationTake extends Animation {

	public float x, y; // originX, originY, targetX, targetY, speedX, speedY;
	public float originX = 0;
	public float originY = 0;
	public float targetX = 0;
	public float targetY = 0;
	public float targetOffsetX = 0;
	public float targetOffsetY = 0;
	public double speedX = 0.1d;
	public double speedY = 0.1d;
	// float distanceToCoverX, distanceToCoverY, distanceCoveredX,
	// distanceCoveredY;
	public GameObject gameObject;
	public GameObject targetGameObject;

	public float speed = 1f;

	ArrayList<Line> trailLines = new ArrayList<Line>();

	boolean reachedDestination = false;

	// Color trailColor1 = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	Color trailColor1 = Color.BLUE;

	// Color trailColor2 = new Color(0.5f, 0f, 0f, 0f);
	Color trailColor2 = Color.PINK;

	public AnimationTake(GameObject gameObject, GameObject taker, float originX, float originY, float speed,
			float targetOffsetX, float targetOffsetY) {

		this.targetGameObject = taker;
		this.gameObject = gameObject;
		this.speed = speed;

		this.x = this.originX = originX;
		this.y = this.originY = originY;

		this.targetOffsetX = targetOffsetX;
		this.targetOffsetY = targetOffsetY;

		blockAI = false;

	}

	public AnimationTake(GameObject gameObject, GameObject taker, float targetOffsetX, float targetOffsetY,
			float speed) {

		this.targetGameObject = taker;
		this.gameObject = gameObject;
		this.speed = speed;

		if (gameObject.squareGameObjectIsOn != null) {
			// on the ground
			this.x = originX = (int) (gameObject.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * gameObject.drawOffsetRatioX);
			this.y = originY = (int) (gameObject.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * gameObject.drawOffsetRatioY);
		} else {
			// in a container
			this.x = originX = (int) (((GameObject) gameObject.inventoryThatHoldsThisObject.parent).squareGameObjectIsOn.xInGridPixels
					+ (Game.SQUARE_WIDTH - gameObject.width) / 2);
			this.y = originY = (int) (((GameObject) gameObject.inventoryThatHoldsThisObject.parent).squareGameObjectIsOn.yInGridPixels
					+ (Game.SQUARE_HEIGHT - gameObject.height) / 2);
		}

		this.targetOffsetX = targetOffsetX;
		this.targetOffsetY = targetOffsetY;

	}

	@Override
	public void update(double delta) {

		if (completed)
			return;

		float oldX = x;
		float oldY = y;

		targetX = (int) (targetGameObject.squareGameObjectIsOn.xInGridPixels
				+ (Game.SQUARE_WIDTH - gameObject.width) / 2) + targetOffsetX;
		targetY = (int) (targetGameObject.squareGameObjectIsOn.yInGridPixels
				+ (Game.SQUARE_HEIGHT - gameObject.height) / 2) + targetOffsetY;
		if (targetGameObject.primaryAnimation != null) {
			targetX += targetGameObject.primaryAnimation.offsetX;
			targetX += targetGameObject.primaryAnimation.offsetY;
		}

		float distanceToCoverX = this.targetX - this.x;
		float distanceToCoverY = this.targetY - this.y;
		float totalDistanceToCover = Math.abs(distanceToCoverX) + Math.abs(distanceToCoverY);

		this.speedX = (distanceToCoverX / totalDistanceToCover) * speed;
		this.speedY = (distanceToCoverY / totalDistanceToCover) * speed;

		double distanceX = speedX * delta;
		double distanceY = speedY * delta;

		// angle += rotationSpeed * delta;

		// distanceCoveredX += distanceX;
		// distanceCoveredY += distanceY;

		// if (Math.abs(distanceCoveredX) >= Math.abs(distanceToCoverX)
		// && Math.abs(distanceCoveredY) >= Math.abs(distanceToCoverY)) {
		// completed = true;
		// } else {

		if (totalDistanceToCover < 32) {
			reachedDestination = true;
		} else {
			x += distanceX;
			y += distanceY;
		}

		if (reachedDestination) {
			if (trailLines.size() > 0) {
				trailLines.remove(0);
				if (trailLines.size() == 0)
					completed = true;
			}
		} else {
			trailLines.add(new Line(oldX + gameObject.halfWidth, oldY + gameObject.halfHeight, x + gameObject.halfWidth,
					y + gameObject.halfHeight));
			if (trailLines.size() > 20) {
				trailLines.remove(0);
			}
		}

	}

	@Override
	public void draw1() {
		if (originY < targetGameObject.squareGameObjectIsOn.yInGridPixels) {
			draw();
		}
	}

	@Override
	public void draw2() {
		if (originY >= targetGameObject.squareGameObjectIsOn.yInGridPixels) {
			draw();
		}
	}

	@Override
	public void drawStaticUI() {
	}

	public void draw() {
		if (!reachedDestination)
			TextureUtils.drawTexture(gameObject.imageTexture, 1f, x, y, x + gameObject.width, y + gameObject.height,
					false);
	}

	public class Line {
		public float x1, y1, x2, y2;

		public Line(float x1, float y1, float x2, float y2) {
			super();
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
	}

}
