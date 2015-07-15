package com.marklynch;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import org.newdawn.slick.opengl.Texture;

public class GameCursor {

	public Texture imageTexture = null;
	public Texture imageTexture2 = null;
	public Texture imageTexture3 = null;
	public Texture imageTexture4 = null;
	public Texture cursor = null;

	public GameCursor() {
		this.imageTexture = getGlobalImage("highlight.png");
		this.imageTexture2 = getGlobalImage("highlight2.png");
		this.imageTexture3 = getGlobalImage("highlight3.png");
		this.imageTexture4 = getGlobalImage("highlight4.png");
		this.cursor = getGlobalImage("cursor.png");
	}
}
