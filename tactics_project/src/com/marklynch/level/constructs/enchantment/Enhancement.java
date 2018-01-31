package com.marklynch.level.constructs.enchantment;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.effect.Effect;

import com.marklynch.utils.Texture;

public abstract class Enhancement {

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

	public static void loadEnchantmentImages() {
		getGlobalImage("effect_bleed.png");
		getGlobalImage("effect_burn.png");
		getGlobalImage("effect_poison.png");
		getGlobalImage("effect_wet.png");
	}
}
