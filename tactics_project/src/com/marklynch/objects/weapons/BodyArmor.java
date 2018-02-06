package com.marklynch.objects.weapons;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;

public class BodyArmor extends Armor {
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"totalHealth", "remainingHealth", "owner", "inventory", "showInventory", "fitsInInventory",
			"canContainOtherObjects" };

	public BodyArmor() {

		super();

		canBePickedUp = true;
		showInventoryInGroundDisplay = false;
		fitsInInventory = true;
		canShareSquare = true;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
		attackable = true;

	}

	@Override
	public Action getUtilityAction(Actor performer) {
		return null;
	}

	@Override
	public BodyArmor makeCopy(Square square, Actor owner) {
		BodyArmor bodyArmor = new BodyArmor();
		setAttributesForCopy(bodyArmor, square, owner);
		return bodyArmor;
	}
}
