package com.marklynch.objects.templates;

import com.marklynch.ai.routines.AIRoutineForCarnivoreNeutralWildAnimal;
import com.marklynch.ai.routines.AIRoutineForFish;
import com.marklynch.ai.routines.AIRoutineForHerbivoreWildAnimal;
import com.marklynch.ai.routines.AIRoutineForPig;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.actors.CarnivoreNeutralWildAnimal;
import com.marklynch.objects.actors.Fish;
import com.marklynch.objects.actors.HerbivoreWildAnimal;
import com.marklynch.objects.actors.Pig;
import com.marklynch.objects.actors.TinyNeutralWildAnimal;
import com.marklynch.objects.inanimateobjects.GameObject;

public class TemplatesAnimals {

	// \t\tpublic\sstatic\sfinal\s[a-zA-Z]+\s

	public TemplatesAnimals() {

		Templates.PIG = new Pig();
		Templates.PIG.title = "Pig";
		Templates.PIG.level = 1;
		Templates.PIG.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, new Stat(4));
		Templates.PIG.totalHealth = Templates.PIG.remainingHealth = 100;
		Templates.PIG.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(10));
		Templates.PIG.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.PIG.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(10));
		Templates.PIG.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(10));
		Templates.PIG.setImageAndExtrapolateSize("pig.png");
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
		Templates.RAT.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, new Stat(2));
		Templates.RAT.totalHealth = Templates.RAT.remainingHealth = 5;
		Templates.RAT.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(10));
		Templates.RAT.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.RAT.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(10));
		Templates.RAT.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(10));
		Templates.RAT.setImageAndExtrapolateSize("rat.png");
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
		Templates.RABBIT.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, new Stat(1));
		Templates.RABBIT.totalHealth = Templates.RABBIT.remainingHealth = 10;
		Templates.RABBIT.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(10));
		Templates.RABBIT.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.RABBIT.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(10));
		Templates.RABBIT.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(10));
		Templates.RABBIT.setImageAndExtrapolateSize("rabbit.png");
		Templates.RABBIT.weight = 10f;
		Templates.RABBIT.canOpenDoors = false;
		Templates.RABBIT.canEquipWeapons = false;
		Templates.RABBIT.templateId = GameObject.generateNewTemplateId();
		Templates.RABBIT.aiRoutine = new AIRoutineForHerbivoreWildAnimal(Templates.RABBIT);
		Templates.RABBIT.flipYAxisInMirror = false;

		Templates.BABY_RABBIT = new TinyNeutralWildAnimal();
		Templates.BABY_RABBIT.title = "Baby Rabbit";
		Templates.BABY_RABBIT.level = 1;
		Templates.BABY_RABBIT.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, new Stat(1));
		Templates.BABY_RABBIT.totalHealth = Templates.BABY_RABBIT.remainingHealth = 5;
		Templates.BABY_RABBIT.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(10));
		Templates.BABY_RABBIT.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.BABY_RABBIT.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(10));
		Templates.BABY_RABBIT.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(10));
		Templates.BABY_RABBIT.setImageAndExtrapolateSize("baby_rabbit.png");
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
		Templates.FOX.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, new Stat(6));
		Templates.FOX.totalHealth = Templates.FOX.remainingHealth = 15;
		Templates.FOX.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(10));
		Templates.FOX.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.FOX.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(10));
		Templates.FOX.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(10));
		Templates.FOX.setImageAndExtrapolateSize("fox.png");
		Templates.FOX.weight = 60f;
		Templates.FOX.canOpenDoors = false;
		Templates.FOX.canEquipWeapons = false;
		Templates.FOX.templateId = GameObject.generateNewTemplateId();
		Templates.FOX.aiRoutine = new AIRoutineForCarnivoreNeutralWildAnimal(Templates.FOX);
		Templates.FOX.flipYAxisInMirror = false;

		Templates.WOLF = new CarnivoreNeutralWildAnimal();
		Templates.WOLF.title = "Wolf";
		Templates.WOLF.level = 1;
		Templates.WOLF.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, new Stat(10));
		Templates.WOLF.totalHealth = Templates.WOLF.remainingHealth = 20;
		Templates.WOLF.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(10));
		Templates.WOLF.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.WOLF.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(10));
		Templates.WOLF.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(10));
		Templates.WOLF.setImageAndExtrapolateSize("wolf_pink.png");
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
		Templates.FISH.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(1));
		Templates.FISH.totalHealth = Templates.FISH.remainingHealth = 5;
		Templates.FISH.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(1));
		Templates.FISH.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(1));
		Templates.FISH.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(1));
		Templates.FISH.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(1));
		Templates.FISH.setImageAndExtrapolateSize("fish.png");
		Templates.FISH.drawOffsetRatioY = 0.75f;
		Templates.FISH.drawOffsetRatioX = 0.75f;
		Templates.FISH.weight = 2f;
		Templates.FISH.canOpenDoors = false;
		Templates.FISH.canEquipWeapons = false;
		Templates.FISH.floatsInWater = true;
		Templates.FISH.templateId = GameObject.generateNewTemplateId();
		Templates.FISH.aiRoutine = new AIRoutineForFish(Templates.FISH);
		Templates.FISH.canBePickedUp = true;
		Templates.FISH.fitsInInventory = true;

		Templates.TURTLE = new Fish();
		Templates.TURTLE.title = "Turtle";
		Templates.TURTLE.level = 1;
		Templates.TURTLE.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(2));
		Templates.TURTLE.totalHealth = Templates.TURTLE.remainingHealth = 5;
		Templates.TURTLE.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(1));
		Templates.TURTLE.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(1));
		Templates.TURTLE.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(1));
		Templates.TURTLE.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(1));
		Templates.TURTLE.setImageAndExtrapolateSize("turtle.png");
		Templates.TURTLE.drawOffsetRatioY = 0.75f;
		Templates.TURTLE.drawOffsetRatioX = 0.5f;
		Templates.TURTLE.anchorX = 32;
		Templates.TURTLE.anchorY = 16;
		Templates.TURTLE.weight = 4f;
		Templates.TURTLE.canOpenDoors = false;
		Templates.TURTLE.canEquipWeapons = false;
		Templates.TURTLE.floatsInWater = true;
		Templates.TURTLE.templateId = GameObject.generateNewTemplateId();
		Templates.TURTLE.aiRoutine = new AIRoutineForFish(Templates.TURTLE);
		Templates.TURTLE.canBePickedUp = true;
		Templates.TURTLE.fitsInInventory = true;
	}

}
