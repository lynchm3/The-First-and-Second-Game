package com.marklynch.tactics.objects.unit;

import com.marklynch.tactics.objects.Inventory;
import com.marklynch.tactics.objects.level.Square;

public class ShopKeeper extends Actor {

	public ShopKeeper(String name, String title, int actorLevel, int health, int strength, int dexterity,
			int intelligence, int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance,
			Inventory inventory, boolean showInventory, boolean fitsInInventory, boolean canContainOtherObjects) {
		super(name, title, actorLevel, health, strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, travelDistance, inventory, showInventory, fitsInInventory,
				canContainOtherObjects);
	}

}
