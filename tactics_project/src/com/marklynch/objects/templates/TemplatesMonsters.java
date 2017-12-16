package com.marklynch.objects.templates;

import com.marklynch.ai.routines.AIRoutineForRockGolem;
import com.marklynch.level.quest.caveoftheblind.AIRoutineForBlind;
import com.marklynch.level.quest.caveoftheblind.Blind;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.RockGolem;

public class TemplatesMonsters {

	// \t\tpublic\sstatic\sfinal\s[a-zA-Z]+\s

	public TemplatesMonsters() {

		Templates.BLIND = new Blind();
		Templates.BLIND.title = "Blind";
		Templates.BLIND.actorLevel = 1;
		Templates.BLIND.totalHealth = Templates.BLIND.remainingHealth = 100;
		Templates.BLIND.strength = 10;
		Templates.BLIND.dexterity = 10;
		Templates.BLIND.intelligence = 10;
		Templates.BLIND.endurance = 10;
		Templates.BLIND.imageTexturePath = "blind.png";
		Templates.BLIND.heightRatio = 1f;
		Templates.BLIND.drawOffsetY = 0f;
		Templates.BLIND.weight = 70f;
		Templates.BLIND.canOpenDoors = false;
		Templates.BLIND.canEquipWeapons = true;
		Templates.BLIND.templateId = GameObject.generateNewTemplateId();
		Templates.BLIND.aiRoutine = new AIRoutineForBlind(Templates.BLIND);

		Templates.ROCK_GOLEM = new RockGolem();
		Templates.ROCK_GOLEM.title = "Suspicious Boulder";
		Templates.ROCK_GOLEM.actorLevel = 1;
		Templates.ROCK_GOLEM.totalHealth = Templates.ROCK_GOLEM.remainingHealth = 300;
		Templates.ROCK_GOLEM.strength = 10;
		Templates.ROCK_GOLEM.dexterity = 10;
		Templates.ROCK_GOLEM.intelligence = 10;
		Templates.ROCK_GOLEM.endurance = 10;
		Templates.ROCK_GOLEM.imageTexturePath = "rock_golem_sleeping.png";
		Templates.ROCK_GOLEM.heightRatio = 1f;
		Templates.ROCK_GOLEM.drawOffsetY = 1f;
		Templates.ROCK_GOLEM.weight = 210f;
		Templates.ROCK_GOLEM.canOpenDoors = false;
		Templates.ROCK_GOLEM.canEquipWeapons = false;
		Templates.ROCK_GOLEM.templateId = GameObject.generateNewTemplateId();
		Templates.ROCK_GOLEM.aiRoutine = new AIRoutineForRockGolem(Templates.ROCK_GOLEM);

		// ROCK_GOLEM = new RockGolem("Rock Golem", "Rock Golem", 1, 100, 0, 0,
		// 0, 0, "rock_golem.png", null, 1, 10, null,
		// new Inventory(), 1, 1.5f, 0, -0.5f, 1f, 1f, 1f, null, 0.5f, 0.5f,
		// false, 90f, 0f, 0f, 0f, 100f, 150f,
		// null, null, 88, 54, 88, 54, 88, 54, 88, 54, null, false, new
		// GameObject[] {}, new GameObject[] {},
		// GameObject.generateNewTemplateId());
	}

}
