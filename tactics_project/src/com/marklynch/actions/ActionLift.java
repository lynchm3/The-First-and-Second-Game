package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
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

		if (Game.level.shouldLog(targetGameObject, performer))
			if (legal)
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " picked up ", targetGameObject }));
			else
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " stole ", targetGameObject }));

		if (performer.inventory.contains(performer.equipped))
			performer.equippedBeforePickingUpObject = performer.equipped;
		targetGameObject.squareGameObjectIsOn.inventory.remove(targetGameObject);
		if (targetGameObject.fitsInInventory)
			performer.inventory.add(targetGameObject);
		performer.equip(targetGameObject);
		if (targetGameObject.owner == null)
			targetGameObject.owner = performer;
		if (sound != null)
			sound.play();

		if (!legal) {
			Crime crime = new Crime(this.performer, targetGameObject.owner, Crime.TYPE.CRIME_THEFT, targetGameObject);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		}
	}

	@Override
	public boolean check() {
		if (targetGameObject.moveable == false)
			return false;

		float maxWeightForPerformer = 50f + performer.getEffectiveHighLevelStat(HIGH_LEVEL_STATS.STRENGTH) * 10f;
		if (targetGameObject.weight > maxWeightForPerformer) {
			disabledReason = TOO_HEAVY;
			return false;
		}
		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) > 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (targetGameObject.owner != null && targetGameObject.owner != performer) {
			illegalReason = THEFT;
			if (targetGameObject.value > 100)
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