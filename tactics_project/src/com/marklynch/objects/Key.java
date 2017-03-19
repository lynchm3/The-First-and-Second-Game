package com.marklynch.objects;

import mdesl.graphics.Color;

import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;

public class Key extends GameObject {

	Key key;

	public Key(String name, int health, String imagePath,
			Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare,
			boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen,
			float widthRatio, float heightRatio, float soundHandleX,
			float soundHandleY, float soundWhenHit, float soundWhenHitting,
			Color light, float lightHandleX, float lightHandlY,
			boolean stackable, float fireResistance, float iceResistance,
			float electricResistance, float poisonResistance) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory,
				showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, widthRatio, heightRatio, soundHandleX,
				soundHandleY, soundWhenHit, soundWhenHitting, light,
				lightHandleX, lightHandlY, stackable, fireResistance,
				iceResistance, electricResistance, poisonResistance);
	}

	@Override
	public void draw1() {

		if (this.squareGameObjectIsOn.visibleToPlayer == false
				&& persistsWhenCantBeSeen == false)
			return;

		if (!this.squareGameObjectIsOn.seenByPlayer)
			return;

		if (!this.squareGameObjectIsOn.inventory.contains(Actor.class)) {
			super.draw1();
		} else {
		}

	}

	@Override
	public Key makeCopy(Square square) {
		return new Key(new String(name), (int) totalHealth, imageTexturePath,
				square, inventory.makeCopy(), showInventory, canShareSquare,
				fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, widthRatio, heightRatio, soundHandleX,
				soundHandleY, soundWhenHit, soundWhenHitting, light,
				lightHandleX, lightHandlY, stackable, fireResistance,
				iceResistance, electricResistance, poisonResistance);
	}

}
