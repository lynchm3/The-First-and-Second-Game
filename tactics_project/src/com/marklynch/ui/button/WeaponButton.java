package com.marklynch.ui.button;

import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.weapons.Weapon;

public class WeaponButton extends ActorButton {

	Weapon weapon;
	public transient Level level;

	public WeaponButton(int x, int y, int width, int height,
			String enabledTexturePath, String disabledTexturePath, Level level,
			Weapon weapon) {
		super(x, y, width, height, enabledTexturePath, disabledTexturePath);

		this.weapon = weapon;
		this.enabled = true;
		this.level = level;
	}

	@Override
	public void click() {
		if (enabled)
			level.activeActor.weaponButtonClicked(weapon);

	}

}
