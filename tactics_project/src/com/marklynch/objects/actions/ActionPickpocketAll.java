package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionPickpocketAll extends Action {

	public static final String ACTION_NAME = "Pickpocket All";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject container;

	public ActionPickpocketAll(Actor performer, GameObject container) {
		super(ACTION_NAME);
		this.performer = performer;
		this.container = container;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		ArrayList<GameObject> gameObjectsToLoot = (ArrayList<GameObject>) container.inventory.getGameObjects().clone();
		for (GameObject gameObjectToLoot : gameObjectsToLoot) {
			Game.level.logOnScreen(
					new ActivityLog(new Object[] { performer, " looted ", gameObjectToLoot, " from ", container }));
			container.inventory.remove(gameObjectToLoot);
			performer.inventory.add(gameObjectToLoot);
		}

	}

	@Override
	public boolean check() {
		if (performer.straightLineDistanceTo(container.squareGameObjectIsOn) < 2) {
			return true;
		}
		return false;
	}

}