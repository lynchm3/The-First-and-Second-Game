package com.marklynch.editor;

import java.util.Vector;

import org.newdawn.slick.Color;

import com.marklynch.Game;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;
import com.marklynch.utils.QuadUtils;

public class SettingsWindow {
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

		SettingsWindowButton widthButton = new SettingsWindowButton(0, 100,
				200, 30, "Level Width", true, true, this);
		widthButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				editor.level.changeSize(20, editor.level.height);

			}
		};
		buttons.add(widthButton);
	}

	public void draw() {
		QuadUtils.drawQuad(Color.white, 0, width, 100, Game.windowHeight);
		for (Button button : buttons) {
			button.draw();
		}
	}

	public void depressButtons() {
		for (Button button : buttons) {
			button.down = false;
		}

	}

}
