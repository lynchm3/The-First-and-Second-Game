package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.SmallHidingPlace;
import com.marklynch.ui.ActivityLog;

public class ActionHideInside extends Action {

	public static final String ACTION_NAME = "Hide";

	public ActionHideInside(Actor performer, SmallHidingPlace target) {
		super(ACTION_NAME, textureHide, performer, target);
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
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " hid in ", "" + targetGameObject }));

		performer.squareGameObjectIsOn.inventory.remove(performer);
		targetGameObject.inventory.add(performer);

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
		if (performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) > 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (targetGameObject.squareGameObjectIsOn.restricted() == true
				&& !targetGameObject.squareGameObjectIsOn.owners.contains(performer)) {
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