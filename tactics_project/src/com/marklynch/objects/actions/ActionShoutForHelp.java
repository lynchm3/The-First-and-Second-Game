package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionShoutForHelp extends Action {

	public static final String ACTION_NAME = "Shout For Help";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject attacker;

	// Default for hostiles
	public ActionShoutForHelp(Actor performer, GameObject attacker) {
		super(ACTION_NAME, "action_scream.png");
		this.performer = performer;
		this.attacker = attacker;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
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
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, "shouted for help" }));

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
