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

		// Width Button
		final SettingsWindowButton widthButton = new SettingsWindowButton(0,
				100, 200, 30, "Level Width: " + editor.level.width, true, true,
				this) {

			@Override
			public void keyTyped(char character) {
				if (48 <= character && character <= 57
						&& textEntered.length() < 2) {
					this.textEntered += character;
					this.text = "Level Width: " + textEntered;
				}

			}

			@Override
			public void enterTyped() {
				int newWidth = 0;
				if (this.textEntered.length() > 0)
					newWidth = Integer.valueOf(this.textEntered).intValue();
				editor.level.changeSize(newWidth, editor.level.height);
				editor.state = Editor.STATE.DEFAULT;
				this.down = false;
			}

			@Override
			public void backTyped() {
				if (textEntered.length() > 0) {
					this.textEntered = this.textEntered.substring(0,
							this.textEntered.length() - 1);
					this.text = "Level Width: " + textEntered;
				}
			}
		};
		widthButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				editor.state = Editor.STATE.SETTINGS_CHANGE;
				editor.settingsButton = widthButton;
				widthButton.textEntered = "";
				widthButton.down = true;
			}
		};
		buttons.add(widthButton);

		// Height Button
		final SettingsWindowButton heightButton = new SettingsWindowButton(0,
				130, 200, 30, "Level Height: " + editor.level.height, true,
				true, this) {

			@Override
			public void keyTyped(char character) {
				if (48 <= character && character <= 57
						&& textEntered.length() < 2) {
					this.textEntered += character;
				}
				this.text = "Level Height: " + textEntered;

			}

			@Override
			public void enterTyped() {
				int newHeight = 0;
				if (this.textEntered.length() > 0)
					newHeight = Integer.valueOf(this.textEntered).intValue();
				editor.level.changeSize(editor.level.width, newHeight);
				this.down = false;
			}

			@Override
			public void backTyped() {
				if (textEntered.length() > 0) {
					this.textEntered = this.textEntered.substring(0,
							this.textEntered.length() - 1);
					this.text = "Level Height: " + textEntered;
				}
			}
		};
		heightButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				editor.state = Editor.STATE.SETTINGS_CHANGE;
				editor.settingsButton = heightButton;
				heightButton.textEntered = "";
				heightButton.down = true;
			}
		};
		buttons.add(heightButton);
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
