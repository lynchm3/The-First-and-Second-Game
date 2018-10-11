package com.marklynch.ui.popups;

import com.marklynch.objects.actions.Action;
import com.marklynch.ui.button.Tooltip;
import com.marklynch.utils.TextUtils;

public class PopupMenuActionButton extends PopupMenuButton {

	public Action action;

	public PopupMenuActionButton(float x, float y, float width, float height, String enabledTexturePath,
			String disabledTexturePath, String text, boolean xFromLeft, boolean yFromTop, Action action,
			PopupMenu popup) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath, xFromLeft, yFromTop, action, popup, text,
				null);
		this.action = action;

		if (action.disabledReason != null && action.illegalReason != null) {
			this.tooltip = new Tooltip(false, action.disabledReason, TextUtils.NewLine.NEW_LINE, action.illegalReason);
		} else if (action.disabledReason != null) {
			this.tooltip = new Tooltip(false, action.disabledReason);
		} else if (action.illegalReason != null) {
			this.tooltip = new Tooltip(false, action.illegalReason);
		}

	}

	public void drawSound() {
		if (highlighted) {
			if (action.sound != null && action.sound.destinationSquares != null) {
				action.sound.draw();
			}
		}
	}
}
