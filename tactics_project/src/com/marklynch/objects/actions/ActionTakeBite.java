package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionTakeBite extends Action {

	public static final String ACTION_NAME = "Take bite";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject target;

	// Default for hostiles
	public ActionTakeBite(Actor attacker, GameObject target) {
		super(ACTION_NAME, "action_take_bite.png");
		this.performer = attacker;
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

		if (!checkRange())
			return;

		if (Game.level.shouldLog(target, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " took a bite of ", target }));

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endTurn();

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {
			Crime crime = new Crime(this, this.performer, target.owner, Crime.TYPE.CRIME_THEFT);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkRange() {
		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1)
			return false;
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (target.owner != null && target.owner != performer)
			return false;
		return true;
	}

	@Override
	public Sound createSound() {
		return new Sound(performer, target, target.squareGameObjectIsOn, 1, legal, this.getClass());
	}

}
