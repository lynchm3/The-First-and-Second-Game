package com.marklynch.editor;

import java.util.Vector;

import org.newdawn.slick.Color;

import com.marklynch.Game;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.SettingsWindowButton;
import com.marklynch.utils.QuadUtils;

public abstract class SettingsWindow {
	public float width;

	public Vector<SettingsWindowButton> buttons = new Vector<SettingsWindowButton>();

	public Editor editor;

	public final static String[] gameObjectFields = { "name", "strength",
			"dexterity", "intelligence", "endurance", "totalHealth",
			"remainingHealth" };

	public SettingsWindow(float width, final Editor editor) {
		super();
		this.width = width;
		this.editor = editor;
	}

	public void draw() {
		QuadUtils.drawQuad(Color.white, 0, width, 100, Game.windowHeight);
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

}
