package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionTalk extends Action {

	public static final String ACTION_NAME = "Talk";

	// Default for hostiles
	public ActionTalk(Actor performer, Actor targetActor) {
		super(ACTION_NAME, performer, targetActor);
	}

	@Override
	public void perform() {

		Game.level.logOnScreen(new ActivityLog(new Object[] { "Talking to ", target }));

	}

}
