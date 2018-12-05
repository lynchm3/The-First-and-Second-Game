package com.marklynch.objects.tools;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.ArrayList;

public class Axe extends Tool {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public Axe() {
		super();
		type = "Axe";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Axe makeCopy(Square square, Actor owner) {
		Axe weapon = new Axe();
		setInstances(weapon);
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
