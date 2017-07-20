package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionTrespass extends Action {

	public static final String ACTION_NAME = "Trespass here";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor performer;
	Square target;
	float loudness;

	public ActionTrespass(Actor mover, Square target, float loudness) {
		super(ACTION_NAME, "action_trespass.png");
		this.performer = mover;
		this.target = target;
		this.loudness = loudness;
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

		performer.actionsPerformedThisTurn.add(this);
		Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " trespassed" }));
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkLegality() {
		return false;
	}

	@Override
	public Sound createSound() {
		return new Sound(performer, performer, target, loudness, legal, this.getClass());
	}
}
