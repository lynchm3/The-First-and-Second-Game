package com.marklynch.tactics.objects.level.popup;

import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.ui.button.Button;

public class Popup {

	public float width;
	public Vector<PopupButton> buttons = new Vector<PopupButton>();
	public Level level;
	public Square square;
	public PopupButton selectSquareButton;
	public float drawPositionX, drawPositionY;

	public Popup(float width, Level level, Square square) {
		this.width = width;
		this.level = level;
		this.square = square;

	}

	public void draw() {

		int squarePositionX = square.xInGrid * (int) Game.SQUARE_WIDTH;
		int squarePositionY = square.yInGrid * (int) Game.SQUARE_HEIGHT;

		drawPositionX = (Game.windowWidth / 2) + (Game.zoom * (squarePositionX - Game.windowWidth / 2 + Game.dragX));
		drawPositionY = (Game.windowHeight / 2) + (Game.zoom * (squarePositionY - Game.windowHeight / 2 + Game.dragY));

		// QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionX + width,
		// drawPositionY, Game.windowHeight);
		for (Button button : buttons) {
			button.draw();
		}
	}

	public void depressButtons() {
		for (PopupButton button : buttons) {
			button.down = false;
			// button.depress();
		}

	}

	public PopupButton getButton(Object object) {
		for (PopupButton button : buttons) {
			if (button.object == object)
				return button;
		}
		return null;
	}

}
