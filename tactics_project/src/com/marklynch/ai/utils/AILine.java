package com.marklynch.ai.utils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

public class AILine {

	public static enum AILineType {
		AI_LINE_TYPE_ATTACK, AI_LINE_TYPE_SEARCH, AI_LINE_TYPE_CRIME, AI_LINE_TYPE_FOLLOW
	};

	AILineType aiLineType;
	Actor source;
	Square target;

	public AILine(AILineType aiLineType, Actor source, Square target) {
		super();
		this.aiLineType = aiLineType;
		this.source = source;
		this.target = target;
	}

	public void draw2() {
		// TODO Auto-generated method stub
		float x1 = this.source.squareGameObjectIsOn.xInGrid * Game.SQUARE_WIDTH + Game.HALF_SQUARE_WIDTH;
		float y1 = this.source.squareGameObjectIsOn.yInGrid * Game.SQUARE_HEIGHT + Game.HALF_SQUARE_HEIGHT;
		// System.out.println("target = " + target);
		// System.out.println("target.squareGameObjectIsOn = " +
		// target.squareGameObjectIsOn);
		float x2 = this.target.xInGrid * Game.SQUARE_WIDTH + Game.HALF_SQUARE_WIDTH;
		float y2 = this.target.yInGrid * Game.SQUARE_HEIGHT + Game.HALF_SQUARE_HEIGHT;

		float deltaX = x2 - x1;
		float deltaY = y2 - y1;
		float radians = (float) Math.atan2(deltaY, deltaX);// + 1.5708f;
		float degrees = (float) Math.toDegrees(radians);
		float distance = (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));

		Game.activeBatch.flush();
		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.translate(new Vector2f(x1, y1));
		view.rotate(radians, new Vector3f(0f, 0f, 1f));
		Game.activeBatch.updateUniforms();

		// Draw
		if (aiLineType == AILineType.AI_LINE_TYPE_ATTACK) {
			TextureUtils.drawTexture(Game.level.gameCursor.redArrow, 0, 0 + distance, 0 - 16, 0 + 16);
		} else if (aiLineType == AILineType.AI_LINE_TYPE_SEARCH) {
			TextureUtils.drawTexture(Game.level.gameCursor.yellowArrow, 0, 0 + distance, 0 - 16, 0 + 16);
		} else if (aiLineType == AILineType.AI_LINE_TYPE_CRIME) {
			TextureUtils.drawTexture(Game.level.gameCursor.blueArrow, 0, 0 + distance, 0 - 16, 0 + 16);
		} else if (aiLineType == AILineType.AI_LINE_TYPE_FOLLOW) {
			TextureUtils.drawTexture(Game.level.gameCursor.greenArrow, 0, 0 + distance, 0 - 16, 0 + 16);
		}

		Game.activeBatch.flush();
		view.rotate(-radians, new Vector3f(0f, 0f, 1f));
		view.translate(new Vector2f(-x1, -y1));
		Game.activeBatch.updateUniforms();

		// }

		// LineUtils.drawLine(color, x1, y1, x2, y2, 10f);
		// LineUtils.drawLine(Color.WHITE, x1, y1, x2, y2, 10f);
		// QuadUtils.drawQuad(new Color(0.0f, 0.0f, 0.0f, 0.5f), x1, y1, x2,
		// y2);

	}
}
