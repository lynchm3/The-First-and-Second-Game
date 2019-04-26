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

	public static Texture jumper1 = ResourceUtils.getGlobalImage("jumper_1.png", true);
	public static Texture jumper2 = ResourceUtils.getGlobalImage("jumper_2.png", true);
	public static ArrayList<Texture> jumperTextures;

	public static Color jumperColor1 = Color.BLACK;
	public static Color jumperColor2 = Color.WHITE;
	public static ArrayList<Color> jumperColors;

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
		hair1 = ResourceUtils.getGlobalImage("hair_1.png", true);
		hair2 = ResourceUtils.getGlobalImage("hair_2.png", true);

		hairTextures = new ArrayList<Texture>(Texture.class);
		hairTextures.add(hair1);
		hairTextures.add(hair2);

		hairColors = new ArrayList<Color>(Color.class);
		hairColors.add(hairColor1);
		hairColors.add(hairColor2);

		// Jumper
		jumper1 = ResourceUtils.getGlobalImage("jumper_1.png", true);
		jumper2 = ResourceUtils.getGlobalImage("jumper_2.png", true);

		jumperTextures = new ArrayList<Texture>(Texture.class);
		jumperTextures.add(jumper1);
		jumperTextures.add(jumper2);

		jumperColors = new ArrayList<Color>(Color.class);
		jumperColors.add(jumperColor1);
		jumperColors.add(jumperColor2);

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
