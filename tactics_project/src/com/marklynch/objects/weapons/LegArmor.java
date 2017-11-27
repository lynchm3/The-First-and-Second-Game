package com.marklynch.objects.weapons;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;

public class LegArmor extends Armor {
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"totalHealth", "remainingHealth", "owner", "inventory", "showInventory", "fitsInInventory",
			"canContainOtherObjects" };

	public LegArmor() {

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
	public Action getUtilityAction(Actor performer) {
		return null;
	}

	@Override
	public LegArmor makeCopy(Square square, Actor owner) {
		LegArmor legArmor = new LegArmor();
		setAttributesForCopy(legArmor, square, owner);
		return legArmor;
	}
}
