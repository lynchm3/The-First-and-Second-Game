package com.marklynch.editor;

import com.marklynch.editor.Editor.STATE;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class ActorsSettingsWindow extends SettingsWindow {
	SettingsWindowButton addActorButton;

	public ActorsSettingsWindow(float width, final Editor editor) {
		super(width, editor);

		addActorButton = new SettingsWindowButton(0, 100, 200, 30, "ADD ACTOR",
				true, true, this) {

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
		updateActorsButtons();
	}

	public void updateActorsButtons() {

		buttons.clear();

		buttons.add(addActorButton);

		for (final Faction faction : editor.level.factions) {
			for (int i = 0; i < faction.actors.size(); i++) {
				final int index = i;

				final SettingsWindowButton actorButton = new SettingsWindowButton(
						0, 200 + i * 30, 200, 30, faction.actors.get(index),
						true, true, this) {

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
						editor.attributesWindow = new AttributesWindow(200,
								200, 350, editor.selectedGameObject, editor);
						System.out.println("actorButton.click");
						// getButton(editor.selectedGameObject).down = true;
						actorButton.down = true;
					}
				};
				buttons.add(actorButton);

			}
		}

	}

	@Override
	public void update() {
		updateActorsButtons();
		SettingsWindowButton button = this.getButton(editor.objectToEdit);
		if (button != null)
			button.down = true;
	}
}
