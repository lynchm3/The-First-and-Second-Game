package com.marklynch.ui;

import com.marklynch.Game;
import com.marklynch.utils.QuadUtils;

import com.marklynch.utils.Color;

public class EditorToast {

	float x;
	float y;
	String text;

	float width;
	float height;

	public EditorToast(String text) {
		this.x = 200f;
		this.y = 50f;
		this.text = text;
		this.width = Game.smallFont.getWidth(text);
		this.height = 30;
	}

	public EditorToast(float x, float y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.width = Game.smallFont.getWidth(text);
		this.height = 30;
	}

	public void draw() {

		QuadUtils.drawQuad(Color.WHITE, x, y, x + width, y + height);
		Game.activeBatch.setColor(Color.RED);
		Game.smallFont.drawText(Game.activeBatch, text, x, y);
		Game.activeBatch.setColor(1,1,1,1);
	}

}
