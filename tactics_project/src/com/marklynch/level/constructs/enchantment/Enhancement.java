package com.marklynch.level.constructs.enchantment;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public abstract class Enhancement extends GameObject {

	public Enhancement(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit,
			float soundWhenHitting, float soundDampening, Color light, float lightHandleX, float lightHandlY,
			boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, int value, Actor owner, int templateId) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, stackable, fireResistance, waterResistance,
				electricResistance, poisonResistance, slashResistance, weight, value, owner,
				templateId);
		// TODO Auto-generated constructor stub
	}

	public String enchantmentName;
	public Texture imageTexture;
	public float slashDamage = 0;
	public float pierceDamage = 0;
	public float bluntDamage = 0;
	public float fireDamage = 0;
	public float waterDamage = 0;
	public float electricalDamage = 0;
	public float poisonDamage = 0;

	public Effect[] effect = {};

	public abstract void activate();

	public enum TYPE {
		WEAPON
	}

	public TYPE type;

	public abstract Enhancement makeCopy();

	@Override
	public void draw2() {
	}

	public static void loadEnchantmentImages() {
		getGlobalImage("effect_bleed.png");
		getGlobalImage("effect_burn.png");
		getGlobalImage("effect_poison.png");
		getGlobalImage("effect_wet.png");
	}
}
