package com.marklynch.objects.tools;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Lantern extends Tool {

	public Lantern(String name, float minRange, float maxRange, String imagePath, float health,
			Square squareGameObjectIsOn,  
			  float widthRatio, float heightRatio,
			float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting, float soundDampening, Color light,
			float lightHandleX, float lightHandlY, boolean stackable, float fireResistance, float waterResistance,
			float electricResistance, float poisonResistance, float slashResistance, float weight, Actor owner, float anchorX, float anchorY) {
		super(name, minRange, maxRange, imagePath, health, squareGameObjectIsOn, 
				   widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable,
				fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance, weight, owner, anchorX, anchorY, 5);
	}

	// @Override
	// public Action getUtilityAction(Actor performer) {
	// return new ActionRing(performer, this);
	// }

	@Override
	public Lantern makeCopy(Square square, Actor owner) {
		return new Lantern(new String(name), minRange, maxRange, imageTexturePath, totalHealth, square, 
				   widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable,
				fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance, weight, owner, anchorX, anchorY);
	}
}
