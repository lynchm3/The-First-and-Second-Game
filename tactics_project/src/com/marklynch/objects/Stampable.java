package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Stampable extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Stampable() {
		super();

	}

	@Override
	public Stampable makeCopy(Square square, Actor owner) {
		Stampable stampable = new Stampable();
		instances.add(stampable);
		super.setAttributesForCopy(stampable, square, owner);
		return stampable;
	}

}
