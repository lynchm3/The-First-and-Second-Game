package com.marklynch.objects.weapons;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class Weapon extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"totalHealth", "remainingHealth", "owner", "inventory", "showInventory", "fitsInInventory",
			"canContainOtherObjects" };

	public Weapon() {

		super();

	}

	@Override
	public Weapon makeCopy(Square square, Actor owner) {
		Weapon weapon = new Weapon();
		instances.add(weapon);
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
