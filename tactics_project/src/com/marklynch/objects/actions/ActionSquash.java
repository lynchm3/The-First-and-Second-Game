package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.Monster;
import com.marklynch.ui.ActivityLog;

public class ActionSquash extends Action {

	public static final String ACTION_NAME = "Squash";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject target;
	boolean accidental;

	// Default for hostiles
	public ActionSquash(Actor attacker, GameObject target, boolean accidental) {
		super(ACTION_NAME, "action_squash.png");
		this.performer = attacker;
		this.target = target;
		this.accidental = accidental;
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

		if (target.attackable) {
			target.remainingHealth = 0;
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
			target.attackedBy(performer, this);
		}

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		if (performer.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
		}

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {

			Actor victim;
			if (target instanceof Actor)
				victim = (Actor) target;
			else
				victim = target.owner;

			int severity = Crime.CRIME_SEVERITY_MANSLAUGHTER;
			if (!accidental)
				severity = Crime.CRIME_SEVERITY_ATTACK;
			if (!(target instanceof Actor))
				severity = Crime.CRIME_SEVERITY_VANDALISM;

			Crime crime = new Crime(this, this.performer, victim, severity);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}

		if (performer == Game.level.player)
			Game.level.endTurn();
	}

	@Override
	public boolean check() {

		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1)
			return false;

		return true;
	}

	// @Override
	// public boolean checkLegality() {
	// // Something that belongs to some one else
	// if (target.owner != null && target.owner != Game.level.player)
	// return false;
	// return true;
	// }

	@Override
	public boolean checkLegality() {
		// Something that belongs to some one else

		if (target.owner != null && target.owner != performer)
			return false;
		// Is human

		if (target instanceof Actor)
			if (!(target instanceof Monster) && !(target instanceof AggressiveWildAnimal))
				return false;
		return true;
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
