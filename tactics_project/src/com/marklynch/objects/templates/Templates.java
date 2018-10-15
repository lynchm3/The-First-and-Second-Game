package com.marklynch.objects.templates;

import com.marklynch.level.quest.caveoftheblind.Blind;
import com.marklynch.level.quest.caveoftheblind.Mort;
import com.marklynch.level.quest.thesecretroom.Kidnapper;
import com.marklynch.objects.Arrow;
import com.marklynch.objects.Bed;
import com.marklynch.objects.BigGameObject;
import com.marklynch.objects.BrokenGlass;
import com.marklynch.objects.Carcass;
import com.marklynch.objects.Corpse;
import com.marklynch.objects.Discoverable;
import com.marklynch.objects.Door;
import com.marklynch.objects.Floor;
import com.marklynch.objects.Food;
import com.marklynch.objects.Furnace;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Gate;
import com.marklynch.objects.Gold;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.Inspectable;
import com.marklynch.objects.Junk;
import com.marklynch.objects.Key;
import com.marklynch.objects.Landmine;
import com.marklynch.objects.Liquid;
import com.marklynch.objects.MapMarker;
import com.marklynch.objects.Matches;
import com.marklynch.objects.MeatChunk;
import com.marklynch.objects.MineCart;
import com.marklynch.objects.Mirror;
import com.marklynch.objects.Orb;
import com.marklynch.objects.Portal;
import com.marklynch.objects.PressurePlate;
import com.marklynch.objects.Rail;
import com.marklynch.objects.Readable;
import com.marklynch.objects.RemoteDoor;
import com.marklynch.objects.Searchable;
import com.marklynch.objects.Seesaw;
import com.marklynch.objects.Sign;
import com.marklynch.objects.SmallHidingPlace;
import com.marklynch.objects.Storage;
import com.marklynch.objects.Stump;
import com.marklynch.objects.Support;
import com.marklynch.objects.Switch;
import com.marklynch.objects.Tree;
import com.marklynch.objects.Vein;
import com.marklynch.objects.VoidHole;
import com.marklynch.objects.Wall;
import com.marklynch.objects.WantedPoster;
import com.marklynch.objects.WaterBody;
import com.marklynch.objects.WaterSource;
import com.marklynch.objects.tools.Axe;
import com.marklynch.objects.tools.Bell;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.tools.FishingRod;
import com.marklynch.objects.tools.Knife;
import com.marklynch.objects.tools.Lantern;
import com.marklynch.objects.tools.Pickaxe;
import com.marklynch.objects.tools.Shovel;
import com.marklynch.objects.units.CarnivoreNeutralWildAnimal;
import com.marklynch.objects.units.Doctor;
import com.marklynch.objects.units.Fish;
import com.marklynch.objects.units.Guard;
import com.marklynch.objects.units.HerbivoreWildAnimal;
import com.marklynch.objects.units.Human;
import com.marklynch.objects.units.Pig;
import com.marklynch.objects.units.Player;
import com.marklynch.objects.units.RockGolem;
import com.marklynch.objects.units.Thief;
import com.marklynch.objects.units.TinyNeutralWildAnimal;
import com.marklynch.objects.units.Trader;
import com.marklynch.objects.weapons.BodyArmor;
import com.marklynch.objects.weapons.Helmet;
import com.marklynch.objects.weapons.LegArmor;
import com.marklynch.objects.weapons.Weapon;

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

	public static WantedPoster WANTED_POSTER;

	public static Readable DOCUMENTS;

	public static Support WOODEN_SUPPORT;

	// Openables
	public static Door WEAK_WOODEN_DOOR;

	public static Door DOOR;

	public static Gate GATE;

	public static Storage CHEST;

	public static Storage CRATE;

	public static Storage LOST_AND_FOUND;

	public static RemoteDoor OPENABLE_WALL;;

	public static Key KEY;

	public static Switch ANTLERS_SWITCH;

	public static PressurePlate PRESSURE_PLATE;

	public static Seesaw SEESAW;
	public static Seesaw.SeesawPart SEESAW_PART;

	// Kitchenware
	public static GameObject PLATE;

	public static GameObject BROKEN_PLATE;

	public static GameObject DINNER_KNIFE;

	public static GameObject DINNER_FORK;

	public static Wall WALL;
	public static Wall FALSE_WALL;

	public static Wall FENCE;

	public static Rail RAIL;
	public static Rail RAIL_INVISIBLE;

	public static MineCart MINE_CART;

	public static Vein VEIN;

	public static WaterBody WATER_BODY;

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
	public static GameObject WOOD_CHIPS;

	public static Inspectable BLOODY_PULP;
	public static Inspectable BLOOD;

	// Food?
	public static MeatChunk MEAT_CHUNK;

	public static ContainerForLiquids JAR;
	public static ContainerForLiquids JAR_OF_WATER;

	public static Liquid WATER;

	public static Liquid POISON;

	// JUNK
	public static Junk FUR;

	public static Junk DIRTY_SHEET_3;

	public static Junk ORE;

	public static Junk WOOD;

	public static Inspectable DRIED_BLOOD;

	public static MapMarker MAP_MARKER_RED;

	public static MapMarker MAP_MARKER_GREEN;

	public static MapMarker MAP_MARKER_BLUE;

	public static MapMarker MAP_MARKER_SKULL;

	public static MapMarker MAP_MARKER_TREASURE;

	public static MapMarker MAP_MARKER_PORTAL;

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

	public static HidingPlace BUSH;

	public static HidingPlace POISON_BUSH;

	public static HidingPlace LONG_GRASS;

	public static HidingPlace WHEAT;

	public static Food MUSHROOM;
	public static Food CANDY;

	public static Food APPLE;

	public static SmallHidingPlace BURROW;

	public static Discoverable MOUND;

	public static VoidHole VOID_HOLE;

	public static Portal PORTAL;

	public static Landmine LANDMINE;

	public static Floor STONE_FLOOR;

	public static Floor CIRCLE_FLOOR;

}
