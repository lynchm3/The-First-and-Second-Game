package com.marklynch.level.quest.caveoftheblind;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.structure.StructureRoom;
import com.marklynch.level.constructs.structure.StructureSection;
import com.marklynch.objects.Bed;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.tools.Bell;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Mort extends Actor {

	public StructureRoom mortsMine;
	public StructureSection mortsRooms;
	public StructureRoom mortsRoom;
	public StructureRoom mortsVault;
	public Square mortsStandingSpot = Game.level.squares[80][42];
	public GameObject mortsMeatChunk;
	public Bell mortsBell;
	public boolean performingFeedingDemo = false;
	public QuestCaveOfTheBlind questCaveOfTheBlind;
	public Square mortsRoomDoorway;
	public Square mortsVaultDoorway;

	public Mort(String name, String title, int actorLevel, int health, int strength, int dexterity, int intelligence,
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
				heightRatio, soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, iceResistance, electricResistance,
				poisonResistance, owner, faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY, bodyAnchorX,
				bodyAnchorY, legsAnchorX, legsAnchorY);
		return actor;
	}

}
