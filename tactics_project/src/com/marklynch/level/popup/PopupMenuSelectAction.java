package com.marklynch.level.popup;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;

public class PopupMenuSelectAction extends PopupMenu {
	public PopupMenuButton selectSquareButton;
	public ArrayList<Action> actions;
	public float offsetX;
	public float height = 40f;

	public PopupMenuSelectAction(float offsetX, float width, Level level, Square square, ArrayList<Action> actions) {

		super(width, level, square);
		this.actions = actions;
		this.offsetX = offsetX;
		updateActionsButtons();
	}

	public void updateActionsButtons() {

		buttons.clear();

		for (int i = 0; i < actions.size(); i++) {
			final int index = i;

			final PopupMenuActionButton actionButton = new PopupMenuActionButton(offsetX, buttons.size() * height - 10,
					width, height, null, null, actions.get(i).actionName, true, true, actions.get(i), this);
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
						Game.level.popupMenuObjects.clear();
						Game.level.popupMenuActions.clear();
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
