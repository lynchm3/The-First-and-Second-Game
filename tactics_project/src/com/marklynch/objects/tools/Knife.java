package com.marklynch.objects.tools;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Knife extends Tool {

	public Knife() {
		super();
	}

	@Override
	public Knife makeCopy(Square square, Actor owner) {
		Knife weapon = new Knife();
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
