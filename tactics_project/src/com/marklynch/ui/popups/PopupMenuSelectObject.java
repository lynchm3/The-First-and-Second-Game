package com.marklynch.ui.popups;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Player;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.utils.ArrayList;

public class PopupMenuSelectObject extends PopupMenu {
	public PopupMenuButton selectSquareButton;
	public boolean showSquare = true;
	public boolean keyControl = true;
	public boolean onlySmallObjects = false;

	public PopupMenuSelectObject(float width, Level level, Square square, boolean showSquare, boolean keyControl,
			boolean onlySmallObjects) {

		super(width, level, square);

		this.showSquare = showSquare;
		this.keyControl = keyControl;
		this.onlySmallObjects = onlySmallObjects;

		if (showSquare) {

			selectSquareButton = new PopupMenuButton(0, 0, 128, 30, null, null, true, true, square, this, "" + square,
					null, true);

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
			if (PopupMenuSelectObject.this.square.getAllActionsPerformedOnThisInWorld(Level.player).size() > 0)
				buttons.add(selectSquareButton);
		}

		for (int i = 0; i < square.inventory.size(); i++) {
			final GameObject gameObject = square.inventory.get(i);

			// The line and the highlight are drawn in relation to zoom and
			// position...

			// BUT... I dont want the buttons to zoom :P
			if (onlySmallObjects && !gameObject.canBePickedUp)
				continue;

			if (gameObject.getAllActionsPerformedOnThisInWorld(Level.player).size() > 0) {
				final PopupMenuButton objectButton = new PopupMenuButton(0, buttons.size() * 30, 128, 30, null, null,
						true, true, gameObject, this, "" + gameObject, null, true);

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

		ArrayList<Action> actions = new ArrayList<Action>(Action.class);

		if (Level.player.equipped != null) {
			actions = Level.player.equipped.getAllActionsForEquippedItem(Level.player,
					PopupMenuSelectObject.this.square);
//			action = new ActionMove(Level.player, square, true);
		}

		for (Action action : actions) {
			final PopupMenuActionButton actionButton = new PopupMenuActionButton(0, buttons.size() * 30, 128, 30, null,
					null, action.actionName, true, true, action, this);
			actionButton.enabled = action.enabled;
			final Action finalAction = action;

			actionButton.clickListener = new ClickListener() {

				@Override
				public void click() {
					if (actionButton.enabled) {
						for (Button button : buttons) {
							button.down = false;
						}
						highlightedButton.down = true;
						Game.level.popupMenuObjects.clear();
						Game.level.popupMenuActions.clear();

						if (!(square instanceof InventorySquare) && !finalAction.checkRange()) {
							if (Game.level.settingFollowPlayer && Game.level.player.onScreen()) {
								Game.level.cameraFollow = true;
							}
							Player.playerTargetAction = finalAction;
							// Player.playerTargetSquare = square;
							Player.playerFirstMove = true;
							return;
						} else {
							finalAction.perform();
						}
					}
				}
			};
			buttons.add(actionButton);
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
