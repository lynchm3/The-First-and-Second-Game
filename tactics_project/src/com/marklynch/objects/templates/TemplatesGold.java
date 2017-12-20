package com.marklynch.objects.templates;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.Gold;

public class TemplatesGold {

	public TemplatesGold() {

		// GOLD BITCH

		Templates.GOLD = new Gold();
		Templates.GOLD.name = "Gold";
		Templates.GOLD.imageTexturePath = "gold.png";
		Templates.GOLD.totalHealth = Templates.GOLD.remainingHealth = 1;
		Templates.GOLD.widthRatio = 0.25f;
		Templates.GOLD.heightRatio = 0.25f;
		Templates.GOLD.drawOffsetX = 0f;
		Templates.GOLD.drawOffsetY = 0f;
		Templates.GOLD.soundWhenHit = 1f;
		Templates.GOLD.soundWhenHitting = 1f;
		Templates.GOLD.soundDampening = 1f;
		Templates.GOLD.fireResistance = 100f;
		Templates.GOLD.stackable = false;
		Templates.GOLD.weight = 1f;
		Templates.GOLD.value = 1;
		Templates.GOLD.anchorX = 0;
		Templates.GOLD.anchorY = 0;
		Templates.GOLD.templateId = GameObject.generateNewTemplateId();

	}

}
