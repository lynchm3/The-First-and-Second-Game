package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.secondary.AnimationTake;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Openable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionTakeItems extends VariableQtyAction {

	public static final String ACTION_NAME = "Take";
	public static final String ACTION_NAME_ILLEGAL = "Steal";

	GameObject performer;
	Object target;
	Square targetSquare;
	GameObject targetGameObject;
	GameObject[] objects;

	public ActionTakeItems(GameObject performer, Object target, ArrayList<GameObject> objects) {
		this(performer, target, objects.toArray(new GameObject[objects.size()]), false);
	}

	public ActionTakeItems(GameObject performer, Object target, GameObject... objects) {
		this(performer, target, objects, false);
	}

	public ActionTakeItems(GameObject performer, Object target, GameObject[] objects, boolean doesnothing) {

		// public ActionTakeItems(Actor performer, Object target, GameObject
		// object) {
		super(ACTION_NAME, textureLeft);
		super.gameObjectPerformer = this.performer = performer;
		this.target = target;
		if (this.target instanceof Square)
			targetSquare = (Square) target;
		if (this.target instanceof GameObject)
			targetGameObject = (GameObject) target;
		this.objects = objects;
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

		int amountToTake = Math.min(objects.length, qty);

		if (amountToTake == 0)
			return;

		if (Game.level.openInventories.size() > 0) {
		} else if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer) {
			performer.addSecondaryAnimation(new AnimationTake(objects[0], performer, 0, 0, 1f, null));
		}

		for (int i = 0; i < amountToTake; i++) {
			GameObject object = objects[i];

			if (targetSquare != null)
				targetSquare.inventory.remove(object);

			if (targetGameObject != null)
				targetGameObject.inventory.remove(object);

			if (target instanceof Openable) {
				((Openable) target).open();
			}

			performer.inventory.add(object);
			if (object.owner == null && performer instanceof Actor)
				object.owner = ((Actor) performer);
			performer.actionsPerformedThisTurn.add(this);
			if (sound != null)
				sound.play();

			if (!legal && performer instanceof Actor) {
				Crime crime = new Crime(this, ((Actor) performer), object.owner, Crime.TYPE.CRIME_THEFT, object);
				((Actor) performer).crimesPerformedThisTurn.add(crime);
				((Actor) performer).crimesPerformedInLifetime.add(crime);
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
					if (targetGameObject == null)
						Game.level.logOnScreen(
								new ActivityLog(new Object[] { performer, " took ", objects[0], amountText }));
					else
						Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " took ", objects[0],
								amountText, " from ", targetGameObject }));
				} else {
					if (targetGameObject == null)
						Game.level.logOnScreen(
								new ActivityLog(new Object[] { performer, " stole ", objects[0], amountText }));
					else
						Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " stole ", objects[0],
								amountText, " from ", targetGameObject }));
				}
			}
		}

	}

	@Override
	public boolean check() {

		if (objects.length == 0) {
			return false;
		}

		// Check it's still on the same spot
		if (targetGameObject != null) {
			for (GameObject object : objects) {
				if (!targetGameObject.inventory.contains(object)) {
					return false;
				}
			}
		}

		// Check it's still on the same spot
		if (targetSquare != null) {
			for (GameObject object : objects) {
				if (!targetSquare.inventory.contains(object)) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (targetSquare != null && performer.straightLineDistanceTo(targetSquare) < 2) {
			return true;
		}

		if (targetGameObject != null && performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) < 2) {
			return true;
		}

		return false;
	}

	@Override
	public boolean checkLegality() {
		if (objects.length == 0)
			return true;
		if (objects[0].owner != null && objects[0].owner != performer) {
			illegalReason = THEFT;
			if (objects[0].value > 100)
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