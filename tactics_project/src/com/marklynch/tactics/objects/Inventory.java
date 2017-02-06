package com.marklynch.tactics.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.UserInputEditor;
import com.marklynch.tactics.objects.unit.Actor;

public class Inventory {

	public transient InventorySquare[][] inventorySquares = new InventorySquare[5][5];
	protected ArrayList<GameObject> gameObjects = new ArrayList<GameObject>(25);

	public enum INVENTORY_STATE {
		DEFAULT, ADD_OBJECT, MOVEABLE_OBJECT_SELECTED, SETTINGS_CHANGE
	}

	public transient INVENTORY_STATE inventoryState = INVENTORY_STATE.DEFAULT;

	private transient boolean isOpen = false;
	transient float x = 500;
	transient float y = 100;
	transient float width = 5 * Game.SQUARE_WIDTH;
	transient float height = 5 * Game.SQUARE_HEIGHT;
	transient private InventorySquare inventorySquareMouseIsOver;
	transient private GameObject selectedGameObject;

	public Inventory() {
		for (int i = 0; i < inventorySquares.length; i++) {
			for (int j = 0; j < inventorySquares[i].length; j++) {
				inventorySquares[i][j] = new InventorySquare(i, j, "dialogbg.png", this);
			}
		}
	}

	public void postLoad1() {
		inventorySquares = new InventorySquare[5][5];
		for (int i = 0; i < inventorySquares.length; i++) {
			for (int j = 0; j < inventorySquares[i].length; j++) {
				inventorySquares[i][j] = new InventorySquare(i, j, "dialogbg.png", this);
				inventorySquares[i][j].inventoryThisBelongsTo = this;
				inventorySquares[i][j].loadImages();
			}
		}

		// Tell objects they're in this inventory
		for (GameObject gameObject : gameObjects) {
			gameObject.inventoryThatHoldsThisObject = this;
			gameObject.postLoad1();
		}

		int index = 0;

		// Put objects in inventory
		for (int i = 0; i < inventorySquares.length; i++) {
			for (int j = 0; j < inventorySquares[i].length; j++) {

				if (index >= gameObjects.size())
					return;

				if (inventorySquares[i][j].gameObject == null) {
					inventorySquares[i][j].gameObject = gameObjects.get(index);
					gameObjects.get(index).inventorySquareGameObjectIsOn = inventorySquares[i][j];
					index++;
				}
			}
		}
	}

	public void postLoad2() {
		// Tell objects they're in this inventory
		for (GameObject gameObject : gameObjects) {
			gameObject.inventoryThatHoldsThisObject = this;
			gameObject.postLoad2();
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

			// Add to this inventory's list of game objects
			gameObjects.add(gameObject);
			gameObject.inventoryThatHoldsThisObject = this;

			// Add to the inventory UI
			for (int i = 0; i < inventorySquares.length; i++) {
				for (int j = 0; j < inventorySquares[i].length; j++) {
					if (inventorySquares[i][j].gameObject == null) {
						inventorySquares[i][j].gameObject = gameObject;
						gameObject.inventorySquareGameObjectIsOn = inventorySquares[i][j];
						return;
					}
				}
			}
		}
	}

	public void remove(GameObject gameObject) {
		if (gameObjects.contains(gameObject)) {
			gameObjects.remove(gameObject);
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

	public int size() {
		return gameObjects.size();
	}

	public ArrayList<GameObject> getGameObjects() {
		return gameObjects;
	}

	public void setGameObjects(ArrayList<GameObject> gameObjects) {
		this.gameObjects = gameObjects;
		int index = 0;
		for (int i = 0; i < inventorySquares.length; i++) {
			for (int j = 0; j < inventorySquares[i].length; j++) {

				if (index >= gameObjects.size())
					return;

				if (inventorySquares[i][j].gameObject == null) {
					inventorySquares[i][j].gameObject = gameObjects.get(index);
					gameObjects.get(index).inventorySquareGameObjectIsOn = inventorySquares[i][j];
					index++;
				}
			}
		}
	}

	public boolean contains(GameObject gameObject) {
		return gameObjects.contains(gameObject);
	}

	public boolean contains(Class clazz) {
		for (GameObject gameObject : gameObjects) {
			if (clazz.isInstance(gameObject)) {
				return true;
			}
		}
		return false;
	}

	public boolean canShareSquare() {
		for (GameObject gameObject : gameObjects) {
			if (gameObject != null && !gameObject.canShareSquare)
				return false;
		}
		return true;
	}

	public boolean isPassable() {
		for (GameObject gameObject : gameObjects) {

			if (gameObject instanceof Actor) {
				return true;
				// Actor actor = (Actor) gameObject;
				// if (actor.travelDistance - actor.distanceMovedThisTurn > 0) {
				// return false;
				// }

			} else {
				if (gameObject != null && !gameObject.canShareSquare)
					return false;
			}
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
		int gameObjectIndex = 0;
		for (int i = 0; i < inventorySquares.length; i++) {
			for (int j = 0; j < inventorySquares[i].length; j++) {

				inventorySquares[i][j].drawStaticUI();
				// gameObjectIndex = i * inventorySquares[i].length + j;
				// System.out.println("gameObjects.size() = " +
				// gameObjects.size());
				// System.out.println("gameObjectIndex = " + gameObjectIndex);
				// if (gameObjects.size() > gameObjectIndex) {
				//
				// System.out.println("gameObjects.get(gameObjectIndex) = " +
				// gameObjects.get(gameObjectIndex));
				// }
				// if (gameObjects.size() > gameObjectIndex &&
				// gameObjects.get(gameObjectIndex) != null) {
				//
				// TextureUtils.drawTexture(gameObjects.get(gameObjectIndex).imageTexture,
				// inventorySquares[i][j].xInPixels,
				// inventorySquares[i][j].xInPixels + Game.SQUARE_WIDTH,
				// inventorySquares[i][j].yInPixels,
				// inventorySquares[i][j].yInPixels + Game.SQUARE_HEIGHT);
				//
				// }

			}
		}

		if (this.inventorySquareMouseIsOver != null) {
			this.inventorySquareMouseIsOver.drawCursor();
		}
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

	public boolean calculateIfPointInBoundsOfInventory(float mouseX, float mouseY) {
		if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height) {
			return true;
		}
		return false;
	}

	public void userInput() {

		this.inventorySquareMouseIsOver = null;
		for (int i = 0; i < inventorySquares.length; i++) {
			for (int j = 0; j < inventorySquares[i].length; j++) {
				if (inventorySquares[i][j].calculateIfPointInBoundsOfSquare(UserInputEditor.mouseXinPixels,
						Game.windowHeight - UserInputEditor.mouseYinPixels)) {
					this.inventorySquareMouseIsOver = inventorySquares[i][j];
				}
			}
		}
	}

	public void click() {
		if (this.inventorySquareMouseIsOver != null) {
			this.inventorySquareClicked(inventorySquareMouseIsOver);
		}
	}

	private void inventorySquareClicked(InventorySquare inventorySquare) {
		if (inventorySquare.gameObject == null) {
			// Nothing on the square
			if (inventoryState == INVENTORY_STATE.DEFAULT || inventoryState == INVENTORY_STATE.SETTINGS_CHANGE) {
				// selectSquare(square);
			} else if (inventoryState == INVENTORY_STATE.ADD_OBJECT) {
				// attemptToAddNewObjectToSquare(square);
			} else if (inventoryState == INVENTORY_STATE.MOVEABLE_OBJECT_SELECTED) {
				// swapGameObjects(this.selectedGameObject, gameObjectOnSquare);
				moveGameObject(this.selectedGameObject, inventorySquare);
			}
		} else {
			// There's an object on the square
			if (inventoryState == INVENTORY_STATE.DEFAULT || inventoryState == INVENTORY_STATE.SETTINGS_CHANGE) {
				selectGameObject(inventorySquare.gameObject);
			} else if (inventoryState == INVENTORY_STATE.MOVEABLE_OBJECT_SELECTED) {
				swapGameObjects(this.selectedGameObject, inventorySquare.gameObject);
			}
		}
	}

	private void selectGameObject(GameObject gameObject) {
		selectedGameObject = gameObject;
		inventoryState = INVENTORY_STATE.MOVEABLE_OBJECT_SELECTED;
	}

	public void swapGameObjects(GameObject gameObject1, GameObject gameObject2) {
		InventorySquare square1 = gameObject1.inventorySquareGameObjectIsOn;
		InventorySquare square2 = gameObject2.inventorySquareGameObjectIsOn;

		square1.gameObject = gameObject2;
		square2.gameObject = gameObject1;

		gameObject1.inventorySquareGameObjectIsOn = square2;
		gameObject2.inventorySquareGameObjectIsOn = square1;

	}

	public void moveGameObject(GameObject gameObject1, InventorySquare square2) {
		InventorySquare square1 = gameObject1.inventorySquareGameObjectIsOn;

		if (square1 != null)
			square1.gameObject = null;

		square2.gameObject = gameObject1;

		gameObject1.inventorySquareGameObjectIsOn = square2;
	}

}
