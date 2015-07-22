package com.marklynch.editor.settingswindow;

import com.google.gson.Gson;
import com.marklynch.Game;
import com.marklynch.editor.Editor;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;
import com.marklynch.utils.FileUtils;

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

		// Play lvl Button
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

		// Save lvl Button
		final SettingsWindowButton saveLevelButton = new SettingsWindowButton(
				0, 400, 200, 30, "Save Level", true, true, this) {

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
		saveLevelButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				Gson gson = new Gson();
				String json = gson.toJson(editor.level);
				// System.out.println(editor.json);
				FileUtils.saveFile(json);

				// I HAVE REMOVED
				// GameObject.level
				// Faction.level
				// Square.level
				// AttackButton.level
				// WeaponButton.level
				//
				// All Textures, so I need to hang on to their URL
				//
				// Square.gameObject
				//
				// GameObject.faction

				// ISSUES
				// GAMEOBJECT <-> SQUARE
				// Faction.relationships

				// Transiented the shit out of
				// Square.java
				// GameObject.java
				// Faction.java
				// Actor.java
				// Level.java

				// SO... saving the game..... Like... mid game..... :/
				// Saving at the start of the turn should be fine... but
				// mid-turn???

				// Theres also Editor.stuff I want to save too...

			}
		};
		buttons.add(saveLevelButton);

		// Load lvl Button
		final SettingsWindowButton loadLevelButton = new SettingsWindowButton(
				0, 500, 200, 30, "Load Level", true, true, this) {

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
		loadLevelButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				Gson gson = new Gson();
				String json = FileUtils.openFile();
				// System.out.println(editor.json);
				// FileUtils.saveFile(json);
				if (json != null) {
					Level level = gson.fromJson(json, Level.class);
					editor.level = level;
					editor.level.postLoad();
					editor.level.loadImages();
				}
			}
		};
		buttons.add(loadLevelButton);

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
