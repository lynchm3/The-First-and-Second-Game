package com.marklynch.objects.weapons;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;

public class BodyArmor extends Armor {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"totalHealth", "remainingHealth", "owner", "inventory", "showInventory", "fitsInInventory",
			"canContainOtherObjects" };

	public BodyArmor() {

		super();

	}

	@Override
	public Action getUtilityAction(Actor performer) {
		return null;
	}

	@Override
	public BodyArmor makeCopy(Square square, Actor owner) {
		BodyArmor bodyArmor = new BodyArmor();
		instances.add(bodyArmor);
		setAttributesForCopy(bodyArmor, square, owner);
		return bodyArmor;
	}
}
