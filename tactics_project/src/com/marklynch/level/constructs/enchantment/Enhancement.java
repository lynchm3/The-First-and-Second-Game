package com.marklynch.level.constructs.enchantment;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.HashMap;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.characterscreen.CharacterScreen;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.utils.Texture;

public class Enhancement {

	public HashMap<HIGH_LEVEL_STATS, Stat> highLevelStats = new HashMap<HIGH_LEVEL_STATS, Stat>();
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
	public float minRange = 0;
	public float maxRange = 0;

	public float slashResistance = 0;
	public float pierceResistance = 0;
	public float bluntResistance = 0;
	public float fireResistance = 0;
	public float waterResistance = 0;
	public float electricalResistance = 0;
	public float poisonResistance = 0;
	public float bleedResistance = 0;

	public Effect[] effect = {};
	public int templateId;

	public enum TYPE {
		WEAPON
	}

	public TYPE type;

	public Enhancement() {

		highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(CharacterScreen.STRENGTH, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(CharacterScreen.DEXTERITY, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(CharacterScreen.ENDURANCE, 0));
		highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(CharacterScreen.INTELLIGENCE, 0));
	}

	public Enhancement makeCopy() {

		Enhancement enhancement = new Enhancement();
		for (HIGH_LEVEL_STATS statKey : this.highLevelStats.keySet()) {
			enhancement.highLevelStats.put(statKey, this.highLevelStats.get(statKey).makeCopy());
		}

		enhancement.slashDamage = slashDamage;
		enhancement.pierceDamage = pierceDamage;
		enhancement.bluntDamage = bluntDamage;
		enhancement.fireDamage = fireDamage;
		enhancement.waterDamage = waterDamage;
		enhancement.electricalDamage = electricalDamage;
		enhancement.poisonDamage = poisonDamage;
		enhancement.bleedDamage = bleedDamage;
		enhancement.healing = healing;
		enhancement.minRange = minRange;
		enhancement.maxRange = maxRange;

		enhancement.slashResistance = slashResistance;
		enhancement.bluntResistance = bluntResistance;
		enhancement.pierceResistance = pierceResistance;
		enhancement.fireResistance = fireResistance;
		enhancement.waterResistance = waterResistance;
		enhancement.electricalResistance = electricalResistance;
		enhancement.poisonResistance = poisonResistance;
		enhancement.bleedResistance = bleedResistance;

		return enhancement;

	}

	public static void loadEnchantmentImages() {
		getGlobalImage("effect_bleed.png", false);
		getGlobalImage("effect_burn.png", false);
		getGlobalImage("effect_poison.png", false);
		getGlobalImage("effect_wet.png", false);
	}
}
