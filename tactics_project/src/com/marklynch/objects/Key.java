package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Key extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	Key key;

	public Key() {
		super();
		type = "Key";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public Key makeCopy(String name, Square square, Actor owner) {
		Key key = new Key();
		setInstances(key);
		super.setAttributesForCopy(key, square, owner);
		key.name = name;
		return key;
	}

}
