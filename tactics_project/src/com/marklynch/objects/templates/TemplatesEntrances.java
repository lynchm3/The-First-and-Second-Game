package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.RemoteDoor;

public class TemplatesEntrances {

	public TemplatesEntrances() {

		Templates.WEAK_WOODEN_DOOR = new Door();
		Templates.WEAK_WOODEN_DOOR.name = "Weak Wooden Door";
		Templates.WEAK_WOODEN_DOOR.baseName = "Weak Wooden Door";
		Templates.WEAK_WOODEN_DOOR.setImageAndExtrapolateSize("door.png");
		Templates.WEAK_WOODEN_DOOR.totalHealth = Templates.WEAK_WOODEN_DOOR.remainingHealth = 25;
		Templates.WEAK_WOODEN_DOOR.weight = 80f;
		Templates.WEAK_WOODEN_DOOR.value = 20;
		Templates.WEAK_WOODEN_DOOR.anchorX = 0;
		Templates.WEAK_WOODEN_DOOR.anchorY = 0;
		Templates.WEAK_WOODEN_DOOR.soundDampeningWhenClosed = 2;
		Templates.WEAK_WOODEN_DOOR.blocksLineOfSightWhenClosed = true;
		Templates.WEAK_WOODEN_DOOR.templateId = GameObject.generateNewTemplateId();
		Templates.WEAK_WOODEN_DOOR.flipYAxisInMirror = false;

		Templates.DOOR = new Door();
		Templates.DOOR.name = "Door";
		Templates.DOOR.baseName = "Door";
		Templates.DOOR.setImageAndExtrapolateSize("door.png");
		Templates.DOOR.totalHealth = Templates.DOOR.remainingHealth = 160;
		Templates.DOOR.weight = 80f;
		Templates.DOOR.value = 30;
		Templates.DOOR.anchorX = 0;
		Templates.DOOR.anchorY = 0;
		Templates.DOOR.soundDampeningWhenClosed = 3;
		Templates.DOOR.blocksLineOfSightWhenClosed = true;
		Templates.DOOR.templateId = GameObject.generateNewTemplateId();

		Templates.GATE = new Door();
		Templates.GATE.name = "Gate";
		Templates.GATE.baseName = "Gate";
		Templates.GATE.setImageAndExtrapolateSize("gate.png");
		Templates.GATE.totalHealth = Templates.GATE.remainingHealth = 100;
		Templates.GATE.weight = 80f;
		Templates.GATE.value = 20;
		Templates.GATE.anchorX = 0;
		Templates.GATE.anchorY = 0;
		Templates.GATE.templateId = GameObject.generateNewTemplateId();
		Templates.GATE.flipYAxisInMirror = false;
		Templates.GATE.blocksLineOfSight = false;
		Templates.GATE.blocksLineOfSightWhenClosed = false;

		Templates.OPENABLE_WALL = new RemoteDoor();
		Templates.OPENABLE_WALL.name = "Wall";
		Templates.OPENABLE_WALL.baseName = "Wall";
		Templates.OPENABLE_WALL.imageTexturePath = "wall.png";
		Templates.OPENABLE_WALL.imageTexture = getGlobalImage(Templates.OPENABLE_WALL.imageTexturePath, true);
		Templates.OPENABLE_WALL.totalHealth = Templates.OPENABLE_WALL.remainingHealth = 500;
		Templates.OPENABLE_WALL.soundDampeningWhenClosed = 5;
		Templates.OPENABLE_WALL.stackable = false;
		Templates.OPENABLE_WALL.weight = 200f;
		Templates.OPENABLE_WALL.value = 29;
		Templates.OPENABLE_WALL.anchorX = 0;
		Templates.OPENABLE_WALL.anchorY = 0;
		Templates.OPENABLE_WALL.blocksLineOfSightWhenClosed = true;
		Templates.OPENABLE_WALL.templateId = GameObject.generateNewTemplateId();
		Templates.OPENABLE_WALL.flipYAxisInMirror = false;
		Templates.OPENABLE_WALL.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_RES, new Stat(100));
		Templates.OPENABLE_WALL.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_RES, new Stat(100));
		Templates.OPENABLE_WALL.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_RES, new Stat(100));
		Templates.OPENABLE_WALL.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(100));
		Templates.OPENABLE_WALL.highLevelStats.put(HIGH_LEVEL_STATS.WATER_RES, new Stat(100));
		Templates.OPENABLE_WALL.highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_RES, new Stat(100));
		Templates.OPENABLE_WALL.highLevelStats.put(HIGH_LEVEL_STATS.POISON_RES, new Stat(100));
		Templates.OPENABLE_WALL.highLevelStats.put(HIGH_LEVEL_STATS.BLEED_RES, new Stat(100));
		Templates.OPENABLE_WALL.highLevelStats.put(HIGH_LEVEL_STATS.HEALING_RES, new Stat(100));

		Templates.REMOTE_DOOR = new RemoteDoor();
		Templates.REMOTE_DOOR.name = "Remote Door";
		Templates.REMOTE_DOOR.baseName = "Remote Door";
		Templates.REMOTE_DOOR.imageTexturePath = "door.png";
		Templates.REMOTE_DOOR.imageTexture = getGlobalImage(Templates.REMOTE_DOOR.imageTexturePath, true);
		Templates.REMOTE_DOOR.totalHealth = Templates.REMOTE_DOOR.remainingHealth = 180;
		Templates.REMOTE_DOOR.soundDampeningWhenClosed = 5;
		Templates.REMOTE_DOOR.weight = 100f;
		Templates.REMOTE_DOOR.value = 37;
		Templates.REMOTE_DOOR.blocksLineOfSightWhenClosed = true;
		Templates.REMOTE_DOOR.templateId = GameObject.generateNewTemplateId();
		Templates.REMOTE_DOOR.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_RES, new Stat(100));
		Templates.REMOTE_DOOR.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_RES, new Stat(100));
		Templates.REMOTE_DOOR.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_RES, new Stat(100));
		Templates.REMOTE_DOOR.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(100));
		Templates.REMOTE_DOOR.highLevelStats.put(HIGH_LEVEL_STATS.WATER_RES, new Stat(100));
		Templates.REMOTE_DOOR.highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_RES, new Stat(100));
		Templates.REMOTE_DOOR.highLevelStats.put(HIGH_LEVEL_STATS.POISON_RES, new Stat(100));
		Templates.REMOTE_DOOR.highLevelStats.put(HIGH_LEVEL_STATS.BLEED_RES, new Stat(100));
		Templates.REMOTE_DOOR.highLevelStats.put(HIGH_LEVEL_STATS.HEALING_RES, new Stat(100));

	}

}
