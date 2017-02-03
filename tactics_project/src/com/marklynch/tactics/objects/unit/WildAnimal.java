package com.marklynch.tactics.objects.unit;

import com.marklynch.tactics.objects.Bed;
import com.marklynch.tactics.objects.Inventory;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.ai.routines.AIRoutineForWildAnimal;

public class WildAnimal extends Actor {

	public WildAnimal(String name, String title, int actorLevel, int health, int strength, int dexterity,
			int intelligence, int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance,
			Bed bed, Inventory inventory, boolean showInventory, boolean fitsInInventory,
			boolean canContainOtherObjects) {
		super(name, title, actorLevel, health, strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, travelDistance, bed, inventory, showInventory, fitsInInventory,
				canContainOtherObjects);
		aiRoutine = new AIRoutineForWildAnimal();
	}

	@Override
	public Actor makeCopy(Square square) {

		return new WildAnimal(name, title, actorLevel, (int) totalHealth, strength, dexterity, intelligence, endurance,
				imageTexturePath, square, travelDistance, null, inventory, showInventory, fitsInInventory,
				canContainOtherObjects);
	}

}
