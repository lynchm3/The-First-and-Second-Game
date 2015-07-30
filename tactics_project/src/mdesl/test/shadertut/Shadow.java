package mdesl.test.shadertut;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.ArrayList;

import mdesl.graphics.Color;
import mdesl.graphics.SpriteBatch;
import mdesl.graphics.Texture;
import mdesl.graphics.TextureRegion;
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

	/**
	 * Compiles a new instance of the default shader for this batch and returns
	 * it. If compilation was unsuccessful, GdxRuntimeException will be thrown.
	 * 
	 * @return the default shader
	 */
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

	private final int lightSize = 256;

	private final float upScale = 1f; // for example; try lightSize=128,
										// upScale=1.5f

	SpriteBatch batch;
	// CHANGE
	// OrthographicCamera cam;

	BitmapFont font;

	// TextureRegion shadowMap1D; // 1 dimensional shadow map
	// TextureRegion occluders; // occluder map

	FrameBuffer occludersFBO;
	FrameBuffer shadowMapFBO;

	Texture casterSprites;
	Texture light;
	Texture fakeShadowMap;

	ShaderProgram shadowMapShader, shadowRenderShader, lesson2Shader;

	ArrayList<Light> lights = new ArrayList<Light>();

	boolean additive = true;
	boolean softShadows = true;

	private TextureRegion shadowMapTextureRegion;
	private TextureRegion occludersTextureRegion;

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

		// TRY
		// Get my code for opengl init and normal batch init

		try {
			// enable alpha blending
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			GL11.glViewport(0, 0, 800, 600);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, 800, 600, 0, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			batch = new SpriteBatch();
			// CHANGE ShaderProgram.pedantic = false;
			ShaderProgram.setStrictMode(false);

			// read vertex pass-through shader
			// final String VERT_SRC = Util.readFile(Util
			// .getResourceAsStream("res/shadertut/shadow_pass.vert"));

			// renders occluders to 1D shadow map
			shadowMapShader = createShader(
					Util.readFile(Util
							.getResourceAsStream("res/shadertut/shadow_pass.vert")),
					Util.readFile(Util
							.getResourceAsStream("res/shadertut/shadow_map.frag")));
			// samples 1D shadow map to create the blurred soft shadow
			shadowRenderShader = createShader(
					Util.readFile(Util
							.getResourceAsStream("res/shadertut/shadow_pass.vert")),
					Util.readFile(Util
							.getResourceAsStream("res/shadertut/shadow_render.frag")));
			lesson2Shader = createShader(Util.readFile(Util
					.getResourceAsStream("res/shadertut/lesson2.vert")),
					Util.readFile(Util
							.getResourceAsStream("res/shadertut/lesson2.frag")));

			// the occluders
			casterSprites = new Texture(Util.getResource("res/cat4.png"),
					Texture.LINEAR);

			fakeShadowMap = new Texture(Util.getResource("res/shadowMap.png"),
					Texture.LINEAR);
			// the light sprite
			// TRY WRAP
			light = new Texture(Util.getResource("res/light.png"),
					Texture.LINEAR);

			// build frame buffers
			// CHANGE occludersFBO = new FrameBuffer(Format.RGBA8888, lightSize,
			// lightSize, false); //FrameBuffer(Pixmap.Format format, int width,
			// int
			// height, boolean hasDepth)
			// TRY WRAP, TRY CHECK IF U CAN CHANGE THE FORMAT
			occludersFBO = new FrameBuffer(lightSize, lightSize, Texture.LINEAR);

			// CHANGE occluders = new
			// TextureRegion(occludersFBO.getColorBufferTexture());
			// getColorBufferTexture() Returns: the gl texture
			// occluders = new TextureRegion(occludersFBO.getTexture());
			// occluders.flip(false, true);

			// our 1D shadow map, lightSize x 1 pixels, no depth
			// CHANGE shadowMapFBO = new FrameBuffer(Format.RGBA8888, lightSize,
			// 1,
			// false);
			// TRY WRAP, TRY CHECK IF U CAN CHANGE THE FORMAT
			shadowMapFBO = new FrameBuffer(lightSize, 1, Texture.LINEAR);

			// CHANGE Texture shadowMapTex =
			// shadowMapFBO.getColorBufferTexture();
			Texture shadowMapTex = shadowMapFBO.getTexture();

			// use linear filtering and repeat wrap mode when sampling
			// CHANGE
			// shadowMapTex.setFilter(TextureFilter.Linear,
			// TextureFilter.Linear);
			// shadowMapTex.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
			shadowMapTex.setFilter(Texture.LINEAR, Texture.LINEAR);
			shadowMapTex.setWrap(Texture.REPEAT);

			// for debugging only; in order to render the 1D shadow map FBO to
			// screen
			// shadowMap1D = new TextureRegion(shadowMapTex);
			// shadowMap1D.flip(false, true);

			// CHANGE font = new BitmapFont();
			Texture fontTexture = new Texture(
					Util.getResource("res/ptsans_00.png"), Texture.NEAREST);
			font = new BitmapFont(Util.getResource("res/ptsans.fnt"),
					fontTexture);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}

		// CHANGE just skipping this
		// cam = new OrthographicCamera(Gdx.graphics.getWidth(),
		// Gdx.graphics.getHeight());
		// cam.setToOrtho(false);

		// CHANGE I remove the input
		// Gdx.input.setInputProcessor(new InputAdapter() {
		//
		// public boolean touchDown(int x, int y, int pointer, int button) {
		// float mx = x;
		// float my = Gdx.graphics.getHeight() - y;
		// lights.add(new Light(mx, my, randomColor()));
		// return true;
		// }
		//
		// public boolean keyDown(int key) {
		// if (key == Keys.SPACE) {
		// clearLights();
		// return true;
		// } else if (key == Keys.A) {
		// additive = !additive;
		// return true;
		// } else if (key == Keys.S) {
		// softShadows = !softShadows;
		// return true;
		// }
		// return false;
		// }
		// });

		clearLights();
		resize(800, 600);
	}

	// RESIZE
	public void resize(int width, int height) {

		// CHANGE
		// cam.setToOrtho(false, width, height);
		// batch.setProjectionMatrix(cam.combined);

		batch.resize(width, height);
	}

	@Override
	public void render() {
		// clear frame
		GL11.glClearColor(0.25f, 0.25f, 0.25f, 1f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		// TRY resize batch and reset view
		batch.setColor(Color.WHITE);
		batch.resize(Display.getWidth(), Display.getHeight());
		batch.getViewMatrix().setIdentity();
		batch.updateUniforms();

		float mx = 128;
		float my = 128;

		if (additive) {
			// CHANGE
			// batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		}

		for (int i = 0; i < lights.size(); i++) {
			Light light = lights.get(i);
			if (i == lights.size() - 1) {
				light.x = mx;
				light.y = my;
			}
			renderLight(light);
		}

		if (additive) {
			// CHANGE
			// batch.setBlendFunction(GL11.GL_SRC_ALPHA,
			// GL11.GL_ONE_MINUS_SRC_ALPHA);
			glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}

		// TRY resize batch and reset view
		batch.resize(Display.getWidth(), Display.getHeight());
		batch.getViewMatrix().setIdentity();
		batch.updateUniforms();

		// STEP 4. render sprites in full colour
		try {
			batch.setShader(SpriteBatch.getDefaultShader());
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		} // default shader
		batch.begin();

		// batch.draw(casterSprites, 0, 0);

		// DEBUG RENDERING -- show occluder map and 1D shadow map
		batch.setColor(Color.WHITE);
		// CHANGE Gdx.graphics.getWidth()

		batch.draw(occludersTextureRegion, 0, 0);
		batch.draw(shadowMapTextureRegion, 0, lightSize + 5);

		//
		// // DEBUG RENDERING -- show light
		// batch.draw(light, mx - light.getWidth() / 2f, my - light.getHeight()
		// / 2f); // mouse
		// CHANGE Gdx.graphics.getWidth()
		// batch.draw(light,
		// Display.getWidth() - lightSize / 2f - light.getWidth() / 2f,
		// lightSize / 2f - light.getHeight() / 2f);

		// draw FPS
		// CHANGE Gdx.graphics.getWidth()
		// CHANGE Gdx.graphics.getHeight()
		batch.setColor(Color.BLACK);
		font.drawText(batch,
				"FPS: " + ":P" + "           Lights: " + lights.size()
						+ "                  SPACE to clear lights"
						+ "                  A to toggle additive blending"
						+ "                  S to toggle soft shadows", 10,
				Display.getHeight() - 100);
		// font.drawMultiLine(batch,
		// "FPS: " + ":P" + "\n\nLights: " + lights.size()
		// + "\nSPACE to clear lights"
		// + "\nA to toggle additive blending"
		// + "\nS to toggle soft shadows", 10,
		// Display.getHeight() - 10);

		batch.end();
	}

	void clearLights() {
		lights.clear();
		lights.add(new Light(128, 128, Color.WHITE));
	}

	static Color randomColor() {
		float intensity = (float) Math.random() * 0.5f + 0.5f;
		return new Color((float) Math.random(), (float) Math.random(),
				(float) Math.random(), intensity);
	}

	void renderLight(Light o) {
		occlusion(o);
		shadowMap();
		renderShadows(o);
	}

	public void occlusion(Light o) {
		float mx = o.x;
		float my = o.y;

		// OCCLUSION
		// STEP 1. render light region to occluder FBO

		// Bind FBO target A
		occludersFBO.begin();

		// Clear FBO A with an opaque colour to minimize blending issues
		glClearColor(0f, 0f, 0f, 0f);
		glClear(GL_COLOR_BUFFER_BIT);

		// Reset batch to default shader (without blur)
		try {
			batch.setShader(SpriteBatch.getDefaultShader());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}

		// send the new projection matrix (FBO size) to the default shader
		batch.resize(occludersFBO.getWidth(), occludersFBO.getHeight());

		// now we can start our batch
		batch.begin();

		batch.getViewMatrix().translate(
				new Vector2f(mx - lightSize / 2f, my - lightSize / 2f));
		batch.updateUniforms();

		// render our scene fully to FBO A
		batch.draw(casterSprites, 0, 0);

		// flush the batch, i.e. render entities to GPU
		batch.flush();

		// After flushing, we can finish rendering to FBO target A
		occludersFBO.end();

		batch.end();

		if (additive) {
			// CHANGE
			// batch.setBlendFunction(GL11.GL_SRC_ALPHA,
			// GL11.GL_ONE_MINUS_SRC_ALPHA);
			glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
	}

	public void shadowMap() {

		// STEP 2
		// SHADOW MAP
		shadowMapFBO.begin();

		// Clear FBO A with an opaque colour to minimize blending issues
		glClearColor(0f, 0f, 0f, 0f);
		glClear(GL_COLOR_BUFFER_BIT);

		// Reset batch to default shader (without blur)
		try {
			// batch.setShader(batch.getDefaultShader());

			batch.setShader(this.shadowMapShader);
			shadowMapShader.use();
			shadowMapShader.setUniformf("resolution", lightSize, lightSize);

			// batch.setShader(this.lesson2Shader);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// send the new projection matrix (FBO size) to the default shader
		batch.resize(shadowMapFBO.getWidth(), shadowMapFBO.getHeight());

		// now we can start our batch
		batch.begin();

		batch.getViewMatrix().setIdentity();
		batch.updateUniforms();

		Texture occludersTexture = this.occludersFBO.getTexture();
		occludersTextureRegion = new TextureRegion(occludersTexture, 0, 0,
				occludersTexture.getWidth(), occludersTexture.getHeight());
		occludersTextureRegion.flip(false, true);

		// render our scene fully to FBO A
		batch.draw(occludersTextureRegion, 0, 0, lightSize,
				shadowMapFBO.getHeight());
		// batch.draw(fakeShadowMap, 0, 0, lightSize, shadowMapFBO.getHeight());

		// flush the batch, i.e. render entities to GPU
		batch.flush();

		// After flushing, we can finish rendering to FBO target A
		shadowMapFBO.end();

		batch.end();
	}

	// TRY -> Make a fake shadowmap :P

	public void renderShadows(Light o) {
		float mx = o.x;
		float my = o.y;

		// STEP 3. render the blurred shadows
		glClearColor(0.5f, 0.5f, 0.5f, 1f);
		glClear(GL_COLOR_BUFFER_BIT);

		// Reset batch to default shader (without blur)
		try {
			// batch.setShader(batch.getDefaultShader());

			// batch.setShader(shadowRenderShader);
			// shadowMapShader.use();
			// shadowRenderShader.setUniformf("resolution", lightSize,
			// lightSize);
			// shadowRenderShader.setUniformf("softShadows", softShadows ? 1f :
			// 0f);

			batch.setShader(this.lesson2Shader);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		batch.setColor(o.color);

		// send the new projection matrix (FBO size) to the default shader
		// batch.resize(Display.getWidth(), Display.getHeight());
		// batch.resize(lightSize, lightSize);
		batch.begin();

		batch.getViewMatrix().setIdentity();
		batch.updateUniforms();

		// render our scene fully to FBO A
		float finalSize = lightSize * upScale;

		Texture shadowMapTexture = this.shadowMapFBO.getTexture();
		shadowMapTextureRegion = new TextureRegion(shadowMapTexture, 0, 0,
				shadowMapTexture.getWidth(), shadowMapTexture.getHeight());
		shadowMapTextureRegion.flip(false, true);

		// draw centered on light position
		batch.draw(shadowMapTextureRegion, mx - finalSize / 2f, my - finalSize
				/ 2f, finalSize, finalSize);

		// batch.draw(this.shadowMapFBO.getTexture(), 0, 0, 100, 100);

		// flush the batch, i.e. render entities to GPU
		batch.flush();

		// After flushing, we can finish rendering to FBO target A

		batch.end();
	}
}