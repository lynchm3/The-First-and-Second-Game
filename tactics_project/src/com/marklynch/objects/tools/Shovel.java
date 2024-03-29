package com.marklynch.objects.tools;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.CopyOnWriteArrayList;

public class Shovel extends Tool {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

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
