package com.marklynch.editor;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.script.ScriptEvent;
import com.marklynch.script.trigger.ScriptTrigger;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SelectionWindowButton;
import com.marklynch.utils.QuadUtils;

import mdesl.graphics.Color;

public class ClassSelectionWindow<T> {

	ArrayList<Class> classes;
	public ArrayList<SelectionWindowButton> buttons = new ArrayList<SelectionWindowButton>();
	public Editor editor;
	public Class superClass;
	public String title;

	public ClassSelectionWindow(final ArrayList<Class> classes, final Editor editor, final Class superClass,
			String title) {
		this.editor = editor;
		this.classes = classes;
		this.superClass = superClass;
		this.title = title;

		for (int i = 0; i < classes.size(); i++) {

			final int index = i;

			float x = i / ((int) Game.windowHeight / 30) * 200;
			float y = i % (Game.windowHeight / 30) * 30;

			final SelectionWindowButton selectionWindowButton = new SelectionWindowButton(x, y, 190, 30, null, null, "",
					true, true, classes.get(i));

			selectionWindowButton.clickListener = new ClickListener() {

				@Override
				public void click() {

					try {

						if (superClass == ScriptEvent.class) {
							Game.level.script.scriptEvents.add((ScriptEvent) classes.get(index).newInstance());
							editor.classSelectionWindow = null;
						} else if (superClass == ScriptTrigger.class) {
							Game.level.script.scriptTriggers.add((ScriptTrigger) classes.get(index).newInstance());
							editor.classSelectionWindow = null;
						} else if (superClass == AIRoutineUtils.class) {
							Game.level.ais.add((AIRoutineUtils) classes.get(index).newInstance());
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
		QuadUtils.drawQuad(Color.WHITE, 0, 0, Game.windowWidth, Game.windowHeight);
		for (SelectionWindowButton button : buttons) {
			button.draw();
		}

		float textWidth = Game.font.getWidth(title);
		Game.activeBatch.setColor(Color.RED);
		Game.font.drawText(Game.activeBatch, title, 200, 50);
		Game.activeBatch.setColor(Color.WHITE);

	}

	public Button getButtonFromMousePosition(float mouseX, float mouseY) {
		for (Button button : buttons) {
			if (button.calculateIfPointInBoundsOfButton(mouseX, Game.windowHeight - mouseY))
				return button;
		}
		return null;
	}
}
