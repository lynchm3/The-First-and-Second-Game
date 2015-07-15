package com.marklynch;

import org.lwjgl.input.Mouse;

import com.marklynch.config.Config;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.unit.Path;
import com.marklynch.ui.button.Button;

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

	public static void userInput(int delta2, Level level) {

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

		// Check if a script is hogging the screen and intercepting clicks
		boolean scriptInterceptsClick = false;
		if (level.script.checkIfBlocking()) {
			scriptInterceptsClick = true;
		}

		// Get the square that we're hovering over
		Game.squareMouseIsOver = null;
		if ((int) mouseXInSquares > -1
				&& (int) mouseXInSquares < level.squares.length
				&& (int) mouseYInSquares > -1
				&& (int) mouseYInSquares < level.squares[0].length) {
			Game.squareMouseIsOver = level.squares[(int) mouseXInSquares][(int) mouseYInSquares];
		}

		// Clear path highlights
		for (int i = 0; i < level.width; i++) {
			for (int j = 0; j < level.height; j++) {
				level.squares[i][j].inPath = false;
			}
		}

		// Getting button that we have clicked, if any
		Button buttonHoveringOver = null;
		if (dragging == false) {
			buttonHoveringOver = level.getButtonFromMousePosition(Mouse.getX(),
					Mouse.getY(), mouseXTransformed, mouseYTransformed);
		}

		// Path highlights
		if (scriptInterceptsClick == false && buttonHoveringOver == null
				&& level.activeActor != null && Game.squareMouseIsOver != null
				&& Game.squareMouseIsOver.reachableBySelectedCharater
				&& level.activeActor.faction == level.factions.get(0)
				&& level.currentFactionMoving == level.factions.get(0)) {
			path = level.activeActor.paths.get(Game.squareMouseIsOver);
			for (Square square : path.squares) {
				square.inPath = true;
			}
		}

		// If we've clicked... where are we putting it?
		if (scriptInterceptsClick && mouseButtonStateLeft == true
				&& !Mouse.isButtonDown(0) && dragging == false) {
			level.script.click();
		} else if (level.waitingForPlayerClick == true
				&& mouseButtonStateLeft == true && !Mouse.isButtonDown(0)
				&& dragging == false) {
			level.waitingForPlayerClick = false;
			level.showTurnNotification = false;
		} else if (mouseButtonStateLeft == true && !Mouse.isButtonDown(0)
				&& dragging == false && buttonHoveringOver != null
				&& level.currentFactionMovingIndex == 0) {
			// click button if we're on one
			buttonHoveringOver.click();

		} else if (mouseButtonStateLeft == true && !Mouse.isButtonDown(0)
				&& dragging == false && Game.squareMouseIsOver != null
				&& level.currentFactionMovingIndex == 0) {
			// click square if we're on one

			GameObject clickedGameObject = Game.squareMouseIsOver.gameObject;
			if (clickedGameObject != null) {
				boolean selectedNewActor = false;
				if (clickedGameObject instanceof Actor) {
					Actor clickedActor = (Actor) clickedGameObject;
					if (clickedActor.faction == level.currentFactionMoving) {
						if (level.activeActor != null) {
							level.activeActor.unselected();
						}
						level.activeActor = clickedActor;
						Actor.highlightSelectedCharactersSquares(level);
						selectedNewActor = true;
					}
				}

				if (level.activeActor != null
						&& selectedNewActor == false
						&& level.activeActor.equippedWeapon != null
						&& level.activeActor.equippedWeapon
								.hasRange(level.activeActor
										.weaponDistanceTo(Game.squareMouseIsOver))) {
					level.activeActor.attack(clickedGameObject, false);
					Actor.highlightSelectedCharactersSquares(level);
				}
			}

			// Check if we clicked on an empty reachable square and act
			// accordingly
			if (level.activeActor != null
					&& Game.squareMouseIsOver.reachableBySelectedCharater
					&& level.activeActor.faction == level.factions.get(0)
					&& level.currentFactionMoving == level.factions.get(0)
					&& level.activeActor.squareGameObjectIsOn != Game.squareMouseIsOver) {
				level.activeActor.moveTo(Game.squareMouseIsOver);
			}
		}

		if (!Mouse.isButtonDown(0)) {
			mouseButtonStateLeft = false;
			mouseDownX = -1;
			mouseDownY = -1;

			// Show/hide Hover preview
			if (Config.SHOW_BATTLE_PREVIEW_ON_HOVER
					&& Game.squareMouseIsOver != null
					&& Game.squareMouseIsOver.gameObject != null
					&& level.activeActor != null
					&& Game.squareMouseIsOver.gameObject != level.activeActor
					&& level.currentFactionMoving == level.factions.get(0)
					&& buttonHoveringOver == null) {
				// show hover preview
				level.activeActor
						.showHoverFightPreview(Game.squareMouseIsOver.gameObject);
			} else if (level.activeActor != null) {
				// hide Hover Preview
				level.activeActor.hideHoverFightPreview();
			}

		}

		if (mouseButtonStateRight == false && Mouse.isButtonDown(1)
				&& level.currentFactionMovingIndex == 0) {
			level.clearDialogs();
			// right click
			if (level.activeActor != null) {
				level.activeActor.unselected();
				level.activeActor = null;
			} else if (Game.squareMouseIsOver != null) {
				if (Game.squareMouseIsOver.showingDialogs == false)
					Game.squareMouseIsOver.showDialogs();
				else
					Game.squareMouseIsOver.clearDialogs();

				// level.dialogs.addElement(new Dialog(Mouse.getX(),
				// windowHeight-Mouse.getY(),64,64,"marlene.png"));
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

		// keep char on the screen
		// if (actorPositionX < 0)
		// actorPositionX = 0;
		// if (actorPositionX > 9)
		// actorPositionX = 9;
		// if (actorPositionY < 0)
		// actorPositionY = 0;
		// if (actorPositionY > 9)
		// actorPositionY = 9;

	}

}
