package com.marklynch.objects.inanimateobjects;

import com.marklynch.utils.CopyOnWriteArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;

public class Stampable extends GameObject {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	public Stampable() {
		super();

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Stampable makeCopy(Square square, Actor owner) {
		Stampable stampable = new Stampable();
		setInstances(stampable);
		super.setAttributesForCopy(stampable, square, owner);
		return stampable;
	}

}
