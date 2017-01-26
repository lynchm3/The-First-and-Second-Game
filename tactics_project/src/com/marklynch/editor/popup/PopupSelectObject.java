package com.marklynch.editor.popup;

import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.editor.Editor;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.SettingsWindowButton;
import com.marklynch.utils.QuadUtils;

import mdesl.graphics.Color;

public class PopupSelectObject {

	public float width;
	public Vector<SettingsWindowButton> buttons = new Vector<SettingsWindowButton>();
	public Editor editor;
	public Square square;
	public SettingsWindowButton selectSquareButton;

	public PopupSelectObject(float width, Editor editor, Square square) {
		this.width = width;
		this.editor = editor;
		this.square = square;
		selectSquareButton = new SettingsWindowButton(0, 0, 200, 30, "" + square, true, true) {

			@Override
			public void keyTyped(char character) {
			}

			@Override
			public void enterTyped() {
			}

			@Override
			public void backTyped() {
			}

			@Override
			public void depress() {
				if (PopupSelectObject.this.editor.editorState == Editor.EDITOR_STATE.ADD_OBJECT)
					PopupSelectObject.this.editor.editorState = Editor.EDITOR_STATE.DEFAULT;
			}

		};

		selectSquareButton.clickListener = new ClickListener() {

			@Override
			public void click() {
				PopupSelectObject.this.editor.selectSquare(PopupSelectObject.this.square);
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

			final SettingsWindowButton objectButton = new SettingsWindowButton(0, 30 + i * 30, 200, 30,
					square.inventory.get(index), true, true) {

				@Override
				public void keyTyped(char character) {
				}

				@Override
				public void enterTyped() {
				}

				@Override
				public void backTyped() {
				}

				@Override
				public void depress() {
					squareSelected(square);
				}

			};

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

	}

	public void gameObjectSelected(GameObject gameObject) {
		editor.selectGameObject(gameObject);
	}

	public void squareSelected(Square square) {
		editor.selectSquare(square);
	}

	public void draw() {

		int squarePositionX = square.x * (int) Game.SQUARE_WIDTH;
		int squarePositionY = square.y * (int) Game.SQUARE_HEIGHT;

		float drawPositionX = (Game.windowWidth / 2)
				+ (Game.zoom * (squarePositionX - Game.windowWidth / 2 + Game.dragX));
		float drawPositionY = (Game.windowHeight / 2)
				+ (Game.zoom * (squarePositionY - Game.windowHeight / 2 + Game.dragY));

		QuadUtils.drawQuad(Color.PINK, drawPositionX, drawPositionX + width, drawPositionY, Game.windowHeight);
		for (Button button : buttons) {
			button.draw();
		}
	}

	public void depressButtons() {
		for (SettingsWindowButton button : buttons) {
			button.down = false;
			button.depress();
		}

	}

	public SettingsWindowButton getButton(Object object) {
		for (SettingsWindowButton button : buttons) {
			if (button.object == object)
				return button;
		}
		return null;
	}

	public void update() {
		updateObjectsButtons();
	}
}
