package com.marklynch.level.constructs.enchantment;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.utils.Texture;

public abstract class Enhancement {

	public String enhancementName = "Enhancement";
	public Texture imageTexture;
	public float slashDamage = 0;
	public float pierceDamage = 0;
	public float bluntDamage = 0;
	public float fireDamage = 0;
	public float waterDamage = 0;
	public float electricalDamage = 0;
	public float poisonDamage = 0;
	public float bleedDamage = 0;
	public float healing = 0;

	public float slashResistance = 0;
	public float pierceResistance = 0;
	public float bluntResistance = 0;
	public float fireResistance = 0;
	public float waterResistance = 0;
	public float electricalResistance = 0;
	public float poisonResistance = 0;
	public float bleedResistance = 0;

	public Effect[] effect = {};

	public abstract void activate();

	public enum TYPE {
		WEAPON
	}

	public TYPE type;

	public abstract Enhancement makeCopy();

	public static void loadEnchantmentImages() {
		getGlobalImage("effect_bleed.png", false);
		getGlobalImage("effect_burn.png", false);
		getGlobalImage("effect_poison.png", false);
		getGlobalImage("effect_wet.png", false);
	}
}
