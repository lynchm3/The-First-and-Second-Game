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
	public int caretPositionIndex = 0;
	public static final int CARET_BLINK_TIME = 500;
	public String instructions;
	public int width = 2;

	public PopupTextBox(GameObject gameObject, String instructions) {
		this.gameObject = gameObject;
		this.instructions = instructions;
		tempString = new String(((MapMarker) gameObject).baseName);
		caretPositionIndex = tempString.length();
		drawPositionX = 300;
		drawPositionY = 300;
		caretX = drawPositionX + 5;
		caretY = drawPositionX + 5;
		// this.width = 200;
		this.height = 20;
	}

	public void draw() {
		width = Game.font.getWidth(tempString);
		// if(tempString.length() > 0)
		// {
		// int caretPosition =
		// }
		// else
		// {
		int caretPosition = Game.font.getWidth(tempString, 0, caretPositionIndex);
		// }

		// Full blakc bg
		QuadUtils.drawQuad(new Color(0f, 0f, 0f, 0.5f), 0, Game.windowWidth, 0, Game.windowHeight);
		// Instructions
		TextUtils.printTextWithImages(drawPositionX, drawPositionY - 36, Integer.MAX_VALUE, true,
				new Object[] { instructions });
		// Text box
		QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionX + width + 4, drawPositionY, drawPositionY + height);
		// Text string
		TextUtils.printTextWithImages(drawPositionX, drawPositionY, Integer.MAX_VALUE, true,
				new Object[] { tempString });
		// Caret
		if (caretOn) {
			QuadUtils.drawQuad(Color.BLACK, drawPositionX + caretPosition, drawPositionX + caretPosition + 2,
					drawPositionY, drawPositionY + height);

		}
	}

	public void keyTyped(char character) {
		if (tempString.equals(MapMarker.NO_DESCRIPTION)) {
			tempString = "";
			caretPositionIndex = 0;
		}
		tempString = tempString.substring(0, this.caretPositionIndex) + character
				+ tempString.substring(this.caretPositionIndex, tempString.length());
		caretPositionIndex++;
	}

	public void enterTyped() {
		if (tempString.length() == 0)
			tempString = MapMarker.NO_DESCRIPTION;
		((MapMarker) gameObject).baseName = tempString;
		gameObject.name = "Marker: " + tempString;
		Game.level.popupTextBoxes.clear();

	}

	public void backSpacedTyped() {
		if (tempString.equals(MapMarker.NO_DESCRIPTION)) {
			tempString = "";
			caretPositionIndex = 0;
		}
		if (tempString.length() > 0 && caretPositionIndex > 0) {
			tempString = tempString.substring(0, this.caretPositionIndex - 1)
					+ tempString.substring(this.caretPositionIndex, tempString.length());
			caretPositionIndex--;
		}
	}

	public void deleteTyped() {
		if (tempString.equals(MapMarker.NO_DESCRIPTION)) {
			tempString = "";
			caretPositionIndex = 0;
		}
		if (tempString.length() > 0 && caretPositionIndex < tempString.length()) {
			tempString = tempString.substring(0, this.caretPositionIndex)
					+ tempString.substring(this.caretPositionIndex + 1, tempString.length());
		}
	}

	public void updateRealtime(int delta) {
		caretTimer += delta;
		if (caretTimer >= CARET_BLINK_TIME) {
			caretOn = !caretOn;
			caretTimer -= CARET_BLINK_TIME;
		}
	}

	public void moveCaretLeft() {
		this.moveCaretTo(caretPositionIndex - 1);
	}

	public void moveCaretRight() {
		this.moveCaretTo(caretPositionIndex + 1);

	}

	public void moveCaretTo(int newPosition) {
		if (newPosition < 0 || newPosition > tempString.length()) {
			return;
		}
		caretPositionIndex = newPosition;
		caretOn = true;
		caretTimer = 0;
	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		if (mouseX > drawPositionX && mouseX < drawPositionX + width && mouseY > drawPositionY
				&& mouseY < drawPositionY + height) {
			return true;
		}
		return false;
	}

	public boolean click(int mouseX, int mouseY) {
		if (mouseX > drawPositionX && mouseX < drawPositionX + width && mouseY > drawPositionY
				&& mouseY < drawPositionY + height) {

			int clickIndex = 0;
			int relativeX = (int) (mouseX - drawPositionX);
			boolean found = false;
			while (clickIndex < tempString.length() && !found) {
				if (relativeX < Game.font.getWidth(tempString, 0, clickIndex)) {
					found = true;
				} else {
					clickIndex++;
				}
			}

			if (clickIndex == 0) {
				this.caretPositionIndex = clickIndex;
			} else {
				int distanceToLower = Math.abs(relativeX - Game.font.getWidth(tempString, 0, clickIndex - 1));
				int distanceToUpper = Math.abs(relativeX - Game.font.getWidth(tempString, 0, clickIndex));
				if (distanceToLower <= distanceToUpper)
					this.caretPositionIndex = clickIndex - 1;
				else
					this.caretPositionIndex = clickIndex;
			}
			caretOn = true;
			caretTimer = 0;

		}
		return false;
	}

}
