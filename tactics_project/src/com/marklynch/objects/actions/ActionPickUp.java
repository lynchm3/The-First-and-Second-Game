package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionPickUp extends Action {

	public static final String ACTION_NAME = "Pick Up";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject object;

	public ActionPickUp(Actor performer, GameObject object) {
		super(ACTION_NAME);
		this.performer = performer;
		this.object = object;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " picked up ", object }));
		object.squareGameObjectIsOn.inventory.remove(object);
		performer.inventory.add(object);
	}

	@Override
	public boolean check() {
		if (performer.straightLineDistanceTo(object.squareGameObjectIsOn) < 2) {
			return true;
		}
		return false;
	}

}