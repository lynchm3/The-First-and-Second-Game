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

		if (gameObjectPerformer.squareGameObjectIsOn.xInGrid > target.squareGameObjectIsOn.xInGrid) {
			gameObjectPerformer.backwards = true;
		} else if (gameObjectPerformer.squareGameObjectIsOn.xInGrid < target.squareGameObjectIsOn.xInGrid) {
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
			gameObjectPerformer
					.setPrimaryAnimation(new AnimationSlash(gameObjectPerformer, target, new OnCompletionListener() {
						@Override
						public void animationComplete(GameObject gameObject) {
							postMeleeAnimation();
						}
					}));
		} else {

			// Ranged weapon

			gameObjectPerformer.setPrimaryAnimation(new AnimationShootArrow(performer, target, weapon, this, null) {

				@Override
				public void shootArrow() {

					AnimationThrown animationThrown;
					arrow = Templates.ARROW.makeCopy(null, null);
					arrow.drawOffsetRatioX = (float) (0.45f + Math.random() * 0.1f);
					arrow.drawOffsetRatioY = (float) (0.45f + Math.random() * 0.1f);
					animationThrown = new AnimationThrown("Arrow", (Actor) performer, ActionAttack.this, target,
							target.squareGameObjectIsOn, arrow, weapon, 2f, 0f, true, null);

					// AnimationThrown(String name, Actor shooter, Action action, GameObject
					// performer, Square targetSquare,
					// GameObject projectileObject, GameObject weapon, float speed, float
					// rotationSpeed, boolean onTarget,
					// OnCompletionListener onCompletionListener)

					performer.addSecondaryAnimation(animationThrown);
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

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {

			Actor victim;
			if (target instanceof Actor)
				victim = (Actor) target;
			else
				victim = target.owner;

			Crime.TYPE type = Crime.TYPE.CRIME_ASSAULT;
			if (!(target instanceof Actor))
				type = Crime.TYPE.CRIME_VANDALISM;
			Crime crime = new Crime(this, this.performer, victim, type);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}

		if (performer == Game.level.player)
			Game.level.endPlayerTurn();
	}

	public void postMeleeAnimation() {

		if (target.attackable) {
			float damage = target.changeHealth(performer, ActionAttack.this, weapon);
			String attackTypeString;
			attackTypeString = "attacked ";

			if (performer.squareGameObjectIsOn.visibleToPlayer) {

				if (weapon != performer) {
					if (Game.level.shouldLog(target, performer))
						Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " " + attackTypeString + " ",
								target, " with ", weapon, " for " + damage + " damage" }));
				} else {
					if (Game.level.shouldLog(target, performer))
						Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " " + attackTypeString + " ",
								target, " for " + damage + " damage" }));
				}
			}

			if (weapon instanceof ContainerForLiquids) {
				target.squareGameObjectIsOn.inventory.add(weapon);
				((ContainerForLiquids) weapon).landed(performer, this);
				// AnimationThrown.smashContainer(actor, target, (ContainerForLiquids)
				// weapon);
			}

			if (target.remainingHealth > 0)
				target.setPrimaryAnimation(new AnimationFlinch(target, performer.squareGameObjectIsOn,
						target.getPrimaryAnimation(), null));
		}

	}

	@Override
	public boolean check() {

		if (!target.attackable) {
			disabledReason = CANT_BE_ATTACKED;
			return false;
		}

		if (!performer.canSeeGameObject(target))
			return false;

		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.equipped == null || !(performer.equipped instanceof Weapon)) {
			if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1)
				return false;
		} else {
			Weapon weapon = (Weapon) performer.equipped;
			if (!weapon.hasRange(performer.straightLineDistanceTo(target.squareGameObjectIsOn)))
				return false;
		}

		if (!performer.canSeeGameObject(target))
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
		return standardAttackLegalityCheck(performer, target);
	}

	@Override
	public Sound createSound() {

		// Sound

		if (performer.equipped == null)
			return null;

		float loudness = Math.max(target.soundWhenHit, performer.equipped.soundWhenHitting);
		if (performer.equipped != null)
			return new Sound(performer, performer.equipped, target.squareGameObjectIsOn, loudness, legal,
					this.getClass());
		return null;
	}

	@Override
	public boolean shouldContinue() {

		if (performed && Player.inFight()) {
			return false;
		}

		if (target.remainingHealth <= 0) {
			disabledReason = null;
			return false;
		}

		return true;
	}
}
