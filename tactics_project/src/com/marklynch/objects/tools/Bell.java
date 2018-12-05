package com.marklynch.objects.tools;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.ArrayList;

public class Bell extends Tool {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public Bell() {

		super();
		type = "Bell";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Bell makeCopy(Square square, Actor owner) {
		Bell weapon = new Bell();
		setInstances(weapon);
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
