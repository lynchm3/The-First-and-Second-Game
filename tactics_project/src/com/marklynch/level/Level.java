package com.marklynch.level;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

import org.lwjgl.util.vector.Matrix4f;

import com.marklynch.Game;
import com.marklynch.GameCursor;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.ai.utils.Move;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBurning;
import com.marklynch.level.constructs.structure.Structure;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.popup.Popup;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.InventorySquare;
import com.marklynch.objects.SquareInventory;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Vein;
import com.marklynch.objects.Wall;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionLoiter;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Projectile;
import com.marklynch.script.Script;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.ActivityLogger;
import com.marklynch.ui.Toast;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Level {

	public int width;
	public int height;
	public Square[][] squares;
	public ArrayList<Structure> structures;
	public Vector<Decoration> decorations;
	public transient Script script;
	public transient ArrayList<AIRoutineUtils> ais = new ArrayList<AIRoutineUtils>();
	public transient ArrayList<Inventory> openInventories = new ArrayList<Inventory>();

	// public Vector<Actor> actors;
	public transient Actor player;
	public transient Actor activeActor;
	public transient ArrayListMappedInanimateObjects<GameObject> inanimateObjectsOnGround;
	public transient ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public transient ArrayList<Projectile> projectilesToRemove = new ArrayList<Projectile>();

	public ArrayList<Popup> popups = new ArrayList<Popup>();
	public Toast toast;
	public Conversation conversation;
	public transient LevelButton endTurnButton;
	public transient LevelButton editorButton;
	public transient ArrayList<Button> buttons;

	public transient int turn = 1;
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
		Action.loadActionImages();
		Effect.loadEffectImages();
		Wall.loadStaticImages();
		Vein.loadStaticImages();

		structures = new ArrayList<Structure>();

		factions = new ArrayList<Faction>();
		inanimateObjectsOnGround = new ArrayListMappedInanimateObjects<GameObject>();

		initGrid(this.squares, this.width, this.height);

		endTurnButton = new LevelButton(110f, 40f, 100f, 30f, "end_turn_button.png", "end_turn_button.png", "END TURN",
				false, false, Color.BLACK, Color.WHITE);
		endTurnButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				new ActionLoiter(player, player.squareGameObjectIsOn).perform();
			}
		});
		buttons.add(endTurnButton);

		Button centerOnPlayerButton = new LevelButton(220f, 40f, 100f, 30f, "undo_button.png",
				"undo_button_disabled.png", "CENTER", false, false, Color.BLACK, Color.WHITE);
		centerOnPlayerButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.dragX = -Game.level.player.squareGameObjectIsOn.xInGrid * Game.SQUARE_WIDTH + 800;
				Game.dragY = -Game.level.player.squareGameObjectIsOn.yInGrid * Game.SQUARE_HEIGHT + 400;
			}
		});
		centerOnPlayerButton.enabled = true;
		buttons.add(centerOnPlayerButton);

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
				"undo_button_disabled.png", "HILITE SQRS", false, false, Color.BLACK, Color.WHITE);
		highlightRestrictedButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.redHighlightOnRestrictedSquares = !Game.redHighlightOnRestrictedSquares;
			}
		});
		highlightRestrictedButton.enabled = true;
		buttons.add(highlightRestrictedButton);
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
				new ActionLoiter(player, player.squareGameObjectIsOn).perform();
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

		Square.loadStaticImages();

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
		int gridX1Bounds = (int) (((Game.windowWidth / 2) - Game.dragX - (Game.windowWidth / 2) / Game.zoom)
				/ Game.SQUARE_WIDTH);
		if (gridX1Bounds < 0)
			gridX1Bounds = 0;

		// + (mouseXinPixels) / Game.zoom);

		int gridX2Bounds = (int) (gridX1Bounds + ((Game.windowWidth / Game.SQUARE_WIDTH)) / Game.zoom) + 2;
		if (gridX2Bounds >= width)
			gridX2Bounds = width - 1;

		int gridY1Bounds = (int) (((Game.windowHeight / 2) - Game.dragY - (Game.windowHeight / 2) / Game.zoom)
				/ Game.SQUARE_HEIGHT);
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
	}

	public void drawForeground() {

		// Background decorations

		// float mouseXTransformed = (((Game.windowWidth / 2) - Game.dragX -
		// (Game.windowWidth / 2) / Game.zoom)
		// + (mouseXinPixels) / Game.zoom);

		// GameObjects and actors
		// int gridX1Bounds = -(int) (Game.dragX / Game.SQUARE_WIDTH) + 1;
		// if (gridX1Bounds < 0)
		// gridX1Bounds = 0;

		int gridX1Bounds = (int) (((Game.windowWidth / 2) - Game.dragX - (Game.windowWidth / 2) / Game.zoom)
				/ Game.SQUARE_WIDTH);
		if (gridX1Bounds < 0)
			gridX1Bounds = 0;

		// + (mouseXinPixels) / Game.zoom);

		int gridX2Bounds = (int) (gridX1Bounds + ((Game.windowWidth / Game.SQUARE_WIDTH)) / Game.zoom) + 2;
		if (gridX2Bounds >= width)
			gridX2Bounds = width - 1;

		int gridY1Bounds = (int) (((Game.windowHeight / 2) - Game.dragY - (Game.windowHeight / 2) / Game.zoom)
				/ Game.SQUARE_HEIGHT);
		if (gridY1Bounds < 0)
			gridY1Bounds = 0;

		int gridY2Bounds = (int) (gridY1Bounds + ((Game.windowHeight / Game.SQUARE_HEIGHT)) / Game.zoom) + 2;
		if (gridY2Bounds >= height)
			gridY2Bounds = height - 1;

		ArrayList<Square> squaresInWindow = new ArrayList<Square>();

		for (int j = gridY1Bounds; j < gridY2Bounds; j++) {
			for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
				// is it better to bind once and draw all the same ones?
				for (GameObject gameObject : squares[i][j].inventory.getGameObjects()) {
					gameObject.draw1();
				}
			}
			for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
				// is it better to bind once and draw all the same ones?
				for (GameObject gameObject : squares[i][j].inventory.getGameObjects()) {
					gameObject.draw2();
				}
			}
			for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
				// is it better to bind once and draw all the same ones?
				for (GameObject gameObject : squares[i][j].inventory.getGameObjects()) {
					gameObject.draw3();
				}
			}
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
		if (Game.inventoryHoveringOver == null && Game.buttonHoveringOver == null && Game.squareMouseIsOver != null) {
			Game.squareMouseIsOver.drawCursor();

			Game.squareMouseIsOver.drawDefaultOrSecondaryAction();
			player.squareGameObjectIsOn.drawDefaultOrSecondaryAction();

			if (player.squareGameObjectIsOn.getSquareToLeftOf() != null)
				player.squareGameObjectIsOn.getSquareToLeftOf().drawDefaultOrSecondaryAction();

			if (player.squareGameObjectIsOn.getSquareToRightOf() != null)
				player.squareGameObjectIsOn.getSquareToRightOf().drawDefaultOrSecondaryAction();

			if (player.squareGameObjectIsOn.getSquareAbove() != null)
				player.squareGameObjectIsOn.getSquareAbove().drawDefaultOrSecondaryAction();

			if (player.squareGameObjectIsOn.getSquareBelow() != null)
				player.squareGameObjectIsOn.getSquareBelow().drawDefaultOrSecondaryAction();

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

		// Static UI (not zoomed)
		for (GameObject gameObject : inanimateObjectsOnGround) {
			gameObject.drawStaticUI();
		}

		// Actors 2
		for (Faction faction : factions) {
			for (Actor actor : faction.actors) {
				actor.drawStaticUI();
			}
		}

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
		else

		{

			activityLogger.drawStaticUI();
		}

		// script
		script.draw();

		for (Inventory inventory : openInventories) {
			inventory.drawStaticUI();
		}

		if (!popups.isEmpty()) {
			for (Popup popup : popups) {
				popup.draw();
			}
		}

	}

	// To stop java.util.ConcurrentModificationException in inanimate object
	// loop
	public static ArrayList<Action> actionQueueForInanimateObjects = new ArrayList<Action>();

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

		for (GameObject inanimateObject : inanimateObjectsOnGround)
			inanimateObject.updateRealtime(0);

		for (Decoration decoration : decorations)
			decoration.update(delta);

		if (!this.script.checkIfBlocking() && currentFactionMoving != factions.get(0)) {
			currentFactionMoving.update(delta);
		}
		// } else if (currentFactionMoving != factions.get(0)) {
		// currentFactionMoving.update(delta);
		// }
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

		for (Button button : this.buttons) {
			if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
				return button;
		}

		if (!Game.level.popups.isEmpty()) {
			for (Popup popup : popups) {
				for (Button button : popup.buttons) {
					if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
						return button;
				}
			}
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
			System.out.println("\nTurn " + turn + "\n");
			Game.level.logOnScreen(new ActivityLog(new Object[] { "-----TURN " + turn + "-----" }));
			Game.level.activeActor = player;
			// Game.level.activeActor.equippedWeapon =
			// Game.level.activeActor.getWeaponsInInventory().get(0);
			// Actor.calculateReachableSquares();

			System.out.println("end turn player = " + player);
			System.out.println("end turn peekSquare = " + player.peekSquare);
			System.out.println("end turn peekingThrough = " + player.peekingThrough);
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
			for (Action action : actionQueueForInanimateObjects) {
				action.perform();
			}
			actionQueueForInanimateObjects.clear();

			ArrayList<GameObject> toRemove = new ArrayList<GameObject>();
			for (GameObject inanimateObject : inanimateObjectsOnGround) {
				if (inanimateObject.remainingHealth <= 0)
					toRemove.add(inanimateObject);
			}
			for (GameObject gameObject : toRemove) {
				inanimateObjectsOnGround.remove(gameObject);
				// gameObject.squareGameObjectIsOn.inventory.remove(gameObject);
				// if (gameObject.destroyedBy instanceof EffectBurning)
				// Templates.ASH.makeCopy(gameObject.squareGameObjectIsOn,
				// null);
				gameObject.squareGameObjectIsOn.inventory.remove(gameObject);
			}

		}

	}

	public void logOnScreen(ActivityLog stringToLog) {
		activityLogger.addActivityLog(stringToLog);
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
