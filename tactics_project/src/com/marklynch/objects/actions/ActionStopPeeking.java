package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
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
		if (performer.squareGameObjectIsOn.visibleToPlayer || performer == Game.level.player)
			Game.level.logOnScreen(
					new ActivityLog(new Object[] { performer, " stopped peeking through ", performer.peekingThrough }));

		if (!legal) {
			Crime crime = new Crime(this, this.performer, Game.level.player.peekingThrough.owner, 4);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
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
		if (performer.peekingThrough != null) {
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