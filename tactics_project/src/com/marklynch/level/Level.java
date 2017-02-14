package com.marklynch.level;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

import org.lwjgl.util.vector.Matrix4f;

import com.marklynch.Game;
import com.marklynch.GameCursor;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.constructs.Building;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.popup.Popup;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.SquareInventory;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Move;
import com.marklynch.objects.weapons.Projectile;
import com.marklynch.script.Script;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.Toast;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.TextUtils;

public class Level {

	public int width;
	public int height;
	public Square[][] squares;
	public ArrayList<Building> buildings;
	public Vector<Decoration> decorations;
	public transient Script script;
	public transient ArrayList<AIRoutineUtils> ais = new ArrayList<AIRoutineUtils>();
	public transient ArrayList<Inventory> openInventories = new ArrayList<Inventory>();

	// public Vector<Actor> actors;
	public transient Actor player;
	public transient Actor activeActor;
	public transient Vector<GameObject> inanimateObjectsOnGround;
	public transient ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public transient ArrayList<Projectile> projectilesToRemove = new ArrayList<Projectile>();

	public Popup popup;
	public Toast toast;
	public Conversation conversation;
	public transient LevelButton endTurnButton;
	public transient LevelButton undoButton;
	public transient LevelButton editorButton;
	public transient ArrayList<Button> buttons;

	public transient int turn = 1;
	public ArrayList<Faction> factions;
	public transient Faction currentFactionMoving;
	public transient int currentFactionMovingIndex;
	public transient Vector<ActivityLog> logs;
	public transient Stack<Move> undoList;

	public transient GameCursor gameCursor;

	// public transient boolean showTurnNotification = true;
	// public transient boolean waitingForPlayerClickToBeginTurn = true;

	public transient boolean ended = false;

	// java representation of a grid??
	// 2d array?

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		squares = new Square[width][height];

		logs = new Vector<ActivityLog>();
		undoList = new Stack<Move>();
		buttons = new ArrayList<Button>();
		decorations = new Vector<Decoration>();
		gameCursor = new GameCursor();
		script = new Script();

		buildings = new ArrayList<Building>();

		factions = new ArrayList<Faction>();
		inanimateObjectsOnGround = new Vector<GameObject>();
		initGrid(this.squares, this.width, this.height);

		endTurnButton = new LevelButton(210f, 40f, 200f, 30f, "end_turn_button.png", "end_turn_button.png", "END TURN",
				false, false);
		endTurnButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Level.this.endTurn();
			}
		});
		buttons.add(endTurnButton);
		undoButton = new LevelButton(420f, 40f, 200f, 30f, "undo_button.png", "undo_button_disabled.png", "UNDO", false,
				false);
		undoButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Level.this.undo();
			}
		});
		undoButton.enabled = false;
		buttons.add(undoButton);
		editorButton = new LevelButton(630f, 40f, 200f, 30f, "undo_button.png", "undo_button_disabled.png", "EDITOR",
				false, false);
		editorButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.editorMode = true;
				// right click
				if (activeActor != null) {
					activeActor.unselected();
					activeActor = null;
				}
			}
		});
		editorButton.enabled = true;
		buttons.add(editorButton);

	}

	public void postLoad() {
		logs = new Vector<ActivityLog>();
		undoList = new Stack<Move>();
		buttons = new ArrayList<Button>();
		gameCursor = new GameCursor();

		endTurnButton = new LevelButton(210f, 110f, 200f, 100f, "end_turn_button.png", "end_turn_button.png",
				"END TURN", false, false);
		endTurnButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Level.this.endTurn();
			}
		});
		buttons.add(endTurnButton);
		undoButton = new LevelButton(420f, 110f, 200f, 100f, "undo_button.png", "undo_button_disabled.png", "UNDO",
				false, false);
		undoButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Level.this.undo();
			}
		});
		undoButton.enabled = false;
		buttons.add(undoButton);
		editorButton = new LevelButton(630f, 110f, 200f, 100f, "undo_button.png", "undo_button_disabled.png", "EDITOR",
				false, false);
		editorButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.editorMode = true;
				// right click
				if (activeActor != null) {
					activeActor.unselected();
					activeActor = null;
				}
			}
		});
		editorButton.enabled = true;
		buttons.add(editorButton);

		this.inanimateObjectsOnGround = new Vector<GameObject>();
		this.projectiles = new ArrayList<Projectile>();
		this.projectilesToRemove = new ArrayList<Projectile>();
		this.openInventories = new ArrayList<Inventory>();
		this.script = new Script();

		// buildings = new ArrayList<Building>();

		for (Building building : buildings) {
			for (int i = building.gridX1; i <= building.gridX2; i++) {
				for (int j = building.gridY1; j <= building.gridY2; j++) {
					squares[i][j].building = building;
				}
			}
		}

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

	private void initGrid(Square[][] squares, int width, int height) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				squares[i][j] = new Square(i, j, "grass.png", 1, 0, new SquareInventory());
			}
		}
	}

	public void removeWalkingHighlight() {
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[0].length; j++) {
				squares[i][j].reachableBySelectedCharater = false;
			}
		}
	}

	public void removeWeaponsThatCanAttackHighlight() {
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[0].length; j++) {
				squares[i][j].weaponsThatCanAttack.clear();
			}
		}
	}

	public void drawBackground() {
		// Squares
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				// is it better to bind once and draw all the same ones?
				squares[i][j].draw();
			}
		}
	}

	public void drawForeground() {

		// Background decorations

		for (Decoration decoration : decorations) {
			decoration.draw();
		}

		// Objects 1

		for (GameObject gameObject : inanimateObjectsOnGround) {
			gameObject.draw1();
		}

		// Actors 1
		for (Faction faction : factions) {
			for (Actor actor : faction.actors) {
				actor.draw1();
			}
		}

		// Foreground decorations

		for (Decoration decoration : decorations) {
			decoration.draw2();
		}

		// Objects 2

		for (GameObject gameObject : inanimateObjectsOnGround) {
			gameObject.draw2();
		}

		// Actors 2
		for (Faction faction : factions) {
			for (Actor actor : faction.actors) {
				actor.draw2();
			}
		}

		for (GameObject gameObject : inanimateObjectsOnGround) {
			gameObject.draw3();
		}

		// draw any projectiles
		for (Projectile projectile : projectiles) {
			projectile.drawForeground();
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

		for (Inventory inventory : openInventories) {
			inventory.drawStaticUI();
		}

		if (popup != null) {
			popup.draw();
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

		// Log text
		for (int i = logs.size() - 1; i > -1; i--) {
			TextUtils.printTextWithImages(logs.get(i).contents, 150, 100 + i * 20, Integer.MAX_VALUE, true);
		}

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

		// script
		script.draw();

	}

	public void update(int delta) {

		if (conversation != null)
			return;

		// if (this.script.activeScriptEvent != null) {
		script.update(delta);

		for (GameObject inanimateObject : inanimateObjectsOnGround)
			inanimateObject.update(delta);

		// update projectiles
		for (Projectile projectile : projectiles) {
			projectile.update(delta);
		}
		projectiles.removeAll(projectilesToRemove);
		projectilesToRemove.clear();

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

		for (Button button : this.buttons) {
			if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
				return button;
		}

		if (Game.level.popup != null) {
			for (Button button : Game.level.popup.buttons) {
				if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
					return button;
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

		for (Faction faction : factions) {
			for (Actor actor : faction.actors) {
				actor.distanceMovedThisTurn = 0;
				actor.hasAttackedThisTurn = false;
			}
		}

		removeWalkingHighlight();
		removeWeaponsThatCanAttackHighlight();

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
				this.turn++;
			}
		}
		currentFactionMoving = factions.get(currentFactionMovingIndex);
		if (currentFactionMovingIndex == 0) {
			Game.level.activeActor = player;
			Game.level.activeActor.equippedWeapon = Game.level.activeActor.getWeaponsInInventory().get(0);
			Actor.highlightSelectedCharactersSquares();
		}

		undoList.clear();
		undoButton.enabled = false;

	}

	public void logOnScreen(ActivityLog stringToLog) {
		logs.add(stringToLog);
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

	public void undo() {
		if (!this.undoList.isEmpty()) {
			Move move = undoList.pop();
			move.actor.distanceMovedThisTurn -= move.travelCost;
			move.actor.squareGameObjectIsOn = move.squareMovedFrom;
			move.squareMovedFrom.inventory.add(move.actor);
			move.squareMovedTo.inventory.remove(move.actor);
			if (activeActor != null)
				activeActor.unselected();
			activeActor = move.actor;
			Actor.highlightSelectedCharactersSquares();
			removeLastLog();
			if (this.undoList.isEmpty()) {
				undoButton.enabled = false;
			}
		}
	}

	private void removeLastLog() {
		logs.remove(logs.lastElement());
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
