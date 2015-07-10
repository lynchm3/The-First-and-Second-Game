package com.marklynch.ui.button;

import com.marklynch.tactics.objects.level.Level;

public class AttackButton extends ActorButton {

	public AttackButton(int x, int y, int width, int height,
			String enabledTexturePath, String disabledTexturePath, Level level) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath,
				level);
		this.enabled = true;
	}

	@Override
	public void click() {
		if (enabled)
			level.activeActor.attackClicked();

	}

}
