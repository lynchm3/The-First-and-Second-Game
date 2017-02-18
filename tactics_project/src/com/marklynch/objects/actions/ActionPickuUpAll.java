package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionPickuUpAll extends Action {

	public static final String ACTION_NAME = "Pick Up All";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	Square square;

	public ActionPickuUpAll(Actor performer, Square square) {
		super(ACTION_NAME);
		this.performer = performer;
		this.square = square;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		ArrayList<GameObject> gameObjectsToLoot = (ArrayList<GameObject>) square.inventory
				.getGameObjectsThatFitInInventory().clone();
		for (GameObject gameObjectToLoot : gameObjectsToLoot) {
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " picked up ", gameObjectToLoot }));
			square.inventory.remove(gameObjectToLoot);
			performer.inventory.add(gameObjectToLoot);
		}

	}

	@Override
	public boolean check() {
		if (performer.straightLineDistanceTo(square) < 2) {
			return true;
		}
		return false;
	}

}