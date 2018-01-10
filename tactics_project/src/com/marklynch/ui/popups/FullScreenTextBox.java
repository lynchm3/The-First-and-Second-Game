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

	public static final String ENTER_NEW_MARKER_NAME = "Enter New Marker Name";
	public GameObject gameObject;
	// public float width;
	public String instructions;
	public TextBox textBox;
	public float instructionsDrawPositionY;

	public FullScreenTextBox(GameObject gameObject, String instructions) {
		this.gameObject = gameObject;
		this.instructions = instructions;

		String textBoxText = ((MapMarker) gameObject).baseName;
		this.textBox = new TextBox(this, textBoxText, ENTER_NEW_MARKER_NAME, 300, 300);
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
	public void enterTyped() {
		MapMarker mapMarker = (MapMarker) gameObject;
		mapMarker.baseName = textBox.getText();
		if (mapMarker.baseName.length() == 0) {
			mapMarker.name = "Marker";
			mapMarker.links = TextUtils.getLinks(true, this);
		} else {
			mapMarker.name = mapMarker.baseName;
			mapMarker.links = TextUtils.getLinks(true, this);
		}

		// Game.level.popupTextBoxes.clear();
		Level.activeTextBox = null;
		Level.fullScreenTextBox = null;
	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		return textBox.isMouseOver(mouseX, mouseY);
	}

	public boolean click(int mouseX, int mouseY) {
		return textBox.click(mouseX, mouseY);
	}

	@Override
	public void textChanged() {
	}

}
