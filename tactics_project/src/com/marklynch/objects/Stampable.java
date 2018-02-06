package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Stampable extends GameObject {

	public Stampable() {
		super();

		canBePickedUp = true;
		showInventoryInGroundDisplay = false;
		fitsInInventory = true;
		canShareSquare = true;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
		attackable = true;

	}

	@Override
	public Stampable makeCopy(Square square, Actor owner) {
		Stampable stampable = new Stampable();
		super.setAttributesForCopy(stampable, square, owner);
		return stampable;
	}

}
