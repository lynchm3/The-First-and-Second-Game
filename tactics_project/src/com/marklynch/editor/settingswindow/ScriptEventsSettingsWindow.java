package com.marklynch.editor.settingswindow;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.editor.AttributesDialog;
import com.marklynch.editor.ClassSelectionWindow;
import com.marklynch.editor.Editor;
import com.marklynch.tactics.objects.level.script.ScriptEvent;
import com.marklynch.tactics.objects.level.script.ScriptEventEndLevel;
import com.marklynch.tactics.objects.level.script.ScriptEventGroup;
import com.marklynch.tactics.objects.level.script.ScriptEventInlineSpeech;
import com.marklynch.tactics.objects.level.script.ScriptEventSetAI;
import com.marklynch.tactics.objects.level.script.ScriptEventSpeech;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class ScriptEventsSettingsWindow extends SettingsWindow {

	public ScriptEventsSettingsWindow(float width, final Editor editor) {
		super(width, editor);
		updateScriptsButtons();
	}

	public void updateScriptsButtons() {
		buttons.clear();

		final SettingsWindowButton addScriptButton = new SettingsWindowButton(0, 100, 200, 30, "ADD SCRIPT EVENT", true,
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

		addScriptButton.clickListener = new ClickListener() {

			@Override
			public void click() {

				ArrayList<Class> classes = new ArrayList<Class>();
				classes.add(ScriptEventEndLevel.class);
				classes.add(ScriptEventGroup.class);
				classes.add(ScriptEventInlineSpeech.class);
				classes.add(ScriptEventSetAI.class);
				classes.add(ScriptEventSpeech.class);

				editor.classSelectionWindow = new ClassSelectionWindow(classes, editor, ScriptEvent.class,
						"Select a Script Event Type");
			}
		};
		buttons.add(addScriptButton);

		for (int i = 0; i < Game.level.script.scriptEvents.size(); i++) {
			final int index = i;

			final SettingsWindowButton scriptButton = new SettingsWindowButton(0, 200 + i * 30, 200, 30,
					Game.level.script.scriptEvents.get(i), true, true) {

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

			scriptButton.clickListener = new ClickListener() {

				@Override
				public void click() {
					editor.clearSelectedObject();
					editor.depressButtonsSettingsAndDetailsButtons();
					scriptButton.down = true;
					editor.attributesWindow = new AttributesDialog(200, 200, 200,
							Game.level.script.scriptEvents.get(index), editor);

				}
			};
			buttons.add(scriptButton);

		}

	}

	@Override
	public void update() {
		updateScriptsButtons();

	}
}
