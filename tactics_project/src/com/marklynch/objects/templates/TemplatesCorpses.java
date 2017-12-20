package com.marklynch.objects.templates;

import com.marklynch.objects.Carcass;
import com.marklynch.objects.Corpse;
import com.marklynch.objects.GameObject;

public class TemplatesCorpses {

	public TemplatesCorpses() {

		Templates.CARCASS = new Carcass();
		Templates.CARCASS.name = "Carcass";
		Templates.CARCASS.imageTexturePath = "carcass.png";
		Templates.CARCASS.totalHealth = Templates.CARCASS.remainingHealth = 34;
		Templates.CARCASS.widthRatio = 0.5f;
		Templates.CARCASS.heightRatio = 0.5f;
		Templates.CARCASS.drawOffsetX = 0f;
		Templates.CARCASS.drawOffsetY = -0f;
		Templates.CARCASS.soundWhenHit = 1f;
		Templates.CARCASS.soundWhenHitting = 1f;
		Templates.CARCASS.soundDampening = 1f;
		Templates.CARCASS.stackable = false;
		Templates.CARCASS.weight = 32f;
		Templates.CARCASS.value = 16;
		Templates.CARCASS.anchorX = 0;
		Templates.CARCASS.anchorY = 0;
		Templates.CARCASS.templateId = GameObject.generateNewTemplateId();

		Templates.CORPSE = new Corpse();
		Templates.CORPSE.name = "Corpse";
		Templates.CORPSE.imageTexturePath = "carcass.png";
		Templates.CORPSE.totalHealth = Templates.CORPSE.remainingHealth = 32;
		Templates.CORPSE.widthRatio = 0.5f;
		Templates.CORPSE.heightRatio = 0.5f;
		Templates.CORPSE.drawOffsetX = 0f;
		Templates.CORPSE.drawOffsetY = 0f;
		Templates.CORPSE.soundWhenHit = 1f;
		Templates.CORPSE.soundWhenHitting = 1f;
		Templates.CORPSE.soundDampening = 1f;
		Templates.CORPSE.stackable = false;
		Templates.CORPSE.weight = 28f;
		Templates.CORPSE.value = 8;
		Templates.CORPSE.anchorX = 0;
		Templates.CORPSE.anchorY = 0;
		Templates.CORPSE.templateId = GameObject.generateNewTemplateId();

		Templates.ASH = new GameObject();
		Templates.ASH.name = "Ash";
		Templates.ASH.imageTexturePath = "ash.png";
		Templates.ASH.totalHealth = Templates.ASH.remainingHealth = 1000;
		Templates.ASH.widthRatio = 0.5f;
		Templates.ASH.heightRatio = 0.5f;
		Templates.ASH.drawOffsetX = 0f;
		Templates.ASH.drawOffsetY = 0f;
		Templates.ASH.soundWhenHit = 1f;
		Templates.ASH.soundWhenHitting = 1f;
		Templates.ASH.soundDampening = 1f;
		Templates.ASH.stackable = true;
		Templates.ASH.fireResistance = 100f;
		Templates.ASH.weight = 2f;
		Templates.ASH.value = 3;
		Templates.ASH.anchorX = 0;
		Templates.ASH.anchorY = 0;
		Templates.ASH.templateId = GameObject.generateNewTemplateId();

		Templates.WOOD_CHIPS = new GameObject();
		Templates.WOOD_CHIPS.name = "Whood Chips";
		Templates.WOOD_CHIPS.imageTexturePath = "wood_chips.png";
		Templates.WOOD_CHIPS.totalHealth = Templates.WOOD_CHIPS.remainingHealth = 16;
		Templates.WOOD_CHIPS.widthRatio = 0.5f;
		Templates.WOOD_CHIPS.heightRatio = 0.5f;
		Templates.WOOD_CHIPS.drawOffsetX = 0f;
		Templates.WOOD_CHIPS.drawOffsetY = 0f;
		Templates.WOOD_CHIPS.soundWhenHit = 1f;
		Templates.WOOD_CHIPS.soundWhenHitting = 1f;
		Templates.WOOD_CHIPS.soundDampening = 1f;
		Templates.WOOD_CHIPS.fireResistance = -100f;
		Templates.WOOD_CHIPS.stackable = true;
		Templates.WOOD_CHIPS.weight = 12f;
		Templates.WOOD_CHIPS.value = 6;
		Templates.WOOD_CHIPS.anchorX = 0;
		Templates.WOOD_CHIPS.anchorY = 0;
		Templates.WOOD_CHIPS.templateId = GameObject.generateNewTemplateId();

	}

}
