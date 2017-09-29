package com.marklynch.ui.button;

import com.marklynch.Game;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class Tooltip {

	StringWithColor textWithColor;
	int textWidth;
	int wrapWidth = 100;
	LevelButton levelButton;

	public Tooltip(String text, LevelButton button) {
		textWithColor = new StringWithColor(text, Color.BLACK);
		textWidth = Game.font.getWidth(text);
		this.levelButton = button;
	}

	public void drawStaticUI() {

		if (levelButton != null) {

			float x1 = 0;
			float x2 = 0;
			float y1 = 0;
			float y2 = 0;
			if (levelButton.realX <= Game.halfWindowWidth && levelButton.realY <= Game.halfWindowHeight) {
				// top left quadrant
				x1 = levelButton.realX + levelButton.width + 10;
				x2 = x1 + wrapWidth;
				y2 = levelButton.realY + levelButton.height;
				y1 = y2 - 20;

			} else if (levelButton.realX <= Game.halfWindowWidth && levelButton.realY > Game.halfWindowHeight) {
				// bottom left quadrant
				x1 = levelButton.realX + levelButton.width + 10;
				x2 = x1 + wrapWidth;
				y1 = levelButton.realY;
				y2 = y1 + 20;

			} else if (levelButton.realX > Game.halfWindowWidth && levelButton.realY <= Game.halfWindowHeight) {
				// top right
				x2 = levelButton.realX + -10;
				x1 = x2 - wrapWidth;
				y2 = levelButton.realY + levelButton.height;
				y1 = y2 - 20;

			} else if (levelButton.realX > Game.halfWindowWidth && levelButton.realY > Game.halfWindowHeight) {
				// bottom right
				x2 = levelButton.realX + -10;
				x1 = x2 - wrapWidth;
				y1 = levelButton.realY;
				y2 = y1 + 20;

			}

			// textWidth

			QuadUtils.drawQuad(Color.WHITE, x1, x2, y1, y2);

			TextUtils.printTextWithImages(x1, y1, wrapWidth, true, false, null, textWithColor);

		}
	}

}
