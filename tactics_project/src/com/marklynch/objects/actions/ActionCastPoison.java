package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.objects.ContainerForLiquids;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Liquid;
import com.marklynch.objects.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Monster;
import com.marklynch.objects.units.WildAnimal;
import com.marklynch.ui.ActivityLog;

public class ActionCastPoison extends Action {

	public static final String ACTION_NAME = "Cast Posion";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject target;

	// Default for hostiles
	public ActionCastPoison(Actor attacker, GameObject target) {
		super(ACTION_NAME, "action_poison.png");
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

		Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " cast poison on ", target }));
		if (target instanceof ContainerForLiquids && target.inventory.size() != 0) {
			ContainerForLiquids containerForLiquids = (ContainerForLiquids) target;
			target.inventory.remove(target.inventory.get(0));
			Liquid poison = Templates.POISON.makeCopy(null, containerForLiquids.owner, containerForLiquids.volume);
			target.inventory.add(poison);

			if ((target.squareGameObjectIsOn != null && target.squareGameObjectIsOn.visibleToPlayer)
					|| (target.inventorySquareGameObjectIsOn != null
							&& target.inventorySquareGameObjectIsOn.inventoryThisBelongsTo == Game.level.player.inventory)) {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " made ", poison, " in ", target }));
			}
		} else {
			if (Math.random() * 100 > target.getEffectivePosionResistance()) {
				target.addEffect(new EffectPoison(performer, target, 5));
			} else {

				if ((target.squareGameObjectIsOn != null && target.squareGameObjectIsOn.visibleToPlayer)
						|| (target.inventorySquareGameObjectIsOn != null
								&& target.inventorySquareGameObjectIsOn.inventoryThisBelongsTo == Game.level.player.inventory)) {
					Game.level.logOnScreen(
							new ActivityLog(new Object[] { target, " resisted poison cast by ", performer }));
				}
			}
		}

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

		if (performer.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
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
			if (!(target instanceof Monster) && !(target instanceof WildAnimal))
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
