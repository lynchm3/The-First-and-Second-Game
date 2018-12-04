package com.marklynch.objects.armor;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.Texture;

public class BodyArmor extends Armor {

	public Texture armUpperTexture;
	public Texture armLowerTexture;
	public Texture backTexture;

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"totalHealth", "remainingHealth", "owner", "inventory", "showInventory", "fitsInInventory",
			"canContainOtherObjects" };

	public BodyArmor() {

		super();
		type = "Body Armor";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public BodyArmor makeCopy(Square square, Actor owner) {
		BodyArmor bodyArmor = new BodyArmor();
		setInstances(bodyArmor);
		setAttributesForCopy(bodyArmor, square, owner);
		bodyArmor.armUpperTexture = this.armUpperTexture;
		bodyArmor.armLowerTexture = this.armLowerTexture;
		bodyArmor.backTexture = this.backTexture;
		return bodyArmor;
	}
}
