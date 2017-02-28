package com.marklynch.objects;

import com.marklynch.level.Square;

public class Junk extends GameObject {

	public Junk(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, float widthRatio, float heightRatio) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, widthRatio, heightRatio);
	}

	@Override
	public GameObject makeCopy(Square square) {
		return new Junk(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight, widthRatio,
				heightRatio);
	}

}
