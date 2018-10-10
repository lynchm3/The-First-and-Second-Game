package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Liquid;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.Monster;
import com.marklynch.ui.ActivityLog;

public class ActionCastPoison extends Action {

	public static final String ACTION_NAME = "Cast Posion";

	Actor performer;
	GameObject target;

	// Default for hostiles
	public ActionCastPoison(Actor attacker, GameObject target) {
		super(ACTION_NAME, "action_poison.png");
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
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " cast poison on ", target }));
		if (target instanceof ContainerForLiquids && target.inventory.size() != 0) {
			ContainerForLiquids containerForLiquids = (ContainerForLiquids) target;
			target.inventory.remove(target.inventory.get(0));
			Liquid poison = Templates.POISON.makeCopy(null, containerForLiquids.owner, containerForLiquids.volume);
			target.inventory.add(poison);

			if (Game.level.shouldLog(target, performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " made ", poison, " in ", target }));
		} else {
			if (Math.random() * 100 > target.highLevelStats.get(HIGH_LEVEL_STATS.POISON_DAMAGE).value) {
				target.addEffect(new EffectPoison(performer, target, 5));
			} else {

				if (Game.level.shouldLog(target, performer))
					Game.level.logOnScreen(
							new ActivityLog(new Object[] { target, " resisted poison cast by ", performer }));
			}
		}

		target.attackedBy(performer, this);
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

			Crime.TYPE severity = Crime.TYPE.CRIME_ASSAULT;
			if (!(target instanceof Actor))
				severity = Crime.TYPE.CRIME_VANDALISM;
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
			disabledReason = CANT_BE_ATTACKED;
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

		float loudness = 5;
		if (performer.equipped != null)
			return new Sound(performer, performer.equipped, target.squareGameObjectIsOn, loudness, legal,
					this.getClass());
		return null;
	}

}
