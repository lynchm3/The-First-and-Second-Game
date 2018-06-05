package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.Monster;

public class ActionUsePower extends Action {

	// public static final String ACTION_NAME = "Attack";
	// public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't
	// reach)";

	Actor performer;
	Square target;
	Power power;

	// Default for hostiles
	public ActionUsePower(Actor attacker, Square target, Power power) {
		super("Cast " + power.name, "action_attack.png");
		super.gameObjectPerformer = this.performer = attacker;
		this.target = target;
		this.power = power;
		if (!check()) {
			enabled = false;
			actionName = actionName + "(can't reach)";
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

		power.log(performer, target);
		power.cast(performer, target, this);

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		// if (!legal) {
		//

		// Something that belongs to some one else

		if (power.hostile) {
			for (Square square : power.getAffectedSquares(target)) {
				for (GameObject gameObject : square.inventory.getGameObjects()) {
					gameObject.attackedBy(this.performer, this);
				}
			}
		}

		if (!legal) {
			for (Square square : power.getAffectedSquares(target)) {
				for (GameObject gameObject : square.inventory.getGameObjects()) {
					Actor victim;
					if (gameObject instanceof Actor)
						victim = (Actor) gameObject;
					else
						victim = gameObject.owner;

					Crime.TYPE severity = power.crimeSeverity;
					if (severity == Crime.TYPE.CRIME_ASSAULT && !(gameObject instanceof Actor))
						severity = Crime.TYPE.CRIME_VANDALISM;

					if (victim != performer && victim != null) {
						Crime crime = new Crime(this, this.performer, victim, severity);
						this.performer.crimesPerformedThisTurn.add(crime);
						this.performer.crimesPerformedInLifetime.add(crime);
						notifyWitnessesOfCrime(crime);
					}
				}
			}

		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}

		if (performer == Game.level.player && !power.passive)// && power.)
			Game.level.endPlayerTurn();
	}

	@Override
	public boolean check() {

		disabledReason = power.disabledReason;
		return power.check(performer, target);
	}

	@Override
	public boolean checkRange() {

		if (!target.visibleToPlayer && !power.hasRange(Integer.MAX_VALUE))
			return false;

		if (!power.hasRange(performer.straightLineDistanceTo(target)))
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

		if (!power.potentialyCriminal)
			return true;

		if (performer.attackers.contains(target))
			return true;

		for (Square square : power.getAffectedSquares(target)) {

			for (GameObject gameObject : square.inventory.getGameObjects()) {

				if (gameObject.owner != null && gameObject.owner != performer)
					return false;
				// Is human

				if (gameObject instanceof Actor)
					if (!(gameObject instanceof Monster) && !(gameObject instanceof AggressiveWildAnimal))
						return false;

			}

		}
		return true;
	}

	@Override
	public Sound createSound() {

		// Sound

		if (performer.equipped == null)
			return null;

		float loudness = power.loudness;

		if (performer.equipped != null)
			return new Sound(performer, performer, target, loudness, legal, this.getClass());
		return null;
	}

}
