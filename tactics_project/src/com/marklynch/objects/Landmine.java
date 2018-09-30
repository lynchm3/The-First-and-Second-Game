package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.actions.ActionUsePower;
import com.marklynch.objects.units.Actor;

public class Landmine extends Discoverable implements UpdatesWhenSquareContentsChange, OnCompletionListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	int targetWeight = 10;
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
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public void update(int delta) {
		super.update(delta);
	}

	@Override
	public void squareContentsChanged() {

		if (squareGameObjectIsOn == null)
			return;

		for (final GameObject gameObject : (ArrayList<GameObject>) squareGameObjectIsOn.inventory.gameObjects.clone()) {

			if (gameObject.primaryAnimation != null && gameObject.primaryAnimation.completed == false) {
				gameObject.primaryAnimation.onCompletionListener = this;
			} else {
				doTheThing(gameObject);
			}
		}
	}

	public void doTheThing(GameObject g) {
		int weightOnPlate = 0;

		System.out.println("Landmine.squareContentsChanged()");

		if (squareGameObjectIsOn == null)
			return;

		System.out.println("Landmine.squareContentsChanged() 2");
		for (GameObject gameObject : squareGameObjectIsOn.inventory.gameObjects) {
			if (gameObject.isFloorObject == false) {
				weightOnPlate += gameObject.weight;
			}
		}
		System.out.println("Landmine.squareContentsChanged() 3");

		if (weightOnPlate >= targetWeight) {
			System.out.println("Landmine.squareContentsChanged() calling explode");
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
		System.out.println("explode");
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
		ArrayList<Action> actions = new ArrayList<Action>();
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
		System.out.println("VoidHole.animationComplete");
		doTheThing(gameObject);
	}

}
