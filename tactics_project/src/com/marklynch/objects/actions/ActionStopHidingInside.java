package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.SmallHidingPlace;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionStopHidingInside extends Action {

	public static final String ACTION_NAME = "Stop Hiding";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject object;

	// public ActionMove actionMove;

	public ActionStopHidingInside(Actor performer, SmallHidingPlace object) {
		super(ACTION_NAME, "action_stop_hiding.png");
		this.performer = performer;
		this.object = object;
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

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " stopped hiding in ", "" + object }));

		// object.inventory.remove(performer);
		// object.squareGameObjectIsOn.inventory.remove(performer);
		// performer.squareGameObjectIsOn = object.squareGameObjectIsOn;
		// performer.inventoryThatHoldsThisObject =
		// object.squareGameObjectIsOn.inventory;
		object.inventory.remove(performer);
		object.squareGameObjectIsOn.inventory.add(performer);

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (performer == Game.level.player)
			Game.level.endPlayerTurn();

	}

	@Override
	public boolean check() {

		if (object.inventory.contains(performer))
			return true;
		else
			return false;
	}

	@Override
	public boolean checkRange() {
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (object.squareGameObjectIsOn.restricted == true && !object.squareGameObjectIsOn.owners.contains(performer)) {
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}