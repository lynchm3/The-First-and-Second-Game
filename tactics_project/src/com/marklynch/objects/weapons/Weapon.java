package com.marklynch.objects.weapons;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class Weapon extends GameObject {
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"totalHealth", "remainingHealth", "owner", "inventory", "showInventory", "fitsInInventory",
			"canContainOtherObjects" };

	public Weapon() {

		super();

		canBePickedUp = true;
		showInventory = false;
		fitsInInventory = true;
		canShareSquare = true;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
		attackable = true;

	}

	@Override
	public Weapon makeCopy(Square square, Actor owner) {
		Weapon weapon = new Weapon();
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}
}
