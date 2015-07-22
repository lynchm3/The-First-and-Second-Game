package com.marklynch.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import mdesl.graphics.Texture;
import mdesl.test.Util;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class ResourceUtils {

	private static HashMap<String, Texture> globalImages = new HashMap<String, Texture>();
	private static HashMap<String, Texture> levelImages = new HashMap<String, Texture>();
	private static HashMap<String, TrueTypeFont> globalFonts = new HashMap<String, TrueTypeFont>();
	private static HashMap<String, TrueTypeFont> levelFonts = new HashMap<String, TrueTypeFont>();

	public static Texture getGlobalImage(String path) {

		Texture texture = null;
		for (String key : globalImages.keySet()) {
			if (key.equals(path)) {
				texture = globalImages.get(key);
				break;
			}
		}

		if (texture != null) {
		} else {
			try {
				texture = new mdesl.graphics.Texture(
						Util.getResource("res/images/" + path),
						mdesl.graphics.Texture.NEAREST);
				globalImages.put(path, texture);
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}

		return texture;
	}

	public static void unloadLevelImages() {

		// ResourceLoader??
		// TextureLoader??

		// Texture t;
		// t.release()

		// levelImages = null

	}

	public static void unloadLevelImage(String path) {

		// ResourceLoader??
		// TextureLoader??

		// Texture t;
		// t.release()

		// levelImages = null

	}

	public static void unloadGlobalImages() {

		// ResourceLoader??
		// TextureLoader??

		// Texture t;
		// t.release()

		// levelImages = null

	}

	public static void unloadGlobalImage(String path) {

		// ResourceLoader??
		// TextureLoader??

		// Texture t;
		// t.release()

		// levelImages = null

	}

	public static TrueTypeFont getGlobalFont(String path, float size) {

		TrueTypeFont font = null;
		for (String key : globalFonts.keySet()) {
			if (key.equals(path + size)) {
				font = globalFonts.get(key);
				break;
			}
		}

		if (font != null) {

		} else {

			try {

				Font awtFont2;
				InputStream inputStream = ResourceLoader
						.getResourceAsStream("res/fonts/" + path); // move
																	// this
				awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
				awtFont2 = awtFont2.deriveFont(size); // set font size
				font = new TrueTypeFont(awtFont2, true);
				globalFonts.put(path + size, font);
			} catch (FontFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return font;
	}
}
