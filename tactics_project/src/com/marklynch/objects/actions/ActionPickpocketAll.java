package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionPickpocketAll extends Action {

	public static final String ACTION_NAME = "Pickpocket All";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject container;

	public ActionPickpocketAll(Actor performer, GameObject container) {
		super(ACTION_NAME, "action_pickpocket_all.png");
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
				Crime crime = new Crime(this, this.performer, stolenObjects.get(0).owner, 2, stolenObjects);
				this.performer.crimesPerformedThisTurn.add(crime);
				this.performer.crimesPerformedInLifetime.add(crime);
				notifyWitnessesOfCrime(crime);
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
		return false;
	}

	@Override
	public Sound createSound() {
		// TODO Auto-generated method stub
		return null;
	}

}