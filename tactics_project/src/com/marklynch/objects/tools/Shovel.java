package com.marklynch.objects.tools;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class Shovel extends Tool {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Shovel() {
		super();
	}

	@Override
	public Shovel makeCopy(Square square, Actor owner) {
		Shovel weapon = new Shovel();
		instances.add(weapon);
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
