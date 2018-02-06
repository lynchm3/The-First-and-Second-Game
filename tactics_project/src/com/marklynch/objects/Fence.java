package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Fence extends Wall {

	public Fence() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;
		canShareSquare = false;


		persistsWhenCantBeSeen = true;

	}

	@Override
	public Fence makeCopy(Square square, Actor owner) {
		Fence fence = new Fence();
		super.setAttributesForCopy(fence, square, owner);
		return fence;
	}

}
