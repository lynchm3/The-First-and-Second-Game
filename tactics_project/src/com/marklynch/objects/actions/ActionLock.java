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
	public static final String ACTION_NAME_BLOCKED = ACTION_NAME + " (blocked)";

	Actor locker;
	Door door;

	// Default for hostiles
	public ActionLock(Actor unlocker, Door door) {
		super(ACTION_NAME);
		this.locker = unlocker;
		this.door = door;
		if (!check()) {
			enabled = false;
		}
	}

	@Override
	public void perform() {

		Key key = locker.getKeyFor(door);

		if (door.isOpen())
			new ActionClose(locker, door).perform();

		door.locked = true;

		if (locker.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { locker, " locked ", door, " with ", key }));

		locker.showPow(door);

		// Sound
		float loudness = 1;
		locker.sounds.add(new Sound(locker, key, locker.squareGameObjectIsOn, loudness));

		if (locker.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}

		if (locker == Game.level.player)
			Game.level.endTurn();
	}

	@Override
	public boolean check() {
		if (!locker.visibleFrom(door.squareGameObjectIsOn)) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}
		if (locker.straightLineDistanceTo(door.squareGameObjectIsOn) != 1) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}

		if (!locker.hasKeyForDoor(door)) {
			actionName = ACTION_NAME_NEED_KEY;
			return false;
		}

		if (door.squareGameObjectIsOn.inventory.canShareSquare() == false) {
			actionName = ACTION_NAME_BLOCKED;
			return false;
		}

		return true;
	}

}
