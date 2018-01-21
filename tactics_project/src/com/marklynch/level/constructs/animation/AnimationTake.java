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
	float distanceToCoverX, distanceToCoverY, distanceCoveredX, distanceCoveredY;
	public GameObject gameObject;
	public Square originSquare;
	public Square targetSquare;

	public float speed = 1;
	public boolean onTarget = true;

	public AnimationTake(GameObject gameObject, Square targetSquare, Square originSquare) {
		super();

		this.originSquare = originSquare;
		this.targetSquare = targetSquare;
		this.gameObject = gameObject;
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
		targetX = (int) (targetSquare.xInGridPixels + (Game.SQUARE_WIDTH - gameObject.width) / 2);
		targetY = (int) (targetSquare.yInGridPixels + (Game.SQUARE_HEIGHT - gameObject.height) / 2);

		distanceToCoverX = this.targetX - this.originX;
		distanceToCoverY = this.targetY - this.originY;
		float totalDistanceToCover = Math.abs(distanceToCoverX) + Math.abs(distanceToCoverY);

		this.speedX = (distanceToCoverX / totalDistanceToCover) * speed;
		this.speedY = (distanceToCoverY / totalDistanceToCover) * speed;

		this.onTarget = true;

		blockAI = false;

	}

	@Override
	public void update(double delta) {

		if (completed)
			return;

		double distanceX = speedX * delta;
		double distanceY = speedY * delta;

		// angle += rotationSpeed * delta;

		distanceCoveredX += distanceX;
		distanceCoveredY += distanceY;

		if (Math.abs(distanceCoveredX) >= Math.abs(distanceToCoverX)
				&& Math.abs(distanceCoveredY) >= Math.abs(distanceToCoverY)) {
			completed = true;

			if (Game.level.player.inventory.groundDisplay != null)
				Game.level.player.inventory.groundDisplay.refreshGameObjects();

		} else {
			x += distanceX;
			y += distanceY;
			// Square square = Game.level.squares[(int) Math.floor(x /
			// Game.SQUARE_WIDTH)][(int) Math
			// .floor(y / Game.SQUARE_HEIGHT)];
			// square.inventory.smashWindows(this);

		}

	}

	@Override
	public void draw1() {
		if (originSquare.yInGrid < targetSquare.yInGrid) {
			System.out.println("draw1");
			TextureUtils.drawTexture(gameObject.imageTexture, 1f, x, y, x + gameObject.width, y + gameObject.height,
					false);
		}
	}

	@Override
	public void draw2() {
		if (originSquare.yInGrid >= targetSquare.yInGrid) {
			System.out.println("draw2");
			TextureUtils.drawTexture(gameObject.imageTexture, 1f, x, y, x + gameObject.width, y + gameObject.height,
					false);
		}
	}

}
