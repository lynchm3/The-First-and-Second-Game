package com.marklynch;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import mdesl.graphics.Texture;

public class GameCursor {

	public transient Texture imageTexture = null;
	public transient Texture imageTexture2 = null;
	public transient Texture imageTexture3 = null;
	public transient Texture imageTexture4 = null;
	public transient Texture cursor = null;
	public transient Texture circle = null;
	public transient Texture redArrow = null;
	public transient Texture yellowArrow = null;
	public transient Texture blueArrow = null;
	public transient Texture greenArrow = null;

	public GameCursor() {
		this.imageTexture = getGlobalImage("highlight.png");
		this.imageTexture2 = getGlobalImage("highlight2.png");
		this.imageTexture3 = getGlobalImage("highlight3.png");
		this.imageTexture4 = getGlobalImage("highlight4.png");
		this.cursor = getGlobalImage("cursor.png");
		this.circle = getGlobalImage("circle.png");
		this.redArrow = getGlobalImage("red_arrow.png");
		this.yellowArrow = getGlobalImage("yellow_arrow.png");
		this.blueArrow = getGlobalImage("blue_arrow.png");
		this.greenArrow = getGlobalImage("green_arrow.png");
	}
}
