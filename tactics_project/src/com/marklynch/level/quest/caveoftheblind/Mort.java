package com.marklynch.level.quest.caveoftheblind;

import com.marklynch.level.Square;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.structure.StructureRoom;
import com.marklynch.objects.Bed;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Bell;

import mdesl.graphics.Color;

public class Mort extends Actor {

	public StructureRoom mortsMine;
	public StructureRoom mortsRoom;
	public GameObject mortsMeatChunk;
	public Bell mortsBell;
	public boolean performingFeedingDemo = false;
	public QuestCaveOfTheBlind questCaveOfTheBlind;

	public Mort(String name, String title, int actorLevel, int health, int strength, int dexterity, int intelligence,
			int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance, int sight, Bed bed,
			Inventory inventory, boolean showInventory, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting, Color light,
			float lightHandleX, float lightHandlY, boolean stackable, float fireResistance, float iceResistance,
			float electricResistance, float poisonResistance, Faction faction, float anchorX, float anchorY,
			float hearing) {
		super(name, title, actorLevel, health, strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, travelDistance, sight, bed, inventory, showInventory, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, light, lightHandleX, lightHandlY, stackable,
				fireResistance, iceResistance, electricResistance, poisonResistance, faction, anchorX, anchorY,
				hearing);
		aiRoutine = new AIRoutineForMort(this);
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		aiRoutine = new AIRoutineForMort(this);
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	@Override
	public Mort makeCopy(Square square, Faction faction, Bed bed) {

		Mort actor = new Mort(name, title, actorLevel, (int) totalHealth, strength, dexterity, intelligence, endurance,
				imageTexturePath, square, travelDistance, sight, bed, inventory.makeCopy(), showInventory,
				fitsInInventory, canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio,
				heightRatio, soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, light, lightHandleX,
				lightHandlY, stackable, fireResistance, iceResistance, electricResistance, poisonResistance, faction,
				anchorX, anchorY, hearing);
		return actor;
	}

}
