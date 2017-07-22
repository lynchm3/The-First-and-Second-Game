package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.Junk;
import com.marklynch.objects.Templates;
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

		Pickaxe pickaxe = (Pickaxe) performer.inventory.getGameObjectOfClass(Pickaxe.class);

		float damage = target.totalHealth / 4f;
		target.remainingHealth -= damage;
		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		Actor oreOwner = performer;
		if (target.owner != null)
			oreOwner = target.owner;

		Junk ore = Templates.ORE.makeCopy(null, oreOwner);
		performer.inventory.add(ore);

		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " mined ", target, " with ", pickaxe }));

		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " received ", ore }));

		if (target.checkIfDestroyed(null, this)) {
			if (performer.squareGameObjectIsOn.visibleToPlayer)
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " depleted a ", target }));
		}

		performer.showPow(target);

		if (performer.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endTurn();
		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {
			Crime crime = new Crime(this, this.performer, this.target.owner, 4, ore);
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
			return false;
		}

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
			float loudness = target.soundWhenHit * pickaxe.soundWhenHitting;
			return new Sound(performer, pickaxe, target.squareGameObjectIsOn, loudness, legal, this.getClass());
		}
		return null;
	}

}
