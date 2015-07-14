package com.marklynch.ui.button;

import com.marklynch.tactics.objects.level.Level;

public class PushButton extends ActorButton {

	public PushButton(int x, int y, int width, int height,
			String enabledTexturePath, String disabledTexturePath, Level level) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath);
		this.enabled = true;
	}

	@Override
	public void click() {
	}

}
