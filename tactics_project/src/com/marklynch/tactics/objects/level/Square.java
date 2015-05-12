package com.marklynch.tactics.objects.level;

import static com.marklynch.utils.LoadedResources.loadGlobalImage;

import org.newdawn.slick.opengl.Texture;

public class Square {
	
	private int x;
	private int y;

	// image
	private String imagePath;
	private Texture imageTexture = null;
	

	public Square(int x, int y, String imagePath) {
		super();
		this.imagePath = imagePath;
		this.imageTexture = loadGlobalImage(imagePath);
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
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
}
