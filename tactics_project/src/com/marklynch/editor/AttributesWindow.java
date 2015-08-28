package com.marklynch.editor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;

import mdesl.graphics.Color;

import com.marklynch.Game;
import com.marklynch.editor.Editor.STATE;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Decoration;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.level.script.ScriptEventSpeech.SpeechPart;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.ui.button.AtributesWindowButton;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;

public class AttributesWindow {

	public final static String[] colorFields = { "r", "g", "b", "a" };

	public float x;
	public float width;

	public float y;

	public Object object;

	public Vector<AtributesWindowButton> buttons = new Vector<AtributesWindowButton>();

	public Editor editor;

	String[] fields;

	String title = "";

	public AttributesWindow(float x, float width, float y, final Object object,
			final Editor editor) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.object = object;
		this.editor = editor;

		if (object instanceof Color) {
			fields = colorFields;
		} else {
			try {
				fields = (String[]) object.getClass()
						.getField("editableAttributes").get(object);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// if (object instanceof Square) {
		// } else if (object instanceof Actor) {
		// fields = actorFields;
		// } else if (object instanceof GameObject) {
		// fields = gameObjectFields;
		// } else if (object instanceof Faction) {
		// fields = factionFields;
		// } else if (object instanceof Weapon) {
		// fields = weaponFields;
		// } else
		// } else if (object instanceof Decoration) {
		// fields = decorationFields;
		// } else if (object instanceof ScriptEventSetAI) {
		// fields = scriptEventSetAiFields;
		// } else if (object instanceof ScriptTriggerTurnStart) {
		// fields = scriptTriggerTurnStartFields;
		// } else if (object instanceof ScriptEventEndLevel) {
		// fields = scriptEventEndLevelFields;
		// }

		// Title
		if (object instanceof Actor) {
			Actor actor = (Actor) object;
			title = "Actor @ " + actor.squareGameObjectIsOn.x + ","
					+ actor.squareGameObjectIsOn.y;
		} else if (object instanceof GameObject) {
			GameObject gameObject = (GameObject) object;
			title = "Object @ " + gameObject.squareGameObjectIsOn.x + ","
					+ gameObject.squareGameObjectIsOn.y;
		} else if (object instanceof Faction) {
			title = "Faction " + Game.level.factions.indexOf(object);
		} else if (object instanceof Square) {
			Square square = (Square) object;
			title = "Square @ " + square.x + "," + square.y;
		} else if (object instanceof Weapon) {
			title = "Weapon " + editor.weapons.indexOf(object);
		} else if (object instanceof Color) {
			title = "Color " + editor.colors.indexOf(object);
		} else if (object instanceof Decoration) {
			title = "Decoration...";
		}

		// Attribute buttons
		int i = 0;
		int count = 0;
		for (; i < fields.length; i++) {
			final int fieldIndex = i;
			Field field = null;
			ArrayList arrayList = null;

			try {
				field = object.getClass().getField(fields[i]);
				if (field.getType().isAssignableFrom(ArrayList.class)) {
					arrayList = (ArrayList) field.get(object);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (arrayList != null) {
				for (int j = 0; j < arrayList.size(); j++) {
					final int arrayListIndex = j;
					final int index = count;
					final AtributesWindowButton button = new AtributesWindowButton(
							0, 0 + (index + 1) * 30, 200, 30, object,
							fields[fieldIndex], true, true, this,
							arrayListIndex);
					buttons.add(button);
					button.setClickListener(new ClickListener() {
						@Override
						public void click() {
							button.down = !button.down;
							if (button.down) {
								depressButtons();
								button.down = true;
								editor.editAttribute(object,
										fields[fieldIndex], button,
										arrayListIndex);
							} else {
								editor.enterTyped();
							}
						}
					});
					count++;
				}
			} else {
				final int index = count;
				final AtributesWindowButton button = new AtributesWindowButton(
						0, 0 + (index + 1) * 30, 200, 30, object,
						fields[fieldIndex], true, true, this, 0);
				buttons.add(button);
				button.setClickListener(new ClickListener() {
					@Override
					public void click() {
						button.down = !button.down;
						if (button.down) {
							depressButtons();
							button.down = true;
							editor.editAttribute(object, fields[fieldIndex],
									button, 0);
						} else {
							editor.enterTyped();
						}
					}
				});
				count++;
			}

		}

		// Custom buttons (copy, delete...)
		if (object instanceof Actor) {
			final Actor actor = (Actor) object;
			final AtributesWindowButton button = new AtributesWindowButton(0,
					0 + (count + 2) * 30, 200, 30, actor, "delete", true, true,
					this, 0);
			buttons.add(button);
			button.setClickListener(new ClickListener() {
				@Override
				public void click() {

					int actorCount = 0;
					for (Faction faction : Game.level.factions) {
						actorCount += faction.actors.size();
					}
					if (actorCount > 1) {
						depressButtons();
						actor.faction.actors.remove(actor);
						actor.squareGameObjectIsOn.gameObject = null;
						editor.clearSelectedObject();
						editor.settingsWindow.update();
					}
				}
			});
			final AtributesWindowButton copyButton = new AtributesWindowButton(
					0, 0 + (count + 3) * 30, 200, 30, actor, "copy", true,
					true, this, 0);
			buttons.add(copyButton);
			copyButton.setClickListener(new ClickListener() {
				@Override
				public void click() {
					depressButtons();
					editor.depressButtonsSettingsAndDetailsButtons();
					editor.clearSelectedObject();
					editor.state = STATE.ADD_ACTOR;
					editor.actorTemplate = actor;
					editor.clearSelectedObject();
					editor.settingsWindow.update();
					editor.actorsSettingsWindow.addActorsButton.down = true;
				}
			});

		} else if (object instanceof GameObject) {
			final GameObject gameObject = (GameObject) object;
			final AtributesWindowButton deleteButton = new AtributesWindowButton(
					0, 0 + (count + 2) * 30, 200, 30, gameObject, "delete",
					true, true, this, 0);
			buttons.add(deleteButton);
			deleteButton.setClickListener(new ClickListener() {
				@Override
				public void click() {
					depressButtons();
					Game.level.inanimateObjects.remove(gameObject);
					gameObject.squareGameObjectIsOn.gameObject = null;
					editor.clearSelectedObject();
					editor.settingsWindow.update();
				}
			});
			final AtributesWindowButton copyButton = new AtributesWindowButton(
					0, 0 + (count + 3) * 30, 200, 30, gameObject, "copy", true,
					true, this, 0);
			buttons.add(copyButton);
			copyButton.setClickListener(new ClickListener() {
				@Override
				public void click() {
					depressButtons();
					editor.depressButtonsSettingsAndDetailsButtons();
					editor.clearSelectedObject();
					editor.state = STATE.ADD_OBJECT;
					editor.gameObjectTemplate = gameObject;
					editor.clearSelectedObject();
					editor.settingsWindow.update();
					editor.objectsSettingsWindow.addObjectsButton.down = true;
				}
			});
		} else if (object instanceof Faction) {
			final Faction faction = (Faction) object;
			final AtributesWindowButton button = new AtributesWindowButton(0,
					0 + (count + 2) * 30, 200, 30, faction, "delete", true,
					true, this, 0);
			buttons.add(button);
			button.setClickListener(new ClickListener() {
				@Override
				public void click() {

					if (Game.level.factions.size() == 1)
						return;

					depressButtons();
					for (Actor actor : faction.actors) {
						actor.squareGameObjectIsOn.gameObject = null;
					}
					Game.level.factions.remove(faction);
					editor.clearSelectedObject();
					editor.factionsSettingsWindow.updateFactionsButtons();
					for (Faction faction : Game.level.factions) {
						faction.relationships.remove(faction);
					}
					editor.settingsWindow.update();
				}
			});
		} else if (object instanceof Weapon) {
			final Weapon weapon = (Weapon) object;
			final AtributesWindowButton button = new AtributesWindowButton(0,
					0 + (count + 2) * 30, 200, 30, weapon, "delete", true,
					true, this, 0);
			buttons.add(button);
			button.setClickListener(new ClickListener() {
				@Override
				public void click() {

					depressButtons();
					editor.weapons.remove(weapon);
					editor.clearSelectedObject();
					editor.weaponsSettingsWindow.updateWeaponsButtons();
					editor.settingsWindow.update();
				}
			});
		} else if (object instanceof Decoration) {
			final AtributesWindowButton button = new AtributesWindowButton(0,
					0 + (count + 2) * 30, 200, 30, object, "delete", true,
					true, this, 0);
			buttons.add(button);
			button.setClickListener(new ClickListener() {
				@Override
				public void click() {
					depressButtons();
					Game.level.decorations.remove(object);
					editor.clearSelectedObject();
					editor.decorationsSettingsWindow.updateDecorationsButtons();
					editor.settingsWindow.update();
				}
			});
		} else if (object instanceof SpeechPart) {
			final AtributesWindowButton button = new AtributesWindowButton(0,
					0 + (count + 2) * 30, 200, 30, object, "Add actor", true,
					true, this, 0);
			buttons.add(button);
			final SpeechPart speechPart = (SpeechPart) object;
			button.setClickListener(new ClickListener() {
				@Override
				public void click() {

					speechPart.actors.add(Game.level.factions.get(0).actors
							.get(0));
					speechPart.positions.add(100f);
					speechPart.directions.add(true);

					depressButtons();
					// editor.clearSelectedObject();
					editor.settingsWindow.update();
				}
			});
		}
	}

	public void draw() {
		// QuadUtils.drawQuad(Color.WHITE, x, x + width, y, Game.windowHeight);

		QuadUtils.drawQuad(Color.WHITE, x, x + 200, y, y + 30);

		TextUtils.printTextWithImages(new Object[] { new StringWithColor(title,
				Color.BLACK) }, x, y, 200, true);

		for (AtributesWindowButton button : buttons) {
			button.draw();
		}
	}

	public void depressButtons() {
		for (Button button : buttons) {
			button.down = false;
		}

	}

}
