package com.marklynch.ui.popups;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.MapMarker;
import com.marklynch.ui.TextBox;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class PopupTextBox {

	public GameObject gameObject;
	// public float width;
	public String instructions;
	public TextBox textBox;
	public float instructionsDrawPositionY;

	public PopupTextBox(GameObject gameObject, String instructions) {
		this.gameObject = gameObject;
		this.instructions = instructions;
		this.textBox = new TextBox(new String(((MapMarker) gameObject).baseName), 300, 300);
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

		if (textBox.tempString.equals(MapMarker.NO_DESCRIPTION)) {
			textBox.tempString = "";
			textBox.caretPositionIndex = 0;
		}
		textBox.keyTyped(character);
	}

	public void enterTyped() {
		if (textBox.tempString.length() == 0)
			textBox.tempString = MapMarker.NO_DESCRIPTION;
		((MapMarker) gameObject).baseName = textBox.tempString;
		gameObject.name = "Marker: " + textBox.tempString;
		// Game.level.popupTextBoxes.clear();

	}

	public void backSpacedTyped() {
		if (textBox.tempString.equals(MapMarker.NO_DESCRIPTION)) {
			textBox.tempString = "";
			textBox.caretPositionIndex = 0;
		}
		textBox.backSpaceTyped();
	}

	public void deleteTyped() {
		if (textBox.tempString.equals(MapMarker.NO_DESCRIPTION)) {
			textBox.tempString = "";
			textBox.caretPositionIndex = 0;
		}
		textBox.deleteTyped();
	}

	public void updateRealtime(int delta) {
		textBox.updateRealtime(delta);
	}

	public void moveCaretLeft() {
		textBox.moveCaretLeft();
	}

	public void moveCaretRight() {
		textBox.moveCaretRight();

	}

	public void moveCaretTo(int newPosition) {
		textBox.moveCaretTo(newPosition);
	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		return textBox.isMouseOver(mouseX, mouseY);
	}

	public boolean click(int mouseX, int mouseY) {
		return textBox.click(mouseX, mouseY);
	}

}
