package mdesl.test.shadertut;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

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
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
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

	private final int lightSize = 256;

	private final float upScale = 1f; // for example; try lightSize=128,
										// upScale=1.5f

	SpriteBatch batch;

	BitmapFont font;

	TextureRegion shadowMap1D; // 1 dimensional shadow map
	TextureRegion occluders; // occluder map

	FrameBuffer shadowMapFBO;
	FrameBuffer occludersFBO;

	Texture casterSprites;
	Texture light;

	ShaderProgram shadowMapShader, shadowRenderShader;

	ArrayList<Light> lights = new ArrayList<Light>();

	boolean additive = true;
	boolean softShadows = true;

	/**
	 * Compiles a new instance of the default shader for this batch and returns
	 * it. If compilation was unsuccessful, GdxRuntimeException will be thrown.
	 * 
	 * @return the default shader
	 */
	public static ShaderProgram createShader(String vert, String frag) {
		ShaderProgram prog = null;
		try {
			prog = new ShaderProgram(vert, frag);
			if (prog.getLog().length() != 0)
				System.out.println(prog.getLog());
		} catch (Exception e) {

		}
		return prog;
	}

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
			// Display.setDisplayMode(new DisplayMode(800, 600));
			// Display.setResizable(true);
			// Display.create();
			// Display.setVSyncEnabled(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);

			GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

			GL11.glDisable(GL_DEPTH_TEST);
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
			ShaderProgram.setStrictMode(false);
			// ShaderProgram.pedantic = false;

			// read vertex pass-through shader
			final String VERT_SRC = Util.readFile(Util
					.getResourceAsStream("res/shadertut/shadow_pass.vert"));

			// renders occluders to 1D shadow map
			shadowMapShader = createShader(VERT_SRC, Util.readFile(Util
					.getResourceAsStream("res/shadertut/shadow_map.frag")));
			// samples 1D shadow map to create the blurred soft shadow
			shadowRenderShader = createShader(VERT_SRC, Util.readFile(Util
					.getResourceAsStream("res/shadertut/shadow_render.frag")));

			// the occluders
			new Texture(Util.getResource("res/rock.png"), Texture.LINEAR);
			casterSprites = new Texture(Util.getResource("res/cat4.png"),
					Texture.LINEAR);
			// the light sprite
			light = new Texture(Util.getResource("res/light.png"),
					Texture.LINEAR);

			// build frame buffers
			occludersFBO = new FrameBuffer(lightSize, lightSize, Texture.LINEAR);
			occluders = new TextureRegion(occludersFBO.getTexture());
			occluders.flip(false, true);

			// our 1D shadow map, lightSize x 1 pixels, no depth
			shadowMapFBO = new FrameBuffer(lightSize, 1, Texture.LINEAR);
			Texture shadowMapTex = shadowMapFBO.getTexture();

			// use linear filtering and repeat wrap mode when sampling
			shadowMapTex.setFilter(Texture.LINEAR, Texture.LINEAR);
			shadowMapTex.setWrap(Texture.REPEAT);

			// for debugging only; in order to render the 1D shadow map FBO to
			// screen
			shadowMap1D = new TextureRegion(shadowMapTex);
			shadowMap1D.flip(false, true);
			Texture fontTexture = new Texture(
					Util.getResource("res/ptsans_00.png"), Texture.NEAREST);
			font = new BitmapFont(Util.getResource("res/ptsans.fnt"),
					fontTexture);

			GL11.glOrtho(0, 800, 600, 0, 1, -1);

			// cam = new OrthographicCamera(Gdx.graphics.getWidth(),
			// Gdx.graphics.getHeight());
			// cam.setToOrtho(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// INPUT
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
	}

	// RESIZE
	// @Override
	// public void resize(int width, int height) {
	// cam.setToOrtho(false, width, height);
	// batch.setProjectionMatrix(cam.combined);
	// }

	@Override
	public void render() {
		// clear frame
		GL11.glClearColor(0.25f, 0.25f, 0.25f, 1f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		Matrix4f view = batch.getViewMatrix();
		view.setIdentity();
		batch.updateUniforms();

		float mx = 100;
		float my = 100;

		if (additive)
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		// batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		System.out.println("lights.size() = " + lights.size());
		for (int i = 0; i < lights.size(); i++) {
			Light light = lights.get(i);
			if (i == lights.size() - 1) {
				light.x = mx;
				light.y = my;
			}
			renderLight(light);
		}

		if (additive)
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		// STEP 4. render sprites in full colour
		batch.begin();
		try {
			batch.setShader(SpriteBatch.getDefaultShader());
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // default shader

		batch.draw(casterSprites, 0, 0);

		// DEBUG RENDERING -- show occluder map and 1D shadow map
		batch.setColor(Color.BLACK);
		batch.draw(occluders, 800 - lightSize, 0);
		batch.setColor(Color.WHITE);
		batch.draw(shadowMap1D, 800 - lightSize, lightSize + 5);

		// DEBUG RENDERING -- show light
		batch.draw(light, mx - light.getWidth() / 2f, my - light.getHeight()
				/ 2f); // mouse
		batch.draw(light, 800 - lightSize / 2f - light.getWidth() / 2f,
				lightSize / 2f - light.getHeight() / 2f);

		// draw FPS
		// font.drawMultiLine(batch, "FPS: " + Gdx.graphics.getFramesPerSecond()
		// + "\n\nLights: " + lights.size + "\nSPACE to clear lights"
		// + "\nA to toggle additive blending"
		// + "\nS to toggle soft shadows", 10,
		// Gdx.graphics.getHeight() - 10);

		batch.end();
	}

	void clearLights() {
		lights.clear();
		lights.add(new Light(100, 100, Color.WHITE));
	}

	static Color randomColor() {
		float intensity = (float) Math.random() * 0.5f + 0.5f;
		return new Color((float) Math.random(), (float) Math.random(),
				(float) Math.random(), intensity);
	}

	void renderLight(Light o) {
		float mx = o.x;
		float my = o.y;

		// STEP 1. render light region to occluder FBO

		// bind the occluder FBO
		occludersFBO.begin();

		// clear the FBO
		GL11.glClearColor(0.25f, 0.25f, 0.25f, 1f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		// set the orthographic camera to the size of our FBO

		GL11.glOrtho(0, occludersFBO.getWidth(), occludersFBO.getHeight(), 0,
				1, -1);
		// cam.setToOrtho(false, occludersFBO.getWidth(),
		// occludersFBO.getHeight());

		Matrix4f view = batch.getViewMatrix();
		// translate camera so that light is in the center
		view.translate(new Vector2f(mx - lightSize / 2f, my - lightSize / 2f));

		// update camera matrices
		batch.updateUniforms();

		// set up our batch for the occluder pass
		// batch.setProjectionMatrix(cam.combined);
		try {
			batch.setShader(SpriteBatch.getDefaultShader());
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // use default shader
		batch.begin();
		// ... draw any sprites that will cast shadows here ... //
		batch.draw(casterSprites, 0, 0);

		// end the batch before unbinding the FBO
		batch.end();

		// unbind the FBO
		occludersFBO.end();

		// STEP 2. build a 1D shadow map from occlude FBO

		// bind shadow map
		shadowMapFBO.begin();

		// clear it
		GL11.glClearColor(0f, 0f, 0f, 0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		// set our shadow map shader
		batch.setShader(shadowMapShader);
		batch.begin();
		shadowMapShader.setUniformf("resolution", lightSize, lightSize);
		shadowMapShader.setUniformf("upScale", upScale);

		// reset our projection matrix to the FBO size
		GL11.glOrtho(0, shadowMapFBO.getWidth(), shadowMapFBO.getHeight(), 0,
				1, -1);
		// cam.setToOrtho(false, shadowMapFBO.getWidth(),
		// shadowMapFBO.getHeight());
		// batch.setProjectionMatrix(cam.combined);

		// draw the occluders texture to our 1D shadow map FBO
		batch.draw(occluders.getTexture(), 0, 0, lightSize,
				shadowMapFBO.getHeight());

		// flush batch
		batch.end();

		// unbind shadow map FBO
		shadowMapFBO.end();

		// STEP 3. render the blurred shadows

		// reset projection matrix to screen
		// cam.setToOrtho(false);
		// batch.setProjectionMatrix(cam.combined);

		// set the shader which actually draws the light/shadow
		batch.setShader(shadowRenderShader);
		batch.begin();

		shadowRenderShader.setUniformf("resolution", lightSize, lightSize);
		shadowRenderShader.setUniformf("softShadows", softShadows ? 1f : 0f);
		// set color to light
		batch.setColor(o.color);

		float finalSize = lightSize * upScale;

		// draw centered on light position
		batch.draw(shadowMap1D.getTexture(), mx - finalSize / 2f, my
				- finalSize / 2f, finalSize, finalSize);

		// flush the batch before swapping shaders
		batch.end();

		// reset color
		batch.setColor(Color.WHITE);
	}

}