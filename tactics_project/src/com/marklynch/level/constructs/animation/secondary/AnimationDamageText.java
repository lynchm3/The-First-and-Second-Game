package com.marklynch.level.constructs.animation.secondary;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.level.constructs.Stat.OFFENSIVE_STATS;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.characterscreen.CharacterScreen;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.utils.Color;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class AnimationDamageText extends Animation {

	float x, y; // originX, originY, targetX, targetY, speedX, speedY;
	public float originX = 0;
	public float originY = 0;
	public float targetX = 0;
	public float targetY = 0;
	public int damageStringLength;
	public StringWithColor damageStringWithColor;

	public GameObject targetGameObject;

	public float speed = 1f;

	boolean reachedDestination = false;

	public Texture texture = Action.textureAttack;

	Color color;

	public AnimationDamageText(int damage, GameObject targetGameObject, float originX, float originY, float speed,
			OFFENSIVE_STATS statType, Color color) {

		this.targetGameObject = targetGameObject;

		String damageString = "" + damage;
		this.damageStringLength = Game.smallFont.getWidth(damageString);
		this.color = color;
		damageStringWithColor = new StringWithColor(damageString, color);

		this.speed = speed;

		this.x = this.originX = originX;
		this.y = this.originY = originY;

		this.durationToReach = 1000f;

		texture = CharacterScreen.offensiveStatImages.get(statType);

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
		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.translate(new Vector2f(Game.windowWidth / 2, Game.windowHeight / 2));
		view.scale(new Vector3f(size, size, 1f));
		view.translate(new Vector2f(-Game.windowWidth / 2, -Game.windowHeight / 2));
		Game.activeBatch.updateUniforms();

		float drawPositionX = (Game.halfWindowWidth) + (Game.zoom * inverseSize
				* (x + Game.HALF_SQUARE_WIDTH - Game.halfWindowWidth + Game.getDragXWithOffset()));
		float drawPositionY = (Game.halfWindowHeight) + (Game.zoom * inverseSize
				* (y + Game.HALF_SQUARE_HEIGHT - Game.halfWindowHeight + Game.getDragYWithOffset()));

		TextUtils.printTextWithImages(drawPositionX, drawPositionY, Integer.MAX_VALUE, false, null,
				damageStringWithColor);
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

}
