package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.ai.routines.AIRoutineForCarnivoreNeutralWildAnimal;
import com.marklynch.ai.routines.AIRoutineForFish;
import com.marklynch.ai.routines.AIRoutineForHerbivoreWildAnimal;
import com.marklynch.ai.routines.AIRoutineForPig;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.CarnivoreNeutralWildAnimal;
import com.marklynch.objects.units.Fish;
import com.marklynch.objects.units.HerbivoreWildAnimal;
import com.marklynch.objects.units.Pig;
import com.marklynch.objects.units.TinyNeutralWildAnimal;

public class TemplatesAnimals {

	// \t\tpublic\sstatic\sfinal\s[a-zA-Z]+\s

	public TemplatesAnimals() {

		Templates.PIG = new Pig();
		Templates.PIG.title = "Pig";
		Templates.PIG.level = 1;
		Templates.PIG.pierceDamage = 4;
		Templates.PIG.totalHealth = Templates.PIG.remainingHealth = 100;
		Templates.PIG.strength = 10;
		Templates.PIG.dexterity = 10;
		Templates.PIG.intelligence = 10;
		Templates.PIG.endurance = 10;
		Templates.PIG.imageTexturePath = "pig.png";
		Templates.PIG.imageTexture = getGlobalImage(Templates.PIG.imageTexturePath, true);
		Templates.PIG.heightRatio = 0.25f;
		Templates.PIG.drawOffsetRatioY = 0.75f;
		Templates.PIG.weight = 70f;
		Templates.PIG.canOpenDoors = false;
		Templates.PIG.canEquipWeapons = false;
		Templates.PIG.templateId = GameObject.generateNewTemplateId();
		Templates.PIG.aiRoutine = new AIRoutineForPig(Templates.PIG);
		Templates.PIG.flipYAxisInMirror = false;

		// Friendly Wild animals
		Templates.RAT = new TinyNeutralWildAnimal();
		Templates.RAT.title = "Rat";
		Templates.RAT.level = 1;
		Templates.RAT.pierceDamage = 2;
		Templates.RAT.totalHealth = Templates.RAT.remainingHealth = 5;
		Templates.RAT.strength = 10;
		Templates.RAT.dexterity = 10;
		Templates.RAT.intelligence = 10;
		Templates.RAT.endurance = 10;
		Templates.RAT.imageTexturePath = "rat.png";
		Templates.RAT.imageTexture = getGlobalImage(Templates.RAT.imageTexturePath, true);
		Templates.RAT.heightRatio = 0.25f;
		Templates.RAT.drawOffsetRatioY = 0.75f;
		Templates.RAT.weight = 70f;
		Templates.RAT.canOpenDoors = false;
		Templates.RAT.canEquipWeapons = false;
		Templates.RAT.templateId = GameObject.generateNewTemplateId();
		Templates.RAT.aiRoutine = new AIRoutineForHerbivoreWildAnimal(Templates.RAT);
		Templates.RAT.canBePickedUp = true;
		Templates.RAT.fitsInInventory = true;
		Templates.RAT.flipYAxisInMirror = false;

		Templates.RABBIT = new HerbivoreWildAnimal();
		Templates.RABBIT.title = "Rabbit";
		Templates.RABBIT.level = 1;
		Templates.RABBIT.pierceDamage = 1;
		Templates.RABBIT.totalHealth = Templates.RABBIT.remainingHealth = 10;
		Templates.RABBIT.strength = 10;
		Templates.RABBIT.dexterity = 10;
		Templates.RABBIT.intelligence = 10;
		Templates.RABBIT.endurance = 10;
		Templates.RABBIT.imageTexturePath = "rabbit.png";
		Templates.RABBIT.imageTexture = getGlobalImage(Templates.RABBIT.imageTexturePath, true);
		Templates.RABBIT.widthRatio = 0.5f;
		Templates.RABBIT.heightRatio = 0.5f;
		Templates.RABBIT.weight = 10f;
		Templates.RABBIT.canOpenDoors = false;
		Templates.RABBIT.canEquipWeapons = false;
		Templates.RABBIT.templateId = GameObject.generateNewTemplateId();
		Templates.RABBIT.aiRoutine = new AIRoutineForHerbivoreWildAnimal(Templates.RABBIT);
		Templates.RABBIT.flipYAxisInMirror = false;

		Templates.BABY_RABBIT = new TinyNeutralWildAnimal();
		Templates.BABY_RABBIT.title = "Baby Rabbit";
		Templates.BABY_RABBIT.level = 1;
		Templates.BABY_RABBIT.pierceDamage = 1;
		Templates.BABY_RABBIT.totalHealth = Templates.BABY_RABBIT.remainingHealth = 5;
		Templates.BABY_RABBIT.strength = 10;
		Templates.BABY_RABBIT.dexterity = 10;
		Templates.BABY_RABBIT.intelligence = 10;
		Templates.BABY_RABBIT.endurance = 10;
		Templates.BABY_RABBIT.imageTexturePath = "baby_rabbit.png";
		Templates.BABY_RABBIT.imageTexture = getGlobalImage(Templates.BABY_RABBIT.imageTexturePath, true);
		Templates.BABY_RABBIT.widthRatio = 0.25f;
		Templates.BABY_RABBIT.heightRatio = 0.25f;
		Templates.BABY_RABBIT.weight = 10f;
		Templates.BABY_RABBIT.canOpenDoors = false;
		Templates.BABY_RABBIT.canEquipWeapons = false;
		Templates.BABY_RABBIT.templateId = GameObject.generateNewTemplateId();
		Templates.BABY_RABBIT.aiRoutine = new AIRoutineForHerbivoreWildAnimal(Templates.BABY_RABBIT);
		Templates.BABY_RABBIT.canBePickedUp = true;
		Templates.BABY_RABBIT.fitsInInventory = true;
		Templates.BABY_RABBIT.flipYAxisInMirror = false;

		Templates.FOX = new CarnivoreNeutralWildAnimal();
		Templates.FOX.title = "Fox";
		Templates.FOX.level = 1;
		Templates.FOX.pierceDamage = 6;
		Templates.FOX.totalHealth = Templates.FOX.remainingHealth = 15;
		Templates.FOX.strength = 10;
		Templates.FOX.dexterity = 10;
		Templates.FOX.intelligence = 10;
		Templates.FOX.endurance = 10;
		Templates.FOX.imageTexturePath = "fox.png";
		Templates.FOX.imageTexture = getGlobalImage(Templates.FOX.imageTexturePath, true);
		Templates.FOX.widthRatio = 1f;
		Templates.FOX.heightRatio = 1f;
		Templates.FOX.weight = 60f;
		Templates.FOX.canOpenDoors = false;
		Templates.FOX.canEquipWeapons = false;
		Templates.FOX.templateId = GameObject.generateNewTemplateId();
		Templates.FOX.aiRoutine = new AIRoutineForCarnivoreNeutralWildAnimal(Templates.FOX);
		Templates.FOX.flipYAxisInMirror = false;

		Templates.WOLF = new CarnivoreNeutralWildAnimal();
		Templates.WOLF.title = "Wolf";
		Templates.WOLF.level = 1;
		Templates.WOLF.pierceDamage = 10;
		Templates.WOLF.totalHealth = Templates.WOLF.remainingHealth = 20;
		Templates.WOLF.strength = 10;
		Templates.WOLF.dexterity = 10;
		Templates.WOLF.intelligence = 10;
		Templates.WOLF.endurance = 10;
		Templates.WOLF.imageTexturePath = "wolf_pink.png";
		Templates.WOLF.imageTexture = getGlobalImage(Templates.WOLF.imageTexturePath, true);
		Templates.WOLF.widthRatio = 1f;
		Templates.WOLF.heightRatio = 1f;
		Templates.WOLF.weight = 80f;
		Templates.WOLF.canOpenDoors = false;
		Templates.WOLF.canEquipWeapons = false;
		Templates.WOLF.templateId = GameObject.generateNewTemplateId();
		Templates.WOLF.aiRoutine = new AIRoutineForCarnivoreNeutralWildAnimal(Templates.WOLF);
		Templates.WOLF.flipYAxisInMirror = false;

		// Friendly Wild animals
		Templates.FISH = new Fish();
		Templates.FISH.title = "Fish";
		Templates.FISH.level = 1;
		Templates.FISH.slashDamage = 1;
		Templates.FISH.totalHealth = Templates.FISH.remainingHealth = 5;
		Templates.FISH.strength = 1;
		Templates.FISH.dexterity = 1;
		Templates.FISH.intelligence = 1;
		Templates.FISH.endurance = 1;
		Templates.FISH.imageTexturePath = "fish.png";
		Templates.FISH.imageTexture = getGlobalImage(Templates.FISH.imageTexturePath, true);
		Templates.FISH.heightRatio = 0.25f;
		Templates.FISH.drawOffsetRatioY = 0.75f;
		Templates.FISH.widthRatio = 0.25f;
		Templates.FISH.drawOffsetRatioX = 0.75f;
		Templates.FISH.weight = 10f;
		Templates.FISH.canOpenDoors = false;
		Templates.FISH.canEquipWeapons = false;
		Templates.FISH.templateId = GameObject.generateNewTemplateId();
		Templates.FISH.aiRoutine = new AIRoutineForFish(Templates.FISH);
		Templates.FISH.canBePickedUp = true;
		Templates.FISH.fitsInInventory = true;
	}

}
