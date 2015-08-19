package com.marklynch.ui.button;

import com.marklynch.Game;
import com.marklynch.tactics.objects.weapons.Weapon;

public class WeaponButton extends ActorButton {

	Weapon weapon;

	public WeaponButton(int x, int y, int width, int height,
			String enabledTexturePath, String disabledTexturePath, Weapon weapon) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath);

		this.weapon = weapon;
		this.enabled = true;
	}

	@Override
	public void click() {
		if (enabled)
			Game.level.activeActor.weaponButtonClicked(weapon);

	}

}
