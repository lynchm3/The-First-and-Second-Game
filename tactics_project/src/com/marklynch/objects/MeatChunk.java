package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class MeatChunk extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public MeatChunk() {
		super();

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public MeatChunk makeCopy(Square square, Actor owner) {
		MeatChunk meatChunk = new MeatChunk();
		setInstances(meatChunk);
		super.setAttributesForCopy(meatChunk, square, owner);
		return meatChunk;
	}
}