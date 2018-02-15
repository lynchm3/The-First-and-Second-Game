package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Junk extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Junk() {

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Junk makeCopy(Square square, Actor owner) {
		Junk junk = new Junk();
		setInstances(junk);
		super.setAttributesForCopy(junk, square, owner);
		return junk;
	}

}
