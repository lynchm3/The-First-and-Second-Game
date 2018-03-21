package com.marklynch.graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.marklynch.Game;
import com.marklynch.level.Level.LevelMode;
import com.marklynch.objects.actions.Action;
import com.marklynch.utils.Color;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.SpriteBatch;
import mdesl.graphics.glutils.FrameBuffer;
import mdesl.graphics.glutils.ShaderProgram;
import mdesl.graphics.text.BitmapFont;
import mdesl.test.Util;

public class ShadowLight {

	// LIGHT

	public static Texture rock;
	public static Texture rockNormals;

	static ShaderProgram lightShader;
	public static final float DEFAULT_LIGHT_Z = 0.075f;
	public static final Vector4f LIGHT_COLOR = new Vector4f(1f, 1f, 1f, 1f);
	public static final Vector4f AMBIENT_COLOR = new Vector4f(1f, 1f, 1f, 1f);
	public static final Vector3f FALLOFF = new Vector3f(.4f, 3f, 20f);
	public static final Vector3f lightPos = new Vector3f(0f, 0f, DEFAULT_LIGHT_Z);
	static FrameBuffer lightsFBO;

	// SHADOW

	private final static float lightSize = 100;
	static SpriteBatch batch;
	static BitmapFont font;
	static FrameBuffer occludersFBO;
	static FrameBuffer shadowMapFBO;
	static ShaderProgram shadowMapShader;
	static ShaderProgram shadowRenderShader;
	static ArrayList<Light> lights = new ArrayList<Light>();
	static boolean softShadows = true;

	// BLUR
	static ShaderProgram blurShader;
	static FrameBuffer blur2FBO, blur3FBO;
	static int BLUR_FBO_SIZE = 1024;
	static float blurRadius = 3f;
	static float MAX_BLUR = 10f;
	public static float blurTime = 0f;
	public static float blurTimeMax = 1000f;

	boolean blur = true;

	public static void init() {

		// LIGHT init
		try {
			// load our texture with linear filter
			rock = new Texture(Util.getResource("res/rock.png"), Texture.LINEAR, false);
			rockNormals = new Texture(Util.getResource("res/rock_n.png"), Texture.LINEAR, false);
		} catch (IOException e) {
			throw new RuntimeException("couldn't decode texture");
		}

		// load our shader program and sprite batch
		try {
			// our basic pass-through vertex shader
			final String VERT = Util.readFile(Util.getResourceAsStream("res/shadertut/lesson6.vert"));

			// our fragment shader, which does the blur in one direction at a
			// time
			final String FRAG = Util.readFile(Util.getResourceAsStream("res/shadertut/lesson6.frag"));

			// create our shader program
			ShaderProgram.setStrictMode(false);
			lightShader = new ShaderProgram(VERT, FRAG, SpriteBatch.ATTRIBUTES);

			// Good idea to log any warnings if they exist
			if (lightShader.getLog().length() != 0)
				System.out.println(lightShader.getLog());

			// always a good idea to set up default uniforms...
			lightShader.use();
			// our normal map
			lightShader.setUniformi("u_normals", 1); // GL_TEXTURE1
			// light/ambient colors
			lightShader.setUniformf("LightColor", LIGHT_COLOR);
			lightShader.setUniformf("AmbientColor", AMBIENT_COLOR);
			lightShader.setUniformf("Falloff", FALLOFF);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		// SHADOW Init
		try {
			batch = new SpriteBatch();
			ShaderProgram.setStrictMode(false);
			shadowMapShader = createShader(Util.readFile(Util.getResourceAsStream("res/shadertut/shadow_pass.vert")),
					Util.readFile(Util.getResourceAsStream("res/shadertut/shadow_map.frag")));
			shadowRenderShader = createShader(Util.readFile(Util.getResourceAsStream("res/shadertut/shadow_pass.vert")),
					Util.readFile(Util.getResourceAsStream("res/shadertut/shadow_render.frag")));
			occludersFBO = new FrameBuffer((int) lightSize, (int) lightSize, Texture.LINEAR);
			shadowMapFBO = new FrameBuffer((int) lightSize, 1, Texture.LINEAR);

			lightsFBO = new FrameBuffer(Display.getWidth(), Display.getHeight(), Texture.LINEAR);

			mdesl.graphics.Texture shadowMapTex = shadowMapFBO.getTexture();
			shadowMapTex.setFilter(Texture.LINEAR, Texture.LINEAR);
			shadowMapTex.setWrap(Texture.REPEAT);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		// Blur init

		try {

			// create our FBOs
			blur2FBO = new FrameBuffer(BLUR_FBO_SIZE, BLUR_FBO_SIZE, Texture.LINEAR);
			blur3FBO = new FrameBuffer(BLUR_FBO_SIZE, BLUR_FBO_SIZE, Texture.LINEAR);

			// our basic pass-through vertex shader
			final String VERT = Util.readFile(Util.getResourceAsStream("res/shadertut/lesson5.vert"));

			// our fragment shader, which does the blur in one direction at a
			// time
			final String FRAG = Util.readFile(Util.getResourceAsStream("res/shadertut/lesson5.frag"));

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
		} catch (Exception e) {
			// simple exception handling...
			e.printStackTrace();
			System.exit(0);
		}

		clearLights();
	}

	public static void draw() {

		Game.activeBatch = batch;

		Game.activeBatch.begin();

		glClearColor(0.5f, 0.5f, 0.5f, 1f);
		glClear(GL_COLOR_BUFFER_BIT);

		// lightsFBO.begin();
		// GL11.glEnable(GL11.GL_BLEND);
		// GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		// glClearColor(0.5f, 0.5f, 0.5f, 1f);
		// glClear(GL_COLOR_BUFFER_BIT);

		// code for mouse light, spot light, spotlight, mouselight
		// float mouseXTransformed = (((Game.windowWidth / 2) - Game.getDragX()
		// -
		// (Game.windowWidth / 2) / Game.zoom)
		// + (Mouse.getX()) / Game.zoom);
		// float mouseYTransformed = ((Game.windowHeight / 2 - Game.getDragY() -
		// (Game.windowHeight / 2) / Game.zoom)
		// + (((Game.windowHeight - Mouse.getY())) / Game.zoom));
		// float x = mouseXTransformed;
		// float y = mouseYTransformed;
		// lights.get(0).x = x;
		// lights.get(0).y = y;
		// lightPos.x = Mouse.getX() / (float) Display.getWidth();
		// lightPos.y = Mouse.getY() / (float) Display.getHeight();

		// Bump map shader
		Game.activeBatch.setShader(lightShader);
		// lightShader.setUniformf("LightPos", lightPos);
		lightShader.setUniformf("Resolution", Display.getWidth(), Display.getHeight());

		Game.activeBatch.setColor(1, 1, 1, 1);
		Matrix4f view = Game.activeBatch.getViewMatrix();

		// Draw level BG

		// TextureUtils.drawTexture(Game.level.textureUndiscovered, 1f, 0,
		// 1920,
		// 0, 1080);
		// Game.flush();

		// Draw BG
		view.setIdentity();
		view.translate(new Vector2f(Game.windowWidth / 2, Game.windowHeight / 2));
		view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
		view.translate(new Vector2f(-Game.windowWidth / 2, -Game.windowHeight / 2));
		view.translate(new Vector2f(Game.getDragXWithOffset(), Game.getDragYWithOffset()));
		Game.activeBatch.updateUniforms();

		if (Game.level.levelMode == LevelMode.LEVEL_MODE_FISHING) {
			Game.level.player.draw1();
			Game.level.player.fishingTarget.draw1();
			Game.flush();
			Game.activeBatch.end();
			return;
		}

		if (Game.editorMode)
			Game.level.drawBackground();
		else
			Game.level.drawBackground();
		Game.flush();

		// Draw foreground
		if (Game.zoomLevelIndex < Game.MAP_MODE_ZOOM_LEVEL_INDEX) {
			Game.activeBatch.resize(Display.getWidth(), Display.getHeight());
			Game.activeBatch.getViewMatrix().setIdentity();
			Game.activeBatch.updateUniforms();
			Game.activeBatch.setShader(lightShader);
			Game.activeBatch.setColor(1, 1, 1, 1);
			view.setIdentity();
			view.translate(new Vector2f(Game.windowWidth / 2, Game.windowHeight / 2));
			view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
			view.translate(new Vector2f(-Game.windowWidth / 2, -Game.windowHeight / 2));
			view.translate(new Vector2f(Game.getDragXWithOffset(), Game.getDragYWithOffset()));
			Game.activeBatch.updateUniforms();
			if (Game.editorMode)
				Game.level.drawForeground();
			else
				Game.level.drawForeground();
			Game.flush();
		}

		// Draw level UI
		view.setIdentity();
		view.translate(new Vector2f(Game.windowWidth / 2, Game.windowHeight / 2));
		view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
		view.translate(new Vector2f(-Game.windowWidth / 2, -Game.windowHeight / 2));
		view.translate(new Vector2f(Game.getDragXWithOffset(), Game.getDragYWithOffset()));
		Game.activeBatch.updateUniforms();
		try {
			Game.activeBatch.setShader(SpriteBatch.getDefaultShader());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Game.activeBatch.setColor(1, 1, 1, 1);
		Game.level.drawUI();
		Game.flush();

		if (Game.editorMode) {
			// Draw editor overlay
			try {
				Game.activeBatch.setShader(SpriteBatch.getDefaultShader());
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
			view.setIdentity();
			view.translate(new Vector2f(Game.windowWidth / 2, Game.windowHeight / 2));
			view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
			view.translate(new Vector2f(-Game.windowWidth / 2, -Game.windowHeight / 2));
			view.translate(new Vector2f(Game.getDragXWithOffset(), Game.getDragYWithOffset()));
			Game.activeBatch.updateUniforms();
			Game.editor.drawOverlay();
			Game.flush();

			// Draw Editor UI
			try {
				Game.activeBatch.setShader(SpriteBatch.getDefaultShader());
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
			view.setIdentity();
			Game.activeBatch.updateUniforms();
			Game.activeBatch.setColor(1, 1, 1, 1);
			Game.editor.drawUI();
			Game.flush();
		}

		/// START SKY
		// Matrix4f view = Game.activeBatch.getViewMatrix();
		if (Game.zoomLevelIndex > 3) {
			view.setIdentity();
			view.translate(new Vector2f(Game.windowWidth / 2, Game.windowHeight / 2));
			view.scale(new Vector3f(Game.zoomLevels[Game.zoomLevelIndex - 1], Game.zoomLevels[Game.zoomLevelIndex - 1],
					1f));
			view.translate(new Vector2f(-Game.windowWidth / 2, -Game.windowHeight / 2));
			view.translate(new Vector2f(Game.getDragXWithOffset(), Game.getDragYWithOffset()));

			Game.activeBatch.updateUniforms();
			// System.out.println("Map draw Game.getDragX() = " +
			// Game.getDragX() + ",
			// Game.getDragY() = " + Game.getDragY());
			TextureUtils.drawTexture(Action.textureBird, 3000, 1000, 3000 + 128, 1000 + 128);
			Game.flush();
		}
		////////// END SKY

		Game.activeBatch.end();
	}

	static void renderLight(Light light, FrameBuffer frameBuffer) {
		occlusion(light);
		shadowMap();
		renderShadows(light, frameBuffer);
	}

	public static void occlusion(Light light) {
		occludersFBO.begin();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		glClearColor(0f, 0f, 0f, 0f);
		glClear(GL_COLOR_BUFFER_BIT);
		try {
			batch.setShader(SpriteBatch.getDefaultShader());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		batch.resize(occludersFBO.getWidth(), occludersFBO.getHeight());
		Matrix4f view = Game.activeBatch.getViewMatrix();
		// view.setIdentity();

		view.translate(new Vector2f(-(light.x - lightSize / 2f), -(light.y - lightSize / 2f)));
		batch.updateUniforms();

		// update the new view matrix
		// Game.activeBatch.updateUniforms();

		if (Game.editorMode)
			Game.level.drawForeground();
		else
			Game.level.drawForeground();
		batch.flush();
		view.setIdentity();
		// update the new view matrix
		Game.activeBatch.updateUniforms();
		occludersFBO.end();
		// batch.end();
	}

	public static void shadowMap() {
		shadowMapFBO.begin();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		glClearColor(0f, 0f, 0f, 0f);
		glClear(GL_COLOR_BUFFER_BIT);
		batch.setShader(shadowMapShader);
		shadowMapShader.use();
		shadowMapShader.setUniformf("resolution", lightSize, lightSize);
		batch.resize(shadowMapFBO.getWidth(), shadowMapFBO.getHeight());
		batch.getViewMatrix().setIdentity();
		batch.updateUniforms();
		batch.setColor(Color.WHITE);
		batch.draw(occludersFBO.getTexture(), 0, 0, lightSize, shadowMapFBO.getHeight());
		batch.flush();
		shadowMapFBO.end();
		// batch.end();
	}

	public static void renderShadows(Light light, FrameBuffer frameBuffer) {
		// glClearColor(0.5f, 0.5f, 0.5f, 1f);
		// glClear(GL_COLOR_BUFFER_BIT);

		if (frameBuffer != null) {
			frameBuffer.begin();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
		batch.setShader(shadowRenderShader);
		shadowRenderShader.use();
		shadowRenderShader.setUniformf("resolution", lightSize, lightSize);
		shadowRenderShader.setUniformf("softShadows", softShadows ? 1f : 0f);
		batch.setColor(light.color);
		batch.resize(Display.getWidth(), Display.getHeight());

		Matrix4f view = Game.activeBatch.getViewMatrix();
		// view.setIdentity();

		view.translate(new Vector2f(Display.getWidth() / 2, Display.getHeight() / 2));
		view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
		view.translate(new Vector2f(-Display.getWidth() / 2, -Display.getHeight() / 2));
		view.translate(new Vector2f(Game.getDragXWithOffset(), Game.getDragYWithOffset()));
		// batch.begin();
		// view.translate(new Vector2f(-(light.x - lightSize / 2f),
		// -(light.y - lightSize / 2f)));
		batch.updateUniforms();

		// batch.begin();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		batch.draw(shadowMapFBO.getTexture(), light.x - lightSize / 2f, light.y - lightSize / 2f, lightSize, lightSize);
		batch.flush();
		if (frameBuffer != null) {
			frameBuffer.end();
		}
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Game.activeBatch.setColor(1, 1, 1, 1);
		// batch.end();
	}

	static void clearLights() {
		lights.clear();
		lights.add(new Light(128, 128, Color.WHITE));
		// lights.add(new Light(200, 200, Color.BLUE));
	}

	public static ShaderProgram createShader(String vert, String frag) {
		ShaderProgram prog = null;
		try {
			prog = new ShaderProgram(vert, frag, SpriteBatch.ATTRIBUTES);
			if (prog.getLog().length() != 0)
				System.out.println(prog.getLog());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prog;
	}

	static class Light {

		float x, y;
		Color color;

		public Light(float x, float y, Color color) {
			this.x = x;
			this.y = y;
			this.color = color;
		}
	}

	public static void resize() {
		batch.resize(Display.getWidth(), Display.getHeight());
	}

	// public static void renderBlur() {
	// Matrix4f view = Game.activeBatch.getViewMatrix();
	//
	// blur2FBO.begin();
	// view.setIdentity();
	// Game.activeBatch.updateUniforms();
	// Game.activeBatch.setShader(blurShader);
	// blurShader.setUniformf("dir", 1f, 0f);
	// blurShader.setUniformf("radius", 2);
	// GL11.glEnable(GL_DEPTH_TEST);
	// Game.activeBatch.draw(lightsFBO, 0, 0);
	// GL11.glDisable(GL_DEPTH_TEST);
	// Game.flush();
	// blur2FBO.end();
	//
	// // VERTICAL BLUR()
	// blur3FBO.begin();
	// view.setIdentity();
	// Game.activeBatch.updateUniforms();
	// Game.activeBatch.resize(Display.getWidth(), Display.getHeight());
	// blurShader.setUniformf("dir", 0f, 1f);
	// blurShader.setUniformf("radius", 2);
	// Game.activeBatch.draw(blur2FBO, 0, 0);
	// Game.flush();
	// blur3FBO.end();
	// }
}
