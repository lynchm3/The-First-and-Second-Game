package com.marklynch;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.marklynch.config.Config;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.unit.Path;
import com.marklynch.ui.button.Button;

public class Game {

	/** time at last frame */
	static long lastFrame;

	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	long lastMoveTime = 0l;
	long timeBetweenMoveCommands = 1l;
	Level level;
	public static float SQUARE_WIDTH = 128f;
	public static float SQUARE_HEIGHT = 128f;

	boolean keyStateLeft = false;
	boolean keyStateRight = false;
	boolean keyStateUp = false;
	boolean keyStateDown = false;
	boolean mouseButtonStateLeft = false;
	boolean mouseButtonStateRight = false;

	public static int windowWidth = 1900;
	public static int windowHeight = 1000;
	// public static int windowWidth = 400;
	// public static int windowHeight = 400;

	public static float zoom = 0.8f;

	public static float dragX = 100;
	public static float dragY = -100;
	float mouseDownX = -1;
	float mouseDownY = -1;
	float mouseLastX = -1;
	float mouseLastY = -1;
	boolean dragging = false;

	public static int delta = 0;

	public static Square squareMouseIsOver;
	public static Path path;

	public static void main(String[] argv) {
		Game game = new Game();
		game.start();
	}

	public void start() {

		initGL(windowWidth, windowHeight); // init OpenGL
		init();
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer

		while (!Display.isCloseRequested()) {
			delta = getDelta();

			update(delta);
			renderGL();

			Display.update();
			Display.sync(60); // cap fps to 60fps
		}

		Display.destroy();
	}

	public void init() {
		// Level
		level = new Level(10, 10);
	}

	private void initGL(int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

		// enable alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glViewport(0, 0, width, height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public void update(int delta) {

		// if (level.script.activeScriptEvent != null
		// && level.script.activeScriptEvent.blockUserInput == true) {
		//
		// } else {
		userInput(delta);
		// }

		level.update(delta);

		updateFPS(); // update FPS Counter
	}

	private void userInput(int delta2) {

		// Getting what square pixel the mouse is on
		float mouseXinPixels = Mouse.getX();
		float mouseYinPixels = Mouse.getY();

		// Transformed mouse coords

		float mouseXTransformed = (((windowWidth / 2) - dragX - (windowWidth / 2)
				/ zoom) + (mouseXinPixels) / zoom);
		float mouseYTransformed = ((windowHeight / 2 - dragY - (windowHeight / 2)
				/ zoom) + (((windowHeight - mouseYinPixels)) / zoom));

		// Getting what square coordinates the mouse is on (as in squares on the
		// grid)
		float mouseXInSquares = (int) (mouseXTransformed / SQUARE_WIDTH);
		float mouseYInSquares = (int) (mouseYTransformed / SQUARE_HEIGHT);

		// Calculate zoom
		zoom += 0.001 * Mouse.getDWheel();
		if (zoom < 0.1)
			zoom = 0.1f;
		if (zoom > 2)
			zoom = 2f;

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
				dragX += (Mouse.getX() - mouseLastX) / zoom;
				dragY -= (Mouse.getY() - mouseLastY) / zoom;
			}
			mouseLastX = Mouse.getX();
			mouseLastY = Mouse.getY();
		}

		boolean scriptGetsClick = false;
		if (level.script.activeScriptEvent != null
				&& level.script.activeScriptEvent.blockUserInput == true) {
			scriptGetsClick = true;
		}

		// Get the square that we're hovering over
		squareMouseIsOver = null;
		if ((int) mouseXInSquares > -1
				&& (int) mouseXInSquares < level.squares.length
				&& (int) mouseYInSquares > -1
				&& (int) mouseYInSquares < level.squares[0].length) {
			squareMouseIsOver = level.squares[(int) mouseXInSquares][(int) mouseYInSquares];
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
		if (scriptGetsClick == false && buttonHoveringOver == null
				&& level.activeActor != null && squareMouseIsOver != null
				&& squareMouseIsOver.reachableBySelectedCharater
				&& level.activeActor.faction == level.factions.get(0)
				&& level.currentFactionMoving == level.factions.get(0)) {
			path = level.activeActor.paths.get(squareMouseIsOver);
			for (Square square : path.squares) {
				square.inPath = true;
			}
		}

		if (scriptGetsClick && mouseButtonStateLeft == true
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
				&& dragging == false && squareMouseIsOver != null
				&& level.currentFactionMovingIndex == 0) {
			// click square if we're on one

			GameObject clickedGameObject = squareMouseIsOver.gameObject;
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
										.weaponDistanceTo(squareMouseIsOver))) {
					level.activeActor.attack(clickedGameObject, false);
					Actor.highlightSelectedCharactersSquares(level);
				}
			}

			// Check if we clicked on an empty reachable square and act
			// accordingly
			if (level.activeActor != null
					&& squareMouseIsOver.reachableBySelectedCharater
					&& level.activeActor.faction == level.factions.get(0)
					&& level.currentFactionMoving == level.factions.get(0)
					&& level.activeActor.squareGameObjectIsOn != squareMouseIsOver) {
				level.activeActor.moveTo(squareMouseIsOver);
			}
		}

		if (!Mouse.isButtonDown(0)) {
			mouseButtonStateLeft = false;
			mouseDownX = -1;
			mouseDownY = -1;
			lastMoveTime = lastFPS;

			// Show/hide Hover preview
			if (Config.SHOW_BATTLE_PREVIEW_ON_HOVER
					&& squareMouseIsOver != null
					&& squareMouseIsOver.gameObject != null
					&& level.activeActor != null
					&& squareMouseIsOver.gameObject != level.activeActor
					&& level.currentFactionMoving == level.factions.get(0)
					&& buttonHoveringOver == null) {
				// show hover preview
				level.activeActor
						.showHoverFightPreview(squareMouseIsOver.gameObject);
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
			} else if (squareMouseIsOver != null) {
				if (squareMouseIsOver.showingDialogs == false)
					squareMouseIsOver.showDialogs();
				else
					squareMouseIsOver.clearDialogs();

				// level.dialogs.addElement(new Dialog(Mouse.getX(),
				// windowHeight-Mouse.getY(),64,64,"marlene.png"));
			}
		}

		if (Mouse.isButtonDown(1)) {
			mouseButtonStateRight = true;
		} else if (!Mouse.isButtonDown(1)) {
			mouseButtonStateRight = false;
		}

		if (keyStateLeft == false && Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			// actorPositionX -= 1;
			lastMoveTime = lastFPS;
			keyStateLeft = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			keyStateLeft = false;
		}

		if (keyStateRight == false && Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			// actorPositionX += 1;
			lastMoveTime = lastFPS;
			keyStateRight = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			keyStateRight = false;
		}

		if (keyStateUp == false && Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			// actorPositionY -= 1;
			lastMoveTime = lastFPS;
			keyStateUp = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			keyStateUp = false;
		}

		if (keyStateDown == false && Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			// actorPositionY += 1;
			lastMoveTime = lastFPS;
			keyStateDown = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			keyStateDown = false;
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

	/**
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public static int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public void renderGL() {

		// Clear The Screen And The Depth Buffer
		GL11.glClearColor(0, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		level.draw();
	}
}