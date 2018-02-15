package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Gold extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

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
		instances.add(gold);

		super.setAttributesForCopy(gold, square, owner);
		gold.value = value;

		return gold;

	}
}
