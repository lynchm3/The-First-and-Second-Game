package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionTrespass extends Action {

	public static final String ACTION_NAME = "Trespass here";
	Actor performer;
	Square target;
	float loudness;

	public ActionTrespass(Actor mover, Square target, float loudness) {
		super(ACTION_NAME, textureTrespass, performer, performer, target, targetSquare);
		super.gameObjectPerformer = this.performer = mover;
		this.target = target;
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

		performer.actionsPerformedThisTurn.add(this);

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
		return new Sound(performer, performer, target, loudness, legal, this.getClass());
	}
}
