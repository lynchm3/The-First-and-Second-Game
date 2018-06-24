package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.Fence;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.MineCart;
import com.marklynch.objects.Rail;
import com.marklynch.objects.Wall;

public class TemplatesBuilding {

	public TemplatesBuilding() {

		Templates.WALL = new Wall();
		Templates.WALL.name = "Wall";
		Templates.WALL.imageTexturePath = "wall.png";
		Templates.WALL.imageTexture = getGlobalImage(Templates.WALL.imageTexturePath, true);
		Templates.WALL.totalHealth = Templates.WALL.remainingHealth = 1000;
		Templates.WALL.widthRatio = 1f;
		Templates.WALL.heightRatio = 1f;
		Templates.WALL.drawOffsetRatioX = 0f;
		Templates.WALL.drawOffsetRatioY = 0f;
		Templates.WALL.soundWhenHit = 10f;
		Templates.WALL.soundWhenHitting = 1f;
		Templates.WALL.soundDampening = 10f;
		Templates.WALL.stackable = false;
		Templates.WALL.weight = 100f;
		Templates.WALL.value = 24;
		Templates.WALL.anchorX = 0;
		Templates.WALL.anchorY = 0;
		Templates.WALL.templateId = GameObject.generateNewTemplateId();
		Templates.WALL.flipYAxisInMirror = false;
		Templates.WALL.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_RES, new Stat(100));
		Templates.WALL.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_RES, new Stat(100));
		Templates.WALL.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_RES, new Stat(100));
		Templates.WALL.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(100));
		Templates.WALL.highLevelStats.put(HIGH_LEVEL_STATS.WATER_RES, new Stat(100));
		Templates.WALL.highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_RES, new Stat(100));
		Templates.WALL.highLevelStats.put(HIGH_LEVEL_STATS.POISON_RES, new Stat(100));
		Templates.WALL.highLevelStats.put(HIGH_LEVEL_STATS.BLEED_RES, new Stat(100));
		Templates.WALL.highLevelStats.put(HIGH_LEVEL_STATS.HEALING_RES, new Stat(100));

		Templates.FENCE = new Fence();
		Templates.FENCE.name = "Fence";
		Templates.FENCE.imageTexturePath = "wall.png";
		Templates.FENCE.imageTexture = getGlobalImage(Templates.FENCE.imageTexturePath, true);
		Templates.FENCE.totalHealth = Templates.FENCE.remainingHealth = 100;
		Templates.FENCE.widthRatio = 1f;
		Templates.FENCE.heightRatio = 1f;
		Templates.FENCE.drawOffsetRatioX = 0f;
		Templates.FENCE.drawOffsetRatioY = 0f;
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

		Templates.RAIL = new Rail();
		Templates.RAIL.name = "Rail";
		Templates.RAIL.imageTexturePath = "rail.png";
		Rail.imageTextureLeftRight = getGlobalImage("rail.png", true);
		Rail.imageTextureUpDown = getGlobalImage("rail_up_down.png", true);
		Rail.imageTextureLeftUp = getGlobalImage("rail_left_up.png", true);
		Rail.imageTextureRightUp = getGlobalImage("rail_right_up.png", true);
		Rail.imageTextureLeftDown = getGlobalImage("rail_left_down.png", true);
		Rail.imageTextureRightDown = getGlobalImage("rail_right_down.png", true);
		Templates.RAIL.imageTexture = getGlobalImage(Templates.RAIL.imageTexturePath, true);
		Templates.RAIL.totalHealth = Templates.RAIL.remainingHealth = 100;
		Templates.RAIL.weight = 45f;
		Templates.RAIL.value = 29;
		Templates.RAIL.templateId = GameObject.generateNewTemplateId();

		Templates.RAIL_INVISIBLE = new Rail();
		Templates.RAIL_INVISIBLE.name = "RAIL_INVISIBLE";
		Templates.RAIL_INVISIBLE.hiding = true;
		Templates.RAIL_INVISIBLE.imageTexture = getGlobalImage(Templates.RAIL_INVISIBLE.imageTexturePath, true);
		Templates.RAIL_INVISIBLE.totalHealth = Templates.RAIL_INVISIBLE.remainingHealth = 100;
		Templates.RAIL_INVISIBLE.weight = 45f;
		Templates.RAIL_INVISIBLE.value = 29;
		Templates.RAIL_INVISIBLE.templateId = GameObject.generateNewTemplateId();

		Templates.MINE_CART = new MineCart();
		Templates.MINE_CART.name = "Mine Cart";
		Templates.MINE_CART.imageTexture = getGlobalImage("mine_cart.png", true);
		Templates.MINE_CART.totalHealth = Templates.MINE_CART.remainingHealth = 300;
		Templates.MINE_CART.weight = 68f;
		Templates.MINE_CART.value = 97;
		Templates.MINE_CART.templateId = GameObject.generateNewTemplateId();
	}

}
