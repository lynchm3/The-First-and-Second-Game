package com.marklynch.objects.templates;

import com.marklynch.ai.routines.AIRoutineForPig;
import com.marklynch.level.constructs.inventory.Inventory;
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

		// "Pig", "Pig", 1, 10, 0, 0, 0, 0, "pig.png", null, 1, 10, null, new
		// Inventory(), 1f,
		// 0.65625f, 0, 0, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f,
		// 0f, 90f, null, null, 40, 96, 40,
		// 96, 40, 96, 40, 96, new GameObject[] {}, new GameObject[] {},
		// GameObject.generateNewTemplateId());

		// Friendly Wild animals
		Templates.RAT = new TinyNeutralWildAnimal("Rat", "Rat", 1, 1, 0, 0, 0, 0, "rat.png", null, 1, 10, null,
				new Inventory(), 1, 0.25f, 0f, 0.75f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0f, 60f,
				null, null, 0, 0, 0, 0, 0, 0, 0, 0, new GameObject[] {}, new GameObject[] {},
				GameObject.generateNewTemplateId());

		Templates.RABBIT = new HerbivoreWildAnimal("Rabbit", "Rabbit", 1, 4, 0, 0, 0, 0, "rabbit.png", null, 1, 10,
				null, new Inventory(), 0.5f, 0.5f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0f, 60f,
				null, null, 0, 0, 0, 0, 0, 0, 0, 0, new GameObject[] {}, new GameObject[] {},
				GameObject.generateNewTemplateId());

		Templates.BABY_RABBIT = new TinyNeutralWildAnimal("Baby Rabbit", "Baby Rabbit", 1, 10, 0, 0, 0, 0,
				"baby_rabbit.png", null, 1, 10, null, new Inventory(), 0.25f, 0.25f, 0f, 0f, 1f, 1f, 1f, null, 0.5f,
				0.5f, false, 0f, 0f, 0f, 0f, 0f, 60f, null, null, 0, 0, 0, 0, 0, 0, 0, 0, new GameObject[] {},
				new GameObject[] {}, GameObject.generateNewTemplateId());

		Templates.FOX = new CarnivoreNeutralWildAnimal("Fox", "Fox", 1, 10, 0, 0, 0, 0, "fox.png", null, 1, 10, null,
				new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0f, 60f, null,
				null, 0, 0, 0, 0, 0, 0, 0, 0, new GameObject[] {}, new GameObject[] {},
				GameObject.generateNewTemplateId());
	}

}
