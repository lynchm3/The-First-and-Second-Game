package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionStopHiding extends Action {

	public static final String ACTION_NAME = "Stop Hiding";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	HidingPlace object;

	public ActionStopHiding(Actor performer, HidingPlace object) {
		super(ACTION_NAME, "action_stop_hiding.png");
		this.performer = performer;
		this.object = object;
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (performer.hiding == true) {
			performer.hiding = false;
			performer.hidingPlace.actorsHidingHere.remove(performer);
			performer.hidingPlace = null;
			if (performer.squareGameObjectIsOn.visibleToPlayer) {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " stopped hiding in ", object }));
			}
		}

		performer.actionsPerformedThisTurn.add(this);

		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkLegality() {
		// if (object.squareGameObjectIsOn.restricted == true &&
		// !object.squareGameObjectIsOn.owners.contains(performer)) {
		// return false;
		// }
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}