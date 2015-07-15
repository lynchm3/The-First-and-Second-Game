package com.marklynch.editor;

import java.util.Vector;

import org.newdawn.slick.Color;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.ui.button.AtributesWindowButton;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.utils.QuadUtils;

public class AttributesWindow {

	public float x;
	public float width;

	public float realX1;
	public float realX2;

	public Object object;

	public Vector<AtributesWindowButton> buttons = new Vector<AtributesWindowButton>();

	public Editor editor;

	public final static String[] gameObjectFields = { "name", "strength",
			"dexterity", "intelligence", "endurance", "totalHealth",
			"remainingHealth" };

	public final static String[] actorFields = { "faction", "name", "strength",
			"dexterity", "intelligence", "endurance", "totalHealth",
			"remainingHealth" };

	public final static String[] squareFields = { "elevation", "travelCost" };

	public final static String[] factionFields = { "name" };

	String[] fields;

	public AttributesWindow(float x, float width, final Object object,
			final Editor editor) {
		super();
		this.x = x;
		this.width = width;
		this.object = object;
		this.editor = editor;

		if (object instanceof Square) {
			fields = squareFields;
		} else if (object instanceof Actor) {
			fields = actorFields;
		} else if (object instanceof GameObject) {
			fields = gameObjectFields;
		} else if (object instanceof Faction) {
			fields = factionFields;
		}

		int i = 0;
		for (; i < fields.length; i++) {
			final int index = i;
			final AtributesWindowButton button = new AtributesWindowButton(0,
					0 + index * 30, 200, 30, object, fields[i], true, true,
					this);
			buttons.add(button);
			button.setClickListener(new ClickListener() {
				@Override
				public void click() {
					button.down = !button.down;
					if (button.down) {
						depressButtons();
						button.down = true;
						editor.editAttribute(object, fields[index], button);
					} else {
						editor.enterTyped();
					}
				}
			});

		}

		if (object instanceof Actor) {
			final Actor actor = (Actor) object;
			final AtributesWindowButton button = new AtributesWindowButton(0,
					0 + i * 30, 200, 30, actor, "delete", true, true, this);
			buttons.add(button);
			button.setClickListener(new ClickListener() {
				@Override
				public void click() {
					depressButtons();
					editor.clearSelectedObject();
				}
			});

		} else if (object instanceof GameObject) {
			final GameObject gameObject = (GameObject) object;
			final AtributesWindowButton button = new AtributesWindowButton(0,
					0 + i * 30, 200, 30, gameObject, "delete", true, true, this);
			buttons.add(button);
			button.setClickListener(new ClickListener() {
				@Override
				public void click() {
					depressButtons();
					editor.level.inanimateObjects.remove(gameObject);
					gameObject.squareGameObjectIsOn.gameObject = null;
					editor.clearSelectedObject();
				}
			});
		} else if (object instanceof Faction) {
			final Faction faction = (Faction) object;
			final AtributesWindowButton button = new AtributesWindowButton(0,
					0 + i * 30, 200, 30, faction, "delete", true, true, this);
			buttons.add(button);
			button.setClickListener(new ClickListener() {
				@Override
				public void click() {
					depressButtons();
					for (Actor actor : faction.actors) {
						actor.squareGameObjectIsOn.gameObject = null;
					}
					editor.level.factions.remove(faction);
					editor.clearSelectedObject();
				}
			});
		}
	}

	public void draw() {
		realX1 = Game.windowWidth - x - width;
		realX2 = Game.windowWidth - x;
		QuadUtils.drawQuad(Color.white, Game.windowWidth - x - width,
				Game.windowWidth - x, 0, Game.windowHeight);
		for (Button button : buttons) {
			button.draw();
		}
	}

	public void depressButtons() {
		for (Button button : buttons) {
			button.down = false;
		}

	}

}
