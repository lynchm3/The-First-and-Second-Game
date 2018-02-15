package com.marklynch.objects.weapons;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;

public class LegArmor extends Armor {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"totalHealth", "remainingHealth", "owner", "inventory", "showInventory", "fitsInInventory",
			"canContainOtherObjects" };

	public LegArmor() {

		super();

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Action getUtilityAction(Actor performer) {
		return null;
	}

	@Override
	public LegArmor makeCopy(Square square, Actor owner) {
		LegArmor legArmor = new LegArmor();
		setInstances(legArmor);
		setAttributesForCopy(legArmor, square, owner);
		return legArmor;
	}
}
