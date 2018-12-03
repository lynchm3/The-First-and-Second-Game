package com.marklynch.objects.weapons;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;

public class Helmet extends Armor {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"totalHealth", "remainingHealth", "owner", "inventory", "showInventory", "fitsInInventory",
			"canContainOtherObjects" };

	public Helmet() {

		super();
		type = "Headgear";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Helmet makeCopy(Square square, Actor owner) {
		Helmet helmet = new Helmet();
		setInstances(helmet);
		setAttributesForCopy(helmet, square, owner);
		return helmet;
	}
}
