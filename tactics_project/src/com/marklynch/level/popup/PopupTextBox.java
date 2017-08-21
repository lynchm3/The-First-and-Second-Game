package com.marklynch.level.popup;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.MapMarker;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class PopupTextBox {

	public GameObject gameObject;
	public float width;
	public float height;
	public float drawPositionX, drawPositionY;
	public String tempString;

	public PopupTextBox(GameObject gameObject) {
		this.gameObject = gameObject;
		tempString = new String(((MapMarker) gameObject).baseName);
		drawPositionX = 300;
		drawPositionY = 300;
		this.width = 200;
		this.height = 40;
	}

	public void draw() {
		QuadUtils.drawQuad(new Color(0f, 0f, 0f, 0.5f), 0, Game.windowWidth, 0, Game.windowHeight);
		QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionX + width, drawPositionY, drawPositionY + height);
		TextUtils.printTextWithImages(drawPositionX, drawPositionY, width, true, new Object[] { tempString });
	}

	public void keyTyped(char character) {
		if (tempString.equals(MapMarker.NO_DESCRIPTION))
			tempString = "";
		tempString += character;
	}

	public void enterTyped() {
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

}
