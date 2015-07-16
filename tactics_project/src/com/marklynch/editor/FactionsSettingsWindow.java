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

				Faction newFaction = new Faction("Faction "
						+ editor.level.factions.size(),
						FactionsSettingsWindow.this.editor.level, Color.red,
						"faction_blue.png");
				editor.level.factions.add(newFaction);
				updateFactionsButtons();
				editor.clearSelectedObject();
				editor.depressButtonsSettingsAndDetailsButtons();

				for (Faction faction : editor.level.factions) {
					if (faction != newFaction) {
						faction.relationships.put(newFaction, -100);
						newFaction.relationships.put(faction, -100);
					}
				}

			}
		};
		buttons.add(addFactionButton);

		for (int i = 0; i < editor.level.factions.size(); i++) {
			final int index = i;

			final SettingsWindowButton factionButton = new SettingsWindowButton(
					0, 200 + i * 30, 200, 30, editor.level.factions.get(i),
					true, true, this) {

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
					editor.attributesWindow = new AttributesWindow(200, 200,
							350, editor.level.factions.get(index), editor);

				}
			};
			buttons.add(factionButton);

		}

	}

	@Override
	public void update() {
		updateFactionsButtons();
		SettingsWindowButton button = this.getButton(editor.objectToEdit);
		if (button != null)
			button.down = true;

	}
}
