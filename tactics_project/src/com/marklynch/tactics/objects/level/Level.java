package com.marklynch.tactics.objects.level;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import com.marklynch.Game;
import com.marklynch.GameCursor;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.script.InlineSpeechPart;
import com.marklynch.tactics.objects.level.script.Script;
import com.marklynch.tactics.objects.level.script.ScriptEvent;
import com.marklynch.tactics.objects.level.script.ScriptEventEndLevel;
import com.marklynch.tactics.objects.level.script.ScriptEventInlineSpeech;
import com.marklynch.tactics.objects.level.script.ScriptEventSpeech;
import com.marklynch.tactics.objects.level.script.SpeechPart;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTriggerActorSelected;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTriggerScriptEventEnded;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTriggerTurnStart;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.unit.Move;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.Dialog;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;

public class Level {

	public int width;
	public int height;
	public GameCursor gameCursor;
	public Vector<Actor> actors;
	public Actor activeActor;
	public Vector<GameObject> inanimateObjects;
	public Vector<Dialog> dialogs;
	public Square[][] squares;
	public Vector<Decoration> decorations;
	public int turn = 1;
	public TrueTypeFont font12;
	public TrueTypeFont font60;
	public Vector<Faction> factions;
	public Faction currentFactionMoving;
	public int currentFactionMovingIndex;
	public Vector<ActivityLog> logs = new Vector<ActivityLog>();
	public Stack<Move> undoList = new Stack<Move>();

	public LevelButton endTurnButton;
	public LevelButton undoButton;
	public ArrayList<Button> buttons = new ArrayList<Button>();

	public boolean showTurnNotification = true;
	public boolean waitingForPlayerClick = true;

	public Script script;
	public boolean ended = false;

	// java representation of a grid??
	// 2d array?

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		squares = new Square[width][height];
		initGrid();

		factions = new Vector<Faction>();
		inanimateObjects = new Vector<GameObject>();
		decorations = new Vector<Decoration>();
		script = new Script(new Vector<ScriptEvent>());
		gameCursor = new GameCursor();

		// initObjects();

		dialogs = new Vector<Dialog>();
		endTurnButton = new LevelButton(210f, 110f, 200f, 100f,
				"end_turn_button.png", "end_turn_button.png", "END TURN",
				false, false);
		endTurnButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Level.this.endTurn();
			}
		});
		buttons.add(endTurnButton);
		undoButton = new LevelButton(420f, 110f, 200f, 100f, "undo_button.png",
				"undo_button_disabled.png", "UNDO", false, false);
		undoButton.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Level.this.undo();
			}
		});
		undoButton.enabled = false;
		buttons.add(undoButton);
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
		factions.add(new Faction("Good Guys", this, new Color(0.29f, 0.31f,
				0.77f), "faction_blue.png"));
		factions.add(new Faction("Bad Guys", this,
				new Color(0.8f, 0.37f, 0.27f), "faction_red.png"));
		factions.add(new Faction("Green Party", this, new Color(0.30f, 0.8f,
				0.30f), "faction_green.png"));

		// Good guys relationships
		factions.get(0).relationships.put(factions.get(1), -100);
		factions.get(0).relationships.put(factions.get(2), -100);

		// Bad guys relationships
		factions.get(1).relationships.put(factions.get(0), -100);
		factions.get(1).relationships.put(factions.get(2), -100);

		// Green party relationships
		factions.get(2).relationships.put(factions.get(0), -100);
		factions.get(2).relationships.put(factions.get(1), -100);

		// Actors
		actors = new Vector<Actor>();

		// Goody weapons
		Weapon weapon0ForActor0 = new Weapon("a3r1", 3, 1, 1, "a3r1.png");
		Weapon weapon1ForActor0 = new Weapon("a2r2", 2, 2, 2, "a2r2.png");
		Weapon weapon2ForActor0 = new Weapon("a5r3", 5, 3, 3, "a2r2.png");
		// Weapon weapon3ForActor0 = new Weapon("a5r3", 5, 3, 3, "a2r2.png");
		Vector<Weapon> weaponsForActor0 = new Vector<Weapon>();
		weaponsForActor0.add(weapon0ForActor0);
		weaponsForActor0.add(weapon1ForActor0);
		weaponsForActor0.add(weapon2ForActor0);
		// weaponsForActor0.add(weapon3ForActor0);

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

		// Green Party Weapons
		Weapon weapon0ForActor5 = new Weapon("a3r1", 3, 1, 1, "a3r1.png");
		Vector<Weapon> weaponsForActor5 = new Vector<Weapon>();
		weaponsForActor5.add(weapon0ForActor5);

		actors.add(new Actor("Old lady", "Fighter", 1, 10, 0, 0, 0, 0,
				"red1.png", squares[0][0], weaponsForActor0, 4, this));
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

		actors.add(new Actor("Green1", "Hippy", 3, 10, 0, 0, 0, 0, "green.png",
				squares[8][6], weaponsForActor5, 6, this));
		actors.get(5).faction = factions.get(2);
		factions.get(2).actors.add(actors.get(5));

		currentFactionMovingIndex = 0;

		// Adding actors to factions

		// Game Objects
		inanimateObjects.add(new GameObject("dumpster", 5, 0, 0, 0, 0,
				"skip_with_shadow.png", squares[0][3], new Vector<Weapon>(),
				this));
		inanimateObjects.add(new GameObject("dumpster", 5, 0, 0, 0, 0,
				"sign.png", squares[1][3], new Vector<Weapon>(), this));
		inanimateObjects.add(new GameObject("dumpster", 5, 0, 0, 0, 0,
				"skip_with_shadow.png", squares[2][3], new Vector<Weapon>(),
				this));
		inanimateObjects.add(new GameObject("dumpster", 5, 0, 0, 0, 0,
				"skip_with_shadow.png", squares[3][3], new Vector<Weapon>(),
				this));

		decorations
				.add(new Decoration(300f, 240f, 28f, 28f, false, "sign.png"));
		decorations.add(new Decoration(468f, 200f, 28f, 28f, true,
				"skip_with_shadow.png"));

		// Script

		// Speech 1
		Vector<Actor> speechActors1 = new Vector<Actor>();
		speechActors1.add(factions.get(0).actors.get(0));
		speechActors1.add(factions.get(0).actors.get(1));

		Vector<Float> speechPositions1 = new Vector<Float>();
		speechPositions1.add(0f);
		speechPositions1.add(0f);

		Vector<SpeechPart.DIRECTION> speechDirections1 = new Vector<SpeechPart.DIRECTION>();
		speechDirections1.add(SpeechPart.DIRECTION.RIGHT);
		speechDirections1.add(SpeechPart.DIRECTION.LEFT);

		SpeechPart speechPart1_1 = new SpeechPart(speechActors1,
				speechPositions1, speechDirections1,
				factions.get(0).actors.get(0),
				new Object[] { new StringWithColor(
						"HI, THIS IS SCRIPTED SPEECH :D", Color.black) }, this);

		SpeechPart speechPart1_2 = new SpeechPart(
				speechActors1,
				speechPositions1,
				speechDirections1,
				factions.get(0).actors.get(0),
				new Object[] { new StringWithColor(
						"HI, THIS IS THE SECOND PART, WOO, THIS IS GOING GREAT",
						Color.black) }, this);

		Vector<SpeechPart> speechParts1 = new Vector<SpeechPart>();
		speechParts1.add(speechPart1_1);
		speechParts1.add(speechPart1_2);

		// ScriptTrigger scriptTrigger1 = new
		// ScriptTriggerDestructionOfSpecificGameObject(
		// this, factions.get(0).actors.get(0));
		ScriptTrigger scriptTrigger1 = new ScriptTriggerActorSelected(this,
				factions.get(0).actors.get(0));
		// ScriptTrigger scriptTrigger1 = new ScriptTriggerTurnStart(this, 1,
		// 0);

		ScriptEventEndLevel scriptEventEndLevel = new ScriptEventEndLevel(true,
				scriptTrigger1, this, new Object[] { "GAME OVER" });
		// ScriptEventSpeech scriptEventSpeech1 = new ScriptEventSpeech(true,
		// speechParts1, null);

		// Speech 2

		Vector<Actor> speechActors2 = new Vector<Actor>();
		speechActors2.add(factions.get(2).actors.get(0));
		speechActors2.add(factions.get(0).actors.get(1));

		Vector<Float> speechPositions2 = new Vector<Float>();
		speechPositions2.add(0f);
		speechPositions2.add(0f);

		Vector<SpeechPart.DIRECTION> speechDirections2 = new Vector<SpeechPart.DIRECTION>();
		speechDirections2.add(SpeechPart.DIRECTION.RIGHT);
		speechDirections2.add(SpeechPart.DIRECTION.LEFT);

		SpeechPart speechPart2_1 = new SpeechPart(speechActors2,
				speechPositions2, speechDirections2,
				factions.get(2).actors.get(0),
				new Object[] { new StringWithColor("GREEN TEAM HOOOOOOOO",
						Color.black) }, this);

		Vector<SpeechPart> speechParts2 = new Vector<SpeechPart>();
		speechParts2.add(speechPart2_1);

		ScriptTrigger scriptTrigger2 = new ScriptTriggerTurnStart(this, 1, 2);

		ScriptEventSpeech scriptEventSpeech2 = new ScriptEventSpeech(true,
				speechParts2, null);

		// Vector<ScriptEvent> scriptEventsForGroup = new Vector<ScriptEvent>();
		// scriptEventsForGroup.add(scriptEventSpeech1);
		// scriptEventsForGroup.add(scriptEventSpeech2);
		// ScriptEventGroup scriptEventGroup = new ScriptEventGroup(true,
		// scriptTrigger1, scriptEventsForGroup);

		// Inline speechVector<Actor> speechActors1 = new Vector<Actor>();

		InlineSpeechPart inlineSpeechPart1_1 = new InlineSpeechPart(
				factions.get(0).actors.get(0),
				new Object[] { new StringWithColor("HOLLA INLINE SPEECH YO",
						Color.black) }, this);

		InlineSpeechPart inlineSpeechPart1_2 = new InlineSpeechPart(
				factions.get(0).actors.get(0),
				new Object[] { new StringWithColor(
						"HOLLA, PART 2 OF THE INLINE SPEECH, WANT TO PUSH IT TO OVER 2 LINES, JUST TO SEE WTF IT LOOKS LIKE HOLLA",
						Color.black) }, this);

		Vector<InlineSpeechPart> inlineSpeechParts1 = new Vector<InlineSpeechPart>();
		inlineSpeechParts1.add(inlineSpeechPart1_1);
		inlineSpeechParts1.add(inlineSpeechPart1_2);

		// ScriptTrigger scriptTrigger3 = new ScriptTriggerTurnStart(this, 2,
		// 0);
		ScriptTriggerScriptEventEnded scriptTrigger3 = new ScriptTriggerScriptEventEnded(
				scriptEventEndLevel);

		ScriptEventInlineSpeech inlineScriptEventSpeech1 = new ScriptEventInlineSpeech(
				false, inlineSpeechParts1, scriptTrigger3);

		// The script

		Vector<ScriptEvent> scriptEvents = new Vector<ScriptEvent>();
		scriptEvents.add(scriptEventEndLevel);
		scriptEvents.add(scriptEventSpeech2);
		scriptEvents.add(inlineScriptEventSpeech1);

		script.scriptEvents = scriptEvents;
		// script.activateScriptEvent();
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

		// Background decorations

		for (Decoration decoration : decorations) {
			decoration.draw();
		}

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

		// Foreground decorations

		for (Decoration decoration : decorations) {
			decoration.draw2();
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

		if (!Game.editorMode)
			for (Button button : buttons) {
				button.draw();
			}

		// Turn text
		if (currentFactionMoving != null)
			font12.drawString(Game.windowWidth - 150, 20,
					currentFactionMoving.name + " turn " + turn, Color.magenta);

		// Log text
		for (int i = logs.size() - 1; i > -1; i--) {
			TextUtils.printTextWithImages(logs.get(i).contents, 150,
					100 + i * 20);
		}

		if (factions.size() > 0 && currentFactionMoving != null) {
			if (showTurnNotification) {
				if (currentFactionMoving == factions.get(0)) {
					TextUtils.printTextWithImages(new Object[] { "Your turn ",
							this.currentFactionMoving.imageTexture,
							", click to continue." }, 500, 500);
				} else {
					TextUtils.printTextWithImages(new Object[] {
							this.currentFactionMoving, "'s turn" }, 500, 500);
				}
			}
		}

		// script
		script.draw();

		GL11.glColor3f(1.0f, 1.0f, 1.0f);
	}

	public void update(int delta) {
		// if (this.script.activeScriptEvent != null) {
		script.update(delta);
		if (!this.script.checkIfBlocking()
				&& currentFactionMoving != factions.get(0)) {
			currentFactionMoving.update(delta);
		}
		// } else if (currentFactionMoving != factions.get(0)) {
		// currentFactionMoving.update(delta);
		// }
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

	public Button getButtonFromMousePosition(float mouseX, float mouseY,
			float alteredMouseX, float alteredMouseY) {

		for (Button button : this.buttons) {
			if (button.calculateIfPointInBoundsOfButton(mouseX,
					Game.windowHeight - mouseY))
				return button;
		}

		if (activeActor != null && activeActor.faction == factions.get(0))
			return this.activeActor.getButtonFromMousePosition(alteredMouseX,
					alteredMouseY);

		return null;
	}

	public void endTurn() {

		this.logOnScreen(new ActivityLog(new Object[] { currentFactionMoving,
				" ended turn " + this.turn }));

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
		if (currentFactionMovingIndex == 0)
			waitingForPlayerClick = true;

		showTurnNotification();

		undoList.clear();
		undoButton.enabled = false;

	}

	public void logOnScreen(ActivityLog stringToLog) {
		logs.add(stringToLog);
	}

	public void showTurnNotification() {
		showTurnNotification = true;
		if (this.currentFactionMoving != factions.get(0))
			new hideTurnNotificationThread().start();
	}

	public class hideTurnNotificationThread extends Thread {

		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			showTurnNotification = false;
		}
	}

	public void undo() {
		if (!this.undoList.isEmpty()) {
			Move move = undoList.pop();
			move.actor.distanceMovedThisTurn -= move.travelCost;
			move.actor.squareGameObjectIsOn = move.squareMovedFrom;
			move.squareMovedFrom.gameObject = move.actor;
			move.squareMovedTo.gameObject = null;
			if (activeActor != null)
				activeActor.unselected();
			activeActor = move.actor;
			Actor.highlightSelectedCharactersSquares(this);
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
}
