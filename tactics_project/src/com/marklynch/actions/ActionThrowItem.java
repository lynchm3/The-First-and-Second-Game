package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.animation.primary.AnimationThrow;
import com.marklynch.level.constructs.animation.secondary.AnimationThrown;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;

public class ActionThrowItem extends Action {

	public static final String ACTION_NAME = "Throw";

	GameObject gameObjectToThrow;

	// Default for hostiles
	public ActionThrowItem(Actor performer, Object target, GameObject gameObjectToThrow) {
		super(ACTION_NAME, textureThrow, performer, target);
		this.gameObjectToThrow = gameObjectToThrow;
		if (!check()) {
			enabled = false;
		} else {
			actionName = ACTION_NAME + " " + gameObjectToThrow.name;
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

		// float damage = 0;

		// if (gameObjectToThrow instanceof Weapon) {
		// damage = performer.getEffectiveStrength() + gameObjectToThrow.weight
		// / 10f
		// + ((Weapon) gameObjectToThrow).getTotalEffectiveDamage();
		// } else {
		// damage = performer.getEffectiveStrength() + gameObjectToThrow.weight
		// / 10f;
		//
		// }

		if (performer.squareGameObjectIsOn.xInGrid > targetSquare.xInGrid) {
			performer.backwards = true;
		} else if (performer.squareGameObjectIsOn.xInGrid < targetSquare.xInGrid) {
			performer.backwards = false;
		}

		if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer) {
			performer.setPrimaryAnimation(new AnimationThrow(performer, targetGameObject, null));
		}

		// if (targetGameObject != null && targetGameObject.attackable) {
		// float damage = targetGameObject.changeHealth(performer, this,
		// gameObjectToThrow);
		//
		// if (Game.level.shouldLog(targetGameObject, performer)) {
		// Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " threw a ",
		// gameObjectToThrow, " at ",
		// targetGameObject, " for " + damage + " damage" }));
		// } else {
		// Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " threw a ",
		// gameObjectToThrow }));
		//
		// }
		// }
		// target.attacked(performer);

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		// shoot projectile
		Level.addSecondaryAnimation(new AnimationThrown(gameObjectToThrow.name, performer, this, targetGameObject,
				targetSquare, gameObjectToThrow, gameObjectToThrow, 2f, 0.5f, true, null));

		if (performer.equipped == gameObjectToThrow) {
			if (performer.inventory.contains(performer.equippedBeforePickingUpObject)) {
				performer.equip(performer.equippedBeforePickingUpObject);
			} else if (performer.inventory.containsDuplicateOf(gameObjectToThrow)) {
				performer.equip(performer.inventory.getDuplicateOf(gameObjectToThrow));
			} else {
				performer.equip(null);
			}
			performer.equippedBeforePickingUpObject = null;
		}

		if (performer.helmet == gameObjectToThrow)
			performer.helmet = null;
		if (performer.bodyArmor == gameObjectToThrow)
			performer.bodyArmor = null;
		if (performer.legArmor == gameObjectToThrow)
			performer.legArmor = null;

		if (performer.inventory.contains(gameObjectToThrow))
			performer.inventory.remove(gameObjectToThrow);

		gameObjectToThrow.thrown(performer);

		if (performer.faction == Game.level.factions.player)

		{
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player)
			Game.level.endPlayerTurn();

		if (sound != null)
			sound.play();

		if (!legal) {

			Actor victim = null;
			if (targetGameObject instanceof Actor)
				victim = (Actor) targetGameObject;
			else if (targetGameObject != null)
				victim = targetGameObject.owner;
			if (victim != null) {
				Crime.TYPE severity = Crime.TYPE.CRIME_ASSAULT;
				if (!(targetGameObject instanceof Actor))
					severity = Crime.TYPE.CRIME_VANDALISM;
				Crime crime = new Crime(this.performer, victim, Crime.TYPE.CRIME_ASSAULT);
				this.performer.crimesPerformedThisTurn.add(crime);
				this.performer.crimesPerformedInLifetime.add(crime);
				notifyWitnessesOfCrime(crime);
			}
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}

		Level.closeAllScreens();
	}

	@Override
	public boolean check() {

		if (targetSquare == null && targetGameObject == null)
			return false;

		float maxDistance = (performer.getEffectiveHighLevelStat(HIGH_LEVEL_STATS.STRENGTH) * 100)
				/ gameObjectToThrow.weight;
		if (maxDistance > 10)
			maxDistance = 10;

		if (performer.straightLineDistanceTo(targetSquare) > maxDistance) {
			disabledReason = TOO_HEAVY;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (targetSquare == null && targetGameObject == null)
			return false;

		if (!performer.canSeeSquare(targetSquare)) {
			return false;
		}

		if (performer == Game.level.player && !targetSquare.playerCanCastTo)
			return false;

		return true;
	}

	@Override
	public boolean checkLegality() {
		// Empty square, it's fine
		if (targetGameObject == null)
			return true;

		return standardAttackLegalityCheck(performer, targetGameObject);
	}

	@Override
	public Sound createSound() {

		// Sound
		if (targetGameObject != null) {
			float loudness = Math.max(targetGameObject.soundWhenHit, gameObjectToThrow.soundWhenHitting);
			// float loudness = targetGameObject.soundWhenHit *
			// projectile.soundWhenHitting;
			if (performer.equipped != null)
				return new Sound(performer, gameObjectToThrow, targetSquare, loudness, legal, this.getClass());
		} else {
			float loudness = gameObjectToThrow.soundWhenHitting;
			if (performer.equipped != null)
				return new Sound(performer, gameObjectToThrow, targetSquare, loudness, legal, this.getClass());

		}
		return null;
	}

}
