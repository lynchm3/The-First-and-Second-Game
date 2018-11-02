package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionStopHiding extends Action {

	public static final String ACTION_NAME = "Stop Hiding";

	HidingPlace hidingPlace;

	public ActionStopHiding(Actor performer, HidingPlace target) {
		super(ACTION_NAME, textureStopHiding, performer, target);
		this.hidingPlace = target;
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
				performer.hidingPlace.gameObjectsHiddenHere.remove(performer);
				performer.hidingPlace = null;

				if (Game.level.shouldLog(hidingPlace, performer))
					Game.level.logOnScreen(
							new ActivityLog(new Object[] { performer, " stopped hiding in ", hidingPlace }));
			} else {

				if (Game.level.shouldLog(hidingPlace, performer))
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
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}