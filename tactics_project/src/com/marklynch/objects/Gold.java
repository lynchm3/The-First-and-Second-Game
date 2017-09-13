package com.marklynch.objects;

import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Gold extends GameObject {

	public Gold(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit,
			float soundWhenHitting, float soundDampening, Color light, float lightHandleX, float lightHandlY,
			boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, int value, Actor owner, int templateId) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, value, owner, templateId);

		// BOOK / SCROLL
		canBePickedUp = true;
		showInventory = false;
		fitsInInventory = true;
		canShareSquare = true;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
		attackable = false;

	}

	@Override
	public String toString() {
		return "" + value;
	}

	public Gold makeCopy(Square square, Actor owner, int value) {
		return new Gold(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(), widthRatio,
				heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, value, owner, templateId);
	}
}
