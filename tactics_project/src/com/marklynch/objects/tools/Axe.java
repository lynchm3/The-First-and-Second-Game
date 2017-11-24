package com.marklynch.objects.tools;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Axe extends Tool {

	public Axe() {
		super();
	}

	@Override
	public Axe makeCopy(Square square, Actor owner) {
		Axe weapon = new Axe();
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
