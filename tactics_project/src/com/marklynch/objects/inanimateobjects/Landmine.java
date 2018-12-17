package com.marklynch.objects.inanimateobjects;

import com.marklynch.actions.Action;
import com.marklynch.actions.ActionMove;
import com.marklynch.actions.ActionUsePower;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.utils.UpdatesWhenSquareContentsChange;
import com.marklynch.utils.ArrayList;

public class Landmine extends Discoverable implements UpdatesWhenSquareContentsChange, OnCompletionListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
	public int targetWeight = 10;
	public Power power;

	public Landmine() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = false;
		persistsWhenCantBeSeen = true;
		attackable = true;
		isFloorObject = true;
		orderingOnGound = 40;
		type = "Trap";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public void squareContentsChanged() {

		if (squareGameObjectIsOn == null)
			return;

		doTheThing(null);
	}

	public void doTheThing(GameObject g) {
		int weightOnPlate = 0;

		if (squareGameObjectIsOn == null)
			return;

		for (GameObject gameObject : squareGameObjectIsOn.inventory.gameObjects) {
			if (gameObject.isFloorObject == false && gameObject != this) {
				weightOnPlate += gameObject.weight;
			}
		}

		if (weightOnPlate >= targetWeight) {
			explode();
		}

	}

	@Override
	public boolean checkIfDestroyed(Object attacker, Action action) {
		boolean destroyed = super.checkIfDestroyed(attacker, action);

		if (destroyed) {
			new ActionUsePower(this, null, this.squareGameObjectIsOn, power.makeCopy(this)).perform();
		}

		return destroyed;

	}

	public void explode() {
		discovered();
		this.changeHealthSafetyOff(-this.remainingHealth, this, null);
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return new ActionMove(performer, squareGameObjectIsOn, true);
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionMove(performer, squareGameObjectIsOn, true);
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>(Action.class);
		if (!this.discovered)
			return actions;
		actions.add(new ActionMove(performer, squareGameObjectIsOn, true));
		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
		return actions;
	}

	public Landmine makeCopy(Square square, Actor owner, int level, int targetWeight) {
		Landmine landmine = new Landmine();
		setInstances(landmine);
		super.setAttributesForCopy(landmine, square, owner);
		landmine.level = level;
		landmine.targetWeight = targetWeight;
		landmine.preDiscoverTexture = preDiscoverTexture;
		landmine.postDiscoverTexture = postDiscoverTexture;
		landmine.power = power;
		return landmine;
	}

	@Override
	public void animationComplete(GameObject gameObject) {
		doTheThing(gameObject);
	}

}
