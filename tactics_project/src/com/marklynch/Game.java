package com.marklynch;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.ui.Button;

public class Game {

	/** time at last frame */
	long lastFrame;

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

	public static int windowWidth = 1280;
	public static int windowHeight = 640;

	public static float zoom = 0.5f;

	public static float dragX = 0;
	public static float dragY = 0;
	float mouseDownX = -1;
	float mouseDownY = -1;
	float mouseLastX = -1;
	float mouseLastY = -1;
	boolean dragging = false;

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
			int delta = getDelta();

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

		// Movement
		// if(lastFPS - lastMoveTime >= timeBetweenMoveCommands)
		// {

		float mouseXinPixels = Mouse.getX();
		float mouseYinPixels = Mouse.getY();

		// a = ((((b / 2) - c - (b / 2) / d) + (e) / d) / f)

		// mouseXInSquares a
		// windowWidth b
		// dragX c
		// zoom d
		// mouseXinPixels g
		// SQUARE_WIDTH f

		float mouseXInSquares = (int) ((((windowWidth / 2) - dragX - (windowWidth / 2)
				/ zoom) + (mouseXinPixels) / zoom) / SQUARE_WIDTH);
		float mouseYInSquares = (int) (((windowHeight / 2 - dragY - (windowHeight / 2)
				/ zoom) + (((windowHeight - mouseYinPixels)) / zoom)) / SQUARE_HEIGHT);

		// mouseYInSquares += Math.round((dragY/zoom)/squareHeight);

		zoom += 0.001 * Mouse.getDWheel();
		if (zoom < 0.5)
			zoom = 0.5f;
		if (zoom > 2)
			zoom = 2f;

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

		Button buttonClicked = null;
		Square squareClicked = null;
		if ((int) mouseXInSquares > -1
				&& (int) mouseXInSquares < level.squares.length
				&& (int) mouseYInSquares > -1
				&& (int) mouseYInSquares < level.squares[0].length) {
			squareClicked = level.squares[(int) mouseXInSquares][(int) mouseYInSquares];
		}

		if (dragging == false) {
			buttonClicked = level.getButtonFromMousePosition();
		}

		if (mouseButtonStateLeft == true && !Mouse.isButtonDown(0)
				&& dragging == false && buttonClicked != null) {
			buttonClicked.click();
		} else if (mouseButtonStateLeft == true && !Mouse.isButtonDown(0)
				&& dragging == false && squareClicked != null) {
			// CLICK

			for (Actor actor : level.actors) {
				if (actor.squareGameObjectIsOn == squareClicked) {
					level.selectedActor = actor;
					level.selectedActor
							.calculateReachableSquares(level.squares);
					level.selectedActor
							.calculateAttackableSquares(level.squares);
					level.gameCursor.square = level.selectedActor.squareGameObjectIsOn;
				}
			}

			if (level.selectedActor != null
					&& squareClicked.reachableBySelectedCharater) {
				level.selectedActor.squareGameObjectIsOn.gameObject = null;
				level.selectedActor.squareGameObjectIsOn = null;
				level.selectedActor.distanceMovedThisTurn += squareClicked.distanceToSquare;
				level.selectedActor.squareGameObjectIsOn = squareClicked;
				squareClicked.gameObject = level.selectedActor;
				level.gameCursor.square = level.selectedActor.squareGameObjectIsOn;
				level.selectedActor.calculateReachableSquares(level.squares);
				level.selectedActor.calculateAttackableSquares(level.squares);
			}

			mouseButtonStateLeft = false;

			mouseDownX = -1;
			mouseDownY = -1;
		}

		if (!Mouse.isButtonDown(0)) {
			mouseButtonStateLeft = false;
			mouseDownX = -1;
			mouseDownY = -1;
			lastMoveTime = lastFPS;
		}

		if (mouseButtonStateRight == false && Mouse.isButtonDown(1)) {
			level.clearDialogs();
			// right click
			if (level.selectedActor != null) {
				level.removeWalkingHighlight();
				level.removeWeaponsThatCanAttackHighlight();
				level.selectedActor = null;
			} else if (squareClicked != null) {
				if (squareClicked.showingDialogs == false)
					squareClicked.showDialogs();
				else
					squareClicked.clearDialogs();

				// level.dialogs.addElement(new Dialog(Mouse.getX(),
				// windowHeight-Mouse.getY(),64,64,"marlene.png"));
			}
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

		// keep char on the screen
		// if (actorPositionX < 0)
		// actorPositionX = 0;
		// if (actorPositionX > 9)
		// actorPositionX = 9;
		// if (actorPositionY < 0)
		// actorPositionY = 0;
		// if (actorPositionY > 9)
		// actorPositionY = 9;

		updateFPS(); // update FPS Counter
	}

	/**
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public int getDelta() {
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
	public long getTime() {
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
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		level.draw();
	}
}