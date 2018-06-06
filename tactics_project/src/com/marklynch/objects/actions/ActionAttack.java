package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.primary.AnimationFlinch;
import com.marklynch.level.constructs.animation.primary.AnimationShootArrow;
import com.marklynch.level.constructs.animation.primary.AnimationSlash;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBleed;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Liquid;
import com.marklynch.objects.Wall;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Monster;
import com.marklynch.objects.units.WildAnimal;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLog;

public class ActionAttack extends Action {

	public static final String ACTION_NAME = "Attack";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject target;

	// Default for hostiles
	public ActionAttack(Actor attacker, GameObject target) {
		super(ACTION_NAME, "action_attack.png");
		super.gameObjectPerformer = this.performer = attacker;
		this.target = target;

		if (performer.equipped != null && performer.equipped.maxRange > 1)
			this.image = Action.textureBow;

		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
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

		if (performer.squareGameObjectIsOn.xInGrid > target.squareGameObjectIsOn.xInGrid) {
			performer.backwards = true;
		} else if (performer.squareGameObjectIsOn.xInGrid < target.squareGameObjectIsOn.xInGrid) {
			performer.backwards = false;
		}

		// float damage = 1;

		if (performer.equipped != null) {
			weapon = performer.equipped;
		} else {
			weapon = performer;
		}

		if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer) {
			if (weapon.maxRange == 1) {
				// performer.primaryAnimation = new AnimationSlash(performer,
				// target);
				performer.setPrimaryAnimation(new AnimationSlash(performer, target) {

					@Override
					public void complete() {
						super.complete();
						postAnimation();
					}
				}

				);
			} else {
				performer.setPrimaryAnimation(new AnimationShootArrow(performer, target, weapon, this) {

					@Override
					public void complete() {
						super.complete();
						postAnimation();
					}
				});
			}
		}

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		// shoot projectile
		// if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) >
		// 1) {
		// if (performer.squareGameObjectIsOn.onScreen() &&
		// performer.squareGameObjectIsOn.visibleToPlayer)
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

	public void postAnimation() {

		if (weapon.maxRange == 1 && target.attackable) {
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
				smashContainer((ContainerForLiquids) weapon);
			}

			target.setPrimaryAnimation(
					new AnimationFlinch(target, performer.squareGameObjectIsOn, target.getPrimaryAnimation()));
		}

	}

	@Override
	public boolean check() {

		if (!target.attackable) {
			disabledReason = "Can't be attacked";
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
		// Something that belongs to some one else
		if (performer.attackers.contains(target))
			return true;

		if (target.owner != null && target.owner != performer)
			return false;
		// Is human

		if (target instanceof Actor)
			if (!(target instanceof Monster) && !(target instanceof WildAnimal))
				return false;
		return true;
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

	public void smashContainer(ContainerForLiquids container) {
		target.squareGameObjectIsOn.inventory.add(container);
		new ActionSmash(performer, container).perform();

		// Find a square for broken glass and put it there
		Square squareForGlass = null;
		if (!container.squareGameObjectIsOn.inventory.contains(Wall.class)) {
			squareForGlass = container.squareGameObjectIsOn;
		}

		if (squareForGlass != null)
			Templates.BROKEN_GLASS.makeCopy(squareForGlass, container.owner);

		if (container.inventory.size() > 0 && container.inventory.get(0) instanceof Liquid) {
			Liquid liquid = (Liquid) container.inventory.get(0);
			for (GameObject gameObject : container.squareGameObjectIsOn.inventory.getGameObjects()) {
				if (gameObject != container) {
					// new ActionDouse(shooter, gameObject).perform();
					for (Effect effect : liquid.touchEffects) {
						gameObject.addEffect(effect.makeCopy(performer, gameObject));
						if (effect instanceof EffectWet)
							gameObject.removeBurningEffect();
					}
					if (gameObject instanceof Actor)
						gameObject.addEffect(new EffectBleed(performer, target, 5));
				}

			}
		}
		for (GameObject gameObject : container.squareGameObjectIsOn.inventory.getGameObjects()) {
			if (gameObject instanceof Actor)
				gameObject.addEffect(new EffectBleed(performer, target, 5));

		}

	}

}
