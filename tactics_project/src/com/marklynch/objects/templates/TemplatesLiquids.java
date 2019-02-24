package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Liquid;
import com.marklynch.objects.inanimateobjects.WaterShallow;

public class TemplatesLiquids {

	public TemplatesLiquids() {

		Templates.WATER = new WaterShallow();
		Templates.WATER.name = "Water";
		Templates.WATER.setImageAndExtrapolateSize("water.png");
		Templates.WATER.totalHealth = Templates.WATER.remainingHealth = 1000;
		Templates.WATER.weight = 5f;
		Templates.WATER.value = 36;
		Templates.WATER.anchorX = 0;
		Templates.WATER.anchorY = 0;
		Templates.WATER.templateId = GameObject.generateNewTemplateId();
		Templates.WATER.volume = 1;
		Templates.WATER.touchEffects = new Effect[] { new EffectWet(5) };
		Templates.WATER.consumeEffects = new Effect[] {};
		Templates.WATER.textures.add(getGlobalImage("water.png", true));
		Templates.WATER.textures.add(getGlobalImage("water_2.png", true));
		Templates.WATER.textures.add(getGlobalImage("water_3.png", true));
		Templates.WATER.textures.add(getGlobalImage("water_4.png", true));
		Templates.WATER.setAllResistances100();
//		Templates.WATER.highLevelStats.put(HIGH_LEVEL_STATS.WATER_DAMAGE, new Stat(HIGH_LEVEL_STATS.WATER_DAMAGE, 1));

		Templates.POISON = new Liquid();
		Templates.POISON.name = "Poison";
		Templates.POISON.setImageAndExtrapolateSize("effect_poison.png");
		Templates.POISON.totalHealth = Templates.POISON.remainingHealth = 10;
		Templates.POISON.weight = 6f;
		Templates.POISON.value = 16;
		Templates.POISON.anchorX = 0;
		Templates.POISON.anchorY = 0;
		Templates.POISON.templateId = GameObject.generateNewTemplateId();
		Templates.POISON.volume = 1;
		Templates.POISON.touchEffects = new Effect[] { new EffectPoison(5) };
		Templates.POISON.consumeEffects = new Effect[] { new EffectPoison(10) };
		Templates.POISON.highLevelStats.put(HIGH_LEVEL_STATS.POISON_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.POISON_DAMAGE, 3));
	}

}
