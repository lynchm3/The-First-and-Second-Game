package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.Carcass;
import com.marklynch.objects.Corpse;
import com.marklynch.objects.Food;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionEat extends Action {

	public static final String ACTION_NAME = "Eat";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject object;

	public ActionEat(Actor performer, GameObject object) {
		super(ACTION_NAME, "action_eat.png");
		this.performer = performer;
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

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " ate ", object }));
		object.inventoryThatHoldsThisObject.remove(object);

		if (object instanceof Food || object instanceof Corpse || object instanceof Carcass) {

		} else {
			performer.inventory.add(object);
		}

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
		if (performer.straightLineDistanceTo(object.squareGameObjectIsOn) < 2) {
			return true;
		}
		if (performer.inventory == object.inventoryThatHoldsThisObject)
			return true;
		return false;
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