package com.marklynch;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.marklynch.editor.Editor;
import com.marklynch.graphics.ShadowLight;
import com.marklynch.tactics.objects.Inventory;
import com.marklynch.tactics.objects.InventorySquare;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.ui.button.Button;

import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;
import mdesl.graphics.TextureRegion;
import mdesl.graphics.glutils.ShaderProgram;
import mdesl.graphics.text.BitmapFont;
import mdesl.test.Util;

public class Game {

	/** time at last frame */
	static long lastFrame;

	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	long timeBetweenMoveCommands = 1l;
	public static Level level;
	public static Editor editor;
	public static boolean editorMode = true;
	public static float SQUARE_WIDTH = 128f;
	public static float SQUARE_HEIGHT = 128f;

	public static float windowWidth = 800;
	public static float windowHeight = 600;

	public static Square squareMouseIsOver;
	public static float dragY = -100;
	public static float dragX = 300;
	public static float zoom = 1f;

	public static int delta = 0;

	public static void main(String[] argv) {
		Game game = new Game();
		game.start();
	}

	public boolean paused = false;

	public static TextureRegion quadTexture;

	public static Texture fontTexture;

	// a simple font to play with
	public static BitmapFont font;

	public static SpriteBatch activeBatch;
	public static ShaderProgram program;

	public static SpriteBatch normalBatch;

	public static Button buttonHoveringOver = null;

	public static Inventory inventoryHoveringOver = null;
	public static InventorySquare inventorySquareMouseIsOver = null;

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

			// Resize?
			if (windowWidth != Display.getWidth() || windowHeight != Display.getHeight()) {
				resize();
			}

			if (!paused)
				update(delta);

			render();

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

	public void init() {
		// Level
		if (editorMode)
			editor = new Editor();
		else
			level = new Level(10, 10);

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
			GL11.glEnable(GL11.GL_TEXTURE_2D);

			GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		GL11.glDisable(GL_DEPTH_TEST);
		// enable alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glViewport(0, 0, (int) width, (int) height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		// load our shader program and sprite batch
		try {
			Game.fontTexture = new Texture(Util.getResource("res/ptsans_00.png"), Texture.NEAREST);

			// in Photoshop, we included a small white box at the bottom
			// right
			// of our font sheet
			// we will use this to draw lines and rectangles within the same
			// batch as our text
			Game.quadTexture = new TextureRegion(Game.fontTexture, Game.fontTexture.getWidth() - 2,
					Game.fontTexture.getHeight() - 2, 1, 1);

			Game.font = new BitmapFont(Util.getResource("res/ptsans.fnt"), Game.fontTexture);

			ShadowLight.init();
			resize();
		} catch (Exception e) {
			// simple exception handling...
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void resize() {
		windowWidth = Display.getWidth();
		windowHeight = Display.getHeight();
		glViewport(0, 0, Display.getWidth(), Display.getHeight());

		ShadowLight.resize();

		if (editor != null)
			editor.resize();

		// whenever our screen resizes, we need to update our uniform
		// program.use();
		// program.setUniformf("resolution", Display.getWidth(),
		// Display.getHeight());
	}

	public void update(int delta) {
		if (editorMode) {
			UserInputEditor.userInput(delta, editor);
			editor.update(delta);
		} else {
			UserInputLevel.userInput(delta);
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

	public void render() {
		ShadowLight.render();
	}
}