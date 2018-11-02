package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionLift extends Action {

	public static final String ACTION_NAME = "Lift";

	public ActionLift(Actor performer, GameObject target) {
		super(ACTION_NAME, texturePickUp, performer, target);
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
			if (legal)
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " picked up ", target }));
			else
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " stole ", target }));

		if (performer.inventory.contains(performer.equipped))
			performer.equippedBeforePickingUpObject = performer.equipped;
		target.squareGameObjectIsOn.inventory.remove(target);
		if (target.fitsInInventory)
			performer.inventory.add(target);
		performer.equip(target);
		if (target.owner == null)
			target.owner = performer;
		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {
			Crime crime = new Crime(this, this.performer, target.owner, Crime.TYPE.CRIME_THEFT, target);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		}
	}

	@Override
	public boolean check() {
		float maxWeightForPerformer = 50f + performer.getEffectiveHighLevelStat(HIGH_LEVEL_STATS.STRENGTH) * 10f;
		if (target.weight > maxWeightForPerformer) {
			disabledReason = TOO_HEAVY;
			return false;
		}
		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (target.owner != null && target.owner != performer) {
			illegalReason = THEFT;
			if (target.value > 100)
				illegalReason = GRAND_THEFT;

			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}