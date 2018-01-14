package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.Carcass;
import com.marklynch.objects.Corpse;
import com.marklynch.objects.Food;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionEatItems extends VariableQtyAction {

	public static final String ACTION_NAME = "Eat";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject[] objects;

	public ActionEatItems(Actor performer, ArrayList<GameObject> objects) {
		this(performer, objects.toArray(new GameObject[objects.size()]), false);
	}

	public ActionEatItems(Actor performer, GameObject... objects) {
		this(performer, objects, false);
	}

	public ActionEatItems(Actor performer, GameObject[] objects, boolean doesNothing) {
		super(ACTION_NAME, "action_eat.png");
		this.performer = performer;
		this.objects = objects;
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

		int amountToTake = Math.min(objects.length, qty);

		if (amountToTake == 0)
			return;

		for (int i = 0; i < amountToTake; i++) {
			GameObject object = objects[i];
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

		if (Game.level.shouldLog(performer)) {
			String amountText = "";
			if (amountToTake > 1) {
				amountText = "x" + amountToTake;
			}
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " ate ", objects[0], amountText }));
		}
	}

	@Override
	public boolean check() {
		if (performer.straightLineDistanceTo(objects[0].squareGameObjectIsOn) < 2) {
			return true;
		}
		if (performer.inventory == objects[0].inventoryThatHoldsThisObject)
			return true;
		return false;
	}

	@Override
	public boolean checkLegality() {
		if (objects[0].owner != null && objects[0].owner != performer)
			return false;
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}