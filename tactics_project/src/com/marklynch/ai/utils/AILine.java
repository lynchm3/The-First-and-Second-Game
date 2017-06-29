package com.marklynch.ai.utils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

public class AILine {

	mdesl.graphics.Color color;
	Actor source;
	GameObject target;

	public AILine(mdesl.graphics.Color red, Actor source, GameObject target) {
		super();
		this.color = red;
		this.source = source;
		this.target = target;
	}

	public void draw2() {
		// TODO Auto-generated method stub
		float x1 = this.source.squareGameObjectIsOn.xInGrid * Game.SQUARE_WIDTH;
		float y1 = this.source.squareGameObjectIsOn.yInGrid * Game.SQUARE_HEIGHT;
		float x2 = this.target.squareGameObjectIsOn.xInGrid * Game.SQUARE_WIDTH;
		float y2 = this.target.squareGameObjectIsOn.yInGrid * Game.SQUARE_HEIGHT;

		float deltaX = x1 - x2;
		float deltaY = y1 - y2;
		float radians = (float) Math.atan2(deltaY, deltaX);// + 1.5708f;
		float degrees = (float) Math.toDegrees(radians);
		float distance = (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));

		System.out.println("radians = " + radians);
		System.out.println("Math.toRadians(60) = " + Math.toRadians(60));
		System.out.println("Math.toRadians(-60) = " + Math.toRadians(-60));

		// for (int i = 0; i < 360; i += 30) {

		// radians = (float) Math.toRadians(i);

		Game.activeBatch.flush();
		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.translate(new Vector2f(x1, y1));

		view.rotate(radians, new Vector3f(0f, 0f, 1f));
		// view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
		// view.translate(new Vector2f(-x1, -y1));
		Game.activeBatch.updateUniforms();

		// Draw
		TextureUtils.drawTexture(Game.level.gameCursor.redArrow, 0, 0 + distance, 0 - 50, 0 + 50);
		// TextureUtils.drawTexture(Game.level.gameCursor.redArrow, 0, 100,
		// 0,
		// 100);

		Game.activeBatch.flush();
		// view.translate(new Vector2f(x1, y1));
		view.rotate(-radians, new Vector3f(0f, 0f, 1f));
		// view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
		view.translate(new Vector2f(-x1, -y1));
		Game.activeBatch.updateUniforms();

		// }

		// LineUtils.drawLine(color, x1, y1, x2, y2, 10f);
		// LineUtils.drawLine(Color.WHITE, x1, y1, x2, y2, 10f);
		// QuadUtils.drawQuad(new Color(0.0f, 0.0f, 0.0f, 0.5f), x1, y1, x2,
		// y2);

	}
}
