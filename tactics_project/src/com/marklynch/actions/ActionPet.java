package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.ui.ActivityLog;

public class ActionPet extends Action {

	public static final String ACTION_NAME = "Pet";

	public ActionPet(Actor performer, GameObject target) {
		super(ACTION_NAME, texturePet, performer, target);
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

		if (Game.level.shouldLog(targetGameObject, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " petted ", targetGameObject }));
		if (performer == Game.level.player && Math.random() > 0.9d) {
			if (Game.level.shouldLog(targetGameObject, performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { targetGameObject, " wonders what your deal is" }));
		}

		if (sound != null)
			sound.play();
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
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}