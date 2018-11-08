package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.SmallHidingPlace;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionStopHidingInside extends Action {

	public static final String ACTION_NAME = "Stop Hiding";

	public ActionStopHidingInside(Actor performer, SmallHidingPlace target) {
		super(ACTION_NAME, textureStopHiding, performer, null);
		this.target = target;
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
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " stopped hiding in ", "" + target }));

		// object.inventory.remove(performer);
		// object.squareGameObjectIsOn.inventory.remove(performer);
		// performer.squareGameObjectIsOn = object.squareGameObjectIsOn;
		// performer.inventoryThatHoldsThisObject =
		// object.squareGameObjectIsOn.inventory;
		target.inventory.remove(performer);
		target.squareGameObjectIsOn.inventory.add(performer);

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (performer == Game.level.player)
			Game.level.endPlayerTurn();

	}

	@Override
	public boolean check() {

		if (target.inventory.contains(performer))
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
		if (target.squareGameObjectIsOn.restricted() == true
				&& !target.squareGameObjectIsOn.owners.contains(performer)) {
			illegalReason = TRESPASSING;
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}