package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.SmallHidingPlace;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionHideInside extends Action {

	public static final String ACTION_NAME = "Hide";

	Actor performer;
	GameObject object;

	// public ActionMove actionMove;

	public ActionHideInside(Actor performer, SmallHidingPlace object) {
		super(ACTION_NAME, "action_hide.png");
		super.gameObjectPerformer = this.performer = performer;
		this.object = object;
		if (!check()) {
			enabled = false;
		}

		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " hid in ", "" + object }));

		performer.squareGameObjectIsOn.inventory.remove(performer);
		object.inventory.add(performer);

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (performer == Game.level.player)
			Game.level.endPlayerTurn();

	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkRange() {
		if (performer.straightLineDistanceTo(object.squareGameObjectIsOn) > 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (object.squareGameObjectIsOn.restricted() == true
				&& !object.squareGameObjectIsOn.owners.contains(performer)) {
			illegalReason = TRESSPASSING;
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}