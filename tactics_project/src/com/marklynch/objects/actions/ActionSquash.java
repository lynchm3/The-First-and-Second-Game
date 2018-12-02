package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actors.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionSquash extends Action {

	public static final String ACTION_NAME = "Squash";
	boolean accidental;

	// Default for hostiles
	public ActionSquash(Actor attacker, GameObject target, boolean accidental) {
		super(ACTION_NAME, textureSquash, attacker, target);
		this.accidental = accidental;
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

		if (target.attackable) {
			target.changeHealth(-target.remainingHealth, performer, this);
			if (performer.squareGameObjectIsOn.visibleToPlayer) {

				if (accidental) {

					if (Game.level.shouldLog(target, performer))
						Game.level.logOnScreen(new ActivityLog(
								new Object[] { performer, "accidentally squashed ", target, " ...whoops" }));
				} else {

					if (Game.level.shouldLog(target, performer))
						Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " squashed", target }));
				}
			}
		}

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		if (sound != null)
			sound.play();

		if (!legal) {

			Actor victim;
			if (target instanceof Actor)
				victim = (Actor) target;
			else
				victim = target.owner;

			Crime.TYPE severity = Crime.TYPE.CRIME_MANSLAUGHTER;
			if (!accidental)
				severity = Crime.TYPE.CRIME_ASSAULT;
			if (!(target instanceof Actor))
				severity = Crime.TYPE.CRIME_VANDALISM;

			Crime crime = new Crime(this, this.performer, victim, severity);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}

		if (performer == Game.level.player)
			Game.level.endPlayerTurn();
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
		return standardAttackLegalityCheck(performer, target);
	}

	@Override
	public Sound createSound() {

		// Sound

		if (performer.equipped == null)
			return null;

		float loudness = 10f;
		if (performer.equipped != null)
			return new Sound(performer, performer.equipped, target.squareGameObjectIsOn, loudness, legal,
					this.getClass());
		return null;
	}

}
