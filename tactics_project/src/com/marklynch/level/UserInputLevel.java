package com.marklynch.level;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIPath;
import com.marklynch.level.Level.LevelMode;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionTeleport;
import com.marklynch.objects.actions.ActionUsePower;
import com.marklynch.objects.units.Player;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.PinWindow;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.popups.PopupMenu;
import com.marklynch.ui.popups.PopupMenuButton;
import com.marklynch.ui.popups.PopupMenuSelectAction;
import com.marklynch.ui.popups.PopupMenuSelectObject;

public class UserInputLevel {

	public static float mouseDownX = -1;
	public static float mouseDownY = -1;
	public static float mouseLastX = -1;
	public static float mouseLastY = -1;
	public static boolean draggingMap = false;
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
	public static boolean keyStateDelete = false;
	public static boolean keyStateLeftShift = false;
	public static boolean keyStateRightShift = false;
	public static boolean keyStateEscape = false;
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
	static boolean controllingMenu = false;

	public static Draggable draggableMouseIsOver = null;
	public static Scrollable scrollableMouseIsOver = null;

	public static void userInput(int delta2) {

		// Getting what square pixel the mouse is on
		float mouseXinPixels = Mouse.getX();
		float mouseYinPixels = Mouse.getY();

		// Setting the draggable

		setDraggableAndScrollableMouseIsOver();

		// Transformed mouse coords

		float mouseXTransformed = (((Game.windowWidth / 2) - Game.getDragXWithOffset()
				- (Game.windowWidth / 2) / Game.zoom) + (mouseXinPixels) / Game.zoom);
		float mouseYTransformed = ((Game.windowHeight / 2 - Game.getDragYWithOffset()
				- (Game.windowHeight / 2) / Game.zoom) + (((Game.windowHeight - mouseYinPixels)) / Game.zoom));

		// Getting what square coordinates the mouse is on (as in squares on the
		// grid)
		float mouseXInSquares = (int) (mouseXTransformed / Game.SQUARE_WIDTH);
		float mouseYInSquares = (int) (mouseYTransformed / Game.SQUARE_HEIGHT);

		// MOUSE WHEEL
		int mouseWheelDelta = Mouse.getDWheel();

		if (UserInputLevel.scrollableMouseIsOver != null) {
			if (mouseWheelDelta > 0)
				scrollableMouseIsOver.scroll(0, -100);
			else if (mouseWheelDelta < 0)
				scrollableMouseIsOver.scroll(0, 100);
			// if (mouseWheelDelta != 0) {
			// Game.level.activityLogger.drag(-mouseWheelDelta);
			// }
		} else {
			// Calculate zoom
			if (mouseWheelDelta > 0)
				Game.zoomLevelIndex--;
			else if (mouseWheelDelta < 0)
				Game.zoomLevelIndex++;

			// halfWindowWidth = Display.getWidth() / 2;
			// halfWindowHeight

			// Game.zoom += 0.001 * mouseWheelDelta;
			if (Game.zoomLevelIndex < 0)
				Game.zoomLevelIndex = 0;
			else if (Game.zoomLevelIndex >= Game.zoomLevels.length)
				Game.zoomLevelIndex = Game.zoomLevels.length - 1;

			// Change in zoom
			if (Game.lastZoomLevelIndex != Game.zoomLevelIndex) {
				float oldZoom = Game.zoom;
				Game.zoom = Game.zoomLevels[Game.zoomLevelIndex];
				Game.dragX += ((Mouse.getX() - Game.halfWindowWidth) / Game.zoom)
						- ((Mouse.getX() - Game.halfWindowWidth) / oldZoom);
				Game.dragY -= ((Mouse.getY() - Game.halfWindowHeight) / Game.zoom)
						- ((Mouse.getY() - Game.halfWindowHeight) / oldZoom);
				Game.lastZoomLevelIndex = Game.zoomLevelIndex;
			}

		}

		// DRAG
		if (Mouse.isButtonDown(0)) {
			if (mouseDownX == -1) {
				mouseDownX = Mouse.getX();
				mouseDownY = Mouse.getY();
				draggingMap = false;
			}
			mouseButtonStateLeft = true;

			if (Mouse.getX() - mouseDownX > 20 || Mouse.getX() - mouseDownX < -20 || Mouse.getY() - mouseDownY > 20
					|| Mouse.getY() - mouseDownY < -20) {
				draggingMap = true;
				if (Game.level.cameraFollow) {
					Game.dragX = Game.getDragXWithOffset();
					Game.dragY = Game.getDragYWithOffset();
				}
				Game.level.cameraFollow = false;
				Game.level.centerToSquare = false;

				if (draggableMouseIsOver != null) {

					if (draggableMouseIsOver instanceof PinWindow) {
						((PinWindow) draggableMouseIsOver).bringToFront();
					}

					draggableMouseIsOver.drag(Mouse.getX() - mouseLastX, Mouse.getY() - mouseLastY);
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
		if (Level.journal.showing) {
		} else if (Game.level.openInventories.size() > 0) {
			InventorySquare inventorySquareMouseIsOver = Game.level.openInventories.get(0)
					.getInventorySquareMouseIsOver(mouseXinPixels, mouseYinPixels);
			Game.squareMouseIsOver = inventorySquareMouseIsOver;
			Game.level.openInventories.get(0).setSquareMouseHoveringOver(inventorySquareMouseIsOver);
		} else if (Game.level.fullScreenTextBox != null) {

		} else if (Game.level.conversation != null) {

		} else if (draggableMouseIsOver != null) {

		} else {
			if ((int) mouseXInSquares > -1 && (int) mouseXInSquares < Game.level.squares.length
					&& (int) mouseYInSquares > -1 && (int) mouseYInSquares < Game.level.squares[0].length) {
				Game.squareMouseIsOver = Game.level.squares[(int) mouseXInSquares][(int) mouseYInSquares];

				GameObject gameObjectMouseIsOver;
				for (int i = Game.squareMouseIsOver.inventory.size() - 1; i >= 0; i--) {
					if (Game.squareMouseIsOver.inventory.get(i).mouseOver(mouseXTransformed, mouseYTransformed)) {

					}
				}
			}
		}

		if (Game.highlightPath) {
			// remove path highlight
			if (Game.level.player.playerPathToDraw != null) {
				for (Square square : Game.level.player.playerPathToDraw.squares) {
					square.highlight = false;
				}
			}

			// Add path highlight
			if (Game.squareMouseIsOver != null) {
				Game.level.player.playerPathToDraw = Game.level.player.getPathTo(Game.squareMouseIsOver);
				if (Game.level.player.playerPathToDraw != null) {
					for (Square square : Game.level.player.playerPathToDraw.squares) {
						square.highlight = true;
					}
				}
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
		Game.windowHoveringOver = null;
		Game.fullScreenTextBoxHoveringOver = null;
		if (draggingMap == false) {
			Game.buttonHoveringOver = Game.level.getButtonFromMousePosition(Mouse.getX(), Mouse.getY(),
					mouseXTransformed, mouseYTransformed);
			Game.windowHoveringOver = Game.level.getWindowFromMousePosition(Mouse.getX(), Mouse.getY(),
					mouseXTransformed, mouseYTransformed);
			Game.fullScreenTextBoxHoveringOver = Game.level.getPopupTextBoxFromMousePosition(Mouse.getX(), Mouse.getY(),
					mouseXTransformed, mouseYTransformed);
		}

		if (mouseLastX != Mouse.getX() || mouseLastY != Mouse.getY()) {
			if (Game.oldButtonHoveringOver != null) {
				Game.oldButtonHoveringOver.removeHighlight();

			}

			for (PopupMenu popupMenu : Game.level.popupMenuObjects) {
				popupMenu.clearHighlights();
			}

			for (PopupMenu popupMenu : Game.level.popupMenuActions) {
				popupMenu.clearHighlights();
			}

			if (Game.buttonHoveringOver != null) {
				for (PopupMenu popUp : Game.level.popupMenuActions) {
					if (popUp.buttons.contains(Game.buttonHoveringOver)) {
						popUp.highlightedButton = Game.buttonHoveringOver;
						popUp.highlightedButtonIndex = popUp.buttons.indexOf(Game.buttonHoveringOver);
					}
				}
				for (PopupMenu popUp : Game.level.popupMenuObjects) {
					if (popUp.buttons.contains(Game.buttonHoveringOver)) {
						popUp.highlightedButton = Game.buttonHoveringOver;
						popUp.highlightedButtonIndex = popUp.buttons.indexOf(Game.buttonHoveringOver);
					}
				}
				Game.buttonHoveringOver.highlight();
			}
		}

		boolean hoveringOverPopup = Game.buttonHoveringOver != null
				&& Game.buttonHoveringOver instanceof PopupMenuButton;

		// Path highlights
		// if (scriptInterceptsClick == false && Game.buttonHoveringOver == null
		// && Game.level.activeActor != null
		// && Game.squareMouseIsOver != null &&
		// Game.squareMouseIsOver.reachableBySelectedCharater
		// && Game.level.activeActor.faction == Game.level.factions.player
		// && Game.level.currentFactionMoving == Game.level.factions.player) {
		//// path = Game.level.activeActor.paths.get(Game.squareMouseIsOver);
		// for (Square square : path.squares) {
		// square.inPath = true;
		// }
		// }

		// Lifted the mouse to perform click
		if (mouseButtonStateLeft == true && !Mouse.isButtonDown(0) && draggingMap == false) {

			// Left Click
			// Game.level.popupMenuObjects.clear();
			// Game.level.popupMenuActions.clear();
			if (Game.windowHoveringOver != null)
				Game.windowHoveringOver.bringToFront();

			if (scriptInterceptsClick) {
				// Continue script
				Game.level.script.click();
			} else if (Game.buttonHoveringOver != null) {
				// Click button
				Game.buttonHoveringOver.click();
			} else if (Level.journal.showing) {

			} else if (Game.level.conversation != null && Game.level.openInventories.size() == 0) {

			} else if (Game.fullScreenTextBoxHoveringOver != null) {
				Game.fullScreenTextBoxHoveringOver.click(Mouse.getX(), (int) (Game.windowHeight - Mouse.getY()));
			} else if (Game.level.fullScreenTextBox != null) {

			} else if (Game.windowHoveringOver != null) {

				// } else if (Game.squareMouseIsOver != null &&
				// Player.playerTargetSquare != null) {
				// Game.level.pausePlayer();
			} else if (Game.squareMouseIsOver != null && Game.level.currentFactionMovingIndex == 0) {
				if (Game.level.activeActor == Game.level.player) {
					Level.closeAllPopups();
					interactWith(Game.squareMouseIsOver, -1,
							Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL),
							Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT),
							Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU));
				}
			}
		}

		if (mouseButtonStateRight == true && !Mouse.isButtonDown(1) && draggingMap == false) {

			// Right Click
			if (Game.windowHoveringOver != null)
				Game.windowHoveringOver.bringToFront();

			if (Level.journal.showing) {
			} else if (Game.level.conversation != null && Game.level.openInventories.size() == 0) {

			} else if (Game.level.fullScreenTextBox != null) {

			} else if (Game.windowHoveringOver != null) {

			} else if (Game.squareMouseIsOver != null && Player.playerTargetSquare != null) {
				Game.level.pausePlayer();
			} else if (Game.level.popupMenuObjects.isEmpty() && Game.squareMouseIsOver != null) {
				interactWith(Game.squareMouseIsOver, -1, true, false, false);
				// Game.level.popups.add(new PopupSelectObject(100, Game.level,
				// Game.squareMouseIsOver));
			} else {
				Level.closeAllPopups();
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
			draggingMap = false;
			UserInputLevel.scrollableMouseIsOver = null;
			UserInputLevel.draggableMouseIsOver = null;
		}

		checkButtons();

		mouseLastX = Mouse.getX();
		mouseLastY = Mouse.getY();
		// if (interactedThisTurn)
		// Game.level.endTurn();

		// interactedThisTurn = false;
	}

	private static void setDraggableAndScrollableMouseIsOver() {

		if (draggingMap)
			return;

		if (draggableMouseIsOver != null)
			return;

		if (scrollableMouseIsOver != null)
			return;

		if (Level.journal.showing) {
			draggableMouseIsOver = Level.journal;
			scrollableMouseIsOver = Level.journal;
		}

		boolean inventoriesOpen = Game.level.openInventories.size() > 0;
		if (inventoriesOpen) {
			draggableMouseIsOver = Game.level.openInventories.get(0).getDraggable(Mouse.getX(),
					(int) Game.windowHeight - Mouse.getY());
			scrollableMouseIsOver = (Scrollable) draggableMouseIsOver;
		}

		if (!inventoriesOpen) {
			for (int i = Game.level.popupPinneds.size() - 1; i >= 0; i--) {
				if (Game.level.popupPinneds.get(i).isMouseOver(Mouse.getX(), (int) Game.windowHeight - Mouse.getY())) {
					draggableMouseIsOver = Game.level.popupPinneds.get(i);
					break;
				}
			}

			if (draggableMouseIsOver == null && scrollableMouseIsOver == null
					&& Game.level.activityLogger.isMouseOver(Mouse.getX(), (int) Game.windowHeight - Mouse.getY())) {
				draggableMouseIsOver = Game.level.activityLogger;
				scrollableMouseIsOver = Game.level.activityLogger;
			}
		}

	}

	// static boolean interactedThisTurn = false;

	public static void interactWith(Square square, int key, boolean openMenu, boolean secondary, boolean attack) {

		if (Game.level.activeActor != Game.level.player)
			return;

		if (openMenu)
			Game.level.levelMode = LevelMode.LEVEL_MODE_NORMAL;

		if (Game.level.levelMode == LevelMode.LEVEL_SELECT_TELEPORT_SQUARE) {
			new ActionTeleport(Game.level.player, Level.teleportee, square, true).perform();
			Game.level.levelMode = LevelMode.LEVEL_MODE_NORMAL;
			return;
		} else if (Game.level.levelMode == LevelMode.LEVEL_MODE_CAST) {
			new ActionUsePower(Game.level.player, Game.squareMouseIsOver, Game.level.selectedPower).perform();
			// Game.level.selectedPower.cast(Game.level.player, square);
			Game.level.levelMode = LevelMode.LEVEL_MODE_NORMAL;
			return;
		}

		Action action = null;

		if (!openMenu) {
			if (attack) {
				action = square.getAttackActionForTheSquareOrObject(Game.level.activeActor);
			} else if (secondary) {
				action = square.getSecondaryActionForTheSquareOrObject(Game.level.activeActor);
			} else {
				action = square.getDefaultActionForTheSquareOrObject(Game.level.activeActor);
			}
		}

		if (key == -1 && action != null && !(square instanceof InventorySquare) && !action.enabled) {
			if (Game.level.player.onScreen()) {
				Game.level.cameraFollow = true;
			}
			Player.playerTargetAction = action;
			Player.playerTargetSquare = square;
			Player.playerFirstMove = true;
			return;
		}

		if (action != null) {

			action.perform();

			if (action.movement && action.enabled) {

				if (key == Keyboard.KEY_UP) {
					Level.wHasBeenPressed = true;
				} else if (key == Keyboard.KEY_DOWN) {
					Level.sHasBeenPressed = true;
				} else if (key == Keyboard.KEY_LEFT) {
					Level.aHasBeenPressed = true;
				} else if (key == Keyboard.KEY_RIGHT) {
					Level.dHasBeenPressed = true;
				}

			}
		} else {
			Level.closeAllPopups();
			if (square instanceof InventorySquare && ((InventorySquare) square).gameObject != null) {
				PopupMenuSelectAction popupSelectAction = new PopupMenuSelectAction(0, 200, Game.level, square,
						((InventorySquare) square).getAllActionsForTheSquareOrObject(Game.level.player));
				if (popupSelectAction.buttons.size() > 0)
					Game.level.popupMenuActions.add(popupSelectAction);

				// Game.level.popups.add(e);
			} else if (!(square instanceof InventorySquare)) {
				PopupMenuSelectObject popupSelectObject = new PopupMenuSelectObject(100, Game.level, square, true, true,
						false);
				if (popupSelectObject.buttons.size() > 0)
					Game.level.popupMenuObjects.add(popupSelectObject);
			}
		}

	}

	public static void waitPressed(boolean allowMenuControl, boolean held) {

		if (Player.playerTargetSquare != null) {
			Game.level.pausePlayer();
			return;
		}

		if (Game.level.fullScreenTextBox != null) {
			return;
		} else if (Level.journal.showing) {
			return;
		} else if (Game.level.openInventories.size() != 0) {
			if (Inventory.buttons.contains(Inventory.buttonLootAll)) {
				Inventory.buttonLootAll.click();
			} else if (Inventory.buttons.contains(Inventory.buttonQuickSell)) {
				Inventory.buttonQuickSell.click();
			}
			controllingMenu = true;
			return;
		} else if (Game.level.conversation != null) {
			return;
		}

		if (!held)
			controllingMenu = false;

		if (Game.level.activeActor != Game.level.player)
			return;

		if (Game.level.popupMenuActions.size() != 0) {
			if (allowMenuControl) {
				controllingMenu = true;
				Game.level.popupMenuActions.get(0).clickHighlightedButton();
			}
		} else if (Game.level.popupMenuObjects.size() != 0) {
			if (allowMenuControl) {
				controllingMenu = true;
				Game.level.popupMenuObjects.get(0).clickHighlightedButton();
			}
		} else if (!controllingMenu) {
			interactWith(Game.level.activeActor.squareGameObjectIsOn, Keyboard.KEY_SPACE,
					Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL),
					Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT),
					Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU));
		}
	}

	public static void upPressed(boolean allowMenuControl, boolean held) {

		if (Player.playerTargetSquare != null) {
			Game.level.pausePlayer();
			return;
		}

		if (Game.level.fullScreenTextBox != null) {
			return;
		} else if (Level.journal.showing) {
			return;
		} else if (Game.level.openInventories.size() != 0) {
			return;
		} else if (Game.level.conversation != null) {
			return;
		}

		if (!held)
			controllingMenu = false;
		if (Game.level.activeActor != Game.level.player)
			return;
		if (Game.level.popupMenuActions.size() != 0) {
			if (allowMenuControl) {
				controllingMenu = true;
				Game.level.popupMenuActions.get(0).moveHighLightUp();
			}
		} else if (Game.level.popupMenuObjects.size() != 0) {
			if (allowMenuControl) {
				controllingMenu = true;
				Game.level.popupMenuObjects.get(0).moveHighLightUp();
			}
		} else if (!controllingMenu) {
			int y = Game.level.activeActor.squareGameObjectIsOn.yInGrid - 1;
			if (y >= 0) {
				interactWith(Game.level.squares[Game.level.activeActor.squareGameObjectIsOn.xInGrid][y],
						Keyboard.KEY_UP,
						Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL),
						Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT),
						Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU));

			}
		}
	}

	public static void downPressed(boolean allowMenuControl, boolean held) {

		if (Player.playerTargetSquare != null) {
			Game.level.pausePlayer();
			return;
		}

		if (Game.level.fullScreenTextBox != null) {
			return;
		} else if (Level.journal.showing) {
			return;
		} else if (Game.level.openInventories.size() != 0) {
			return;
		} else if (Game.level.conversation != null) {
			return;
		}

		if (!held)
			controllingMenu = false;
		if (Game.level.activeActor != Game.level.player)
			return;
		if (Game.level.popupMenuActions.size() != 0) {
			if (allowMenuControl) {
				controllingMenu = true;
				Game.level.popupMenuActions.get(0).moveHighLightDown();
			}
		} else if (Game.level.popupMenuObjects.size() != 0) {
			if (allowMenuControl) {
				controllingMenu = true;
				Game.level.popupMenuObjects.get(0).moveHighLightDown();
			}
		} else if (!controllingMenu) {
			int y = Game.level.activeActor.squareGameObjectIsOn.yInGrid + 1;
			if (y < Game.level.squares[0].length) {
				interactWith(Game.level.squares[Game.level.activeActor.squareGameObjectIsOn.xInGrid][y],
						Keyboard.KEY_DOWN,
						Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL),
						Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT),
						Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU));
			}
		}

	}

	public static void leftPressed(boolean allowMenuControl, boolean held) {
		if (Player.playerTargetSquare != null) {
			Game.level.pausePlayer();
			return;
		}

		if (Level.activeTextBox != null) {
			Level.activeTextBox.moveCaretLeft();
			return;
		} else if (Level.journal.showing) {
			return;
		} else if (Game.level.openInventories.size() != 0) {
			return;
		} else if (Game.level.conversation != null) {
			return;
		}

		if (!held)
			controllingMenu = false;
		if (Game.level.activeActor != Game.level.player)
			return;

		if (Game.level.popupMenuActions.size() != 0) {
			if (allowMenuControl) {
				controllingMenu = true;
				Game.level.popupMenuActions.clear();
				for (Button button : Game.level.popupMenuObjects.get(0).buttons) {
					button.down = false;
				}
			}
		} else if (Game.level.popupMenuObjects.size() != 0) {
			if (allowMenuControl) {
				controllingMenu = true;
				Game.level.popupMenuObjects.clear();
			}
		}

		else if (!controllingMenu) {
			int x = Game.level.activeActor.squareGameObjectIsOn.xInGrid - 1;
			if (x >= 0) {
				interactWith(Game.level.squares[x][Game.level.activeActor.squareGameObjectIsOn.yInGrid],
						Keyboard.KEY_LEFT,
						Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL),
						Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT),
						Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU));
			}
		}

	}

	public static void rightPressed(boolean allowMenuControl, boolean held) {
		if (Player.playerTargetSquare != null) {
			Game.level.pausePlayer();
			return;
		}

		if (Level.activeTextBox != null) {
			Level.activeTextBox.moveCaretRight();
			return;
		} else if (Level.journal.showing) {
			return;
		} else if (Game.level.openInventories.size() != 0) {
			return;
		} else if (Game.level.conversation != null) {
			return;
		}

		if (!held)
			controllingMenu = false;
		if (Game.level.activeActor != Game.level.player)
			return;
		if (Game.level.popupMenuActions.size() != 0) {
			if (allowMenuControl) {
				controllingMenu = true;
				Game.level.popupMenuActions.get(0).clickHighlightedButton();
			}
		} else if (Game.level.popupMenuObjects.size() != 0) {
			if (allowMenuControl) {
				controllingMenu = true;
				Game.level.popupMenuObjects.get(0).clickHighlightedButton();
			}
		} else if (!controllingMenu) {

			int x = Game.level.activeActor.squareGameObjectIsOn.xInGrid + 1;
			if (x < Game.level.squares.length) {

				interactWith(Game.level.squares[x][Game.level.activeActor.squareGameObjectIsOn.yInGrid],
						Keyboard.KEY_RIGHT,
						Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL),
						Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT),
						Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU));
			}
		}

	}

	public static void enterTyped() {
		if (Level.activeTextBox != null) {
			Level.activeTextBox.enterTyped();
			return;
		}
		if (Game.level.activeActor != Game.level.player)
			return;
		if (Game.level.popupMenuActions.size() != 0) {
			controllingMenu = true;
			Game.level.popupMenuActions.get(0).clickHighlightedButton();
		} else if (Game.level.popupMenuObjects.size() != 0) {
			controllingMenu = true;
			Game.level.popupMenuObjects.get(0).clickHighlightedButton();
		}
	}

	public static void backSpaceTyped() {

		if (Game.level.activeTextBox != null) {
			Game.level.activeTextBox.backSpaceTyped();
			return;
		}
		if (Game.level.activeActor != Game.level.player)
			return;
		Level.closeAllPopups();
	}

	public static void deleteTyped() {

		if (Game.level.activeTextBox != null) {
			Game.level.activeTextBox.deleteTyped();
			return;
		}
	}

	public static void escapeTyped() {

		// if (Game.level.activeActor != Game.level.player)
		// return;

		Game.level.levelMode = Level.LevelMode.LEVEL_MODE_NORMAL;

		Level.closeAllPopups();
		Game.level.fullScreenTextBox = null;
		// Game.level.notifications.clear();
		if (Level.journal.showing) {
			Game.level.openCloseJournal();
		} else if (Game.level.openInventories.size() != 0) {
			Game.level.openCloseInventory();
			return;
		} else if (Game.level.conversation != null) {
			Game.level.conversation.currentConversationPart.windowSelectConversationResponse.buttonLeave.click();
			return;
		}

		Level.closeAllPopups();
		return;
	}

	public static void tabTyped() {
		int gridX1Bounds = Game.level.player.squareGameObjectIsOn.xInGrid - Game.level.player.sight;
		if (gridX1Bounds < 0)
			gridX1Bounds = 0;

		// + (mouseXinPixels) / Game.zoom);

		int gridX2Bounds = Game.level.player.squareGameObjectIsOn.xInGrid + Game.level.player.sight;
		if (gridX2Bounds >= Game.level.width)
			gridX2Bounds = Game.level.width - 1;

		int gridY1Bounds = Game.level.player.squareGameObjectIsOn.yInGrid - Game.level.player.sight;
		if (gridY1Bounds < 0)
			gridY1Bounds = 0;

		int gridY2Bounds = Game.level.player.squareGameObjectIsOn.yInGrid + Game.level.player.sight;
		if (gridY2Bounds >= Game.level.height)
			gridY2Bounds = Game.level.height - 1;
		if (Keyboard.isKeyDown(Keyboard.KEY_TAB) == true) {
			Level.closeAllPopups();
			Game.level.popupMenuHighlightObjects.clear();
			for (int j = gridY1Bounds; j < gridY2Bounds; j++) {
				for (int i = gridX1Bounds; i < gridX2Bounds; i++) {
					if (Game.level.squares[i][j].visibleToPlayer) {
						PopupMenuSelectObject popupSelectObject = new PopupMenuSelectObject(100, Game.level,
								Game.level.squares[i][j], false, false, true);
						if (popupSelectObject.buttons.size() > 0)
							Game.level.popupMenuHighlightObjects.add(popupSelectObject);
					}
				}
			}
		}
	}

	public static long lastCopy = 0;

	public static void keyTyped(char character) {

		if (Level.activeTextBox != null) {
			Level.activeTextBox.keyTyped(character);
			return;
		}

		if (Game.level.fullScreenTextBox != null) {
			Game.level.fullScreenTextBox.keyTyped(character);
			return;
		}

		if (character == 'j' || character == 'J') {
			Game.level.openCloseJournal();
			return;
		}

		if (character == 'i' || character == 'I') {
			Game.level.openCloseInventory();
			return;
		}

		if (character == ' ') {
			waitPressed(true, false);
		} else if (character == 'c' || character == 'C') {

			if (Game.squareMouseIsOver != null) {

				long now = System.currentTimeMillis();
				String stringToCopy = "";
				if (now - lastCopy < 10000) {
					try {
						stringToCopy += (String) Toolkit.getDefaultToolkit().getSystemClipboard()
								.getData(DataFlavor.stringFlavor);
					} catch (HeadlessException e) {
						e.printStackTrace();
					} catch (UnsupportedFlavorException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				stringToCopy += ("Templates.BIG_TREE.makeCopy(Game.level.squares[" + Game.squareMouseIsOver.xInGrid
						+ "][" + Game.squareMouseIsOver.yInGrid + "],null);\n");

				StringSelection stringSelection = new StringSelection(stringToCopy);
				Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
				clpbrd.setContents(stringSelection, null);

				lastCopy = now;
			}
		} else if (character == 'm' || character == 'M') {
			Game.level.mapButton.click();
		} else if (character == 'q' || character == 'Q') {
			Game.level.centerButton.click();
		} else if (character == 'l' || character == 'L') {
			Game.level.showHideLogButton.click();
		} else if (character == 'w' || character == 'W') {
			if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
				Level.ctrlActionHasBeenPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU)) {
				Level.altActionHasBeenPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				Level.shiftActionHasBeenPressed = true;
			}
			upPressed(true, false);
		} else if (character == 'a' || character == 'A') {
			if (Level.journal.showing) {

			} else if (Game.level.openInventories.size() > 0) {
			} else if (Game.level.conversation != null) {
				Game.level.conversation.currentConversationPart.windowSelectConversationResponse.buttonTrade.click();
			} else {
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
					Level.ctrlActionHasBeenPressed = true;
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU)) {
					Level.altActionHasBeenPressed = true;
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
					Level.shiftActionHasBeenPressed = true;
				}
				leftPressed(true, false);
			}
		} else if (character == 's' || character == 'S') {
			if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
				Level.ctrlActionHasBeenPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU)) {
				Level.altActionHasBeenPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				Level.shiftActionHasBeenPressed = true;
			}
			downPressed(true, false);
		} else if (character == 'd' || character == 'D') {
			if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
				Level.ctrlActionHasBeenPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU)) {
				Level.altActionHasBeenPressed = true;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				Level.shiftActionHasBeenPressed = true;
			}
			rightPressed(true, false);
		} else if (character == '1' || character == '2' || character == '3' || character == '4' || character == '5'
				|| character == '6' || character == '7' || character == '8' || character == '9' || character == '0') {
			if (Game.level.conversation != null) {
				Game.level.conversation.selectDialogueOption(character);
			}
		}

	}

	private static void checkButtons() {

		if (keyStateTab == false && Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
			tabTyped();
			keyStateTab = true;
		} else if (keyStateTab == true && !Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
			keyStateTab = false;
			Game.level.popupMenuHighlightObjects.clear();
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
			keyStateTab = false;
		}

		if (keyStateUp == false && Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			upPressed(true, false);
			keyStateUp = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			keyStateUp = false;
		}

		if (keyStateDown == false && Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {

			downPressed(true, false);
			keyStateDown = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			keyStateDown = false;
		}

		if (keyStateLeft == false && Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			leftPressed(true, false);
			keyStateLeft = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			keyStateLeft = false;
		}

		if (keyStateRight == false && Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			rightPressed(true, false);
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
			backSpaceTyped();
			keyStateBack = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
			keyStateBack = false;
		}

		if (keyStateDelete == false && Keyboard.isKeyDown(Keyboard.KEY_DELETE)) {
			deleteTyped();
			keyStateDelete = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_DELETE)) {
			keyStateDelete = false;
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

		if (keyStateEscape == false && Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			keyStateEscape = true;
			escapeTyped();
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			keyStateEscape = false;
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
			Game.zoomLevelIndex++;
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
			Game.zoomLevelIndex--;
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
