package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.Door;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionClose extends Action {

	public static final String ACTION_NAME = "Close";
	public static final String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_NEED_KEY = ACTION_NAME + " (need key)";
	public static final String ACTION_NAME_BLOCKED = ACTION_NAME + " (blocked)";

	Actor performer;
	Door door;

	// Default for hostiles
	public ActionClose(Actor opener, Door door) {
		super(ACTION_NAME);
		this.performer = opener;
		this.door = door;
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
	}

	@Override
	public void perform() {

		// Key key = closer.getKeyFor(door);

		door.close();

		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " closed ", door }));

		performer.showPow(door);

		// Sound
		float loudness = 1;
		sound = new Sound(performer, door, performer.squareGameObjectIsOn, loudness, legal, this.getClass());

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

		if (door.locked && !performer.hasKeyForDoor(door)) {
			actionName = ACTION_NAME_NEED_KEY;
			return false;
		}

		if (door.squareGameObjectIsOn.inventory.canShareSquare() == false) {
			actionName = ACTION_NAME_BLOCKED;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

}
