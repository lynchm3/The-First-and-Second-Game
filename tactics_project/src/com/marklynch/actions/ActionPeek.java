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
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " peeked through ", targetGameObject }));

		if (performer == Game.level.player) {

			performer.peekingThrough = targetGameObject;
			Game.level.player.peekingThrough = targetGameObject;

			Square peekSquare = null;
			if (targetGameObject.squareGameObjectIsOn.xInGrid < performer.squareGameObjectIsOn.xInGrid) {
				peekSquare = Game.level.squares[performer.squareGameObjectIsOn.xInGrid
						- 2][performer.squareGameObjectIsOn.yInGrid];
			} else if (targetGameObject.squareGameObjectIsOn.xInGrid > performer.squareGameObjectIsOn.xInGrid) {
				peekSquare = Game.level.squares[performer.squareGameObjectIsOn.xInGrid
						+ 2][performer.squareGameObjectIsOn.yInGrid];
			} else if (targetGameObject.squareGameObjectIsOn.yInGrid < performer.squareGameObjectIsOn.yInGrid) {
				peekSquare = Game.level.squares[performer.squareGameObjectIsOn.xInGrid][performer.squareGameObjectIsOn.yInGrid
						- 2];
			} else if (targetGameObject.squareGameObjectIsOn.yInGrid > performer.squareGameObjectIsOn.yInGrid) {
				peekSquare = Game.level.squares[performer.squareGameObjectIsOn.xInGrid][performer.squareGameObjectIsOn.yInGrid
						+ 2];
			}
			performer.peekSquare = peekSquare;
			Game.level.player.peekSquare = peekSquare;
			if (peekSquare != null && performer == Game.level.player)
				Game.level.player.calculateVisibleSquares(peekSquare);
		}
		if (!legal) {
			Crime crime = new Crime(this.performer, targetGameObject.owner, Crime.TYPE.CRIME_VOYEURISM);
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
		if (performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) > 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (targetGameObject.squareGameObjectIsOn.restricted() == true
				&& !targetGameObject.squareGameObjectIsOn.owners.contains(performer)) {
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