package com.marklynch.editor.settingswindow;

import java.util.ArrayList;

import com.marklynch.editor.AttributesDialog;
import com.marklynch.editor.Editor;
import com.marklynch.editor.InstanceSelectionWindow;
import com.marklynch.tactics.objects.weapons.WeaponTemplate;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class WeaponsSettingsWindow extends SettingsWindow {

	public WeaponsSettingsWindow(float width, final Editor editor) {
		super(width, editor);
		updateWeaponsButtons();
	}

	public void updateWeaponsButtons() {
		buttons.clear();

		final SettingsWindowButton addWeaponButton = new SettingsWindowButton(0, 100, 200, 30, "ADD WEAPON", true,
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

		addWeaponButton.clickListener = new ClickListener() {

			@Override
			public void click() {

				ArrayList<WeaponTemplate> weaponTemplates = editor.weaponTemplates;

				editor.instanceSelectionWindow = new InstanceSelectionWindow<WeaponTemplate>(weaponTemplates, editor,
						"Select a Weapon Template");

				// public AttributeSelectionWindow(final ArrayList<T> objects,
				// boolean multi, final Editor editor,
				// final Object ownerOfAttribute)
			}
		};
		buttons.add(addWeaponButton);

		for (int i = 0; i < editor.weapons.size(); i++) {
			final int index = i;

			final SettingsWindowButton weaponButton = new SettingsWindowButton(0, 200 + i * 30, 200, 30,
					editor.weapons.get(i), true, true) {

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
					editor.attributesWindow = new AttributesDialog(200, 200, 200, editor.weapons.get(index), editor);

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
