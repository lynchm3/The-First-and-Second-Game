package com.marklynch.editor;

import org.newdawn.slick.Color;

import com.marklynch.tactics.objects.level.Faction;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class FactionsSettingsWindow extends SettingsWindow {

	public FactionsSettingsWindow(float width, Editor editor) {
		super(width, editor);

		final SettingsWindowButton addActorButton = new SettingsWindowButton(0,
				100, 200, 30, "ADD FACTION", true, true, this) {

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

		addActorButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				FactionsSettingsWindow.this.editor.level.factions
						.add(new Faction(
								"Faction "
										+ FactionsSettingsWindow.this.editor.level.factions
												.size(),
								FactionsSettingsWindow.this.editor.level,
								Color.blue, "faction_blue.png"));
				FactionsSettingsWindow.this.editor.clearSelectedObject();
				FactionsSettingsWindow.this.editor
						.depressButtonsSettingsAndDetailsButtons();
			}
		};
		buttons.add(addActorButton);
	}
}
