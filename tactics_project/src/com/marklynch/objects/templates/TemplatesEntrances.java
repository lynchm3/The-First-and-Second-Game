package com.marklynch.objects.templates;

import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Gate;
import com.marklynch.objects.RemoteDoor;

public class TemplatesEntrances {

	public TemplatesEntrances() {

		Templates.WEAK_WOODEN_DOOR = new Door();
		Templates.WEAK_WOODEN_DOOR.name = "Weak Wooden Door";
		Templates.WEAK_WOODEN_DOOR.baseName = "Weak Wooden Door";
		Templates.WEAK_WOODEN_DOOR.imageTexturePath = "door.png";
		Templates.WEAK_WOODEN_DOOR.totalHealth = Templates.WEAK_WOODEN_DOOR.remainingHealth = 25;
		Templates.WEAK_WOODEN_DOOR.widthRatio = 1f;
		Templates.WEAK_WOODEN_DOOR.heightRatio = 1f;
		Templates.WEAK_WOODEN_DOOR.drawOffsetRatioX = 0f;
		Templates.WEAK_WOODEN_DOOR.drawOffsetRatioY = 0f;
		Templates.WEAK_WOODEN_DOOR.soundWhenHit = 1f;
		Templates.WEAK_WOODEN_DOOR.soundWhenHitting = 1f;
		Templates.WEAK_WOODEN_DOOR.soundDampening = 1f;
		Templates.WEAK_WOODEN_DOOR.stackable = false;
		Templates.WEAK_WOODEN_DOOR.waterResistance = 0f;
		Templates.WEAK_WOODEN_DOOR.electricResistance = 0f;
		Templates.WEAK_WOODEN_DOOR.poisonResistance = 0f;
		Templates.WEAK_WOODEN_DOOR.slashResistance = 0f;
		Templates.WEAK_WOODEN_DOOR.weight = 80f;
		Templates.WEAK_WOODEN_DOOR.value = 20;
		Templates.WEAK_WOODEN_DOOR.anchorX = 0;
		Templates.WEAK_WOODEN_DOOR.anchorY = 0;
		Templates.WEAK_WOODEN_DOOR.soundDampeningWhenClosed = 2;
		Templates.WEAK_WOODEN_DOOR.blocksLineOfSightWhenClosed = true;
		Templates.WEAK_WOODEN_DOOR.templateId = GameObject.generateNewTemplateId();

		Templates.DOOR = new Door();
		Templates.DOOR.name = "Door";
		Templates.DOOR.baseName = "Door";
		Templates.DOOR.imageTexturePath = "door.png";
		Templates.DOOR.totalHealth = Templates.DOOR.remainingHealth = 160;
		Templates.DOOR.widthRatio = 1f;
		Templates.DOOR.heightRatio = 1f;
		Templates.DOOR.drawOffsetRatioX = 0f;
		Templates.DOOR.drawOffsetRatioY = 0f;
		Templates.DOOR.soundWhenHit = 1f;
		Templates.DOOR.soundWhenHitting = 1f;
		Templates.DOOR.soundDampening = 1f;
		Templates.DOOR.stackable = false;
		Templates.DOOR.waterResistance = 0f;
		Templates.DOOR.electricResistance = 0f;
		Templates.DOOR.poisonResistance = 0f;
		Templates.DOOR.slashResistance = 0f;
		Templates.DOOR.weight = 80f;
		Templates.DOOR.value = 30;
		Templates.DOOR.anchorX = 0;
		Templates.DOOR.anchorY = 0;
		Templates.DOOR.soundDampeningWhenClosed = 3;
		Templates.DOOR.blocksLineOfSightWhenClosed = true;
		Templates.DOOR.templateId = GameObject.generateNewTemplateId();

		Templates.GATE = new Gate();
		Templates.GATE.name = "Gate";
		Templates.GATE.baseName = "Gate";
		Templates.GATE.imageTexturePath = "gate.png";
		Templates.GATE.totalHealth = Templates.GATE.remainingHealth = 100;
		Templates.GATE.widthRatio = 1f;
		Templates.GATE.heightRatio = 1f;
		Templates.GATE.drawOffsetRatioX = 0f;
		Templates.GATE.drawOffsetRatioY = 0f;
		Templates.GATE.soundWhenHit = 1f;
		Templates.GATE.soundWhenHitting = 1f;
		Templates.GATE.soundDampening = 1f;
		Templates.GATE.stackable = false;
		Templates.GATE.waterResistance = 0f;
		Templates.GATE.electricResistance = 0f;
		Templates.GATE.poisonResistance = 0f;
		Templates.GATE.slashResistance = 0f;
		Templates.GATE.weight = 80f;
		Templates.GATE.value = 20;
		Templates.GATE.anchorX = 0;
		Templates.GATE.anchorY = 0;
		Templates.GATE.templateId = GameObject.generateNewTemplateId();

		Templates.FALSE_WALL = new RemoteDoor();
		Templates.FALSE_WALL.name = "Wall";
		Templates.FALSE_WALL.baseName = "Wall";
		Templates.FALSE_WALL.imageTexturePath = "wall.png";
		Templates.FALSE_WALL.totalHealth = Templates.FALSE_WALL.remainingHealth = 500;
		Templates.FALSE_WALL.widthRatio = 1f;
		Templates.FALSE_WALL.heightRatio = 1f;
		Templates.FALSE_WALL.drawOffsetRatioX = 0f;
		Templates.FALSE_WALL.drawOffsetRatioY = 0f;
		Templates.FALSE_WALL.soundWhenHit = 1f;
		Templates.FALSE_WALL.soundWhenHitting = 1f;
		Templates.FALSE_WALL.soundDampening = 1f;
		Templates.FALSE_WALL.stackable = false;
		Templates.FALSE_WALL.waterResistance = 0f;
		Templates.FALSE_WALL.electricResistance = 0f;
		Templates.FALSE_WALL.poisonResistance = 0f;
		Templates.FALSE_WALL.slashResistance = 0f;
		Templates.FALSE_WALL.weight = 200f;
		Templates.FALSE_WALL.value = 29;
		Templates.FALSE_WALL.anchorX = 0;
		Templates.FALSE_WALL.anchorY = 0;
		Templates.FALSE_WALL.soundDampeningWhenClosed = 2;
		Templates.FALSE_WALL.blocksLineOfSightWhenClosed = true;
		Templates.FALSE_WALL.templateId = GameObject.generateNewTemplateId();

	}

}
