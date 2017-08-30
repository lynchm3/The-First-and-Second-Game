package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionBuySpecificItem extends Action {

	public static final String ACTION_NAME = "Buy";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	Actor target;
	GameObject object;

	public ActionBuySpecificItem(Actor performer, Actor target, GameObject object) {
		super(ACTION_NAME, "left.png");
		this.performer = performer;
		this.target = target;
		this.object = object;
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

		if (Game.level.shouldLog(target, performer))
			Game.level.logOnScreen(new ActivityLog(
					new Object[] { performer, " bought ", object, " from ", target, " for ", object.value, " gold" }));

		performer.gold -= object.value;
		target.gold += object.value;
		object.owner = performer;

		if (target != null)
			target.inventory.remove(object);

		performer.inventory.add(object);
		if (object.owner == null)
			object.owner = performer;
		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {
			Crime crime = new Crime(this, this.performer, object.owner, Crime.CRIME_SEVERITY_THEFT, object);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		}
	}

	@Override
	public boolean check() {
		if (target != null && performer.straightLineDistanceTo(target.squareGameObjectIsOn) < 2) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}