package com.marklynch.level;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIPath;
import com.marklynch.level.popup.Popup;
import com.marklynch.level.popup.PopupButton;
import com.marklynch.level.popup.PopupSelectAction;
import com.marklynch.level.popup.PopupSelectObject;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.InventorySquare;
import com.marklynch.objects.actions.Action;
import com.marklynch.ui.button.Button;

public class UserInputLevel {

	public static float mouseDownX = -1;
	public static float mouseDownY = -1;
	public static float mouseLastX = -1;
	public static float mouseLastY = -1;
	public static boolean dragging = false;
	public static boolean mouseButtonStateLeft = false;
	public static boolean mouseButtonStateRight = false;

	public static boolean capsLock = false;
	public static boolean keyStateUp = false;
	public static boolean keyStateDown = false;
	public static boolean keyStateLeft = false;
	public static boolean keyStateRight = false;
	public static boolean keyStateTab = false;
	public static boolean keyStateReturn = false;
	public static boolean keyStateBack = false;
	public static boolean keyStateLeftShift = false;
	public static boolean keyStateRightShift = false;
	public static boolean keyStateSpace = false;
	public static boolean keyStateA = false;
	public static boolean keyStateB = false;
	public static boolean keyStateC = false;
	public static boolean keyStateD = false;
	public static boolean keyStateE = false;
	public static boolean keyStateF = false;
	public static boolean keyStateG = false;
	public static boolean keyStateH = false;
	public static boolean keyStateI = false;
	public static boolean keyStateJ = false;
	public static boolean keyStateK = false;
	public static boolean keyStateL = false;
	public static boolean keyStateM = false;
	public static boolean keyStateN = false;
	public static boolean keyStateO = false;
	public static boolean keyStateP = false;
	public static boolean keyStateQ = false;
	public static boolean keyStateR = false;
	public static boolean keyStateS = false;
	public static boolean keyStateT = false;
	public static boolean keyStateU = false;
	public static boolean keyStateV = false;
	public static boolean keyStateW = false;
	public static boolean keyStateX = false;
	public static boolean keyStateY = false;
	public static boolean keyStateZ = false;
	public static boolean keyState1 = false;
	public static boolean keyState2 = false;
	public static boolean keyState3 = false;
	public static boolean keyState4 = false;
	public static boolean keyState5 = false;
	public static boolean keyState6 = false;
	public static boolean keyState7 = false;
	public static boolean keyState8 = false;
	public static boolean keyState9 = false;
	public static boolean keyState0 = false;
	public static boolean keyStateMinus = false;
	public static boolean keyStatePeriod = false;

	public static AIPath path;

	public static void userInput(int delta2) {

		// Getting what square pixel the mouse is on
		float mouseXinPixels = Mouse.getX();
		float mouseYinPixels = Mouse.getY();
		boolean inventoriesOpen = Game.level.openInventories.size() > 0;
		boolean mouseOverLog = Game.level.activityLogger.isMouseOver(Mouse.getX(), Mouse.getY());

		// Transformed mouse coords

		float mouseXTransformed = (((Game.windowWidth / 2) - Game.dragX - (Game.windowWidth / 2) / Game.zoom)
				+ (mouseXinPixels) / Game.zoom);
		float mouseYTransformed = ((Game.windowHeight / 2 - Game.dragY - (Game.windowHeight / 2) / Game.zoom)
				+ (((Game.windowHeight - mouseYinPixels)) / Game.zoom));

		// Getting what square coordinates the mouse is on (as in squares on the
		// grid)
		float mouseXInSquares = (int) (mouseXTransformed / Game.SQUARE_WIDTH);
		float mouseYInSquares = (int) (mouseYTransformed / Game.SQUARE_HEIGHT);

		// MOUSE WHEEL
		int wheel = Mouse.getDWheel();
		if (inventoriesOpen) {

		} else if (mouseOverLog) {
			if (wheel != 0) {
				Game.level.activityLogger.drag(-wheel);
			}
		} else {
			// Calculate zoom
			Game.zoom += 0.001 * wheel;
			if (Game.zoom < 0.01)
				Game.zoom = 0.01f;
			if (Game.zoom > 1)
				Game.zoom = 1f;
		}

		// DRAG
		if (Mouse.isButtonDown(0)) {
			if (mouseDownX == -1) {
				mouseDownX = Mouse.getX();
				mouseDownY = Mouse.getY();
				dragging = false;
			}
			mouseButtonStateLeft = true;

			if (Mouse.getX() - mouseDownX > 20 || Mouse.getX() - mouseDownX < -20 || Mouse.getY() - mouseDownY > 20
					|| Mouse.getY() - mouseDownY < -20) {
				dragging = true;

				if (inventoriesOpen) {

				} else if (mouseOverLog) {
					Game.level.activityLogger.drag(Mouse.getY() - mouseLastY);
				} else {
					Game.dragX += (Mouse.getX() - mouseLastX) / Game.zoom;
					Game.dragY -= (Mouse.getY() - mouseLastY) / Game.zoom;
				}
			}
		}

		// Check if a script is hogging the screen and intercepting clicks
		boolean scriptInterceptsClick = false;
		if (Game.level.script.checkIfBlocking()) {
			scriptInterceptsClick = true;
		}

		// Get the square that we're hovering over
		Game.squareMouseIsOver = null;
		if (Game.level.openInventories.size() > 0) {
			InventorySquare inventorySquareMouseIsOver = Game.level.openInventories.get(0)
					.getInventorySquareMouseIsOver(mouseXinPixels, mouseYinPixels);
			Game.squareMouseIsOver = inventorySquareMouseIsOver;
			Game.level.openInventories.get(0).setSquareMouseHoveringOver(inventorySquareMouseIsOver);
		} else if (mouseOverLog) {

		} else {
			if ((int) mouseXInSquares > -1 && (int) mouseXInSquares < Game.level.squares.length
					&& (int) mouseYInSquares > -1 && (int) mouseYInSquares < Game.level.squares[0].length) {
				Game.squareMouseIsOver = Game.level.squares[(int) mouseXInSquares][(int) mouseYInSquares];
			}
		}

		// Clear path highlights
		for (int i = 0; i < Game.level.width; i++) {
			for (int j = 0; j < Game.level.height; j++) {
				Game.level.squares[i][j].inPath = false;
			}
		}

		// Getting button that the mouse is over, if any
		Game.oldButtonHoveringOver = Game.buttonHoveringOver;
		Game.buttonHoveringOver = null;
		if (dragging == false) {
			Game.buttonHoveringOver = Game.level.getButtonFromMousePosition(Mouse.getX(), Mouse.getY(),
					mouseXTransformed, mouseYTransformed);
		}

		if (mouseLastX != Mouse.getX() || mouseLastY != Mouse.getY()) {
			if (Game.oldButtonHoveringOver != null) {
				Game.oldButtonHoveringOver.removeHighlight();

			}

			if (Game.level.popups.size() != 0) {
				Game.level.popups.get(Game.level.popups.size() - 1).highlightedButton.removeHighlight();
			}

			if (Game.buttonHoveringOver != null) {
				for (Popup popUp : Game.level.popups) {
					if (popUp.buttons.contains(Game.buttonHoveringOver)) {
						popUp.highlightedButton.removeHighlight();
						popUp.highlightedButton = Game.buttonHoveringOver;
						popUp.highlightedButtonIndex = popUp.buttons.indexOf(Game.buttonHoveringOver);
					}
				}

				// if (Game.buttonHoveringOver instanceof PopupActionButton)
				// ((PopupActionButton) Game.buttonHoveringOver).highlight();
				// else
				Game.buttonHoveringOver.highlight();

			}
		}

		// Getting inventory that the mouse is over, if any
		Game.inventoryHoveringOver = null;
		if (dragging == false) {
			Game.inventoryHoveringOver = Game.level.getInventoryFromMousePosition(Mouse.getX(), Mouse.getY());

		}

		boolean hoveringOverPopup = Game.buttonHoveringOver != null && Game.buttonHoveringOver instanceof PopupButton;

		// Path highlights
		// if (scriptInterceptsClick == false && Game.buttonHoveringOver == null
		// && Game.level.activeActor != null
		// && Game.squareMouseIsOver != null &&
		// Game.squareMouseIsOver.reachableBySelectedCharater
		// && Game.level.activeActor.faction == Game.level.factions.get(0)
		// && Game.level.currentFactionMoving == Game.level.factions.get(0)) {
		//// path = Game.level.activeActor.paths.get(Game.squareMouseIsOver);
		// for (Square square : path.squares) {
		// square.inPath = true;
		// }
		// }

		// Lifted the mouse to perform click
		if (mouseButtonStateLeft == true && !Mouse.isButtonDown(0) && dragging == false) {
			if (!hoveringOverPopup) {
				for (Popup popup : Game.level.popups) {
					for (Button button : popup.buttons) {
						button.removeHighlight();
					}
				}
				Game.level.popups.clear();
			}
			if (scriptInterceptsClick) {
				// Continue script
				Game.level.script.click();
			} else if (Game.buttonHoveringOver != null) {
				// Click button
				Game.buttonHoveringOver.click();
			} else if (Game.level.conversation != null) {

			} else if (Game.squareMouseIsOver != null && Game.level.currentFactionMovingIndex == 0) {
				if (Game.level.activeActor == Game.level.player) {
					if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
						interactWith(Game.squareMouseIsOver, -1, false, true);
					} else {
						interactWith(Game.squareMouseIsOver, -1, false, false);
					}
				}
			}
		}

		if (mouseButtonStateRight == true && !Mouse.isButtonDown(1) && dragging == false)

		{
			// Right Click
			if (Game.level.popups.isEmpty() && Game.squareMouseIsOver != null) {
				interactWith(Game.squareMouseIsOver, -1, true, false);
				// Game.level.popups.add(new PopupSelectObject(100, Game.level,
				// Game.squareMouseIsOver));
			} else {
				for (Popup popup : Game.level.popups) {
					for (Button button : popup.buttons) {
						button.removeHighlight();
					}
				}
				Game.level.popups.clear();
			}
		}

		if (!Mouse.isButtonDown(0)) {
			mouseButtonStateLeft = false;
			mouseDownX = -1;
			mouseDownY = -1;
		}

		if (Mouse.isButtonDown(1)) {
			mouseButtonStateRight = true;
		} else if (!Mouse.isButtonDown(1)) {
			mouseButtonStateRight = false;
		}

		if (!Mouse.isButtonDown(0)) {
			dragging = false;
		}

		if (Game.level.conversation != null) {

		} else {
			checkButtons();
		}

		mouseLastX = Mouse.getX();
		mouseLastY = Mouse.getY();
		// if (interactedThisTurn)
		// Game.level.endTurn();

		// interactedThisTurn = false;
	}

	// static boolean interactedThisTurn = false;

	public static void interactWith(Square square, int key, boolean openMenu, boolean secondary) {

		// if (interactedThisTurn)
		// return;

		// if (square == Game.level.activeActor.squareGameObjectIsOn)
		// return;

		if (Game.level.activeActor != Game.level.player)
			return;

		Action defaultAction = null;

		if (!openMenu) {
			if (secondary) {
				defaultAction = square.getSecondaryActionForTheSquareOrObject(Game.level.activeActor);
			} else {
				defaultAction = square.getDefaultActionForTheSquareOrObject(Game.level.activeActor);
			}
		}

		if (defaultAction != null) {
			defaultAction.perform();
			// if (!(action instanceof ActionRead) && !(action instanceof
			// ActionTalk))
			// interactedThisTurn = true;
			if (defaultAction.movement && defaultAction.enabled) {

				if (key == Keyboard.KEY_UP) {
					Game.dragY += Game.SQUARE_HEIGHT;
				} else if (key == Keyboard.KEY_DOWN) {
					Game.dragY -= Game.SQUARE_HEIGHT;

				} else if (key == Keyboard.KEY_LEFT) {
					Game.dragX += Game.SQUARE_WIDTH;

				} else if (key == Keyboard.KEY_RIGHT) {
					Game.dragX -= Game.SQUARE_WIDTH;

				}

			}
		} else {
			for (Popup popup : Game.level.popups) {
				for (Button button : popup.buttons) {
					button.removeHighlight();
				}
			}
			Game.level.popups.clear();
			if (square instanceof InventorySquare && ((InventorySquare) square).gameObject != null) {
				PopupSelectAction popupSelectAction = new PopupSelectAction(0, 200, Game.level, square,
						((InventorySquare) square).gameObject.getAllActionsInInventory(Game.level.player));
				if (popupSelectAction.buttons.size() > 0)
					Game.level.popups.add(popupSelectAction);
				// Game.level.popups.add(e);
			} else if (!(square instanceof InventorySquare)) {
				PopupSelectObject popupSelectObject = new PopupSelectObject(100, Game.level, square);
				if (popupSelectObject.buttons.size() > 0)
					Game.level.popups.add(popupSelectObject);
			}
		}

	}

	public static void closeAllPopups() {

		if (Game.level.popups.size() != 0) {

			int popupToRemoveIndex = Game.level.popups.size() - 1;
			for (Button button : Game.level.popups.get(popupToRemoveIndex).buttons) {
				button.removeHighlight();
			}
			Game.level.popups.remove(popupToRemoveIndex);

		}

	}

	public static void upTyped() {
		if (Game.level.activeActor != Game.level.player)
			return;
		if (Game.level.popups.size() != 0) {
			Game.level.popups.get(Game.level.popups.size() - 1).moveHighLightUp();
		} else {
			int y = Game.level.activeActor.squareGameObjectIsOn.yInGrid - 1;
			if (y >= 0) {

				boolean openMenu = false;
				boolean secondary = false;
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
					openMenu = true;
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
					secondary = true;
				}
				interactWith(Game.level.squares[Game.level.activeActor.squareGameObjectIsOn.xInGrid][y],
						Keyboard.KEY_UP, openMenu, secondary);
			}
		}
	}

	public static void downTyped() {
		if (Game.level.activeActor != Game.level.player)
			return;

		if (Game.level.popups.size() != 0) {
			Game.level.popups.get(Game.level.popups.size() - 1).moveHighLightDown();
		} else {
			int y = Game.level.activeActor.squareGameObjectIsOn.yInGrid + 1;
			if (y < Game.level.squares[0].length) {

				boolean openMenu = false;
				boolean secondary = false;
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
					openMenu = true;
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
					secondary = true;
				}
				interactWith(Game.level.squares[Game.level.activeActor.squareGameObjectIsOn.xInGrid][y],
						Keyboard.KEY_DOWN, openMenu, secondary);
			}
		}

	}

	public static void leftTyped() {
		if (Game.level.activeActor != Game.level.player)
			return;
		if (Game.level.popups.size() != 0) {

			int popupToRemoveIndex = Game.level.popups.size() - 1;
			for (Button button : Game.level.popups.get(popupToRemoveIndex).buttons) {
				button.removeHighlight();
			}
			Game.level.popups.remove(popupToRemoveIndex);

			if (Game.level.popups.size() != 0) {
				for (Button button : Game.level.popups.get(Game.level.popups.size() - 1).buttons) {
					button.down = false;
				}
			}

		} else {
			int x = Game.level.activeActor.squareGameObjectIsOn.xInGrid - 1;
			if (x >= 0) {

				boolean openMenu = false;
				boolean secondary = false;
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
					openMenu = true;
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
					secondary = true;
				}
				interactWith(Game.level.squares[x][Game.level.activeActor.squareGameObjectIsOn.yInGrid],
						Keyboard.KEY_LEFT, openMenu, secondary);
			}
		}

	}

	public static void rightTyped() {
		if (Game.level.activeActor != Game.level.player)
			return;
		if (Game.level.popups.size() != 0) {
			// Game.level.popups.get(Game.level.popups.size() - 1).high
			Game.level.popups.get(Game.level.popups.size() - 1).clickHighlightedButton();
		} else {

			int x = Game.level.activeActor.squareGameObjectIsOn.xInGrid + 1;
			if (x < Game.level.squares.length) {

				boolean openMenu = false;
				boolean secondary = false;
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
					openMenu = true;
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
					secondary = true;
				}

				interactWith(Game.level.squares[x][Game.level.activeActor.squareGameObjectIsOn.yInGrid],
						Keyboard.KEY_RIGHT, openMenu, secondary);
			}
		}

	}

	public static void enterTyped() {
		if (Game.level.activeActor != Game.level.player)
			return;
		if (Game.level.popups.size() != 0) {
			Game.level.popups.get(Game.level.popups.size() - 1).clickHighlightedButton();
		}
	}

	public static void backSpacedTyped() {
		if (Game.level.activeActor != Game.level.player)
			return;
		closeAllPopups();
	}

	public static void tabTyped() {
		if (Game.level.openInventories.size() > 0) {
			for (Inventory inventory : (ArrayList<Inventory>) Game.level.openInventories.clone()) {
				inventory.close();
			}
		} else {
			Game.level.player.inventory.open();
			Game.level.player.inventory.filter(Inventory.inventoryFilterBy, false);
			Game.level.player.inventory.sort(Inventory.inventorySortBy, false);
			Game.level.player.inventory.setMode(Inventory.INVENTORY_MODE.MODE_NORMAL);
			// Game.level.openInventories.add(Game.level.player.inventory);
		}
		closeAllPopups();
	}

	public static void keyTyped(char character) {
		if (Game.level.activeActor != Game.level.player)
			return;
		if (character == ' ') {

			if (Game.level.popups.size() != 0) {
				Game.level.popups.get(Game.level.popups.size() - 1).clickHighlightedButton();
			} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
				interactWith(Game.level.activeActor.squareGameObjectIsOn, Keyboard.KEY_SPACE, true, false);
			} else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				interactWith(Game.level.activeActor.squareGameObjectIsOn, Keyboard.KEY_SPACE, false, true);
			} else {
				interactWith(Game.level.activeActor.squareGameObjectIsOn, Keyboard.KEY_SPACE, false, false);
			}
		}

	}

	private static void checkButtons() {

		if (keyStateTab == false && Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
			tabTyped();
			keyStateTab = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
			keyStateTab = false;
		}

		if (keyStateUp == false && Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			upTyped();
			keyStateUp = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			keyStateUp = false;
		}

		if (keyStateDown == false && Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			downTyped();
			keyStateDown = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			keyStateDown = false;
		}

		if (keyStateLeft == false && Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			leftTyped();
			keyStateLeft = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			keyStateLeft = false;
		}

		if (keyStateRight == false && Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			rightTyped();
			keyStateRight = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			keyStateRight = false;
		}

		if (keyStateReturn == false && Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			enterTyped();
			keyStateReturn = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			keyStateReturn = false;
		}

		if (keyStateBack == false && Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
			backSpacedTyped();
			keyStateBack = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
			keyStateBack = false;
		}

		if (keyStateLeftShift == false && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			keyStateLeftShift = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			keyStateLeftShift = false;
		}

		if (keyStateRightShift == false && Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			keyStateRightShift = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			keyStateRightShift = false;
		}

		if (keyStateSpace == false && Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			keyTyped(' ');
			keyStateSpace = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			keyStateSpace = false;
		}

		if (keyStateA == false && Keyboard.isKeyDown(Keyboard.KEY_A)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('a');
				} else {
					keyTyped('A');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('A');
				} else {
					keyTyped('a');
				}
			}
			keyStateA = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_A)) {
			keyStateA = false;
		}

		if (keyStateB == false && Keyboard.isKeyDown(Keyboard.KEY_B)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('b');
				} else {
					keyTyped('B');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('B');
				} else {
					keyTyped('b');
				}
			}
			keyStateB = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_B)) {
			keyStateB = false;
		}

		if (keyStateC == false && Keyboard.isKeyDown(Keyboard.KEY_C)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('c');
				} else {
					keyTyped('C');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('C');
				} else {
					keyTyped('c');
				}
			}
			keyStateC = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_C)) {
			keyStateC = false;
		}

		if (keyStateD == false && Keyboard.isKeyDown(Keyboard.KEY_D)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('d');
				} else {
					keyTyped('D');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('D');
				} else {
					keyTyped('d');
				}
			}
			keyStateD = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_D)) {
			keyStateD = false;
		}

		if (keyStateE == false && Keyboard.isKeyDown(Keyboard.KEY_E)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('e');
				} else {
					keyTyped('E');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('E');
				} else {
					keyTyped('e');
				}
			}
			keyStateE = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_E)) {
			keyStateE = false;
		}

		if (keyStateF == false && Keyboard.isKeyDown(Keyboard.KEY_F)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('f');
				} else {
					keyTyped('F');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('F');
				} else {
					keyTyped('f');
				}
			}
			keyStateF = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_F)) {
			keyStateF = false;
		}

		if (keyStateG == false && Keyboard.isKeyDown(Keyboard.KEY_G)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('g');
				} else {
					keyTyped('G');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('G');
				} else {
					keyTyped('g');
				}
			}
			keyStateG = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_G)) {
			keyStateG = false;
		}

		if (keyStateH == false && Keyboard.isKeyDown(Keyboard.KEY_H)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('h');
				} else {
					keyTyped('H');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('H');
				} else {
					keyTyped('h');
				}
			}
			keyStateH = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_H)) {
			keyStateH = false;
		}

		if (keyStateI == false && Keyboard.isKeyDown(Keyboard.KEY_I)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('i');
				} else {
					keyTyped('I');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('I');
				} else {
					keyTyped('i');
				}
			}
			keyStateI = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_I)) {
			keyStateI = false;
		}

		if (keyStateJ == false && Keyboard.isKeyDown(Keyboard.KEY_J)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('j');
				} else {
					keyTyped('J');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('J');
				} else {
					keyTyped('j');
				}
			}
			keyStateJ = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_J)) {
			keyStateJ = false;
		}

		if (keyStateK == false && Keyboard.isKeyDown(Keyboard.KEY_K)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('k');
				} else {
					keyTyped('K');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('K');
				} else {
					keyTyped('k');
				}
			}
			keyStateK = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_K)) {
			keyStateK = false;
		}

		if (keyStateL == false && Keyboard.isKeyDown(Keyboard.KEY_L)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('l');
				} else {
					keyTyped('L');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('L');
				} else {
					keyTyped('l');
				}
			}
			keyStateL = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_L)) {
			keyStateL = false;
		}

		if (keyStateM == false && Keyboard.isKeyDown(Keyboard.KEY_M)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('m');
				} else {
					keyTyped('M');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('M');
				} else {
					keyTyped('m');
				}
			}
			keyStateM = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_M)) {
			keyStateM = false;
		}

		if (keyStateN == false && Keyboard.isKeyDown(Keyboard.KEY_N)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('n');
				} else {
					keyTyped('N');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('N');
				} else {
					keyTyped('n');
				}
			}
			keyStateN = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_N)) {
			keyStateN = false;
		}

		if (keyStateO == false && Keyboard.isKeyDown(Keyboard.KEY_O)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('o');
				} else {
					keyTyped('O');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('O');
				} else {
					keyTyped('o');
				}
			}
			keyStateO = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_O)) {
			keyStateO = false;
		}

		if (keyStateP == false && Keyboard.isKeyDown(Keyboard.KEY_P)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('p');
				} else {
					keyTyped('P');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('P');
				} else {
					keyTyped('p');
				}
			}
			keyStateP = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_P)) {
			keyStateP = false;
		}

		if (keyStateQ == false && Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('q');
				} else {
					keyTyped('Q');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('Q');
				} else {
					keyTyped('q');
				}
			}
			keyStateQ = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			keyStateQ = false;
		}

		if (keyStateR == false && Keyboard.isKeyDown(Keyboard.KEY_R)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('r');
				} else {
					keyTyped('R');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('R');
				} else {
					keyTyped('r');
				}
			}
			keyStateR = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_R)) {
			keyStateR = false;
		}

		if (keyStateS == false && Keyboard.isKeyDown(Keyboard.KEY_S)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('s');
				} else {
					keyTyped('S');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('S');
				} else {
					keyTyped('s');
				}
			}
			keyStateS = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_S)) {
			keyStateS = false;
		}

		if (keyStateT == false && Keyboard.isKeyDown(Keyboard.KEY_T)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('t');
				} else {
					keyTyped('T');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('T');
				} else {
					keyTyped('t');
				}
			}
			keyStateT = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_T)) {
			keyStateT = false;
		}

		if (keyStateU == false && Keyboard.isKeyDown(Keyboard.KEY_U)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('u');
				} else {
					keyTyped('U');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('U');
				} else {
					keyTyped('u');
				}
			}
			keyStateU = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_U)) {
			keyStateU = false;
		}

		if (keyStateV == false && Keyboard.isKeyDown(Keyboard.KEY_V)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('v');
				} else {
					keyTyped('V');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('V');
				} else {
					keyTyped('v');
				}
			}
			keyStateV = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_V)) {
			keyStateV = false;
		}

		if (keyStateW == false && Keyboard.isKeyDown(Keyboard.KEY_W)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('w');
				} else {
					keyTyped('W');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('W');
				} else {
					keyTyped('w');
				}
			}
			keyStateW = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_W)) {
			keyStateW = false;
		}

		if (keyStateX == false && Keyboard.isKeyDown(Keyboard.KEY_X)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('x');
				} else {
					keyTyped('X');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('X');
				} else {
					keyTyped('x');
				}
			}
			keyStateX = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_X)) {
			keyStateX = false;
		}

		if (keyStateY == false && Keyboard.isKeyDown(Keyboard.KEY_Y)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('y');
				} else {
					keyTyped('Y');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('Y');
				} else {
					keyTyped('y');
				}
			}
			keyStateY = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_Y)) {
			keyStateY = false;
		}

		if (keyStateZ == false && Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('z');
				} else {
					keyTyped('Z');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					keyTyped('Z');
				} else {
					keyTyped('z');
				}
			}
			keyStateZ = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			keyStateZ = false;
		}

		if (keyState1 == false && Keyboard.isKeyDown(Keyboard.KEY_1)) {
			keyTyped('1');
			keyState1 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_1)) {
			keyState1 = false;
		}

		if (keyState2 == false && Keyboard.isKeyDown(Keyboard.KEY_2)) {
			keyTyped('2');
			keyState2 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_2)) {
			keyState2 = false;
		}

		if (keyState3 == false && Keyboard.isKeyDown(Keyboard.KEY_3)) {
			keyTyped('3');
			keyState3 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_3)) {
			keyState3 = false;
		}

		if (keyState4 == false && Keyboard.isKeyDown(Keyboard.KEY_4)) {
			keyTyped('4');
			keyState4 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_4)) {
			keyState4 = false;
		}

		if (keyState5 == false && Keyboard.isKeyDown(Keyboard.KEY_5)) {
			keyTyped('5');
			keyState5 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_5)) {
			keyState5 = false;
		}

		if (keyState6 == false && Keyboard.isKeyDown(Keyboard.KEY_6)) {
			keyTyped('6');
			keyState6 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_6)) {
			keyState6 = false;
		}

		if (keyState7 == false && Keyboard.isKeyDown(Keyboard.KEY_7)) {
			keyTyped('7');
			keyState7 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_7)) {
			keyState7 = false;
		}

		if (keyState8 == false && Keyboard.isKeyDown(Keyboard.KEY_8)) {
			keyTyped('8');
			keyState8 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_8)) {
			keyState8 = false;
		}

		if (keyState9 == false && Keyboard.isKeyDown(Keyboard.KEY_9)) {
			keyTyped('9');
			keyState9 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_9)) {
			keyState9 = false;
		}

		if (keyState0 == false && Keyboard.isKeyDown(Keyboard.KEY_0)) {
			keyTyped('0');
			keyState0 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_0)) {
			keyState0 = false;
		}

		if (keyStateMinus == false && Keyboard.isKeyDown(Keyboard.KEY_MINUS)) {
			keyTyped('-');
			keyStateMinus = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_MINUS)) {
			keyStateMinus = false;
		}

		if (keyStatePeriod == false && Keyboard.isKeyDown(Keyboard.KEY_PERIOD)) {
			keyTyped('.');
			keyStatePeriod = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_PERIOD)) {
			keyStatePeriod = false;
		}

	}
}
