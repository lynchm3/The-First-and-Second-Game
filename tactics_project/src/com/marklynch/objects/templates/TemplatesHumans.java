package com.marklynch.objects.templates;

import com.marklynch.ai.routines.AIRoutineForHunter;
import com.marklynch.ai.routines.AIRoutineForThief;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Human;
import com.marklynch.objects.units.Player;

public class TemplatesHumans {

	// \t\tpublic\sstatic\sfinal\s[a-zA-Z]+\s

	public TemplatesHumans() {

		// Player
		Templates.PLAYER = new Player();
		Templates.PLAYER.title = "Fighter";
		Templates.PLAYER.actorLevel = 1;
		Templates.PLAYER.totalHealth = Templates.PLAYER.remainingHealth = 100;
		Templates.PLAYER.strength = 10;
		Templates.PLAYER.dexterity = 10;
		Templates.PLAYER.intelligence = 10;
		Templates.PLAYER.endurance = 10;
		Templates.PLAYER.imageTexturePath = "hero.png";
		Templates.PLAYER.heightRatio = 1.5f;
		Templates.PLAYER.drawOffsetY = -0.5f;
		Templates.PLAYER.weight = 90f;
		Templates.PLAYER.handAnchorX = 75f;
		Templates.PLAYER.handAnchorY = 127f;
		Templates.PLAYER.headAnchorX = 70f;
		Templates.PLAYER.headAnchorY = 23f;
		Templates.PLAYER.canOpenDoors = true;
		Templates.PLAYER.canEquipWeapons = true;
		Templates.PLAYER.templateId = GameObject.generateNewTemplateId();

		// General People
		Templates.HUNTER = new Human();
		Templates.HUNTER.title = "Hunter";
		Templates.HUNTER.actorLevel = 1;
		Templates.HUNTER.totalHealth = Templates.HUNTER.remainingHealth = 10;
		Templates.HUNTER.strength = 10;
		Templates.HUNTER.dexterity = 10;
		Templates.HUNTER.intelligence = 10;
		Templates.HUNTER.endurance = 10;
		Templates.HUNTER.imageTexturePath = "hunter.png";
		Templates.HUNTER.weight = 90f;
		Templates.HUNTER.handAnchorX = 88f;
		Templates.HUNTER.handAnchorY = 54f;
		Templates.HUNTER.canOpenDoors = true;
		Templates.HUNTER.canEquipWeapons = true;
		Templates.HUNTER.aiRoutine = new AIRoutineForHunter(Templates.HUNTER);
		Templates.HUNTER.templateId = GameObject.generateNewTemplateId();

		Templates.THIEF = new Human();
		Templates.THIEF.title = "Thief";
		Templates.THIEF.actorLevel = 1;
		Templates.THIEF.totalHealth = Templates.THIEF.remainingHealth = 10;
		Templates.THIEF.strength = 10;
		Templates.THIEF.dexterity = 10;
		Templates.THIEF.intelligence = 10;
		Templates.THIEF.endurance = 10;
		Templates.THIEF.imageTexturePath = "thief.png";
		Templates.THIEF.weight = 90f;
		Templates.THIEF.handAnchorX = 88f;
		Templates.THIEF.handAnchorY = 54f;
		Templates.THIEF.canOpenDoors = true;
		Templates.THIEF.canEquipWeapons = true;
		Templates.THIEF.aiRoutine = new AIRoutineForThief(Templates.THIEF);
		Templates.THIEF.templateId = GameObject.generateNewTemplateId();

		Templates.FARMER = new Human();
		Templates.FARMER.title = "Farmer";
		Templates.FARMER.actorLevel = 1;
		Templates.FARMER.totalHealth = Templates.FARMER.remainingHealth = 10;
		Templates.FARMER.strength = 10;
		Templates.FARMER.dexterity = 10;
		Templates.FARMER.intelligence = 10;
		Templates.FARMER.endurance = 10;
		Templates.FARMER.imageTexturePath = "farmer.png";
		Templates.FARMER.weight = 90f;
		Templates.FARMER.handAnchorX = 88f;
		Templates.FARMER.handAnchorY = 54f;
		Templates.FARMER.canOpenDoors = true;
		Templates.FARMER.canEquipWeapons = true;
		Templates.FARMER.aiRoutine = new AIRoutineForHunter(Templates.FARMER);
		Templates.FARMER.templateId = GameObject.generateNewTemplateId();
	}

}
