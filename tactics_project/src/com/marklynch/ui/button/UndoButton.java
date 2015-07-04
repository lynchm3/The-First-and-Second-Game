package com.marklynch.ui.button;

import com.marklynch.tactics.objects.level.Level;

public class UndoButton extends Button {

	public UndoButton(int x, int y, int width, int height, String texturePath,
			Level level) {
		super(x, y, width, height, texturePath, level);
	}

	@Override
	public void click() {
		if (enabled)
			level.undo();

	}

}
