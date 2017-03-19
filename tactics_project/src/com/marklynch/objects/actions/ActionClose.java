package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.Door;
import com.marklynch.objects.Key;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionClose extends Action {

	public static final String ACTION_NAME = "Close";
	public static final String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_NEED_KEY = ACTION_NAME + " (need key)";
	public static final String ACTION_NAME_BLOCKED = ACTION_NAME + " (blocked)";

	Actor closer;
	Door door;

	// Default for hostiles
	public ActionClose(Actor opener, Door door) {
		super(ACTION_NAME);
		this.closer = opener;
		this.door = door;
		if (!check()) {
			enabled = false;
		}
	}

	@Override
	public void perform() {

		Key key = closer.getKeyFor(door);

		door.open = false;

		if (closer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { closer, " closed ", door }));

		closer.showPow(door);

		// Sound
		float loudness = 1;
		closer.sounds.add(new Sound(closer, key, closer.squareGameObjectIsOn, loudness));

		if (closer.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}

		if (closer == Game.level.player)
			Game.level.endTurn();
	}

	@Override
	public boolean check() {
		if (!closer.visibleFrom(door.squareGameObjectIsOn)) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}
		if (closer.straightLineDistanceTo(door.squareGameObjectIsOn) != 1) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}

		if (door.locked && !closer.hasKeyForDoor(door)) {
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
