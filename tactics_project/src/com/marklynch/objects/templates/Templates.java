package com.marklynch.objects.templates;

import com.marklynch.objects.actors.Blind;
import com.marklynch.objects.actors.CarnivoreNeutralWildAnimal;
import com.marklynch.objects.actors.Doctor;
import com.marklynch.objects.actors.Fish;
import com.marklynch.objects.actors.Guard;
import com.marklynch.objects.actors.HerbivoreWildAnimal;
import com.marklynch.objects.actors.Human;
import com.marklynch.objects.actors.Kidnapper;
import com.marklynch.objects.actors.Mort;
import com.marklynch.objects.actors.Pig;
import com.marklynch.objects.actors.Player;
import com.marklynch.objects.actors.RockGolem;
import com.marklynch.objects.actors.Thief;
import com.marklynch.objects.actors.TinyNeutralWildAnimal;
import com.marklynch.objects.actors.Trader;
import com.marklynch.objects.armor.BodyArmor;
import com.marklynch.objects.armor.Helmet;
import com.marklynch.objects.armor.LegArmor;
import com.marklynch.objects.armor.Weapon;
import com.marklynch.objects.inanimateobjects.Arrow;
import com.marklynch.objects.inanimateobjects.AttackableSwitch;
import com.marklynch.objects.inanimateobjects.Bed;
import com.marklynch.objects.inanimateobjects.BrokenGlass;
import com.marklynch.objects.inanimateobjects.Carcass;
import com.marklynch.objects.inanimateobjects.Corpse;
import com.marklynch.objects.inanimateobjects.Door;
import com.marklynch.objects.inanimateobjects.ElectricalWiring;
import com.marklynch.objects.inanimateobjects.Fireplace;
import com.marklynch.objects.inanimateobjects.Food;
import com.marklynch.objects.inanimateobjects.Furnace;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Gold;
import com.marklynch.objects.inanimateobjects.HidingPlace;
import com.marklynch.objects.inanimateobjects.InputDrain;
import com.marklynch.objects.inanimateobjects.Inspectable;
import com.marklynch.objects.inanimateobjects.Landmine;
import com.marklynch.objects.inanimateobjects.Liquid;
import com.marklynch.objects.inanimateobjects.MapMarker;
import com.marklynch.objects.inanimateobjects.Matches;
import com.marklynch.objects.inanimateobjects.MeatChunk;
import com.marklynch.objects.inanimateobjects.MineCart;
import com.marklynch.objects.inanimateobjects.Mirror;
import com.marklynch.objects.inanimateobjects.Orb;
import com.marklynch.objects.inanimateobjects.Portal;
import com.marklynch.objects.inanimateobjects.PressurePlate;
import com.marklynch.objects.inanimateobjects.PressurePlateRequiringSpecificItem;
import com.marklynch.objects.inanimateobjects.Rail;
import com.marklynch.objects.inanimateobjects.RemoteDoor;
import com.marklynch.objects.inanimateobjects.Searchable;
import com.marklynch.objects.inanimateobjects.Seesaw;
import com.marklynch.objects.inanimateobjects.SeesawPart;
import com.marklynch.objects.inanimateobjects.SmallHidingPlace;
import com.marklynch.objects.inanimateobjects.Spikes;
import com.marklynch.objects.inanimateobjects.Storage;
import com.marklynch.objects.inanimateobjects.Stump;
import com.marklynch.objects.inanimateobjects.Switch;
import com.marklynch.objects.inanimateobjects.Tree;
import com.marklynch.objects.inanimateobjects.Vein;
import com.marklynch.objects.inanimateobjects.VoidHole;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.objects.inanimateobjects.WallSupport;
import com.marklynch.objects.inanimateobjects.WallWithCrack;
import com.marklynch.objects.inanimateobjects.WantedPoster;
import com.marklynch.objects.inanimateobjects.WaterBody;
import com.marklynch.objects.inanimateobjects.WaterSource;
import com.marklynch.objects.tools.Axe;
import com.marklynch.objects.tools.Bell;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.tools.FishingRod;
import com.marklynch.objects.tools.Knife;
import com.marklynch.objects.tools.Lantern;
import com.marklynch.objects.tools.Pickaxe;
import com.marklynch.objects.tools.Shovel;

public class Templates {

	// 2 todo out of an original 113

	public Templates() {

		// GOLD BITCH
		new TemplatesGold();

		// Small
		new TemplatesReadables();
		new TemplatesInspectables();
		new TemplatesNature();
		new TemplatesJunk();
		new TemplatesWeapons();
		new TemplatesArmor();
		new TemplatesLiquids();
		new TemplatesTools();
		new TemplatesProjectiles();

		// Big
		new TemplatesCorpses();
		new TemplatesEntrances();
		new TemplatesContainers();
		new TemplatesBigObjects();
		new TemplatesFurniture();
		new TemplatesBuilding();

		// Peeps
		new TemplatesHumans();
		new TemplatesAnimals();
		new TemplatesMonsters();

		// Markers
		new TemplatesMapMarkers();

	}

	// GOLD
	public static Gold GOLD;
	public static Orb SMALL_ORB;
	public static Orb MEDIUM_ORB;
	public static Orb LARGE_ORB;
	public static Orb BUBBLE;

	// Player
	public static Player PLAYER;

	// General People
	public static Human HUNTER;
	public static Guard GUARD;
	public static Human FISHERMAN;
	public static Human MINER;

	public static Thief THIEF;

	public static Human FARMER;

	public static Trader TRADER;
	public static Doctor DOCTOR;

	public static Human MINECART_RIDER;

	// Domestic animals
	public static Pig PIG;

	// Wild animals
	public static TinyNeutralWildAnimal RAT;

	public static Fish FISH;
	public static Fish TURTLE;

	public static HerbivoreWildAnimal RABBIT;

	public static TinyNeutralWildAnimal BABY_RABBIT;

	public static CarnivoreNeutralWildAnimal FOX;

	public static CarnivoreNeutralWildAnimal WOLF;

	// Monsters
	public static Blind BLIND;

	public static RockGolem ROCK_GOLEM;

	// Special People
	public static Mort MORT;
	public static Kidnapper KIDNAPPER;

	// Tools
	public static Weapon BROOM;

	public static Pickaxe PICKAXE;

	public static Shovel SHOVEL;

	public static FishingRod FISHING_ROD;

	public static Axe HATCHET;

	public static Knife HUNTING_KNIFE;

	public static Weapon SWORD;

	public static Weapon HOE;

	public static Weapon SICKLE;

	public static Weapon HAMMER;

	public static Weapon BASKET;

	public static Weapon WHIP;

	public static Weapon SERRATED_SPOON;

	public static Bell DINNER_BELL;

	public static Lantern LANTERN;
	public static Matches MATCHES;

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
	public static Helmet HUNTING_CAP;
	public static Helmet HELMET_ANTLERS;

	// Body Armor
	public static BodyArmor JUMPER;
	public static BodyArmor APRON;
	public static BodyArmor ROBE;
	public static BodyArmor LEATHERS;
	public static BodyArmor CHAINMAIL;

	// Leg Armor
	public static LegArmor PANTS;
	public static LegArmor DUNGAREES;
	public static LegArmor UNDIES;

	// Furniture;
	public static Bed BED;

	public static Mirror MIRROR;

	public static GameObject SHOP_COUNTER;

	public static GameObject TABLE;

	public static GameObject CHAIR;

	public static GameObject BENCH;

	public static GameObject CHAIR_FALLEN;

	public static GameObject BARRICADE;

	public static GameObject ROCK_WITH_ETCHING;

	public static GameObject SCROLL;

	public static GameObject SIGN;

	public static GameObject SIGNPOST;

	public static WantedPoster WANTED_POSTER;

	public static GameObject DOCUMENTS;

	public static WallSupport WOODEN_SUPPORT;

	// Openables
	public static Door WEAK_WOODEN_DOOR;

	public static Door DOOR;

	public static Door GATE;

	public static Storage CHEST;

	public static Storage CRATE;
	public static Storage CRATE_WITH_ETCHING;

	public static Storage LOST_AND_FOUND;

	public static RemoteDoor OPENABLE_WALL;
	public static RemoteDoor REMOTE_DOOR;

	public static GameObject KEY;

	public static Switch ANTLERS_SWITCH;
	public static Switch REMOTE_SWITCH;

	public static PressurePlate PRESSURE_PLATE;
	public static PressurePlateRequiringSpecificItem PRESSURE_PLATE_REQUIRING_SPECIFIC_ITEM;
	public static AttackableSwitch ATTACKABLE_SWITCH;

	public static Seesaw SEESAW;
	public static SeesawPart SEESAW_PART;

	// Kitchenware
	public static GameObject PLATE;

	public static GameObject BROKEN_PLATE;

	public static GameObject DINNER_KNIFE;

	public static GameObject DINNER_FORK;

	public static Wall WALL_CAVE;
	public static Wall WALL_WINDOW;
	public static Wall WALL_BUILDING;
	public static WallWithCrack WALL_WITH_CRACK;
	public static Wall FALSE_WALL;
	public static GameObject RUBBLE;
	public static Fireplace FIRE_PLACE;
	public static InputDrain INPUT_DRAIN;
	public static ElectricalWiring ELECTRICAL_WIRING;

	public static Wall FENCE;

	public static Rail RAIL;
	public static Rail RAIL_INVISIBLE;

	public static MineCart MINE_CART;

	public static Vein VEIN;

	public static WaterBody WATER_BODY;

	public static GameObject BOULDER;

	public static Arrow ARROW;

	public static Arrow FIRE_BALL;

	public static Arrow WATER_BALL;

	public static GameObject ROCK;

	// LARGE CONTAINER
	public static GameObject DUMPSTER;

	public static GameObject TROUGH;

	public static Furnace FURNACE;

	public static WaterSource WELL;

	public static GameObject SHELF;

	public static Inspectable PIG_SIGN;

	// Corpses
	public static Carcass CARCASS;

	public static Corpse CORPSE;

	public static GameObject ASH;
	public static GameObject WOOD_CHIPS;

	public static Inspectable BLOODY_PULP;
//	public static Inspectable BLOOD;

	// Food?
	public static MeatChunk MEAT_CHUNK;

	public static ContainerForLiquids JAR;
	public static ContainerForLiquids JAR_OF_WATER;
	public static ContainerForLiquids JAR_OF_POISON;
	public static ContainerForLiquids JAR_OF_OIL;
	public static ContainerForLiquids JAR_OF_LAVA;
	public static ContainerForLiquids JAR_OF_BLOOD;
	public static ContainerForLiquids JAR_OF_SOUP;

	public static Liquid WATER;
	public static Liquid POISON;
	public static Liquid OIL;
	public static Liquid LAVA;
	public static Liquid BLOOD;
	public static Liquid SOUP;

	// JUNK
	public static GameObject FUR;

	public static GameObject DIRTY_SHEET_3;

	public static GameObject ORE;

	public static GameObject WOOD;

	public static Inspectable DRIED_BLOOD;

	public static MapMarker MAP_MARKER_RED;

	public static MapMarker MAP_MARKER_GREEN;

	public static MapMarker MAP_MARKER_BLUE;

	public static MapMarker MAP_MARKER_SKULL;

	public static MapMarker MAP_MARKER_TREASURE;

	public static MapMarker MAP_MARKER_PORTAL;

	// TRAPS
	public static BrokenGlass BROKEN_LAMP;

	public static BrokenGlass BROKEN_GLASS;

	public static Searchable DROP_HOLE;

	// NATURE
	public static Tree TREE;
	public static GameObject TREE_READABLE;
	public static Storage TREE_CONTAINER;

	public static Tree BIG_TREE;

	public static Stump STUMP;

	public static HidingPlace BUSH;

	public static HidingPlace POISON_BUSH;

	public static HidingPlace LONG_GRASS;

	public static HidingPlace WHEAT;

	public static Food MUSHROOM;
	public static Food CANDY;

	public static Food APPLE;

	public static SmallHidingPlace BURROW;

	public static GameObject MOUND;
	public static GameObject INVISIBLE_DIGGABLE;

	public static VoidHole VOID_HOLE;

	public static Portal PORTAL;

	public static Landmine LANDMINE;
	public static Spikes SPIKE_FLOOR;
	public static Spikes SPIKE_WALL_DOWN;

	// Floor
	public static GameObject STONE_FLOOR;
	public static GameObject CIRCLE_FLOOR;
	public static GameObject DRAIN_FLOOR;
	public static GameObject LEAVES;
	public static GameObject GIANT_FOOTPRINT;
	public static GameObject GIANT_FOOTPRINT_LEFT;

}
