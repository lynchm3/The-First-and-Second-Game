package com.marklynch.objects.tools;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actors.Actor;

public class Shovel extends Tool {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Shovel() {
		super();
		type = "Shovel";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Shovel makeCopy(Square square, Actor owner) {
		Shovel weapon = new Shovel();
		setInstances(weapon);
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
