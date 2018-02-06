package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Junk extends GameObject {

	public Junk() {








	}

	@Override
	public Junk makeCopy(Square square, Actor owner) {
		Junk junk = new Junk();
		super.setAttributesForCopy(junk, square, owner);
		return junk;
	}

}
