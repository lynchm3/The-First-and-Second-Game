package com.marklynch.editor.popup;

import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.Game;
import com.marklynch.editor.Editor;
import com.marklynch.level.squares.Square;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.PopupButton;

public class Popup {

	public float width;
	public CopyOnWriteArrayList<PopupButton> buttons = new CopyOnWriteArrayList<PopupButton>();
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

		float squarePositionX = square.xInGridPixels;
		float squarePositionY = square.yInGridPixels;

		drawPositionX = (Game.windowWidth / 2)
				+ (Game.zoom * (squarePositionX - Game.windowWidth / 2 + Game.getDragXWithOffset()));
		drawPositionY = (Game.windowHeight / 2)
				+ (Game.zoom * (squarePositionY - Game.windowHeight / 2 + Game.getDragYWithOffset()));

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
