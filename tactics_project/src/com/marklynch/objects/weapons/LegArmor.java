package com.marklynch.objects.weapons;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.Texture;

public class LegArmor extends Armor {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"totalHealth", "remainingHealth", "owner", "inventory", "showInventory", "fitsInInventory",
			"canContainOtherObjects" };

	public Texture legUpperTexture;
	public Texture legLowerTexture;
	public Texture frontTexture;

	public LegArmor() {

		super();
		type = "Leg Armor";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public LegArmor makeCopy(Square square, Actor owner) {
		LegArmor legArmor = new LegArmor();
		setInstances(legArmor);
		setAttributesForCopy(legArmor, square, owner);
		legArmor.legUpperTexture = this.legUpperTexture;
		legArmor.legLowerTexture = this.legLowerTexture;
		legArmor.frontTexture = this.frontTexture;
		return legArmor;
	}
}
