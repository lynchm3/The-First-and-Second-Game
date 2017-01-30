package com.marklynch.tactics.objects;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.marklynch.Game;
import com.marklynch.UserInputEditor;

public class Inventory {

	public InventorySquare[][] inventorySquares = new InventorySquare[5][5];

	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

	private boolean isOpen = false;

	float x = 0;
	float y = 0;
	float width = 250;
	float height = 250;

	private InventorySquare inventorySquaresMouseIsOver;

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

		if (this.inventorySquaresMouseIsOver != null)
			this.inventorySquaresMouseIsOver.drawCursor();

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

	public boolean calculateIfPointInBoundsOfInventory(float mouseX, float mouseY) {
		if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height) {
			return true;
		}
		return false;
	}

	public void userInput() {
		// TODO Auto-generated method stub

		// Getting what square pixel the mouse is on
		// Editor.mouseXinPixels = Mouse.getX();
		// float mouseYinPixels = Mouse.getY();

		// Transformed mouse coords

		// float mouseXTransformed = (((Game.windowWidth / 2) - Game.dragX -
		// (Game.windowWidth / 2) / Game.zoom)
		// + (mouseXinPixels) / Game.zoom);
		// float mouseYTransformed = ((Game.windowHeight / 2 - Game.dragY -
		// (Game.windowHeight / 2) / Game.zoom)
		// + (((Game.windowHeight - mouseYinPixels)) / Game.zoom));

		// Getting what square coordinates the mouse is on (as in squares on the
		// grid)
		float mouseXInSquares = -1;
		float mouseYInSquares = -1;
		if (UserInputEditor.mouseXinPixels >= 0)
			mouseXInSquares = (int) (UserInputEditor.mouseXinPixels / Game.SQUARE_WIDTH);
		if (UserInputEditor.mouseYinPixels >= 0)
			mouseYInSquares = (int) (UserInputEditor.mouseYinPixels / Game.SQUARE_HEIGHT);

		// Calculate zoom
		Game.zoom += 0.001 * Mouse.getDWheel();
		if (Game.zoom < 0.1)
			Game.zoom = 0.1f;
		if (Game.zoom > 2)
			Game.zoom = 2f;

		// Checking for drag

		// Get the square that we're hovering over
		Game.squareMouseIsOver = null;
		if (mouseXInSquares >= 0 && mouseYInSquares >= 0 && (int) mouseXInSquares > -1
				&& (int) mouseXInSquares < inventorySquares.length && (int) mouseYInSquares > -1
				&& (int) mouseYInSquares < inventorySquares[0].length) {
			this.inventorySquaresMouseIsOver = this.inventorySquares[(int) mouseXInSquares][(int) mouseYInSquares];
		}

	}

}
