package com.marklynch.objects.tools;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Lantern extends Tool {

	public Lantern() {
		super();
	}

	// @Override
	// public Action getUtilityAction(Actor performer) {
	// return new ActionRing(performer, this);
	// }

	@Override
	public Lantern makeCopy(Square square, Actor owner) {
		Lantern weapon = new Lantern();
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
