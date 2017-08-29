package com.marklynch.objects;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.quest.caveoftheblind.Blind;
import com.marklynch.level.quest.caveoftheblind.Mort;
import com.marklynch.objects.tools.Axe;
import com.marklynch.objects.tools.Bell;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.tools.Knife;
import com.marklynch.objects.tools.Lantern;
import com.marklynch.objects.tools.Pickaxe;
import com.marklynch.objects.units.CarnivoreNeutralWildAnimal;
import com.marklynch.objects.units.Farmer;
import com.marklynch.objects.units.HerbivoreWildAnimal;
import com.marklynch.objects.units.Hunter;
import com.marklynch.objects.units.Pig;
import com.marklynch.objects.units.Player;
import com.marklynch.objects.units.RockGolem;
import com.marklynch.objects.units.Thief;
import com.marklynch.objects.units.TinyNeutralWildAnimal;
import com.marklynch.objects.weapons.BodyArmor;
import com.marklynch.objects.weapons.Helmet;
import com.marklynch.objects.weapons.LegArmor;
import com.marklynch.objects.weapons.Weapon;

public class Templates {
	// Player
	public static final Player Player = new Player("You", "Fighter", 10, 100, 10, 0, 0, 0, "hero.png", null, 1, 10,
			null, new Inventory(), 1f, 1.5f, 0f, -0.5f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0f, 90f,
			null, null, 75f, 127f, 70f, 23f, 0f, 0f, 0f, 0f);

	// General People
	public static final Hunter HUNTER = new Hunter("Hunter", "Hunter", 1, 10, 0, 0, 0, 0, "hunter.png", null, 1, 10,
			null, new Inventory(), 1f, 1f, 0, 0, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0f, 90f, null,
			null, 88, 54, 88, 54, 88, 54, 88, 54);

	public static final Thief THIEF = new Thief("Thief", "Thief", 1, 10, 0, 0, 0, 0, "thief.png", null, 1, 10, null,
			new Inventory(), 1.5f, 1.5f, -0.25f, -0.5f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0f, 90f,
			null, null, 88, 54, 88, 54, 88, 54, 88, 54);

	public static final Farmer FARMER = new Farmer("Farmer", "Farmer", 1, 10, 0, 0, 0, 0, "farmer.png", null, 1, 10,
			null, new Inventory(), 1f, 1f, 0, 0, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0f, 90f, null,
			null, 88, 54, 88, 54, 88, 54, 88, 54);

	// Domestic animals
	public static final Pig PIG = new Pig("Pig", "Pig", 1, 10, 0, 0, 0, 0, "pig.png", null, 1, 10, null,
			new Inventory(), 1f, 0.65625f, 0, 0, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0f, 90f, null,
			null, 40, 96, 40, 96, 40, 96, 40, 96);

	// Friendly Wild animals
	public static final TinyNeutralWildAnimal RAT = new TinyNeutralWildAnimal("Rat", "Rat", 1, 1, 0, 0, 0, 0, "rat.png",
			null, 1, 10, null, new Inventory(), 1, 0.25f, 0f, 0.75f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f,
			0f, 0f, 60f, null, null, 0, 0, 0, 0, 0, 0, 0, 0, null);

	public static final HerbivoreWildAnimal RABBIT = new HerbivoreWildAnimal("Rabbit", "Rabbit", 1, 4, 0, 0, 0, 0,
			"rabbit.png", null, 1, 10, null, new Inventory(), 0.5f, 0.5f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false,
			0f, 0f, 0f, 0f, 0f, 60f, null, null, 0, 0, 0, 0, 0, 0, 0, 0, null);

	public static final TinyNeutralWildAnimal BABY_RABBIT = new TinyNeutralWildAnimal("Baby Rabbit", "Baby Rabbit", 1,
			10, 0, 0, 0, 0, "baby_rabbit.png", null, 1, 10, null, new Inventory(), 0.25f, 0.25f, 0f, 0f, 1f, 1f, 1f,
			null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0f, 60f, null, null, 0, 0, 0, 0, 0, 0, 0, 0, null);

	public static final CarnivoreNeutralWildAnimal FOX = new CarnivoreNeutralWildAnimal("Fox", "Fox", 1, 10, 0, 0, 0, 0,
			"fox.png", null, 1, 10, null, new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f,
			0f, 0f, 0f, 60f, null, null, 0, 0, 0, 0, 0, 0, 0, 0, null);

	// Monsters
	public static final Blind BLIND = new Blind("Blind", "Blind", 1, 10, 0, 0, 0, 0, "blind.png", null, 1, 1, null,
			new Inventory(), 1f, 1f, 0, 0, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0f, 50f, null, null, 88,
			54, 88, 54, 88, 54, 88, 54, null);

	public static final RockGolem ROCK_GOLEM = new RockGolem("Rock Golem", "Rock Golem", 1, 100, 0, 0, 0, 0,
			"rock_golem.png", null, 1, 10, null, new Inventory(), 1, 1.5f, 0, -0.5f, 1f, 1f, 1f, null, 0.5f, 0.5f,
			false, 90f, 0f, 0f, 0f, 100f, 150f, null, null, 88, 54, 88, 54, 88, 54, 88, 54, null, false);

	// Special People
	public static final Mort MORT = new Mort("Mort", "Miner", 1, 50, 0, 0, 0, 0, "old_man.png", null, 1, 10, null,
			new Inventory(), 1f, 1f, 0, 0, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0f, 70f, null, null, 88,
			54, 88, 54, 88, 54, 88, 54);

	// Tools
	public static final Weapon BROOM = new Weapon("Broom", 1, 1, 1, "broom.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f, 1f,
			null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 3f, null, 55, 88);
	public static final Pickaxe PICKAXE = new Pickaxe("Pickaxe", 1, 1, "pickaxe.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f,
			1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 5f, null, 32, 94);
	public static final Axe HATCHET = new Axe("Hatchet", 1, 1, "hatchet.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f, 1f,
			null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 5f, null, 48, 89);
	public static final Knife HUNTING_KNIFE = new Knife("Hunting Knife", 1, 1, "hunting_knife.png", 100, null, 1f, 1f,
			0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 5f, null, 47, 58);
	public static final Weapon HOE = new Weapon("Hoe", 2, 1, 1, "hoe.png", 100, null, 1f, 1f, 1f, 1f, 0f, 0f, 1f, null,
			0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 5f, null, 61, 90);
	public static final Weapon SICKLE = new Weapon("Sickle", 3, 1, 1, "sickle.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f,
			1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 5f, null, 20, 29);
	public static final Weapon HAMMER = new Weapon("Hammer", 4, 1, 1, "hammer.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f,
			1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 4f, null, 31, 98);
	public static final Weapon BASKET = new Weapon("Basket", 1, 1, 1, "basket.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f,
			1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 1f, null, 65, 11);
	public static final Weapon WHIP = new Weapon("Whip", 3, 1, 1, "whip.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f, 1f,
			null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 3f, null, 18, 80);
	public static final Weapon SERRATED_SPOON = new Weapon("Serrated Spoon", 1, 1, 1, "serrated_spoon.png", 100, null,
			1f, 1f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 1f, null, 48, 113);
	public static final Bell DINNER_BELL = new Bell("Dinner Bell", 1, 1, "bell.png", 100, null, 1f, 1f, 0f, 0f, 25f,
			25f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 1f, null, 53, 103);
	public static final Lantern LANTERN = new Lantern("Lantern", 1, 1, "lantern.png", 100, null, 1f, 1f, 0f, 0f, 15f,
			15f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 3f, null, 63, 6);

	// Blades
	public static final Weapon KATANA = new Weapon("Katana", 10, 1, 1, "katana.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f,
			1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 3f, null, 27, 85);

	public static final Weapon CLEAVER = new Weapon("Cleaver", 5, 1, 1, "cleaver.png", 100, null, 1f, 1f, 0f, 0f, 1f,
			1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 3f, null, 33, 104);

	// Bows
	// https://en.wikipedia.org/wiki/Bow_and_arrow#Types_of_bow
	public static final Weapon HUNTING_BOW = new Weapon("Hunting Bow", 1, 1, 10, "a2r2.png", 100, null, 1f, 1f, 0f, 0f,
			1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 3f, null, 86, 86);

	// ARMOUR
	// Helmets
	public static final Helmet HARD_HAT = new Helmet("Hard Hat", "hard_hat.png", 100, null, 0.25f, 0.12f, 0f, 0f, 1f,
			1f, 1f, null, 0.5f, 0.5f, false, 50f, 0f, 0f, 0f, 100f, 3f, null, 20, 8);
	public static final Helmet PINK_HARD_HAT = new Helmet("Hard Hat", "pink_hard_hat.png", 100, null, 0.25f, 0.12f, 0f,
			0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 50f, 0f, 0f, 0f, 100f, 3f, null, 20, 8);
	public static final Helmet COWBOY_HAT = new Helmet("Cowby Hat", "cowboy_hat.png", 100, null, 1f, 1f, 0f, 0f, 1f, 1f,
			1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 3f, null, 34, 68);
	// Body Armor
	public static final BodyArmor JUMPER = new BodyArmor("Jumper", "jumper.png", 100, null, 1f, 1.5f, 0f, -32f, 1f, 1f,
			1f, null, 0.5f, 0.5f, false, 50f, 0f, 0f, 0f, 100f, 3f, null, 0, 0);

	// Leg Armor
	public static final LegArmor PANTS = new LegArmor("Pants", "pants.png", 100, null, 1f, 1.5f, 0f, -32f, 1f, 1f, 1f,
			null, 0.5f, 0.5f, false, 50f, 0f, 0f, 0f, 100f, 3f, null, 0, 0);

	// Furniture
	public static final Bed BED = new Bed("Bed", 5, "bed.png", "bed_Covers.png", null, new Inventory(), 1, 1, 0f, 0f,
			1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 75f, null);
	public static final BigGameObject SHOP_COUNTER = new BigGameObject("Shop Counter", 5, "shop_counter.png", null,
			new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, null);
	public static final BigGameObject TABLE = new BigGameObject("Table", 5, "table.png", null, new Inventory(), 1f, 1f,
			0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 20f, null);
	public static final BigGameObject CHAIR = new BigGameObject("Chair", 5, "chair.png", null, new Inventory(), 1f, 1f,
			0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 10f, null);
	public static final BigGameObject BENCH = new BigGameObject("Bench", 5, "bench.png", null, new Inventory(), 2f, 1f,
			-0.5f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 30f, null);
	public static final BigGameObject CHAIR_FALLEN = new BigGameObject("Chair", 5, "chair_fallen.png", null,
			new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 10f, null);
	public static final BigGameObject BARRICADE = new BigGameObject("BARRICADE", 25, "barricade.png", null,
			new Inventory(), 1f, 1f, 0f, 0f, 10f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 25f, null);
	public static final Sign ROCK_WITH_ETCHING = new Sign("Rock with Etching", 1000, "rock_with_etching.png", null,
			new Inventory(), null, 1, 1, 0f, 0f, 20f, 1f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 100f,
			null);
	public static final Readable SCROLL = new Readable("Scroll", 1000, "scroll.png", null, new Inventory(), null, 1, 1,
			0f, 0f, 20f, 1f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 100f, null);
	public static final Sign SIGN = new Sign("Sign", 5, "sign.png", null, new Inventory(), null, 1, 1, 0f, 0f, 1f, 1f,
			1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 15f, null);
	public static final Sign SIGNPOST = new Sign("Signpost", 50, "signpost.png", null, new Inventory(), null, 1, 1.25f,
			0f, -0.25f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 15f, null);
	public static final Readable DOCUMENTS = new Readable("Documents", 5, "documents.png", null, new Inventory(), null,
			0.5f, 0.5f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0.1f, null);

	// Openables
	public static final Door WEAK_WOODEN_DOOR = new Door("Weak Wooden Door", 25, "door.png", null, new Inventory(), 1,
			1, 0f, 0f, 10f, 1f, 5f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 40f, null, false, false, false);
	public static final Door DOOR = new Door("Door", 500, "door.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 10f,
			null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 40f, null, false, false, false);
	public static final Gate GATE = new Gate("Gate", 100, "gate.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 10f,
			null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 40f, null, false, false, false);
	public static final Chest CHEST = new Chest("Chest", 200, "chest.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f,
			1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 25f, null, false);
	public static final RemoteDoor FALSE_WALL = new RemoteDoor("Wall", 500, "wall.png", null, new Inventory(), 1, 1, 0f,
			0f, 1f, 1f, 10f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 40f, null, false);

	public static final Key KEY = new Key("Key", 10, "key.png", null, new Inventory(), 0.3f, 0.3f, 0f, 0f, 1f, 1f, 1f,
			null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 0.5f, null);
	public static final SwitchForOpenables ANTLERS_SWITCH_FOR_OPENABLES = new SwitchForOpenables("Obvious Antlers", 5,
			"antlers.png", null, new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f,
			100f, 0.5f, null, "Touch", "touched", null, null, null);

	// Kitchenware
	public static final GameObject PLATE = new Stampable("Plate", 10, "plate.png", null, new Inventory(), 0.5f, 0.5f,
			0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 40f, 0f, 0f, 0f, 100f, 0.5f, null);
	public static final GameObject BROKEN_PLATE = new Stampable("Broken Plate", 10, "broken_plate.png", null,
			new Inventory(), 0.5f, 0.5f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 40f, 0f, 0f, 0f, 100f, 0.5f,
			null);
	public static final GameObject DINNER_KNIFE = new GameObject("Dinner Knife", 10, "knife.png", null, new Inventory(),
			0.5f, 0.125f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 0.5f, null);
	public static final GameObject DINNER_FORK = new GameObject("Dinner Fork", 10, "fork.png", null, new Inventory(),
			0.5f, 0.125f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 0.5f, null);

	public static final Wall WALL = new Wall("Wall", 1000, "wall.png", null, new Inventory(), 1, 1, 0f, 0f, 10f, 0f,
			10f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 200f, null);
	public static final Wall FENCE = new Fence("Fence", 50, "wall.png", null, new Inventory(), 1, 1, 0f, 0f, 10f, 0f,
			10f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, null);
	public static final Vein VEIN = new Vein("Vein", 1000, "wall.png", null, new Inventory(), 1, 1, 0f, 0f, 15f, 0f,
			10f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 200f, null);
	public static final BigGameObject BOULDER = new BigGameObject("Boulder", 1000, "boulder.png", null, new Inventory(),
			1, 1, 0f, 0f, 15f, 0f, 10f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 200f, null);
	public static final Arrow ARROW = new Arrow("Arrow", 5, "arrow.png", null, new Inventory(), 0.32f, 0.16f, 0f, 0f,
			15f, 0f, 10f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0.1f, null);
	public static final Arrow FIRE_BALL = new Arrow("Fireball", 5, "effect_burn.png", null, new Inventory(), 0.32f,
			0.32f, 0f, 0f, 15f, 0f, 10f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0.1f, null);
	public static final Arrow WATER_BALL = new Arrow("Waterball", 5, "effect_wet.png", null, new Inventory(), 0.32f,
			0.32f, 0f, 0f, 15f, 0f, 10f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0.1f, null);
	public static final GameObject ROCK = new GameObject("Stone", 1000, "rock.png", null, new Inventory(), 0.32f, 0.32f,
			0f, 0f, 15f, 0f, 10f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 100f, null);

	// LARGE CONTAINER
	public static final BigGameObject DUMPSTER = new BigGameObject("Dumpster", 5, "skip_with_shadow.png", null,
			new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 10f, null);
	public static final GameObject TROUGH = new BigGameObject("Trough", 5, "trough.png", null, new Inventory(), 1, 1,
			0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 10f, null);
	// public static final GameObjectTemplate FURNACE = new
	// GameObjectTemplate("Furnace", 5, "furnace.png", null,
	// new Inventory(), true, true, 2, 2,
	// 0.5f, 0.5f, 1f, 1f, 1f, null, 0.5f,
	// 0.5f, false, 0f, 0f, 0f, 0f, 100f,10f);

	public static final Furnace FURNACE = new Furnace("Furnace", 200, "furnace.png", null, new Inventory(), 1.5f, 1.5f,
			-0.25f, -0.5f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 100f, 0f, 0f, 0f, 100f, 150f, null, false);
	public static final WaterSource WELL = new WaterSource("Well", 5, "well.png", null, new Inventory(), 1.5f, 1.5f,
			-0.25f, -0.25f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 100f, 0f, 0f, 0f, 100f, 300f, null, new Effect[] {});
	public static final GameObject SHELF = new GameObject("Shelf", 5, "shelf.png", null, new Inventory(), 1, 0.5f, 0f,
			0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 10f, null);
	public static final Inspectable PIG_SIGN = new Inspectable("Piggy Farm", 5, "pig_sign.png", null, new Inventory(),
			1f, 1f, -0.25f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 80f, 0f, 0f, 0f, 100f, 10f, null);

	// Corpses
	public static final Carcass CARCASS = new Carcass("Carcass", 50, "carcass.png", null, new Inventory(), 0.5f, 0.5f,
			0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0f, null);
	public static final Corpse CORPSE = new Corpse("Carcass", 50, "carcass.png", null, new Inventory(), 0.5f, 0.5f, 0f,
			0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0f, null);
	public static final GameObject ASH = new GameObject("Ash", 50, "ash.png", null, new Inventory(), 0.5f, 0.5f, 0f, 0f,
			1f, 1f, 1f, null, 0.5f, 0.5f, false, 100f, 0f, 0f, 0f, 100f, 0f, null);

	public static final Inspectable BLOODY_PULP = new Inspectable("Bloody Pulp", 50, "blood.png", null, new Inventory(),
			0.5f, 0.5f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0f, 100f, null);

	public static final Inspectable BLOOD = new Inspectable("Blood", 50, "blood.png", null, new Inventory(), 0.5f, 0.5f,
			0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0f, null);

	// Food?
	public static final MeatChunk MEAT_CHUNK = new MeatChunk("Meat Chunk", 5, "meat_chunk.png", null, new Inventory(),
			0.5f, 0.5f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 5f, null);
	public static final ContainerForLiquids JAR = new ContainerForLiquids("Jar", 1, 1, "jar.png", 100, null, 1f, 1f, 0f,
			0f, 25f, 25f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 1f, null, 56, 100, 1f, "water_jar.png");
	public static final Liquid WATER = new Liquid("Water", 5, "effect_wet.png", null, new Inventory(), 0.5f, 0.5f, 0f,
			0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0f, null, 0f,
			new Effect[] { new EffectWet(5) }, new Effect[] {});
	public static final Liquid POISON = new Liquid("Poison", 5, "effect_poison.png", null, new Inventory(), 0.5f, 0.5f,
			0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0f, null, 0f,
			new Effect[] { new EffectPoison(5) }, new Effect[] { new EffectPoison(5) });

	// JUNK
	public static final Junk FUR = new Junk("Fur", 5, "fur.png", null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 1f, null,
			0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 5f, null);
	public static final Junk DIRTY_SHEET = new Junk("Dirty Sheet", 5, "dirty_sheet.png", null, new Inventory(), 0.75f,
			0.56f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 1f, null);
	public static final Junk DIRTY_SHEET_2 = new Junk("Dirty Sheet", 5, "dirty_sheet_2.png", null, new Inventory(),
			0.88f, 1f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 1f, null);
	public static final Junk DIRTY_SHEET_3 = new Junk("Dirty Sheet", 5, "dirty_sheet_3.png", null, new Inventory(),
			0.88f, 1f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 1f, null);
	public static final Junk ORE = new Junk("Ore", 5, "ore.png", null, new Inventory(), 0.25f, 0.25f, 0f, 0f, 1f, 1f,
			1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 5f, null);
	public static final Junk WOOD = new Junk("Wood", 5, "wood.png", null, new Inventory(), 0.5f, 0.25f, 0f, 0f, 1f, 1f,
			1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 5f, null);
	public static final Inspectable DRIED_BLOOD = new Inspectable("Dried Blood", 5, "blood.png", null, new Inventory(),
			1, 1, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 40f, 0f, 0f, 0f, 100f, 0.1f, null);
	public static final MapMarker MAP_MARKER_RED = new MapMarker("", 5, "map_marker_red.png", null, new Inventory(), 1,
			1, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 40f, 0f, 0f, 0f, 100f, 0.1f, null);
	public static final MapMarker MAP_MARKER_GREEN = new MapMarker("", 5, "map_marker_green.png", null, new Inventory(),
			1, 1, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 40f, 0f, 0f, 0f, 100f, 0.1f, null);
	public static final MapMarker MAP_MARKER_BLUE = new MapMarker("", 5, "map_marker_blue.png", null, new Inventory(),
			1, 1, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 40f, 0f, 0f, 0f, 100f, 0.1f, null);
	public static final MapMarker MAP_MARKER_SKULL = new MapMarker("", 5, "map_marker_skull.png", null, new Inventory(),
			1, 1, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 40f, 0f, 0f, 0f, 100f, 0.1f, null);
	public static final MapMarker MAP_MARKER_TREASURE = new MapMarker("", 5, "map_marker_treasure.png", null,
			new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 40f, 0f, 0f, 0f, 100f, 0.1f, null);
	public static final Inspectable GIANT_FOOTPRINT = new Inspectable("Giant Footprint", 5, "footprint.png", null,
			new Inventory(), 2, 1.5f, -0.5f, -0.25f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 100f, 0f, 0f, 0f, 100f, 0.1f,
			null);
	public static final Inspectable GIANT_FOOTPRINT_LEFT = new Inspectable("Giant Footprint", 5, "footprint_left.png",
			null, new Inventory(), 2, 1.5f, -0.5f, -0.25f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 100f, 0f, 0f, 0f, 100f,
			0.1f, null);

	// TRAPS
	public static final BrokenGlass BROKEN_LAMP = new BrokenGlass("Broken Lamp", 5, "smashed_glass.png", null,
			new Inventory(), 0.5f, 0.5f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 3f, null);
	public static final BrokenGlass BROKEN_GLASS = new BrokenGlass("Broken Glass", 5, "smashed_glass.png", null,
			new Inventory(), 0.5f, 0.5f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 90f, 0f, 0f, 0f, 100f, 3f, null);
	public static final Searchable DROP_HOLE = new Searchable("Drop Hole", 5, "drop_hole.png", null, new Inventory(),
			1f, 1f, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 100f, 0f, 0f, 0f, 0f, 100f, null,
			new Effect[] { new EffectPoison(3) });

	// NATURE
	public static final Tree TREE = new Tree("Tree", 100, "tree_1.png", null, new Inventory(), 1f, 1.5f, 0f, -0.5f, 1f,
			1f, 2f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, null);
	public static final Tree BIG_TREE = new Tree("Big Tree", 200, "tree_1.png", null, new Inventory(), 1.5f, 1.5f,
			-0.25f, -0.5f, 1f, 1f, 2f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, null);

	public static final Stump STUMP = new Stump("Stump", 100, "stump.png", null, new Inventory(), 0.5f, 1f, 0.25f, 0f,
			1f, 1f, 2f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, null);
	public static final Stump BIG_STUMP = new Stump("Big Stump", 200, "stump.png", null, new Inventory(), 1f, 1f, 0f,
			0f, 1f, 1f, 2f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, null);

	public static final HidingPlace BUSH = new HidingPlace("Bush", 10, "bush.png", null, new Inventory(), 1f, 1f, 0f,
			0f, 1f, 1f, 2f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, null, new Effect[] {});
	public static final HidingPlace POISON_BUSH = new HidingPlace("Posion Bush", 10, "bush.png", null, new Inventory(),
			1f, 1f, 0f, 0f, 1f, 1f, 2f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, null,
			new Effect[] { new EffectPoison(3) });

	public static final HidingPlace LONG_GRASS = new HidingPlace("Long Grass", 10, "long_grass.png", null,
			new Inventory(), 1f, 1f, 0f, 0f, 1f, 1f, 2f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, null,
			new Effect[] {});

	public static final HidingPlace WHEAT = new HidingPlace("Wheat", 10, "wheat.png", null, new Inventory(), 1f, 1f, 0f,
			0f, 1f, 1f, 2f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 100f, null, new Effect[] {});

	public static final Food MUSHROOM = new Food("Mushroom", 5, "mushroom.png", null, new Inventory(), 0.5f, 0.5f, 0f,
			0f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 100f, 0.5f, null);

	public static final SmallHidingPlace BURROW = new SmallHidingPlace("Burrow", 5, "burrow.png", null, new Inventory(),
			0.5f, 0.5f, 16f, 16f, 1f, 1f, 1f, null, 0.5f, 0.5f, false, 100f, 0f, 0f, 0f, 100f, 0f, null,
			new Effect[] {});

	// BURROW
	// TREE
	//

}
