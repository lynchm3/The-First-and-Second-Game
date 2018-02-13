package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.objects.Bed;
import com.marklynch.objects.BigGameObject;
import com.marklynch.objects.BrokenGlass;
import com.marklynch.objects.Furnace;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Key;
import com.marklynch.objects.Mirror;
import com.marklynch.objects.PressurePlate;
import com.marklynch.objects.Searchable;
import com.marklynch.objects.Stampable;
import com.marklynch.objects.Switch;
import com.marklynch.objects.WaterSource;

public class TemplatesFurniture {

	public TemplatesFurniture() {

		// Furniture
		Templates.BED = new Bed();
		Templates.BED.name = "Bed";
		Templates.BED.imageTexturePath = "bed.png";
		Templates.BED.imageTextureCovers = getGlobalImage("bed_covers.png", false);
		Templates.BED.totalHealth = Templates.BED.remainingHealth = 50;
		Templates.BED.widthRatio = 1f;
		Templates.BED.heightRatio = 1f;
		Templates.BED.drawOffsetRatioX = 0f;
		Templates.BED.drawOffsetRatioY = 0f;
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

		Templates.MIRROR = new Mirror();
		Templates.MIRROR.name = "Mirror";
		Templates.MIRROR.imageTexturePath = "mirror.png";
		Templates.MIRROR.imageTextureFront = getGlobalImage("mirror.png", true);
		Templates.MIRROR.imageTextureBack = getGlobalImage("mirror_back.png", true);
		Templates.MIRROR.totalHealth = Templates.MIRROR.remainingHealth = 50;
		Templates.MIRROR.widthRatio = 1f;
		Templates.MIRROR.heightRatio = 1.5f;
		Templates.MIRROR.drawOffsetRatioY = -0.5f;
		Templates.MIRROR.soundWhenHit = 10f;
		Templates.MIRROR.stackable = false;
		Templates.MIRROR.weight = 52f;
		Templates.MIRROR.value = 104;
		Templates.MIRROR.templateId = GameObject.generateNewTemplateId();
		Templates.MIRROR.flipYAxisInMirror = false;

		Templates.SHOP_COUNTER = new BigGameObject();
		Templates.SHOP_COUNTER.name = "Shop Counter";
		Templates.SHOP_COUNTER.imageTexturePath = "shop_counter.png";
		Templates.SHOP_COUNTER.totalHealth = Templates.SHOP_COUNTER.remainingHealth = 50;
		Templates.SHOP_COUNTER.widthRatio = 1f;
		Templates.SHOP_COUNTER.heightRatio = 1f;
		Templates.SHOP_COUNTER.drawOffsetRatioX = 0f;
		Templates.SHOP_COUNTER.drawOffsetRatioY = 0f;
		Templates.SHOP_COUNTER.soundWhenHit = 1f;
		Templates.SHOP_COUNTER.soundWhenHitting = 1f;
		Templates.SHOP_COUNTER.soundDampening = 1f;
		Templates.SHOP_COUNTER.stackable = false;
		Templates.SHOP_COUNTER.weight = 67f;
		Templates.SHOP_COUNTER.value = 55;
		Templates.SHOP_COUNTER.anchorX = 0;
		Templates.SHOP_COUNTER.anchorY = 0;
		Templates.SHOP_COUNTER.templateId = GameObject.generateNewTemplateId();
		Templates.SHOP_COUNTER.flipYAxisInMirror = false;

		Templates.TABLE = new BigGameObject();
		Templates.TABLE.name = "Table";
		Templates.TABLE.imageTexturePath = "table.png";
		Templates.TABLE.totalHealth = Templates.TABLE.remainingHealth = 35;
		Templates.TABLE.widthRatio = 1f;
		Templates.TABLE.heightRatio = 1f;
		Templates.TABLE.drawOffsetRatioX = 0f;
		Templates.TABLE.drawOffsetRatioY = 0f;
		Templates.TABLE.soundWhenHit = 1f;
		Templates.TABLE.soundWhenHitting = 1f;
		Templates.TABLE.soundDampening = 1f;
		Templates.TABLE.stackable = false;
		Templates.TABLE.weight = 37f;
		Templates.TABLE.value = 33;
		Templates.TABLE.anchorX = 0;
		Templates.TABLE.anchorY = 0;
		Templates.TABLE.templateId = GameObject.generateNewTemplateId();
		Templates.TABLE.flipYAxisInMirror = false;

		Templates.CHAIR = new BigGameObject();
		Templates.CHAIR.name = "Chair";
		Templates.CHAIR.imageTexturePath = "chair.png";
		Templates.CHAIR.totalHealth = Templates.CHAIR.remainingHealth = 27;
		Templates.CHAIR.widthRatio = 1f;
		Templates.CHAIR.heightRatio = 1f;
		Templates.CHAIR.drawOffsetRatioX = 0f;
		Templates.CHAIR.drawOffsetRatioY = 0f;
		Templates.CHAIR.soundWhenHit = 1f;
		Templates.CHAIR.soundWhenHitting = 1f;
		Templates.CHAIR.soundDampening = 1f;
		Templates.CHAIR.stackable = false;
		Templates.CHAIR.weight = 28f;
		Templates.CHAIR.value = 24;
		Templates.CHAIR.anchorX = 0;
		Templates.CHAIR.anchorY = 0;
		Templates.CHAIR.templateId = GameObject.generateNewTemplateId();
		Templates.CHAIR.flipYAxisInMirror = false;

		Templates.BENCH = new BigGameObject();
		Templates.BENCH.name = "Bench";
		Templates.BENCH.imageTexturePath = "bench.png";
		Templates.BENCH.totalHealth = Templates.BENCH.remainingHealth = 31;
		Templates.BENCH.widthRatio = 1f;
		Templates.BENCH.heightRatio = 1f;
		Templates.BENCH.drawOffsetRatioX = 0f;
		Templates.BENCH.drawOffsetRatioY = 0f;
		Templates.BENCH.soundWhenHit = 1f;
		Templates.BENCH.soundWhenHitting = 1f;
		Templates.BENCH.soundDampening = 1f;
		Templates.BENCH.stackable = false;
		Templates.BENCH.weight = 32f;
		Templates.BENCH.value = 28;
		Templates.BENCH.anchorX = 0;
		Templates.BENCH.anchorY = 0;
		Templates.BENCH.templateId = GameObject.generateNewTemplateId();
		Templates.BENCH.flipYAxisInMirror = false;

		Templates.CHAIR_FALLEN = new BigGameObject();
		Templates.CHAIR_FALLEN.name = "Chair";
		Templates.CHAIR_FALLEN.imageTexturePath = "chair_fallen.png";
		Templates.CHAIR_FALLEN.totalHealth = Templates.CHAIR_FALLEN.remainingHealth = 27;
		Templates.CHAIR_FALLEN.widthRatio = 1f;
		Templates.CHAIR_FALLEN.heightRatio = 1f;
		Templates.CHAIR_FALLEN.drawOffsetRatioX = 0f;
		Templates.CHAIR_FALLEN.drawOffsetRatioY = 0f;
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
		Templates.KEY.drawOffsetRatioX = 0f;
		Templates.KEY.drawOffsetRatioY = 0f;
		Templates.KEY.soundWhenHit = 1f;
		Templates.KEY.soundWhenHitting = 1f;
		Templates.KEY.soundDampening = 1f;
		Templates.KEY.stackable = false;
		Templates.KEY.weight = 5f;
		Templates.KEY.value = 12;
		Templates.KEY.anchorX = 0;
		Templates.KEY.anchorY = 0;
		Templates.KEY.templateId = GameObject.generateNewTemplateId();
		Templates.KEY.pierceDamage = 1;

		Templates.PLATE = new Stampable();
		Templates.PLATE.name = "Plate";
		Templates.PLATE.imageTexturePath = "plate.png";
		Templates.PLATE.totalHealth = Templates.PLATE.remainingHealth = 6;
		Templates.PLATE.widthRatio = 0.5f;
		Templates.PLATE.heightRatio = 0.5f;
		Templates.PLATE.drawOffsetRatioX = 0f;
		Templates.PLATE.drawOffsetRatioY = 0f;
		Templates.PLATE.soundWhenHit = 1f;
		Templates.PLATE.soundWhenHitting = 1f;
		Templates.PLATE.soundDampening = 1f;
		Templates.PLATE.stackable = false;
		Templates.PLATE.weight = 12f;
		Templates.PLATE.value = 14;
		Templates.PLATE.anchorX = 0;
		Templates.PLATE.anchorY = 0;
		Templates.PLATE.templateId = GameObject.generateNewTemplateId();
		Templates.PLATE.slashDamage = 1;

		Templates.BROKEN_PLATE = new Stampable();
		Templates.BROKEN_PLATE.name = "Broken Plate";
		Templates.BROKEN_PLATE.imageTexturePath = "broken_plate.png";
		Templates.BROKEN_PLATE.totalHealth = Templates.BROKEN_PLATE.remainingHealth = 4;
		Templates.BROKEN_PLATE.widthRatio = 0.5f;
		Templates.BROKEN_PLATE.heightRatio = 0.5f;
		Templates.BROKEN_PLATE.drawOffsetRatioX = 0f;
		Templates.BROKEN_PLATE.drawOffsetRatioY = 0f;
		Templates.BROKEN_PLATE.soundWhenHit = 1f;
		Templates.BROKEN_PLATE.soundWhenHitting = 1f;
		Templates.BROKEN_PLATE.soundDampening = 1f;
		Templates.BROKEN_PLATE.stackable = false;
		Templates.BROKEN_PLATE.weight = 12f;
		Templates.BROKEN_PLATE.value = 3;
		Templates.BROKEN_PLATE.anchorX = 0;
		Templates.BROKEN_PLATE.anchorY = 0;
		Templates.BROKEN_PLATE.templateId = GameObject.generateNewTemplateId();
		Templates.BROKEN_PLATE.slashDamage = 1;

		Templates.DINNER_KNIFE = new GameObject();
		Templates.DINNER_KNIFE.name = "Dinner Knife";
		Templates.DINNER_KNIFE.imageTexturePath = "knife.png";
		Templates.DINNER_KNIFE.totalHealth = Templates.DINNER_KNIFE.remainingHealth = 21;
		Templates.DINNER_KNIFE.widthRatio = 1f;
		Templates.DINNER_KNIFE.heightRatio = 1f;
		Templates.DINNER_KNIFE.drawOffsetRatioX = 0.5f;
		Templates.DINNER_KNIFE.drawOffsetRatioY = 0.125f;
		Templates.DINNER_KNIFE.soundWhenHit = 1f;
		Templates.DINNER_KNIFE.soundWhenHitting = 1f;
		Templates.DINNER_KNIFE.soundDampening = 1f;
		Templates.DINNER_KNIFE.stackable = false;
		Templates.DINNER_KNIFE.weight = 6f;
		Templates.DINNER_KNIFE.value = 17;
		Templates.DINNER_KNIFE.anchorX = 0;
		Templates.DINNER_KNIFE.anchorY = 0;
		Templates.DINNER_KNIFE.templateId = GameObject.generateNewTemplateId();
		Templates.DINNER_KNIFE.slashDamage = 1;

		Templates.DINNER_FORK = new GameObject();
		Templates.DINNER_FORK.name = "Dinner Fork";
		Templates.DINNER_FORK.imageTexturePath = "fork.png";
		Templates.DINNER_FORK.totalHealth = Templates.DINNER_FORK.remainingHealth = 12;
		Templates.DINNER_FORK.widthRatio = 0.5f;
		Templates.DINNER_FORK.heightRatio = 0.125f;
		Templates.DINNER_FORK.drawOffsetRatioX = 0f;
		Templates.DINNER_FORK.drawOffsetRatioY = 0f;
		Templates.DINNER_FORK.soundWhenHit = 1f;
		Templates.DINNER_FORK.soundWhenHitting = 1f;
		Templates.DINNER_FORK.soundDampening = 1f;
		Templates.DINNER_FORK.stackable = false;
		Templates.DINNER_FORK.weight = 7f;
		Templates.DINNER_FORK.value = 17;
		Templates.DINNER_FORK.anchorX = 0;
		Templates.DINNER_FORK.anchorY = 0;
		Templates.DINNER_FORK.templateId = GameObject.generateNewTemplateId();
		Templates.DINNER_FORK.slashDamage = 1;

		Templates.ANTLERS_SWITCH = new Switch();
		Templates.ANTLERS_SWITCH.name = "Obvious Antlers";
		Templates.ANTLERS_SWITCH.imageTexturePath = "antlers.png";
		Templates.ANTLERS_SWITCH.totalHealth = Templates.ANTLERS_SWITCH.remainingHealth = 36;
		Templates.ANTLERS_SWITCH.widthRatio = 1f;
		Templates.ANTLERS_SWITCH.heightRatio = 1f;
		Templates.ANTLERS_SWITCH.drawOffsetRatioX = 0f;
		Templates.ANTLERS_SWITCH.drawOffsetRatioY = 0f;
		Templates.ANTLERS_SWITCH.soundWhenHit = 1f;
		Templates.ANTLERS_SWITCH.soundWhenHitting = 1f;
		Templates.ANTLERS_SWITCH.soundDampening = 1f;
		Templates.ANTLERS_SWITCH.stackable = false;
		Templates.ANTLERS_SWITCH.weight = 43f;
		Templates.ANTLERS_SWITCH.value = 130;
		Templates.ANTLERS_SWITCH.anchorX = 0;
		Templates.ANTLERS_SWITCH.anchorY = 0;
		Templates.ANTLERS_SWITCH.templateId = GameObject.generateNewTemplateId();
		Templates.ANTLERS_SWITCH.actionName = "Touch";
		Templates.ANTLERS_SWITCH.actionVerb = "touched";

		Templates.PRESSURE_PLATE = new PressurePlate();
		Templates.PRESSURE_PLATE.name = "Pressure Plate";
		Templates.PRESSURE_PLATE.imageTexturePath = "pressure_plate.png";
		Templates.PRESSURE_PLATE.totalHealth = Templates.PRESSURE_PLATE.remainingHealth = 1;
		Templates.PRESSURE_PLATE.widthRatio = 1f;
		Templates.PRESSURE_PLATE.heightRatio = 1f;
		Templates.PRESSURE_PLATE.drawOffsetRatioX = 0f;
		Templates.PRESSURE_PLATE.drawOffsetRatioY = 0f;
		Templates.PRESSURE_PLATE.soundWhenHit = 1f;
		Templates.PRESSURE_PLATE.soundWhenHitting = 1f;
		Templates.PRESSURE_PLATE.soundDampening = 1f;
		Templates.PRESSURE_PLATE.stackable = false;
		Templates.PRESSURE_PLATE.weight = 0f;
		Templates.PRESSURE_PLATE.value = 27;
		Templates.PRESSURE_PLATE.anchorX = 0;
		Templates.PRESSURE_PLATE.anchorY = 0;
		Templates.PRESSURE_PLATE.templateId = GameObject.generateNewTemplateId();
		Templates.PRESSURE_PLATE.actionName = "Trigger";
		Templates.PRESSURE_PLATE.actionVerb = "triggered";

		Templates.FURNACE = new Furnace();
		Templates.FURNACE.name = "FURNACE";
		Templates.FURNACE.imageTexturePath = "furnace.png";
		Templates.FURNACE.totalHealth = Templates.FURNACE.remainingHealth = 122;
		Templates.FURNACE.widthRatio = 1f;
		Templates.FURNACE.heightRatio = 1f;
		Templates.FURNACE.drawOffsetRatioX = 0f;
		Templates.FURNACE.drawOffsetRatioY = 0f;
		Templates.FURNACE.soundWhenHit = 1f;
		Templates.FURNACE.soundWhenHitting = 1f;
		Templates.FURNACE.soundDampening = 1f;
		Templates.FURNACE.stackable = false;
		Templates.FURNACE.weight = 165f;
		Templates.FURNACE.value = 142;
		Templates.FURNACE.anchorX = 0;
		Templates.FURNACE.anchorY = 0;
		Templates.FURNACE.templateId = GameObject.generateNewTemplateId();

		Templates.BROKEN_LAMP = new BrokenGlass();
		Templates.BROKEN_LAMP.name = "Broken Lamp";
		Templates.BROKEN_LAMP.imageTexturePath = "smashed_glass.png";
		Templates.BROKEN_LAMP.totalHealth = Templates.BROKEN_LAMP.remainingHealth = 11;
		Templates.BROKEN_LAMP.widthRatio = 1f;
		Templates.BROKEN_LAMP.heightRatio = 1f;
		Templates.BROKEN_LAMP.drawOffsetRatioX = 0f;
		Templates.BROKEN_LAMP.drawOffsetRatioY = 0f;
		Templates.BROKEN_LAMP.soundWhenHit = 1f;
		Templates.BROKEN_LAMP.soundWhenHitting = 1f;
		Templates.BROKEN_LAMP.soundDampening = 1f;
		Templates.BROKEN_LAMP.stackable = false;
		Templates.BROKEN_LAMP.weight = 23f;
		Templates.BROKEN_LAMP.value = 2;
		Templates.BROKEN_LAMP.anchorX = 0;
		Templates.BROKEN_LAMP.anchorY = 0;
		Templates.BROKEN_LAMP.templateId = GameObject.generateNewTemplateId();
		Templates.BROKEN_LAMP.slashDamage = 2;

		Templates.BROKEN_GLASS = new BrokenGlass();
		Templates.BROKEN_GLASS.name = "Broken Glass";
		Templates.BROKEN_GLASS.imageTexturePath = "smashed_glass.png";
		Templates.BROKEN_GLASS.totalHealth = Templates.BROKEN_GLASS.remainingHealth = 12;
		Templates.BROKEN_GLASS.widthRatio = 1f;
		Templates.BROKEN_GLASS.heightRatio = 1f;
		Templates.BROKEN_GLASS.drawOffsetRatioX = 0f;
		Templates.BROKEN_GLASS.drawOffsetRatioY = 0f;
		Templates.BROKEN_GLASS.soundWhenHit = 1f;
		Templates.BROKEN_GLASS.soundWhenHitting = 1f;
		Templates.BROKEN_GLASS.soundDampening = 1f;
		Templates.BROKEN_GLASS.stackable = false;
		Templates.BROKEN_GLASS.weight = 16f;
		Templates.BROKEN_GLASS.value = 1;
		Templates.BROKEN_GLASS.anchorX = 0;
		Templates.BROKEN_GLASS.anchorY = 0;
		Templates.BROKEN_GLASS.templateId = GameObject.generateNewTemplateId();
		Templates.BROKEN_GLASS.slashDamage = 2;

		Templates.DROP_HOLE = new Searchable();
		Templates.DROP_HOLE.name = "Drop Hole";
		Templates.DROP_HOLE.imageTexturePath = "drop_hole.png";
		Templates.DROP_HOLE.totalHealth = Templates.DROP_HOLE.remainingHealth = 100;
		Templates.DROP_HOLE.widthRatio = 1f;
		Templates.DROP_HOLE.heightRatio = 1f;
		Templates.DROP_HOLE.drawOffsetRatioX = 0f;
		Templates.DROP_HOLE.drawOffsetRatioY = 0f;
		Templates.DROP_HOLE.soundWhenHit = 1f;
		Templates.DROP_HOLE.soundWhenHitting = 1f;
		Templates.DROP_HOLE.soundDampening = 1f;
		Templates.DROP_HOLE.stackable = false;
		Templates.DROP_HOLE.weight = 1f;
		Templates.DROP_HOLE.value = 1;
		Templates.DROP_HOLE.anchorX = 0;
		Templates.DROP_HOLE.anchorY = 0;
		Templates.DROP_HOLE.templateId = GameObject.generateNewTemplateId();
		Templates.DROP_HOLE.effectsFromInteracting = new Effect[] { new EffectPoison(3) };

		Templates.SHELF = new GameObject();
		Templates.SHELF.name = "Shelf";
		Templates.SHELF.imageTexturePath = "shelf.png";
		Templates.SHELF.totalHealth = Templates.SHELF.remainingHealth = 28;
		Templates.SHELF.widthRatio = 1f;
		Templates.SHELF.heightRatio = 0.5f;
		Templates.SHELF.drawOffsetRatioX = 0f;
		Templates.SHELF.drawOffsetRatioY = 0f;
		Templates.SHELF.soundWhenHit = 1f;
		Templates.SHELF.soundWhenHitting = 1f;
		Templates.SHELF.soundDampening = 1f;
		Templates.SHELF.stackable = false;
		Templates.SHELF.weight = 27f;
		Templates.SHELF.value = 21;
		Templates.SHELF.anchorX = 0;
		Templates.SHELF.anchorY = 0;
		Templates.SHELF.templateId = GameObject.generateNewTemplateId();

		Templates.WELL = new WaterSource();
		Templates.WELL.name = "Well";
		Templates.WELL.imageTexturePath = "well.png";
		Templates.WELL.totalHealth = Templates.WELL.remainingHealth = 138;
		Templates.WELL.widthRatio = 1.5f;
		Templates.WELL.heightRatio = 1.5f;
		Templates.WELL.drawOffsetRatioX = -0.25f;
		Templates.WELL.drawOffsetRatioY = -0.25f;
		Templates.WELL.soundWhenHit = 1f;
		Templates.WELL.soundWhenHitting = 1f;
		Templates.WELL.soundDampening = 1f;
		Templates.WELL.stackable = false;
		Templates.WELL.weight = 213f;
		Templates.WELL.value = 89;
		Templates.WELL.anchorX = 0;
		Templates.WELL.anchorY = 0;
		Templates.WELL.templateId = GameObject.generateNewTemplateId();
		Templates.WELL.effectsFromInteracting = new Effect[] {};

	}

}
