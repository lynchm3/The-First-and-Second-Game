package com.marklynch.objects.templates;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.objects.Discoverable;
import com.marklynch.objects.Food;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.MapMarker;
import com.marklynch.objects.SmallHidingPlace;
import com.marklynch.objects.Stump;
import com.marklynch.objects.Tree;

import mdesl.graphics.Texture;

public class TemplatesNature {

	public TemplatesNature() {
		TREE = new Tree("Tree", 100, "tree_1.png", null, new Inventory(), 1f, 1.5f, 0f, -0.5f, 1f, 1f, 2f, null, 0.5f,
				0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, 15, null, true, GameObject.generateNewTemplateId());
		BIG_TREE = new Tree("Big Tree", 200, "tree_1.png", null, new Inventory(), 1.5f, 1.5f, -0.25f, -0.5f, 1f, 1f, 2f,
				null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, 20, null, false,
				GameObject.generateNewTemplateId());

		STUMP = new Stump("Stump", 100, "stump.png", null, new Inventory(), 0.5f, 1f, 0.25f, 0f, 1f, 1f, 2f, null, 0.5f,
				0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, 5, null, GameObject.generateNewTemplateId());
		BIG_STUMP = new Stump("Big Stump", 200, "stump.png", null, new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 2f, null,
				0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, 7, null, GameObject.generateNewTemplateId());

		BUSH = new HidingPlace("Bush", 10, "bush.png", null, new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 2f, null, 0.5f,
				0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, 7, null, new Effect[] {}, GameObject.generateNewTemplateId());

		POISON_BUSH = new HidingPlace("Posion Bush", 10, "bush.png", null, new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 2f,
				null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, 7, null, new Effect[] { new EffectPoison(3) },
				GameObject.generateNewTemplateId());

		LONG_GRASS = new HidingPlace("Long Grass", 10, "long_grass.png", null, new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f,
				2f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, 5, null, new Effect[] {},
				GameObject.generateNewTemplateId());

		WHEAT = new HidingPlace("Wheat", 10, "wheat.png", null, new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 2f, null, 0.5f,
				0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, 10, null, new Effect[] {}, GameObject.generateNewTemplateId());

		MUSHROOM = new Food("Mushroom", 5, "mushroom.png", null, new Inventory(), 0.5f, 0.5f, 0f, 0f, 1f, 1f, 1f, null,
				0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0.5f, 10, null, GameObject.generateNewTemplateId());

		BURROW = new SmallHidingPlace("Burrow", 5, "burrow.png", null, new Inventory(), 0.5f, 0.5f, 16f, 16f, 1f, 1f,
				1f, null, 0.5f, 0.5f, false, 100f, 0f, 0f, 0f, 100f, 0f, 0, null, new Effect[] {},
				GameObject.generateNewTemplateId());

		MOUND = new Discoverable("Mound of Dirt", 5, 1, "mound.png", null, null, new Inventory(), 1f, 1f, 0f, 0f, 1f,
				1f, 1f, null, 0.5f, 0.5f, false, 100f, 0f, 0f, 0f, 100f, 0f, 0, null,
				GameObject.generateNewTemplateId());
		public Texture preDiscoverTexture;
		public Texture postDiscoverTexture;
		this.level = level;

		APPLE = new Food("Unripe Apple", 5, "apple.png", null, new Inventory(), appleSize, appleSize, 0.5f, 0.5f, 1f,
				1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0.5f, 1, null, 1000);

		Templates.MAP_MARKER_RED = new MapMarker();
		Templates.MAP_MARKER_RED.name = "";
		Templates.MAP_MARKER_RED.imageTexturePath = "map_marker_red.png";
		Templates.MAP_MARKER_RED.totalHealth = Templates.MAP_MARKER_RED.remainingHealth = 1;
		Templates.MAP_MARKER_RED.widthRatio = 1f;
		Templates.MAP_MARKER_RED.heightRatio = 1f;
		Templates.MAP_MARKER_RED.drawOffsetX = 0f;
		Templates.MAP_MARKER_RED.drawOffsetY = 0f;
		Templates.MAP_MARKER_RED.soundWhenHit = 1f;
		Templates.MAP_MARKER_RED.soundWhenHitting = 1f;
		Templates.MAP_MARKER_RED.soundDampening = 1f;
		Templates.MAP_MARKER_RED.stackable = false;
		Templates.MAP_MARKER_RED.weight = 0f;
		Templates.MAP_MARKER_RED.value = 0;
		Templates.MAP_MARKER_RED.anchorX = 0;
		Templates.MAP_MARKER_RED.anchorY = 0;
		Templates.MAP_MARKER_RED.templateId = GameObject.generateNewTemplateId();

		Templates.MAP_MARKER_GREEN = new MapMarker();
		Templates.MAP_MARKER_GREEN.name = "";
		Templates.MAP_MARKER_GREEN.imageTexturePath = "map_marker_green.png";
		Templates.MAP_MARKER_GREEN.totalHealth = Templates.MAP_MARKER_GREEN.remainingHealth = 1;
		Templates.MAP_MARKER_GREEN.widthRatio = 1f;
		Templates.MAP_MARKER_GREEN.heightRatio = 1f;
		Templates.MAP_MARKER_GREEN.drawOffsetX = 0f;
		Templates.MAP_MARKER_GREEN.drawOffsetY = 0f;
		Templates.MAP_MARKER_GREEN.soundWhenHit = 1f;
		Templates.MAP_MARKER_GREEN.soundWhenHitting = 1f;
		Templates.MAP_MARKER_GREEN.soundDampening = 1f;
		Templates.MAP_MARKER_GREEN.stackable = false;
		Templates.MAP_MARKER_GREEN.weight = 0f;
		Templates.MAP_MARKER_GREEN.value = 0;
		Templates.MAP_MARKER_GREEN.anchorX = 0;
		Templates.MAP_MARKER_GREEN.anchorY = 0;
		Templates.MAP_MARKER_GREEN.templateId = GameObject.generateNewTemplateId();

		Templates.MAP_MARKER_BLUE = new MapMarker();
		Templates.MAP_MARKER_BLUE.name = "";
		Templates.MAP_MARKER_BLUE.imageTexturePath = "map_marker_blue.png";
		Templates.MAP_MARKER_BLUE.totalHealth = Templates.MAP_MARKER_BLUE.remainingHealth = 1;
		Templates.MAP_MARKER_BLUE.widthRatio = 1f;
		Templates.MAP_MARKER_BLUE.heightRatio = 1f;
		Templates.MAP_MARKER_BLUE.drawOffsetX = 0f;
		Templates.MAP_MARKER_BLUE.drawOffsetY = 0f;
		Templates.MAP_MARKER_BLUE.soundWhenHit = 1f;
		Templates.MAP_MARKER_BLUE.soundWhenHitting = 1f;
		Templates.MAP_MARKER_BLUE.soundDampening = 1f;
		Templates.MAP_MARKER_BLUE.stackable = false;
		Templates.MAP_MARKER_BLUE.weight = 0f;
		Templates.MAP_MARKER_BLUE.value = 0;
		Templates.MAP_MARKER_BLUE.anchorX = 0;
		Templates.MAP_MARKER_BLUE.anchorY = 0;
		Templates.MAP_MARKER_BLUE.templateId = GameObject.generateNewTemplateId();

		Templates.MAP_MARKER_SKULL = new MapMarker();
		Templates.MAP_MARKER_SKULL.name = "";
		Templates.MAP_MARKER_SKULL.imageTexturePath = "map_marker_skull.png";
		Templates.MAP_MARKER_SKULL.totalHealth = Templates.MAP_MARKER_SKULL.remainingHealth = 1;
		Templates.MAP_MARKER_SKULL.widthRatio = 1f;
		Templates.MAP_MARKER_SKULL.heightRatio = 1f;
		Templates.MAP_MARKER_SKULL.drawOffsetX = 0f;
		Templates.MAP_MARKER_SKULL.drawOffsetY = 0f;
		Templates.MAP_MARKER_SKULL.soundWhenHit = 1f;
		Templates.MAP_MARKER_SKULL.soundWhenHitting = 1f;
		Templates.MAP_MARKER_SKULL.soundDampening = 1f;
		Templates.MAP_MARKER_SKULL.stackable = false;
		Templates.MAP_MARKER_SKULL.weight = 0f;
		Templates.MAP_MARKER_SKULL.value = 0;
		Templates.MAP_MARKER_SKULL.anchorX = 0;
		Templates.MAP_MARKER_SKULL.anchorY = 0;
		Templates.MAP_MARKER_SKULL.templateId = GameObject.generateNewTemplateId();

		Templates.MAP_MARKER_TREASURE = new MapMarker();
		Templates.MAP_MARKER_TREASURE.name = "";
		Templates.MAP_MARKER_TREASURE.imageTexturePath = "map_marker_treasure.png";
		Templates.MAP_MARKER_TREASURE.totalHealth = Templates.MAP_MARKER_TREASURE.remainingHealth = 1;
		Templates.MAP_MARKER_TREASURE.widthRatio = 1f;
		Templates.MAP_MARKER_TREASURE.heightRatio = 1f;
		Templates.MAP_MARKER_TREASURE.drawOffsetX = 0f;
		Templates.MAP_MARKER_TREASURE.drawOffsetY = 0f;
		Templates.MAP_MARKER_TREASURE.soundWhenHit = 1f;
		Templates.MAP_MARKER_TREASURE.soundWhenHitting = 1f;
		Templates.MAP_MARKER_TREASURE.soundDampening = 1f;
		Templates.MAP_MARKER_TREASURE.stackable = false;
		Templates.MAP_MARKER_TREASURE.weight = 0f;
		Templates.MAP_MARKER_TREASURE.value = 0;
		Templates.MAP_MARKER_TREASURE.anchorX = 0;
		Templates.MAP_MARKER_TREASURE.anchorY = 0;
		Templates.MAP_MARKER_TREASURE.templateId = GameObject.generateNewTemplateId();

	}

}
