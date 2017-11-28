package com.marklynch.objects.templates;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.Storage;
import com.marklynch.utils.ResourceUtils;

public class TemplatesContainers {

	public TemplatesContainers() {

		Templates.CHEST = new Storage();
		Templates.CHEST.name = "Chest";
		Templates.CHEST.baseName = "Chest";
		Templates.CHEST.chestClosedTexture = ResourceUtils.getGlobalImage("chest.png");
		Templates.CHEST.chestOpenTexture = ResourceUtils.getGlobalImage("chest_open.png");
		Templates.CHEST.totalHealth = Templates.CHEST.remainingHealth = 200;
		Templates.CHEST.widthRatio = 1f;
		Templates.CHEST.heightRatio = 1f;
		Templates.CHEST.drawOffsetX = 0f;
		Templates.CHEST.drawOffsetY = 0f;
		Templates.CHEST.soundWhenHit = 1f;
		Templates.CHEST.soundWhenHitting = 1f;
		Templates.CHEST.soundDampening = 1f;
		Templates.CHEST.stackable = false;
		Templates.CHEST.waterResistance = 0f;
		Templates.CHEST.electricResistance = 0f;
		Templates.CHEST.poisonResistance = 0f;
		Templates.CHEST.slashResistance = 0f;
		Templates.CHEST.weight = 100f;
		Templates.CHEST.value = 37;
		Templates.CHEST.anchorX = 0;
		Templates.CHEST.anchorY = 0;
		Templates.CHEST.templateId = GameObject.generateNewTemplateId();

		Templates.CRATE = new Storage();
		Templates.CRATE.name = "Crate";
		Templates.CRATE.baseName = "Crate";
		Templates.CRATE.chestClosedTexture = ResourceUtils.getGlobalImage("crate.png");
		Templates.CRATE.chestOpenTexture = ResourceUtils.getGlobalImage("crate_open.png");
		Templates.CRATE.totalHealth = Templates.CRATE.remainingHealth = 60;
		Templates.CRATE.widthRatio = 1f;
		Templates.CRATE.heightRatio = 1f;
		Templates.CRATE.drawOffsetX = 0f;
		Templates.CRATE.drawOffsetY = 0f;
		Templates.CRATE.soundWhenHit = 1f;
		Templates.CRATE.soundWhenHitting = 1f;
		Templates.CRATE.soundDampening = 1f;
		Templates.CRATE.stackable = false;
		Templates.CRATE.waterResistance = 0f;
		Templates.CRATE.fireResistance = -50f;
		Templates.CRATE.electricResistance = 0f;
		Templates.CRATE.poisonResistance = 0f;
		Templates.CRATE.slashResistance = 0f;
		Templates.CRATE.weight = 100f;
		Templates.CRATE.value = 20;
		Templates.CRATE.anchorX = 0;
		Templates.CRATE.anchorY = 0;
		Templates.CRATE.templateId = GameObject.generateNewTemplateId();

	}

}
