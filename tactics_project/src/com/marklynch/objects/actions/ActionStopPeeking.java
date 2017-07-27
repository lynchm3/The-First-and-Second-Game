package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionStopPeeking extends Action {

	public static final String ACTION_NAME = "Stop Peeking";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;

	public ActionStopPeeking(Actor performer) {
		super(ACTION_NAME, "action_hide.png");
		this.performer = performer;

		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
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
			performer.calculateVisibleSquares(performer.squareGameObjectIsOn);
		}

		performer.actionsPerformedThisTurn.add(this);
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
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}