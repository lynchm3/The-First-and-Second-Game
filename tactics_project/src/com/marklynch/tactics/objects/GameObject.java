package com.marklynch.tactics.objects;

import static com.marklynch.utils.Resources.getGlobalImage;

import java.util.Vector;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.weapons.Weapon;

public class GameObject {

	public Level level;

	public String name = "";

	// attributes
	public int strength = 0;
	public int dexterity = 0;
	public int intelligence = 0;
	public int endurance = 0;
	public int totalHealth = 0;
	public int remainingHealth = 0;

	// Inventory
	public Vector<Weapon> weapons = new Vector<Weapon>();

	// Interaction with the level
	public Square squareGameObjectIsOn = null;

	// image
	public String imagePath = "";
	public Texture imageTexture = null;

	public GameObject(String name, int health, int strength, int dexterity,
			int intelligence, int endurance, String imagePath,
			Square squareGameObjectIsOn, Vector<Weapon> weapons, Level level) {
		super();
		this.name = name;
		this.totalHealth = health;
		this.remainingHealth = health;
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
		this.endurance = endurance;
		this.imagePath = imagePath;
		this.imageTexture = getGlobalImage(imagePath);
		this.squareGameObjectIsOn = squareGameObjectIsOn;
		this.squareGameObjectIsOn.gameObject = this;
		this.weapons = weapons;
		this.level = level;
	}

	public void draw() {

		// Draw object
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

		// Draw health
		float healthWidthInPixels = Game.SQUARE_WIDTH / 2;
		float healthHeightInPixels = Game.SQUARE_HEIGHT / 5;

		float healthPositionXInPixels = (this.squareGameObjectIsOn.x * (int) Game.SQUARE_WIDTH)
				+ Game.SQUARE_WIDTH - healthWidthInPixels;
		float healthPositionYInPixels = this.squareGameObjectIsOn.y
				* (int) Game.SQUARE_HEIGHT;

		level.font.drawString(healthPositionXInPixels, healthPositionYInPixels,
				"" + remainingHealth + "/" + totalHealth, Color.black);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);

	}

	public void checkIfDestroyed() {
		if (remainingHealth <= 0) {
			this.squareGameObjectIsOn.gameObject = null;
			level.gameObjects.remove(this);
		}
	}
}
