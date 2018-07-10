package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

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
		Templates.WEAK_WOODEN_DOOR.imageTexture = getGlobalImage(Templates.WEAK_WOODEN_DOOR.imageTexturePath, true);
		Templates.WEAK_WOODEN_DOOR.totalHealth = Templates.WEAK_WOODEN_DOOR.remainingHealth = 25;
		Templates.WEAK_WOODEN_DOOR.widthRatio = 1f;
		Templates.WEAK_WOODEN_DOOR.heightRatio = 1f;
		Templates.WEAK_WOODEN_DOOR.drawOffsetRatioX = 0f;
		Templates.WEAK_WOODEN_DOOR.drawOffsetRatioY = 0f;
		Templates.WEAK_WOODEN_DOOR.soundWhenHit = 1f;
		Templates.WEAK_WOODEN_DOOR.soundWhenHitting = 1f;
		Templates.WEAK_WOODEN_DOOR.soundDampening = 1f;
		Templates.WEAK_WOODEN_DOOR.stackable = false;
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
		Templates.DOOR.imageTexturePath = "door.png";
		Templates.DOOR.imageTexture = getGlobalImage(Templates.DOOR.imageTexturePath, true);
		Templates.DOOR.totalHealth = Templates.DOOR.remainingHealth = 160;
		Templates.DOOR.widthRatio = 1f;
		Templates.DOOR.heightRatio = 1f;
		Templates.DOOR.drawOffsetRatioX = 0f;
		Templates.DOOR.drawOffsetRatioY = 0f;
		Templates.DOOR.soundWhenHit = 1f;
		Templates.DOOR.soundWhenHitting = 1f;
		Templates.DOOR.soundDampening = 1f;
		Templates.DOOR.stackable = false;
		Templates.DOOR.weight = 80f;
		Templates.DOOR.value = 30;
		Templates.DOOR.anchorX = 0;
		Templates.DOOR.anchorY = 0;
		Templates.DOOR.soundDampeningWhenClosed = 3;
		Templates.DOOR.blocksLineOfSightWhenClosed = true;
		Templates.DOOR.templateId = GameObject.generateNewTemplateId();
		Templates.DOOR.flipYAxisInMirror = false;

		Templates.GATE = new Gate();
		Templates.GATE.name = "Gate";
		Templates.GATE.baseName = "Gate";
		Templates.GATE.imageTexturePath = "gate.png";
		Templates.GATE.imageTexture = getGlobalImage(Templates.GATE.imageTexturePath, true);
		Templates.GATE.totalHealth = Templates.GATE.remainingHealth = 100;
		Templates.GATE.widthRatio = 1f;
		Templates.GATE.heightRatio = 1f;
		Templates.GATE.drawOffsetRatioX = 0f;
		Templates.GATE.drawOffsetRatioY = 0f;
		Templates.GATE.soundWhenHit = 1f;
		Templates.GATE.soundWhenHitting = 1f;
		Templates.GATE.soundDampening = 1f;
		Templates.GATE.stackable = false;
		Templates.GATE.weight = 80f;
		Templates.GATE.value = 20;
		Templates.GATE.anchorX = 0;
		Templates.GATE.anchorY = 0;
		Templates.GATE.templateId = GameObject.generateNewTemplateId();
		Templates.GATE.flipYAxisInMirror = false;

		Templates.OPENABLE_WALL = new RemoteDoor();
		Templates.OPENABLE_WALL.name = "Wall";
		Templates.OPENABLE_WALL.baseName = "Wall";
		Templates.OPENABLE_WALL.imageTexturePath = "wall.png";
		Templates.OPENABLE_WALL.imageTexture = getGlobalImage(Templates.OPENABLE_WALL.imageTexturePath, true);
		Templates.OPENABLE_WALL.totalHealth = Templates.OPENABLE_WALL.remainingHealth = 500;
		Templates.OPENABLE_WALL.widthRatio = 1f;
		Templates.OPENABLE_WALL.heightRatio = 1f;
		Templates.OPENABLE_WALL.drawOffsetRatioX = 0f;
		Templates.OPENABLE_WALL.drawOffsetRatioY = 0f;
		Templates.OPENABLE_WALL.soundWhenHit = 1f;
		Templates.OPENABLE_WALL.soundWhenHitting = 1f;
		Templates.OPENABLE_WALL.soundDampening = 1f;
		Templates.OPENABLE_WALL.soundDampeningWhenClosed = 5;
		Templates.OPENABLE_WALL.stackable = false;
		Templates.OPENABLE_WALL.weight = 200f;
		Templates.OPENABLE_WALL.value = 29;
		Templates.OPENABLE_WALL.anchorX = 0;
		Templates.OPENABLE_WALL.anchorY = 0;
		Templates.OPENABLE_WALL.blocksLineOfSightWhenClosed = true;
		Templates.OPENABLE_WALL.templateId = GameObject.generateNewTemplateId();
		Templates.OPENABLE_WALL.flipYAxisInMirror = false;

	}

}
