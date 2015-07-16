package com.marklynch.editor;

import com.marklynch.Game;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class LevelSettingsWindow extends SettingsWindow {

	public LevelSettingsWindow(float width, final Editor editor) {
		super(width, editor);

		// Width Button
		final SettingsWindowButton widthButton = new SettingsWindowButton(0,
				100, 200, 30, "Level Width: " + editor.level.width, true, true,
				this) {

			@Override
			public void keyTyped(char character) {
				if (48 <= character && character <= 57
						&& textEntered.length() < 2) {
					this.textEntered += character;
					this.text = "Level Width: " + textEntered;
				}

			}

			@Override
			public void enterTyped() {
				int newWidth = 0;
				if (this.textEntered.length() > 0)
					newWidth = Integer.valueOf(this.textEntered).intValue();
				LevelSettingsWindow.this.editor.level.changeSize(newWidth,
						LevelSettingsWindow.this.editor.level.height);
				LevelSettingsWindow.this.editor.state = Editor.STATE.DEFAULT;
				this.down = false;
			}

			@Override
			public void backTyped() {
				if (textEntered.length() > 0) {
					this.textEntered = this.textEntered.substring(0,
							this.textEntered.length() - 1);
					this.text = "Level Width: " + textEntered;
				}
			}

			@Override
			public void depress() {
				text = "Level Width: "
						+ LevelSettingsWindow.this.editor.level.width;

			}
		};
		widthButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				depressButtons();
				LevelSettingsWindow.this.editor.state = Editor.STATE.SETTINGS_CHANGE;
				LevelSettingsWindow.this.editor.settingsButton = widthButton;
				widthButton.textEntered = "";
				widthButton.down = true;
			}
		};
		buttons.add(widthButton);

		// Height Button
		final SettingsWindowButton heightButton = new SettingsWindowButton(0,
				130, 200, 30, "Level Height: " + editor.level.height, true,
				true, this) {

			@Override
			public void keyTyped(char character) {
				if (48 <= character && character <= 57
						&& textEntered.length() < 2) {
					this.textEntered += character;
				}
				this.text = "Level Height: " + textEntered;

			}

			@Override
			public void enterTyped() {
				int newHeight = 0;
				if (this.textEntered.length() > 0)
					newHeight = Integer.valueOf(this.textEntered).intValue();
				LevelSettingsWindow.this.editor.level.changeSize(
						LevelSettingsWindow.this.editor.level.width, newHeight);
				this.down = false;
			}

			@Override
			public void backTyped() {
				if (textEntered.length() > 0) {
					this.textEntered = this.textEntered.substring(0,
							this.textEntered.length() - 1);
					this.text = "Level Height: " + textEntered;
				}
			}

			@Override
			public void depress() {
				text = "Level Height: "
						+ LevelSettingsWindow.this.editor.level.height;
			}
		};
		heightButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				depressButtons();
				LevelSettingsWindow.this.editor.state = Editor.STATE.SETTINGS_CHANGE;
				LevelSettingsWindow.this.editor.settingsButton = heightButton;
				heightButton.textEntered = "";
				heightButton.down = true;
			}
		};
		buttons.add(heightButton);

		// Height Button
		final SettingsWindowButton playLevelButton = new SettingsWindowButton(
				0, 300, 200, 30, "Play Level", true, true, this) {

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
		playLevelButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				editor.level.currentFactionMoving = editor.level.factions
						.get(editor.level.currentFactionMovingIndex);
				editor.level.turn = 1;
				Game.level = editor.level;
				Game.editorMode = false;
			}
		};
		buttons.add(playLevelButton);

		//
		// playLevelButton = new LevelButton(50, 650, 100, 50, "", "",
		// "PLAY LEVEL", true, true);
		// addFactionButton.setClickListener(new ClickListener() {
		//
		// @Override
		// public void click() {
		// }
		// });
		// buttons.add(addFactionButton);
	}

	@Override
	public void update() {

	}

}
