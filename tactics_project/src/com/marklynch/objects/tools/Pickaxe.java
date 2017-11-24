package com.marklynch.objects.tools;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Pickaxe extends Tool {

	public Pickaxe() {
		super();
	}

	@Override
	public Pickaxe makeCopy(Square square, Actor owner) {
		Pickaxe weapon = new Pickaxe();
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
