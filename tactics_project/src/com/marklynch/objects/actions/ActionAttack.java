package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationFlinch;
import com.marklynch.level.constructs.animation.primary.AnimationShootArrow;
import com.marklynch.level.constructs.animation.primary.AnimationSlash;
import com.marklynch.level.constructs.animation.secondary.AnimationThrown;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Player;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLog;

public class ActionAttack extends Action {

	public static final String ACTION_NAME = "Attack";

	// Actor performer;
	// GameObject targetGameObject;
	Actor actor;

	// Default for hostiles
	public ActionAttack(Actor performer, GameObject target) {
		super(ACTION_NAME, textureAttack, performer, performer, target, null);
		this.gameObjectperformer = performer;
		this.targetGameObject = target;

		if (performer.equipped != null && performer.equipped.maxRange > 1)
			this.image = Action.textureBow;

		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	GameObject weapon;

	@Override
	public void perform() {
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (gameObjectperformer.squareGameObjectIsOn.xInGrid > targetGameObject.squareGameObjectIsOn.xInGrid) {
			gameObjectperformer.backwards = true;
		} else if (gameObjectperformer.squareGameObjectIsOn.xInGrid < targetGameObject.squareGameObjectIsOn.xInGrid) {
			gameObjectperformer.backwards = false;
		}

		// float damage = 1;

		if (gameObjectperformer.equipped != null) {
			weapon = gameObjectperformer.equipped;
		} else {
			weapon = gameObjectperformer;
		}

		if (weapon.maxRange == 1) {

			// Melee weapons
			gameObjectperformer.setPrimaryAnimation(new AnimationSlash(gameObjectperformer, targetGameObject, new OnCompletionListener() {
				@Override
				public void animationComplete(GameObject gameObject) {
					postMeleeAnimation();
				}
			}));
		} else {

			// Ranged weapon

			gameObjectperformer.setPrimaryAnimation(new AnimationShootArrow(actor, targetGameObject, weapon, this, null) {

				@Override
				public void shootArrow() {

					AnimationThrown animationThrown;
					arrow = Templates.ARROW.makeCopy(null, null);
					arrow.drawOffsetRatioX = (float) (0.45f + Math.random() * 0.1f);
					arrow.drawOffsetRatioY = (float) (0.45f + Math.random() * 0.1f);
					animationThrown = new AnimationThrown("Arrow", actor, ActionAttack.this, targetGameObject,
							targetGameObject.squareGameObjectIsOn, arrow, weapon, 2f, 0f, true, null);
					actor.addSecondaryAnimation(animationThrown);
				}

				@Override
				public void draw3() {
					// TODO Auto-generated method stub

				}
			});
		}

		actor.distanceMovedThisTurn = actor.travelDistance;
		actor.hasAttackedThisTurn = true;

		// shoot projectile
		// if (actor.straightLineDistanceTo(target.squareGameObjectIsOn) >
		// 1) {
		// if (actor.squareGameObjectIsOn.onScreen() &&
		// actor.squareGameObjectIsOn.visibleToPlayer)
		// } else {
		// }

		if (actor.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		actor.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {

			Actor victim;
			if (targetGameObject instanceof Actor)
				victim = (Actor) targetGameObject;
			else
				victim = targetGameObject.owner;

			Crime.TYPE type = Crime.TYPE.CRIME_ASSAULT;
			if (!(targetGameObject instanceof Actor))
				type = Crime.TYPE.CRIME_VANDALISM;
			Crime crime = new Crime(this, this.actor, victim, type);
			this.actor.crimesPerformedThisTurn.add(crime);
			this.actor.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, actor, actor.squareGameObjectIsOn);
		}

		if (actor == Game.level.player)
			Game.level.endPlayerTurn();
	}

	public void postMeleeAnimation() {

		if (targetGameObject.attackable) {
			float damage = targetGameObject.changeHealth(actor, ActionAttack.this, weapon);
			String attackTypeString;
			attackTypeString = "attacked ";

			if (actor.squareGameObjectIsOn.visibleToPlayer) {

				if (weapon != actor) {
					if (Game.level.shouldLog(targetGameObject, actor))
						Game.level.logOnScreen(new ActivityLog(new Object[] { actor, " " + attackTypeString + " ",
								targetGameObject, " with ", weapon, " for " + damage + " damage" }));
				} else {
					if (Game.level.shouldLog(targetGameObject, actor))
						Game.level.logOnScreen(new ActivityLog(new Object[] { actor, " " + attackTypeString + " ",
								targetGameObject, " for " + damage + " damage" }));
				}
			}

			if (weapon instanceof ContainerForLiquids) {
				targetGameObject.squareGameObjectIsOn.inventory.add(weapon);
				((ContainerForLiquids) weapon).landed(actor, this);
				// AnimationThrown.smashContainer(actor, target, (ContainerForLiquids)
				// weapon);
			}

			if (targetGameObject.remainingHealth > 0)
				targetGameObject.setPrimaryAnimation(new AnimationFlinch(targetGameObject, actor.squareGameObjectIsOn,
						targetGameObject.getPrimaryAnimation(), null));
		}

	}

	@Override
	public boolean check() {

		if (!targetGameObject.attackable) {
			disabledReason = CANT_BE_ATTACKED;
			return false;
		}

		if (!actor.canSeeGameObject(targetGameObject))
			return false;

		return true;
	}

	@Override
	public boolean checkRange() {

		if (actor.equipped == null || !(actor.equipped instanceof Weapon)) {
			if (actor.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) > 1)
				return false;
		} else {
			Weapon weapon = (Weapon) actor.equipped;
			if (!weapon.hasRange(actor.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn)))
				return false;
		}

		if (!actor.canSeeGameObject(targetGameObject))
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
		return standardAttackLegalityCheck(actor, targetGameObject);
	}

	@Override
	public Sound createSound() {

		// Sound

		if (actor.equipped == null)
			return null;

		float loudness = Math.max(targetGameObject.soundWhenHit, actor.equipped.soundWhenHitting);
		if (actor.equipped != null)
			return new Sound(actor, actor.equipped, targetGameObject.squareGameObjectIsOn, loudness, legal,
					this.getClass());
		return null;
	}

	@Override
	public boolean shouldContinue() {

		if (performed && Player.inFight()) {
			return false;
		}

		if (targetGameObject.remainingHealth <= 0) {
			disabledReason = null;
			return false;
		}

		return true;
	}
}
