package com.marklynch.objects.units;

import com.marklynch.level.Square;
import com.marklynch.level.constructs.Faction;
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
	public int sight = 4;

	public final static String[] editableAttributes = { "name", "imageTexture", "faction", "strength", "dexterity",
			"intelligence", "endurance", "totalHealth", "remainingHealth", "travelDistance", "inventory",
			"showInventory", "fitsInInventory", "canContainOtherObjects" };

	public ActorTemplate(String name, String title, int actorLevel, int health, int strength, int dexterity,
			int intelligence, int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance,
			int sight, Inventory inventory, boolean showInventory, boolean fitsInInventory,
			boolean canContainOtherObjects, boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio) {
		super(name, health, imagePath, squareActorIsStandingOn, inventory, showInventory, false, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio);
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
		this.endurance = endurance;
		this.title = title;
		this.actorLevel = actorLevel;
		this.travelDistance = travelDistance;
		this.sight = sight;

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

	public Actor makeCopy(Square square, Faction faction) {
		Actor actor = new Actor(new String(name), new String(title), actorLevel, (int) totalHealth, strength, dexterity,
				intelligence, endurance, imageTexturePath, square, travelDistance, sight, null, inventory.makeCopy(),
				showInventory, fitsInInventory, canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				faction, 0, 0);
		return actor;

	}

}
