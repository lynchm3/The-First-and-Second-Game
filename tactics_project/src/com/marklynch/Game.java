package com.marklynch;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.unit.Actor;

public class Game {

	/** position of quad */
	int actorPositionX = 0, actorPositionY = 0;
	/** angle of quad rotation */

	/** time at last frame */
	long lastFrame;

	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	long lastMoveTime = 0l;
	long timeBetweenMoveCommands = 1l;

	Actor actor;
	Level level;
	float squareWidth = 64f;
	float squareHeight = 64f;

	boolean keyStateLeft = false;
	boolean keyStateRight = false;
	boolean keyStateUp = false;
	boolean keyStateDown = false;

	float mouseX;
	float mouseY;
	boolean mouseButtonStateLeft = false;
	boolean mouseButtonStateRight = false;

	int windowWidth = 640;
	int windowHeight = 640;

	float zoom = 1.0f;

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
		boolean moved = false;

		mouseX = Mouse.getX();
		mouseY = Mouse.getY();
		zoom += 0.001 * Mouse.getDWheel();
		if(zoom < 0.5)
			zoom = 0.5f;
		if(zoom > 2)
			zoom = 2f;
//		int dWheel = Mouse.getDWheel();
//		if (dWheel != 0)
//			System.out.println(dWheel);

		if (mouseButtonStateLeft == false && Mouse.isButtonDown(0)) {
			actorPositionX = (int) (((windowWidth/2 - (windowWidth/2)/zoom) + (mouseX / zoom)) / squareWidth);
			actorPositionY = (int) (((windowHeight/2 - (windowHeight/2)/zoom) + ((windowHeight - mouseY) / zoom)) / squareHeight);
			lastMoveTime = lastFPS;
			mouseButtonStateLeft = true;
			moved = true;
		} else if (!Mouse.isButtonDown(0)) {
			mouseButtonStateLeft = false;
		}

		if (keyStateLeft == false && Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			actorPositionX -= 1;
			lastMoveTime = lastFPS;
			keyStateLeft = true;
			moved = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			keyStateLeft = false;
		}

		if (keyStateRight == false && Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			actorPositionX += 1;
			lastMoveTime = lastFPS;
			keyStateRight = true;
			moved = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			keyStateRight = false;
		}

		if (keyStateUp == false && Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			actorPositionY -= 1;
			lastMoveTime = lastFPS;
			keyStateUp = true;
			moved = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			keyStateUp = false;
		}

		if (keyStateDown == false && Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			actorPositionY += 1;
			lastMoveTime = lastFPS;
			keyStateDown = true;
			moved = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			keyStateDown = false;
		}

		// keep char on the screen
		if (moved) {
			if (actorPositionX < 0)
				actorPositionX = 0;
			if (actorPositionX > 9)
				actorPositionX = 9;
			if (actorPositionY < 0)
				actorPositionY = 0;
			if (actorPositionY > 9)
				actorPositionY = 9;
		}
		// }

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
		actor = new Actor(0, 0, 0, 0, "marlene.png");
		level = new Level(10, 10);
	}

	public void renderGL() {

		// Clear The Screen And The Depth Buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glPushMatrix();
		GL11.glTranslatef(windowWidth/2, windowHeight/2, 0);
		GL11.glScalef(zoom, zoom, 0);
		GL11.glTranslatef(-windowWidth/2, -windowHeight/2, 0);
		// GL11.glTranslatef(x, y, 0);
		// // GL11.glRotatef(rotation, 0f, 0f, 1f);
		// GL11.glTranslatef(-x, -y, 0);

		for (int i = 0; i < level.getWidth(); i++) {
			for (int j = 0; j < level.getHeight(); j++) {
				// is it better to bind once and draw all the same ones
				level.getSquares()[i][j].getImageTexture().bind(); // or
																	// GL11.glBind(texture.getTextureID());

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

		actor.getImageTexture().bind(); // or
										// GL11.glBind(texture.getTextureID());

		// // draw quad

		int actorPositionXInPixels = actorPositionX * (int) squareWidth;
		int actorPositionYInPixels = actorPositionY * (int) squareHeight;

		GL11.glPushMatrix();

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(actorPositionXInPixels, actorPositionYInPixels);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(actorPositionXInPixels
				+ actor.getImageTexture().getTextureWidth(),
				actorPositionYInPixels);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(actorPositionXInPixels
				+ actor.getImageTexture().getTextureWidth(),
				actorPositionYInPixels
						+ actor.getImageTexture().getTextureHeight());
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(actorPositionXInPixels, actorPositionYInPixels
				+ actor.getImageTexture().getTextureHeight());
		GL11.glEnd();
		GL11.glPopMatrix();

		// zoom
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