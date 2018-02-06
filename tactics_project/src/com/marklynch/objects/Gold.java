package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Gold extends GameObject {

	public Gold() {
		super();

		// BOOK / SCROLL







		attackable = false;

	}

	@Override
	public String toString() {
		return "" + value;
	}

	public Gold makeCopy(Square square, Actor owner, int value) {
		Gold gold = new Gold();

		super.setAttributesForCopy(gold, square, owner);
		gold.value = value;

		return gold;

	}
}
