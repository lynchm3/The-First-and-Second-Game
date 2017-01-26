package com.marklynch.tactics.objects;

import com.marklynch.tactics.objects.level.Square;

public class GameObjectTemplate extends GameObject {

	public GameObjectTemplate(String name, int health, String imagePath, Square squareGameObjectIsOn,
			Inventory inventory, boolean showInventory, boolean canShareSquare) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare);
		// TODO Auto-generated constructor stub
	}

	public GameObject makeObject() {
		return null;
	}

}
