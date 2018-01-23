package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Orb extends GameObject {

	public Orb() {
		super();
		canBePickedUp = true;
		showInventory = false;
		fitsInInventory = true;
		canShareSquare = true;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
		attackable = false;

	}

	@Override
	public String toString() {
		return "" + value + "XP";
	}

	public Orb makeCopy(Square square, Actor owner, int value) {
		Orb orb = new Orb();

		super.setAttributesForCopy(orb, square, owner);
		orb.value = value;

		return orb;

	}
}
