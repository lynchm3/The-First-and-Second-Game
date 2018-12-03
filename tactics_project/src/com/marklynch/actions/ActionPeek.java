package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.ui.ActivityLog;

public class ActionPeek extends Action {

	public static final String ACTION_NAME = "Peek";

	public ActionPeek(Actor performer, GameObject target) {
		super(ACTION_NAME, textureStopHiding, performer, target);

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
		// target.actorsHidingHere.add(performer);

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " peeked through ", target }));

		if (performer == Game.level.player) {

			performer.peekingThrough = target;
			Game.level.player.peekingThrough = target;

			Square peekSquare = null;
			if (target.squareGameObjectIsOn.xInGrid < performer.squareGameObjectIsOn.xInGrid) {
				peekSquare = Game.level.squares[performer.squareGameObjectIsOn.xInGrid
						- 2][performer.squareGameObjectIsOn.yInGrid];
			} else if (target.squareGameObjectIsOn.xInGrid > performer.squareGameObjectIsOn.xInGrid) {
				peekSquare = Game.level.squares[performer.squareGameObjectIsOn.xInGrid
						+ 2][performer.squareGameObjectIsOn.yInGrid];
			} else if (target.squareGameObjectIsOn.yInGrid < performer.squareGameObjectIsOn.yInGrid) {
				peekSquare = Game.level.squares[performer.squareGameObjectIsOn.xInGrid][performer.squareGameObjectIsOn.yInGrid
						- 2];
			} else if (target.squareGameObjectIsOn.yInGrid > performer.squareGameObjectIsOn.yInGrid) {
				peekSquare = Game.level.squares[performer.squareGameObjectIsOn.xInGrid][performer.squareGameObjectIsOn.yInGrid
						+ 2];
			}
			performer.peekSquare = peekSquare;
			Game.level.player.peekSquare = peekSquare;
			if (peekSquare != null && performer == Game.level.player)
				Game.level.player.calculateVisibleSquares(peekSquare);
		}
		if (!legal) {
			Crime crime = new Crime(this.performer, target.owner, Crime.TYPE.CRIME_VOYEURISM);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		}
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkRange() {
		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (target.squareGameObjectIsOn.restricted() == true
				&& !target.squareGameObjectIsOn.owners.contains(performer)) {
			illegalReason = TRESPASSING;
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}