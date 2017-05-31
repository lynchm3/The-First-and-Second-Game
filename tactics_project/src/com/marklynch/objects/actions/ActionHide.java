package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionHide extends Action {

	public static final String ACTION_NAME = "Search";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	HidingPlace object;

	public ActionHide(Actor performer, HidingPlace object) {
		super(ACTION_NAME);
		this.performer = performer;
		this.object = object;
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

		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " searched ", object }));

		ArrayList<GameObject> gameObjectsToLoot = object.search();
		for (GameObject gameObjectToLoot : gameObjectsToLoot) {
			if (performer.squareGameObjectIsOn.visibleToPlayer)
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { performer, " found ", gameObjectToLoot, " in ", object }));
			object.inventory.remove(gameObjectToLoot);
			performer.inventory.add(gameObjectToLoot);
			if (gameObjectToLoot.owner == null)
				gameObjectToLoot.owner = performer;
		}

		if (gameObjectsToLoot.size() == 0)
			if (performer.squareGameObjectIsOn.visibleToPlayer)
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " found nothing" }));

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
		if (performer.straightLineDistanceTo(object.squareGameObjectIsOn) < 2) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkLegality() {
		if (object.owner != null && object.owner != performer)
			return false;
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}