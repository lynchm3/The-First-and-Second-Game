package com.tutorials;

import java.awt.Font;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class FontExample {

	/** The fonts to draw to the screen */
	private TrueTypeFont font;

	/** Boolean flag on whether AntiAliasing is enabled or not */
	private boolean antiAlias = true;

	/**
	 * Start the test
	 */
	public void start() {
		initGL(800, 600);
		init();

		while (true) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			render();

			Display.update();
			Display.sync(100);

			if (Display.isCloseRequested()) {
				Display.destroy();
				System.exit(0);
			}
		}
	}

	/**
	 * Initialise the GL display
	 * 
	 * @param width
	 *            The width of the display
	 * @param height
	 *            The height of the display
	 */
	private void initGL(int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glClearDepth(1);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glViewport(0, 0, width, height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	/**
	 * Initialise resources
	 */
	public void init() {
		// load a default java font
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, antiAlias);

		// load font from file
		try {
			InputStream inputStream = ResourceLoader
					.getResourceAsStream("res/KeepCalm-Medium.ttf");

			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(24f); // set font size
			font = new TrueTypeFont(awtFont2, antiAlias);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Game loop render
	 */
	public void render() {
		Color.white.bind();

		// font.drawString(100, 50, "THE LIGHTWEIGHT JAVA GAMES LIBRARY",
		// Color.yellow);
		font.drawString(100, 100, "MARLENE", Color.green);

		font.drawString(100, 150, "        ______            ______\n",
				Color.red);
		font.drawString(100, 170,
				"       /  ____  \\       /   ____  \\\n", Color.red);
		font.drawString(100, 190,
				"     /  /        \\  \\   /  /         \\  \\\n", Color.red);
		font.drawString(100, 210,
				"   /  /            \\  \"  /             \\  \\\n", Color.red);
		font.drawString(100, 230,
				"  |  |               \\ /               |  |\n", Color.red);
		font.drawString(100, 250,
				"  |  |                \"                |  |\n", Color.red);
		font.drawString(100, 270,
				"  |  |                                 |  |\n", Color.red);
		font.drawString(100, 290,
				"   |  |                               |  |\n", Color.red);
		font.drawString(100, 310,
				"    |  |                             |  |\n", Color.red);
		font.drawString(100, 330, "      |  |                         |  |\n",
				Color.red);
		font.drawString(100, 350, "        |  |                     |  |\n",
				Color.red);
		font.drawString(100, 370, "          |  |                 |  |\n",
				Color.red);
		font.drawString(100, 390, "            |  |             |  |\n",
				Color.red);
		font.drawString(100, 410, "              |  |         |  |\n",
				Color.red);
		font.drawString(100, 430, "                |  |     |  |\n", Color.red);
		font.drawString(100, 450, "                  |  | |  |\n", Color.red);
		font.drawString(100, 470, "                    | | |\n", Color.red);
		font.drawString(100, 490, "                     | |\n", Color.red);
		font.drawString(100, 510, "                      |\n", Color.red);

//		font2.drawString(100, 150, s, Color.red);

	}

	/**
	 * Main method
	 */
	public static void main(String[] argv) {
		FontExample fontExample = new FontExample();
		fontExample.start();
	}
}
