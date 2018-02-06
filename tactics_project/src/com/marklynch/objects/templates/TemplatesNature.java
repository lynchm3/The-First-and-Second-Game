package com.marklynch.objects.templates;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.objects.Discoverable;
import com.marklynch.objects.Food;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.MeatChunk;
import com.marklynch.objects.SmallHidingPlace;
import com.marklynch.objects.Stump;
import com.marklynch.objects.Tree;
import com.marklynch.objects.Vein;
import com.marklynch.utils.ResourceUtils;

public class TemplatesNature {

	public TemplatesNature() {

		Templates.TREE = new Tree();
		Templates.TREE.name = "Tree";
		Templates.TREE.imageTexturePath = "tree_1.png";
		Templates.TREE.totalHealth = Templates.TREE.remainingHealth = 100;
		Templates.TREE.widthRatio = 1f;
		Templates.TREE.heightRatio = 1.5f;
		Templates.TREE.drawOffsetRatioX = 0f;
		Templates.TREE.drawOffsetRatioY = -0.5f;
		Templates.TREE.soundWhenHit = 1f;
		Templates.TREE.soundWhenHitting = 1f;
		Templates.TREE.soundDampening = 1f;
		Templates.TREE.stackable = false;
		Templates.TREE.weight = 100f;
		Templates.TREE.value = 10;
		Templates.TREE.anchorX = 0;
		Templates.TREE.anchorY = 0;
		Templates.TREE.templateId = GameObject.generateNewTemplateId();

		Templates.BIG_TREE = new Tree();
		Templates.BIG_TREE.name = "Big Tree";
		Templates.BIG_TREE.imageTexturePath = "tree_1.png";
		Templates.BIG_TREE.totalHealth = Templates.BIG_TREE.remainingHealth = 150;
		Templates.BIG_TREE.widthRatio = 1.5f;
		Templates.BIG_TREE.heightRatio = 1.5f;
		Templates.BIG_TREE.drawOffsetRatioX = -0.25f;
		Templates.BIG_TREE.drawOffsetRatioY = -0.5f;
		Templates.BIG_TREE.soundWhenHit = 1f;
		Templates.BIG_TREE.soundWhenHitting = 1f;
		Templates.BIG_TREE.soundDampening = 1f;
		Templates.BIG_TREE.stackable = false;
		Templates.BIG_TREE.weight = 200f;
		Templates.BIG_TREE.value = 13;
		Templates.BIG_TREE.anchorX = 0;
		Templates.BIG_TREE.anchorY = 0;
		Templates.BIG_TREE.templateId = GameObject.generateNewTemplateId();

		Templates.STUMP = new Stump();
		Templates.STUMP.name = "Stump";
		Templates.STUMP.imageTexturePath = "stump.png";
		Templates.STUMP.totalHealth = Templates.STUMP.remainingHealth = 52;
		Templates.STUMP.widthRatio = 0.5f;
		Templates.STUMP.heightRatio = 1f;
		Templates.STUMP.drawOffsetRatioX = 0.25f;
		Templates.STUMP.drawOffsetRatioY = 0f;
		Templates.STUMP.soundWhenHit = 1f;
		Templates.STUMP.soundWhenHitting = 1f;
		Templates.STUMP.soundDampening = 1f;
		Templates.STUMP.stackable = false;
		Templates.STUMP.weight = 50f;
		Templates.STUMP.value = 6;
		Templates.STUMP.anchorX = 0;
		Templates.STUMP.anchorY = 0;
		Templates.STUMP.templateId = GameObject.generateNewTemplateId();

		Templates.BIG_STUMP = new Stump();
		Templates.BIG_STUMP.name = "";
		Templates.BIG_STUMP.imageTexturePath = "BIG_STUMP.png";
		Templates.BIG_STUMP.totalHealth = Templates.BIG_STUMP.remainingHealth = 73;
		Templates.BIG_STUMP.widthRatio = 1f;
		Templates.BIG_STUMP.heightRatio = 1f;
		Templates.BIG_STUMP.drawOffsetRatioX = 0f;
		Templates.BIG_STUMP.drawOffsetRatioY = 0f;
		Templates.BIG_STUMP.soundWhenHit = 1f;
		Templates.BIG_STUMP.soundWhenHitting = 1f;
		Templates.BIG_STUMP.soundDampening = 1f;
		Templates.BIG_STUMP.stackable = false;
		Templates.BIG_STUMP.weight = 83f;
		Templates.BIG_STUMP.value = 9;
		Templates.BIG_STUMP.anchorX = 0;
		Templates.BIG_STUMP.anchorY = 0;
		Templates.BIG_STUMP.templateId = GameObject.generateNewTemplateId();

		Templates.BUSH = new HidingPlace();
		Templates.BUSH.name = "Bush";
		Templates.BUSH.imageTexturePath = "bush.png";
		Templates.BUSH.totalHealth = Templates.BUSH.remainingHealth = 21;
		Templates.BUSH.widthRatio = 1f;
		Templates.BUSH.heightRatio = 1f;
		Templates.BUSH.drawOffsetRatioX = 0f;
		Templates.BUSH.drawOffsetRatioY = 0f;
		Templates.BUSH.soundWhenHit = 1f;
		Templates.BUSH.soundWhenHitting = 1f;
		Templates.BUSH.soundDampening = 1f;
		Templates.BUSH.stackable = false;
		Templates.BUSH.weight = 34f;
		Templates.BUSH.value = 11;
		Templates.BUSH.anchorX = 0;
		Templates.BUSH.anchorY = 0;
		Templates.BUSH.templateId = GameObject.generateNewTemplateId();
		Templates.BUSH.effectsFromInteracting = new Effect[] {};

		Templates.POISON_BUSH = new HidingPlace();
		Templates.POISON_BUSH.name = "Posion Bush";
		Templates.POISON_BUSH.imageTexturePath = "bush.png";
		Templates.POISON_BUSH.totalHealth = Templates.POISON_BUSH.remainingHealth = 21;
		Templates.POISON_BUSH.widthRatio = 1f;
		Templates.POISON_BUSH.heightRatio = 1f;
		Templates.POISON_BUSH.drawOffsetRatioX = 0f;
		Templates.POISON_BUSH.drawOffsetRatioY = 0f;
		Templates.POISON_BUSH.soundWhenHit = 1f;
		Templates.POISON_BUSH.soundWhenHitting = 1f;
		Templates.POISON_BUSH.soundDampening = 1f;
		Templates.POISON_BUSH.stackable = false;
		Templates.POISON_BUSH.weight = 34f;
		Templates.POISON_BUSH.value = 16;
		Templates.POISON_BUSH.anchorX = 0;
		Templates.POISON_BUSH.anchorY = 0;
		Templates.POISON_BUSH.effectsFromInteracting = new Effect[] { new EffectPoison(3) };
		Templates.POISON_BUSH.templateId = GameObject.generateNewTemplateId();

		Templates.LONG_GRASS = new HidingPlace();
		Templates.LONG_GRASS.name = "Long Grass";
		Templates.LONG_GRASS.imageTexturePath = "long_grass.png";
		Templates.LONG_GRASS.totalHealth = Templates.LONG_GRASS.remainingHealth = 10;
		Templates.LONG_GRASS.widthRatio = 1f;
		Templates.LONG_GRASS.heightRatio = 1f;
		Templates.LONG_GRASS.drawOffsetRatioX = 0f;
		Templates.LONG_GRASS.drawOffsetRatioY = 0f;
		Templates.LONG_GRASS.soundWhenHit = 1f;
		Templates.LONG_GRASS.soundWhenHitting = 1f;
		Templates.LONG_GRASS.soundDampening = 1f;
		Templates.LONG_GRASS.stackable = false;
		Templates.LONG_GRASS.weight = 5f;
		Templates.LONG_GRASS.value = 8;
		Templates.LONG_GRASS.anchorX = 0;
		Templates.LONG_GRASS.anchorY = 0;
		Templates.LONG_GRASS.templateId = GameObject.generateNewTemplateId();
		Templates.LONG_GRASS.effectsFromInteracting = new Effect[] {};

		Templates.WHEAT = new HidingPlace();
		Templates.WHEAT.name = "Wheat";
		Templates.WHEAT.imageTexturePath = "wheat.png";
		Templates.WHEAT.totalHealth = Templates.WHEAT.remainingHealth = 11;
		Templates.WHEAT.widthRatio = 1f;
		Templates.WHEAT.heightRatio = 1f;
		Templates.WHEAT.drawOffsetRatioX = 0f;
		Templates.WHEAT.drawOffsetRatioY = 0f;
		Templates.WHEAT.soundWhenHit = 1f;
		Templates.WHEAT.soundWhenHitting = 1f;
		Templates.WHEAT.soundDampening = 1f;
		Templates.WHEAT.stackable = false;
		Templates.WHEAT.weight = 9f;
		Templates.WHEAT.value = 17;
		Templates.WHEAT.anchorX = 0;
		Templates.WHEAT.anchorY = 0;
		Templates.WHEAT.templateId = GameObject.generateNewTemplateId();
		Templates.WHEAT.effectsFromInteracting = new Effect[] {};

		Templates.MUSHROOM = new Food();
		Templates.MUSHROOM.name = "Mushroom";
		Templates.MUSHROOM.imageTexturePath = "mushroom.png";
		Templates.MUSHROOM.totalHealth = Templates.MUSHROOM.remainingHealth = 13;
		Templates.MUSHROOM.widthRatio = 0.5f;
		Templates.MUSHROOM.heightRatio = 0.5f;
		Templates.MUSHROOM.drawOffsetRatioX = 0f;
		Templates.MUSHROOM.drawOffsetRatioY = 0f;
		Templates.MUSHROOM.soundWhenHit = 1f;
		Templates.MUSHROOM.soundWhenHitting = 1f;
		Templates.MUSHROOM.soundDampening = 1f;
		Templates.MUSHROOM.stackable = false;
		Templates.MUSHROOM.weight = 14f;
		Templates.MUSHROOM.value = 19;
		Templates.MUSHROOM.anchorX = 0;
		Templates.MUSHROOM.anchorY = 0;
		Templates.MUSHROOM.templateId = GameObject.generateNewTemplateId();

		Templates.BURROW = new SmallHidingPlace();
		Templates.BURROW.name = "Burrow";
		Templates.BURROW.imageTexturePath = "burrow.png";
		Templates.BURROW.totalHealth = Templates.BURROW.remainingHealth = 32;
		Templates.BURROW.widthRatio = 0.5f;
		Templates.BURROW.heightRatio = 0.5f;
		Templates.BURROW.drawOffsetRatioX = 16f;
		Templates.BURROW.drawOffsetRatioY = 16f;
		Templates.BURROW.soundWhenHit = 1f;
		Templates.BURROW.soundWhenHitting = 1f;
		Templates.BURROW.soundDampening = 1f;
		Templates.BURROW.stackable = false;
		Templates.BURROW.weight = 100f;
		Templates.BURROW.value = 14;
		Templates.BURROW.anchorX = 0;
		Templates.BURROW.anchorY = 0;
		Templates.BURROW.templateId = GameObject.generateNewTemplateId();

		Templates.MOUND = new Discoverable();
		Templates.MOUND.name = "Mound of Dirt";
		Templates.MOUND.imageTexturePath = "mound.png";
		Templates.MOUND.totalHealth = Templates.MOUND.remainingHealth = 103;
		Templates.MOUND.widthRatio = 1f;
		Templates.MOUND.heightRatio = 1f;
		Templates.MOUND.drawOffsetRatioX = 0f;
		Templates.MOUND.drawOffsetRatioY = 0f;
		Templates.MOUND.soundWhenHit = 1f;
		Templates.MOUND.soundWhenHitting = 1f;
		Templates.MOUND.soundDampening = 1f;
		Templates.MOUND.stackable = false;
		Templates.MOUND.weight = 100f;
		Templates.MOUND.value = 15;
		Templates.MOUND.anchorX = 0;
		Templates.MOUND.anchorY = 0;
		Templates.MOUND.templateId = GameObject.generateNewTemplateId();
		Templates.MOUND.preDiscoverTexture = ResourceUtils.getGlobalImage("mound.png");
		Templates.MOUND.postDiscoverTexture = ResourceUtils.getGlobalImage("mound.png");
		Templates.MOUND.diggable = true;
		Templates.MOUND.canContainOtherObjects = false;

		Templates.APPLE = new Food();
		Templates.APPLE.name = "Apple";
		Templates.APPLE.imageTexturePath = "apple.png";
		Templates.APPLE.totalHealth = Templates.APPLE.remainingHealth = 11;
		Templates.APPLE.widthRatio = 0.25f;
		Templates.APPLE.heightRatio = 0.25f;
		Templates.APPLE.drawOffsetRatioX = 0f;
		Templates.APPLE.drawOffsetRatioY = 0f;
		Templates.APPLE.soundWhenHit = 1f;
		Templates.APPLE.soundWhenHitting = 1f;
		Templates.APPLE.soundDampening = 1f;
		Templates.APPLE.stackable = false;
		Templates.APPLE.weight = 5f;
		Templates.APPLE.value = 15;
		Templates.APPLE.anchorX = 0;
		Templates.APPLE.anchorY = 0;
		Templates.APPLE.templateId = GameObject.generateNewTemplateId();
		Templates.APPLE.bluntDamage = 1;

		Templates.ROCK = new GameObject();
		Templates.ROCK.name = "Rock";
		Templates.ROCK.imageTexturePath = "rock.png";
		Templates.ROCK.totalHealth = Templates.ROCK.remainingHealth = 51;
		Templates.ROCK.widthRatio = 0.32f;
		Templates.ROCK.heightRatio = 0.32f;
		Templates.ROCK.drawOffsetRatioX = 0f;
		Templates.ROCK.drawOffsetRatioY = 0f;
		Templates.ROCK.soundWhenHit = 1f;
		Templates.ROCK.soundWhenHitting = 1f;
		Templates.ROCK.soundDampening = 1f;
		Templates.ROCK.stackable = false;
		Templates.ROCK.weight = 12f;
		Templates.ROCK.value = 6;
		Templates.ROCK.anchorX = 12;
		Templates.ROCK.anchorY = 14;
		Templates.ROCK.templateId = GameObject.generateNewTemplateId();
		Templates.ROCK.bluntDamage = 5;

		Templates.VEIN = new Vein();
		Templates.VEIN.name = "Vein";
		Templates.VEIN.imageTexturePath = "wall.png";
		Templates.VEIN.totalHealth = Templates.VEIN.remainingHealth = 1000;
		Templates.VEIN.widthRatio = 1f;
		Templates.VEIN.heightRatio = 1f;
		Templates.VEIN.drawOffsetRatioX = 0f;
		Templates.VEIN.drawOffsetRatioY = 0f;
		Templates.VEIN.soundWhenHit = 1f;
		Templates.VEIN.soundWhenHitting = 1f;
		Templates.VEIN.soundDampening = 1f;
		Templates.VEIN.stackable = false;
		Templates.VEIN.weight = 1000f;
		Templates.VEIN.value = 36;
		Templates.VEIN.anchorX = 0;
		Templates.VEIN.anchorY = 0;
		Templates.VEIN.templateId = GameObject.generateNewTemplateId();

		// Food?
		Templates.MEAT_CHUNK = new MeatChunk();
		Templates.MEAT_CHUNK.name = "Meat Chunk";
		Templates.MEAT_CHUNK.imageTexturePath = "meat_chunk.png";
		Templates.MEAT_CHUNK.totalHealth = Templates.MEAT_CHUNK.remainingHealth = 13;
		Templates.MEAT_CHUNK.widthRatio = 1f;
		Templates.MEAT_CHUNK.heightRatio = 1f;
		Templates.MEAT_CHUNK.drawOffsetRatioX = 0f;
		Templates.MEAT_CHUNK.drawOffsetRatioY = 0f;
		Templates.MEAT_CHUNK.soundWhenHit = 1f;
		Templates.MEAT_CHUNK.soundWhenHitting = 1f;
		Templates.MEAT_CHUNK.soundDampening = 1f;
		Templates.MEAT_CHUNK.stackable = false;
		Templates.MEAT_CHUNK.weight = 14f;
		Templates.MEAT_CHUNK.value = 18;
		Templates.MEAT_CHUNK.anchorX = 0;
		Templates.MEAT_CHUNK.anchorY = 0;
		Templates.MEAT_CHUNK.templateId = GameObject.generateNewTemplateId();
		Templates.MEAT_CHUNK.bluntDamage = 2;

	}

}
