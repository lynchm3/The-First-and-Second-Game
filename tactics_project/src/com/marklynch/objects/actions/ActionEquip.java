package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.BodyArmor;
import com.marklynch.objects.weapons.Helmet;
import com.marklynch.objects.weapons.LegArmor;
import com.marklynch.ui.ActivityLog;

public class ActionEquip extends Action {

	public static final String ACTION_NAME = "Equip";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (unequippable)";

	Actor performer;
	GameObject gameObject;

	public ActionEquip(Actor performer, GameObject gameObject) {
		super(ACTION_NAME, "action_equip.png");
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

		if (gameObject instanceof Helmet) {
			performer.helmet = (Helmet) gameObject;
		} else if (gameObject instanceof BodyArmor) {
			performer.bodyArmor = (BodyArmor) gameObject;
		} else if (gameObject instanceof LegArmor) {
			performer.legArmor = (LegArmor) gameObject;
		} else {
			performer.equip(gameObject);
		}

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
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