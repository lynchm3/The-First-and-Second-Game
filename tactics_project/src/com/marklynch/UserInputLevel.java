package com.marklynch;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.Path;
import com.marklynch.tactics.objects.unit.ai.utils.AIRoutineUtils;

public class UserInputLevel {

	public static float mouseDownX = -1;
	public static float mouseDownY = -1;
	public static float mouseLastX = -1;
	public static float mouseLastY = -1;
	public static boolean dragging = false;

	public static boolean keyStateLeft = false;
	public static boolean keyStateRight = false;
	public static boolean keyStateUp = false;
	public static boolean keyStateDown = false;
	public static boolean mouseButtonStateLeft = false;
	public static boolean mouseButtonStateRight = false;

	public static Path path;

	public static void userInput(int delta2) {

		// Getting what square pixel the mouse is on
		float mouseXinPixels = Mouse.getX();
		float mouseYinPixels = Mouse.getY();

		// Transformed mouse coords

		float mouseXTransformed = (((Game.windowWidth / 2) - Game.dragX - (Game.windowWidth / 2) / Game.zoom)
				+ (mouseXinPixels) / Game.zoom);
		float mouseYTransformed = ((Game.windowHeight / 2 - Game.dragY - (Game.windowHeight / 2) / Game.zoom)
				+ (((Game.windowHeight - mouseYinPixels)) / Game.zoom));

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

			if (Mouse.getX() - mouseDownX > 20 || Mouse.getX() - mouseDownX < -20 || Mouse.getY() - mouseDownY > 20
					|| Mouse.getY() - mouseDownY < -20) {
				dragging = true;
				Game.dragX += (Mouse.getX() - mouseLastX) / Game.zoom;
				Game.dragY -= (Mouse.getY() - mouseLastY) / Game.zoom;
			}
			mouseLastX = Mouse.getX();
			mouseLastY = Mouse.getY();
		}

		// Check if a script is hogging the screen and intercepting clicks
		boolean scriptInterceptsClick = false;
		if (Game.level.script.checkIfBlocking()) {
			scriptInterceptsClick = true;
		}

		// Get the square that we're hovering over
		Game.squareMouseIsOver = null;
		if ((int) mouseXInSquares > -1 && (int) mouseXInSquares < Game.level.squares.length
				&& (int) mouseYInSquares > -1 && (int) mouseYInSquares < Game.level.squares[0].length) {
			Game.squareMouseIsOver = Game.level.squares[(int) mouseXInSquares][(int) mouseYInSquares];
		}

		// Clear path highlights
		for (int i = 0; i < Game.level.width; i++) {
			for (int j = 0; j < Game.level.height; j++) {
				Game.level.squares[i][j].inPath = false;
			}
		}

		// Getting button that the mouse is over, if any
		Game.buttonHoveringOver = null;
		if (dragging == false) {
			Game.buttonHoveringOver = Game.level.getButtonFromMousePosition(Mouse.getX(), Mouse.getY(),
					mouseXTransformed, mouseYTransformed);
		}

		// Getting inventory that the mouse is over, if any
		Game.inventoryHoveringOver = null;
		if (dragging == false) {
			Game.inventoryHoveringOver = Game.level.getInventoryFromMousePosition(Mouse.getX(), Mouse.getY());

		}

		// Path highlights
		if (scriptInterceptsClick == false && Game.buttonHoveringOver == null && Game.level.activeActor != null
				&& Game.squareMouseIsOver != null && Game.squareMouseIsOver.reachableBySelectedCharater
				&& Game.level.activeActor.faction == Game.level.factions.get(0)
				&& Game.level.currentFactionMoving == Game.level.factions.get(0)) {
			path = Game.level.activeActor.paths.get(Game.squareMouseIsOver);
			for (Square square : path.squares) {
				square.inPath = true;
			}
		}

		boolean moved = false;
		boolean attacked = false;

		// Lifter the mouse to perform click
		if (mouseButtonStateLeft == true && !Mouse.isButtonDown(0) && dragging == false)
			if (scriptInterceptsClick) {
				// Continue script
				Game.level.script.click();
			} else if (Game.buttonHoveringOver != null && Game.level.currentFactionMovingIndex == 0) {
				// Click button
				Game.buttonHoveringOver.click();
			} else if (Game.squareMouseIsOver != null && Game.level.currentFactionMovingIndex == 0) {
				// Click square / Object / Actor
				ArrayList<GameObject> clickedGameObjects = null;
				if (Game.squareMouseIsOver.inventory.size() != 0)
					clickedGameObjects = Game.squareMouseIsOver.inventory.getGameObjects();

				GameObject clickedGameObject = null;
				if (clickedGameObjects != null && clickedGameObjects.size() == 1) {
					clickedGameObject = clickedGameObjects.get(0);
				}

				System.out.println("clickedGameObject = " + clickedGameObject);

				if (clickedGameObject != null) {
					if (Game.level.activeActor != null && Game.level.activeActor.equippedWeapon != null
							&& Game.level.activeActor.equippedWeapon
									.hasRange(Game.level.activeActor.straightLineDistanceTo(Game.squareMouseIsOver))) {
						Game.level.activeActor.attack(clickedGameObject, false);
						attacked = true;
					}
				}

				// Check if we clicked on an empty reachable square and act
				// accordingly
				if (Game.level.activeActor != null && Game.squareMouseIsOver.reachableBySelectedCharater
						&& Game.level.activeActor.faction == Game.level.factions.get(0)
						&& Game.level.currentFactionMoving == Game.level.factions.get(0)
						&& Game.level.activeActor.squareGameObjectIsOn != Game.squareMouseIsOver) {
					AIRoutineUtils.moveTo(Game.level.activeActor, Game.squareMouseIsOver);
					moved = true;
				}
			}

		if (!Mouse.isButtonDown(0)) {
			mouseButtonStateLeft = false;
			mouseDownX = -1;
			mouseDownY = -1;
		}

		if (mouseButtonStateRight == false && Mouse.isButtonDown(1) && Game.level.currentFactionMovingIndex == 0) {
			Game.level.clearDialogs();
			if (Game.squareMouseIsOver != null) {
				if (Game.squareMouseIsOver.showingDialogs == false)
					Game.squareMouseIsOver.showDialogs();
				else
					Game.squareMouseIsOver.clearDialogs();
			}
		}

		if (Mouse.isButtonDown(1)) {
			mouseButtonStateRight = true;
		} else if (!Mouse.isButtonDown(1)) {
			mouseButtonStateRight = false;
		}

		if (!Mouse.isButtonDown(0)) {
			dragging = false;
		}

		if (moved || attacked)
			Game.level.endTurn();
	}
}
