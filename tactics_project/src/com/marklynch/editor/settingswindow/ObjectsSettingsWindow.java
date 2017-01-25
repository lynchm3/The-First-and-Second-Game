package com.marklynch.editor.settingswindow;

import com.marklynch.Game;
import com.marklynch.editor.AttributesDialog;
import com.marklynch.editor.Editor;
import com.marklynch.editor.Editor.STATE;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class ObjectsSettingsWindow extends SettingsWindow {
	public SettingsWindowButton addObjectsButton;

	public ObjectsSettingsWindow(float width, Editor editor) {
		super(width, editor);

		addObjectsButton = new SettingsWindowButton(0, 100, 200, 30, "ADD OBJECTS", true, true, this) {

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
				if (ObjectsSettingsWindow.this.editor.state == Editor.STATE.ADD_OBJECT)
					ObjectsSettingsWindow.this.editor.state = Editor.STATE.DEFAULT;
			}

		};

		addObjectsButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				addObjectsButton.down = !addObjectsButton.down;
				if (addObjectsButton.down) {
					ObjectsSettingsWindow.this.editor.depressButtonsSettingsAndDetailsButtons();
					ObjectsSettingsWindow.this.editor.clearSelectedObject();
					addObjectsButton.down = true;
					ObjectsSettingsWindow.this.editor.state = STATE.ADD_OBJECT;
				} else {
					ObjectsSettingsWindow.this.editor.state = STATE.DEFAULT;
				}
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
					Game.level.inanimateObjects.get(index), true, true, this) {

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
					editor.state = STATE.MOVEABLE_OBJECT_SELECTED;
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
