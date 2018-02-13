package com.marklynch.utils;

import java.io.IOException;
import java.net.URL;

public class Texture extends mdesl.graphics.Texture {

	public Texture() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Texture(int width, int height, int filter, int wrap) {
		super(width, height, filter, wrap);
		// TODO Auto-generated constructor stub
	}

	public Texture(int width, int height, int filter) {
		super(width, height, filter);
		// TODO Auto-generated constructor stub
	}

	public Texture(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	public Texture(URL pngRef, int filter, boolean genMipmap, boolean generatePixelsArray) throws IOException {
		super(pngRef, filter, genMipmap, generatePixelsArray);
		// TODO Auto-generated constructor stub
	}

	public Texture(URL pngRef, int minFilter, int magFilter, int wrap, boolean genMipmap, boolean generatePixelsArray)
			throws IOException {
		super(pngRef, minFilter, magFilter, wrap, genMipmap, generatePixelsArray);
		// TODO Auto-generated constructor stub
	}

	public Texture(URL pngRef, int filter, int wrap, boolean generatePixelsArray) throws IOException {
		super(pngRef, filter, wrap, generatePixelsArray);
		// TODO Auto-generated constructor stub
	}

	public Texture(URL pngRef, int filter, boolean generatePixelsArray) throws IOException {
		super(pngRef, filter, generatePixelsArray);
		// TODO Auto-generated constructor stub
	}

	public Texture(URL pngRef, boolean generatePixelsArray) throws IOException {
		super(pngRef, generatePixelsArray);
		// TODO Auto-generated constructor stub
	}

}
