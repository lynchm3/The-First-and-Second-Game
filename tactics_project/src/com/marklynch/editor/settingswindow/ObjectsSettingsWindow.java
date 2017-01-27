package com.marklynch.editor.settingswindow;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.editor.AttributesDialog;
import com.marklynch.editor.Editor;
import com.marklynch.editor.Editor.EDITOR_STATE;
import com.marklynch.editor.InstanceSelectionWindow;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.GameObjectTemplate;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class ObjectsSettingsWindow extends SettingsWindow {

	public enum OBJECT_SETTINGS_FILTER {
		ALL, ACTORS, OBJECTS, WEAPONS
	}

	public OBJECT_SETTINGS_FILTER objectSettingsFilter = OBJECT_SETTINGS_FILTER.ALL;

	public SettingsWindowButton addObjectsButton;
	public SettingsWindowButton allFilterButton;
	public SettingsWindowButton actorsFilterButton;
	public SettingsWindowButton objectsFilterButton;
	public SettingsWindowButton weaponsFilterButton;

	public ObjectsSettingsWindow(float width, Editor editor) {
		super(width, editor);

		addObjectsButton = new SettingsWindowButton(0, 100, 200, 30, "ADD ACTORS/OBJECTS", true, true) {

			@Override
			public void keyTyped(char character) {
			}

			@Override
			public void enterTyped() {
			}

			@Override
			public void backTyped() {
			}

			@Override
			public void depress() {
				if (ObjectsSettingsWindow.this.editor.editorState == Editor.EDITOR_STATE.ADD_OBJECT)
					ObjectsSettingsWindow.this.editor.editorState = Editor.EDITOR_STATE.DEFAULT;
			}

		};

		addObjectsButton.clickListener = new ClickListener() {

			@Override
			public void click() {

				ObjectsSettingsWindow.this.editor.clearSelectedObject();
				ObjectsSettingsWindow.this.editor.depressButtonsSettingsAndDetailsButtons();

				ObjectsSettingsWindow.this.editor.instanceSelectionWindow = new InstanceSelectionWindow<GameObjectTemplate>(
						ObjectsSettingsWindow.this.editor.gameObjectTemplates, ObjectsSettingsWindow.this.editor,
						"Select a Template");
			}
		};

		allFilterButton = new SettingsWindowButton(0, 130, 50, 30, "ALL", true, true) {

			@Override
			public void keyTyped(char character) {
			}

			@Override
			public void enterTyped() {
			}

			@Override
			public void backTyped() {
			}

			@Override
			public void depress() {
			}

		};

		allFilterButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				objectSettingsFilter = OBJECT_SETTINGS_FILTER.ALL;
				updateObjectsButtons();
			}
		};

		actorsFilterButton = new SettingsWindowButton(50, 130, 50, 30, "ACTORS", true, true) {

			@Override
			public void keyTyped(char character) {
			}

			@Override
			public void enterTyped() {
			}

			@Override
			public void backTyped() {
			}

			@Override
			public void depress() {
			}

		};

		actorsFilterButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				objectSettingsFilter = OBJECT_SETTINGS_FILTER.ACTORS;
				updateObjectsButtons();
			}
		};

		objectsFilterButton = new SettingsWindowButton(100, 130, 50, 30, "OBJECTS", true, true) {

			@Override
			public void keyTyped(char character) {
			}

			@Override
			public void enterTyped() {
			}

			@Override
			public void backTyped() {
			}

			@Override
			public void depress() {
			}

		};

		objectsFilterButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				objectSettingsFilter = OBJECT_SETTINGS_FILTER.OBJECTS;
				updateObjectsButtons();
			}
		};

		weaponsFilterButton = new SettingsWindowButton(150, 130, 50, 30, "WEAPONS", true, true) {

			@Override
			public void keyTyped(char character) {
			}

			@Override
			public void enterTyped() {
			}

			@Override
			public void backTyped() {
			}

			@Override
			public void depress() {
			}

		};

		weaponsFilterButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				objectSettingsFilter = OBJECT_SETTINGS_FILTER.WEAPONS;
				updateObjectsButtons();
			}
		};
		updateObjectsButtons();
	}

	public void updateObjectsButtons() {

		buttons.clear();

		buttons.add(addObjectsButton);
		buttons.add(allFilterButton);
		buttons.add(actorsFilterButton);
		buttons.add(objectsFilterButton);
		buttons.add(weaponsFilterButton);

		final ArrayList<GameObject> objects = new ArrayList<GameObject>();
		if (objectSettingsFilter == OBJECT_SETTINGS_FILTER.ALL) {
			objects.addAll(Game.level.inanimateObjects);
			for (Faction faction : Game.level.factions) {
				objects.addAll(faction.actors);
			}
			allFilterButton.down = true;
			actorsFilterButton.down = false;
			objectsFilterButton.down = false;
			weaponsFilterButton.down = false;
		} else if (objectSettingsFilter == OBJECT_SETTINGS_FILTER.ACTORS) {
			for (Faction faction : Game.level.factions) {
				objects.addAll(faction.actors);
			}
			allFilterButton.down = false;
			actorsFilterButton.down = true;
			objectsFilterButton.down = false;
			weaponsFilterButton.down = false;
		} else if (objectSettingsFilter == OBJECT_SETTINGS_FILTER.OBJECTS) {
			for (GameObject gameObject : Game.level.inanimateObjects) {
				if (!(gameObject instanceof Weapon))
					objects.add(gameObject);
			}
			allFilterButton.down = false;
			actorsFilterButton.down = false;
			objectsFilterButton.down = true;
			weaponsFilterButton.down = false;
		} else if (objectSettingsFilter == OBJECT_SETTINGS_FILTER.WEAPONS) {
			for (GameObject gameObject : Game.level.inanimateObjects) {
				if (gameObject instanceof Weapon)
					objects.add(gameObject);
			}
			allFilterButton.down = false;
			actorsFilterButton.down = false;
			objectsFilterButton.down = false;
			weaponsFilterButton.down = true;
		}

		for (int i = 0; i < objects.size(); i++) {
			final int index = i;

			final SettingsWindowButton objectButton = new SettingsWindowButton(0, 230 + i * 30, 200, 30,
					objects.get(index), true, true) {

				@Override
				public void keyTyped(char character) {
				}

				@Override
				public void enterTyped() {
				}

				@Override
				public void backTyped() {
				}

				@Override
				public void depress() {
				}

			};

			objectButton.clickListener = new ClickListener() {

				@Override
				public void click() {

					editor.clearSelectedObject();
					editor.depressButtonsSettingsAndDetailsButtons();
					editor.editorState = EDITOR_STATE.MOVEABLE_OBJECT_SELECTED;
					editor.selectedGameObject = objects.get(index);
					editor.attributesWindow = new AttributesDialog(200, 200, 200, editor.selectedGameObject, editor);
					// getButton(editor.selectedGameObject).down = true;
					objectButton.down = true;
				}
			};
			buttons.add(objectButton);

		}

	}

	@Override
	public void update() {
		updateObjectsButtons();
	}
}
