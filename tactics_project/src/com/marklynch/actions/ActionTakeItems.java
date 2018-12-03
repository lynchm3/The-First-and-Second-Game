package com.marklynch.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.secondary.AnimationTake;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Openable;
import com.marklynch.ui.ActivityLog;

public class ActionTakeItems extends VariableQtyAction {

	public static final String ACTION_NAME = "Take";
	public static final String ACTION_NAME_ILLEGAL = "Steal";

	GameObject[] objectsTotake;
	Object objectToTakeFrom;

	public ActionTakeItems(GameObject performer, Object objectToTakeFrom, ArrayList<GameObject> objectsTotake) {
		this(performer, objectToTakeFrom, objectsTotake.toArray(new GameObject[objectsTotake.size()]), false);
	}

	public ActionTakeItems(GameObject performer, Object objectToTakeFrom, GameObject... objectsTotake) {
		this(performer, objectToTakeFrom, objectsTotake, false);
	}

	public ActionTakeItems(GameObject performer, Object objectToTakeFrom, GameObject[] objectsTotake,
			boolean doesnothing) {

		super(ACTION_NAME, textureLeft, performer, objectToTakeFrom);

		this.objectsTotake = objectsTotake;
		this.objectToTakeFrom = objectToTakeFrom;
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		if (legal == false) {
			if (enabled) {
				actionName = ACTION_NAME_ILLEGAL;
			} else {
			}
		}

		sound = createSound();
	}

	@Override
	public void perform() {
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		int amountToTake = Math.min(objectsTotake.length, qty);

		if (amountToTake == 0)
			return;

		if (Game.level.openInventories.size() > 0) {
		} else if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer) {
			Level.addSecondaryAnimation(new AnimationTake(objectsTotake[0], performer, 0, 0, 1f, null));
		}

		for (int i = 0; i < amountToTake; i++) {
			GameObject object = objectsTotake[i];

			if (objectToTakeFrom == targetSquare)
				targetSquare.inventory.remove(object);

			if (objectToTakeFrom == target)
				target.inventory.remove(object);

			if (objectToTakeFrom instanceof Openable) {
				((Openable) objectToTakeFrom).open();
			}

			performer.inventory.add(object);
			if (object.owner == null && performer instanceof Actor)
				object.owner = (performer);
			if (sound != null)
				sound.play();

			if (!legal && performer instanceof Actor) {
				Crime crime = new Crime((performer), object.owner, Crime.TYPE.CRIME_THEFT, object);
				performer.crimesPerformedThisTurn.add(crime);
				performer.crimesPerformedInLifetime.add(crime);
				notifyWitnessesOfCrime(crime);
			}
		}

		if (Game.level.shouldLog(performer)) {
			if (amountToTake > 0) {
				String amountText = "";
				if (amountToTake > 1) {
					amountText = "x" + amountToTake;
				}
				if (legal) {
					if (objectToTakeFrom == targetSquare)
						Game.level.logOnScreen(
								new ActivityLog(new Object[] { performer, " took ", objectsTotake[0], amountText }));
					else
						Game.level.logOnScreen(new ActivityLog(
								new Object[] { performer, " took ", objectsTotake[0], amountText, " from ", target }));
				} else {
					if (objectToTakeFrom == targetSquare)
						Game.level.logOnScreen(
								new ActivityLog(new Object[] { performer, " stole ", objectsTotake[0], amountText }));
					else
						Game.level.logOnScreen(new ActivityLog(
								new Object[] { performer, " stole ", objectsTotake[0], amountText, " from ", target }));
				}
			}
		}

	}

	@Override
	public boolean check() {

		if (objectsTotake.length == 0 || objectToTakeFrom == null) {
			return false;
		}

		// if (performer.inventory.contains(target)) {
		// return false;
		// }

		// Check it's still on the same spot
		if (target == objectToTakeFrom) {
			for (GameObject object : objectsTotake) {
				if (!target.inventory.contains(object)) {
					return false;
				}
			}
		}

		// Check it's still on the same spot
		if (targetSquare == objectToTakeFrom) {
			for (GameObject object : objectsTotake) {
				if (!targetSquare.inventory.contains(object)) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (targetSquare == objectToTakeFrom && performer.straightLineDistanceTo(targetSquare) < 2) {
			return true;
		}

		if (target == objectToTakeFrom && performer.straightLineDistanceTo(target.squareGameObjectIsOn) < 2) {
			return true;
		}

		return false;
	}

	@Override
	public boolean checkLegality() {
		if (objectsTotake.length == 0)
			return true;
		if (objectsTotake[0].owner != null && objectsTotake[0].owner != performer) {
			illegalReason = THEFT;
			if (objectsTotake[0].value > 100)
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