package com.marklynch.objects.units;

import java.util.ArrayList;

import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionSquash;

public class TinyNeutralWildAnimal extends HerbivoreWildAnimal {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public TinyNeutralWildAnimal() {
		super();
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	// @Override
	// public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
	// return new ActionMove(performer, this.squareGameObjectIsOn, true);
	// }

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(new ActionSquash(performer, this, true));
		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
		return actions;
	}

	@Override
	public void postLoad1() {
		super.postLoad1();
	}

	@Override
	public void postLoad2() {
		super.postLoad2();
	}

	@Override
	public TinyNeutralWildAnimal makeCopy(String name, Square square, Faction faction, GameObject bed,
			GameObject[] mustHaves, GameObject[] mightHaves, Area area) {
		TinyNeutralWildAnimal actor = new TinyNeutralWildAnimal();
		setInstances(actor);
		super.setAttributesForCopy(actor, square, faction, bed, 0, mustHaves, mightHaves, area);

		return actor;
	}

}
