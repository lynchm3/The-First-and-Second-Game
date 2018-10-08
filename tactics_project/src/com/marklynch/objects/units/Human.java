package com.marklynch.objects.units;

import java.util.ArrayList;

import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.ResourceUtils;

public class Human extends Actor {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Human() {
		torsoImageTexture = ResourceUtils.getGlobalImage("hero_upper.png", true);
		pelvisImageTexture = ResourceUtils.getGlobalImage("hero_lower.png", true);
		armImageTexture = ResourceUtils.getGlobalImage("arm.png", true);
		legImageTexture = ResourceUtils.getGlobalImage("leg.png", true);
		hairImageTexture = ResourceUtils.getGlobalImage("hair.png", true);
		canOpenDoors = true;
		canEquipWeapons = true;
		type = "Human";
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
			actor.bodyArmor = bodyArmor.makeCopy(null, null);
			actor.inventory.add(actor.bodyArmor);
		}

		if (legArmor != null) {
			actor.legArmor = legArmor.makeCopy(null, null);
			actor.inventory.add(actor.legArmor);
		}

		if (helmet != null) {
			actor.helmet = helmet.makeCopy(null, null);
			actor.inventory.add(actor.helmet);
		}

		if (hairImageTexture != null) {
			actor.hairImageTexture = hairImageTexture;
		}

		return actor;
	}

}
