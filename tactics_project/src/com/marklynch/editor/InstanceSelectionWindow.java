package com.marklynch.editor;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.editor.Editor.EDITOR_STATE;
import com.marklynch.objects.GameObject;
import com.marklynch.ui.EditorToast;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SelectionWindowButton;
import com.marklynch.utils.QuadUtils;

import mdesl.graphics.Color;

public class InstanceSelectionWindow<T> {

	ArrayList<T> instances;
	public ArrayList<SelectionWindowButton> buttons = new ArrayList<SelectionWindowButton>();
	public Editor editor;
	public String title;

	public InstanceSelectionWindow(final ArrayList<T> instances, final Editor editor, String title) {
		this.editor = editor;
		this.instances = instances;
		this.title = title;

		for (int i = 0; i < instances.size(); i++) {

			final int index = i;

			float x = i / ((int) Game.windowHeight / 30) * 200;
			float y = i % (Game.windowHeight / 30) * 30;

			final SelectionWindowButton selectionWindowButton = new SelectionWindowButton(x, y, 190, 30, null, null, "",
					true, true, instances.get(i));

			selectionWindowButton.clickListener = new ClickListener() {

				@Override
				public void click() {

					try {

						if (instances.get(index) instanceof GameObject) {
							GameObject gameObject = (GameObject) instances.get(index);
							// Weapon weapon = weaponTemplate.makeWeapon();
							editor.gameObject = gameObject;
							// editor.weapons.add(weapon);
							editor.instanceSelectionWindow = null;

							editor.depressButtonsSettingsAndDetailsButtons();
							editor.clearSelectedObject();
							editor.editorState = EDITOR_STATE.ADD_OBJECT;
							editor.toast = new EditorToast(200, 50, "Select location to add object");

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

		QuadUtils.drawQuad(Color.WHITE, 0, 0, Game.windowWidth, Game.windowHeight);

		for (SelectionWindowButton button : buttons) {
			button.draw();
		}

		float textWidth = Game.smallFont.getWidth(title);
		Game.activeBatch.setColor(Color.RED);
		Game.smallFont.drawText(Game.activeBatch, title, 200, 50);
		Game.activeBatch.setColor(1,1,1,1);

	}

	public Button getButtonFromMousePosition(float mouseX, float mouseY) {
		for (Button button : buttons) {
			if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
				return button;
		}
		return null;
	}
}
