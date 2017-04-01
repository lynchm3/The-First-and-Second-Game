package com.marklynch.level.popup;

import com.marklynch.level.Square;
import com.marklynch.objects.actions.Action;

public class PopupActionButton extends PopupButton {

	public Action action;

	public PopupActionButton(float x, float y, float width, float height, String enabledTexturePath,
			String disabledTexturePath, String text, boolean xFromLeft, boolean yFromTop, Action action, Popup popup) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath, text, xFromLeft, yFromTop, action, popup);
		this.action = action;
	}

	@Override
	public void highlight() {
		super.highlight();
		System.out.println("PopupActionButton.highlight()");
		if (action.sound != null) {
			System.out.println("PopupActionButton.highlight() action.sound != null");
			for (Square square : action.sound.destinationSquares) {
				square.highlight = true;
				System.out.println("PopupActionButton.highlight() highlighting square");
			}
		}

	}

	@Override
	public void removeHighlight() {
		super.removeHighlight();
		System.out.println("PopupActionButton.removeHighlight()");
		if (action.sound != null) {
			System.out.println("PopupActionButton.removeHighlight() action.sound != null");
			for (Square square : action.sound.destinationSquares) {
				System.out.println("PopupActionButton.removeHighlight() removing highlight on square");
				square.highlight = false;
			}
		}
	}

}
