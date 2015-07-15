package com.marklynch.editor;

import com.marklynch.editor.Editor.STATE;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class ActorsSettingsWindow extends SettingsWindow {

	public ActorsSettingsWindow(float width, Editor editor) {
		super(width, editor);

		final SettingsWindowButton addActorButton = new SettingsWindowButton(0,
				100, 200, 30, "ADD ACTOR", true, true, this) {

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
				if (ActorsSettingsWindow.this.editor.state == Editor.STATE.ADD_ACTOR)
					ActorsSettingsWindow.this.editor.state = Editor.STATE.DEFAULT;
			}

		};

		addActorButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				addActorButton.down = !addActorButton.down;
				if (addActorButton.down) {
					ActorsSettingsWindow.this.editor
							.depressButtonsSettingsAndDetailsButtons();
					ActorsSettingsWindow.this.editor.clearSelectedObject();
					addActorButton.down = true;
					ActorsSettingsWindow.this.editor.state = STATE.ADD_ACTOR;
				} else {
					ActorsSettingsWindow.this.editor.state = STATE.DEFAULT;
				}
			}
		};
		buttons.add(addActorButton);
	}
}
