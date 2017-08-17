package com.marklynch.level;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
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
import com.marklynch.level.constructs.bounds.Area;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBurning;
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
import com.marklynch.level.popup.PopupMenu;
import com.marklynch.level.popup.PopupMenuActionButton;
import com.marklynch.level.popup.PopupMenuSelectAction;
import com.marklynch.level.popup.PopupMenuSelectObject;
import com.marklynch.level.popup.PopupTextBox;
import com.marklynch.level.popup.PopupToast;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.InanimateObjectToAddOrRemove;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.InventorySquare;
import com.marklynch.objects.MapMarker;
import com.marklynch.objects.SquareInventory;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Vein;
import com.marklynch.objects.Wall;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionHide;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.actions.ActionStopHiding;
import com.marklynch.objects.actions.ActionUsePower;
import com.marklynch.objects.actions.ActionWait;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Player;
import com.marklynch.objects.weapons.Projectile;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.script.Script;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.ActivityLogger;
import com.marklynch.ui.Toast;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
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
	public Vector<Decoration> decorations;
	public transient Script script;
	public transient ArrayList<AIRoutineUtils> ais = new ArrayList<AIRoutineUtils>();
	public transient ArrayList<Inventory> openInventories = new ArrayList<Inventory>();

	// public Vector<Actor> actors;
	public transient Player player;
	public transient Actor activeActor;
	public transient ArrayListMappedInanimateObjects<GameObject> inanimateObjectsOnGround;
	public transient ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public transient ArrayList<Projectile> projectilesToRemove = new ArrayList<Projectile>();

	public ArrayList<PopupMenuSelectObject> popupMenuObjects = new ArrayList<PopupMenuSelectObject>();
	public ArrayList<PopupMenuSelectAction> popupMenuActions = new ArrayList<PopupMenuSelectAction>();
	public ArrayList<PopupMenuSelectObject> popupMenuHighlightObjects = new ArrayList<PopupMenuSelectObject>();
	public ArrayList<PopupTextBox> popupTextBoxes = new ArrayList<PopupTextBox>();
	public ArrayList<PopupToast> popupToasts = new ArrayList<PopupToast>();

	public Toast toast;
	public Conversation conversation;
	public transient LevelButton endTurnButton;
	public transient LevelButton centerButton;
	public transient LevelButton showHideLogButton;
	public transient LevelButton editorButton;
	public transient ArrayList<Button> buttons;
	public transient Button poisonBlastButton;

	public boolean showLog = true;

	public transient static int turn = 1;
	public ArrayList<Faction> factions;
	public transient Faction currentFactionMoving;
	public transient int currentFactionMovingIndex;
	public transient Stack<Move> undoList;
	public ActivityLogger activityLogger;

	public transient GameCursor gameCursor;

	// public transient boolean showTurnNotification = true;
	// public transient boolean waitingForPlayerClickToBeginTurn = true;

	public transient boolean ended = false;
	public Texture textureUndiscovered;
	public ArrayList<InanimateObjectToAddOrRemove> inanimateObjectsToAdd = new ArrayList<InanimateObjectToAddOrRemove>();
	public ArrayList<GameObject> inanimateObjectsToRemove = new ArrayList<GameObject>();

	public enum LevelMode {
		LEVEL_MODE_NORMAL, LEVEL_MODE_CAST, LEVEL_SELECT_TELEPORT_SQUARE
	}

	public LevelMode levelMode = LevelMode.LEVEL_MODE_NORMAL;
	public Power selectedPower = null;

	public boolean cameraFollow = false;

	// java representation of a grid??
	// 2d array?

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		squares = new Square[width][height];

		activityLogger = new ActivityLogger();
		undoList = new Stack<Move>();
		buttons = new ArrayList<Button>();
		decorations = new Vector<Decoration>();
		gameCursor = new GameCursor();
		script = new Script();

		// textureUndiscovered =
		// ResourceUtils.getGlobalImage("undiscovered_small.png");
		Square.loadStaticImages();
		Actor.loadStaticImages();
		Action.loadActionImages();
		Effect.loadEffectImages();
		Wall.loadStaticImages();
		Vein.loadStaticImages();
		MapMarker.loadStaticImages();

		structures = new ArrayList<Structure>();

		factions = new ArrayList<Faction>();
		inanimateObjectsOnGround = new ArrayListMappedInanimateObjects<GameObject>();

		initGrid(this.squares, this.width, this.height);

		Button doNothing2Button = new LevelButton(110f, 40f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"nothing", false, false, Color.BLACK, Color.WHITE);
		doNothing2Button.setClickListener(new ClickListener() {
			@Override
			public void click() {
			}
		});
		buttons.add(doNothing2Button);

		Button nothingButton = new LevelButton(220f, 40f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"nothing", false, false, Color.BLACK, Color.WHITE);
		nothingButton.setClickListener(new ClickListener() {
			@Override
			public void click() {

			}
		});
		nothingButton.enabled = true;
		buttons.add(nothingButton);

		Button seeAllButton = new LevelButton(330f, 40f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"FULL VIS", false, false, Color.BLACK, Color.WHITE);
		seeAllButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.fullVisiblity = !Game.fullVisiblity;
			}
		});
		seeAllButton.enabled = true;
		buttons.add(seeAllButton);

		Button showAILinesButton = new LevelButton(440f, 40f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"AI LINES", false, false, Color.BLACK, Color.WHITE);
		showAILinesButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.showAILines = !Game.showAILines;
			}
		});
		showAILinesButton.enabled = true;
		buttons.add(showAILinesButton);

		Button showTriggerLinesButton = new LevelButton(550f, 40f, 100f, 30f, "undo_button.png",
				"undo_button_disabled.png", "TRGR LINES", false, false, Color.BLACK, Color.WHITE);
		showTriggerLinesButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.showTriggerLines = !Game.showTriggerLines;
			}
		});
		showTriggerLinesButton.enabled = true;
		buttons.add(showTriggerLinesButton);

		Button highlightRestrictedButton = new LevelButton(660f, 40f, 100f, 30f, "undo_button.png",
				"undo_button_disabled.png", "RSTRCTD SQRS", false, false, Color.BLACK, Color.WHITE);
		highlightRestrictedButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.redHighlightOnRestrictedSquares = !Game.redHighlightOnRestrictedSquares;
			}
		});
		highlightRestrictedButton.enabled = true;
		buttons.add(highlightRestrictedButton);

		Button highlightOPathButton = new LevelButton(770f, 40f, 100f, 30f, "undo_button.png",
				"undo_button_disabled.png", "PATH SQRS", false, false, Color.BLACK, Color.WHITE);
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
				"INFERNO", false, false, Color.BLACK, Color.WHITE);
		infernoButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Player.playerTargetSquare = null;
				Player.playerTargetActor = null;
				Level.this.levelMode = LevelMode.LEVEL_MODE_CAST;
				Level.this.selectedPower = new PowerInferno(Game.level.player);
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
			}
		});
		infernoButton.enabled = true;
		buttons.add(infernoButton);

		Button superPeekButton = new LevelButton(220f, 80f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"SUPERPEEK", false, false, Color.BLACK, Color.WHITE);
		superPeekButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				player.playerTargetSquare = null;
				player.playerTargetActor = null;
				Level.this.levelMode = LevelMode.LEVEL_MODE_CAST;
				Level.this.selectedPower = new PowerSuperPeek(Game.level.player);
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
			}
		});
		superPeekButton.enabled = true;
		buttons.add(superPeekButton);

		Button healSelfButton = new LevelButton(330f, 80f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"HEAL SELF", false, false, Color.BLACK, Color.WHITE);
		healSelfButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				player.playerTargetSquare = null;
				player.playerTargetActor = null;
				new ActionUsePower(Game.level.player, Game.level.player.squareGameObjectIsOn,
						new PowerHealSelf(Game.level.player)).perform();
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
			}
		});
		healSelfButton.enabled = true;
		buttons.add(healSelfButton);

		Button healTouchButton = new LevelButton(440f, 80f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"HEAL TOUCH", false, false, Color.BLACK, Color.WHITE);
		healTouchButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				player.playerTargetSquare = null;
				player.playerTargetActor = null;
				Level.this.levelMode = LevelMode.LEVEL_MODE_CAST;
				Level.this.selectedPower = new PowerHealTouch(Game.level.player);
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
			}
		});
		healTouchButton.enabled = true;
		buttons.add(healTouchButton);

		Button healRangedButton = new LevelButton(550f, 80f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"HEAL RANGED", false, false, Color.BLACK, Color.WHITE);
		healRangedButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				player.playerTargetSquare = null;
				player.playerTargetActor = null;
				Level.this.levelMode = LevelMode.LEVEL_MODE_CAST;
				Level.this.selectedPower = new PowerHealRanged(Game.level.player);
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
			}
		});
		healRangedButton.enabled = true;
		buttons.add(healRangedButton);

		Button unlockButton = new LevelButton(110f, 120f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"UNLOCK", false, false, Color.BLACK, Color.WHITE);
		unlockButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				player.playerTargetSquare = null;
				player.playerTargetActor = null;
				Level.this.levelMode = LevelMode.LEVEL_MODE_CAST;
				Level.this.selectedPower = new PowerUnlock(Game.level.player);
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
			}
		});
		unlockButton.enabled = true;
		buttons.add(unlockButton);

		poisonBlastButton = new LevelButton(220f, 120f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"POISON", false, false, Color.BLACK, Color.WHITE);
		poisonBlastButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				player.playerTargetSquare = null;
				player.playerTargetActor = null;
				Level.this.levelMode = LevelMode.LEVEL_MODE_CAST;
				Level.this.selectedPower = new PowerPoisonBlast(Game.level.player);
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
			}
		});
		poisonBlastButton.enabled = true;

		Button bleedButton = new LevelButton(330f, 120f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"BLEED", false, false, Color.BLACK, Color.WHITE);
		bleedButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				player.playerTargetSquare = null;
				player.playerTargetActor = null;
				Level.this.levelMode = LevelMode.LEVEL_MODE_CAST;
				Level.this.selectedPower = new PowerBleed(Game.level.player);
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
			}
		});
		bleedButton.enabled = true;
		buttons.add(bleedButton);

		showHideLogButton = new LevelButton(activityLogger.width, 40f, 70f, 30f, "undo_button.png",
				"undo_button_disabled.png", " LOG [L] <", true, true, Color.BLACK, Color.WHITE);
		showHideLogButton.setClickListener(new ClickListener() {
			@Override
			public void click() {

				if (activityLogger.x != 0 && activityLogger.x != -activityLogger.width)
					return;

				showLog = !showLog;

				new Thread() {
					@Override
					public void run() {
						super.run();
						if (showLog == true) {
							while (activityLogger.x < 0) {
								activityLogger.x += 2;
								showHideLogButton.x += 2;
								try {
									Thread.sleep(1);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							activityLogger.x = 0;
							showHideLogButton.x = activityLogger.width;
							showHideLogButton.textParts = new Object[] {
									new StringWithColor("LOG [L] <", Color.WHITE) };
						} else {
							while (activityLogger.x > -activityLogger.width) {
								activityLogger.x -= 2;
								showHideLogButton.x -= 2;
								try {
									Thread.sleep(1);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							activityLogger.x = -activityLogger.width;
							showHideLogButton.x = 0;
							showHideLogButton.textParts = new Object[] {
									new StringWithColor("LOG [L] >", Color.WHITE) };
						}
					}
				}.start();
			}
		});
		showHideLogButton.enabled = true;
		buttons.add(showHideLogButton);

		// UI buttons
		Button inventoryButton = new LevelButton(110f, 400f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"INVENTORY [I]", false, false, Color.BLACK, Color.WHITE);
		inventoryButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				openCloseInventory();
			}
		});
		inventoryButton.enabled = true;
		buttons.add(inventoryButton);

		centerButton = new LevelButton(110f, 440f, 100f, 30f, "undo_button.png", "undo_button_disabled.png",
				"CENTER [Q]", false, false, Color.BLACK, Color.WHITE);
		centerButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				cameraFollow = true;
				Game.dragX = (-Game.level.player.squareGameObjectIsOn.xInGrid * Game.SQUARE_WIDTH)
						+ Game.halfWindowWidth - Game.HALF_SQUARE_WIDTH;
				Game.dragY = (-Game.level.player.squareGameObjectIsOn.yInGrid * Game.SQUARE_HEIGHT)
						+ Game.halfWindowHeight - Game.HALF_SQUARE_HEIGHT;
			}
		});
		centerButton.enabled = true;
		buttons.add(centerButton);

		endTurnButton = new LevelButton(110f, 480f, 100f, 30f, "end_turn_button.png", "end_turn_button.png",
				"WAIT [SPACE]", false, false, Color.BLACK, Color.WHITE);
		endTurnButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				UserInputLevel.waitPressed(false, false);
				// new ActionWait(player,
				// player.squareGameObjectIsOn).perform();
			}
		});
		buttons.add(endTurnButton);
	}

	public void openCloseInventory() {
		if (Game.level.openInventories.size() > 0) {
			for (Inventory inventory : (ArrayList<Inventory>) Game.level.openInventories.clone()) {
				inventory.close();
			}
		} else {
			Game.level.player.inventory.open();
			Game.level.player.inventory.filter(Inventory.inventoryFilterBy, false);
			Game.level.player.inventory.sort(Inventory.inventorySortBy, false);
			Game.level.player.inventory.setMode(Inventory.INVENTORY_MODE.MODE_NORMAL);
			// Game.level.openInventories.add(Game.level.player.inventory);
		}
		closeAllPopups();
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

		endTurnButton = new LevelButton(210f, 110f, 200f, 100f, "end_turn_button.png", "end_turn_button.png",
				"END TURN", false, false, Color.BLACK, Color.WHITE);
		endTurnButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				new ActionWait(player, player.squareGameObjectIsOn).perform();
			}
		});
		buttons.add(endTurnButton);

		this.inanimateObjectsOnGround = new ArrayListMappedInanimateObjects<GameObject>();
		this.projectiles = new ArrayList<Projectile>();
		this.projectilesToRemove = new ArrayList<Projectile>();
		this.openInventories = new ArrayList<Inventory>();
		this.script = new Script();

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

		for (Decoration decoration : decorations) {
			decoration.loadImages();
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

	// public void removeWalkingHighlight() {
	// for (int i = 0; i < squares.length; i++) {
	// for (int j = 0; j < squares[0].length; j++) {
	// squares[i][j].reachableBySelectedCharater = false;
	// }
	// }
	// }

	// public void removeWeaponsThatCanAttackHighlight() {
	// for (int i = 0; i < squares.length; i++) {
	// for (int j = 0; j < squares[0].length; j++) {
	// squares[i][j].weaponsThatCanAttack.clear();
	// }
	// }
	// }

	public void drawBackground() {
		// Squares
		int gridX1Bounds = (int) (((Game.windowWidth / 2) - Game.getDragXWithOffset()
				- (Game.windowWidth / 2) / Game.zoom) / Game.SQUARE_WIDTH);
		if (gridX1Bounds < 0)
			gridX1Bounds = 0;

		// + (mouseXinPixels) / Game.zoom);

		int gridX2Bounds = (int) (gridX1Bounds + ((Game.windowWidth / Game.SQUARE_WIDTH)) / Game.zoom) + 2;
		if (gridX2Bounds >= width)
			gridX2Bounds = width - 1;

		int gridY1Bounds = (int) (((Game.windowHeight / 2) - Game.getDragYWithOffset()
				- (Game.windowHeight / 2) / Game.zoom) / Game.SQUARE_HEIGHT);
		if (gridY1Bounds < 0)
			gridY1Bounds = 0;

		int gridY2Bounds = (int) (gridY1Bounds + ((Game.windowHeight / Game.SQUARE_HEIGHT)) / Game.zoom) + 2;
		if (gridY2Bounds >= height)
			gridY2Bounds = height - 1;

		for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
			for (int j = gridY1Bounds; j < gridY2Bounds; j++) {
				// is it better to bind once and draw all the same ones?
				squares[i][j].draw1();
			}
		}

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

		// float mouseXTransformed = (((Game.windowWidth / 2) - Game.getDragX()
		// -
		// (Game.windowWidth / 2) / Game.zoom)
		// + (mouseXinPixels) / Game.zoom);

		// GameObjects and actors
		// int gridX1Bounds = -(int) (Game.getDragX() / Game.SQUARE_WIDTH) + 1;
		// if (gridX1Bounds < 0)
		// gridX1Bounds = 0;

		int gridX1Bounds = (int) (((Game.windowWidth / 2) - Game.getDragXWithOffset()
				- (Game.windowWidth / 2) / Game.zoom) / Game.SQUARE_WIDTH);
		if (gridX1Bounds < 0)
			gridX1Bounds = 0;

		// + (mouseXinPixels) / Game.zoom);

		int gridX2Bounds = (int) (gridX1Bounds + ((Game.windowWidth / Game.SQUARE_WIDTH)) / Game.zoom) + 2;
		if (gridX2Bounds >= width)
			gridX2Bounds = width - 1;

		int gridY1Bounds = (int) (((Game.windowHeight / 2) - Game.getDragYWithOffset()
				- (Game.windowHeight / 2) / Game.zoom) / Game.SQUARE_HEIGHT);
		if (gridY1Bounds < 0)
			gridY1Bounds = 0;

		int gridY2Bounds = (int) (gridY1Bounds + ((Game.windowHeight / Game.SQUARE_HEIGHT)) / Game.zoom) + 2;
		if (gridY2Bounds >= height)
			gridY2Bounds = height - 1;

		ArrayList<Square> squaresInWindow = new ArrayList<Square>();

		for (int j = gridY1Bounds; j < gridY2Bounds; j++) {

			Game.activeBatch.flush();
			for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
				// is it better to bind once and draw all the same ones?
				for (GameObject gameObject : squares[i][j].inventory.getGameObjects()) {
					gameObject.draw1();
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
		// for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
		// for (int j = gridY1Bounds; j < gridY2Bounds; j++) {
		// // is it better to bind once and draw all the same ones?
		// for (GameObject gameObject :
		// squares[i][j].inventory.getGameObjects()) {
		// gameObject.draw2();
		// }
		// }
		// }
		// for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
		// for (int j = gridY1Bounds; j < gridY2Bounds; j++) {
		// // is it better to bind once and draw all the same ones?
		// for (GameObject gameObject :
		// squares[i][j].inventory.getGameObjects()) {
		// gameObject.draw3();
		// }
		// }
		// }

		for (Decoration decoration : decorations) {
			decoration.draw();
		}

		// // Objects 1
		//
		// for (GameObject gameObject : inanimateObjectsOnGround) {
		// gameObject.draw1();
		// }
		//
		// // Actors 1
		// for (Faction faction : factions) {
		// for (Actor actor : faction.actors) {
		// actor.draw1();
		// }
		// }

		// Foreground decorations

		for (Decoration decoration : decorations) {
			decoration.draw2();
		}

		// // Objects 2
		//
		// for (GameObject gameObject : inanimateObjectsOnGround) {
		// gameObject.draw2();
		// }
		//
		// // Actors 2
		// for (Faction faction : factions) {
		// for (Actor actor : faction.actors) {
		// actor.draw2();
		// }
		// }
		//
		// for (GameObject gameObject : inanimateObjectsOnGround) {
		// gameObject.draw3();
		// }

		// draw any projectiles
		for (Projectile projectile : projectiles) {
			projectile.drawForeground();
		}
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

		// Objects 2

		for (GameObject gameObject : inanimateObjectsOnGround) {
			gameObject.drawUI();
		}

		// Actors 2
		for (Faction faction : factions) {
			for (Actor actor : faction.actors) {
				actor.drawUI();
			}
		}

		// if (Game.inventoryHoveringOver == null &&
		// Game.inventorySquareMouseIsOver != null) {
		// Game.inventorySquareMouseIsOver.drawCursor();
		// } else
		//

		// Draw actions on sqrs.

		player.squareGameObjectIsOn.drawAction();

		if (Player.playerTargetSquare != null)
			Player.playerTargetSquare.drawX();

		if (player.squareGameObjectIsOn.getSquareToLeftOf() != null) {
			Action action = player.squareGameObjectIsOn.getSquareToLeftOf().drawAction();
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
			Action action = player.squareGameObjectIsOn.getSquareToRightOf().drawAction();
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
			Action action = player.squareGameObjectIsOn.getSquareAbove().drawAction();
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
			Action action = player.squareGameObjectIsOn.getSquareBelow().drawAction();
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

		if (Game.inventoryHoveringOver == null && Game.buttonHoveringOver == null && Game.squareMouseIsOver != null) {

			if (levelMode == LevelMode.LEVEL_SELECT_TELEPORT_SQUARE) {
				Game.squareMouseIsOver.drawX();
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
					Game.squareMouseIsOver.drawX();
				}
				Game.squareMouseIsOver.drawCursor();
			} else {

				// NORMAL MODE

				if (Player.playerTargetSquare == null) {
					Game.squareMouseIsOver.drawCursor();
					Game.squareMouseIsOver.drawAction();
				}
			}

		}

		if (popupMenuActions.size() == 1) {
			((PopupMenuActionButton) popupMenuActions.get(0).highlightedButton).drawSound();
		}

		// GL11.glColor4f;
		// font60.drawString(0, 0, "YOUR", new Color(1.0f, 0.5f, 0.5f, 0.75f));
		// font60.drawString(0, Game.SQUARE_HEIGHT, "TURN ", new Color(1.0f,
		// 0.5f,
		// 0.5f, 0.75f));
		// GL11.glColor3f(1.0f, 1.0f, 1.0f);

		// zoom end
		// GL11.glPopMatrix();

		// GL11.glColor3f(1.0f, 1.0f, 1.0f);

		// reset the matrix to identity, i.e. "no camera transform"

		Game.activeBatch.flush();
		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.setIdentity();
		Game.activeBatch.updateUniforms();

		for (GameObject gameObject : inanimateObjectsOnGround) {
			gameObject.drawStaticUI();
		}

		// Actors 2
		for (Faction faction : factions) {
			for (Actor actor : faction.actors) {
				actor.drawStaticUI();
			}
		}
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
			Game.activeBatch.setColor(Color.WHITE);

			for (Area area : Game.level.areas) {
				area.drawUI();
			}
			for (Structure structure : Game.level.structures) {
				structure.drawUI();
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
		}

		// Turn text
		if (currentFactionMoving != null) {
			TextUtils.printTextWithImages(new Object[] { currentFactionMoving.name + " turn " + turn },
					Game.windowWidth - 150, 20, Integer.MAX_VALUE, true);
		}

		// Zoom
		TextUtils.printTextWithImages(new Object[] { "Zoom " + Game.zoom }, Game.windowWidth - 150, 40,
				Integer.MAX_VALUE, true);

		// FPS
		TextUtils.printTextWithImages(new Object[] { "FPS " + Game.displayFPS }, Game.windowWidth - 150, 60,
				Integer.MAX_VALUE, true);

		// if (factions.size() > 0 && currentFactionMoving != null) {
		// if (showTurnNotification) {
		// if (currentFactionMoving == factions.get(0)) {
		// TextUtils.printTextWithImages(new Object[] { "Your turn ",
		// this.currentFactionMoving.imageTexture,
		// ", click to continue." }, 500, 500, Integer.MAX_VALUE, true);
		// } else {
		// TextUtils.printTextWithImages(new Object[] {
		// this.currentFactionMoving, "'s turn" }, 500, 500,
		// Integer.MAX_VALUE, true);
		// }
		// }
		// }

		if (conversation != null)

			conversation.drawStaticUI();
		else // if (showLog)

		{

			activityLogger.drawStaticUI();
		}

		// script
		script.draw();

		for (Inventory inventory : openInventories) {
			inventory.drawStaticUI();
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

		if (!popupTextBoxes.isEmpty()) {
			for (PopupTextBox popupTextBox : popupTextBoxes) {
				popupTextBox.draw();
			}
		}
		if (!popupToasts.isEmpty()) {
			for (PopupToast popupToast : popupToasts) {
				popupToast.draw();
			}
		}

	}

	// To stop java.util.ConcurrentModificationException in inanimate object
	// loop
	// public static ArrayList<Action> actionQueueForInanimateObjects = new
	// ArrayList<Action>();

	public void update(int delta) {

		if (conversation != null)
			return;

		// if (this.script.activeScriptEvent != null) {
		script.update(delta);

		// update projectiles
		for (Projectile projectile : projectiles) {
			projectile.update(delta);
		}
		projectiles.removeAll(projectilesToRemove);
		projectilesToRemove.clear();

		int gridX1Bounds = player.squareGameObjectIsOn.xInGrid - player.sight - 1;
		if (gridX1Bounds < 0)
			gridX1Bounds = 0;

		// + (mouseXinPixels) / Game.zoom);

		int gridX2Bounds = player.squareGameObjectIsOn.xInGrid + player.sight + 1;
		if (gridX2Bounds >= width)
			gridX2Bounds = width - 1;

		int gridY1Bounds = player.squareGameObjectIsOn.yInGrid - player.sight - 1;
		if (gridY1Bounds < 0)
			gridY1Bounds = 0;

		int gridY2Bounds = player.squareGameObjectIsOn.yInGrid + player.sight + 1;
		if (gridY2Bounds >= height)
			gridY2Bounds = height - 1;

		for (int j = gridY1Bounds; j < gridY2Bounds; j++) {
			for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
				// is it better to bind once and draw all the same ones?
				for (GameObject gameObject : squares[i][j].inventory.getGameObjects()) {
					gameObject.updateRealtime(delta);
				}
			}
		}

		for (Decoration decoration : decorations)
			decoration.update(delta);

		if (Player.playerTargetActor != null) {
			Player.playerTargetSquare = Player.playerTargetActor.squareGameObjectIsOn;
			if (player.straightLineDistanceTo(Player.playerTargetSquare) <= 1) {
				Player.playerTargetSquare = null;
				Player.playerTargetActor = null;
			}
		}

		if (!this.script.checkIfBlocking() && currentFactionMoving != factions.get(0)) {
			currentFactionMoving.update(delta);
		}
		// Auto move player
		else if (Game.level.player.animation.completed && Player.playerTargetSquare != null) {

			Player.playerPathToMove = Game.level.player.getPathTo(Player.playerTargetSquare);
			if (Player.playerPathToMove == null) {
				if (!player.playerTargetSquare.inventory.canShareSquare()) {

					Object[] objects = new Object[] { "Theres a ",
							player.playerTargetSquare.inventory.getGameObjectThatCantShareSquare(), " there!" };
					popupToasts.add(new PopupToast(objects));
					Game.level.logOnScreen(new ActivityLog(objects));
				} else {
					Object[] objects = new Object[] { "There's no available path" };
					popupToasts.add(new PopupToast(objects));
					Game.level.logOnScreen(new ActivityLog(objects));

				}
				Player.playerTargetSquare = null;
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
				popupToasts.add(new PopupToast(objects));
				Game.level.logOnScreen(new ActivityLog(new Object[] { objects }));
				Player.playerPathToMove = null;
				Player.playerTargetSquare = null;
			} else if (!action.legal && !player.squareGameObjectIsOn.restricted && Player.playerFirstMove == false) {
				Object[] objects = new Object[] { "Stopped before illegal action!" };
				popupToasts.add(new PopupToast(objects));
				Game.level.logOnScreen(new ActivityLog(new Object[] { objects }));
				Player.playerPathToMove = null;
				Player.playerTargetSquare = null;
			} else {
				action.perform();
				Player.playerFirstMove = false;
				if (player.squareGameObjectIsOn == Player.playerTargetSquare) {
					Player.playerPathToMove = null;
					Player.playerTargetSquare = null;
				} else {

					Object[] objects = new Object[] { "WASD, SPACE or CLICK to stop" };
					popupToasts.add(new PopupToast(objects));
				}
			}
		} else if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) == true && Game.level.player.animation.completed) {
			UserInputLevel.waitPressed(false, true);
		} else if ((Keyboard.isKeyDown(Keyboard.KEY_UP) == true || Keyboard.isKeyDown(Keyboard.KEY_W) == true)
				&& Game.level.player.animation.completed) {
			UserInputLevel.upPressed(false, true);
		} else if ((Keyboard.isKeyDown(Keyboard.KEY_DOWN) == true || Keyboard.isKeyDown(Keyboard.KEY_S) == true)
				&& Game.level.player.animation.completed) {
			UserInputLevel.downPressed(false, true);
		} else if ((Keyboard.isKeyDown(Keyboard.KEY_LEFT) == true || Keyboard.isKeyDown(Keyboard.KEY_A) == true)
				&& Game.level.player.animation.completed) {
			UserInputLevel.leftPressed(false, true);
		} else if ((Keyboard.isKeyDown(Keyboard.KEY_RIGHT) == true || Keyboard.isKeyDown(Keyboard.KEY_D) == true)
				&& Game.level.player.animation.completed) {
			UserInputLevel.rightPressed(false, true);
		}
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

		if (conversation != null) {
			for (Button button : conversation.currentConversationPart.windowSelectConversationResponse.buttons) {
				if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
					return button;
			}
		}

		for (Inventory inventory : openInventories) {
			for (Button button : inventory.buttons) {
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

		for (Button button : this.buttons) {
			if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
				return button;
		}

		if (activeActor != null && activeActor.faction == factions.get(0))
			return this.activeActor.getButtonFromMousePosition(alteredMouseX, alteredMouseY);

		return null;
	}

	public Inventory getInventoryFromMousePosition(float mouseX, float mouseY) {

		for (Inventory inventory : this.openInventories) {
			if (inventory.calculateIfPointInBoundsOfInventory(mouseX, Game.windowHeight - mouseY))
				return inventory;
		}

		return null;
	}

	public void endTurn() {
		// this.logOnScreen(new ActivityLog(new Object[] { currentFactionMoving,
		// " ended turn " + this.turn }));

		// Pre end turn
		if (currentFactionMovingIndex == 0) {

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
				if (gameObject.destroyedBy instanceof EffectBurning) {
					player.inventory.replace(gameObject, Templates.ASH.makeCopy(null, player));
				} else {
					player.inventory.remove(gameObject);
				}
			}

			popupToasts.clear();

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

			for (InanimateObjectToAddOrRemove inanimateObjectToAdd : inanimateObjectsToAdd) {
				GameObject gameObject = inanimateObjectToAdd.gameObject;
				inanimateObjectToAdd.square.inventory.add(gameObject);
			}
			inanimateObjectsToAdd.clear();

			for (GameObject gameObject : inanimateObjectsToRemove) {
				inanimateObjectsOnGround.remove(gameObject);
				gameObject.squareGameObjectIsOn.inventory.remove(gameObject);
			}
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
	// if (this.currentFactionMoving != factions.get(0))
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
}
