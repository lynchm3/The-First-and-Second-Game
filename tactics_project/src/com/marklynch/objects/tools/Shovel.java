package com.marklynch.objects.tools;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Shovel extends Tool {

	public Shovel() {
		super();
	}

	@Override
	public Shovel makeCopy(Square square, Actor owner) {
		Shovel weapon = new Shovel();
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
