package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.CopyOnWriteArrayList;

public class ActionTakeAll extends Action {

	public static final String ACTION_NAME = "Take All";

	public ActionTakeAll(Actor performer, Square target) {
		super(ACTION_NAME, textureTakeAll, performer, target);
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

		CopyOnWriteArrayList<GameObject> gameObjectsToLoot = (CopyOnWriteArrayList<GameObject>) targetSquare.inventory
				.getGameObjectsThatFitInInventory();
		for (GameObject gameObjectToLoot : gameObjectsToLoot) {
			if (Game.level.shouldLog(gameObjectToLoot, performer))
				if (legal)
					Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " took ", gameObjectToLoot }));
				else
					Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " stole ", gameObjectToLoot }));
			targetSquare.inventory.remove(gameObjectToLoot);
			performer.inventory.add(gameObjectToLoot);
			if (gameObjectToLoot.owner == null)
				gameObjectToLoot.owner = performer;
		}
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
		return true;
	}

	@Override
	public boolean checkRange() {
		if (performer.straightLineDistanceTo(targetSquare) < 2) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkLegality() {
		CopyOnWriteArrayList<GameObject> gameObjectsToLoot = (CopyOnWriteArrayList<GameObject>) targetSquare.inventory.getGameObjects()
				;
		for (GameObject gameObjectToLoot : gameObjectsToLoot) {
			if (gameObjectToLoot.owner != null && gameObjectToLoot.owner != performer) {
				illegalReason = THEFT;
				if (gameObjectToLoot.value > 100)
					illegalReason = GRAND_THEFT;
				return false;
			}
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}