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
		Templates.HARD_HAT.widthRatio = 1f;
		Templates.HARD_HAT.heightRatio = 0.5f;
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
		Templates.HARD_HAT.widthRatio = 1f;
		Templates.HARD_HAT.heightRatio = 0.5f;
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
		Templates.HARD_HAT.value = 82;
		Templates.HARD_HAT.anchorX = 20;
		Templates.HARD_HAT.anchorY = 8;
		Templates.HARD_HAT.templateId = GameObject.generateNewTemplateId();

		Templates.HUNTING_CAP = new Helmet();
		Templates.HUNTING_CAP.name = "Hunting Cap";
		Templates.HUNTING_CAP.imageTexturePath = "hunting_cap.png";
		Templates.HUNTING_CAP.imageTexture = getGlobalImage(Templates.HUNTING_CAP.imageTexturePath, true);
		Templates.HUNTING_CAP.totalHealth = Templates.HUNTING_CAP.remainingHealth = 50;
		Templates.HUNTING_CAP.widthRatio = 1f;
		Templates.HUNTING_CAP.heightRatio = 0.5f;
		Templates.HUNTING_CAP.weight = 5;
		Templates.HUNTING_CAP.value = 21;
		Templates.HUNTING_CAP.anchorX = 20;
		Templates.HUNTING_CAP.anchorY = 8;
		Templates.HUNTING_CAP.templateId = GameObject.generateNewTemplateId();

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
		Templates.JUMPER.armUpperTexture = getGlobalImage("jumper_arm_upper.png", true);
		Templates.JUMPER.armLowerTexture = getGlobalImage("jumper_arm_lower.png", true);
		Templates.JUMPER.totalHealth = Templates.JUMPER.remainingHealth = 20;
		Templates.JUMPER.widthRatio = 1f;
		Templates.JUMPER.heightRatio = 1.5f;
		Templates.JUMPER.drawOffsetRatioX = 0f;
		Templates.JUMPER.drawOffsetRatioY = 0f;
		Templates.JUMPER.soundWhenHit = 1f;
		Templates.JUMPER.soundWhenHitting = 1f;
		Templates.JUMPER.soundDampening = 1f;
		Templates.JUMPER.stackable = false;
		Templates.JUMPER.weight = 14f;
		Templates.JUMPER.value = 30;
		Templates.JUMPER.templateId = GameObject.generateNewTemplateId();

		Templates.APRON = new BodyArmor();
		Templates.APRON.name = "Apron";
		Templates.APRON.imageTexturePath = "apron.png";
		Templates.APRON.imageTexture = getGlobalImage(Templates.APRON.imageTexturePath, true);
		Templates.APRON.armUpperTexture = getGlobalImage("apron_arm_upper.png", true);
		Templates.APRON.armLowerTexture = getGlobalImage("apron_arm_lower.png", true);
		Templates.APRON.totalHealth = Templates.APRON.remainingHealth = 20;
		Templates.APRON.widthRatio = 1f;
		Templates.APRON.heightRatio = 1.5f;
		Templates.APRON.weight = 17f;
		Templates.APRON.value = 38;
		Templates.APRON.templateId = GameObject.generateNewTemplateId();

		Templates.ROBE = new BodyArmor();
		Templates.ROBE.name = "Robe";
		Templates.ROBE.imageTexturePath = "robe.png";
		Templates.ROBE.imageTexture = getGlobalImage(Templates.ROBE.imageTexturePath, true);
		Templates.ROBE.armUpperTexture = getGlobalImage("robe_arm_upper.png", true);
		Templates.ROBE.armLowerTexture = getGlobalImage("robe_arm_lower.png", true);
		Templates.ROBE.backTexture = getGlobalImage("robe_back.png", true);
		Templates.ROBE.totalHealth = Templates.ROBE.remainingHealth = 26;
		Templates.ROBE.widthRatio = 1f;
		Templates.ROBE.heightRatio = 1.5f;
		Templates.ROBE.weight = 21f;
		Templates.ROBE.value = 46;
		Templates.ROBE.templateId = GameObject.generateNewTemplateId();

		Templates.LEATHERS = new BodyArmor();
		Templates.LEATHERS.name = "Leathers";
		Templates.LEATHERS.imageTexturePath = "leathers.png";
		Templates.LEATHERS.imageTexture = getGlobalImage(Templates.LEATHERS.imageTexturePath, true);
		Templates.LEATHERS.armUpperTexture = getGlobalImage("leathers_arm_upper.png", true);
		Templates.LEATHERS.armLowerTexture = getGlobalImage("leathers_arm_lower.png", true);
		Templates.LEATHERS.totalHealth = Templates.LEATHERS.remainingHealth = 54;
		Templates.LEATHERS.widthRatio = 1f;
		Templates.LEATHERS.heightRatio = 1.5f;
		Templates.LEATHERS.weight = 46f;
		Templates.LEATHERS.value = 104;
		Templates.LEATHERS.templateId = GameObject.generateNewTemplateId();

		Templates.CHAINMAIL = new BodyArmor();
		Templates.CHAINMAIL.name = "Chainmail";
		Templates.CHAINMAIL.imageTexturePath = "chainmail.png";
		Templates.CHAINMAIL.imageTexture = getGlobalImage(Templates.CHAINMAIL.imageTexturePath, true);
		Templates.CHAINMAIL.armUpperTexture = getGlobalImage("chainmail_arm_upper.png", true);
		Templates.CHAINMAIL.armLowerTexture = getGlobalImage("chainmail_arm_lower.png", true);
		Templates.CHAINMAIL.backTexture = getGlobalImage("chainmail_back.png", true);
		Templates.CHAINMAIL.totalHealth = Templates.CHAINMAIL.remainingHealth = 77;
		Templates.CHAINMAIL.widthRatio = 1f;
		Templates.CHAINMAIL.heightRatio = 1.5f;
		Templates.CHAINMAIL.weight = 75f;
		Templates.CHAINMAIL.value = 201;
		Templates.CHAINMAIL.templateId = GameObject.generateNewTemplateId();

		// Leg Armor

		Templates.PANTS = new LegArmor();
		Templates.PANTS.name = "Pants";
		Templates.PANTS.imageTexturePath = "pants.png";
		Templates.PANTS.imageTexture = getGlobalImage(Templates.PANTS.imageTexturePath, true);
		Templates.PANTS.totalHealth = Templates.PANTS.remainingHealth = 20;
		Templates.PANTS.widthRatio = 1f;
		Templates.PANTS.heightRatio = 1.5f;
		Templates.PANTS.drawOffsetRatioX = 0f;
		Templates.PANTS.drawOffsetRatioY = 0f;
		Templates.PANTS.soundWhenHit = 1f;
		Templates.PANTS.soundWhenHitting = 1f;
		Templates.PANTS.soundDampening = 1f;
		Templates.PANTS.stackable = false;
		Templates.PANTS.weight = 12f;
		Templates.PANTS.value = 24;
		Templates.PANTS.templateId = GameObject.generateNewTemplateId();

		Templates.DUNGAREES = new LegArmor();
		Templates.DUNGAREES.name = "Dungarees";
		Templates.DUNGAREES.imageTexturePath = "dungarees.png";
		Templates.DUNGAREES.imageTexture = getGlobalImage(Templates.DUNGAREES.imageTexturePath, true);
		Templates.DUNGAREES.totalHealth = Templates.DUNGAREES.remainingHealth = 27;
		Templates.DUNGAREES.widthRatio = 1f;
		Templates.DUNGAREES.heightRatio = 1.5f;
		Templates.DUNGAREES.drawOffsetRatioX = 0f;
		Templates.DUNGAREES.drawOffsetRatioY = 0f;
		Templates.DUNGAREES.soundWhenHit = 1f;
		Templates.DUNGAREES.soundWhenHitting = 1f;
		Templates.DUNGAREES.soundDampening = 1f;
		Templates.DUNGAREES.stackable = false;
		Templates.DUNGAREES.weight = 14f;
		Templates.DUNGAREES.value = 29;
		Templates.DUNGAREES.templateId = GameObject.generateNewTemplateId();
	}

}
