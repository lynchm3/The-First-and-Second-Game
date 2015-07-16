package com.marklynch.editor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.ui.button.AtributesWindowButton;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.button.SettingsWindowButton;
import com.marklynch.utils.LineUtils;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class Editor {
	public ArrayList<Button> buttons = new ArrayList<Button>();

	// faction, actors, object, scriptevent, script
	// trigger, weapon,
	// level, squares

	Button levelTabButton;
	Button squaresTabButton;
	Button objectsTabButton;
	Button factionsTabButton;
	Button actorsTabButton;
	Button weaponsTabButton;
	Button scriptEventsTabButton;
	Button scriptTriggersTabButton;

	// Button addFactionButton;
	// Button addObjectButton;
	// Button addActorButton;
	// Button playLevelButton;
	public Level level;

	public GameObject selectedGameObject;

	public AttributesWindow attributesWindow;

	public SettingsWindow settingsWindow;
	public LevelSettingsWindow levelSettingsWindow;
	public SquaresSettingsWindow squaresSettingsWindow;
	public ObjectsSettingsWindow objectsSettingsWindow;
	public ActorsSettingsWindow actorsSettingsWindow;
	public FactionsSettingsWindow factionsSettingsWindow;

	public Object objectToEdit = null;
	public String attributeToEdit = "";
	public String textEntered = "";
	public AtributesWindowButton attributeButton = null;
	public SettingsWindowButton settingsButton = null;

	public enum STATE {
		DEFAULT, ADD_OBJECT, ADD_ACTOR, SELECTED_OBJECT, SETTINGS_CHANGE
	}

	STATE state = STATE.DEFAULT;

	Color[] colors = new Color[] { Color.red, Color.blue, Color.green,
			Color.magenta, Color.cyan, Color.orange };

	public Editor() {
		level = new Level(10, 10);

		levelSettingsWindow = new LevelSettingsWindow(200, this);
		squaresSettingsWindow = new SquaresSettingsWindow(200, this);
		objectsSettingsWindow = new ObjectsSettingsWindow(200, this);
		actorsSettingsWindow = new ActorsSettingsWindow(200, this);
		factionsSettingsWindow = new FactionsSettingsWindow(200, this);

		settingsWindow = levelSettingsWindow;

		// Add a game object
		GameObject gameObject = new GameObject("dumpster", 5, 0, 0, 0, 0,
				"skip_with_shadow.png", level.squares[0][3],
				new Vector<Weapon>(), level);
		level.inanimateObjects.add(gameObject);
		level.squares[0][3].gameObject = gameObject;

		// Add a faction
		level.factions.add(new Faction("Faction " + level.factions.size(),
				level, Color.blue, "faction_blue.png"));
		factionsSettingsWindow.updateFactionsButtons();

		// Weapons
		Weapon weapon0ForActor0 = new Weapon("a3r1", 3, 1, 1, "a3r1.png");
		Weapon weapon1ForActor0 = new Weapon("a2r2", 2, 2, 2, "a2r2.png");
		Weapon weapon2ForActor0 = new Weapon("a5r3", 5, 3, 3, "a2r2.png");
		Vector<Weapon> weaponsForActor0 = new Vector<Weapon>();
		weaponsForActor0.add(weapon0ForActor0);
		weaponsForActor0.add(weapon1ForActor0);
		weaponsForActor0.add(weapon2ForActor0);

		// Add actor
		Actor actor = new Actor("Old lady", "Fighter", 1, 10, 0, 0, 0, 0,
				"red1.png", level.squares[0][4], weaponsForActor0, 4, level);
		actor.faction = level.factions.get(0);
		level.factions.get(0).actors.add(actor);

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
			}
		};
		buttons.add(factionsTabButton);

	}

	public void update(int delta) {

	}

	public void draw() {

		level.draw();

		// draw highlight on selected object
		if (selectedGameObject != null) {
			selectedGameObject.squareGameObjectIsOn.drawHighlight();
		}

		// Draw a move line if click will result in move
		if (Game.buttonHoveringOver == null
				&& state == STATE.SELECTED_OBJECT
				&& Game.squareMouseIsOver != null
				&& Game.squareMouseIsOver != this.selectedGameObject.squareGameObjectIsOn) {
			GL11.glPushMatrix();

			GL11.glTranslatef(Game.windowWidth / 2, Game.windowHeight / 2, 0);
			GL11.glScalef(Game.zoom, Game.zoom, 0);
			GL11.glTranslatef(Game.dragX, Game.dragY, 0);
			GL11.glTranslatef(-Game.windowWidth / 2, -Game.windowHeight / 2, 0);

			float x1 = this.selectedGameObject.squareGameObjectIsOn.x
					* Game.SQUARE_WIDTH + Game.SQUARE_WIDTH / 2;
			float y1 = this.selectedGameObject.squareGameObjectIsOn.y
					* Game.SQUARE_HEIGHT + Game.SQUARE_HEIGHT / 2;
			float x2 = Game.squareMouseIsOver.x * Game.SQUARE_WIDTH
					+ Game.SQUARE_WIDTH / 2;
			float y2 = Game.squareMouseIsOver.y * Game.SQUARE_HEIGHT
					+ Game.SQUARE_HEIGHT / 2;

			// CircleUtils.drawCircle(Color.white, 10d, x1, y1);
			TextureUtils.drawTexture(level.gameCursor.circle, x1 - 10, x1 + 10,
					y1 - 10, y1 + 10);
			LineUtils.drawLine(Color.white, x1, y1, x2, y2, 10f);
			TextureUtils.drawTexture(level.gameCursor.circle, x2 - 10, x2 + 10,
					y2 - 10, y2 + 10);

			GL11.glPopMatrix();

		}

		if (attributesWindow != null)
			attributesWindow.draw();

		settingsWindow.draw();

		for (Button button : buttons) {
			button.draw();
		}

		// Depending on what we're editing, might wnat to show something
		// different
		if (objectToEdit != null) {
			try {
				Class<? extends Object> objectClass = objectToEdit.getClass();
				Field field = objectClass.getField(attributeToEdit);

				if (field.getType().isAssignableFrom(int.class)
						|| field.getType().isAssignableFrom(float.class)) {
					// int or float
				} else if (field.getType().isAssignableFrom(String.class)) {
					// string
				} else if (field.getType().isAssignableFrom(Faction.class)) {
					// faction
					QuadUtils.drawQuad(Color.black, 0, Game.windowWidth, 0,
							Game.windowHeight);
					for (int i = 0; i < level.factions.size(); i++) {
						TextUtils.printTextWithImages(new Object[] { i + " - ",
								level.factions.get(i) }, 200, i * 100 + 200);
					}
				} else if (field.getType().isAssignableFrom(Color.class)) {
					// color
					QuadUtils.drawQuad(Color.black, 0, Game.windowWidth, 0,
							Game.windowHeight);
					for (int i = 0; i < colors.length; i++) {
						TextUtils.printTextWithImages(new Object[] { i + " - ",
								new StringWithColor("Color " + i, colors[i]) },
								200, i * 100 + 200);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// if (state == STATE.ADD_OBJECT) {
		// TextureUtils.drawTexture(
		// level.inanimateObjects.get(0).imageTexture,
		// Mouse.getX() + 10, Mouse.getX() + 30, Game.windowHeight
		// - Mouse.getY() + 20,
		// Game.windowHeight - Mouse.getY() + 40);
		// }

		if (state == STATE.SELECTED_OBJECT) {
			TextureUtils.drawTexture(level.gameCursor.imageTexture2,
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
		for (Button button : this.buttons) {
			if (button.calculateIfPointInBoundsOfButton(mouseX,
					Game.windowHeight - mouseY))
				return button;
		}

		if (attributesWindow != null) {
			for (Button button : attributesWindow.buttons) {
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
		System.out.println("gameObjectClicked is " + gameObject);
		if (state == STATE.DEFAULT || state == STATE.ADD_ACTOR
				|| state == STATE.ADD_OBJECT || state == STATE.SETTINGS_CHANGE) {
			if (gameObject instanceof Actor) {
				actorsTabButton.click();
			} else {
				objectsTabButton.click();
			}
			this.clearSelectedObject();
			this.selectedGameObject = gameObject;
			attributesWindow = new AttributesWindow(200, 200, 350,
					selectedGameObject, this);
			state = STATE.SELECTED_OBJECT;
			depressButtonsSettingsAndDetailsButtons();
		} else if (state == STATE.SELECTED_OBJECT) {
			swapGameObjects(this.selectedGameObject, gameObject);
		}

	}

	public void squareClicked(Square square) {
		System.out.println("squareClicked is " + square);
		if (state == STATE.DEFAULT || state == STATE.SETTINGS_CHANGE) {
			squaresTabButton.click();
			this.clearSelectedObject();
			attributesWindow = new AttributesWindow(200, 200, 350, square, this);
			depressButtonsSettingsAndDetailsButtons();
		} else if (state == STATE.ADD_OBJECT) {
			GameObject gameObject = new GameObject("dumpster", 5, 0, 0, 0, 0,
					"skip_with_shadow.png", square, new Vector<Weapon>(), level);
			level.inanimateObjects.add(gameObject);
			square.gameObject = gameObject;
			// state = STATE.DEFAULT;
		} else if (state == STATE.ADD_ACTOR) {
			// Add actor
			Actor actor = new Actor("Old lady", "Fighter", 1, 10, 0, 0, 0, 0,
					"red1.png", square, new Vector<Weapon>(), 4, level);
			actor.faction = level.factions.get(0);
			level.factions.get(0).actors.add(actor);
			square.gameObject = actor;
			// state = STATE.DEFAULT;
		} else if (state == STATE.SELECTED_OBJECT) {
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

		if (state == STATE.SETTINGS_CHANGE && settingsButton != null) {
			settingsButton.keyTyped(character);
		} else if (objectToEdit != null) {
			if (attributeToEdit != null && this.textEntered != null) {

				try {
					Class<? extends Object> objectClass = objectToEdit
							.getClass();
					Field field = objectClass.getField(attributeToEdit);

					if (field.getType().isAssignableFrom(int.class)
							|| field.getType().isAssignableFrom(float.class)) { // int
																				// or
																				// float
						if (48 <= character && character <= 57
								&& textEntered.length() < 8) {
							this.textEntered += character;
							field.set(objectToEdit,
									Integer.valueOf(this.textEntered)
											.intValue());
						}
					} else if (field.getType().isAssignableFrom(String.class)) { // string
						this.textEntered += character;
						field.set(objectToEdit, textEntered);
					} else if (field.getType().isAssignableFrom(Faction.class)) {// faction
						if (48 <= character && character <= 57) {
							int factionIndex = character - 48;

							if (factionIndex < level.factions.size()) {
								Actor actor = (Actor) objectToEdit;
								actor.faction.actors.remove(actor);
								level.factions.get(factionIndex).actors
										.add(actor);
								actor.faction = level.factions
										.get(factionIndex);
								this.clearSelectedObject();
								this.depressButtonsSettingsAndDetailsButtons();
							}
						}

					} else if (field.getType().isAssignableFrom(Color.class)) {// color
						if (48 <= character && character <= 57) {
							int colorIndex = character - 48;
							if (colorIndex < colors.length) {
								field.set(objectToEdit, colors[colorIndex]);
								this.clearSelectedObject();
								this.depressButtonsSettingsAndDetailsButtons();
							}
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
		} else if (state == STATE.SELECTED_OBJECT && objectToEdit != null) {
			objectToEdit = null;
			attributeToEdit = null;
			this.textEntered = "";
			attributesWindow.depressButtons();
		}
	}

	public void backTyped() {
		if (state == STATE.SETTINGS_CHANGE && settingsButton != null) {
			settingsButton.backTyped();
			return;
		}

		System.out.println("backTyped()");
		if (state == STATE.SELECTED_OBJECT && objectToEdit != null
				&& textEntered.length() > 0) {
			this.textEntered = this.textEntered.substring(0,
					this.textEntered.length() - 1);
		}

		if (state == STATE.SELECTED_OBJECT && objectToEdit != null
				&& attributeToEdit != null && this.textEntered != null) {
			if (objectToEdit instanceof GameObject) {
				GameObject gameObject = (GameObject) objectToEdit;

				Class<? extends GameObject> gameObjectClass = gameObject
						.getClass();
				try {
					Field field = gameObjectClass.getField(attributeToEdit);

					if (field.getType().isAssignableFrom(int.class)
							|| field.getType().isAssignableFrom(float.class)) { // int
																				// or
																				// float
						if (textEntered.length() == 0) {
							field.set(gameObject, 0);
						} else {
							field.set(gameObject,
									Integer.valueOf(this.textEntered)
											.intValue());
						}
					} else if (field.getType().isAssignableFrom(String.class)) { // string
						field.set(gameObject, textEntered);
					}

					// field.set(gameObject, textEntered);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	public void editAttribute(Object object, String attribute,
			AtributesWindowButton attributeButton) {
		objectToEdit = object;
		attributeToEdit = attribute;
		// if (objectToEdit instanceof GameObject) {
		// GameObject gameObject = (GameObject) objectToEdit;
		// Class<? extends GameObject> gameObjectClass = gameObject.getClass();
		// try {
		// Field field;
		// field = gameObjectClass.getField(attributeToEdit);
		// textEntered = "" + field.get(gameObject);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		textEntered = "";
		this.attributeButton = attributeButton;
	}

	public void clearSelectedObject() {
		this.selectedGameObject = null;
		this.attributesWindow = null;
		objectToEdit = null;
		attributeToEdit = null;
		textEntered = "";
		this.attributeButton = null;
		if (state == STATE.SELECTED_OBJECT)
			state = STATE.DEFAULT;
	}

	public void rightClick() {
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
