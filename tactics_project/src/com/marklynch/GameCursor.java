package com.marklynch;

import static com.marklynch.utils.LoadedResources.loadGlobalImage;

import org.newdawn.slick.opengl.Texture;

import com.marklynch.tactics.objects.level.Square;

public class GameCursor {
	
	private String imagePath = "";
	private Texture imageTexture = null;
	private Square square = null;
	
	public GameCursor(String imagePath)
	{
		this.imagePath = imagePath;
		this.imageTexture = loadGlobalImage(imagePath);
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

	public Square getSquare() {
		return square;
	}

	public void setSquare(Square square) {
		this.square = square;
	}	
}
