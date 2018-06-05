package com.marklynch.ui.button;

import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.input.Mouse;

import com.marklynch.Game;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;

public class Tooltip {

	public static Tooltip lastTooltipShown = null;

	public StringWithColor textWithColor;
	// public int textWidth;
	public static final int wrapWidth = 200;
	// LevelButton levelButton;
	float[] dimensions;
	ArrayList<Object> text;
	float alpha = 0f;
	Color backgroundColor = new Color(1f, 1f, 1f, 0f);
	Color textColor = new Color(0f, 0f, 0f, 0f);

	public Tooltip(boolean doesNothing, Object... text) {
		this.text = new ArrayList<Object>(Arrays.asList(text));
		// this.levelButton = button;
		dimensions = TextUtils.getDimensions(this.text, wrapWidth);
	}

	public Tooltip(Object[] text) {
		this.text = new ArrayList<Object>(Arrays.asList(text));
		// this.levelButton = button;
		dimensions = TextUtils.getDimensions(this.text, wrapWidth);
	}

	public Tooltip(ArrayList<Object> text) {
		this.text = text;
		// this.levelButton = button;
		dimensions = TextUtils.getDimensions(this.text, wrapWidth);
	}

	public void drawStaticUI() {

		// if (1 == 1)
		// return;
		if (lastTooltipShown != this) {
			alpha = 0f;
		} else if (alpha < 1f) {
			alpha += 0.05f;
			if (alpha > 1) {
				alpha = 1;
			}
		}

		float mouseX = Mouse.getX();
		float mouseY = Game.windowHeight - Mouse.getY();

		float x1 = 0;
		float x2 = 0;
		float y1 = 0;
		float y2 = 0;

		if (mouseX <= Game.halfWindowWidth && mouseY <= Game.halfWindowHeight) {
			// top left quadrant
			x1 = mouseX + 10;
			x2 = x1 + dimensions[0];
			y1 = mouseY;
			y2 = y1 + dimensions[1];

		} else if (mouseX <= Game.halfWindowWidth && mouseY > Game.halfWindowHeight) {
			// bottom left quadrant
			x1 = mouseX + 10;
			x2 = x1 + dimensions[0];
			y2 = mouseY;
			y1 = y2 - dimensions[1];

		} else if (mouseX > Game.halfWindowWidth && mouseY <= Game.halfWindowHeight) {
			// bottom right
			x2 = mouseX + -10;
			x1 = x2 - dimensions[0];
			y1 = mouseY;
			y2 = y1 + dimensions[1];

		} else if (mouseX > Game.halfWindowWidth && mouseY > Game.halfWindowHeight) {
			// top right
			x2 = mouseX + -10;
			x1 = x2 - dimensions[0];
			y2 = mouseY;
			y1 = y2 - dimensions[1];

		}

		// textWidth

		backgroundColor.a = alpha;
		QuadUtils.drawQuad(backgroundColor, x1, y1, x2, y2);// WAT

		textColor.a = alpha;
		TextUtils.printTextWithImages(this.text, x1, y1, wrapWidth, true, textColor, null);

	}

}
