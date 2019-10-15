package com.marklynch.objects.actors;

import com.marklynch.utils.CopyOnWriteArrayList;

import com.marklynch.actions.Action;
import com.marklynch.actions.ActionAttack;
import com.marklynch.actions.ActionScream;
import com.marklynch.ai.routines.AIRoutineForBlind;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.armor.Weapon;
import com.marklynch.objects.inanimateobjects.BrokenGlass;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.tools.Bell;
import com.marklynch.objects.tools.Tool;

public class Blind extends Monster {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	public StructureRoom roomLivingIn;

	public Blind() {
		super();
		soundClassesToReactTo = new Class[] { Bell.class, Tool.class, Weapon.class, BrokenGlass.class };
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
	// public CopyOnWriteArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor
	// performer) {
	// CopyOnWriteArrayList<Action> actions = new CopyOnWriteArrayList<Action>();
	// actions.add(new ActionAttack(performer, this));
	// actions.add(new ActionThrow(performer, this, performer.equipped));
	//
	// // CopyOnWriteArrayList<Action> actions = new CopyOnWriteArrayList<Action>();
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

	public Blind makeCopy(Square square, Faction faction, int gold, StructureRoom roomLivingIn, GameObject[] mustHaves,
			GameObject[] mightHaves) {

		Blind actor = new Blind();
		setInstances(actor);
		super.setAttributesForCopy("Blind", actor, square, faction, null, 0, mustHaves, mightHaves, area);

		actor.roomLivingIn = roomLivingIn;
		return actor;
	}

}
