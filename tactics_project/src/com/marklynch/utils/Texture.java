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

	public Texture(URL pngRef, int filter, boolean genMipmap) throws IOException {
		super(pngRef, filter, genMipmap);
		// TODO Auto-generated constructor stub
	}

	public Texture(URL pngRef, int minFilter, int magFilter, int wrap, boolean genMipmap) throws IOException {
		super(pngRef, minFilter, magFilter, wrap, genMipmap);
		// TODO Auto-generated constructor stub
	}

	public Texture(URL pngRef, int filter, int wrap) throws IOException {
		super(pngRef, filter, wrap);
		// TODO Auto-generated constructor stub
	}

	public Texture(URL pngRef, int filter) throws IOException {
		super(pngRef, filter);
		// TODO Auto-generated constructor stub
	}

	public Texture(URL pngRef) throws IOException {
		super(pngRef);
		// TODO Auto-generated constructor stub
	}

}
