package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Junk extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Junk() {

	}

	@Override
	public Junk makeCopy(Square square, Actor owner) {
		Junk junk = new Junk();
		instances.add(junk);
		super.setAttributesForCopy(junk, square, owner);
		return junk;
	}

}
