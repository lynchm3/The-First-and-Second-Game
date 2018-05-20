package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionUnequip extends Action {

	public static final String ACTION_NAME = "Unequip";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (unequippable)";

	Actor performer;
	GameObject gameObject;

	public ActionUnequip(Actor performer, GameObject gameObject) {
		super(ACTION_NAME, "x.png");
		super.gameObjectPerformer = this.performer = performer;
		this.gameObject = gameObject;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " unequipped ", gameObject }));

		if (performer.equipped == gameObject)
			performer.equipped = null;
		else if (performer.helmet == gameObject)
			performer.helmet = null;
		else if (performer.bodyArmor == gameObject)
			performer.bodyArmor = null;
		else if (performer.legArmor == gameObject)
			performer.legArmor = null;

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {

		if (performer.equipped == gameObject)
			return true;

		if (performer.helmet == gameObject)
			return true;

		if (performer.bodyArmor == gameObject)
			return true;

		if (performer.legArmor == gameObject)
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