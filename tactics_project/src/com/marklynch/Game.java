package com.marklynch;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;
import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;
import mdesl.graphics.TextureRegion;
import mdesl.graphics.glutils.FrameBuffer;
import mdesl.graphics.glutils.ShaderProgram;
import mdesl.graphics.text.BitmapFont;
import mdesl.test.Util;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import com.marklynch.editor.Editor;
import com.marklynch.graphics.Light;
import com.marklynch.graphics.Shadow;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.ui.button.Button;

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

	// BLUR
	ShaderProgram blurShader;
	FrameBuffer blurTargetA, blurTargetB;
	public static SpriteBatch blurBatch;
	int BLUR_FBO_SIZE = 1024;
	float blurRadius = 3f;
	float MAX_BLUR = 10f;

	enum DRAW_MODE {
		NORMAL, BLUR, LIGHT, SHADOW
	};

	public static DRAW_MODE drawMode = DRAW_MODE.SHADOW;

	public static float blurTime = 0f;
	public static float blurTimeMax = 1000f;

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
			if (windowWidth != Display.getWidth()
					|| windowHeight != Display.getHeight()) {
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
			Game.fontTexture = new Texture(
					Util.getResource("res/ptsans_00.png"), Texture.NEAREST);

			// in Photoshop, we included a small white box at the bottom
			// right
			// of our font sheet
			// we will use this to draw lines and rectangles within the same
			// batch as our text
			Game.quadTexture = new TextureRegion(Game.fontTexture,
					Game.fontTexture.getWidth() - 2,
					Game.fontTexture.getHeight() - 2, 1, 1);

			Game.font = new BitmapFont(Util.getResource("res/ptsans.fnt"),
					Game.fontTexture);

			initNormalBatch();
			initBlurBatch();
			Light.initLightBatch();
			Shadow.initShadowBatch();
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

		Light.resize();
		Shadow.resize();

		// resize our batch with the new screen size
		normalBatch.resize(Display.getWidth(), Display.getHeight());
		blurBatch.resize(Display.getWidth(), Display.getHeight());

		// whenever our screen resizes, we need to update our uniform
		// program.use();
		// program.setUniformf("resolution", Display.getWidth(),
		// Display.getHeight());
	}

	public void initBlurBatch() {
		try {

			// create our FBOs
			blurTargetA = new FrameBuffer(BLUR_FBO_SIZE, BLUR_FBO_SIZE,
					Texture.LINEAR);
			blurTargetB = new FrameBuffer(BLUR_FBO_SIZE, BLUR_FBO_SIZE,
					Texture.LINEAR);

			// our basic pass-through vertex shader
			final String VERT = Util.readFile(Util
					.getResourceAsStream("res/shadertut/lesson5.vert"));

			// our fragment shader, which does the blur in one direction at a
			// time
			final String FRAG = Util.readFile(Util
					.getResourceAsStream("res/shadertut/lesson5.frag"));

			// create our shader program
			blurShader = new ShaderProgram(VERT, FRAG, SpriteBatch.ATTRIBUTES);

			// Good idea to log any warnings if they exist
			if (blurShader.getLog().length() != 0)
				System.out.println(blurShader.getLog());

			// always a good idea to set up default uniforms...
			blurShader.use();
			blurShader.setUniformf("dir", 0f, 0f); // direction of blur; nil for
													// now
			blurShader.setUniformf("resolution", BLUR_FBO_SIZE); // size of FBO
			// texture
			blurShader.setUniformf("radius", blurRadius); // radius of blur

			blurBatch = new SpriteBatch();
		} catch (Exception e) {
			// simple exception handling...
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void initNormalBatch() {
		try {
			Game.normalBatch = new SpriteBatch();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public void update(int delta) {
		if (editorMode) {
			UserInputEditor.userInput(delta, editor);
			editor.update(delta);
		} else {
			UserInputLevel.userInput(delta, level);
			level.update(delta);
		}

		if (this.drawMode == DRAW_MODE.BLUR) {
			blurTime += delta;
			if (blurTime > blurTimeMax)
				this.drawMode = DRAW_MODE.NORMAL;
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

		if (Game.drawMode == DRAW_MODE.BLUR) {
			blurTime += delta;
			if (blurTime > blurTimeMax)
				Game.drawMode = DRAW_MODE.NORMAL;
		}

		if (drawMode == DRAW_MODE.NORMAL) {
			renderNormal();
		} else if (drawMode == DRAW_MODE.BLUR) {
			renderBlur();
		} else if (drawMode == DRAW_MODE.LIGHT) {
			Light.renderLight();
		} else if (drawMode == DRAW_MODE.SHADOW) {
			Shadow.renderShadow();
		}
	}

	public void renderNormal() {
		activeBatch = normalBatch;
		glClearColor(0.5f, 0.5f, 0.5f, 1f);
		glClear(GL_COLOR_BUFFER_BIT);
		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.setIdentity();
		activeBatch.updateUniforms();
		activeBatch.begin();
		if (editorMode)
			editor.draw();
		else
			level.draw();
		activeBatch.flush();
		activeBatch.end();
	}

	public void renderBlur() {
		activeBatch = blurBatch;

		// RENDER SCENE ()

		// Bind FBO target A
		blurTargetA.begin();
		// Clear FBO A with an opaque colour to minimize blending issues
		glClearColor(0.5f, 0.5f, 0.5f, 1f);
		glClear(GL_COLOR_BUFFER_BIT);
		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.setIdentity();
		activeBatch.updateUniforms();
		// Reset batch to default shader (without blur)
		try {
			activeBatch.setShader(SpriteBatch.getDefaultShader());
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// send the new projection matrix (FBO size) to the default shader
		activeBatch.resize(blurTargetA.getWidth(), blurTargetA.getHeight());
		// now we can start our batch
		activeBatch.begin();
		// render our scene fully to FBO A
		if (editorMode)
			editor.draw();
		else
			level.draw();
		// flush the batch, i.e. render entities to GPU
		activeBatch.flush();
		// After flushing, we can finish rendering to FBO target A
		blurTargetA.end();

		// HORIZONTAL BLUR ()
		// swap the shaders
		// this will send the batch's (FBO-sized) projection matrix to our blur
		// shader
		view.setIdentity();
		activeBatch.updateUniforms();
		activeBatch.setShader(blurShader);
		// ensure the direction is along the X-axis only
		blurShader.setUniformf("dir", 1f, 0f);
		// determine radius of blur based on mouse position
		float blurTimeProgress = 1f - blurTime / blurTimeMax;
		blurShader.setUniformf("radius", blurTimeProgress * MAX_BLUR);
		// start rendering to target B
		blurTargetB.begin();
		// no need to clear since targetA has an opaque background
		// render target A (the scene) using our horizontal blur shader
		// it will be placed into target B
		activeBatch.draw(blurTargetA, 0, 0);
		// flush the batch before ending target B
		activeBatch.flush();
		// finish rendering target B
		blurTargetB.end();
		// now we can render to the screen using the vertical blur shader

		// VERTICAL BLUR()
		// send the screen-size projection matrix to the blurShader
		view.setIdentity();
		activeBatch.updateUniforms();
		activeBatch.resize(Display.getWidth(), Display.getHeight());

		// apply the blur only along Y-axis
		blurShader.setUniformf("dir", 0f, 1f);

		// update Y-axis blur radius based on mouse
		blurShader.setUniformf("radius", blurTimeProgress * MAX_BLUR);

		// draw the horizontally-blurred FBO B to the screen, applying the
		// vertical blur as we go
		activeBatch.draw(blurTargetB, 0, 0);

		activeBatch.end();

	}

	public static void runBlurAnimation() {
		blurTime = 0l;
		drawMode = DRAW_MODE.BLUR;
	}
}