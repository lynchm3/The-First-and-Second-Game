package com.marklynch.objects.actors;

import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.ArrayList;

public class Animal extends Actor {
	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public Animal() {
		super();
		type = "Animal";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

}
