package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.GameObject.HIGH_LEVEL_STATS;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionLift extends Action {

	public static final String ACTION_NAME = "Lift";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject object;

	public ActionLift(Actor performer, GameObject object) {
		super(ACTION_NAME, "action_pick_up.png");
		super.gameObjectPerformer = this.performer = performer;
		this.object = object;
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

		if (Game.level.shouldLog(object, performer))
			if (legal)
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " picked up ", object }));
			else
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " stole ", object }));

		if (performer.inventory.contains(performer.equipped))
			performer.equippedBeforePickingUpObject = performer.equipped;
		object.squareGameObjectIsOn.inventory.remove(object);
		if (object.fitsInInventory)
			performer.inventory.add(object);
		performer.equip(object);
		if (object.owner == null)
			object.owner = performer;
		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {
			Crime crime = new Crime(this, this.performer, object.owner, Crime.TYPE.CRIME_THEFT, object);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		}
	}

	@Override
	public boolean check() {
		float maxWeightForPerformer = 50f + performer.getEffectiveHighLevelStat(HIGH_LEVEL_STATS.STRENGTH) * 10f;
		if (object.weight > maxWeightForPerformer) {
			actionName = ACTION_NAME + " (too heavy)";
			disabledReason = "Too heavy";
			return false;
		}
		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(object.squareGameObjectIsOn) > 1) {
			actionName = ACTION_NAME_DISABLED;
			return false;
		}
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (object.owner != null && object.owner != performer)
			return false;
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}