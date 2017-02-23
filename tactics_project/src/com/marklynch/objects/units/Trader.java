package com.marklynch.objects.units;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.marklynch.Game;
import com.marklynch.ai.routines.AIRoutineForTrader;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Building;
import com.marklynch.level.constructs.Faction;
import com.marklynch.objects.Bed;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.Sign;

public class Trader extends Actor implements Comparator<GameObject> {

	public Building shop;
	public Sign shopSign;

	public Trader(String name, String title, int actorLevel, int health, int strength, int dexterity, int intelligence,
			int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance, Bed bed,
			Inventory inventory, boolean showInventory, boolean fitsInInventory, boolean canContainOtherObjects,
			float widthRatio, float heightRatio, Faction faction, float anchorX, float anchorY, Building shop,
			Sign shopSign) {
		super(name, title, actorLevel, health, strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, travelDistance, bed, inventory, showInventory, fitsInInventory,
				canContainOtherObjects, widthRatio, heightRatio, faction, anchorX, anchorY);
		this.shop = shop;
		this.shopSign = shopSign;

		aiRoutine = new AIRoutineForTrader(this);
	}

	public boolean isPlayerInTheShop() {
		return Game.level.player.squareGameObjectIsOn.building == shop;
	}

	public Object[] getTextForSign() {

		ArrayList<GameObject> temp = (ArrayList<GameObject>) this.inventory.getGameObjects().clone();
		Collections.sort(temp, this);
		if (temp.size() == 0) {
			if (shopSign.getText().length != 1) {
				return new Object[] { this.shop };
			} else {
				return null;
			}

		} else if (temp.size() == 1) {
			if (shopSign.getText().length != 3) {
				return new Object[] { this.shop, " - FEATURED INVENTORY - ", temp.get(0) };
			} else if (shopSign.getText()[2] != temp.get(0)) {
				return new Object[] { this.shop, " - FEATURED INVENTORY - ", temp.get(0) };
			} else {
				return null;
			}

		} else {
			if (shopSign.getText().length != 5) {
				return new Object[] { this.shop, " - FEATURED INVENTORY - ", temp.get(0), " - ", temp.get(1) };
			} else if (shopSign.getText()[2] != temp.get(0) || shopSign.getText()[4] != temp.get(1)) {
				return new Object[] { this.shop, " - FEATURED INVENTORY - ", temp.get(0), " - ", temp.get(1) };
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
	public Trader makeCopy(Square square, Faction factione) {

		Trader actor = new Trader(name, title, actorLevel, (int) totalHealth, strength, dexterity, intelligence,
				endurance, imageTexturePath, square, travelDistance, null, inventory.makeCopy(), showInventory,
				fitsInInventory, canContainOtherObjects, widthRatio, heightRatio, faction, anchorX, anchorY, shop,
				shopSign);
		return actor;
	}

	@Override
	public int compare(GameObject gameObject1, GameObject gameObject2) {
		return (int) (gameObject2.value - gameObject1.value);
	}

}
