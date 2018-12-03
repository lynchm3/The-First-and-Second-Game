package com.marklynch.objects.templates;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.inanimateobjects.Carcass;
import com.marklynch.objects.inanimateobjects.Corpse;
import com.marklynch.objects.inanimateobjects.GameObject;

public class TemplatesCorpses {

	public TemplatesCorpses() {

		Templates.CARCASS = new Carcass();
		Templates.CARCASS.name = "Carcass";
		Templates.CARCASS.setImageAndExtrapolateSize("carcass.png");
		Templates.CARCASS.totalHealth = Templates.CARCASS.remainingHealth = 34;
		Templates.CARCASS.weight = 32f;
		Templates.CARCASS.value = 16;
		Templates.CARCASS.anchorX = 0;
		Templates.CARCASS.anchorY = 0;
		Templates.CARCASS.templateId = GameObject.generateNewTemplateId();
		Templates.CARCASS.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(3));

		Templates.CORPSE = new Corpse();
		Templates.CORPSE.name = "Corpse";
		Templates.CORPSE.setImageAndExtrapolateSize("carcass.png");
		Templates.CORPSE.totalHealth = Templates.CORPSE.remainingHealth = 32;
		Templates.CORPSE.weight = 28f;
		Templates.CORPSE.value = 8;
		Templates.CORPSE.anchorX = 0;
		Templates.CORPSE.anchorY = 0;
		Templates.CORPSE.templateId = GameObject.generateNewTemplateId();
		Templates.CORPSE.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(3));

		Templates.ASH = new GameObject();
		Templates.ASH.name = "Ash";
		Templates.ASH.setImageAndExtrapolateSize("ash.png");
		Templates.ASH.totalHealth = Templates.ASH.remainingHealth = 1000;
		Templates.ASH.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(100));
		Templates.ASH.weight = 2f;
		Templates.ASH.value = 3;
		Templates.ASH.anchorX = 16;
		Templates.ASH.anchorY = 32;
		Templates.ASH.templateId = GameObject.generateNewTemplateId();
		Templates.ASH.flipYAxisInMirror = false;

		Templates.WOOD_CHIPS = new GameObject();
		Templates.WOOD_CHIPS.name = "Whood Chips";
		Templates.WOOD_CHIPS.setImageAndExtrapolateSize("wood_chips.png");
		Templates.WOOD_CHIPS.totalHealth = Templates.WOOD_CHIPS.remainingHealth = 16;
		Templates.WOOD_CHIPS.drawOffsetRatioX = 0f;
		Templates.WOOD_CHIPS.drawOffsetRatioY = 0f;
		Templates.WOOD_CHIPS.soundWhenHit = 1f;
		Templates.WOOD_CHIPS.soundWhenHitting = 1f;
		Templates.WOOD_CHIPS.soundDampening = 1f;
		Templates.WOOD_CHIPS.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(-100));
		Templates.WOOD_CHIPS.stackable = true;
		Templates.WOOD_CHIPS.weight = 12f;
		Templates.WOOD_CHIPS.value = 6;
		Templates.WOOD_CHIPS.anchorX = 16;
		Templates.WOOD_CHIPS.anchorY = 32;
		Templates.WOOD_CHIPS.templateId = GameObject.generateNewTemplateId();

	}

}
