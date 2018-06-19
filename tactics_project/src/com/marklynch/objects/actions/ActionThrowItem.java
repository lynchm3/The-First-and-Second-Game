package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.animation.primary.AnimationThrow;
import com.marklynch.level.constructs.animation.secondary.AnimationThrown;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.Monster;
import com.marklynch.ui.ActivityLog;

public class ActionThrowItem extends Action {

	public static final String ACTION_NAME = "Throw";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	Square targetSquare;
	GameObject targetGameObject;
	GameObject gameObjectToThrow;

	// Default for hostiles
	public ActionThrowItem(Actor performer, Object target, GameObject gameObjectToThrow) {
		super(ACTION_NAME, "action_throw.png");
		super.gameObjectPerformer = this.performer = performer;
		if (target instanceof Square) {
			targetSquare = (Square) target;
			targetGameObject = targetSquare.inventory.gameObjectThatCantShareSquare;
		} else if (target instanceof GameObject) {
			targetGameObject = (GameObject) target;
			targetSquare = targetGameObject.squareGameObjectIsOn;
		}
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
			performer.setPrimaryAnimation(new AnimationThrow(performer, targetGameObject));
		}

		if (targetGameObject != null && targetGameObject.attackable) {
			float damage = targetGameObject.changeHealth(performer, this, gameObjectToThrow);

			if (Game.level.shouldLog(targetGameObject, performer)) {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " threw a ", gameObjectToThrow, " at ",
						targetGameObject, " for " + damage + " damage" }));
			} else {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " threw a ", gameObjectToThrow }));

			}
		}

		if (targetGameObject != null && targetGameObject.attackable)

		{
			targetGameObject.attackedBy(performer, this);
		}
		// target.attacked(performer);

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		// shoot projectile
		if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer) {
			performer.addSecondaryAnimation(new AnimationThrown(gameObjectToThrow.name, performer, this,
					targetGameObject, targetSquare, gameObjectToThrow, gameObjectToThrow, 1f, 0.5f, true) {
				@Override
				public void runCompletionAlgorightm() {
					super.runCompletionAlgorightm();
					postRangedAnimation(ActionThrowItem.this.performer, ActionThrowItem.this.gameObjectToThrow,
							ActionThrowItem.this.targetGameObject, this.targetSquare,
							ActionThrowItem.this.gameObjectToThrow, ActionThrowItem.this);
					// postRangedAnimation(arrow);
				}
			});
		} else {

			AnimationThrown.postRangedAnimation(ActionThrowItem.this.performer, ActionThrowItem.this.gameObjectToThrow,
					ActionThrowItem.this.targetGameObject, this.targetSquare, ActionThrowItem.this.gameObjectToThrow,
					ActionThrowItem.this);
		}
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

		if (Game.level.openInventories.size() > 0)
			Game.level.openInventories.get(0).close();

		if (performer.faction == Game.level.factions.player)

		{
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player)
			Game.level.endPlayerTurn();

		performer.actionsPerformedThisTurn.add(this);
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
				Crime crime = new Crime(this, this.performer, victim, Crime.TYPE.CRIME_ASSAULT);
				this.performer.crimesPerformedThisTurn.add(crime);
				this.performer.crimesPerformedInLifetime.add(crime);
				notifyWitnessesOfCrime(crime);
			}
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}
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
			actionName = ACTION_NAME + " " + gameObjectToThrow.name + " (too heavy)";
			disabledReason = "Too heavy";
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (targetSquare == null && targetGameObject == null)
			return false;

		if (!performer.canSeeSquare(targetSquare)) {
			actionName = ACTION_NAME + " " + gameObjectToThrow.name + " (can't reach)";
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		// Empty square, it's fine
		if (targetGameObject == null)
			return true;

		if (performer.attackers.contains(targetGameObject))
			return true;

		// Something that belongs to some one else
		if (targetGameObject.owner != null && targetGameObject.owner != performer)
			return false;

		// Is human
		if (targetGameObject instanceof Actor)
			if (!(targetGameObject instanceof Monster) && !(targetGameObject instanceof AggressiveWildAnimal))
				return false;

		return true;
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
