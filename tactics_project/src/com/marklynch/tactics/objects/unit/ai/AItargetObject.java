package com.marklynch.tactics.objects.unit.ai;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;

public class AITargetObject extends AI {

	transient GameObject object;

	// For loading and saving
	String objectGUID;

	public AITargetObject() {
		name = this.getClass().getSimpleName();
	}

	public AITargetObject(GameObject object) {
		this.object = object;
		objectGUID = object.guid;
		name = "AITargetObject";
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

	@Override
	public AITargetObject makeCopy() {
		AITargetObject ai = new AITargetObject(object);
		ai.name = new String(name);
		return ai;
	}
}
