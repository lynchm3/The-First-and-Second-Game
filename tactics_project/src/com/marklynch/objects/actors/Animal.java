package com.marklynch.objects.actors;

import java.util.ArrayList;

import com.marklynch.objects.GameObject;

public class Animal extends Actor {
	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

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
