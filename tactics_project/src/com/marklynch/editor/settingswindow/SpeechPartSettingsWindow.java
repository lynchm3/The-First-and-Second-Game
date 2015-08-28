package com.marklynch.editor.settingswindow;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.editor.AttributesWindow;
import com.marklynch.editor.Editor;
import com.marklynch.tactics.objects.level.script.ScriptEventSpeech.SpeechPart;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class SpeechPartSettingsWindow extends SettingsWindow {
	SettingsWindowButton addSpeechPartButton;

	public SpeechPartSettingsWindow(float width, Editor editor) {
		super(width, editor);

		addSpeechPartButton = new SettingsWindowButton(0, 100, 200, 30,
				"ADD SPEECH PART", true, true, this) {

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
				if (SpeechPartSettingsWindow.this.editor.state == Editor.STATE.ADD_OBJECT)
					SpeechPartSettingsWindow.this.editor.state = Editor.STATE.DEFAULT;
			}

		};

		addSpeechPartButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				ArrayList<String> arrayList1 = new ArrayList<String>();
				arrayList1.add("TEXT IN SPEECH PART");
				SpeechPart newSpeechPart = new SpeechPart(
						Game.level.factions.get(0).actors.get(0), arrayList1);
				Game.level.script.speechParts.add(newSpeechPart);
				updateSpeechPartsButtons();
				SpeechPartSettingsWindow.this.editor.clearSelectedObject();
				SpeechPartSettingsWindow.this.editor
						.depressButtonsSettingsAndDetailsButtons();
			}
		};
		updateSpeechPartsButtons();
	}

	public void updateSpeechPartsButtons() {

		buttons.clear();

		buttons.add(addSpeechPartButton);
		System.out.println("Game.level.script.speechParts.size() = "
				+ Game.level.script.speechParts.size());

		for (int i = 0; i < Game.level.script.speechParts.size(); i++) {
			final int index = i;

			final SettingsWindowButton speechPartButton = new SettingsWindowButton(
					0, 200 + i * 30, 200, 30,
					Game.level.script.speechParts.get(index), true, true, this) {

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

			speechPartButton.clickListener = new ClickListener() {

				@Override
				public void click() {
					editor.clearSelectedObject();
					editor.depressButtonsSettingsAndDetailsButtons();
					speechPartButton.down = true;
					editor.attributesWindow = new AttributesWindow(200, 200,
							200, Game.level.script.speechParts.get(index),
							editor);
				}
			};
			buttons.add(speechPartButton);

		}

	}

	@Override
	public void update() {
		updateSpeechPartsButtons();
		SettingsWindowButton button = this.getButton(editor.objectToEdit);
		if (button != null)
			button.down = true;
	}
}
