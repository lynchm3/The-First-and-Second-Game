package com.marklynch.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.marklynch.Game;
import com.marklynch.editor.UserInputEditor;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
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
		FILTER_BY_ALL, FILTER_BY_WEAPON, FILTER_BY_FOOD, FILTER_BY_CONTAINER_FOR_LIQUIDS
	}

	public static transient INVENTORY_FILTER_BY inventoryFilterBy = INVENTORY_FILTER_BY.FILTER_BY_ALL;

	public enum INVENTORY_MODE {
		MODE_NORMAL, MODE_SELECT_ITEM_TO_FILL, MODE_SELECT_ITEM_TO_DROP, MODE_SELECT_ITEM_TO_THROW, MODE_SELECT_ITEM_TO_GIVE, MODE_SELECT_ITEM_TO_POUR
	}

	public static transient INVENTORY_MODE inventoryMode = INVENTORY_MODE.MODE_NORMAL;

	private transient boolean isOpen = false;
	transient float x = 300;
	transient float y = 100;
	transient float width = widthInSquares * Game.SQUARE_WIDTH;
	transient float height = heightInSquares * Game.SQUARE_HEIGHT;
	transient private InventorySquare inventorySquareMouseIsOver;
	transient private GameObject selectedGameObject;

	// Sort buttons
	static LevelButton buttonSortAlphabetically;
	static LevelButton buttonSortByNewest;
	static LevelButton buttonSortByFavourite;
	static LevelButton buttonSortByValue;
	static LevelButton buttonSortByTotalDamage;
	static LevelButton buttonSortBySlashDamage;

	// Filter buttons
	static LevelButton buttonFilterByAll;
	static LevelButton buttonFilterByWeapon;
	static LevelButton buttonFilterByFood;

	public static ArrayList<Button> buttons;
	public static ArrayList<Button> buttonsSort;
	public static ArrayList<Button> buttonsFilter;

	public InventoryParent parent;

	public static WaterSource waterSource;
	public static Square square;
	public static Object target;

	public Inventory(GameObject... gameObjects) {
		for (GameObject gameObject : gameObjects) {
			add(gameObject);
		}

	}

	public void open() {
		for (int i = 0; i < inventorySquares[0].length; i++) {
			for (int j = 0; j < inventorySquares.length; j++) {
				inventorySquares[j][i] = new InventorySquare(j, i, null, this);
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
				sort(INVENTORY_SORT_BY.SORT_ALPHABETICALLY, true);
			}
		});
		buttons.add(buttonSortAlphabetically);
		buttonsSort.add(buttonSortAlphabetically);

		buttonSortByNewest = new LevelButton(100f, 150f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"NEWEST", true, true, Color.BLACK, Color.WHITE);
		buttonSortByNewest.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_NEWEST, true);
			}
		});
		buttons.add(buttonSortByNewest);
		buttonsSort.add(buttonSortByNewest);

		buttonSortByFavourite = new LevelButton(100f, 200f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"FAVOURITES", true, true, Color.BLACK, Color.WHITE);
		buttonSortByFavourite.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_FAVOURITE, true);
			}
		});
		buttons.add(buttonSortByFavourite);
		buttonsSort.add(buttonSortByFavourite);

		buttonSortByValue = new LevelButton(100f, 250f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"VALUE", true, true, Color.BLACK, Color.WHITE);
		buttonSortByValue.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_VALUE, true);
			}
		});
		buttons.add(buttonSortByValue);
		buttonsSort.add(buttonSortByValue);

		buttonSortByTotalDamage = new LevelButton(100f, 300f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"DAMAGE", true, true, Color.BLACK, Color.WHITE);
		buttonSortByTotalDamage.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_TOTAL_DAMAGE, true);
			}
		});
		buttons.add(buttonSortByTotalDamage);
		buttonsSort.add(buttonSortByTotalDamage);

		buttonSortBySlashDamage = new LevelButton(100f, 350f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"SLASH", true, true, Color.BLACK, Color.WHITE);
		buttonSortBySlashDamage.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_SLASH_DAMAGE, true);
			}
		});
		buttons.add(buttonSortBySlashDamage);
		buttonsSort.add(buttonSortBySlashDamage);

		buttonFilterByAll = new LevelButton(300f, 50f, 100f, 30f, "end_turn_button.png", "end_turn_button.png", "ALL",
				true, true, Color.BLACK, Color.WHITE);
		buttonFilterByAll.setClickListener(new ClickListener() {
			@Override
			public void click() {
				filter(INVENTORY_FILTER_BY.FILTER_BY_ALL, false);
			}
		});
		buttons.add(buttonFilterByAll);
		buttonsFilter.add(buttonFilterByAll);

		buttonFilterByWeapon = new LevelButton(400f, 50f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"WEAPONS", true, true, Color.BLACK, Color.WHITE);
		buttonFilterByWeapon.setClickListener(new ClickListener() {
			@Override
			public void click() {
				filter(INVENTORY_FILTER_BY.FILTER_BY_WEAPON, false);
			}
		});
		buttons.add(buttonFilterByWeapon);
		buttonsFilter.add(buttonFilterByWeapon);

		buttonFilterByFood = new LevelButton(500f, 50f, 100f, 30f, "end_turn_button.png", "end_turn_button.png", "FOOD",
				true, true, Color.BLACK, Color.WHITE);
		buttonFilterByFood.setClickListener(new ClickListener() {
			@Override
			public void click() {
				filter(INVENTORY_FILTER_BY.FILTER_BY_FOOD, false);
			}
		});
		buttons.add(buttonFilterByFood);
		buttonsFilter.add(buttonFilterByFood);
		this.isOpen = true;
		if (!Game.level.openInventories.contains(this))
			Game.level.openInventories.add(this);
	}

	public void close() {
		this.isOpen = false;
		if (Game.level.openInventories.contains(this))
			Game.level.openInventories.remove(this);
	}

	public void sort(INVENTORY_SORT_BY inventorySortBy, boolean filterFirst) {

		for (Button button : buttonsSort) {
			button.down = false;
		}

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

		if (filterFirst) {
			filter(this.inventoryFilterBy, false);
			return;
		}

		Collections.sort(filteredGameObjects);
		matchGameObjectsToSquares();
	}

	public void filter(INVENTORY_FILTER_BY inventoryFilterBy, boolean temporary) {
		if (!temporary)
			Inventory.inventoryFilterBy = inventoryFilterBy;
		for (Button button : buttonsFilter)
			button.down = false;
		if (inventoryFilterBy == INVENTORY_FILTER_BY.FILTER_BY_ALL) {
		} else if (inventoryFilterBy == INVENTORY_FILTER_BY.FILTER_BY_WEAPON) {
		}
		filteredGameObjects.clear();
		if (inventoryFilterBy == INVENTORY_FILTER_BY.FILTER_BY_ALL) {
			buttonFilterByAll.down = true;
			filteredGameObjects.addAll(gameObjects);
		} else if (inventoryFilterBy == INVENTORY_FILTER_BY.FILTER_BY_WEAPON) {
			buttonFilterByWeapon.down = true;
			for (GameObject gameObject : gameObjects) {
				if (gameObject instanceof Weapon) {
					filteredGameObjects.add(gameObject);
				}
			}
		} else if (inventoryFilterBy == INVENTORY_FILTER_BY.FILTER_BY_FOOD) {
			buttonFilterByFood.down = true;
			for (GameObject gameObject : gameObjects) {
				if (gameObject instanceof Food) {
					filteredGameObjects.add(gameObject);
				}
			}
		} else if (inventoryFilterBy == INVENTORY_FILTER_BY.FILTER_BY_CONTAINER_FOR_LIQUIDS) {
			buttonFilterByFood.down = true;
			for (GameObject gameObject : gameObjects) {
				if (gameObject instanceof ContainerForLiquids) {
					filteredGameObjects.add(gameObject);
				}
			}
		}

		sort(Inventory.inventorySortBy, false);
	}

	public void postLoad1() {
		inventorySquares = new InventorySquare[widthInSquares][heightInSquares];
		for (int i = 0; i < inventorySquares[0].length; i++) {
			for (int j = 0; j < inventorySquares.length; j++) {
				inventorySquares[j][i] = new InventorySquare(i, j, null, this);
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
		// for (int i = 0; i < inventorySquares[0].length; i++) {
		// for (int j = 0; j < inventorySquares.length; j++) {
		// inventorySquares[j][i].loadImages();
		// }
		// }
	}

	public GameObject get(int i) {
		return gameObjects.get(i);
	}

	public void add(GameObject gameObject) {
		add(gameObject, -1);
	}

	public void add(GameObject gameObject, int index) {
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

			if (index != -1) {
				filteredGameObjects.remove(index);
				filteredGameObjects.add(index, gameObject);
			}
			matchGameObjectsToSquares();

		}
	}

	public int remove(GameObject gameObject) {
		int index = -1;
		if (gameObjects.contains(gameObject)) {

			gameObjects.remove(gameObject);
			gameObject.inventoryThatHoldsThisObject = null;
			if (parent != null)
				parent.inventoryChanged();
			// this.sort(inventorySortBy);
			if (filteredGameObjects.contains(gameObject)) {
				index = filteredGameObjects.indexOf(gameObject);
				filteredGameObjects.set(filteredGameObjects.indexOf(gameObject), null);
			}
			this.matchGameObjectsToSquares();
		}
		return index;
	}

	public void replace(GameObject out, GameObject in) {
		int index = this.remove(out);
		this.add(in, index);

	}

	public int size() {
		return gameObjects.size();
	}

	public ArrayList<GameObject> getGameObjects() {
		return gameObjects;
	}

	public void setGameObjects(ArrayList<GameObject> gameObjects) {
		this.gameObjects = gameObjects;
		this.sort(inventorySortBy, true);
		matchGameObjectsToSquares();
	}

	public void matchGameObjectsToSquares() {
		if (!isOpen)
			return;

		int index = 0;
		for (int i = 0; i < inventorySquares[0].length; i++) {
			for (int j = 0; j < inventorySquares.length; j++) {
				inventorySquares[j][i].gameObject = null;
				if (index < filteredGameObjects.size()) {
					inventorySquares[j][i].gameObject = filteredGameObjects.get(index);
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
			this.inventorySquareMouseIsOver.drawAction();
		}

		// buttons
		if (inventoryMode == INVENTORY_MODE.MODE_NORMAL) {
			for (Button button : buttonsFilter) {
				button.draw();
			}
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_FILL) {
			TextUtils.printTextWithImages(
					new Object[] { new StringWithColor("Please Select a Container to Fill", Color.WHITE) }, 100f, 8f,
					300f, true);
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_POUR) {
			TextUtils.printTextWithImages(
					new Object[] { new StringWithColor("Please Select a Container to Pour Out", Color.WHITE) }, 100f,
					8f, 300f, true);
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_DROP) {
			TextUtils.printTextWithImages(
					new Object[] { new StringWithColor("Please Select an Item to Drop", Color.WHITE) }, 100f, 8f, 300f,
					true);
			for (Button button : buttonsFilter) {
				button.draw();
			}
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_GIVE) {
			TextUtils.printTextWithImages(
					new Object[] { new StringWithColor("Please Select an Item to Give", Color.WHITE) }, 100f, 8f, 300f,
					true);
			for (Button button : buttonsFilter) {
				button.draw();
			}
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_THROW) {
			TextUtils.printTextWithImages(
					new Object[] { new StringWithColor("Please Select an Item to Throw", Color.WHITE) }, 100f, 8f, 300f,
					true);
			for (Button button : buttonsFilter) {
				button.draw();
			}
		}

		for (Button button : buttonsSort) {
			button.draw();
		}

		// Actor
		int actorPositionXInPixels = 650;
		int actorPositionYInPixels = 250;
		float alpha = 1.0f;
		TextureUtils.drawTexture(Game.level.player.imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
				actorPositionXInPixels + Game.level.player.width * 2,
				actorPositionYInPixels + Game.level.player.height * 2);

		GameObject gameObjectMouseIsOver = null;

		GameObject gameObjectToDrawInPlayersHand = null;
		GameObject gameObjectToDrawOnPlayersHead = Game.level.player.helmet;
		GameObject gameObjectToDrawOnPlayersBody = Game.level.player.bodyArmor;
		GameObject gameObjectToDrawOnPlayersLegs = Game.level.player.legArmor;

		// if (Game.squareMouseIsOver != null && Game.squareMouseIsOver
		// instanceof InventorySquare) {
		// // Preview weapon
		// gameObjectMouseIsOver = ((InventorySquare)
		// Game.squareMouseIsOver).gameObject;
		//
		// if (gameObjectMouseIsOver instanceof Helmet) {
		// gameObjectToDrawOnPlayersHead = gameObjectMouseIsOver;
		// } else if (gameObjectMouseIsOver instanceof BodyArmor) {
		// gameObjectToDrawOnPlayersBody = gameObjectMouseIsOver;
		// } else if (gameObjectMouseIsOver instanceof LegArmor) {
		// gameObjectToDrawOnPlayersLegs = gameObjectMouseIsOver;
		// } else {
		// gameObjectToDrawInPlayersHand = gameObjectMouseIsOver;
		// }
		//
		// }

		if (gameObjectToDrawInPlayersHand == null) {
			gameObjectToDrawInPlayersHand = Game.level.player.equipped;
		}

		// Object to draw player holding
		if (gameObjectToDrawInPlayersHand != null) {
			int weaponPositionXInPixels = (int) (actorPositionXInPixels
					+ (Game.level.player.handAnchorX - gameObjectToDrawInPlayersHand.anchorX) * 2);
			int weaponPositionYInPixels = (int) (actorPositionYInPixels
					+ (Game.level.player.handAnchorY - gameObjectToDrawInPlayersHand.anchorY) * 2);
			TextureUtils.drawTexture(gameObjectToDrawInPlayersHand.imageTexture, alpha, weaponPositionXInPixels,
					weaponPositionYInPixels, weaponPositionXInPixels + gameObjectToDrawInPlayersHand.width * 2,
					weaponPositionYInPixels + gameObjectToDrawInPlayersHand.height * 2);
		}

		// Helmet
		if (gameObjectToDrawOnPlayersHead != null) {
			int helmetPositionXInPixels = (int) (actorPositionXInPixels
					+ (Game.level.player.headAnchorX - gameObjectToDrawOnPlayersHead.anchorX) * 2);
			int helmetPositionYInPixels = (int) (actorPositionYInPixels
					+ (Game.level.player.headAnchorY - gameObjectToDrawOnPlayersHead.anchorY) * 2);
			TextureUtils.drawTexture(gameObjectToDrawOnPlayersHead.imageTexture, alpha, helmetPositionXInPixels,
					helmetPositionYInPixels, helmetPositionXInPixels + gameObjectToDrawOnPlayersHead.width * 2,
					helmetPositionYInPixels + gameObjectToDrawOnPlayersHead.height * 2);
		} else if (Game.level.player.hairImageTexture != null) {
			int bodyArmorPositionXInPixels = (int) (actorPositionXInPixels + (Game.level.player.bodyAnchorX - 0) * 2);
			int bodyArmorPositionYInPixels = (int) (actorPositionYInPixels + (Game.level.player.bodyAnchorY - 0) * 2);
			TextureUtils.drawTexture(Game.level.player.hairImageTexture, alpha, bodyArmorPositionXInPixels,
					bodyArmorPositionYInPixels,
					bodyArmorPositionXInPixels + Game.level.player.hairImageTexture.getWidth() * 2,
					bodyArmorPositionYInPixels + Game.level.player.hairImageTexture.getHeight() * 2);
		}

		// Body Armor
		if (gameObjectToDrawOnPlayersBody != null) {
			int bodyArmorPositionXInPixels = (int) (actorPositionXInPixels
					+ (Game.level.player.bodyAnchorX - gameObjectToDrawOnPlayersBody.anchorX) * 2);
			int bodyArmorPositionYInPixels = (int) (actorPositionYInPixels
					+ (Game.level.player.bodyAnchorY - gameObjectToDrawOnPlayersBody.anchorY) * 2);
			TextureUtils.drawTexture(gameObjectToDrawOnPlayersBody.imageTexture, alpha, bodyArmorPositionXInPixels,
					bodyArmorPositionYInPixels, bodyArmorPositionXInPixels + gameObjectToDrawOnPlayersBody.width * 2,
					bodyArmorPositionYInPixels + gameObjectToDrawOnPlayersBody.height * 2);
		}

		// Leg Armor
		if (gameObjectToDrawOnPlayersLegs != null) {
			int legArmorPositionXInPixels = (int) (actorPositionXInPixels
					+ (Game.level.player.legsAnchorX - gameObjectToDrawOnPlayersLegs.anchorX) * 2);
			int legArmorPositionYInPixels = (int) (actorPositionYInPixels
					+ (Game.level.player.legsAnchorY - gameObjectToDrawOnPlayersLegs.anchorY) * 2);
			TextureUtils.drawTexture(gameObjectToDrawOnPlayersLegs.imageTexture, alpha, legArmorPositionXInPixels,
					legArmorPositionYInPixels, legArmorPositionXInPixels + gameObjectToDrawOnPlayersLegs.width * 2,
					legArmorPositionYInPixels + gameObjectToDrawOnPlayersLegs.height * 2);
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
	}

	private void selectGameObject(GameObject gameObject) {
		selectedGameObject = gameObject;
		inventoryState = INVENTORY_STATE.MOVEABLE_OBJECT_SELECTED;
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

	public void setMode(INVENTORY_MODE mode) {
		inventoryMode = mode;
	}

}
