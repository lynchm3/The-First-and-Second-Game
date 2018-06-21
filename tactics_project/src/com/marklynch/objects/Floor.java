package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Floor extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Floor() {
		super();

		// DROP HOLE
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
	public Floor makeCopy(Square square, Actor owner) {
		Floor searchable = new Floor();
		setInstances(searchable);
		super.setAttributesForCopy(searchable, square, owner);
		return searchable;
	}

}
