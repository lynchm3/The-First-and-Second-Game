package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Carcass;
import com.marklynch.objects.Corpse;
import com.marklynch.objects.Food;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionEatItems extends VariableQtyAction {

	public static final String ACTION_NAME = "Eat";

	Actor performer;
	GameObject[] targets;

	public ActionEatItems(Actor performer, ArrayList<GameObject> objects) {
		this(performer, objects.toArray(new GameObject[objects.size()]), false);
	}

	public ActionEatItems(Actor performer, GameObject... objects) {
		this(performer, objects, false);
	}

	public ActionEatItems(Actor performer, GameObject[] objects, boolean doesNothing) {
		super(ACTION_NAME, textureEat);
		super.gameObjectPerformer = this.performer = performer;
		this.targets = objects;
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

		int amountToEat = Math.min(targets.length, qty);

		if (amountToEat == 0)
			return;

		for (int i = 0; i < amountToEat; i++) {
			GameObject object = targets[i];

			if (object instanceof Food || object instanceof Corpse || object instanceof Carcass) {
				object.changeHealth(-object.remainingHealth, null, null);
				if (object.inventoryThatHoldsThisObject.parent instanceof Square) {
					Game.level.inanimateObjectsOnGroundToRemove.add(object);
				}
				object.inventoryThatHoldsThisObject.remove(object);
			} else {
				performer.inventory.add(object);
			}

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

		if (Game.level.shouldLog(performer)) {
			String amountText = "";
			if (amountToEat > 1) {
				amountText = "x" + amountToEat;
			}
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " ate ", targets[0], amountText }));
		}
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkRange() {
		if (performer.straightLineDistanceTo(targets[0].squareGameObjectIsOn) < 2) {
			return true;
		}
		if (performer.inventory == targets[0].inventoryThatHoldsThisObject)
			return true;
		return false;
	}

	@Override
	public boolean checkLegality() {
		return standardAttackLegalityCheck(performer, targets[0]);
	}

	@Override
	public Sound createSound() {
		return null;
	}

}