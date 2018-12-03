package com.marklynch.objects.templates;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.weapons.Weapon;

public class TemplatesWeapons {

	// \t\tpublic\sstatic\sfinal\s[a-zA-Z]+\s

	public TemplatesWeapons() {

		// Weapons
		Templates.KATANA = new Weapon();
		Templates.KATANA.name = "Katana";
		Templates.KATANA.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(11));
		Templates.KATANA.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, new Stat(3));
		Templates.KATANA.setImageAndExtrapolateSize("katana.png");
		Templates.KATANA.totalHealth = Templates.KATANA.remainingHealth = 22;
		Templates.KATANA.soundWhenHitting = 5f;
		Templates.KATANA.weight = 15;
		Templates.KATANA.value = 69;
		Templates.KATANA.anchorX = 27;
		Templates.KATANA.anchorY = 85;
		Templates.KATANA.templateId = GameObject.generateNewTemplateId();

		Templates.SWORD = new Weapon();
		Templates.SWORD.name = "Sword";
		Templates.SWORD.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(9));
		Templates.SWORD.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, new Stat(3));
		Templates.SWORD.setImageAndExtrapolateSize("sword.png");
		Templates.SWORD.totalHealth = Templates.SWORD.remainingHealth = 29;
		Templates.SWORD.soundWhenHitting = 5f;
		Templates.SWORD.weight = 25;
		Templates.SWORD.value = 56;
		Templates.SWORD.anchorX = 24;
		Templates.SWORD.anchorY = 101;
		Templates.SWORD.templateId = GameObject.generateNewTemplateId();

		Templates.CLEAVER = new Weapon();
		Templates.CLEAVER.name = "Cleaver";
		Templates.CLEAVER.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(7));
		Templates.CLEAVER.setImageAndExtrapolateSize("cleaver.png");
		Templates.CLEAVER.totalHealth = Templates.CLEAVER.remainingHealth = 20;
		Templates.CLEAVER.weight = 17f;
		Templates.CLEAVER.value = 16;
		Templates.CLEAVER.anchorX = 33;
		Templates.CLEAVER.anchorY = 104;
		Templates.CLEAVER.templateId = GameObject.generateNewTemplateId();

		Templates.HUNTING_BOW = new Weapon();
		Templates.HUNTING_BOW.name = "Hunting Bow";
		Templates.HUNTING_BOW.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, new Stat(5));
		Templates.HUNTING_BOW.maxRange = 9;
		Templates.HUNTING_BOW.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.HUNTING_BOW.setImageAndExtrapolateSize("a2r2.png");
		Templates.HUNTING_BOW.totalHealth = Templates.HUNTING_BOW.remainingHealth = 20;
		Templates.HUNTING_BOW.soundWhenHitting = 5f;
		Templates.HUNTING_BOW.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(-70));
		Templates.HUNTING_BOW.weight = 26f;
		Templates.HUNTING_BOW.value = 54;
		Templates.HUNTING_BOW.anchorX = 64;
		Templates.HUNTING_BOW.anchorY = 112;
		Templates.HUNTING_BOW.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(1));
		Templates.HUNTING_BOW.templateId = GameObject.generateNewTemplateId();
	}

}
