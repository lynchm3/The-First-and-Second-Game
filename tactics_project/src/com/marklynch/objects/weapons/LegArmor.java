package com.marklynch.objects.weapons;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class LegArmor extends GameObject {
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"totalHealth", "remainingHealth", "owner", "inventory", "showInventory", "fitsInInventory",
			"canContainOtherObjects" };

	public LegArmor(String name, String imagePath, float health, Square squareGameObjectIsOn, float widthRatio,
			float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting,
			float soundDampening, Color light, float lightHandleX, float lightHandlY, boolean stackable,
			float fireResistance, float waterResistance, float electricResistance, float poisonResistance,
			float slashResistance, float weight, Actor owner, float anchorX, float anchorY) {

		super(name, (int) health, imagePath, squareGameObjectIsOn, new Inventory(), widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, waterResistance, electricResistance, poisonResistance,
				slashResistance, weight, owner);

		this.owner = owner;
		this.anchorX = anchorX;
		this.anchorY = anchorY;

	}

	public Action getUtilityAction(Actor performer) {
		return null;
	}

	@Override
	public LegArmor makeCopy(Square square, Actor owner) {
		return new LegArmor(new String(name), imageTexturePath, totalHealth, square, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, waterResistance, electricResistance, poisonResistance,
				slashResistance, weight, owner, anchorX, anchorY);
	}
}
