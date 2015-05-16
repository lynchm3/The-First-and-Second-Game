package com.marklynch.tactics.objects;

import static com.marklynch.utils.LoadedResources.loadGlobalImage;

import java.util.Vector;

import org.newdawn.slick.opengl.Texture;

import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.weapons.Weapon;

public class GameObject {

		// attributes
		public int strength = 0;
		public int dexterity = 0;
		public int intelligence = 0;
		public int endurance = 0;
		public int travelDistance = 4;
		
		//Inventory
		public Vector<Weapon> weapons = new Vector<Weapon>();
		
		//Interaction with the level
		public Square squareGameObjectIsOn = null;

		// image
		public String imagePath = "";
		public Texture imageTexture = null;
		

		public GameObject(int strength, int dexterity, int intelligence, int endurance,
				String imagePath, Square squareGameObjectIsOn, Vector<Weapon> weapons) {
			super();
			this.strength = strength;
			this.dexterity = dexterity;
			this.intelligence = intelligence;
			this.endurance = endurance;
			this.imagePath = imagePath;
			this.imageTexture = loadGlobalImage(imagePath);
			this.squareGameObjectIsOn = squareGameObjectIsOn;
			this.squareGameObjectIsOn.gameObject = this;
			this.weapons = weapons;
		}
}
