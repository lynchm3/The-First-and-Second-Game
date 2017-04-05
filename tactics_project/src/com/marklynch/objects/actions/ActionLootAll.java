package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Openable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionLootAll extends Action {

	public static final String ACTION_NAME = "Loot All";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject container;

	public ActionLootAll(Actor performer, GameObject container) {
		super(ACTION_NAME);
		this.performer = performer;
		this.container = container;
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

		if (container instanceof Openable) {
			Openable openable = (Openable) container;
			if (!openable.isOpen()) {
				openable.open();
			}
		}

		ArrayList<GameObject> gameObjectsToLoot = (ArrayList<GameObject>) container.inventory.getGameObjects().clone();
		for (GameObject gameObjectToLoot : gameObjectsToLoot) {
			if (performer.squareGameObjectIsOn.visibleToPlayer)
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { performer, " looted ", gameObjectToLoot, " from ", container }));
			container.inventory.remove(gameObjectToLoot);
			performer.inventory.add(gameObjectToLoot);
			if (gameObjectToLoot.owner == null)
				gameObjectToLoot.owner = performer;
		}
		container.looted();
		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {

			for (GameObject gameObjectToLoot : gameObjectsToLoot) {
				if (gameObjectToLoot.owner != null && gameObjectToLoot.owner != performer) {
					Crime crime = new Crime(this, this.performer, gameObjectToLoot.owner, 2, gameObjectToLoot);
					this.performer.crimesPerformedThisTurn.add(crime);
					this.performer.crimesPerformedInLifetime.add(crime);
					notifyWitnessesOfCrime(crime);
				}
			}
		}
	}

	@Override
	public boolean check() {
		if (performer.straightLineDistanceTo(container.squareGameObjectIsOn) < 2) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkLegality() {
		ArrayList<GameObject> gameObjectsToLoot = (ArrayList<GameObject>) container.inventory.getGameObjects().clone();
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