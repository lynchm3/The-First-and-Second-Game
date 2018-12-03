package com.marklynch.objects.inanimateobjects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;

public class Gold extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Gold() {
		super();

		// BOOK / SCROLL

		attackable = false;
		type = "Gold";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public String toString() {
		return "" + value;
	}

	public Gold makeCopy(Square square, Actor owner, int value) {
		Gold gold = new Gold();
		setInstances(gold);

		super.setAttributesForCopy(gold, square, owner);
		gold.value = value;

		return gold;

	}
}
