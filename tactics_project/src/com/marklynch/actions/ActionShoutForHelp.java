package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.ui.ActivityLog;

public class ActionShoutForHelp extends Action {

	public static final String ACTION_NAME = "Shout For Help";

	GameObject attacker;

	// Default for hostiles
	public ActionShoutForHelp(Actor performer, GameObject attacker) {
		super(ACTION_NAME, textureScream, performer, null);
		this.attacker = attacker;
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
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, "shouted for help" }));

		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkRange() {
		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		if (performer.investigationsMap.get(attacker) != null)
			return new Sound(performer, attacker, performer.investigationsMap.get(attacker).square, 20, legal,
					this.getClass());
		else
			return null;
	}

}
