package com.marklynch.objects.templates;

import com.marklynch.ai.routines.AIRoutineForCarnivoreNeutralWildAnimal;
import com.marklynch.ai.routines.AIRoutineForHerbivoreWildAnimal;
import com.marklynch.ai.routines.AIRoutineForPig;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.CarnivoreNeutralWildAnimal;
import com.marklynch.objects.units.HerbivoreWildAnimal;
import com.marklynch.objects.units.Pig;
import com.marklynch.objects.units.TinyNeutralWildAnimal;

public class TemplatesAnimals {

	// \t\tpublic\sstatic\sfinal\s[a-zA-Z]+\s

	public TemplatesAnimals() {

		Templates.PIG = new Pig();
		Templates.PIG.title = "Pig";
		Templates.PIG.actorLevel = 1;
		Templates.PIG.totalHealth = Templates.PIG.remainingHealth = 100;
		Templates.PIG.strength = 10;
		Templates.PIG.dexterity = 10;
		Templates.PIG.intelligence = 10;
		Templates.PIG.endurance = 10;
		Templates.PIG.imageTexturePath = "pig.png";
		Templates.PIG.heightRatio = 0.25f;
		Templates.PIG.drawOffsetY = 0.75f;
		Templates.PIG.weight = 70f;
		Templates.PIG.canOpenDoors = false;
		Templates.PIG.canEquipWeapons = false;
		Templates.PIG.templateId = GameObject.generateNewTemplateId();
		Templates.PIG.aiRoutine = new AIRoutineForPig(Templates.PIG);

		// Friendly Wild animals
		Templates.RAT = new TinyNeutralWildAnimal();
		Templates.RAT.title = "Rat";
		Templates.RAT.actorLevel = 1;
		Templates.RAT.totalHealth = Templates.RAT.remainingHealth = 100;
		Templates.RAT.strength = 10;
		Templates.RAT.dexterity = 10;
		Templates.RAT.intelligence = 10;
		Templates.RAT.endurance = 10;
		Templates.RAT.imageTexturePath = "rat.png";
		Templates.RAT.heightRatio = 0.25f;
		Templates.RAT.drawOffsetY = 0.75f;
		Templates.RAT.weight = 70f;
		Templates.RAT.canOpenDoors = false;
		Templates.RAT.canEquipWeapons = false;
		Templates.RAT.templateId = GameObject.generateNewTemplateId();
		Templates.RAT.aiRoutine = new AIRoutineForHerbivoreWildAnimal(Templates.RAT);
		Templates.RAT.canBePickedUp = true;
		Templates.RAT.fitsInInventory = true;

		Templates.RABBIT = new HerbivoreWildAnimal();
		Templates.RABBIT.title = "Rabbit";
		Templates.RABBIT.actorLevel = 1;
		Templates.RABBIT.totalHealth = Templates.RABBIT.remainingHealth = 100;
		Templates.RABBIT.strength = 10;
		Templates.RABBIT.dexterity = 10;
		Templates.RABBIT.intelligence = 10;
		Templates.RABBIT.endurance = 10;
		Templates.RABBIT.imageTexturePath = "rabbit.png";
		Templates.RABBIT.widthRatio = 0.5f;
		Templates.RABBIT.heightRatio = 0.5f;
		Templates.RABBIT.weight = 10f;
		Templates.RABBIT.canOpenDoors = false;
		Templates.RABBIT.canEquipWeapons = false;
		Templates.RABBIT.templateId = GameObject.generateNewTemplateId();
		Templates.RABBIT.aiRoutine = new AIRoutineForHerbivoreWildAnimal(Templates.RABBIT);

		Templates.BABY_RABBIT = new TinyNeutralWildAnimal();
		Templates.BABY_RABBIT.title = "Baby Rabbit";
		Templates.BABY_RABBIT.actorLevel = 1;
		Templates.BABY_RABBIT.totalHealth = Templates.BABY_RABBIT.remainingHealth = 100;
		Templates.BABY_RABBIT.strength = 10;
		Templates.BABY_RABBIT.dexterity = 10;
		Templates.BABY_RABBIT.intelligence = 10;
		Templates.BABY_RABBIT.endurance = 10;
		Templates.BABY_RABBIT.imageTexturePath = "baby_rabbit.png";
		Templates.BABY_RABBIT.widthRatio = 0.25f;
		Templates.BABY_RABBIT.heightRatio = 0.25f;
		Templates.BABY_RABBIT.weight = 10f;
		Templates.BABY_RABBIT.canOpenDoors = false;
		Templates.BABY_RABBIT.canEquipWeapons = false;
		Templates.BABY_RABBIT.templateId = GameObject.generateNewTemplateId();
		Templates.BABY_RABBIT.aiRoutine = new AIRoutineForHerbivoreWildAnimal(Templates.BABY_RABBIT);
		Templates.BABY_RABBIT.canBePickedUp = true;
		Templates.BABY_RABBIT.fitsInInventory = true;

		Templates.FOX = new CarnivoreNeutralWildAnimal();
		Templates.FOX.title = "Fox";
		Templates.FOX.actorLevel = 1;
		Templates.FOX.totalHealth = Templates.FOX.remainingHealth = 100;
		Templates.FOX.strength = 10;
		Templates.FOX.dexterity = 10;
		Templates.FOX.intelligence = 10;
		Templates.FOX.endurance = 10;
		Templates.FOX.imageTexturePath = "fox.png";
		Templates.FOX.widthRatio = 1f;
		Templates.FOX.heightRatio = 1f;
		Templates.FOX.weight = 60f;
		Templates.FOX.canOpenDoors = false;
		Templates.FOX.canEquipWeapons = false;
		Templates.FOX.templateId = GameObject.generateNewTemplateId();
		Templates.FOX.aiRoutine = new AIRoutineForCarnivoreNeutralWildAnimal(Templates.FOX);

		// "Fox", "Fox", 1, 10, 0, 0, 0, 0, "fox.png", null, 1, 10, null,
		// new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false,
		// 0f, 0f, 0f, 0f, 0f, 60f, null,
		// null, 0, 0, 0, 0, 0, 0, 0, 0, new GameObject[] {}, new GameObject[]
		// {},
		// GameObject.generateNewTemplateId());
	}

}
