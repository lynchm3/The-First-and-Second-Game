package com.marklynch.objects.templates;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.weapons.Weapon;

public class TemplatesWeapons {

	// \t\tpublic\sstatic\sfinal\s[a-zA-Z]+\s

	public TemplatesWeapons() {

		// Weapons
		Templates.KATANA = new Weapon();
		Templates.KATANA.name = "Katana";
		Templates.KATANA.slashDamage = 11;
		Templates.KATANA.minRange = 1;
		Templates.KATANA.maxRange = 1;
		Templates.KATANA.imageTexturePath = "katana.png";
		Templates.KATANA.totalHealth = Templates.KATANA.remainingHealth = 22;
		Templates.KATANA.widthRatio = 1f;
		Templates.KATANA.heightRatio = 1f;
		Templates.KATANA.drawOffsetRatioX = 0f;
		Templates.KATANA.drawOffsetRatioY = 0f;
		Templates.KATANA.soundWhenHit = 1f;
		Templates.KATANA.soundWhenHitting = 1f;
		Templates.KATANA.soundDampening = 1f;
		Templates.KATANA.stackable = false;
		Templates.KATANA.waterResistance = 0f;
		Templates.KATANA.electricResistance = 0f;
		Templates.KATANA.poisonResistance = 0f;
		Templates.KATANA.slashResistance = 0f;
		Templates.KATANA.weight = 15;
		Templates.KATANA.value = 69;
		Templates.KATANA.anchorX = 27;
		Templates.KATANA.anchorY = 85;
		Templates.KATANA.templateId = GameObject.generateNewTemplateId();

		Templates.CLEAVER = new Weapon();
		Templates.CLEAVER.name = "Cleaver";
		Templates.CLEAVER.slashDamage = 7;
		Templates.CLEAVER.minRange = 1;
		Templates.CLEAVER.maxRange = 1;
		Templates.CLEAVER.imageTexturePath = "cleaver.png";
		Templates.CLEAVER.totalHealth = Templates.CLEAVER.remainingHealth = 20;
		Templates.CLEAVER.widthRatio = 1f;
		Templates.CLEAVER.heightRatio = 1f;
		Templates.CLEAVER.drawOffsetRatioX = 0f;
		Templates.CLEAVER.drawOffsetRatioY = 0f;
		Templates.CLEAVER.soundWhenHit = 1f;
		Templates.CLEAVER.soundWhenHitting = 1f;
		Templates.CLEAVER.soundDampening = 1f;
		Templates.CLEAVER.stackable = false;
		Templates.CLEAVER.weight = 17f;
		Templates.CLEAVER.value = 16;
		Templates.CLEAVER.anchorX = 33;
		Templates.CLEAVER.anchorY = 104;
		Templates.CLEAVER.templateId = GameObject.generateNewTemplateId();

		Templates.HUNTING_BOW = new Weapon();
		Templates.HUNTING_BOW.name = "Hunting Bow";
		Templates.HUNTING_BOW.pierceDamage = 5;
		Templates.HUNTING_BOW.minRange = 1;
		Templates.HUNTING_BOW.maxRange = 9;
		Templates.HUNTING_BOW.imageTexturePath = "a2r2.png";
		Templates.HUNTING_BOW.totalHealth = Templates.HUNTING_BOW.remainingHealth = 20;
		Templates.HUNTING_BOW.widthRatio = 1f;
		Templates.HUNTING_BOW.heightRatio = 1f;
		Templates.HUNTING_BOW.drawOffsetRatioX = 0f;
		Templates.HUNTING_BOW.drawOffsetRatioY = 0f;
		Templates.HUNTING_BOW.soundWhenHit = 1f;
		Templates.HUNTING_BOW.soundWhenHitting = 1f;
		Templates.HUNTING_BOW.soundDampening = 1f;
		Templates.HUNTING_BOW.stackable = false;
		Templates.HUNTING_BOW.fireResistance = -70f;
		Templates.HUNTING_BOW.weight = 26f;
		Templates.HUNTING_BOW.value = 54;
		Templates.HUNTING_BOW.anchorX = 86;
		Templates.HUNTING_BOW.anchorY = 86;
		Templates.HUNTING_BOW.templateId = GameObject.generateNewTemplateId();
	}

}
