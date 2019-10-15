package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;

public class ActionSmash extends Action {

	public static final String ACTION_NAME = "Smash";

	// Default for hostiles
	public ActionSmash(GameObject attacker, GameObject target) {
		super(ACTION_NAME, textureSmash, attacker, target);
		super.gameObjectPerformer = this.gameObjectPerformer = attacker;
		if (gameObjectPerformer instanceof Actor)
			performer = (Actor) gameObjectPerformer;
		this.targetGameObject = target;
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

		targetGameObject.changeHealth(-targetGameObject.remainingHealth, performer, this);
		targetGameObject.checkIfDestroyed(gameObjectPerformer, this);

		if (sound != null)
			sound.play();

		if (gameObjectPerformer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();

		if (performer != null) {
			if (!legal) {
				Crime crime = new Crime(this.performer, targetGameObject.owner, Crime.TYPE.CRIME_VANDALISM);
				this.performer.crimesPerformedThisTurn.add(crime);
				this.performer.crimesPerformedInLifetime.add(crime);
				notifyWitnessesOfCrime(crime);
			} else {
				trespassingCheck(performer, performer.squareGameObjectIsOn);
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
		return standardAttackLegalityCheck(performer, targetGameObject);
	}

	@Override
	public Sound createSound() {

		if (gameObjectPerformer instanceof Actor)
			return new Sound(gameObjectPerformer, targetGameObject, targetGameObject.squareGameObjectIsOn, 20, legal, this.getClass());
		else
			return new Sound(null, targetGameObject, targetGameObject.squareGameObjectIsOn, 20, legal, this.getClass());

	}

}
