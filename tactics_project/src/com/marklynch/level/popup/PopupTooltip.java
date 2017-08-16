package com.marklynch.level.popup;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class PopupTooltip {

	public GameObject gameObject;
	public float width;
	public float height;
	public float drawPositionX, drawPositionY;
	public String tempString;

	public PopupTooltip(GameObject gameObject) {
		this.gameObject = gameObject;
		tempString = new String(gameObject.name);
		drawPositionX = gameObject.squareGameObjectIsOn.xInGrid * Game.SQUARE_WIDTH;
		drawPositionY = gameObject.squareGameObjectIsOn.yInGrid * Game.SQUARE_HEIGHT;
		this.width = 200;
		this.height = 40;
	}

	public void draw() {
		int squarePositionX = gameObject.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY = gameObject.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT;
		drawPositionX = (Game.windowWidth / 2)
				+ (Game.zoom * (squarePositionX - Game.windowWidth / 2 + Game.getDragXWithOffset()));
		drawPositionY = (Game.windowHeight / 2)
				+ (Game.zoom * (squarePositionY - Game.windowHeight / 2 + Game.getDragYWithOffset()));
		// QuadUtils.drawQuad(new Color(0f, 0f, 0f, 0.5f), 0, Game.windowWidth,
		// 0, Game.windowHeight);
		QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionX + width, drawPositionY, drawPositionY + height);
		TextUtils.printTextWithImages(new Object[] { gameObject }, drawPositionX, drawPositionY, width, true);
	}

}
