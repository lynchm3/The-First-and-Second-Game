package com.marklynch.editor;

import java.util.ArrayList;

import mdesl.graphics.Color;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.script.ScriptEvent;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;
import com.marklynch.tactics.objects.unit.ai.AI;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SelectionWindowButton;
import com.marklynch.utils.QuadUtils;

public class ClassSelectionWindow<T> {

	ArrayList<Class> classes;
	public ArrayList<SelectionWindowButton> buttons = new ArrayList<SelectionWindowButton>();
	public Editor editor;
	public Class superClass;

	public ClassSelectionWindow(final ArrayList<Class> classes,
			final Editor editor, final Class superClass) {
		this.editor = editor;
		this.classes = classes;
		this.superClass = superClass;

		for (int i = 0; i < classes.size(); i++) {

			final int index = i;

			float x = i / ((int) Game.windowHeight / 30) * 200;
			float y = i % (Game.windowHeight / 30) * 30;

			final SelectionWindowButton selectionWindowButton = new SelectionWindowButton(
					x, y, 190, 30, null, null, "", true, true, classes.get(i));

			selectionWindowButton.clickListener = new ClickListener() {

				@Override
				public void click() {

					try {

						if (superClass == ScriptEvent.class) {
							Game.level.script.scriptEvents
									.add((ScriptEvent) classes.get(index)
											.newInstance());
							editor.classSelectionWindow = null;
						} else if (superClass == ScriptTrigger.class) {
							Game.level.script.scriptTriggers
									.add((ScriptTrigger) classes.get(index)
											.newInstance());
							editor.classSelectionWindow = null;
						} else if (superClass == AI.class) {
							Game.level.ais.add((AI) classes.get(index)
									.newInstance());
							editor.classSelectionWindow = null;
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
