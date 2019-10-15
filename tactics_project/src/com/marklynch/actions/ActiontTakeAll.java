package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Openable;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.CopyOnWriteArrayList;

public class ActiontTakeAll extends Action {

	public static final String ACTION_NAME = "Loot All";

	ActionOpen actionOpen;

	public ActiontTakeAll(Actor performer, GameObject target) {
		super(ACTION_NAME, textureLootAll, performer, target);
		if (target instanceof Openable && !((Openable) target).isOpen()) {
			actionOpen = new ActionOpen(performer, (Openable) target);
		}
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (actionOpen != null) {
			actionOpen.perform();
		}

		CopyOnWriteArrayList<GameObject> gameObjectsToLoot = (CopyOnWriteArrayList<GameObject>) targetGameObject.inventory.getGameObjects();
		for (GameObject gameObjectToLoot : gameObjectsToLoot) {

			if (Game.level.shouldLog(gameObjectToLoot, performer)) {
				if (gameObjectToLoot.owner == null || gameObjectToLoot.owner == performer) {
					Game.level.logOnScreen(
							new ActivityLog(new Object[] { performer, " took ", gameObjectToLoot, " from ", targetGameObject }));
				} else {
					Game.level.logOnScreen(
							new ActivityLog(new Object[] { performer, " stole ", gameObjectToLoot, " from ", targetGameObject }));
				}
			}
			targetGameObject.inventory.remove(gameObjectToLoot);
			performer.inventory.add(gameObjectToLoot);
			if (gameObjectToLoot.owner == null)
				gameObjectToLoot.owner = performer;
		}
		targetGameObject.looted();
		if (sound != null)
			sound.play();

		if (!legal) {

			CopyOnWriteArrayList<GameObject> stolenObjects = new CopyOnWriteArrayList<GameObject>(GameObject.class);
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
			disabledReason = actionOpen.disabledReason;
			return actionOpen.check();
		}
		return true;
	}

	@Override
	public boolean checkRange() {

		if (actionOpen != null) {
			return actionOpen.checkRange();
		}

		if (performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) < 2) {
			return true;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		if (actionOpen != null) {
			if (!actionOpen.legal) {
				illegalReason = actionOpen.illegalReason;
				return false;
			}
		}

		CopyOnWriteArrayList<GameObject> gameObjectsToLoot = (CopyOnWriteArrayList<GameObject>) targetGameObject.inventory.getGameObjects();
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