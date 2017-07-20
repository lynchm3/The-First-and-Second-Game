package com.marklynch.level.popup;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;

public class PopupSelectObject extends Popup {
	public PopupButton selectSquareButton;

	public PopupSelectObject(float width, Level level, Square square) {

		super(width, level, square);

		selectSquareButton = new PopupButton(0, 0, 200, 30, null, null, true, true, square, this, "" + square);

		selectSquareButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				for (Button button : buttons) {
					button.down = false;
				}
				highlightedButton.down = true;
				squareSelected(PopupSelectObject.this.square);
			}
		};
		updateObjectsButtons();
	}

	public void updateObjectsButtons() {

		buttons.clear();

		if (PopupSelectObject.this.square.getAllActionsPerformedOnThisInWorld(Game.level.activeActor).size() > 0)
			buttons.add(selectSquareButton);

		for (int i = 0; i < square.inventory.size(); i++) {
			final GameObject gameObject = square.inventory.get(i);

			// The line and the highlight are drawn in relation to zoom and
			// position...

			// BUT... I dont want the buttons to zoom :P

			if (gameObject.getAllActionsPerformedOnThisInWorld(Game.level.activeActor).size() > 0) {
				final PopupButton objectButton = new PopupButton(0, buttons.size() * 30, 200, 30, null, null,
						true, true, gameObject, this, "" + gameObject);

				objectButton.clickListener = new ClickListener() {

					@Override
					public void click() {

						for (Button button : buttons) {
							button.down = false;
						}
						highlightedButton.down = true;
						gameObjectSelected(gameObject);
					}
				};
				buttons.add(objectButton);
			}

		}

		if (buttons.size() > 0) {
			highlightedButton = buttons.get(highlightedButtonIndex);
			highlightedButton.highlight();
		}

	}

	public void gameObjectSelected(GameObject gameObject) {
		if (Game.level.popups.size() == 2) {
			int popupToRemoveIndex = 1;
			for (Button button : Game.level.popups.get(popupToRemoveIndex).buttons) {
				button.removeHighlight();
			}
			Game.level.popups.remove(popupToRemoveIndex);
		}
		Game.level.popups.add(new PopupSelectAction(200, 100, level, gameObject.squareGameObjectIsOn,
				gameObject.getAllActionsPerformedOnThisInWorld(Game.level.player)));
	}

	public void squareSelected(Square square) {
		if (Game.level.popups.size() == 2) {
			int popupToRemoveIndex = 1;
			for (Button button : Game.level.popups.get(popupToRemoveIndex).buttons) {
				button.removeHighlight();
			}
			Game.level.popups.remove(popupToRemoveIndex);
		}
		Game.level.popups.add(new PopupSelectAction(200, 100, level, square,
				square.getAllActionsPerformedOnThisInWorld(Game.level.player)));
	}
}
