package com.marklynch.editor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;

import org.newdawn.slick.Color;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;

public class Editor {
	public ArrayList<Button> buttons = new ArrayList<Button>();
	Button addFactionButton;
	Button addObjectButton;
	Button playLevelButton;
	public Level level;

	public GameObject selectedObject;

	public DetailsWindow detailsWindow;

	public Object objectToEdit = null;
	public String attributeToEdit = "";
	public String textEntered = "";

	public enum STATE {
		DEFAULT, ADD_OBJECT, MOVE_OBJECT, EDIT_ATTRIBUTE
	}

	STATE state = STATE.DEFAULT;

	public Editor() {
		level = new Level(10, 10);

		addFactionButton = new LevelButton(50, 50, 100, 50, "", "",
				"ADD FACTION", true, true);
		addFactionButton.setClickListener(new ClickListener() {

			@Override
			public void click() {
				level.factions.add(new Faction("Faction "
						+ level.factions.size(), level, Color.blue,
						"faction_blue.png"));
				System.out.println("Added faction @ " + level.factions.size());
			}
		});
		buttons.add(addFactionButton);

		addObjectButton = new LevelButton(50, 150, 100, 50, "", "",
				"ADD OBJECT", true, true);
		addObjectButton.setClickListener(new ClickListener() {

			@Override
			public void click() {
				addObjectButton.down = !addObjectButton.down;
				if (addObjectButton.down)
					state = STATE.ADD_OBJECT;
				else
					state = STATE.DEFAULT;

			}
		});
		buttons.add(addObjectButton);

		addFactionButton = new LevelButton(50, 650, 100, 50, "", "",
				"PLAY LEVEL", true, true);
		addFactionButton.setClickListener(new ClickListener() {

			@Override
			public void click() {
				level.currentFactionMoving = level.factions
						.get(level.currentFactionMovingIndex);
				Game.level = level;
				Game.editorMode = false;
			}
		});
		buttons.add(addFactionButton);
	}

	public void update(int delta) {

	}

	public void draw() {

		level.draw();

		if (selectedObject != null) {
			selectedObject.squareGameObjectIsOn.drawHighlight();
		}

		if (detailsWindow != null)
			detailsWindow.draw();

		for (Button button : buttons) {
			button.draw();
		}
	}

	public Button getButtonFromMousePosition(float mouseX, float mouseY) {
		for (Button button : this.buttons) {
			if (button.calculateIfPointInBoundsOfButton(mouseX,
					Game.windowHeight - mouseY))
				return button;
		}

		if (detailsWindow != null) {
			for (Button button : detailsWindow.buttons) {
				if (button.calculateIfPointInBoundsOfButton(mouseX,
						Game.windowHeight - mouseY))
					return button;
			}
		}

		return null;
	}

	public void gameObjectClicked(GameObject gameObject) {
		System.out.println("gameObjectClicked is " + gameObject);
		if (state == STATE.DEFAULT) {
			this.selectedObject = gameObject;
			detailsWindow = new DetailsWindow(200, 0, 200, 200, selectedObject,
					this);
			state = STATE.MOVE_OBJECT;
		} else if (state == STATE.MOVE_OBJECT) {
			swapGameObjects(this.selectedObject, gameObject);
			this.selectedObject = null;
			state = STATE.DEFAULT;
		}

	}

	public void squareClicked(Square square) {
		System.out.println("squareClicked is " + square);
		if (state == STATE.ADD_OBJECT) {
			GameObject gameObject = new GameObject("dumpster", 5, 0, 0, 0, 0,
					"skip_with_shadow.png", square, new Vector<Weapon>(), level);
			level.inanimateObjects.add(gameObject);
			square.gameObject = gameObject;
			// state = STATE.DEFAULT;
		} else if (state == STATE.MOVE_OBJECT) {
			if (square.gameObject != null) {
				swapGameObjects(this.selectedObject, square.gameObject);
			} else {
				moveGameObject(this.selectedObject, square);
			}
			this.selectedObject = null;
			state = STATE.DEFAULT;
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

		System.out.println("keyTyped - " + character);
		System.out.println("state - " + state);
		System.out.println("objectToEdit - " + objectToEdit);
		System.out.println("attributeToEdit - " + attributeToEdit);
		System.out.println("this.textEntered - " + this.textEntered);
		System.out.println("state - " + state);

		if (state == STATE.EDIT_ATTRIBUTE) {
			if (objectToEdit != null && attributeToEdit != null
					&& this.textEntered != null) {
				this.textEntered += character;
				if (objectToEdit instanceof GameObject) {
					GameObject gameObject = (GameObject) objectToEdit;

					Class<? extends GameObject> gameObjectClass = gameObject
							.getClass();
					try {
						Field field = gameObjectClass.getField(attributeToEdit);
						field.set(gameObject, textEntered);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
	}

	public void enterTyped() {

		if (state == STATE.EDIT_ATTRIBUTE) {
			if (objectToEdit != null && attributeToEdit != null
					&& this.textEntered != null && !"".equals(this.textEntered)) {
				if (objectToEdit instanceof GameObject) {
					GameObject gameObject = (GameObject) objectToEdit;

					Class<? extends GameObject> gameObjectClass = gameObject
							.getClass();
					try {
						Field field = gameObjectClass.getField(attributeToEdit);
						field.set(gameObject, textEntered);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			state = STATE.DEFAULT;
		}
	}

	public void editAttribute(Object object, String attribute) {
		state = Editor.STATE.EDIT_ATTRIBUTE;
		objectToEdit = object;
		attributeToEdit = attribute;
		textEntered = "";
	}
}
