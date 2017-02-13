package com.marklynch.editor.settingswindow;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.editor.AttributesDialog;
import com.marklynch.editor.Editor;
import com.marklynch.script.ScriptEventSpeech.SpeechPart;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class SpeechPartSettingsWindow extends SettingsWindow {
	SettingsWindowButton addSpeechPartButton;

	public SpeechPartSettingsWindow(float width, Editor editor) {
		super(width, editor);

		addSpeechPartButton = new SettingsWindowButton(0, 100, 200, 30, "ADD SPEECH PART", true, true) {

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
				if (SpeechPartSettingsWindow.this.editor.editorState == Editor.EDITOR_STATE.ADD_OBJECT)
					SpeechPartSettingsWindow.this.editor.editorState = Editor.EDITOR_STATE.DEFAULT;
			}

		};

		addSpeechPartButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				ArrayList<String> arrayList1 = new ArrayList<String>();
				arrayList1.add("TEXT IN SPEECH PART");
				SpeechPart newSpeechPart = new SpeechPart(Game.level.factions.get(0).actors.get(0), arrayList1);
				Game.level.script.speechParts.add(newSpeechPart);
				updateSpeechPartsButtons();
				SpeechPartSettingsWindow.this.editor.clearSelectedObject();
				SpeechPartSettingsWindow.this.editor.depressButtonsSettingsAndDetailsButtons();
			}
		};
		updateSpeechPartsButtons();
	}

	public void updateSpeechPartsButtons() {

		buttons.clear();

		buttons.add(addSpeechPartButton);

		for (int i = 0; i < Game.level.script.speechParts.size(); i++) {
			final int index = i;

			final SettingsWindowButton speechPartButton = new SettingsWindowButton(0, 200 + i * 30, 200, 30,
					Game.level.script.speechParts.get(index), true, true) {

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
					editor.attributesWindow = new AttributesDialog(200, 200, 200,
							Game.level.script.speechParts.get(index), editor);
				}
			};
			if (speechPartButton.object == editor.objectToEdit)
				speechPartButton.down = true;

			buttons.add(speechPartButton);

			// if (editor.objectToEdit ==
			// Game.level.script.speechParts.get(index)) {
			// speechPartButton.click();
			// }

		}

	}

	@Override
	public void update() {
		updateSpeechPartsButtons();
	}
}
