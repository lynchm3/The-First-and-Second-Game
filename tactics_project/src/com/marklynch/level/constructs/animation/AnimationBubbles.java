package com.marklynch.level.constructs.animation;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class AnimationBubbles extends Animation {

	float x, y; // originX, originY, targetX, targetY, speedX, speedY;
	public float originX = 0;
	public float originY = 0;
	public float targetX = 0;
	public float targetY = 0;
	public GameObject targetGameObject;
	public float speed = 1f;
	boolean reachedDestination = false;
	public Texture texture = Templates.LARGE_ORB.imageTexture;

	public AnimationBubbles(GameObject targetGameObject, float originX, float originY, float speed) {

		this.targetGameObject = targetGameObject;

		this.speed = speed;

		this.x = this.originX = originX;
		this.y = this.originY = originY;

		this.durationToReach = 1000f;

		blockAI = false;

	}

	@Override
	public void update(double delta) {

		if (completed)
			return;

		durationSoFar += delta;
		double progress = durationSoFar / durationToReach;
		if (progress >= 1) {
			completed = true;
		} else {
			y -= delta * 0.1f;
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

		if (completed)
			return;

		float size = 2f;
		float inverseSize = 0.5f;

		Game.activeBatch.flush();

		float drawPositionX = (Game.halfWindowWidth) + (Game.zoom * inverseSize
				* (x + Game.HALF_SQUARE_WIDTH - Game.halfWindowWidth + Game.getDragXWithOffset()));
		float drawPositionY = (Game.halfWindowHeight) + (Game.zoom * inverseSize
				* (y + Game.HALF_SQUARE_HEIGHT - Game.halfWindowHeight + Game.getDragYWithOffset()));

		// TextureUtils.drawTexture(texture, 1f, x, y, x + gameObject.width, y +
		// gameObject.height,
		// false);

		TextureUtils.drawTexture(texture, 1, drawPositionX - 16, drawPositionY, drawPositionX, drawPositionY + 16);

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
