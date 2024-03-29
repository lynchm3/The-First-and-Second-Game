package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionStopPeeking extends Action {

	public static final String ACTION_NAME = "Stop Peeking";

	public ActionStopPeeking(Actor performer) {
		super(ACTION_NAME, textureStopHiding, performer, null);

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

		// object.actorsHidingHere.add(performer);
		if (performer.peekingThrough != null) {
			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(new ActivityLog(
						new Object[] { performer, " stopped peeking through ", performer.peekingThrough }));
		} else {
			if (Game.level.shouldLog(performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " stopped peeking" }));

		}

		if (performer == Game.level.player) {
			Game.level.player.peekingThrough = null;
			performer.peekSquare = null;
			Game.level.player.calculateVisibleAndCastableSquares(performer.squareGameObjectIsOn);
		}

		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		if (performer.peekSquare != null) {
			return false;
		}
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
		return null;
	}

}