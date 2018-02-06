package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Fence extends Wall {

	public Fence() {
		super();

		canBePickedUp = false;
		showInventoryInGroundDisplay = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = true;
		attackable = true;
	}

	@Override
	public Fence makeCopy(Square square, Actor owner) {
		Fence fence = new Fence();
		super.setAttributesForCopy(fence, square, owner);
		return fence;
	}

}
