package com.marklynch.editor.settingswindow;

import com.marklynch.Game;
import com.marklynch.editor.AttributesDialog;
import com.marklynch.editor.Editor;
import com.marklynch.level.constructs.Faction;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class FactionsSettingsWindow extends SettingsWindow {

	public FactionsSettingsWindow(float width, final Editor editor) {
		super(width, editor);
		updateFactionsButtons();
	}

	public void updateFactionsButtons() {
		buttons.clear();

		final SettingsWindowButton addFactionButton = new SettingsWindowButton(0, 100, 200, 30, "ADD FACTION", true,
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

		addFactionButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				if (Game.level.factions.size() == 10)
					return;

				Faction newFaction = new Faction("Faction " + Game.level.factions.size(), "faction_blue.png");
				Game.level.factions.add(newFaction);
				updateFactionsButtons();
				editor.clearSelectedObject();
				editor.depressButtonsSettingsAndDetailsButtons();

				for (Faction faction : Game.level.factions) {
					if (faction != newFaction) {
						faction.relationships.put(newFaction, -100);
						newFaction.relationships.put(faction, -100);
					}
				}

			}
		};
		buttons.add(addFactionButton);

		for (int i = 0; i < Game.level.factions.size(); i++) {
			final int index = i;

			final SettingsWindowButton factionButton = new SettingsWindowButton(0, 200 + i * 30, 200, 30,
					Game.level.factions.get(i), true, true) {

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
					factionButton.down = true;
					editor.attributesWindow = new AttributesDialog(200, 200, 200, Game.level.factions.get(index),
							editor);

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
