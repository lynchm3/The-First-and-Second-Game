package com.marklynch.graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.io.IOException;

import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;
import mdesl.graphics.glutils.ShaderProgram;
import mdesl.test.Util;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.marklynch.Game;

public class Light {

	public static Texture rock;
	public static Texture rockNormals;

	static SpriteBatch lightBatch;
	static ShaderProgram lightShader;
	public static final float DEFAULT_LIGHT_Z = 0.075f;
	public static final Vector4f LIGHT_COLOR = new Vector4f(1f, 0.8f, 0.6f, 1f);
	public static final Vector4f AMBIENT_COLOR = new Vector4f(0.6f, 0.6f, 1f,
			0.2f);
	public static final Vector3f FALLOFF = new Vector3f(.4f, 3f, 20f);
	public static final Vector3f lightPos = new Vector3f(0f, 0f,
			DEFAULT_LIGHT_Z);

	public static void renderLight() {

		Game.activeBatch = lightBatch;

		Game.activeBatch.begin();
		// Clear FBO A with an opaque colour to minimize blending issues
		glClearColor(0.5f, 0.5f, 0.5f, 1f);
		glClear(GL_COLOR_BUFFER_BIT);
		Matrix4f view = Game.activeBatch.getViewMatrix();
		view.setIdentity();
		Game.activeBatch.updateUniforms();

		// shader will now be in use...

		// update light position, normalized to screen resolution
		float x = Mouse.getX() / (float) Display.getWidth();
		float y = Mouse.getY() / (float) Display.getHeight();
		lightPos.x = x;
		lightPos.y = y;

		// send a Vector4f to GLSL
		lightShader.setUniformf("LightPos", lightPos);

		// // bind normal map to texture unit 1
		// glActiveTexture(GL_TEXTURE1);
		// rockNormals.bind();
		//
		// // bind diffuse color to texture unit 0
		// glActiveTexture(GL_TEXTURE0);
		// rock.bind();

		// render our scene fully to FBO A
		if (Game.editorMode)
			Game.editor.draw();
		else
			Game.level.draw();

		// draw the texture unit 0 with our shader effect applied
		// activeBatch.draw(rock, 50, 50);

		Game.activeBatch.end();

	}

	public static void initBatch() {

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

			lightBatch = new SpriteBatch(lightShader);
		} catch (Exception e) {
			// simple exception handling...
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void resize() {
		lightShader.use();
		lightShader.setUniformf("Resolution", Display.getWidth(),
				Display.getHeight());
		lightBatch.resize(Display.getWidth(), Display.getHeight());
	}
}
