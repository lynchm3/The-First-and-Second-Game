package com.marklynch.objects.actions;

import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class ActionGetIn extends Action {

	public static final String ACTION_NAME = "Get In";

	// Actor performer;
	// GameObject object;

	public ActionGetIn(Actor performer, GameObject object) {
		super(ACTION_NAME, textureDrop, performer, object, null);
		// super.gameObjectPerformer = this.performer = performer;
		// this.object = object;
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
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 0) {
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