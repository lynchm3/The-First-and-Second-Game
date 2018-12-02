package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.ai.routines.AIRoutineForBlind;
import com.marklynch.ai.routines.AIRoutineForRockGolem;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.quest.caveoftheblind.Blind;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actors.RockGolem;

public class TemplatesMonsters {

	// \t\tpublic\sstatic\sfinal\s[a-zA-Z]+\s

	public TemplatesMonsters() {

		Templates.BLIND = new Blind();
		Templates.BLIND.title = "Blind";
		Templates.BLIND.level = 1;
		Templates.BLIND.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(6));
		Templates.BLIND.totalHealth = Templates.BLIND.remainingHealth = 100;
		Templates.BLIND.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(10));
		Templates.BLIND.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.BLIND.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(10));
		Templates.BLIND.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(10));
		Templates.BLIND.imageTexture = getGlobalImage("blind.png", true);
		Templates.BLIND.heightRatio = 1f;
		Templates.BLIND.drawOffsetRatioY = 0f;
		Templates.BLIND.weight = 70f;
		Templates.BLIND.canOpenDoors = false;
		Templates.BLIND.canEquipWeapons = true;
		Templates.BLIND.templateId = GameObject.generateNewTemplateId();
		Templates.BLIND.aiRoutine = new AIRoutineForBlind(Templates.BLIND);
		Templates.BLIND.flipYAxisInMirror = false;

		Templates.ROCK_GOLEM = new RockGolem();
		Templates.ROCK_GOLEM.title = "Suspicious Boulder";
		Templates.ROCK_GOLEM.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(20));
		Templates.ROCK_GOLEM.level = 1;
		Templates.ROCK_GOLEM.totalHealth = Templates.ROCK_GOLEM.remainingHealth = 300;
		Templates.ROCK_GOLEM.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(10));
		Templates.ROCK_GOLEM.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.ROCK_GOLEM.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(10));
		Templates.ROCK_GOLEM.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(10));
		Templates.ROCK_GOLEM.imageTexture = getGlobalImage("rock_golem_sleeping.png", true);
		Templates.ROCK_GOLEM.heightRatio = 1f;
		Templates.ROCK_GOLEM.drawOffsetRatioY = 1f;
		Templates.ROCK_GOLEM.weight = 210f;
		Templates.ROCK_GOLEM.canOpenDoors = false;
		Templates.ROCK_GOLEM.canEquipWeapons = false;
		Templates.ROCK_GOLEM.templateId = GameObject.generateNewTemplateId();
		Templates.ROCK_GOLEM.aiRoutine = new AIRoutineForRockGolem(Templates.ROCK_GOLEM);
		Templates.ROCK_GOLEM.flipYAxisInMirror = false;
	}

}
