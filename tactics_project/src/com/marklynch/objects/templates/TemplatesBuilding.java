package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.inanimateobjects.InputDrain;
import com.marklynch.objects.inanimateobjects.Fireplace;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.MineCart;
import com.marklynch.objects.inanimateobjects.Rail;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.objects.inanimateobjects.WallWithCrack;

public class TemplatesBuilding {

	public TemplatesBuilding() {

		Templates.WALL_CAVE = new Wall();
		Templates.WALL_CAVE.name = "Cave Wall";
		Templates.WALL_CAVE.imageTexture = getGlobalImage("wall_cave.png", true);
		Templates.WALL_CAVE.totalHealth = Templates.WALL_CAVE.remainingHealth = 1000;
		Templates.WALL_CAVE.soundWhenHit = 10f;
		Templates.WALL_CAVE.soundWhenHitting = 1f;
		Templates.WALL_CAVE.soundDampening = 10f;
		Templates.WALL_CAVE.weight = 1000f;
		Templates.WALL_CAVE.value = 24;
		Templates.WALL_CAVE.maxRandomness = 16f;
		Templates.WALL_CAVE.templateId = GameObject.generateNewTemplateId();
		Templates.WALL_CAVE.setAllResistances100();

		Templates.WALL_WINDOW = new Wall();
		Templates.WALL_WINDOW.name = "Window Wall";
		Templates.WALL_WINDOW.imageTexture = getGlobalImage("wall_window.png", true);
		Templates.WALL_WINDOW.totalHealth = Templates.WALL_WINDOW.remainingHealth = 1000;
		Templates.WALL_WINDOW.soundWhenHit = 10f;
		Templates.WALL_WINDOW.soundWhenHitting = 1f;
		Templates.WALL_WINDOW.soundDampening = 10f;
		Templates.WALL_WINDOW.weight = 1000f;
		Templates.WALL_WINDOW.value = 68;
		Templates.WALL_WINDOW.maxRandomness = 16f;
		Templates.WALL_WINDOW.blocksLineOfSight = false;
		Templates.WALL_WINDOW.blocksCasting = true;
		Templates.WALL_WINDOW.templateId = GameObject.generateNewTemplateId();
		Templates.WALL_WINDOW.setAllResistances100();

		Templates.WALL_BUILDING = new Wall();
		Templates.WALL_BUILDING.name = "Building Wall";
		Templates.WALL_BUILDING.imageTexture = getGlobalImage("wall_building.png", true);
		Templates.WALL_BUILDING.totalHealth = Templates.WALL_BUILDING.remainingHealth = 1000;
		Templates.WALL_BUILDING.soundWhenHit = 10f;
		Templates.WALL_BUILDING.soundWhenHitting = 1f;
		Templates.WALL_BUILDING.soundDampening = 10f;
		Templates.WALL_BUILDING.weight = 1000f;
		Templates.WALL_BUILDING.value = 29;
		Templates.WALL_CAVE.maxRandomness = 4f;
		Templates.WALL_BUILDING.templateId = GameObject.generateNewTemplateId();
		Templates.WALL_BUILDING.setAllResistances100();

		Templates.WALL_WITH_CRACK = new WallWithCrack();
		Templates.WALL_WITH_CRACK.name = "Wall";
		Templates.WALL_WITH_CRACK.imageTexture = getGlobalImage("wall_crack.png", true);
		Templates.WALL_WITH_CRACK.totalHealth = Templates.WALL_WITH_CRACK.remainingHealth = 30;
		Templates.WALL_WITH_CRACK.soundWhenHit = 10f;
		Templates.WALL_WITH_CRACK.soundWhenHitting = 1f;
		Templates.WALL_WITH_CRACK.soundDampening = 10f;
		Templates.WALL_WITH_CRACK.weight = 90f;
		Templates.WALL_WITH_CRACK.value = 2;
		Templates.WALL_WITH_CRACK.canBePickedUp = false;
		Templates.WALL_WITH_CRACK.fitsInInventory = false;
		Templates.WALL_WITH_CRACK.persistsWhenCantBeSeen = true;
		Templates.WALL_WITH_CRACK.canShareSquare = false;
		Templates.WALL_WITH_CRACK.moveable = false;
		Templates.WALL_WITH_CRACK.blocksLineOfSight = true;
		Templates.WALL_WITH_CRACK.templateId = GameObject.generateNewTemplateId();
		Templates.WALL_WITH_CRACK.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES,
				new Stat(HIGH_LEVEL_STATS.FIRE_RES, 100));
		Templates.WALL_WITH_CRACK.highLevelStats.put(HIGH_LEVEL_STATS.POISON_RES,
				new Stat(HIGH_LEVEL_STATS.POISON_RES, 100));
		Templates.WALL_WITH_CRACK.highLevelStats.put(HIGH_LEVEL_STATS.BLEED_RES,
				new Stat(HIGH_LEVEL_STATS.BLEED_RES, 100));
		Templates.WALL_WITH_CRACK.highLevelStats.put(HIGH_LEVEL_STATS.HEALING_RES,
				new Stat(HIGH_LEVEL_STATS.HEALING_RES, 100));

		Templates.FALSE_WALL = new Wall();
		Templates.FALSE_WALL.name = "Wall";
		Templates.FALSE_WALL.imageTexture = getGlobalImage("wall.png", true);
		Templates.FALSE_WALL.totalHealth = Templates.FALSE_WALL.remainingHealth = 1;
		Templates.FALSE_WALL.soundWhenHit = 10f;
		Templates.FALSE_WALL.soundDampening = 10f;
		Templates.FALSE_WALL.stackable = false;
		Templates.FALSE_WALL.weight = 40f;
		Templates.FALSE_WALL.value = 34;
		Templates.FALSE_WALL.templateId = GameObject.generateNewTemplateId();
		Templates.FALSE_WALL.setAllResistances100();

		Templates.RUBBLE = new GameObject();
		Templates.RUBBLE.name = "Rubble";
		Templates.RUBBLE.imageTexture = getGlobalImage("rubble.png", true);
		Templates.RUBBLE.canBePickedUp = false;
		Templates.RUBBLE.fitsInInventory = false;
		Templates.RUBBLE.persistsWhenCantBeSeen = true;
		Templates.RUBBLE.attackable = false;
		Templates.RUBBLE.isFloorObject = true;
		Templates.RUBBLE.drawShadow = false;
		Templates.RUBBLE.moveable = false;
		Templates.RUBBLE.orderingOnGound = 20;
		Templates.RUBBLE.templateId = GameObject.generateNewTemplateId();

		Templates.FENCE = new Wall();
		Templates.FENCE.name = "Fence";
		Templates.FENCE.imageTexture = getGlobalImage("wall.png", true);
		Templates.FENCE.totalHealth = Templates.FENCE.remainingHealth = 100;
		Templates.FENCE.widthRatio = 1f;
		Templates.FENCE.heightRatio = 1f;
		Templates.FENCE.soundWhenHit = 1f;
		Templates.FENCE.soundWhenHitting = 1f;
		Templates.FENCE.soundDampening = 1f;
		Templates.FENCE.stackable = false;
		Templates.FENCE.weight = 50f;
		Templates.FENCE.value = 17;
		Templates.FENCE.anchorX = 0;
		Templates.FENCE.anchorY = 0;
		Templates.FENCE.templateId = GameObject.generateNewTemplateId();
		Templates.FENCE.flipYAxisInMirror = false;
		Templates.FENCE.blocksLineOfSight = false;

		Templates.RAIL = new Rail();
		Templates.RAIL.name = "Rail";
		Rail.imageTextureLeftRight = getGlobalImage("rail.png", true);
		Rail.imageTextureUpDown = getGlobalImage("rail_up_down.png", true);
		Rail.imageTextureLeftUp = getGlobalImage("rail_left_up.png", true);
		Rail.imageTextureRightUp = getGlobalImage("rail_right_up.png", true);
		Rail.imageTextureLeftDown = getGlobalImage("rail_left_down.png", true);
		Rail.imageTextureRightDown = getGlobalImage("rail_right_down.png", true);
		Rail.imageTextureLeftBufferStop = getGlobalImage("rail_buffer_stop_left.png", false);
		Rail.imageTextureRightBufferStop = getGlobalImage("rail_buffer_stop_right.png", false);
		Rail.imageTextureUpBufferStop = getGlobalImage("rail_buffer_stop_up.png", false);
		Rail.imageTextureDownBufferStop = getGlobalImage("rail_buffer_stop_down.png", false);
		Templates.RAIL.imageTexture = getGlobalImage("rail.png", true);
		Templates.RAIL.totalHealth = Templates.RAIL.remainingHealth = 100;
		Templates.RAIL.weight = 45f;
		Templates.RAIL.value = 29;
		Templates.RAIL.templateId = GameObject.generateNewTemplateId();
		Templates.RAIL.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_RES, new Stat(HIGH_LEVEL_STATS.SLASH_RES, 100));
		Templates.RAIL.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_RES, new Stat(HIGH_LEVEL_STATS.BLUNT_RES, 100));
		Templates.RAIL.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_RES, new Stat(HIGH_LEVEL_STATS.PIERCE_RES, 100));
		Templates.RAIL.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(HIGH_LEVEL_STATS.FIRE_RES, 100));
		Templates.RAIL.highLevelStats.put(HIGH_LEVEL_STATS.WATER_RES, new Stat(HIGH_LEVEL_STATS.WATER_RES, 100));
		Templates.RAIL.highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_RES,
				new Stat(HIGH_LEVEL_STATS.ELECTRICAL_RES, 100));
		Templates.RAIL.highLevelStats.put(HIGH_LEVEL_STATS.POISON_RES, new Stat(HIGH_LEVEL_STATS.POISON_RES, 100));
		Templates.RAIL.highLevelStats.put(HIGH_LEVEL_STATS.BLEED_RES, new Stat(HIGH_LEVEL_STATS.BLEED_RES, 100));
		Templates.RAIL.highLevelStats.put(HIGH_LEVEL_STATS.HEALING_RES, new Stat(HIGH_LEVEL_STATS.HEALING_RES, 100));

		Templates.RAIL_INVISIBLE = new Rail();
		Templates.RAIL_INVISIBLE.name = "RAIL_INVISIBLE";
		Templates.RAIL_INVISIBLE.hiding = true;
		Templates.RAIL_INVISIBLE.imageTexture = getGlobalImage(null, true);
		Templates.RAIL_INVISIBLE.totalHealth = Templates.RAIL_INVISIBLE.remainingHealth = 100;
		Templates.RAIL_INVISIBLE.weight = 45f;
		Templates.RAIL_INVISIBLE.value = 29;
		Templates.RAIL_INVISIBLE.templateId = GameObject.generateNewTemplateId();
		Templates.RAIL_INVISIBLE.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_RES,
				new Stat(HIGH_LEVEL_STATS.SLASH_RES, 100));
		Templates.RAIL_INVISIBLE.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_RES,
				new Stat(HIGH_LEVEL_STATS.BLUNT_RES, 100));
		Templates.RAIL_INVISIBLE.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_RES,
				new Stat(HIGH_LEVEL_STATS.PIERCE_RES, 100));
		Templates.RAIL_INVISIBLE.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES,
				new Stat(HIGH_LEVEL_STATS.FIRE_RES, 100));
		Templates.RAIL_INVISIBLE.highLevelStats.put(HIGH_LEVEL_STATS.WATER_RES,
				new Stat(HIGH_LEVEL_STATS.WATER_RES, 100));
		Templates.RAIL_INVISIBLE.highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_RES,
				new Stat(HIGH_LEVEL_STATS.ELECTRICAL_RES, 100));
		Templates.RAIL_INVISIBLE.highLevelStats.put(HIGH_LEVEL_STATS.POISON_RES,
				new Stat(HIGH_LEVEL_STATS.POISON_RES, 100));
		Templates.RAIL_INVISIBLE.highLevelStats.put(HIGH_LEVEL_STATS.BLEED_RES,
				new Stat(HIGH_LEVEL_STATS.BLEED_RES, 100));
		Templates.RAIL_INVISIBLE.highLevelStats.put(HIGH_LEVEL_STATS.HEALING_RES,
				new Stat(HIGH_LEVEL_STATS.HEALING_RES, 100));

		Templates.CIRCLE_FLOOR = new GameObject();
		Templates.CIRCLE_FLOOR.name = "Floor";
		Templates.CIRCLE_FLOOR.imageTexture = getGlobalImage("circle.png", true);
		Templates.CIRCLE_FLOOR.canBePickedUp = false;
		Templates.CIRCLE_FLOOR.fitsInInventory = false;
		Templates.CIRCLE_FLOOR.persistsWhenCantBeSeen = true;
		Templates.CIRCLE_FLOOR.attackable = false;
		Templates.CIRCLE_FLOOR.isFloorObject = true;
		Templates.CIRCLE_FLOOR.drawShadow = false;
		Templates.CIRCLE_FLOOR.moveable = false;
		Templates.CIRCLE_FLOOR.orderingOnGound = 19;
		Templates.CIRCLE_FLOOR.templateId = GameObject.generateNewTemplateId();

		Templates.MINE_CART = new MineCart();
		Templates.MINE_CART.name = "Mine Cart";
		Templates.MINE_CART.setImageAndExtrapolateSize("mine_cart.png");
		Templates.MINE_CART.totalHealth = Templates.MINE_CART.remainingHealth = 300;
		Templates.MINE_CART.weight = 68f;
		Templates.MINE_CART.value = 97;
		Templates.MINE_CART.moveable = false;
		Templates.MINE_CART.templateId = GameObject.generateNewTemplateId();

		Templates.FIRE_PLACE = new Fireplace();
		Templates.FIRE_PLACE.name = "Fireplace";
		Templates.FIRE_PLACE.setImageAndExtrapolateSize("fireplace_lit.png");
		Templates.FIRE_PLACE.imageTextureLit = getGlobalImage("fireplace_lit.png", true);
		Templates.FIRE_PLACE.imageTextureUnlit = getGlobalImage("fireplace_unlit.png", true);
		Templates.FIRE_PLACE.totalHealth = Templates.FIRE_PLACE.remainingHealth = 112;
		Templates.FIRE_PLACE.weight = 300f;
		Templates.FIRE_PLACE.value = 254;
		Templates.FIRE_PLACE.lit = true;
		Templates.FIRE_PLACE.templateId = GameObject.generateNewTemplateId();
		Templates.FIRE_PLACE.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_RES, new Stat(HIGH_LEVEL_STATS.SLASH_RES, 100));
		Templates.FIRE_PLACE.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_RES, new Stat(HIGH_LEVEL_STATS.BLUNT_RES, 100));
		Templates.FIRE_PLACE.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_RES,
				new Stat(HIGH_LEVEL_STATS.PIERCE_RES, 100));
		Templates.FIRE_PLACE.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(HIGH_LEVEL_STATS.FIRE_RES, 100));
		Templates.FIRE_PLACE.highLevelStats.put(HIGH_LEVEL_STATS.WATER_RES, new Stat(HIGH_LEVEL_STATS.WATER_RES, 100));
		Templates.FIRE_PLACE.highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_RES,
				new Stat(HIGH_LEVEL_STATS.ELECTRICAL_RES, 100));
		Templates.FIRE_PLACE.highLevelStats.put(HIGH_LEVEL_STATS.POISON_RES,
				new Stat(HIGH_LEVEL_STATS.POISON_RES, 100));
		Templates.FIRE_PLACE.highLevelStats.put(HIGH_LEVEL_STATS.BLEED_RES, new Stat(HIGH_LEVEL_STATS.BLEED_RES, 100));
		Templates.FIRE_PLACE.highLevelStats.put(HIGH_LEVEL_STATS.HEALING_RES,
				new Stat(HIGH_LEVEL_STATS.HEALING_RES, 100));

		Templates.INPUT_DRAIN = new InputDrain();
		Templates.INPUT_DRAIN.name = "Drain";
		Templates.INPUT_DRAIN.setImageAndExtrapolateSize("drain.png");
		Templates.INPUT_DRAIN.templateId = GameObject.generateNewTemplateId();

	}

}
