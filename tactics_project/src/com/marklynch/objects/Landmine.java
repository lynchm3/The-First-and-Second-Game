package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.units.Actor;

public class Landmine extends Discoverable implements UpdatesWhenSquareContentsChange {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Landmine() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
		isFloorObject = true;
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
		System.out.println("updateLandmine");
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

}
