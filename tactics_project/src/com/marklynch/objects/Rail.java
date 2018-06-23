package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Rail extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	boolean up, down, left, right;

	public Rail() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public Rail makeCopy(Square square, Actor owner, boolean up, boolean down, boolean left, boolean right) {
		Rail rail = new Rail();
		setInstances(rail);
		super.setAttributesForCopy(rail, square, owner);
		rail.up = up;
		rail.down = down;
		rail.left = left;
		rail.right = right;
		return rail;
	}

}
