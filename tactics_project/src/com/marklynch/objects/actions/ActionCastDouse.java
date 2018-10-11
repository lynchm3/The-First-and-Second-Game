package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Liquid;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionCastDouse extends Action {

	public static final String ACTION_NAME = "Cast Douse";

	Actor performer;
	GameObject target;

	// Default for hostiles
	public ActionCastDouse(Actor attacker, GameObject target) {
		super(ACTION_NAME, "action_douse.png");
		super.gameObjectPerformer = this.performer = attacker;
		this.target = target;
		if (!check()) {
			enabled = false;
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
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " cast douse on ", target }));
		target.removeBurningEffect();
		if (target instanceof ContainerForLiquids) {
			if (target.inventory.size() == 0) {
				Liquid water = Templates.WATER.makeCopy(null, performer, ((ContainerForLiquids) target).volume);
				target.inventory.add(water);

				if (Game.level.shouldLog(target, performer))
					Game.level.logOnScreen(
							new ActivityLog(new Object[] { performer, " filled ", target, " with ", water }));
			}
		} else {
			target.addEffect(new EffectWet(performer, target, 5));
		}
		// if (Math.random() * 100 > target.fireResistance) {
		// target.addEffect(new EffectBurn(performer, target, 5));
		//
		// } else {
		// Game.level.logOnScreen(new ActivityLog(new Object[] { target, "
		// resisted burn cast by ", performer }));
		//
		// }

		// target.attackedBy(performer);
		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		// shoot projectile
		// if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) >
		// 1) {
		// Game.level.projectiles.add(new Projectile("Arrow", performer, target,
		// target.squareGameObjectIsOn,
		// Templates.WATER_BALL.makeCopy(null, null), 1f, true));
		// }
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

			Crime crime = new Crime(this, this.performer, victim, Crime.TYPE.CRIME_DOUSE);
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
			disabledReason = CANT_BE_ATTACKED;
			return false;
		}

		if (!performer.canSeeGameObject(target))
			return false;

		return true;
	}

	@Override
	public boolean checkRange() {

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
		return standardAttackLegalityCheck(performer, target);
	}

	@Override
	public Sound createSound() {

		if (target.squareGameObjectIsOn == null)
			return null;

		// Sound

		if (performer.equipped == null)
			return null;

		float loudness = 5;
		if (performer.equipped != null)
			return new Sound(performer, performer.equipped, target.squareGameObjectIsOn, loudness, legal,
					this.getClass());
		return null;
	}

}
