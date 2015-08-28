package com.marklynch.editor.settingswindow;

import com.marklynch.Game;
import com.marklynch.editor.AttributesWindow;
import com.marklynch.editor.Editor;
import com.marklynch.tactics.objects.level.Decoration;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class DecorationsSettingsWindow extends SettingsWindow {
	SettingsWindowButton addDecorationButton;

	public DecorationsSettingsWindow(float width, Editor editor) {
		super(width, editor);

		addDecorationButton = new SettingsWindowButton(0, 100, 200, 30,
				"ADD DECORATION", true, true, this) {

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
				if (DecorationsSettingsWindow.this.editor.state == Editor.STATE.ADD_OBJECT)
					DecorationsSettingsWindow.this.editor.state = Editor.STATE.DEFAULT;
			}

		};

		addDecorationButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				Decoration newDecoration = new Decoration("Decoration "
						+ Game.level.decorations.size(), 0, 0, 100, 100, true,
						"sign.png");
				Game.level.decorations.add(newDecoration);
				updateDecorationsButtons();
				DecorationsSettingsWindow.this.editor.clearSelectedObject();
				DecorationsSettingsWindow.this.editor
						.depressButtonsSettingsAndDetailsButtons();
			}
		};
		updateDecorationsButtons();
	}

	public void updateDecorationsButtons() {

		buttons.clear();

		buttons.add(addDecorationButton);

		for (int i = 0; i < Game.level.decorations.size(); i++) {
			final int index = i;

			final SettingsWindowButton decorationButton = new SettingsWindowButton(
					0, 200 + i * 30, 200, 30,
					Game.level.decorations.get(index), true, true, this) {

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

			decorationButton.clickListener = new ClickListener() {

				@Override
				public void click() {
					editor.clearSelectedObject();
					editor.depressButtonsSettingsAndDetailsButtons();
					decorationButton.down = true;
					editor.attributesWindow = new AttributesWindow(200, 200,
							200, Game.level.decorations.get(index), editor);
				}
			};
			buttons.add(decorationButton);

		}

	}

	@Override
	public void update() {
		updateDecorationsButtons();
	}
}
