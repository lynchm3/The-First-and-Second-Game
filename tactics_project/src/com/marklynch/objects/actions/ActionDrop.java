package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.ui.ActivityLog;

public class ActionDrop extends Action {

	public static final String ACTION_NAME = "Give";
	GameObject giver;
	Square receiver;
	GameObject object;

	public ActionDrop(GameObject performer, Square receiver, GameObject object) {
		super(ACTION_NAME);
		this.giver = performer;
		this.receiver = receiver;
		this.object = object;
	}

	@Override
	public void perform() {
		Game.level.logOnScreen(new ActivityLog(new Object[] { giver, " dropped ", object }));
		giver.inventory.remove(object);
		// receiver.inventory.add(object);
		receiver.inventory.add(object);
	}
}
