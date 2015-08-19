package com.marklynch.ui.button;

import com.marklynch.Game;

public class AttackButton extends ActorButton {

	public AttackButton(int x, int y, int width, int height,
			String enabledTexturePath, String disabledTexturePath) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath);
		this.enabled = true;
	}

	@Override
	public void click() {
		if (enabled)
			Game.level.activeActor.attackClicked();

	}

}
