package com.marklynch.objects;

import com.marklynch.Game;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Hunter;
import com.marklynch.objects.weapons.Weapon;

public class Templates {

	// People
	public static final Actor OLD_LADY = new Actor("You", "Fighter", 10, 100, 0, 0, 0, 0, "red1.png", null, 1, 10, null,
			new Inventory(), true, false, true, false, false, 1, 1, null, 80f, 80f);
	public static final Actor HUNTER = new Hunter("Hunter", "Hunter", 1, 10, 0, 0, 0, 0, "hunter.png", null, 1, 10,
			null, new Inventory(), true, false, true, false, false, 1, 1, null, 88, 54);

	// Tools
	public static final Weapon BROOM = new Weapon("Broom", 1, 1, 1, "broom.png", 100, null, true, false, false, false,
			1f, 1f, 59, 63);
	public static final Weapon PICKAXE = new Weapon("Pickaxe", 3, 1, 1, "pickaxe.png", 100, null, true, false, false,
			false, 1f, 1f, 22, 15);
	public static final Weapon HOE = new Weapon("Hoe", 2, 1, 1, "hoe.png", 100, null, true, false, false, false, 1f, 1f,
			22, 15);
	public static final Weapon SICKLE = new Weapon("Sickle", 3, 1, 1, "sickle.png", 100, null, true, false, false,
			false, 1f, 1f, 22, 15);
	public static final Weapon HAMMER = new Weapon("Hammer", 4, 1, 1, "hammer.png", 100, null, true, false, false,
			false, 1f, 1f, 22, 15);
	public static final Weapon BASKET = new Weapon("Basket", 1, 1, 1, "basket.png", 100, null, true, false, false,
			false, 1f, 1f, 22, 15);
	public static final Weapon BUCKET = new Weapon("Bucket", 1, 1, 1, "bucket.png", 100, null, true, false, false,
			false, 1f, 1f, 22, 15);
	public static final Weapon WHIP = new Weapon("Whip", 3, 1, 1, "whip.png", 100, null, true, false, false, false, 1f,
			1f, 22, 15);

	// Blades
	public static final Weapon KATANA = new Weapon("Katana", 10, 1, 1, "katana.png", 100, null, true, false, false,
			false, 1f, 0.5f, 22, 15);

	// Axes
	// https://en.wikipedia.org/wiki/Axe#Types_of_axes
	public static final Weapon HATCHET = new Weapon("Hatchet", 3, 1, 1, "a3r1.png", 100, null, true, false, false,
			false, 0.5f, 0.5f, 24, 40);

	// Bows
	// https://en.wikipedia.org/wiki/Bow_and_arrow#Types_of_bow
	public static final Weapon HUNTING_BOW = new Weapon("Hunting Bow", 3, 1, 3, "a2r2.png", 100, null, true, false,
			false, false, 0.5f, 0.5f, 40, 42);

	// Furniture
	public static final GameObjectTemplate BED = new Bed("Bed", 5, "bed.png", "bed_Covers.png", null, new Inventory(),
			false, true, false, false, false, false, 1, 1);
	public static final GameObjectTemplate SIGN = new Sign("Sign", 5, "sign.png", Game.level.squares[6][8],
			new Inventory(), true, false, false, true, false, true, new Object[] { "" }, 1, 1);
	public static final GameObjectTemplate SHOP_COUNTER = new GameObjectTemplate("Shop Counter", 5, "shop_counter.png",
			null, new Inventory(), false, true, false, true, false, false, 1f, 1f);

	// LARGE CONTAINER
	public static final GameObjectTemplate DUMPSTER = new GameObjectTemplate("dumpster", 5, "skip_with_shadow.png",
			null, new Inventory(), true, false, false, true, false, false, 1, 1);

	// JUNK
	public static final Junk furTemplate = new Junk("Fur", 5, "fur.png", null, new Inventory(), false, true, true,
			false, false, false, 1, 1);

	// NATURE
	// BURROW
	// TREE
	//

}
