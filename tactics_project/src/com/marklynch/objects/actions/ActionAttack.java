package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.Monster;
import com.marklynch.objects.weapons.Projectile;
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
		this.performer = attacker;
		this.target = target;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		float damage = 1;
		Weapon weapon = null;
		if (performer.equipped != null && performer.equipped instanceof Weapon) {
			weapon = (Weapon) performer.equipped;
			damage = weapon.getTotalEffectiveDamage();
		}

		if (target.attackable) {
			target.remainingHealth -= damage;
			String attackTypeString;
			attackTypeString = "attacked ";

			if (performer.squareGameObjectIsOn.visibleToPlayer) {

				if (weapon != null) {
					Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " " + attackTypeString + " ",
							target, " with ", weapon, " for " + damage + " damage" }));
				} else {
					Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " " + attackTypeString + " ",
							target, " for " + damage + " damage" }));

				}
			}
			target.attackedBy(performer);
		}

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		// shoot projectile
		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1) {
			Game.level.projectiles.add(new Projectile("Arrow", performer, target, target.squareGameObjectIsOn,
					Templates.ARROW.makeCopy(null, null), 2f, true));
		} else {
			performer.showPow(target);
		}

		if (performer.faction == Game.level.factions.get(0)) {
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

			Crime crime = new Crime(this, this.performer, victim, 6);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}

		if (performer == Game.level.player)
			Game.level.endTurn();
	}

	@Override
	public boolean check() {

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

		if (!target.attackable)
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

		if (target.owner != null && target.owner != performer)
			return false;
		// Is human

		if (target instanceof Actor)
			if (!(target instanceof Monster) && !(target instanceof AggressiveWildAnimal))
				return false;
		return true;
	}

	@Override
	public Sound createSound() {

		// Sound

		if (performer.equipped == null)
			return null;

		float loudness = target.soundWhenHit * performer.equipped.soundWhenHitting;
		if (performer.equipped != null)
			return new Sound(performer, performer.equipped, target.squareGameObjectIsOn, loudness, legal,
					this.getClass());
		return null;
	}

}
