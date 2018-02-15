package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Arrow extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Arrow() {
		super();

	}

	@Override
	public Arrow makeCopy(Square square, Actor owner) {
		Arrow arrow = new Arrow();
		instances.add(arrow);
		super.setAttributesForCopy(arrow, square, owner);
		return arrow;
	}
}
