package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.units.Actor;

public class Landmine extends Discoverable implements UpdatesWhenSquareContentsChange {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	int targetWeight = 10;

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
		int weightOnPlate = 0;

		if (squareGameObjectIsOn == null)
			return;

		for (GameObject gameObject : squareGameObjectIsOn.inventory.gameObjects) {
			if (gameObject.isFloorObject == false) {
				weightOnPlate += gameObject.weight;
			}
		}

		if (weightOnPlate >= targetWeight) {
			explode();
		}
	}

	public void explode() {
		// new ActionUsePower(attacker, targetGameObject, targetSquare, power);
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

	public Discoverable makeCopy(Square square, Actor owner, int level, int targetWeight) {
		Landmine landmine = new Landmine();
		setInstances(landmine);
		super.setAttributesForCopy(landmine, square, owner);
		landmine.level = level;
		landmine.targetWeight = targetWeight;
		landmine.preDiscoverTexture = preDiscoverTexture;
		landmine.postDiscoverTexture = postDiscoverTexture;
		return landmine;
	}

}
