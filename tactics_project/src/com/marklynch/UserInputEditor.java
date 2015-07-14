package com.marklynch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.marklynch.editor.Editor;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.Path;
import com.marklynch.ui.button.Button;

public class UserInputEditor {
	public static float mouseDownX = -1;
	public static float mouseDownY = -1;
	public static float mouseLastX = -1;
	public static float mouseLastY = -1;
	public static boolean dragging = false;

	public static boolean keyStateReturn = false;
	public static boolean keyStateQ = false;
	public static boolean keyStateRight = false;
	public static boolean keyStateUp = false;
	public static boolean keyStateDown = false;
	public static boolean mouseButtonStateLeft = false;
	public static boolean mouseButtonStateRight = false;

	public static Square squareMouseIsOver;
	public static Path path;

	public static void userInput(int delta2, Editor editor) {

		// Getting what square pixel the mouse is on
		float mouseXinPixels = Mouse.getX();
		float mouseYinPixels = Mouse.getY();

		// Transformed mouse coords

		float mouseXTransformed = (((Game.windowWidth / 2) - Game.dragX - (Game.windowWidth / 2)
				/ Game.zoom) + (mouseXinPixels) / Game.zoom);
		float mouseYTransformed = ((Game.windowHeight / 2 - Game.dragY - (Game.windowHeight / 2)
				/ Game.zoom) + (((Game.windowHeight - mouseYinPixels)) / Game.zoom));

		// Getting what square coordinates the mouse is on (as in squares on the
		// grid)
		float mouseXInSquares = (int) (mouseXTransformed / Game.SQUARE_WIDTH);
		float mouseYInSquares = (int) (mouseYTransformed / Game.SQUARE_HEIGHT);

		// Calculate zoom
		Game.zoom += 0.001 * Mouse.getDWheel();
		if (Game.zoom < 0.1)
			Game.zoom = 0.1f;
		if (Game.zoom > 2)
			Game.zoom = 2f;

		// Checking for drag
		if (Mouse.isButtonDown(0)) {
			if (mouseDownX == -1) {
				mouseDownX = Mouse.getX();
				mouseDownY = Mouse.getY();
				mouseLastX = Mouse.getX();
				mouseLastY = Mouse.getY();
				dragging = false;
			}
			mouseButtonStateLeft = true;

			if (Mouse.getX() - mouseDownX > 20
					|| Mouse.getX() - mouseDownX < -20
					|| Mouse.getY() - mouseDownY > 20
					|| Mouse.getY() - mouseDownY < -20) {
				dragging = true;
				Game.dragX += (Mouse.getX() - mouseLastX) / Game.zoom;
				Game.dragY -= (Mouse.getY() - mouseLastY) / Game.zoom;
			}
			mouseLastX = Mouse.getX();
			mouseLastY = Mouse.getY();
		}

		// Get the square that we're hovering over
		squareMouseIsOver = null;
		if ((int) mouseXInSquares > -1
				&& (int) mouseXInSquares < editor.level.squares.length
				&& (int) mouseYInSquares > -1
				&& (int) mouseYInSquares < editor.level.squares[0].length) {
			squareMouseIsOver = editor.level.squares[(int) mouseXInSquares][(int) mouseYInSquares];
		}

		// Getting button that we have clicked, if any
		Button buttonHoveringOver = null;
		if (dragging == false) {
			buttonHoveringOver = editor.getButtonFromMousePosition(
					Mouse.getX(), Mouse.getY());
		}

		// left click logic
		if (mouseButtonStateLeft == true && !Mouse.isButtonDown(0)
				&& dragging == false && buttonHoveringOver != null) {
			// click button
			buttonHoveringOver.click();
		} else if (mouseButtonStateLeft == true && !Mouse.isButtonDown(0)
				&& dragging == false && squareMouseIsOver != null) {
			// click square/game object if we're on one

			GameObject clickedGameObject = squareMouseIsOver.gameObject;
			if (clickedGameObject != null) {
				editor.gameObjectClicked(clickedGameObject);
			} else {
				editor.squareClicked(squareMouseIsOver);
			}
		}

		if (!Mouse.isButtonDown(0)) {
			mouseButtonStateLeft = false;
			mouseDownX = -1;
			mouseDownY = -1;
		}

		if (!Mouse.isButtonDown(0)) {
			dragging = false;
		}

		if (keyStateReturn == false && Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			editor.enterTyped();
			keyStateReturn = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			keyStateReturn = false;
		}

		if (keyStateQ == false && Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			editor.keyTyped('q');
			keyStateQ = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			keyStateQ = false;
		}

		if (keyStateUp == false && Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			// actorPositionY -= 1;
			keyStateUp = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			keyStateUp = false;
		}

		if (keyStateDown == false && Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			// actorPositionY += 1;
			keyStateDown = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			keyStateDown = false;
		}

	}
}
