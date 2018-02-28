package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.ai.routines.AIRoutineForHunter;
import com.marklynch.ai.routines.AIRoutineForMiner;
import com.marklynch.ai.routines.AIRoutineForThief;
import com.marklynch.ai.routines.AIRoutineForTrader;
import com.marklynch.level.quest.caveoftheblind.AIRoutineForMort;
import com.marklynch.level.quest.caveoftheblind.Mort;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Human;
import com.marklynch.objects.units.Player;
import com.marklynch.objects.units.Trader;

public class TemplatesHumans {

	// \t\tpublic\sstatic\sfinal\s[a-zA-Z]+\s

	public TemplatesHumans() {

		// Player
		Templates.PLAYER = new Player();
		Templates.PLAYER.title = "Fighter";
		Templates.PLAYER.level = 1;
		Templates.PLAYER.totalHealth = Templates.PLAYER.remainingHealth = 1000;
		Templates.PLAYER.strength = 10;
		Templates.PLAYER.dexterity = 10;
		Templates.PLAYER.intelligence = 10;
		Templates.PLAYER.endurance = 10;
		Templates.PLAYER.bluntDamage = 3;
		Templates.PLAYER.imageTexturePath = "hero.png";
		Templates.PLAYER.imageTexture = getGlobalImage(Templates.PLAYER.imageTexturePath, true);
		Templates.PLAYER.heightRatio = 1.5f;
		Templates.PLAYER.drawOffsetRatioY = -0.5f;
		Templates.PLAYER.weight = 90f;
		Templates.PLAYER.handAnchorX = 75f;
		Templates.PLAYER.handAnchorY = 127f;
		Templates.PLAYER.headAnchorX = 70f;
		Templates.PLAYER.headAnchorY = 23f;
		Templates.PLAYER.bodyAnchorX = 46f;
		Templates.PLAYER.bodyAnchorY = 44f;
		Templates.PLAYER.legsAnchorX = 32f;
		Templates.PLAYER.legsAnchorY = 64f;
		Templates.PLAYER.canOpenDoors = true;
		Templates.PLAYER.canEquipWeapons = true;
		Templates.PLAYER.templateId = GameObject.generateNewTemplateId();
		Templates.PLAYER.flipYAxisInMirror = false;

		// General People
		Templates.HUNTER = new Human();
		Templates.HUNTER.title = "Hunter";
		Templates.HUNTER.level = 1;
		Templates.HUNTER.bluntDamage = 3;
		Templates.HUNTER.totalHealth = Templates.HUNTER.remainingHealth = 10;
		Templates.HUNTER.strength = 10;
		Templates.HUNTER.dexterity = 10;
		Templates.HUNTER.intelligence = 10;
		Templates.HUNTER.endurance = 10;
		Templates.HUNTER.imageTexturePath = "hunter.png";
		Templates.HUNTER.imageTexture = getGlobalImage(Templates.HUNTER.imageTexturePath, true);
		Templates.HUNTER.weight = 90f;
		Templates.HUNTER.handAnchorX = 88f;
		Templates.HUNTER.handAnchorY = 54f;
		Templates.HUNTER.canOpenDoors = true;
		Templates.HUNTER.canEquipWeapons = true;
		Templates.HUNTER.aiRoutine = new AIRoutineForHunter(Templates.HUNTER);
		Templates.HUNTER.templateId = GameObject.generateNewTemplateId();
		Templates.HUNTER.flipYAxisInMirror = false;

		// General People
		Templates.MINER = new Human();
		Templates.MINER.title = "Miner";
		Templates.MINER.level = 1;
		Templates.MINER.bluntDamage = 2;
		Templates.MINER.totalHealth = Templates.MINER.remainingHealth = 10;
		Templates.MINER.strength = 13;
		Templates.MINER.dexterity = 5;
		Templates.MINER.intelligence = 4;
		Templates.MINER.endurance = 12;
		Templates.MINER.imageTexturePath = "hunter.png";
		Templates.MINER.imageTexture = getGlobalImage(Templates.MINER.imageTexturePath, true);
		Templates.MINER.weight = 110f;
		Templates.MINER.handAnchorX = 88f;
		Templates.MINER.handAnchorY = 54f;
		Templates.MINER.canOpenDoors = true;
		Templates.MINER.canEquipWeapons = true;
		Templates.MINER.aiRoutine = new AIRoutineForMiner(Templates.MINER);
		Templates.MINER.templateId = GameObject.generateNewTemplateId();
		Templates.MINER.flipYAxisInMirror = false;

		Templates.THIEF = new Human();
		Templates.THIEF.title = "Thief";
		Templates.THIEF.level = 1;
		Templates.THIEF.bluntDamage = 3;
		Templates.THIEF.totalHealth = Templates.THIEF.remainingHealth = 10;
		Templates.THIEF.strength = 10;
		Templates.THIEF.dexterity = 10;
		Templates.THIEF.intelligence = 10;
		Templates.THIEF.endurance = 10;
		Templates.THIEF.imageTexturePath = "thief.png";
		Templates.THIEF.imageTexture = getGlobalImage(Templates.THIEF.imageTexturePath, true);
		Templates.THIEF.weight = 90f;
		Templates.THIEF.handAnchorX = 88f;
		Templates.THIEF.handAnchorY = 54f;
		Templates.THIEF.canOpenDoors = true;
		Templates.THIEF.canEquipWeapons = true;
		Templates.THIEF.aiRoutine = new AIRoutineForThief(Templates.THIEF);
		Templates.THIEF.templateId = GameObject.generateNewTemplateId();
		Templates.THIEF.flipYAxisInMirror = false;

		Templates.FARMER = new Human();
		Templates.FARMER.title = "Farmer";
		Templates.FARMER.level = 1;
		Templates.FARMER.bluntDamage = 3;
		Templates.FARMER.totalHealth = Templates.FARMER.remainingHealth = 10;
		Templates.FARMER.strength = 10;
		Templates.FARMER.dexterity = 10;
		Templates.FARMER.intelligence = 10;
		Templates.FARMER.endurance = 10;
		Templates.FARMER.imageTexturePath = "farmer.png";
		Templates.FARMER.imageTexture = getGlobalImage(Templates.FARMER.imageTexturePath, true);
		Templates.FARMER.weight = 90f;
		Templates.FARMER.handAnchorX = 88f;
		Templates.FARMER.handAnchorY = 54f;
		Templates.FARMER.canOpenDoors = true;
		Templates.FARMER.canEquipWeapons = true;
		Templates.FARMER.aiRoutine = new AIRoutineForHunter(Templates.FARMER);
		Templates.FARMER.templateId = GameObject.generateNewTemplateId();
		Templates.FARMER.flipYAxisInMirror = false;

		// Special People
		Templates.MORT = new Mort();
		Templates.MORT.title = "Mort";
		Templates.MORT.level = 1;
		Templates.MORT.bluntDamage = 2;
		Templates.MORT.totalHealth = Templates.MORT.remainingHealth = 10;
		Templates.MORT.strength = 10;
		Templates.MORT.dexterity = 10;
		Templates.MORT.intelligence = 10;
		Templates.MORT.endurance = 10;
		Templates.MORT.imageTexturePath = "farmer.png";
		Templates.MORT.imageTexture = getGlobalImage(Templates.MORT.imageTexturePath, true);
		Templates.MORT.weight = 90f;
		Templates.MORT.handAnchorX = 88f;
		Templates.MORT.handAnchorY = 54f;
		Templates.MORT.canOpenDoors = true;
		Templates.MORT.canEquipWeapons = true;
		Templates.MORT.aiRoutine = new AIRoutineForMort(Templates.MORT);
		Templates.MORT.templateId = GameObject.generateNewTemplateId();
		Templates.MORT.flipYAxisInMirror = false;

		Templates.TRADER = new Trader();
		Templates.TRADER.title = "Trader";
		Templates.TRADER.level = 1;
		Templates.TRADER.bluntDamage = 2;
		Templates.TRADER.totalHealth = Templates.TRADER.remainingHealth = 10;
		Templates.TRADER.strength = 10;
		Templates.TRADER.dexterity = 10;
		Templates.TRADER.intelligence = 10;
		Templates.TRADER.endurance = 10;
		Templates.TRADER.imageTexturePath = "shopkeeper.png";
		Templates.TRADER.imageTexture = getGlobalImage(Templates.TRADER.imageTexturePath, true);
		Templates.TRADER.weight = 90f;
		Templates.TRADER.handAnchorX = 40f;
		Templates.TRADER.handAnchorY = 96f;
		Templates.TRADER.canOpenDoors = true;
		Templates.TRADER.canEquipWeapons = true;
		Templates.TRADER.aiRoutine = new AIRoutineForTrader(Templates.TRADER);
		Templates.TRADER.templateId = GameObject.generateNewTemplateId();
		Templates.TRADER.flipYAxisInMirror = false;
	}

}
