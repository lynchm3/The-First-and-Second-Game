package com.marklynch.objects.templates;

import com.marklynch.objects.Fence;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Wall;

public class TemplatesBuilding {

	public TemplatesBuilding() {

		Templates.WALL = new Wall();
		Templates.WALL.name = "Wall";
		Templates.WALL.imageTexturePath = "wall.png";
		Templates.WALL.totalHealth = Templates.WALL.remainingHealth = 1000;
		Templates.WALL.widthRatio = 1f;
		Templates.WALL.heightRatio = 1f;
		Templates.WALL.drawOffsetX = 0f;
		Templates.WALL.drawOffsetY = 0f;
		Templates.WALL.soundWhenHit = 1f;
		Templates.WALL.soundWhenHitting = 1f;
		Templates.WALL.soundDampening = 1f;
		Templates.WALL.stackable = false;
		Templates.WALL.weight = 100f;
		Templates.WALL.value = 24;
		Templates.WALL.anchorX = 0;
		Templates.WALL.anchorY = 0;
		Templates.WALL.templateId = GameObject.generateNewTemplateId();

		Templates.FENCE = new Fence();
		Templates.FENCE.name = "Fence";
		Templates.FENCE.imageTexturePath = "wall.png";
		Templates.FENCE.totalHealth = Templates.FENCE.remainingHealth = 100;
		Templates.FENCE.widthRatio = 1f;
		Templates.FENCE.heightRatio = 1f;
		Templates.FENCE.drawOffsetX = 0f;
		Templates.FENCE.drawOffsetY = 0f;
		Templates.FENCE.soundWhenHit = 1f;
		Templates.FENCE.soundWhenHitting = 1f;
		Templates.FENCE.soundDampening = 1f;
		Templates.FENCE.stackable = false;
		Templates.FENCE.weight = 50f;
		Templates.FENCE.value = 17;
		Templates.FENCE.anchorX = 0;
		Templates.FENCE.anchorY = 0;
		Templates.FENCE.templateId = GameObject.generateNewTemplateId();
	}

}
