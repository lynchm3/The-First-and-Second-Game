package com.marklynch;

import java.util.Vector;

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

public class Game {

	/** time at last frame */
	long lastFrame;

	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	long lastMoveTime = 0l;
	long timeBetweenMoveCommands = 1l;

	GameCursor gameCursor;
	Vector<Actor> actors;
	Actor selectedActor = null;
	Level level;
	float squareWidth = 128f;
	float squareHeight = 128f;

	boolean keyStateLeft = false;
	boolean keyStateRight = false;
	boolean keyStateUp = false;
	boolean keyStateDown = false;
	boolean mouseButtonStateLeft = false;
	boolean mouseButtonStateRight = false;

	int windowWidth = 1280;
	int windowHeight = 640;

	float zoom = 1f;

	float dragX = 0;
	float dragY = 0;
	float mouseDownX = -1;
	float mouseDownY = -1;
	float mouseLastX = -1;
	float mouseLastY = -1;
	boolean dragging = false;

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

	/**
	 * Initialise resources
	 */
	public void init() {
		// Level
		level = new Level(10, 10);

		// Actors
		actors = new Vector<Actor>();
		actors.add(new Actor(0, 0, 0, 0, "avatar.png", level.squares[0][0]));
		level.squares[0][0].actor = actors.get(0);
		actors.add(new Actor(0, 0, 0, 0, "avatar.png", level.squares[2][7]));
		level.squares[2][7].actor = actors.get(1);
		actors.add(new Actor(0, 0, 0, 0, "avatar.png", level.squares[5][3]));
		level.squares[5][3].actor = actors.get(2);

		// Cursor
		gameCursor = new GameCursor("highlight.png");
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

	public void update(int delta) {

		// Movement
		// if(lastFPS - lastMoveTime >= timeBetweenMoveCommands)
		// {

		float mouseXinPixels = Mouse.getX();
		float mouseYinPixels = Mouse.getY();

		float mouseXInSquares = (int) ((((windowWidth / 2) - dragX - (windowWidth / 2) / zoom) + (mouseXinPixels) / zoom)	/ squareWidth);
		float mouseYInSquares = (int) (((windowHeight / 2 - dragY - (windowHeight / 2)	/ zoom) + (((windowHeight - mouseYinPixels)) / zoom)) / squareHeight);

		// mouseYInSquares += Math.round((dragY/zoom)/squareHeight);

		zoom += 0.001 * Mouse.getDWheel();
		if (zoom < 0.5)
			zoom = 0.5f;
		if (zoom > 2)
			zoom = 2f;
		// int dWheel = Mouse.getDWheel();
		// if (dWheel != 0)
		// System.out.println(dWheel);

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

		if (mouseButtonStateLeft == true && !Mouse.isButtonDown(0)
				&& dragging == false && (int) mouseXInSquares > -1
				&& (int) mouseXInSquares < level.squares.length
				&& (int) mouseYInSquares > -1
				&& (int) mouseYInSquares < level.squares[0].length) {
			// CLICK

			Square squareClicked = level.squares[(int) mouseXInSquares][(int) mouseYInSquares];

			for (Actor actor : actors) {
				if (actor.squareActorIsStandingOn == squareClicked) {
					selectedActor = actor;
					selectedActor.calculateWalkableSquares(level.squares);
					gameCursor.square = selectedActor.squareActorIsStandingOn;
				}
			}

			if (selectedActor != null
					&& squareClicked.reachableBySelectedCaharater) {
				selectedActor.squareActorIsStandingOn.actor = null;
				selectedActor.squareActorIsStandingOn = null;
				selectedActor.squareActorIsStandingOn = squareClicked;
				squareClicked.actor = selectedActor;
				gameCursor.square = selectedActor.squareActorIsStandingOn;
				selectedActor.calculateWalkableSquares(level.squares);
			}

			lastMoveTime = lastFPS;
			mouseButtonStateLeft = false;

			mouseDownX = -1;
			mouseDownY = -1;
		} else if (!Mouse.isButtonDown(0)) {
			mouseButtonStateLeft = false;
			mouseDownX = -1;
			mouseDownY = -1;
		}

		if (mouseButtonStateRight == false && Mouse.isButtonDown(1)) {
			// right click
			level.removeWalkingHighlight();
			selectedActor = null;
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

		// zoom
		GL11.glPushMatrix();

		GL11.glTranslatef(windowWidth / 2, windowHeight / 2, 0);
		GL11.glScalef(zoom, zoom, 0);
		GL11.glTranslatef(dragX, dragY, 0);
		GL11.glTranslatef(-windowWidth / 2, -windowHeight / 2, 0);

		// Squares
		for (int i = 0; i < level.width; i++) {
			for (int j = 0; j < level.height; j++) {
				// is it better to bind once and draw all the same ones
				level.squares[i][j].imageTexture.bind();

				int squarePositionX = i * (int) squareWidth;
				int squarePositionY = j * (int) squareHeight;

				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex2f(squarePositionX, squarePositionY);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex2f(squarePositionX + squareWidth, squarePositionY);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex2f(squarePositionX + squareWidth, squarePositionY
						+ squareHeight);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex2f(squarePositionX, squarePositionY + squareHeight);
				GL11.glEnd();
			}
		}

		// Highlighted Squares
		for (int i = 0; i < level.width; i++) {
			for (int j = 0; j < level.height; j++) {
				// is it better to bind once and draw all the same ones
				if (level.squares[i][j].reachableBySelectedCaharater) {
					gameCursor.imageTexture.bind();

					int squarePositionX = i * (int) squareWidth;
					int squarePositionY = j * (int) squareHeight;

					GL11.glBegin(GL11.GL_QUADS);
					GL11.glTexCoord2f(0, 0);
					GL11.glVertex2f(squarePositionX, squarePositionY);
					GL11.glTexCoord2f(1, 0);
					GL11.glVertex2f(squarePositionX + squareWidth,
							squarePositionY);
					GL11.glTexCoord2f(1, 1);
					GL11.glVertex2f(squarePositionX + squareWidth,
							squarePositionY + squareHeight);
					GL11.glTexCoord2f(0, 1);
					GL11.glVertex2f(squarePositionX, squarePositionY
							+ squareHeight);
					GL11.glEnd();
				}
			}
		}

		// Cursor
		if (selectedActor != null) {
			gameCursor.imageTexture.bind();
			int cursorPositionXInPixels = gameCursor.square.x
					* (int) squareWidth;
			int cursorPositionYInPixels = gameCursor.square.y
					* (int) squareHeight;
			GL11.glPushMatrix();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(cursorPositionXInPixels, cursorPositionYInPixels);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(cursorPositionXInPixels + squareWidth,
					cursorPositionYInPixels);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(cursorPositionXInPixels + squareWidth,
					cursorPositionYInPixels + squareHeight);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(cursorPositionXInPixels, cursorPositionYInPixels
					+ squareHeight);
			GL11.glEnd();
			GL11.glPopMatrix();
		}

		// Actor

		for (Actor actor : actors) {
			actor.imageTexture.bind();
			int actorPositionXInPixels = actor.squareActorIsStandingOn.x
					* (int) squareWidth;
			int actorPositionYInPixels = actor.squareActorIsStandingOn.y
					* (int) squareHeight;

			GL11.glPushMatrix();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(actorPositionXInPixels, actorPositionYInPixels);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(actorPositionXInPixels + squareWidth,
					actorPositionYInPixels);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(actorPositionXInPixels + squareWidth,
					actorPositionYInPixels + squareHeight);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(actorPositionXInPixels, actorPositionYInPixels
					+ squareHeight);
			GL11.glEnd();
			GL11.glPopMatrix();
		}

		// zoom end
		GL11.glPopMatrix();

		// GL11.glScalef(-zoom, -zoom, 0);

		// Clear The Screen And The Depth Buffer
		// GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		//
		// // Color.white.bind();
		// actor.getImageTexture().bind(); // or
		// GL11.glBind(texture.getTextureID())
		//
		// // draw quad
		// GL11.glPushMatrix();
		// GL11.glTranslatef(x, y, 0);
		// // GL11.glRotatef(rotation, 0f, 0f, 1f);
		// GL11.glTranslatef(-x, -y, 0);
		//
		// GL11.glBegin(GL11.GL_QUADS);
		// GL11.glTexCoord2f(0, 0);
		// GL11.glVertex2f(100, 100);
		// GL11.glTexCoord2f(1, 0);
		// GL11.glVertex2f(100 + actor.getImageTexture().getTextureWidth(),
		// 100);
		// GL11.glTexCoord2f(1, 1);
		// GL11.glVertex2f(100 + actor.getImageTexture().getTextureWidth(),
		// 100 + actor.getImageTexture().getTextureHeight());
		// GL11.glTexCoord2f(0, 1);
		// GL11.glVertex2f(100, 100 +
		// actor.getImageTexture().getTextureHeight());
		// GL11.glEnd();
		// GL11.glPopMatrix();
	}

	public static void main(String[] argv) {
		Game timerExample = new Game();
		timerExample.start();
	}
}