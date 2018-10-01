package com.marklynch.objects.templates;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Junk;

public class TemplatesJunk {

	public TemplatesJunk() {

		Templates.FUR = new Junk();
		Templates.FUR.name = "Fur";
		Templates.FUR.setImageAndExtrapolateSize("fur.png");
		Templates.FUR.totalHealth = Templates.FUR.remainingHealth = 21;
		Templates.FUR.weight = 15f;
		Templates.FUR.value = 55;
		Templates.FUR.anchorX = 0;
		Templates.FUR.anchorY = 0;
		Templates.FUR.templateId = GameObject.generateNewTemplateId();

		Templates.DIRTY_SHEET_3 = new Junk();
		Templates.DIRTY_SHEET_3.name = "Dirty Sheet";
		Templates.DIRTY_SHEET_3.setImageAndExtrapolateSize("dirty_sheet_3.png");
		Templates.DIRTY_SHEET_3.totalHealth = Templates.DIRTY_SHEET_3.remainingHealth = 1;
		Templates.DIRTY_SHEET_3.weight = 4f;
		Templates.DIRTY_SHEET_3.value = 5;
		Templates.DIRTY_SHEET_3.anchorX = 0;
		Templates.DIRTY_SHEET_3.anchorY = 0;
		Templates.DIRTY_SHEET_3.templateId = GameObject.generateNewTemplateId();

		Templates.WOOD = new Junk();
		Templates.WOOD.name = "Wood";
		Templates.WOOD.setImageAndExtrapolateSize("wood.png");
		Templates.WOOD.totalHealth = Templates.WOOD.remainingHealth = 16;
		Templates.WOOD.weight = 0f;
		Templates.WOOD.value = 8;
		Templates.WOOD.anchorX = 0;
		Templates.WOOD.anchorY = 0;
		Templates.WOOD.templateId = GameObject.generateNewTemplateId();
		Templates.WOOD.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(4));

		Templates.ORE = new Junk();
		Templates.ORE.name = "Ore";
		Templates.ORE.setImageAndExtrapolateSize("ore.png");
		Templates.ORE.totalHealth = Templates.ORE.remainingHealth = 1;
		Templates.ORE.weight = 7f;
		Templates.ORE.value = 39;
		Templates.ORE.anchorX = 0;
		Templates.ORE.anchorY = 0;
		Templates.ORE.templateId = GameObject.generateNewTemplateId();
		Templates.ORE.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(6));

	}

}
