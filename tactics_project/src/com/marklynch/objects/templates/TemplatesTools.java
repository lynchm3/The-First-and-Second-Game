package com.marklynch.objects.templates;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.tools.Axe;
import com.marklynch.objects.tools.Bell;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.tools.FishingRod;
import com.marklynch.objects.tools.Knife;
import com.marklynch.objects.tools.Lantern;
import com.marklynch.objects.tools.Pickaxe;
import com.marklynch.objects.tools.Shovel;
import com.marklynch.objects.weapons.Weapon;

public class TemplatesTools {

	// \t\tpublic\sstatic\sfinal\s[a-zA-Z]+\s

	public TemplatesTools() {

		// Tools
		Templates.BROOM = new Weapon();
		Templates.BROOM.name = "Broom";
		Templates.BROOM.slashDamage = 0;
		Templates.BROOM.pierceDamage = 0;
		Templates.BROOM.bluntDamage = 1;
		Templates.BROOM.fireDamage = 0;
		Templates.BROOM.waterDamage = 0;
		Templates.BROOM.electricalDamage = 1;
		Templates.BROOM.poisonDamage = 0;
		Templates.BROOM.minRange = 1;
		Templates.BROOM.maxRange = 1;
		Templates.BROOM.imageTexturePath = "broom.png";
		Templates.BROOM.totalHealth = Templates.BROOM.remainingHealth = 20;
		Templates.BROOM.widthRatio = 1f;
		Templates.BROOM.heightRatio = 1f;
		Templates.BROOM.drawOffsetRatioX = 0f;
		Templates.BROOM.drawOffsetRatioY = 0f;
		Templates.BROOM.soundWhenHit = 1f;
		Templates.BROOM.soundWhenHitting = 1f;
		Templates.BROOM.soundDampening = 1f;
		Templates.BROOM.stackable = false;
		Templates.BROOM.fireResistance = -50f;
		Templates.BROOM.waterResistance = 0f;
		Templates.BROOM.electricResistance = 0f;
		Templates.BROOM.poisonResistance = 0f;
		Templates.BROOM.slashResistance = 0f;
		Templates.BROOM.weight = 15;
		Templates.BROOM.value = 3;
		Templates.BROOM.anchorX = 55;
		Templates.BROOM.anchorY = 88;
		Templates.BROOM.templateId = GameObject.generateNewTemplateId();

		Templates.PICKAXE = new Pickaxe();
		Templates.PICKAXE.name = "Pickaxe";
		Templates.PICKAXE.pierceDamage = 7;
		Templates.PICKAXE.minRange = 1;
		Templates.PICKAXE.maxRange = 1;
		Templates.PICKAXE.imageTexturePath = "pickaxe.png";
		Templates.PICKAXE.totalHealth = Templates.PICKAXE.remainingHealth = 20;
		Templates.PICKAXE.widthRatio = 1f;
		Templates.PICKAXE.heightRatio = 1f;
		Templates.PICKAXE.drawOffsetRatioX = 0f;
		Templates.PICKAXE.drawOffsetRatioY = 0f;
		Templates.PICKAXE.soundWhenHit = 1f;
		Templates.PICKAXE.soundWhenHitting = 1f;
		Templates.PICKAXE.soundDampening = 1f;
		Templates.PICKAXE.stackable = false;
		Templates.PICKAXE.fireResistance = -50f;
		Templates.PICKAXE.weight = 25f;
		Templates.PICKAXE.value = 3;
		Templates.PICKAXE.anchorX = 32;
		Templates.PICKAXE.anchorY = 94;
		Templates.PICKAXE.templateId = GameObject.generateNewTemplateId();

		Templates.SHOVEL = new Shovel();
		Templates.SHOVEL.name = "Shovel";
		Templates.SHOVEL.slashDamage = 6;
		Templates.SHOVEL.imageTexturePath = "shovel.png";
		Templates.SHOVEL.totalHealth = Templates.SHOVEL.remainingHealth = 27;
		Templates.SHOVEL.soundWhenHit = 1f;
		Templates.SHOVEL.soundWhenHitting = 1f;
		Templates.SHOVEL.soundDampening = 1f;
		Templates.SHOVEL.stackable = false;
		Templates.SHOVEL.fireResistance = -40f;
		Templates.SHOVEL.weight = 28f;
		Templates.SHOVEL.value = 6;
		Templates.SHOVEL.anchorX = 63;
		Templates.SHOVEL.anchorY = 67;
		Templates.SHOVEL.templateId = GameObject.generateNewTemplateId();

		Templates.FISHING_ROD = new FishingRod();
		Templates.FISHING_ROD.name = "Fishing Rod";
		Templates.FISHING_ROD.slashDamage = 4;
		Templates.FISHING_ROD.imageTexturePath = "fishing_rod.png";
		Templates.FISHING_ROD.totalHealth = Templates.FISHING_ROD.remainingHealth = 16;
		Templates.FISHING_ROD.soundWhenHit = 1f;
		Templates.FISHING_ROD.soundWhenHitting = 1f;
		Templates.FISHING_ROD.soundDampening = 1f;
		Templates.FISHING_ROD.stackable = false;
		Templates.FISHING_ROD.fireResistance = -60f;
		Templates.FISHING_ROD.weight = 23f;
		Templates.FISHING_ROD.value = 17;
		Templates.FISHING_ROD.anchorX = 55;
		Templates.FISHING_ROD.anchorY = 89;
		Templates.FISHING_ROD.templateId = GameObject.generateNewTemplateId();

		Templates.HATCHET = new Axe();
		Templates.HATCHET.name = "Hatchet";
		Templates.HATCHET.slashDamage = 6;
		Templates.HATCHET.minRange = 1;
		Templates.HATCHET.maxRange = 1;
		Templates.HATCHET.imageTexturePath = "hatchet.png";
		Templates.HATCHET.totalHealth = Templates.HATCHET.remainingHealth = 20;
		Templates.HATCHET.widthRatio = 1f;
		Templates.HATCHET.heightRatio = 1f;
		Templates.HATCHET.drawOffsetRatioX = 0f;
		Templates.HATCHET.drawOffsetRatioY = 0f;
		Templates.HATCHET.soundWhenHit = 1f;
		Templates.HATCHET.soundWhenHitting = 1f;
		Templates.HATCHET.soundDampening = 1f;
		Templates.HATCHET.stackable = false;
		Templates.HATCHET.fireResistance = -50f;
		Templates.HATCHET.weight = 22f;
		Templates.HATCHET.value = 32;
		Templates.HATCHET.anchorX = 48;
		Templates.HATCHET.anchorY = 89;
		Templates.HATCHET.templateId = GameObject.generateNewTemplateId();

		Templates.HUNTING_KNIFE = new Knife();
		Templates.HUNTING_KNIFE.name = "Hunting Knife";
		Templates.HUNTING_KNIFE.slashDamage = 9;
		Templates.HUNTING_KNIFE.minRange = 1;
		Templates.HUNTING_KNIFE.maxRange = 1;
		Templates.HUNTING_KNIFE.imageTexturePath = "hunting_knife.png";
		Templates.HUNTING_KNIFE.totalHealth = Templates.HUNTING_KNIFE.remainingHealth = 25;
		Templates.HUNTING_KNIFE.widthRatio = 1f;
		Templates.HUNTING_KNIFE.heightRatio = 1f;
		Templates.HUNTING_KNIFE.drawOffsetRatioX = 0f;
		Templates.HUNTING_KNIFE.drawOffsetRatioY = 0f;
		Templates.HUNTING_KNIFE.soundWhenHit = 1f;
		Templates.HUNTING_KNIFE.soundWhenHitting = 1f;
		Templates.HUNTING_KNIFE.soundDampening = 1f;
		Templates.HUNTING_KNIFE.stackable = false;
		Templates.HUNTING_KNIFE.weight = 12f;
		Templates.HUNTING_KNIFE.value = 37;
		Templates.HUNTING_KNIFE.anchorX = 47;
		Templates.HUNTING_KNIFE.anchorY = 58;
		Templates.HUNTING_KNIFE.templateId = GameObject.generateNewTemplateId();

		Templates.HOE = new Weapon();
		Templates.HOE.name = "Hoe";
		Templates.HOE.slashDamage = 3;
		Templates.HOE.minRange = 1;
		Templates.HOE.maxRange = 1;
		Templates.HOE.imageTexturePath = "hoe.png";
		Templates.HOE.totalHealth = Templates.HOE.remainingHealth = 24;
		Templates.HOE.widthRatio = 1f;
		Templates.HOE.heightRatio = 1f;
		Templates.HOE.drawOffsetRatioX = 0f;
		Templates.HOE.drawOffsetRatioY = 0f;
		Templates.HOE.soundWhenHit = 1f;
		Templates.HOE.soundWhenHitting = 1f;
		Templates.HOE.soundDampening = 1f;
		Templates.HOE.stackable = false;
		Templates.HOE.fireResistance = -50f;
		Templates.HOE.weight = 31f;
		Templates.HOE.value = 17;
		Templates.HOE.anchorX = 61;
		Templates.HOE.anchorY = 90;
		Templates.HOE.templateId = GameObject.generateNewTemplateId();

		Templates.SICKLE = new Weapon();
		Templates.SICKLE.name = "Sickle";
		Templates.SICKLE.slashDamage = 4;
		Templates.SICKLE.minRange = 1;
		Templates.SICKLE.maxRange = 1;
		Templates.SICKLE.imageTexturePath = "sickle.png";
		Templates.SICKLE.totalHealth = Templates.SICKLE.remainingHealth = 22;
		Templates.SICKLE.widthRatio = 1f;
		Templates.SICKLE.heightRatio = 1f;
		Templates.SICKLE.drawOffsetRatioX = 0f;
		Templates.SICKLE.drawOffsetRatioY = 0f;
		Templates.SICKLE.soundWhenHit = 1f;
		Templates.SICKLE.soundWhenHitting = 1f;
		Templates.SICKLE.soundDampening = 1f;
		Templates.SICKLE.stackable = false;
		Templates.SICKLE.fireResistance = -50f;
		Templates.SICKLE.weight = 21f;
		Templates.SICKLE.value = 22;
		Templates.SICKLE.anchorX = 20;
		Templates.SICKLE.anchorY = 29;
		Templates.SICKLE.templateId = GameObject.generateNewTemplateId();

		Templates.HAMMER = new Weapon();
		Templates.HAMMER.name = "Hammer";
		Templates.HAMMER.bluntDamage = 4;
		Templates.HAMMER.minRange = 1;
		Templates.HAMMER.maxRange = 1;
		Templates.HAMMER.imageTexturePath = "hammer.png";
		Templates.HAMMER.totalHealth = Templates.HAMMER.remainingHealth = 36;
		Templates.HAMMER.widthRatio = 1f;
		Templates.HAMMER.heightRatio = 1f;
		Templates.HAMMER.drawOffsetRatioX = 0f;
		Templates.HAMMER.drawOffsetRatioY = 0f;
		Templates.HAMMER.soundWhenHit = 1f;
		Templates.HAMMER.soundWhenHitting = 1f;
		Templates.HAMMER.soundDampening = 1f;
		Templates.HAMMER.stackable = false;
		Templates.HAMMER.fireResistance = -50f;
		Templates.HAMMER.weight = 23f;
		Templates.HAMMER.value = 16;
		Templates.HAMMER.anchorX = 31;
		Templates.HAMMER.anchorY = 98;
		Templates.HAMMER.templateId = GameObject.generateNewTemplateId();

		Templates.BASKET = new Weapon();
		Templates.BASKET.name = "Basket";
		Templates.BASKET.bluntDamage = 4;
		Templates.BASKET.minRange = 1;
		Templates.BASKET.maxRange = 1;
		Templates.BASKET.imageTexturePath = "basket.png";
		Templates.BASKET.totalHealth = Templates.BASKET.remainingHealth = 14;
		Templates.BASKET.widthRatio = 1f;
		Templates.BASKET.heightRatio = 1f;
		Templates.BASKET.drawOffsetRatioX = 0f;
		Templates.BASKET.drawOffsetRatioY = 0f;
		Templates.BASKET.soundWhenHit = 1f;
		Templates.BASKET.soundWhenHitting = 1f;
		Templates.BASKET.soundDampening = 1f;
		Templates.BASKET.stackable = false;
		Templates.BASKET.fireResistance = -80f;
		Templates.BASKET.weight = 9f;
		Templates.BASKET.value = 13;
		Templates.BASKET.anchorX = 65;
		Templates.BASKET.anchorY = 11;
		Templates.BASKET.templateId = GameObject.generateNewTemplateId();

		Templates.WHIP = new Weapon();
		Templates.WHIP.name = "Whip";
		Templates.WHIP.bluntDamage = 4;
		Templates.WHIP.minRange = 1;
		Templates.WHIP.maxRange = 1;
		Templates.WHIP.imageTexturePath = "whip.png";
		Templates.WHIP.totalHealth = Templates.WHIP.remainingHealth = 19;
		Templates.WHIP.widthRatio = 1f;
		Templates.WHIP.heightRatio = 1f;
		Templates.WHIP.drawOffsetRatioX = 0f;
		Templates.WHIP.drawOffsetRatioY = 0f;
		Templates.WHIP.soundWhenHit = 1f;
		Templates.WHIP.soundWhenHitting = 1f;
		Templates.WHIP.soundDampening = 1f;
		Templates.WHIP.stackable = false;
		Templates.WHIP.weight = 12f;
		Templates.WHIP.value = 24;
		Templates.WHIP.anchorX = 18;
		Templates.WHIP.anchorY = 80;
		Templates.WHIP.templateId = GameObject.generateNewTemplateId();

		Templates.SERRATED_SPOON = new Weapon();
		Templates.SERRATED_SPOON.name = "Serrated Spoon";
		Templates.SERRATED_SPOON.slashDamage = 2;
		Templates.SERRATED_SPOON.minRange = 1;
		Templates.SERRATED_SPOON.maxRange = 1;
		Templates.SERRATED_SPOON.imageTexturePath = "serrated_spoon.png";
		Templates.SERRATED_SPOON.totalHealth = Templates.SERRATED_SPOON.remainingHealth = 23;
		Templates.SERRATED_SPOON.widthRatio = 1f;
		Templates.SERRATED_SPOON.heightRatio = 1f;
		Templates.SERRATED_SPOON.drawOffsetRatioX = 0f;
		Templates.SERRATED_SPOON.drawOffsetRatioY = 0f;
		Templates.SERRATED_SPOON.soundWhenHit = 1f;
		Templates.SERRATED_SPOON.soundWhenHitting = 1f;
		Templates.SERRATED_SPOON.soundDampening = 1f;
		Templates.SERRATED_SPOON.stackable = false;
		Templates.SERRATED_SPOON.weight = 7f;
		Templates.SERRATED_SPOON.value = 6;
		Templates.SERRATED_SPOON.anchorX = 48;
		Templates.SERRATED_SPOON.anchorY = 113;
		Templates.SERRATED_SPOON.templateId = GameObject.generateNewTemplateId();

		Templates.DINNER_BELL = new Bell();
		Templates.DINNER_BELL.name = "Dinner Bell";
		Templates.DINNER_BELL.bluntDamage = 2;
		Templates.DINNER_BELL.minRange = 1;
		Templates.DINNER_BELL.maxRange = 1;
		Templates.DINNER_BELL.imageTexturePath = "bell.png";
		Templates.DINNER_BELL.totalHealth = Templates.DINNER_BELL.remainingHealth = 25;
		Templates.DINNER_BELL.widthRatio = 1f;
		Templates.DINNER_BELL.heightRatio = 1f;
		Templates.DINNER_BELL.drawOffsetRatioX = 0f;
		Templates.DINNER_BELL.drawOffsetRatioY = 0f;
		Templates.DINNER_BELL.soundWhenHit = 1f;
		Templates.DINNER_BELL.soundWhenHitting = 1f;
		Templates.DINNER_BELL.soundDampening = 1f;
		Templates.DINNER_BELL.stackable = false;
		Templates.DINNER_BELL.weight = 8f;
		Templates.DINNER_BELL.value = 14;
		Templates.DINNER_BELL.anchorX = 53;
		Templates.DINNER_BELL.anchorY = 103;
		Templates.DINNER_BELL.templateId = GameObject.generateNewTemplateId();

		Templates.LANTERN = new Lantern();
		Templates.LANTERN.name = "Lantern";
		Templates.LANTERN.bluntDamage = 1;
		Templates.LANTERN.fireDamage = 1;
		Templates.LANTERN.minRange = 1;
		Templates.LANTERN.maxRange = 1;
		Templates.LANTERN.imageTexturePath = "lantern.png";
		Templates.LANTERN.totalHealth = Templates.LANTERN.remainingHealth = 14;
		Templates.LANTERN.widthRatio = 1f;
		Templates.LANTERN.heightRatio = 1f;
		Templates.LANTERN.drawOffsetRatioX = 0f;
		Templates.LANTERN.drawOffsetRatioY = 0f;
		Templates.LANTERN.soundWhenHit = 1f;
		Templates.LANTERN.soundWhenHitting = 1f;
		Templates.LANTERN.soundDampening = 1f;
		Templates.LANTERN.stackable = false;
		Templates.LANTERN.weight = 10f;
		Templates.LANTERN.value = 19;
		Templates.LANTERN.anchorX = 63;
		Templates.LANTERN.anchorY = 6;
		Templates.LANTERN.templateId = GameObject.generateNewTemplateId();

		Templates.JAR = new ContainerForLiquids();
		Templates.JAR.name = "Empty Jar";
		Templates.JAR.slashDamage = 10;
		Templates.JAR.minRange = 1;
		Templates.JAR.maxRange = 1;
		Templates.JAR.imageTexturePath = "jar.png";
		Templates.JAR.totalHealth = Templates.JAR.remainingHealth = 14;
		Templates.JAR.widthRatio = 0.25f;
		Templates.JAR.heightRatio = 0.25f;
		Templates.JAR.drawOffsetRatioX = 0f;
		Templates.JAR.drawOffsetRatioY = 0f;
		Templates.JAR.soundWhenHit = 1f;
		Templates.JAR.soundWhenHitting = 1f;
		Templates.JAR.soundDampening = 1f;
		Templates.JAR.stackable = true;
		Templates.JAR.weight = 10f;
		Templates.JAR.value = 42;
		Templates.JAR.anchorX = 56;
		Templates.JAR.anchorY = 100;
		Templates.JAR.templateId = GameObject.generateNewTemplateId();
		Templates.JAR.volume = 1;
		Templates.JAR.liquid = null;

		Templates.JAR_OF_WATER = new ContainerForLiquids();
		Templates.JAR_OF_WATER.name = "Jar of Water";
		Templates.JAR_OF_WATER.slashDamage = 10;
		Templates.JAR_OF_WATER.minRange = 1;
		Templates.JAR_OF_WATER.maxRange = 1;
		Templates.JAR_OF_WATER.imageTexturePath = "water_jar.png";
		Templates.JAR_OF_WATER.totalHealth = Templates.JAR_OF_WATER.remainingHealth = 14;
		Templates.JAR_OF_WATER.widthRatio = 0.25f;
		Templates.JAR_OF_WATER.heightRatio = 0.25f;
		Templates.JAR_OF_WATER.drawOffsetRatioX = 0f;
		Templates.JAR_OF_WATER.drawOffsetRatioY = 0f;
		Templates.JAR_OF_WATER.soundWhenHit = 1f;
		Templates.JAR_OF_WATER.soundWhenHitting = 1f;
		Templates.JAR_OF_WATER.soundDampening = 1f;
		Templates.JAR_OF_WATER.stackable = true;
		Templates.JAR_OF_WATER.weight = 10f;
		Templates.JAR_OF_WATER.value = 48;
		Templates.JAR_OF_WATER.anchorX = 56;
		Templates.JAR_OF_WATER.anchorY = 100;
		Templates.JAR_OF_WATER.templateId = GameObject.generateNewTemplateId();
		Templates.JAR_OF_WATER.volume = 1;
		Templates.JAR_OF_WATER.liquid = Templates.WATER.makeCopy(null, null, 1);
	}

}
