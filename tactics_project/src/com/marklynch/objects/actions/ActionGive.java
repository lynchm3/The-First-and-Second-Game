package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionGive extends Action {

	public static final String ACTION_NAME = "Give";
	Actor giver;
	GameObject receiver;
	GameObject object;

	public ActionGive(Actor performer, GameObject receiver, GameObject object) {
		super(ACTION_NAME);
		this.giver = performer;
		this.receiver = receiver;
		this.object = object;
	}

	@Override
	public void perform() {
		Game.level.logOnScreen(new ActivityLog(new Object[] { giver, " gave ", object, " to ", receiver }));
		giver.inventory.remove(object);
		receiver.inventory.add(object);
	}
}
