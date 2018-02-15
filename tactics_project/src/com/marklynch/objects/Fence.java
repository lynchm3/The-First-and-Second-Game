package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Fence extends Wall {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Fence() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;
		canShareSquare = false;

		persistsWhenCantBeSeen = true;

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Fence makeCopy(Square square, Actor owner) {
		Fence fence = new Fence();
		setInstances(fence);
		super.setAttributesForCopy(fence, square, owner);
		return fence;
	}

}
