package com.marklynch.editor;

import java.util.Vector;

import org.newdawn.slick.Color;

import com.marklynch.tactics.objects.GameObject;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.WindowButton;
import com.marklynch.utils.QuadUtils;

public class DetailsWindow {

	public float x;
	public float y;

	public float width;
	public float height;

	public Object object;

	public Vector<WindowButton> buttons = new Vector<WindowButton>();

	public Editor editor;

	public final static String[] gameObjectFields = { "name", "strength",
			"dexterity", "intelligence", "endurance", "totalHealth",
			"remainingHealth" };

	public DetailsWindow(float x, float y, float width, float height,
			Object object, final Editor editor) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.object = object;
		this.editor = editor;

		if (object instanceof GameObject) {

			final GameObject gameObject = (GameObject) object;

			for (int i = 0; i < gameObjectFields.length; i++) {
				final int index = i;
				final WindowButton button = new WindowButton(0, 0 + index * 50,
						200, 50, gameObject, gameObjectFields[i], true, true,
						this);
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

			// Name

			// // attributes
			// public int strength = 0;
			// public int dexterity = 0;
			// public int intelligence = 0;
			// public int endurance = 0;
			// public float totalHealth = 0;
			// public float remainingHealth = 0;
			//
			// // Inventory
			// public Vector<Weapon> weapons = new Vector<Weapon>();
			//
			// // images
			// public Texture imageTexture = null;
			//
			// // faction
			// public Faction faction;

		}
	}

	public void draw() {
		QuadUtils.drawQuad(Color.white, x, x + width, y, y + height);
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
