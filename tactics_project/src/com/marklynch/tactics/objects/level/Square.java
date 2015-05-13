package com.marklynch.tactics.objects.level;

import static com.marklynch.utils.LoadedResources.loadGlobalImage;

import org.newdawn.slick.opengl.Texture;

import com.marklynch.tactics.objects.unit.Actor;

public class Square {
	
	private final int x;
	private final int y;
	private int travelCost;
	private boolean walkable = false;

	// image
	private String imagePath;
	private Texture imageTexture = null;
	
	private Actor actor = null;
	

	public Square(int x, int y, String imagePath, int travelCost) {
		super();
		this.x = x;
		this.y = y;
		this.imagePath = imagePath;
		this.imageTexture = loadGlobalImage(imagePath);
		this.travelCost = travelCost;
	}


	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}


	public Texture getImageTexture() {
		return imageTexture;
	}


	public void setImageTexture(Texture imageTexture) {
		this.imageTexture = imageTexture;
	}


	public Actor getActor() {
		return actor;
	}


	public void setActor(Actor actor) {
		this.actor = actor;
	}


	public int getTravelCost() {
		return travelCost;
	}


	public void setTravelCost(int travelCost) {
		this.travelCost = travelCost;
	}


	public boolean isWalkable() {
		return walkable;
	}


	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}
	
	
	
	
	
	
}
