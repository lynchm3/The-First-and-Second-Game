package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.units.Actor;

public class ActionLoiter extends Action {

	public static final String ACTION_NAME = "Loiter here";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor performer;
	Square target;

	public ActionLoiter(Actor loiterer, Square target) {
		super(ACTION_NAME);
		this.performer = loiterer;
		this.target = target;
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
		loiter(performer, target);
		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endTurn();

		if (!legal) {

			// Actor victim;
			// if (object instanceof Actor)
			// victim = (Actor) object;
			// else
			// victim = target.owner;

			Crime crime = new Crime(this, this.performer, null, 1);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		}
	}

	public void loiter(Actor actor, Square squareToLoiterOn) {
	}

	@Override
	public boolean check() {
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
