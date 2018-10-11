package com.marklynch.ui.popups;

import com.marklynch.objects.actions.Action;
import com.marklynch.ui.button.Tooltip;
import com.marklynch.ui.button.TooltipGroup;

public class PopupMenuActionButton extends PopupMenuButton {

	public Action action;

	public PopupMenuActionButton(float x, float y, float width, float height, String enabledTexturePath,
			String disabledTexturePath, String text, boolean xFromLeft, boolean yFromTop, Action action,
			PopupMenu popup) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath, xFromLeft, yFromTop, action, popup, text,
				null);
		this.action = action;

		if (action.disabledReason != null) {
			if (tooltipGroup == null)
				tooltipGroup = new TooltipGroup();

			this.tooltipGroup.add(new Tooltip(false, action.disabledReason));
		}

		if (action.illegalReason != null) {
			if (tooltipGroup == null)
				tooltipGroup = new TooltipGroup();
			this.tooltipGroup.add(new Tooltip(false, action.illegalReason));
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
