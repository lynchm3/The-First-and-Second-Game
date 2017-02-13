package com.marklynch.objects;

import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;

public class Door extends GameObject {

	public Door(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects);
	}

	@Override
	public void draw1() {

		if (!this.squareGameObjectIsOn.inventory.contains(Actor.class)) {
			super.draw1();
		} else {

		}

	}

	@Override
	public GameObject makeCopy(Square square) {
		return new Door(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects);
	}

}
