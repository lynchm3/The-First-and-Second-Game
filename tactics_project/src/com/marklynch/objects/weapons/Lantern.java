package com.marklynch.objects.weapons;

import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Lantern extends Weapon {

	public Lantern(String name, float damage, float minRange, float maxRange, String imagePath, float health,
			Square squareGameObjectIsOn, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting, Color light,
			float lightHandleX, float lightHandlY, boolean stackable, float fireResistance, float iceResistance,
			float electricResistance, float poisonResistance, Actor owner, float anchorX, float anchorY) {
		super(name, damage, minRange, maxRange, imagePath, health, squareGameObjectIsOn, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, light, lightHandleX, lightHandlY, stackable,
				fireResistance, iceResistance, electricResistance, poisonResistance, owner, anchorX, anchorY);
	}

	// @Override
	// public Action getUtilityAction(Actor performer) {
	// return new ActionRing(performer, this);
	// }

	@Override
	public Lantern makeCopy(Square square, Actor owner) {
		return new Lantern(new String(name), slashDamage, minRange, maxRange, imageTexturePath, totalHealth, square,
				fitsInInventory, canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio,
				heightRatio, soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, light, lightHandleX,
				lightHandlY, stackable, fireResistance, iceResistance, electricResistance, poisonResistance, owner,
				anchorX, anchorY);
	}
}
