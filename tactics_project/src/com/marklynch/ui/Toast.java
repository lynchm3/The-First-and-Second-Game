package com.marklynch.ui;

import com.marklynch.Game;

import mdesl.graphics.Color;

public class Toast {

	float x;
	float y;
	String text;

	public Toast(float x, float y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
	}

	public void draw() {

		Game.activeBatch.setColor(Color.RED);
		Game.font.drawText(Game.activeBatch, text, x, y);
	}

}
