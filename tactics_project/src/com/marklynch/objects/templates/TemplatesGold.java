package com.marklynch.objects.templates;

import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Gold;
import com.marklynch.objects.inanimateobjects.Orb;

public class TemplatesGold {

	public TemplatesGold() {

		// GOLD BITCH

		Templates.GOLD = new Gold();
		Templates.GOLD.name = "Gold";
		Templates.GOLD.setImageAndExtrapolateSize("gold.png");
		Templates.GOLD.totalHealth = Templates.GOLD.remainingHealth = 1;
		Templates.GOLD.weight = 1f;
		Templates.GOLD.value = 1;
		Templates.GOLD.anchorX = 0;
		Templates.GOLD.anchorY = 0;
		Templates.GOLD.templateId = GameObject.generateNewTemplateId();

		Templates.SMALL_ORB = new Orb();
		Templates.SMALL_ORB.name = "Orb";
		Templates.SMALL_ORB.setImageAndExtrapolateSize("orb.png");
		Templates.SMALL_ORB.totalHealth = Templates.SMALL_ORB.remainingHealth = 1;
		Templates.SMALL_ORB.widthRatio = 0.125f;
		Templates.SMALL_ORB.heightRatio = 0.125f;
		Templates.SMALL_ORB.weight = 1f;
		Templates.SMALL_ORB.value = 1;
		Templates.SMALL_ORB.anchorX = 0;
		Templates.SMALL_ORB.anchorY = 0;
		Templates.SMALL_ORB.templateId = GameObject.generateNewTemplateId();

		Templates.MEDIUM_ORB = new Orb();
		Templates.MEDIUM_ORB.name = "Orb";
		Templates.MEDIUM_ORB.setImageAndExtrapolateSize("orb.png");
		Templates.MEDIUM_ORB.totalHealth = Templates.MEDIUM_ORB.remainingHealth = 1;
		Templates.MEDIUM_ORB.widthRatio = 0.25f;
		Templates.MEDIUM_ORB.heightRatio = 0.25f;
		Templates.MEDIUM_ORB.weight = 1f;
		Templates.MEDIUM_ORB.value = 1;
		Templates.MEDIUM_ORB.anchorX = 0;
		Templates.MEDIUM_ORB.anchorY = 0;
		Templates.MEDIUM_ORB.templateId = GameObject.generateNewTemplateId();

		Templates.LARGE_ORB = new Orb();
		Templates.LARGE_ORB.name = "Orb";
		Templates.LARGE_ORB.setImageAndExtrapolateSize("orb.png");
		Templates.LARGE_ORB.totalHealth = Templates.LARGE_ORB.remainingHealth = 1;
		Templates.LARGE_ORB.widthRatio = 0.5f;
		Templates.LARGE_ORB.heightRatio = 0.5f;
		Templates.LARGE_ORB.weight = 1f;
		Templates.LARGE_ORB.value = 1;
		Templates.LARGE_ORB.anchorX = 0;
		Templates.LARGE_ORB.anchorY = 0;
		Templates.LARGE_ORB.templateId = GameObject.generateNewTemplateId();

		Templates.BUBBLE = new Orb();
		Templates.BUBBLE.name = "Bubble";
		Templates.BUBBLE.setImageAndExtrapolateSize("bubble.png");
		Templates.BUBBLE.totalHealth = Templates.BUBBLE.remainingHealth = 1;
		Templates.BUBBLE.widthRatio = 0.5f;
		Templates.BUBBLE.heightRatio = 0.5f;
		Templates.BUBBLE.weight = 1f;
		Templates.BUBBLE.value = 1;
		Templates.BUBBLE.anchorX = 0;
		Templates.BUBBLE.anchorY = 0;
		Templates.BUBBLE.templateId = GameObject.generateNewTemplateId();

	}

}
