package com.marklynch;

import static com.marklynch.utils.Resources.getGlobalImage;

import org.newdawn.slick.opengl.Texture;

import com.marklynch.tactics.objects.level.Square;

public class GameCursor {

	public String imagePath = "";
	public Texture imageTexture = null;
	public Texture imageTexture2 = null;
	public Square square = null;

	public GameCursor(String imagePath, String imagePath2) {
		this.imagePath = imagePath;
		this.imageTexture = getGlobalImage(imagePath);
		this.imageTexture2 = getGlobalImage(imagePath2);
	}
}
