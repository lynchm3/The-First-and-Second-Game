package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class MeatChunk extends GameObject {

	public MeatChunk() {
		super();




	}

	@Override
	public MeatChunk makeCopy(Square square, Actor owner) {
		MeatChunk meatChunk = new MeatChunk();
		super.setAttributesForCopy(meatChunk, square, owner);
		return meatChunk;
	}
}