package com.marklynch.objects.units;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutineForTrader;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Gold;
import com.marklynch.objects.Sign;
import com.marklynch.objects.WantedPoster;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.weapons.Weapon;

public class Trader extends Human implements Comparator<GameObject> {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public StructureRoom shopRoom;
	// public StructureRoom shop;
	public Sign shopSign;
	public WantedPoster wantedPoster;
	public Weapon broom;

	public Trader() {
		super();
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
		temp.remove(broom);
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
	public Trader makeCopy(String name, Square square, Faction faction, GameObject bed, int gold,
			GameObject[] mustHaves, GameObject[] mightHaves, Area area) {

		Trader actor = new Trader();
		setInstances(actor);

		super.setAttributesForCopy(actor, square, faction, bed, gold, mustHaves, mightHaves, area);

		actor.broom = Templates.BROOM.makeCopy(null, null);
		actor.inventory.add(actor.broom);
		actor.equip(actor.broom);
		return actor;
	}

	@Override
	public int compare(GameObject gameObject1, GameObject gameObject2) {
		return gameObject2.value - gameObject1.value;
	}

}
