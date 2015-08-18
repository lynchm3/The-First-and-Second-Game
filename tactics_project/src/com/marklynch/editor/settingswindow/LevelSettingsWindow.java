package com.marklynch.editor.settingswindow;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.marklynch.Game;
import com.marklynch.editor.Editor;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.script.ScriptEvent;
import com.marklynch.tactics.objects.level.script.SpeechPart;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTriggerActorSelected;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;
import com.marklynch.utils.FileUtils;

public class LevelSettingsWindow extends SettingsWindow {

	public LevelSettingsWindow(float width, final Editor editor) {
		super(width, editor);

		// Width Button
		final SettingsWindowButton widthButton = new SettingsWindowButton(0,
				100, 200, 30, "Level Width: " + Game.level.width, true, true,
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
				Game.level.changeSize(newWidth, Game.level.height);
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
				text = "Level Width: " + Game.level.width;

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
				130, 200, 30, "Level Height: " + Game.level.height, true, true,
				this) {

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
				Game.level.changeSize(Game.level.width, newHeight);
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
				text = "Level Height: " + Game.level.height;
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
				Game.level.currentFactionMoving = Game.level.factions
						.get(Game.level.currentFactionMovingIndex);
				Game.level.turn = 1;
				Game.level = Game.level;
				Game.editorMode = false;
				// Game.runBlurAnimation();
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

				// Gson gson = new GsonBuilder().setPrettyPrinting().create();
				// String json = gson.toJson(obj);
				// System.out.println(json);

				Gson gson = new GsonBuilder()
						.setPrettyPrinting()
						.registerTypeAdapterFactory(
								new SpeechPart.SpeechPartTypeAdapterFactory())
						.registerTypeAdapterFactory(
								new Faction.FactionTypeAdapterFactory())
						// .registerTypeAdapterFactory(
						// new
						// ScriptTriggerActorSelected.ScriptTriggerActorSelectedAdapterFactory())
						.registerTypeAdapter(ScriptEvent.class,
								new SubClassFriendlyAdapter<ScriptEvent>())
						.registerTypeAdapter(ScriptTrigger.class,
								new SubClassFriendlyAdapter<ScriptTrigger>())
						.create();

				String json = gson.toJson(Game.level);
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
				Gson gson = new GsonBuilder()
						.setPrettyPrinting()
						.registerTypeAdapterFactory(
								new SpeechPart.SpeechPartTypeAdapterFactory())
						.registerTypeAdapterFactory(
								new Faction.FactionTypeAdapterFactory())
						// .registerTypeAdapterFactory(
						// new
						// ScriptTriggerActorSelected.ScriptTriggerActorSelectedAdapterFactory())
						.registerTypeAdapter(ScriptEvent.class,
								new SubClassFriendlyAdapter<ScriptEvent>())
						.registerTypeAdapter(ScriptTrigger.class,
								new SubClassFriendlyAdapter<ScriptTrigger>())
						.create();
				String json = FileUtils.openFile();
				// System.out.println(editor.json);
				// FileUtils.saveFile(json);
				if (json != null) {
					Game.level = gson.fromJson(json, Level.class);
					Game.level.postLoad();
					Game.level.loadImages();
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

	public static class SubClassFriendlyAdapter<C> implements
			JsonSerializer<C>, JsonDeserializer<C> {

		private static final String CLASSNAME = "CLASSNAME";
		private static final String INSTANCE = "INSTANCE";

		@Override
		public JsonElement serialize(C src, Type typeOfSrc,
				JsonSerializationContext context) {

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
		public C deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
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
			if (object instanceof ScriptTriggerActorSelected) {
				ScriptTriggerActorSelected scriptTriggerActorSelected = (ScriptTriggerActorSelected) object;
				scriptTriggerActorSelected.actor = Game.level
						.findActorFromGUID(scriptTriggerActorSelected.actorGUID);
				scriptTriggerActorSelected.level = Game.level;

			}
			return object;
		}
	}

	@Override
	public void update() {

	}

}
