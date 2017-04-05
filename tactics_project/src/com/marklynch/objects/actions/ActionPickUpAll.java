package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionPickUpAll extends Action {

	public static final String ACTION_NAME = "Pick Up All";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	Square square;

	public ActionPickUpAll(Actor performer, Square square) {
		super(ACTION_NAME);
		this.performer = performer;
		this.square = square;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		ArrayList<GameObject> gameObjectsToLoot = (ArrayList<GameObject>) square.inventory
				.getGameObjectsThatFitInInventory().clone();
		for (GameObject gameObjectToLoot : gameObjectsToLoot) {
			if (performer.squareGameObjectIsOn.visibleToPlayer)
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " picked up ", gameObjectToLoot }));
			square.inventory.remove(gameObjectToLoot);
			performer.inventory.add(gameObjectToLoot);
			if (gameObjectToLoot.owner == null)
				gameObjectToLoot.owner = performer;
		}
		performer.actionsPerformedThisTurn.add(this);if (sound != null)sound.play();

	}

	@Override
	public boolean check() {
		if (performer.straightLineDistanceTo(square) < 2) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkLegality() {
		ArrayList<GameObject> gameObjectsToLoot = (ArrayList<GameObject>) square.inventory.getGameObjects().clone();
		for (GameObject gameObjectToLoot : gameObjectsToLoot) {
			if (gameObjectToLoot.owner != null && gameObjectToLoot.owner != performer)
				return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}