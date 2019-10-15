package com.marklynch.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import mdesl.test.Util;

public class ResourceUtils {

	private static ConcurrentHashMap<String, Texture> globalImages = new ConcurrentHashMap<String, Texture>();
	private static ConcurrentHashMap<String, Texture> levelImages = new ConcurrentHashMap<String, Texture>();
	private static ConcurrentHashMap<String, TrueTypeFont> globalFonts = new ConcurrentHashMap<String, TrueTypeFont>();
	private static ConcurrentHashMap<String, TrueTypeFont> levelFonts = new ConcurrentHashMap<String, TrueTypeFont>();
	private static ConcurrentHashMap<String, Audio> globalSounds = new ConcurrentHashMap<String, Audio>();

	public static Texture getGlobalImage(String path, boolean generatePixels) {

		if (path == null || path.length() == 0)
			return null;

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
				texture = new com.marklynch.utils.Texture(Util.getResource("res/images/" + path),
						com.marklynch.utils.Texture.LINEAR, generatePixels);
				texture.path = path;
				globalImages.put(path, texture);
			} catch (Exception e) {
			}
		}

		return texture;
	}

	public static Audio getGlobalSound(String path) {

		Audio sound = null;
		for (String key : globalSounds.keySet()) {
			if (key.equals(path)) {
				sound = globalSounds.get(key);
				break;
			}
		}

		if (sound != null) {
		} else {
			try {
				sound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/" + path));
				globalSounds.put(path, sound);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sound;
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
				InputStream inputStream = ResourceLoader.getResourceAsStream("res/fonts/" + path); // move
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

	public static String getPathForTexture(Texture texture) {

		for (String path : globalImages.keySet()) {
			if (globalImages.get(path) == texture) {
				return path;
			}
		}

		return null;
	}
}
