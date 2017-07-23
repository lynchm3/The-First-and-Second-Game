package com.marklynch.objects.units;

import java.util.ArrayList;

import com.marklynch.ai.routines.AIRoutineForPig;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.structure.Structure;
import com.marklynch.level.constructs.structure.StructureRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Bed;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.Readable;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.actions.ActionPet;
import com.marklynch.objects.weapons.Weapon;

import mdesl.graphics.Color;

public class Pig extends Actor {

	public StructureRoom room;
	public Structure shop;
	public Readable shopSign;
	public Weapon broom;

	public Pig(String name, String title, int actorLevel, int health, int strength, int dexterity, int intelligence,
			int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance, int sight, Bed bed,
			Inventory inventory, boolean showInventory, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting, float soundDampening,
			Color light, float lightHandleX, float lightHandlY, boolean stackable, float fireResistance,
			float waterResistance, float electricResistance, float poisonResistance, float weight, Actor owner,
			Faction faction, float handAnchorX, float handAnchorY, float headAnchorX, float headAnchorY,
			float bodyAnchorX, float bodyAnchorY, float legsAnchorX, float legsAnchorY) {
		super(name, title, actorLevel, health, strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, travelDistance, sight, bed, inventory, showInventory, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, false, false, widthRatio,
				heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, weight, owner, faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY,
				bodyAnchorX, bodyAnchorY, legsAnchorX, legsAnchorY);

		aiRoutine = new AIRoutineForPig(this);
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		aiRoutine = new AIRoutineForPig(this);
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return new ActionMove(performer, this.squareGameObjectIsOn, true);

	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		if (performer.attackers.contains(this)) {
			return new ActionAttack(performer, this);
		} else {
			return new ActionPet(performer, this);
		}
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(new ActionPet(performer, this));
		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
		return actions;
	}

	public Pig makeCopy(String name, Square square, Faction faction, Bed bed) {

		Pig actor = new Pig(name, title, actorLevel, (int) totalHealth, strength, dexterity, intelligence, endurance,
				imageTexturePath, square, travelDistance, sight, bed, inventory.makeCopy(), showInventory,
				fitsInInventory, canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio,
				heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, weight, owner, faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY,
				bodyAnchorX, bodyAnchorY, legsAnchorX, legsAnchorY);
		return actor;
	}

}
