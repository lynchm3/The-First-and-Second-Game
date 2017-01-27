package com.marklynch.editor.settingswindow;

import com.marklynch.editor.AttributesDialog;
import com.marklynch.editor.Editor;
import com.marklynch.tactics.objects.weapons.WeaponTemplate;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class TemplatesSettingsWindow extends SettingsWindow {

	public enum TEMPLATE_FILTER {
		ALL, ACTOR, GAME_OBJECT, WEAPON
	}

	public TemplatesSettingsWindow(float width, final Editor editor) {
		super(width, editor);
		updateButtons();
	}

	public void updateButtons() {
		buttons.clear();

		final SettingsWindowButton addTemplateButton = new SettingsWindowButton(0, 100, 200, 30, "ADD TEMPLATE", true,
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

		addTemplateButton.clickListener = new ClickListener() {

			@Override
			public void click() {

				WeaponTemplate newWeapon = new WeaponTemplate("Weapon Template " + editor.gameObjectTemplates.size(), 3,
						1, 1, "a3r1.png", 100, null, true, false);
				editor.gameObjectTemplates.add(newWeapon);
				updateButtons();
				editor.clearSelectedObject();
				editor.depressButtonsSettingsAndDetailsButtons();
			}
		};
		buttons.add(addTemplateButton);

		for (int i = 0; i < editor.gameObjectTemplates.size(); i++) {
			final int index = i;

			final SettingsWindowButton templateButton = new SettingsWindowButton(0, 200 + i * 30, 200, 30,
					editor.gameObjectTemplates.get(i), true, true) {

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

			templateButton.clickListener = new ClickListener() {

				@Override
				public void click() {
					editor.clearSelectedObject();
					editor.depressButtonsSettingsAndDetailsButtons();
					templateButton.down = true;
					editor.attributesWindow = new AttributesDialog(200, 200, 200, editor.gameObjectTemplates.get(index),
							editor);

				}
			};
			buttons.add(templateButton);

		}

	}

	@Override
	public void update() {
		updateButtons();

	}
}
