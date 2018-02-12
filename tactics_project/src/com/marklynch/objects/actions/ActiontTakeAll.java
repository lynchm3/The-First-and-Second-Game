package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Openable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActiontTakeAll extends Action {

	public static final String ACTION_NAME = "Loot All";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject container;
	ActionOpen actionOpen;

	public ActiontTakeAll(Actor performer, GameObject container) {
		super(ACTION_NAME, "action_loot_all.png");
		this.performer = performer;
		this.container = container;
		if (container instanceof Openable && !((Openable) container).isOpen()) {
			actionOpen = new ActionOpen(performer, (Openable) container);
		}
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

		if (actionOpen != null) {
			actionOpen.perform();
		}

		ArrayList<GameObject> gameObjectsToLoot = (ArrayList<GameObject>) container.inventory.getGameObjects().clone();
		for (GameObject gameObjectToLoot : gameObjectsToLoot) {

			if (Game.level.shouldLog(gameObjectToLoot, performer)) {
				if (gameObjectToLoot.owner == null || gameObjectToLoot.owner == performer) {
					Game.level.logOnScreen(new ActivityLog(
							new Object[] { performer, " took ", gameObjectToLoot, " from ", container }));
				} else {
					Game.level.logOnScreen(new ActivityLog(
							new Object[] { performer, " stole ", gameObjectToLoot, " from ", container }));
				}
			}
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

			ArrayList<GameObject> stolenObjects = new ArrayList<GameObject>();
			for (GameObject gameObjectToLoot : gameObjectsToLoot) {
				if (gameObjectToLoot.owner != null && gameObjectToLoot.owner != performer) {
					stolenObjects.add(gameObjectToLoot);
				}
			}
			if (stolenObjects.size() > 0) {
				Crime crime = new Crime(this, this.performer, stolenObjects.get(0).owner, Crime.TYPE.CRIME_THEFT,
						stolenObjects);
				this.performer.crimesPerformedThisTurn.add(crime);
				this.performer.crimesPerformedInLifetime.add(crime);
				notifyWitnessesOfCrime(crime);
			}
		}
	}

	@Override
	public boolean check() {

		if (actionOpen != null) {
			return actionOpen.check();
		}
		return false;
	}

	@Override
	public boolean checkRange() {

		if (actionOpen != null) {
			return actionOpen.checkRange();
		}

		if (performer.straightLineDistanceTo(container.squareGameObjectIsOn) < 2) {
			return true;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		if (actionOpen != null) {
			if (!actionOpen.legal)
				return false;
		}

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