package com.marklynch.level.popup;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;

public class PopupActionButton extends PopupButton {

	public Action action;

	public PopupActionButton(float x, float y, float width, float height, String enabledTexturePath,
			String disabledTexturePath, String text, boolean xFromLeft, boolean yFromTop, Action action, Popup popup) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath, xFromLeft, yFromTop, action, popup, text);
		this.action = action;
	}

	@Override
	public void highlight() {
		super.highlight();
		if (action.sound != null) {
			for (Square square : action.sound.destinationSquares) {
				square.highlight = true;
			}
		}

	}

	@Override
	public void removeHighlight() {
		super.removeHighlight();
		if (action.sound != null) {
			for (Square square : action.sound.destinationSquares) {
				square.highlight = false;
			}
		}
	}

}
