package com.marklynch.objects.templates;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.inanimateobjects.GameObject;

public class TemplatesJunk {

	public TemplatesJunk() {

		Templates.FUR = new GameObject();
		Templates.FUR.name = "Fur";
		Templates.FUR.setImageAndExtrapolateSize("fur.png");
		Templates.FUR.totalHealth = Templates.FUR.remainingHealth = 21;
		Templates.FUR.weight = 15f;
		Templates.FUR.value = 55;
		Templates.FUR.anchorX = 64;
		Templates.FUR.anchorY = 64;
		Templates.FUR.templateId = GameObject.generateNewTemplateId();

		Templates.DIRTY_SHEET_3 = new GameObject();
		Templates.DIRTY_SHEET_3.name = "Dirty Sheet";
		Templates.DIRTY_SHEET_3.setImageAndExtrapolateSize("dirty_sheet_3.png");
		Templates.DIRTY_SHEET_3.totalHealth = Templates.DIRTY_SHEET_3.remainingHealth = 1;
		Templates.DIRTY_SHEET_3.weight = 4f;
		Templates.DIRTY_SHEET_3.value = 5;
		Templates.DIRTY_SHEET_3.anchorX = 64;
		Templates.DIRTY_SHEET_3.anchorY = 64;
		Templates.DIRTY_SHEET_3.templateId = GameObject.generateNewTemplateId();

		Templates.WOOD = new GameObject();
		Templates.WOOD.name = "Wood";
		Templates.WOOD.setImageAndExtrapolateSize("wood.png");
		Templates.WOOD.totalHealth = Templates.WOOD.remainingHealth = 16;
		Templates.WOOD.weight = 0f;
		Templates.WOOD.value = 8;
		Templates.WOOD.anchorX = 32;
		Templates.WOOD.anchorY = 16;
		Templates.WOOD.templateId = GameObject.generateNewTemplateId();
		Templates.WOOD.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, 4));

		Templates.ORE = new GameObject();
		Templates.ORE.name = "Ore";
		Templates.ORE.setImageAndExtrapolateSize("ore.png");
		Templates.ORE.totalHealth = Templates.ORE.remainingHealth = 1;
		Templates.ORE.weight = 7f;
		Templates.ORE.value = 39;
		Templates.ORE.anchorX = 16;
		Templates.ORE.anchorY = 16;
		Templates.ORE.templateId = GameObject.generateNewTemplateId();
		Templates.ORE.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, 6));

	}

}
