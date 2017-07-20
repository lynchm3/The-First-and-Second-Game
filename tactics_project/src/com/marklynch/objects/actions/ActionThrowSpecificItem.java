package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Monster;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.weapons.Projectile;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLog;

public class ActionThrowSpecificItem extends Action {

	public static final String ACTION_NAME = "Throw";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	Square targetSquare;
	GameObject targetGameObject;
	GameObject projectile;

	// Default for hostiles
	public ActionThrowSpecificItem(Actor performer, Object target, GameObject object) {
		super(ACTION_NAME, "action_throw.png");
		this.performer = performer;
		if (target instanceof Square) {
			targetSquare = (Square) target;
			targetGameObject = targetSquare.inventory.getGameObjectThatCantShareSquare();
		} else if (target instanceof GameObject) {
			targetGameObject = (GameObject) target;
			targetSquare = targetGameObject.squareGameObjectIsOn;
		}
		this.projectile = object;
		if (!check()) {
			enabled = false;
		} else {
			actionName = ACTION_NAME + " " + object.name;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		float damage = 0;

		if (projectile instanceof Weapon) {
			damage = performer.getEffectiveStrength() + projectile.weight / 10f
					+ ((Weapon) projectile).getTotalEffectiveDamage();
		} else {
			damage = performer.getEffectiveStrength() + projectile.weight / 10f;

		}
		if (targetGameObject != null && targetGameObject.attackable) {
			targetGameObject.remainingHealth -= damage;
		}

		if (performer.squareGameObjectIsOn.visibleToPlayer) {
			if (targetGameObject != null) {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " threw a ", projectile, " at ",
						targetGameObject, " for " + damage + " damage" }));
			} else {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " threw a ", projectile }));

			}
		}

		if (targetGameObject != null && targetGameObject.attackable) {
			targetGameObject.attackedBy(performer);
		}
		// target.attacked(performer);

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		// shoot projectile
		Game.level.projectiles
				.add(new Projectile(projectile.name, performer, targetGameObject, targetSquare, projectile, 2f, true));

		if (performer.equipped == projectile) {
			if (performer.inventory.contains(performer.equippedBeforePickingUpObject)) {
				performer.equip(performer.equippedBeforePickingUpObject);
			} else {
				performer.equip(null);
			}
			performer.equippedBeforePickingUpObject = null;
		}
		if (performer.helmet == projectile)
			performer.helmet = null;
		if (performer.bodyArmor == projectile)
			performer.bodyArmor = null;
		if (performer.legArmor == projectile)
			performer.legArmor = null;

		if (performer.inventory.contains(projectile))
			performer.inventory.remove(projectile);

		projectile.thrown(performer);

		if (performer.faction == Game.level.factions.get(0))

		{
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}

		if (performer == Game.level.player)
			Game.level.endTurn();

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {

			Actor victim = null;
			if (projectile instanceof Actor)
				victim = (Actor) projectile;
			else if (targetGameObject != null)
				victim = targetGameObject.owner;
			if (victim != null) {
				Crime crime = new Crime(this, this.performer, victim, 6);
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

		float maxDistance = (performer.getEffectiveStrength() * 100) / projectile.weight;
		if (maxDistance > 10)
			maxDistance = 10;

		if (performer.straightLineDistanceTo(targetSquare) > maxDistance) {
			actionName = ACTION_NAME + " " + projectile.name + " (too heavy)";
			return false;
		}

		if (!performer.canSeeSquare(targetSquare)) {
			actionName = ACTION_NAME + " " + projectile.name + " (can't reach)";
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		// Empty square, it's fine
		if (targetGameObject == null)
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
			float loudness = targetGameObject.soundWhenHit * projectile.soundWhenHitting;
			if (performer.equipped != null)
				return new Sound(performer, projectile, targetSquare, loudness, legal, this.getClass());
		} else {
			float loudness = projectile.soundWhenHitting;
			if (performer.equipped != null)
				return new Sound(performer, projectile, targetSquare, loudness, legal, this.getClass());

		}
		return null;
	}

}
