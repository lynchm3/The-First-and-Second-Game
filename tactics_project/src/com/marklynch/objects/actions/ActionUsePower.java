package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Level.LevelMode;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.constructs.power.PowerTeleportOther;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.Monster;

public class ActionUsePower extends Action {
	GameObject gameObjectSource;
	Actor actorPerformer;
	Square targetSquare;
	GameObject targetGameObject;
	Power power;

	// Default for hostiles
	public ActionUsePower(GameObject attacker, GameObject targetGameObject, Square targetSquare, Power power) {
		super("Cast " + power.name, "action_attack.png");
		super.gameObjectPerformer = this.gameObjectSource = attacker;
		if (attacker instanceof Actor)
			this.actorPerformer = (Actor) attacker;
		this.targetSquare = targetSquare;
		this.targetGameObject = targetGameObject;
		this.power = power;
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

		Game.level.levelMode = LevelMode.LEVEL_MODE_NORMAL;
		power.log(gameObjectSource, targetSquare);
		power.cast(gameObjectSource, targetGameObject, targetSquare, this);

		gameObjectSource.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (power.hostile) {
			for (Square square : power.getAffectedSquares(targetSquare)) {
				for (GameObject gameObject : square.inventory.getGameObjects()) {
					gameObject.attackedBy(this.gameObjectSource, this);
				}
			}
		}

		if (!legal) {
			for (Square square : power.getAffectedSquares(targetSquare)) {
				for (GameObject gameObject : square.inventory.getGameObjects()) {
					Actor victim;
					if (gameObject instanceof Actor)
						victim = (Actor) gameObject;
					else
						victim = gameObject.owner;

					Crime.TYPE severity = power.crimeSeverity;
					if (severity == Crime.TYPE.CRIME_ASSAULT && !(gameObject instanceof Actor))
						severity = Crime.TYPE.CRIME_VANDALISM;

					if (victim != gameObjectSource && victim != null && actorPerformer != null) {
						Crime crime = new Crime(this, this.actorPerformer, victim, severity);
						this.actorPerformer.crimesPerformedThisTurn.add(crime);
						this.actorPerformer.crimesPerformedInLifetime.add(crime);
						notifyWitnessesOfCrime(crime);
					}
				}
			}

		} else {
			if (actorPerformer != null)
				trespassingCheck(this, actorPerformer, gameObjectSource.squareGameObjectIsOn);
		}

		if (gameObjectSource == Game.level.player && !power.passive && !(power instanceof PowerTeleportOther))
			Game.level.endPlayerTurn();
	}

	@Override
	public boolean check() {

		if (targetGameObject != null)
			if (targetGameObject.attackable == false || targetGameObject.isFloorObject)
				return false;

		boolean powerCheck = power.check(gameObjectSource, targetSquare);
		if (powerCheck) {
			return true;
		} else {
			disabledReason = power.disabledReason;
			return false;
		}
	}

	@Override
	public boolean checkRange() {

		if (!targetSquare.visibleToPlayer && !power.hasRange(Integer.MAX_VALUE))
			return false;

		if (!power.hasRange(gameObjectSource.straightLineDistanceTo(targetSquare)))
			return false;

		if (!power.squareInCastLocations(gameObjectSource, targetSquare))
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

		if (gameObjectSource.attackers.contains(targetSquare))
			return true;

		for (Square square : power.getAffectedSquares(targetSquare)) {

			for (GameObject gameObject : square.inventory.getGameObjects()) {

				if (gameObject.owner != null && gameObject.owner != gameObjectSource)
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

		if (gameObjectSource.equipped == null)
			return null;

		float loudness = power.loudness;

		if (gameObjectSource.equipped != null)
			return new Sound(gameObjectSource, gameObjectSource, targetSquare, loudness, legal, this.getClass());
		return null;
	}

}
