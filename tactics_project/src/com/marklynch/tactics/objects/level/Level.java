package com.marklynch.tactics.objects.level;

import java.util.Vector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import com.marklynch.Dialog;
import com.marklynch.Game;
import com.marklynch.GameCursor;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.ui.Button;
import com.marklynch.utils.ResourceUtils;

public class Level {

	public int width;
	public int height;
	public GameCursor gameCursor;
	public Vector<Actor> actors;
	public Actor activeActor;
	public Vector<GameObject> inanimateObjects;
	public Vector<Dialog> dialogs;
	public Square[][] squares;
	public Button endTurnButton;
	public int turn = 1;
	public TrueTypeFont font12;
	public TrueTypeFont font60;
	public Vector<Faction> factions;
	public Faction currentFactionMoving;
	public int currentFactionMovingIndex;
	public Vector<ActivityLog> log = new Vector<ActivityLog>();

	// java representation of a grid??
	// 2d array?

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		squares = new Square[width][height];
		initGrid();
		initObjects();
		dialogs = new Vector<Dialog>();
		endTurnButton = new Button(Game.windowWidth - 210,
				Game.windowHeight - 110, 200, 100, "end_turn_button.png", this);
		font12 = ResourceUtils.getGlobalFont("KeepCalm-Medium.ttf", 12);
		font60 = ResourceUtils.getGlobalFont("KeepCalm-Medium.ttf", 60);
	}

	private void initGrid() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				squares[i][j] = new Square(i, j, "grass.png", 1, 0, this);
			}
		}
	}

	private void initObjects() {

		// Factions
		factions = new Vector<Faction>();
		factions.add(new Faction("Good Guys", this, Color.blue));
		factions.add(new Faction("Bad Guys", this, Color.red));

		// Good guys relationships
		factions.get(0).relationships.put(factions.get(1), -100);

		// Bad guys relationships
		factions.get(1).relationships.put(factions.get(0), -100);

		// Actors
		actors = new Vector<Actor>();

		// Goody weapons
		Weapon weapon0ForActor0 = new Weapon("a3r1", 3, 1, 1, "a3r1.png");
		Weapon weapon1ForActor0 = new Weapon("a2r2", 2, 2, 2, "a2r2.png");
		Vector<Weapon> weaponsForActor0 = new Vector<Weapon>();
		weaponsForActor0.add(weapon0ForActor0);
		weaponsForActor0.add(weapon1ForActor0);

		Weapon weapon0ForActor1 = new Weapon("a3r1", 3, 1, 1, "a3r1.png");
		Vector<Weapon> weaponsForActor1 = new Vector<Weapon>();
		weaponsForActor1.add(weapon0ForActor1);

		Weapon weapon0ForActor2 = new Weapon("a2r2", 2, 2, 2, "a2r2.png");
		Vector<Weapon> weaponsForActor2 = new Vector<Weapon>();
		weaponsForActor2.add(weapon0ForActor2);

		// Baddy weapons
		Weapon weapon0ForActor3 = new Weapon("a3r1", 3, 1, 1, "a3r1.png");
		Weapon weapon1ForActor3 = new Weapon("a2r3", 2, 2, 2, "a2r2.png");
		Vector<Weapon> weaponsForActor3 = new Vector<Weapon>();
		weaponsForActor3.add(weapon0ForActor3);
		weaponsForActor3.add(weapon1ForActor3);

		Weapon weapon0ForActor4 = new Weapon("a3r1", 3, 1, 1, "a3r1.png");
		Weapon weapon1ForActor4 = new Weapon("a2r3", 2, 2, 2, "a2r2.png");
		Vector<Weapon> weaponsForActor4 = new Vector<Weapon>();
		weaponsForActor4.add(weapon0ForActor4);
		weaponsForActor4.add(weapon1ForActor4);

		actors.add(new Actor("John Lennon", "Fighter", 1, 10, 0, 0, 0, 0,
				"avatar.png", squares[0][0], weaponsForActor0, 4, this));
		actors.get(0).faction = factions.get(0);
		factions.get(0).actors.add(actors.get(0));

		actors.add(new Actor("Paul McCartney", "Maniac", 2, 10, 0, 0, 0, 0,
				"avatar.png", squares[2][7], weaponsForActor1, 4, this));
		actors.get(1).faction = factions.get(0);
		factions.get(0).actors.add(actors.get(1));

		actors.add(new Actor("Steve", "Maniac", 2, 100, 0, 0, 0, 0,
				"avatar.png", squares[2][8], weaponsForActor2, 4, this));
		actors.get(2).faction = factions.get(0);
		factions.get(0).actors.add(actors.get(2));

		actors.add(new Actor("George Harrison", "Thief", 3, 10, 0, 0, 0, 0,
				"red.png", squares[5][3], weaponsForActor3, 4, this));
		actors.get(3).faction = factions.get(1);
		factions.get(1).actors.add(actors.get(3));

		actors.add(new Actor("Ghandi", "Thief", 3, 10, 0, 0, 0, 0, "red.png",
				squares[6][3], weaponsForActor4, 4, this));
		actors.get(4).faction = factions.get(1);
		factions.get(1).actors.add(actors.get(4));

		currentFactionMovingIndex = 0;
		currentFactionMoving = factions.get(currentFactionMovingIndex);

		// Adding actors to factions

		// Game Objects
		inanimateObjects = new Vector<GameObject>();
		inanimateObjects.add(new GameObject("dumpster", 5, 0, 0, 0, 0,
				"skip.png", squares[0][3], weaponsForActor0, this));
		inanimateObjects.add(new GameObject("dumpster", 5, 0, 0, 0, 0,
				"skip_with_shadow.png", squares[1][3], weaponsForActor0, this));
		inanimateObjects
				.add(new GameObject("dumpster", 5, 0, 0, 0, 0,
						"skip_with_shadow2.png", squares[2][3],
						weaponsForActor0, this));
		inanimateObjects
				.add(new GameObject("dumpster", 5, 0, 0, 0, 0,
						"skip_with_shadow3.png", squares[3][3],
						weaponsForActor0, this));

		// Cursor
		gameCursor = new GameCursor();
	}

	public void removeWalkingHighlight() {
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares.length; j++) {
				squares[i][j].reachableBySelectedCharater = false;
			}
		}
	}

	public void removeWeaponsThatCanAttackHighlight() {
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares.length; j++) {
				squares[i][j].weaponsThatCanAttack.clear();
			}
		}
	}

	public void draw() {

		// zoom
		GL11.glPushMatrix();

		GL11.glTranslatef(Game.windowWidth / 2, Game.windowHeight / 2, 0);
		GL11.glScalef(Game.zoom, Game.zoom, 0);
		GL11.glTranslatef(Game.dragX, Game.dragY, 0);
		GL11.glTranslatef(-Game.windowWidth / 2, -Game.windowHeight / 2, 0);

		// Squares
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				// is it better to bind once and draw all the same ones?
				squares[i][j].draw(this);
			}
		}

		// Cursor
		// if (selectedActor != null) {
		// gameCursor.imageTexture.bind();
		// int cursorPositionXInPixels = gameCursor.square.x
		// * (int) Game.SQUARE_WIDTH;
		// int cursorPositionYInPixels = gameCursor.square.y
		// * (int) Game.SQUARE_HEIGHT;
		// GL11.glPushMatrix();
		// GL11.glBegin(GL11.GL_QUADS);
		// GL11.glTexCoord2f(0, 0);
		// GL11.glVertex2f(cursorPositionXInPixels, cursorPositionYInPixels);
		// GL11.glTexCoord2f(1, 0);
		// GL11.glVertex2f(cursorPositionXInPixels + Game.SQUARE_WIDTH,
		// cursorPositionYInPixels);
		// GL11.glTexCoord2f(1, 1);
		// GL11.glVertex2f(cursorPositionXInPixels + Game.SQUARE_WIDTH,
		// cursorPositionYInPixels + Game.SQUARE_HEIGHT);
		// GL11.glTexCoord2f(0, 1);
		// GL11.glVertex2f(cursorPositionXInPixels, cursorPositionYInPixels
		// + Game.SQUARE_HEIGHT);
		// GL11.glEnd();
		// GL11.glPopMatrix();
		// }

		// Objects

		for (GameObject gameObject : inanimateObjects) {
			gameObject.draw();
		}

		// Actors
		for (Faction faction : factions) {
			for (Actor actor : faction.actors) {
				actor.draw();
			}
		}

		// Objects

		for (GameObject gameObject : inanimateObjects) {
			gameObject.draw2();
		}

		// Actors
		for (Faction faction : factions) {
			for (Actor actor : faction.actors) {
				actor.draw2();
			}
		}

		// GL11.glColor4f;
		// font60.drawString(0, 0, "YOUR", new Color(1.0f, 0.5f, 0.5f, 0.75f));
		// font60.drawString(0, Game.SQUARE_HEIGHT, "TURN ", new Color(1.0f,
		// 0.5f,
		// 0.5f, 0.75f));
		// GL11.glColor3f(1.0f, 1.0f, 1.0f);

		// zoom end
		GL11.glPopMatrix();

		// Dialogs
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				// is it better to bind once and draw all the same ones?
				squares[i][j].drawDialogs();
			}
		}

		// End Turn Button
		endTurnButton.draw();

		// Turn text
		font12.drawString(Game.windowWidth - 150, 20, currentFactionMoving.name
				+ " turn " + turn, Color.magenta);

		// Log text
		for (int i = log.size() - 1; i > -1; i--) {
			font12.drawString(150, 100 + i * 20, log.get(i).text,
					log.get(i).faction.color);
		}

		GL11.glColor3f(1.0f, 1.0f, 1.0f);
	}

	public void update(int delta) {
		if (currentFactionMoving != factions.get(0)) {
			currentFactionMoving.update(delta);
		}
	}

	public void clearDialogs() {
		// Level
		this.dialogs.clear();
		// Squares
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				squares[i][j].dialogs.clear();
			}
		}
	}

	public Button getButtonFromMousePosition() {

		if (Mouse.getX() > endTurnButton.x
				&& Mouse.getX() < endTurnButton.x + endTurnButton.width
				&& Game.windowHeight - Mouse.getY() > endTurnButton.y
				&& Game.windowHeight - Mouse.getY() < endTurnButton.y
						+ endTurnButton.height) {

			return endTurnButton;
		}
		return null;
	}

	public void endTurn() {

		this.logOnScreen(new ActivityLog("" + currentFactionMoving.name
				+ " ended turn " + this.turn, this.currentFactionMoving));

		for (Faction faction : factions) {
			for (Actor actor : faction.actors) {
				actor.distanceMovedThisTurn = 0;
				actor.attackedThisTurn = false;
			}
		}
		removeWalkingHighlight();
		removeWeaponsThatCanAttackHighlight();
		activeActor = null;
		currentFactionMovingIndex++;
		if (currentFactionMovingIndex >= factions.size()) {
			currentFactionMovingIndex = 0;
			this.turn++;
		}
		while (factions.get(currentFactionMovingIndex).actors.size() == 0) {
			currentFactionMovingIndex++;
			if (currentFactionMovingIndex >= factions.size()) {
				currentFactionMovingIndex = 0;
				this.turn++;
			}
		}
		currentFactionMoving = factions.get(currentFactionMovingIndex);
	}

	public void logOnScreen(ActivityLog stringToLog) {
		log.add(stringToLog);
		System.out.println(stringToLog.text);
	}
}
