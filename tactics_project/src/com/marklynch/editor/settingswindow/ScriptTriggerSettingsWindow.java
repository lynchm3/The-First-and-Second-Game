package com.marklynch.editor.settingswindow;

import com.marklynch.Game;
import com.marklynch.editor.AttributesWindow;
import com.marklynch.editor.Editor;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class ScriptTriggerSettingsWindow extends SettingsWindow {

	public ScriptTriggerSettingsWindow(float width, final Editor editor) {
		super(width, editor);
		updateScriptsButtons();
	}

	public void updateScriptsButtons() {
		buttons.clear();

		final SettingsWindowButton addScriptTriggerButton = new SettingsWindowButton(
				0, 100, 200, 30, "ADD SCRIPT TRIGGER", true, true, this) {

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

		addScriptTriggerButton.clickListener = new ClickListener() {

			@Override
			public void click() {

				// Weapon newWeapon = new Weapon("Weapon" +
				// editor.weapons.size(),
				// 3, 1, 1, "a3r1.png");
				// editor.weapons.add(newWeapon);
				// updateWeaponsButtons();
				// editor.clearSelectedObject();
				// editor.depressButtonsSettingsAndDetailsButtons();
			}
		};
		buttons.add(addScriptTriggerButton);

		int count = 0;
		for (int i = 0; i < Game.level.script.scriptEvents.size(); i++) {

			final int index = count;

			final SettingsWindowButton scriptButton = new SettingsWindowButton(
					0, 200 + i * 30, 200, 30,
					Game.level.script.scriptEvents.get(i).scriptTrigger, true,
					true, this) {

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

			scriptButton.clickListener = new ClickListener() {

				@Override
				public void click() {
					editor.clearSelectedObject();
					editor.depressButtonsSettingsAndDetailsButtons();
					scriptButton.down = true;
					editor.attributesWindow = new AttributesWindow(
							200,
							200,
							350,
							Game.level.script.scriptEvents.get(index).scriptTrigger,
							editor);

				}
			};
			buttons.add(scriptButton);

			count++;

		}

	}

	@Override
	public void update() {
		updateScriptsButtons();
		SettingsWindowButton button = this.getButton(editor.objectToEdit);
		if (button != null)
			button.down = true;

	}
}
