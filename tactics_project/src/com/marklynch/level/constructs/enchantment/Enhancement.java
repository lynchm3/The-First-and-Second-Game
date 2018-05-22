package com.marklynch.level.constructs.enchantment;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.HashMap;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.Stat.OFFENSIVE_STATS;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.utils.Texture;

public class Enhancement {

	public HashMap<HIGH_LEVEL_STATS, Stat> highLevelStats = new HashMap<HIGH_LEVEL_STATS, Stat>();
	public HashMap<OFFENSIVE_STATS, Stat> offensiveStats = new HashMap<OFFENSIVE_STATS, Stat>();
	public HashMap<OFFENSIVE_STATS, Stat> defensiveStats = new HashMap<OFFENSIVE_STATS, Stat>();

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

		offensiveStats.put(OFFENSIVE_STATS.SLASH_DAMAGE, new Stat(0));
		offensiveStats.put(OFFENSIVE_STATS.BLUNT_DAMAGE, new Stat(0));
		offensiveStats.put(OFFENSIVE_STATS.PIERCE_DAMAGE, new Stat(0));
		offensiveStats.put(OFFENSIVE_STATS.FIRE_DAMAGE, new Stat(0));
		offensiveStats.put(OFFENSIVE_STATS.WATER_DAMAGE, new Stat(0));
		offensiveStats.put(OFFENSIVE_STATS.ELECTRICAL_DAMAGE, new Stat(0));
		offensiveStats.put(OFFENSIVE_STATS.POISON_DAMAGE, new Stat(0));
		offensiveStats.put(OFFENSIVE_STATS.BLEED_DAMAGE, new Stat(0));
		offensiveStats.put(OFFENSIVE_STATS.HEALING, new Stat(0));

		defensiveStats.put(OFFENSIVE_STATS.SLASH_DAMAGE, new Stat(0));
		defensiveStats.put(OFFENSIVE_STATS.BLUNT_DAMAGE, new Stat(0));
		defensiveStats.put(OFFENSIVE_STATS.PIERCE_DAMAGE, new Stat(0));
		defensiveStats.put(OFFENSIVE_STATS.FIRE_DAMAGE, new Stat(0));
		defensiveStats.put(OFFENSIVE_STATS.WATER_DAMAGE, new Stat(0));
		defensiveStats.put(OFFENSIVE_STATS.ELECTRICAL_DAMAGE, new Stat(0));
		defensiveStats.put(OFFENSIVE_STATS.POISON_DAMAGE, new Stat(0));
		defensiveStats.put(OFFENSIVE_STATS.BLEED_DAMAGE, new Stat(0));
		defensiveStats.put(OFFENSIVE_STATS.HEALING, new Stat(0));
	}

	public Enhancement makeCopy() {

		Enhancement enhancement = new Enhancement();

		for (HIGH_LEVEL_STATS statKey : this.highLevelStats.keySet()) {
			enhancement.highLevelStats.put(statKey, this.highLevelStats.get(statKey).makeCopy());
		}

		for (OFFENSIVE_STATS statKey : this.offensiveStats.keySet()) {
			enhancement.offensiveStats.put(statKey, this.offensiveStats.get(statKey).makeCopy());
		}

		for (OFFENSIVE_STATS statKey : this.defensiveStats.keySet()) {
			enhancement.defensiveStats.put(statKey, this.defensiveStats.get(statKey).makeCopy());
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
