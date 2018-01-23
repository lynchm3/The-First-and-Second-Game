package com.marklynch.editor.settingswindow;

import com.marklynch.editor.Editor;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;

public class DecorationsSettingsWindow extends SettingsWindow {
	SettingsWindowButton addDecorationButton;

	public DecorationsSettingsWindow(float width, Editor editor) {
		super(width, editor);

		addDecorationButton = new SettingsWindowButton(0, 100, 200, 30, "ADD DECORATION", true, true) {

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
				if (DecorationsSettingsWindow.this.editor.editorState == Editor.EDITOR_STATE.ADD_OBJECT)
					DecorationsSettingsWindow.this.editor.editorState = Editor.EDITOR_STATE.DEFAULT;
			}

		};

		addDecorationButton.clickListener = new ClickListener() {

			@Override
			public void click() {

			}
		};
		updateDecorationsButtons();
	}

	public void updateDecorationsButtons() {

	}

	@Override
	public void update() {
		updateDecorationsButtons();
	}
}
