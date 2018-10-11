package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionTakeAll extends Action {

	public static final String ACTION_NAME = "Take All";

	Actor performer;
	Square square;

	public ActionTakeAll(Actor performer, Square square) {
		super(ACTION_NAME, "action_take_all.png");
		super.gameObjectPerformer = this.performer = performer;
		this.square = square;
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

		ArrayList<GameObject> gameObjectsToLoot = (ArrayList<GameObject>) square.inventory
				.getGameObjectsThatFitInInventory().clone();
		for (GameObject gameObjectToLoot : gameObjectsToLoot) {
			if (Game.level.shouldLog(gameObjectToLoot, performer))
				if (legal)
					Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " took ", gameObjectToLoot }));
				else
					Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " stole ", gameObjectToLoot }));
			square.inventory.remove(gameObjectToLoot);
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
		if (performer.straightLineDistanceTo(square) < 2) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkLegality() {
		ArrayList<GameObject> gameObjectsToLoot = (ArrayList<GameObject>) square.inventory.getGameObjects().clone();
		for (GameObject gameObjectToLoot : gameObjectsToLoot) {
			if (gameObjectToLoot.owner != null && gameObjectToLoot.owner != performer) {
				illegalReason = THEFT;
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