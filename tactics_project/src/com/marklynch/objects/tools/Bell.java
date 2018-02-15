package com.marklynch.objects.tools;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionRing;
import com.marklynch.objects.units.Actor;

public class Bell extends Tool {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

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
		instances.add(weapon);
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
