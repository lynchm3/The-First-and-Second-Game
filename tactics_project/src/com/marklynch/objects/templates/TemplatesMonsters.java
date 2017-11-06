package com.marklynch.objects.templates;

import com.marklynch.level.quest.caveoftheblind.AIRoutineForBlind;
import com.marklynch.level.quest.caveoftheblind.Blind;
import com.marklynch.objects.GameObject;

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
		Templates.BLIND.drawOffsetY = 1f;
		Templates.BLIND.weight = 70f;
		Templates.BLIND.canOpenDoors = false;
		Templates.BLIND.canEquipWeapons = true;
		Templates.BLIND.templateId = GameObject.generateNewTemplateId();
		Templates.BLIND.aiRoutine = new AIRoutineForBlind(Templates.BLIND);

		// Monsters
		// BLIND = new Blind("Blind", "Blind", 1, 10, 0, 0, 0, 0, "blind.png",
		// null, 1, 1, null, new Inventory(), 1f, 1f,
		// 0, 0, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0f, 50f,
		// null, null, 88, 54, 88, 54, 88, 54,
		// 88, 54, null, new GameObject[] {}, new GameObject[] {},
		// GameObject.generateNewTemplateId());
		//
		// ROCK_GOLEM = new RockGolem("Rock Golem", "Rock Golem", 1, 100, 0, 0,
		// 0, 0, "rock_golem.png", null, 1, 10, null,
		// new Inventory(), 1, 1.5f, 0, -0.5f, 1f, 1f, 1f, null, 0.5f, 0.5f,
		// false, 90f, 0f, 0f, 0f, 100f, 150f,
		// null, null, 88, 54, 88, 54, 88, 54, 88, 54, null, false, new
		// GameObject[] {}, new GameObject[] {},
		// GameObject.generateNewTemplateId());
	}

}
