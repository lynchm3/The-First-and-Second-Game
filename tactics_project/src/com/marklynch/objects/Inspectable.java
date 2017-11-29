package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Inspectable extends GameObject {

	public Inspectable() {
		super();

		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
		decorative = true;

	}

	@Override
	public Inspectable makeCopy(Square square, Actor owner) {
		Inspectable inspectable = new Inspectable();
		super.setAttributesForCopy(inspectable, square, owner);
		return inspectable;
	}

}
