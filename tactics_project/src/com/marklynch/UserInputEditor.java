package com.marklynch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.marklynch.editor.Editor;
import com.marklynch.tactics.objects.unit.Path;

public class UserInputEditor {
	public static float mouseXinPixels;
	public static float mouseYinPixels;
	public static float mouseDownX = -1;
	public static float mouseDownY = -1;
	public static float mouseLastX = -1;
	public static float mouseLastY = -1;
	public static boolean dragging = false;

	public static boolean capsLock = false;
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

	public static boolean mouseButtonStateLeft = false;
	public static boolean mouseButtonStateRight = false;

	public static Path path;

	public static void userInput(int delta2, Editor editor) {

		// Getting what square pixel the mouse is on
		mouseXinPixels = Mouse.getX();
		mouseYinPixels = Mouse.getY();

		// Transformed mouse coords

		float mouseXTransformed = (((Game.windowWidth / 2) - Game.dragX - (Game.windowWidth / 2) / Game.zoom)
				+ (mouseXinPixels) / Game.zoom);
		float mouseYTransformed = ((Game.windowHeight / 2 - Game.dragY - (Game.windowHeight / 2) / Game.zoom)
				+ (((Game.windowHeight - mouseYinPixels)) / Game.zoom));

		// Getting what square coordinates the mouse is on (as in squares on the
		// grid)
		float mouseXInSquares = -1;
		float mouseYInSquares = -1;
		if (mouseXTransformed >= 0)
			mouseXInSquares = (int) (mouseXTransformed / Game.SQUARE_WIDTH);
		if (mouseYTransformed >= 0)
			mouseYInSquares = (int) (mouseYTransformed / Game.SQUARE_HEIGHT);

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

		// Get the square that we're hovering over
		Game.squareMouseIsOver = null;
		if (mouseXInSquares >= 0 && mouseYInSquares >= 0 && (int) mouseXInSquares > -1
				&& (int) mouseXInSquares < Game.level.squares.length && (int) mouseYInSquares > -1
				&& (int) mouseYInSquares < Game.level.squares[0].length) {
			Game.squareMouseIsOver = Game.level.squares[(int) mouseXInSquares][(int) mouseYInSquares];
		}

		// Getting button that we have clicked, if any
		Game.buttonHoveringOver = null;
		if (dragging == false) {
			Game.buttonHoveringOver = editor.getButtonFromMousePosition(Mouse.getX(), Mouse.getY());
		}

		// Getting inventory that the mouse is over, if any
		Game.inventoryHoveringOver = null;
		// if (dragging == false) {
		Game.inventoryHoveringOver = Game.level.getInventoryFromMousePosition(Mouse.getX(), Mouse.getY(),
				mouseXTransformed, mouseYTransformed);
		if (Game.inventoryHoveringOver != null)
			Game.inventoryHoveringOver.userInput();

		// }

		// left click logic
		if (mouseButtonStateLeft == true && !Mouse.isButtonDown(0) && dragging == false
				&& Game.buttonHoveringOver != null) {
			// click button
			Game.buttonHoveringOver.click();
		} else if (mouseButtonStateLeft == true && !Mouse.isButtonDown(0) && dragging == false
				&& Game.inventoryHoveringOver != null) {
			// click button
			// Game.inventoryHoveringOver.click();
		} else if (mouseButtonStateLeft == true && !Mouse.isButtonDown(0) && dragging == false
				&& Game.squareMouseIsOver != null) {
			// click square/game object if we're on one

			System.out.println("Game.squareMouseIsOver = " + Game.squareMouseIsOver);
			// if (Game.squareMouseIsOver.inventory.size() != 0) {
			// editor.gameObjectClicked(Game.squareMouseIsOver.inventory.get(0));
			// } else {
			editor.squareClicked(Game.squareMouseIsOver);
			// }
		}

		// right click
		if (mouseButtonStateRight == false && Mouse.isButtonDown(1)) {
			editor.rightClick();
		}

		if (Mouse.isButtonDown(1)) {
			mouseButtonStateRight = true;
		} else if (!Mouse.isButtonDown(1)) {
			mouseButtonStateRight = false;
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

		if (keyStateBack == false && Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
			editor.backTyped();
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
			editor.keyTyped(' ');
			keyStateSpace = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			keyStateSpace = false;
		}

		if (keyStateA == false && Keyboard.isKeyDown(Keyboard.KEY_A)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('a');
				} else {
					editor.keyTyped('A');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('A');
				} else {
					editor.keyTyped('a');
				}
			}
			keyStateA = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_A)) {
			keyStateA = false;
		}

		if (keyStateB == false && Keyboard.isKeyDown(Keyboard.KEY_B)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('b');
				} else {
					editor.keyTyped('B');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('B');
				} else {
					editor.keyTyped('b');
				}
			}
			keyStateB = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_B)) {
			keyStateB = false;
		}

		if (keyStateC == false && Keyboard.isKeyDown(Keyboard.KEY_C)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('c');
				} else {
					editor.keyTyped('C');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('C');
				} else {
					editor.keyTyped('c');
				}
			}
			keyStateC = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_C)) {
			keyStateC = false;
		}

		if (keyStateD == false && Keyboard.isKeyDown(Keyboard.KEY_D)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('d');
				} else {
					editor.keyTyped('D');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('D');
				} else {
					editor.keyTyped('d');
				}
			}
			keyStateD = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_D)) {
			keyStateD = false;
		}

		if (keyStateE == false && Keyboard.isKeyDown(Keyboard.KEY_E)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('e');
				} else {
					editor.keyTyped('E');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('E');
				} else {
					editor.keyTyped('e');
				}
			}
			keyStateE = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_E)) {
			keyStateE = false;
		}

		if (keyStateF == false && Keyboard.isKeyDown(Keyboard.KEY_F)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('f');
				} else {
					editor.keyTyped('F');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('F');
				} else {
					editor.keyTyped('f');
				}
			}
			keyStateF = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_F)) {
			keyStateF = false;
		}

		if (keyStateG == false && Keyboard.isKeyDown(Keyboard.KEY_G)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('g');
				} else {
					editor.keyTyped('G');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('G');
				} else {
					editor.keyTyped('g');
				}
			}
			keyStateG = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_G)) {
			keyStateG = false;
		}

		if (keyStateH == false && Keyboard.isKeyDown(Keyboard.KEY_H)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('h');
				} else {
					editor.keyTyped('H');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('H');
				} else {
					editor.keyTyped('h');
				}
			}
			keyStateH = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_H)) {
			keyStateH = false;
		}

		if (keyStateI == false && Keyboard.isKeyDown(Keyboard.KEY_I)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('i');
				} else {
					editor.keyTyped('I');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('I');
				} else {
					editor.keyTyped('i');
				}
			}
			keyStateI = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_I)) {
			keyStateI = false;
		}

		if (keyStateJ == false && Keyboard.isKeyDown(Keyboard.KEY_J)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('j');
				} else {
					editor.keyTyped('J');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('J');
				} else {
					editor.keyTyped('j');
				}
			}
			keyStateJ = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_J)) {
			keyStateJ = false;
		}

		if (keyStateK == false && Keyboard.isKeyDown(Keyboard.KEY_K)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('k');
				} else {
					editor.keyTyped('K');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('K');
				} else {
					editor.keyTyped('k');
				}
			}
			keyStateK = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_K)) {
			keyStateK = false;
		}

		if (keyStateL == false && Keyboard.isKeyDown(Keyboard.KEY_L)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('l');
				} else {
					editor.keyTyped('L');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('L');
				} else {
					editor.keyTyped('l');
				}
			}
			keyStateL = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_L)) {
			keyStateL = false;
		}

		if (keyStateM == false && Keyboard.isKeyDown(Keyboard.KEY_M)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('m');
				} else {
					editor.keyTyped('M');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('M');
				} else {
					editor.keyTyped('m');
				}
			}
			keyStateM = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_M)) {
			keyStateM = false;
		}

		if (keyStateN == false && Keyboard.isKeyDown(Keyboard.KEY_N)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('n');
				} else {
					editor.keyTyped('N');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('N');
				} else {
					editor.keyTyped('n');
				}
			}
			keyStateN = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_N)) {
			keyStateN = false;
		}

		if (keyStateO == false && Keyboard.isKeyDown(Keyboard.KEY_O)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('o');
				} else {
					editor.keyTyped('O');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('O');
				} else {
					editor.keyTyped('o');
				}
			}
			keyStateO = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_O)) {
			keyStateO = false;
		}

		if (keyStateP == false && Keyboard.isKeyDown(Keyboard.KEY_P)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('p');
				} else {
					editor.keyTyped('P');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('P');
				} else {
					editor.keyTyped('p');
				}
			}
			keyStateP = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_P)) {
			keyStateP = false;
		}

		if (keyStateQ == false && Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('q');
				} else {
					editor.keyTyped('Q');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('Q');
				} else {
					editor.keyTyped('q');
				}
			}
			keyStateQ = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			keyStateQ = false;
		}

		if (keyStateR == false && Keyboard.isKeyDown(Keyboard.KEY_R)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('r');
				} else {
					editor.keyTyped('R');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('R');
				} else {
					editor.keyTyped('r');
				}
			}
			keyStateR = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_R)) {
			keyStateR = false;
		}

		if (keyStateS == false && Keyboard.isKeyDown(Keyboard.KEY_S)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('s');
				} else {
					editor.keyTyped('S');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('S');
				} else {
					editor.keyTyped('s');
				}
			}
			keyStateS = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_S)) {
			keyStateS = false;
		}

		if (keyStateT == false && Keyboard.isKeyDown(Keyboard.KEY_T)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('t');
				} else {
					editor.keyTyped('T');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('T');
				} else {
					editor.keyTyped('t');
				}
			}
			keyStateT = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_T)) {
			keyStateT = false;
		}

		if (keyStateU == false && Keyboard.isKeyDown(Keyboard.KEY_U)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('u');
				} else {
					editor.keyTyped('U');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('U');
				} else {
					editor.keyTyped('u');
				}
			}
			keyStateU = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_U)) {
			keyStateU = false;
		}

		if (keyStateV == false && Keyboard.isKeyDown(Keyboard.KEY_V)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('v');
				} else {
					editor.keyTyped('V');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('V');
				} else {
					editor.keyTyped('v');
				}
			}
			keyStateV = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_V)) {
			keyStateV = false;
		}

		if (keyStateW == false && Keyboard.isKeyDown(Keyboard.KEY_W)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('w');
				} else {
					editor.keyTyped('W');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('W');
				} else {
					editor.keyTyped('w');
				}
			}
			keyStateW = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_W)) {
			keyStateW = false;
		}

		if (keyStateX == false && Keyboard.isKeyDown(Keyboard.KEY_X)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('x');
				} else {
					editor.keyTyped('X');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('X');
				} else {
					editor.keyTyped('x');
				}
			}
			keyStateX = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_X)) {
			keyStateX = false;
		}

		if (keyStateY == false && Keyboard.isKeyDown(Keyboard.KEY_Y)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('y');
				} else {
					editor.keyTyped('Y');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('Y');
				} else {
					editor.keyTyped('y');
				}
			}
			keyStateY = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_Y)) {
			keyStateY = false;
		}

		if (keyStateZ == false && Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			if (capsLock) {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('z');
				} else {
					editor.keyTyped('Z');
				}
			} else {
				if (keyStateLeftShift || keyStateRightShift) {
					editor.keyTyped('Z');
				} else {
					editor.keyTyped('z');
				}
			}
			keyStateZ = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			keyStateZ = false;
		}

		if (keyState1 == false && Keyboard.isKeyDown(Keyboard.KEY_1)) {
			editor.keyTyped('1');
			keyState1 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_1)) {
			keyState1 = false;
		}

		if (keyState2 == false && Keyboard.isKeyDown(Keyboard.KEY_2)) {
			editor.keyTyped('2');
			keyState2 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_2)) {
			keyState2 = false;
		}

		if (keyState3 == false && Keyboard.isKeyDown(Keyboard.KEY_3)) {
			editor.keyTyped('3');
			keyState3 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_3)) {
			keyState3 = false;
		}

		if (keyState4 == false && Keyboard.isKeyDown(Keyboard.KEY_4)) {
			editor.keyTyped('4');
			keyState4 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_4)) {
			keyState4 = false;
		}

		if (keyState5 == false && Keyboard.isKeyDown(Keyboard.KEY_5)) {
			editor.keyTyped('5');
			keyState5 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_5)) {
			keyState5 = false;
		}

		if (keyState6 == false && Keyboard.isKeyDown(Keyboard.KEY_6)) {
			editor.keyTyped('6');
			keyState6 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_6)) {
			keyState6 = false;
		}

		if (keyState7 == false && Keyboard.isKeyDown(Keyboard.KEY_7)) {
			editor.keyTyped('7');
			keyState7 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_7)) {
			keyState7 = false;
		}

		if (keyState8 == false && Keyboard.isKeyDown(Keyboard.KEY_8)) {
			editor.keyTyped('8');
			keyState8 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_8)) {
			keyState8 = false;
		}

		if (keyState9 == false && Keyboard.isKeyDown(Keyboard.KEY_9)) {
			editor.keyTyped('9');
			keyState9 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_9)) {
			keyState9 = false;
		}

		if (keyState0 == false && Keyboard.isKeyDown(Keyboard.KEY_0)) {
			editor.keyTyped('0');
			keyState0 = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_0)) {
			keyState0 = false;
		}

		if (keyStateMinus == false && Keyboard.isKeyDown(Keyboard.KEY_MINUS)) {
			editor.keyTyped('-');
			keyStateMinus = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_MINUS)) {
			keyStateMinus = false;
		}

		if (keyStatePeriod == false && Keyboard.isKeyDown(Keyboard.KEY_PERIOD)) {
			editor.keyTyped('.');
			keyStatePeriod = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_PERIOD)) {
			keyStatePeriod = false;
		}

	}
}
