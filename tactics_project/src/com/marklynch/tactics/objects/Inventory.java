package com.marklynch.tactics.objects;

import java.util.ArrayList;

import com.marklynch.Game;

public class Inventory {

	public InventorySquare[][] inventorySquares = new InventorySquare[5][5];

	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

	private boolean isOpen = false;

	public Inventory() {
		for (int i = 0; i < inventorySquares.length; i++) {
			for (int j = 0; j < inventorySquares[i].length; j++) {
				inventorySquares[i][j] = new InventorySquare(i, j, "grass.png", this);
			}
		}

	}

	public void loadImages() {
		for (int i = 0; i < inventorySquares.length; i++) {
			for (int j = 0; j < inventorySquares[i].length; j++) {
				inventorySquares[i][j].loadImages();
			}
		}
	}

	public GameObject get(int i) {
		return gameObjects.get(i);
	}

	public void add(GameObject gameObject) {
		if (!gameObjects.contains(gameObject))
			gameObjects.add(gameObject);
	}

	public void remove(GameObject gameObject) {
		gameObjects.remove(gameObject);
	}

	public int size() {
		return gameObjects.size();
	}

	public ArrayList<GameObject> getGameObjects() {
		return gameObjects;
	}

	public void setGameObjects(ArrayList<GameObject> gameObjects) {
		this.gameObjects = gameObjects;
	}

	public boolean contains(GameObject gameObject) {
		return gameObjects.contains(gameObject);
	}

	public boolean canShareSquare() {
		for (GameObject gameObject : gameObjects) {
			if (!gameObject.canShareSquare)
				return false;
		}
		return true;
	}

	public GameObject getGameObjectThatCantShareSquare() {
		for (GameObject gameObject : gameObjects) {
			if (!gameObject.canShareSquare)
				return gameObject;
		}
		return null;
	}

	public boolean hasGameObjectsThatCanContainOtherObjects() {
		// TODO Auto-generated method stubArrayList<GameObject>
		for (GameObject gameObject : gameObjects) {
			if (gameObject.canContainOtherObjects)
				return true;
		}
		return false;
	}

	public ArrayList<GameObject> getGameObjectsThatCanContainOtherObjects() {
		ArrayList<GameObject> gameObjectsThatCanContainOtherObjects = new ArrayList<GameObject>();
		for (GameObject gameObject : gameObjects) {
			if (gameObject.canContainOtherObjects)
				gameObjectsThatCanContainOtherObjects.add(gameObject);
		}
		return gameObjectsThatCanContainOtherObjects;
	}

	public Inventory makeCopy() {
		Inventory copy = new Inventory();
		for (GameObject gameObject : gameObjects) {
			copy.add(gameObject.makeCopy(null));
		}
		return copy;
	}

	public void drawBackground() {

	}

	public void drawForeground() {

	}

	public void drawStaticUI() {
		// if (isOpen) {
		for (int i = 0; i < inventorySquares.length; i++) {
			for (int j = 0; j < inventorySquares[i].length; j++) {
				inventorySquares[i][j].drawStaticUI();
			}
		}
		// }
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void open() {
		this.isOpen = true;
		Game.level.openInventories.add(this);
	}

	public void close() {
		this.isOpen = false;
		Game.level.openInventories.remove(this);
	}

}
