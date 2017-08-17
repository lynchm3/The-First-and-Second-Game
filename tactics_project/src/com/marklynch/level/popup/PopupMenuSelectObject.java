package com.marklynch.level.popup;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;

public class PopupMenuSelectObject extends PopupMenu {
	public PopupMenuButton selectSquareButton;
	public boolean showSquare = true;

	public PopupMenuSelectObject(float width, Level level, Square square, boolean showSquare) {

		super(width, level, square);

		this.showSquare = showSquare;

		if (showSquare) {

			selectSquareButton = new PopupMenuButton(0, 0, 200, 30, null, null, true, true, square, this, "" + square);

			selectSquareButton.clickListener = new ClickListener() {

				@Override
				public void click() {
					for (Button button : buttons) {
						button.down = false;
					}
					highlightedButton.down = true;
					squareSelected(PopupMenuSelectObject.this.square);
				}
			};
		}
		updateObjectsButtons();
	}

	public void updateObjectsButtons() {

		buttons.clear();

		if (showSquare) {
			if (PopupMenuSelectObject.this.square.getAllActionsPerformedOnThisInWorld(Game.level.activeActor)
					.size() > 0)
				buttons.add(selectSquareButton);
		}

		for (int i = 0; i < square.inventory.size(); i++) {
			final GameObject gameObject = square.inventory.get(i);

			// The line and the highlight are drawn in relation to zoom and
			// position...

			// BUT... I dont want the buttons to zoom :P

			if (gameObject.getAllActionsPerformedOnThisInWorld(Game.level.activeActor).size() > 0) {
				final PopupMenuButton objectButton = new PopupMenuButton(0, buttons.size() * 30, 200, 30, null, null,
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
		if (Game.level.popupMenus.size() == 2) {
			int popupToRemoveIndex = 1;
			for (Button button : Game.level.popupMenus.get(popupToRemoveIndex).buttons) {
				button.removeHighlight();
			}
			Game.level.popupMenus.remove(popupToRemoveIndex);
		}
		Game.level.popupMenus.add(new PopupMenuSelectAction(100, 200, level, gameObject.squareGameObjectIsOn,
				gameObject.getAllActionsPerformedOnThisInWorld(Game.level.player)));
	}

	public void squareSelected(Square square) {
		if (Game.level.popupMenus.size() == 2) {
			int popupToRemoveIndex = 1;
			for (Button button : Game.level.popupMenus.get(popupToRemoveIndex).buttons) {
				button.removeHighlight();
			}
			Game.level.popupMenus.remove(popupToRemoveIndex);
		}
		Game.level.popupMenus.add(new PopupMenuSelectAction(100, 200, level, square,
				square.getAllActionsPerformedOnThisInWorld(Game.level.player)));
	}
}
