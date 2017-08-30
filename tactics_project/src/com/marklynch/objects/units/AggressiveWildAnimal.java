package com.marklynch.objects.units;

import com.marklynch.ai.routines.AIRoutineForWildAnimal;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

import mdesl.graphics.Color;

public class AggressiveWildAnimal extends Animal {

	public AggressiveWildAnimal(String name, String title, int actorLevel, int health, int strength, int dexterity,
			int intelligence, int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance,
			int sight, GameObject bed, Inventory inventory, float widthRatio, float heightRatio, float drawOffsetX,
			float drawOffsetY, float soundWhenHit, float soundWhenHitting, float soundDampening, Color light,
			float lightHandleX, float lightHandlY, boolean stackable, float fireResistance, float waterResistance,
			float electricResistance, float poisonResistance, float slashResistance, float weight, Actor owner,
			Faction faction, float handAnchorX, float handAnchorY, float headAnchorX, float headAnchorY,
			float bodyAnchorX, float bodyAnchorY, float legsAnchorX, float legsAnchorY, GameObject[] mustHaves,
			GameObject[] mightHaves) {
		super(name, title, actorLevel, health, strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, travelDistance, sight, bed, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, owner, faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY, bodyAnchorX, bodyAnchorY,
				legsAnchorX, legsAnchorY, mustHaves, mightHaves);
		aiRoutine = new AIRoutineForWildAnimal(this);
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		aiRoutine = new AIRoutineForWildAnimal(this);
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	public AggressiveWildAnimal makeCopy(Square square, Faction faction, GameObject bed, GameObject[] mustHaves,
			GameObject[] mightHaves) {

		AggressiveWildAnimal actor = new AggressiveWildAnimal(name, title, actorLevel, (int) totalHealth, strength,
				dexterity, intelligence, endurance, imageTexturePath, square, travelDistance, sight, bed,
				new Inventory(), widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting,
				soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance,
				electricResistance, poisonResistance, slashResistance, weight, owner, faction, handAnchorX, handAnchorY,
				headAnchorX, headAnchorY, bodyAnchorX, bodyAnchorY, legsAnchorX, legsAnchorY, mustHaves, mightHaves);
		return actor;
	}

}
