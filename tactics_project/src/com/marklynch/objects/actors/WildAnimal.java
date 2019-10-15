package com.marklynch.objects.actors;

import com.marklynch.utils.CopyOnWriteArrayList;

import com.marklynch.objects.inanimateobjects.GameObject;

public class WildAnimal extends Animal {
	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	public WildAnimal() {
		super();
		type = "Wild Animal";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

}
