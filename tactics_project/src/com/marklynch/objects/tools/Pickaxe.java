package com.marklynch.objects.tools;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.CopyOnWriteArrayList;

public class Pickaxe extends Tool {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	public Pickaxe() {
		super();
		type = "Pickaxe";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Pickaxe makeCopy(Square square, Actor owner) {
		Pickaxe weapon = new Pickaxe();
		setInstances(weapon);
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
