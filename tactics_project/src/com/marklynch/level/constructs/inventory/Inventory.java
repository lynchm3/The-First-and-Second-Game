package com.marklynch.level.constructs.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.objects.Door;
import com.marklynch.objects.Food;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Gold;
import com.marklynch.objects.Junk;
import com.marklynch.objects.WaterSource;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionTakeItems;
import com.marklynch.objects.actions.VariableQtyAction;
import com.marklynch.objects.tools.ContainerForLiquids;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Human;
import com.marklynch.objects.units.NonHuman;
import com.marklynch.objects.units.Trader;
import com.marklynch.objects.weapons.Armor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLogger;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.TextBox;
import com.marklynch.ui.TextBoxHolder;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.popups.Notification;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class Inventory implements Draggable, Scrollable, TextBoxHolder {

	public Animation animation;

	public enum INVENTORY_STATE {
		DEFAULT, ADD_OBJECT, MOVEABLE_OBJECT_SELECTED, SETTINGS_CHANGE
	}

	public transient INVENTORY_STATE inventoryState = INVENTORY_STATE.DEFAULT;

	public enum INVENTORY_SORT_BY {
		SORT_ALPHABETICALLY, SORT_BY_NEWEST, SORT_BY_VALUE, SORT_BY_FAVOURITE, SORT_BY_TOTAL_DAMAGE, SORT_BY_SLASH_DAMAGE, SORT_BY_BLUNT_DAMAGE, SORT_BY_PIERCE_DAMAGE, SORT_BY_FIRE_DAMAGE, SORT_BY_WATER_DAMAGE, SORT_BY_POISON_DAMAGE, SORT_BY_ELECTRICAL_DAMAGE, SORT_BY_BLEEDING_DAMAGE, SORT_BY_HEALING, SORT_BY_MAX_RANGE, SORT_BY_MIN_RANGE
	}

	public static transient INVENTORY_SORT_BY inventorySortBy = INVENTORY_SORT_BY.SORT_BY_NEWEST;

	public enum INVENTORY_FILTER_BY {
		FILTER_BY_ALL, FILTER_BY_WEAPON, FILTER_BY_FOOD, FILTER_BY_CONTAINER_FOR_LIQUIDS
	}

	public static transient INVENTORY_FILTER_BY inventoryFilterBy = INVENTORY_FILTER_BY.FILTER_BY_ALL;
	private static INVENTORY_FILTER_BY lastInventoryFilterBy = null;

	public enum INVENTORY_MODE {
		MODE_NORMAL, MODE_SELECT_ITEM_TO_FILL, MODE_SELECT_ITEM_TO_DROP, MODE_SELECT_ITEM_TO_THROW, MODE_SELECT_ITEM_TO_GIVE, MODE_SELECT_ITEM_TO_POUR, MODE_SELECT_MAP_MARKER, MODE_TRADE, MODE_LOOT
	}

	public static transient INVENTORY_MODE inventoryMode = INVENTORY_MODE.MODE_NORMAL;

	public static boolean sortBackwards = false;
	public int squareGridWidthInSquares = 5;
	public transient ArrayList<InventorySquare> inventorySquares = new ArrayList<InventorySquare>();
	public ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	protected ArrayList<GameObject> filteredGameObjects = new ArrayList<GameObject>();

	private transient boolean isOpen = false;
	public transient float squaresX = 500;
	transient final static float squaresBaseY = 100;
	public final static float inventoryNamesY = 30f;

	transient float squaresY = 100;
	transient float sortButtonX = 400;
	transient float sortButtonWidth = 100;
	transient int actorX = 100;
	transient int actorWidth = 256;
	transient static int bottomBorderHeight = 384;
	transient static int topBorderHeight = 100;
	transient InventorySquare inventorySquareMouseIsOver;

	// Sort buttons
	static LevelButton selectedSortButton;
	public static String selectedSortButtonString;
	public static int selectedSortButtonLength;

	static LevelButton buttonSortAlphabetically;
	public final static String stringSortAlphabetically = "A - Z";
	public final static int lengthSortAlphabetically = Game.smallFont.getWidth(stringSortAlphabetically);
	static LevelButton buttonSortByNewest;
	public final static String stringSortByNewest = "NEW";
	public final static int lengthSortByNewest = Game.smallFont.getWidth(stringSortByNewest);
	static LevelButton buttonSortByFavourite;
	public final static String stringSortByFavourite = "FAV";
	public final static int lengthSortByFavourite = Game.smallFont.getWidth(stringSortByFavourite);
	static LevelButton buttonSortByValue;
	public final static String stringSortByValue = "VALUE";
	public final static int lengthSortByValue = Game.smallFont.getWidth(stringSortByValue);
	static LevelButton buttonSortByTotalDamage;
	public final static String stringSortByTotalDamage = "DMG";
	public final static int lengthSortByTotalDamage = Game.smallFont.getWidth(stringSortByTotalDamage);
	static LevelButton buttonSortBySlashDamage;
	public final static String stringSortBySlashDamage = "SLASH DMG";
	public final static int lengthSortBySlashDamage = Game.smallFont.getWidth(stringSortBySlashDamage);

	// Filter buttons
	static LevelButton buttonFilterByAll;
	static LevelButton buttonFilterByWeapon;
	static LevelButton buttonFilterByFood;

	// Empty text
	public static final String stringEmpty = "Empty";
	public static final int lengthEmpty = Game.smallFont.getWidth(stringEmpty);

	// SHIFT text
	String stringShiftDrop = "[SHIFT] Drop";
	int lengthShiftDrop = Game.smallFont.getWidth(stringShiftDrop);
	String stringShiftPut = "[SHIFT] Put";
	int lengthShiftPut = Game.smallFont.getWidth(stringShiftPut);
	String stringShiftEquip = "[SHIFT] Equip";
	int lengthShiftEquip = Game.smallFont.getWidth(stringShiftEquip);

	float textShiftX = 0;
	float textShiftY = 0;
	float textOtherShiftX = 0;

	// [ENTER] / Search text
	String stringEnterSearch = "[ENTER] Search";
	int lengthEnterSearch = Game.smallFont.getWidth(stringEnterSearch);

	// [<-] / Clear search text
	String stringClearSearch = "[<-] Clear";
	int lengthClearSearch = Game.smallFont.getWidth(stringClearSearch);

	// "Search:" tag
	String stringSearch = "Search:";
	int lengthSearch = Game.smallFont.getWidth(stringSearch);

	// Color beind inventory squares
	public final static Color inventoryAreaColor = new Color(1f, 1f, 1f, 0.25f);

	// Close button
	static LevelButton buttonClose;

	// Loot all button
	public static LevelButton buttonLootAll;

	// Search buttons
	public static LevelButton buttonSearch;
	public static LevelButton buttonClearSearch;

	// Quick sell button
	public static LevelButton buttonQuickSell;

	public static ArrayList<Button> buttons;
	public static ArrayList<Button> buttonsSort;
	public static ArrayList<Button> buttonsFilter;

	public InventoryParent parent;
	public GroundDisplay groundDisplay;
	public Inventory otherInventory;
	public static ComparisonDisplay weaponComparisonDisplay;

	public static float squaresAreaWidth;
	public static float squaresAreaHeight;
	public float totalSquaresHeight;

	public static WaterSource waterSource;
	// public static InventorySquare square;
	public static Object target;

	public static Texture textureUp;
	public static Texture textureDown;
	public static Texture textureCorner;
	public static Texture textureFilter;
	public static Texture textureBag;
	public static Texture textureStar;
	public static Texture textureGold;

	public TextBox textBoxSearch = new TextBox(this, "", "Enter Search Term", lengthSearch + 16, 0, TextBox.TYPE.ALL);
	public TextBox textBoxQty = new TextBox(this, "", "Enter Qty", 300, 300, TextBox.TYPE.NUMERIC);

	public Inventory(GameObject... gameObjects) {
		for (GameObject gameObject : gameObjects) {
			add(gameObject);
		}

		if (weaponComparisonDisplay == null)
			weaponComparisonDisplay = new ComparisonDisplay();

	}

	public void open() {

		if (Level.activeTextBox == textBoxSearch) {
			Level.activeTextBox = null;
		}
		// Game.level.player.inventory.textBoxSearch.clearText();
		buttons = new ArrayList<Button>();
		buttonsSort = new ArrayList<Button>();
		buttonsFilter = new ArrayList<Button>();

		buttonSortAlphabetically = new LevelButton(sortButtonX, 100f, sortButtonWidth, 30f, "end_turn_button.png",
				"end_turn_button.png", stringSortAlphabetically, true, true, Color.BLACK, Color.WHITE,
				"Sort items in inventory alphabetically");
		buttonSortAlphabetically.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_ALPHABETICALLY, true, true);
			}
		});
		buttonsSort.add(buttonSortAlphabetically);

		buttonSortByNewest = new LevelButton(sortButtonX, 150f, sortButtonWidth, 30f, "end_turn_button.png",
				"end_turn_button.png", stringSortByNewest, true, true, Color.BLACK, Color.WHITE,
				"Sort items in inventory by the order they were acquired");
		buttonSortByNewest.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_NEWEST, true, true);
			}
		});
		buttonsSort.add(buttonSortByNewest);

		buttonSortByFavourite = new LevelButton(sortButtonX, 200f, sortButtonWidth, 30f, "end_turn_button.png",
				"end_turn_button.png", stringSortByFavourite, true, true, Color.BLACK, Color.WHITE,
				"Sort items in inventory by starredness");
		buttonSortByFavourite.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_FAVOURITE, true, true);
			}
		});
		buttonsSort.add(buttonSortByFavourite);

		buttonSortByValue = new LevelButton(sortButtonX, 250f, sortButtonWidth, 30f, "end_turn_button.png",
				"end_turn_button.png", stringSortByValue, true, true, Color.BLACK, Color.WHITE,
				"Sort items in inventory by value");
		buttonSortByValue.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_VALUE, true, true);
			}
		});
		buttonsSort.add(buttonSortByValue);

		buttonSortByTotalDamage = new LevelButton(sortButtonX, 300f, sortButtonWidth, 30f, "end_turn_button.png",
				"end_turn_button.png", stringSortByTotalDamage, true, true, Color.BLACK, Color.WHITE,
				"Sort items in inventory by total damage");
		buttonSortByTotalDamage.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_TOTAL_DAMAGE, true, true);
			}
		});
		buttonsSort.add(buttonSortByTotalDamage);

		buttonSortBySlashDamage = new LevelButton(sortButtonX, 350f, sortButtonWidth, 30f, "end_turn_button.png",
				"end_turn_button.png", stringSortBySlashDamage, true, true, Color.BLACK, Color.WHITE,
				"Sort items in inventory by slash damage");
		buttonSortBySlashDamage.setClickListener(new ClickListener() {
			@Override
			public void click() {
				sort(INVENTORY_SORT_BY.SORT_BY_SLASH_DAMAGE, true, true);
			}
		});
		buttonsSort.add(buttonSortBySlashDamage);

		buttonFilterByAll = new LevelButton(sortButtonX + 100f, squaresY - 30, 100f, 30f, "end_turn_button.png",
				"end_turn_button.png", "ALL", true, true, Color.BLACK, Color.WHITE, "Show all items in inventory");
		buttonFilterByAll.setClickListener(new ClickListener() {
			@Override
			public void click() {
				filter(INVENTORY_FILTER_BY.FILTER_BY_ALL, false);
			}
		});
		buttonsFilter.add(buttonFilterByAll);

		buttonFilterByWeapon = new LevelButton(sortButtonX + 200f, squaresY - 30, 100f, 30f, "end_turn_button.png",
				"end_turn_button.png", "WEAPONS", true, true, Color.BLACK, Color.WHITE,
				"Show only weapons in inventory");
		buttonFilterByWeapon.setClickListener(new ClickListener() {
			@Override
			public void click() {
				filter(INVENTORY_FILTER_BY.FILTER_BY_WEAPON, false);
			}
		});
		buttonsFilter.add(buttonFilterByWeapon);

		buttonFilterByFood = new LevelButton(sortButtonX + 300f, squaresY - 30, 100f, 30f, "end_turn_button.png",
				"end_turn_button.png", "FOOD", true, true, Color.BLACK, Color.WHITE,
				"Show only food items in inventory");
		buttonFilterByFood.setClickListener(new ClickListener() {
			@Override
			public void click() {
				filter(INVENTORY_FILTER_BY.FILTER_BY_FOOD, false);
			}
		});
		buttonsFilter.add(buttonFilterByFood);

		buttonLootAll = new LevelButton(900f, bottomBorderHeight, 150f, 30f, "end_turn_button.png",
				"end_turn_button.png", "[SPACE] LOOT ALL", true, false, Color.BLACK, Color.WHITE,
				"Loot all items nearby (legal if white, illegal if red)");
		buttonLootAll.setClickListener(new ClickListener() {
			@Override
			public void click() {

				ArrayList<Action> actionsToPerform = new ArrayList<Action>();
				if (inventoryMode == INVENTORY_MODE.MODE_LOOT) {
					for (GameObject gameObject : otherInventory.gameObjects) {
						Action action = new ActionTakeItems(Game.level.player,
								gameObject.inventoryThatHoldsThisObject.parent, gameObject);
						if (!action.legal && buttonLootAll.textParts == LOOT_ALL) {
						} else {
							actionsToPerform.add(action);
						}
					}
				} else if (inventoryMode == INVENTORY_MODE.MODE_NORMAL) {
					for (GroundDisplaySquare groundDisplaySquare : groundDisplay.groundDisplaySquares) {
						Action action = new ActionTakeItems(Game.level.player,
								groundDisplaySquare.stack.get(0).inventoryThatHoldsThisObject.parent,
								groundDisplaySquare.stack.get(0));
						if (!action.legal && buttonLootAll.textParts == LOOT_ALL) {
						} else {
							actionsToPerform.add(action);
						}
					}
				}
				for (Action action : actionsToPerform) {
					action.perform();
				}

				// Close inventory if emptied
				if (inventoryMode == INVENTORY_MODE.MODE_LOOT) {
					if (otherInventory.size() == 0) {
						Game.level.openCloseInventory();
						Object[] objects = new Object[] { "Looted everything!" };
						Game.level.addNotification(new Notification(objects, Notification.NotificationType.MISC, null));
					}
				} else if (inventoryMode == INVENTORY_MODE.MODE_NORMAL) {
					if (groundDisplay.groundDisplaySquares.size() == 0) {
						Game.level.openCloseInventory();
						Object[] objects = new Object[] { "Looted everything!" };
						Game.level.addNotification(new Notification(objects, Notification.NotificationType.MISC, null));
					}
				}

			}
		});

		buttonSearch = new LevelButton(1100f, bottomBorderHeight, 100f, 30f, "end_turn_button.png",
				"end_turn_button.png", stringEnterSearch, true, false, Color.BLACK, Color.WHITE, "Search!");
		buttonSearch.setClickListener(new ClickListener() {
			@Override
			public void click() {
				// Level.activeTextBox = null;
				if (Level.activeTextBox == textBoxSearch) {
					Level.activeTextBox = null;
				} else {
					Level.activeTextBox = textBoxSearch;

				}
			}
		});
		buttons.add(buttonSearch);

		buttonClearSearch = new LevelButton(lengthSearch + 16, 30f, lengthClearSearch, 30f, "end_turn_button.png",
				"end_turn_button.png", stringClearSearch, true, true, Color.BLACK, Color.WHITE, "Clear search filter");
		buttonClearSearch.setClickListener(new ClickListener() {
			@Override
			public void click() {
				textBoxSearch.clearText();
				if (Level.activeTextBox == textBoxSearch) {
					Level.activeTextBox = null;
				}
			}
		});
		buttons.add(buttonClearSearch);

		buttonQuickSell = new LevelButton(900f, bottomBorderHeight, 100f, 30f, "end_turn_button.png",
				"end_turn_button.png", "[SPACE] Quick Sell", true, false, Color.BLACK, Color.WHITE,
				"Sell useless items, obsolete weapons and armor, and duplicate tools");
		buttonQuickSell.setClickListener(new ClickListener() {

			@Override
			public void click() {
				if (inventoryMode == INVENTORY_MODE.MODE_TRADE) {
					Game.level.player.sellItemsMarkedToSell((Actor) Inventory.target);
				}
			}
		});

		if (inventoryMode == INVENTORY_MODE.MODE_NORMAL || inventoryMode == INVENTORY_MODE.MODE_LOOT) {
			// buttons.add(buttonStealAll);
			buttons.add(buttonLootAll);
		}

		if (inventoryMode == INVENTORY_MODE.MODE_TRADE) {
			buttons.add(buttonQuickSell);
		}

		buttonClose = new LevelButton(Game.halfWindowWidth - 25f, bottomBorderHeight, 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", "[ESC] Close", true, false, Color.BLACK, Color.WHITE, null);
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
		if (inventoryMode == INVENTORY_MODE.MODE_NORMAL || inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_DROP)
			this.groundDisplay = new GroundDisplay(900, 100);

		if (inventoryMode == INVENTORY_MODE.MODE_TRADE || inventoryMode == INVENTORY_MODE.MODE_LOOT) {
			otherInventory.isOpen = true;
		}

		updateStacks();
		matchStacksToSquares();
		if (otherInventory != null) {
			otherInventory.updateStacks();
			otherInventory.matchStacksToSquares();
		}
		if (groundDisplay != null) {
			groundDisplay.matchStacksToSquares();
			groundDisplay.resize2();
		}
	}

	public void close() {
		this.isOpen = false;
		Inventory.target = null;
		if (Inventory.lastInventoryFilterBy != null) {
			Inventory.inventoryFilterBy = lastInventoryFilterBy;
			Inventory.lastInventoryFilterBy = null;
		}

		if (Game.level.openInventories.contains(this))
			Game.level.openInventories.remove(this);
		this.inventorySquares = new ArrayList<InventorySquare>();
		this.groundDisplay = null;
		if (this.otherInventory != null) {
			otherInventory.isOpen = false;
			this.otherInventory.inventorySquares = new ArrayList<InventorySquare>();
			this.otherInventory = null;
		}
		Level.activeTextBox = null;
	}

	public void sort(INVENTORY_SORT_BY inventorySortBy, boolean filterFirst, boolean fromSortButtonPress) {

		if (otherInventory != null) {
			otherInventory.sort(inventorySortBy, filterFirst, false);
			otherInventory.updateStacks();
			otherInventory.matchStacksToSquares();
		}

		// Button selectedSortButton = null;

		if (inventorySortBy == INVENTORY_SORT_BY.SORT_ALPHABETICALLY) {
			selectedSortButton = buttonSortAlphabetically;
			selectedSortButtonString = stringSortAlphabetically;
			selectedSortButtonLength = lengthSortAlphabetically;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_NEWEST) {
			selectedSortButton = buttonSortByNewest;
			selectedSortButtonString = stringSortByNewest;
			selectedSortButtonLength = lengthSortByNewest;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_VALUE) {
			selectedSortButton = buttonSortByValue;
			selectedSortButtonString = stringSortByValue;
			selectedSortButtonLength = lengthSortByValue;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_FAVOURITE) {
			selectedSortButton = buttonSortByFavourite;
			selectedSortButtonString = stringSortByFavourite;
			selectedSortButtonLength = lengthSortByFavourite;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_TOTAL_DAMAGE) {
			selectedSortButton = buttonSortByTotalDamage;
			selectedSortButtonString = stringSortByTotalDamage;
			selectedSortButtonLength = lengthSortByTotalDamage;
		} else if (inventorySortBy == INVENTORY_SORT_BY.SORT_BY_SLASH_DAMAGE) {
			selectedSortButton = buttonSortBySlashDamage;
			selectedSortButtonString = stringSortBySlashDamage;
			selectedSortButtonLength = lengthSortBySlashDamage;
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

		// sorts filtered game objects
		Collections.sort(filteredGameObjects);
		if (sortBackwards)
			Collections.reverse(filteredGameObjects);

		updateStacks();
		matchStacksToSquares();
	}

	public void filter(INVENTORY_FILTER_BY inventoryFilterBy, boolean temporary) {

		if (parent == Game.level.player && inventoryFilterBy != Inventory.inventoryFilterBy) {
			if (temporary) {
				Inventory.lastInventoryFilterBy = Inventory.inventoryFilterBy;
				Inventory.inventoryFilterBy = inventoryFilterBy;
			} else {
				Inventory.inventoryFilterBy = inventoryFilterBy;
				Inventory.lastInventoryFilterBy = null;
			}
		}

		for (Button button : buttonsFilter)
			button.down = false;

		filteredGameObjects.clear();
		if (Game.level.player.inventory.textBoxSearch.getText().length() > 0) {
			for (GameObject gameObject : gameObjects) {
				if (TextUtils.containsIgnoreCase(gameObject.name,
						Game.level.player.inventory.textBoxSearch.getText())) {
					filteredGameObjects.add(gameObject);
				}
			}
		} else if (inventoryFilterBy == INVENTORY_FILTER_BY.FILTER_BY_ALL) {
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

		sort(Inventory.inventorySortBy, false, false);

		if (otherInventory != null) {
			otherInventory.filter(inventoryFilterBy, temporary);
		}
	}

	public void postLoad1() {

		new ArrayList<InventorySquare>();

		// Tell objects they're in this inventory
		for (GameObject gameObject : gameObjects) {
			gameObject.inventoryThatHoldsThisObject = this;
			gameObject.postLoad1();
		}

		int index = 0;
	}

	public void postLoad2() {
		// Tell objects they're in this inventory
		for (GameObject gameObject : gameObjects) {
			gameObject.inventoryThatHoldsThisObject = this;
			gameObject.postLoad2();
		}

	}

	public static void loadStaticImages() {
		textureUp = ResourceUtils.getGlobalImage("inventory_up.png");
		textureDown = ResourceUtils.getGlobalImage("inventory_down.png");
		textureCorner = ResourceUtils.getGlobalImage("inventory_corner.png");
		textureFilter = ResourceUtils.getGlobalImage("filter.png");
		textureBag = ResourceUtils.getGlobalImage("bag.png");
		textureStar = ResourceUtils.getGlobalImage("star.png");
		textureGold = ResourceUtils.getGlobalImage("gold.png");
	}

	public GameObject get(int index) {
		return gameObjects.get(index);
	}

	public void add(GameObject gameObject) {
		add(gameObject, -1);
	}

	public void add(GameObject gameObject, int index) {
		if (!gameObjects.contains(gameObject)) {

			// Remove references with square
			if (gameObject.squareGameObjectIsOn != null) {
				gameObject.squareGameObjectIsOn.inventory.remove(gameObject);
				gameObject.squareGameObjectIsOn.inventory.updateStacks();
				gameObject.squareGameObjectIsOn.inventory.matchStacksToSquares();
			}
			gameObject.squareGameObjectIsOn = null;

			// Remove from ground squares index
			if (Game.level.inanimateObjectsOnGround.contains(gameObject))
				Game.level.inanimateObjectsOnGround.remove(gameObject);

			// Remove from another gameObjects inventory
			if (gameObject.inventoryThatHoldsThisObject != null) {
				Inventory oldInventory = gameObject.inventoryThatHoldsThisObject;
				oldInventory.remove(gameObject);
				oldInventory.updateStacks();
				oldInventory.matchStacksToSquares();
			}

			gameObject.turnAcquired = Level.turn;

			// Set that date on all items with that name
			for (GameObject gameObjectInInventory : gameObjects) {
				if (gameObjectInInventory.templateId == gameObject.templateId) {
					gameObjectInInventory.turnAcquired = Level.turn;
				}
			}

			// Add to this inventory's list of game objects
			gameObjects.add(gameObject);
			gameObject.inventoryThatHoldsThisObject = this;

			// this.sort(inventorySortBy);

			// pick up date for sorting by newest

			if (parent != null)
				parent.inventoryChanged();

			markItemsToSell();

			if (index != -1) {
				filteredGameObjects.remove(index);
				filteredGameObjects.add(index, gameObject);
			} else {
				filteredGameObjects.add(gameObject);
			}

			if (groundDisplay != null)
				groundDisplay.refreshGameObjects();

			updateStacks();
			matchStacksToSquares();

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

			markItemsToSell();
			// this.sort(inventorySortBy);
			if (filteredGameObjects.contains(gameObject)) {
				index = filteredGameObjects.indexOf(gameObject);
				filteredGameObjects.remove(gameObject);
				// filteredGameObjects.set(filteredGameObjects.indexOf(gameObject),
				// null);
			}

			if (groundDisplay != null)
				groundDisplay.refreshGameObjects();

			updateStacks();
			matchStacksToSquares();
		}
		return index;
	}

	// public void replace(GameObject out, GameObject in) {
	// int index = this.remove(out);
	// this.add(in, index);
	//
	// }

	public int size() {
		return gameObjects.size();
	}

	public ArrayList<GameObject> getGameObjects() {
		return gameObjects;
	}

	public HashMap<Integer, ArrayList<GameObject>> legalStacks = new HashMap<Integer, ArrayList<GameObject>>();
	public HashMap<Integer, ArrayList<GameObject>> illegalStacks = new HashMap<Integer, ArrayList<GameObject>>();
	public HashMap<Integer, ArrayList<GameObject>> equippedStacks = new HashMap<Integer, ArrayList<GameObject>>();
	public ArrayList<ArrayList<GameObject>> allStacks = new ArrayList<ArrayList<GameObject>>();

	private Object[] LOOT_ALL = new Object[] { new StringWithColor("[SPACE] LOOT ALL", Color.WHITE) };
	private Object[] STEAL_ALL = new Object[] { new StringWithColor("[SPACE] STEAL ALL", Color.RED) };

	public void updateStacks() {

		legalStacks.clear();
		illegalStacks.clear();
		equippedStacks.clear();
		allStacks.clear();

		ArrayList<GameObject> toUse = gameObjects;
		if (parent instanceof GameObject)
			toUse = filteredGameObjects;

		for (GameObject gameObject : toUse) {

			if (!gameObject.fitsInInventory && inventoryMode != INVENTORY_MODE.MODE_SELECT_MAP_MARKER)
				continue;

			if (this.parent == Game.level.player
					&& (Game.level.player.equipped == gameObject || Game.level.player.helmet == gameObject
							|| Game.level.player.bodyArmor == gameObject || Game.level.player.legArmor == gameObject)) {

				if (equippedStacks.containsKey(gameObject.templateId)) {
					equippedStacks.get(gameObject.templateId).add(gameObject);
				} else {
					ArrayList<GameObject> newStack = new ArrayList<GameObject>();
					newStack.add(gameObject);
					equippedStacks.put(gameObject.templateId, newStack);
					allStacks.add(newStack);
				}
			} else if (objectLegal(gameObject, this)) {
				if (legalStacks.containsKey(gameObject.templateId)) {
					legalStacks.get(gameObject.templateId).add(gameObject);
				} else {
					ArrayList<GameObject> newStack = new ArrayList<GameObject>();
					newStack.add(gameObject);
					legalStacks.put(gameObject.templateId, newStack);
					allStacks.add(newStack);
				}

			} else {// Illegal items
				if (illegalStacks.containsKey(gameObject.templateId)) {
					illegalStacks.get(gameObject.templateId).add(gameObject);
				} else {
					ArrayList<GameObject> newStack = new ArrayList<GameObject>();
					newStack.add(gameObject);
					illegalStacks.put(gameObject.templateId, newStack);
					allStacks.add(newStack);
				}
			}
		}
	}

	public void matchStacksToSquares() {

		if (!isOpen)
			return;

		inventorySquares.clear();

		for (ArrayList<GameObject> stack : allStacks) {
			matchStackToSquare(stack);
		}

		totalSquaresHeight = ((inventorySquares.size() / squareGridWidthInSquares) * Game.INVENTORY_SQUARE_HEIGHT);

		// if (inventoryMode != INVENTORY_MODE.MODE_SELECT_MAP_MARKER &&
		// this.parent != Game.level.player) {
		// Game.level.player.inventory.updateStacks();
		// Game.level.player.inventory.matchStacksToSquares();
		// return;
		// }

		resize1();

	}

	public void matchStackToSquare(ArrayList<GameObject> stack) {

		if (this.parent == Game.level.player && stack.get(0) instanceof Gold)
			return;

		if (inventoryMode == INVENTORY_MODE.MODE_TRADE && stack.get(0) instanceof Gold)
			return;

		if (inventoryMode == INVENTORY_MODE.MODE_TRADE && this.parent != Game.level.player
				&& stack.get(0).toSell == false && stack.get(0).turnAcquired != Level.turn)
			return;

		if (stack.get(0).value == 0 && stack.get(0) instanceof Gold)
			return;

		InventorySquare inventorySquare = new InventorySquare(0, 0, null, this);
		inventorySquare.stack = stack;
		inventorySquares.add(inventorySquare);
	}

	public static boolean objectLegal(GameObject gameObject, Inventory inventory) {
		if (inventory != null && inventory.parent == Game.level.player) { // player
			if (gameObject.owner != null && gameObject.owner != Game.level.player) {
				return false;
			} else {
				return true;
			}
		} else if (inventory != null && inventory.parent instanceof Human) { // npc
			if (Inventory.inventoryMode == INVENTORY_MODE.MODE_TRADE) {
				return true;
			} else {
				return false;
			}
		} else { // a container or ground
			if (gameObject.owner != null && gameObject.owner != Game.level.player) {
				return false;
			} else {
				return true;
			}
		}
	}

	public void resize1() {
		if (!isOpen)
			return;

		float pixelsToLeftOfSquares = this.sortButtonX + sortButtonWidth;
		float pixelsToRightOfSquares = actorWidth;
		float pixelsBetweenSquares = Game.INVENTORY_SQUARE_WIDTH;
		float availablePixelsForSquares = Game.windowWidth
				- (pixelsToLeftOfSquares + pixelsBetweenSquares + pixelsToRightOfSquares);
		this.squareGridWidthInSquares = (int) ((availablePixelsForSquares / 2f) / Game.INVENTORY_SQUARE_WIDTH);
		if (this.squareGridWidthInSquares < 1)
			this.squareGridWidthInSquares = 1;
		squaresAreaWidth = squareGridWidthInSquares * Game.INVENTORY_SQUARE_WIDTH;
		this.squaresX = pixelsToLeftOfSquares;
		squaresAreaHeight = Game.windowHeight - Inventory.topBorderHeight - Inventory.bottomBorderHeight;

		if (otherInventory != null) {
			otherInventory.squareGridWidthInSquares = this.squareGridWidthInSquares;
			otherInventory.squaresX = pixelsToLeftOfSquares + (squareGridWidthInSquares * Game.INVENTORY_SQUARE_WIDTH)
					+ pixelsBetweenSquares;
			otherInventory.actorX = (int) (otherInventory.squaresX + squaresAreaWidth);
		}
		if (groundDisplay != null) {
			groundDisplay.squareGridWidthInSquares = this.squareGridWidthInSquares;
			groundDisplay.squaresX = (int) (pixelsToLeftOfSquares
					+ (squareGridWidthInSquares * Game.INVENTORY_SQUARE_WIDTH) + pixelsBetweenSquares);
		}

		fixScroll();
		resize2();

		buttonClose.x = squaresX;
		textShiftX = squaresX;
		buttonSearch.x = squaresX;
		buttonQuickSell.x = squaresX;

		textShiftY = Game.windowHeight - bottomBorderHeight;
		buttonQuickSell.y = bottomBorderHeight;
		buttonSearch.y = bottomBorderHeight - 30;
		buttonClose.y = bottomBorderHeight - 60;

		if (this.groundDisplay != null) {
			this.groundDisplay.fixScroll();
			this.groundDisplay.resize2();
			buttonLootAll.x = groundDisplay.squaresX;
			buttonLootAll.y = bottomBorderHeight - 30;
			textOtherShiftX = this.groundDisplay.squaresX;
		}
		if (this.otherInventory != null) {
			this.otherInventory.fixScroll();
			this.otherInventory.resize2();
			buttonLootAll.x = this.otherInventory.squaresX;
			buttonLootAll.y = bottomBorderHeight - 30;
			textOtherShiftX = this.otherInventory.squaresX;
		}

		weaponComparisonDisplay.resize();

	}

	public void resize2() {
		if (!isOpen)
			return;
		int xIndex = 0;
		int yIndex = 0;
		for (InventorySquare inventorySquare : inventorySquares) {
			inventorySquare.xInGrid = xIndex;
			inventorySquare.yInGrid = yIndex;
			inventorySquare.xInPixels = Math
					.round(this.squaresX + inventorySquare.xInGrid * Game.INVENTORY_SQUARE_WIDTH);
			inventorySquare.yInPixels = Math
					.round(this.squaresY + inventorySquare.yInGrid * Game.INVENTORY_SQUARE_HEIGHT);
			xIndex++;
			if (xIndex == this.squareGridWidthInSquares) {
				xIndex = 0;
				yIndex++;
			}
		}
		totalSquaresHeight = ((inventorySquares.size() / squareGridWidthInSquares) * Game.INVENTORY_SQUARE_HEIGHT);
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

	public boolean containsObjectWithTemplateId(int id) {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.templateId == id) {
				return true;
			}
		}
		return false;
	}

	public GameObject getObjectWithTemplateId(int id) {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.templateId == id) {
				return gameObject;
			}
		}
		return null;
	}

	public boolean containsDuplicateOf(GameObject g) {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.templateId == g.templateId && gameObject != g) {
				return true;
			}
		}
		return false;
	}

	public GameObject getDuplicateOf(GameObject g) {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.templateId == g.templateId && gameObject != g) {
				return gameObject;
			}
		}
		return null;
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
		QuadUtils.drawQuad(backgroundColor, 0, 0, Game.windowWidth, Game.windowHeight);

		drawBorder();

		// Draw bag textureBag
		float bagTextureX = this.squaresX + Inventory.squaresAreaWidth / 2 - textureBag.getWidth();
		float bagTextureY = this.squaresBaseY + Inventory.squaresAreaHeight / 2 - textureBag.getHeight();
		TextureUtils.drawTexture(textureBag, 0.5f, bagTextureX, bagTextureY, bagTextureX + textureBag.getWidth() * 2,
				bagTextureY + textureBag.getHeight() * 2);

		// sqrs
		drawSquares();

		// Ground display sqrs
		if (groundDisplay != null) {
			groundDisplay.drawStaticUI();
			if (groundDisplay.groundDisplaySquares.size() > 0) {
				groundDisplay.drawSquares();
			} else {
				float emptyStringX = groundDisplay.squaresX + this.squaresAreaWidth / 2 - GroundDisplay.lengthEmpty / 2;
				float emptyStringY = groundDisplay.squaresY + this.squaresAreaHeight / 2 - 10;
				QuadUtils.drawQuad(Color.BLACK, emptyStringX - 8, emptyStringY - 8,
						emptyStringX + GroundDisplay.lengthEmpty + 8, emptyStringY + 28);
				TextUtils.printTextWithImages(emptyStringX, emptyStringY, Integer.MAX_VALUE, false, null,
						new Object[] { GroundDisplay.stringEmpty });
			}

			boolean containsLegalStuff = false;
			for (GroundDisplaySquare groundDisplaySquare : groundDisplay.groundDisplaySquares) {
				if (objectLegal(groundDisplaySquare.stack.get(0), this)) {
					containsLegalStuff = true;
					break;
				}
			}
			if (groundDisplay.groundDisplaySquares.size() == 0) {
				Inventory.buttonLootAll.textParts = LOOT_ALL;
				Inventory.buttonLootAll.enabled = false;
				// Inventory.buttonLootAll.setTextColor(Color.WHITE);
			} else {
				if (containsLegalStuff) {
					Inventory.buttonLootAll.textParts = LOOT_ALL;
					// Inventory.buttonLootAll.setTextColor(Color.WHITE);
				} else {
					Inventory.buttonLootAll.textParts = STEAL_ALL;
					// Inventory.buttonLootAll.setTextColor(Color.RED);
				}
				Inventory.buttonLootAll.enabled = true;
			}
		}

		// Other Gameobject / actor inventory squares
		if (otherInventory != null) {
			otherInventory.drawBorder();
			GameObject otherGameObject = (GameObject) target;
			float otherTextureX = otherInventory.squaresX + otherInventory.squaresAreaWidth / 2
					- (otherGameObject.width);
			float otherTextureY = otherInventory.squaresBaseY + otherInventory.squaresAreaHeight / 2
					- (otherGameObject.height);
			if (otherGameObject instanceof Actor) {
				int actorPositionXInPixels = otherInventory.actorX;
				int actorPositionYInPixels = (int) (squaresBaseY + squaresAreaHeight / 2
						- (otherGameObject.imageTexture.getHeight()));
				TextureUtils.drawTexture(otherGameObject.imageTexture, 1f, actorPositionXInPixels,
						actorPositionYInPixels, actorPositionXInPixels + otherGameObject.width * 2,
						actorPositionYInPixels + otherGameObject.height * 2, true);
				TextureUtils.drawTexture(textureBag, 0.5f, otherTextureX, otherTextureY,
						otherTextureX + otherGameObject.width * 2, otherTextureY + otherGameObject.height * 2, true);
			} else {
				TextureUtils.drawTexture(otherGameObject.imageTexture, 0.5f, otherTextureX, otherTextureY,
						otherTextureX + otherGameObject.width * 2, otherTextureY + otherGameObject.height * 2);

			}
			if (otherInventory.size() > 0) {
				otherInventory.drawSquares();
			} else {
				float emptyStringX = otherInventory.squaresX + otherInventory.squaresAreaWidth / 2 - lengthEmpty / 2;
				float emptyStringY = otherInventory.squaresY + otherInventory.squaresAreaHeight / 2 - 10;
				QuadUtils.drawQuad(Color.BLACK, emptyStringX - 8, emptyStringY - 8, emptyStringX + lengthEmpty + 8,
						emptyStringY + 28);
				TextUtils.printTextWithImages(emptyStringX, emptyStringY, Integer.MAX_VALUE, false, null,
						new Object[] { stringEmpty });
			}

			boolean containsLegalStuff = false;
			for (GameObject gameObject : otherInventory.gameObjects) {
				if (objectLegal(gameObject, this)) {
					containsLegalStuff = true;
					break;
				}
			}
			if (otherInventory.size() == 0) {
				Inventory.buttonLootAll.textParts = LOOT_ALL;
				Inventory.buttonLootAll.enabled = false;
				// Inventory.buttonLootAll.setTextColor(Color.WHITE);
			} else {
				if (containsLegalStuff) {
					Inventory.buttonLootAll.textParts = LOOT_ALL;
					// Inventory.buttonLootAll.setTextColor(Color.WHITE);
				} else {
					Inventory.buttonLootAll.textParts = STEAL_ALL;
					// Inventory.buttonLootAll.setTextColor(Color.RED);
				}
				Inventory.buttonLootAll.enabled = true;
			}
		} else if (target != null && target instanceof GameObject) {
			// Not drawing other actor/objects inventory, but draw them
			GameObject otherGameObject = (GameObject) target;
			float otherTextureX = squaresX + squaresAreaWidth + Game.SQUARE_WIDTH;
			float otherTextureY = squaresBaseY + squaresAreaHeight / 2 - (otherGameObject.height);
			if (otherGameObject instanceof Actor) {
				int actorPositionXInPixels = (int) otherTextureX;
				int actorPositionYInPixels = (int) (squaresBaseY + squaresAreaHeight / 2
						- (otherGameObject.imageTexture.getHeight()));
				TextureUtils.drawTexture(otherGameObject.imageTexture, 1f, actorPositionXInPixels,
						actorPositionYInPixels, actorPositionXInPixels + otherGameObject.width * 2,
						actorPositionYInPixels + otherGameObject.height * 2, true);
			} else {
				TextureUtils.drawTexture(otherGameObject.imageTexture, 0.5f, otherTextureX, otherTextureY,
						otherTextureX + otherGameObject.width * 2, otherTextureY + otherGameObject.height * 2);

			}

		}

		// cursor and action over squares
		if (this.inventorySquareMouseIsOver != null && Game.buttonHoveringOver == null) {
			this.inventorySquareMouseIsOver.drawCursor();
			this.inventorySquareMouseIsOver.drawActionThatWillBePerformed(false);

		}

		// Top border black mask
		QuadUtils.drawQuad(backgroundColor, 0, 0, Game.windowWidth, topBorderHeight);

		// Bottom border black mask
		QuadUtils.drawQuad(backgroundColor, 0, Game.windowHeight - bottomBorderHeight, Game.windowWidth,
				Game.windowHeight);

		// "Search:" text
		TextUtils.printTextWithImages(0, 0, Integer.MAX_VALUE, false, null, new Object[] { stringSearch });

		// [SHIFT]
		if (groundDisplay != null) {
			TextUtils.printTextWithImages(textShiftX, textShiftY, Integer.MAX_VALUE, false, null,
					new Object[] { stringShiftDrop });
			TextUtils.printTextWithImages(textOtherShiftX, Game.windowHeight - bottomBorderHeight, Integer.MAX_VALUE,
					false, null, new Object[] { stringShiftEquip });
		}

		if (otherInventory != null && inventoryMode != INVENTORY_MODE.MODE_TRADE) {
			TextUtils.printTextWithImages(textShiftX, textShiftY, Integer.MAX_VALUE, false, null,
					new Object[] { stringShiftPut });
			TextUtils.printTextWithImages(textOtherShiftX, Game.windowHeight - bottomBorderHeight, Integer.MAX_VALUE,
					false, null, new Object[] { stringShiftEquip });
		}

		// [ENTER] Search
		buttonSearch.draw();

		// text
		if (inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_FILL) {
			TextUtils.printTextWithImages(100f, 32f, 300f, true, null,
					new Object[] { new StringWithColor("Please Select a Container to Fill", Color.WHITE) });
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_POUR) {
			TextUtils.printTextWithImages(100f, 32f, 300f, true, null,
					new Object[] { new StringWithColor("Please Select a Container to Pour Out", Color.WHITE) });
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_MAP_MARKER) {
			TextUtils.printTextWithImages(100f, 32f, 300f, true, null,
					new Object[] { new StringWithColor("Please Select a Map Marker", Color.WHITE) });
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_DROP) {
			TextUtils.printTextWithImages(100f, 32f, 300f, true, null,
					new Object[] { new StringWithColor("Please Select an Item to Drop", Color.WHITE) });
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_GIVE) {
			TextUtils.printTextWithImages(100f, 32f, 300f, true, null,
					new Object[] { new StringWithColor("Please Select an Item to Give", Color.WHITE) });
		} else if (inventoryMode == INVENTORY_MODE.MODE_SELECT_ITEM_TO_THROW) {
			TextUtils.printTextWithImages(100f, 32f, 300f, true, null,
					new Object[] { new StringWithColor("Please Select an Item to Throw", Color.WHITE) });
		}

		TextUtils.printTextWithImages(this.squaresX, inventoryNamesY, 300f, true, null, "Bag");

		if (groundDisplay != null) {
			groundDisplay.drawText();
		}

		if (otherInventory != null) {
			otherInventory.drawOtherInventoryText();
		}

		// Actor
		int actorPositionXInPixels = this.actorX;
		int actorPositionYInPixels = (int) (squaresBaseY + squaresAreaHeight / 2
				- (Game.level.player.imageTexture.getHeight()));
		float alpha = 1.0f;
		TextureUtils.drawTexture(Game.level.player.imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
				actorPositionXInPixels + actorWidth, actorPositionYInPixels + Game.level.player.height * 2);

		GameObject gameObjectToDrawInPlayersHand = null;
		GameObject gameObjectToDrawOnPlayersHead = Game.level.player.helmet;
		GameObject gameObjectToDrawOnPlayersBody = Game.level.player.bodyArmor;
		GameObject gameObjectToDrawOnPlayersLegs = Game.level.player.legArmor;

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
			int helmetPositionXInPixels = actorPositionXInPixels + 0 * 2;
			int helmetPositionYInPixels = actorPositionYInPixels + -8 * 2;
			TextureUtils.drawTexture(Game.level.player.hairImageTexture, alpha, helmetPositionXInPixels,
					helmetPositionYInPixels,
					helmetPositionXInPixels + Game.level.player.hairImageTexture.getWidth() * 4,
					helmetPositionYInPixels + Game.level.player.hairImageTexture.getHeight() * 4);
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
		if (this.inventorySquareMouseIsOver != null && this.inventorySquareMouseIsOver.stack.get(0) instanceof Weapon) {

			int comparisonPositionXInPixels = 1150;
			int comparisonPositionYInPixels = 250;
		}

		// Top left corner
		// TextureUtils.drawTexture(textureCorner, squaresX - 100, squaresY -
		// 100, squaresX, squaresY);

		// LineUtils.drawLine(Color.WHITE, squaresX - 200, squaresY - 200,
		// squaresX, squaresY, 10);

		// buttons
		for (Button button : buttons) {
			button.draw();
		}

		// Up / down icon on active sort button
		if (buttons.contains(selectedSortButton)) {
			if (sortBackwards)
				TextureUtils.drawTexture(textureUp, selectedSortButton.x + selectedSortButtonLength + 5,
						selectedSortButton.y + 5, selectedSortButton.x + selectedSortButtonLength + 15,
						selectedSortButton.y + 15);
			else
				TextureUtils.drawTexture(textureDown, selectedSortButton.x + selectedSortButtonLength + 5,
						selectedSortButton.y + 5, selectedSortButton.x + selectedSortButtonLength + 15,
						selectedSortButton.y + 15);
		}

		// Up / down icon on active sort button
		for (Button filterButton : buttonsFilter) {
			if (filterButton.down && buttons.contains(filterButton)) {
				TextureUtils.drawTexture(textureFilter, filterButton.x - 5, filterButton.y - 5, filterButton.x + 5,
						filterButton.y + 5);
			}
		}

		// Gold for this actor
		if (this.parent instanceof Actor && !(this.parent instanceof Trader)) {
			Actor actor = (Actor) this.parent;
			String goldText = "Gold: " + actor.getCarriedGoldValue();
			float goldWidth = Game.smallFont.getWidth(goldText);
			float goldPositionX = squaresX + squaresAreaWidth - goldWidth;
			TextUtils.printTextWithImages(goldPositionX, Game.windowHeight - bottomBorderHeight, Integer.MAX_VALUE,
					false, null, new Object[] { goldText });
		}

		// Gold for other actor
		if (otherInventory != null && otherInventory.parent instanceof Actor) {
			// && !(otherInventory.parent instanceof Trader)) {
			Actor actor = (Actor) otherInventory.parent;
			String goldText = "Gold: " + actor.getCarriedGoldValue();
			float goldWidth = Game.smallFont.getWidth(goldText);
			float goldPositionX = otherInventory.squaresX + squaresAreaWidth - goldWidth;
			TextUtils.printTextWithImages(goldPositionX, Game.windowHeight - bottomBorderHeight, Integer.MAX_VALUE,
					false, null, new Object[] { goldText });

		}

		weaponComparisonDisplay.drawStaticUI();

		// if (this.parent == Game.level.player && searching)
		textBoxSearch.draw();

		// Fade for scroll
		if (this.squaresY < squaresBaseY) {
			TextureUtils.drawTexture(ActivityLogger.fadeTop, squaresX, squaresBaseY, squaresX + squaresAreaWidth,
					squaresBaseY + 64);
		}

		if (this.squaresY + totalSquaresHeight > Game.windowHeight - bottomBorderHeight) {
			TextureUtils.drawTexture(ActivityLogger.fadeBottom, squaresX, Game.windowHeight - bottomBorderHeight - 64,
					squaresX + squaresAreaWidth, Game.windowHeight - bottomBorderHeight);//
		}

		if (Game.level.activeTextBox == textBoxQty) {
			// textBoxQty.setText("TEST BOX QTY");

			// Full blakc bg
			QuadUtils.drawQuad(new Color(0f, 0f, 0f, 0.5f), 0, 0, Game.windowWidth, Game.windowHeight);
			// Instructions
			TextUtils.printTextWithImages(textBoxQty.drawPositionX, textBoxQty.drawPositionY - 36, Integer.MAX_VALUE,
					true, null, new Object[] { qtyStringWithColor });
			textBoxQty.draw();
			if (valuePerQty != 0 && textBoxQty.numericValue > 0) {
				int totalValue = valuePerQty * textBoxQty.numericValue;
				String totalValueString = "Total value " + totalValue + " gold";
				TextUtils.printTextWithImages(textBoxQty.drawPositionX, textBoxQty.drawPositionY + 36,
						Integer.MAX_VALUE, true, null,
						new Object[] { new StringWithColor(totalValueString, Color.WHITE) });

			}

		}

		if (animation != null && !animation.completed) {
			animation.draw2();
		}

	}

	public void drawOtherInventoryText() {
		TextUtils.printTextWithImages(this.squaresX, inventoryNamesY, 300f, true, null,
				new Object[] { ((GameObject) this.parent).name });
	}

	public void drawBorder() {
		QuadUtils.drawQuad(inventoryAreaColor, squaresX, this.squaresY, squaresX + squaresAreaWidth,
				this.squaresY + squaresAreaHeight);
	}

	public void drawSquares() {
		for (InventorySquare inventorySquare : inventorySquares) {
			inventorySquare.drawStaticUI();
		}
	}

	public boolean isOpen() {
		return isOpen;
	}

	public InventorySquare getInventorySquareMouseIsOver(float mouseXInPixels, float mouseYInPixels) {

		if (mouseYInPixels <= (bottomBorderHeight))
			return null;

		// Inventory sqr
		float offsetX = squaresX;
		float offsetY = squaresY;
		float scroll = 0;

		int mouseXInSquares = (int) (((mouseXInPixels - offsetX) / Game.INVENTORY_SQUARE_WIDTH));
		int mouseYInSquares = (int) ((Game.windowHeight - mouseYInPixels - offsetY - scroll)
				/ Game.INVENTORY_SQUARE_HEIGHT);

		for (InventorySquare inventorySquare : inventorySquares) {
			if (inventorySquare.xInGrid == mouseXInSquares && inventorySquare.yInGrid == mouseYInSquares)
				return inventorySquare;
		}

		// Ground display sqr
		if (groundDisplay != null) {
			GroundDisplaySquare groundDisplaySquareMouseIsOver = groundDisplay
					.getGroundDisplaySquareMouseIsOver(mouseXInPixels, mouseYInPixels);
			if (groundDisplaySquareMouseIsOver != null) {
				return groundDisplaySquareMouseIsOver;
			}
		}

		if (otherInventory != null) {
			InventorySquare inventorySquareMouseIsOver = otherInventory.getInventorySquareMouseIsOver(mouseXInPixels,
					mouseYInPixels);
			if (inventorySquareMouseIsOver != null) {
				return inventorySquareMouseIsOver;
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

	@Override
	public void scroll(float dragX, float dragY) {
		drag(dragX, dragY);
	}

	@Override
	public void drag(float dragX, float dragY) {
		this.squaresY -= dragY;
		fixScroll();
		resize2();
	}

	private void fixScroll() {
		// TODO Auto-generated method stub

		if (totalSquaresHeight < Game.windowHeight - bottomBorderHeight - topBorderHeight) {
			this.squaresY = this.squaresBaseY;
		} else if (this.squaresY < -(totalSquaresHeight - (Game.windowHeight - bottomBorderHeight))) {
			this.squaresY = -(totalSquaresHeight - (Game.windowHeight - bottomBorderHeight));
		} else if (this.squaresY > this.squaresBaseY) {
			this.squaresY = this.squaresBaseY;
		}

	}

	public Draggable getDraggable(int mouseX, int mouseY) {

		if (mouseX > squaresX && mouseX < squaresX + squaresAreaWidth && mouseY > squaresBaseY
				&& mouseY < squaresBaseY + squaresAreaHeight) {
			return this;
		}

		if (this.otherInventory != null) {
			if (mouseX > otherInventory.squaresX && mouseX < otherInventory.squaresX + squaresAreaWidth
					&& mouseY > squaresBaseY && mouseY < squaresBaseY + squaresAreaHeight) {
				return otherInventory;
			}
		}

		if (this.groundDisplay != null) {
			if (mouseX > groundDisplay.squaresX && mouseX < groundDisplay.squaresX + squaresAreaWidth
					&& mouseY > squaresBaseY && mouseY < squaresBaseY + squaresAreaHeight) {
				return groundDisplay;
			}
		}

		return null;
	}

	public int itemsToSellCount = 0;

	public void markItemsToSell() {

		if (!(parent instanceof Actor) || parent instanceof NonHuman)
			return;

		itemsToSellCount = 0;

		// Special tules for trader
		if (parent instanceof Trader) {
			for (GameObject gameObject : gameObjects) {
				gameObject.toSell = true;
				itemsToSellCount++;
			}

			Trader trader = (Trader) parent;
			if (trader.broom != null) {
				trader.broom.toSell = false;
				itemsToSellCount--;
			}

			return;
		}
		// General rules for actors
		ArrayList<String> weaponsSeenInInventory = new ArrayList<String>();

		for (GameObject gameObject : gameObjects) {

			gameObject.toSell = false;

			if (gameObject instanceof Gold)
				continue;

			if (gameObject instanceof ContainerForLiquids)
				continue;

			if (this.parent == Game.level.player) {
				if (gameObject.starred)
					continue;
				if (Game.level.player.equipped == gameObject)
					continue;
				if (Game.level.player.bodyArmor == gameObject)
					continue;
				if (Game.level.player.legArmor == gameObject)
					continue;
				if (Game.level.player.helmet == gameObject)
					continue;

				if (gameObject instanceof Weapon) {
					if (checkIfPlayersWeaponObsolete(gameObject))
						continue;
				}

				if (gameObject instanceof Armor) {
					if (checkIfPlayersArmorObsolete((Armor) gameObject))
						continue;
				}
			}

			// Junk
			if (gameObject instanceof Junk) {
				gameObject.toSell = true;
				itemsToSellCount++;
				continue;
			}

			// Duplicate weapons
			if (gameObject instanceof Weapon) {
				if (weaponsSeenInInventory.contains(gameObject.name)) {
					gameObject.toSell = true;
					itemsToSellCount++;
				} else {
					weaponsSeenInInventory.add(gameObject.name);
				}
			}
		}
	}

	public boolean checkIfPlayersWeaponObsolete(GameObject weapon) {

		boolean seenSelf = false;

		for (GameObject otherWeapon : Game.level.player.inventory.getGameObjectsOfClass(weapon.getClass())) {

			if (weapon == otherWeapon) {
				seenSelf = true;
				continue;
			}

			if (weapon.templateId == otherWeapon.templateId) {
				if (seenSelf) {
					continue;
				} else {
					weapon.toSell = true;
					itemsToSellCount++;
					return true;
				}
			}

			if (otherWeapon.bluntDamage >= weapon.bluntDamage && otherWeapon.slashDamage >= weapon.slashDamage
					&& otherWeapon.pierceDamage >= weapon.pierceDamage && otherWeapon.waterDamage >= weapon.waterDamage
					&& otherWeapon.fireDamage >= weapon.fireDamage
					&& otherWeapon.electricalDamage >= weapon.electricalDamage
					&& otherWeapon.poisonDamage >= weapon.poisonDamage
					&& otherWeapon.bleedingDamage >= weapon.bleedingDamage && otherWeapon.healing >= weapon.healing
					&& otherWeapon.maxRange >= weapon.maxRange && otherWeapon.minRange <= weapon.minRange) {
				weapon.toSell = true;
				itemsToSellCount++;
				return true;
			}
		}

		return false;
	}

	public boolean checkIfPlayersArmorObsolete(Armor armor) {

		ArrayList<GameObject> armorsOFSameTypeInInventory = Game.level.player.inventory
				.getGameObjectsOfClass(armor.getClass());

		for (GameObject armorOFSameTypeInInventory : armorsOFSameTypeInInventory) {

			if (armor == armorOFSameTypeInInventory)
				continue;

			if (armorOFSameTypeInInventory.fireResistance >= armor.fireResistance
					&& armorOFSameTypeInInventory.waterResistance >= armor.waterResistance
					&& armorOFSameTypeInInventory.electricResistance >= armor.electricResistance
					&& armorOFSameTypeInInventory.poisonResistance >= armor.poisonResistance
					&& armorOFSameTypeInInventory.bleedingResistance >= armor.bleedingResistance
					&& armorOFSameTypeInInventory.pierceResistance >= armor.pierceResistance
					&& armorOFSameTypeInInventory.bluntResistance >= armor.bluntResistance
					&& armorOFSameTypeInInventory.slashResistance >= armor.slashResistance) {
				armor.toSell = true;
				itemsToSellCount++;
				return true;
			}
		}

		return false;
	}

	@Override
	public void enterTyped(TextBox textBox) {
		if (textBox == textBoxSearch) {
			Inventory.buttonSearch.click();
		} else if (textBox == textBoxQty) {
			variableAction.qty = textBoxQty.numericValue;
			variableAction.perform();
			Level.activeTextBox = null;
		}
	}

	VariableQtyAction variableAction = null;
	// String qtyString = null;
	StringWithColor qtyStringWithColor = null;
	int valuePerQty = 0;

	public void showQTYDialog(VariableQtyAction variableAction, int maxQty, String qtyString, int valuePerQty) {
		this.variableAction = variableAction;
		textBoxQty.maxNumericValue = maxQty;
		textBoxQty.setText("" + maxQty);
		textBoxQty.moveCaretToEnd();
		textBoxQty.textHighlighted = true;
		qtyStringWithColor = new StringWithColor(qtyString, Color.WHITE);
		this.valuePerQty = valuePerQty;

		Level.activeTextBox = textBoxQty;
	}

	public void backSpaceTyped() {
		Game.level.player.inventory.buttonClearSearch.click();
	}

	@Override
	public void textChanged(TextBox textBox) {
		if (textBox == textBoxSearch) {
			filter(inventoryFilterBy, false);
			if (otherInventory != null) {
				otherInventory.filter(inventoryFilterBy, false);
			}
		} else if (textBox == textBoxQty) {

		}
	}

	public boolean isMouseOverTextBox(int mouseX, int mouseY) {
		return textBoxSearch.isMouseOver(mouseX, mouseY);
	}

	public boolean clickTextBox(int mouseX, int mouseY) {
		return textBoxSearch.click(mouseX, mouseY);
	}

	public void escapeTyped() {
		if (Level.activeTextBox == textBoxSearch) {
			Level.activeTextBox = null;
		} else if (Level.activeTextBox == textBoxQty) {
			Level.activeTextBox = null;
		} else {
			Game.level.openCloseInventory();
		}
	}

	public void updateRealtime(int delta) {
		if (animation != null && !animation.completed) {
			animation.update(delta);
		} else {
		}
	}
}
