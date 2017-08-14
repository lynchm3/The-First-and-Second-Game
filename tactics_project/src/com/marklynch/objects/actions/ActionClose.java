package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Openable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionClose extends Action {

	public static final String ACTION_NAME = "Close";
	public static final String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_NEED_KEY = ACTION_NAME + " (need key)";
	public static final String ACTION_NAME_BLOCKED = ACTION_NAME + " (blocked)";

	GameObject performer;
	Openable openable;

	// Default for hostiles
	public ActionClose(GameObject opener, Openable openable) {
		super(ACTION_NAME, "action_close.png");
		this.performer = opener;
		this.openable = openable;
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		// Key key = closer.getKeyFor(door);

		openable.close();

		if (Game.level.shouldLog(openable, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " closed ", openable }));

		performer.showPow(openable);

		if (performer instanceof Actor) {
			Actor actor = (Actor) performer;
			if (actor.faction == Game.level.factions.get(0)) {
				Game.level.undoList.clear();
			}

			trespassingCheck(this, actor, actor.squareGameObjectIsOn);
			actor.actionsPerformedThisTurn.add(this);
		}
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {

		if (performer instanceof Actor) {
			Actor actor = (Actor) performer;
			if (!actor.canSeeGameObject(openable)) {
				actionName = ACTION_NAME_CANT_REACH;
				return false;
			}

			if (openable.isLocked() && !actor.hasKeyForDoor(openable)) {
				actionName = ACTION_NAME_NEED_KEY;
				return false;
			}

			if (performer.straightLineDistanceTo(openable.squareGameObjectIsOn) != 1) {
				actionName = ACTION_NAME_CANT_REACH;
				return false;
			}
		}

		if (openable instanceof Door && openable.squareGameObjectIsOn.inventory.canShareSquare() == false) {
			actionName = ACTION_NAME_BLOCKED;
		}

		if (openable instanceof Door && openable.squareGameObjectIsOn.inventory.contains(Actor.class)) {
			actionName = ACTION_NAME_BLOCKED;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return new Sound(performer, openable, openable.squareGameObjectIsOn, 1, legal, this.getClass());
	}

}
