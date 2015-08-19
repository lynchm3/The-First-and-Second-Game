package com.marklynch.tactics.objects.unit.ai;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;
import com.marklynch.tactics.objects.unit.Actor;

public class AITargetObject extends AI {

	transient GameObject object;

	// For loading and saving
	String objectGUID;

	public AITargetObject(GameObject object, Actor actor) {
		super(actor);
		this.object = object;
		objectGUID = object.guid;
	}

	@Override
	public boolean move() {
		if (object != null && object.remainingHealth > 0)
			return this.moveTowardsTargetToAttack(object);
		else
			return false;
	}

	@Override
	public boolean attack() {

		if (object != null && object.remainingHealth > 0)
			return this.attackTarget(object);
		else
			return false;
	}

	@Override
	public void postLoad() {
		super.postLoad();
		object = Game.level.findObjectFromGUID(objectGUID);
	}
}
