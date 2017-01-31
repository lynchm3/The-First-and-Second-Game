package com.marklynch.tactics.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.UserInputEditor;
import com.marklynch.utils.TextureUtils;

public class Inventory {

	public InventorySquare[][] inventorySquares = new InventorySquare[5][5];

	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

	private boolean isOpen = false;

	float x = 0;
	float y = 0;
	float width = 5 * Game.SQUARE_WIDTH;
	float height = 5 * Game.SQUARE_HEIGHT;

	private InventorySquare inventorySquaresMouseIsOver;

	public Inventory() {
		for (int i = 0; i < inventorySquares.length; i++) {
			for (int j = 0; j < inventorySquares[i].length; j++) {
				inventorySquares[i][j] = new InventorySquare(i, j, "dialogbg.png", this);
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
		int gameObjectIndex = 0;
		for (int i = 0; i < inventorySquares.length; i++) {
			for (int j = 0; j < inventorySquares[i].length; j++) {

				inventorySquares[i][j].drawStaticUI();
				gameObjectIndex = i * inventorySquares[i].length + j;
				System.out.println("gameObjects.size() = " + gameObjects.size());
				System.out.println("gameObjectIndex = " + gameObjectIndex);
				if (gameObjects.size() > gameObjectIndex) {

					System.out.println("gameObjects.get(gameObjectIndex)  = " + gameObjects.get(gameObjectIndex));
				}
				if (gameObjects.size() > gameObjectIndex && gameObjects.get(gameObjectIndex) != null) {

					int squarePositionX = inventorySquares[i][j].x * (int) Game.SQUARE_WIDTH;
					int squarePositionY = inventorySquares[i][j].y * (int) Game.SQUARE_HEIGHT;
					TextureUtils.drawTexture(gameObjects.get(gameObjectIndex).imageTexture, squarePositionX,
							squarePositionX + Game.SQUARE_WIDTH, squarePositionY, squarePositionY + Game.SQUARE_HEIGHT);

				}

			}
		}

		if (this.inventorySquaresMouseIsOver != null) {
			System.out.println("inventorySquaresMouseIsOver != null");
			this.inventorySquaresMouseIsOver.drawCursor();
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

	public boolean calculateIfPointInBoundsOfInventory(float mouseX, float mouseY) {
		if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height) {
			return true;
		}
		return false;
	}

	public void userInput() {
		float mouseXInSquares = -1;
		float mouseYInSquares = -1;
		if (UserInputEditor.mouseXinPixels >= 0)
			mouseXInSquares = (int) (UserInputEditor.mouseXinPixels / Game.SQUARE_WIDTH);
		if ((Game.windowHeight - UserInputEditor.mouseYinPixels) >= 0)
			mouseYInSquares = (int) ((Game.windowHeight - UserInputEditor.mouseYinPixels) / Game.SQUARE_HEIGHT);

		System.out.println("mouseXInSquares = " + mouseXInSquares);
		System.out.println("mouseYInSquares = " + mouseYInSquares);

		// Get the square that we're hovering over
		this.inventorySquaresMouseIsOver = null;
		if (mouseXInSquares >= 0 && mouseYInSquares >= 0 && (int) mouseXInSquares > -1
				&& (int) mouseXInSquares < inventorySquares.length && (int) mouseYInSquares > -1
				&& (int) mouseYInSquares < inventorySquares[0].length) {
			this.inventorySquaresMouseIsOver = this.inventorySquares[(int) mouseXInSquares][(int) mouseYInSquares];
			System.out.println("inside if");
		}
		System.out.println("this.inventorySquaresMouseIsOver = " + this.inventorySquaresMouseIsOver);

	}

}
