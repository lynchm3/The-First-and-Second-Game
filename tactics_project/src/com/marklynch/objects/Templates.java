package com.marklynch.objects;

import com.marklynch.level.Square;
import com.marklynch.level.constructs.Faction;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Hunter;
import com.marklynch.objects.weapons.WeaponTemplate;

public class Templates {

	// People
	private static final Actor OLD_LADY = new Actor("You", "Fighter", 10, 100, 0, 0, 0, 0, "red1.png", null, 1, null,
			new Inventory(), true, false, true, 1, 1, null);
	private static final Actor HUNTER = new Hunter("Hunter", "Hunter", 1, 10, 0, 0, 0, 0, "hunter.png", null, 1, null,
			new Inventory(), true, false, true, 1, 1, null);
	// Axes
	// https://en.wikipedia.org/wiki/Axe#Types_of_axes
	private static final WeaponTemplate HATCHET = new WeaponTemplate("Hatchet", 3, 1, 1, "a3r1.png", 100, null, true,
			false, 0.5f, 0.5f);

	// Bows
	// https://en.wikipedia.org/wiki/Bow_and_arrow#Types_of_bow
	private static final WeaponTemplate HUNTING_BOW = new WeaponTemplate("Hunting Bow", 3, 1, 1, "a3r1.png", 100, null,
			true, false, 0.5f, 0.5f);

	// Furniture
	private static final GameObjectTemplate BED = new Bed("Bed", 5, "bed.png", "bed_Covers.png", null, new Inventory(),
			false, true, false, false, 1, 1);

	// LARGE CONTAINER
	private static final GameObjectTemplate DUMPSTER = new GameObjectTemplate("dumpster", 5, "skip_with_shadow.png",
			null, new Inventory(), true, false, false, true, 1, 1);

	// JUNK
	private static final Junk furTemplate = new Junk("Fur", 5, "fur.png", null, new Inventory(), false, true, true,
			false, 1, 1);

	public static Actor getOldLady(Square square, Faction faction) {
		return OLD_LADY.makeCopy(square, faction);
	}

	public static Actor getHunter(Square square, Faction faction) {
		return HUNTER.makeCopy(square, faction);
	}

	public static GameObject getHatchet(Square square) {
		return HATCHET.makeCopy(square);
	}

	public static GameObject getHuntingBow(Square square) {
		return HUNTING_BOW.makeCopy(square);
	}

	public static GameObject getBed(Square square) {
		return BED.makeCopy(square);
	}

	public static GameObject getDumpster(Square square) {
		return DUMPSTER.makeCopy(square);
	}

	public static GameObject getFurtemplate(Square square) {
		return furTemplate.makeCopy(square);
	}

}
