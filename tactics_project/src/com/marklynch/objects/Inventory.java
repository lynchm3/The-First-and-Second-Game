package com.marklynch.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.marklynch.Game;
import com.marklynch.editor.UserInputEditor;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;

public class Inventory {

	public int widthInSquares = 5;
	public int heightInSquares = 6;
	public transient InventorySquare[][] inventorySquares = new InventorySquare[widthInSquares][heightInSquares];
	protected ArrayList<GameObject> gameObjects = new ArrayList<GameObject>(widthInSquares * heightInSquares);

	public enum INVENTORY_STATE {
		DEFAULT, ADD_OBJECT, MOVEABLE_OBJECT_SELECTED, SETTINGS_CHANGE
	}

	public transient INVENTORY_STATE inventoryState = INVENTORY_STATE.DEFAULT;

	public enum INVENTORY_SORT_BY {
		SORT_ALPHABETICALLY, SORT_BY_NEWEST, SORT_BY_VALUE, SORT_BY_FAVOURITE, SORT_BY_TOTAL_DAMAGE, SORT_BY_SLASH_DAMAGE, SORT_BY_BLUNT_DAMAGE, SORT_BY_PIERCE_DAMAGE, SORT_BY_FIRE_DAMAGE, SORT_BY_WATER_DAMAGE, SORT_BY_POISON_DAMAGE, SORT_BY_ELECTRICAL_DAMAGE, SORT_BY_MAX_RANGE, SORT_BY_MIN_RANGE
	}

	public static transient INVENTORY_SORT_BY inventorySortBy = INVENTORY_SORT_BY.SORT_BY_MAX_RANGE;

	private transient boolean isOpen = false;
	transient float x = 300;
	transient float y = 100;
	transient float width = widthInSquares * Game.SQUARE_WIDTH;
	transient float height = heightInSquares * Game.SQUARE_HEIGHT;
	transient private InventorySquare inventorySquareMouseIsOver;
	transient private GameObject selectedGameObject;

	LevelButton buttonSortAlphabetically;
	LevelButton buttonSortByNewest;
	LevelButton buttonSortByFavourite;
	LevelButton buttonSortByValue;
	LevelButton buttonSortByTotalDamage;
	LevelButton buttonSortBySlashDamage;

	public ArrayList<Button> buttons;

	public Inventory() {
		for (int i = 0; i < inventorySquares[0].length; i++) {
			for (int j = 0; j < inventorySquares.length; j++) {
				inventorySquares[j][i] = new InventorySquare(j, i, "dialogbg.png", this);
			}
		}
		buttons = new ArrayList<Button>();

		buttonSortAlphabetically = new LevelButton(100f, 100f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"SORT A-Z", true, true, Color.BLACK, Color.WHITE);
		buttonSortAlphabetically.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_ALPHABETICALLY);
			}
		});
		buttons.add(buttonSortAlphabetically);

		buttonSortByNewest = new LevelButton(100f, 150f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"NEWEST", true, true, Color.BLACK, Color.WHITE);
		buttonSortByNewest.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_NEWEST);
			}
		});
		buttons.add(buttonSortByNewest);

		buttonSortByFavourite = new LevelButton(100f, 200f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"FAVOURITES", true, true, Color.BLACK, Color.WHITE);
		buttonSortByFavourite.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_FAVOURITE);
			}
		});
		buttons.add(buttonSortByFavourite);

		buttonSortByValue = new LevelButton(100f, 250f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"VALUE", true, true, Color.BLACK, Color.WHITE);
		buttonSortByValue.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_VALUE);
			}
		});
		buttons.add(buttonSortByValue);

		buttonSortByTotalDamage = new LevelButton(100f, 300f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"DAMAGE", true, true, Color.BLACK, Color.WHITE);
		buttonSortByTotalDamage.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_TOTAL_DAMAGE);
			}
		});
		buttons.add(buttonSortByTotalDamage);

		buttonSortBySlashDamage = new LevelButton(100f, 350f, 10f, 30f, "end_turn_button.png", "end_turn_button.png",
				"SLASH", true, true, Color.BLACK, Color.WHITE);
		buttonSortBySlashDamage.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_SLASH_DAMAGE);
			}
		});
		buttons.add(buttonSortBySlashDamage);

	}

	public void sort(INVENTORY_SORT_BY inventorySortBy) {
		Inventory.inventorySortBy = inventorySortBy;
		Collections.sort(gameObjects);
		this.setGameObjects(this.gameObjects);
	}

	public void postLoad1() {
		inventorySquares = new InventorySquare[widthInSquares][heightInSquares];
		for (int i = 0; i < inventorySquares[0].length; i++) {
			for (int j = 0; j < inventorySquares.length; j++) {
				inventorySquares[j][i] = new InventorySquare(i, j, "dialogbg.png", this);
				inventorySquares[j][i].inventoryThisBelongsTo = this;
				inventorySquares[j][i].loadImages();
			}
		}

		// Tell objects they're in this inventory
		for (GameObject gameObject : gameObjects) {
			gameObject.inventoryThatHoldsThisObject = this;
			gameObject.postLoad1();
		}

		int index = 0;

		// Put objects in inventory
		for (int i = 0; i < inventorySquares[0].length; i++) {
			for (int j = 0; j < inventorySquares.length; j++) {

				if (index >= gameObjects.size())
					return;

				if (inventorySquares[j][i].gameObject == null) {
					inventorySquares[j][i].gameObject = gameObjects.get(index);
					gameObjects.get(index).inventorySquareGameObjectIsOn = inventorySquares[j][i];
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
		for (int i = 0; i < inventorySquares[0].length; i++) {
			for (int j = 0; j < inventorySquares.length; j++) {
				inventorySquares[j][i].loadImages();
			}
		}
	}

	public GameObject get(int i) {
		return gameObjects.get(i);
	}

	public void add(GameObject gameObject) {
		System.out.println("add " + gameObject.name);
		if (!gameObjects.contains(gameObject)) {

			// Remove references with square
			if (gameObject.squareGameObjectIsOn != null)
				gameObject.squareGameObjectIsOn.inventory.remove(gameObject);
			gameObject.squareGameObjectIsOn = null;

			// Remove from ground squares index
			if (Game.level.inanimateObjectsOnGround.contains(gameObject))
				Game.level.inanimateObjectsOnGround.remove(gameObject);

			// Remove from another gameObjects inventory
			if (gameObject.inventoryThatHoldsThisObject != null)
				gameObject.inventoryThatHoldsThisObject.remove(gameObject);

			// Add to this inventory's list of game objects
			gameObjects.add(gameObject);
			gameObject.inventoryThatHoldsThisObject = this;

			// Add to the inventory UI
			for (int i = 0; i < inventorySquares[0].length; i++) {
				for (int j = 0; j < inventorySquares.length; j++) {
					if (inventorySquares[j][i].gameObject == null) {
						inventorySquares[j][i].gameObject = gameObject;
						gameObject.pickUpdateDateTime = new Date();
						gameObject.inventorySquareGameObjectIsOn = inventorySquares[j][i];
						System.out.println(
								"Setting pickupdate for " + gameObject.name + " - " + gameObject.pickUpdateDateTime);
						return;
					}
				}
			}
		}
		System.out.println("add end " + gameObject.name);
	}

	public void remove(GameObject gameObject) {
		if (gameObjects.contains(gameObject)) {
			gameObjects.remove(gameObject);
			for (int i = 0; i < inventorySquares[0].length; i++) {
				for (int j = 0; j < inventorySquares.length; j++) {
					if (inventorySquares[j][i].gameObject == gameObject) {
						inventorySquares[j][i].gameObject.inventorySquareGameObjectIsOn = null;
						inventorySquares[j][i].gameObject = null;
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
		for (int i = 0; i < inventorySquares[0].length; i++) {
			for (int j = 0; j < inventorySquares.length; j++) {

				if (index >= gameObjects.size())
					return;

				// if (inventorySquares[j][i].gameObject == null) {
				inventorySquares[j][i].gameObject = gameObjects.get(index);
				gameObjects.get(index).inventorySquareGameObjectIsOn = inventorySquares[j][i];
				index++;
				// }
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

	public boolean isPassable(Actor forActor) {
		for (GameObject gameObject : gameObjects) {

			if (forActor.group != null && forActor.group.getLeader() == gameObject)
				return false;

			if (gameObject != Game.level.player && gameObject instanceof Actor)
				return true;

			if (gameObject != null && !gameObject.canShareSquare)
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

	public GameObject getGameObectOfClass(Class clazz) {
		for (GameObject gameObject : gameObjects) {
			if (clazz.isInstance(gameObject)) {
				return gameObject;
			}
		}
		return null;
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

	@SuppressWarnings("unchecked")
	public void drawStaticUI() {

		// Black cover
		QuadUtils.drawQuad(Color.BLACK, 0, Game.windowWidth, 0, Game.windowHeight);

		for (int i = 0; i < inventorySquares[0].length; i++) {
			for (int j = 0; j < inventorySquares.length; j++) {

				inventorySquares[j][i].drawStaticUI();

			}
		}

		// cursor
		if (this.inventorySquareMouseIsOver != null) {
			this.inventorySquareMouseIsOver.drawCursor();
		}

		// buttons
		for (Button button : buttons) {
			button.draw();
		}

		// Actor
		int actorPositionXInPixels = 1150;
		int actorPositionYInPixels = 250;
		float alpha = 1.0f;
		TextureUtils.drawTexture(Game.level.player.imageTexture, alpha, actorPositionXInPixels,
				actorPositionXInPixels + Game.level.player.width * 3, actorPositionYInPixels,
				actorPositionYInPixels + Game.level.player.height * 3);

		Weapon equippedWeapon = Game.level.player.equippedWeapon;
		// Equipped weapon on actor
		if (equippedWeapon != null) {
			int weaponPositionXInPixels = (int) (actorPositionXInPixels
					+ ((int) Game.HALF_SQUARE_WIDTH - equippedWeapon.halfWidth) * 3);
			int weaponPositionYInPixels = (int) (actorPositionYInPixels
					+ ((int) Game.HALF_SQUARE_HEIGHT - equippedWeapon.halfHeight) * 3);
			TextureUtils.drawTexture(equippedWeapon.imageTexture, alpha, weaponPositionXInPixels,
					weaponPositionXInPixels + equippedWeapon.width * 3, weaponPositionYInPixels,
					weaponPositionYInPixels + equippedWeapon.height * 3);
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
		for (int i = 0; i < inventorySquares[0].length; i++) {
			for (int j = 0; j < inventorySquares.length; j++) {
				if (inventorySquares[j][i].calculateIfPointInBoundsOfSquare(UserInputEditor.mouseXinPixels,
						Game.windowHeight - UserInputEditor.mouseYinPixels)) {
					this.inventorySquareMouseIsOver = inventorySquares[j][i];
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

	public InventorySquare getInventorySquareMouseIsOver(float mouseXInPixels, float mouseYInPixels) {

		float offsetX = x;
		float offsetY = y;
		float scroll = 0;

		int mouseXInSquares = (int) ((mouseXInPixels - offsetX) / Game.SQUARE_WIDTH);
		int mouseYInSquares = (int) ((Game.windowHeight - mouseYInPixels - offsetY - scroll) / Game.SQUARE_HEIGHT);

		if (mouseXInSquares > -1 && mouseXInSquares < inventorySquares.length && mouseYInSquares > -1
				&& mouseYInSquares < inventorySquares[0].length) {

			System.out.println("getSquareMouseIsOver = " + this.inventorySquares[mouseXInSquares][mouseYInSquares]);

			return this.inventorySquares[mouseXInSquares][mouseYInSquares];
		}

		return null;
	}

	public void setSquareMouseHoveringOver(InventorySquare squareMouseIsOver) {
		this.inventorySquareMouseIsOver = squareMouseIsOver;

	}

}
