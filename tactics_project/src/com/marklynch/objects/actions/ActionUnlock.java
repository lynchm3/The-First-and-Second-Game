package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.Key;
import com.marklynch.objects.Openable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionUnlock extends Action {

	public static final String ACTION_NAME = "Unlock";
	public static final String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_NEED_KEY = ACTION_NAME + " (need key)";

	Actor performer;
	Openable openable;

	// Default for hostiles
	public ActionUnlock(Actor unlocker, Openable openable) {
		super(ACTION_NAME);
		this.performer = unlocker;
		this.openable = openable;
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		Key key = performer.getKeyFor(openable);

		openable.unlock();
		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " unlocked ", openable, " with ", key }));

		performer.showPow(openable);

		if (performer.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {
			Crime crime = new Crime(this, this.performer, openable.owner, 4, key);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		}
	}

	@Override
	public boolean check() {
		if (!performer.canSeeGameObject(openable)) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}
		if (performer.straightLineDistanceTo(openable.squareGameObjectIsOn) != 1) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}

		if (!performer.hasKeyForDoor(openable)) {
			actionName = ACTION_NAME_NEED_KEY;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		if (openable.owner != null && openable.owner != performer) {
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return new Sound(performer, openable, openable.squareGameObjectIsOn, 1, legal, this.getClass());
	}

}
