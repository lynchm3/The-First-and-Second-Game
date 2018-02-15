package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Inspectable extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Inspectable() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;

		persistsWhenCantBeSeen = true;
		attackable = false;
		decorative = true;

	}

	@Override
	public Inspectable makeCopy(Square square, Actor owner) {
		Inspectable inspectable = new Inspectable();
		instances.add(inspectable);
		super.setAttributesForCopy(inspectable, square, owner);
		return inspectable;
	}

}
