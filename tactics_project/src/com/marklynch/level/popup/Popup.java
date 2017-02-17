package com.marklynch.level.popup;

import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.Square;
import com.marklynch.ui.button.Button;

public class Popup {

	public float width;
	public Vector<PopupButton> buttons = new Vector<PopupButton>();
	public Level level;
	public Square square;
	public PopupButton selectSquareButton;
	public float drawPositionX, drawPositionY;

	public Button highlightedButton;
	public int highlightedButtonIndex = 0;

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

	public void moveHighLightUp() {
		highlightedButton.highlighted = false;
		this.highlightedButtonIndex--;
		if (highlightedButtonIndex < 0)
			highlightedButtonIndex = buttons.size() - 1;
		highlightedButton = buttons.get(highlightedButtonIndex);
		highlightedButton.highlighted = true;
	}

	public void moveHighLightDown() {
		highlightedButton.highlighted = false;
		this.highlightedButtonIndex++;
		if (highlightedButtonIndex >= buttons.size())
			highlightedButtonIndex = 0;
		highlightedButton = buttons.get(highlightedButtonIndex);
		highlightedButton.highlighted = true;

	}

	public void clickHighlightedButton() {
		highlightedButton.click();
	}

}
