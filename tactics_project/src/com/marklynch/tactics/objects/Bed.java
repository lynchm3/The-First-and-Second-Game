package com.marklynch.tactics.objects;

import com.marklynch.tactics.objects.level.Square;

public class Bed extends GameObject {

	public Bed(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects);
	}

	@Override
	public GameObject makeCopy(Square square) {
		return new Bed(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects);
	}

}
