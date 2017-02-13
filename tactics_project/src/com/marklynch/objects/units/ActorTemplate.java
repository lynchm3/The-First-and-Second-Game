package com.marklynch.objects.units;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inventory;

public class ActorTemplate extends GameObject {

	public int strength;
	public int dexterity;
	public int intelligence;
	public int endurance;
	public String title = "";
	public int actorLevel = 1;
	public int travelDistance = 4;

	public final static String[] editableAttributes = { "name", "imageTexture", "faction", "strength", "dexterity",
			"intelligence", "endurance", "totalHealth", "remainingHealth", "travelDistance", "inventory",
			"showInventory", "fitsInInventory", "canContainOtherObjects" };

	public ActorTemplate(String name, String title, int actorLevel, int health, int strength, int dexterity,
			int intelligence, int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance,
			Inventory inventory, boolean showInventory, boolean fitsInInventory, boolean canContainOtherObjects) {
		super(name, health, imagePath, squareActorIsStandingOn, inventory, showInventory, false, fitsInInventory,
				canContainOtherObjects);
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
		this.endurance = endurance;
		this.title = title;
		this.actorLevel = actorLevel;
		this.travelDistance = travelDistance;
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		loadImages();
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	@Override
	public Actor makeCopy(Square square) {
		Actor actor = new Actor(new String(name), new String(title), actorLevel, (int) totalHealth, strength, dexterity,
				intelligence, endurance, imageTexturePath, square, travelDistance, null, inventory.makeCopy(),
				showInventory, fitsInInventory, canContainOtherObjects);
		actor.faction = Game.level.factions.get(1);
		actor.factionGUID = Game.level.factions.get(1).guid;
		return actor;

	}

}
