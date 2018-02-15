package com.marklynch.objects.units;

import java.util.ArrayList;

import com.marklynch.objects.GameObject;

public class Animal extends NonHuman {
	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Animal() {
		super();
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

}
