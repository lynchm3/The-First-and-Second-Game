package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
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
import com.marklynch.objects.PressurePlateRequiringSpecificItem;
import com.marklynch.objects.Searchable;
import com.marklynch.objects.Seesaw;
import com.marklynch.objects.Stampable;
import com.marklynch.objects.WallSupport;
import com.marklynch.objects.Switch;
import com.marklynch.objects.WaterSource;

public class TemplatesFurniture {

	public TemplatesFurniture() {

		// Furniture
		Templates.BED = new Bed();
		Templates.BED.name = "Bed";
		Templates.BED.setImageAndExtrapolateSize("bed.png");
		Templates.BED.imageTextureCovers = getGlobalImage("bed_covers.png", false);
		Templates.BED.totalHealth = Templates.BED.remainingHealth = 50;
		Templates.BED.weight = 75f;
		Templates.BED.value = 150;
		Templates.BED.anchorX = 0;
		Templates.BED.anchorY = 0;
		Templates.BED.templateId = GameObject.generateNewTemplateId();

		Templates.MIRROR = new Mirror();
		Templates.MIRROR.name = "Mirror";
		Templates.MIRROR.setImageAndExtrapolateSize("mirror.png");
		Templates.MIRROR.imageTexture = getGlobalImage(Templates.MIRROR.imageTexturePath, true);
		Templates.MIRROR.imageTextureFront = getGlobalImage("mirror.png", true);
		Templates.MIRROR.imageTextureBack = getGlobalImage("mirror_back.png", true);
		Templates.MIRROR.imageTextureCrack = getGlobalImage("mirror_crack.png", true);
		Templates.MIRROR.totalHealth = Templates.MIRROR.remainingHealth = 50;
		Templates.MIRROR.drawOffsetRatioY = -0.5f;
		Templates.MIRROR.soundWhenHit = 10f;
		Templates.MIRROR.stackable = false;
		Templates.MIRROR.weight = 52f;
		Templates.MIRROR.value = 104;
		Templates.MIRROR.templateId = GameObject.generateNewTemplateId();
		Templates.MIRROR.flipYAxisInMirror = false;

		Templates.SHOP_COUNTER = new BigGameObject();
		Templates.SHOP_COUNTER.name = "Shop Counter";
		Templates.SHOP_COUNTER.setImageAndExtrapolateSize("shop_counter.png");
		Templates.SHOP_COUNTER.totalHealth = Templates.SHOP_COUNTER.remainingHealth = 50;
		Templates.SHOP_COUNTER.weight = 67f;
		Templates.SHOP_COUNTER.value = 55;
		Templates.SHOP_COUNTER.anchorX = 0;
		Templates.SHOP_COUNTER.anchorY = 0;
		Templates.SHOP_COUNTER.templateId = GameObject.generateNewTemplateId();
		Templates.SHOP_COUNTER.flipYAxisInMirror = false;

		Templates.TABLE = new BigGameObject();
		Templates.TABLE.name = "Table";
		Templates.TABLE.setImageAndExtrapolateSize("table.png");
		Templates.TABLE.totalHealth = Templates.TABLE.remainingHealth = 35;
		Templates.TABLE.weight = 37f;
		Templates.TABLE.value = 33;
		Templates.TABLE.anchorX = 0;
		Templates.TABLE.anchorY = 0;
		Templates.TABLE.templateId = GameObject.generateNewTemplateId();
		Templates.TABLE.flipYAxisInMirror = false;

		Templates.CHAIR = new BigGameObject();
		Templates.CHAIR.name = "Chair";
		Templates.CHAIR.setImageAndExtrapolateSize("chair.png");
		Templates.CHAIR.totalHealth = Templates.CHAIR.remainingHealth = 27;
		Templates.CHAIR.weight = 28f;
		Templates.CHAIR.value = 24;
		Templates.CHAIR.anchorX = 0;
		Templates.CHAIR.anchorY = 0;
		Templates.CHAIR.templateId = GameObject.generateNewTemplateId();
		Templates.CHAIR.flipYAxisInMirror = false;

		Templates.BENCH = new BigGameObject();
		Templates.BENCH.name = "Bench";
		Templates.BENCH.setImageAndExtrapolateSize("bench.png");
		Templates.BENCH.totalHealth = Templates.BENCH.remainingHealth = 31;
		Templates.BENCH.weight = 32f;
		Templates.BENCH.value = 28;
		Templates.BENCH.anchorX = 0;
		Templates.BENCH.anchorY = 0;
		Templates.BENCH.templateId = GameObject.generateNewTemplateId();
		Templates.BENCH.flipYAxisInMirror = false;

		Templates.CHAIR_FALLEN = new BigGameObject();
		Templates.CHAIR_FALLEN.name = "Chair";
		Templates.CHAIR_FALLEN.setImageAndExtrapolateSize("chair_fallen.png");
		Templates.CHAIR_FALLEN.totalHealth = Templates.CHAIR_FALLEN.remainingHealth = 27;
		Templates.CHAIR_FALLEN.weight = 28f;
		Templates.CHAIR_FALLEN.value = 24;
		Templates.CHAIR_FALLEN.anchorX = 0;
		Templates.CHAIR_FALLEN.anchorY = 0;
		Templates.CHAIR_FALLEN.templateId = GameObject.generateNewTemplateId();

		Templates.KEY = new Key();
		Templates.KEY.name = "Key";
		Templates.KEY.setImageAndExtrapolateSize("key.png");
		Templates.KEY.totalHealth = Templates.KEY.remainingHealth = 10;
		Templates.KEY.weight = 5f;
		Templates.KEY.value = 12;
		Templates.KEY.anchorX = 6;
		Templates.KEY.anchorY = 6;
		Templates.KEY.templateId = GameObject.generateNewTemplateId();
		Templates.KEY.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, new Stat(1));

		Templates.PLATE = new Stampable();
		Templates.PLATE.name = "Plate";
		Templates.PLATE.setImageAndExtrapolateSize("plate.png");
		Templates.PLATE.totalHealth = Templates.PLATE.remainingHealth = 6;
		Templates.PLATE.weight = 12f;
		Templates.PLATE.value = 14;
		Templates.PLATE.anchorX = 16;
		Templates.PLATE.anchorY = 16;
		Templates.PLATE.templateId = GameObject.generateNewTemplateId();
		Templates.PLATE.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(1));

		Templates.BROKEN_PLATE = new Stampable();
		Templates.BROKEN_PLATE.name = "Broken Plate";
		Templates.BROKEN_PLATE.setImageAndExtrapolateSize("broken_plate.png");
		Templates.BROKEN_PLATE.totalHealth = Templates.BROKEN_PLATE.remainingHealth = 4;
		Templates.BROKEN_PLATE.weight = 12f;
		Templates.BROKEN_PLATE.value = 3;
		Templates.BROKEN_PLATE.anchorX = 16;
		Templates.BROKEN_PLATE.anchorY = 16;
		Templates.BROKEN_PLATE.templateId = GameObject.generateNewTemplateId();
		Templates.BROKEN_PLATE.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(1));

		Templates.DINNER_KNIFE = new GameObject();
		Templates.DINNER_KNIFE.name = "Dinner Knife";
		Templates.DINNER_KNIFE.setImageAndExtrapolateSize("knife.png");
		Templates.DINNER_KNIFE.totalHealth = Templates.DINNER_KNIFE.remainingHealth = 21;
		Templates.DINNER_KNIFE.weight = 6f;
		Templates.DINNER_KNIFE.value = 17;
		Templates.DINNER_KNIFE.anchorX = 4;
		Templates.DINNER_KNIFE.anchorY = 4;
		Templates.DINNER_KNIFE.templateId = GameObject.generateNewTemplateId();
		Templates.DINNER_KNIFE.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(2));

		Templates.DINNER_FORK = new GameObject();
		Templates.DINNER_FORK.name = "Dinner Fork";
		Templates.DINNER_FORK.setImageAndExtrapolateSize("fork.png");
		Templates.DINNER_FORK.totalHealth = Templates.DINNER_FORK.remainingHealth = 12;
		Templates.DINNER_FORK.weight = 7f;
		Templates.DINNER_FORK.value = 17;
		Templates.DINNER_FORK.anchorX = 4;
		Templates.DINNER_FORK.anchorY = 4;
		Templates.DINNER_FORK.templateId = GameObject.generateNewTemplateId();
		Templates.DINNER_FORK.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_DAMAGE, new Stat(1));

		Templates.ANTLERS_SWITCH = new Switch();
		Templates.ANTLERS_SWITCH.name = "Obvious Antlers";
		Templates.ANTLERS_SWITCH.setImageAndExtrapolateSize("antlers.png");
		Templates.ANTLERS_SWITCH.imageTexturePath = "antlers.png";
		Templates.ANTLERS_SWITCH.totalHealth = Templates.ANTLERS_SWITCH.remainingHealth = 36;
		Templates.ANTLERS_SWITCH.weight = 43f;
		Templates.ANTLERS_SWITCH.value = 130;
		Templates.ANTLERS_SWITCH.anchorX = 0;
		Templates.ANTLERS_SWITCH.anchorY = 0;
		Templates.ANTLERS_SWITCH.templateId = GameObject.generateNewTemplateId();
		Templates.ANTLERS_SWITCH.actionName = "Touch";
		Templates.ANTLERS_SWITCH.actionVerb = "touched";

		Templates.PRESSURE_PLATE = new PressurePlate();
		Templates.PRESSURE_PLATE.name = "Pressure Plate";
		Templates.PRESSURE_PLATE.totalHealth = Templates.PRESSURE_PLATE.remainingHealth = 1;
		Templates.PRESSURE_PLATE.setImageAndExtrapolateSize("pressure_plate.png");
		Templates.PRESSURE_PLATE.weight = 0f;
		Templates.PRESSURE_PLATE.value = 27;
		Templates.PRESSURE_PLATE.templateId = GameObject.generateNewTemplateId();
		Templates.PRESSURE_PLATE.actionName = "Trigger";
		Templates.PRESSURE_PLATE.actionVerb = "triggered";

		Templates.PRESSURE_PLATE_REQUIRING_SPECIFIC_ITEM = new PressurePlateRequiringSpecificItem();
		Templates.PRESSURE_PLATE_REQUIRING_SPECIFIC_ITEM.name = "Pressure Plate with inlet";
		Templates.PRESSURE_PLATE_REQUIRING_SPECIFIC_ITEM.totalHealth = Templates.PRESSURE_PLATE_REQUIRING_SPECIFIC_ITEM.remainingHealth = 1;
		Templates.PRESSURE_PLATE_REQUIRING_SPECIFIC_ITEM.setImageAndExtrapolateSize("pressure_plate.png");
		Templates.PRESSURE_PLATE_REQUIRING_SPECIFIC_ITEM.weight = 0f;
		Templates.PRESSURE_PLATE_REQUIRING_SPECIFIC_ITEM.value = 39;
		Templates.PRESSURE_PLATE_REQUIRING_SPECIFIC_ITEM.templateId = GameObject.generateNewTemplateId();
		Templates.PRESSURE_PLATE_REQUIRING_SPECIFIC_ITEM.actionName = "Trigger";
		Templates.PRESSURE_PLATE_REQUIRING_SPECIFIC_ITEM.actionVerb = "triggered";

		Templates.SEESAW = new Seesaw();
		Templates.SEESAW.name = "Seesaw";
		Templates.SEESAW.imageTexture = getGlobalImage("seesaw_middle.png", true);
		Seesaw.gradientRightUp = getGlobalImage("seesaw_gradient_right_up.png", false);
		Seesaw.gradientLeftUp = getGlobalImage("seesaw_gradient_left_up.png", false);
		Templates.SEESAW.totalHealth = Templates.SEESAW.remainingHealth = 1;
		Templates.SEESAW.value = 33;
		Templates.SEESAW.templateId = GameObject.generateNewTemplateId();

		Templates.SEESAW_PART = new Seesaw.SeesawPart();
		Templates.SEESAW_PART.name = "Seesaw";
		// Templates.SEESAW_PART.imageTexturePath = "SEESAW_PART.png";
		Templates.SEESAW_PART.imageTexture = getGlobalImage("seesaw_part.png", true);
		Templates.SEESAW_PART.totalHealth = Templates.SEESAW_PART.remainingHealth = 1;
		Templates.SEESAW_PART.value = 22;
		Templates.SEESAW_PART.templateId = GameObject.generateNewTemplateId();
		Templates.SEESAW_PART.actionName = "Trigger";
		Templates.SEESAW_PART.actionVerb = "triggered";

		Templates.FURNACE = new Furnace();
		Templates.FURNACE.name = "FURNACE";
		Templates.FURNACE.setImageAndExtrapolateSize("furnace.png");
		Templates.FURNACE.totalHealth = Templates.FURNACE.remainingHealth = 122;
		Templates.FURNACE.weight = 165f;
		Templates.FURNACE.value = 142;
		Templates.FURNACE.anchorX = 0;
		Templates.FURNACE.anchorY = 0;
		Templates.FURNACE.templateId = GameObject.generateNewTemplateId();

		Templates.BROKEN_LAMP = new BrokenGlass();
		Templates.BROKEN_LAMP.name = "Broken Lamp";
		Templates.BROKEN_LAMP.setImageAndExtrapolateSize("smashed_glass.png");
		Templates.BROKEN_LAMP.totalHealth = Templates.BROKEN_LAMP.remainingHealth = 11;
		Templates.BROKEN_LAMP.weight = 23f;
		Templates.BROKEN_LAMP.value = 2;
		Templates.BROKEN_LAMP.anchorX = 0;
		Templates.BROKEN_LAMP.anchorY = 0;
		Templates.BROKEN_LAMP.templateId = GameObject.generateNewTemplateId();
		Templates.BROKEN_LAMP.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(2));

		Templates.BROKEN_GLASS = new BrokenGlass();
		Templates.BROKEN_GLASS.name = "Broken Glass";
		Templates.BROKEN_GLASS.setImageAndExtrapolateSize("smashed_glass.png");
		Templates.BROKEN_GLASS.totalHealth = Templates.BROKEN_GLASS.remainingHealth = 12;
		Templates.BROKEN_GLASS.weight = 16f;
		Templates.BROKEN_GLASS.value = 1;
		Templates.BROKEN_GLASS.anchorX = 0;
		Templates.BROKEN_GLASS.anchorY = 0;
		Templates.BROKEN_GLASS.templateId = GameObject.generateNewTemplateId();
		Templates.BROKEN_GLASS.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_DAMAGE, new Stat(2));

		Templates.DROP_HOLE = new Searchable();
		Templates.DROP_HOLE.name = "Drop Hole";
		Templates.DROP_HOLE.setImageAndExtrapolateSize("drop_hole.png");
		Templates.DROP_HOLE.totalHealth = Templates.DROP_HOLE.remainingHealth = 100;
		Templates.DROP_HOLE.weight = 1f;
		Templates.DROP_HOLE.value = 1;
		Templates.DROP_HOLE.anchorX = 0;
		Templates.DROP_HOLE.anchorY = 0;
		Templates.DROP_HOLE.templateId = GameObject.generateNewTemplateId();
		Templates.DROP_HOLE.effectsFromInteracting = new Effect[] { new EffectPoison(3) };
		Templates.DROP_HOLE.isFloorObject = true;
		Templates.DROP_HOLE.moveable = false;

		Templates.SHELF = new GameObject();
		Templates.SHELF.name = "Shelf";
		Templates.SHELF.setImageAndExtrapolateSize("shelf.png");
		Templates.SHELF.totalHealth = Templates.SHELF.remainingHealth = 28;
		Templates.SHELF.weight = 27f;
		Templates.SHELF.value = 21;
		Templates.SHELF.anchorX = 32;
		Templates.SHELF.anchorY = 8;
		Templates.SHELF.templateId = GameObject.generateNewTemplateId();

		Templates.WELL = new WaterSource();
		Templates.WELL.name = "Well";
		Templates.WELL.setImageAndExtrapolateSize("well.png");
		Templates.WELL.totalHealth = Templates.WELL.remainingHealth = 138;
		Templates.WELL.weight = 213f;
		Templates.WELL.value = 89;
		Templates.WELL.anchorX = 0;
		Templates.WELL.anchorY = 0;
		Templates.WELL.templateId = GameObject.generateNewTemplateId();
		Templates.WELL.effectsFromInteracting = new Effect[] {};
		Templates.WELL.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_RES, new Stat(100));
		Templates.WELL.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_RES, new Stat(100));
		Templates.WELL.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_RES, new Stat(100));
		Templates.WELL.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(100));
		Templates.WELL.highLevelStats.put(HIGH_LEVEL_STATS.WATER_RES, new Stat(100));
		Templates.WELL.highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_RES, new Stat(100));
		Templates.WELL.highLevelStats.put(HIGH_LEVEL_STATS.POISON_RES, new Stat(100));
		Templates.WELL.highLevelStats.put(HIGH_LEVEL_STATS.BLEED_RES, new Stat(100));
		Templates.WELL.highLevelStats.put(HIGH_LEVEL_STATS.HEALING_RES, new Stat(100));

		Templates.WOODEN_SUPPORT = new WallSupport();
		Templates.WOODEN_SUPPORT.setImageAndExtrapolateSize("wooden_support.png");
		Templates.WOODEN_SUPPORT.totalHealth = Templates.WOODEN_SUPPORT.remainingHealth = 10;
		Templates.WOODEN_SUPPORT.drawOffsetRatioX = -0.25f;
		Templates.WOODEN_SUPPORT.soundWhenHit = 10f;
		Templates.WOODEN_SUPPORT.weight = 112f;
		Templates.WOODEN_SUPPORT.value = 24;
		Templates.WOODEN_SUPPORT.templateId = GameObject.generateNewTemplateId();

	}

}
