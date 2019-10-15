package com.marklynch.objects.inanimateobjects;

import com.marklynch.utils.CopyOnWriteArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;

public class MeatChunk extends GameObject {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

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