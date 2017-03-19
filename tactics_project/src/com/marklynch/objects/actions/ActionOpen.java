package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.Door;
import com.marklynch.objects.Key;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionOpen extends Action {

	public static final String ACTION_NAME = "Open";
	public static final String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_NEED_KEY = ACTION_NAME + " (need key)";

	Actor opener;
	Door door;

	// Default for hostiles
	public ActionOpen(Actor opener, Door door) {
		super(ACTION_NAME);
		this.opener = opener;
		this.door = door;
		if (!check()) {
			enabled = false;
		}
	}

	@Override
	public void perform() {

		Key key = opener.getKeyFor(door);

		if (door.locked)
			new ActionUnlock(opener, door).perform();

		door.open();

		if (opener.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { opener, " opened ", door }));

		opener.showPow(door);

		// Sound
		float loudness = 1;
		opener.sounds.add(new Sound(opener, key, opener.squareGameObjectIsOn, loudness));

		if (opener.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}

		if (opener == Game.level.player)
			Game.level.endTurn();
	}

	@Override
	public boolean check() {
		if (!opener.visibleFrom(door.squareGameObjectIsOn)) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}
		if (opener.straightLineDistanceTo(door.squareGameObjectIsOn) != 1) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}

		if (door.locked && !opener.hasKeyForDoor(door)) {
			actionName = ACTION_NAME_NEED_KEY;
			return false;
		}

		return true;
	}

}
