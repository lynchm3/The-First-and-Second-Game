package com.marklynch.objects;

import com.marklynch.level.quest.caveoftheblind.Blind;
import com.marklynch.level.quest.caveoftheblind.Mort;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Hunter;
import com.marklynch.objects.weapons.Bell;
import com.marklynch.objects.weapons.Lantern;
import com.marklynch.objects.weapons.Pickaxe;
import com.marklynch.objects.weapons.Weapon;

public class Templates {

	// Player
	public static final Actor Player = new Actor("You", "Fighter", 10, 100, 0, 0, 0, 0, "red1.png", null, 1, 10, null,
			new Inventory(), true, false, true, false, false, 2, 2, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f,
			0f, 0f, null, null, 80f, 80f, 10f);

	// General People
	public static final Hunter HUNTER = new Hunter("Hunter", "Hunter", 1, 10, 0, 0, 0, 0, "hunter.png", null, 1, 10,
			null, new Inventory(), true, false, true, false, false, 2, 2, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false,
			0f, 0f, 0f, 0f, null, null, 88, 54, 10f);

	// Monsters
	public static final Blind BLIND = new Blind("Blind", "Blind", 1, 10, 0, 0, 0, 0, "blind.png", null, 1, 1, null,
			new Inventory(), true, false, true, false, false, 2, 2, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f,
			0f, 0f, null, null, 88, 54, 20f, null);

	// Special People
	public static final Mort MORT = new Mort("Mort", "Miner", 1, 10, 0, 0, 0, 0, "old_man.png", null, 1, 10, null,
			new Inventory(), true, false, true, false, false, 2f, 2f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f,
			0f, 0f, 0f, null, null, 88, 54, 20f);

	// Tools
	public static final Weapon BROOM = new Weapon("Broom", 1, 1, 1, "broom.png", 100, null, true, false, false, false,
			1f, 1f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null, 59, 63);
	public static final Pickaxe PICKAXE = new Pickaxe("Pickaxe", 3, 1, 1, "pickaxe.png", 100, null, true, false, false,
			false, 0.34f, 0.34f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null, 24, 32);
	public static final Weapon HOE = new Weapon("Hoe", 2, 1, 1, "hoe.png", 100, null, true, false, false, false, 1f, 1f,
			0.5f, 0.5f, 15f, 15f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null, 22, 15);
	public static final Weapon SICKLE = new Weapon("Sickle", 3, 1, 1, "sickle.png", 100, null, true, false, false,
			false, 1f, 1f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null, 22, 15);
	public static final Weapon HAMMER = new Weapon("Hammer", 4, 1, 1, "hammer.png", 100, null, true, false, false,
			false, 1f, 1f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null, 22, 15);
	public static final Weapon BASKET = new Weapon("Basket", 1, 1, 1, "basket.png", 100, null, true, false, false,
			false, 1f, 1f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null, 22, 15);
	public static final Weapon BUCKET = new Weapon("Bucket", 1, 1, 1, "bucket.png", 100, null, true, false, false,
			false, 1f, 1f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null, 22, 15);
	public static final Weapon WHIP = new Weapon("Whip", 3, 1, 1, "whip.png", 100, null, true, false, false, false, 1f,
			1f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null, 22, 15);
	public static final Weapon SERRATED_SPOON = new Weapon("Serrated Spoon", 1, 1, 1, "serrated_spoon.png", 100, null,
			true, false, false, false, 0.5f, 0.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null,
			16, 24);
	public static final Bell DINNER_BELL = new Bell("Dinner Bell", 1, 1, 1, "bell.png", 100, null, true, false, false,
			false, 0.5f, 0.5f, 0.5f, 0.5f, 25f, 25f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null, 21, 32);
	public static final Lantern LANTERN = new Lantern("Lantern", 1, 1, 1, "lantern.png", 100, null, true, false, false,
			false, 0.25f, 0.25f, 0.5f, 0.5f, 25f, 25f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null, 16, 4);

	// Blades
	public static final Weapon KATANA = new Weapon("Katana", 10, 1, 1, "katana.png", 100, null, true, false, false,
			false, 1f, 0.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null, 22, 15);

	// Axes
	// https://en.wikipedia.org/wiki/Axe#Types_of_axes
	public static final Weapon HATCHET = new Weapon("Hatchet", 3, 1, 1, "a3r1.png", 100, null, true, false, false,
			false, 0.5f, 0.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null, 24, 40);
	public static final Weapon CLEAVER = new Weapon("Cleaver", 5, 1, 1, "cleaver.png", 100, null, true, false, false,
			false, 0.5f, 0.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null, 24, 40);

	// Bows
	// https://en.wikipedia.org/wiki/Bow_and_arrow#Types_of_bow
	public static final Weapon HUNTING_BOW = new Weapon("Hunting Bow", 1, 1, 4, "a2r2.png", 100, null, true, false,
			false, false, 0.5f, 0.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null, 40, 42);

	// Furniture
	public static final Bed BED = new Bed("Bed", 5, "bed.png", "bed_Covers.png", null, new Inventory(), false, true,
			false, false, false, false, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null);
	public static final Sign SIGN = new Sign("Sign", 5, "sign.png", null, new Inventory(), true, false, false, true,
			false, true, new Object[] { "" }, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null);
	public static final GameObjectTemplate SHOP_COUNTER = new GameObjectTemplate("Shop Counter", 5, "shop_counter.png",
			null, new Inventory(), false, true, false, true, false, false, 1f, 1f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f,
			false, 0f, 0f, 0f, 0f, null);
	public static final GameObjectTemplate TABLE = new GameObjectTemplate("Table", 5, "table.png", null,
			new Inventory(), false, true, false, true, false, false, 1f, 1f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f,
			false, 0f, 0f, 0f, 0f, null);
	public static final Sign ROCK_WITH_ETCHING = new Sign("Rock with Etching", 1000, "rock_with_etching.png", null,
			new Inventory(), true, false, false, true, false, true, new Object[] { "" }, 1, 1, 0.5f, 0.5f, 20f, 1f,
			null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null);
	public static final Door DOOR = new Door("Door", 100, "door.png", null, new Inventory(), false, true, false, false,
			true, true, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null, null, true);
	public static final Key KEY = new Key("Key", 100, "key.png", null, new Inventory(), false, true, false, false, true,
			true, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null);

	public static final Wall WALL = new Wall("Wall", 1000, "wall.png", null, new Inventory(), false, false, false,
			false, true, true, 1, 1, 0.5f, 0.5f, 10f, 0f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null);
	public static final Vein VEIN = new Vein("Vein", 1000, "wall.png", null, new Inventory(), false, false, false,
			false, true, true, 1, 1, 0.5f, 0.5f, 15f, 0f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null);

	// LARGE CONTAINER
	public static final GameObjectTemplate DUMPSTER = new GameObjectTemplate("Dumpster", 5, "skip_with_shadow.png",
			null, new Inventory(), true, false, false, true, false, false, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f,
			false, 0f, 0f, 0f, 0f, null);
	public static final GameObjectTemplate TROUGH = new GameObjectTemplate("Trough", 5, "trough.png", null,
			new Inventory(), true, false, false, true, false, false, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false,
			0f, 0f, 0f, 0f, null);

	// Food?
	public static final Carcass CARCASS = new Carcass("Carcass", 5, "carcass.png", null, new Inventory(), false, true,
			false, true, false, false, 0.5f, 0.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null);
	public static final Corpse CORPSE = new Corpse("Carcass", 5, "carcass.png", null, new Inventory(), false, true,
			false, true, false, false, 0.5f, 0.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null);
	public static final MeatChunk MEAT_CHUNK = new MeatChunk("Meat Chunk", 5, "meat_chunk.png", null, new Inventory(),
			false, true, true, true, false, false, 0.5f, 0.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f,
			0f, null);

	// JUNK
	public static final Junk FUR = new Junk("Fur", 5, "fur.png", null, new Inventory(), false, true, true, false, false,
			false, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null);
	public static final Junk DIRTY_SHEET = new Junk("Dirty Sheet", 5, "dirty_sheet.png", null, new Inventory(), false,
			true, true, false, false, false, 0.75f, 0.56f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f,
			null);
	public static final Junk DIRTY_SHEET_2 = new Junk("Dirty Sheet", 5, "dirty_sheet_2.png", null, new Inventory(),
			false, true, true, false, false, false, 0.88f, 1f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f,
			0f, null);
	public static final Junk DIRTY_SHEET_3 = new Junk("Dirty Sheet", 5, "dirty_sheet_3.png", null, new Inventory(),
			false, true, true, false, false, false, 0.88f, 1f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f,
			0f, null);
	public static final Junk ORE = new Junk("Ore", 5, "ore.png", null, new Inventory(), false, true, true, false, false,
			false, 0.25f, 0.25f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null);
	public static final Junk BLOOD = new Junk("Blood", 5, "blood.png", null, new Inventory(), false, true, false, false,
			false, false, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, null);

	// TRAPS
	public static final BrokenGlass BROKEN_GLASS = new BrokenGlass("Broken Glass", 5, "smashed_glass.png", null,
			new Inventory(), false, true, true, false, false, false, 0.5f, 0.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f,
			false, 0f, 0f, 0f, 0f, null);

	// NATURE
	// BURROW
	// TREE
	//

}
