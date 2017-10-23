package com.marklynch.ui;

import com.marklynch.Game;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class TextBox {
	public float caretX;
	public float caretY;
	public boolean caretOn = true;
	public int caretTimer = 0;
	public int caretPositionIndex = 0;
	public static final int CARET_BLINK_TIME = 500;
	public float drawPositionX, drawPositionY;
	public float height = 20;
	public String tempString;
	public int width = 2;

	// public TextBoxHolder parent;

	public TextBox(String tempString, float drawPositionX, float drawPositionY) {

		this.tempString = tempString;
		this.drawPositionX = drawPositionX;
		this.drawPositionY = drawPositionY;

		caretX = drawPositionX + 5;
		caretY = drawPositionX + 5;
		caretPositionIndex = tempString.length();

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
		// Text box
		QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionY, drawPositionX + width + 4, drawPositionY + height);
		// Text string
		TextUtils.printTextWithImages(drawPositionX, drawPositionY, Integer.MAX_VALUE, true, null,
				new Object[] { tempString });
		// Caret
		if (caretOn) {
			QuadUtils.drawQuad(Color.BLACK, drawPositionX + caretPosition, drawPositionY,
					drawPositionX + caretPosition + 2, drawPositionY + height);

		}
	}

	public void keyTyped(char character) {
		tempString = tempString.substring(0, this.caretPositionIndex) + character
				+ tempString.substring(this.caretPositionIndex, tempString.length());
		caretPositionIndex++;
	}

	public void backSpaceTyped() {
		if (tempString.length() > 0 && caretPositionIndex > 0) {
			tempString = tempString.substring(0, this.caretPositionIndex - 1)
					+ tempString.substring(this.caretPositionIndex, tempString.length());
			caretPositionIndex--;
		}
	}

	public void deleteTyped() {
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
