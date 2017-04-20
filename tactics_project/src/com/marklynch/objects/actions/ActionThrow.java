package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Projectile;
import com.marklynch.ui.ActivityLog;

public class ActionThrow extends Action {

	public static final String ACTION_NAME = "Throw";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	Square targetSquare;
	GameObject targetGameObject;
	GameObject projectile;

	// Default for hostiles
	public ActionThrow(Actor performer, Object target, GameObject object) {
		super(ACTION_NAME);
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
			actionName = ACTION_NAME + " " + object.name + " (can't reach)";
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

		float damage = 5;
		if (targetGameObject != null && targetGameObject.attackable) {
			targetGameObject.remainingHealth -= damage;
			targetGameObject.attacked(performer);
		}
		// target.attacked(performer);

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;
		String attackTypeString;

		if (performer.squareGameObjectIsOn.visibleToPlayer) {
			if (targetGameObject != null) {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " threw a ", projectile, " at ",
						targetGameObject, " for " + damage + " damage" }));
			} else {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " threw a ", projectile }));

			}
		}

		// shoot projectile
		Game.level.projectiles
				.add(new Projectile(projectile.name, performer, targetGameObject, targetSquare, projectile, 2f, true));

		if (performer.equipped == projectile)
			performer.equipped = null;

		if (performer.inventory.contains(projectile))
			performer.inventory.remove(projectile);

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

		if (performer.straightLineDistanceTo(targetSquare) > 10)
			return false;

		if (!performer.visibleFrom(targetSquare))
			return false;

		return true;
	}

	@Override
	public boolean checkLegality() {
		// Something that belongs to some one else
		if (targetGameObject != null && targetGameObject.owner != null && targetGameObject.owner != performer)
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
