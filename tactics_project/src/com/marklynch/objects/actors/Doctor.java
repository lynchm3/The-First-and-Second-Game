package com.marklynch.objects.actors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutineForTrader;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.armor.BodyArmor;
import com.marklynch.objects.armor.LegArmor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Gold;
import com.marklynch.objects.templates.Templates;

public class Doctor extends Human implements Comparator<GameObject> {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public StructureRoom shopRoom;
	public GameObject shopSign;
	// public Weapon broom;
	public BodyArmor apron;
	public LegArmor pants;

	public Doctor() {
		super();
		type = "Doctor";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public boolean isPlayerInTheShop() {
		return Game.level.player.squareGameObjectIsOn.structureRoomSquareIsIn == shopRoom;
	}

	public Object[] getTextForSign() {

		if (shopSign == null)
			return null;

		ArrayList<GameObject> temp = (ArrayList<GameObject>) this.inventory.getGameObjects().clone();
		temp.remove(equipped);
		for (GameObject gameObject : (ArrayList<GameObject>) temp.clone()) {
			if (gameObject instanceof Gold)
				temp.remove(gameObject);
		}
		Collections.sort(temp, this);

		if (temp.size() == 0) {
			if (shopSign.getConversation().openingConversationPart.text.length != 1) {
				return new Object[] { this.shopRoom };
			} else {
				return null;
			}

		} else if (temp.size() == 1) {
			if (shopSign.getConversation().openingConversationPart.text.length != 3) {
				return new Object[] { this.shopRoom, " - FEATURED INVENTORY - ", temp.get(0) };
			} else if (shopSign.getConversation().openingConversationPart.text[2] != temp.get(0)) {
				return new Object[] { this.shopRoom, " - FEATURED INVENTORY - ", temp.get(0) };
			} else {
				return null;
			}

		} else {
			// shopSign.getConversation() = null
			if (shopSign.getConversation().openingConversationPart.text.length != 5) {
				return new Object[] { this.shopRoom, " - FEATURED INVENTORY - ", temp.get(0), " - ", temp.get(1) };
			} else if (shopSign.getConversation().openingConversationPart.text[2] != temp.get(0)
					|| shopSign.getConversation().openingConversationPart.text[4] != temp.get(1)) {
				return new Object[] { this.shopRoom, " - FEATURED INVENTORY - ", temp.get(0), " - ", temp.get(1) };
			} else {
				return null;
			}

		}
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		aiRoutine = new AIRoutineForTrader(this);
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	@Override
	public Doctor makeCopy(String name, Square square, Faction faction, GameObject bed, int gold,
			GameObject[] mustHaves, GameObject[] mightHaves, Area area, int[] requiredEquipmentTemplateIds,
			HOBBY[] hobbies) {

		Doctor actor = new Doctor();
		setInstances(actor);

		super.setAttributesForCopy(name, actor, square, faction, bed, gold, mustHaves, mightHaves, area);
		actor.requiredEquipmentTemplateIds = requiredEquipmentTemplateIds;
		actor.hobbies = hobbies;

		// actor.broom = Templates.BROOM.makeCopy(null, null);
		// actor.inventory.add(actor.broom);
		// actor.equip(actor.broom);

		actor.apron = Templates.APRON.makeCopy(null, null);
		actor.inventory.add(actor.apron);
		actor.bodyArmor = actor.apron;

		actor.pants = Templates.PANTS.makeCopy(null, null);
		actor.inventory.add(actor.pants);
		actor.legArmor = actor.pants;

		return actor;
	}

	@Override
	public int compare(GameObject gameObject1, GameObject gameObject2) {
		return gameObject2.value - gameObject1.value;
	}

}
