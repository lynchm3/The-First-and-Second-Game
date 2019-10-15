package com.marklynch.objects.armor;

import com.marklynch.utils.CopyOnWriteArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.Texture;

public class LegArmor extends Armor {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);
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
