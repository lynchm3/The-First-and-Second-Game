package com.marklynch.objects.templates;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.quest.caveoftheblind.Blind;
import com.marklynch.level.quest.caveoftheblind.Mort;
import com.marklynch.objects.Arrow;
import com.marklynch.objects.Bed;
import com.marklynch.objects.BigGameObject;
import com.marklynch.objects.BrokenGlass;
import com.marklynch.objects.Carcass;
import com.marklynch.objects.Corpse;
import com.marklynch.objects.Discoverable;
import com.marklynch.objects.Door;
import com.marklynch.objects.Fence;
import com.marklynch.objects.Food;
import com.marklynch.objects.Furnace;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Gate;
import com.marklynch.objects.Gold;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.Inspectable;
import com.marklynch.objects.Junk;
import com.marklynch.objects.Key;
import com.marklynch.objects.Liquid;
import com.marklynch.objects.MapMarker;
import com.marklynch.objects.MeatChunk;
import com.marklynch.objects.Readable;
import com.marklynch.objects.RemoteDoor;
import com.marklynch.objects.Searchable;
import com.marklynch.objects.Sign;
import com.marklynch.objects.SmallHidingPlace;
import com.marklynch.objects.Stampable;
import com.marklynch.objects.Storage;
import com.marklynch.objects.Stump;
import com.marklynch.objects.SwitchForOpenables;
import com.marklynch.objects.Tree;
import com.marklynch.objects.Vein;
import com.marklynch.objects.Wall;
import com.marklynch.objects.WaterSource;
import com.marklynch.objects.tools.Axe;
import com.marklynch.objects.tools.Bell;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.tools.Knife;
import com.marklynch.objects.tools.Lantern;
import com.marklynch.objects.tools.Pickaxe;
import com.marklynch.objects.units.CarnivoreNeutralWildAnimal;
import com.marklynch.objects.units.HerbivoreWildAnimal;
import com.marklynch.objects.units.Human;
import com.marklynch.objects.units.Pig;
import com.marklynch.objects.units.Player;
import com.marklynch.objects.units.RockGolem;
import com.marklynch.objects.units.TinyNeutralWildAnimal;
import com.marklynch.objects.weapons.BodyArmor;
import com.marklynch.objects.weapons.Helmet;
import com.marklynch.objects.weapons.LegArmor;
import com.marklynch.objects.weapons.Weapon;

public class Templates {

	// \t\tpublic\sstatic\sfinal\s[a-zA-Z]+\s

	public Templates() {

		new TemplatesHumans();
		new TemplatesAnimals();
		new TemplatesMonsters();

		// Special People
		MORT = new Mort("Mort", "Miner", 1, 50, 0, 0, 0, 0, "old_man.png", null, 1, 10, null, new Inventory(), 1f, 1f,
				0, 0, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0f, 70f, null, null, 88, 54, 88, 54, 88, 54,
				88, 54, 0, new GameObject[] {}, new GameObject[] {}, GameObject.generateNewTemplateId());

		// Tools
		BROOM = new Weapon("Broom", 0, 0, 1, 0, 0, 0, 0, 1, 1, "broom.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f, 1f, null,
				0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 3f, 10, null, 55, 88, GameObject.generateNewTemplateId());
		PICKAXE = new Pickaxe("Pickaxe", 0, 7, 0, 0, 0, 0, 0, 1, 1, "pickaxe.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f,
				1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 5f, 30, null, 32, 94,
				GameObject.generateNewTemplateId());
		HATCHET = new Axe("Hatchet", 6, 0, 0, 0, 0, 0, 0, 1, 1, "hatchet.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f, 1f,
				null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 5f, 32, null, 48, 89,
				GameObject.generateNewTemplateId());
		HUNTING_KNIFE = new Knife("Hunting Knife", 0, 5, 0, 0, 0, 0, 0, 1, 1, "hunting_knife.png", 100, null, 1f, 1f,
				0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 5f, 27, null, 47, 58,
				GameObject.generateNewTemplateId());
		HOE = new Weapon("Hoe", 2, 0, 0, 0, 0, 0, 0, 1, 1, "hoe.png", 100, null, 1f, 1f, 1f, 1f, 0f, 0f, 1f, null, 0.5f,
				0.5f, false, 0f, 0f, 0f, 0f, 100f, 5f, 40, null, 61, 90, GameObject.generateNewTemplateId());
		SICKLE = new Weapon("Sickle", 3, 0, 0, 0, 0, 0, 0, 1, 1, "sickle.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f, 1f,
				null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 5f, 34, null, 20, 29,
				GameObject.generateNewTemplateId());
		HAMMER = new Weapon("Hammer", 0, 0, 6, 0, 0, 0, 0, 1, 1, "hammer.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f, 1f,
				null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 4f, 29, null, 31, 98,
				GameObject.generateNewTemplateId());
		BASKET = new Weapon("Basket", 0, 0, 1, 0, 0, 0, 0, 1, 1, "basket.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f, 1f,
				null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 1f, 20, null, 65, 11,
				GameObject.generateNewTemplateId());
		WHIP = new Weapon("Whip", 4, 0, 0, 0, 0, 0, 0, 1, 1, "whip.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f, 1f, null,
				0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 3f, 43, null, 18, 80, GameObject.generateNewTemplateId());
		SERRATED_SPOON = new Weapon("Serrated Spoon", 1, 0, 0, 0, 0, 0, 0, 1, 1, "serrated_spoon.png", 100, null, 1f,
				1f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 1f, 23, null, 48, 113,
				GameObject.generateNewTemplateId());
		DINNER_BELL = new Bell("Dinner Bell", 0, 0, 1, 0, 0, 0, 0, 1, 1, "bell.png", 100, null, 1f, 1f, 0f, 0f, 25f,
				25f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 1f, 18, null, 53, 103,
				GameObject.generateNewTemplateId());
		LANTERN = new Lantern("Lantern", 0, 0, 1, 2, 0, 0, 0, 1, 1, "lantern.png", 100, null, 1f, 1f, 0f, 0f, 15f, 15f,
				1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 3f, 32, null, 63, 6,
				GameObject.generateNewTemplateId());

		// Blades
		KATANA = new Weapon("Katana", 10, 10, 0, 0, 0, 0, 0, 1, 1, "katana.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f, 1f,
				null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 3f, 323, null, 27, 85,
				GameObject.generateNewTemplateId());

		CLEAVER = new Weapon("Cleaver", 5, 0, 0, 0, 0, 0, 0, 1, 1, "cleaver.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f, 1f,
				null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 3f, 58, null, 33, 104,
				GameObject.generateNewTemplateId());

		// Bows
		// https://en.wikipedia.org/wiki/Bow_and_arrow#Types_of_bow
		HUNTING_BOW = new Weapon("Hunting Bow", 0, 5, 0, 0, 0, 0, 0, 1, 10, "a2r2.png", 100, null, 1f, 1f, 0f, 0f, 1f,
				1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 3f, 73, null, 86, 86,
				GameObject.generateNewTemplateId());

		// ARMOUR
		// Helmets
		HARD_HAT = new Helmet("Hard Hat", "hard_hat.png", 100, null, 0.25f, 0.12f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f,
				false, 50f, 0f, 0f, 0f, 100f, 3f, 52, null, 20, 8, GameObject.generateNewTemplateId());
		PINK_HARD_HAT = new Helmet("Hard Hat", "pink_hard_hat.png", 100, null, 0.25f, 0.12f, 0f, 0f, 1f, 1f, 1f, null,
				0.5f, 0.5f, false, 50f, 0f, 0f, 0f, 100f, 3f, 53, null, 20, 8, GameObject.generateNewTemplateId());
		COWBOY_HAT = new Helmet("Cowby Hat", "cowboy_hat.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f,
				false, 0f, 0f, 0f, 0f, 100f, 3f, 73, null, 34, 68, GameObject.generateNewTemplateId());
		// Body Armor
		JUMPER = new BodyArmor("Jumper", "jumper.png", 100, null, 1f, 1.5f, 0f, -32f, 1f, 1f, 1f, null, 0.5f, 0.5f,
				false, 50f, 0f, 0f, 0f, 100f, 3f, 54, null, 0, 0, GameObject.generateNewTemplateId());

		// Leg Armor
		PANTS = new LegArmor("Pants", "pants.png", 100, null, 1f, 1.5f, 0f, -32f, 1f, 1f, 1f, null, 0.5f, 0.5f, false,
				50f, 0f, 0f, 0f, 100f, 3f, 78, null, 0, 0, GameObject.generateNewTemplateId());

		// Furniture
		BED = new Bed("Bed", 5, "bed.png", "bed_Covers.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 1f, null,
				0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 75f, 300, null, GameObject.generateNewTemplateId());
		SHOP_COUNTER = new BigGameObject("Shop Counter", 5, "shop_counter.png", null, new Inventory(), 1f, 1f, 0f, 0f,
				1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, 232, null,
				GameObject.generateNewTemplateId());
		TABLE = new BigGameObject("Table", 5, "table.png", null, new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 1f, null,
				0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 20f, 148, null, GameObject.generateNewTemplateId());
		CHAIR = new BigGameObject("Chair", 5, "chair.png", null, new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 1f, null,
				0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 10f, 52, null, GameObject.generateNewTemplateId());
		BENCH = new BigGameObject("Bench", 5, "bench.png", null, new Inventory(), 2f, 1f, -0.5f, 0f, 1f, 1f, 1f, null,
				0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 30f, 86, null, GameObject.generateNewTemplateId());
		CHAIR_FALLEN = new BigGameObject("Chair", 5, "chair_fallen.png", null, new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f,
				1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 10f, 86, null, GameObject.generateNewTemplateId());
		BARRICADE = new BigGameObject("BARRICADE", 25, "barricade.png", null, new Inventory(), 1f, 1f, 0f, 0f, 10f, 1f,
				1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 25f, 24, null, GameObject.generateNewTemplateId());
		ROCK_WITH_ETCHING = new Sign("Rock with Etching", 1000, "rock_with_etching.png", null, new Inventory(), null, 1,
				1, 0f, 0f, 20f, 1f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 100f, 14, null,
				GameObject.generateNewTemplateId());
		SCROLL = new Readable("Scroll", 1000, "scroll.png", null, new Inventory(), null, 1, 1, 0f, 0f, 20f, 1f, 1f,
				null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 100f, 0, null, GameObject.generateNewTemplateId());
		SIGN = new Sign("Sign", 5, "sign.png", null, new Inventory(), null, 1, 1, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f,
				false, 0f, 0f, 0f, 0f, 100f, 15f, 128, null, GameObject.generateNewTemplateId());
		SIGNPOST = new Sign("Signpost", 50, "signpost.png", null, new Inventory(), null, 1, 1.25f, 0f, -0.25f, 1f, 1f,
				1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 15f, 213, null, GameObject.generateNewTemplateId());
		DOCUMENTS = new Readable("Documents", 5, "documents.png", null, new Inventory(), null, 0.5f, 0.5f, 0f, 0f, 1f,
				1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0.1f, 14, null,
				GameObject.generateNewTemplateId());

		// Openables
		WEAK_WOODEN_DOOR = new Door("Weak Wooden Door", 25, "door.png", null, new Inventory(), 1, 1, 0f, 0f, 10f, 1f,
				5f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 40f, 128, null, false, false, false,
				GameObject.generateNewTemplateId());
		DOOR = new Door("Door", 500, "door.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 10f, null, 0.5f, 0.5f,
				false, 0f, 0f, 0f, 0f, 100f, 40f, 275, null, false, false, false, GameObject.generateNewTemplateId());
		GATE = new Gate("Gate", 100, "gate.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 10f, null, 0.5f, 0.5f,
				false, 0f, 0f, 0f, 0f, 100f, 40f, 232, null, false, false, false, GameObject.generateNewTemplateId());
		CHEST = new Storage("Chest", 200, "chest.png", "chest_open.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f,
				1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 25f, 135, null, false,
				GameObject.generateNewTemplateId());
		CRATE = new Storage("Crate", 200, "crate.png", "crate_open.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f,
				1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 25f, 135, null, false,
				GameObject.generateNewTemplateId());
		FALSE_WALL = new RemoteDoor("Wall", 500, "wall.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 10f, null,
				0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 40f, 200, null, false, GameObject.generateNewTemplateId());

		KEY = new Key("Key", 10, "key.png", null, new Inventory(), 0.3f, 0.3f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f,
				false, 90f, 0f, 0f, 0f, 100f, 0.5f, 0, null, GameObject.generateNewTemplateId());
		ANTLERS_SWITCH_FOR_OPENABLES = new SwitchForOpenables("Obvious Antlers", 5, "antlers.png", null,
				new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0.5f, 305,
				null, "Touch", "touched", null, null, null, GameObject.generateNewTemplateId());

		// Kitchenware
		PLATE = new Stampable("Plate", 10, "plate.png", null, new Inventory(), 0.5f, 0.5f, 0f, 0f, 1f, 1f, 1f, null,
				0.5f, 0.5f, false, 40f, 0f, 0f, 0f, 100f, 0.5f, 12, null, GameObject.generateNewTemplateId());
		BROKEN_PLATE = new Stampable("Broken Plate", 10, "broken_plate.png", null, new Inventory(), 0.5f, 0.5f, 0f, 0f,
				1f, 1f, 1f, null, 0.5f, 0.5f, false, 40f, 0f, 0f, 0f, 100f, 0.5f, 1, null,
				GameObject.generateNewTemplateId());
		DINNER_KNIFE = new GameObject("Dinner Knife", 10, "knife.png", null, new Inventory(), 0.5f, 0.125f, 0f, 0f, 1f,
				1f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 0.5f, 5, null,
				GameObject.generateNewTemplateId());

		DINNER_FORK = new GameObject("Dinner Fork", 10, "fork.png", null, new Inventory(), 0.5f, 0.125f, 0f, 0f, 1f, 1f,
				1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 0.5f, 5, null, GameObject.generateNewTemplateId());

		WALL = new Wall("Wall", 1000, "wall.png", null, new Inventory(), 1, 1, 0f, 0f, 10f, 0f, 10f, null, 0.5f, 0.5f,
				false, 90f, 0f, 0f, 0f, 100f, 200f, 100, null, GameObject.generateNewTemplateId());

		FENCE = new Fence("Fence", 50, "wall.png", null, new Inventory(), 1, 1, 0f, 0f, 10f, 0f, 10f, null, 0.5f, 0.5f,
				false, 0f, 0f, 0f, 0f, 100f, 100f, 50, null, GameObject.generateNewTemplateId());

		VEIN = new Vein("Vein", 1000, "wall.png", null, new Inventory(), 1, 1, 0f, 0f, 15f, 0f, 10f, null, 0.5f, 0.5f,
				false, 90f, 0f, 0f, 0f, 100f, 200f, 200, null, GameObject.generateNewTemplateId());

		BOULDER = new BigGameObject("Boulder", 1000, "boulder.png", null, new Inventory(), 1, 1, 0f, 0f, 15f, 0f, 10f,
				null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 200f, 17, null, GameObject.generateNewTemplateId());

		ARROW = new Arrow("Arrow", 5, "arrow.png", null, new Inventory(), 0.32f, 0.16f, 0f, 0f, 15f, 0f, 10f, null,
				0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0.1f, 1, null, GameObject.generateNewTemplateId());

		FIRE_BALL = new Arrow("Fireball", 5, "effect_burn.png", null, new Inventory(), 0.32f, 0.32f, 0f, 0f, 15f, 0f,
				10f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0.1f, 0, null, GameObject.generateNewTemplateId());

		WATER_BALL = new Arrow("Waterball", 5, "effect_wet.png", null, new Inventory(), 0.32f, 0.32f, 0f, 0f, 15f, 0f,
				10f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0.1f, 0, null, GameObject.generateNewTemplateId());

		ROCK = new GameObject("Stone", 1000, "rock.png", null, new Inventory(), 0.32f, 0.32f, 0f, 0f, 15f, 0f, 10f,
				null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 100f, 0, null, GameObject.generateNewTemplateId());

		// LARGE CONTAINER
		DUMPSTER = new BigGameObject("Dumpster", 5, "skip_with_shadow.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f,
				1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 10f, 103, null, GameObject.generateNewTemplateId());

		TROUGH = new BigGameObject("Trough", 5, "trough.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 1f, null,
				0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 10f, 38, null, GameObject.generateNewTemplateId());

		FURNACE = new Furnace("Furnace", 200, "furnace.png", null, new Inventory(), 1.5f, 1.5f, -0.25f, -0.5f, 1f, 1f,
				1f, null, 0.5f, 0.5f, false, 100f, 0f, 0f, 0f, 100f, 150f, 402, null, false,
				GameObject.generateNewTemplateId());

		WELL = new WaterSource("Well", 5, "well.png", null, new Inventory(), 1.5f, 1.5f, -0.25f, -0.25f, 1f, 1f, 1f,
				null, 0.5f, 0.5f, false, 100f, 0f, 0f, 0f, 100f, 300f, 364, null, new Effect[] {},
				GameObject.generateNewTemplateId());

		SHELF = new GameObject("Shelf", 5, "shelf.png", null, new Inventory(), 1, 0.5f, 0f, 0f, 1f, 1f, 1f, null, 0.5f,
				0.5f, false, 0f, 0f, 0f, 0f, 100f, 10f, 28, null, GameObject.generateNewTemplateId());

		PIG_SIGN = new Inspectable("Piggy Farm", 5, "pig_sign.png", null, new Inventory(), 1f, 1f, -0.25f, 0f, 1f, 1f,
				1f, null, 0.5f, 0.5f, false, 80f, 0f, 0f, 0f, 100f, 10f, 92, null, GameObject.generateNewTemplateId());

		// Corpses
		CARCASS = new Carcass("Carcass", 50, "carcass.png", null, new Inventory(), 0.5f, 0.5f, 0f, 0f, 1f, 1f, 1f, null,
				0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0f, 15, null, GameObject.generateNewTemplateId());

		CORPSE = new Corpse("Carcass", 50, "carcass.png", null, new Inventory(), 0.5f, 0.5f, 0f, 0f, 1f, 1f, 1f, null,
				0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0f, 0, null, GameObject.generateNewTemplateId());

		ASH = new GameObject("Ash", 50, "ash.png", null, new Inventory(), 0.5f, 0.5f, 0f, 0f, 1f, 1f, 1f, null, 0.5f,
				0.5f, false, 100f, 0f, 0f, 0f, 100f, 0f, 0, null, GameObject.generateNewTemplateId());

		BLOODY_PULP = new Inspectable("Bloody Pulp", 50, "blood.png", null, new Inventory(), 0.5f, 0.5f, 0f, 0f, 1f, 1f,
				1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0f, 100f, 15, null, GameObject.generateNewTemplateId());

		BLOOD = new Inspectable("Blood", 50, "blood.png", null, new Inventory(), 0.5f, 0.5f, 0f, 0f, 1f, 1f, 1f, null,
				0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0f, 10, null, GameObject.generateNewTemplateId());

		// Food?
		MEAT_CHUNK = new MeatChunk("Meat Chunk", 5, "meat_chunk.png", null, new Inventory(), 0.5f, 0.5f, 0f, 0f, 1f, 1f,
				1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 5f, 10, null, GameObject.generateNewTemplateId());

		JAR = new ContainerForLiquids("Jar", 2, 2, 0, 0, 0, 0, 0, 1, 1, "jar.png", 100, null, 1f, 1f, 0f, 0f, 25f, 25f,
				1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 1f, 5, null, 56, 100, 1f, "water_jar.png",
				GameObject.generateNewTemplateId());

		WATER = new Liquid("Water", 5, "effect_wet.png", null, new Inventory(), 0.5f, 0.5f, 0f, 0f, 1f, 1f, 1f, null,
				0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0f, 2, null, 0f, new Effect[] { new EffectWet(5) },
				new Effect[] {}, GameObject.generateNewTemplateId());

		POISON = new Liquid("Poison", 5, "effect_poison.png", null, new Inventory(), 0.5f, 0.5f, 0f, 0f, 1f, 1f, 1f,
				null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0f, 25, null, 0f, new Effect[] { new EffectPoison(5) },
				new Effect[] { new EffectPoison(5) }, GameObject.generateNewTemplateId());

		// JUNK
		FUR = new Junk("Fur", 5, "fur.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false,
				0f, 0f, 0f, 0f, 100f, 5f, 55, null, GameObject.generateNewTemplateId());

		DIRTY_SHEET = new Junk("Dirty Sheet", 5, "dirty_sheet.png", null, new Inventory(), 0.75f, 0.56f, 0f, 0f, 1f, 1f,
				1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 1f, 5, null, GameObject.generateNewTemplateId());

		DIRTY_SHEET_2 = new Junk("Dirty Sheet", 5, "dirty_sheet_2.png", null, new Inventory(), 0.88f, 1f, 0f, 0f, 1f,
				1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 1f, 5, null, GameObject.generateNewTemplateId());

		DIRTY_SHEET_3 = new Junk("Dirty Sheet", 5, "dirty_sheet_3.png", null, new Inventory(), 0.88f, 1f, 0f, 0f, 1f,
				1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 1f, 5, null, GameObject.generateNewTemplateId());

		ORE = new Junk("Ore", 5, "ore.png", null, new Inventory(), 0.25f, 0.25f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f,
				false, 90f, 0f, 0f, 0f, 100f, 5f, 39, null, GameObject.generateNewTemplateId());

		WOOD = new Junk("Wood", 5, "wood.png", null, new Inventory(), 0.5f, 0.25f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f,
				false, 90f, 0f, 0f, 0f, 100f, 5f, 3, null, GameObject.generateNewTemplateId());

		DRIED_BLOOD = new Inspectable("Dried Blood", 5, "blood.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 1f,
				null, 0.5f, 0.5f, false, 40f, 0f, 0f, 0f, 100f, 0.1f, 1, null, GameObject.generateNewTemplateId());

		MAP_MARKER_RED = new MapMarker("", 5, "map_marker_red.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 1f,
				null, 0.5f, 0.5f, false, 40f, 0f, 0f, 0f, 100f, 0.1f, 0, null, GameObject.generateNewTemplateId());

		MAP_MARKER_GREEN = new MapMarker("", 5, "map_marker_green.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 1f,
				null, 0.5f, 0.5f, false, 40f, 0f, 0f, 0f, 100f, 0.1f, 0, null, GameObject.generateNewTemplateId());

		MAP_MARKER_BLUE = new MapMarker("", 5, "map_marker_blue.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 1f,
				null, 0.5f, 0.5f, false, 40f, 0f, 0f, 0f, 100f, 0.1f, 0, null, GameObject.generateNewTemplateId());

		MAP_MARKER_SKULL = new MapMarker("", 5, "map_marker_skull.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 1f,
				null, 0.5f, 0.5f, false, 40f, 0f, 0f, 0f, 100f, 0.1f, 0, null, GameObject.generateNewTemplateId());

		MAP_MARKER_TREASURE = new MapMarker("", 5, "map_marker_treasure.png", null, new Inventory(), 1, 1, 0f, 0f, 1f,
				1f, 1f, null, 0.5f, 0.5f, false, 40f, 0f, 0f, 0f, 100f, 0.1f, 0, null,
				GameObject.generateNewTemplateId());

		GIANT_FOOTPRINT = new Inspectable("Giant Footprint", 5, "footprint.png", null, new Inventory(), 2, 1.5f, -0.5f,
				-0.25f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 100f, 0f, 0f, 0f, 100f, 0.1f, 0, null,
				GameObject.generateNewTemplateId());

		GIANT_FOOTPRINT_LEFT = new Inspectable("Giant Footprint", 5, "footprint_left.png", null, new Inventory(), 2,
				1.5f, -0.5f, -0.25f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 100f, 0f, 0f, 0f, 100f, 0.1f, 0, null,
				GameObject.generateNewTemplateId());

		// TRAPS
		BROKEN_LAMP = new BrokenGlass("Broken Lamp", 5, "smashed_glass.png", null, new Inventory(), 0.5f, 0.5f, 0f, 0f,
				1f, 1f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 3f, 1, null,
				GameObject.generateNewTemplateId());

		BROKEN_GLASS = new BrokenGlass("Broken Glass", 5, "smashed_glass.png", null, new Inventory(), 0.5f, 0.5f, 0f,
				0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 3f, 1, null,
				GameObject.generateNewTemplateId());

		DROP_HOLE = new Searchable("Drop Hole", 5, "drop_hole.png", null, new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 1f,
				null, 0.5f, 0.5f, false, 100f, 0f, 0f, 0f, 0f, 100f, 0, null, new Effect[] { new EffectPoison(3) },
				GameObject.generateNewTemplateId());

		// NATURE
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

		// GOLD BITCH
		GOLD = new Gold("Gold", 10, "gold.png", null, new Inventory(), 0.5f, 0.125f, 0f, 0f, 1f, 1f, 1f, null, 0.5f,
				0.5f, false, 90f, 0f, 0f, 0f, 100f, 0.5f, 0, null, GameObject.generateNewTemplateId());

	}

	// GOLD
	public static Gold GOLD;

	// Player
	public static Player PLAYER;

	// General People
	public static Human HUNTER;

	public static Human THIEF;

	public static Human FARMER;

	// Domestic animals
	public static Pig PIG;

	// Wild animals
	public static TinyNeutralWildAnimal RAT;

	public static HerbivoreWildAnimal RABBIT;

	public static TinyNeutralWildAnimal BABY_RABBIT;

	public static CarnivoreNeutralWildAnimal FOX;

	public static CarnivoreNeutralWildAnimal WOLF;

	// Monsters
	public static Blind BLIND;

	public static RockGolem ROCK_GOLEM;

	// Special People
	public static Mort MORT;

	// Tools
	public static Weapon BROOM;

	public static Pickaxe PICKAXE;

	public static Axe HATCHET;

	public static Knife HUNTING_KNIFE;

	public static Weapon HOE;

	public static Weapon SICKLE;

	public static Weapon HAMMER;

	public static Weapon BASKET;

	public static Weapon WHIP;

	public static Weapon SERRATED_SPOON;

	public static Bell DINNER_BELL;

	public static Lantern LANTERN;

	// Blades
	public static Weapon KATANA;

	public static Weapon CLEAVER;

	// Bows
	// https://en.wikipedia.org/wiki/Bow_and_arrow#Types_of_bow
	public static Weapon HUNTING_BOW;

	// ARMOUR
	// Helmets
	public static Helmet HARD_HAT;

	public static Helmet PINK_HARD_HAT;

	public static Helmet COWBOY_HAT;

	// Body Armor
	public static BodyArmor JUMPER;

	// Leg Armor
	public static LegArmor PANTS;

	// Furniture;
	public static Bed BED;

	public static BigGameObject SHOP_COUNTER;

	public static BigGameObject TABLE;

	public static BigGameObject CHAIR;

	public static BigGameObject BENCH;

	public static BigGameObject CHAIR_FALLEN;

	public static BigGameObject BARRICADE;

	public static Sign ROCK_WITH_ETCHING;

	public static Readable SCROLL;

	public static Sign SIGN;

	public static Sign SIGNPOST;

	public static Readable DOCUMENTS;

	// Openables
	public static Door WEAK_WOODEN_DOOR;

	public static Door DOOR;

	public static Gate GATE;

	public static Storage CHEST;

	public static Storage CRATE;

	public static RemoteDoor FALSE_WALL;;
	public static Key KEY;

	public static SwitchForOpenables ANTLERS_SWITCH_FOR_OPENABLES;

	// Kitchenware
	public static GameObject PLATE;

	public static GameObject BROKEN_PLATE;

	public static GameObject DINNER_KNIFE;

	public static GameObject DINNER_FORK;

	public static Wall WALL;

	public static Wall FENCE;

	public static Vein VEIN;

	public static BigGameObject BOULDER;

	public static Arrow ARROW;

	public static Arrow FIRE_BALL;

	public static Arrow WATER_BALL;

	public static GameObject ROCK;

	// LARGE CONTAINER
	public static BigGameObject DUMPSTER;

	public static GameObject TROUGH;

	public static Furnace FURNACE;

	public static WaterSource WELL;

	public static GameObject SHELF;

	public static Inspectable PIG_SIGN;

	// Corpses
	public static Carcass CARCASS;

	public static Corpse CORPSE;

	public static GameObject ASH;

	public static Inspectable BLOODY_PULP;;;
	public static Inspectable BLOOD;

	// Food?
	public static MeatChunk MEAT_CHUNK;

	public static ContainerForLiquids JAR;

	public static Liquid WATER;

	public static Liquid POISON;

	// JUNK
	public static Junk FUR;

	public static Junk DIRTY_SHEET;

	public static Junk DIRTY_SHEET_2;

	public static Junk DIRTY_SHEET_3;

	public static Junk ORE;

	public static Junk WOOD;

	public static Inspectable DRIED_BLOOD;

	public static MapMarker MAP_MARKER_RED;

	public static MapMarker MAP_MARKER_GREEN;

	public static MapMarker MAP_MARKER_BLUE;

	public static MapMarker MAP_MARKER_SKULL;

	public static MapMarker MAP_MARKER_TREASURE;

	public static Inspectable GIANT_FOOTPRINT;

	public static Inspectable GIANT_FOOTPRINT_LEFT;

	// TRAPS
	public static BrokenGlass BROKEN_LAMP;

	public static BrokenGlass BROKEN_GLASS;

	public static Searchable DROP_HOLE;

	// NATURE
	public static Tree TREE;

	public static Tree BIG_TREE;

	public static Stump STUMP;

	public static Stump BIG_STUMP;

	public static HidingPlace BUSH;

	public static HidingPlace POISON_BUSH;

	public static HidingPlace LONG_GRASS;

	public static HidingPlace WHEAT;

	public static Food MUSHROOM;

	public static SmallHidingPlace BURROW;

	public static Discoverable MOUND;

}
