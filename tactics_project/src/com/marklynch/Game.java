package com.marklynch;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import com.marklynch.editor.Editor;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.utils.ResourceUtils;

public class Game {

	/** time at last frame */
	static long lastFrame;

	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	long timeBetweenMoveCommands = 1l;
	Level level;
	Editor editor;
	boolean editorMode = true;
	public static float SQUARE_WIDTH = 128f;
	public static float SQUARE_HEIGHT = 128f;

	public static float windowWidth = 800;
	public static float windowHeight = 800;
	// public static int windowWidth = 400;
	// public static int windowHeight = 400;

	public static int delta = 0;

	public static void main(String[] argv) {
		Game game = new Game();
		game.start();
	}

	public boolean paused = false;

	public void start() {

		initGL(windowWidth, windowHeight); // init OpenGL
		init();
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer

		while (!Display.isCloseRequested()) {

			if (!Display.isActive())
				paused = true;
			else
				paused = false;

			delta = getDelta();

			// So... these work...
			if (windowWidth != Display.getWidth()
					|| windowHeight != Display.getHeight()) {
				windowWidth = Display.getWidth();
				windowHeight = Display.getHeight();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

				GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
				GL11.glMatrixMode(GL11.GL_MODELVIEW);

				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1,
						-1);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
			}

			if (!paused)
				update(delta);

			renderGL();

			Display.update();
			Display.sync(60); // cap fps to 60fps

			if (editorMode) {

			} else {
				if (level.ended) {
					Display.destroy();
					break;
				}
			}
		}

	}

	// GUI gui;
	// protected LWJGLRenderer renderer;
	//
	// private FPSCounter fpsCounter;
	// private Button[] buttons;

	public boolean quit;

	public static TrueTypeFont font20;

	public void init() {
		// Level
		if (editorMode)
			editor = new Editor();
		else
			level = new Level(10, 10);
		Game.font20 = ResourceUtils.getGlobalFont("KeepCalm-Medium.ttf", 20);

		// LWJGLRenderer renderer = null;
		// try {
		// renderer = new LWJGLRenderer();
		// } catch (LWJGLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// SimpleGameMenu gameUI = new SimpleGameMenu();
		// // gui = new GUI(gameUI, renderer);
		//
		// ThemeManager theme = null;
		// try {
		// theme = ThemeManager.createThemeManager(
		// SimpleGameMenu.class.getResource("simpleGameMenu.xml"),
		// renderer);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// gui.applyTheme(theme);
	}

	private void initGL(float width, float height) {
		try {
			Display.setDisplayMode(new DisplayMode((int) width, (int) height));
			Display.setResizable(true);
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

		GL11.glViewport(0, 0, (int) width, (int) height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public void update(int delta) {
		if (editorMode) {
			UserInputEditor.userInput(delta, editor);
			editor.update(delta);
		} else {
			UserInputLevel.userInput(delta, level);
			level.update(delta);
		}

		updateFPS(); // update FPS Counter
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
		if (editorMode)
			editor.draw();
		else
			level.draw();
	}
}