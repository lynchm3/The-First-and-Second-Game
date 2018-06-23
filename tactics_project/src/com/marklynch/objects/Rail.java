package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Actor.Direction;

public class Rail extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	Direction direction1;
	Direction direction2;
	// boolean up, down, left, right;

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

	public Rail makeCopy(Square square, Actor owner, Direction direction1, Direction direction2) {
		Rail rail = new Rail();
		setInstances(rail);
		super.setAttributesForCopy(rail, square, owner);
		rail.direction1 = direction1;
		rail.direction2 = direction2;
		return rail;
	}

	public Direction getOppositeDirection(Direction direction) {
		if (direction1 == direction) {
			return direction2;
		} else if (direction2 == direction) {
			return direction1;
		}
		return null;
	}

}
