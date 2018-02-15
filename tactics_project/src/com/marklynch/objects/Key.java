package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Key extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	Key key;

	public Key() {
		super();

	}

	public Key makeCopy(String name, Square square, Actor owner) {
		Key key = new Key();
		instances.add(key);
		super.setAttributesForCopy(key, square, owner);
		key.name = name;
		return key;
	}

}
