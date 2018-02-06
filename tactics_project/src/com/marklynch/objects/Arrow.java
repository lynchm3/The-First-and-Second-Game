package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Arrow extends GameObject {

	public Arrow() {
		super();








	}

	@Override
	public Arrow makeCopy(Square square, Actor owner) {
		Arrow arrow = new Arrow();
		super.setAttributesForCopy(arrow, square, owner);
		return arrow;
	}
}
