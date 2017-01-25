package com.marklynch.editor.settingswindow;

import com.marklynch.Game;
import com.marklynch.editor.AttributesDialog;
import com.marklynch.editor.Editor;
import com.marklynch.editor.Editor.STATE;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class ActorsSettingsWindow extends SettingsWindow {
	public SettingsWindowButton addActorsButton;

	public ActorsSettingsWindow(float width, final Editor editor) {
		super(width, editor);

		addActorsButton = new SettingsWindowButton(0, 100, 200, 30, "ADD ACTORS", true, true, this) {

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

		addActorsButton.clickListener = new ClickListener() {

			@Override
			public void click() {

				addActorsButton.down = !addActorsButton.down;
				if (addActorsButton.down) {
					ActorsSettingsWindow.this.editor.depressButtonsSettingsAndDetailsButtons();
					ActorsSettingsWindow.this.editor.clearSelectedObject();
					addActorsButton.down = true;
					ActorsSettingsWindow.this.editor.state = STATE.ADD_ACTOR;
				} else {
					ActorsSettingsWindow.this.editor.state = STATE.DEFAULT;
				}
			}
		};
		updateActorsButtons();
	}

	public void updateActorsButtons() {

		buttons.clear();

		buttons.add(addActorsButton);

		int buttonCount = 0;
		for (final Faction faction : Game.level.factions) {
			for (int i = 0; i < faction.actors.size(); i++) {
				final int index = i;

				final SettingsWindowButton actorButton = new SettingsWindowButton(0, 200 + buttonCount * 30, 200, 30,
						faction.actors.get(index), true, true, this) {

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

				actorButton.clickListener = new ClickListener() {

					@Override
					public void click() {

						editor.clearSelectedObject();
						editor.depressButtonsSettingsAndDetailsButtons();
						editor.state = STATE.MOVEABLE_OBJECT_SELECTED;
						editor.selectedGameObject = faction.actors.get(index);
						editor.attributesWindow = new AttributesDialog(200, 200, 200, editor.selectedGameObject,
								editor);
						// getButton(editor.selectedGameObject).down = true;
						actorButton.down = true;
					}
				};
				buttons.add(actorButton);
				buttonCount++;
				if (actorButton.object == editor.selectedGameObject)
					actorButton.down = true;

			}
		}

	}

	@Override
	public void update() {
		updateActorsButtons();
	}
}
