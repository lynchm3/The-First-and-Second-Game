package com.marklynch.objects;

import com.marklynch.level.Square;

public class Corpse extends GameObject {

	public Corpse(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			float widthRatio, float heightRatio) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, widthRatio, heightRatio);
	}

	@Override
	public GameObject makeCopy(Square square) {
		return new Corpse(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, widthRatio, heightRatio);
	}

}
