package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionUnequip extends Action {

	public static final String ACTION_NAME = "Unequip";

	public ActionUnequip(Actor performer, GameObject target) {
		super(ACTION_NAME, textureX, performer, target);
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " unequipped ", target }));

		if (performer.equipped == target)
			performer.equipped = null;
		else if (performer.helmet == target)
			performer.helmet = null;
		else if (performer.bodyArmor == target)
			performer.bodyArmor = null;
		else if (performer.legArmor == target)
			performer.legArmor = null;

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {

		System.out.println("unequip.check()");
		System.out.println("performer  = " + performer);
		System.out.println("performer.equipped  = " + performer.equipped);

		if (performer.equipped == target)
			return true;

		if (performer.helmet == target)
			return true;

		if (performer.bodyArmor == target)
			return true;

		if (performer.legArmor == target)
			return true;

		return false;
	}

	@Override
	public boolean checkRange() {
		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}