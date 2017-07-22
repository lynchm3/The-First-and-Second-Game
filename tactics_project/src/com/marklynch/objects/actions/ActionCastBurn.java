package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.effect.EffectBurning;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.Monster;
import com.marklynch.objects.weapons.Projectile;
import com.marklynch.ui.ActivityLog;

public class ActionCastBurn extends Action {

	public static final String ACTION_NAME = "Cast Burn";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject target;

	// Default for hostiles
	public ActionCastBurn(Actor attacker, GameObject target) {
		super(ACTION_NAME, "action_burn.png");
		this.performer = attacker;
		this.target = target;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		} else {
			actionName = ACTION_NAME + " (" + (100 - target.getEffectiveFireResistance()) + "%)";
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if ((target.squareGameObjectIsOn != null && target.squareGameObjectIsOn.visibleToPlayer)
				|| (target.inventoryThatHoldsThisObject != null
						&& target.inventoryThatHoldsThisObject == Game.level.player.inventory)) {
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " cast burn on ", target }));
		}
		if (Math.random() * 100 > target.getEffectiveFireResistance()) {
			target.removeWetEffect();
			target.addEffect(new EffectBurning(performer, target, 5));

		} else {

			if ((target.squareGameObjectIsOn != null && target.squareGameObjectIsOn.visibleToPlayer)
					|| (target.inventoryThatHoldsThisObject != null
							&& target.inventoryThatHoldsThisObject == Game.level.player.inventory)) {
				Game.level.logOnScreen(new ActivityLog(new Object[] { target, " resisted burn cast by ", performer }));
			}

		}

		target.attackedBy(performer, this);
		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		// shoot projectile
		if (target.squareGameObjectIsOn != null && performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1) {
			Game.level.projectiles.add(new Projectile("Arrow", performer, target, target.squareGameObjectIsOn,
					Templates.FIRE_BALL.makeCopy(null, null), 1f, true));
		}
		// else {
		// performer.showPow(target);
		// }

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

		// if (!performer.canSeeGameObject(target))
		// return false;

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

		if (target.squareGameObjectIsOn == null)
			return null;

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
