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
	public boolean keyControl = true;

	public PopupMenuSelectObject(float width, Level level, Square square, boolean showSquare, boolean keyControl) {

		super(width, level, square);

		this.showSquare = showSquare;
		this.keyControl = keyControl;

		if (showSquare) {

			selectSquareButton = new PopupMenuButton(0, 0, 128, 30, null, null, true, true, square, this, "" + square);

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
				final PopupMenuButton objectButton = new PopupMenuButton(0, buttons.size() * 30, 128, 30, null, null,
						true, true, gameObject, this, "" + gameObject);

				objectButton.clickListener = new ClickListener() {

					@Override
					public void click() {

						for (Button button : buttons) {
							button.down = false;
						}
						objectButton.down = true;
						gameObjectSelected(gameObject);
					}
				};
				buttons.add(objectButton);
			}

		}

		if (keyControl && buttons.size() > 0) {
			highlightedButton = buttons.get(highlightedButtonIndex);
			highlightedButton.highlight();
		}

	}

	public void gameObjectSelected(GameObject gameObject) {

		Game.level.popupMenuObjects.clear();
		Game.level.popupMenuObjects.add(this);

		Game.level.popupMenuActions.clear();
		Game.level.popupMenuActions.add(new PopupMenuSelectAction(128, 128, level, gameObject.squareGameObjectIsOn,
				gameObject.getAllActionsPerformedOnThisInWorld(Game.level.player)));
	}

	public void squareSelected(Square square) {
		Game.level.popupMenuObjects.clear();
		Game.level.popupMenuObjects.add(this);
		Game.level.popupMenuActions.clear();
		Game.level.popupMenuActions.add(new PopupMenuSelectAction(128, 128, level, square,
				square.getAllActionsPerformedOnThisInWorld(Game.level.player)));
	}
}
