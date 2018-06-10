package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.animation.secondary.AnimationThrown;
import com.marklynch.level.constructs.effect.EffectBurning;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.Monster;
import com.marklynch.ui.ActivityLog;

public class ActionCastBurn extends Action {

	public static final String ACTION_NAME = "Cast Burn";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject target;

	// Default for hostiles
	public ActionCastBurn(Actor attacker, GameObject target) {
		super(ACTION_NAME, "action_burn.png");
		super.gameObjectPerformer = this.performer = attacker;
		this.target = target;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		} else {
			actionName = ACTION_NAME + " (" + (100 - target.highLevelStats.get(HIGH_LEVEL_STATS.FIRE_DAMAGE).value)
					+ "%)";
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

		if (Game.level.shouldLog(target, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " cast burn on ", target }));
		if (Math.random() * 100 > target.highLevelStats.get(HIGH_LEVEL_STATS.FIRE_DAMAGE).value) {
			target.removeWetEffect();
			target.addEffect(new EffectBurning(performer, target, 5));

		} else {

			if (Game.level.shouldLog(target, performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { target, " resisted burn cast by ", performer }));

		}

		target.attackedBy(performer, this);
		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		// shoot projectile
		if (target.squareGameObjectIsOn != null && performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1) {
			if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer)
				performer.addSecondaryAnimation(
						new AnimationThrown("Fire Ball", performer, this, target, target.squareGameObjectIsOn,
								Templates.FIRE_BALL.makeCopy(null, null), performer, 1f, 0f, true));
		}
		// else {
		// performer.showPow(target);
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

			Crime.TYPE severity = Crime.TYPE.CRIME_ASSAULT;
			if (!(target instanceof Actor))
				severity = Crime.TYPE.CRIME_ARSON;
			Crime crime = new Crime(this, this.performer, victim, severity);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}

		if (performer == Game.level.player)
			Game.level.endPlayerTurn();
	}

	@Override
	public boolean check() {

		// if (!performer.canSeeGameObject(target))
		// return false;

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

		float loudness = 10f;
		if (performer.equipped != null)
			return new Sound(performer, performer.equipped, target.squareGameObjectIsOn, loudness, legal,
					this.getClass());
		return null;
	}

}
