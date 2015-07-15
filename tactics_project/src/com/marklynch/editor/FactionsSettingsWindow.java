package com.marklynch.editor;

import org.newdawn.slick.Color;

import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class FactionsSettingsWindow extends SettingsWindow {

	public FactionsSettingsWindow(float width, final Editor editor) {
		super(width, editor);
		updateFactionsButtons();
	}

	public void updateFactionsButtons() {
		buttons.clear();

		final SettingsWindowButton addFactionButton = new SettingsWindowButton(
				0, 100, 200, 30, "ADD FACTION", true, true, this) {

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

		addFactionButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				if (editor.level.factions.size() == 10)
					return;

				editor.level.factions.add(new Faction("Faction "
						+ editor.level.factions.size(),
						FactionsSettingsWindow.this.editor.level, Color.blue,
						"faction_blue.png"));
				updateFactionsButtons();
				editor.clearSelectedObject();
				editor.depressButtonsSettingsAndDetailsButtons();
			}
		};
		buttons.add(addFactionButton);

		System.out.println("SSS");
		for (int i = 0; i < editor.level.factions.size(); i++) {
			System.out.println("RRRRRRRRR");
			final int index = i;

			final SettingsWindowButton factionButton = new SettingsWindowButton(
					0, 200 + i * 30, 200, 30,
					"" + editor.level.factions.get(i), true, true, this) {

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

			factionButton.clickListener = new ClickListener() {

				@Override
				public void click() {
					editor.clearSelectedObject();
					editor.depressButtonsSettingsAndDetailsButtons();
					editor.attributesWindow = new AttributesWindow(0, 200,
							editor.level.factions.get(index), editor);

				}
			};
			buttons.add(factionButton);

		}

	}

	@Override
	public void update() {
		updateFactionsButtons();

	}
}
