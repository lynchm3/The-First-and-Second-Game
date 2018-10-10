package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionStopHiding extends Action {

	public static final String ACTION_NAME = "Stop Hiding";

	Actor performer;
	HidingPlace object;

	public ActionStopHiding(Actor performer, HidingPlace object) {
		super(ACTION_NAME, "action_stop_hiding.png");
		super.gameObjectPerformer = this.performer = performer;
		this.object = object;
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

		if (performer.hiding == true) {
			performer.hiding = false;
			if (performer.hidingPlace != null) {
				performer.hidingPlace.actorsHidingHere.remove(performer);
				performer.hidingPlace = null;

				if (Game.level.shouldLog(object, performer))
					Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " stopped hiding in ", object }));
			} else {

				if (Game.level.shouldLog(object, performer))
					Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " stopped hiding" }));
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
	public boolean checkRange() {
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