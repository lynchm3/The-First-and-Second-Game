package com.marklynch.objects.templates;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.level.constructs.power.PowerInferno;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.Discoverable;
import com.marklynch.objects.inanimateobjects.Food;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.HidingPlace;
import com.marklynch.objects.inanimateobjects.Landmine;
import com.marklynch.objects.inanimateobjects.MeatChunk;
import com.marklynch.objects.inanimateobjects.Portal;
import com.marklynch.objects.inanimateobjects.SmallHidingPlace;
import com.marklynch.objects.inanimateobjects.Storage;
import com.marklynch.objects.inanimateobjects.Stump;
import com.marklynch.objects.inanimateobjects.Tree;
import com.marklynch.objects.inanimateobjects.Vein;
import com.marklynch.objects.inanimateobjects.VoidHole;
import com.marklynch.objects.inanimateobjects.WaterBody;
import com.marklynch.utils.ResourceUtils;;

public class TemplatesNature {

	public TemplatesNature() {

		Templates.TREE = new Tree();
		Templates.TREE.name = "Tree";
		Templates.TREE.setImageAndExtrapolateSize("tree.png");
		Templates.TREE.totalHealth = Templates.TREE.remainingHealth = 100;
		Templates.TREE.heightRatio = 1.5f;
		Templates.TREE.drawOffsetRatioY = -0.5f;
		Templates.TREE.weight = 100f;
		Templates.TREE.value = 10;
		Templates.TREE.bigShadow = true;
		Templates.TREE.moveable = false;
		Templates.TREE.canShareSquare = false;
		Templates.TREE.fitsInInventory = false;
		Templates.TREE.templateId = GameObject.generateNewTemplateId();

		Templates.TREE_READABLE = new GameObject();
		Templates.TREE_READABLE.name = "Tree with engraving";
		Templates.TREE_READABLE.setImageAndExtrapolateSize("tree_readable.png");
		Templates.TREE_READABLE.totalHealth = Templates.TREE_READABLE.remainingHealth = Templates.TREE.totalHealth;
		Templates.TREE_READABLE.heightRatio = Templates.TREE.heightRatio;
		Templates.TREE_READABLE.drawOffsetRatioY = Templates.TREE.drawOffsetRatioY;
		Templates.TREE_READABLE.weight = Templates.TREE.weight;
		Templates.TREE_READABLE.value = Templates.TREE.value;
		Templates.TREE_READABLE.bigShadow = Templates.TREE.bigShadow;
		Templates.TREE_READABLE.persistsWhenCantBeSeen = Templates.TREE.persistsWhenCantBeSeen;
		Templates.TREE_READABLE.moveable = Templates.TREE.moveable;
		Templates.TREE_READABLE.canShareSquare = Templates.TREE.canShareSquare;
		Templates.TREE_READABLE.fitsInInventory = Templates.TREE.fitsInInventory;
		Templates.TREE_READABLE.templateId = GameObject.generateNewTemplateId();

		Templates.TREE_CONTAINER = new Storage();
		Templates.TREE_CONTAINER.name = Templates.TREE_CONTAINER.baseName = "Tree with hole";
		Templates.TREE_CONTAINER.setImageAndExtrapolateSize("tree_searchable.png");
		Templates.TREE_CONTAINER.storageClosedTexture = ResourceUtils.getGlobalImage("tree_searchable.png", true);
		Templates.TREE_CONTAINER.storageOpenTexture = ResourceUtils.getGlobalImage("tree_searchable.png", true);
		Templates.TREE_CONTAINER.totalHealth = Templates.TREE_CONTAINER.remainingHealth = Templates.TREE.totalHealth;
		Templates.TREE_CONTAINER.heightRatio = Templates.TREE.heightRatio;
		Templates.TREE_CONTAINER.drawOffsetRatioY = Templates.TREE.drawOffsetRatioY;
		Templates.TREE_CONTAINER.weight = Templates.TREE.weight;
		Templates.TREE_CONTAINER.value = Templates.TREE.value;
		Templates.TREE_CONTAINER.bigShadow = Templates.TREE.bigShadow;
		Templates.TREE_CONTAINER.isOpenable = false;
		Templates.TREE_CONTAINER.lockable = false;
		Templates.TREE_CONTAINER.moveable = false;
		Templates.TREE_CONTAINER.canShareSquare = false;
		Templates.TREE_CONTAINER.fitsInInventory = false;
		Templates.TREE_CONTAINER.persistsWhenCantBeSeen = Templates.TREE.persistsWhenCantBeSeen;
		Templates.TREE_CONTAINER.moveable = Templates.TREE.moveable;
		Templates.TREE_CONTAINER.templateId = GameObject.generateNewTemplateId();

		Templates.BIG_TREE = new Tree();
		Templates.BIG_TREE.name = "Big Tree";
		Templates.BIG_TREE.setImageAndExtrapolateSize("tree_big.png");
		Templates.BIG_TREE.totalHealth = Templates.BIG_TREE.remainingHealth = 150;
		Templates.BIG_TREE.widthRatio = 1.5f;
		Templates.BIG_TREE.heightRatio = 1.5f;
		Templates.BIG_TREE.drawOffsetRatioX = -0.25f;
		Templates.BIG_TREE.drawOffsetRatioY = -0.5f;
		Templates.BIG_TREE.soundDampening = 2f;
		Templates.BIG_TREE.weight = 200f;
		Templates.BIG_TREE.value = 13;
		Templates.BIG_TREE.anchorX = 0;
		Templates.BIG_TREE.anchorY = 0;
		Templates.BIG_TREE.bigShadow = true;
		Templates.BIG_TREE.templateId = GameObject.generateNewTemplateId();
		Templates.BIG_TREE.flipYAxisInMirror = false;
		Templates.BIG_TREE.moveable = false;
		Templates.BIG_TREE.canShareSquare = false;
		Templates.BIG_TREE.fitsInInventory = false;

		Templates.STUMP = new Stump();
		Templates.STUMP.name = "Stump";
		Templates.STUMP.setImageAndExtrapolateSize("stump.png");
		Templates.STUMP.totalHealth = Templates.STUMP.remainingHealth = 52;
		Templates.STUMP.weight = 50f;
		Templates.STUMP.value = 6;
		Templates.STUMP.anchorX = 0;
		Templates.STUMP.anchorY = 0;
		Templates.STUMP.bigShadow = true;
		Templates.STUMP.templateId = GameObject.generateNewTemplateId();
		Templates.STUMP.flipYAxisInMirror = false;
		Templates.STUMP.moveable = false;
		Templates.STUMP.canShareSquare = false;
		Templates.STUMP.fitsInInventory = false;

		Templates.BUSH = new HidingPlace();
		Templates.BUSH.name = "Bush";
		Templates.BUSH.setImageAndExtrapolateSize("bush.png");
		Templates.BUSH.totalHealth = Templates.BUSH.remainingHealth = 21;
		Templates.BUSH.canShareSquare = true;
		Templates.BUSH.weight = 34f;
		Templates.BUSH.value = 11;
		Templates.BUSH.anchorX = 0;
		Templates.BUSH.anchorY = 0;
		Templates.BUSH.bigShadow = true;
		Templates.BUSH.templateId = GameObject.generateNewTemplateId();
		Templates.BUSH.effectsFromInteracting = new Effect[] {};
		Templates.BUSH.flipYAxisInMirror = false;
		Templates.BUSH.moveable = false;
		Templates.BUSH.canShareSquare = false;
		Templates.BUSH.fitsInInventory = false;

		Templates.POISON_BUSH = new HidingPlace();
		Templates.POISON_BUSH.name = "Posion Bush";
		Templates.POISON_BUSH.setImageAndExtrapolateSize("bush.png");
		Templates.POISON_BUSH.totalHealth = Templates.POISON_BUSH.remainingHealth = 21;
		Templates.POISON_BUSH.canShareSquare = true;
		Templates.POISON_BUSH.weight = 34f;
		Templates.POISON_BUSH.value = 16;
		Templates.POISON_BUSH.anchorX = 0;
		Templates.POISON_BUSH.anchorY = 0;
		Templates.POISON_BUSH.bigShadow = true;
		Templates.POISON_BUSH.effectsFromInteracting = new Effect[] { new EffectPoison(3) };
		Templates.POISON_BUSH.templateId = GameObject.generateNewTemplateId();
		Templates.POISON_BUSH.flipYAxisInMirror = false;
		Templates.POISON_BUSH.moveable = false;
		Templates.POISON_BUSH.canShareSquare = false;
		Templates.POISON_BUSH.fitsInInventory = false;

		Templates.LONG_GRASS = new HidingPlace();
		Templates.LONG_GRASS.name = "Long Grass";
		Templates.LONG_GRASS.setImageAndExtrapolateSize("long_grass.png");
		Templates.LONG_GRASS.totalHealth = Templates.LONG_GRASS.remainingHealth = 10;
		Templates.LONG_GRASS.weight = 5f;
		Templates.LONG_GRASS.value = 8;
		Templates.LONG_GRASS.anchorX = 0;
		Templates.LONG_GRASS.anchorY = 0;
		Templates.LONG_GRASS.bigShadow = true;
		Templates.LONG_GRASS.templateId = GameObject.generateNewTemplateId();
		Templates.LONG_GRASS.effectsFromInteracting = new Effect[] {};
		Templates.LONG_GRASS.flipYAxisInMirror = false;
		Templates.LONG_GRASS.moveable = false;

		Templates.WHEAT = new HidingPlace();
		Templates.WHEAT.name = "Wheat";
		Templates.WHEAT.setImageAndExtrapolateSize("wheat.png");
		Templates.WHEAT.totalHealth = Templates.WHEAT.remainingHealth = 11;
		Templates.WHEAT.weight = 9f;
		Templates.WHEAT.value = 17;
		Templates.WHEAT.anchorX = 0;
		Templates.WHEAT.anchorY = 0;
		Templates.WHEAT.bigShadow = true;
		Templates.WHEAT.templateId = GameObject.generateNewTemplateId();
		Templates.WHEAT.effectsFromInteracting = new Effect[] {};
		Templates.WHEAT.flipYAxisInMirror = false;
		Templates.WHEAT.moveable = false;

		Templates.MUSHROOM = new Food();
		Templates.MUSHROOM.name = "Mushroom";
		Templates.MUSHROOM.setImageAndExtrapolateSize("mushroom.png");
		Templates.MUSHROOM.totalHealth = Templates.MUSHROOM.remainingHealth = 13;
		Templates.MUSHROOM.stackable = false;
		Templates.MUSHROOM.weight = 14f;
		Templates.MUSHROOM.value = 19;
		Templates.MUSHROOM.anchorX = 0;
		Templates.MUSHROOM.anchorY = 0;
		Templates.MUSHROOM.templateId = GameObject.generateNewTemplateId();
		Templates.MUSHROOM.flipYAxisInMirror = false;

		Templates.CANDY = new Food();
		Templates.CANDY.name = "Candy";
		Templates.CANDY.setImageAndExtrapolateSize("candy.png");
		Templates.CANDY.totalHealth = Templates.CANDY.remainingHealth = 11;
		Templates.CANDY.weight = 7f;
		Templates.CANDY.value = 9;
		Templates.CANDY.anchorX = 18;
		Templates.CANDY.anchorY = 60;
		Templates.CANDY.templateId = GameObject.generateNewTemplateId();
		Templates.CANDY.flipYAxisInMirror = false;

		Templates.BURROW = new SmallHidingPlace();
		Templates.BURROW.name = "Burrow";
		Templates.BURROW.setImageAndExtrapolateSize("burrow.png");
		Templates.BURROW.drawOffsetRatioX = 16f;
		Templates.BURROW.drawOffsetRatioY = 16f;
		Templates.BURROW.weight = 100f;
		Templates.BURROW.value = 14;
		Templates.BURROW.anchorX = 0;
		Templates.BURROW.anchorY = 0;
		Templates.BURROW.templateId = GameObject.generateNewTemplateId();

		Templates.MOUND = new Discoverable();
		Templates.MOUND.name = "Mound of Dirt";
		Templates.MOUND.setImageAndExtrapolateSize("mound.png");
		Templates.MOUND.totalHealth = Templates.MOUND.remainingHealth = 103;
		Templates.MOUND.weight = 100f;
		Templates.MOUND.value = 15;
		Templates.MOUND.templateId = GameObject.generateNewTemplateId();
		Templates.MOUND.preDiscoverTexture = ResourceUtils.getGlobalImage("mound.png", true);
		Templates.MOUND.postDiscoverTexture = ResourceUtils.getGlobalImage("mound.png", true);
		Templates.MOUND.diggable = true;
		Templates.MOUND.canContainOtherObjects = false;
		Templates.MOUND.flipYAxisInMirror = false;
		Templates.MOUND.moveable = false;

		Templates.INVISIBLE_DIGGABLE = new GameObject();
		Templates.INVISIBLE_DIGGABLE.name = "Diggable Spot";
		// Templates.INVISIBLE_DIGGABLE.setImageAndExtrapolateSize("INVISIBLE_DIGGABLE.png");
		Templates.INVISIBLE_DIGGABLE.totalHealth = Templates.INVISIBLE_DIGGABLE.remainingHealth = 103;
		Templates.INVISIBLE_DIGGABLE.weight = 100f;
		Templates.INVISIBLE_DIGGABLE.value = 15;
		Templates.INVISIBLE_DIGGABLE.templateId = GameObject.generateNewTemplateId();
		// Templates.INVISIBLE_DIGGABLE.preDiscoverTexture =
		// ResourceUtils.getGlobalImage("INVISIBLE_DIGGABLE.png", true);
		// Templates.INVISIBLE_DIGGABLE.postDiscoverTexture =
		// ResourceUtils.getGlobalImage("INVISIBLE_DIGGABLE.png", true);
		Templates.INVISIBLE_DIGGABLE.diggable = true;
		Templates.INVISIBLE_DIGGABLE.canContainOtherObjects = false;
		Templates.INVISIBLE_DIGGABLE.flipYAxisInMirror = false;
		Templates.INVISIBLE_DIGGABLE.moveable = false;

		Templates.APPLE = new Food();
		Templates.APPLE.name = "Apple";
		Templates.APPLE.setImageAndExtrapolateSize("apple.png");
		Templates.APPLE.totalHealth = Templates.APPLE.remainingHealth = 11;
		Templates.APPLE.weight = 5f;
		Templates.APPLE.value = 15;
		Templates.APPLE.anchorX = 16;
		Templates.APPLE.anchorY = 16;
		Templates.APPLE.templateId = GameObject.generateNewTemplateId();
		Templates.APPLE.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, 1));
		Templates.APPLE.flipYAxisInMirror = false;

		Templates.ROCK = new GameObject();
		Templates.ROCK.name = "Rock";
		Templates.ROCK.setImageAndExtrapolateSize("rock.png");
		Templates.ROCK.totalHealth = Templates.ROCK.remainingHealth = 51;
		Templates.ROCK.weight = 12f;
		Templates.ROCK.value = 6;
		Templates.ROCK.anchorX = 12;
		Templates.ROCK.anchorY = 14;
		Templates.ROCK.templateId = GameObject.generateNewTemplateId();
		Templates.ROCK.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE, new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, 5));
		Templates.ROCK.flipYAxisInMirror = false;

		Templates.VEIN = new Vein();
		Templates.VEIN.name = "Vein";
		Templates.VEIN.imageTexture = getGlobalImage("vein.png", true);
		Templates.VEIN.totalHealth = Templates.VEIN.remainingHealth = 1000;
		Templates.VEIN.widthRatio = 1f;
		Templates.VEIN.heightRatio = 1f;
		Templates.VEIN.drawOffsetRatioX = 0f;
		Templates.VEIN.drawOffsetRatioY = 0f;
		Templates.VEIN.soundWhenHit = 10f;
		Templates.VEIN.soundWhenHitting = 1f;
		Templates.VEIN.soundDampening = 10f;
		Templates.VEIN.stackable = false;
		Templates.VEIN.weight = 100f;
		Templates.VEIN.value = 59;
		Templates.VEIN.anchorX = 0;
		Templates.VEIN.anchorY = 0;
		Templates.VEIN.templateId = GameObject.generateNewTemplateId();
		Templates.VEIN.flipYAxisInMirror = false;
		Templates.VEIN.canShareSquare = false;
		Templates.VEIN.fitsInInventory = false;
		Templates.VEIN.moveable = false;
		Templates.VEIN.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_RES, new Stat(HIGH_LEVEL_STATS.SLASH_RES, 100));
		Templates.VEIN.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_RES, new Stat(HIGH_LEVEL_STATS.BLUNT_RES, 100));
		Templates.VEIN.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_RES, new Stat(HIGH_LEVEL_STATS.PIERCE_RES, 100));
		Templates.VEIN.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(HIGH_LEVEL_STATS.FIRE_RES, 100));
		Templates.VEIN.highLevelStats.put(HIGH_LEVEL_STATS.WATER_RES, new Stat(HIGH_LEVEL_STATS.WATER_RES, 100));
		Templates.VEIN.highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_RES,
				new Stat(HIGH_LEVEL_STATS.ELECTRICAL_RES, 100));
		Templates.VEIN.highLevelStats.put(HIGH_LEVEL_STATS.POISON_RES, new Stat(HIGH_LEVEL_STATS.POISON_RES, 100));
		Templates.VEIN.highLevelStats.put(HIGH_LEVEL_STATS.BLEED_RES, new Stat(HIGH_LEVEL_STATS.BLEED_RES, 100));
		Templates.VEIN.highLevelStats.put(HIGH_LEVEL_STATS.HEALING_RES, new Stat(HIGH_LEVEL_STATS.HEALING_RES, 100));

		// Food?
		Templates.MEAT_CHUNK = new MeatChunk();
		Templates.MEAT_CHUNK.name = "Meat Chunk";
		Templates.MEAT_CHUNK.setImageAndExtrapolateSize("meat_chunk.png");
		Templates.MEAT_CHUNK.totalHealth = Templates.MEAT_CHUNK.remainingHealth = 13;
		Templates.MEAT_CHUNK.weight = 14f;
		Templates.MEAT_CHUNK.value = 18;
		Templates.MEAT_CHUNK.anchorX = 12;
		Templates.MEAT_CHUNK.anchorY = 20;
		Templates.MEAT_CHUNK.templateId = GameObject.generateNewTemplateId();
		Templates.MEAT_CHUNK.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_DAMAGE,
				new Stat(HIGH_LEVEL_STATS.BLUNT_DAMAGE, 2));

		// WATER_BODY
		Templates.WATER_BODY = new WaterBody();
		Templates.WATER_BODY.name = "Water";
		Templates.WATER_BODY.setImageAndExtrapolateSize("water.png");
		Templates.WATER_BODY.totalHealth = Templates.WATER_BODY.remainingHealth = 1000;
		Templates.WATER_BODY.weight = 1000f;
		Templates.WATER_BODY.value = 36;
		Templates.WATER_BODY.anchorX = 0;
		Templates.WATER_BODY.anchorY = 0;
		Templates.WATER_BODY.templateId = GameObject.generateNewTemplateId();
		Templates.WATER_BODY.flipYAxisInMirror = false;
		Templates.WATER_BODY.canShareSquare = false;
		Templates.WATER_BODY.fitsInInventory = false;
		Templates.WATER_BODY.moveable = false;
		Templates.WATER_BODY.highLevelStats.put(HIGH_LEVEL_STATS.SLASH_RES, new Stat(HIGH_LEVEL_STATS.SLASH_RES, 100));
		Templates.WATER_BODY.highLevelStats.put(HIGH_LEVEL_STATS.BLUNT_RES, new Stat(HIGH_LEVEL_STATS.BLUNT_RES, 100));
		Templates.WATER_BODY.highLevelStats.put(HIGH_LEVEL_STATS.PIERCE_RES,
				new Stat(HIGH_LEVEL_STATS.PIERCE_RES, 100));
		Templates.WATER_BODY.highLevelStats.put(HIGH_LEVEL_STATS.FIRE_RES, new Stat(HIGH_LEVEL_STATS.FIRE_RES, 100));
		Templates.WATER_BODY.highLevelStats.put(HIGH_LEVEL_STATS.WATER_RES, new Stat(HIGH_LEVEL_STATS.WATER_RES, 100));
		Templates.WATER_BODY.highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_RES,
				new Stat(HIGH_LEVEL_STATS.ELECTRICAL_RES, 100));
		Templates.WATER_BODY.highLevelStats.put(HIGH_LEVEL_STATS.POISON_RES,
				new Stat(HIGH_LEVEL_STATS.POISON_RES, 100));
		Templates.WATER_BODY.highLevelStats.put(HIGH_LEVEL_STATS.BLEED_RES, new Stat(HIGH_LEVEL_STATS.BLEED_RES, 100));
		Templates.WATER_BODY.highLevelStats.put(HIGH_LEVEL_STATS.HEALING_RES,
				new Stat(HIGH_LEVEL_STATS.HEALING_RES, 100));
		Templates.WATER_BODY.textures.add(getGlobalImage("water.png", true));
		Templates.WATER_BODY.textures.add(getGlobalImage("water_2.png", true));
		Templates.WATER_BODY.textures.add(getGlobalImage("water_3.png", true));
		Templates.WATER_BODY.textures.add(getGlobalImage("water_4.png", true));

		Templates.VOID_HOLE = new VoidHole();
		Templates.VOID_HOLE.name = "Void";
		Templates.VOID_HOLE.setImageAndExtrapolateSize("void_hole.png");
		Templates.VOID_HOLE.templateId = GameObject.generateNewTemplateId();

		Templates.PORTAL = new Portal();
		Templates.PORTAL.name = "Portal";
		Templates.PORTAL.setImageAndExtrapolateSize("portal.png");
		Templates.PORTAL.templateId = GameObject.generateNewTemplateId();

		Templates.LANDMINE = new Landmine();
		Templates.LANDMINE.name = "Landmine";
		Templates.LANDMINE.setImageAndExtrapolateSize("landmine.png");
		Templates.LANDMINE.templateId = GameObject.generateNewTemplateId();
		Templates.LANDMINE.totalHealth = Templates.LANDMINE.remainingHealth = 10;
		Templates.LANDMINE.weight = 14f;
		Templates.LANDMINE.value = 110;
		Templates.LANDMINE.anchorX = 16;
		Templates.LANDMINE.anchorY = 4;
		Templates.LANDMINE.preDiscoverTexture = ResourceUtils.getGlobalImage("landmine.png", true);
		Templates.LANDMINE.postDiscoverTexture = ResourceUtils.getGlobalImage("landmine.png", true);
		Templates.LANDMINE.power = new PowerInferno(null);
		Templates.LANDMINE.templateId = GameObject.generateNewTemplateId();

		Templates.STONE_FLOOR = new GameObject();
		Templates.STONE_FLOOR.name = "Floor";
		Templates.STONE_FLOOR.imageTexture = Square.STONE_TEXTURE;
		Templates.STONE_FLOOR.canBePickedUp = false;
		Templates.STONE_FLOOR.fitsInInventory = false;
		Templates.STONE_FLOOR.persistsWhenCantBeSeen = true;
		Templates.STONE_FLOOR.attackable = false;
		Templates.STONE_FLOOR.isFloorObject = true;
		Templates.STONE_FLOOR.moveable = false;
		Templates.STONE_FLOOR.orderingOnGound = 20;
		Templates.STONE_FLOOR.templateId = GameObject.generateNewTemplateId();

		Templates.CIRCLE_FLOOR = new GameObject();
		Templates.CIRCLE_FLOOR.name = "Floor";
		Templates.CIRCLE_FLOOR.imageTexture = getGlobalImage("circle.png", true);
		Templates.CIRCLE_FLOOR.canBePickedUp = false;
		Templates.CIRCLE_FLOOR.fitsInInventory = false;
		Templates.CIRCLE_FLOOR.persistsWhenCantBeSeen = true;
		Templates.CIRCLE_FLOOR.attackable = false;
		Templates.CIRCLE_FLOOR.isFloorObject = true;
		Templates.CIRCLE_FLOOR.moveable = false;
		Templates.CIRCLE_FLOOR.orderingOnGound = 20;
		Templates.CIRCLE_FLOOR.templateId = GameObject.generateNewTemplateId();

		Templates.LEAVES = new GameObject();
		Templates.LEAVES.name = "Leaves";
		Templates.LEAVES.imageTexture = getGlobalImage("leaves.png", true);
		Templates.LEAVES.canBePickedUp = false;
		Templates.LEAVES.fitsInInventory = false;
		Templates.LEAVES.persistsWhenCantBeSeen = true;
		Templates.LEAVES.attackable = false;
		Templates.LEAVES.isFloorObject = true;
		Templates.LEAVES.moveable = false;
		Templates.LEAVES.orderingOnGound = 20;
		Templates.LEAVES.templateId = GameObject.generateNewTemplateId();

	}

}
