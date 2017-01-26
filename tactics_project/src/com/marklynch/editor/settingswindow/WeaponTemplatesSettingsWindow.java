package com.marklynch.editor.settingswindow;

import com.marklynch.editor.AttributesDialog;
import com.marklynch.editor.Editor;
import com.marklynch.tactics.objects.weapons.WeaponTemplate;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class WeaponTemplatesSettingsWindow extends SettingsWindow {

	public WeaponTemplatesSettingsWindow(float width, final Editor editor) {
		super(width, editor);
		updateWeaponsButtons();
	}

	public void updateWeaponsButtons() {
		buttons.clear();

		final SettingsWindowButton addWeaponTemplateButton = new SettingsWindowButton(0, 100, 200, 30,
				"ADD WEAPON TEMPLATE", true, true) {

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

		addWeaponTemplateButton.clickListener = new ClickListener() {

			@Override
			public void click() {

				WeaponTemplate newWeapon = new WeaponTemplate("Weapon Template " + editor.weaponTemplates.size(), 3, 1,
						1, "a3r1.png", 100, null);
				editor.weaponTemplates.add(newWeapon);
				updateWeaponsButtons();
				editor.clearSelectedObject();
				editor.depressButtonsSettingsAndDetailsButtons();
			}
		};
		buttons.add(addWeaponTemplateButton);

		for (int i = 0; i < editor.weaponTemplates.size(); i++) {
			final int index = i;

			final SettingsWindowButton weaponButton = new SettingsWindowButton(0, 200 + i * 30, 200, 30,
					editor.weaponTemplates.get(i), true, true) {

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

			weaponButton.clickListener = new ClickListener() {

				@Override
				public void click() {
					editor.clearSelectedObject();
					editor.depressButtonsSettingsAndDetailsButtons();
					weaponButton.down = true;
					editor.attributesWindow = new AttributesDialog(200, 200, 200, editor.weaponTemplates.get(index),
							editor);

				}
			};
			buttons.add(weaponButton);

		}

	}

	@Override
	public void update() {
		updateWeaponsButtons();

	}
}
