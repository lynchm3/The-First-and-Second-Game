package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Food extends GameObject {

	public Food() {
		super();

		canBePickedUp = true;
		showInventory = false;
		fitsInInventory = true;
		canShareSquare = true;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
		attackable = true;
	}

	@Override
	public Food makeCopy(Square square, Actor owner) {
		Food food = new Food();
		super.setAttributesForCopy(food, square, owner);
		return food;
	}

}
