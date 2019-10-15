package com.marklynch.objects.actors;

import com.marklynch.actions.Action;
import com.marklynch.actions.ActionSquash;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.CopyOnWriteArrayList;

public class TinyNeutralWildAnimal extends HerbivoreWildAnimal {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

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
	public CopyOnWriteArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		CopyOnWriteArrayList<Action> actions = new CopyOnWriteArrayList<Action>(Action.class);
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
		super.setAttributesForCopy(name, actor, square, faction, bed, 0, mustHaves, mightHaves, area);

		return actor;
	}

}
