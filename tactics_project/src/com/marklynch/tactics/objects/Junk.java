package com.marklynch.tactics.objects;

import com.marklynch.tactics.objects.level.Square;

public class Junk extends GameObject {

	public Junk(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects);
	}

}
