package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.armor.Weapon;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Matches;
import com.marklynch.objects.tools.Axe;
import com.marklynch.objects.tools.Bell;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.tools.FishingRod;
import com.marklynch.objects.tools.Knife;
import com.marklynch.objects.tools.Lantern;
import com.marklynch.objects.tools.Pickaxe;
import com.marklynch.objects.tools.Shovel;

public class TemplatesTools {

	// \t\tpublic\sstatic\sfinal\s[a-zA-Z]+\s

	public TemplatesTools() {

		// Tools
		Templates.BROOM = new Weapon();
		Templates.BROOM.name = "Broom";
		Templates.BROOM.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, 1));
		Templates.BROOM.setImageAndExtrapolateSize("broom.png");
		Templates.BROOM.totalHealth = Templates.BROOM.remainingHealth = 20;
		Templates.BROOM.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(HIGH_LEVEL_STATS.FIRE_RES, -50));
		Templates.BROOM.weight = 15;
		Templates.BROOM.value = 12;
		Templates.BROOM.anchorX = 55;
		Templates.BROOM.anchorY = 88;
		Templates.BROOM.templateId = GameObject.generateNewTemplateId();

		Templates.PICKAXE = new Pickaxe();
		Templates.PICKAXE.name = "Pickaxe";
		Templates.PICKAXE.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 7));
		Templates.PICKAXE.setImageAndExtrapolateSize("pickaxe.png");
		Templates.PICKAXE.totalHealth = Templates.PICKAXE.remainingHealth = 20;
		Templates.PICKAXE.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(HIGH_LEVEL_STATS.FIRE_RES, -50));
		Templates.PICKAXE.weight = 25f;
		Templates.PICKAXE.value = 29;
		Templates.PICKAXE.anchorX = 32;
		Templates.PICKAXE.anchorY = 94;
		Templates.PICKAXE.templateId = GameObject.generateNewTemplateId();

		Templates.SHOVEL = new Shovel();
		Templates.SHOVEL.name = "Shovel";
		Templates.SHOVEL.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 6));
		Templates.SHOVEL.setImageAndExtrapolateSize("shovel.png");
		Templates.SHOVEL.totalHealth = Templates.SHOVEL.remainingHealth = 27;
		Templates.SHOVEL.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(HIGH_LEVEL_STATS.FIRE_RES, -50));
		Templates.SHOVEL.weight = 28f;
		Templates.SHOVEL.value = 32;
		Templates.SHOVEL.anchorX = 33;
		Templates.SHOVEL.anchorY = 68;
		Templates.SHOVEL.templateId = GameObject.generateNewTemplateId();

		Templates.FISHING_ROD = new FishingRod(); // 93,0
		Templates.FISHING_ROD.name = "Fishing Rod";
		Templates.FISHING_ROD.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 4));
		Templates.FISHING_ROD.fishingRange = 10;
		Templates.FISHING_ROD.lineAnchorX = 93;
		Templates.FISHING_ROD.lineAnchorY = 0;
		Templates.FISHING_ROD.setImageAndExtrapolateSize("fishing_rod.png");
		Templates.FISHING_ROD.totalHealth = Templates.FISHING_ROD.remainingHealth = 16;
		Templates.FISHING_ROD.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(HIGH_LEVEL_STATS.FIRE_RES, -60));
		Templates.FISHING_ROD.weight = 23f;
		Templates.FISHING_ROD.value = 31;
		Templates.FISHING_ROD.anchorX = 55;
		Templates.FISHING_ROD.anchorY = 89;
		Templates.FISHING_ROD.templateId = GameObject.generateNewTemplateId();

		Templates.HATCHET = new Axe();
		Templates.HATCHET.name = "Hatchet";
		Templates.HATCHET.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 6));
		Templates.HATCHET.setImageAndExtrapolateSize("hatchet.png");
		Templates.HATCHET.totalHealth = Templates.HATCHET.remainingHealth = 20;
		Templates.HATCHET.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(HIGH_LEVEL_STATS.FIRE_RES, -50));
		Templates.HATCHET.weight = 22f;
		Templates.HATCHET.value = 32;
		Templates.HATCHET.anchorX = 48;
		Templates.HATCHET.anchorY = 89;
		Templates.HATCHET.templateId = GameObject.generateNewTemplateId();

		Templates.HUNTING_KNIFE = new Knife();
		Templates.HUNTING_KNIFE.name = "Hunting Knife";
		Templates.HUNTING_KNIFE.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 9));
		Templates.HUNTING_KNIFE.setImageAndExtrapolateSize("hunting_knife.png");
		Templates.HUNTING_KNIFE.totalHealth = Templates.HUNTING_KNIFE.remainingHealth = 25;
		Templates.HUNTING_KNIFE.weight = 12f;
		Templates.HUNTING_KNIFE.value = 37;
		Templates.HUNTING_KNIFE.anchorX = 47;
		Templates.HUNTING_KNIFE.anchorY = 58;
		Templates.HUNTING_KNIFE.templateId = GameObject.generateNewTemplateId();

		Templates.HOE = new Weapon();
		Templates.HOE.name = "Hoe";
		Templates.HOE.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 3));
		Templates.HOE.setImageAndExtrapolateSize("hoe.png");
		Templates.HOE.totalHealth = Templates.HOE.remainingHealth = 24;
		Templates.HOE.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(HIGH_LEVEL_STATS.FIRE_RES, -50));
		Templates.HOE.weight = 31f;
		Templates.HOE.value = 17;
		Templates.HOE.anchorX = 61;
		Templates.HOE.anchorY = 90;
		Templates.HOE.templateId = GameObject.generateNewTemplateId();

		Templates.SICKLE = new Weapon();
		Templates.SICKLE.name = "Sickle";
		Templates.SICKLE.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 6));
		Templates.SICKLE.setImageAndExtrapolateSize("sickle.png");
		Templates.SICKLE.totalHealth = Templates.SICKLE.remainingHealth = 22;
		Templates.SICKLE.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(HIGH_LEVEL_STATS.FIRE_RES, -50));
		Templates.SICKLE.weight = 21f;
		Templates.SICKLE.value = 22;
		Templates.SICKLE.anchorX = 20;
		Templates.SICKLE.anchorY = 29;
		Templates.SICKLE.templateId = GameObject.generateNewTemplateId();

		Templates.HAMMER = new Weapon();
		Templates.HAMMER.name = "Hammer";
		Templates.HAMMER.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, 5));
		Templates.HAMMER.setImageAndExtrapolateSize("hammer.png");
		Templates.HAMMER.totalHealth = Templates.HAMMER.remainingHealth = 36;
		Templates.HAMMER.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(HIGH_LEVEL_STATS.FIRE_RES, -50));
		Templates.HAMMER.weight = 23f;
		Templates.HAMMER.value = 41;
		Templates.HAMMER.anchorX = 10;
		Templates.HAMMER.anchorY = 54;
		Templates.HAMMER.templateId = GameObject.generateNewTemplateId();

		Templates.BASKET = new Weapon();
		Templates.BASKET.name = "Basket";
		Templates.BASKET.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, 1));
		Templates.BASKET.setImageAndExtrapolateSize("basket.png");
		Templates.BASKET.totalHealth = Templates.BASKET.remainingHealth = 14;
		Templates.BASKET.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(HIGH_LEVEL_STATS.FIRE_RES, -80));
		Templates.BASKET.weight = 9f;
		Templates.BASKET.value = 13;
		Templates.BASKET.anchorX = 20;
		Templates.BASKET.anchorY = 16;
		Templates.BASKET.templateId = GameObject.generateNewTemplateId();
		Templates.BASKET.flipYAxisInMirror = false;

		Templates.WHIP = new Weapon();
		Templates.WHIP.name = "Whip";
		Templates.WHIP.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 9));
		Templates.WHIP.setImageAndExtrapolateSize("whip.png");
		Templates.WHIP.totalHealth = Templates.WHIP.remainingHealth = 19;
		Templates.WHIP.weight = 12f;
		Templates.WHIP.value = 54;
		Templates.WHIP.anchorX = 18;
		Templates.WHIP.anchorY = 80;
		Templates.WHIP.templateId = GameObject.generateNewTemplateId();

		Templates.SERRATED_SPOON = new Weapon();
		Templates.SERRATED_SPOON.name = "Serrated Spoon";
		Templates.SERRATED_SPOON.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 2));
		Templates.SERRATED_SPOON.setImageAndExtrapolateSize("serrated_spoon.png");
		Templates.SERRATED_SPOON.totalHealth = Templates.SERRATED_SPOON.remainingHealth = 23;
		Templates.SERRATED_SPOON.weight = 7f;
		Templates.SERRATED_SPOON.value = 11;
		Templates.SERRATED_SPOON.anchorX = 48;
		Templates.SERRATED_SPOON.anchorY = 113;
		Templates.SERRATED_SPOON.templateId = GameObject.generateNewTemplateId();

		Templates.DINNER_BELL = new Bell();
		Templates.DINNER_BELL.name = "Dinner Bell";
		Templates.DINNER_BELL.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, 2));
		Templates.DINNER_BELL.setImageAndExtrapolateSize("bell.png");
		Templates.DINNER_BELL.totalHealth = Templates.DINNER_BELL.remainingHealth = 25;
		Templates.DINNER_BELL.weight = 8f;
		Templates.DINNER_BELL.value = 34;
		Templates.DINNER_BELL.anchorX = 53;
		Templates.DINNER_BELL.anchorY = 103;
		Templates.DINNER_BELL.templateId = GameObject.generateNewTemplateId();

		Templates.MATCHES = new Matches();
		Templates.MATCHES.name = "Matches";
		Templates.MATCHES.setImageAndExtrapolateSize("matches.png");
		Templates.MATCHES.totalHealth = Templates.MATCHES.remainingHealth = 16;
		Templates.MATCHES.weight = 6f;
		Templates.MATCHES.value = 24;
		Templates.MATCHES.anchorX = 6;
		Templates.MATCHES.anchorY = 20;
		Templates.MATCHES.templateId = GameObject.generateNewTemplateId();

		Templates.LANTERN = new Lantern();
		Templates.LANTERN.name = "Lantern";
		Templates.LANTERN.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, 1));
		Templates.LANTERN.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_DAMAGE, new Stat(HIGH_LEVEL_STATS.FIRE_DAMAGE, 1));
		Templates.LANTERN.setImageAndExtrapolateSize("lantern_lit.png");
		Templates.LANTERN.imageTextureLit = getGlobalImage("lantern_lit.png", true);
		Templates.LANTERN.imageTextureUnlit = getGlobalImage("lantern_unlit.png", true);
		Templates.LANTERN.totalHealth = Templates.LANTERN.remainingHealth = 14;
		Templates.LANTERN.weight = 10f;
		Templates.LANTERN.value = 43;
		Templates.LANTERN.anchorX = 32;
		Templates.LANTERN.anchorY = 7;
		Templates.LANTERN.templateId = GameObject.generateNewTemplateId();
		Templates.LANTERN.flipYAxisInMirror = false;
		Templates.LANTERN.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(HIGH_LEVEL_STATS.FIRE_RES, 100));

		Templates.JAR = new ContainerForLiquids();
		Templates.JAR.name = "Empty Jar";
		Templates.JAR.description = "Very good at containing liquids, also fairies. Surprisingly rare, so probably don't lose them.";
		Templates.JAR.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 6));
		Templates.JAR.setImageAndExtrapolateSize("jar.png");
		Templates.JAR.totalHealth = Templates.JAR.remainingHealth = 14;
		Templates.JAR.weight = 10f;
		Templates.JAR.value = 42;
		Templates.JAR.anchorX = 56;
		Templates.JAR.anchorY = 100;
		Templates.JAR.templateId = GameObject.generateNewTemplateId();
		Templates.JAR.volume = 1;
		Templates.JAR.liquid = null;
		Templates.JAR.flipYAxisInMirror = false;

		Templates.JAR_OF_WATER = new ContainerForLiquids();
		Templates.JAR_OF_WATER.name = "Jar of Water";
		Templates.JAR_OF_WATER.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 6));
		Templates.JAR_OF_WATER.setImageAndExtrapolateSize("jar_of_water.png");
		Templates.JAR_OF_WATER.totalHealth = Templates.JAR_OF_WATER.remainingHealth = 14;
		Templates.JAR_OF_WATER.weight = 10f;
		Templates.JAR_OF_WATER.value = 48;
		Templates.JAR_OF_WATER.anchorX = 56;
		Templates.JAR_OF_WATER.anchorY = 100;
		Templates.JAR_OF_WATER.templateId = GameObject.generateNewTemplateId();
		Templates.JAR_OF_WATER.volume = 1;
		Templates.WATER.jarForm = Templates.JAR_OF_WATER;
		Templates.JAR_OF_WATER.liquid = Templates.WATER.makeCopy(null, null, 1);
		Templates.JAR_OF_WATER.flipYAxisInMirror = false;

		Templates.JAR_OF_POISON = new ContainerForLiquids();
		Templates.JAR_OF_POISON.name = "Jar of Poison";
		Templates.JAR_OF_POISON.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 6));
		Templates.JAR_OF_POISON.highLevelStats.put(HIGH_LEVEL_STATS.POISON_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.POISON_DAMAGE, 6));
		Templates.JAR_OF_POISON.setImageAndExtrapolateSize("jar_of_poison.png");
		Templates.JAR_OF_POISON.totalHealth = Templates.JAR_OF_POISON.remainingHealth = 14;
		Templates.JAR_OF_POISON.weight = 10f;
		Templates.JAR_OF_POISON.value = 78;
		Templates.JAR_OF_POISON.anchorX = 56;
		Templates.JAR_OF_POISON.anchorY = 100;
		Templates.JAR_OF_POISON.templateId = GameObject.generateNewTemplateId();
		Templates.JAR_OF_POISON.volume = 1;
		Templates.POISON.jarForm = Templates.JAR_OF_POISON;
		Templates.JAR_OF_POISON.liquid = Templates.POISON.makeCopy(null, null, 1);
		Templates.JAR_OF_POISON.flipYAxisInMirror = false;

		Templates.JAR_OF_OIL = new ContainerForLiquids();
		Templates.JAR_OF_OIL.name = "Jar of Oil";
		Templates.JAR_OF_OIL.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 6));
		Templates.JAR_OF_OIL.highLevelStats.put(HIGH_LEVEL_STATS.POISON_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.POISON_DAMAGE, 6));
		Templates.JAR_OF_OIL.setImageAndExtrapolateSize("jar_of_oil.png");
		Templates.JAR_OF_OIL.totalHealth = Templates.JAR_OF_OIL.remainingHealth = 14;
		Templates.JAR_OF_OIL.weight = 10f;
		Templates.JAR_OF_OIL.value = 78;
		Templates.JAR_OF_OIL.anchorX = 56;
		Templates.JAR_OF_OIL.anchorY = 100;
		Templates.JAR_OF_OIL.templateId = GameObject.generateNewTemplateId();
		Templates.JAR_OF_OIL.volume = 1;
		Templates.OIL.jarForm = Templates.JAR_OF_OIL;
		Templates.JAR_OF_OIL.liquid = Templates.OIL.makeCopy(null, null, 1);
		Templates.JAR_OF_OIL.flipYAxisInMirror = false;

		Templates.JAR_OF_BLOOD = new ContainerForLiquids();
		Templates.JAR_OF_BLOOD.name = "Jar of Blood";
		Templates.JAR_OF_BLOOD.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 6));
		Templates.JAR_OF_BLOOD.setImageAndExtrapolateSize("jar_of_blood.png");
		Templates.JAR_OF_BLOOD.totalHealth = Templates.JAR_OF_BLOOD.remainingHealth = 14;
		Templates.JAR_OF_BLOOD.weight = 10f;
		Templates.JAR_OF_BLOOD.value = 78;
		Templates.JAR_OF_BLOOD.anchorX = 56;
		Templates.JAR_OF_BLOOD.anchorY = 100;
		Templates.JAR_OF_BLOOD.templateId = GameObject.generateNewTemplateId();
		Templates.JAR_OF_BLOOD.volume = 1;
		Templates.BLOOD.jarForm = Templates.JAR_OF_BLOOD;
		Templates.JAR_OF_BLOOD.liquid = Templates.BLOOD.makeCopy(null, null, 1);
		Templates.JAR_OF_BLOOD.flipYAxisInMirror = false;

		Templates.JAR_OF_LAVA = new ContainerForLiquids();
		Templates.JAR_OF_LAVA.name = "Jar of Lava";
		Templates.JAR_OF_LAVA.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 6));
		Templates.JAR_OF_LAVA.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.FIRE_DAMAGE, 20));
		Templates.JAR_OF_LAVA.setImageAndExtrapolateSize("jar_of_lava.png");
		Templates.JAR_OF_LAVA.totalHealth = Templates.JAR_OF_BLOOD.remainingHealth = 14;
		Templates.JAR_OF_LAVA.weight = 10f;
		Templates.JAR_OF_LAVA.value = 78;
		Templates.JAR_OF_LAVA.anchorX = 56;
		Templates.JAR_OF_LAVA.anchorY = 100;
		Templates.JAR_OF_LAVA.templateId = GameObject.generateNewTemplateId();
		Templates.JAR_OF_LAVA.volume = 1;
		Templates.LAVA.jarForm = Templates.JAR_OF_LAVA;
		Templates.JAR_OF_LAVA.liquid = Templates.LAVA.makeCopy(null, null, 1);
		Templates.JAR_OF_LAVA.flipYAxisInMirror = false;

		Templates.JAR_OF_SOUP = new ContainerForLiquids();
		Templates.JAR_OF_SOUP.name = "Jar of Soup";
		Templates.JAR_OF_SOUP.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.SLASH_DAMAGE, 6));
		Templates.JAR_OF_SOUP.setImageAndExtrapolateSize("jar_of_soup.png");
		Templates.JAR_OF_SOUP.totalHealth = Templates.JAR_OF_BLOOD.remainingHealth = 14;
		Templates.JAR_OF_SOUP.weight = 10f;
		Templates.JAR_OF_SOUP.value = 78;
		Templates.JAR_OF_SOUP.anchorX = 56;
		Templates.JAR_OF_SOUP.anchorY = 100;
		Templates.JAR_OF_SOUP.templateId = GameObject.generateNewTemplateId();
		Templates.JAR_OF_SOUP.volume = 1;
		Templates.SOUP.jarForm = Templates.JAR_OF_SOUP;
		Templates.JAR_OF_SOUP.liquid = Templates.SOUP.makeCopy(null, null, 1);
		Templates.JAR_OF_SOUP.flipYAxisInMirror = false;

	}

}
