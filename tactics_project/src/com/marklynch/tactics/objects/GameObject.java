package com.marklynch.tactics.objects;

import static com.marklynch.utils.Resources.getGlobalImage;

import java.util.Vector;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.weapons.Weapon;

public class GameObject {

	// attributes
	public int strength = 0;
	public int dexterity = 0;
	public int intelligence = 0;
	public int endurance = 0;
	public int travelDistance = 4;

	// Inventory
	public Vector<Weapon> weapons = new Vector<Weapon>();

	// Interaction with the level
	public Square squareGameObjectIsOn = null;

	// image
	public String imagePath = "";
	public Texture imageTexture = null;

	public GameObject(int strength, int dexterity, int intelligence,
			int endurance, String imagePath, Square squareGameObjectIsOn,
			Vector<Weapon> weapons) {
		super();
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
		this.endurance = endurance;
		this.imagePath = imagePath;
		this.imageTexture = getGlobalImage(imagePath);
		this.squareGameObjectIsOn = squareGameObjectIsOn;
		this.squareGameObjectIsOn.gameObject = this;
		this.weapons = weapons;
	}

	public void draw() {
		this.imageTexture.bind();
		int actorPositionXInPixels = this.squareGameObjectIsOn.x
				* (int) Game.SQUARE_WIDTH;
		int actorPositionYInPixels = this.squareGameObjectIsOn.y
				* (int) Game.SQUARE_HEIGHT;

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(actorPositionXInPixels, actorPositionYInPixels);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(actorPositionXInPixels + Game.SQUARE_WIDTH,
				actorPositionYInPixels);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(actorPositionXInPixels + Game.SQUARE_WIDTH,
				actorPositionYInPixels + Game.SQUARE_HEIGHT);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(actorPositionXInPixels, actorPositionYInPixels
				+ Game.SQUARE_HEIGHT);
		GL11.glEnd();
	}
}
