package com.marklynch.editor.settingswindow;

import com.marklynch.Game;
import com.marklynch.editor.AttributesDialog;
import com.marklynch.editor.Editor;
import com.marklynch.editor.Editor.EDITOR_STATE;
import com.marklynch.editor.InstanceSelectionWindow;
import com.marklynch.tactics.objects.GameObjectTemplate;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class ObjectsSettingsWindow extends SettingsWindow {
	public SettingsWindowButton addObjectsButton;

	public ObjectsSettingsWindow(float width, Editor editor) {
		super(width, editor);

		addObjectsButton = new SettingsWindowButton(0, 100, 200, 30, "ADD OBJECTS", true, true) {

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
				// addObjectsButton.down = !addObjectsButton.down;
				// if (addObjectsButton.down) {
				// ObjectsSettingsWindow.this.editor.depressButtonsSettingsAndDetailsButtons();
				// ObjectsSettingsWindow.this.editor.clearSelectedObject();
				// addObjectsButton.down = true;
				// ObjectsSettingsWindow.this.editor.editorState =
				// EDITOR_STATE.ADD_OBJECT;
				// ObjectsSettingsWindow.this.editor.toast = new Toast(200, 50,
				// "Select location to add object");
				// } else {
				// ObjectsSettingsWindow.this.editor.editorState =
				// EDITOR_STATE.DEFAULT;
				// }
			}
		};
		updateObjectsButtons();
	}

	public void updateObjectsButtons() {

		buttons.clear();

		buttons.add(addObjectsButton);

		for (int i = 0; i < Game.level.inanimateObjects.size(); i++) {
			final int index = i;

			final SettingsWindowButton objectButton = new SettingsWindowButton(0, 200 + i * 30, 200, 30,
					Game.level.inanimateObjects.get(index), true, true) {

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
					editor.selectedGameObject = Game.level.inanimateObjects.get(index);
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
