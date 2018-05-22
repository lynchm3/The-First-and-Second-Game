package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.OFFENSIVE_STATS;
import com.marklynch.objects.Fence;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Wall;

public class TemplatesBuilding {

	public TemplatesBuilding() {

		Templates.WALL = new Wall();
		Templates.WALL.name = "Wall";
		Templates.WALL.imageTexturePath = "wall.png";
		Templates.WALL.imageTexture = getGlobalImage(Templates.WALL.imageTexturePath, true);
		Templates.WALL.totalHealth = Templates.WALL.remainingHealth = 1000;
		Templates.WALL.widthRatio = 1f;
		Templates.WALL.heightRatio = 1f;
		Templates.WALL.drawOffsetRatioX = 0f;
		Templates.WALL.drawOffsetRatioY = 0f;
		Templates.WALL.soundWhenHit = 10f;
		Templates.WALL.soundWhenHitting = 1f;
		Templates.WALL.soundDampening = 10f;
		Templates.WALL.stackable = false;
		Templates.WALL.weight = 100f;
		Templates.WALL.value = 24;
		Templates.WALL.anchorX = 0;
		Templates.WALL.anchorY = 0;
		Templates.WALL.templateId = GameObject.generateNewTemplateId();
		Templates.WALL.flipYAxisInMirror = false;
		Templates.WALL.defensiveStats.put(OFFENSIVE_STATS.SLASH_DAMAGE, new Stat(100));
		Templates.WALL.defensiveStats.put(OFFENSIVE_STATS.BLUNT_DAMAGE, new Stat(100));
		Templates.WALL.defensiveStats.put(OFFENSIVE_STATS.PIERCE_DAMAGE, new Stat(100));
		Templates.WALL.defensiveStats.put(OFFENSIVE_STATS.FIRE_DAMAGE, new Stat(100));
		Templates.WALL.defensiveStats.put(OFFENSIVE_STATS.WATER_DAMAGE, new Stat(100));
		Templates.WALL.defensiveStats.put(OFFENSIVE_STATS.ELECTRICAL_DAMAGE, new Stat(100));
		Templates.WALL.defensiveStats.put(OFFENSIVE_STATS.POISON_DAMAGE, new Stat(100));
		Templates.WALL.defensiveStats.put(OFFENSIVE_STATS.BLEED_DAMAGE, new Stat(100));
		Templates.WALL.defensiveStats.put(OFFENSIVE_STATS.HEALING, new Stat(100));

		Templates.FENCE = new Fence();
		Templates.FENCE.name = "Fence";
		Templates.FENCE.imageTexturePath = "wall.png";
		Templates.FENCE.imageTexture = getGlobalImage(Templates.FENCE.imageTexturePath, true);
		Templates.FENCE.totalHealth = Templates.FENCE.remainingHealth = 100;
		Templates.FENCE.widthRatio = 1f;
		Templates.FENCE.heightRatio = 1f;
		Templates.FENCE.drawOffsetRatioX = 0f;
		Templates.FENCE.drawOffsetRatioY = 0f;
		Templates.FENCE.soundWhenHit = 1f;
		Templates.FENCE.soundWhenHitting = 1f;
		Templates.FENCE.soundDampening = 1f;
		Templates.FENCE.stackable = false;
		Templates.FENCE.weight = 50f;
		Templates.FENCE.value = 17;
		Templates.FENCE.anchorX = 0;
		Templates.FENCE.anchorY = 0;
		Templates.FENCE.templateId = GameObject.generateNewTemplateId();
		Templates.FENCE.flipYAxisInMirror = false;
	}

}
