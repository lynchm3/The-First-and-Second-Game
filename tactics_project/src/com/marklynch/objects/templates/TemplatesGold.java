package com.marklynch.objects.templates;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.Gold;
import com.marklynch.objects.Orb;

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

		Templates.ORB = new Orb();
		Templates.ORB.name = "Orb";
		Templates.ORB.imageTexturePath = "orb.png";
		Templates.ORB.totalHealth = Templates.ORB.remainingHealth = 1;
		Templates.ORB.widthRatio = 0.25f;
		Templates.ORB.heightRatio = 0.25f;
		Templates.ORB.drawOffsetX = 0f;
		Templates.ORB.drawOffsetY = 0f;
		Templates.ORB.soundWhenHit = 1f;
		Templates.ORB.soundWhenHitting = 1f;
		Templates.ORB.soundDampening = 1f;
		Templates.ORB.fireResistance = 100f;
		Templates.ORB.stackable = false;
		Templates.ORB.weight = 1f;
		Templates.ORB.value = 1;
		Templates.ORB.anchorX = 0;
		Templates.ORB.anchorY = 0;
		Templates.ORB.templateId = GameObject.generateNewTemplateId();

	}

}
