package com.marklynch.tactics.objects.unit;

import com.marklynch.tactics.objects.Bed;
import com.marklynch.tactics.objects.Inventory;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.ai.routines.AIRoutineForTrader;

public class Trader extends Actor {

	public Trader(String name, String title, int actorLevel, int health, int strength, int dexterity, int intelligence,
			int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance, Bed bed,
			Inventory inventory, boolean showInventory, boolean fitsInInventory, boolean canContainOtherObjects) {
		super(name, title, actorLevel, health, strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, travelDistance, bed, inventory, showInventory, fitsInInventory,
				canContainOtherObjects);

		aiRoutine = new AIRoutineForTrader();
	}

	@Override
	public void postLoad() {
		super.postLoad();
		aiRoutine = new AIRoutineForTrader();
	}

	@Override
	public Actor makeCopy(Square square) {

		return new Trader(name, title, actorLevel, (int) totalHealth, strength, dexterity, intelligence, endurance,
				imageTexturePath, square, travelDistance, null, inventory, showInventory, fitsInInventory,
				canContainOtherObjects);
	}

}
