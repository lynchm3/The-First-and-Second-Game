package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Stampable extends GameObject {

	public Stampable() {
		super();









	}

	@Override
	public Stampable makeCopy(Square square, Actor owner) {
		Stampable stampable = new Stampable();
		super.setAttributesForCopy(stampable, square, owner);
		return stampable;
	}

}
