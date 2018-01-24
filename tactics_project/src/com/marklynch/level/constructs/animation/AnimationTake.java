package com.marklynch.level.constructs.animation;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.TextureUtils;

public class AnimationTake extends Animation {

	float x, y; // originX, originY, targetX, targetY, speedX, speedY;
	public float originX = 0;
	public float originY = 0;
	public float targetX = 0;
	public float targetY = 0;
	public double speedX = 0.1d;
	public double speedY = 0.1d;
	// float distanceToCoverX, distanceToCoverY, distanceCoveredX,
	// distanceCoveredY;
	public GameObject gameObject;
	public GameObject taker;
	public Square originSquare;

	public float speed = 1f;

	public AnimationTake(GameObject gameObject, GameObject taker, Square originSquare, float speed) {
		super();

		this.originSquare = originSquare;
		this.taker = taker;
		this.gameObject = gameObject;
		this.speed = speed;
		if (gameObject.squareGameObjectIsOn != null) {
			// on the ground
			this.x = originX = (int) (gameObject.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * gameObject.drawOffsetX);
			this.y = originY = (int) (gameObject.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * gameObject.drawOffsetY);
		} else {
			// in a container
			this.x = originX = (int) (((GameObject) gameObject.inventoryThatHoldsThisObject.parent).squareGameObjectIsOn.xInGridPixels
					+ (Game.SQUARE_WIDTH - gameObject.width) / 2);
			this.y = originY = (int) (((GameObject) gameObject.inventoryThatHoldsThisObject.parent).squareGameObjectIsOn.yInGridPixels
					+ (Game.SQUARE_HEIGHT - gameObject.height) / 2);
		}

		blockAI = false;

	}

	@Override
	public void update(double delta) {

		if (completed)
			return;

		targetX = (int) (taker.squareGameObjectIsOn.xInGridPixels + (Game.SQUARE_WIDTH - gameObject.width) / 2);
		targetY = (int) (taker.squareGameObjectIsOn.yInGridPixels + (Game.SQUARE_HEIGHT - gameObject.height) / 2);
		if (taker.primaryAnimation != null) {
			targetX += taker.primaryAnimation.offsetX;
			targetX += taker.primaryAnimation.offsetY;
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

		if (totalDistanceToCover < 1) {
			completed = true;
		} else {
			x += distanceX;
			y += distanceY;
		}

	}

	@Override
	public void draw1() {
		// if (originSquare.yInGrid < taker.squareGameObjectIsOn.yInGrid) {
		TextureUtils.drawTexture(gameObject.imageTexture, 1f, x, y, x + gameObject.width, y + gameObject.height, false);
		// }
	}

	@Override
	public void draw2() {
		// if (originSquare.yInGrid >= taker.squareGameObjectIsOn.yInGrid) {
		TextureUtils.drawTexture(gameObject.imageTexture, 1f, x, y, x + gameObject.width, y + gameObject.height, false);
		// }
	}

}
