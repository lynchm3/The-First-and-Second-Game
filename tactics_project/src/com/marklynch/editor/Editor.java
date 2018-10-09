package com.marklynch.editor;

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
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.FactionRelationship;
import com.marklynch.level.constructs.enchantment.Enhancement;
import com.marklynch.level.constructs.faction.FactionList;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.constructs.journal.AreaList;
import com.marklynch.level.constructs.requirementtomeet.RequirementToMeet;
import com.marklynch.level.constructs.skilltree.SkillTreeNode;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Switch.SWITCH_TYPE;
import com.marklynch.objects.ThoughtBubbles;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Actor.HOBBY;
import com.marklynch.objects.units.Player;
import com.marklynch.objects.weapons.BodyArmor;
import com.marklynch.objects.weapons.Helmet;
import com.marklynch.objects.weapons.LegArmor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.script.ScriptEvent;
import com.marklynch.script.trigger.ScriptTrigger;
import com.marklynch.ui.EditorToast;
import com.marklynch.ui.button.AtributesWindowButton;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.button.SettingsWindowButton;
import com.marklynch.utils.Color;
import com.marklynch.utils.LineUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

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
	public EditorToast toast;

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
	public GameObject gameObject;
	public Actor actorTemplate;

	public enum EDITOR_STATE {
		DEFAULT, ADD_OBJECT, MOVEABLE_OBJECT_SELECTED, SETTINGS_CHANGE
	}

	public EDITOR_STATE editorState = EDITOR_STATE.DEFAULT;

	public ArrayList<Color> colors = new ArrayList<Color>();

	public ClassSelectionWindow classSelectionWindow;
	public InstanceSelectionWindow instanceSelectionWindow;

	public Editor() {

		// LOAD COLORS
		colors.add(new Color(Color.BLUE));
		colors.add(new Color(Color.RED));
		colors.add(new Color(Color.GREEN));
		colors.add(new Color(Color.MAGENTA));
		colors.add(new Color(Color.CYAN));
		colors.add(new Color(Color.ORANGE));

		Game.level = new Level(360, 360);

		for (int i = 0; i < Game.level.squares.length; i++) {
			for (int j = 0; j < Game.level.squares[0].length; j++) {
				Game.level.squares[i][j].afterContructor();
			}
		}

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

		new AreaList();
		Game.level.fullQuestList.makeQuests();
		AreaList.buildAreas();

		// TABS
		String tabText = "LEVEL";
		levelTabButton = new LevelButton(10, 10, Game.smallFont.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE, null);
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
		squaresTabButton = new LevelButton(90, 10, Game.smallFont.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE, null);
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
		objectsTabButton = new LevelButton(210, 10, Game.smallFont.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE, null);
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
		templatesTabButton = new LevelButton(560, 10, Game.smallFont.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE, null);
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
		factionsTabButton = new LevelButton(430, 10, Game.smallFont.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE, null);
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
		colorsTabButton = new LevelButton(690, 10, Game.smallFont.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE, null);
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
		decorationsTabButton = new LevelButton(10, 50, Game.smallFont.getWidth(tabText), 30, "", "", tabText, true,
				true, Color.BLACK, Color.WHITE, null);
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
		scriptEventsTabButton = new LevelButton(180, 50, Game.smallFont.getWidth(tabText), 30, "", "", tabText, true,
				true, Color.BLACK, Color.WHITE, null);
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
		scriptTriggersTabButton = new LevelButton(350, 50, Game.smallFont.getWidth(tabText), 30, "", "", tabText, true,
				true, Color.BLACK, Color.WHITE, null);
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
		aisTabButton = new LevelButton(520, 50, Game.smallFont.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE, null);
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
		relationsTabButton = new LevelButton(590, 50, Game.smallFont.getWidth(tabText), 30, "", "", tabText, true, true,
				Color.BLACK, Color.WHITE, null);
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
		speechPartTabButton = new LevelButton(730, 50, Game.smallFont.getWidth(tabText), 30, "", "", tabText, true,
				true, Color.BLACK, Color.WHITE, null);
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

		// Expressions
		ThoughtBubbles.loadExpressions();

		new Templates();

		// Add player
		Player player = Templates.PLAYER.makeCopy("You", Game.level.squares[Game.playerStartPosX][Game.playerStartPosY],
				Level.factions.player, null, 100, new GameObject[] {}, new GameObject[] {}, null, new int[] {},
				new HOBBY[] { HOBBY.HUNTING });
		Game.level.player = player;
		for (SkillTreeNode skillTreeNode : Level.skillTree.activateAtStart) {
			skillTreeNode.activate(player);
		}
		Weapon playersHuntingBow = Templates.HUNTING_BOW.makeCopy(null, player);
		playersHuntingBow.enhancement = new Enhancement();
		player.inventory.add(playersHuntingBow);
		player.inventory.add(Templates.KATANA.makeCopy(null, player));
		player.inventory.add(Templates.KATANA.makeCopy(null, player));
		player.inventory.add(Templates.SWORD.makeCopy(null, player));
		player.inventory.add(Templates.HATCHET.makeCopy(null, player));
		player.inventory.add(Templates.HATCHET.makeCopy(null, player));
		for (int i = 0; i < 25; i++) {
			player.inventory.add(Templates.ROCK.makeCopy(null, player));
		}
		for (int i = 0; i < 22; i++) {
			player.inventory.add(Templates.APPLE.makeCopy(null, player));
		}
		for (int i = 0; i < 21; i++) {
			player.inventory.add(Templates.FUR.makeCopy(null, player));
		}
		player.inventory.add(Templates.FUR.makeCopy(null, player));
		player.inventory.add(Templates.HUNTING_KNIFE.makeCopy(null, player));
		player.inventory.add(Templates.HUNTING_KNIFE.makeCopy(null, player));
		player.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, player));
		player.inventory.add(Templates.DINNER_BELL.makeCopy(null, player));
		player.inventory.add(Templates.DINNER_BELL.makeCopy(null, player));
		player.inventory.add(Templates.CLEAVER.makeCopy(null, player));
		player.inventory.add(Templates.CLEAVER.makeCopy(null, player));
		player.inventory.add(Templates.LANTERN.makeCopy(null, player));
		player.inventory.add(Templates.KEY.makeCopy("Player test key", null, player));
		player.inventory.add(Templates.PICKAXE.makeCopy(null, player));
		player.inventory.add(Templates.SHOVEL.makeCopy(null, player));
		player.inventory.add(Templates.FISHING_ROD.makeCopy(null, player));
		player.inventory.add(Templates.ASH.makeCopy(null, player));
		player.inventory.add(Templates.BROKEN_PLATE.makeCopy(null, player));
		player.inventory.add(Templates.DINNER_FORK.makeCopy(null, player));
		player.inventory.add(Templates.DINNER_KNIFE.makeCopy(null, player));
		player.inventory.add(Templates.PLATE.makeCopy(null, player));
		player.inventory.add(Templates.SHELF.makeCopy(null, player));
		player.inventory.add(Templates.TROUGH.makeCopy(null, player));
		player.inventory.add(Templates.WOOD_CHIPS.makeCopy(null, player));
		player.inventory.add(
				Templates.ANTLERS_SWITCH.makeCopy(null, player, SWITCH_TYPE.OPEN_CLOSE, new RequirementToMeet[] {}));
		player.inventory.add(Templates.APRON.makeCopy(null, player));
		player.inventory.add(Templates.ARROW.makeCopy(null, player));
		player.inventory.add(Templates.BABY_RABBIT.makeCopy("Bun", null, FactionList.buns, null, new GameObject[] {},
				new GameObject[] {}, null));
		player.inventory.add(Templates.BARRICADE.makeCopy(null, player));
		player.inventory.add(Templates.BASKET.makeCopy(null, player));
		player.inventory.add(Templates.BED.makeCopy(null, player));
		player.inventory.add(Templates.BENCH.makeCopy(null, player));
		player.inventory.add(Templates.BIG_TREE.makeCopy(null, player));
		player.inventory.add(Templates.BLOOD.makeCopy(null, player));
		player.inventory.add(Templates.BLOODY_PULP.makeCopy(null, player));
		player.inventory.add(Templates.BOULDER.makeCopy(null, player));
		player.inventory.add(Templates.BROKEN_GLASS.makeCopy(null, player));
		player.inventory.add(Templates.BROKEN_LAMP.makeCopy(null, player));
		player.inventory.add(Templates.BROOM.makeCopy(null, player));
		player.inventory.add(Templates.BUBBLE.makeCopy(null, player, 1));
		player.inventory.add(Templates.BURROW.makeCopy(null, player));
		player.inventory.add(Templates.BUSH.makeCopy(null, player));
		player.inventory.add(Templates.CANDY.makeCopy(null, player));
		player.inventory.add(Templates.CARCASS.makeCopy("Mystery Cascass", null, player, 60));
		player.inventory.add(Templates.CHAINMAIL.makeCopy(null, player));
		player.inventory.add(Templates.CHAIR.makeCopy(null, player));
		player.inventory.add(Templates.CHAIR_FALLEN.makeCopy(null, player));
		player.inventory.add(Templates.CHEST.makeCopy("Locked Chest", null, true, player));
		player.inventory.add(Templates.CRATE.makeCopy("Crate", null, false, player));
		player.inventory.add(Templates.DIRTY_SHEET_3.makeCopy(null, player));
		player.inventory.add(Templates.DOCUMENTS.makeCopy(null, "Mystery Documents", new Object[] {}, player));
		player.inventory.add(Templates.DRIED_BLOOD.makeCopy(null, player));
		player.inventory.add(Templates.FENCE.makeCopy(null, player));
		player.inventory.add(Templates.FIRE_BALL.makeCopy(null, player));
		player.inventory.add(Templates.HAMMER.makeCopy(null, player));
		player.inventory.add(Templates.HOE.makeCopy(null, player));
		player.inventory.add(Templates.HUNTING_CAP.makeCopy(null, player));
		player.inventory.add(Templates.LANDMINE.makeCopy(null, player));
		player.inventory.add(Templates.LEATHERS.makeCopy(null, player));
		player.inventory.add(Templates.LONG_GRASS.makeCopy(null, player));
		player.inventory.add(Templates.MEAT_CHUNK.makeCopy(null, player));
		player.inventory.add(Templates.MIRROR.makeCopy(null, player));
		player.inventory.add(Templates.MUSHROOM.makeCopy(null, player));
		player.inventory.add(Templates.ORE.makeCopy(null, player));
		player.inventory.add(Templates.ROBE.makeCopy(null, player));
		player.inventory.add(Templates.SCROLL.makeCopy(null, "Mysterious Scroll", new Object[] {}, player));
		player.inventory.add(Templates.SICKLE.makeCopy(null, player));
		player.inventory.add(Templates.SWORD.makeCopy(null, player));
		player.inventory.add(Templates.TURTLE.makeCopy("Michael", null, FactionList.buns, null, new GameObject[] {},
				new GameObject[] {}, null));
		player.inventory.add(Templates.UNDIES.makeCopy(null, player));
		player.inventory.add(Templates.WATER_BALL.makeCopy(null, player));
		player.inventory.add(Templates.WHEAT.makeCopy(null, player));
		player.inventory.add(Templates.WHIP.makeCopy(null, player));
		player.inventory.add(Templates.WOOD.makeCopy(null, player));
		// player.inventory.add(Templates.PICKAXE.makeCopy(null));
		Helmet playersHardHat = Templates.HARD_HAT.makeCopy(null, player);
		player.inventory.add(playersHardHat);
		player.helmet = playersHardHat;
		BodyArmor playersJumper = Templates.JUMPER.makeCopy(null, player);
		player.inventory.add(playersJumper);
		player.bodyArmor = playersJumper;
		LegArmor playersPants = Templates.PANTS.makeCopy(null, player);
		player.inventory.add(playersPants);
		player.legArmor = playersPants;
		LegArmor playersDungarees = Templates.DUNGAREES.makeCopy(null, player);
		player.inventory.add(playersDungarees);
		player.inventory.add(Templates.PINK_HARD_HAT.makeCopy(null, player));
		player.inventory.add(Templates.COWBOY_HAT.makeCopy(null, player));
		player.inventory.add(Templates.JAR_OF_WATER.makeCopy(null, player));
		player.inventory.add(Templates.JAR_OF_WATER.makeCopy(null, player));
		player.inventory.add(Templates.JAR_OF_WATER.makeCopy(null, player));
		player.inventory.add(Templates.JAR_OF_WATER.makeCopy(null, player));
		player.inventory.add(Templates.JAR_OF_WATER.makeCopy(null, player));
		player.inventory.add(Templates.JAR_OF_WATER.makeCopy(null, player));
		player.inventory.add(Templates.JAR.makeCopy(null, player));
		player.inventory.add(Templates.JAR.makeCopy(null, player));
		player.inventory.add(Templates.JAR.makeCopy(null, player));
		player.inventory.add(Templates.JAR.makeCopy(null, player));
		player.inventory.add(Templates.JAR.makeCopy(null, player));
		player.inventory.add(Templates.JAR.makeCopy(null, player));
		player.inventory.add(Templates.JAR.makeCopy(null, player));
		player.inventory.add(Templates.MATCHES.makeCopy(null, player));

		// relationships
		Game.level.factions.player.relationships.put(Game.level.factions.get(1),
				new FactionRelationship(-100, Game.level.factions.player, Game.level.factions.get(1)));
		Game.level.factions.get(1).relationships.put(Game.level.factions.player,
				new FactionRelationship(-100, Game.level.factions.get(1), Game.level.factions.player));

		// Decorations
		// Cat cat = new Cat("Cat", 345f, 464f, 128f, 128f, false, "cat.png");
		// Game.level.decorations.add(cat);

		// Script

	}

	public void update(int delta) {

	}

	public void drawOverlay() {

		// draw highlight on selected object

		if (selectedGameObject != null && selectedGameObject.squareGameObjectIsOn != null) {
			selectedGameObject.squareGameObjectIsOn.drawHighlight();
		}

		// Draw a move line if click will result in move
		if (selectedGameObject != null && selectedGameObject.squareGameObjectIsOn != null
				&& Game.buttonHoveringOver == null && editorState == EDITOR_STATE.MOVEABLE_OBJECT_SELECTED
				&& Game.squareMouseIsOver != null
				&& Game.squareMouseIsOver != this.selectedGameObject.squareGameObjectIsOn) {

			float x1 = this.selectedGameObject.squareGameObjectIsOn.xInGridPixels / 2;
			float y1 = this.selectedGameObject.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT / 2;
			float x2 = Game.squareMouseIsOver.xInGridPixels / 2;
			float y2 = Game.squareMouseIsOver.yInGridPixels / 2;

			// CircleUtils.drawCircle(Color.white, 10d, x1, y1);
			TextureUtils.drawTexture(Game.level.gameCursor.circle, x1 - 10, y1 - 10, x1 + 10, y1 + 10);
			LineUtils.drawLine(Color.WHITE, x1, y1, x2, y2, 5f);
			TextureUtils.drawTexture(Game.level.gameCursor.circle, x2 - 10, y2 - 10, x2 + 10, y2 + 10);

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
			TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2, Mouse.getX() + 10,
					Game.windowHeight - Mouse.getY() + 20, Mouse.getX() + 30, Game.windowHeight - Mouse.getY() + 40);
			TextureUtils.drawTexture(selectedGameObject.imageTexture, Mouse.getX() + 10,
					Game.windowHeight - Mouse.getY() + 20, Mouse.getX() + 30, Game.windowHeight - Mouse.getY() + 40);
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
				if (!this.selectedGameObject.canShareSquare && !square.inventory.canShareSquare) {
					swapGameObjects(this.selectedGameObject, square.inventory.gameObjectThatCantShareSquare);
				} else {
					moveGameObject(this.selectedGameObject, square);
				}
			}
		} else if (square.inventory.canShareSquare) {
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
					swapGameObjects(this.selectedGameObject, square.inventory.gameObjectThatCantShareSquare);
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

		boolean canBePlaceOnGround = gameObject.canShareSquare || square.inventory.canShareSquare;
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
			this.toast = new EditorToast("No space for the object here! Please pick a different square. :)");
		}
	}

	public void placeObjectOnSquare(Square square) {
		// GameObject gameObject = gameObjectTemplate.makeCopy(square, null);
		// if (gameObject instanceof Actor) {
		// ((Actor) gameObject).faction.actors.add((Actor) gameObject);
		// } else {
		// // Game.level.inanimateObjectsOnGround.add(gameObject);
		// }
		// square.inventory.add(gameObject);
		// this.objectsSettingsWindow.update();
		// this.toast = new EditorToast("Select a location to add object");
	}

	public void placeObjectInInventory(GameObject gameObjectThatCanHoldOtherObjects) {
		// GameObject gameObjectToPutInInventroy =
		// gameObjectTemplate.makeCopy(null, null);
		// gameObjectThatCanHoldOtherObjects.inventory.add(gameObjectToPutInInventroy);
		// this.objectsSettingsWindow.update();
		// this.toast = new EditorToast("Select a location to add object");
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
