package com.marklynch.objects.tools;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class Axe extends Tool {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Axe() {
		super();
	}

	@Override
	public Axe makeCopy(Square square, Actor owner) {
		Axe weapon = new Axe();
		instances.add(weapon);
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
