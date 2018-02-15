package com.marklynch.objects.tools;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class Knife extends Tool {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Knife() {
		super();
	}

	@Override
	public Knife makeCopy(Square square, Actor owner) {
		Knife weapon = new Knife();
		instances.add(weapon);
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
