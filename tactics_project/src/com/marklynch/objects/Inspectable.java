package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;

public class Inspectable extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Inspectable() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;

		persistsWhenCantBeSeen = true;
		attackable = false;
		decorative = true;
		type = "Inspectable";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Inspectable makeCopy(Square square, Actor owner) {
		Inspectable inspectable = new Inspectable();
		setInstances(inspectable);
		super.setAttributesForCopy(inspectable, square, owner);
		return inspectable;
	}

}
