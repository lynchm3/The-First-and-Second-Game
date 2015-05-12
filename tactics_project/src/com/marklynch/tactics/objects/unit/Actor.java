package com.marklynch.tactics.objects.unit;

import static com.marklynch.utils.LoadedResources.loadGlobalImage;

import org.newdawn.slick.opengl.Texture;

public class Actor {

	// attributes
	private int strength = 0;
	private int dexterity = 0;
	private int intelligence = 0;
	private int endurance = 0;

	// image
	private String imagePath = "avatar.png";
	private Texture imageTexture = null;

	public Actor(int strength, int dexterity, int intelligence, int endurance,
			String imagePath) {
		super();
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
		this.endurance = endurance;
		this.imagePath = imagePath;
		this.imageTexture = loadGlobalImage(imagePath);
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int getEndurance() {
		return endurance;
	}

	public void setEndurance(int endurance) {
		this.endurance = endurance;
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
