package com.marklynch.objects;

import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Gate extends Door {
	float soundDampeningWhenClosed;
	boolean blocksLineOfSightWhenClosed;

	public Gate(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,

			float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit,
			float soundWhenHitting, float soundDampening, Color light, float lightHandleX, float lightHandlY,
			boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, int value, Actor owner, boolean locked,
			boolean shouldBeClosed, boolean shouldBeLocked, int templateId, Key... keys) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, value, owner, locked, shouldBeClosed, shouldBeLocked, templateId, keys);

		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = true;
		attackable = true;
		soundDampeningWhenClosed = soundDampening;
		blocksLineOfSightWhenClosed = blocksLineOfSight;

	}

	@Override
	public Gate makeCopy(String name, Square square, boolean locked, boolean shouldBeClosed, boolean shouldBeLocked,
			Actor owner, Key... keys) {
		return new Gate(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(),

				widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening,
				light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, value, owner, locked, shouldBeClosed, shouldBeLocked,
				templateId, keys);
	}

}
