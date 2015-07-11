package com.marklynch.ui.button;

import com.marklynch.tactics.objects.level.Level;

public class UndoButton extends LevelButton {

	public UndoButton(float x, float y, float width, float height,
			String enabledTexturePath, String disabledTexturePath, Level level,
			boolean xFromLeft, boolean yFromTop) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath,
				level, xFromLeft, yFromTop);
	}

	@Override
	public void click() {
		if (enabled)
			level.undo();
	}
}
