package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.Door;
import com.marklynch.objects.Key;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionLock extends Action {

	public static final String ACTION_NAME = "Lock";
	public static final String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_NEED_KEY = ACTION_NAME + " (need key)";

	Actor unlocker;
	Door door;

	// Default for hostiles
	public ActionLock(Actor unlocker, Door door) {
		super(ACTION_NAME);
		this.unlocker = unlocker;
		this.door = door;
		if (!check()) {
			enabled = false;
		}
	}

	@Override
	public void perform() {

		Key key = unlocker.getKeyFor(door);

		door.locked = true;

		if (unlocker.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { unlocker, " unlocked ", door, " with ", key }));

		unlocker.showPow(door);

		// Sound
		float loudness = 1;
		unlocker.sounds.add(new Sound(unlocker, key, unlocker.squareGameObjectIsOn, loudness));

		if (unlocker.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}

		if (unlocker == Game.level.player)
			Game.level.endTurn();
	}

	@Override
	public boolean check() {
		if (!unlocker.visibleFrom(door.squareGameObjectIsOn)) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}
		if (unlocker.straightLineDistanceTo(door.squareGameObjectIsOn) != 1) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}

		if (!unlocker.hasKeyForDoor(door)) {
			actionName = ACTION_NAME_NEED_KEY;
			return false;
		}

		return true;
	}

}
