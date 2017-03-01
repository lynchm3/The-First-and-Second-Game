package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionSpot extends Action {

	public static final String ACTION_NAME = "Spot";

	public Actor spotter;
	public Object spotted;

	public ActionSpot(Actor spotter, Object spotted) {
		super(ACTION_NAME);
		this.spotter = spotter;
		this.spotted = spotted;
	}

	@Override
	public void perform() {
		if (spotter.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { spotter, " spotted ", spotted }));
	}

}
