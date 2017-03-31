package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionScream extends Action {

	public static final String ACTION_NAME = "Scream";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;

	// Default for hostiles
	public ActionScream(Actor attacker) {
		super(ACTION_NAME);
		this.performer = attacker;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, "screamed" }));

		// Sound
		float loudness = 10;
		if (performer.equipped != null)
			sound = new Sound(performer, performer, performer.squareGameObjectIsOn, 20, legal, this.getClass());

		performer.actions.add(this);
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

}
