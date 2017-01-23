package com.marklynch.tactics.objects.unit.ai.routines;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;

public class AIRoutineTargetObject extends AIRoutine {
	public final static String[] editableAttributes = { "name", "object" };
	public transient GameObject object;

	// For loading and saving
	public String objectGUID;

	public AIRoutineTargetObject() {
		name = this.getClass().getSimpleName();
	}

	public AIRoutineTargetObject(GameObject object) {
		name = this.getClass().getSimpleName();
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

	@Override
	public AIRoutineTargetObject makeCopy() {
		AIRoutineTargetObject ai = new AIRoutineTargetObject(object);
		ai.name = new String(name);
		return ai;
	}
}
