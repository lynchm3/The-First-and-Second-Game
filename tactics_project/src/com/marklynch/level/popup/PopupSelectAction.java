package com.marklynch.level.popup;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;

public class PopupSelectAction extends Popup {
	public PopupButton selectSquareButton;
	public ArrayList<Action> actions;
	public float offsetX;

	public PopupSelectAction(float offsetX, float width, Level level, Square square, ArrayList<Action> actions) {

		super(width, level, square);
		this.actions = actions;
		this.offsetX = offsetX;
		updateActionsButtons();
	}

	public void updateActionsButtons() {

		buttons.clear();

		for (int i = 0; i < actions.size(); i++) {
			final int index = i;

			final PopupActionButton actionButton = new PopupActionButton(offsetX, buttons.size() * 30, 200, 30, null,
					null, actions.get(i).actionName, true, true, actions.get(i), this);
			actionButton.enabled = actions.get(index).enabled;

			actionButton.clickListener = new ClickListener() {

				@Override
				public void click() {
					if (actionButton.enabled) {
						for (Button button : buttons) {
							button.down = false;
						}
						highlightedButton.down = true;
						actions.get(index).perform();
						for (Popup popup : Game.level.popups) {
							for (Button button : popup.buttons) {
								button.removeHighlight();
							}
						}
						Game.level.popups.clear();
					}
				}
			};
			buttons.add(actionButton);

		}

		if (buttons.size() > 0) {
			highlightedButton = buttons.get(highlightedButtonIndex);
			highlightedButton.highlight();
		}

	}

}
