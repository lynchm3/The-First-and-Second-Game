package com.marklynch.objects.tools;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Pickaxe extends Tool {

	public Pickaxe(String name, float minRange, float maxRange, String imagePath, float health,
			Square squareGameObjectIsOn, float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY,
			float soundWhenHit, float soundWhenHitting, float soundDampening, Color light, float lightHandleX,
			float lightHandlY, boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, int value, Actor owner, float anchorX,
			float anchorY, int templateId) {
		super(name, minRange, maxRange, imagePath, health, squareGameObjectIsOn, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, value, owner, anchorX, anchorY, 5, templateId);
	}

	@Override
	public Pickaxe makeCopy(Square square, Actor owner) {
		return new Pickaxe(new String(name), minRange, maxRange, imageTexturePath, totalHealth, square, widthRatio,
				heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, value, owner, anchorX, anchorY, templateId);
	}
}
