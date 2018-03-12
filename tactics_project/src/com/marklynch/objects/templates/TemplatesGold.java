package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.Gold;
import com.marklynch.objects.Orb;

public class TemplatesGold {

	public TemplatesGold() {

		// GOLD BITCH

		Templates.GOLD = new Gold();
		Templates.GOLD.name = "Gold";
		Templates.GOLD.imageTexturePath = "gold.png";
		Templates.GOLD.imageTexture = getGlobalImage(Templates.GOLD.imageTexturePath, true);
		Templates.GOLD.totalHealth = Templates.GOLD.remainingHealth = 1;
		Templates.GOLD.widthRatio = 0.25f;
		Templates.GOLD.heightRatio = 0.25f;
		Templates.GOLD.drawOffsetRatioX = 0f;
		Templates.GOLD.drawOffsetRatioY = 0f;
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

		Templates.SMALL_ORB = new Orb();
		Templates.SMALL_ORB.name = "Orb";
		Templates.SMALL_ORB.imageTexturePath = "orb.png";
		Templates.SMALL_ORB.imageTexture = getGlobalImage(Templates.SMALL_ORB.imageTexturePath, true);
		Templates.SMALL_ORB.totalHealth = Templates.SMALL_ORB.remainingHealth = 1;
		Templates.SMALL_ORB.widthRatio = 0.125f;
		Templates.SMALL_ORB.heightRatio = 0.125f;
		Templates.SMALL_ORB.drawOffsetRatioX = 0f;
		Templates.SMALL_ORB.drawOffsetRatioY = 0f;
		Templates.SMALL_ORB.soundWhenHit = 1f;
		Templates.SMALL_ORB.soundWhenHitting = 1f;
		Templates.SMALL_ORB.soundDampening = 1f;
		Templates.SMALL_ORB.fireResistance = 100f;
		Templates.SMALL_ORB.stackable = false;
		Templates.SMALL_ORB.weight = 1f;
		Templates.SMALL_ORB.value = 1;
		Templates.SMALL_ORB.anchorX = 0;
		Templates.SMALL_ORB.anchorY = 0;
		Templates.SMALL_ORB.templateId = GameObject.generateNewTemplateId();

		Templates.MEDIUM_ORB = new Orb();
		Templates.MEDIUM_ORB.name = "Orb";
		Templates.MEDIUM_ORB.imageTexturePath = "orb.png";
		Templates.MEDIUM_ORB.imageTexture = getGlobalImage(Templates.MEDIUM_ORB.imageTexturePath, true);
		Templates.MEDIUM_ORB.totalHealth = Templates.MEDIUM_ORB.remainingHealth = 1;
		Templates.MEDIUM_ORB.widthRatio = 0.25f;
		Templates.MEDIUM_ORB.heightRatio = 0.25f;
		Templates.MEDIUM_ORB.drawOffsetRatioX = 0f;
		Templates.MEDIUM_ORB.drawOffsetRatioY = 0f;
		Templates.MEDIUM_ORB.soundWhenHit = 1f;
		Templates.MEDIUM_ORB.soundWhenHitting = 1f;
		Templates.MEDIUM_ORB.soundDampening = 1f;
		Templates.MEDIUM_ORB.fireResistance = 100f;
		Templates.MEDIUM_ORB.stackable = false;
		Templates.MEDIUM_ORB.weight = 1f;
		Templates.MEDIUM_ORB.value = 1;
		Templates.MEDIUM_ORB.anchorX = 0;
		Templates.MEDIUM_ORB.anchorY = 0;
		Templates.MEDIUM_ORB.templateId = GameObject.generateNewTemplateId();

		Templates.LARGE_ORB = new Orb();
		Templates.LARGE_ORB.name = "Orb";
		Templates.LARGE_ORB.imageTexturePath = "orb.png";
		Templates.LARGE_ORB.imageTexture = getGlobalImage(Templates.LARGE_ORB.imageTexturePath, true);
		Templates.LARGE_ORB.totalHealth = Templates.LARGE_ORB.remainingHealth = 1;
		Templates.LARGE_ORB.widthRatio = 0.5f;
		Templates.LARGE_ORB.heightRatio = 0.5f;
		Templates.LARGE_ORB.drawOffsetRatioX = 0f;
		Templates.LARGE_ORB.drawOffsetRatioY = 0f;
		Templates.LARGE_ORB.soundWhenHit = 1f;
		Templates.LARGE_ORB.soundWhenHitting = 1f;
		Templates.LARGE_ORB.soundDampening = 1f;
		Templates.LARGE_ORB.fireResistance = 100f;
		Templates.LARGE_ORB.stackable = false;
		Templates.LARGE_ORB.weight = 1f;
		Templates.LARGE_ORB.value = 1;
		Templates.LARGE_ORB.anchorX = 0;
		Templates.LARGE_ORB.anchorY = 0;
		Templates.LARGE_ORB.templateId = GameObject.generateNewTemplateId();

		Templates.BUBBLE = new Orb();
		Templates.BUBBLE.name = "Bubble";
		Templates.BUBBLE.imageTexturePath = "bubble.png";
		Templates.BUBBLE.imageTexture = getGlobalImage(Templates.BUBBLE.imageTexturePath, true);
		Templates.BUBBLE.totalHealth = Templates.BUBBLE.remainingHealth = 1;
		Templates.BUBBLE.widthRatio = 0.5f;
		Templates.BUBBLE.heightRatio = 0.5f;
		Templates.BUBBLE.drawOffsetRatioX = 0f;
		Templates.BUBBLE.drawOffsetRatioY = 0f;
		Templates.BUBBLE.soundWhenHit = 1f;
		Templates.BUBBLE.soundWhenHitting = 1f;
		Templates.BUBBLE.soundDampening = 1f;
		Templates.BUBBLE.fireResistance = 100f;
		Templates.BUBBLE.stackable = false;
		Templates.BUBBLE.weight = 1f;
		Templates.BUBBLE.value = 1;
		Templates.BUBBLE.anchorX = 0;
		Templates.BUBBLE.anchorY = 0;
		Templates.BUBBLE.templateId = GameObject.generateNewTemplateId();

	}

}
