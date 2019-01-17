package com.marklynch.level.constructs.animation.secondary;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.Color;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class AnimationFanOut extends SecondaryAnimation {

	float x, y; // originX, originY, targetX, targetY, speedX, speedY;
	public float originX = 0;
	public float originY = 0;
	public int damageStringLength;
	public StringWithColor damageStringWithColor;

	public GameObject targetGameObject;

	public float speed = 1f;
	public float speedX = 0.f;
	public float speedY = 0.1f;

	boolean reachedDestination = false;

	public Texture texture;

	Color color;
	private Direction direction;

	public AnimationFanOut(GameObject targetGameObject, Direction direction, float originX, float originY, float speed,
			Texture texture, OnCompletionListener onCompletionListener) {

		super(null, onCompletionListener, null, null, null, null, null, null, false, targetGameObject);
		if (!runAnimation)
			return;

		this.targetGameObject = targetGameObject;
		this.texture = texture;

		this.speed = speed;

		this.direction = direction;

		if (direction == Direction.LEFT) {
			speedX = -0.1f;
			speedY = 0f;
		} else if (direction == Direction.RIGHT) {
			speedX = 0.1f;
			speedY = 0f;

		} else if (direction == Direction.DOWN) {
			speedX = 0f;
			speedY = -0.1f;

		} else if (direction == Direction.UP) {
			speedX = 0f;
			speedY = 0.1f;

		}

		this.x = this.originX = originX;
		this.y = this.originY = originY;

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
			x += delta * speedX;
			y += delta * speedY;
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

		float size = 1f;
		float inverseSize = 1f;

		Game.activeBatch.flush();
		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.translate(new Vector2f(Game.windowWidth / 2, Game.windowHeight / 2));
		view.scale(new Vector3f(size, size, 1f));
		view.translate(new Vector2f(-Game.windowWidth / 2, -Game.windowHeight / 2));
		Game.activeBatch.updateUniforms();

		float drawPositionX = (Game.halfWindowWidth) + (Game.zoom * inverseSize
				* (x + Game.HALF_SQUARE_WIDTH - Game.halfWindowWidth + Game.getDragXWithOffset()));
		float drawPositionY = (Game.halfWindowHeight) + (Game.zoom * inverseSize
				* (y + Game.HALF_SQUARE_HEIGHT - Game.halfWindowHeight + Game.getDragYWithOffset()));

//		TextUtils.printTextWithImages(drawPositionX, drawPositionY, Integer.MAX_VALUE, false, null, Color.WHITE,
//				damageStringWithColor);
		TextureUtils.drawTexture(texture, 1, drawPositionX - 16, drawPositionY, drawPositionX, drawPositionY + 16);

		Game.activeBatch.flush();
		view.translate(new Vector2f(Game.windowWidth / 2, Game.windowHeight / 2));
		view.scale(new Vector3f(inverseSize, inverseSize, 1f));
		view.translate(new Vector2f(-Game.windowWidth / 2, -Game.windowHeight / 2));
		Game.activeBatch.updateUniforms();

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

	@Override
	public void draw3() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void childRunCompletionAlgorightm(boolean wait) {
		// TODO Auto-generated method stub

	}

}
