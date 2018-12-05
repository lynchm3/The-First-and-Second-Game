package com.marklynch.objects.armor;

import com.marklynch.utils.ArrayList;

import com.marklynch.objects.inanimateobjects.GameObject;

public class Armor extends GameObject {
	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"totalHealth", "remainingHealth", "owner", "inventory", "showInventory", "fitsInInventory",
			"canContainOtherObjects" };

	public Armor() {

		super();
		type = "Armor";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}
}
