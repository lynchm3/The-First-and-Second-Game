package com.marklynch.tactics.objects.level;

import static com.marklynch.utils.LoadedResources.loadGlobalImage;

import org.newdawn.slick.opengl.Texture;

import com.marklynch.tactics.objects.unit.Actor;

public class Square {
	
	public final int x;
	public final int y;
	public int travelCost;
	public boolean walkable = false;

	// image
	public String imagePath;
	public Texture imageTexture = null;
	
	public Actor actor = null;
	

	public Square(int x, int y, String imagePath, int travelCost) {
		super();
		this.x = x;
		this.y = y;
		this.imagePath = imagePath;
		this.imageTexture = loadGlobalImage(imagePath);
		this.travelCost = travelCost;
	}
}
