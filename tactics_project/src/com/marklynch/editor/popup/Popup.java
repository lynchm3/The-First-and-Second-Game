package com.marklynch.editor.popup;

import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.editor.Editor;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.PopupButton;

public class Popup {

	public float width;
	public Vector<PopupButton> buttons = new Vector<PopupButton>();
	public Editor editor;
	public Square square;
	public PopupButton selectSquareButton;
	public float drawPositionX, drawPositionY;

	public Popup(float width, Editor editor, Square square) {
		this.width = width;
		this.editor = editor;
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
