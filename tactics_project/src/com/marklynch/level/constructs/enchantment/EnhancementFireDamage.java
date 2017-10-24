package com.marklynch.level.constructs.enchantment;

import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class EnhancementFireDamage extends Enhancement {

	public EnhancementFireDamage(String name, int health, String imagePath, Square squareGameObjectIsOn,
			Inventory inventory, float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY,
			float soundWhenHit, float soundWhenHitting, float soundDampening, Color light, float lightHandleX,
			float lightHandlY, boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, int value, Actor owner, int templateId) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, value, owner, templateId);
		// TODO Auto-generated constructor stub
	}

	// Template id
	public int templateId;

	// public EnhancementFireDamage() {
	// this.enchantmentName = "Fire Dmg";
	// this.imageTexture = getGlobalImage("effect_bleed.png");
	// fireDamage = 10;
	// type = TYPE.WEAPON;
	// }

	@Override
	public void activate() {

	}

	@Override
	public Enhancement makeCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public EnhancementFireDamage makeCopy() {
	// return new EnhancementFireDamage();
	// }
}
