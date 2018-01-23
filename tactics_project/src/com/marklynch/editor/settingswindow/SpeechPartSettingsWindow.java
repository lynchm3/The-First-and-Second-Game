package com.marklynch.editor.settingswindow;

import java.util.ArrayList;

import com.marklynch.Game;
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
				SpeechPart newSpeechPart = new SpeechPart(Game.level.player, arrayList1);
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

	}

	@Override
	public void update() {
		updateSpeechPartsButtons();
	}
}
