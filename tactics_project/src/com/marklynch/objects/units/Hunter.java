package com.marklynch.objects.units;

import com.marklynch.ai.routines.AIRoutineForHunter;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Faction;
import com.marklynch.objects.Bed;
import com.marklynch.objects.Inventory;

import mdesl.graphics.Color;

public class Hunter extends Actor {

	public Hunter(String name, String title, int actorLevel, int health, int strength, int dexterity, int intelligence,
			int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance, int sight, Bed bed,
			Inventory inventory, boolean showInventory, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting, float soundDampening,
			Color light, float lightHandleX, float lightHandlY, boolean stackable, float fireResistance,
			float iceResistance, float electricResistance, float poisonResistance, Actor owner, Faction faction,
			float handAnchorX, float handAnchorY, float headAnchorX, float headAnchorY, float bodyAnchorX,
			float bodyAnchorY, float legsAnchorX, float legsAnchorY) {
		super(name, title, actorLevel, health, strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, travelDistance, sight, bed, inventory, showInventory, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, iceResistance, electricResistance, poisonResistance, owner,
				faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY, bodyAnchorX, bodyAnchorY, legsAnchorX,
				legsAnchorY);
		aiRoutine = new AIRoutineForHunter(this);
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		aiRoutine = new AIRoutineForHunter(this);
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	@Override
	public Hunter makeCopy(Square square, Faction faction, Bed bed) {

		Hunter actor = new Hunter(name, title, actorLevel, (int) totalHealth, strength, dexterity, intelligence,
				endurance, imageTexturePath, square, travelDistance, sight, bed, inventory.makeCopy(), showInventory,
				fitsInInventory, canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio,
				heightRatio, soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, iceResistance, electricResistance,
				poisonResistance, owner, faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY, bodyAnchorX,
				bodyAnchorY, legsAnchorX, legsAnchorY);
		return actor;
	}

}
