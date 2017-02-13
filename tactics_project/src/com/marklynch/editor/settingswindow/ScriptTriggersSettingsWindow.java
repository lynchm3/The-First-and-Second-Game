package com.marklynch.editor.settingswindow;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.editor.AttributesDialog;
import com.marklynch.editor.ClassSelectionWindow;
import com.marklynch.editor.Editor;
import com.marklynch.script.trigger.ScriptTrigger;
import com.marklynch.script.trigger.ScriptTriggerActorMoves;
import com.marklynch.script.trigger.ScriptTriggerActorSelected;
import com.marklynch.script.trigger.ScriptTriggerFactionSize;
import com.marklynch.script.trigger.ScriptTriggerObjectDestroyed;
import com.marklynch.script.trigger.ScriptTriggerScriptEventEnded;
import com.marklynch.script.trigger.ScriptTriggerSpecficActorOccupiesSquare;
import com.marklynch.script.trigger.ScriptTriggerSquareOccupied;
import com.marklynch.script.trigger.ScriptTriggerSquareUnoccupied;
import com.marklynch.script.trigger.ScriptTriggerTurnStart;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class ScriptTriggersSettingsWindow extends SettingsWindow {

	public ScriptTriggersSettingsWindow(float width, final Editor editor) {
		super(width, editor);
		updateScriptsButtons();
	}

	public void updateScriptsButtons() {
		buttons.clear();

		final SettingsWindowButton addScriptTriggerButton = new SettingsWindowButton(0, 100, 200, 30,
				"ADD SCRIPT TRIGGER", true, true) {

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

		addScriptTriggerButton.clickListener = new ClickListener() {

			@Override
			public void click() {

				ArrayList<Class> classes = new ArrayList<Class>();
				classes.add(ScriptTriggerActorMoves.class);
				classes.add(ScriptTriggerActorSelected.class);
				classes.add(ScriptTriggerObjectDestroyed.class);
				classes.add(ScriptTriggerFactionSize.class);
				classes.add(ScriptTriggerScriptEventEnded.class);
				classes.add(ScriptTriggerSpecficActorOccupiesSquare.class);
				classes.add(ScriptTriggerSquareOccupied.class);
				classes.add(ScriptTriggerSquareUnoccupied.class);
				classes.add(ScriptTriggerTurnStart.class);

				editor.classSelectionWindow = new ClassSelectionWindow(classes, editor, ScriptTrigger.class,
						"Select a Script Trigger Type");
			}
		};
		buttons.add(addScriptTriggerButton);

		int count = 0;
		for (int i = 0; i < Game.level.script.scriptTriggers.size(); i++) {

			final int index = count;

			final SettingsWindowButton scriptButton = new SettingsWindowButton(0, 200 + i * 30, 200, 30,
					Game.level.script.scriptTriggers.get(i), true, true) {

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
							Game.level.script.scriptTriggers.get(index), editor);

				}
			};
			buttons.add(scriptButton);

			count++;

		}

	}

	@Override
	public void update() {
		updateScriptsButtons();

	}
}
