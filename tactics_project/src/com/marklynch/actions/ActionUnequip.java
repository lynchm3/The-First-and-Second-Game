package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
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
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " unequipped ", targetGameObject }));

		if (performer.equipped == targetGameObject)
			performer.equipped = null;
		else if (performer.helmet == targetGameObject)
			performer.helmet = null;
		else if (performer.bodyArmor == targetGameObject)
			performer.bodyArmor = null;
		else if (performer.legArmor == targetGameObject)
			performer.legArmor = null;

		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {

		if (performer.equipped == targetGameObject)
			return true;

		if (performer.helmet == targetGameObject)
			return true;

		if (performer.bodyArmor == targetGameObject)
			return true;

		if (performer.legArmor == targetGameObject)
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