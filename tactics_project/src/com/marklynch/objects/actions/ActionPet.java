package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionPet extends Action {

	public static final String ACTION_NAME = "Pet";

	Actor performer;
	GameObject object;

	public ActionPet(Actor performer, GameObject object) {
		super(ACTION_NAME, "action_pet.png");
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

		if (Game.level.shouldLog(object, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " petted ", object }));
		if (performer == Game.level.player && Math.random() > 0.9d) {
			if (Game.level.shouldLog(object, performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { object, " wonders what your deal is" }));
		}

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
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
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}