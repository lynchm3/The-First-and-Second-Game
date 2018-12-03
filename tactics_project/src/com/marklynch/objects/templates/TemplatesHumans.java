package com.marklynch.objects.templates;

import com.marklynch.ai.routines.AIRoutineForDoctor;
import com.marklynch.ai.routines.AIRoutineForFisherman;
import com.marklynch.ai.routines.AIRoutineForGuard;
import com.marklynch.ai.routines.AIRoutineForHunter;
import com.marklynch.ai.routines.AIRoutineForKidnapper;
import com.marklynch.ai.routines.AIRoutineForMinecart;
import com.marklynch.ai.routines.AIRoutineForMiner;
import com.marklynch.ai.routines.AIRoutineForMort;
import com.marklynch.ai.routines.AIRoutineForThief;
import com.marklynch.ai.routines.AIRoutineForTrader;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.quest.caveoftheblind.Mort;
import com.marklynch.level.quest.thesecretroom.Kidnapper;
import com.marklynch.objects.actors.Doctor;
import com.marklynch.objects.actors.Guard;
import com.marklynch.objects.actors.Human;
import com.marklynch.objects.actors.Player;
import com.marklynch.objects.actors.Thief;
import com.marklynch.objects.actors.Trader;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.ResourceUtils;

public class TemplatesHumans {

	// \t\tpublic\sstatic\sfinal\s[a-zA-Z]+\s

	public TemplatesHumans() {

		// Player
		Templates.PLAYER = new Player();
		Templates.PLAYER.title = "Fighter";
		Templates.PLAYER.level = 1;
		Templates.PLAYER.totalHealth = Templates.PLAYER.remainingHealth = 1000;
		Templates.PLAYER.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(10));
		Templates.PLAYER.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.PLAYER.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(10));
		Templates.PLAYER.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(10));
		Templates.PLAYER.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(3));
		Templates.PLAYER.heightRatio = 1.5f;
		Templates.PLAYER.drawOffsetRatioY = -0.5f;
		Templates.PLAYER.weight = 90f;
		Templates.PLAYER.canOpenDoors = true;
		Templates.PLAYER.canEquipWeapons = true;
		Templates.PLAYER.templateId = GameObject.generateNewTemplateId();
		Templates.PLAYER.flipYAxisInMirror = false;

		// General People
		Templates.HUNTER = new Human();
		Templates.HUNTER.title = "Hunter";
		Templates.HUNTER.level = 1;
		Templates.HUNTER.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(3));
		Templates.HUNTER.totalHealth = Templates.HUNTER.remainingHealth = 10;
		Templates.HUNTER.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(10));
		Templates.HUNTER.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.HUNTER.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(10));
		Templates.HUNTER.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(10));
		Templates.HUNTER.heightRatio = 1.5f;
		Templates.HUNTER.drawOffsetRatioY = -0.5f;
		Templates.HUNTER.weight = 90f;
		Templates.HUNTER.canOpenDoors = true;
		Templates.HUNTER.canEquipWeapons = true;
		Templates.HUNTER.aiRoutine = new AIRoutineForHunter(Templates.HUNTER);
		Templates.HUNTER.templateId = GameObject.generateNewTemplateId();
		Templates.HUNTER.flipYAxisInMirror = false;
		Templates.HUNTER.bodyArmor = Templates.LEATHERS.makeCopy(null, null);
		Templates.HUNTER.legArmor = Templates.PANTS.makeCopy(null, null);
		Templates.HUNTER.helmet = Templates.HUNTING_CAP.makeCopy(null, null);

		Templates.GUARD = new Guard();
		Templates.GUARD.title = "Guard";
		Templates.GUARD.level = 10;
		Templates.GUARD.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(5));
		Templates.GUARD.totalHealth = Templates.GUARD.remainingHealth = 10;
		Templates.GUARD.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(20));
		Templates.GUARD.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(11));
		Templates.GUARD.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(13));
		Templates.GUARD.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(21));
		Templates.GUARD.heightRatio = 1.5f;
		Templates.GUARD.drawOffsetRatioY = -0.5f;
		Templates.GUARD.weight = 100f;
		Templates.GUARD.canOpenDoors = true;
		Templates.GUARD.canEquipWeapons = true;
		Templates.GUARD.aiRoutine = new AIRoutineForGuard(Templates.GUARD);
		Templates.GUARD.templateId = GameObject.generateNewTemplateId();
		Templates.GUARD.flipYAxisInMirror = false;

		Templates.FISHERMAN = new Human();
		Templates.FISHERMAN.title = "Fisherman";
		Templates.FISHERMAN.level = 10;
		Templates.FISHERMAN.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(3));
		Templates.FISHERMAN.totalHealth = Templates.FISHERMAN.remainingHealth = 10;
		Templates.FISHERMAN.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(6));
		Templates.FISHERMAN.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(4));
		Templates.FISHERMAN.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(3));
		Templates.FISHERMAN.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(11));
		Templates.FISHERMAN.hairImageTexture = ResourceUtils.getGlobalImage("hair.png", false);
		Templates.FISHERMAN.heightRatio = 1.5f;
		Templates.FISHERMAN.drawOffsetRatioY = -0.5f;
		Templates.FISHERMAN.weight = 68f;
		Templates.FISHERMAN.canOpenDoors = true;
		Templates.FISHERMAN.canEquipWeapons = true;
		Templates.FISHERMAN.aiRoutine = new AIRoutineForFisherman(Templates.FISHERMAN);
		Templates.FISHERMAN.templateId = GameObject.generateNewTemplateId();
		Templates.FISHERMAN.flipYAxisInMirror = false;

		// General People
		Templates.MINER = new Human();
		Templates.MINER.title = "Miner";
		Templates.MINER.level = 1;
		Templates.MINER.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(3));
		Templates.MINER.totalHealth = Templates.MINER.remainingHealth = 10;
		Templates.MINER.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(13));
		Templates.MINER.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(5));
		Templates.MINER.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(4));
		Templates.MINER.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(12));
		Templates.MINER.heightRatio = 1.5f;
		Templates.MINER.drawOffsetRatioY = -0.5f;
		Templates.MINER.weight = 110f;
		Templates.MINER.canOpenDoors = true;
		Templates.MINER.canEquipWeapons = true;
		Templates.MINER.aiRoutine = new AIRoutineForMiner(Templates.MINER);
		Templates.MINER.templateId = GameObject.generateNewTemplateId();
		Templates.MINER.flipYAxisInMirror = false;

		Templates.THIEF = new Thief();
		Templates.THIEF.title = "Thief";
		Templates.THIEF.level = 1;
		Templates.THIEF.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(2));
		Templates.THIEF.totalHealth = Templates.THIEF.remainingHealth = 10;
		Templates.THIEF.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(10));
		Templates.THIEF.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.THIEF.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(10));
		Templates.THIEF.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(10));
		Templates.THIEF.heightRatio = 1.5f;
		Templates.THIEF.drawOffsetRatioY = -0.5f;
		Templates.THIEF.weight = 90f;
		Templates.THIEF.canOpenDoors = true;
		Templates.THIEF.canEquipWeapons = true;
		Templates.THIEF.aiRoutine = new AIRoutineForThief(Templates.THIEF);
		Templates.THIEF.templateId = GameObject.generateNewTemplateId();
		Templates.THIEF.flipYAxisInMirror = false;

		Templates.FARMER = new Human();
		Templates.FARMER.title = "Farmer";
		Templates.FARMER.level = 1;
		Templates.FARMER.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(3));
		Templates.FARMER.totalHealth = Templates.FARMER.remainingHealth = 10;
		Templates.FARMER.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(10));
		Templates.FARMER.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.FARMER.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(10));
		Templates.FARMER.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(10));
		Templates.FARMER.heightRatio = 1.5f;
		Templates.FARMER.drawOffsetRatioY = -0.5f;
		Templates.FARMER.weight = 90f;
		Templates.FARMER.canOpenDoors = true;
		Templates.FARMER.canEquipWeapons = true;
		Templates.FARMER.aiRoutine = new AIRoutineForHunter(Templates.FARMER);
		Templates.FARMER.templateId = GameObject.generateNewTemplateId();
		Templates.FARMER.flipYAxisInMirror = false;
		Templates.FARMER.bodyArmor = null;
		Templates.FARMER.legArmor = Templates.UNDIES.makeCopy(null, null);

		// Special People
		Templates.MORT = new Mort();
		Templates.MORT.title = "Mort";
		Templates.MORT.level = 1;
		Templates.MORT.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(2));
		Templates.MORT.totalHealth = Templates.MORT.remainingHealth = 10;
		Templates.MORT.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(10));
		Templates.MORT.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.MORT.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(10));
		Templates.MORT.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(10));
		Templates.MORT.heightRatio = 1.5f;
		Templates.MORT.drawOffsetRatioY = -0.5f;
		Templates.MORT.weight = 90f;
		Templates.MORT.canOpenDoors = true;
		Templates.MORT.canEquipWeapons = true;
		Templates.MORT.aiRoutine = new AIRoutineForMort(Templates.MORT);
		Templates.MORT.templateId = GameObject.generateNewTemplateId();
		Templates.MORT.flipYAxisInMirror = false;

		Templates.KIDNAPPER = new Kidnapper();
		Templates.KIDNAPPER.title = "KIDNAPPER";
		Templates.KIDNAPPER.level = 1;
		Templates.KIDNAPPER.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(2));
		Templates.KIDNAPPER.totalHealth = Templates.KIDNAPPER.remainingHealth = 10;
		Templates.KIDNAPPER.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(10));
		Templates.KIDNAPPER.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.KIDNAPPER.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(10));
		Templates.KIDNAPPER.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(10));
		Templates.KIDNAPPER.heightRatio = 1.5f;
		Templates.KIDNAPPER.drawOffsetRatioY = -0.5f;
		Templates.KIDNAPPER.weight = 90f;
		Templates.KIDNAPPER.aiRoutine = new AIRoutineForKidnapper(Templates.KIDNAPPER);
		Templates.KIDNAPPER.templateId = GameObject.generateNewTemplateId();

		Templates.TRADER = new Trader();
		Templates.TRADER.title = "Trader";
		Templates.TRADER.level = 1;
		Templates.TRADER.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(2));
		Templates.TRADER.totalHealth = Templates.TRADER.remainingHealth = 10;
		Templates.TRADER.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(10));
		Templates.TRADER.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.TRADER.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(10));
		Templates.TRADER.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(10));
		Templates.TRADER.heightRatio = 1.5f;
		Templates.TRADER.drawOffsetRatioY = -0.5f;
		Templates.TRADER.weight = 90f;
		Templates.TRADER.canOpenDoors = true;
		Templates.TRADER.canEquipWeapons = true;
		Templates.TRADER.aiRoutine = new AIRoutineForTrader(Templates.TRADER);
		Templates.TRADER.templateId = GameObject.generateNewTemplateId();
		Templates.TRADER.flipYAxisInMirror = false;

		Templates.DOCTOR = new Doctor();
		Templates.DOCTOR.title = "Doctor";
		Templates.DOCTOR.level = 1;
		Templates.DOCTOR.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(4));
		Templates.DOCTOR.totalHealth = Templates.DOCTOR.remainingHealth = 10;
		Templates.DOCTOR.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(7));
		Templates.DOCTOR.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(12));
		Templates.DOCTOR.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(20));
		Templates.DOCTOR.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(7));
		Templates.DOCTOR.heightRatio = 1.5f;
		Templates.DOCTOR.drawOffsetRatioY = -0.5f;
		Templates.DOCTOR.weight = 80f;
		Templates.DOCTOR.aiRoutine = new AIRoutineForDoctor(Templates.DOCTOR);
		Templates.DOCTOR.templateId = GameObject.generateNewTemplateId();

		Templates.MINECART_RIDER = new Human();
		Templates.MINECART_RIDER.title = "MINECART_RIDER";
		Templates.MINECART_RIDER.level = 1;
		Templates.MINECART_RIDER.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(3));
		Templates.MINECART_RIDER.totalHealth = Templates.MINECART_RIDER.remainingHealth = 10;
		Templates.MINECART_RIDER.highLevelStats.put(HIGH_LEVEL_STATS.STRENGTH, new Stat(10));
		Templates.MINECART_RIDER.highLevelStats.put(HIGH_LEVEL_STATS.DEXTERITY, new Stat(10));
		Templates.MINECART_RIDER.highLevelStats.put(HIGH_LEVEL_STATS.INTELLIGENCE, new Stat(10));
		Templates.MINECART_RIDER.highLevelStats.put(HIGH_LEVEL_STATS.ENDURANCE, new Stat(10));
		Templates.MINECART_RIDER.heightRatio = 1.5f;
		Templates.MINECART_RIDER.drawOffsetRatioY = -0.5f;
		Templates.MINECART_RIDER.weight = 90f;
		Templates.MINECART_RIDER.canOpenDoors = true;
		Templates.MINECART_RIDER.canEquipWeapons = true;
		Templates.MINECART_RIDER.aiRoutine = new AIRoutineForMinecart(Templates.MINECART_RIDER);
		Templates.MINECART_RIDER.templateId = GameObject.generateNewTemplateId();
		Templates.MINECART_RIDER.flipYAxisInMirror = false;
		Templates.MINECART_RIDER.bodyArmor = null;
		Templates.MINECART_RIDER.legArmor = Templates.UNDIES.makeCopy(null, null);
	}

}
