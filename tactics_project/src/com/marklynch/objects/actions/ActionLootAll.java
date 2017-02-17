package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionLootAll extends Action {

	public static final String ACTION_NAME = "Pick Up";

	Actor performer;
	GameObject container;

	public ActionLootAll(Actor performer, GameObject container) {
		super(ACTION_NAME);
		this.performer = performer;
		this.container = container;
	}

	@Override
	public void perform() {
		if (performer.straightLineDistanceTo(container.squareGameObjectIsOn) > 1) {
			return;
		}
		ArrayList<GameObject> gameObjectsToLoot = (ArrayList<GameObject>) container.inventory.getGameObjects().clone();
		for (GameObject gameObjectToLoot : gameObjectsToLoot) {
			Game.level.logOnScreen(
					new ActivityLog(new Object[] { performer, " looted ", gameObjectToLoot, " from ", container }));
			container.inventory.remove(gameObjectToLoot);
			performer.inventory.add(gameObjectToLoot);
		}

	}

	public void lootAll(GameObject gameObject) {
	}

	public void pickup(Actor actor, GameObject target) {
	}

}