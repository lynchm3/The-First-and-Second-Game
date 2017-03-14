package com.marklynch.editor;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

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
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.FactionRelationship;
import com.marklynch.level.constructs.Group;
import com.marklynch.level.constructs.QuestSmallGame;
import com.marklynch.level.constructs.structure.Structure;
import com.marklynch.level.constructs.structure.StructureHall;
import com.marklynch.level.constructs.structure.StructureRoom;
import com.marklynch.level.constructs.structure.StructureSection;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.GameObjectExploder;
import com.marklynch.objects.GameObjectTemplate;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.Junk;
import com.marklynch.objects.Sign;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Tree;
import com.marklynch.objects.Window;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Hunter;
import com.marklynch.objects.units.Trader;
import com.marklynch.objects.units.WildAnimal;
import com.marklynch.objects.weapons.Weapon;
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
		Game.level = new Level(100, 50);

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
		levelTabButton = new LevelButton(10, 10, Game.font.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE);
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
		squaresTabButton = new LevelButton(90, 10, Game.font.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE);
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
		objectsTabButton = new LevelButton(210, 10, Game.font.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE);
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
		templatesTabButton = new LevelButton(560, 10, Game.font.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE);
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
		factionsTabButton = new LevelButton(430, 10, Game.font.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE);
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
		colorsTabButton = new LevelButton(690, 10, Game.font.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE);
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
		decorationsTabButton = new LevelButton(10, 50, Game.font.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE);
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
		scriptEventsTabButton = new LevelButton(180, 50, Game.font.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE);
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
		scriptTriggersTabButton = new LevelButton(350, 50, Game.font.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE);
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
		aisTabButton = new LevelButton(520, 50, Game.font.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE);
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
		relationsTabButton = new LevelButton(590, 50, Game.font.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE);
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
		speechPartTabButton = new LevelButton(730, 50, Game.font.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE);
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

		// Add factions
		Game.level.factions.add(new Faction("Player", colors.get(0), "faction_blue.png"));
		Game.level.factions.add(new Faction("Townspeople", colors.get(1), "faction_red.png"));
		Game.level.factions.add(new Faction("Wolves", colors.get(2), "wolf.png"));
		Game.level.factions.add(new Faction("Blind", colors.get(3), "blind.png"));

		// Add player
		Actor player = Templates.OLD_LADY.makeCopy(Game.level.squares[46][10], Game.level.factions.get(0));
		Game.level.player = player;
		player.inventory.add(Templates.KATANA.makeCopy(null));
		player.inventory.add(Templates.HATCHET.makeCopy(null));
		player.inventory.add(Templates.HUNTING_BOW.makeCopy(null));
		player.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		player.anchorX = 80;
		player.anchorY = 80;

		// Joe's shop
		ArrayList<Square> entranceSquares = new ArrayList<Square>(
				Arrays.asList(new Square[] { Game.level.squares[4][4] }));

		ArrayList<GameObject> shopFeatures = new ArrayList<GameObject>();
		shopFeatures.add(new Door("Shop Door", 100, "door.png", Game.level.squares[5][4], new Inventory(), false, true,
				false, false, true, true, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f));
		shopFeatures.add(new Door("Shop Door", 100, "door.png", Game.level.squares[11][4], new Inventory(), false, true,
				false, false, true, true, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f));

		shopFeatures.add(new GameObject("Shop Wall", 1000, "wall.png", Game.level.squares[6][5], new Inventory(), false,
				true, false, false, false, true, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f));
		shopFeatures.add(new Window("Shop Window", 10, "window_left.png", Game.level.squares[6][5], new Inventory(),
				false, false, false, false, false, true, 1, 5, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f,
				0f));
		shopFeatures.add(new GameObject("Shop Wall", 1000, "wall.png", Game.level.squares[7][5], new Inventory(), false,
				true, false, false, false, true, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f));
		shopFeatures.add(new Window("Shop Window", 10, "window_right.png", Game.level.squares[7][5], new Inventory(),
				false, false, false, false, false, true, 1, 5, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f,
				0f));
		shopFeatures.add(new GameObject("Shop Wall", 1000, "wall.png", Game.level.squares[9][5], new Inventory(), false,
				true, false, false, false, true, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f));
		shopFeatures.add(new Window("Shop Window", 10, "window_left.png", Game.level.squares[9][5], new Inventory(),
				false, false, false, false, false, true, 1, 5, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f,
				0f));
		shopFeatures.add(
				new GameObject("Shop Wall", 1000, "wall.png", Game.level.squares[10][5], new Inventory(), false, true,
						false, false, false, true, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f));
		shopFeatures.add(new Window("Shop Window", 10, "window_right.png", Game.level.squares[10][5], new Inventory(),
				false, false, false, false, false, true, 1, 5, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f,
				0f));
		shopFeatures.add(
				new GameObject("Shop Wall", 1000, "wall.png", Game.level.squares[12][5], new Inventory(), false, true,
						false, false, false, true, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f));
		shopFeatures.add(new Window("Shop Window", 10, "window_left.png", Game.level.squares[12][5], new Inventory(),
				false, false, false, false, false, true, 1, 5, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f,
				0f));
		shopFeatures.add(
				new GameObject("Shop Wall", 1000, "wall.png", Game.level.squares[13][5], new Inventory(), false, true,
						false, false, false, true, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f));
		shopFeatures.add(new Window("Shop Window", 10, "window_right.png", Game.level.squares[13][5], new Inventory(),
				false, false, false, false, false, true, 1, 5, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f,
				0f));
		shopFeatures.add(
				new GameObject("Shop Wall", 1000, "wall.png", Game.level.squares[15][5], new Inventory(), false, true,
						false, false, false, true, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f));
		shopFeatures.add(new Window("Shop Window", 10, "window_left.png", Game.level.squares[15][5], new Inventory(),
				false, false, false, false, false, true, 1, 5, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f,
				0f));
		shopFeatures.add(
				new GameObject("Shop Wall", 1000, "wall.png", Game.level.squares[16][5], new Inventory(), false, true,
						false, false, false, true, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f));
		shopFeatures.add(new Window("Shop Window", 10, "window_right.png", Game.level.squares[16][5], new Inventory(),
				false, false, false, false, false, true, 1, 5, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f,
				0f));

		Game.level.squares[4][4].imageTexturePath = "stone.png";
		Game.level.squares[4][4].loadImages();

		ArrayList<StructureRoom> shopAtriums = new ArrayList<StructureRoom>();
		shopAtriums.add(new StructureRoom("Super Wolf's Den", 6, 1, 10, 4));
		shopAtriums.add(new StructureRoom("Super Wolf's Den", 12, 1, 16, 4));
		ArrayList<StructureSection> shopSections = new ArrayList<StructureSection>();
		shopSections.add(new StructureSection("Super Wolf's Den", 5, 0, 17, 5));
		Structure joesShop = new Structure("Trader Joe's Shop", shopSections, shopAtriums,
				new ArrayList<StructureHall>(), shopFeatures, entranceSquares, "building2.png", 640, 640 + 1664, -100,
				-100 + 868, true);
		Game.level.structures.add(joesShop);
		// Hunting lodge
		ArrayList<GameObject> lodgeFeatures = new ArrayList<GameObject>();
		ArrayList<StructureRoom> lodgeAtriums = new ArrayList<StructureRoom>();
		lodgeAtriums.add(new StructureRoom("Super Wolf's Den", 8, 8, 10, 10));
		ArrayList<StructureSection> lodgeSections = new ArrayList<StructureSection>();
		lodgeSections.add(new StructureSection("Super Wolf's Den", 7, 7, 11, 11));
		Structure lodge = new Structure("Hunting Lodge", lodgeSections, lodgeAtriums, new ArrayList<StructureHall>(),
				lodgeFeatures, new ArrayList<Square>(), "building.png", 896, 896 + 640, 896, 896 + 640, true);
		Game.level.structures.add(lodge);

		// ArrayList<Square> doorLocations2 = new ArrayList<Square>();
		// doorLocations2.add(Game.level.squares[7][9]);
		// // doorLocations2.add(Game.level.squares[11][9]);
		// Game.level.structures.add(new Building("Hunting Lodge", 7, 7, 11, 11,
		// doorLocations2));

		// Cave
		ArrayList<GameObject> caveFeatures = new ArrayList<GameObject>();
		ArrayList<StructureHall> cavePaths = new ArrayList<StructureHall>();
		ArrayList<Square> cavePathSquares = new ArrayList<Square>();
		cavePathSquares.add(Game.level.squares[24][14]);
		cavePathSquares.add(Game.level.squares[25][14]);
		cavePaths.add(new StructureHall("Path", cavePathSquares));
		ArrayList<Square> cavePathSquares2 = new ArrayList<Square>();
		cavePathSquares.add(Game.level.squares[38][16]);
		cavePathSquares.add(Game.level.squares[39][16]);
		cavePathSquares.add(Game.level.squares[39][15]);
		cavePathSquares.add(Game.level.squares[39][14]);
		cavePathSquares.add(Game.level.squares[40][14]);
		cavePathSquares.add(Game.level.squares[41][14]);
		cavePathSquares.add(Game.level.squares[42][14]);
		cavePaths.add(new StructureHall("Path", cavePathSquares2));

		ArrayList<StructureRoom> caveAtriums = new ArrayList<StructureRoom>();
		caveAtriums.add(new StructureRoom("Wolf's Den", 25, 13, 37, 18));
		caveAtriums.add(new StructureRoom("Entrance to the Blind", 43, 7, 47, 14));
		ArrayList<StructureSection> caveSections = new ArrayList<StructureSection>();
		caveSections.add(new StructureSection("Cave of the Blind", 24, 12, 40, 19));
		caveSections.add(new StructureSection("Cave of the Blind", 41, 5, 49, 16));
		Game.level.structures.add(new Structure("Cave of the Blind", caveSections, caveAtriums, cavePaths, caveFeatures,
				new ArrayList<Square>(), null, 0, 0, 0, 0, true));

		Sign rockWithEtching = Templates.ROCK_WITH_ETCHING.makeCopy(Game.level.squares[45][14]);
		rockWithEtching.setText(new Object[] { "SHHHHHhhhhhhhh..." });

		// Add blind
		Actor blind1 = Templates.BLIND.makeCopy(Game.level.squares[46][7], Game.level.factions.get(3));
		blind1.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind1.addAttackerForThisAndGroupMembers(player);

		// Add spoons
		Weapon serratedSpoon1 = Templates.SERRATED_SPOON.makeCopy(Game.level.squares[44][11]);

		// 6,1 to 8,4
		// for(int i = 6; i<=8; )

		// Add a game object
		new GameObjectExploder("Dumpster", 5, "skip_with_shadow.png", Game.level.squares[4][2], new Inventory(), true,
				false, false, true, false, false, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f);

		Junk groundFur = new Junk("Ground fur", 5, "fur.png", Game.level.squares[0][7], new Inventory(), false, true,
				true, false, false, false, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f);
		groundFur.value = 10;
		// new Junk("Ground fur", 5, "fur.png", Game.level.squares[21][9], new
		// Inventory(), false, true, true, false);
		// new Junk("Ground fur", 5, "fur.png", Game.level.squares[23][16], new
		// Inventory(), false, true, true, false);
		// groundFur.value = 10;
		// groundFur = new Junk("Ground fur", 5, "fur.png",
		// Game.level.squares[33][19], new Inventory(), false, true, true,
		// false, 1, 1);
		// groundFur.value = 10;

		new Tree("Tree", 100, "tree_1.png", Game.level.squares[1][2], new Inventory(), true, false, false, true, false,
				true, 1f, 1.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f);
		new Tree("Tree", 100, "tree_1.png", Game.level.squares[21][19], new Inventory(), true, false, false, true,
				false, true, 1f, 1.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f);
		new Tree("Tree", 100, "tree_1.png", Game.level.squares[14][8], new Inventory(), true, false, false, true, false,
				true, 1f, 1.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f);
		new Tree("Tree", 100, "tree_1.png", Game.level.squares[19][3], new Inventory(), true, false, false, true, false,
				true, 1f, 1.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f);
		new Tree("Tree", 100, "tree_1.png", Game.level.squares[18][13], new Inventory(), true, false, false, true,
				false, true, 1f, 1.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f);
		new Tree("Tree", 100, "tree_1.png", Game.level.squares[9][14], new Inventory(), true, false, false, true, false,
				true, 1f, 1.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f);
		new Tree("Tree", 100, "tree_1.png", Game.level.squares[12][8], new Inventory(), true, false, false, true, false,
				true, 1f, 1.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f);
		new Tree("Tree", 100, "tree_1.png", Game.level.squares[27][3], new Inventory(), true, false, false, true, false,
				true, 1f, 1.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f);
		new Tree("Tree", 100, "tree_1.png", Game.level.squares[23][5], new Inventory(), true, false, false, true, false,
				true, 1f, 1.5f, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f);

		// relationships
		Game.level.factions.get(0).relationships.put(Game.level.factions.get(1),
				new FactionRelationship(-100, Game.level.factions.get(0), Game.level.factions.get(1)));
		Game.level.factions.get(1).relationships.put(Game.level.factions.get(0),
				new FactionRelationship(-100, Game.level.factions.get(1), Game.level.factions.get(0)));

		// Add lead hunter
		Actor hunterBrent = Templates.HUNTER.makeCopy(Game.level.squares[5][8], Game.level.factions.get(1));
		hunterBrent.inventory.add(Templates.HUNTING_BOW.makeCopy(null));
		hunterBrent.bed = Templates.BED.makeCopy(Game.level.squares[10][9]);
		hunterBrent.equippedWeapon = (Weapon) hunterBrent.inventory.get(0);
		hunterBrent.equippedWeaponGUID = hunterBrent.inventory.get(0).guid;

		// Add hunters
		Actor hunterBront1 = Templates.HUNTER.makeCopy(Game.level.squares[3][7], Game.level.factions.get(1));
		hunterBront1.inventory.add(Templates.HUNTING_BOW.makeCopy(null));
		hunterBront1.bed = Templates.BED.makeCopy(Game.level.squares[9][9]);
		hunterBront1.equippedWeapon = (Weapon) hunterBrent.inventory.get(0);
		hunterBront1.equippedWeaponGUID = hunterBrent.inventory.get(0).guid;

		Actor hunterBront2 = Templates.HUNTER.makeCopy(Game.level.squares[3][8], Game.level.factions.get(1));
		hunterBront2.inventory.add(Templates.HATCHET.makeCopy(null));
		hunterBront2.equippedWeapon = (Weapon) hunterBrent.inventory.get(0);
		hunterBront2.equippedWeaponGUID = hunterBrent.inventory.get(0).guid;

		Actor hunterBront3 = hunterBront2.makeCopy(Game.level.squares[3][9], Game.level.factions.get(1));
		Actor hunterBront4 = hunterBront2.makeCopy(Game.level.squares[2][7], Game.level.factions.get(1));
		Actor hunterBront5 = hunterBront2.makeCopy(Game.level.squares[2][8], Game.level.factions.get(1));
		Actor hunterBront6 = hunterBront2.makeCopy(Game.level.squares[2][9], Game.level.factions.get(1));

		ArrayList<Actor> hunterPackMembers = new ArrayList<Actor>();
		hunterPackMembers.add(hunterBrent);
		hunterPackMembers.add(hunterBront1);
		hunterPackMembers.add(hunterBront2);
		hunterPackMembers.add(hunterBront3);
		hunterPackMembers.add(hunterBront4);
		hunterPackMembers.add(hunterBront5);
		hunterPackMembers.add(hunterBront6);

		Group hunterPack = new Group("Hunting party", hunterPackMembers, hunterBrent);

		Sign huntingPlan = Templates.SIGN.makeCopy(Game.level.squares[6][8]);
		huntingPlan.setText(new Object[] { "Super Wolf - Weaknesses: Water Strengths: Fire will heal the beast" });
		huntingPlan.name = "Hunt Action Plan";

		Actor environmentalistBill = new Hunter("Environmentalist Bill", "Environmentalist", 1, 10, 0, 0, 0, 0,
				"environmentalist.png", Game.level.squares[7][12], 1, 10, null, new Inventory(), true, false, true,
				false, false, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f,
				Game.level.factions.get(1), 0, 0, 10f);

		Actor superWolf = new WildAnimal("Wolf Queen", "Wild animal", 1, 10, 0, 0, 0, 0, "fire_wolf.png",
				Game.level.squares[22][16], 1, 10, null, new Inventory(), true, false, true, false, false, 1, 1, 0.5f,
				0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, Game.level.factions.get(2), 0, 0, 15f);

		Actor wolf2 = new WildAnimal("Wolf", "Wild animal", 1, 10, 0, 0, 0, 0, "wolf_green.png",
				Game.level.squares[20][15], 1, 10, null, new Inventory(), true, false, true, false, false, 1, 1, 0.5f,
				0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, Game.level.factions.get(2), 0, 0, 15f);

		Actor wolf3 = new WildAnimal("Wolf", "Wild animal", 1, 10, 0, 0, 0, 0, "wolf_pink.png",
				Game.level.squares[20][17], 1, 10, null, new Inventory(), true, false, true, false, false, 1, 1, 0.5f,
				0.5f, 1f, 1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, Game.level.factions.get(2), 0, 0, 15f);

		ArrayList<Actor> wolfPackMembers = new ArrayList<Actor>();
		wolfPackMembers.add(superWolf);
		wolfPackMembers.add(wolf2);
		wolfPackMembers.add(wolf3);
		Group wolfPack = new Group("Wolf pack", wolfPackMembers, superWolf);

		ArrayList<GameObject> weaponsBehindTheLodge = new ArrayList<GameObject>();
		weaponsBehindTheLodge.add(Templates.HATCHET.makeCopy(Game.level.squares[12][9]));
		weaponsBehindTheLodge.add(Templates.HUNTING_BOW.makeCopy(Game.level.squares[12][9]));
		QuestSmallGame questSmallGame = new QuestSmallGame(hunterPack, environmentalistBill, superWolf, wolfPack, null,
				weaponsBehindTheLodge);

		Sign joesShopSign = Templates.SIGN.makeCopy(Game.level.squares[4][5]);
		joesShopSign.setText(new Object[] { joesShop.name });
		joesShopSign.name = joesShop.name + " sign";
		Weapon broom = Templates.BROOM.makeCopy(null);
		Actor trader = new Trader("Trader Joe", "Trader", 1, 10, 0, 0, 0, 0, "shopKeeper.png", Game.level.squares[7][1],
				1, 10, null, new Inventory(), true, false, true, false, false, 1, 1, 0.5f, 0.5f, 1f, 1f, null, 0.5f,
				0.5f, false, 0f, 0f, 0f, 0f, Game.level.factions.get(1), 40, 96, 10f, joesShop, shopAtriums.get(0),
				joesShopSign, broom);
		trader.inventory.add(broom);
		trader.inventory.add(Templates.KATANA.makeCopy(null));
		trader.inventory.add(Templates.HATCHET.makeCopy(null));
		trader.inventory.add(Templates.HUNTING_BOW.makeCopy(null));
		Templates.SHOP_COUNTER.makeCopy(Game.level.squares[7][1]);

		// Decorations
		// Cat cat = new Cat("Cat", 345f, 464f, 128f, 128f, false, "cat.png");
		// Game.level.decorations.add(cat);

		// Script

		// SpeechPart 1
		ArrayList<Actor> speechActors1 = new ArrayList<Actor>();
		speechActors1.add(Game.level.player);
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
				speechDirections1, Game.level.player, arrayList1);

		// SpeechPart 2
		ArrayList<Actor> speechActors2 = new ArrayList<Actor>();
		speechActors2.add(Game.level.player);
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
				speechDirections2, Game.level.player, arrayList2);

		ArrayList<ScriptEventSpeech.SpeechPart> speechParts = new ArrayList<ScriptEventSpeech.SpeechPart>();
		speechParts.add(speechPart1_1.makeCopy());
		speechParts.add(speechPart1_2.makeCopy());
		ScriptTrigger scriptTriggerActorSelected = new ScriptTriggerActorSelected(Game.level.player);
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
