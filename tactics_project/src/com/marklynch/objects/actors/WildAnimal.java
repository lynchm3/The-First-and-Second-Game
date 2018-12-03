package com.marklynch.objects.actors;

import java.util.ArrayList;

import com.marklynch.objects.inanimateobjects.GameObject;

public class WildAnimal extends Animal {
	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

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
