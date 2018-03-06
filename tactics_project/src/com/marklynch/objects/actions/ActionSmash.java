package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class ActionSmash extends Action {

	public static final String ACTION_NAME = "Smash";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	GameObject performer;
	GameObject target;
	Actor actor;

	// Default for hostiles
	public ActionSmash(GameObject attacker, GameObject target) {
		super(ACTION_NAME, "action_smash.png");
		this.performer = attacker;
		if (performer instanceof Actor)
			actor = (Actor) performer;
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

		target.changeHealth(-target.remainingHealth, null, null);
		target.checkIfDestroyed(performer, this);

		performer.actionsPerformedThisTurn.add(this);

		if (sound != null)
			sound.play();

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();

		if (actor != null) {
			if (!legal) {
				Crime crime = new Crime(this, this.actor, target.owner, Crime.TYPE.CRIME_VANDALISM);
				this.actor.crimesPerformedThisTurn.add(crime);
				this.actor.crimesPerformedInLifetime.add(crime);
				notifyWitnessesOfCrime(crime);
			} else {
				trespassingCheck(this, actor, actor.squareGameObjectIsOn);
			}
		}
	}

	@Override
	public boolean check() {

		// if (performer.equipped == target)
		// return true;

		// if (performer instanceof Actor) {
		// Actor actor = (performer);
		//
		// Weapon weapon = null;
		// if (actor.equipped instanceof Weapon) {
		// weapon = (Weapon) actor.equipped;
		// } else {
		// return false;
		// }
		// if
		// (weapon.hasRange(actor.straightLineDistanceTo(target.squareGameObjectIsOn)))
		// return false;
		// if (!actor.canSeeGameObject(target))
		// return false;
		// }

		return true;
	}

	@Override
	public boolean checkRange() {

		// if (performer.equipped == target)
		// return true;

		// if (performer instanceof Actor) {
		// Actor actor = (performer);
		//
		// Weapon weapon = null;
		// if (actor.equipped instanceof Weapon) {
		// weapon = (Weapon) actor.equipped;
		// } else {
		// return false;
		// }
		// if
		// (weapon.hasRange(actor.straightLineDistanceTo(target.squareGameObjectIsOn)))
		// return false;
		// if (!actor.canSeeGameObject(target))
		// return false;
		// }

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

		if (performer instanceof Actor)
			return new Sound(performer, target, target.squareGameObjectIsOn, 20, legal, this.getClass());
		else
			return new Sound(null, target, target.squareGameObjectIsOn, 20, legal, this.getClass());

	}

}
