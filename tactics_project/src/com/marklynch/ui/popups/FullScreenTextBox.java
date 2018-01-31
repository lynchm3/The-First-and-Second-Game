package com.marklynch.ui.popups;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.MapMarker;
import com.marklynch.ui.TextBox;
import com.marklynch.ui.TextBoxHolder;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import com.marklynch.utils.Color;

public class FullScreenTextBox implements TextBoxHolder {

	public enum TYPE {
		RENAME_MAP_MARKER, SQUARE_SEARCH_X, SQUARE_SEARCH_Y
	}

	TYPE type;

	public static final String ENTER_NEW_MARKER = "Enter New Marker Name";
	public static final String SQUARE_SEARCH_X = "Square search - Enter square X";
	public static final String SQUARE_SEARCH_Y = "Square search - Enter square Y";
	public GameObject gameObject;
	// public float width;
	public String instructions;
	public TextBox textBox;
	public float instructionsDrawPositionY;

	static int squareX = 0;
	static int squareY = 0;

	public FullScreenTextBox(GameObject gameObject, String instructions, TYPE type) {
		this.gameObject = gameObject;
		this.instructions = instructions;
		this.type = type;

		if (type == TYPE.RENAME_MAP_MARKER) {
			String textBoxText = ((MapMarker) gameObject).baseName;
			this.textBox = new TextBox(this, textBoxText, ENTER_NEW_MARKER, 300, 300, TextBox.TYPE.ALL);
		} else if (type == TYPE.SQUARE_SEARCH_X) {

			this.textBox = new TextBox(this, "", SQUARE_SEARCH_X, 300, 300, TextBox.TYPE.NUMERIC);
		} else if (type == TYPE.SQUARE_SEARCH_Y) {
			this.textBox = new TextBox(this, "", SQUARE_SEARCH_Y, 300, 300, TextBox.TYPE.NUMERIC);
		}
		this.instructionsDrawPositionY = textBox.drawPositionY - 36;
	}

	public void draw() {

		// Full blakc bg
		QuadUtils.drawQuad(new Color(0f, 0f, 0f, 0.5f), 0, 0, Game.windowWidth, Game.windowHeight);
		// Instructions
		TextUtils.printTextWithImages(textBox.drawPositionX, instructionsDrawPositionY, Integer.MAX_VALUE, true, null,
				new Object[] { instructions });
		textBox.draw();
	}

	@Override
	public void enterTyped(TextBox textBox) {
		if (type == TYPE.RENAME_MAP_MARKER) {
			if (gameObject instanceof MapMarker) {
				MapMarker mapMarker = (MapMarker) gameObject;
				mapMarker.baseName = textBox.getText();
				if (mapMarker.baseName.length() == 0) {
					mapMarker.name = "Marker";
					mapMarker.links = TextUtils.getLinks(true, this);
				} else {
					mapMarker.name = mapMarker.baseName;
					mapMarker.links = TextUtils.getLinks(true, this);
				}
				Level.activeTextBox = null;
				Level.closeFullScreenTextBox();
			}
		} else if (type == TYPE.SQUARE_SEARCH_X) {
			squareX = this.textBox.numericValue;
			FullScreenTextBox fullScreenTextBox = new FullScreenTextBox(null, FullScreenTextBox.SQUARE_SEARCH_Y,
					FullScreenTextBox.TYPE.SQUARE_SEARCH_Y);
			Level.openFullScreenTextBox(fullScreenTextBox);
			Level.activeTextBox.maxNumericValue = Game.level.squares[0].length - 1;
		} else if (type == TYPE.SQUARE_SEARCH_Y) {
			Level.closeFullScreenTextBox();
			squareY = this.textBox.numericValue;
			Game.level.centerToSquare = true;
			Game.level.squareToCenterTo = Game.level.squares[squareX][squareY];
		}
	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		return textBox.isMouseOver(mouseX, mouseY);
	}

	public boolean click(int mouseX, int mouseY) {
		return textBox.click(mouseX, mouseY);
	}

	@Override
	public void textChanged(TextBox textBox) {
	}

}
