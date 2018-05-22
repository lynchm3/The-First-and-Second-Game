package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.OFFENSIVE_STATS;
import com.marklynch.objects.Carcass;
import com.marklynch.objects.Corpse;
import com.marklynch.objects.GameObject;

public class TemplatesCorpses {

	public TemplatesCorpses() {

		Templates.CARCASS = new Carcass();
		Templates.CARCASS.name = "Carcass";
		Templates.CARCASS.imageTexturePath = "carcass.png";
		Templates.CARCASS.imageTexture = getGlobalImage(Templates.CARCASS.imageTexturePath, true);
		Templates.CARCASS.totalHealth = Templates.CARCASS.remainingHealth = 34;
		Templates.CARCASS.widthRatio = 0.5f;
		Templates.CARCASS.heightRatio = 0.5f;
		Templates.CARCASS.drawOffsetRatioX = 0f;
		Templates.CARCASS.drawOffsetRatioY = -0f;
		Templates.CARCASS.soundWhenHit = 1f;
		Templates.CARCASS.soundWhenHitting = 1f;
		Templates.CARCASS.soundDampening = 1f;
		Templates.CARCASS.stackable = false;
		Templates.CARCASS.weight = 32f;
		Templates.CARCASS.value = 16;
		Templates.CARCASS.anchorX = 0;
		Templates.CARCASS.anchorY = 0;
		Templates.CARCASS.templateId = GameObject.generateNewTemplateId();
		Templates.CARCASS.offensiveStats.put(OFFENSIVE_STATS.BLUNT_DAMAGE, new Stat(3));

		Templates.CORPSE = new Corpse();
		Templates.CORPSE.name = "Corpse";
		Templates.CORPSE.imageTexturePath = "carcass.png";
		Templates.CORPSE.imageTexture = getGlobalImage(Templates.CORPSE.imageTexturePath, true);
		Templates.CORPSE.totalHealth = Templates.CORPSE.remainingHealth = 32;
		Templates.CORPSE.widthRatio = 0.5f;
		Templates.CORPSE.heightRatio = 0.5f;
		Templates.CORPSE.drawOffsetRatioX = 0f;
		Templates.CORPSE.drawOffsetRatioY = 0f;
		Templates.CORPSE.soundWhenHit = 1f;
		Templates.CORPSE.soundWhenHitting = 1f;
		Templates.CORPSE.soundDampening = 1f;
		Templates.CORPSE.stackable = false;
		Templates.CORPSE.weight = 28f;
		Templates.CORPSE.value = 8;
		Templates.CORPSE.anchorX = 0;
		Templates.CORPSE.anchorY = 0;
		Templates.CORPSE.templateId = GameObject.generateNewTemplateId();
		Templates.CORPSE.offensiveStats.put(OFFENSIVE_STATS.BLUNT_DAMAGE, new Stat(3));

		Templates.ASH = new GameObject();
		Templates.ASH.name = "Ash";
		Templates.ASH.imageTexturePath = "ash.png";
		Templates.ASH.imageTexture = getGlobalImage(Templates.ASH.imageTexturePath, true);
		Templates.ASH.totalHealth = Templates.ASH.remainingHealth = 1000;
		Templates.ASH.widthRatio = 0.5f;
		Templates.ASH.heightRatio = 0.5f;
		Templates.ASH.drawOffsetRatioX = 0f;
		Templates.ASH.drawOffsetRatioY = 0f;
		Templates.ASH.soundWhenHit = 1f;
		Templates.ASH.soundWhenHitting = 1f;
		Templates.ASH.soundDampening = 1f;
		Templates.ASH.stackable = true;
		Templates.ASH.defensiveStats.put(OFFENSIVE_STATS.FIRE_DAMAGE, new Stat(100));
		Templates.ASH.weight = 2f;
		Templates.ASH.value = 3;
		Templates.ASH.anchorX = 0;
		Templates.ASH.anchorY = 0;
		Templates.ASH.templateId = GameObject.generateNewTemplateId();
		Templates.ASH.flipYAxisInMirror = false;

		Templates.WOOD_CHIPS = new GameObject();
		Templates.WOOD_CHIPS.name = "Whood Chips";
		Templates.WOOD_CHIPS.imageTexturePath = "wood_chips.png";
		Templates.WOOD_CHIPS.imageTexture = getGlobalImage(Templates.WOOD_CHIPS.imageTexturePath, true);
		Templates.WOOD_CHIPS.totalHealth = Templates.WOOD_CHIPS.remainingHealth = 16;
		Templates.WOOD_CHIPS.widthRatio = 0.5f;
		Templates.WOOD_CHIPS.heightRatio = 0.5f;
		Templates.WOOD_CHIPS.drawOffsetRatioX = 0f;
		Templates.WOOD_CHIPS.drawOffsetRatioY = 0f;
		Templates.WOOD_CHIPS.soundWhenHit = 1f;
		Templates.WOOD_CHIPS.soundWhenHitting = 1f;
		Templates.WOOD_CHIPS.soundDampening = 1f;
		Templates.WOOD_CHIPS.defensiveStats.put(OFFENSIVE_STATS.FIRE_DAMAGE, new Stat(-100));
		Templates.WOOD_CHIPS.stackable = true;
		Templates.WOOD_CHIPS.weight = 12f;
		Templates.WOOD_CHIPS.value = 6;
		Templates.WOOD_CHIPS.anchorX = 0;
		Templates.WOOD_CHIPS.anchorY = 0;
		Templates.WOOD_CHIPS.templateId = GameObject.generateNewTemplateId();

	}

}
