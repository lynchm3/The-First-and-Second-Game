package com.marklynch.level.constructs.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Door;
import com.marklynch.objects.Food;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.WaterSource;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionLootAll;
import com.marklynch.objects.actions.ActionTakeSpecificItem;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Inventory {

	public static boolean sortBackwards = false;
	public int widthInSquares = 5;
	public int heightInSquares = 6;
	public transient InventorySquare[][] inventorySquares = new InventorySquare[widthInSquares][heightInSquares];
	public ArrayList<GameObject> gameObjects = new ArrayList<GameObject>(widthInSquares * heightInSquares);
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
		MODE_NORMAL, MODE_SELECT_ITEM_TO_FILL, MODE_SELECT_ITEM_TO_DROP, MODE_SELECT_ITEM_TO_THROW, MODE_SELECT_ITEM_TO_GIVE, MODE_SELECT_ITEM_TO_POUR, MODE_SELECT_MAP_MARKER, MODE_TRADE, MODE_LOOT
	}

	public static transient INVENTORY_MODE inventoryMode = INVENTORY_MODE.MODE_NORMAL;

	private transient boolean isOpen = false;
	public transient float x = 500;
	transient float y = 100;
	transient float sortButtonX = 400;
	transient float sortButtonWidth = 100;
	transient int actorX = 100;
	transient int otherInventoryGameObjectX = 1250;
	transient float width = widthInSquares * Game.INVENTORY_SQUARE_WIDTH;
	transient float height = heightInSquares * Game.INVENTORY_SQUARE_HEIGHT;
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

	// Close button
	static LevelButton buttonClose;

	// Loot all button
	public static LevelButton buttonLootAll;

	public static ArrayList<Button> buttons;
	public static ArrayList<Button> buttonsSort;
	public static ArrayList<Button> buttonsFilter;

	public InventoryParent parent;
	public GroundDisplay groundDisplay;
	public Inventory otherInventory;

	public static WaterSource waterSource;
	public static Square square;
	public static Object target;

	public static Texture textureUp;
	public static Texture textureDown;

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

		buttonSortAlphabetically = new LevelButton(sortButtonX, 100f, sortButtonWidth, 30f, "end_turn_button.png",
				"end_turn_button.png", "SORT A-Z", true, true, Color.BLACK, Color.WHITE);
		buttonSortAlphabetically.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_ALPHABETICALLY, false, true);
			}
		});
		buttonsSort.add(buttonSortAlphabetically);

		buttonSortByNewest = new LevelButton(sortButtonX, 150f, sortButtonWidth, 30f, "end_turn_button.png",
				"end_turn_button.png", "NEWEST", true, true, Color.BLACK, Color.WHITE);
		buttonSortByNewest.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_NEWEST, false, true);
			}
		});
		buttonsSort.add(buttonSortByNewest);

		buttonSortByFavourite = new LevelButton(sortButtonX, 200f, sortButtonWidth, 30f, "end_turn_button.png",
				"end_turn_button.png", "FAVOURITES", true, true, Color.BLACK, Color.WHITE);
		buttonSortByFavourite.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_FAVOURITE, false, true);
			}
		});
		buttonsSort.add(buttonSortByFavourite);

		buttonSortByValue = new LevelButton(sortButtonX, 250f, sortButtonWidth, 30f, "end_turn_button.png",
				"end_turn_button.png", "VALUE", true, true, Color.BLACK, Color.WHITE);
		buttonSortByValue.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_VALUE, false, true);
			}
		});
		buttonsSort.add(buttonSortByValue);

		buttonSortByTotalDamage = new LevelButton(sortButtonX, 300f, sortButtonWidth, 30f, "end_turn_button.png",
				"end_turn_button.png", "DAMAGE", true, true, Color.BLACK, Color.WHITE);
		buttonSortByTotalDamage.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_TOTAL_DAMAGE, false, true);
			}
		});
		buttonsSort.add(buttonSortByTotalDamage);

		buttonSortBySlashDamage = new LevelButton(sortButtonX, 350f, sortButtonWidth, 30f, "end_turn_button.png",
				"end_turn_button.png", "SLASH", true, true, Color.BLACK, Color.WHITE);
		buttonSortBySlashDamage.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_SLASH_DAMAGE, false, true);
			}
		});
		buttonsSort.add(buttonSortBySlashDamage);

		buttonFilterByAll = new LevelButton(sortButtonX + 100f, 50f, 100f, 30f, "end_turn_button.png",
				"end_turn_button.png", "ALL", true, true, Color.BLACK, Color.WHITE);
		buttonFilterByAll.setClickListener(new ClickListener() {
			@Override
			public void click() {
				filter(INVENTORY_FILTER_BY.FILTER_BY_ALL, false);
			}
		});
		buttonsFilter.add(buttonFilterByAll);

		buttonFilterByWeapon = new LevelButton(sortButtonX + 200f, 50f, 100f, 30f, "end_turn_button.png",
				"end_turn_button.png", "WEAPONS", true, true, Color.BLACK, Color.WHITE);
		buttonFilterByWeapon.setClickListener(new ClickListener() {
			@Override
			public void click() {
				filter(INVENTORY_FILTER_BY.FILTER_BY_WEAPON, false);
			}
		});
		buttonsFilter.add(buttonFilterByWeapon);

		buttonFilterByFood = new LevelButton(sortButtonX + 300f, 50f, 100f, 30f, "end_turn_button.png",
				"end_turn_button.png", "FOOD", true, true, Color.BLACK, Color.WHITE);
		buttonFilterByFood.setClickListener(new ClickListener() {
			@Override
			public void click() {
				filter(INVENTORY_FILTER_BY.FILTER_BY_FOOD, false);
			}
		});
		buttonsFilter.add(buttonFilterByFood);

		buttonLootAll = new LevelButton(900f, 500f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"LOOT ALL [A]", true, true, Color.BLACK, Color.WHITE);
		buttonLootAll.setClickListener(new ClickListener() {
			@Override
			public void click() {
				if (groundDisplay == null) {
					new ActionLootAll(Game.level.player, (GameObject) target).perform();
				} else {
					ArrayList<Action> actionsToPerform = new ArrayList<Action>();
					for (Square square : groundDisplay.squares) {
						for (GameObject gameObject : square.inventory.gameObjects) {
							if (gameObject.fitsInInventory) {
								Action action = new ActionTakeSpecificItem(Game.level.player, square, gameObject);
								if (action.legal) {
									actionsToPerform.add(action);
								}
							}
						}
					}
					for (Action action : actionsToPerform) {
						action.perform();
					}
					groundDisplay.refreshGameObjects();
				}
			}
		});

		if (inventoryMode == INVENTORY_MODE.MODE_NORMAL || inventoryMode == INVENTORY_MODE.MODE_LOOT) {
			buttons.add(buttonLootAll);
		}

		buttonClose = new LevelButton(Game.halfWindowWidth - 25f, 100f, 100f, 30f, "end_turn_button.png",
				"end_turn_button.png", "CLOSE [I]", true, false, Color.BLACK, Color.WHITE);
		buttonClose.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.level.openCloseInventory();
			}
		});
		buttons.add(buttonClose);

		if (inventoryMode == INVENTORY_MODE.MODE_NORMAL || inventoryMode == INVENTORY_MODE.MODE_LOOT
				|| inventoryMode == INVENTORY_MODE.MODE_TRADE
				|| inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_DROP
				|| inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_GIVE
				|| inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_THROW) {
			buttons.addAll(buttonsFilter);
			buttons.addAll(buttonsSort);
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_FILL
				|| inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_POUR) {
			buttons.addAll(buttonsSort);
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_MAP_MARKER) {

		}

		this.isOpen = true;
		if (!Game.level.openInventories.contains(this))
			Game.level.openInventories.add(this);

		this.groundDisplay = null;
		if (inventoryMode == INVENTORY_MODE.MODE_NORMAL)
			this.groundDisplay = new GroundDisplay(900, 100);

		if (inventoryMode == INVENTORY_MODE.MODE_TRADE || inventoryMode == INVENTORY_MODE.MODE_LOOT) {
			otherInventory.isOpen = true;
			for (int i = 0; i < otherInventory.inventorySquares[0].length; i++) {
				for (int j = 0; j < otherInventory.inventorySquares.length; j++) {
					otherInventory.inventorySquares[j][i] = new InventorySquare(j, i, null, otherInventory);
				}
			}
		}
	}

	public void close() {
		this.isOpen = false;
		if (Game.level.openInventories.contains(this))
			Game.level.openInventories.remove(this);
		this.inventorySquares = new InventorySquare[widthInSquares][heightInSquares];
		this.groundDisplay = null;
		if (this.otherInventory != null) {
			otherInventory.isOpen = false;
			this.otherInventory.inventorySquares = new InventorySquare[this.otherInventory.widthInSquares][this.otherInventory.heightInSquares];
			this.otherInventory = null;
		}
	}

	public void sort(INVENTORY_SORT_BY inventorySortBy, boolean filterFirst, boolean fromSortButtonPress) {

		if (otherInventory != null) {
			otherInventory.sort(inventorySortBy, filterFirst, fromSortButtonPress);
		}

		Button selectedSortButton = null;

		if (inventorySortBy == INVENTORY_SORT_BY.SORT_ALPHABETICALLY) {
			selectedSortButton = buttonSortAlphabetically;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_NEWEST) {
			selectedSortButton = buttonSortByNewest;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_VALUE) {
			selectedSortButton = buttonSortByValue;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_FAVOURITE) {
			selectedSortButton = buttonSortByFavourite;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_TOTAL_DAMAGE) {
			selectedSortButton = buttonSortByTotalDamage;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_SLASH_DAMAGE) {
			selectedSortButton = buttonSortBySlashDamage;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_BLUNT_DAMAGE) {

		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_PIERCE_DAMAGE) {

		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_FIRE_DAMAGE) {

		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_WATER_DAMAGE) {

		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_POISON_DAMAGE) {

		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_ELECTRICAL_DAMAGE) {

		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_MAX_RANGE) {

		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_MIN_RANGE) {

		}

		if (fromSortButtonPress) {
			if (selectedSortButton.down == true) {
				sortBackwards = !sortBackwards;
			} else {
				sortBackwards = false;
			}
		}

		for (Button button : buttonsSort) {
			button.down = false;
			button.x = this.sortButtonX;
			button.width = this.sortButtonWidth;
		}

		selectedSortButton.down = true;
		selectedSortButton.x -= 20;
		selectedSortButton.width += 20;

		Inventory.inventorySortBy = inventorySortBy;

		if (filterFirst) {
			filter(this.inventoryFilterBy, false);
			return;
		}

		Collections.sort(filteredGameObjects);
		if (sortBackwards)
			Collections.reverse(filteredGameObjects);

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

		if (otherInventory != null) {
			otherInventory.filter(inventoryFilterBy, temporary);
		}

		sort(Inventory.inventorySortBy, false, false);
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

	public static void loadStaticImages() {
		textureUp = ResourceUtils.getGlobalImage("up.png");
		textureDown = ResourceUtils.getGlobalImage("down.png");
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
				Inventory oldInventory = gameObject.inventoryThatHoldsThisObject;
				oldInventory.remove(gameObject);
				oldInventory.matchGameObjectsToSquares();
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
			} else {
				filteredGameObjects.add(gameObject);
			}
			matchGameObjectsToSquares();
			if (groundDisplay != null)
				groundDisplay.refreshGameObjects();

		}
	}

	public int remove(GameObject gameObject) {
		int index = -1;
		if (gameObjects.contains(gameObject)) {

			if (this.parent instanceof Actor) {
				Actor actor = (Actor) parent;
				if (actor.equipped == gameObject)
					actor.equipped = null;
			}

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
			if (groundDisplay != null)
				groundDisplay.refreshGameObjects();
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
			}
		}
	}

	// public void matchGameObjectsToSquares() {
	// if (!isOpen)
	// return;
	//
	// int index = 0;
	// if (sortBackwards)
	// index = filteredGameObjects.size() - 1;
	//
	// for (int i = 0; i < inventorySquares[0].length; i++) {
	// for (int j = 0; j < inventorySquares.length; j++) {
	// inventorySquares[j][i].gameObject = null;
	//
	// if (!sortBackwards) {
	//
	// if (index < filteredGameObjects.size()) {
	// inventorySquares[j][i].gameObject = filteredGameObjects.get(index);
	// index++;
	// }
	// } else {
	// if (index >= 0) {
	// inventorySquares[j][i].gameObject = filteredGameObjects.get(index);
	// index--;
	// }
	// }
	// }
	// }
	// }

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

	public boolean isPassable(GameObject forGameObject) {

		if (forGameObject instanceof Actor)
			return isPassable((Actor) forGameObject);

		return this.canShareSquare();
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

	public final static Color backgroundColor = new Color(0f, 0f, 0f, 1f);

	@SuppressWarnings("unchecked")
	public void drawStaticUI() {

		// Black cover
		QuadUtils.drawQuad(backgroundColor, 0, Game.windowWidth, 0, Game.windowHeight);

		// sqrs
		drawSquares();

		// buttons
		for (Button button : buttons) {
			button.draw();
		}

		// Up / down icon on active sort button
		for (Button sortButton : buttonsSort) {
			if (sortButton.down) {
				if (sortBackwards)
					TextureUtils.drawTexture(textureUp, sortButton.x - 8, sortButton.y - 8, sortButton.x + 8,
							sortButton.y + 8);
				else
					TextureUtils.drawTexture(textureDown, sortButton.x - 8, sortButton.y - 8, sortButton.x + 8,
							sortButton.y + 8);
			}
		}

		// text
		if (inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_FILL) {
			TextUtils.printTextWithImages(100f, 8f, 300f, true,
					new Object[] { new StringWithColor("Please Select a Container to Fill", Color.WHITE) });
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_POUR) {
			TextUtils.printTextWithImages(100f, 8f, 300f, true,
					new Object[] { new StringWithColor("Please Select a Container to Pour Out", Color.WHITE) });
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_MAP_MARKER) {
			TextUtils.printTextWithImages(100f, 8f, 300f, true,
					new Object[] { new StringWithColor("Please Select a Map Marker", Color.WHITE) });
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_DROP) {
			TextUtils.printTextWithImages(100f, 8f, 300f, true,
					new Object[] { new StringWithColor("Please Select an Item to Drop", Color.WHITE) });
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_GIVE) {
			TextUtils.printTextWithImages(100f, 8f, 300f, true,
					new Object[] { new StringWithColor("Please Select an Item to Give", Color.WHITE) });
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_THROW) {
			TextUtils.printTextWithImages(100f, 8f, 300f, true,
					new Object[] { new StringWithColor("Please Select an Item to Throw", Color.WHITE) });
		}

		// Actor
		int actorPositionXInPixels = this.actorX;
		int actorPositionYInPixels = 100;
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

		if (groundDisplay != null) {
			groundDisplay.drawStaticUI();
		}

		// Other Gameobject / actor inventory squares
		if (otherInventory != null) {
			otherInventory.drawSquares();
		}

		// Other Gameobject / actor
		if (otherInventory != null) {
			// Actor
			GameObject otherGameObject = (GameObject) target;
			TextureUtils.drawTexture(otherGameObject.imageTexture, alpha, otherInventoryGameObjectX,
					actorPositionYInPixels, otherInventoryGameObjectX + otherGameObject.width * 2,
					actorPositionYInPixels + otherGameObject.height * 2);
		}

		// cursor
		if (this.inventorySquareMouseIsOver != null && Game.buttonHoveringOver == null) {
			this.inventorySquareMouseIsOver.drawCursor();
			this.inventorySquareMouseIsOver.drawAction();
		}

	}

	public void drawSquares() {

		for (int i = 0; i < inventorySquares[0].length; i++) {
			for (int j = 0; j < inventorySquares.length; j++) {

				inventorySquares[j][i].drawStaticUI();

			}
		}
	}

	public boolean isOpen() {
		return isOpen;
	}

	public InventorySquare getInventorySquareMouseIsOver(float mouseXInPixels, float mouseYInPixels) {

		// Inventory sqr
		float offsetX = x;
		float offsetY = y;
		float scroll = 0;

		float mouseXInSquares = (((mouseXInPixels - offsetX) / Game.INVENTORY_SQUARE_WIDTH));
		float mouseYInSquares = ((Game.windowHeight - mouseYInPixels - offsetY - scroll)
				/ Game.INVENTORY_SQUARE_HEIGHT);

		if (mouseXInSquares >= 0 && mouseXInSquares < inventorySquares.length && mouseYInSquares >= 0
				&& mouseYInSquares < inventorySquares[0].length) {

			return this.inventorySquares[(int) mouseXInSquares][(int) mouseYInSquares];
		}

		// Ground display sqr
		if (groundDisplay != null) {
			GroundDisplaySquare groundDisplaySquareMouseIsOver = groundDisplay
					.getGroundDisplaySquareMouseIsOver(mouseXInPixels, mouseYInPixels);
			if (groundDisplaySquareMouseIsOver != null) {
				return groundDisplaySquareMouseIsOver; // This is working great
														// 23/08/2017
			}
		}

		if (otherInventory != null) {
			InventorySquare inventorySquareMouseIsOver = otherInventory.getInventorySquareMouseIsOver(mouseXInPixels,
					mouseYInPixels);
			if (inventorySquareMouseIsOver != null) {
				return inventorySquareMouseIsOver; // This is working great
													// 23/08/2017
			}
		}

		return null;
	}

	public void setSquareMouseHoveringOver(InventorySquare squareMouseIsOver) {
		this.inventorySquareMouseIsOver = squareMouseIsOver;

	}

	public void setMode(INVENTORY_MODE mode) {
		inventoryMode = mode;
	}

}
