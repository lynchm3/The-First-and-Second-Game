package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Openable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionOpen extends Action {

	public static final String ACTION_NAME = "Open";
	public static final String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_NEED_KEY = ACTION_NAME + " (need key)";

	GameObject performer;
	Openable openable;

	// Default for hostiles
	public ActionOpen(GameObject opener, Openable openable) {
		super(ACTION_NAME, "action_open.png");
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

		if (openable.isLocked())
			new ActionUnlock(performer, openable).perform();

		openable.open();

		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " opened ", openable }));

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
