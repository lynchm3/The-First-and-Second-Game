package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionPut extends Action {

	public static final String ACTION_NAME = "Give";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor performer;
	GameObject receiver;
	GameObject object;

	public ActionPut(Actor performer, GameObject receiver, GameObject object) {
		super(ACTION_NAME);
		this.performer = performer;
		this.receiver = receiver;
		this.object = object;
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
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " put ", object, " in ", receiver }));
		performer.inventory.remove(object);
		receiver.inventory.add(object);
		if (receiver instanceof Actor) {
			object.owner = (Actor) receiver;
		}
		performer.actions.add(this);
	}

	@Override
	public boolean check() {
		if (performer.straightLineDistanceTo(receiver.squareGameObjectIsOn) < 2) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}
}
