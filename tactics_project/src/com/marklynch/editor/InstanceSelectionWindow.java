package com.marklynch.editor;

import java.util.ArrayList;

import mdesl.graphics.Color;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.script.ScriptEvent;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;
import com.marklynch.tactics.objects.unit.ai.routines.AIRoutine;
import com.marklynch.tactics.objects.weapons.Weapon;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SelectionWindowButton;
import com.marklynch.utils.QuadUtils;

public class InstanceSelectionWindow<T> {

	ArrayList<T> instances;
	public ArrayList<SelectionWindowButton> buttons = new ArrayList<SelectionWindowButton>();
	public Editor editor;

	public InstanceSelectionWindow(final ArrayList<T> instances,
			final Editor editor) {
		this.editor = editor;
		this.instances = instances;

		for (int i = 0; i < instances.size(); i++) {

			final int index = i;

			float x = i / ((int) Game.windowHeight / 30) * 200;
			float y = i % (Game.windowHeight / 30) * 30;

			final SelectionWindowButton selectionWindowButton = new SelectionWindowButton(
					x, y, 190, 30, null, null, "", true, true, instances.get(i));

			selectionWindowButton.clickListener = new ClickListener() {

				@Override
				public void click() {

					try {

						if (instances.get(index) instanceof Weapon) {
							Weapon weaponTemplate = (Weapon)instances.get(index);
							Weapon weapon = weaponTemplate.makeWeapon();
							editor.weapons.add(weapon);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

					editor.settingsWindow.update();

				}
			};
			buttons.add(selectionWindowButton);
		}
	}

	public void draw() {
		// faction
		QuadUtils.drawQuad(Color.WHITE, 0, Game.windowWidth, 0,
				Game.windowHeight);
		for (SelectionWindowButton button : buttons) {
			button.draw();
		}

	}

	public Button getButtonFromMousePosition(float mouseX, float mouseY) {
		for (Button button : buttons) {
			if (button.calculateIfPointInBoundsOfButton(mouseX,
					Game.windowHeight - mouseY))
				return button;
		}
		return null;
	}
}
