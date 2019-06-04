package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Liquid;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.Jar;
import com.marklynch.ui.ActivityLog;

public class ActionCastPoison extends Action {

	public static final String ACTION_NAME = "Cast Posion";

	// Default for hostiles
	public ActionCastPoison(Actor attacker, GameObject target) {
		super(ACTION_NAME, texturePoison, attacker, target);
		this.targetGameObject = target;
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

		if (Game.level.shouldLog(targetGameObject, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " cast poison on ", targetGameObject }));
		if (targetGameObject instanceof Jar && targetGameObject.inventory.size() != 0) {
			Jar jar = (Jar) targetGameObject;
			targetGameObject.inventory.remove(targetGameObject.inventory.get(0));
			Liquid poison = Templates.POISON.makeCopy(null, jar.owner);
			targetGameObject.inventory.add(poison);

			if (Game.level.shouldLog(targetGameObject, performer))
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { performer, " made ", poison, " in ", targetGameObject }));
		} else {
			if (Math.random() * 100 > targetGameObject.highLevelStats.get(HIGH_LEVEL_STATS.POISON_DAMAGE).value) {
				targetGameObject.addEffect(new EffectPoison(performer, targetGameObject, 5));

				if (targetGameObject.squareGameObjectIsOn != null) {
					targetGameObject.squareGameObjectIsOn.liquidSpread(Templates.POISON);
				}
			} else {

				if (Game.level.shouldLog(targetGameObject, performer))
					Game.level.logOnScreen(
							new ActivityLog(new Object[] { targetGameObject, " resisted poison cast by ", performer }));
			}
		}

		targetGameObject.attackedBy(performer, this);
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

		if (sound != null)
			sound.play();

		if (!legal) {

			Actor victim;
			if (targetGameObject instanceof Actor)
				victim = (Actor) targetGameObject;
			else
				victim = targetGameObject.owner;

			Crime.TYPE severity = Crime.TYPE.CRIME_ASSAULT;
			if (!(targetGameObject instanceof Actor))
				severity = Crime.TYPE.CRIME_VANDALISM;
			Crime crime = new Crime(this.performer, victim, severity);
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

		if (targetGameObject.squareGameObjectIsOn == null)
			return null;

		// Sound

		if (performer.equipped == null)
			return null;

		float loudness = 5;
		if (performer.equipped != null)
			return new Sound(performer, performer.equipped, targetGameObject.squareGameObjectIsOn, loudness, legal,
					this.getClass());
		return null;
	}

}
