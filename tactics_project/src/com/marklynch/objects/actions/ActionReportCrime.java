package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Guard;
import com.marklynch.ui.ActivityLog;

public class ActionReportCrime extends Action {

	public static final String ACTION_NAME = "Report Crime";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	Guard guard;

	// Default for hostiles
	public ActionReportCrime(Actor performer, Guard guard) {
		super(ACTION_NAME, "action_scream.png");
		super.gameObjectPerformer = this.performer = performer;
		this.guard = guard;
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

		if (Game.level.shouldLog(performer, guard))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " reported crimes to ", guard }));

		for (Crime crime : performer.crimesWitnessedUnresolved) {
			if (!guard.crimesWitnessedUnresolved.contains(crime)) {
				guard.addWitnessedCrime(crime);
				crime.reported = true;
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
		if (performer.straightLineDistanceTo(guard.squareGameObjectIsOn) > 2) {
			return false;
		}
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
