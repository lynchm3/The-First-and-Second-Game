package com.marklynch.objects.inanimateobjects;

import com.marklynch.utils.CopyOnWriteArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;

public class Inspectable extends GameObject {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

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
