package com.marklynch.editor;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;

public class Editor {
	public ArrayList<Button> buttons = new ArrayList<Button>();
	Button testButton;

	public Editor() {
		testButton = new LevelButton(50, 50, 50, 50, "", "", "TEST", true, true);
		testButton.setClickListener(new ClickListener() {

			@Override
			public void click() {
				System.out.println("TEEEEEEEEST");
			}
		});
		buttons.add(testButton);
	}

	public void update(int delta) {

	}

	public void draw() {

		for (Button button : buttons) {
			button.draw();
		}

	}

	public Button getButtonFromMousePosition(float mouseX, float mouseY) {
		for (Button button : this.buttons) {
			if (button.calculateIfPointInBoundsOfButton(mouseX,
					Game.windowHeight - mouseY))
				return button;
		}

		return null;
	}

}
