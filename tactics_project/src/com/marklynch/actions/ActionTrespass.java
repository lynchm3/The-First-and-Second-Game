package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionTrespass extends Action {

	public static final String ACTION_NAME = "Trespass here";
	float loudness;

	public ActionTrespass(Actor performer, Square targetSquare, float loudness) {
		super(ACTION_NAME, textureTrespass, performer, targetSquare);
		this.loudness = loudness;
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
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " trespassed" }));
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
		illegalReason = TRESPASSING;
		return false;
	}

	@Override
	public Sound createSound() {
		return new Sound(performer, performer, targetSquare, loudness, legal, this.getClass());
	}
}
