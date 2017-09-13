package com.marklynch.level.quest.caveoftheblind;

import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionScream;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Monster;

import mdesl.graphics.Color;

public class Blind extends Monster {

	public StructureRoom roomLivingIn;

	public Blind(String name, String title, int actorLevel, int health, int strength, int dexterity, int intelligence,
			int endurance, String imagePath, Square squareActorIsStandingOn, int travelDistance, int sight,
			GameObject bed, Inventory inventory, float widthRatio, float heightRatio, float drawOffsetX,
			float drawOffsetY, float soundWhenHit, float soundWhenHitting, float soundDampening, Color light,
			float lightHandleX, float lightHandlY, boolean stackable, float fireResistance, float waterResistance,
			float electricResistance, float poisonResistance, float slashResistance, float weight, Actor owner,
			Faction faction, float handAnchorX, float handAnchorY, float headAnchorX, float headAnchorY,
			float bodyAnchorX, float bodyAnchorY, float legsAnchorX, float legsAnchorY, StructureRoom roomLivingIn,
			GameObject[] mustHaves, GameObject[] mightHaves, int templateId) {
		super(name, title, actorLevel, health, strength, dexterity, intelligence, endurance, imagePath,
				squareActorIsStandingOn, travelDistance, sight, bed, inventory, false, true, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, waterResistance, electricResistance, poisonResistance,
				slashResistance, weight, owner, faction, handAnchorX, handAnchorY, headAnchorX, headAnchorY,
				bodyAnchorX, bodyAnchorY, legsAnchorX, legsAnchorY, mustHaves, mightHaves, templateId);
		this.roomLivingIn = roomLivingIn;
		aiRoutine = new AIRoutineForBlind(this);
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
		return new ActionAttack(performer, this);
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionAttack(performer, this);
	}

	// @Override
	// public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor
	// performer) {
	// ArrayList<Action> actions = new ArrayList<Action>();
	// actions.add(new ActionAttack(performer, this));
	// actions.add(new ActionThrow(performer, this, performer.equipped));
	//
	// // ArrayList<Action> actions = new ArrayList<Action>();
	// // if (this != Game.level.player) {
	// // // Talk
	// // actions.add(new ActionTalk(performer, this));
	// // // Inherited from object (attack...)
	// // actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
	// // // Inherited from squre (move/swap squares)
	// //
	// actions.addAll(squareGameObjectIsOn.getAllActionsPerformedOnThisInWorld(performer));
	// // }
	//
	// // if (this == Game.level.player) {
	// // // self action
	// // Action utilityAction =
	// // performer.equippedWeapon.getUtilityAction(performer);
	// // if (utilityAction != null) {
	// // actions.add(utilityAction);
	// // }
	// // }
	//
	// return actions;
	// }

	@Override
	public void attackedBy(Object attacker, Action action) {
		new ActionScream(this).perform();
		super.attackedBy(attacker, action);
	}

	public Blind makeCopy(Square square, Faction faction, StructureRoom roomLivingIn, GameObject[] mustHaves,
			GameObject[] mightHaves) {

		Blind actor = new Blind(name, title, actorLevel, (int) totalHealth, strength, dexterity, intelligence,
				endurance, imageTexturePath, square, travelDistance, sight, null, new Inventory(), widthRatio,
				heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light,
				lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, owner, faction, handAnchorX, handAnchorY, headAnchorX,
				headAnchorY, bodyAnchorX, bodyAnchorY, legsAnchorX, legsAnchorY, roomLivingIn, mustHaves, mightHaves,
				templateId);
		return actor;
	}

}
