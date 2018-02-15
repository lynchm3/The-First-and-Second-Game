package com.marklynch.objects.units;

import java.util.ArrayList;

import com.marklynch.objects.GameObject;

public class NonHuman extends Actor {
	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public NonHuman() {
		super();
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

}
