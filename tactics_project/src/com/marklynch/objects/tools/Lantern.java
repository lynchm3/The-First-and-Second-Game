package com.marklynch.objects.tools;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class Lantern extends Tool {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

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
		instances.add(weapon);
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
