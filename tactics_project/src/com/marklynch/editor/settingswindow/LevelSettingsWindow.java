package com.marklynch.editor.settingswindow;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.marklynch.Game;
import com.marklynch.SaveAndLoad;
import com.marklynch.editor.Editor;
import com.marklynch.script.trigger.ScriptTriggerActorSelected;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class LevelSettingsWindow extends SettingsWindow {

	public LevelSettingsWindow(float width, final Editor editor) {
		super(width, editor);

		// Width Button
		final SettingsWindowButton widthButton = new SettingsWindowButton(0, 100, 200, 30,
				"Level Width: " + Game.level.width, true, true) {

			@Override
			public void keyTyped(char character) {
				if (48 <= character && character <= 57 && textEntered.length() < 2) {
					this.textEntered += character;
					this.text = "Level Width: " + textEntered;
				}

			}

			@Override
			public void enterTyped() {
				int newWidth = 0;
				if (this.textEntered.length() > 0)
					newWidth = Integer.valueOf(this.textEntered).intValue();
				Game.level.changeSize(newWidth, Game.level.height);
				LevelSettingsWindow.this.editor.editorState = Editor.EDITOR_STATE.DEFAULT;
				this.down = false;
			}

			@Override
			public void backTyped() {
				if (textEntered.length() > 0) {
					this.textEntered = this.textEntered.substring(0, this.textEntered.length() - 1);
					this.text = "Level Width: " + textEntered;
				}
			}

			@Override
			public void depress() {
				text = "Level Width: " + Game.level.width;

			}
		};
		widthButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				depressButtons();
				LevelSettingsWindow.this.editor.editorState = Editor.EDITOR_STATE.SETTINGS_CHANGE;
				LevelSettingsWindow.this.editor.settingsButton = widthButton;
				widthButton.textEntered = "";
				widthButton.down = true;
			}
		};
		buttons.add(widthButton);

		// Height Button
		final SettingsWindowButton heightButton = new SettingsWindowButton(0, 130, 200, 30,
				"Level Height: " + Game.level.height, true, true) {

			@Override
			public void keyTyped(char character) {
				if (48 <= character && character <= 57 && textEntered.length() < 2) {
					this.textEntered += character;
				}
				this.text = "Level Height: " + textEntered;

			}

			@Override
			public void enterTyped() {
				int newHeight = 0;
				if (this.textEntered.length() > 0)
					newHeight = Integer.valueOf(this.textEntered).intValue();
				Game.level.changeSize(Game.level.width, newHeight);
				this.down = false;
			}

			@Override
			public void backTyped() {
				if (textEntered.length() > 0) {
					this.textEntered = this.textEntered.substring(0, this.textEntered.length() - 1);
					this.text = "Level Height: " + textEntered;
				}
			}

			@Override
			public void depress() {
				text = "Level Height: " + Game.level.height;
			}
		};
		heightButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				depressButtons();
				LevelSettingsWindow.this.editor.editorState = Editor.EDITOR_STATE.SETTINGS_CHANGE;
				LevelSettingsWindow.this.editor.settingsButton = heightButton;
				heightButton.textEntered = "";
				heightButton.down = true;
			}
		};
		buttons.add(heightButton);

		// Play lvl Button
		final SettingsWindowButton playLevelButton = new SettingsWindowButton(0, 300, 200, 30, "Play Level", true,
				true) {

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
				Game.level.currentFactionMoving = Game.level.factions.get(Game.level.currentFactionMovingIndex);
				Game.level.activeActor = Game.level.player;
				Game.level.activeActor.equippedWeapon = Game.level.activeActor.getWeaponsInInventory().get(0);
				Game.level.turn = 1;
				Game.level = Game.level;
				Game.editorMode = false;
				// Game.runBlurAnimation();
			}
		};
		buttons.add(playLevelButton);

		// Save lvl Button
		final SettingsWindowButton saveLevelButton = new SettingsWindowButton(0, 400, 200, 30, "Save Level", true,
				true) {

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

				SaveAndLoad.save();

			}
		};
		buttons.add(saveLevelButton);

		// Load lvl Button
		final SettingsWindowButton loadLevelButton = new SettingsWindowButton(0, 500, 200, 30, "Load Level", true,
				true) {

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
				SaveAndLoad.load();
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

	public static class SubClassFriendlyAdapter<C> implements JsonSerializer<C>, JsonDeserializer<C> {

		private static final String CLASSNAME = "CLASSNAME";
		private static final String INSTANCE = "INSTANCE";

		@Override
		public JsonElement serialize(C src, Type typeOfSrc, JsonSerializationContext context) {

			if (src instanceof ScriptTriggerActorSelected) {
				ScriptTriggerActorSelected scriptTriggerActorSelected = (ScriptTriggerActorSelected) src;
				scriptTriggerActorSelected.actorGUID = scriptTriggerActorSelected.actor.guid;
			}

			JsonObject retValue = new JsonObject();
			String className = src.getClass().getCanonicalName();
			retValue.addProperty(CLASSNAME, className);
			JsonElement elem = context.serialize(src);
			retValue.add(INSTANCE, elem);

			return retValue;
		}

		@Override
		public C deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();
			JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
			String className = prim.getAsString();

			Class<?> klass = null;
			try {
				klass = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new JsonParseException(e.getMessage());
			}

			C object = context.deserialize(jsonObject.get(INSTANCE), klass);
			// if (object instanceof SpeechPart) {
			// ScriptTriggerActorSelected scriptTriggerActorSelected =
			// (ScriptTriggerActorSelected) object;
			// scriptTriggerActorSelected.actor = Game.level
			// .findActorFromGUID(scriptTriggerActorSelected.actorGUID);
			//
			// }
			return object;
		}
	}

	@Override
	public void update() {

	}

}
