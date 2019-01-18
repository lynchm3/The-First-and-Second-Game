package com.marklynch.level.constructs.animation.secondary;

import com.marklynch.Game;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class AnimationFanOut extends SecondaryAnimation {

//	float x, y; // originX, originY, targetX, targetY, speedX, speedY;
	public float originX = 0;
	public float originY = 0;
	public int damageStringLength;
	public StringWithColor damageStringWithColor;

	public GameObject targetGameObject;

	public float baseSpeed = 1f;
	public float baseSpeedX = 0.f;
	public float baseSpeedY = 0.1f;

	boolean reachedDestination = false;

	int debrisCount = 10;

	public Texture texture;

	Color color;
	private Direction direction;

	public ArrayList<Float> xs = new ArrayList<Float>(Float.class);
	public ArrayList<Float> ys = new ArrayList<Float>(Float.class);
	public ArrayList<Float> speedXs = new ArrayList<Float>(Float.class);
	public ArrayList<Float> speedYs = new ArrayList<Float>(Float.class);

	public AnimationFanOut(GameObject targetGameObject, Direction direction, float originX, float originY,
			float baseSpeed, Texture texture, OnCompletionListener onCompletionListener) {

		super(null, onCompletionListener, null, null, null, null, null, null, false, targetGameObject);

		if (!runAnimation)
			return;

		this.targetGameObject = targetGameObject;
		this.texture = texture;

		this.baseSpeed = baseSpeed;

		this.direction = direction;

		if (direction == Direction.LEFT) {
			baseSpeedX = -baseSpeed;
			baseSpeedY = 0f;
		} else if (direction == Direction.RIGHT) {
			baseSpeedX = baseSpeed;
			baseSpeedY = 0f;

		} else if (direction == Direction.DOWN) {
			baseSpeedX = 0f;
			baseSpeedY = -baseSpeed;

		} else if (direction == Direction.UP) {
			baseSpeedX = 0f;
			baseSpeedY = baseSpeed;

		}

		for (int i = 0; i < debrisCount; i++) {
			xs.add(originX);
			ys.add(originY);
			speedXs.add(baseSpeedX - 0.04f + Game.random.nextFloat() * 0.08f);
			speedYs.add(baseSpeedY - 0.04f + Game.random.nextFloat() * 0.08f);

			System.out.println("speedXs.get(i) = " + speedXs.get(i));
		}

		this.durationToReachMillis = 1000f;

		blockAI = false;

	}

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;

		durationSoFar += delta;
		double progress = durationSoFar / durationToReachMillis;
		if (progress >= 1) {
			runCompletionAlorightm(true);
		} else {

			for (int i = 0; i < debrisCount; i++) {
				xs.set(i, (float) (xs.get(i) + delta * speedXs.get(i)));
				ys.set(i, (float) (ys.get(i) + delta * speedYs.get(i)));
			}
		}
	}

	@Override
	public void draw1() {
	}

	@Override
	public void draw2() {

	}

	@Override
	public void drawStaticUI() {
		draw();
	}

	public void draw() {

		if (reachedDestination)
			return;

		if (getCompleted())
			return;

		for (int i = 0; i < debrisCount; i++) {

			float drawPositionX = (Game.halfWindowWidth) + (Game.zoom
					* (xs.get(i) + Game.HALF_SQUARE_WIDTH - Game.halfWindowWidth + Game.getDragXWithOffset()));

			float drawPositionY = (Game.halfWindowHeight) + (Game.zoom
					* (ys.get(i) + Game.HALF_SQUARE_HEIGHT - Game.halfWindowHeight + Game.getDragYWithOffset()));

			TextureUtils.drawTexture(texture, 1, drawPositionX - 2, drawPositionY - 2, drawPositionX + 2,
					drawPositionY + 2);
		}

	}

	@Override
	public void draw3() {
	}

	@Override
	protected void childRunCompletionAlgorightm(boolean wait) {
	}

}
