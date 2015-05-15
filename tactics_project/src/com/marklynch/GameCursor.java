package com.marklynch;

import static com.marklynch.utils.LoadedResources.loadGlobalImage;

import org.newdawn.slick.opengl.Texture;

import com.marklynch.tactics.objects.level.Square;

public class GameCursor {
	
	public String imagePath = "";
	public Texture imageTexture = null;
	public Texture imageTexture2 = null;
	public Square square = null;
	
	public GameCursor(String imagePath, String imagePath2)
	{
		this.imagePath = imagePath;
		this.imageTexture = loadGlobalImage(imagePath);
		this.imageTexture2 = loadGlobalImage(imagePath2);
	}
}
