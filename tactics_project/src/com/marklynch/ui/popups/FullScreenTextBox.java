package com.marklynch.ui.popups;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.MapMarker;
import com.marklynch.ui.TextBox;
import com.marklynch.ui.TextBoxHolder;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class FullScreenTextBox implements TextBoxHolder {

	public GameObject gameObject;
	// public float width;
	public String instructions;
	public TextBox textBox;
	public float instructionsDrawPositionY;

	public FullScreenTextBox(GameObject gameObject, String instructions) {
		this.gameObject = gameObject;
		this.instructions = instructions;

		String textBoxText = ((MapMarker) gameObject).baseName;
		if (textBoxText == MapMarker.NO_DESCRIPTION) {
			textBoxText = "";
		}

		this.textBox = new TextBox(this, textBoxText, MapMarker.NO_DESCRIPTION, 300, 300);
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

	public void keyTyped(char character) {

		if (textBox.text.equals(MapMarker.NO_DESCRIPTION)) {
			textBox.text = "";
			textBox.caretPositionIndex = 0;
		}
		textBox.keyTyped(character);
	}

	@Override
	public void enterTyped() {
		if (textBox.text.length() == 0)
			textBox.text = MapMarker.NO_DESCRIPTION;
		((MapMarker) gameObject).baseName = textBox.text;
		gameObject.name = "Marker: " + textBox.text;
		// Game.level.popupTextBoxes.clear();
		Level.activeTextBox = null;
		Level.fullScreenTextBox = null;

	}

	public void backSpaceTyped() {
		if (textBox.text.equals(MapMarker.NO_DESCRIPTION)) {
			textBox.text = "";
			textBox.caretPositionIndex = 0;
		}
		textBox.backSpaceTyped();
	}

	public void deleteTyped() {
		if (textBox.text.equals(MapMarker.NO_DESCRIPTION)) {
			textBox.text = "";
			textBox.caretPositionIndex = 0;
		}
		textBox.deleteTyped();
	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		return textBox.isMouseOver(mouseX, mouseY);
	}

	public boolean click(int mouseX, int mouseY) {
		return textBox.click(mouseX, mouseY);
	}

}
