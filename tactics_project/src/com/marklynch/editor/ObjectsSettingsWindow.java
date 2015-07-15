package com.marklynch.editor;

import com.marklynch.editor.Editor.STATE;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class ObjectsSettingsWindow extends SettingsWindow {

	public ObjectsSettingsWindow(float width, Editor editor) {
		super(width, editor);

		final SettingsWindowButton addObjectButton = new SettingsWindowButton(
				0, 100, 200, 30, "ADD OBJECT", true, true, this) {

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
		buttons.add(addObjectButton);
	}

	@Override
	public void update() {

	}
}
