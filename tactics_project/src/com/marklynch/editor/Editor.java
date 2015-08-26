package com.marklynch.editor;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

import org.lwjgl.input.Mouse;

import com.marklynch.Game;
import com.marklynch.editor.settingswindow.AIsSettingsWindow;
import com.marklynch.editor.settingswindow.ActorsSettingsWindow;
import com.marklynch.editor.settingswindow.ColorSettingsWindow;
import com.marklynch.editor.settingswindow.DecorationsSettingsWindow;
import com.marklynch.editor.settingswindow.FactionsSettingsWindow;
import com.marklynch.editor.settingswindow.LevelSettingsWindow;
import com.marklynch.editor.settingswindow.ObjectsSettingsWindow;
import com.marklynch.editor.settingswindow.RelationsSettingsWindow;
import com.marklynch.editor.settingswindow.ScriptEventsSettingsWindow;
import com.marklynch.editor.settingswindow.ScriptTriggersSettingsWindow;
import com.marklynch.editor.settingswindow.SettingsWindow;
import com.marklynch.editor.settingswindow.SpeechPartSettingsWindow;
import com.marklynch.editor.settingswindow.SquaresSettingsWindow;
import com.marklynch.editor.settingswindow.WeaponsSettingsWindow;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.tactics.objects.level.FactionRelationship;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.level.script.ScriptEvent;
import com.marklynch.tactics.objects.level.script.ScriptEventSetAI;
import com.marklynch.tactics.objects.level.script.ScriptEventSpeech;
import com.marklynch.tactics.objects.level.script.ScriptEventSpeech.SpeechPart;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTriggerActorSelected;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTriggerTurnStart;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.unit.ai.AI;
import com.marklynch.tactics.objects.unit.ai.AITargetObject;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.tactics.objects.weapons.Weapons;
import com.marklynch.ui.button.AtributesWindowButton;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.button.SettingsWindowButton;
import com.marklynch.utils.LineUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextureUtils;

public class Editor {

	public String json = null;

	public ArrayList<Button> buttons = new ArrayList<Button>();

	// faction, actors, object, scriptevent, script
	// trigger, weapon,
	// level, squares

	public AttributesWindow attributesWindow;

	Button levelTabButton;
	Button squaresTabButton;
	Button objectsTabButton;
	Button factionsTabButton;
	Button colorsTabButton;
	Button actorsTabButton;
	Button weaponsTabButton;
	Button decorationsTabButton;
	Button scriptEventsTabButton;
	Button scriptTriggersTabButton;
	Button aisTabButton;
	Button relationsTabButton;
	Button speechPartTabButton;

	public SettingsWindow settingsWindow;
	public LevelSettingsWindow levelSettingsWindow;
	public SquaresSettingsWindow squaresSettingsWindow;
	public ObjectsSettingsWindow objectsSettingsWindow;
	public ActorsSettingsWindow actorsSettingsWindow;
	public FactionsSettingsWindow factionsSettingsWindow;
	public WeaponsSettingsWindow weaponsSettingsWindow;
	public ColorSettingsWindow colorsSettingsWindow;
	public DecorationsSettingsWindow decorationsSettingsWindow;
	public ScriptEventsSettingsWindow scriptsEventsSettingsWindow;
	public ScriptTriggersSettingsWindow scriptsTriggersSettingsWindow;
	public AIsSettingsWindow aisSettingsWindow;
	public RelationsSettingsWindow relationsSettingsWindow;
	public SpeechPartSettingsWindow speechPartSettingsWindow;

	public GameObject selectedGameObject;

	public Object objectToEdit = null;
	public String attributeToEditName = "";
	public int attributeToEditIndex = 0;
	public String textEntered = "";
	public AtributesWindowButton attributeButton = null;
	public SettingsWindowButton settingsButton = null;

	public ArrayList<Texture> textures = new ArrayList<Texture>();
	public ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	public AttributeSelectionWindow attributeSelectionWindow;
	public GameObject gameObjectTemplate;
	public Actor actorTemplate;

	public enum STATE {
		DEFAULT,
		ADD_OBJECT,
		ADD_ACTOR,
		MOVEABLE_OBJECT_SELECTED,
		SETTINGS_CHANGE
	}

	public STATE state = STATE.DEFAULT;

	public ArrayList<Color> colors = new ArrayList<Color>();

	public ClassSelectionWindow classSelectionWindow;

	public Editor() {

		// LOAD THE TEXTURES
		File file = new File("res/images/");
		File[] listOfFiles = file.listFiles();
		for (File imageFile : listOfFiles) {
			Texture texture = ResourceUtils.getGlobalImage(imageFile.getName());
			if (texture != null) {
				textures.add(texture);
			}
		}

		// LOAD COLORS
		colors.add(new Color(Color.BLUE));
		colors.add(new Color(Color.RED));
		colors.add(new Color(Color.GREEN));
		colors.add(new Color(Color.MAGENTA));
		colors.add(new Color(Color.CYAN));
		colors.add(new Color(Color.ORANGE));

		// LOAD Weapons// Weapons
		Weapon weapon0 = new Weapon("a3r1", 3, 1, 1, "a3r1.png");
		weapons.add(weapon0);
		Weapon weapon1 = new Weapon("a2r2", 2, 2, 2, "a2r2.png");
		weapons.add(weapon1);
		Weapon weapon2 = new Weapon("a5r3", 5, 3, 3, "a2r2.png");
		weapons.add(weapon2);

		Game.level = new Level(10, 10);

		levelSettingsWindow = new LevelSettingsWindow(200, this);
		squaresSettingsWindow = new SquaresSettingsWindow(200, this);
		objectsSettingsWindow = new ObjectsSettingsWindow(200, this);
		actorsSettingsWindow = new ActorsSettingsWindow(200, this);
		factionsSettingsWindow = new FactionsSettingsWindow(200, this);
		weaponsSettingsWindow = new WeaponsSettingsWindow(200, this);
		colorsSettingsWindow = new ColorSettingsWindow(200, this);
		decorationsSettingsWindow = new DecorationsSettingsWindow(200, this);
		scriptsEventsSettingsWindow = new ScriptEventsSettingsWindow(200, this);
		scriptsTriggersSettingsWindow = new ScriptTriggersSettingsWindow(200,
				this);
		aisSettingsWindow = new AIsSettingsWindow(200, this);
		relationsSettingsWindow = new RelationsSettingsWindow(200, this);
		speechPartSettingsWindow = new SpeechPartSettingsWindow(200, this);

		settingsWindow = levelSettingsWindow;

		generateTestObjects();

		// TABS
		levelTabButton = new LevelButton(10, 10, 70, 30, "", "", "LEVEL", true,
				true);
		buttons.add(levelTabButton);
		levelTabButton.clickListener = new ClickListener() {
			@Override
			public void click() {
				clearSelectedObject();
				depressButtonsSettingsAndDetailsButtons();
				depressTabButtons();
				levelTabButton.down = true;
				settingsWindow = levelSettingsWindow;
				settingsWindow.update();
			}
		};
		levelTabButton.down = true;

		squaresTabButton = new LevelButton(90, 10, 110, 30, "", "", "SQUARES",
				true, true);
		squaresTabButton.clickListener = new ClickListener() {
			@Override
			public void click() {
				clearSelectedObject();
				depressButtonsSettingsAndDetailsButtons();
				depressTabButtons();
				squaresTabButton.down = true;
				settingsWindow = squaresSettingsWindow;
				settingsWindow.update();
			}
		};
		buttons.add(squaresTabButton);

		objectsTabButton = new LevelButton(210, 10, 100, 30, "", "", "OBJECTS",
				true, true);
		objectsTabButton.clickListener = new ClickListener() {
			@Override
			public void click() {
				clearSelectedObject();
				depressButtonsSettingsAndDetailsButtons();
				depressTabButtons();
				objectsTabButton.down = true;
				settingsWindow = objectsSettingsWindow;
				settingsWindow.update();
			}
		};
		buttons.add(objectsTabButton);

		actorsTabButton = new LevelButton(320, 10, 100, 30, "", "", "ACTORS",
				true, true);
		actorsTabButton.clickListener = new ClickListener() {
			@Override
			public void click() {
				clearSelectedObject();
				depressButtonsSettingsAndDetailsButtons();
				depressTabButtons();
				actorsTabButton.down = true;
				settingsWindow = actorsSettingsWindow;
				settingsWindow.update();
			}
		};
		buttons.add(actorsTabButton);

		factionsTabButton = new LevelButton(430, 10, 120, 30, "", "",
				"FACTIONS", true, true);
		factionsTabButton.clickListener = new ClickListener() {
			@Override
			public void click() {
				clearSelectedObject();
				depressButtonsSettingsAndDetailsButtons();
				depressTabButtons();
				factionsTabButton.down = true;
				settingsWindow = factionsSettingsWindow;
				settingsWindow.update();
			}
		};
		buttons.add(factionsTabButton);

		weaponsTabButton = new LevelButton(560, 10, 120, 30, "", "", "WEAPONS",
				true, true);
		weaponsTabButton.clickListener = new ClickListener() {
			@Override
			public void click() {
				clearSelectedObject();
				depressButtonsSettingsAndDetailsButtons();
				depressTabButtons();
				weaponsTabButton.down = true;
				settingsWindow = weaponsSettingsWindow;
				settingsWindow.update();
			}
		};
		buttons.add(weaponsTabButton);

		colorsTabButton = new LevelButton(690, 10, 120, 30, "", "", "COLORS",
				true, true);
		colorsTabButton.clickListener = new ClickListener() {
			@Override
			public void click() {
				clearSelectedObject();
				depressButtonsSettingsAndDetailsButtons();
				depressTabButtons();
				colorsTabButton.down = true;
				settingsWindow = colorsSettingsWindow;
				settingsWindow.update();
			}
		};
		buttons.add(colorsTabButton);

		decorationsTabButton = new LevelButton(10, 50, 160, 30, "", "",
				"DECORATIONS", true, true);
		decorationsTabButton.clickListener = new ClickListener() {
			@Override
			public void click() {
				clearSelectedObject();
				depressButtonsSettingsAndDetailsButtons();
				depressTabButtons();
				decorationsTabButton.down = true;
				settingsWindow = decorationsSettingsWindow;
				settingsWindow.update();
			}
		};
		buttons.add(decorationsTabButton);

		scriptEventsTabButton = new LevelButton(180, 50, 160, 30, "", "",
				"SCRIPT EVENTS", true, true);
		scriptEventsTabButton.clickListener = new ClickListener() {
			@Override
			public void click() {
				clearSelectedObject();
				depressButtonsSettingsAndDetailsButtons();
				depressTabButtons();
				scriptEventsTabButton.down = true;
				settingsWindow = scriptsEventsSettingsWindow;
				settingsWindow.update();
			}
		};
		buttons.add(scriptEventsTabButton);

		scriptTriggersTabButton = new LevelButton(350, 50, 160, 30, "", "",
				"SCRIPT TRIGGERS", true, true);
		scriptTriggersTabButton.clickListener = new ClickListener() {
			@Override
			public void click() {
				clearSelectedObject();
				depressButtonsSettingsAndDetailsButtons();
				depressTabButtons();
				scriptTriggersTabButton.down = true;
				settingsWindow = scriptsTriggersSettingsWindow;
				settingsWindow.update();
			}
		};
		buttons.add(scriptTriggersTabButton);

		aisTabButton = new LevelButton(520, 50, 60, 30, "", "", "AIS", true,
				true);
		aisTabButton.clickListener = new ClickListener() {
			@Override
			public void click() {
				clearSelectedObject();
				depressButtonsSettingsAndDetailsButtons();
				depressTabButtons();
				aisTabButton.down = true;
				settingsWindow = aisSettingsWindow;
				settingsWindow.update();
			}
		};
		buttons.add(aisTabButton);

		relationsTabButton = new LevelButton(590, 50, 130, 30, "", "",
				"RELATIONS", true, true);
		relationsTabButton.clickListener = new ClickListener() {
			@Override
			public void click() {
				clearSelectedObject();
				depressButtonsSettingsAndDetailsButtons();
				depressTabButtons();
				relationsTabButton.down = true;
				settingsWindow = relationsSettingsWindow;
				settingsWindow.update();
			}
		};
		buttons.add(relationsTabButton);

		speechPartTabButton = new LevelButton(730, 50, 130, 30, "", "",
				"SPEECH PART", true, true);
		speechPartTabButton.clickListener = new ClickListener() {
			@Override
			public void click() {
				clearSelectedObject();
				depressButtonsSettingsAndDetailsButtons();
				depressTabButtons();
				speechPartTabButton.down = true;
				settingsWindow = speechPartSettingsWindow;
				settingsWindow.update();
			}
		};
		buttons.add(speechPartTabButton);

	}

	public void generateTestObjects() {

		// Add a game object
		GameObject gameObject = new GameObject("dumpster", 5, 0, 0, 0, 0,
				"skip_with_shadow.png", Game.level.squares[0][3],
				new ArrayList<Weapon>());
		Game.level.inanimateObjects.add(gameObject);
		Game.level.squares[0][3].gameObject = gameObject;

		// Add factions
		Game.level.factions
				.add(new Faction("Faction " + Game.level.factions.size(),
						colors.get(0), "faction_blue.png"));
		Game.level.factions
				.add(new Faction("Faction " + Game.level.factions.size(),
						colors.get(1), "faction_red.png"));

		// relationships
		Game.level.factions.get(0).relationships
				.put(Game.level.factions.get(1), new FactionRelationship(-100,
						Game.level.factions.get(0), Game.level.factions.get(1)));
		Game.level.factions.get(1).relationships
				.put(Game.level.factions.get(0), new FactionRelationship(-100,
						Game.level.factions.get(1), Game.level.factions.get(0)));

		// Weapons
		ArrayList<Weapon> weaponsForActor0 = new ArrayList<Weapon>();
		weaponsForActor0.add(weapons.get(0).makeCopy());
		weaponsForActor0.add(weapons.get(1).makeCopy());
		weaponsForActor0.add(weapons.get(2).makeCopy());
		ArrayList<Weapon> weaponsForActor1 = new ArrayList<Weapon>();
		weaponsForActor1.add(weapons.get(0).makeCopy());
		weaponsForActor1.add(weapons.get(1).makeCopy());
		weaponsForActor1.add(weapons.get(2).makeCopy());

		// Add actor
		Actor actor0 = new Actor("Old lady", "Fighter", 1, 10, 0, 0, 0, 0,
				"red1.png", Game.level.squares[0][4], weaponsForActor0, 4);
		actor0.faction = Game.level.factions.get(0);
		Game.level.factions.get(0).actors.add(actor0);

		Actor actor1 = new Actor("Old lady", "Fighter", 1, 10, 0, 0, 0, 0,
				"red1.png", Game.level.squares[0][5], weaponsForActor1, 4);
		actor1.faction = Game.level.factions.get(1);
		Game.level.factions.get(1).actors.add(actor1);

		// Script

		// Speech 1
		ArrayList<Actor> speechActors1 = new ArrayList<Actor>();
		speechActors1.add(Game.level.factions.get(0).actors.get(0));
		speechActors1.add(Game.level.factions.get(1).actors.get(0));
		ArrayList<Float> speechPositions1 = new ArrayList<Float>();
		speechPositions1.add(0f);
		speechPositions1.add(0f);
		ArrayList<ScriptEventSpeech.SpeechPart.DIRECTION> speechDirections1 = new ArrayList<ScriptEventSpeech.SpeechPart.DIRECTION>();
		speechDirections1.add(SpeechPart.DIRECTION.RIGHT);
		speechDirections1.add(SpeechPart.DIRECTION.LEFT);

		ScriptEventSpeech.SpeechPart speechPart1_1 = new ScriptEventSpeech.SpeechPart(
				speechActors1, speechPositions1, speechDirections1,
				Game.level.factions.get(0).actors.get(0),
				new StringWithColor[] { new StringWithColor(
						"HI, THIS IS SCRIPTED SPEECH :D", Color.BLACK) });

		ScriptEventSpeech.SpeechPart speechPart1_2 = new ScriptEventSpeech.SpeechPart(
				speechActors1, speechPositions1, speechDirections1,
				Game.level.factions.get(0).actors.get(0),
				new StringWithColor[] { new StringWithColor(
						"THE SECOND PART, WOO, THIS IS GOING GREAT",
						Color.BLACK) });
		ArrayList<ScriptEventSpeech.SpeechPart> speechParts1 = new ArrayList<ScriptEventSpeech.SpeechPart>();
		speechParts1.add(speechPart1_1);
		speechParts1.add(speechPart1_2);
		ScriptTrigger scriptTriggerActorSelected = new ScriptTriggerActorSelected(
				Game.level.factions.get(0).actors.get(0));
		ScriptEventSpeech scriptEventSpeech1 = new ScriptEventSpeech(true,
				speechParts1, scriptTriggerActorSelected);

		Game.level.script.scriptTriggers.add(new ScriptTriggerTurnStart(1, 0));
		Game.level.script.scriptTriggers.add(scriptTriggerActorSelected);
		Game.level.ais.add(new AITargetObject(gameObject));
		Game.level.ais.get(0).name = "attackDumpster";
		Game.level.ais.add(new AITargetObject(actor0));
		Game.level.ais.get(1).name = "attackPlayer";

		ScriptEventSetAI scriptEventSetAIAttackDumpster = new ScriptEventSetAI(
				false, Game.level.script.scriptTriggers.get(0).makeCopy(),
				actor1, Game.level.ais.get(0).makeCopy());

		ArrayList<ScriptEvent> scriptEvents = new ArrayList<ScriptEvent>();
		// scriptEvents.add(scriptEventSpeech1);
		scriptEvents.add(scriptEventSetAIAttackDumpster);
		scriptEvents.add(scriptEventSpeech1);

		Game.level.script.scriptEvents = scriptEvents;
		// script.activateScriptEvent();

	}

	public void update(int delta) {

	}

	public void drawOverlay() {

		// draw highlight on selected object
		if (selectedGameObject != null) {
			selectedGameObject.squareGameObjectIsOn.drawHighlight();
		}

		// Draw a move line if click will result in move
		if (Game.buttonHoveringOver == null
				&& state == STATE.MOVEABLE_OBJECT_SELECTED
				&& Game.squareMouseIsOver != null
				&& Game.squareMouseIsOver != this.selectedGameObject.squareGameObjectIsOn) {

			float x1 = this.selectedGameObject.squareGameObjectIsOn.x
					* Game.SQUARE_WIDTH + Game.SQUARE_WIDTH / 2;
			float y1 = this.selectedGameObject.squareGameObjectIsOn.y
					* Game.SQUARE_HEIGHT + Game.SQUARE_HEIGHT / 2;
			float x2 = Game.squareMouseIsOver.x * Game.SQUARE_WIDTH
					+ Game.SQUARE_WIDTH / 2;
			float y2 = Game.squareMouseIsOver.y * Game.SQUARE_HEIGHT
					+ Game.SQUARE_HEIGHT / 2;

			// CircleUtils.drawCircle(Color.white, 10d, x1, y1);
			TextureUtils.drawTexture(Game.level.gameCursor.circle, x1 - 10,
					x1 + 10, y1 - 10, y1 + 10);
			LineUtils.drawLine(Color.WHITE, x1, y1, x2, y2, 10f);
			TextureUtils.drawTexture(Game.level.gameCursor.circle, x2 - 10,
					x2 + 10, y2 - 10, y2 + 10);

		}
	}

	public void drawUI() {

		if (attributesWindow != null)
			attributesWindow.draw();

		settingsWindow.draw();

		for (Button button : buttons) {
			button.draw();
		}

		// Depending on what we're editing, might wnat to show something
		// different
		// if (objectToEdit != null) {
		// try {
		// Class<? extends Object> objectClass = objectToEdit.getClass();
		// Field field = objectClass.getField(attributeToEdit);
		//
		// if (field.getType().isAssignableFrom(int.class)
		// || field.getType().isAssignableFrom(float.class)) {
		// // int or float
		// } else if (field.getType().isAssignableFrom(String.class)) {
		// // string
		// } else if (field.getType().isAssignableFrom(Faction.class)
		// || field.getType().isAssignableFrom(Color.class)
		// || field.getType().isAssignableFrom(Texture.class)
		// || field.getType().isAssignableFrom(Weapons.class)
		// || field.getType().isAssignableFrom(Actor.class)
		// || field.getType()
		// .isAssignableFrom(ScriptTrigger.class)) {
		// selectionWindow.draw();
		// }
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }

		if (attributeSelectionWindow != null) {
			attributeSelectionWindow.draw();
		}

		if (classSelectionWindow != null) {
			classSelectionWindow.draw();
		}

		if (state == STATE.MOVEABLE_OBJECT_SELECTED) {
			TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2,
					Mouse.getX() + 10, Mouse.getX() + 30, Game.windowHeight
							- Mouse.getY() + 20,
					Game.windowHeight - Mouse.getY() + 40);
			TextureUtils.drawTexture(selectedGameObject.imageTexture,
					Mouse.getX() + 10, Mouse.getX() + 30, Game.windowHeight
							- Mouse.getY() + 20,
					Game.windowHeight - Mouse.getY() + 40);
		}
	}

	public Button getButtonFromMousePosition(float mouseX, float mouseY) {

		if (attributeSelectionWindow != null) {
			return attributeSelectionWindow.getButtonFromMousePosition(mouseX,
					mouseY);

		}

		if (classSelectionWindow != null) {
			// faction, color, texture
			return classSelectionWindow.getButtonFromMousePosition(mouseX,
					mouseY);

		}

		for (Button button : this.buttons) {
			if (button.calculateIfPointInBoundsOfButton(mouseX,
					Game.windowHeight - mouseY))
				return button;
		}

		if (attributesWindow != null) {
			for (AtributesWindowButton button : attributesWindow.buttons) {
				if (button.calculateIfPointInBoundsOfButton(mouseX,
						Game.windowHeight - mouseY))
					return button;
			}
		}

		for (Button button : settingsWindow.buttons) {
			if (button.calculateIfPointInBoundsOfButton(mouseX,
					Game.windowHeight - mouseY))
				return button;
		}

		return null;
	}

	public void gameObjectClicked(GameObject gameObject) {
		if (state == STATE.DEFAULT || state == STATE.ADD_ACTOR
				|| state == STATE.ADD_OBJECT || state == STATE.SETTINGS_CHANGE) {
			if (gameObject instanceof Actor) {
				if (this.settingsWindow != this.actorsSettingsWindow)
					actorsTabButton.click();
				actorsSettingsWindow.getButton(gameObject).click();
			} else {
				if (this.settingsWindow != this.objectsSettingsWindow)
					objectsTabButton.click();
				objectsSettingsWindow.getButton(gameObject).click();
			}
		} else if (state == STATE.MOVEABLE_OBJECT_SELECTED) {
			swapGameObjects(this.selectedGameObject, gameObject);
		}

	}

	public void squareClicked(Square square) {
		if (state == STATE.DEFAULT || state == STATE.SETTINGS_CHANGE) {
			if (this.settingsWindow != this.squaresSettingsWindow)
				squaresTabButton.click();
			this.clearSelectedObject();
			attributesWindow = new AttributesWindow(200, 200, 350, square, this);
			depressButtonsSettingsAndDetailsButtons();
		} else if (state == STATE.ADD_OBJECT) {
			GameObject gameObject = null;
			if (gameObjectTemplate == null) {
				gameObject = new GameObject("dumpster", 5, 0, 0, 0, 0,
						"skip_with_shadow.png", square, new ArrayList<Weapon>());
			} else {
				gameObject = gameObjectTemplate.makeCopy(square);
			}

			Game.level.inanimateObjects.add(gameObject);
			square.gameObject = gameObject;
			this.objectsSettingsWindow.update();
			// state = STATE.DEFAULT;
		} else if (state == STATE.ADD_ACTOR) {
			// Add actor
			Actor actor = null;
			if (actorTemplate == null) {
				actor = new Actor("Old lady", "Fighter", 1, 10, 0, 0, 0, 0,
						"red1.png", square, new ArrayList<Weapon>(), 4);
				actor.faction = Game.level.factions.get(0);
			} else {
				actor = actorTemplate.makeCopy(square);
				actor.faction = actorTemplate.faction;
			}

			actor.faction.actors.add(actor);
			square.gameObject = actor;
			this.actorsSettingsWindow.update();
			// state = STATE.DEFAULT;
		} else if (state == STATE.MOVEABLE_OBJECT_SELECTED) {
			if (square.gameObject != null) {
				swapGameObjects(this.selectedGameObject, square.gameObject);
			} else {
				moveGameObject(this.selectedGameObject, square);
			}
		}

	}

	public void swapGameObjects(GameObject gameObject1, GameObject gameObject2) {
		Square square1 = gameObject1.squareGameObjectIsOn;
		Square square2 = gameObject2.squareGameObjectIsOn;

		square1.gameObject = gameObject2;
		square2.gameObject = gameObject1;

		gameObject1.squareGameObjectIsOn = square2;
		gameObject2.squareGameObjectIsOn = square1;

	}

	public void moveGameObject(GameObject gameObject1, Square square2) {
		Square square1 = gameObject1.squareGameObjectIsOn;

		square1.gameObject = null;
		square2.gameObject = gameObject1;

		gameObject1.squareGameObjectIsOn = square2;
	}

	public void keyTyped(char character) {
		System.out.println("keyTyped()");

		if (state == STATE.SETTINGS_CHANGE && settingsButton != null) {
			settingsButton.keyTyped(character);
		} else if (objectToEdit != null) {
			if (attributeToEditName != null && this.textEntered != null) {
				System.out.println("objectToEdit = " + objectToEdit);

				try {
					Class<? extends Object> objectClass = objectToEdit
							.getClass();
					Field field = objectClass.getField(attributeToEditName);
					System.out.println("field = " + field);
					if (field.getType().isAssignableFrom(ArrayList.class)) {
						System.out
								.println("field.getType().isAssignableFrom(ArrayList.class)");
						ArrayList arrayList = (ArrayList) field
								.get(objectToEdit);
						Class attributeClass = arrayList.get(
								attributeToEditIndex).getClass();
						System.out
								.println("attributeClass = " + attributeClass);
						System.out.println("attributeToEditIndex = "
								+ attributeToEditIndex);
						if (attributeClass.isAssignableFrom(Integer.class)) { // int
							if (48 <= character && character <= 57
									&& textEntered.length() < 8) {
								this.textEntered += character;
								arrayList.set(this.attributeToEditIndex,
										Integer.valueOf(this.textEntered)
												.intValue());
							} else if (character == '-'
									&& textEntered.length() == 0) {
								this.textEntered += character;
							}
						} else if (attributeClass.isAssignableFrom(Float.class)) {
							// float
							if (48 <= character && character <= 57
									&& textEntered.length() < 8) {
								this.textEntered += character;
								arrayList
										.set(this.attributeToEditIndex, Float
												.valueOf(this.textEntered)
												.floatValue());
							} else if (character == '-'
									&& textEntered.length() == 0) {
								this.textEntered += character;
							} else if (character == '.'
									&& !textEntered.contains(".")
									&& textEntered.length() > 0
									&& textEntered.length() < 8) {
								this.textEntered += character;
							}
							System.out.println("Text entered = " + textEntered);
						} else if (attributeClass
								.isAssignableFrom(String.class)) { // string
							this.textEntered += character;
							arrayList.set(this.attributeToEditIndex,
									textEntered);
						}

					} else {
						if (field.getType().isAssignableFrom(int.class)) { // int
							if (48 <= character && character <= 57
									&& textEntered.length() < 8) {
								this.textEntered += character;
								field.set(objectToEdit,
										Integer.valueOf(this.textEntered)
												.intValue());
							} else if (character == '-'
									&& textEntered.length() == 0) {
								this.textEntered += character;
							}
						} else if (field.getType()
								.isAssignableFrom(float.class)) {
							// float
							if (48 <= character && character <= 57
									&& textEntered.length() < 8) {
								this.textEntered += character;
								field.set(objectToEdit,
										Float.valueOf(this.textEntered)
												.floatValue());
							} else if (character == '-'
									&& textEntered.length() == 0) {
								this.textEntered += character;
							} else if (character == '.'
									&& !textEntered.contains(".")
									&& textEntered.length() > 0
									&& textEntered.length() < 8) {
								this.textEntered += character;
							}
							System.out.println("Text entered = " + textEntered);
						} else if (field.getType().isAssignableFrom(
								String.class)) { // string
							this.textEntered += character;
							field.set(objectToEdit, textEntered);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		settingsWindow.update();
	}

	public void enterTyped() {
		if (state == STATE.SETTINGS_CHANGE && settingsButton != null) {
			settingsButton.enterTyped();
		} else if (objectToEdit != null && attributeToEditName != null) {
			stopEditingAttribute();
		}
	}

	public void backTyped() {
		if (state == STATE.SETTINGS_CHANGE && settingsButton != null) {
			settingsButton.backTyped();
			return;
		}

		if (objectToEdit != null && attributeToEditName != null
				&& this.textEntered != null && textEntered.length() > 0) {
			this.textEntered = this.textEntered.substring(0,
					this.textEntered.length() - 1);
		}

		if (objectToEdit != null && attributeToEditName != null
				&& this.textEntered != null) {

			Class<? extends Object> objectClass = objectToEdit.getClass();
			try {
				Field field = objectClass.getField(attributeToEditName);

				if (field.getType().isAssignableFrom(int.class)) { // int
					if (textEntered.length() == 0) {
						field.set(objectToEdit, 0);
					} else {
						field.set(objectToEdit,
								Integer.valueOf(this.textEntered).intValue());
					}
				} else if (field.getType().isAssignableFrom(float.class)) {
					// float
					if (textEntered.length() == 0) {
						field.set(objectToEdit, 0);
					} else {
						field.set(objectToEdit, Float.valueOf(this.textEntered)
								.floatValue());
					}
				} else if (field.getType().isAssignableFrom(String.class)) { // string
					field.set(objectToEdit, textEntered);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void editAttribute(Object object, String attribute,
			AtributesWindowButton attributeButton, int index) {
		objectToEdit = object;
		attributeToEditName = attribute;
		attributeToEditIndex = index;
		try {

			Class<? extends Object> objectClass = objectToEdit.getClass();

			Field field = objectClass.getField(attributeToEditName);

			if (field.getType().isAssignableFrom(Faction.class)) {
				// faction
				attributeSelectionWindow = new AttributeSelectionWindow(
						Game.level.factions, false, this, objectToEdit);
			} else if (field.getType().isAssignableFrom(Color.class)) {
				// color
				attributeSelectionWindow = new AttributeSelectionWindow(colors,
						false, this, objectToEdit);
			} else if (field.getType().isAssignableFrom(Square.class)) {
				// square
				ArrayList<Square> squares = new ArrayList<Square>();
				for (Square[] squareArray : Game.level.squares) {
					for (Square square : squareArray) {
						squares.add(square);
					}
				}
				attributeSelectionWindow = new AttributeSelectionWindow(
						squares, false, this, objectToEdit);
			} else if (field.getType().isAssignableFrom(ScriptEvent.class)) {
				// scriptEvent
				attributeSelectionWindow = new AttributeSelectionWindow(
						Game.level.script.scriptEvents, false, this,
						objectToEdit);
			} else if (field.getType().isAssignableFrom(Texture.class)) {
				// texture
				attributeSelectionWindow = new AttributeSelectionWindow(
						textures, false, this, objectToEdit);
			} else if (field.getType().isAssignableFrom(Weapons.class)) {
				// weapons
				attributeSelectionWindow = new AttributeSelectionWindow(
						weapons, true, this, objectToEdit);
			} else if (field.getType().isAssignableFrom(GameObject.class)) {
				// actor

				ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
				for (Faction faction : Game.level.factions) {
					for (Actor actor : faction.actors) {
						gameObjects.add(actor);
					}
				}
				gameObjects.addAll(Game.level.inanimateObjects);
				attributeSelectionWindow = new AttributeSelectionWindow(
						gameObjects, false, this, objectToEdit);
			} else if (field.getType().isAssignableFrom(Actor.class)) {
				// actor
				ArrayList<Actor> actors = new ArrayList<Actor>();
				for (Faction faction : Game.level.factions) {
					for (Actor actor : faction.actors) {
						actors.add(actor);
					}
				}
				attributeSelectionWindow = new AttributeSelectionWindow(actors,
						false, this, objectToEdit);
			} else if (field.getType().isAssignableFrom(ScriptTrigger.class)) {
				attributeSelectionWindow = new AttributeSelectionWindow(
						Game.level.script.scriptTriggers, false, this,
						objectToEdit);
			} else if (field.getType().isAssignableFrom(AI.class)) {
				attributeSelectionWindow = new AttributeSelectionWindow(
						Game.level.ais, false, this, objectToEdit);
			} else if (field.getType().isAssignableFrom(boolean.class)) {
				boolean b = (boolean) field.get(objectToEdit);
				field.set(objectToEdit, !b);
				settingsWindow.update();
				stopEditingAttribute();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		textEntered = "";
		this.attributeButton = attributeButton;
	}

	public void clearSelectedObject() {
		attributeSelectionWindow = null;
		classSelectionWindow = null;
		this.selectedGameObject = null;
		this.attributesWindow = null;
		objectToEdit = null;
		attributeToEditName = null;
		textEntered = "";
		this.attributeButton = null;
		if (state == STATE.MOVEABLE_OBJECT_SELECTED)
			state = STATE.DEFAULT;
	}

	public void stopEditingAttribute() {

		attributeSelectionWindow = null;
		classSelectionWindow = null;
		objectToEdit = null;
		attributeToEditName = null;
		this.textEntered = "";
		attributesWindow.depressButtons();
	}

	public void rightClick() {
		attributeSelectionWindow = null;
		classSelectionWindow = null;
		depressButtonsSettingsAndDetailsButtons();
		clearSelectedObject();
		state = STATE.DEFAULT;
	}

	public void depressTabButtons() {
		for (Button button : buttons) {
			button.down = false;
		}
	}

	public void depressButtonsSettingsAndDetailsButtons() {
		settingsWindow.depressButtons();
		if (attributesWindow != null)
			attributesWindow.depressButtons();
	}
}
