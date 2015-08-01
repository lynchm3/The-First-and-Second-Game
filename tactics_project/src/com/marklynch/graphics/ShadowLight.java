package com.marklynch.graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.io.IOException;
import java.util.ArrayList;

import mdesl.graphics.Color;
import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;
import mdesl.graphics.glutils.FrameBuffer;
import mdesl.graphics.glutils.ShaderProgram;
import mdesl.graphics.text.BitmapFont;
import mdesl.test.Util;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.marklynch.Game;

public class ShadowLight {

	// LIGHT

	public static Texture rock;
	public static Texture rockNormals;

	static ShaderProgram lightShader;
	public static final float DEFAULT_LIGHT_Z = 0.075f;
	public static final Vector4f LIGHT_COLOR = new Vector4f(1f, 0.8f, 0.6f, 1f);
	public static final Vector4f AMBIENT_COLOR = new Vector4f(0.6f, 0.6f, 1f,
			0.2f);
	public static final Vector3f FALLOFF = new Vector3f(.4f, 3f, 20f);
	public static final Vector3f lightPos = new Vector3f(0f, 0f,
			DEFAULT_LIGHT_Z);

	// SHADOW

	private final static float lightSize = 500;
	static SpriteBatch batch;
	static BitmapFont font;
	static FrameBuffer occludersFBO;
	static FrameBuffer shadowMapFBO;
	static ShaderProgram shadowMapShader;
	static ShaderProgram shadowRenderShader;
	static ArrayList<Light> lights = new ArrayList<Light>();
	static boolean softShadows = true;

	public static void initBatch() {

		// LIGHT

		try {
			// load our texture with linear filter
			rock = new Texture(Util.getResource("res/rock.png"), Texture.LINEAR);
			rockNormals = new Texture(Util.getResource("res/rock_n.png"),
					Texture.LINEAR);
		} catch (IOException e) {
			throw new RuntimeException("couldn't decode texture");
		}

		// load our shader program and sprite batch
		try {
			// our basic pass-through vertex shader
			final String VERT = Util.readFile(Util
					.getResourceAsStream("res/shadertut/lesson6.vert"));

			// our fragment shader, which does the blur in one direction at a
			// time
			final String FRAG = Util.readFile(Util
					.getResourceAsStream("res/shadertut/lesson6.frag"));

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
			// simple exception handling...
			e.printStackTrace();
			System.exit(0);
		}

		// SHADOW

		try {
			batch = new SpriteBatch();
			ShaderProgram.setStrictMode(false);
			shadowMapShader = createShader(
					Util.readFile(Util
							.getResourceAsStream("res/shadertut/shadow_pass.vert")),
					Util.readFile(Util
							.getResourceAsStream("res/shadertut/shadow_map.frag")));
			shadowRenderShader = createShader(
					Util.readFile(Util
							.getResourceAsStream("res/shadertut/shadow_pass.vert")),
					Util.readFile(Util
							.getResourceAsStream("res/shadertut/shadow_render.frag")));
			// casterSprites = new Texture(Util.getResource("res/cat4.png"),
			// Texture.LINEAR);
			occludersFBO = new FrameBuffer((int) lightSize, (int) lightSize,
					Texture.LINEAR);
			shadowMapFBO = new FrameBuffer((int) lightSize, 1, Texture.LINEAR);
			Texture shadowMapTex = shadowMapFBO.getTexture();
			shadowMapTex.setFilter(Texture.LINEAR, Texture.LINEAR);
			shadowMapTex.setWrap(Texture.REPEAT);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		clearLights();
	}

	public static void renderShadow() {

		Game.activeBatch = batch;

		Game.activeBatch.begin();

		glClearColor(0.5f, 0.5f, 0.5f, 1f);
		glClear(GL_COLOR_BUFFER_BIT);

		float mouseXTransformed = (((Game.windowWidth / 2) - Game.dragX - (Game.windowWidth / 2)
				/ Game.zoom) + (Mouse.getX()) / Game.zoom);
		float mouseYTransformed = ((Game.windowHeight / 2 - Game.dragY - (Game.windowHeight / 2)
				/ Game.zoom) + (((Game.windowHeight - Mouse.getY())) / Game.zoom));
		float x = mouseXTransformed;
		float y = mouseYTransformed;
		lights.get(0).x = x;
		lights.get(0).y = y;
		// reset the matrix to identity, i.e. "no camera transform"
		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.setIdentity();

		view.translate(new Vector2f(Game.windowWidth / 2, Game.windowHeight / 2));
		view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
		view.translate(new Vector2f(-Game.windowWidth / 2,
				-Game.windowHeight / 2));
		view.translate(new Vector2f(Game.dragX, Game.dragY));

		lightPos.x = x;
		lightPos.y = y;

		// send a Vector4f to GLSL
		Game.activeBatch.setShader(lightShader);
		lightShader.setUniformf("LightPos", lightPos);
		lightShader.setUniformf("Resolution", Display.getWidth(),
				Display.getHeight());

		// update the new view matrix
		Game.activeBatch.updateUniforms();

		if (Game.editorMode)
			Game.editor.level.drawBackground();
		else
			Game.level.drawBackground();

		// Draw lights
		Game.activeBatch.setColor(Color.WHITE);
		Game.activeBatch.resize(Display.getWidth(), Display.getHeight());
		Game.activeBatch.getViewMatrix().setIdentity();
		Game.activeBatch.updateUniforms();
		for (int i = 0; i < lights.size(); i++) {
			renderLight(lights.get(i));
		}

		// draw lvl
		Game.activeBatch.resize(Display.getWidth(), Display.getHeight());
		Game.activeBatch.getViewMatrix().setIdentity();
		Game.activeBatch.updateUniforms();
		try {
			Game.activeBatch.setShader(lightShader);
			// Game.activeBatch.setShader(SpriteBatch.getDefaultShader());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		// It's drawing the shadow, but in the place b4 translation and zoom
		// Could try w/o the zoom, can make life easier

		Game.activeBatch.setColor(Color.WHITE);

		Game.activeBatch.flush();
		// reset the matrix to identity, i.e. "no camera transform"
		// Matrix4f view = Game.activeBatch.getViewMatrix();
		view.setIdentity();

		view.translate(new Vector2f(Game.windowWidth / 2, Game.windowHeight / 2));
		view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
		view.translate(new Vector2f(-Game.windowWidth / 2,
				-Game.windowHeight / 2));
		view.translate(new Vector2f(Game.dragX, Game.dragY));

		// update the new view matrix
		Game.activeBatch.updateUniforms();

		Game.activeBatch.flush();

		if (Game.editorMode)
			Game.editor.level.drawForeground();
		else
			Game.level.drawForeground();

		Game.activeBatch.flush();

		if (Game.editorMode)
			Game.editor.level.drawUI();
		else
			Game.level.drawUI();

		if (Game.editorMode)
			Game.editor.drawUI();

		Game.activeBatch.flush();
		// reset the matrix to identity, i.e. "no camera transform"
		view.setIdentity();

		// update the new view matrix
		Game.activeBatch.updateUniforms();

		Game.activeBatch.resize(Display.getWidth(), Display.getHeight());
		Game.activeBatch.getViewMatrix().setIdentity();
		Game.activeBatch.updateUniforms();
		// Debug
		// batch.setColor(Color.BLACK);
		// batch.draw(occludersFBO.getTexture(), 0, 0);
		// batch.setColor(Color.WHITE);
		// batch.draw(shadowMapFBO.getTexture(), Display.getWidth() - lightSize,
		// lightSize + 5);

		Game.activeBatch.end();
	}

	static void renderLight(Light light) {
		occlusion(light);
		shadowMap();
		renderShadows(light);
	}

	public static void occlusion(Light light) {
		occludersFBO.begin();
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

		view.translate(new Vector2f(-(light.x - lightSize / 2f),
				-(light.y - lightSize / 2f)));
		batch.updateUniforms();

		// update the new view matrix
		// Game.activeBatch.updateUniforms();

		if (Game.editorMode)
			Game.editor.level.drawForeground();
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
		glClearColor(0f, 0f, 0f, 0f);
		glClear(GL_COLOR_BUFFER_BIT);
		batch.setShader(shadowMapShader);
		shadowMapShader.use();
		shadowMapShader.setUniformf("resolution", lightSize, lightSize);
		batch.resize(shadowMapFBO.getWidth(), shadowMapFBO.getHeight());
		batch.getViewMatrix().setIdentity();
		batch.updateUniforms();
		batch.setColor(Color.WHITE);
		batch.draw(occludersFBO.getTexture(), 0, 0, lightSize,
				shadowMapFBO.getHeight());
		batch.flush();
		shadowMapFBO.end();
		// batch.end();
	}

	public static void renderShadows(Light light) {
		// glClearColor(0.5f, 0.5f, 0.5f, 1f);
		// glClear(GL_COLOR_BUFFER_BIT);

		batch.setShader(shadowRenderShader);
		shadowRenderShader.use();
		shadowRenderShader.setUniformf("resolution", lightSize, lightSize);
		shadowRenderShader.setUniformf("softShadows", softShadows ? 1f : 0f);
		batch.setColor(light.color);
		batch.resize(Display.getWidth(), Display.getHeight());

		Matrix4f view = Game.activeBatch.getViewMatrix();
		// view.setIdentity();

		view.translate(new Vector2f(Display.getWidth() / 2,
				Display.getHeight() / 2));
		view.scale(new Vector3f(Game.zoom, Game.zoom, 1f));
		view.translate(new Vector2f(-Display.getWidth() / 2, -Display
				.getHeight() / 2));
		view.translate(new Vector2f(Game.dragX, Game.dragY));
		// batch.begin();
		// view.translate(new Vector2f(-(light.x - lightSize / 2f),
		// -(light.y - lightSize / 2f)));
		batch.updateUniforms();

		// batch.begin();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		batch.draw(shadowMapFBO.getTexture(), light.x - lightSize / 2f, light.y
				- lightSize / 2f, lightSize, lightSize);
		batch.flush();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// batch.end();
	}

	static void clearLights() {
		lights.clear();
		lights.add(new Light(128, 128, Color.RED));
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

}
