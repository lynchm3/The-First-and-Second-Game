package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.weapons.BodyArmor;
import com.marklynch.objects.weapons.Helmet;
import com.marklynch.objects.weapons.LegArmor;

public class TemplatesArmor {

	// \t\tpublic\sstatic\sfinal\s[a-zA-Z]+\s

	public TemplatesArmor() {

		// ARMOUR
		// Helmets
		Templates.HARD_HAT = new Helmet();
		Templates.HARD_HAT.name = "Hard Hat";
		Templates.HARD_HAT.imageTexturePath = "hard_hat.png";
		Templates.HARD_HAT.imageTexture = getGlobalImage(Templates.HARD_HAT.imageTexturePath, true);
		Templates.HARD_HAT.totalHealth = Templates.HARD_HAT.remainingHealth = 50;
		Templates.HARD_HAT.widthRatio = 0.25f;
		Templates.HARD_HAT.heightRatio = 0.12f;
		Templates.HARD_HAT.drawOffsetRatioX = 0f;
		Templates.HARD_HAT.drawOffsetRatioY = 0f;
		Templates.HARD_HAT.soundWhenHit = 1f;
		Templates.HARD_HAT.soundWhenHitting = 1f;
		Templates.HARD_HAT.soundDampening = 1f;
		Templates.HARD_HAT.stackable = false;
		Templates.HARD_HAT.waterResistance = 0f;
		Templates.HARD_HAT.electricResistance = 0f;
		Templates.HARD_HAT.poisonResistance = 0f;
		Templates.HARD_HAT.slashResistance = 0f;
		Templates.HARD_HAT.weight = 7;
		Templates.HARD_HAT.value = 52;
		Templates.HARD_HAT.anchorX = 20;
		Templates.HARD_HAT.anchorY = 8;
		Templates.HARD_HAT.templateId = GameObject.generateNewTemplateId();

		Templates.PINK_HARD_HAT = new Helmet();
		Templates.PINK_HARD_HAT.name = "Hard Hat";
		Templates.PINK_HARD_HAT.imageTexturePath = "pink_hard_hat.png";
		Templates.PINK_HARD_HAT.imageTexture = getGlobalImage(Templates.PINK_HARD_HAT.imageTexturePath, true);
		Templates.PINK_HARD_HAT.totalHealth = Templates.PINK_HARD_HAT.remainingHealth = 50;
		Templates.PINK_HARD_HAT.widthRatio = 0.25f;
		Templates.PINK_HARD_HAT.heightRatio = 0.12f;
		Templates.PINK_HARD_HAT.drawOffsetRatioX = 0f;
		Templates.PINK_HARD_HAT.drawOffsetRatioY = 0f;
		Templates.PINK_HARD_HAT.soundWhenHit = 1f;
		Templates.PINK_HARD_HAT.soundWhenHitting = 1f;
		Templates.PINK_HARD_HAT.soundDampening = 1f;
		Templates.PINK_HARD_HAT.stackable = false;
		Templates.PINK_HARD_HAT.waterResistance = 0f;
		Templates.PINK_HARD_HAT.electricResistance = 0f;
		Templates.PINK_HARD_HAT.poisonResistance = 0f;
		Templates.PINK_HARD_HAT.slashResistance = 0f;
		Templates.PINK_HARD_HAT.weight = 7;
		Templates.PINK_HARD_HAT.value = 52;
		Templates.PINK_HARD_HAT.anchorX = 20;
		Templates.PINK_HARD_HAT.anchorY = 8;
		Templates.PINK_HARD_HAT.templateId = GameObject.generateNewTemplateId();

		Templates.COWBOY_HAT = new Helmet();
		Templates.COWBOY_HAT.name = "Cowboy Hat";
		Templates.COWBOY_HAT.imageTexturePath = "cowboy_hat.png";
		Templates.COWBOY_HAT.imageTexture = getGlobalImage(Templates.COWBOY_HAT.imageTexturePath, true);
		Templates.COWBOY_HAT.totalHealth = Templates.COWBOY_HAT.remainingHealth = 15;
		Templates.COWBOY_HAT.widthRatio = 0.5f;
		Templates.COWBOY_HAT.heightRatio = 0.5f;
		Templates.COWBOY_HAT.drawOffsetRatioX = 0f;
		Templates.COWBOY_HAT.drawOffsetRatioY = 0f;
		Templates.COWBOY_HAT.soundWhenHit = 1f;
		Templates.COWBOY_HAT.soundWhenHitting = 1f;
		Templates.COWBOY_HAT.soundDampening = 1f;
		Templates.COWBOY_HAT.stackable = false;
		Templates.COWBOY_HAT.weight = 6f;
		Templates.COWBOY_HAT.value = 34;
		Templates.COWBOY_HAT.anchorX = 34;
		Templates.COWBOY_HAT.anchorY = 68;
		Templates.COWBOY_HAT.templateId = GameObject.generateNewTemplateId();

		// Body Armor
		Templates.JUMPER = new BodyArmor();
		Templates.JUMPER.name = "Jumper";
		Templates.JUMPER.imageTexturePath = "jumper.png";
		Templates.JUMPER.imageTexture = getGlobalImage(Templates.JUMPER.imageTexturePath, true);
		Templates.JUMPER.totalHealth = Templates.JUMPER.remainingHealth = 20;
		Templates.JUMPER.widthRatio = 0.25f;
		Templates.JUMPER.heightRatio = 0.75f;
		Templates.JUMPER.drawOffsetRatioX = 0f;
		Templates.JUMPER.drawOffsetRatioY = 0f;
		Templates.JUMPER.soundWhenHit = 1f;
		Templates.JUMPER.soundWhenHitting = 1f;
		Templates.JUMPER.soundDampening = 1f;
		Templates.JUMPER.stackable = false;
		Templates.JUMPER.weight = 14f;
		Templates.JUMPER.value = 30;
		Templates.JUMPER.anchorX = 0;
		Templates.JUMPER.anchorY = 0;
		Templates.JUMPER.templateId = GameObject.generateNewTemplateId();

		// Leg Armor

		Templates.PANTS = new LegArmor();
		Templates.PANTS.name = "Pants";
		Templates.PANTS.imageTexturePath = "pants.png";
		Templates.PANTS.imageTexture = getGlobalImage(Templates.PANTS.imageTexturePath, true);
		Templates.PANTS.totalHealth = Templates.PANTS.remainingHealth = 20;
		Templates.PANTS.widthRatio = 0.5f;
		Templates.PANTS.heightRatio = 1f;
		Templates.PANTS.drawOffsetRatioX = 0f;
		Templates.PANTS.drawOffsetRatioY = 0f;
		Templates.PANTS.soundWhenHit = 1f;
		Templates.PANTS.soundWhenHitting = 1f;
		Templates.PANTS.soundDampening = 1f;
		Templates.PANTS.stackable = false;
		Templates.PANTS.weight = 12f;
		Templates.PANTS.value = 24;
		Templates.PANTS.anchorX = 0;
		Templates.PANTS.anchorY = 0;
		Templates.PANTS.templateId = GameObject.generateNewTemplateId();
	}

}
