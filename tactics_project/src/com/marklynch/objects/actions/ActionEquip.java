package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionEquip extends Action {

	public static final String ACTION_NAME = "Equip";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (unequippable)";

	Actor performer;
	GameObject gameObject;

	public ActionEquip(Actor performer, GameObject gameObject) {
		super(ACTION_NAME);
		this.performer = performer;
		this.gameObject = gameObject;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " equipped ", gameObject }));
		performer.equipped = gameObject;
		performer.actions.add(this);
	}

	@Override
	public boolean check() {

		if (performer.inventory.contains(gameObject))
			return true;

		if (performer.straightLineDistanceTo(gameObject.squareGameObjectIsOn) < 2)
			return true;

		return false;
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