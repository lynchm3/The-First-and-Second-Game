package com.marklynch.level.constructs.enchantment;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.HashMap;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.utils.Texture;

public class Enhancement {

	public HashMap<HIGH_LEVEL_STATS, Stat> highLevelStats = new HashMap<HIGH_LEVEL_STATS, Stat>();

	public String enhancementName = "Enhancement";
	public Texture imageTexture;
	public float minRange = 0;
	public float maxRange = 0;

	public Effect[] effect = {};
	public int templateId;

	public enum TYPE {
		WEAPON
	}

	public TYPE type;

	public Enhancement() {

		highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.FRIENDLY_FIRE, new Stat(0));

		highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.FIRE_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.WATER_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.POISON_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.BLEED_DAMAGE, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.HEALING, new Stat(0));

		highLevelStats.put(HIGH_LEVEL_STATS.SLASH_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.WATER_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.POISON_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.BLEED_RES, new Stat(0));
		highLevelStats.put(HIGH_LEVEL_STATS.HEALING_RES, new Stat(0));
	}

	public Enhancement makeCopy() {

		Enhancement enhancement = new Enhancement();

		for (HIGH_LEVEL_STATS statKey : this.highLevelStats.keySet()) {
			enhancement.highLevelStats.put(statKey, this.highLevelStats.get(statKey).makeCopy());
		}

		for (HIGH_LEVEL_STATS statKey : this.highLevelStats.keySet()) {
			enhancement.highLevelStats.put(statKey, this.highLevelStats.get(statKey).makeCopy());
		}

		for (HIGH_LEVEL_STATS statKey : this.highLevelStats.keySet()) {
			enhancement.highLevelStats.put(statKey, this.highLevelStats.get(statKey).makeCopy());
		}

		enhancement.minRange = minRange;
		enhancement.maxRange = maxRange;

		return enhancement;

	}

	public static void loadEnchantmentImages() {
		getGlobalImage("effect_bleed.png", false);
		getGlobalImage("effect_burn.png", false);
		getGlobalImage("effect_poison.png", false);
		getGlobalImage("effect_wet.png", false);
	}
}
