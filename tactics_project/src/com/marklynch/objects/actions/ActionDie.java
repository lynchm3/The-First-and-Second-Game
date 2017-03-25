package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.level.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.WildAnimal;

public class ActionDie extends Action {

	public static final String ACTION_NAME = "Loiter here";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor performer;
	Square target;

	public ActionDie(Actor performer, Square target) {
		super(ACTION_NAME);
		this.performer = performer;
		this.target = target;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();

	}

	@Override
	public void perform() {
		if (!enabled)
			return;

		// Remove from draw/update
		performer.squareGameObjectIsOn.inventory.remove(performer);
		// this.faction.actors.remove(this);

		// add a carcass
		GameObject body;
		if (performer instanceof WildAnimal)
			body = Templates.CARCASS.makeCopy(performer.name + " carcass", performer.squareGameObjectIsOn, null);
		else
			body = Templates.CORPSE.makeCopy(performer.name + " corpse", performer.squareGameObjectIsOn, null);

		ArrayList<GameObject> gameObjectsInInventory = (ArrayList<GameObject>) performer.inventory.getGameObjects()
				.clone();
		for (GameObject gameObjectInInventory : gameObjectsInInventory) {
			performer.inventory.remove(gameObjectInInventory);
			body.inventory.add(gameObjectInInventory);
			gameObjectInInventory.owner = null;
		}

		performer.actions.add(this);
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}
}
