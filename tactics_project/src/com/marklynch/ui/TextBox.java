package com.marklynch.ui;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class TextBox {

	public enum TYPE {
		ALL, NUMERIC
	}

	public final TYPE type;

	public float caretX;
	public float caretY;
	public boolean caretOn = true;
	public int caretTimer = 0;
	public int caretPositionIndex = 0;
	public static final int CARET_BLINK_TIME = 500;
	public float drawPositionX, drawPositionY;
	public float height = 20;
	private String text;
	public StringWithColor hintWithColor;
	public float width = 2;

	public TextBoxHolder parent;

	public boolean textHighlighted = false;

	// numeric stuff
	public int numericValue = 0;
	public int maxNumericValue = 0;

	public TextBox(TextBoxHolder parent, String text, String hint, float drawPositionX, float drawPositionY,
			TYPE type) {

		this.parent = parent;
		this.text = text;
		this.hintWithColor = new StringWithColor(hint, Color.GRAY);
		this.drawPositionX = drawPositionX;
		this.drawPositionY = drawPositionY;
		this.type = type;

		caretX = drawPositionX + 5;
		caretY = drawPositionX + 5;
		caretPositionIndex = text.length();

	}

	public void draw() {
		width = Game.windowWidth - drawPositionX;
		// Text box
		if (Level.activeTextBox == this) {
			QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionY, drawPositionX + width + 4,
					drawPositionY + height);
			if (textHighlighted) {
				QuadUtils.drawQuad(Color.BLUE, drawPositionX, drawPositionY, drawPositionX + Game.font.getWidth(text),
						drawPositionY + height);
			}
		} else {
			QuadUtils.drawQuad(Color.BLACK, drawPositionX, drawPositionY, drawPositionX + width + 4,
					drawPositionY + height);
		}

		if (text.length() > 0) {
			// Text string
			TextUtils.printTextWithImages(drawPositionX, drawPositionY, Integer.MAX_VALUE, true, null,
					new Object[] { text });

		} else {
			// Text string
			TextUtils.printTextWithImages(drawPositionX, drawPositionY, Integer.MAX_VALUE, true, null,
					new Object[] { hintWithColor });

		}

		// Caret
		if (Game.level.activeTextBox == this) { // CRASH WHEN TEXT WAS CLEARED
												// BUT CARET WAS NOT
			int caretPosition = Game.font.getWidth(text, 0, caretPositionIndex);
			if (caretOn) {
				QuadUtils.drawQuad(Color.BLACK, drawPositionX + caretPosition, drawPositionY,
						drawPositionX + caretPosition + 2, drawPositionY + height);
			}
		}
	}

	public void keyTyped(char character) {

		if (type == TYPE.NUMERIC && !Character.isDigit(character)) {
			return;
		}

		if (textHighlighted) {
			clearText();
			textHighlighted = false;
		}

		int oldTextLength = text.length();

		text = text.substring(0, this.caretPositionIndex) + character
				+ text.substring(this.caretPositionIndex, text.length());

		cleanupNumeric();

		int newTextLength = text.length();

		if (newTextLength > oldTextLength)
			caretPositionIndex++;

		parent.textChanged(this);
	}

	public void cleanupNumeric() {

		// Numeric info
		if (type == TYPE.NUMERIC) {
			numericValue = 0;
			if (text.length() == 0) {
				numericValue = 0;
			} else {
				try {
					numericValue = Integer.parseInt(text);
				} catch (Exception e) {
					text = "0";
					numericValue = 0;
				}
				if (maxNumericValue != 0) {
					if (numericValue > maxNumericValue) {
						text = "" + maxNumericValue;
					}
				}
			}
		}

	}

	public void backSpaceTyped() {
		if (text.length() > 0 && caretPositionIndex > 0) {

			if (textHighlighted) {
				clearText();
				textHighlighted = false;
			}

			text = text.substring(0, this.caretPositionIndex - 1)
					+ text.substring(this.caretPositionIndex, text.length());
			cleanupNumeric();
			caretPositionIndex--;
			textHighlighted = false;
			parent.textChanged(this);
		}
	}

	public void deleteTyped() {
		if (text.length() > 0 && caretPositionIndex < text.length()) {

			if (textHighlighted) {
				clearText();
				textHighlighted = false;
			}
			text = text.substring(0, this.caretPositionIndex)
					+ text.substring(this.caretPositionIndex + 1, text.length());
			cleanupNumeric();
			textHighlighted = false;
			parent.textChanged(this);
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

	public void moveCaretToEnd() {
		moveCaretTo(text.length());
	}

	public void moveCaretTo(int newPosition) {
		textHighlighted = false;
		if (newPosition < 0 || newPosition > text.length()) {
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
		textHighlighted = false;

		Game.level.activeTextBox = this;

		if (mouseX > drawPositionX && mouseX < drawPositionX + width && mouseY > drawPositionY
				&& mouseY < drawPositionY + height) {

			int clickIndex = 0;
			int relativeX = (int) (mouseX - drawPositionX);
			boolean found = false;
			while (clickIndex < text.length() && !found) {
				if (relativeX < Game.font.getWidth(text, 0, clickIndex)) {
					found = true;
				} else {
					clickIndex++;
				}
			}

			if (clickIndex == 0) {
				this.caretPositionIndex = clickIndex;
			} else {
				int distanceToLower = Math.abs(relativeX - Game.font.getWidth(text, 0, clickIndex - 1));
				int distanceToUpper = Math.abs(relativeX - Game.font.getWidth(text, 0, clickIndex));
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

	public void enterTyped() {
		textHighlighted = false;
		parent.enterTyped(this);
	}

	public void clearText() {
		if (text.length() > 0) {
			text = "";
			cleanupNumeric();
			caretPositionIndex = 0;
			textHighlighted = false;
			parent.textChanged(this);
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		cleanupNumeric();
		textHighlighted = false;
		parent.textChanged(this);
	}

}
