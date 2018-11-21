package com.marklynch.level.constructs.animation.secondary;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class AnimationBubbles extends SecondaryAnimation {

	float x, y; // originX, originY, targetX, targetY, speedX, speedY;
	public GameObject targetGameObject;
	public float speed = 1f;
	boolean reachedDestination = false;
	public Texture texture = Templates.BUBBLE.imageTexture;
	public float size = 0f;
	public float targetY;

	public AnimationBubbles(GameObject targetGameObject, float originX, float originY, float speed,
			OnCompletionListener onCompletionListener) {

		super(null, onCompletionListener, null, null, null, null, null, null, false, targetGameObject);
		if (!runAnimation)
			return;
		this.targetGameObject = targetGameObject;

		this.speed = speed;

		size = (float) (Math.random() * 16d);

		this.x = (float) (originX + Math.random() * 20d - 10d);
		this.y = originY;
		targetY = y - 100f;
		blockAI = false;

	}

	@Override
	public void update(double delta) {
		if (getCompleted())
			return;

		y -= delta * 0.1;

		if (y < targetY)
			runCompletionAlorightm(false);

	}

	@Override
	public void draw1() {
		draw();
	}

	@Override
	public void draw2() {

	}

	@Override
	public void drawStaticUI() {
	}

	public void draw() {
		// if (reachedDestination)
		// return;

		if (getCompleted())
			return;

		// float size = 2f;
		// float inverseSize = 0.5f;

		// Game.activeBatch.flush();

		// float drawPositionX = (Game.halfWindowWidth) + (Game.zoom *
		// inverseSize
		// * (x + Game.HALF_SQUARE_WIDTH - Game.halfWindowWidth +
		// Game.getDragXWithOffset()));
		// float drawPositionY = (Game.halfWindowHeight) + (Game.zoom *
		// inverseSize
		// * (y + Game.HALF_SQUARE_HEIGHT - Game.halfWindowHeight +
		// Game.getDragYWithOffset()));

		// TextureUtils.drawTexture(texture, 1f, x, y, x + gameObject.width, y +
		// gameObject.height,
		// false);

		TextureUtils.drawTexture(texture, 1, x, y, x + size, y + size);

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
