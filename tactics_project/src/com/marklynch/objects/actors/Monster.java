package com.marklynch.objects.actors;

import com.marklynch.utils.ArrayList;

import com.marklynch.objects.inanimateobjects.GameObject;

public class Monster extends Actor {
	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

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
