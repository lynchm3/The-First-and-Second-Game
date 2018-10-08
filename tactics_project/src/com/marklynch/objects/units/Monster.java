package com.marklynch.objects.units;

import java.util.ArrayList;

import com.marklynch.objects.GameObject;

public class Monster extends Actor {
	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Monster() {
		super();
		thoughtsOnPlayer = -100;
		type = "Monster";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

}
