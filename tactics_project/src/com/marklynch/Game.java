package com.marklynch;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.Font;
import java.io.InputStream;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import com.marklynch.editor.Editor;
import com.marklynch.editor.UserInputEditor;
import com.marklynch.graphics.ShadowLight;
import com.marklynch.level.Level;
import com.marklynch.level.UserInputLevel;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.ui.PinWindow;
import com.marklynch.ui.TextBox;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.popups.FullScreenTextBox;
import com.marklynch.utils.Texture;

import mdesl.graphics.SpriteBatch;
import mdesl.graphics.TextureRegion;
import mdesl.graphics.glutils.ShaderProgram;
import mdesl.graphics.text.BitmapFont;
import mdesl.test.Util;

public class Game {

	public static int playerStartPosX = 1;
	public static int playerStartPosY = 1;

	// 104,151 boulder kill do it.
	// Or even stand on that spot urself

	public static final float MINIMUM_TURN_TIME = 500f; // ms

	public static int includableInPath;
	public static int findPath;
	public static int constructPath;
	public static int getEstimatedCost;
	public static int straightLineDistanceTo;
	public static int getAllNeighbourSquaresThatCanBeMovedTo;
	public static int getNeighborsThatCanBeMovedTo;

	// DEBUG
	public static boolean fullVisiblity = false;
	public static boolean drawNodes = false;
	public static boolean areaColors = false;
	public static boolean redHighlightOnRestrictedSquares = false;

	public static boolean showAILines = false;
	public static boolean showTriggerLines = false;
	public static boolean highlightPath = false;;

	// PLAYER START POSITION

	// Shop
	// public static int playerStartPosX = 13;
	// public static int playerStartPosY = 13;

	// Town
	// public static int playerStartPosX = 110;
	// public static int playerStartPosY = 120;

	// Minecart puzzle room
	// public static int playerStartPosX = 101;
	// public static int playerStartPosY = 122;

	// Lake
	// public static int playerStartPosX = 103;
	// public static int playerStartPosY = 34;

	// minor mine
	// public static int playerStartPosX = 292;
	// public static int playerStartPosY = 86;

	// Dining room
	// public static int playerStartPosX = 93;
	// public static int playerStartPosY = 52;

	// Mort
	// public static int playerStartPosX = 280;
	// public static int playerStartPosY = 46;

	// Lodge
	// public static int playerStartPosX = 106;
	// public static int playerStartPosY = 6;

	// Wolf
	// public static int playerStartPosX = 129;
	// public static int playerStartPosY = 7;

	// Burrow in forest
	// public static int playerStartPosX = 186;
	// public static int playerStartPosY = 63;

	//

	// Farm
	// public static int playerStartPosX = 30;
	// public static int playerStartPosY = 70;

	// Between the walls
	// public static int playerStartPosX = 53;
	// public static int playerStartPosY = 21;

	// Square size
	public static float SQUARE_WIDTH = 128f;
	public static float SQUARE_HEIGHT = 128f;

	// CAMERA START POSITION

	// 0,0
	public static float dragX = -(Game.SQUARE_WIDTH * playerStartPosX);
	public static float dragY = -(Game.SQUARE_HEIGHT * playerStartPosY);

	public static float getDragXWithOffset() {
		if (Game.level.cameraFollow)
			return dragX - Game.level.player.getPrimaryAnimation().offsetX;
		else
			return dragX;
	}

	public static float getDragYWithOffset() {
		if (Game.level.cameraFollow)
			return dragY - Game.level.player.getPrimaryAnimation().offsetY;
		else
			return dragY;
	}

	// West Security + Dungeon
	// public static float dragX = -3072;
	// public static float dragY = -192;

	// Morts Mine + dining room
	// public static float dragX = -17500;
	// public static float dragY = -2000;

	// Farm
	// public static float dragX = -1000;
	// public static float dragY = -4000;

	public static float[] zoomLevels = { 1f, 0.75f, 0.5f, 0.25f, 0.15f, 0.1f, 0.05f };
	public static float[] mapZoomLevels = { 1f, 1f, 1f, 1f, 1f, 1f, 1f };
	public static int zoomLevelIndex = 3;
	public static int lastZoomLevelIndex = 3;
	public static float zoom = zoomLevels[zoomLevelIndex];
	public static final int MAP_MODE_ZOOM_LEVEL_INDEX = 6;

	// public static float windowWidth = 800;
	// public static float windowHeight = 600;
	public static float windowWidth = 900;
	public static float windowHeight = 1000;
	public static float halfWindowWidth = windowWidth / 2;
	public static float halfWindowHeight = windowHeight / 2;

	/** time at last frame */
	static long lastFrame;

	/** frames per second */
	public static int fps;
	public static int displayFPS;
	/** last fps time */
	public static long lastFPS;

	long timeBetweenMoveCommands = 1l;
	public static Level level;
	public static Editor editor;
	public static boolean editorMode = false;
	public static float INVENTORY_SQUARE_WIDTH = 64f;
	public static float INVENTORY_SQUARE_HEIGHT = 64f;
	public static float HALF_SQUARE_WIDTH = SQUARE_WIDTH / 2f;
	public static float HALF_SQUARE_HEIGHT = SQUARE_HEIGHT / 2f;
	public static float QUARTER_SQUARE_WIDTH = SQUARE_WIDTH / 4f;
	public static float QUARTER_SQUARE_HEIGHT = SQUARE_HEIGHT / 4f;
	public static float THREE_QUARTERS_SQUARE_WIDTH = QUARTER_SQUARE_WIDTH * 3;
	public static float THREE_QUARTERS_SQUARE_HEIGHT = QUARTER_SQUARE_HEIGHT * 3;

	public static Square squareMouseIsOver;
	public static GameObject gameObjectMouseIsOver;

	public static int delta = 0;

	public static void main(String[] argv) {
		Game game = new Game();
		game.start();
	}

	public boolean pausedUpdatesDisplayNotActive = false;
	public static boolean displayActive = true;
	public static int ticksSinceDisplayInactive = 0;

	public static TextureRegion quadTexture;

	public static Texture fontTexture;

	// a simple font to play with
	public static BitmapFont smallFont;
	public static BitmapFont largeFont;

	public static SpriteBatch activeBatch;
	public static ShaderProgram program;

	public static SpriteBatch normalBatch;

	public static Button oldButtonHoveringOver = null;
	public static Button buttonMouseIsOver = null;
	public static InventorySquare inventorySquareMouseIsOver = null;
	public static PinWindow pinWindowHoveringOver;
	public static FullScreenTextBox fullScreenTextBoxHoveringOver = null;
	public static TextBox textBoxHoveringOver;

	public void start() {

		initGL(windowWidth, windowHeight); // init OpenGL
		init();
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer
		// Save.save();
		// Load.load();

		while (!Display.isCloseRequested()) {

			boolean wasActive = displayActive;

			if (!Display.isActive()) {
				// pausedUpdatesDisplayNotActive = true;
				// displayActive = false;
				ticksSinceDisplayInactive = 0;
			} else {
				pausedUpdatesDisplayNotActive = false;
				displayActive = true;

				if (ticksSinceDisplayInactive < 60)
					ticksSinceDisplayInactive++;
			}

			delta = getDelta();

			// Resize?
			if (windowWidth != Display.getWidth() || windowHeight != Display.getHeight()) {
				resize();
			}

			if (!pausedUpdatesDisplayNotActive) {

				Game.includableInPath = 0;
				Game.findPath = 0;
				Game.constructPath = 0;
				Game.getEstimatedCost = 0;
				Game.straightLineDistanceTo = 0;
				Game.getAllNeighbourSquaresThatCanBeMovedTo = 0;
				Game.getNeighborsThatCanBeMovedTo = 0;
				update(delta);
			}

			// if (!displayActive && wasActive)
			// drawInactive();
			// else
			if (displayActive)
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
		// if (editorMode)
		editor = new Editor();
		// else {
		// level = new Level(10, 10);
		//
		// level.fullQuestList.makeQuests();
		// AreaList.buildAreas();
		//
		// for (int i = 0; i < Game.level.squares.length; i++) {
		// for (int j = 0; j < Game.level.squares[0].length; j++) {
		// Game.level.squares[i][j].afterContructor();
		// }
		// }
		// }

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

	public static TrueTypeFont font1;
	public static TrueTypeFont font2;
	public static Random random = new Random();

	private void initGL(float width, float height) {
		try {
			Display.setDisplayMode(new DisplayMode((int) width, (int) height));
			Display.setResizable(true);
			Display.setLocation(500, 0);
//			Display.setLocation(1000, 0);
			// Display.setLocation(1000, -1080);
			Display.create();
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);// copied for FontExample
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);// copied for FontExample

		GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
		GL11.glClearDepth(1);// copied for FontExample

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
			Game.fontTexture = new Texture(Util.getResource("res/ptsans_00.png"), Texture.NEAREST, false);

			Game.smallFont = new BitmapFont(Util.getResource("res/ptsans.fnt"), Game.fontTexture);
			System.out.println("CREATED FONT");

			// in Photoshop, we included a small white box at the bottom
			// right
			// of our font sheet
			// we will use this to draw lines and rectangles within the same
			// batch as our text
			Game.quadTexture = new TextureRegion(Game.fontTexture, Game.fontTexture.getWidth() - 2,
					Game.fontTexture.getHeight() - 2, 1, 1);

			Texture fontTexture2 = new Texture(Util.getResource("res/ptsans_00.png"), Texture.NEAREST, false);
			largeFont = new BitmapFont(Util.getResource("res/ptsans.fnt"), fontTexture2);

			// load a default java font
			Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
			font1 = new TrueTypeFont(awtFont, false);

			// load font from a .ttf file
			try {
				InputStream inputStream = ResourceLoader.getResourceAsStream("res/fonts/myfont.ttf");

				Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
				awtFont2 = awtFont2.deriveFont(24f); // set font size
				font2 = new TrueTypeFont(awtFont2, false);

			} catch (Exception e) {
				e.printStackTrace();
			}

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
		halfWindowWidth = Display.getWidth() / 2;
		halfWindowHeight = Display.getHeight() / 2;
		glViewport(0, 0, Display.getWidth(), Display.getHeight());

		ShadowLight.resize();

		if (editor != null)
			editor.resize();

		if (level != null) {
			level.resize();
		}

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
			Display.setTitle("Eclipse");
			displayFPS = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public void render() {
		ShadowLight.draw();
	}

	public static void flush() {
		activeBatch.flush();
	}
}