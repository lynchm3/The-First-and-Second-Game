package com.marklynch.level.quest.caveoftheblind;

import java.util.ArrayList;

import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionScream;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Monster;

public class Blind extends Monster {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public StructureRoom roomLivingIn;

	public Blind() {
		super();
		sight = 1;
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

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public Blind makeCopy(Square square, Faction faction, StructureRoom roomLivingIn, GameObject[] mustHaves,
			GameObject[] mightHaves) {

		Blind actor = new Blind();
		setInstances(actor);
		actor.squareGameObjectIsOn = square;
		actor.faction = faction;
		this.roomLivingIn = roomLivingIn;

		actor.title = title;
		actor.name = name;
		actor.area = area;
		actor.level = level;
		actor.totalHealth = actor.remainingHealth = totalHealth;
		actor.strength = strength;
		actor.dexterity = dexterity;
		actor.intelligence = intelligence;
		actor.endurance = endurance;
		actor.imageTexturePath = imageTexturePath;
		// actor.squareGameObjectIsOn = null;
		actor.travelDistance = travelDistance;
		actor.sight = sight;
		// actor.bed = null;
		// actor.inventory = new Inventory();
		actor.widthRatio = widthRatio;
		actor.heightRatio = heightRatio;
		actor.drawOffsetRatioX = drawOffsetRatioX;
		actor.drawOffsetRatioY = drawOffsetRatioY;
		actor.soundWhenHit = soundWhenHit;
		actor.soundWhenHitting = soundWhenHitting;
		// actor.soundDampening = 1f;
		// actor.stackable = false;
		actor.weight = weight;
		actor.handAnchorX = handAnchorX;
		actor.handAnchorY = handAnchorY;
		actor.headAnchorX = headAnchorX;
		actor.headAnchorY = headAnchorY;
		actor.bodyAnchorX = bodyAnchorX;
		actor.bodyAnchorY = bodyAnchorY;
		actor.legsAnchorX = legsAnchorX;
		actor.legsAnchorY = legsAnchorY;
		actor.canOpenDoors = canOpenDoors;
		actor.canEquipWeapons = canEquipWeapons;
		// gold
		actor.templateId = templateId;

		actor.aiRoutine = aiRoutine.getInstance(actor);

		actor.init(0, mustHaves, mightHaves);
		return actor;
	}

}
