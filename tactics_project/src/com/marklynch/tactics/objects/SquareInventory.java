package com.marklynch.tactics.objects;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.Actor;

public class SquareInventory extends Inventory {

	public Square square;

	@Override
	public void add(GameObject gameObject) {
		if (!gameObjects.contains(gameObject)) {

			// Remove references with square
			if (gameObject.squareGameObjectIsOn != null)
				gameObject.squareGameObjectIsOn.inventory.remove(gameObject);
			gameObject.squareGameObjectIsOn = null;

			// Remove from ground squares index
			Game.level.inanimateObjectsOnGround.remove(gameObject);

			// Remove from another gameObjects inventory
			if (gameObject.inventoryThatHoldsThisObject != null)
				gameObject.inventoryThatHoldsThisObject.remove(gameObject);

			// add to this inventory
			gameObjects.add(gameObject);
			gameObject.inventoryThatHoldsThisObject = this;
			gameObject.squareGameObjectIsOn = square;

			// Add the general level tracking of inanimate objects
			if (!Game.level.inanimateObjectsOnGround.contains(gameObject) && !(gameObject instanceof Actor))
				Game.level.inanimateObjectsOnGround.add(gameObject);

			for (int i = 0; i < inventorySquares.length; i++) {
				for (int j = 0; j < inventorySquares[i].length; j++) {
					if (inventorySquares[i][j].gameObject == null) {

						// Inventory Square
						inventorySquares[i][j].gameObject = gameObject;
						gameObject.inventorySquareGameObjectIsOn = inventorySquares[i][j];
						return;
					}
				}
			}
		}
	}

	@Override
	public void remove(GameObject gameObject) {
		if (gameObjects.contains(gameObject)) {
			gameObjects.remove(gameObject);
			gameObject.inventorySquareGameObjectIsOn = null;
			for (int i = 0; i < inventorySquares.length; i++) {
				for (int j = 0; j < inventorySquares[i].length; j++) {
					if (inventorySquares[i][j].gameObject == gameObject) {
						inventorySquares[i][j].gameObject.inventorySquareGameObjectIsOn = null;
						inventorySquares[i][j].gameObject = null;
						return;
					}
				}
			}
		}
	}
}
