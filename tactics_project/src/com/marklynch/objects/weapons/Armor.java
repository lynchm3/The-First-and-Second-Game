package com.marklynch.objects.weapons;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;

public class Armor extends GameObject {
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"totalHealth", "remainingHealth", "owner", "inventory", "showInventory", "fitsInInventory",
			"canContainOtherObjects" };

	public Armor() {

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

	public Action getUtilityAction(Actor performer) {
		return null;
	}

	public void setAttributesForCopy(Armor armor, Square square, Actor owner) {
		armor.owner = owner;
		armor.squareGameObjectIsOn = square;
		armor.anchorX = anchorX;
		armor.anchorY = anchorY;
		armor.name = name;
		armor.squareGameObjectIsOn = square;

		armor.totalHealth = armor.remainingHealth = totalHealth;
		armor.imageTexturePath = imageTexturePath;
		armor.widthRatio = widthRatio;
		armor.heightRatio = heightRatio;
		armor.drawOffsetX = drawOffsetX;
		armor.drawOffsetY = drawOffsetY;
		armor.soundWhenHit = soundWhenHit;
		armor.soundWhenHitting = soundWhenHitting;
		armor.weight = weight;

		armor.templateId = templateId;

		armor.init();
	}
}
