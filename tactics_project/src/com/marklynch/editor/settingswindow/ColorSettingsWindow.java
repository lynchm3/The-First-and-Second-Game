package com.marklynch.editor.settingswindow;

import mdesl.graphics.Color;

import com.marklynch.editor.AttributesWindow;
import com.marklynch.editor.Editor;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class ColorSettingsWindow extends SettingsWindow {

	public ColorSettingsWindow(float width, final Editor editor) {
		super(width, editor);
		updateColorsButtons();
	}

	public void updateColorsButtons() {
		buttons.clear();

		final SettingsWindowButton addColorButton = new SettingsWindowButton(0,
				100, 200, 30, "ADD COLOR", true, true, this) {

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

		addColorButton.clickListener = new ClickListener() {

			@Override
			public void click() {

				editor.colors.add(new Color(Color.RED));
				updateColorsButtons();
				editor.clearSelectedObject();
				editor.depressButtonsSettingsAndDetailsButtons();
			}
		};
		buttons.add(addColorButton);

		for (int i = 0; i < editor.colors.size(); i++) {
			final int index = i;

			final SettingsWindowButton collorButton = new SettingsWindowButton(
					0, 200 + i * 30, 200, 30, editor.colors.get(i), true, true,
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

			collorButton.clickListener = new ClickListener() {

				@Override
				public void click() {
					editor.clearSelectedObject();
					editor.depressButtonsSettingsAndDetailsButtons();
					collorButton.down = true;
					editor.attributesWindow = new AttributesWindow(200, 200,
							200, editor.colors.get(index), editor);

				}
			};
			buttons.add(collorButton);

		}

	}

	@Override
	public void update() {
		updateColorsButtons();
	}
}
