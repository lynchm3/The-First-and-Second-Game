package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.objects.Bed;
import com.marklynch.objects.BigGameObject;
import com.marklynch.objects.GameObject;

public class TemplatesFurniture {

	public TemplatesFurniture() {

		// Furniture
		Templates.BED = new Bed();
		Templates.BED.name = "Bed";
		Templates.BED.imageTexturePath = "bed.png";
		Templates.BED.imageTextureCovers = getGlobalImage("bed_covers.png");
		Templates.BED.totalHealth = Templates.BED.remainingHealth = 50;
		Templates.BED.widthRatio = 1f;
		Templates.BED.heightRatio = 1f;
		Templates.BED.drawOffsetX = 0f;
		Templates.BED.drawOffsetY = 0f;
		Templates.BED.soundWhenHit = 1f;
		Templates.BED.soundWhenHitting = 1f;
		Templates.BED.soundDampening = 1f;
		Templates.BED.stackable = false;
		Templates.BED.waterResistance = 0f;
		Templates.BED.electricResistance = 0f;
		Templates.BED.poisonResistance = 0f;
		Templates.BED.slashResistance = 0f;
		Templates.BED.weight = 75f;
		Templates.BED.value = 150;
		Templates.BED.anchorX = 0;
		Templates.BED.anchorY = 0;
		Templates.BED.templateId = GameObject.generateNewTemplateId();

		Templates.SHOP_COUNTER = new BigGameObject();
		Templates.SHOP_COUNTER.name = "Shop Counter";
		Templates.SHOP_COUNTER.imageTexturePath = "shop_counter.png";
		Templates.SHOP_COUNTER.totalHealth = Templates.SHOP_COUNTER.remainingHealth = 50;
		Templates.SHOP_COUNTER.widthRatio = 1f;
		Templates.SHOP_COUNTER.heightRatio = 1f;
		Templates.SHOP_COUNTER.drawOffsetX = 0f;
		Templates.SHOP_COUNTER.drawOffsetY = 0f;
		Templates.SHOP_COUNTER.soundWhenHit = 1f;
		Templates.SHOP_COUNTER.soundWhenHitting = 1f;
		Templates.SHOP_COUNTER.soundDampening = 1f;
		Templates.SHOP_COUNTER.stackable = false;
		Templates.SHOP_COUNTER.weight = 67f;
		Templates.SHOP_COUNTER.value = 55;
		Templates.SHOP_COUNTER.anchorX = 0;
		Templates.SHOP_COUNTER.anchorY = 0;
		Templates.SHOP_COUNTER.templateId = GameObject.generateNewTemplateId();

		Templates.TABLE = new BigGameObject();
		Templates.TABLE.name = "Table";
		Templates.TABLE.imageTexturePath = "table.png";
		Templates.TABLE.totalHealth = Templates.TABLE.remainingHealth = 35;
		Templates.TABLE.widthRatio = 1f;
		Templates.TABLE.heightRatio = 1f;
		Templates.TABLE.drawOffsetX = 0f;
		Templates.TABLE.drawOffsetY = 0f;
		Templates.TABLE.soundWhenHit = 1f;
		Templates.TABLE.soundWhenHitting = 1f;
		Templates.TABLE.soundDampening = 1f;
		Templates.TABLE.stackable = false;
		Templates.TABLE.weight = 37f;
		Templates.TABLE.value = 33;
		Templates.TABLE.anchorX = 0;
		Templates.TABLE.anchorY = 0;
		Templates.TABLE.templateId = GameObject.generateNewTemplateId();

		Templates.CHAIR = new BigGameObject();
		Templates.CHAIR.name = "Chair";
		Templates.CHAIR.imageTexturePath = "chair.png";
		Templates.CHAIR.totalHealth = Templates.CHAIR.remainingHealth = 27;
		Templates.CHAIR.widthRatio = 1f;
		Templates.CHAIR.heightRatio = 1f;
		Templates.CHAIR.drawOffsetX = 0f;
		Templates.CHAIR.drawOffsetY = 0f;
		Templates.CHAIR.soundWhenHit = 1f;
		Templates.CHAIR.soundWhenHitting = 1f;
		Templates.CHAIR.soundDampening = 1f;
		Templates.CHAIR.stackable = false;
		Templates.CHAIR.weight = 28f;
		Templates.CHAIR.value = 24;
		Templates.CHAIR.anchorX = 0;
		Templates.CHAIR.anchorY = 0;
		Templates.CHAIR.templateId = GameObject.generateNewTemplateId();

		Templates.BENCH = new BigGameObject();
		Templates.BENCH.name = "Bench";
		Templates.BENCH.imageTexturePath = "bench.png";
		Templates.BENCH.totalHealth = Templates.BENCH.remainingHealth = 31;
		Templates.BENCH.widthRatio = 1f;
		Templates.BENCH.heightRatio = 1f;
		Templates.BENCH.drawOffsetX = 0f;
		Templates.BENCH.drawOffsetY = 0f;
		Templates.BENCH.soundWhenHit = 1f;
		Templates.BENCH.soundWhenHitting = 1f;
		Templates.BENCH.soundDampening = 1f;
		Templates.BENCH.stackable = false;
		Templates.BENCH.weight = 32f;
		Templates.BENCH.value = 28;
		Templates.BENCH.anchorX = 0;
		Templates.BENCH.anchorY = 0;
		Templates.BENCH.templateId = GameObject.generateNewTemplateId();

		Templates.CHAIR_FALLEN = new BigGameObject();
		Templates.CHAIR_FALLEN.name = "Chair";
		Templates.CHAIR_FALLEN.imageTexturePath = "chair_fallen.png";
		Templates.CHAIR_FALLEN.totalHealth = Templates.CHAIR_FALLEN.remainingHealth = 27;
		Templates.CHAIR_FALLEN.widthRatio = 1f;
		Templates.CHAIR_FALLEN.heightRatio = 1f;
		Templates.CHAIR_FALLEN.drawOffsetX = 0f;
		Templates.CHAIR_FALLEN.drawOffsetY = 0f;
		Templates.CHAIR_FALLEN.soundWhenHit = 1f;
		Templates.CHAIR_FALLEN.soundWhenHitting = 1f;
		Templates.CHAIR_FALLEN.soundDampening = 1f;
		Templates.CHAIR_FALLEN.stackable = false;
		Templates.CHAIR_FALLEN.weight = 28f;
		Templates.CHAIR_FALLEN.value = 24;
		Templates.CHAIR_FALLEN.anchorX = 0;
		Templates.CHAIR_FALLEN.anchorY = 0;
		Templates.CHAIR_FALLEN.templateId = GameObject.generateNewTemplateId();

	}

}
