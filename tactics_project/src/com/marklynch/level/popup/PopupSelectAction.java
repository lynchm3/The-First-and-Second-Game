package com.marklynch.level.popup;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.Actionable;
import com.marklynch.ui.button.ClickListener;

public class PopupSelectAction extends Popup {
	public PopupButton selectSquareButton;
	public Actionable object;

	public PopupSelectAction(float width, Level level, Square square, Actionable object) {

		super(width, level, square);
		this.object = object;
		updateObjectsButtons();
	}

	public void updateObjectsButtons() {

		buttons.clear();

		final ArrayList<Action> actions = object.getAllActions(Game.level.player);

		for (int i = 0; i < actions.size(); i++) {
			final int index = i;

			// The line and the highlight are drawn in relation to zoom and
			// position...

			// BUT... I dont want the buttons to zoom :P

			final PopupButton actionButton = new PopupButton(200, i * 30, 200, 30, null, null,
					actions.get(i).actionName, true, true, actions.get(i), this);
			actionButton.enabled = actions.get(index).enabled;

			actionButton.clickListener = new ClickListener() {

				@Override
				public void click() {
					if (actionButton.enabled) {
						actions.get(index).perform();
						Game.level.popups.clear();
					}
				}
			};
			buttons.add(actionButton);

		}

		highlightedButton = buttons.get(highlightedButtonIndex);
		highlightedButton.highlighted = true;

	}
}
