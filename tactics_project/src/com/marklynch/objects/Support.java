package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;

public class Support extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Square[] supporteesSquares;

	public Support() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		canShareSquare = false;
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public Support makeCopy(Square square, Actor owner, Square... supporteeSquares) {
		Support support = new Support();
		setInstances(support);
		super.setAttributesForCopy(support, square, owner);
		support.supporteesSquares = supporteeSquares;
		return support;
	}

	public Support makeCopy(Square square, Actor owner, ArrayList<Square> supporteeSquares) {
		return makeCopy(square, owner, supporteeSquares.toArray(new Square[supporteeSquares.size()]));
	}

	@Override
	public boolean checkIfDestroyed(Object attacker, Action action) {
		boolean destroyed = super.checkIfDestroyed(attacker, action);
		if (destroyed && supporteesSquares != null) {
			for (Square supporteeSquare : supporteesSquares) {

				Wall wall = (Wall) supporteeSquare.inventory.getGameObjectOfClass(Wall.class);
				if (wall != null) {
					wall.changeHealthSafetyOff(-wall.remainingHealth, attacker, action);
				}
			}
		}
		return destroyed;
	}
}
