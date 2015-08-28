package com.marklynch.editor.settingswindow;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.editor.AttributesWindow;
import com.marklynch.editor.ClassSelectionWindow;
import com.marklynch.editor.Editor;
import com.marklynch.tactics.objects.unit.ai.AI;
import com.marklynch.tactics.objects.unit.ai.AIFreeze;
import com.marklynch.tactics.objects.unit.ai.AIMoveToSquare;
import com.marklynch.tactics.objects.unit.ai.AIStationary;
import com.marklynch.tactics.objects.unit.ai.AITargetObject;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class AIsSettingsWindow extends SettingsWindow {

	public AIsSettingsWindow(float width, final Editor editor) {
		super(width, editor);
		updateAIsButtons();
	}

	public void updateAIsButtons() {
		buttons.clear();

		final SettingsWindowButton addAIButton = new SettingsWindowButton(0,
				100, 200, 30, "ADD AI", true, true, this) {

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

		addAIButton.clickListener = new ClickListener() {

			@Override
			public void click() {

				ArrayList<Class> classes = new ArrayList<Class>();
				classes.add(AITargetObject.class);
				classes.add(AIStationary.class);
				classes.add(AIFreeze.class);
				classes.add(AIMoveToSquare.class);

				editor.classSelectionWindow = new ClassSelectionWindow(classes,
						editor, AI.class);
			}
		};
		buttons.add(addAIButton);

		int count = 0;
		for (int i = 0; i < Game.level.ais.size(); i++) {

			final int index = count;

			final SettingsWindowButton aiButton = new SettingsWindowButton(0,
					200 + i * 30, 200, 30, Game.level.ais.get(i), true, true,
					this) {

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

			aiButton.clickListener = new ClickListener() {

				@Override
				public void click() {
					editor.clearSelectedObject();
					editor.depressButtonsSettingsAndDetailsButtons();
					aiButton.down = true;
					editor.attributesWindow = new AttributesWindow(200, 200,
							200, Game.level.ais.get(index), editor);

				}
			};
			buttons.add(aiButton);

			count++;

		}

	}

	@Override
	public void update() {
		updateAIsButtons();
		SettingsWindowButton button = this.getButton(editor.objectToEdit);
		if (button != null)
			button.down = true;

	}
}
