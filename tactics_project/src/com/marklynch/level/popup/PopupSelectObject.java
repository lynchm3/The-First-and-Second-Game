package com.marklynch.level.popup;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.ui.button.ClickListener;

public class PopupSelectObject extends Popup {

	public PopupSelectObject(float width, Level level, Square square) {

		super(width, level, square);

		selectSquareButton = new PopupButton(0, 0, 200, 30, null, null, "" + square, true, true, square, this);

		selectSquareButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				squareSelected(PopupSelectObject.this.square);
			}
		};
		updateObjectsButtons();
	}

	public void updateObjectsButtons() {

		buttons.clear();

		buttons.add(selectSquareButton);

		for (int i = 0; i < square.inventory.size(); i++) {
			final int index = i;

			// The line and the highlight are drawn in relation to zoom and
			// position...

			// BUT... I dont want the buttons to zoom :P

			final PopupButton objectButton = new PopupButton(0, 30 + i * 30, 200, 30, null, null,
					"" + square.inventory.get(index), true, true, square.inventory.get(index), this);

			objectButton.clickListener = new ClickListener() {

				@Override
				public void click() {

					gameObjectSelected(square.inventory.get(index));
					// editor.popupSelectObject = null; MAYYBE?

					// editor.clearSelectedObject();
					// editor.depressButtonsSettingsAndDetailsButtons();
					// editor.editor.editorState =
					// EDITOR_STATE.MOVEABLE_OBJECT_SELECTED;
					// editor.selectedGameObject =
					// Game.level.inanimateObjects.get(index);
					// editor.attributesWindow = new AttributesDialog(200, 200,
					// 200, editor.selectedGameObject, editor);
					// // getButton(editor.selectedGameObject).down = true;
					// objectButton.down = true;
				}
			};
			buttons.add(objectButton);

		}

		highlightedButton = buttons.get(highlightedButtonIndex);
		highlightedButton.highlighted = true;

	}

	public void gameObjectSelected(GameObject gameObject) {
		gameObject.getDefaultAction(Game.level.activeActor);
	}

	public void squareSelected(Square square) {
		// UserInputLevel.interactWithSquare(square);
		square.getDefaultActionForJustTheSquare(Game.level.activeActor);
	}
}
