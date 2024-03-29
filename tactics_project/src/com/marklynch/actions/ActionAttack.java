package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationFlinch;
import com.marklynch.level.constructs.animation.primary.AnimationShootArrow;
import com.marklynch.level.constructs.animation.primary.AnimationSlash;
import com.marklynch.level.constructs.animation.secondary.AnimationThrown;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Player;
import com.marklynch.objects.armor.Weapon;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.Jar;
import com.marklynch.ui.ActivityLog;

public class ActionAttack extends Action {

	public static final String ACTION_NAME = "Attack";

	// Default for hostiles
	public ActionAttack(Actor performer, GameObject target) {
		super(ACTION_NAME, textureAttack, performer, target);

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

		if (gameObjectPerformer.squareGameObjectIsOn.xInGrid > targetGameObject.squareGameObjectIsOn.xInGrid) {
			gameObjectPerformer.backwards = true;
		} else if (gameObjectPerformer.squareGameObjectIsOn.xInGrid < targetGameObject.squareGameObjectIsOn.xInGrid) {
			gameObjectPerformer.backwards = false;
		}

		// float damage = 1;

		if (gameObjectPerformer.equipped != null) {
			weapon = gameObjectPerformer.equipped;
		} else {
			weapon = gameObjectPerformer;
		}

		if (weapon.maxRange == 1) {

			// Melee weapons
			gameObjectPerformer.setPrimaryAnimation(
					new AnimationSlash(gameObjectPerformer, targetGameObject, new OnCompletionListener() {
						@Override
						public void animationComplete(GameObject gameObject) {
							postMeleeAnimation();
						}
					}));
		} else {

			// Ranged weapon

			gameObjectPerformer
					.setPrimaryAnimation(new AnimationShootArrow(performer, targetGameObject, weapon, this, null) {

						@Override
						public void shootArrow() {

							AnimationThrown animationThrown;
							arrow = Templates.ARROW.makeCopy(null, null);
							arrow.drawOffsetRatioX = (float) (0.45f + Math.random() * 0.1f);
							arrow.drawOffsetX = arrow.drawOffsetRatioX * Game.SQUARE_WIDTH;
							arrow.drawOffsetRatioY = (float) (0.45f + Math.random() * 0.1f);
							arrow.drawOffsetY = arrow.drawOffsetRatioY * Game.SQUARE_HEIGHT;
							animationThrown = new AnimationThrown("Arrow", (Actor) performer, ActionAttack.this,
									targetGameObject, targetGameObject.squareGameObjectIsOn, arrow, weapon, 2f, 0f,
									true, null);

							// AnimationThrown(String name, Actor shooter, Action action, GameObject
							// performer, Square targetSquare,
							// GameObject projectileObject, GameObject weapon, float speed, float
							// rotationSpeed, boolean onTarget,
							// OnCompletionListener onCompletionListener)

							Level.addSecondaryAnimation(animationThrown);
						}

						@Override
						public void draw3() {
							// TODO Auto-generated method stub

						}
					});
		}

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		// shoot projectile
		// if (actor.straightLineDistanceTo(target.squareGameObjectIsOn) >
		// 1) {
		// if (actor.squareGameObjectIsOn.onScreen() &&
		// actor.squareGameObjectIsOn.visibleToPlayer)
		// } else {
		// }

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

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
			Crime crime = new Crime(this.performer, victim, type);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(performer, performer.squareGameObjectIsOn);
		}

		if (performer == Game.level.player)
			Game.level.endPlayerTurn();
	}

	public void postMeleeAnimation() {

		if (targetGameObject.attackable) {

			targetGameObject.showPow();

			float damage = targetGameObject.changeHealth(performer, ActionAttack.this, weapon);
			String attackTypeString;
			attackTypeString = "attacked ";

			if (performer.squareGameObjectIsOn.visibleToPlayer) {

				if (weapon != performer) {
					if (Game.level.shouldLog(targetGameObject, performer))
						Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " " + attackTypeString + " ",
								targetGameObject, " with ", weapon, " for " + damage + " damage" }));
				} else {
					if (Game.level.shouldLog(targetGameObject, performer))
						Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " " + attackTypeString + " ",
								targetGameObject, " for " + damage + " damage" }));
				}
			}

			if (weapon instanceof Jar) {
				targetGameObject.squareGameObjectIsOn.inventory.add(weapon);
				((Jar) weapon).landed(performer, this);
			}

			if (targetGameObject.remainingHealth > 0)
				targetGameObject.setPrimaryAnimation(new AnimationFlinch(targetGameObject,
						performer.squareGameObjectIsOn, targetGameObject.getPrimaryAnimation(), null));
		}

	}

	@Override
	public boolean check() {

		if (!targetGameObject.attackable) {
			disabledReason = CANT_BE_ATTACKED;
			return false;
		}

		if (!performer.canSeeGameObject(targetGameObject))
			return false;

		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.equipped == null || !(performer.equipped instanceof Weapon)) {
			if (performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) > 1)
				return false;
		} else {
			Weapon weapon = (Weapon) performer.equipped;
			if (!weapon.hasRange(performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn)))
				return false;
		}

		if (!performer.canSeeGameObject(targetGameObject))
			return false;

		if (performer == Game.level.player && !targetSquare.playerCanCastTo)
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
		return standardAttackLegalityCheck(performer, targetGameObject);
	}

	@Override
	public Sound createSound() {

		// Sound

		if (performer.equipped == null)
			return null;

		float loudness = Math.max(targetGameObject.soundWhenHit, performer.equipped.soundWhenHitting);
		if (performer.equipped != null)
			return new Sound(performer, performer.equipped, targetGameObject.squareGameObjectIsOn, loudness, legal,
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
