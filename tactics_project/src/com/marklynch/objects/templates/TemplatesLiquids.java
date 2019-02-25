package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBloodCovered;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.level.constructs.effect.EffectSlow;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Liquid;

public class TemplatesLiquids {

	public TemplatesLiquids() {

		Templates.WATER = new Liquid();
		Templates.WATER.name = "Water";
		Templates.WATER.setImageAndExtrapolateSize("water.png");
		Templates.WATER.totalHealth = Templates.WATER.remainingHealth = 1000;
		Templates.WATER.weight = 5f;
		Templates.WATER.value = 36;
		Templates.WATER.anchorX = 0;
		Templates.WATER.anchorY = 0;
		Templates.WATER.templateId = GameObject.generateNewTemplateId();
		Templates.WATER.volume = 1;
		Templates.WATER.touchEffects = new Effect[] { new EffectWet(3) };
		Templates.WATER.consumeEffects = new Effect[] {};
		Templates.WATER.highLevelStats.put(HIGH_LEVEL_STATS.WATER_DAMAGE, new Stat(HIGH_LEVEL_STATS.WATER_DAMAGE, 1));
		Templates.WATER.textures.add(getGlobalImage("water.png", true));
		Templates.WATER.textures.add(getGlobalImage("water_2.png", true));
		Templates.WATER.textures.add(getGlobalImage("water_3.png", true));
		Templates.WATER.textures.add(getGlobalImage("water_4.png", true));
		Templates.WATER.setAllResistances100();
//		Templates.WATER.highLevelStats.put(HIGH_LEVEL_STATS.WATER_DAMAGE, new Stat(HIGH_LEVEL_STATS.WATER_DAMAGE, 1));

		Templates.POISON = new Liquid();
		Templates.POISON.name = "Poison";
		Templates.POISON.setImageAndExtrapolateSize("poison.png");
		Templates.POISON.totalHealth = Templates.POISON.remainingHealth = 1000;
		Templates.POISON.weight = 6f;
		Templates.POISON.value = 16;
		Templates.POISON.anchorX = 0;
		Templates.POISON.anchorY = 0;
		Templates.POISON.templateId = GameObject.generateNewTemplateId();
		Templates.POISON.volume = 1;
		Templates.POISON.touchEffects = new Effect[] { new EffectPoison(3) };
		Templates.POISON.consumeEffects = new Effect[] { new EffectPoison(5) };
		Templates.POISON.highLevelStats.put(HIGH_LEVEL_STATS.POISON_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.POISON_DAMAGE, 3));
		Templates.POISON.textures.add(getGlobalImage("poison.png", true));
		Templates.POISON.textures.add(getGlobalImage("poison_2.png", true));
		Templates.POISON.textures.add(getGlobalImage("poison_3.png", true));
		Templates.POISON.textures.add(getGlobalImage("poison_4.png", true));
		Templates.POISON.setAllResistances100();

		Templates.OIL = new Liquid();
		Templates.OIL.name = "Oil";
		Templates.OIL.setImageAndExtrapolateSize("oil.png");
		Templates.OIL.totalHealth = Templates.OIL.remainingHealth = 1000;
		Templates.OIL.weight = 6f;
		Templates.OIL.value = 16;
		Templates.OIL.anchorX = 0;
		Templates.OIL.anchorY = 0;
		Templates.OIL.templateId = GameObject.generateNewTemplateId();
		Templates.OIL.volume = 1;
		Templates.OIL.touchEffects = new Effect[] { new EffectSlow(3) };
		Templates.OIL.consumeEffects = new Effect[] { new EffectSlow(5) };
		Templates.OIL.textures.add(getGlobalImage("oil.png", true));
		Templates.OIL.textures.add(getGlobalImage("oil_2.png", true));
		Templates.OIL.textures.add(getGlobalImage("oil_3.png", true));
		Templates.OIL.textures.add(getGlobalImage("oil_4.png", true));
		Templates.OIL.setAllResistances100();
		Templates.OIL.setAllResistances100();

		Templates.BLOOD = new Liquid();
		Templates.BLOOD.name = "Blood";
		Templates.BLOOD.setImageAndExtrapolateSize("blood.png");
		Templates.BLOOD.totalHealth = Templates.BLOOD.remainingHealth = 1000;
		Templates.BLOOD.weight = 6f;
		Templates.BLOOD.value = 16;
		Templates.BLOOD.anchorX = 0;
		Templates.BLOOD.anchorY = 0;
		Templates.BLOOD.templateId = GameObject.generateNewTemplateId();
		Templates.BLOOD.volume = 1;
		Templates.BLOOD.touchEffects = new Effect[] { new EffectBloodCovered(3) };
		Templates.BLOOD.consumeEffects = new Effect[] { new EffectBloodCovered(5) };
		Templates.BLOOD.textures.add(getGlobalImage("blood.png", true));
		Templates.BLOOD.textures.add(getGlobalImage("blood_2.png", true));
		Templates.BLOOD.textures.add(getGlobalImage("blood_3.png", true));
		Templates.BLOOD.textures.add(getGlobalImage("blood_4.png", true));
		Templates.BLOOD.setAllResistances100();
	}

}
