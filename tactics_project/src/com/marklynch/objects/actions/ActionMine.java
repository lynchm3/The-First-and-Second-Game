package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.AnimationTake;
import com.marklynch.objects.Junk;
import com.marklynch.objects.Vein;
import com.marklynch.objects.tools.Pickaxe;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionMine extends Action {

	public static final String ACTION_NAME = "Mine";
	public static final String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_NEED_PICKAXE = ACTION_NAME + " (need pickaxe)";

	Actor performer;
	Vein target;

	// Default for hostiles
	public ActionMine(Actor attacker, Vein vein) {
		super(ACTION_NAME, "action_mine.png");
		this.performer = attacker;
		this.target = vein;
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (!checkRange())
			return;

		Pickaxe pickaxe = (Pickaxe) performer.inventory.getGameObjectOfClass(Pickaxe.class);

		if (Game.level.shouldLog(target, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " mined ", target, " with ", pickaxe }));

		if (!target.infinite) {
			float damage = target.totalHealth / 4f;
			target.changeHealth(-damage, null, null);
			performer.distanceMovedThisTurn = performer.travelDistance;
			performer.hasAttackedThisTurn = true;
		}

		Junk ore = null;
		Actor oreOwner = performer;
		if (target.owner != null)
			oreOwner = target.owner;

		if (Math.random() < target.dropChance) {
			ore = target.oreTemplate.makeCopy(target.squareGameObjectIsOn, oreOwner);

			if (Game.level.openInventories.size() > 0) {
			} else if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer) {
				performer.secondaryAnimations.add(new AnimationTake(ore, performer, 0, 0, 1f));

			}
			performer.inventory.add(ore);

			if (Game.level.shouldLog(target, performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " received ", ore }));
		} else {

			if (Game.level.shouldLog(target, performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " received no ore" }));
		}

		if (!target.infinite && target.checkIfDestroyed(performer, this)) {

			if (Game.level.shouldLog(target, performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " depleted ", target }));
		}

		target.showPow();

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endTurn();
		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {

			Crime crime = new Crime(this, this.performer, this.target.owner, Crime.TYPE.CRIME_THEFT, ore);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}
	}

	@Override
	public boolean check() {

		if (!performer.inventory.contains(Pickaxe.class)) {
			actionName = ACTION_NAME_NEED_PICKAXE;
			disabledReason = "You need a pickaxe";
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (!performer.canSeeGameObject(target)) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}

		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		if (target.owner != null && target.owner != performer)
			return false;
		return true;
	}

	@Override
	public Sound createSound() {
		Pickaxe pickaxe = (Pickaxe) performer.inventory.getGameObjectOfClass(Pickaxe.class);
		if (pickaxe != null) {
			float loudness = Math.max(target.soundWhenHit, pickaxe.soundWhenHitting);
			return new Sound(performer, pickaxe, target.squareGameObjectIsOn, loudness, legal, this.getClass());
		}
		return null;
	}

}
