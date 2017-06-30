package com.marklynch.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.marklynch.Game;
import com.marklynch.editor.UserInputEditor;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.BodyArmor;
import com.marklynch.objects.weapons.Helmet;
import com.marklynch.objects.weapons.LegArmor;
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
	protected ArrayList<GameObject> filteredGameObjects = new ArrayList<GameObject>(widthInSquares * heightInSquares);

	public enum INVENTORY_STATE {
		DEFAULT, ADD_OBJECT, MOVEABLE_OBJECT_SELECTED, SETTINGS_CHANGE
	}

	public transient INVENTORY_STATE inventoryState = INVENTORY_STATE.DEFAULT;

	public enum INVENTORY_SORT_BY {
		SORT_ALPHABETICALLY, SORT_BY_NEWEST, SORT_BY_VALUE, SORT_BY_FAVOURITE, SORT_BY_TOTAL_DAMAGE, SORT_BY_SLASH_DAMAGE, SORT_BY_BLUNT_DAMAGE, SORT_BY_PIERCE_DAMAGE, SORT_BY_FIRE_DAMAGE, SORT_BY_WATER_DAMAGE, SORT_BY_POISON_DAMAGE, SORT_BY_ELECTRICAL_DAMAGE, SORT_BY_MAX_RANGE, SORT_BY_MIN_RANGE
	}

	public static transient INVENTORY_SORT_BY inventorySortBy = INVENTORY_SORT_BY.SORT_BY_NEWEST;

	public enum INVENTORY_FILTER_BY {
		FILTER_BY_ALL, FILTER_BY_WEAPON, FILTER_BY_FOOD
	}

	public static transient INVENTORY_FILTER_BY inventoryFilterBy = INVENTORY_FILTER_BY.FILTER_BY_ALL;

	private transient boolean isOpen = false;
	transient float x = 300;
	transient float y = 100;
	transient float width = widthInSquares * Game.SQUARE_WIDTH;
	transient float height = heightInSquares * Game.SQUARE_HEIGHT;
	transient private InventorySquare inventorySquareMouseIsOver;
	transient private GameObject selectedGameObject;

	// Sort buttons
	LevelButton buttonSortAlphabetically;
	LevelButton buttonSortByNewest;
	LevelButton buttonSortByFavourite;
	LevelButton buttonSortByValue;
	LevelButton buttonSortByTotalDamage;
	LevelButton buttonSortBySlashDamage;

	// Filter buttons
	LevelButton buttonFilterByAll;
	LevelButton buttonFilterByWeapon;
	LevelButton buttonFilterByFood;

	public ArrayList<Button> buttons;
	public ArrayList<Button> buttonsSort;
	public ArrayList<Button> buttonsFilter;

	public InventoryParent parent;

	public Inventory(GameObject... gameObjects) {
		for (int i = 0; i < inventorySquares[0].length; i++) {
			for (int j = 0; j < inventorySquares.length; j++) {
				inventorySquares[j][i] = new InventorySquare(j, i, "dialogbg.png", this);
			}
		}
		buttons = new ArrayList<Button>();
		buttonsSort = new ArrayList<Button>();
		buttonsFilter = new ArrayList<Button>();

		buttonSortAlphabetically = new LevelButton(100f, 100f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"SORT A-Z", true, true, Color.BLACK, Color.WHITE);
		buttonSortAlphabetically.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_ALPHABETICALLY);
			}
		});
		buttons.add(buttonSortAlphabetically);
		buttonsSort.add(buttonSortAlphabetically);

		buttonSortByNewest = new LevelButton(100f, 150f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"NEWEST", true, true, Color.BLACK, Color.WHITE);
		buttonSortByNewest.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_NEWEST);
			}
		});
		buttons.add(buttonSortByNewest);
		buttonsSort.add(buttonSortByNewest);

		buttonSortByFavourite = new LevelButton(100f, 200f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"FAVOURITES", true, true, Color.BLACK, Color.WHITE);
		buttonSortByFavourite.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_FAVOURITE);
			}
		});
		buttons.add(buttonSortByFavourite);
		buttonsSort.add(buttonSortByFavourite);

		buttonSortByValue = new LevelButton(100f, 250f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"VALUE", true, true, Color.BLACK, Color.WHITE);
		buttonSortByValue.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_VALUE);
			}
		});
		buttons.add(buttonSortByValue);
		buttonsSort.add(buttonSortByValue);

		buttonSortByTotalDamage = new LevelButton(100f, 300f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"DAMAGE", true, true, Color.BLACK, Color.WHITE);
		buttonSortByTotalDamage.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_TOTAL_DAMAGE);
			}
		});
		buttons.add(buttonSortByTotalDamage);
		buttonsSort.add(buttonSortByTotalDamage);

		buttonSortBySlashDamage = new LevelButton(100f, 350f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"SLASH", true, true, Color.BLACK, Color.WHITE);
		buttonSortBySlashDamage.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_SLASH_DAMAGE);
			}
		});
		buttons.add(buttonSortBySlashDamage);
		buttonsSort.add(buttonSortBySlashDamage);

		buttonFilterByAll = new LevelButton(300f, 50f, 100f, 30f, "end_turn_button.png", "end_turn_button.png", "ALL",
				true, true, Color.BLACK, Color.WHITE);
		buttonFilterByAll.setClickListener(new ClickListener() {
			@Override
			public void click() {
				filter(INVENTORY_FILTER_BY.FILTER_BY_ALL);
			}
		});
		buttons.add(buttonFilterByAll);
		buttonsFilter.add(buttonFilterByAll);

		buttonFilterByWeapon = new LevelButton(400f, 50f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"WEAPONS", true, true, Color.BLACK, Color.WHITE);
		buttonFilterByWeapon.setClickListener(new ClickListener() {
			@Override
			public void click() {
				filter(INVENTORY_FILTER_BY.FILTER_BY_WEAPON);
			}
		});
		buttons.add(buttonFilterByWeapon);
		buttonsFilter.add(buttonFilterByWeapon);

		buttonFilterByFood = new LevelButton(500f, 50f, 100f, 30f, "end_turn_button.png", "end_turn_button.png", "FOOD",
				true, true, Color.BLACK, Color.WHITE);
		buttonFilterByFood.setClickListener(new ClickListener() {
			@Override
			public void click() {
				filter(INVENTORY_FILTER_BY.FILTER_BY_FOOD);
			}
		});
		buttons.add(buttonFilterByFood);
		buttonsFilter.add(buttonFilterByFood);

		for (GameObject gameObject : gameObjects) {
			add(gameObject);
		}

	}

	public void sort(INVENTORY_SORT_BY inventorySortBy) {

		for (Button button : buttonsSort)
			button.down = false;
		if (inventorySortBy == INVENTORY_SORT_BY.SORT_ALPHABETICALLY) {
			buttonSortAlphabetically.down = true;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_NEWEST) {
			buttonSortByNewest.down = true;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_VALUE) {
			buttonSortByValue.down = true;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_FAVOURITE) {
			buttonSortByFavourite.down = true;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_TOTAL_DAMAGE) {
			buttonSortByTotalDamage.down = true;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_SLASH_DAMAGE) {
			buttonSortBySlashDamage.down = true;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_BLUNT_DAMAGE) {

		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_PIERCE_DAMAGE) {

		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_FIRE_DAMAGE) {

		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_WATER_DAMAGE) {

		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_POISON_DAMAGE) {

		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_ELECTRICAL_DAMAGE) {

		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_MAX_RANGE) {

		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_MIN_RANGE) {

		}

		Inventory.inventorySortBy = inventorySortBy;
		Collections.sort(filteredGameObjects);
		matchGameObjectsToSquares();
	}

	public void filter(INVENTORY_FILTER_BY inventoryFilterBy) {
		Inventory.inventoryFilterBy = inventoryFilterBy;
		for (Button button : buttonsFilter)
			button.down = false;
		if (inventoryFilterBy == INVENTORY_FILTER_BY.FILTER_BY_ALL) {
		} else if (inventoryFilterBy == INVENTORY_FILTER_BY.FILTER_BY_WEAPON) {
		}
		filteredGameObjects.clear();
		if (Inventory.inventoryFilterBy == INVENTORY_FILTER_BY.FILTER_BY_ALL) {
			buttonFilterByAll.down = true;
			filteredGameObjects.addAll(gameObjects);
		} else if (Inventory.inventoryFilterBy == INVENTORY_FILTER_BY.FILTER_BY_WEAPON) {
			buttonFilterByWeapon.down = true;
			for (GameObject gameObject : gameObjects) {
				if (gameObject instanceof Weapon) {
					filteredGameObjects.add(gameObject);
				}
			}
		} else if (Inventory.inventoryFilterBy == INVENTORY_FILTER_BY.FILTER_BY_FOOD) {
			buttonFilterByFood.down = true;
			for (GameObject gameObject : gameObjects) {
				if (gameObject instanceof Food) {
					filteredGameObjects.add(gameObject);
				}
			}
		}

		sort(Inventory.inventorySortBy);
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
		// for (int i = 0; i < inventorySquares[0].length; i++) {
		// for (int j = 0; j < inventorySquares.length; j++) {
		//
		// if (index >= gameObjects.size())
		// return;
		//
		// // if (inventorySquares[j][i].gameObject == null) {
		// // inventorySquares[j][i].gameObject = gameObjects.get(index);
		// // gameObjects.get(index).inventorySquareGameObjectIsOn =
		// // inventorySquares[j][i];
		// // index++;
		// // }
		// }
		// }
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
		if (!gameObjects.contains(gameObject)) {

			// Remove references with square
			if (gameObject.squareGameObjectIsOn != null) {
				gameObject.squareGameObjectIsOn.inventory.remove(gameObject);
				gameObject.squareGameObjectIsOn.inventory.matchGameObjectsToSquares();
			}
			gameObject.squareGameObjectIsOn = null;

			// Remove from ground squares index
			if (Game.level.inanimateObjectsOnGround.contains(gameObject))
				Game.level.inanimateObjectsOnGround.remove(gameObject);

			// Remove from another gameObjects inventory
			if (gameObject.inventoryThatHoldsThisObject != null) {
				gameObject.inventoryThatHoldsThisObject.remove(gameObject);
				gameObject.inventoryThatHoldsThisObject.matchGameObjectsToSquares();
			}

			// Add to this inventory's list of game objects
			gameObjects.add(gameObject);
			gameObject.inventoryThatHoldsThisObject = this;
			// this.sort(inventorySortBy);

			// pick up date for sorting by newest
			gameObject.pickUpdateDateTime = new Date();

			if (parent != null)
				parent.inventoryChanged();

		}
	}

	public void remove(GameObject gameObject) {
		if (gameObjects.contains(gameObject)) {
			gameObjects.remove(gameObject);
			gameObject.inventorySquareGameObjectIsOn = null;
			if (parent != null)
				parent.inventoryChanged();
			// this.sort(inventorySortBy);
			if (filteredGameObjects.contains(gameObject)) {
				filteredGameObjects.set(filteredGameObjects.indexOf(gameObject), null);
			}
			this.matchGameObjectsToSquares();
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
		this.sort(inventorySortBy);
		matchGameObjectsToSquares();
	}

	public void matchGameObjectsToSquares() {

		for (GameObject gameObject : gameObjects) {
			gameObject.inventorySquareGameObjectIsOn = null;
		}

		int index = 0;
		for (int i = 0; i < inventorySquares[0].length; i++) {
			for (int j = 0; j < inventorySquares.length; j++) {
				inventorySquares[j][i].gameObject = null;
				if (index < filteredGameObjects.size()) {
					inventorySquares[j][i].gameObject = filteredGameObjects.get(index);
					if (inventorySquares[j][i].gameObject != null)
						inventorySquares[j][i].gameObject.inventorySquareGameObjectIsOn = inventorySquares[j][i];
					index++;
				}
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

	public boolean canBeMovedTo() {
		if (canShareSquare()) {
			return true;
		} else {
			if (contains(Actor.class))
				return true;
		}
		return false;
	}

	public boolean isPassable(Actor forActor) {
		for (GameObject gameObject : gameObjects) {

			// Can't go through group leader
			if (forActor.group != null && forActor.group.getLeader() == gameObject)
				return false;

			// Can't go through player
			if (gameObject == Game.level.player)
				return false;

			// Can't got through impassable objects
			if (!gameObject.canShareSquare && !(gameObject instanceof Door) && !(gameObject instanceof Actor))
				return false;

			// Can't go through locked doors u dont have the key for
			if (gameObject instanceof Door) {
				Door door = (Door) gameObject;
				if (door.locked && !forActor.hasKeyForDoor(door)) {
					return false;
				}
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

	public GameObject getGameObjectOfClass(Class clazz) {
		for (GameObject gameObject : gameObjects) {
			if (clazz.isInstance(gameObject)) {
				return gameObject;
			}
		}
		return null;
	}

	public ArrayList<GameObject> getGameObjectsOfClass(Class clazz) {
		ArrayList<GameObject> gameObjectsToReturn = new ArrayList<GameObject>();
		for (GameObject gameObject : this.gameObjects) {
			if (clazz.isInstance(gameObject)) {
				gameObjectsToReturn.add(gameObject);
			}
		}
		return gameObjectsToReturn;
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
			copy.add(gameObject.makeCopy(null, null));
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
			this.inventorySquareMouseIsOver.drawDefaultAction();
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

		GameObject gameObjectMouseIsOver = null;

		GameObject gameObjectToDrawInPlayersHand = null;
		GameObject gameObjectToDrawOnPlayersHead = Game.level.player.helmet;
		GameObject gameObjectToDrawOnPlayersBody = Game.level.player.bodyArmor;
		GameObject gameObjectToDrawOnPlayersLegs = Game.level.player.legArmor;

		if (Game.squareMouseIsOver != null && Game.squareMouseIsOver instanceof InventorySquare) {
			// Preview weapon
			gameObjectMouseIsOver = ((InventorySquare) Game.squareMouseIsOver).gameObject;

			if (gameObjectMouseIsOver instanceof Helmet) {
				gameObjectToDrawOnPlayersHead = gameObjectMouseIsOver;
			} else if (gameObjectMouseIsOver instanceof BodyArmor) {
				gameObjectToDrawOnPlayersBody = gameObjectMouseIsOver;
			} else if (gameObjectMouseIsOver instanceof LegArmor) {
				gameObjectToDrawOnPlayersLegs = gameObjectMouseIsOver;
			} else {
				gameObjectToDrawInPlayersHand = gameObjectMouseIsOver;
			}

		}

		if (gameObjectToDrawInPlayersHand == null) {
			gameObjectToDrawInPlayersHand = Game.level.player.equipped;
		}

		// Object to draw player holding
		if (gameObjectToDrawInPlayersHand != null) {
			int weaponPositionXInPixels = (int) (actorPositionXInPixels
					+ (Game.level.player.handAnchorX - gameObjectToDrawInPlayersHand.anchorX) * 3);
			int weaponPositionYInPixels = (int) (actorPositionYInPixels
					+ (Game.level.player.handAnchorY - gameObjectToDrawInPlayersHand.anchorY) * 3);
			TextureUtils.drawTexture(gameObjectToDrawInPlayersHand.imageTexture, alpha, weaponPositionXInPixels,
					weaponPositionXInPixels + gameObjectToDrawInPlayersHand.width * 3, weaponPositionYInPixels,
					weaponPositionYInPixels + gameObjectToDrawInPlayersHand.height * 3);
		}

		// Helmet
		if (gameObjectToDrawOnPlayersHead != null) {
			int helmetPositionXInPixels = (int) (actorPositionXInPixels
					+ (Game.level.player.headAnchorX - gameObjectToDrawOnPlayersHead.anchorX) * 3);
			int helmetPositionYInPixels = (int) (actorPositionYInPixels
					+ (Game.level.player.headAnchorY - gameObjectToDrawOnPlayersHead.anchorY) * 3);
			TextureUtils.drawTexture(gameObjectToDrawOnPlayersHead.imageTexture, alpha, helmetPositionXInPixels,
					helmetPositionXInPixels + gameObjectToDrawOnPlayersHead.width * 3, helmetPositionYInPixels,
					helmetPositionYInPixels + gameObjectToDrawOnPlayersHead.height * 3);
		}

		// Body Armor
		if (gameObjectToDrawOnPlayersBody != null) {
			int bodyArmorPositionXInPixels = (int) (actorPositionXInPixels
					+ (Game.level.player.bodyAnchorX - gameObjectToDrawOnPlayersBody.anchorX) * 3);
			int bodyArmorPositionYInPixels = (int) (actorPositionYInPixels
					+ (Game.level.player.bodyAnchorY - gameObjectToDrawOnPlayersBody.anchorY) * 3);
			TextureUtils.drawTexture(gameObjectToDrawOnPlayersBody.imageTexture, alpha, bodyArmorPositionXInPixels,
					bodyArmorPositionXInPixels + gameObjectToDrawOnPlayersBody.width * 3, bodyArmorPositionYInPixels,
					bodyArmorPositionYInPixels + gameObjectToDrawOnPlayersBody.height * 3);
		}

		// Leg Armor
		if (gameObjectToDrawOnPlayersLegs != null) {
			int legArmorPositionXInPixels = (int) (actorPositionXInPixels
					+ (Game.level.player.legsAnchorX - gameObjectToDrawOnPlayersLegs.anchorX) * 3);
			int legArmorPositionYInPixels = (int) (actorPositionYInPixels
					+ (Game.level.player.legsAnchorY - gameObjectToDrawOnPlayersLegs.anchorY) * 3);
			TextureUtils.drawTexture(gameObjectToDrawOnPlayersLegs.imageTexture, alpha, legArmorPositionXInPixels,
					legArmorPositionXInPixels + gameObjectToDrawOnPlayersLegs.width * 3, legArmorPositionYInPixels,
					legArmorPositionYInPixels + gameObjectToDrawOnPlayersLegs.height * 3);
		}

		// Weapon comparison
		if (this.inventorySquareMouseIsOver != null && this.inventorySquareMouseIsOver.gameObject instanceof Weapon) {

			int comparisonPositionXInPixels = 1150;
			int comparisonPositionYInPixels = 250;
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

		float mouseXInSquares = (((mouseXInPixels - offsetX) / Game.SQUARE_WIDTH));
		float mouseYInSquares = ((Game.windowHeight - mouseYInPixels - offsetY - scroll) / Game.SQUARE_HEIGHT);

		if (mouseXInSquares >= 0 && mouseXInSquares < inventorySquares.length && mouseYInSquares >= 0
				&& mouseYInSquares < inventorySquares[0].length) {

			return this.inventorySquares[(int) mouseXInSquares][(int) mouseYInSquares];
		}

		return null;
	}

	public void setSquareMouseHoveringOver(InventorySquare squareMouseIsOver) {
		this.inventorySquareMouseIsOver = squareMouseIsOver;

	}

	public boolean containsAll(GameObject[] gameObjectsToCheck) {

		for (GameObject gameObjectToCheck : gameObjectsToCheck) {
			if (!gameObjects.contains(gameObjectToCheck)) {
				return false;
			}
		}
		return true;
	}

}
