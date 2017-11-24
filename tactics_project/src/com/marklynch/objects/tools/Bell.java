package com.marklynch.objects.tools;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionRing;
import com.marklynch.objects.units.Actor;

public class Bell extends Tool {

	public Bell() {

		super();
	}

	@Override
	public Action getUtilityAction(Actor performer) {
		return new ActionRing(performer, this);
	}

	@Override
	public Bell makeCopy(Square square, Actor owner) {
		Bell weapon = new Bell();
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
