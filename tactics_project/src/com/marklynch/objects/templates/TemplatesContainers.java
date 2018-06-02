package com.marklynch.objects.templates;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Storage;
import com.marklynch.utils.ResourceUtils;

public class TemplatesContainers {

	public TemplatesContainers() {

		Templates.CHEST = new Storage();
		Templates.CHEST.name = "Chest";
		Templates.CHEST.baseName = "Chest";
		Templates.CHEST.chestClosedTexture = ResourceUtils.getGlobalImage("chest.png", true);
		Templates.CHEST.chestOpenTexture = ResourceUtils.getGlobalImage("chest_open.png", true);
		Templates.CHEST.totalHealth = Templates.CHEST.remainingHealth = 200;
		Templates.CHEST.widthRatio = 1f;
		Templates.CHEST.heightRatio = 1f;
		Templates.CHEST.drawOffsetRatioX = 0f;
		Templates.CHEST.drawOffsetRatioY = 0f;
		Templates.CHEST.soundWhenHit = 1f;
		Templates.CHEST.soundWhenHitting = 1f;
		Templates.CHEST.soundDampening = 1f;
		Templates.CHEST.stackable = false;
		Templates.CHEST.weight = 100f;
		Templates.CHEST.value = 37;
		Templates.CHEST.anchorX = 0;
		Templates.CHEST.anchorY = 0;
		Templates.CHEST.templateId = GameObject.generateNewTemplateId();
		Templates.CHEST.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(5));
		Templates.CHEST.flipYAxisInMirror = false;

		Templates.CRATE = new Storage();
		Templates.CRATE.name = "Crate";
		Templates.CRATE.baseName = "Crate";
		Templates.CRATE.chestClosedTexture = ResourceUtils.getGlobalImage("crate.png", true);
		Templates.CRATE.chestOpenTexture = ResourceUtils.getGlobalImage("crate_open.png", true);
		Templates.CRATE.totalHealth = Templates.CRATE.remainingHealth = 60;
		Templates.CRATE.widthRatio = 1f;
		Templates.CRATE.heightRatio = 1f;
		Templates.CRATE.drawOffsetRatioX = 0f;
		Templates.CRATE.drawOffsetRatioY = 0f;
		Templates.CRATE.soundWhenHit = 1f;
		Templates.CRATE.soundWhenHitting = 1f;
		Templates.CRATE.soundDampening = 1f;
		Templates.CRATE.stackable = false;
		Templates.CRATE.floats = true;
		Templates.CRATE.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_DAMAGE, new Stat(-50));
		Templates.CRATE.weight = 100f;
		Templates.CRATE.value = 20;
		Templates.CRATE.anchorX = 0;
		Templates.CRATE.anchorY = 0;
		Templates.CRATE.templateId = GameObject.generateNewTemplateId();
		Templates.CRATE.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(4));
		Templates.CRATE.flipYAxisInMirror = false;

		Templates.LOST_AND_FOUND = new Storage();
		Templates.LOST_AND_FOUND.name = "Lost and Found";
		Templates.LOST_AND_FOUND.baseName = "Lost and Found";
		Templates.LOST_AND_FOUND.chestClosedTexture = ResourceUtils.getGlobalImage("crate.png", true);
		Templates.LOST_AND_FOUND.chestOpenTexture = ResourceUtils.getGlobalImage("crate_open.png", true);
		Templates.LOST_AND_FOUND.totalHealth = Templates.LOST_AND_FOUND.remainingHealth = 120;
		Templates.LOST_AND_FOUND.stackable = false;
		Templates.LOST_AND_FOUND.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_DAMAGE, new Stat(-50));
		Templates.LOST_AND_FOUND.weight = 160f;
		Templates.LOST_AND_FOUND.value = 40;
		Templates.LOST_AND_FOUND.anchorX = 0;
		Templates.LOST_AND_FOUND.anchorY = 0;
		Templates.LOST_AND_FOUND.templateId = GameObject.generateNewTemplateId();
		Templates.LOST_AND_FOUND.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(6));
		Templates.LOST_AND_FOUND.flipYAxisInMirror = false;

	}

}
