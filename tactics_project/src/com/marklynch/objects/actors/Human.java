package com.marklynch.objects.actors;

import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;

public class Human extends Actor {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public static Texture hair1 = ResourceUtils.getGlobalImage("hair_1.png", true);
	public static Texture hair2 = ResourceUtils.getGlobalImage("hair_2.png", true);
	public static ArrayList<Texture> hairTextures;

	public static Color hairColor1 = Color.BLACK;
	public static Color hairColor2 = Color.WHITE;
	public static ArrayList<Color> hairColors;

	public static ArrayList<Texture> helmetTextures;
	public static Texture helmet1 = ResourceUtils.getGlobalImage("helmet_1.png", true);
	public static Texture helmet2 = ResourceUtils.getGlobalImage("helmet_2.png", true);
	public static Color helmetColor1 = Color.BLACK;
	public static Color helmetColor2 = Color.WHITE;
	public static ArrayList<Color> helmetColors;

	public static ArrayList<Texture> bodyArmorTextures;
	public static Texture bodyArmor1 = ResourceUtils.getGlobalImage("jumper_1.png", true);
	public static Texture bodyArmor2 = ResourceUtils.getGlobalImage("jumper_2.png", true);
	public static Color bodyArmorColor1 = Color.BLACK;
	public static Color bodyArmorColor2 = Color.WHITE;
	public static ArrayList<Color> bodyArmorColors;

	public static ArrayList<Texture> legArmorTextures;
	public static Texture legArmor1 = ResourceUtils.getGlobalImage("leg_armor_1.png", true);
	public static Texture legArmor2 = ResourceUtils.getGlobalImage("leg_armor_2.png", true);
	public static Color legArmorColor1 = Color.BLACK;
	public static Color legArmorColor2 = Color.WHITE;
	public static ArrayList<Color> legArmorColors;

	public Human() {
		torsoImageTexture = ResourceUtils.getGlobalImage("hero_upper.png", true);
		pelvisImageTexture = ResourceUtils.getGlobalImage("hero_lower.png", true);
		armImageTexture = ResourceUtils.getGlobalImage("arm.png", true);
		legImageTexture = ResourceUtils.getGlobalImage("leg.png", true);
		hairImageTexture = hairTextures.get(0);
		hairColor = hairColors.get(0);
		canOpenDoors = true;
		canEquipWeapons = true;
		type = "Human";
	}

	public static void loadStaticImages() {

		// Hair
		hairTextures = new ArrayList<Texture>(Texture.class);
		hairTextures.add(hair1);
		hairTextures.add(hair2);

		hairColors = new ArrayList<Color>(Color.class);
		hairColors.add(hairColor1);
		hairColors.add(hairColor2);

		// Helmet

		helmetTextures = new ArrayList<Texture>(Texture.class);
		helmetTextures.add(helmet1);
		helmetTextures.add(helmet2);

		helmetColors = new ArrayList<Color>(Color.class);
		helmetColors.add(helmetColor1);
		helmetColors.add(helmetColor2);

		// Body Armor

		bodyArmorTextures = new ArrayList<Texture>(Texture.class);
		bodyArmorTextures.add(bodyArmor1);
		bodyArmorTextures.add(bodyArmor2);

		bodyArmorColors = new ArrayList<Color>(Color.class);
		bodyArmorColors.add(bodyArmorColor1);
		bodyArmorColors.add(bodyArmorColor2);

		// Leg Armor

		legArmorTextures = new ArrayList<Texture>(Texture.class);
		legArmorTextures.add(legArmor1);
		legArmorTextures.add(legArmor2);

		legArmorColors = new ArrayList<Color>(Color.class);
		legArmorColors.add(legArmorColor1);
		legArmorColors.add(legArmorColor2);

	}

	@Override
	public void postLoad1() {
		super.postLoad1();
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	public Human makeCopy(String name, Square square, Faction faction, GameObject bed, int gold, GameObject[] mustHaves,
			GameObject[] mightHaves, Area area, int[] requiredEquipmentTemplateIds, HOBBY[] hobbies) {
		Human actor = new Human();
		setInstances(actor);
		super.setAttributesForCopy(name, actor, square, faction, bed, gold, mustHaves, mightHaves, area);
		actor.requiredEquipmentTemplateIds = requiredEquipmentTemplateIds;
		actor.hobbies = hobbies;

		if (bodyArmor != null) {
			actor.bodyArmor = bodyArmor.makeCopy(null, actor);
			actor.inventory.add(actor.bodyArmor);
		}

		if (legArmor != null) {
			actor.legArmor = legArmor.makeCopy(null, actor);
			actor.inventory.add(actor.legArmor);
		}

		if (helmet != null) {
			actor.helmet = helmet.makeCopy(null, actor);
			actor.inventory.add(actor.helmet);
		}

		if (hairImageTexture != null) {
			actor.hairImageTexture = hairImageTexture;
		}

		return actor;
	}

}
