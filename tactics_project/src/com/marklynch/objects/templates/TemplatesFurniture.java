package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.objects.Bed;
import com.marklynch.objects.BigGameObject;
import com.marklynch.objects.BrokenGlass;
import com.marklynch.objects.Furnace;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Key;
import com.marklynch.objects.MapMarker;
import com.marklynch.objects.Searchable;
import com.marklynch.objects.Stampable;
import com.marklynch.objects.SwitchForOpenables;

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

		Templates.KEY = new Key();
		Templates.KEY.name = "Key";
		Templates.KEY.imageTexturePath = "key.png";
		Templates.KEY.totalHealth = Templates.KEY.remainingHealth = 10;
		Templates.KEY.widthRatio = 0.3f;
		Templates.KEY.heightRatio = 0.3f;
		Templates.KEY.drawOffsetX = 0f;
		Templates.KEY.drawOffsetY = 0f;
		Templates.KEY.soundWhenHit = 1f;
		Templates.KEY.soundWhenHitting = 1f;
		Templates.KEY.soundDampening = 1f;
		Templates.KEY.stackable = false;
		Templates.KEY.weight = 5f;
		Templates.KEY.value = 12;
		Templates.KEY.anchorX = 0;
		Templates.KEY.anchorY = 0;
		Templates.KEY.templateId = GameObject.generateNewTemplateId();

		Templates.PLATE = new Stampable();
		Templates.PLATE.name = "Plate";
		Templates.PLATE.imageTexturePath = "plate.png";
		Templates.PLATE.totalHealth = Templates.PLATE.remainingHealth = 6;
		Templates.PLATE.widthRatio = 0.5f;
		Templates.PLATE.heightRatio = 0.5f;
		Templates.PLATE.drawOffsetX = 0f;
		Templates.PLATE.drawOffsetY = 0f;
		Templates.PLATE.soundWhenHit = 1f;
		Templates.PLATE.soundWhenHitting = 1f;
		Templates.PLATE.soundDampening = 1f;
		Templates.PLATE.stackable = false;
		Templates.PLATE.weight = 12f;
		Templates.PLATE.value = 14;
		Templates.PLATE.anchorX = 0;
		Templates.PLATE.anchorY = 0;
		Templates.PLATE.templateId = GameObject.generateNewTemplateId();

		Templates.BROKEN_PLATE = new Stampable();
		Templates.BROKEN_PLATE.name = "Broken Plate";
		Templates.BROKEN_PLATE.imageTexturePath = "broken_plate.png";
		Templates.BROKEN_PLATE.totalHealth = Templates.BROKEN_PLATE.remainingHealth = 4;
		Templates.BROKEN_PLATE.widthRatio = 0.5f;
		Templates.BROKEN_PLATE.heightRatio = 0.5f;
		Templates.BROKEN_PLATE.drawOffsetX = 0f;
		Templates.BROKEN_PLATE.drawOffsetY = 0f;
		Templates.BROKEN_PLATE.soundWhenHit = 1f;
		Templates.BROKEN_PLATE.soundWhenHitting = 1f;
		Templates.BROKEN_PLATE.soundDampening = 1f;
		Templates.BROKEN_PLATE.stackable = false;
		Templates.BROKEN_PLATE.weight = 12f;
		Templates.BROKEN_PLATE.value = 3;
		Templates.BROKEN_PLATE.anchorX = 0;
		Templates.BROKEN_PLATE.anchorY = 0;
		Templates.BROKEN_PLATE.templateId = GameObject.generateNewTemplateId();

		Templates.DINNER_KNIFE = new GameObject();
		Templates.DINNER_KNIFE.name = "Dinner Knife";
		Templates.DINNER_KNIFE.imageTexturePath = "knife.png";
		Templates.DINNER_KNIFE.totalHealth = Templates.DINNER_KNIFE.remainingHealth = 21;
		Templates.DINNER_KNIFE.widthRatio = 1f;
		Templates.DINNER_KNIFE.heightRatio = 1f;
		Templates.DINNER_KNIFE.drawOffsetX = 0.5f;
		Templates.DINNER_KNIFE.drawOffsetY = 0.125f;
		Templates.DINNER_KNIFE.soundWhenHit = 1f;
		Templates.DINNER_KNIFE.soundWhenHitting = 1f;
		Templates.DINNER_KNIFE.soundDampening = 1f;
		Templates.DINNER_KNIFE.stackable = false;
		Templates.DINNER_KNIFE.weight = 6f;
		Templates.DINNER_KNIFE.value = 17;
		Templates.DINNER_KNIFE.anchorX = 0;
		Templates.DINNER_KNIFE.anchorY = 0;
		Templates.DINNER_KNIFE.templateId = GameObject.generateNewTemplateId();

		Templates.DINNER_FORK = new MapMarker();
		Templates.DINNER_FORK.name = "Dinner Fork";
		Templates.DINNER_FORK.imageTexturePath = "fork.png";
		Templates.DINNER_FORK.totalHealth = Templates.DINNER_FORK.remainingHealth = 12;
		Templates.DINNER_FORK.widthRatio = 0.5f;
		Templates.DINNER_FORK.heightRatio = 0.125f;
		Templates.DINNER_FORK.drawOffsetX = 0f;
		Templates.DINNER_FORK.drawOffsetY = 0f;
		Templates.DINNER_FORK.soundWhenHit = 1f;
		Templates.DINNER_FORK.soundWhenHitting = 1f;
		Templates.DINNER_FORK.soundDampening = 1f;
		Templates.DINNER_FORK.stackable = false;
		Templates.DINNER_FORK.weight = 7f;
		Templates.DINNER_FORK.value = 17;
		Templates.DINNER_FORK.anchorX = 0;
		Templates.DINNER_FORK.anchorY = 0;
		Templates.DINNER_FORK.templateId = GameObject.generateNewTemplateId();

		ANTLERS_SWITCH_FOR_OPENABLES = new SwitchForOpenables("Obvious Antlers", 5, "antlers.png", null,
				new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0.5f, 305,
				null, "Touch", "touched", null, null, null, GameObject.generateNewTemplateId());

		FURNACE = new Furnace("Furnace", 200, "furnace.png", null, new Inventory(), 1.5f, 1.5f, -0.25f, -0.5f, 1f, 1f,
				1f, null, 0.5f, 0.5f, false, 100f, 0f, 0f, 0f, 100f, 150f, 402, null, false,
				GameObject.generateNewTemplateId());
		BROKEN_LAMP = new BrokenGlass("Broken Lamp", 5, "smashed_glass.png", null, new Inventory(), 0.5f, 0.5f, 0f, 0f,
				1f, 1f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 3f, 1, null,
				GameObject.generateNewTemplateId());

		BROKEN_GLASS = new BrokenGlass("Broken Glass", 5, "smashed_glass.png", null, new Inventory(), 0.5f, 0.5f, 0f,
				0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 3f, 1, null,
				GameObject.generateNewTemplateId());

		DROP_HOLE = new Searchable("Drop Hole", 5, "drop_hole.png", null, new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 1f,
				null, 0.5f, 0.5f, false, 100f, 0f, 0f, 0f, 0f, 100f, 0, null, new Effect[] { new EffectPoison(3) },
				GameObject.generateNewTemplateId());

	}

}
