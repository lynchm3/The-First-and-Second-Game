package com.marklynch.editor.popup;

import java.util.ArrayList;

import com.marklynch.editor.Editor;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.PopupButton;

public class PopupSelectPlaceToPutObject extends Popup {

	public PopupSelectPlaceToPutObject(float width, final Editor editor, final Square square) {
		super(width, editor, square);
		boolean canBePlacedOnGround = editor.gameObjectTemplate.canShareSquare || square.inventory.canShareSquare();

		if (canBePlacedOnGround) {
			selectSquareButton = new PopupButton(0, 0, 200, 30, null, null, "" + square, true, true, square, this);

			selectSquareButton.clickListener = new ClickListener() {

				@Override
				public void click() {
					editor.placeObjectOnSquare(square);
				}
			};
		}
		updateObjectsButtons();
	}

	public void updateObjectsButtons() {

		buttons.clear();

		if (selectSquareButton != null)
			buttons.add(selectSquareButton);

		final ArrayList<GameObject> gameObjectsThatCanContainOtherObjects = square.inventory
				.getGameObjectsThatCanContainOtherObjects();

		for (int i = 0; i < gameObjectsThatCanContainOtherObjects.size(); i++) {
			final int index = i;

			// The line and the highlight are drawn in relation to zoom and
			// position...

			// BUT... I dont want the buttons to zoom :P

			final PopupButton objectButton = new PopupButton(0, 30 + i * 30, 200, 30, null, null,
					"" + gameObjectsThatCanContainOtherObjects.get(index), true, true,
					gameObjectsThatCanContainOtherObjects.get(index), this);

			objectButton.clickListener = new ClickListener() {

				@Override
				public void click() {

					editor.placeObjectInInventory(gameObjectsThatCanContainOtherObjects.get(index));
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

	}

	public void gameObjectSelected(GameObject gameObject) {
		editor.selectGameObject(gameObject);
	}

	public void squareSelected(Square square) {
		editor.selectSquare(square);
	}
}
