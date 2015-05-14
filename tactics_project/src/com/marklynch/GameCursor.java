package com.marklynch;

import static com.marklynch.utils.LoadedResources.loadGlobalImage;

import org.newdawn.slick.opengl.Texture;

import com.marklynch.tactics.objects.level.Square;

public class GameCursor {
	
	public String imagePath = "";
	public Texture imageTexture = null;
	public Square square = null;
	
	public GameCursor(String imagePath)
	{
		this.imagePath = imagePath;
		this.imageTexture = loadGlobalImage(imagePath);
	}
}
