package com.marklynch.level;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;

import com.marklynch.Game;
import com.marklynch.GameCursor;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.ai.utils.Move;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.beastiary.BestiaryKnowledge;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.faction.FactionList;
import com.marklynch.level.constructs.gameover.GameOver;
import com.marklynch.level.constructs.inventory.ComparisonDisplay;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.constructs.inventory.SquareInventory;
import com.marklynch.level.constructs.journal.Journal;
import com.marklynch.level.constructs.journal.MarkerList;
import com.marklynch.level.constructs.journal.QuestList;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.constructs.power.PowerBleed;
import com.marklynch.level.constructs.power.PowerHealRanged;
import com.marklynch.level.constructs.power.PowerHealSelf;
import com.marklynch.level.constructs.power.PowerHealTouch;
import com.marklynch.level.constructs.power.PowerInferno;
import com.marklynch.level.constructs.power.PowerPoisonBlast;
import com.marklynch.level.constructs.power.PowerSuperPeek;
import com.marklynch.level.constructs.power.PowerUnlock;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.InanimateObjectToAddOrRemove;
import com.marklynch.objects.MapMarker;
import com.marklynch.objects.Vein;
import com.marklynch.objects.Wall;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionHide;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.actions.ActionStopHiding;
import com.marklynch.objects.actions.ActionUsePower;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Player;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.ActivityLogger;
import com.marklynch.ui.PinWindow;
import com.marklynch.ui.TextBox;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.popups.FullScreenTextBox;
import com.marklynch.ui.popups.Notification;
import com.marklynch.ui.popups.PopupMenu;
import com.marklynch.ui.popups.PopupMenuActionButton;
import com.marklynch.ui.popups.PopupMenuSelectAction;
import com.marklynch.ui.popups.PopupMenuSelectObject;
import com.marklynch.ui.quickbar.QuickBar;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;
import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;

public class Level {

	public static boolean loggedThisTurn = false;

	// Controls for tutorial buttons
	public static boolean wHasBeenPressed = true;
	public static boolean ctrlActionHasBeenPressed = true;
	public static boolean altActionHasBeenPressed = true;
	public static boolean shiftActionHasBeenPressed = true;
	// public static boolean ctrlWHasBeenPressed = false;
	// public static boolean altWHasBeenPressed = false;
	// public static boolean shiftWHasBeenPressed = false;
	public static boolean aHasBeenPressed = true;
	// public static boolean ctrlAasBeenPressed = false;
	// public static boolean altAHasBeenPressed = false;
	// public static boolean shiftAHasBeenPressed = false;
	public static boolean sHasBeenPressed = true;
	// public static boolean ctrlSHasBeenPressed = false;
	// public static boolean altSHasBeenPressed = false;
	// public static boolean shiftSHasBeenPressed = false;
	public static boolean dHasBeenPressed = true;
	// public static boolean ctrlDHasBeenPressed = false;
	// public static boolean altDHasBeenPressed = false;
	// public static boolean shiftDHasBeenPressed = false;

	public int width;
	public int height;
	public Square[][] squares;
	public ArrayList<Area> areas = new ArrayList<Area>();
	public ArrayList<Structure> structures;
	public transient ArrayList<AIRoutineUtils> ais = new ArrayList<AIRoutineUtils>();
	public transient ArrayList<Inventory> openInventories = new ArrayList<Inventory>();
	public static transient Journal journal = new Journal();
	public static transient QuestList fullQuestList = new QuestList();
	public static transient MarkerList markerList = new MarkerList();
	public static transient FactionList factions = new FactionList();
	public static transient GameOver gameOver = new GameOver();
	public static transient HashMap<Integer, BestiaryKnowledge> bestiaryKnowledgeCollection = new HashMap<Integer, BestiaryKnowledge>();

	// public Vector<Actor> actors;
	public transient Player player;
	public transient Actor activeActor;
	public transient ArrayListMappedInanimateObjects<GameObject> inanimateObjectsOnGround;

	public ArrayList<PopupMenuSelectObject> popupMenuObjects = new ArrayList<PopupMenuSelectObject>();
	public ArrayList<PopupMenuSelectAction> popupMenuActions = new ArrayList<PopupMenuSelectAction>();
	public ArrayList<PopupMenuSelectObject> popupMenuHighlightObjects = new ArrayList<PopupMenuSelectObject>();
	static FullScreenTextBox fullScreenTextBox = null;
	public static TextBox activeTextBox = null;
	public ArrayList<Notification> notifications = new ArrayList<Notification>();
	public ArrayList<PinWindow> pinWindows = new ArrayList<PinWindow>();

	public Conversation conversation;
	public transient LevelButton clearNotificationsButton;
	public transient LevelButton endTurnButton;
	public transient LevelButton centerButton;
	public transient LevelButton mapButton;
	public transient LevelButton showHideLocationIconsButton;
	public transient LevelButton showHideLogButton;
	public transient LevelButton playButton;
	public transient LevelButton pauseButton;
	public transient LevelButton editorButton;
	public transient ArrayList<Button> buttons;
	public transient Button poisonBlastButton;

	public boolean showLog = true;

	public transient static int turn = 1;
	// public ArrayList<Faction> factions;
	public transient Faction currentFactionMoving;
	public transient int currentFactionMovingIndex;
	public transient Stack<Move> undoList;
	public ActivityLogger activityLogger;
	public QuickBar quickBar;

	public transient GameCursor gameCursor;

	// public transient boolean showTurnNotification = true;
	// public transient boolean waitingForPlayerClickToBeginTurn = true;

	public transient boolean ended = false;
	public Texture textureUndiscovered;
	public ArrayList<InanimateObjectToAddOrRemove> inanimateObjectsToAdd = new ArrayList<InanimateObjectToAddOrRemove>();
	public ArrayList<GameObject> inanimateObjectsOnGroundToRemove = new ArrayList<GameObject>();
	public ArrayList<Actor> actorsToRemove = new ArrayList<Actor>();

	public enum LevelMode {
		LEVEL_MODE_NORMAL, LEVEL_MODE_CAST, LEVEL_SELECT_TELEPORT_SQUARE
	}

	public LevelMode levelMode = LevelMode.LEVEL_MODE_NORMAL;
	public Power selectedPower = null;

	public boolean cameraFollow = false;

	// java representation of a grid??
	// 2d array?

	public int day = 1;
	String dayString = "1";
	public int hour = 6;
	String hourString = "6";
	int minute = 0;
	String minuteString = "0";
	int second = 0;
	String secondString = "0";
	public String timeString = "Day 1, 06:00";

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		squares = new Square[width][height];

		activityLogger = new ActivityLogger();
		quickBar = new QuickBar();
		undoList = new Stack<Move>();
		buttons = new ArrayList<Button>();
		gameCursor = new GameCursor();

		// textureUndiscovered =
		// ResourceUtils.getGlobalImage("undiscovered_small.png");
		Square.loadStaticImages();
		GameObject.loadStaticImages();
		Actor.loadStaticImages();
		Action.loadActionImages();
		Effect.loadEffectImages();
		Wall.loadStaticImages();
		Vein.loadStaticImages();
		MapMarker.loadStaticImages();
		Inventory.loadStaticImages();
		ComparisonDisplay.loadStaticImages();
		Journal.loadStaticImages();
		ActivityLogger.loadStaticImages();
		GameOver.loadStaticImages();

		structures = new ArrayList<Structure>();

		factions.makeFactions();
		inanimateObjectsOnGround = new ArrayListMappedInanimateObjects<GameObject>();

		initGrid(this.squares, this.width, this.height);

		clearNotificationsButton = new LevelButton(0, 0, 100, 20, "end_turn_button.png", "end_turn_button.png", "CLEAR",
				true, true, Color.BLACK, Color.WHITE, "Clear All Notifications");
		clearNotificationsButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.level.notifications.clear();
			}
		});

		Button doNothing2Button = new LevelButton(110f, 40f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"nothing", false, false, Color.BLACK, Color.WHITE, "Nothing to see here");
		doNothing2Button.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(doNothing2Button);

		Button nothingButton = new LevelButton(220f, 40f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"nothing", false, false, Color.BLACK, Color.WHITE, "Nothing to see here");
		nothingButton.setClickListener(new ClickListener() {
			@Override
			public void click() {

			}
		});
		nothingButton.enabled = true;
		buttons.add(nothingButton);

		Button seeAllButton = new LevelButton(330f, 40f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"FULL VIS", false, false, Color.BLACK, Color.WHITE, "DEV - Reveal all squares");
		seeAllButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.fullVisiblity = !Game.fullVisiblity;
			}
		});
		seeAllButton.enabled = true;
		buttons.add(seeAllButton);

		Button showAILinesButton = new LevelButton(440f, 40f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"AI LINES", false, false, Color.BLACK, Color.WHITE, "DEV - Draw AI target Lines");
		showAILinesButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.showAILines = !Game.showAILines;
			}
		});
		showAILinesButton.enabled = true;
		buttons.add(showAILinesButton);

		Button showTriggerLinesButton = new LevelButton(550f, 40f, 100f, 30f, "undo_button.png",
				"undo_button_disabled.png", "TRGR LINES", false, false, Color.BLACK, Color.WHITE,
				"DEV - Draw trigger lines between switches etc.");
		showTriggerLinesButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.showTriggerLines = !Game.showTriggerLines;
			}
		});
		showTriggerLinesButton.enabled = true;
		buttons.add(showTriggerLinesButton);

		Button highlightRestrictedButton = new LevelButton(660f, 40f, 100f, 30f, "undo_button.png",
				"undo_button_disabled.png", "RSTRCTD SQRS", false, false, Color.BLACK, Color.WHITE,
				"DEV - Highlight restricted squares");
		highlightRestrictedButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.redHighlightOnRestrictedSquares = !Game.redHighlightOnRestrictedSquares;
			}
		});
		highlightRestrictedButton.enabled = true;
		buttons.add(highlightRestrictedButton);

		Button highlightOPathButton = new LevelButton(770f, 40f, 100f, 30f, "undo_button.png",
				"undo_button_disabled.png", "PATH SQRS", false, false, Color.BLACK, Color.WHITE,
				"DEV - Highlight path");
		highlightOPathButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.highlightPath = !Game.highlightPath;
				if (Game.highlightPath == false) {
					if (Game.level.player.playerPathToDraw != null) {
						for (Square square : Game.level.player.playerPathToDraw.squares) {
							square.highlight = false;
						}
					}
				}
			}
		});
		highlightOPathButton.enabled = true;
		buttons.add(highlightOPathButton);

		Button infernoButton = new LevelButton(110f, 80f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"INFERNO", false, false, Color.BLACK, Color.WHITE, "DEV - Cast INFERNO");
		infernoButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				pausePlayer();
				Level.this.levelMode = LevelMode.LEVEL_MODE_CAST;
				Level.this.selectedPower = new PowerInferno(Game.level.player);
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
			}
		});
		infernoButton.enabled = true;
		buttons.add(infernoButton);

		Button superPeekButton = new LevelButton(220f, 80f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"SUPERPEEK", false, false, Color.BLACK, Color.WHITE, "DEV - Cast SUPERPEEK");
		superPeekButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				pausePlayer();
				Level.this.levelMode = LevelMode.LEVEL_MODE_CAST;
				Level.this.selectedPower = new PowerSuperPeek(Game.level.player);
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
			}
		});
		superPeekButton.enabled = true;
		buttons.add(superPeekButton);

		Button healSelfButton = new LevelButton(330f, 80f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"HEAL SELF", false, false, Color.BLACK, Color.WHITE, "DEV - Cast HEAL SELF");
		healSelfButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				pausePlayer();
				new ActionUsePower(Game.level.player, Game.level.player.squareGameObjectIsOn,
						new PowerHealSelf(Game.level.player)).perform();
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
			}
		});
		healSelfButton.enabled = true;
		buttons.add(healSelfButton);

		Button healTouchButton = new LevelButton(440f, 80f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"HEAL TOUCH", false, false, Color.BLACK, Color.WHITE, "DEV - Cast HEAL TOUCH");
		healTouchButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				pausePlayer();
				Level.this.levelMode = LevelMode.LEVEL_MODE_CAST;
				Level.this.selectedPower = new PowerHealTouch(Game.level.player);
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
			}
		});
		healTouchButton.enabled = true;
		buttons.add(healTouchButton);

		Button healRangedButton = new LevelButton(550f, 80f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"HEAL RANGED", false, false, Color.BLACK, Color.WHITE, "DEV - Cast HEAL RANGED");
		healRangedButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				pausePlayer();
				Level.this.levelMode = LevelMode.LEVEL_MODE_CAST;
				Level.this.selectedPower = new PowerHealRanged(Game.level.player);
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
			}
		});
		healRangedButton.enabled = true;
		buttons.add(healRangedButton);

		Button unlockButton = new LevelButton(110f, 120f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"UNLOCK", false, false, Color.BLACK, Color.WHITE, "DEV - Cast UNLOCK");
		unlockButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				pausePlayer();
				Level.this.levelMode = LevelMode.LEVEL_MODE_CAST;
				Level.this.selectedPower = new PowerUnlock(Game.level.player);
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
			}
		});
		unlockButton.enabled = true;
		buttons.add(unlockButton);

		poisonBlastButton = new LevelButton(220f, 120f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"POISON", false, false, Color.BLACK, Color.WHITE, "DEV - Cast POISON BLAST");
		poisonBlastButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				pausePlayer();
				Level.this.levelMode = LevelMode.LEVEL_MODE_CAST;
				Level.this.selectedPower = new PowerPoisonBlast(Game.level.player);
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
			}
		});
		poisonBlastButton.enabled = true;

		Button bleedButton = new LevelButton(330f, 120f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"BLEED", false, false, Color.BLACK, Color.WHITE, "DEV - Cast BLEED");
		bleedButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				pausePlayer();
				Level.this.levelMode = LevelMode.LEVEL_MODE_CAST;
				Level.this.selectedPower = new PowerBleed(Game.level.player);
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
			}
		});
		bleedButton.enabled = true;
		buttons.add(bleedButton);

		showHideLogButton = new LevelButton(activityLogger.width, 64f, 70f, 30f, "undo_button.png",
				"undo_button_disabled.png", " LOG [L] <", true, true, Color.BLACK, Color.WHITE,
				"Show/Hide the Log - [L]");
		showHideLogButton.setClickListener(new ClickListener() {
			@Override
			public void click() {

				if (activityLogger.x != 0 && activityLogger.x != -activityLogger.width)
					return;

				showLog = !showLog;
			}
		});
		showHideLogButton.enabled = true;
		buttons.add(showHideLogButton);

		// UI buttons
		Button inventoryButton = new LevelButton(110f, 400f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"INVENTORY [I]", false, false, Color.BLACK, Color.WHITE, "Open your Inventory - [I]");
		inventoryButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				openCloseInventory();
			}
		});
		inventoryButton.enabled = true;
		buttons.add(inventoryButton);

		// UI buttons
		Button journalButton = new LevelButton(110f, 360f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"ADVENTURES [J]", false, false, Color.BLACK, Color.WHITE, "Take a look at your Journal - [J]");
		journalButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				openCloseJournal();
			}
		});
		journalButton.enabled = true;
		buttons.add(journalButton);

		centerButton = new LevelButton(110f, 440f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"CENTER [Q]", false, false, Color.BLACK, Color.WHITE, "Center view on self - [Q]");
		centerButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				cameraFollow = true;
				centerToSquare = true;
				squareToCenterTo = Game.level.player.squareGameObjectIsOn;
			}
		});
		centerButton.enabled = true;
		buttons.add(centerButton);

		endTurnButton = new LevelButton(110f, 480f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"WAIT [SPACE]", false, false, Color.BLACK, Color.WHITE,
				"Wait a turn. You can also hold SPACE to wait longer and watch the world go by");
		endTurnButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				UserInputLevel.waitPressed(false, false);
				// new ActionWait(player,
				// player.squareGameObjectIsOn).perform();
			}
		});
		buttons.add(endTurnButton);

		mapButton = new LevelButton(110f, 520f, 100f, 30f, "end_turn_button.png", "end_turn_button.png", "MAP [M]",
				false, false, Color.BLACK, Color.WHITE, "Zoom out to Map and back again - [M]");
		mapButton.setClickListener(new ClickListener() {
			@Override
			public void click() {

				if (zoomToMap || zoomFromMap)
					return;

				if (Game.zoom >= 0.1) {
					zoomToMap = true;
					nonMapZoomLevelIndex = Game.zoomLevelIndex;
				} else {
					zoomFromMap = true;
				}
			}
		});
		buttons.add(mapButton);

		showHideLocationIconsButton = new LevelButton(110f, 560f, 100f, 30f, "end_turn_button.png",
				"end_turn_button.png", "LOCATION ICONS", false, false, Color.BLACK, Color.WHITE,
				"Show/Hide location icons");
		showHideLocationIconsButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				drawLocationIcons = !drawLocationIcons;
			}
		});

		playButton = new LevelButton(30f, 20f, 20f, 20f, "end_turn_button.png", "end_turn_button.png", "  >", false,
				true, Color.GRAY, Color.WHITE, "Let time pass - [SPACE]");
		playButton.setClickListener(new ClickListener() {
			@Override
			public void click() {

				// if (zoomToMap || zoomFromMap)
				// return;
				//
				// if (Game.zoom >= 0.1) {
				// zoomToMap = true;
				// nonMapZoomLevelIndex = Game.zoomLevelIndex;
				// } else {
				// zoomFromMap = true;
				// }
			}
		});
		buttons.add(playButton);

		pauseButton = new LevelButton(60f, 20f, 20f, 20f, "end_turn_button.png", "end_turn_button.png", "  ||", false,
				true, Color.BLACK, Color.WHITE, "Pause time");
		pauseButton.setClickListener(new ClickListener() {
			@Override
			public void click() {

				// if (zoomToMap || zoomFromMap)
				// return;
				//
				// if (Game.zoom >= 0.1) {
				// zoomToMap = true;
				// nonMapZoomLevelIndex = Game.zoomLevelIndex;
				// } else {
				// zoomFromMap = true;
				// }
			}
		});
		buttons.add(pauseButton);
	}

	// values for zoom animation
	boolean zoomToMap;
	boolean zoomFromMap;
	int nonMapZoomLevelIndex;

	// Location icons when fully zoomed out
	boolean drawLocationIcons = true;

	// values for center animation
	public boolean centerToSquare;
	public Square squareToCenterTo;

	// values for square flash animation
	public static boolean flashSquare;
	public static Square squareToFlash;
	public static int flashSquareCounter = 0;
	public static final int flashSquareFrequency = 200;// ms
	public static final int flashSquareTotalTime = 1200;// ms

	// values for square gameObject animation
	public static boolean flashGameObject;
	public static GameObject gameObjectToFlash;
	public static int flashGameObjectCounter = 0;
	public static final int flashGameObjectFrequency = 200;// ms
	public static final int flashGameObjectTotalTime = 1200;// ms

	public void openCloseInventory() {
		if (Game.level.openInventories.size() > 0) {
			for (Inventory inventory : (ArrayList<Inventory>) Game.level.openInventories.clone()) {
				inventory.close();
			}
		} else {
			Game.level.player.inventory.setMode(Inventory.INVENTORY_MODE.MODE_NORMAL);
			Game.level.player.inventory.open();
			Game.level.player.inventory.filter(Inventory.inventoryFilterBy, false);
			Game.level.player.inventory.sort(Inventory.inventorySortBy, false, false);
			pausePlayer();
			// Game.level.openInventories.add(Game.level.player.inventory);
		}
		closeAllPopups();
	}

	public void openCloseJournal() {

		if (journal.showing) {
			journal.close();
		} else {
			journal.open();
			pausePlayer();
		}
		closeAllPopups();
	}

	public void openCloseGameOver() {

		if (gameOver.showing) {
			gameOver.close();
		} else {
			gameOver.open();
			pausePlayer();
		}
		closeAllPopups();
	}

	public static void openFullScreenTextBox(FullScreenTextBox fullScreenTextBox) {
		Level.fullScreenTextBox = fullScreenTextBox;
		Level.activeTextBox = Level.fullScreenTextBox.textBox;
		pausePlayer();
		closeAllPopups();
	}

	public static void closeFullScreenTextBox() {

		if (activeTextBox == fullScreenTextBox.textBox) {
			Level.activeTextBox = null;
		}
		Level.fullScreenTextBox = null;
	}

	public static void closeAllPopups() {
		Game.level.popupMenuObjects.clear();
		Game.level.popupMenuActions.clear();

	}

	public void postLoad() {
		activityLogger = new ActivityLogger();
		undoList = new Stack<Move>();
		buttons = new ArrayList<Button>();
		gameCursor = new GameCursor();

		this.inanimateObjectsOnGround = new ArrayListMappedInanimateObjects<GameObject>();
		this.openInventories = new ArrayList<Inventory>();

		// buildings = new ArrayList<Building>();

		// for (Structure building : structures) {
		// for (int i = building.gridX1; i <= building.gridX2; i++) {
		// for (int j = building.gridY1; j <= building.gridY2; j++) {
		// squares[i][j].structureSquareIsIn = building;
		// }
		// }
		// }

		for (Faction faction : factions) {
			faction.postLoad();
		}

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				squares[i][j].postLoad1();
			}
		}

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				squares[i][j].postLoad2();
			}
		}

		// for (GameObject inanimateObject : inanimateObjectsOnGround) {
		// inanimateObject.postLoad();
		// }
		//
		// script.postLoad();
		//
		// for (AIRoutineUtils ai : ais) {
		// ai.postLoad();
		// }

		// showTurnNotification = true;
		// waitingForPlayerClickToBeginTurn = true;
	}

	public void loadImages() {

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				squares[i][j].loadImages();
			}
		}

		for (GameObject inanimateObject : inanimateObjectsOnGround) {
			inanimateObject.loadImages();
		}

		for (Faction faction : factions) {
			faction.loadImages();
		}
	}

	private void initGrid(final Square[][] squares, final int width, final int height) {

		InventorySquare.imageTexture = getGlobalImage("dialogbg.png");

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				squares[i][j] = new Square(i, j, "grass.png", Square.GRASS_TEXTURE, 1, 0, new SquareInventory(), false);
			}
		}

		// Attempt to thread below

		// int sectorSize = width / 4;
		//
		// final int start1 = 0;
		// final int end1 = start1 + sectorSize;
		// final int start2 = end1;
		// final int end2 = start2 + sectorSize;
		// final int start3 = end2;
		// final int end3 = start3 + sectorSize;
		// final int start4 = end3;
		// final int end4 = width;
		//
		//
		//
		// InventorySquare.imageTexture = getGlobalImage("dialogbg.png");
		// final Texture grassTexture = getGlobalImage("grass.png");
		//
		// Thread thread1 = new Thread() {
		// @Override
		// public void run() {
		// System.out.println("Thread1 start");
		//
		// for (int i = start1; i < end1; i++) {
		// for (int j = 0; j < height; j++) {
		// // System.out.println("Thread1 i = " + i + ", j = " +
		// // j);
		// squares[i][j] = new Square(i, j, "grass.png", grassTexture, 1, 0, new
		// SquareInventory(), false);
		// }
		// }
		// System.out.println("Thread1 end");
		//
		// }
		// };
		// thread1.start();
		//
		// Thread thread2 = new Thread() {
		// @Override
		// public void run() {
		//
		// for (int i = start2; i < end2; i++) {
		// for (int j = 0; j < height; j++) {
		// squares[i][j] = new Square(i, j, "grass.png", grassTexture, 1, 0, new
		// SquareInventory(), false);
		// }
		// }
		// System.out.println("Thread2 end");
		//
		// }
		// };
		// thread2.start();
		//
		// Thread thread3 = new Thread() {
		// @Override
		// public void run() {
		//
		// for (int i = start3; i < end3; i++) {
		// for (int j = 0; j < height; j++) {
		// squares[i][j] = new Square(i, j, "grass.png", grassTexture, 1, 0, new
		// SquareInventory(), false);
		// }
		// }
		// System.out.println("Thread3 end");
		//
		// }
		// };
		// thread3.start();
		//
		// Thread thread4 = new Thread() {
		// @Override
		// public void run() {
		//
		// for (int i = start4; i < end4; i++) {
		// for (int j = 0; j < height; j++) {
		// squares[i][j] = new Square(i, j, "grass.png", grassTexture, 1, 0, new
		// SquareInventory(), false);
		// }
		// }
		// System.out.println("Thread4 end");
		//
		// }
		// };
		// thread4.start();
		//
		// try {
		// Thread.sleep(3000);
		// // thread1.join();
		// // thread2.join();
		// // thread3.join();
		// // thread4.join();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}

	public static int gridX1Bounds;
	public static int gridX2Bounds;
	public static int gridY1Bounds;
	public static int gridY2Bounds;

	public void drawBackground() {

		// Squares
		gridX1Bounds = (int) (((Game.windowWidth / 2) - Game.getDragXWithOffset() - (Game.windowWidth / 2) / Game.zoom)
				/ Game.SQUARE_WIDTH);
		if (gridX1Bounds < 0)
			gridX1Bounds = 0;

		gridX2Bounds = (int) (gridX1Bounds + ((Game.windowWidth / Game.SQUARE_WIDTH)) / Game.zoom) + 2;
		if (gridX2Bounds >= width)
			gridX2Bounds = width;

		gridY1Bounds = (int) (((Game.windowHeight / 2) - Game.getDragYWithOffset()
				- (Game.windowHeight / 2) / Game.zoom) / Game.SQUARE_HEIGHT);
		if (gridY1Bounds < 0)
			gridY1Bounds = 0;

		gridY2Bounds = (int) (gridY1Bounds + ((Game.windowHeight / Game.SQUARE_HEIGHT)) / Game.zoom) + 2;
		if (gridY2Bounds >= height)
			gridY2Bounds = height;

		if (Game.highlightPath) {

			for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
				for (int j = gridY1Bounds; j < gridY2Bounds; j++) {
					squares[i][j].highlight = false;
				}
			}

			for (Faction faction : factions) {
				for (Actor actor : faction.actors) {
					if (actor.path != null && actor.path.complete) {
						for (Square square : actor.path.squares) {
							square.highlight = true;
						}
					}
				}
			}
		}

		for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
			for (int j = gridY1Bounds; j < gridY2Bounds; j++) {
				// is it better to bind once and draw all the same ones?
				squares[i][j].draw1();
			}
		}

		Game.activeBatch.flush();
		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.setIdentity();
		Game.activeBatch.updateUniforms();

		// Quest lines
		// Journal.drawQuestMarkersForOffScreenObjectives();

		// Draw lines for the popup windows
		for (PinWindow window : this.pinWindows) {
			window.drawLine();
		}
		Game.activeBatch.flush();
		// if (levelMode == LevelMode.LEVEL_MODE_CAST) {
		// for (int i = 0; i < 10; i++) {
		// if (selectedPower.hasRange(i)) {
		// ArrayList<Square> squaresToHighlight =
		// Game.level.player.getAllSquaresWithinDistance(i,
		// Game.level.player.squareGameObjectIsOn);
		// for (Square squareToHighlight : squaresToHighlight) {
		// if (squareToHighlight.visibleToPlayer) {
		// squareToHighlight.drawHighlight();
		// }
		// }
		//
		// }
		// }
		// }

	}

	public void drawForeground() {

		// Background decorations

		for (int j = gridY1Bounds; j < gridY2Bounds; j++) {

			Game.activeBatch.flush();
			for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
				// is it better to bind once and draw all the same ones?
				for (GameObject gameObject : squares[i][j].inventory.getGameObjects()) {
					gameObject.draw1(); // HERE
				}
			}

			Game.activeBatch.flush();
			for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
				// is it better to bind once and draw all the same ones?
				for (GameObject gameObject : squares[i][j].inventory.getGameObjects()) {
					gameObject.draw2();
				}
			}
			Game.activeBatch.flush();
			for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
				// is it better to bind once and draw all the same ones?
				for (GameObject gameObject : squares[i][j].inventory.getGameObjects()) {
					gameObject.draw3();
				}
			}

			Game.activeBatch.flush();
		}

		Journal.drawQuestsMarkersForVisibleOnScreenObjectives();

		// Squares
		for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
			for (int j = gridY1Bounds; j < gridY2Bounds; j++) {
				// is it better to bind once and draw all the same ones?
				squares[i][j].draw2();
			}
		}

		for (Structure structure : structures) {
			structure.draw2();
		}

	}

	public static GameObject teleportee = null;

	public void drawUI() {

		for (int j = gridY1Bounds; j < gridY2Bounds; j++) {

			Game.activeBatch.flush();
			for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
				// is it better to bind once and draw all the same ones?
				for (GameObject gameObject : squares[i][j].inventory.getGameObjects()) {
					gameObject.drawUI(); // HERE
				}
			}
			Game.activeBatch.flush();
		}

		// Draw actions on sqrs.

		player.squareGameObjectIsOn.drawActionThatWillBePerformed(false);

		if (Player.playerTargetSquare != null)
			if (Player.playerTargetAction != null)
				Player.playerTargetSquare.drawAction(Player.playerTargetAction, false);
			else
				Player.playerTargetSquare.drawX(false);

		if (player.squareGameObjectIsOn.getSquareToLeftOf() != null) {
			Action action = player.squareGameObjectIsOn.getSquareToLeftOf().drawActionThatWillBePerformed(false);
			if (!aHasBeenPressed && action != null && action instanceof ActionMove && action.legal && action.enabled) {
				player.squareGameObjectIsOn.getSquareToLeftOf().drawKey(Square.A_TEXTURE);
			} else if (!shiftActionHasBeenPressed) {
				Action secondaryAction = player.squareGameObjectIsOn.getSquareToLeftOf()
						.getSecondaryActionForTheSquareOrObject(Game.level.player);
				if (secondaryAction != null && secondaryAction.enabled && secondaryAction.legal)
					player.squareGameObjectIsOn.getSquareToLeftOf().drawKey(Square.SHIFT_A_TEXTURE);
			}
		}

		if (player.squareGameObjectIsOn.getSquareToRightOf() != null) {
			Action action = player.squareGameObjectIsOn.getSquareToRightOf().drawActionThatWillBePerformed(false);
			if (!dHasBeenPressed && action != null && action instanceof ActionMove && action.legal && action.enabled) {
				player.squareGameObjectIsOn.getSquareToRightOf().drawKey(Square.D_TEXTURE);
			} else if (!shiftActionHasBeenPressed) {
				Action secondaryAction = player.squareGameObjectIsOn.getSquareToRightOf()
						.getSecondaryActionForTheSquareOrObject(Game.level.player);
				if (secondaryAction != null && secondaryAction.enabled && secondaryAction.legal)
					player.squareGameObjectIsOn.getSquareToRightOf().drawKey(Square.SHIFT_D_TEXTURE);
			}
		}

		if (player.squareGameObjectIsOn.getSquareAbove() != null) {
			Action action = player.squareGameObjectIsOn.getSquareAbove().drawActionThatWillBePerformed(false);
			if (!wHasBeenPressed && action != null && action instanceof ActionMove && action.legal && action.enabled) {
				player.squareGameObjectIsOn.getSquareAbove().drawKey(Square.W_TEXTURE);
			} else if (!shiftActionHasBeenPressed) {
				Action secondaryAction = player.squareGameObjectIsOn.getSquareAbove()
						.getSecondaryActionForTheSquareOrObject(Game.level.player);
				if (secondaryAction != null && secondaryAction.enabled && secondaryAction.legal)
					player.squareGameObjectIsOn.getSquareAbove().drawKey(Square.SHIFT_W_TEXTURE);
			}
		}

		if (player.squareGameObjectIsOn.getSquareBelow() != null) {
			Action action = player.squareGameObjectIsOn.getSquareBelow().drawActionThatWillBePerformed(false);
			if (!sHasBeenPressed && action != null && action instanceof ActionMove && action.legal && action.enabled) {
				player.squareGameObjectIsOn.getSquareBelow().drawKey(Square.S_TEXTURE);
			} else if (!shiftActionHasBeenPressed) {
				Action secondaryAction = player.squareGameObjectIsOn.getSquareBelow()
						.getSecondaryActionForTheSquareOrObject(Game.level.player);
				if (secondaryAction != null && secondaryAction.enabled && secondaryAction.legal)
					player.squareGameObjectIsOn.getSquareBelow().drawKey(Square.SHIFT_S_TEXTURE);
			}
		}

		// In attack mode, draw attackable sqrs.
		if (Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU)) {
			ArrayList<Square> attackableSquares = new ArrayList<Square>();

			if (Game.level.player == null || !(Game.level.player.equipped instanceof Weapon)) {
				attackableSquares.addAll(Game.level.player.getAllSquaresAtDistance(1));
			} else {
				Weapon weapon = (Weapon) Game.level.player.equipped;
				for (int i = (int) weapon.getEffectiveMinRange(); i <= weapon.getEffectiveMaxRange(); i++) {
					attackableSquares.addAll(Game.level.player.getAllSquaresAtDistance(i));

				}
			}

			for (Square attackableSquare : attackableSquares) {
				if (attackableSquare.visibleToPlayer) {
					attackableSquare.drawAttackHighlight(attackableSquares);
				}
			}
		}

		if (openInventories.size() == 0 && Game.buttonHoveringOver == null && Game.squareMouseIsOver != null) {

			if (levelMode == LevelMode.LEVEL_SELECT_TELEPORT_SQUARE) {
				Game.squareMouseIsOver.drawX(false);
			} else if (levelMode == LevelMode.LEVEL_MODE_CAST) {

				// Highlight sqrs you can cast on
				if (!selectedPower.hasRange(Integer.MAX_VALUE)) {
					for (int i = 0; i < 10; i++) {
						if (selectedPower.hasRange(i)) {
							Vector<Square> squaresToHighlight = Game.level.player.getAllSquaresAtDistance(i);
							for (Square squareToHighlight : squaresToHighlight) {
								if (squareToHighlight.visibleToPlayer) {
									squareToHighlight.drawHighlight();
								}
							}

						}
					}
				}

				// Draw power icon on sqrs / or x if out of range
				ActionUsePower actionUsePower = new ActionUsePower(this.player, Game.squareMouseIsOver, selectedPower);
				if (actionUsePower.enabled) {
					ArrayList<Square> affectedSquares = selectedPower.getAffectedSquares(Game.squareMouseIsOver);
					for (Square affectedSquare : affectedSquares) {
						affectedSquare.drawPower(selectedPower);
					}
				} else {
					Game.squareMouseIsOver.drawX(false);
				}
				Game.squareMouseIsOver.drawCursor();
			} else {

				// NORMAL MODE

				// if (Player.playerTargetSquare == null) {
				Game.squareMouseIsOver.drawCursor();
				Game.squareMouseIsOver.drawActionThatWillBePerformed(false);
				// }
			}

		}

		if (popupMenuActions.size() == 1) {
			((PopupMenuActionButton) popupMenuActions.get(0).highlightedButton).drawSound();
		}

		Game.activeBatch.flush();
		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.setIdentity();
		Game.activeBatch.updateUniforms();

		player.drawStaticUI();

		for (GameObject mapMarker : inanimateObjectsOnGround.get(MapMarker.class)) {
			((MapMarker) mapMarker).drawStaticUI();
		}

		if (openInventories.size() == 0 && Game.buttonHoveringOver == null && Game.squareMouseIsOver != null) {

			if (levelMode == LevelMode.LEVEL_SELECT_TELEPORT_SQUARE) {
			} else if (levelMode == LevelMode.LEVEL_MODE_CAST) {
			} else {
				// NORMAL MODE

				// if (Player.playerTargetSquare == null) {
				Action action = Game.squareMouseIsOver.drawActionThatWillBePerformed(true);
				// }
			}

		}

		// Game.activeBatch.flush();
		// Matrix4f view = Game.activeBatch.getViewMatrix();
		// view.setIdentity();
		// Game.activeBatch.updateUniforms();

		// Quest lines
		Journal.drawQuestsMarkersForNonVisibleOnScreenObjectives();
		Journal.drawQuestMarkersForOffScreenObjectives();
		Journal.drawOfScreenMapMarkers();

		Game.activeBatch.flush();

		// MAP ICONS
		if (Game.zoomLevelIndex >= Game.MAP_MODE_ZOOM_LEVEL_INDEX) {

			view.setIdentity();
			Game.activeBatch.updateUniforms();
			try {
				Game.activeBatch.setShader(SpriteBatch.getDefaultShader());
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
			Game.activeBatch.setColor(1, 1, 1, 1);

			if (drawLocationIcons) {
				for (Area area : Game.level.areas) {
					area.drawUI();
				}
				for (Structure structure : Game.level.structures) {
					structure.drawUI();
				}
			}
		}
		Game.activeBatch.flush();

		// if (Game.buttonHoveringOver == null && Game.squareMouseIsOver !=
		// null)
		// Game.squareMouseIsOver.drawCursor();

		if (!Game.editorMode) {
			for (Button button : buttons) {
				button.draw();
			}

			if (Game.zoomLevelIndex >= Game.MAP_MODE_ZOOM_LEVEL_INDEX) {
				showHideLocationIconsButton.draw();
			}
		}

		// Current objective quest
		journal.drawTrackedStuffInTopRight();

		// Turn text
		if (currentFactionMoving != null) {
			TextUtils.printTextWithImages(Game.windowWidth - 150, 80, Integer.MAX_VALUE, true, null,
					new Object[] { "TURN " + turn });
		}

		// Zoom
		TextUtils.printTextWithImages(Game.windowWidth - 150, 100, Integer.MAX_VALUE, true, null,
				new Object[] { "Zoom " + Game.zoom });

		// FPS
		TextUtils.printTextWithImages(Game.windowWidth - 150, 120, Integer.MAX_VALUE, true, null,
				new Object[] { "FPS " + Game.displayFPS });

		// TIME
		TextUtils.printTextWithImages(Game.windowWidth - 150, 140, Integer.MAX_VALUE, true, null,
				new Object[] { timeString });

		if (conversation != null)

			conversation.drawStaticUI();
		else // if (showLog)

		{
			quickBar.drawStaticUI();
			activityLogger.drawStaticUI();
		}

		for (PinWindow popupPinned : pinWindows) {
			popupPinned.drawStaticUI();
		}

		// Notifications
		float notificationsHeight = Notification.border;
		Notification.x = Game.halfWindowWidth - Notification.halfWidth;
		Notification.textX = Notification.x + Notification.border;
		Notification.closeButtonX = Notification.x + Notification.width - Notification.closeButtonWidth;
		for (Notification notification : notifications) {
			notification.y = notification.closeButton.y = notificationsHeight;
			notification.textY = notification.y + 4;
			notification.closeButton.x = Notification.closeButtonX;
			notification.draw();
			notificationsHeight += notification.height + Notification.border;
		}

		if (notifications.size() > 0) {
			clearNotificationsButton.x = Notification.x;
			clearNotificationsButton.y = notificationsHeight - Notification.border + 8;
			clearNotificationsButton.draw();
		}

		for (Inventory inventory : openInventories) {
			inventory.drawStaticUI();
		}

		if (journal.showing) {
			journal.drawStaticUI();
		}

		if (gameOver.showing) {
			gameOver.drawStaticUI();
		}

		for (PopupMenu popupTooltip : popupMenuHighlightObjects) {
			popupTooltip.draw();
		}

		for (PopupMenu popup : popupMenuObjects) {
			popup.draw();
		}

		for (PopupMenu popup : popupMenuActions) {
			popup.draw();
		}

		if (fullScreenTextBox != null)
			fullScreenTextBox.draw();

		if (Game.buttonHoveringOver != null)
			Game.buttonHoveringOver.drawTooltip();

	}

	// To stop java.util.ConcurrentModificationException in inanimate object
	// loop
	// public static ArrayList<Action> actionQueueForInanimateObjects = new
	// ArrayList<Action>();

	public static long lastUpdate = 0;

	public void update(int delta) {
		lastUpdate = System.currentTimeMillis();
		addRemoveObjectToFromGround();

		for (int j = gridY1Bounds; j < gridY2Bounds; j++) {
			for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
				// is it better to bind once and draw all the same ones?
				for (GameObject gameObject : squares[i][j].inventory.getGameObjects()) {
					gameObject.updateRealtime(delta);
				}
			}
		}

		player.updateRealtime(delta);

		player.inventory.updateRealtime(delta);

		// update map zoom animation
		if (zoomToMap) {
			Game.zoom -= 0.002 * delta;
			if (Game.zoom <= Game.zoomLevels[Game.zoomLevels.length - 1]) {
				Game.zoomLevelIndex = Game.lastZoomLevelIndex = Game.zoomLevels.length - 1;
				Game.zoom = Game.zoomLevels[Game.zoomLevelIndex];
				zoomToMap = false;
			}
		} else if (zoomFromMap) {
			Game.zoom += 0.002 * delta;
			if (Game.zoom >= Game.zoomLevels[nonMapZoomLevelIndex]) {
				Game.zoomLevelIndex = Game.lastZoomLevelIndex = nonMapZoomLevelIndex;
				Game.zoom = Game.zoomLevels[nonMapZoomLevelIndex];
				zoomFromMap = false;
			}
		}

		// update map zoom animation
		if (centerToSquare) {

			float idealDragX = (-squareToCenterTo.xInGridPixels) + Game.halfWindowWidth - Game.HALF_SQUARE_WIDTH;
			float idealDragY = (-squareToCenterTo.yInGridPixels) + Game.halfWindowHeight - Game.HALF_SQUARE_HEIGHT;

			float diffX = idealDragX - Game.dragX;
			float diffY = idealDragY - Game.dragY;
			float totalDiff = Math.abs(diffX) + Math.abs(diffY);

			if (totalDiff <= delta * 16f) {
				Game.dragX = idealDragX;
				Game.dragY = idealDragY;
				centerToSquare = false;
				Level.flashSquare = true;
				Level.squareToFlash = squareToCenterTo;
				Level.flashSquareCounter = 0;
			} else {
				float toMoveX = diffX / totalDiff;
				float toMoveY = diffY / totalDiff;
				Game.dragX += toMoveX * delta * 16f;
				Game.dragY += toMoveY * delta * 16f;
			}
		}

		// Flash square
		if (flashSquare) {
			flashSquareCounter += delta;
			if (flashSquareCounter >= flashSquareTotalTime) {
				squareToFlash.flash = false;
				flashSquare = false;
			} else if ((flashSquareCounter / flashSquareFrequency) % 2 == 0) {
				squareToFlash.flash = true;
			} else {
				squareToFlash.flash = false;
			}
		}

		// Flash gameObject
		if (flashGameObject) {
			flashGameObjectCounter += delta;
			if (flashGameObjectCounter >= flashGameObjectTotalTime) {
				gameObjectToFlash.flash = false;
				flashGameObject = false;
			} else if ((flashGameObjectCounter / flashGameObjectFrequency) % 2 == 0) {
				gameObjectToFlash.flash = true;
			} else {
				gameObjectToFlash.flash = false;
			}
		}

		// update log animation
		if (showLog == true) {
			if (activityLogger.x < 0) {
				activityLogger.x += (int) (1.20 * delta);
				showHideLogButton.x += (int) (1.20 * delta);
				if (activityLogger.x >= 0) {
					activityLogger.x = 0;
					showHideLogButton.x = activityLogger.width;
					showHideLogButton.textParts = new Object[] { new StringWithColor("LOG [L] <", Color.WHITE) };
				}
			}
		} else {
			if (activityLogger.x > -activityLogger.width) {
				activityLogger.x -= (int) (1.20 * delta);
				showHideLogButton.x -= (int) (1.20 * delta);

				if (activityLogger.x <= -activityLogger.width) {
					activityLogger.x = -activityLogger.width;
					showHideLogButton.x = 0;
					showHideLogButton.textParts = new Object[] { new StringWithColor("LOG [L] >", Color.WHITE) };
				}
			}
		}

		if (activeTextBox != null) {
			activeTextBox.updateRealtime(delta);
		}

		if (Player.playerTargetActor != null) {
			Player.playerTargetSquare = Player.playerTargetActor.squareGameObjectIsOn;
			if (player.straightLineDistanceTo(Player.playerTargetSquare) <= 1) {
				pausePlayer();
			}
		}

		if (currentFactionMoving != factions.player && (!Game.level.player.animationsBlockingAI())) {
			currentFactionMoving.update(delta);
		} else if (Game.level.player.primaryAnimation.completed && Game.level.player.playerTargetAction != null
				&& Game.level.player.playerTargetAction.recheck()) {
			Game.level.player.playerTargetAction.perform();
			pausePlayer();
		}
		// Auto move player
		else if (Game.level.player.primaryAnimation.completed && Player.playerTargetSquare != null) {

			Player.playerPathToMove = Game.level.player.getPathTo(Player.playerTargetSquare);
			if (Player.playerPathToMove == null || Player.playerPathToMove.squares == null
					|| Player.playerPathToMove.squares.size() == 0) {
				if (!player.playerTargetSquare.inventory.canShareSquare()) {

					Object[] objects = new Object[] { "Theres a ",
							player.playerTargetSquare.inventory.getGameObjectThatCantShareSquare(), " there!" };
					notifications.add(new Notification(objects, Notification.NotificationType.MISC, null));
					Game.level.logOnScreen(new ActivityLog(objects));
				} else {
					Object[] objects = new Object[] { "There's no available path" };
					notifications.add(new Notification(objects, Notification.NotificationType.MISC, null));
					Game.level.logOnScreen(new ActivityLog(objects));
				}
				pausePlayer();
				return;
			}

			Square squareToMoveTo = Game.level.player.playerPathToMove.squares.get(0);
			Action action;

			HidingPlace hidingPlace = (HidingPlace) squareToMoveTo.inventory.getGameObjectOfClass(HidingPlace.class);
			if (hidingPlace != null && hidingPlace.remainingHealth > 0) {
				if (player.hiding && player.squareGameObjectIsOn == squareToMoveTo) {
					action = new ActionStopHiding(player, hidingPlace);
				} else {
					action = new ActionHide(player, hidingPlace);
				}
			} else {
				action = new ActionMove(Game.level.player, squareToMoveTo, true);

			}

			if (!action.enabled) {
				Object[] objects = new Object[] { "Path blocked by ",
						squareToMoveTo.inventory.getGameObjectThatCantShareSquare(), "!" };
				notifications.add(new Notification(objects, Notification.NotificationType.MISC, null));
				Game.level.logOnScreen(new ActivityLog(new Object[] { objects }));
				pausePlayer();
			} else if (!action.legal && !player.squareGameObjectIsOn.restricted && Player.playerFirstMove == false) {
				Object[] objects = new Object[] { "Stopped before illegal action!" };
				notifications.add(new Notification(objects, Notification.NotificationType.MISC, null));
				Game.level.logOnScreen(new ActivityLog(new Object[] { objects }));
				pausePlayer();
			} else {
				action.perform();
				Player.playerFirstMove = false;
				if (player.squareGameObjectIsOn == Player.playerTargetSquare) {
					pausePlayer();
				} else {
					highlightPlayButton();
				}
			}
		} else if (Game.level.fullScreenTextBox != null) {
			return;
		} else if (journal.showing) {
			return;
		} else if (gameOver.showing) {
			return;
		} else if (Game.level.openInventories.size() != 0) {
			return;
		} else if (Game.level.conversation != null) {
			return;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) == true && Game.level.player.primaryAnimation.completed) {
			highlightPlayButton();
			UserInputLevel.waitPressed(false, true);
		} else if ((Keyboard.isKeyDown(Keyboard.KEY_UP) == true || Keyboard.isKeyDown(Keyboard.KEY_W) == true)
				&& Game.level.player.primaryAnimation.completed) {
			highlightPlayButton();
			UserInputLevel.upPressed(false, true);
		} else if ((Keyboard.isKeyDown(Keyboard.KEY_DOWN) == true || Keyboard.isKeyDown(Keyboard.KEY_S) == true)
				&& Game.level.player.primaryAnimation.completed) {
			highlightPlayButton();
			UserInputLevel.downPressed(false, true);
		} else if ((Keyboard.isKeyDown(Keyboard.KEY_LEFT) == true || Keyboard.isKeyDown(Keyboard.KEY_A) == true)
				&& Game.level.player.primaryAnimation.completed) {
			highlightPlayButton();
			UserInputLevel.leftPressed(false, true);
		} else if ((Keyboard.isKeyDown(Keyboard.KEY_RIGHT) == true || Keyboard.isKeyDown(Keyboard.KEY_D) == true)
				&& Game.level.player.primaryAnimation.completed) {
			highlightPlayButton();
			UserInputLevel.rightPressed(false, true);
		} else if (Game.level.player.primaryAnimation.completed) {
			highlightPauseButton();
		}
	}

	public void highlightPlayButton() {
		playButton.setTextColor(Color.WHITE);
		playButton.buttonColor = Color.BLACK;
		pauseButton.setTextColor(Color.WHITE);
		pauseButton.buttonColor = Color.GRAY;
	}

	public void highlightPauseButton() {
		playButton.setTextColor(Color.WHITE);
		playButton.buttonColor = Color.GRAY;
		pauseButton.setTextColor(Color.WHITE);
		pauseButton.buttonColor = Color.BLACK;
	}

	public static void pausePlayer() {
		Player.playerPathToMove = null;
		Player.playerTargetSquare = null;
		Player.playerTargetAction = null;
		Player.playerTargetActor = null;
		Game.level.highlightPauseButton();
	}

	public void dragToFollowPlayer() {
		if (Game.level.player.squareGameObjectIsOn.xInGrid > Game.level.player.lastSquare.xInGrid) {
			Game.dragX -= Game.SQUARE_WIDTH;
		} else if (Game.level.player.squareGameObjectIsOn.xInGrid < Game.level.player.lastSquare.xInGrid) {
			Game.dragX += Game.SQUARE_WIDTH;
		} else if (Game.level.player.squareGameObjectIsOn.yInGrid > Game.level.player.lastSquare.yInGrid) {
			Game.dragY -= Game.SQUARE_HEIGHT;
		} else if (Game.level.player.squareGameObjectIsOn.yInGrid < Game.level.player.lastSquare.yInGrid) {
			Game.dragY += Game.SQUARE_HEIGHT;
		}

	}

	public Button getButtonFromMousePosition(float mouseX, float mouseY, float alteredMouseX, float alteredMouseY) {

		if (fullScreenTextBox != null)
			return null;

		if (gameOver.showing) {
			for (Button button : gameOver.buttons) {
				if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
					return button;
			}

			return null;
		}

		if (journal.showing) {
			for (Button button : journal.tabButtons) {
				if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
					return button;
			}

			if (journal.mode == Journal.MODE.LOG || journal.mode == Journal.MODE.CONVERSATION) {
				for (Button button : journal.questButtons) {
					if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
						return button;
				}
			}

			if (journal.mode == Journal.MODE.LOG) {
				for (Button button : journal.logLinks) {
					if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
						return button;
				}
			} else if (journal.mode == Journal.MODE.CONVERSATION) {
				for (Button button : journal.conversationLinks) {
					if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
						return button;
				}
			} else if (journal.mode == Journal.MODE.MARKER) {
				for (Button button : journal.markerButtons) {
					if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
						return button;
				}
				for (Button button : journal.markerLinksInJournal) {
					if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
						return button;
				}

			}

			return null;

		}

		for (Inventory inventory : openInventories) {
			for (Button button : inventory.buttons) {
				if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
					return button;
			}

			if (!Game.level.popupMenuActions.isEmpty()) {

				for (int i = popupMenuActions.size() - 1; i >= 0; i--) {
					for (Button button : popupMenuActions.get(i).buttons) {
						if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
							return button;
					}

				}
			}
			return null;
		}

		// On screen objectives links

		for (Button button : journal.questLinksTopRight) {
			if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
				return button;
		}
		for (Button button : journal.objectiveLinksTopRight) {
			if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
				return button;
		}
		for (Button button : journal.markerLinksTopRight) {
			if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
				return button;
		}

		// Notifications
		for (Notification notification : notifications) {
			if (notification.mouseOverCloseButton(mouseX, Game.windowHeight - mouseY))
				return notification.closeButton;

			for (Button button : notification.links) {
				if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
					return button;
			}
		}

		for (int i = pinWindows.size() - 1; i >= 0; i--) {
			if (pinWindows.get(i).mouseOverCloseButton(mouseX, Game.windowHeight - mouseY))
				return pinWindows.get(i).closeButton;
			if (pinWindows.get(i).mouseOverMinimiseButton(mouseX, Game.windowHeight - mouseY))
				return pinWindows.get(i).minimiseButton;
			if (pinWindows.get(i).mouseOverInvisibleMinimiseButton(mouseX, Game.windowHeight - mouseY))
				return pinWindows.get(i).titleBarButton;
		}

		if (conversation != null) {
			for (Button button : conversation.currentConversationPart.windowSelectConversationResponse.responseButtons) {
				if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
					return button;
			}
			for (Button button : conversation.currentConversationPart.windowSelectConversationResponse.standardButtons) {
				if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
					return button;
			}
			for (Button button : conversation.currentConversationPart.links) {
				if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
					return button;
			}

			return null;
		}

		for (ActivityLog log : activityLogger.logs) {

			for (Button button : log.links) {
				if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
					return button;
			}
		}

		// for (PopupTooltip popupTooltip : popupTooltips) {
		// if (popupTooltip.calculateIfPointInBoundsOfButton(mouseX,
		// Game.windowHeight - mouseY))
		// return popupTooltip;
		//
		// }

		if (!Game.level.popupMenuActions.isEmpty()) {

			for (int i = popupMenuActions.size() - 1; i >= 0; i--) {
				for (Button button : popupMenuActions.get(i).buttons) {
					if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
						return button;
				}

			}
		}

		if (!Game.level.popupMenuObjects.isEmpty()) {

			for (int i = popupMenuObjects.size() - 1; i >= 0; i--) {
				for (Button button : popupMenuObjects.get(i).buttons) {
					if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
						return button;
				}

			}
		}

		if (!Game.level.popupMenuHighlightObjects.isEmpty()) {

			for (int i = popupMenuHighlightObjects.size() - 1; i >= 0; i--) {
				for (Button button : popupMenuHighlightObjects.get(i).buttons) {
					if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
						return button;
				}

			}
		}

		for (Button button : buttons) {
			if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
				return button;
		}

		if (Game.zoomLevelIndex >= Game.MAP_MODE_ZOOM_LEVEL_INDEX) {
			if (showHideLocationIconsButton.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
				return showHideLocationIconsButton;
		}

		if (notifications.size() > 0
				&& clearNotificationsButton.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
			return clearNotificationsButton;

		if (activeActor != null && activeActor.faction == factions.player)
			return this.activeActor.getButtonFromMousePosition(alteredMouseX, alteredMouseY);

		return null;
	}

	public PinWindow getWindowFromMousePosition(float mouseX, float mouseY, float alteredMouseX, float alteredMouseY) {

		if (openInventories.size() != 0 || journal.showing || gameOver.showing)
			return null;

		for (int i = pinWindows.size() - 1; i >= 0; i--) {
			if (pinWindows.get(i).isMouseOver((int) mouseX, (int) (Game.windowHeight - mouseY)))
				return pinWindows.get(i);
		}
		return null;
	}

	public FullScreenTextBox getPopupTextBoxFromMousePosition(float mouseX, float mouseY, float alteredMouseX,
			float alteredMouseY) {

		if (fullScreenTextBox != null
				&& fullScreenTextBox.isMouseOver((int) mouseX, (int) (Game.windowHeight - mouseY)))
			return fullScreenTextBox;

		return null;
	}

	public void endTurn() {
		addRemoveObjectToFromGround();
		// this.logOnScreen(new ActivityLog(new Object[] { currentFactionMoving,
		// " ended turn " + this.turn }));

		// Pre end turn
		if (currentFactionMovingIndex == 0) {

			// Time
			second += 20;
			if (second >= 60) {
				second = second - 60;
				minute++;
				if (minute >= 60) {
					minute = minute - 60;
					hour++;
					if (hour == 24) {
						hour = 0;
						day++;
					}
				}
			}

			if (second < 10) {
				secondString = "0" + second;
			} else {
				secondString = "" + second;
			}

			if (minute < 10) {
				minuteString = "0" + minute;
			} else {
				minuteString = "" + minute;
			}

			if (hour < 10) {
				hourString = "0" + hour;
			} else {
				hourString = "" + hour;
			}

			timeString = "Day " + dayString + ", " + hourString + ":" + minuteString;// +
																						// ":"
																						// +
			// secondString;

			// If hiding in a place, add it's effects
			if (player.hidingPlace != null) {
				for (Effect effect : player.hidingPlace.effectsFromInteracting) {
					player.addEffect(effect.makeCopy(player.hidingPlace, player));
				}
			}

			player.activateEffects();

			// Update player inventory
			ArrayList<GameObject> toRemove = new ArrayList<GameObject>();
			for (GameObject gameObjectInInventory : player.inventory.getGameObjects()) {
				gameObjectInInventory.update(0);
				if (gameObjectInInventory.remainingHealth <= 0) {
					toRemove.add(gameObjectInInventory);
				}
			}
			for (GameObject gameObject : toRemove) {
				// if (gameObject.destroyedBy instanceof EffectBurning) {
				// player.inventory.replace(gameObject,
				// Templates.ASH.makeCopy(null, player));
				// } else {
				player.inventory.remove(gameObject);
				// }
			}

			// notifications.clear();

			for (Quest quest : fullQuestList)
				quest.update();

		}

		for (Faction faction : factions) {
			for (Actor actor : faction.actors) {
				actor.distanceMovedThisTurn = 0;
				actor.hasAttackedThisTurn = false;
				// actor.wasSwappedWithThisTurn = false;
			}
		}

		// removeWalkingHighlight();
		// removeWeaponsThatCanAttackHighlight();

		if (activeActor != null)
			activeActor.unselected();
		activeActor = null;
		currentFactionMovingIndex++;
		if (currentFactionMovingIndex >= factions.size())
			currentFactionMovingIndex = 0;

		while (factions.get(currentFactionMovingIndex).actors.size() == 0) {
			currentFactionMovingIndex++;
			if (currentFactionMovingIndex >= factions.size()) {
				currentFactionMovingIndex = 0;
			}
		}

		currentFactionMoving = factions.get(currentFactionMovingIndex);
		if (currentFactionMovingIndex == 0) {
			this.turn++;
			loggedThisTurn = false;

			Game.level.activeActor = player;
			// Game.level.activeActor.equippedWeapon =
			// Game.level.activeActor.getWeaponsInInventory().get(0);
			// Actor.calculateReachableSquares();
			if (player.peekSquare != null) {
				player.calculateVisibleSquares(player.peekSquare);
			} else {
				player.calculateVisibleSquares(Game.level.activeActor.squareGameObjectIsOn);
			}
			player.discoveryCheck();

			ArrayList<GameObject> attackersToRemoveFromList = new ArrayList<GameObject>();
			for (GameObject gameObject : player.getAttackers()) {
				if (gameObject.remainingHealth <= 0) {
					attackersToRemoveFromList.add(gameObject);
				}
			}

			for (GameObject actor : attackersToRemoveFromList) {
				player.getAttackers().remove(actor);
			}

			player.clearActions();
		}

		undoList.clear();

		if (activeActor == player) {
			for (GameObject inanimateObject : inanimateObjectsOnGround) {
				inanimateObject.update(0);
			}
		}

	}

	public void addRemoveObjectToFromGround() {

		if (inanimateObjectsToAdd.size() == 0 && inanimateObjectsOnGroundToRemove.size() == 0
				&& actorsToRemove.size() == 0) {
			return;
		}

		for (InanimateObjectToAddOrRemove inanimateObjectToAdd : inanimateObjectsToAdd) {
			inanimateObjectToAdd.square.inventory.add(inanimateObjectToAdd.gameObject);
		}
		inanimateObjectsToAdd.clear();

		for (GameObject gameObject : inanimateObjectsOnGroundToRemove) {
			if ((inanimateObjectsOnGround).contains(gameObject))
				inanimateObjectsOnGround.remove(gameObject);
			if (gameObject.squareGameObjectIsOn != null)
				gameObject.squareGameObjectIsOn.inventory.remove(gameObject);
		}
		inanimateObjectsOnGroundToRemove.clear();

		for (Actor actor : actorsToRemove) {
			actor.faction.actors.remove(actor);
			if (actor.group != null)
				actor.group.removeMember(actor);
		}
		actorsToRemove.clear();

		if (Game.level.player.inventory.groundDisplay != null) {
			Game.level.player.inventory.groundDisplay.refreshGameObjects();
		}

	}

	public void logOnScreen(ActivityLog stringToLog) {
		if (!Level.loggedThisTurn) {
			Level.loggedThisTurn = true;
			Game.level.logOnScreen(new ActivityLog(new Object[] { "-----TURN " + Level.turn + "-----" }));
		}
		activityLogger.addActivityLog(stringToLog);
	}

	public boolean shouldLog(GameObject... gameObjects) {

		for (GameObject gameObject : gameObjects) {

			if (gameObject == null)
				continue;

			if (gameObject == Game.level.player)
				return true;

			if (gameObject.squareGameObjectIsOn != null && gameObject.squareGameObjectIsOn.visibleToPlayer) {
				return true;
			}

			if (gameObject.inventoryThatHoldsThisObject != null
					&& gameObject.inventoryThatHoldsThisObject == Game.level.player.inventory) {
				return true;
			}
		}

		return false;
	}

	// public void showTurnNotification() {
	// showTurnNotification = true;
	// if (this.currentFactionMoving != factions.player)
	// new hideTurnNotificationThread().start();
	// }

	// public class hideTurnNotificationThread extends Thread {
	//
	// @Override
	// public void run() {
	// try {
	// Thread.sleep(2000);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// showTurnNotification = false;
	// }
	// }

	private void removeLastLog() {

		activityLogger.removeLastActivityLog();
	}

	public void end() {
		this.ended = true;
	}

	public void changeSize(int newWidth, int newHeight) {
		Square[][] newSquares = new Square[newWidth][newHeight];
		initGrid(newSquares, newWidth, newHeight);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (i < newWidth && j < newHeight) {
					// Transfer old squares over to new grid if they fit
					newSquares[i][j] = squares[i][j];
				} else {
					// Delete old squares if they don't fit
					if (squares[i][j].inventory.size() == 0) {

					} else {
						for (int k = 0; k < squares[i][j].inventory.size(); k++) {
							if (squares[i][j].inventory.get(k) instanceof Actor) {
								Actor actor = (Actor) squares[i][j].inventory.get(k);
								actor.faction.actors.remove(actor);
							} else {
								inanimateObjectsOnGround.remove(squares[i][j].inventory.get(k));
							}
						}
					}
				}
			}
		}

		this.width = newWidth;
		this.height = newHeight;
		this.squares = newSquares;
	}

	public Actor findActorFromGUID(String guid) {
		for (Faction faction : factions) {
			for (Actor actor : faction.actors) {
				if (actor.guid.equals(guid)) {
					return actor;
				}
			}
		}
		return null;
	}

	public GameObject findObjectFromGUID(String guid) {
		for (GameObject object : inanimateObjectsOnGround) {
			if (object.guid.equals(guid)) {
				return object;
			}
		}
		for (Faction faction : factions) {
			for (Actor actor : faction.actors) {
				if (actor.guid.equals(guid)) {
					return actor;
				}
			}
		}
		return null;
	}

	public Faction findFactionFromGUID(String guid) {
		for (Faction faction : factions) {
			if (faction.guid.equals(guid)) {
				return faction;
			}
		}
		return null;
	}

	public Square findSquareFromGUID(String guid) {

		ArrayList<Square> squares = new ArrayList<Square>();
		for (Square[] squareArray : Game.level.squares) {
			for (Square square : squareArray) {
				if (square.guid.equals(guid))
					return square;
			}
		}
		return null;
	}

	public void resize() {
		if (openInventories.size() != 0)
			openInventories.get(0).resize1();
		if (journal.showing)
			journal.resize();
		if (gameOver.showing)
			gameOver.resize();

		if (conversation != null)
			conversation.resize();

	}

	public void addNotification(Notification notificationToAdd) {
		Notification oldNotificationToRemove = null;
		for (Notification notification : notifications) {
			if (notification.equals(notificationToAdd)) {
				oldNotificationToRemove = notification;
				break;
			}
		}
		if (oldNotificationToRemove != null)
			notifications.remove(oldNotificationToRemove);
		notifications.add(notificationToAdd);
	}
}
