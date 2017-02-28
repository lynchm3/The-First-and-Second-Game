package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionGive extends Action {

	public static final String ACTION_NAME = "Give";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor giver;
	GameObject receiver;
	GameObject object;

	public ActionGive(Actor performer, GameObject receiver, GameObject object) {
		super(ACTION_NAME);
		this.giver = performer;
		this.receiver = receiver;
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
		if (giver.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { giver, " gave ", object, " to ", receiver }));
		giver.inventory.remove(object);
		receiver.inventory.add(object);
	}

	@Override
	public boolean check() {
		if (giver.straightLineDistanceTo(receiver.squareGameObjectIsOn) < 2) {
			return true;
		}
		return false;
	}
}
