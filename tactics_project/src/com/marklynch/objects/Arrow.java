package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Arrow extends GameObject {

	public Arrow() {
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
	public Arrow makeCopy(Square square, Actor owner) {
		Arrow arrow = new Arrow();
		super.setAttributesForCopy(arrow, square, owner);
		return arrow;
	}
}
