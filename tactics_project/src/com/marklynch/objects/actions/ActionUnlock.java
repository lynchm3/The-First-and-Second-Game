package com.marklynch.objects.actions;

import com.marklynch.Game;
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
	}

	@Override
	public void perform() {

		Key key = performer.getKeyFor(openable);

		openable.unlock();
		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " unlocked ", openable, " with ", key }));

		performer.showPow(openable);

		// Sound
		float loudness = 1;
		sound = new Sound(performer, key, performer.squareGameObjectIsOn, loudness, legal, this.getClass());

		if (performer.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}

		if (performer == Game.level.player)
			Game.level.endTurn();

		performer.actions.add(this);
	}

	@Override
	public boolean check() {
		if (!performer.visibleFrom(openable.squareGameObjectIsOn)) {
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
		System.out.println("openable.owner = " + openable.owner);
		if (openable.owner != null && openable.owner != performer) {
			System.out.println("checkLegality = false");
			return false;
		}
		System.out.println("checkLegality = true");
		return true;
	}

}
