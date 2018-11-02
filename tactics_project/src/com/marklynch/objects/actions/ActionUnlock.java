package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Key;
import com.marklynch.objects.Openable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionUnlock extends Action {

	public static final String ACTION_NAME = "Unlock";

	Openable openable;

	// Default for hostiles
	public ActionUnlock(GameObject unlocker, Openable openable) {
		super(ACTION_NAME, textureUnlock, unlocker, openable);
		this.openable = openable;
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

		if (gameObjectPerformer instanceof Actor) {
			Actor actor = (Actor) gameObjectPerformer;
			Key key = actor.getKeyFor(openable);

			openable.unlock();
			if (Game.level.shouldLog(gameObjectPerformer))
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { gameObjectPerformer, " unlocked ", openable, " with ", key }));

			openable.showPow();

			if (actor.faction == Game.level.factions.player) {
				Game.level.undoList.clear();
			}

			if (!legal) {
				Crime crime = new Crime(this, actor, openable.owner, Crime.TYPE.CRIME_THEFT, key);
				actor.crimesPerformedThisTurn.add(crime);
				actor.crimesPerformedInLifetime.add(crime);
				notifyWitnessesOfCrime(crime);
			}
		} else {

			openable.unlock();
			if (Game.level.shouldLog(gameObjectPerformer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { gameObjectPerformer, " unlocked ", openable }));

			openable.showPow();
		}

		gameObjectPerformer.actionsPerformedThisTurn.add(this);

		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {

		if (gameObjectPerformer instanceof Actor) {
			Actor actor = (Actor) gameObjectPerformer;

			if (!actor.hasKeyForDoor(openable)) {
				disabledReason = NEED_A_KEY;
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (gameObjectPerformer instanceof Actor) {
			Actor actor = (Actor) gameObjectPerformer;
			if (!actor.canSeeGameObject(openable)) {
				return false;
			}
			if (actor.straightLineDistanceTo(openable.squareGameObjectIsOn) != 1) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		if (openable.owner != null && openable.owner != gameObjectPerformer) {
			illegalReason = TRESPASSING;
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return new Sound(gameObjectPerformer, openable, openable.squareGameObjectIsOn, 1, legal, this.getClass());
	}

}
