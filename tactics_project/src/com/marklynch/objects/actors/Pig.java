package com.marklynch.objects.actors;

import java.util.ArrayList;

import com.marklynch.ai.routines.AIRoutineForPig;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionPet;
import com.marklynch.objects.weapons.Weapon;

public class Pig extends Animal {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public StructureRoom room;
	public Structure shop;
	public GameObject shopSign;
	public Weapon broom;

	public Pig() {
		super();
		type = "Pig";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
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
		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
		return actions;
	}

	public Pig makeCopy(String name, Square square, Faction faction, GameObject bed, GameObject[] mustHaves,
			GameObject[] mightHaves, Area area) {
		Pig actor = new Pig();
		setInstances(actor);
		super.setAttributesForCopy(name, actor, square, faction, bed, 0, mustHaves, mightHaves, area);

		return actor;
	}

}
