package com.marklynch.level.constructs.animation;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class AnimationDamageText extends Animation {

	float x, y; // originX, originY, targetX, targetY, speedX, speedY;
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
	public int damageStringLength;
	public StringWithColor damageStringWithColor;

	public GameObject targetGameObject;

	public float speed = 1f;

	boolean reachedDestination = false;

	public AnimationDamageText(int damage, GameObject taker, float originX, float originY, float speed,
			float targetOffsetX, float targetOffsetY) {

		this.targetGameObject = taker;

		String damageString = "" + damage;
		this.damageStringLength = Game.font.getWidth(damageString);
		Color damageStringColor = Color.WHITE;
		damageStringWithColor = new StringWithColor(damageString, damageStringColor);

		this.speed = speed;

		this.x = this.originX = originX;
		this.y = this.originY = originY;

		this.targetOffsetX = targetOffsetX;
		this.targetOffsetY = targetOffsetY;

		blockAI = false;

	}

	@Override
	public void update(double delta) {

		if (completed)
			return;

		float oldX = x;
		float oldY = y;

		targetX = (int) (targetGameObject.squareGameObjectIsOn.xInGridPixels
				+ (Game.SQUARE_WIDTH - damageStringLength) / 2) + targetOffsetX;
		targetY = (int) (targetGameObject.squareGameObjectIsOn.yInGridPixels + (Game.SQUARE_HEIGHT - 12) / 2)
				+ targetOffsetY;
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
			completed = true;
		}

	}

	@Override
	public void draw1() {
		if (originY < targetGameObject.squareGameObjectIsOn.yInGrid) {
			draw();
		}
	}

	@Override
	public void draw2() {
		if (originY >= targetGameObject.squareGameObjectIsOn.yInGrid) {
			draw();
		}
	}

	public void draw() {

		// float trailwidth = 2;
		//
		// if (gameObject.templateId == Templates.MEDIUM_ORB.templateId)
		// trailwidth = 4;
		// if (gameObject.templateId == Templates.MEDIUM_ORB.templateId)
		// trailwidth = 8;
		//
		// for (int i = 2; i < trailLines.size(); i++) {
		//
		// LineUtils.drawLine(trailColor2, trailLines.get(i).x1,
		// trailLines.get(i).y1, trailLines.get(i).x2,
		// trailLines.get(i).y2, trailwidth * 3);
		// }
		//
		// for (Line trailLine : trailLines) {
		// LineUtils.drawLine(trailColor1, trailLine.x1, trailLine.y1,
		// trailLine.x2, trailLine.y2, trailwidth);
		// }

		// this.targetOffsetX = targetOffsetX;
		// this.targetOffsetY = targetOffsetY;

		if (!reachedDestination)
			TextUtils.printTextWithImages(x, y, Integer.MAX_VALUE, false, null, damageStringWithColor);
		// TextureUtils.drawTexture(gameObject.imageTexture, 1f, x, y, x +
		// gameObject.width, y + gameObject.height, false);

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
