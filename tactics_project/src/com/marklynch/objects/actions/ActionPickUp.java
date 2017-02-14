package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionPickUp extends Action {

	public static final String ACTION_NAME = "Pick Up";

	Actor performer;
	GameObject object;

	public ActionPickUp(Actor performer, GameObject object) {
		super(ACTION_NAME);
		this.performer = performer;
		this.object = object;
	}

	@Override
	public void perform() {

		if (performer.straightLineDistanceTo(object.squareGameObjectIsOn) < 2) {
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " picked up ", object }));
			object.squareGameObjectIsOn.inventory.remove(object);
			performer.inventory.add(object);
		}

	}

	public void pickup(Actor actor, GameObject target) {
	}

}