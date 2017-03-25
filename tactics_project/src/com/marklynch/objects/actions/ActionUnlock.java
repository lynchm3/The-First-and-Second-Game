package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.Door;
import com.marklynch.objects.Key;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionUnlock extends Action {

	public static final String ACTION_NAME = "Unlock";
	public static final String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_NEED_KEY = ACTION_NAME + " (need key)";

	Actor performer;
	Door door;

	// Default for hostiles
	public ActionUnlock(Actor unlocker, Door door) {
		super(ACTION_NAME);
		this.performer = unlocker;
		this.door = door;
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
	}

	@Override
	public void perform() {

		Key key = performer.getKeyFor(door);

		door.locked = false;
		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " unlocked ", door, " with ", key }));

		performer.showPow(door);

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
		if (!performer.visibleFrom(door.squareGameObjectIsOn)) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}
		if (performer.straightLineDistanceTo(door.squareGameObjectIsOn) != 1) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}

		if (!performer.hasKeyForDoor(door)) {
			actionName = ACTION_NAME_NEED_KEY;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		if (door.owner != null && door.owner != performer)
			return false;
		return true;
	}

}
