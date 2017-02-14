package com.marklynch.editor;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.editor.popup.Popup;
import com.marklynch.editor.popup.PopupSelectObject;
import com.marklynch.editor.popup.PopupSelectPlaceToPutObject;
import com.marklynch.editor.settingswindow.AIsSettingsWindow;
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
import com.marklynch.editor.settingswindow.TemplatesSettingsWindow;
import com.marklynch.level.Level;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Building;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.FactionRelationship;
import com.marklynch.level.constructs.Pack;
import com.marklynch.level.constructs.QuestSmallGame;
import com.marklynch.objects.Bed;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.GameObjectExploder;
import com.marklynch.objects.GameObjectTemplate;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.Junk;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.ActorTemplate;
import com.marklynch.objects.units.Hunter;
import com.marklynch.objects.units.Trader;
import com.marklynch.objects.units.WildAnimal;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.objects.weapons.WeaponTemplate;
import com.marklynch.script.ScriptEvent;
import com.marklynch.script.ScriptEventSpeech;
import com.marklynch.script.trigger.ScriptTrigger;
import com.marklynch.script.trigger.ScriptTriggerActorSelected;
import com.marklynch.ui.Toast;
import com.marklynch.ui.button.AtributesWindowButton;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.button.SettingsWindowButton;
import com.marklynch.utils.LineUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Editor {

	public String json = null;

	public ArrayList<Button> tabs = new ArrayList<Button>();

	// faction, actors, object, scriptevent, script
	// trigger, weapon,
	// level, squares

	public AttributesDialog attributesWindow;

	public Button levelTabButton;
	public Button squaresTabButton;
	public Button objectsTabButton;
	public Button factionsTabButton;
	public Button colorsTabButton;
	public Button templatesTabButton;
	public Button decorationsTabButton;
	public Button scriptEventsTabButton;
	public Button scriptTriggersTabButton;
	public Button aisTabButton;
	public Button relationsTabButton;
	public Button speechPartTabButton;

	public SettingsWindow settingsWindow;
	public LevelSettingsWindow levelSettingsWindow;
	public SquaresSettingsWindow squaresSettingsWindow;
	public ObjectsSettingsWindow objectsSettingsWindow;
	public FactionsSettingsWindow factionsSettingsWindow;
	public TemplatesSettingsWindow weaponTemplatesSettingsWindow;
	public ColorSettingsWindow colorsSettingsWindow;
	public DecorationsSettingsWindow decorationsSettingsWindow;
	public ScriptEventsSettingsWindow scriptsEventsSettingsWindow;
	public ScriptTriggersSettingsWindow scriptsTriggersSettingsWindow;
	public AIsSettingsWindow aisSettingsWindow;
	public RelationsSettingsWindow relationsSettingsWindow;
	public SpeechPartSettingsWindow speechPartSettingsWindow;
	public Popup popup;
	public Toast toast;

	public GameObject selectedGameObject;

	public Object objectToEdit = null;
	public String attributeToEditName = "";
	public int attributeToEditIndex = 0;
	public String textEntered = "";
	public AtributesWindowButton attributeButton = null;
	public SettingsWindowButton settingsButton = null;

	public ArrayList<Texture> textures = new ArrayList<Texture>();
	public ArrayList<GameObjectTemplate> gameObjectTemplates = new ArrayList<GameObjectTemplate>();
	public ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	public AttributeSelectionWindow attributeSelectionWindow;
	public GameObjectTemplate gameObjectTemplate;
	public Actor actorTemplate;

	public enum EDITOR_STATE {
		DEFAULT, ADD_OBJECT, MOVEABLE_OBJECT_SELECTED, SETTINGS_CHANGE
	}

	public EDITOR_STATE editorState = EDITOR_STATE.DEFAULT;

	public ArrayList<Color> colors = new ArrayList<Color>();

	public ClassSelectionWindow classSelectionWindow;
	public InstanceSelectionWindow instanceSelectionWindow;

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

		WeaponTemplate weaponTemplate0 = new WeaponTemplate("a3r1", 3, 1, 1, "a3r1.png", 100, null, true, false);
		gameObjectTemplates.add(weaponTemplate0);
		WeaponTemplate weaponTemplate1 = new WeaponTemplate("a2r2", 2, 2, 2, "a2r2.png", 100, null, true, false);
		gameObjectTemplates.add(weaponTemplate1);
		WeaponTemplate weaponTemplate2 = new WeaponTemplate("a5r3", 5, 3, 3, "a2r2.png", 100, null, true, false);
		gameObjectTemplates.add(weaponTemplate2);

		GameObjectTemplate gameObjectTemplate = new GameObjectTemplate("dumpster", 5, "skip_with_shadow.png", null,
				new Inventory(), true, false, false, true);
		gameObjectTemplates.add(gameObjectTemplate);

		Junk furTemplate = new Junk("Fur", 5, "fur.png", null, new Inventory(), false, true, true, false);
		gameObjectTemplates.add(furTemplate);

		ActorTemplate actorTemplate = new ActorTemplate("Old lady", "Fighter", 1, 10, 0, 0, 0, 0, "red1.png", null, 4,
				new Inventory(), false, false, true);
		gameObjectTemplates.add(actorTemplate);

		for (int i = 0; i < gameObjectTemplates.size(); i++) {
			gameObjectTemplates.get(i).loadImages();
		}

		Game.level = new Level(20, 20);

		levelSettingsWindow = new LevelSettingsWindow(200, this);
		squaresSettingsWindow = new SquaresSettingsWindow(200, this);
		objectsSettingsWindow = new ObjectsSettingsWindow(200, this);
		factionsSettingsWindow = new FactionsSettingsWindow(200, this);
		weaponTemplatesSettingsWindow = new TemplatesSettingsWindow(200, this);
		colorsSettingsWindow = new ColorSettingsWindow(200, this);
		decorationsSettingsWindow = new DecorationsSettingsWindow(200, this);
		scriptsEventsSettingsWindow = new ScriptEventsSettingsWindow(200, this);
		scriptsTriggersSettingsWindow = new ScriptTriggersSettingsWindow(200, this);
		aisSettingsWindow = new AIsSettingsWindow(200, this);
		relationsSettingsWindow = new RelationsSettingsWindow(200, this);
		speechPartSettingsWindow = new SpeechPartSettingsWindow(200, this);

		settingsWindow = levelSettingsWindow;

		generateTestObjects();

		// TABS
		String tabText = "LEVEL";
		levelTabButton = new LevelButton(10, 10, Game.font.getWidth(tabText), 30, "", "", tabText, true, true);
		tabs.add(levelTabButton);
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

		tabText = "SQUARES";
		squaresTabButton = new LevelButton(90, 10, Game.font.getWidth(tabText), 30, "", "", tabText, true, true);
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
		tabs.add(squaresTabButton);

		tabText = "ACTORS + OBJECTS";
		objectsTabButton = new LevelButton(210, 10, Game.font.getWidth(tabText), 30, "", "", tabText, true, true);
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
		tabs.add(objectsTabButton);

		tabText = "TEMPLATES";
		templatesTabButton = new LevelButton(560, 10, Game.font.getWidth(tabText), 30, "", "", tabText, true, true);
		templatesTabButton.clickListener = new ClickListener() {
			@Override
			public void click() {
				clearSelectedObject();
				depressButtonsSettingsAndDetailsButtons();
				depressTabButtons();
				templatesTabButton.down = true;
				settingsWindow = weaponTemplatesSettingsWindow;
				settingsWindow.update();
			}
		};
		tabs.add(templatesTabButton);

		tabText = "FACTIONS";
		factionsTabButton = new LevelButton(430, 10, Game.font.getWidth(tabText), 30, "", "", tabText, true, true);
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
		tabs.add(factionsTabButton);

		tabText = "COLORS";
		colorsTabButton = new LevelButton(690, 10, Game.font.getWidth(tabText), 30, "", "", tabText, true, true);
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
		tabs.add(colorsTabButton);

		tabText = "DECORATIONS";
		decorationsTabButton = new LevelButton(10, 50, Game.font.getWidth(tabText), 30, "", "", tabText, true, true);
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
		tabs.add(decorationsTabButton);

		tabText = "SCRIPT EVENTS";
		scriptEventsTabButton = new LevelButton(180, 50, Game.font.getWidth(tabText), 30, "", "", tabText, true, true);
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
		tabs.add(scriptEventsTabButton);

		tabText = "SCRIPT TRIGGERS";
		scriptTriggersTabButton = new LevelButton(350, 50, Game.font.getWidth(tabText), 30, "", "", tabText, true,
				true);
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
		tabs.add(scriptTriggersTabButton);

		tabText = "AIS";
		aisTabButton = new LevelButton(520, 50, Game.font.getWidth(tabText), 30, "", "", tabText, true, true);
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
		tabs.add(aisTabButton);

		tabText = "RELATIONS";
		relationsTabButton = new LevelButton(590, 50, Game.font.getWidth(tabText), 30, "", "", tabText, true, true);
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
		tabs.add(relationsTabButton);

		tabText = "SPEECH PART";
		speechPartTabButton = new LevelButton(730, 50, Game.font.getWidth(tabText), 30, "", "", tabText, true, true);
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
		tabs.add(speechPartTabButton);

		// Place the tabs
		placeTabs();

	}

	public void resize() {
		placeTabs();
	}

	public void placeTabs() {
		float tabRow = 0;
		float tabHeight = 30;
		float margin = 10;
		float tabPositionX = margin;

		Button tab = null;

		for (int i = 0; i < tabs.size(); i++) {
			tab = tabs.get(i);
			// if(tabPositionX == 0)
			// {
			// tab.x = 0;
			// tab.y = tabRow*tabHeight;
			// }
			// else
			// {
			if (tabPositionX + tab.width > Game.windowWidth) {
				tabRow++;
				tabPositionX = margin;
			}

			tab.x = tabPositionX;
			tab.y = tabRow * (tabHeight + margin) + margin;
			// }

			tabPositionX += tab.width + margin;
		}
	}

	public void generateTestObjects() {

		ArrayList<Square> doorLocations1 = new ArrayList<Square>();
		doorLocations1.add(Game.level.squares[5][3]);
		Game.level.buildings.add(new Building("Trader Joe's Shop", 5, 0, 9, 5, doorLocations1));

		ArrayList<Square> doorLocations2 = new ArrayList<Square>();
		doorLocations2.add(Game.level.squares[7][9]);
		// doorLocations2.add(Game.level.squares[11][9]);
		Game.level.buildings.add(new Building("Hunter Brent's Home", 7, 7, 11, 11, doorLocations2));

		// 6,1 to 8,4
		// for(int i = 6; i<=8; )

		// Add a game object
		new GameObjectExploder("Dumpster", 5, "skip_with_shadow.png", Game.level.squares[4][2], new Inventory(), true,
				false, false, true);

		new Junk("Ground fur", 5, "fur.png", Game.level.squares[0][7], new Inventory(), false, true, true, false);

		// Add factions
		Game.level.factions.add(new Faction("Player", colors.get(0), "faction_blue.png"));
		Game.level.factions.add(new Faction("Townspeople", colors.get(1), "faction_red.png"));
		Game.level.factions.add(new Faction("Wolves", colors.get(2), "wolf.png"));

		// relationships
		Game.level.factions.get(0).relationships.put(Game.level.factions.get(1),
				new FactionRelationship(-100, Game.level.factions.get(0), Game.level.factions.get(1)));
		Game.level.factions.get(1).relationships.put(Game.level.factions.get(0),
				new FactionRelationship(-100, Game.level.factions.get(1), Game.level.factions.get(0)));

		// Inventory
		Inventory inventoryForActor0 = new Inventory();
		ArrayList<GameObject> weaponsForActor0 = new ArrayList<GameObject>();
		weaponsForActor0.add(gameObjectTemplates.get(0).makeCopy(null));
		weaponsForActor0.add(gameObjectTemplates.get(1).makeCopy(null));
		weaponsForActor0.add(gameObjectTemplates.get(2).makeCopy(null));
		inventoryForActor0.setGameObjects(weaponsForActor0);
		// for (int i = 0; i < weaponsForActor0.size(); i++) {
		// if (weaponsForActor0.get(i) instanceof Weapon)
		// weapons.add((Weapon) weaponsForActor0.get(i));
		// // Game.level.inanimateObjects.add(weaponsForActor0.get(i));
		// }
		Inventory inventoryForActor1 = new Inventory();
		ArrayList<GameObject> weaponsForActor1 = new ArrayList<GameObject>();
		weaponsForActor1.add(gameObjectTemplates.get(0).makeCopy(null));
		weaponsForActor1.add(gameObjectTemplates.get(1).makeCopy(null));
		weaponsForActor1.add(gameObjectTemplates.get(2).makeCopy(null));
		inventoryForActor1.setGameObjects(weaponsForActor1);
		// for (int i = 0; i < weaponsForActor1.size(); i++) {
		// if (weaponsForActor1.get(i) instanceof Weapon)
		// weapons.add((Weapon) weaponsForActor1.get(i));
		// // Game.level.inanimateObjects.add(weaponsForActor1.get(i));
		// }
		Inventory inventoryForWildAnimal = new Inventory();
		inventoryForWildAnimal.add(gameObjectTemplates.get(4).makeCopy(null));
		inventoryForWildAnimal.add(gameObjectTemplates.get(0).makeCopy(null));
		// ArrayList<GameObject> weaponsForActor2 = new ArrayList<GameObject>();
		// weaponsForActor2.add(gameObjectTemplates.get(0).makeCopy(null));
		// inventoryForWildAnimal.setGameObjects(weaponsForActor2);

		// Add actor
		Actor player = new Actor("Old lady", "Fighter", 10, 100, 0, 0, 0, 0, "red1.png", Game.level.squares[0][4], 1,
				null, inventoryForActor0, true, false, true);
		player.faction = Game.level.factions.get(0);
		player.factionGUID = Game.level.factions.get(0).guid;

		Game.level.factions.get(0).actors.add(player);
		for (int i = 0; i < player.inventory.size(); i++) {
			player.inventory.get(i).inventoryThatHoldsThisObject = player.inventory;
		}

		Bed bedForHunterBrent = new Bed("Hunter Brent's Bed", 5, "bed.png", "bed_Covers.png", Game.level.squares[0][5],
				new Inventory(), false, true, false, false);

		Actor hunterBrent = new Hunter("Hunter Brent", "Hunter", 1, 10, 0, 0, 0, 0, "hunter.png",
				Game.level.squares[0][2], 1, bedForHunterBrent, inventoryForActor1, true, false, true);
		hunterBrent.faction = Game.level.factions.get(1);
		hunterBrent.factionGUID = Game.level.factions.get(1).guid;
		Game.level.factions.get(1).actors.add(hunterBrent);
		for (int i = 0; i < hunterBrent.inventory.size(); i++) {
			hunterBrent.inventory.get(i).inventoryThatHoldsThisObject = hunterBrent.inventory;
		}

		Bed bedForHunterBront = new Bed("Hunter Bront's Bed", 5, "bed.png", "bed_Covers.png", Game.level.squares[9][9],
				new Inventory(), false, true, false, false);

		Actor hunterBront = new Hunter("Hunter Bront", "Hunter", 1, 10, 0, 0, 0, 0, "hunter_2.png",
				Game.level.squares[9][9], 1, bedForHunterBront, inventoryForActor1.makeCopy(), true, false, true);
		hunterBront.faction = Game.level.factions.get(1);
		hunterBront.factionGUID = Game.level.factions.get(1).guid;
		Game.level.factions.get(1).actors.add(hunterBront);
		for (int i = 0; i < hunterBront.inventory.size(); i++) {
			hunterBront.inventory.get(i).inventoryThatHoldsThisObject = hunterBront.inventory;
		}

		ArrayList<Actor> hunterPackMembers = new ArrayList<Actor>();
		hunterPackMembers.add(hunterBrent);
		hunterPackMembers.add(hunterBront);
		Pack hunterPack = new Pack("Hunter pack", hunterPackMembers, hunterBrent);

		new GameObject("Hunt Plan", 5, "hunt_plan.png", Game.level.squares[6][8], new Inventory(), true, false, false,
				true);

		Actor environmentalistBill = new Hunter("Environmentalist Bill", "Environmentalist", 1, 10, 0, 0, 0, 0,
				"environmentalist.png", Game.level.squares[5][12], 1, bedForHunterBront, inventoryForActor1.makeCopy(),
				true, false, true);
		environmentalistBill.faction = Game.level.factions.get(1);
		environmentalistBill.factionGUID = Game.level.factions.get(1).guid;
		Game.level.factions.get(1).actors.add(environmentalistBill);
		for (int i = 0; i < environmentalistBill.inventory.size(); i++) {
			environmentalistBill.inventory.get(i).inventoryThatHoldsThisObject = environmentalistBill.inventory;
		}

		Actor superWolf = new WildAnimal("Wolf Queen", "Wild animal", 1, 10, 0, 0, 0, 0, "fire_wolf.png",
				Game.level.squares[9][6], 1, null, inventoryForWildAnimal, true, false, true);
		superWolf.faction = Game.level.factions.get(2);
		superWolf.factionGUID = Game.level.factions.get(2).guid;
		Game.level.factions.get(2).actors.add(superWolf);

		Actor wolf2 = new WildAnimal("Wolf", "Wild animal", 1, 10, 0, 0, 0, 0, "wolf_green.png",
				Game.level.squares[11][6], 1, null, inventoryForWildAnimal.makeCopy(), true, false, true);
		wolf2.faction = Game.level.factions.get(2);
		wolf2.factionGUID = Game.level.factions.get(2).guid;
		Game.level.factions.get(2).actors.add(wolf2);

		Actor wolf3 = new WildAnimal("Wolf", "Wild animal", 1, 10, 0, 0, 0, 0, "wolf.png", Game.level.squares[13][9], 1,
				null, inventoryForWildAnimal.makeCopy(), true, false, true);
		wolf3.faction = Game.level.factions.get(2);
		wolf3.factionGUID = Game.level.factions.get(2).guid;
		Game.level.factions.get(2).actors.add(wolf3);

		ArrayList<Actor> wolfPackMembers = new ArrayList<Actor>();
		wolfPackMembers.add(superWolf);
		wolfPackMembers.add(wolf2);
		wolfPackMembers.add(wolf3);
		Pack wolfPack = new Pack("Wolf pack", wolfPackMembers, superWolf);

		QuestSmallGame questSmallGame = new QuestSmallGame(hunterPack, environmentalistBill, superWolf, wolfPack, null);

		Actor trader = new Trader("Trader Joe", "Trader", 1, 10, 0, 0, 0, 0, "shopKeeper.png", Game.level.squares[7][1],
				1, null, new Inventory(), true, false, true);
		trader.faction = Game.level.factions.get(1);
		trader.factionGUID = Game.level.factions.get(1).guid;
		Game.level.factions.get(1).actors.add(trader);

		// Decorations
		// Cat cat = new Cat("Cat", 345f, 464f, 128f, 128f, false, "cat.png");
		// Game.level.decorations.add(cat);

		// Script

		// SpeechPart 1
		ArrayList<Actor> speechActors1 = new ArrayList<Actor>();
		speechActors1.add(Game.level.factions.get(0).actors.get(0));
		speechActors1.add(Game.level.factions.get(1).actors.get(0));
		ArrayList<Float> speechPositions1 = new ArrayList<Float>();
		speechPositions1.add(0f);
		speechPositions1.add(0f);
		ArrayList<Boolean> speechDirections1 = new ArrayList<Boolean>();
		speechDirections1.add(true);
		speechDirections1.add(false);

		ArrayList<String> arrayList1 = new ArrayList();
		arrayList1.add("HI, THIS IS SCRIPTED SPEECH :D");

		ScriptEventSpeech.SpeechPart speechPart1_1 = new ScriptEventSpeech.SpeechPart(speechActors1, speechPositions1,
				speechDirections1, Game.level.factions.get(0).actors.get(0), arrayList1);

		// SpeechPart 2
		ArrayList<Actor> speechActors2 = new ArrayList<Actor>();
		speechActors2.add(Game.level.factions.get(0).actors.get(0));
		speechActors2.add(Game.level.factions.get(1).actors.get(0));
		ArrayList<Float> speechPositions2 = new ArrayList<Float>();
		speechPositions2.add(0f);
		speechPositions2.add(0f);
		ArrayList<Boolean> speechDirections2 = new ArrayList<Boolean>();
		speechDirections2.add(true);
		speechDirections2.add(false);

		ArrayList<String> arrayList2 = new ArrayList();
		arrayList2.add("HI, PT2 OF THIS TALK");

		ScriptEventSpeech.SpeechPart speechPart1_2 = new ScriptEventSpeech.SpeechPart(speechActors2, speechPositions2,
				speechDirections2, Game.level.factions.get(0).actors.get(0), arrayList2);

		ArrayList<ScriptEventSpeech.SpeechPart> speechParts = new ArrayList<ScriptEventSpeech.SpeechPart>();
		speechParts.add(speechPart1_1.makeCopy());
		speechParts.add(speechPart1_2.makeCopy());
		ScriptTrigger scriptTriggerActorSelected = new ScriptTriggerActorSelected(
				Game.level.factions.get(0).actors.get(0));
		ScriptEventSpeech scriptEventSpeech1 = new ScriptEventSpeech(true, speechParts, scriptTriggerActorSelected);

		// Game.level.script.scriptTriggers.add(new ScriptTriggerTurnStart(1,
		// 0));
		// Game.level.script.scriptTriggers.add(scriptTriggerActorSelected);
		// Game.level.script.speechParts.add(speechPart1_1);
		// Game.level.script.speechParts.add(speechPart1_2);
		// Game.level.ais.add(new AIRoutineTargetObject(gameObject));
		// Game.level.ais.get(0).name = "attackDumpster";
		// Game.level.ais.add(new AIRoutineTargetObject(actor0));
		// Game.level.ais.get(1).name = "attackPlayer";
		//
		// ScriptEventSetAI scriptEventSetAIAttackDumpster = new
		// ScriptEventSetAI(false,
		// Game.level.script.scriptTriggers.get(0).makeCopy(), actor1,
		// Game.level.ais.get(0).makeCopy());
		//
		// ArrayList<ScriptEvent> scriptEvents = new ArrayList<ScriptEvent>();
		// // scriptEvents.add(scriptEventSpeech1);
		// scriptEvents.add(scriptEventSetAIAttackDumpster);
		// scriptEvents.add(scriptEventSpeech1);
		//
		// Game.level.script.scriptEvents = scriptEvents;
		// script.activateScriptEvent();

	}

	public void update(int delta) {

	}

	public void drawOverlay() {

		// draw highlight on selected object

		if (Game.inventoryHoveringOver == null && Game.inventorySquareMouseIsOver != null) {
			Game.inventorySquareMouseIsOver.drawCursor();
		} else if (Game.inventoryHoveringOver == null && Game.buttonHoveringOver == null
				&& Game.squareMouseIsOver != null) {
			Game.squareMouseIsOver.drawCursor();
		}

		if (selectedGameObject != null && selectedGameObject.squareGameObjectIsOn != null) {
			selectedGameObject.squareGameObjectIsOn.drawHighlight();
		}

		// Draw a move line if click will result in move
		if (selectedGameObject != null && selectedGameObject.squareGameObjectIsOn != null
				&& Game.buttonHoveringOver == null && editorState == EDITOR_STATE.MOVEABLE_OBJECT_SELECTED
				&& Game.squareMouseIsOver != null
				&& Game.squareMouseIsOver != this.selectedGameObject.squareGameObjectIsOn) {

			float x1 = this.selectedGameObject.squareGameObjectIsOn.xInGrid * Game.SQUARE_WIDTH + Game.SQUARE_WIDTH / 2;
			float y1 = this.selectedGameObject.squareGameObjectIsOn.yInGrid * Game.SQUARE_HEIGHT
					+ Game.SQUARE_HEIGHT / 2;
			float x2 = Game.squareMouseIsOver.xInGrid * Game.SQUARE_WIDTH + Game.SQUARE_WIDTH / 2;
			float y2 = Game.squareMouseIsOver.yInGrid * Game.SQUARE_HEIGHT + Game.SQUARE_HEIGHT / 2;

			// CircleUtils.drawCircle(Color.white, 10d, x1, y1);
			TextureUtils.drawTexture(Game.level.gameCursor.circle, x1 - 10, x1 + 10, y1 - 10, y1 + 10);
			LineUtils.drawLine(Color.WHITE, x1, y1, x2, y2, 10f);
			TextureUtils.drawTexture(Game.level.gameCursor.circle, x2 - 10, x2 + 10, y2 - 10, y2 + 10);

		}
	}

	public void drawUI() {

		if (attributesWindow != null)
			attributesWindow.draw();

		settingsWindow.draw();

		for (Button button : tabs) {
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

		if (instanceSelectionWindow != null) {
			instanceSelectionWindow.draw();
		}

		if (popup != null) {
			popup.draw();
		}

		if (toast != null) {
			toast.draw();
		}

		if (editorState == EDITOR_STATE.MOVEABLE_OBJECT_SELECTED) {
			TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2, Mouse.getX() + 10, Mouse.getX() + 30,
					Game.windowHeight - Mouse.getY() + 20, Game.windowHeight - Mouse.getY() + 40);
			TextureUtils.drawTexture(selectedGameObject.imageTexture, Mouse.getX() + 10, Mouse.getX() + 30,
					Game.windowHeight - Mouse.getY() + 20, Game.windowHeight - Mouse.getY() + 40);
		}
	}

	public Button getButtonFromMousePosition(float mouseX, float mouseY) {

		if (attributeSelectionWindow != null) {
			return attributeSelectionWindow.getButtonFromMousePosition(mouseX, mouseY);

		}

		if (classSelectionWindow != null) {
			// faction, color, texture
			return classSelectionWindow.getButtonFromMousePosition(mouseX, mouseY);

		}

		if (instanceSelectionWindow != null) {
			// used for new weapon selection right now
			return instanceSelectionWindow.getButtonFromMousePosition(mouseX, mouseY);

		}

		for (Button button : this.tabs) {
			if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
				return button;
		}

		if (popup != null) {
			for (Button button : popup.buttons) {
				if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
					return button;
			}
		}

		if (attributesWindow != null) {
			for (AtributesWindowButton button : attributesWindow.buttons) {
				if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
					return button;
			}
		}

		for (Button button : settingsWindow.buttons) {
			if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
				return button;
		}

		return null;
	}

	public void squareClicked(Square square) {

		if (square.inventory.size() == 0) {// Nothing on the square
			if (editorState == EDITOR_STATE.DEFAULT || editorState == EDITOR_STATE.SETTINGS_CHANGE) {
				selectSquare(square);
			} else if (editorState == EDITOR_STATE.ADD_OBJECT) {
				attemptToAddNewObjectToSquare(square);
				// } else if (editorState == EDITOR_STATE.ADD_ACTOR) {
				// addNewActorToSquare(square);
			} else if (editorState == EDITOR_STATE.MOVEABLE_OBJECT_SELECTED) {
				if (!this.selectedGameObject.canShareSquare && !square.inventory.canShareSquare()) {
					swapGameObjects(this.selectedGameObject, square.inventory.getGameObjectThatCantShareSquare());
				} else {
					moveGameObject(this.selectedGameObject, square);
				}
			}
		} else if (square.inventory.canShareSquare()) {
			// Something on the square, but can share the space
			if (editorState == EDITOR_STATE.DEFAULT || editorState == EDITOR_STATE.SETTINGS_CHANGE) {
				this.clearSelectedObject();
				depressButtonsSettingsAndDetailsButtons();
				popup = new PopupSelectObject(100, this, square);
			} else if (editorState == EDITOR_STATE.ADD_OBJECT) {
				attemptToAddNewObjectToSquare(square);
				// } else if (editorState == EDITOR_STATE.ADD_ACTOR) {
				// addNewActorToSquare(square);
			} else if (editorState == EDITOR_STATE.MOVEABLE_OBJECT_SELECTED) {
				moveGameObject(this.selectedGameObject, square);
			}
		} else {
			// Something on the square, but cant share the space
			if (editorState == EDITOR_STATE.DEFAULT || editorState == EDITOR_STATE.SETTINGS_CHANGE) {
				this.clearSelectedObject();
				depressButtonsSettingsAndDetailsButtons();
				popup = new PopupSelectObject(100, this, square);
			} else if (editorState == EDITOR_STATE.ADD_OBJECT) {
				attemptToAddNewObjectToSquare(square);
			} else if (editorState == EDITOR_STATE.MOVEABLE_OBJECT_SELECTED) {
				if (this.selectedGameObject.canShareSquare) {
					moveGameObject(this.selectedGameObject, square);
				} else {
					swapGameObjects(this.selectedGameObject, square.inventory.getGameObjectThatCantShareSquare());
				}
			}
		}

	}

	public void selectGameObject(GameObject gameObject) {
		// if (gameObject instanceof Actor) {
		// if (this.settingsWindow != this.actorsSettingsWindow)
		// actorsTabButton.click();
		// actorsSettingsWindow.getButton(gameObject).click();
		// } else {
		if (this.settingsWindow != this.objectsSettingsWindow)
			objectsTabButton.click();
		if (gameObject instanceof Actor) {
			objectsSettingsWindow.actorsFilterButton.click();
			objectsSettingsWindow.getButton(gameObject).click();
		} else if (gameObject instanceof Weapon) {
			objectsSettingsWindow.weaponsFilterButton.click();
			objectsSettingsWindow.getButton(gameObject).click();
		} else {
			objectsSettingsWindow.objectsFilterButton.click();
			objectsSettingsWindow.getButton(gameObject).click();
		}
		// }
	}

	public void selectSquare(Square square) {
		if (this.settingsWindow != this.squaresSettingsWindow)
			squaresTabButton.click();
		this.clearSelectedObject();
		attributesWindow = new AttributesDialog(200, 200, 200, square, this);
		depressButtonsSettingsAndDetailsButtons();
	}

	public void attemptToAddNewObjectToSquare(Square square) {

		boolean canBePlaceOnGround = gameObjectTemplate.canShareSquare || square.inventory.canShareSquare();
		boolean canBePlacedInAnInventory = square.inventory.hasGameObjectsThatCanContainOtherObjects();

		if (!canBePlacedInAnInventory && canBePlaceOnGround) {
			// Only option is to place the gameObject on the ground
			placeObjectOnSquare(square);
		} else if (canBePlacedInAnInventory) {

			this.clearSelectedObject();
			depressButtonsSettingsAndDetailsButtons();
			popup = new PopupSelectPlaceToPutObject(100, this, square);
			// Give a popup to choose either
			// Place on ground (MAYBE)
			// Give to Actor X
			// Put in Object Y
		} else {
			this.toast = new Toast("No space for the object here! Please pick a different square. :)");
		}
	}

	public void placeObjectOnSquare(Square square) {
		GameObject gameObject = gameObjectTemplate.makeCopy(square);
		if (gameObject instanceof Actor) {
			((Actor) gameObject).faction.actors.add((Actor) gameObject);
		} else {
			// Game.level.inanimateObjectsOnGround.add(gameObject);
		}
		square.inventory.add(gameObject);
		this.objectsSettingsWindow.update();
		this.toast = new Toast("Select a location to add object");
	}

	public void placeObjectInInventory(GameObject gameObjectThatCanHoldOtherObjects) {
		GameObject gameObjectToPutInInventroy = gameObjectTemplate.makeCopy(null);
		gameObjectThatCanHoldOtherObjects.inventory.add(gameObjectToPutInInventroy);
		this.objectsSettingsWindow.update();
		this.toast = new Toast("Select a location to add object");
		// if (gameObject instanceof Actor) {
		// gameObject.faction.actors.add((Actor) gameObject);
		// } else {
		// Game.level.inanimateObjects.add(gameObject);
		// }
		// square.inventory.add(gameObject);
		// this.objectsSettingsWindow.update();
		// this.toast = new Toast("Select a location to add object");
	}

	public void swapGameObjects(GameObject gameObject1, GameObject gameObject2) {
		Square square1 = gameObject1.squareGameObjectIsOn;
		Square square2 = gameObject2.squareGameObjectIsOn;

		square1.inventory.remove(gameObject1);
		square2.inventory.remove(gameObject2);

		square1.inventory.add(gameObject2);
		square2.inventory.add(gameObject1);

		gameObject1.squareGameObjectIsOn = square2;
		gameObject2.squareGameObjectIsOn = square1;

	}

	public void moveGameObject(GameObject gameObject1, Square square2) {

		Square square1 = gameObject1.squareGameObjectIsOn;

		if (square1 != null)
			square1.inventory.remove(gameObject1);

		square2.inventory.add(gameObject1);

		gameObject1.squareGameObjectIsOn = square2;
	}

	public void keyTyped(char character) {

		if (editorState == EDITOR_STATE.SETTINGS_CHANGE && settingsButton != null) {
			settingsButton.keyTyped(character);
		} else if (objectToEdit != null) {
			if (attributeToEditName != null && this.textEntered != null) {

				try {
					Class<? extends Object> objectClass = objectToEdit.getClass();
					Field field = objectClass.getField(attributeToEditName);
					if (field.getType().isAssignableFrom(ArrayList.class)) {

						ArrayList arrayList = (ArrayList) field.get(objectToEdit);
						Class attributeClass = arrayList.get(attributeToEditIndex).getClass();
						if (attributeClass.isAssignableFrom(Integer.class)) { // int
							if (48 <= character && character <= 57 && textEntered.length() < 8) {
								this.textEntered += character;
								arrayList.set(this.attributeToEditIndex, Integer.valueOf(this.textEntered).intValue());
							} else if (character == '-' && textEntered.length() == 0) {
								this.textEntered += character;
							}
						} else if (attributeClass.isAssignableFrom(Float.class)) {
							// float
							if (48 <= character && character <= 57 && textEntered.length() < 8) {
								this.textEntered += character;
								arrayList.set(this.attributeToEditIndex, Float.valueOf(this.textEntered).floatValue());
							} else if (character == '-' && textEntered.length() == 0) {
								this.textEntered += character;
							} else if (character == '.' && !textEntered.contains(".") && textEntered.length() > 0
									&& textEntered.length() < 8) {
								this.textEntered += character;
							}
						} else if (attributeClass.isAssignableFrom(String.class)) { // string
							this.textEntered += character;
							arrayList.set(this.attributeToEditIndex, textEntered);

						}

					} else {
						if (field.getType().isAssignableFrom(int.class)) { // int
							if (48 <= character && character <= 57 && textEntered.length() < 8) {
								this.textEntered += character;
								field.set(objectToEdit, Integer.valueOf(this.textEntered).intValue());
							} else if (character == '-' && textEntered.length() == 0) {
								this.textEntered += character;
							}
						} else if (field.getType().isAssignableFrom(float.class)) {
							// float
							if (48 <= character && character <= 57 && textEntered.length() < 8) {
								this.textEntered += character;
								field.set(objectToEdit, Float.valueOf(this.textEntered).floatValue());
							} else if (character == '-' && textEntered.length() == 0) {
								this.textEntered += character;
							} else if (character == '.' && !textEntered.contains(".") && textEntered.length() > 0
									&& textEntered.length() < 8) {
								this.textEntered += character;
							}
						} else if (field.getType().isAssignableFrom(String.class)) { // string
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
		if (editorState == EDITOR_STATE.SETTINGS_CHANGE && settingsButton != null) {
			settingsButton.enterTyped();
		} else if (objectToEdit != null && attributeToEditName != null) {
			stopEditingAttribute();
		}
	}

	public void backTyped() {
		if (editorState == EDITOR_STATE.SETTINGS_CHANGE && settingsButton != null) {
			settingsButton.backTyped();
			return;
		}

		if (objectToEdit != null && attributeToEditName != null && this.textEntered != null
				&& textEntered.length() > 0) {
			this.textEntered = this.textEntered.substring(0, this.textEntered.length() - 1);
		}

		if (objectToEdit != null && attributeToEditName != null && this.textEntered != null) {

			Class<? extends Object> objectClass = objectToEdit.getClass();
			try {
				Field field = objectClass.getField(attributeToEditName);

				if (field.getType().isAssignableFrom(int.class)) { // int
					if (textEntered.length() == 0) {
						field.set(objectToEdit, 0);
					} else {
						field.set(objectToEdit, Integer.valueOf(this.textEntered).intValue());
					}
				} else if (field.getType().isAssignableFrom(float.class)) {
					// float
					if (textEntered.length() == 0) {
						field.set(objectToEdit, 0);
					} else {
						field.set(objectToEdit, Float.valueOf(this.textEntered).floatValue());
					}
				} else if (field.getType().isAssignableFrom(String.class)) { // string
					field.set(objectToEdit, textEntered);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void editAttribute(Object object, String attribute, AtributesWindowButton attributeButton, int index) {
		objectToEdit = object;
		attributeToEditName = attribute;
		attributeToEditIndex = index;
		try {

			// if (field.getType().isAssignableFrom(ArrayList.class)) {

			Class<? extends Object> objectClass = objectToEdit.getClass();
			Field field = objectClass.getField(attributeToEditName);
			Class type = null;
			ArrayList arrayList = null;
			if (field.getType().isAssignableFrom(ArrayList.class)) {
				arrayList = (ArrayList) field.get(objectToEdit);
				type = arrayList.get(attributeToEditIndex).getClass();
			} else {
				type = field.getType();
			}

			if (type.isAssignableFrom(Inventory.class)) {
				// faction
				((GameObject) objectToEdit).inventory.open();
			} else if (type.isAssignableFrom(Faction.class)) {
				// faction
				attributeSelectionWindow = new AttributeSelectionWindow(Game.level.factions, false, this, objectToEdit,
						"Select a Faction");
			} else if (type.isAssignableFrom(Color.class)) {
				// color
				attributeSelectionWindow = new AttributeSelectionWindow(colors, false, this, objectToEdit,
						"Select a Color");
			} else if (type.isAssignableFrom(Square.class)) {
				// square
				ArrayList<Square> squares = new ArrayList<Square>();
				for (Square[] squareArray : Game.level.squares) {
					for (Square square : squareArray) {
						squares.add(square);
					}
				}
				attributeSelectionWindow = new AttributeSelectionWindow(squares, false, this, objectToEdit,
						"Select a Square");
			} else if (type.isAssignableFrom(ScriptEvent.class)) {
				// scriptEvent
				attributeSelectionWindow = new AttributeSelectionWindow(Game.level.script.scriptEvents, false, this,
						objectToEdit, "Select a Script Event");
			} else if (type.isAssignableFrom(Texture.class)) {
				// texture
				attributeSelectionWindow = new AttributeSelectionWindow(textures, false, this, objectToEdit,
						"Select a Texture");
				// } else if (type.isAssignableFrom(Weapons.class)) { HERE IS
				// THE ISSUE
				// // weapons
				// attributeSelectionWindow = new
				// AttributeSelectionWindow(weapons, true, this, objectToEdit,
				// "Select a Weapon");
				// // } else if (type.isAssignableFrom(WeaponTemplate.class)) {
				// // // weapon templates
				// // attributeSelectionWindow = new
				// // AttributeSelectionWindow(weaponTemplates, true, this,
				// // objectToEdit,
				// // "Select a Weapon Template");
			} else if (type.isAssignableFrom(GameObject.class)) {
				// actor

				ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
				for (Faction faction : Game.level.factions) {
					for (Actor actor : faction.actors) {
						gameObjects.add(actor);
					}
				}
				gameObjects.addAll(Game.level.inanimateObjectsOnGround);
				attributeSelectionWindow = new AttributeSelectionWindow(gameObjects, false, this, objectToEdit,
						"Select an Object");
			} else if (type.isAssignableFrom(Actor.class)) {
				// actor
				ArrayList<Actor> actors = new ArrayList<Actor>();
				for (Faction faction : Game.level.factions) {
					for (Actor actor : faction.actors) {
						actors.add(actor);
					}
				}
				attributeSelectionWindow = new AttributeSelectionWindow(actors, false, this, objectToEdit,
						"Select an Actor");
			} else if (type.isAssignableFrom(ScriptTrigger.class)) {
				attributeSelectionWindow = new AttributeSelectionWindow(Game.level.script.scriptTriggers, false, this,
						objectToEdit, "Select a Script Trigger");
			} else if (type.isAssignableFrom(AIRoutineUtils.class)) {
				attributeSelectionWindow = new AttributeSelectionWindow(Game.level.ais, false, this, objectToEdit,
						"Select an AI Routine");
			} else if (type.isAssignableFrom(boolean.class) || type.isAssignableFrom(Boolean.class)) {

				if (arrayList != null) {
					arrayList.set(attributeToEditIndex, !(boolean) arrayList.get(attributeToEditIndex));

				} else {
					boolean b = (boolean) field.get(objectToEdit);
					field.set(objectToEdit, !b);
				}
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
		instanceSelectionWindow = null;
		this.selectedGameObject = null;
		this.attributesWindow = null;
		objectToEdit = null;
		attributeToEditName = null;
		popup = null;
		toast = null;
		textEntered = "";
		this.attributeButton = null;
		if (editorState == EDITOR_STATE.MOVEABLE_OBJECT_SELECTED)
			editorState = EDITOR_STATE.DEFAULT;
	}

	public void stopEditingAttribute() {

		attributeSelectionWindow = null;
		classSelectionWindow = null;
		instanceSelectionWindow = null;
		objectToEdit = null;
		attributeToEditName = null;
		popup = null;
		toast = null;
		this.textEntered = "";
		attributesWindow.depressButtons();
	}

	public void rightClick() {
		attributeSelectionWindow = null;
		classSelectionWindow = null;
		instanceSelectionWindow = null;
		popup = null;
		toast = null;
		depressButtonsSettingsAndDetailsButtons();
		clearSelectedObject();
		editorState = EDITOR_STATE.DEFAULT;
	}

	public void depressTabButtons() {
		for (Button button : tabs) {
			button.down = false;
		}
	}

	public void depressButtonsSettingsAndDetailsButtons() {
		settingsWindow.depressButtons();
		if (attributesWindow != null)
			attributesWindow.depressButtons();
	}
}
