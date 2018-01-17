package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.AnimationPickup;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Openable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionTakeItems extends VariableQtyAction {

	public static final String ACTION_NAME = "Take";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_ILLEGAL = "Steal";
	public static final String ACTION_NAME_DISABLED_ILLEGAL = ACTION_NAME_ILLEGAL + " (can't reach)";

	Actor performer;
	Object target;
	Square targetSquare;
	GameObject targetGameObject;
	GameObject[] objects;

	public ActionTakeItems(Actor performer, Object target, ArrayList<GameObject> objects) {
		this(performer, target, objects.toArray(new GameObject[objects.size()]), false);
	}

	public ActionTakeItems(Actor performer, Object target, GameObject... objects) {
		this(performer, target, objects, false);
	}

	public ActionTakeItems(Actor performer, Object target, GameObject[] objects, boolean doesnothing) {

		// public ActionTakeItems(Actor performer, Object target, GameObject
		// object) {
		super(ACTION_NAME, "left.png");
		this.performer = performer;
		this.target = target;
		if (this.target instanceof Square)
			targetSquare = (Square) target;
		if (this.target instanceof GameObject)
			targetGameObject = (GameObject) target;
		this.objects = objects;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		if (legal == false) {
			if (enabled) {
				actionName = ACTION_NAME_ILLEGAL;
			} else {
				actionName = ACTION_NAME_DISABLED_ILLEGAL;
			}
		}

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

			performer.animation = new AnimationPickup(object, performer.squareGameObjectIsOn);

			if (targetSquare != null)
				targetSquare.inventory.remove(object);

			if (targetGameObject != null)
				targetGameObject.inventory.remove(object);

			if (target instanceof Openable) {
				((Openable) target).open();
			}

			performer.inventory.add(object);
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
		if (objects[0].owner != null && objects[0].owner != performer)
			return false;
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}