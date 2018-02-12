package com.marklynch.objects.tools;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class FishingRod extends Tool {

	public FishingRod() {
		super();
	}

	@Override
	public FishingRod makeCopy(Square square, Actor owner) {
		FishingRod weapon = new FishingRod();
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
