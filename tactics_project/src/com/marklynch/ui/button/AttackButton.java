package com.marklynch.ui.button;

import com.marklynch.tactics.objects.level.Level;

public class AttackButton extends ActorButton {

	public transient Level level;

	public AttackButton(int x, int y, int width, int height,
			String enabledTexturePath, String disabledTexturePath, Level level) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath);
		this.enabled = true;
		this.level = level;
		System.out.println("texture  = " + this.enabledTexture);
		System.out.println("texturePath  = " + enabledTexturePath);
	}

	@Override
	public void click() {
		if (enabled)
			level.activeActor.attackClicked();

	}

}
