package com.marklynch.objects.weapons;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Helmet extends Armor {
	public final static String[] editableAttributes = { "name", "imageTexture", "damage", "minRange", "maxRange",
			"totalHealth", "remainingHealth", "owner", "inventory", "showInventory", "fitsInInventory",
			"canContainOtherObjects" };

	public Helmet(String name, String imagePath, float health, Square squareGameObjectIsOn, float widthRatio,
			float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting,
			float soundDampening, Color light, float lightHandleX, float lightHandlY, boolean stackable,
			float fireResistance, float waterResistance, float electricResistance, float poisonResistance,
			float slashResistance, float weight, int value, Actor owner, float anchorX, float anchorY, int templateId) {

		super(name, imagePath, health, squareGameObjectIsOn, widthRatio, heightRatio, drawOffsetX, drawOffsetY,
				soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable,
				fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance, weight, value,
				owner, anchorX, anchorY, templateId);

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
	public Helmet makeCopy(Square square, Actor owner) {
		return new Helmet(new String(name), imageTexturePath, totalHealth, square, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, value, owner, anchorX, anchorY, templateId);
	}
}
