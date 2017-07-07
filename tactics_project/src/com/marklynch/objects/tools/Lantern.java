package com.marklynch.objects.tools;

import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Lantern extends Tool {

	public Lantern(String name, float minRange, float maxRange, String imagePath, float health,
			Square squareGameObjectIsOn, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting, float soundDampening, Color light,
			float lightHandleX, float lightHandlY, boolean stackable, float fireResistance, float iceResistance,
			float electricResistance, float poisonResistance, float weight, Actor owner, float anchorX, float anchorY) {
		super(name, minRange, maxRange, imagePath, health, squareGameObjectIsOn, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable,
				fireResistance, iceResistance, electricResistance, poisonResistance, weight, owner, anchorX, anchorY);
	}

	// @Override
	// public Action getUtilityAction(Actor performer) {
	// return new ActionRing(performer, this);
	// }

	@Override
	public Lantern makeCopy(Square square, Actor owner) {
		return new Lantern(new String(name), minRange, maxRange, imageTexturePath, totalHealth, square, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable,
				fireResistance, iceResistance, electricResistance, poisonResistance, weight, owner, anchorX, anchorY);
	}
}
