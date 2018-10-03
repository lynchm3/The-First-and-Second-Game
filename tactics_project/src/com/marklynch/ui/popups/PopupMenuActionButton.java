package com.marklynch.ui.popups;

import com.marklynch.objects.actions.Action;

public class PopupMenuActionButton extends PopupMenuButton {

	public Action action;

	public PopupMenuActionButton(float x, float y, float width, float height, String enabledTexturePath,
			String disabledTexturePath, String text, boolean xFromLeft, boolean yFromTop, Action action,
			PopupMenu popup) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath, xFromLeft, yFromTop, action, popup, text);
		this.action = action;
	}

	public void drawSound() {
		if (highlighted) {
			if (action.sound != null && action.sound.destinationSquares != null) {
				action.sound.draw();
			}
		}
	}
}
