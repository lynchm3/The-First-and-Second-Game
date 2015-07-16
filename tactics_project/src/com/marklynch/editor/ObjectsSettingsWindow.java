package com.marklynch.editor;

import com.marklynch.editor.Editor.STATE;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class ObjectsSettingsWindow extends SettingsWindow {
	SettingsWindowButton addObjectButton;

	public ObjectsSettingsWindow(float width, Editor editor) {
		super(width, editor);

		addObjectButton = new SettingsWindowButton(0, 100, 200, 30,
				"ADD OBJECT", true, true, this) {

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

		addObjectButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				addObjectButton.down = !addObjectButton.down;
				if (addObjectButton.down) {
					ObjectsSettingsWindow.this.editor
							.depressButtonsSettingsAndDetailsButtons();
					ObjectsSettingsWindow.this.editor.clearSelectedObject();
					addObjectButton.down = true;
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

		buttons.add(addObjectButton);

		for (int i = 0; i < editor.level.inanimateObjects.size(); i++) {
			final int index = i;

			final SettingsWindowButton objectButton = new SettingsWindowButton(
					0, 200 + i * 30, 200, 30,
					editor.level.inanimateObjects.get(index), true, true, this) {

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
					editor.selectedGameObject = editor.level.inanimateObjects
							.get(index);
					editor.attributesWindow = new AttributesWindow(200, 200,
							350, editor.selectedGameObject, editor);
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
		SettingsWindowButton button = this.getButton(editor.objectToEdit);
		if (button != null)
			button.down = true;
	}
}
