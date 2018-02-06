package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Key extends GameObject {

	Key key;

	public Key() {
		super();








	}

	public Key makeCopy(String name, Square square, Actor owner) {
		Key key = new Key();
		super.setAttributesForCopy(key, square, owner);
		key.name = name;
		return key;
	}

}
