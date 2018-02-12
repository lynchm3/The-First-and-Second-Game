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

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (openable.isLocked())
			new ActionUnlock(performer, openable).perform();

		openable.open();

		if (Game.level.shouldLog(openable, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " opened ", openable }));

		openable.showPow();

		if (performer instanceof Actor) {
			Actor actor = (Actor) performer;
			if (actor.faction == Game.level.factions.player) {
				Game.level.undoList.clear();
			}

			trespassingCheck(this, actor, actor.squareGameObjectIsOn);

			if (Game.level.player.peekSquare != null) {
				new ActionStopPeeking(Game.level.player).perform();
			}

			actor.actionsPerformedThisTurn.add(this);

		}
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		if (performer instanceof Actor) {
			Actor actor = (Actor) performer;

			if (openable.isLocked() && !actor.hasKeyForDoor(openable)) {
				actionName = ACTION_NAME_NEED_KEY;
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean checkRange() {
		if (performer instanceof Actor) {
			Actor actor = (Actor) performer;
			if (!actor.canSeeGameObject(openable)) {
				actionName = ACTION_NAME_CANT_REACH;
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
		if (openable.isLocked()) {
			Action unlock = new ActionUnlock(performer, openable);
			if (!unlock.legal)
				return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return new Sound(performer, openable, openable.squareGameObjectIsOn, 1, legal, this.getClass());
	}

}
