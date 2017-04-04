package com.marklynch.level.quest.caveoftheblind;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.structure.StructureRoom;
import com.marklynch.objects.Bed;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;

import mdesl.graphics.Color;

public class Blind extends Actor {

	public StructureRoom roomLivingIn;

	public Blind(String name, String title, int actorLevel, int health, int strength, int dexterity, int intelligence,
			int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance, int sight, Bed bed,
			Inventory inventory, boolean showInventory, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting, float soundDampening,
			Color light, float lightHandleX, float lightHandlY, boolean stackable, float fireResistance,
			float iceResistance, float electricResistance, float poisonResistance, Actor owner, Faction faction,
			float handAnchorX, float handAnchorY, float headAnchorX, float headAnchorY, float bodyAnchorX,
			float bodyAnchorY, float legsAnchorX, float legsAnchorY, StructureRoom roomLivingIn) {
		super(name, title, actorLevel, health, strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, travelDistance, sight, bed, inventory, showInventory, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, iceResistance, electricResistance, poisonResistance, owner,
				faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY, bodyAnchorX, bodyAnchorY, legsAnchorX,
				legsAnchorY);
		aiRoutine = new AIRoutineForBlind(this);
		this.roomLivingIn = roomLivingIn;
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
		aiRoutine = new AIRoutineForBlind(this);
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		if (this == Game.level.player) {
			return null;
		}
		if (performer.equipped instanceof Weapon) {
			Weapon weapon = (Weapon) performer.equipped;
			if (weapon.hasRange(performer.straightLineDistanceTo(this.squareGameObjectIsOn))) {
				return new ActionAttack(performer, this);
			}
		}

		return null;
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		if (performer.equipped instanceof Weapon) {
			Weapon weapon = (Weapon) performer.equipped;
			if (weapon.hasRange(performer.straightLineDistanceTo(this.squareGameObjectIsOn))) {
				actions.add(new ActionAttack(performer, this));
			}
		}

		// ArrayList<Action> actions = new ArrayList<Action>();
		// if (this != Game.level.player) {
		// // Talk
		// actions.add(new ActionTalk(performer, this));
		// // Inherited from object (attack...)
		// actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
		// // Inherited from squre (move/swap squares)
		// actions.addAll(squareGameObjectIsOn.getAllActionsPerformedOnThisInWorld(performer));
		// }

		// if (this == Game.level.player) {
		// // self action
		// Action utilityAction =
		// performer.equippedWeapon.getUtilityAction(performer);
		// if (utilityAction != null) {
		// actions.add(utilityAction);
		// }
		// }

		return actions;
	}

	public Blind makeCopy(Square square, Faction faction, StructureRoom roomLivingIn) {

		Blind actor = new Blind(name, title, actorLevel, (int) totalHealth, strength, dexterity, intelligence,
				endurance, imageTexturePath, square, travelDistance, sight, null, inventory.makeCopy(), showInventory,
				fitsInInventory, canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio,
				heightRatio, soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, iceResistance, electricResistance,
				poisonResistance, owner, faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY, bodyAnchorX,
				bodyAnchorY, legsAnchorX, legsAnchorY, roomLivingIn);
		return actor;
	}

}
