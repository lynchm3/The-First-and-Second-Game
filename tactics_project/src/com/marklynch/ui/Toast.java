package com.marklynch.ui;

import com.marklynch.Game;
import com.marklynch.utils.QuadUtils;

import mdesl.graphics.Color;

public class Toast {

	float x;
	float y;
	String text;

	float width;
	float height;

	public Toast(String text) {
		this.x = 200f;
		this.y = 50f;
		this.text = text;
		this.width = Game.font.getWidth(text);
		this.height = 30;
	}

	public Toast(float x, float y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.width = Game.font.getWidth(text);
		this.height = 30;
	}

	public void draw() {

		QuadUtils.drawQuad(Color.WHITE, x, x + width, y, y + height);
		Game.activeBatch.setColor(Color.RED);
		Game.font.drawText(Game.activeBatch, text, x, y);
	}

}
