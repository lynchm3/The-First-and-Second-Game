package com.marklynch.editor.settingswindow;

import java.util.Vector;

import mdesl.graphics.Color;

import com.marklynch.Game;
import com.marklynch.editor.Editor;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.SettingsWindowButton;
import com.marklynch.utils.QuadUtils;

public abstract class SettingsWindow {
	public float width;
	public Vector<SettingsWindowButton> buttons = new Vector<SettingsWindowButton>();
	public Editor editor;

	public final static String[] gameObjectFields = { "name", "strength", "dexterity", "intelligence", "endurance",
			"totalHealth", "remainingHealth" };

	public SettingsWindow(float width, final Editor editor) {
		super();
		this.width = width;
		this.editor = editor;
	}

	public void draw() {
		QuadUtils.drawQuad(Color.WHITE, 0, 100, width, Game.windowHeight);
		for (Button button : buttons) {
			button.draw();
		}
	}

	public void depressButtons() {
		for (SettingsWindowButton button : buttons) {
			button.down = false;
			button.depress();
		}

	}

	public SettingsWindowButton getButton(Object object) {
		for (SettingsWindowButton button : buttons) {
			if (button.object == object)
				return button;
		}
		return null;
	}

	public abstract void update();

}
