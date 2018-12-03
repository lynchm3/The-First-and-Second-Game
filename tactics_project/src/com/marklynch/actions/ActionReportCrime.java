package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Guard;
import com.marklynch.ui.ActivityLog;

public class ActionReportCrime extends Action {

	public static final String ACTION_NAME = "Report Crime";
	Guard guard;

	// Default for hostiles
	public ActionReportCrime(Actor performer, Guard target) {
		super(ACTION_NAME, textureScream, performer, target);
		super.gameObjectPerformer = this.performer = performer;
		this.guard = target;
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

		if (Game.level.shouldLog(performer, guard))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " reported crimes to ", guard }));

		for (Crime crime : performer.crimesWitnessedUnresolved) {
			if (!guard.crimesWitnessedUnresolved.contains(crime)) {
				guard.addWitnessedCrime(crime);
				crime.reported = true;
			}
		}

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
