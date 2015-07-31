package mdesl.test.shadertut;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.ArrayList;

import mdesl.graphics.Color;
import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;
import mdesl.graphics.glutils.FrameBuffer;
import mdesl.graphics.glutils.ShaderProgram;
import mdesl.graphics.text.BitmapFont;
import mdesl.test.Game;
import mdesl.test.SimpleGame;
import mdesl.test.Util;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

/**
 * Per-pixel shadows on GPU:
 * https://github.com/mattdesl/lwjgl-basics/wiki/2D-Pixel-Perfect-Shadows
 * 
 * @author mattdesl
 */
public class Shadow extends SimpleGame {

	public static void main(String[] args) throws LWJGLException {
		Game game = new Shadow();
		game.setDisplayMode(800, 600, false);
		game.start();
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

	private final float lightSize = 500;
	SpriteBatch batch;
	BitmapFont font;
	FrameBuffer occludersFBO;
	FrameBuffer shadowMapFBO;
	Texture casterSprites;
	ShaderProgram shadowMapShader, shadowRenderShader;
	ArrayList<Light> lights = new ArrayList<Light>();
	boolean softShadows = true;

	class Light {

		float x, y;
		Color color;

		public Light(float x, float y, Color color) {
			this.x = x;
			this.y = y;
			this.color = color;
		}
	}

	@Override
	public void create() {

		try {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glViewport(0, 0, 800, 600);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, 800, 600, 0, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
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
			casterSprites = new Texture(Util.getResource("res/cat4.png"),
					Texture.LINEAR);
			occludersFBO = new FrameBuffer((int) lightSize, (int) lightSize,
					Texture.LINEAR);
			shadowMapFBO = new FrameBuffer((int) lightSize, 1, Texture.LINEAR);
			Texture shadowMapTex = shadowMapFBO.getTexture();
			shadowMapTex.setFilter(Texture.LINEAR, Texture.LINEAR);
			shadowMapTex.setWrap(Texture.REPEAT);
			Texture fontTexture = new Texture(
					Util.getResource("res/ptsans_00.png"), Texture.NEAREST);
			font = new BitmapFont(Util.getResource("res/ptsans.fnt"),
					fontTexture);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		clearLights();
		resize(800, 600);
	}

	// RESIZE
	public void resize(int width, int height) {
		batch.resize(width, height);
	}

	@Override
	public void render() {
		GL11.glClearColor(0.25f, 0.25f, 0.25f, 1f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		batch.setColor(Color.WHITE);
		batch.resize(Display.getWidth(), Display.getHeight());
		batch.getViewMatrix().setIdentity();
		batch.updateUniforms();
		for (int i = 0; i < lights.size(); i++) {
			renderLight(lights.get(i));
		}
		batch.resize(Display.getWidth(), Display.getHeight());
		batch.getViewMatrix().setIdentity();
		batch.updateUniforms();
		try {
			batch.setShader(SpriteBatch.getDefaultShader());
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		batch.setColor(Color.WHITE);
		batch.begin();
		batch.draw(casterSprites, 0, 0);

		// Debug
		batch.setColor(Color.BLACK);
		batch.draw(this.occludersFBO.getTexture(), Display.getWidth()
				- lightSize, 0);
		batch.setColor(Color.WHITE);
		batch.draw(this.shadowMapFBO.getTexture(), Display.getWidth()
				- lightSize, lightSize + 5);
		batch.end();
	}

	void clearLights() {
		lights.clear();
		lights.add(new Light(200, 200, Color.BLUE));
		// lights.add(new Light(128, 128, Color.RED));
	}

	static Color randomColor() {
		float intensity = (float) Math.random() * 0.5f + 0.5f;
		return new Color((float) Math.random(), (float) Math.random(),
				(float) Math.random(), intensity);
	}

	void renderLight(Light light) {
		occlusion(light);
		shadowMap();
		renderShadows(light);
	}

	public void occlusion(Light light) {
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
		batch.begin();
		batch.getViewMatrix().translate(
				new Vector2f(-(light.x - lightSize / 2f),
						-(light.y - lightSize / 2f)));
		batch.updateUniforms();
		batch.draw(casterSprites, 0, 0);
		batch.flush();
		occludersFBO.end();
		batch.end();
	}

	public void shadowMap() {
		shadowMapFBO.begin();
		glClearColor(0f, 0f, 0f, 0f);
		glClear(GL_COLOR_BUFFER_BIT);
		batch.setShader(this.shadowMapShader);
		shadowMapShader.use();
		shadowMapShader.setUniformf("resolution", lightSize, lightSize);
		batch.resize(shadowMapFBO.getWidth(), shadowMapFBO.getHeight());
		batch.begin();
		batch.getViewMatrix().setIdentity();
		batch.updateUniforms();
		batch.draw(this.occludersFBO.getTexture(), 0, 0, lightSize,
				shadowMapFBO.getHeight());
		batch.flush();
		shadowMapFBO.end();
		batch.end();
	}

	public void renderShadows(Light light) {
		glClearColor(0.5f, 0.5f, 0.5f, 1f);
		glClear(GL_COLOR_BUFFER_BIT);
		batch.setShader(shadowRenderShader);
		shadowRenderShader.use();
		shadowRenderShader.setUniformf("resolution", lightSize, lightSize);
		shadowRenderShader.setUniformf("softShadows", softShadows ? 1f : 0f);
		batch.setColor(light.color);
		batch.resize(Display.getWidth(), Display.getHeight());
		batch.begin();
		batch.draw(this.shadowMapFBO.getTexture(), light.x - lightSize / 2f,
				light.y - lightSize / 2f, lightSize, lightSize);
		batch.flush();
		batch.end();
	}
}