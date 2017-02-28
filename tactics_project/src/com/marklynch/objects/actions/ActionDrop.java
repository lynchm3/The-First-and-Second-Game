package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.ui.ActivityLog;

public class ActionDrop extends Action {

	public static final String ACTION_NAME = "Drop";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	GameObject giver;
	Square square;
	GameObject object;

	public ActionDrop(GameObject performer, Square square, GameObject object) {
		super(ACTION_NAME);
		this.giver = performer;
		this.square = square;
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
			Game.level.logOnScreen(new ActivityLog(new Object[] { giver, " dropped ", object }));
		giver.inventory.remove(object);
		// receiver.inventory.add(object);
		square.inventory.add(object);
	}

	@Override
	public boolean check() {
		if (giver.straightLineDistanceTo(square) < 2) {
			return true;
		}
		return false;
	}
}
