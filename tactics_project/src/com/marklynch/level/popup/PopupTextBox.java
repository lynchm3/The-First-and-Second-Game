package com.marklynch.level.popup;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.MapMarker;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class PopupTextBox {

	public GameObject gameObject;
	// public float width;
	public float height;
	public float drawPositionX, drawPositionY;
	public String tempString;
	public float caretX;
	public float caretY;
	public boolean caretOn = true;
	public int caretTimer = 0;
	public static final int CARET_BLINK_TIME = 500;
	public String instructions;

	public PopupTextBox(GameObject gameObject, String instructions) {
		this.gameObject = gameObject;
		this.instructions = instructions;
		tempString = new String(((MapMarker) gameObject).baseName);
		drawPositionX = 300;
		drawPositionY = 300;
		caretX = drawPositionX + 5;
		caretY = drawPositionX + 5;
		// this.width = 200;
		this.height = 20;
	}

	public void draw() {
		int textWidth = Game.font.getWidth(tempString);
		// Full blakc bg
		QuadUtils.drawQuad(new Color(0f, 0f, 0f, 0.5f), 0, Game.windowWidth, 0, Game.windowHeight);
		// Instructions
		TextUtils.printTextWithImages(drawPositionX, drawPositionY - 36, Integer.MAX_VALUE, true,
				new Object[] { instructions });
		// Text box
		QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionX + textWidth + 4, drawPositionY,
				drawPositionY + height);
		// Text string
		TextUtils.printTextWithImages(drawPositionX, drawPositionY, Integer.MAX_VALUE, true,
				new Object[] { tempString });
		// Caret
		if (caretOn) {
			QuadUtils.drawQuad(Color.BLACK, drawPositionX + textWidth, drawPositionX + textWidth + 2, drawPositionY,
					drawPositionY + height);

		}
	}

	public void keyTyped(char character) {
		if (tempString.equals(MapMarker.NO_DESCRIPTION))
			tempString = "";
		tempString += character;
	}

	public void enterTyped() {
		if (tempString.length() == 0)
			tempString = MapMarker.NO_DESCRIPTION;
		((MapMarker) gameObject).baseName = tempString;
		gameObject.name = "Marker: " + tempString;
		Game.level.popupTextBoxes.clear();

	}

	public void backSpacedTyped() {
		if (tempString.equals(MapMarker.NO_DESCRIPTION))
			tempString = "";
		if (tempString.length() > 0)
			tempString = tempString.substring(0, tempString.length() - 1);
	}

	public void updateRealtime(int delta) {
		caretTimer += delta;
		if (caretTimer >= CARET_BLINK_TIME) {
			caretOn = !caretOn;
			caretTimer -= CARET_BLINK_TIME;
		}
	}

}
