package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLog;

public class ActionEquip extends Action {

	public static final String ACTION_NAME = "Equip";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (unequippable)";

	Actor performer;
	Weapon weapon;

	public ActionEquip(Actor performer, Weapon weapon) {
		super(ACTION_NAME);
		this.performer = performer;
		this.weapon = weapon;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " equipped ", weapon }));
		performer.equippedWeapon = weapon;
	}

	@Override
	public boolean check() {

		if (performer.inventory.contains(weapon))
			return true;

		return false;
	}

}