package com.marklynch.ui.button;

import com.marklynch.tactics.objects.level.Level;

public class EndTurnButton extends Button {

	public EndTurnButton(int x, int y, int width, int height,
			String texturePath, Level level) {
		super(x, y, width, height, texturePath, level);
	}

	@Override
	public void click() {
		level.endTurn();
	}

}
