package com.marklynch.editor;

import java.util.Vector;

import org.newdawn.slick.Color;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
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

	public final static String[] squareFields = { "elevation", "travelCost" };

	public AttributesWindow(float x, float width, Object object,
			final Editor editor) {
		super();
		this.x = x;
		this.width = width;
		this.object = object;
		this.editor = editor;

		if (object instanceof Square) {

			final Square square = (Square) object;
			int i = 0;
			for (; i < squareFields.length; i++) {
				final int index = i;
				final AtributesWindowButton button = new AtributesWindowButton(
						0, 0 + index * 30, 200, 30, square, squareFields[i],
						true, true, this);
				buttons.add(button);
				button.setClickListener(new ClickListener() {
					@Override
					public void click() {
						button.down = !button.down;
						if (button.down) {
							depressButtons();
							button.down = true;
							editor.editAttribute(square, squareFields[index],
									button);
						} else {
							editor.enterTyped();
						}
					}
				});

			}
		} else if (object instanceof GameObject) {

			final GameObject gameObject = (GameObject) object;
			int i = 0;
			for (; i < gameObjectFields.length; i++) {
				final int index = i;
				final AtributesWindowButton button = new AtributesWindowButton(
						0, 0 + index * 30, 200, 30, gameObject,
						gameObjectFields[i], true, true, this);
				buttons.add(button);
				button.setClickListener(new ClickListener() {
					@Override
					public void click() {
						button.down = !button.down;
						if (button.down) {
							depressButtons();
							button.down = true;
							editor.editAttribute(gameObject,
									gameObjectFields[index], button);
						} else {
							editor.enterTyped();
						}
					}
				});

			}

			if (gameObject instanceof Actor) {
				final Actor actor = (Actor) gameObject;
				final AtributesWindowButton button = new AtributesWindowButton(
						0, 0 + i * 30, 200, 30, actor, "delete", true, true,
						this);
				buttons.add(button);
				button.setClickListener(new ClickListener() {
					@Override
					public void click() {
						depressButtons();
						actor.faction.actors.remove(actor);
						actor.squareGameObjectIsOn.gameObject = null;
						editor.clearSelectedObject();
					}
				});

			} else {
				final AtributesWindowButton button = new AtributesWindowButton(
						0, 0 + i * 30, 200, 30, gameObject, "delete", true,
						true, this);
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
			}

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
